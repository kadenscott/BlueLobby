package dev.kscott.bluelobby.fishing;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.kscott.bluelobby.items.Item;
import dev.kscott.bluelobby.items.ItemService;
import dev.kscott.bluelobby.items.type.fish.TheWavyEffectItem;
import dev.kscott.bluelobby.spirit.Spirit;
import dev.kscott.bluelobby.spirit.SpiritService;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

/**
 * Listeners for the fishing mechanic.
 */
@Singleton
public class FishingService implements Listener {

    /**
     * The loot index.
     */
    private final @NonNull LootIndex loot;

    /**
     * The item service.
     */
    private final @NonNull ItemService itemService;

    /**
     * The spirit service.
     */
    private final @NonNull SpiritService spiritService;

    /**
     * The fishing leaderboard hologram.
     */
    private final @NonNull Hologram fishingHologram;

    /**
     * The plugin.
     */
    private final @NonNull JavaPlugin plugin;

    private final @NonNull LuckPerms luckPerms;

    /**
     * Constructs {@code FishingListener}.
     *
     * @param itemService the item service
     * @param spiritService the spirit service
     */
    @Inject
    public FishingService(final @NonNull ItemService itemService,
                          final @NonNull SpiritService spiritService,
                          final @NonNull JavaPlugin plugin,
                          final @NonNull LuckPerms luckPerms) {
        this.itemService = itemService;
        this.loot = new BasicLootIndex();
        this.plugin = plugin;
        this.spiritService = spiritService;
        this.luckPerms = luckPerms;
        this.fishingHologram = HologramsAPI.createHologram(this.plugin, new Location(Bukkit.getWorld("world"), -93.5, 188, 157.5));

        new BukkitRunnable() {
            @Override
            public void run() {
                updateFishingHolograms();
            }
        }.runTaskTimer(this.plugin, 0, 100);
    }

    private void updateFishingHolograms() {
        // Make this load permanent fishing data

        final @NonNull Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        if (players.isEmpty()) {
            return;
        }

        this.fishingHologram.clearLines();
        this.fishingHologram.appendTextLine("Today's best fishers");

        List<? extends Player> sortedPlayers = players
                .stream()
                .sorted((p1, p2) -> {
                    final @NonNull Spirit spirit1 = this.spiritService.spirit(p1.getUniqueId());
                    final @NonNull Spirit spirit2 = this.spiritService.spirit(p2.getUniqueId());

                    final int caught1 = this.getTotalFishCaught(spirit1);
                    final int caught2 = this.getTotalFishCaught(spirit2);

                    return Integer.compare(caught2, caught1);
                })
                .toList();

        int index = sortedPlayers.size() > 5 ? 5 : sortedPlayers.size();

        for (int i = 0; i < index; i++) {
            final @NonNull Player player = sortedPlayers.get(i);
            this.fishingHologram.appendTextLine(player.getName()+" has caught "+this.getTotalFishCaught(this.spiritService.spirit(player.getUniqueId())));
        }
    }

    private int getTotalFishCaught(final @NonNull Spirit spirit) {
        int amount = 0;

        final @NonNull User user = Objects.requireNonNull(this.luckPerms.getUserManager().getUser(spirit.uuid()));

        final var meta = user.getCachedData().getMetaData().getMeta();

        for (final @NonNull String key : meta.keySet()) {
            if (!key.startsWith("fishing.catch")) continue;

            final @NonNull String[] nodes = key.split("\\.");

            if (nodes.length != 4) continue;

            if (nodes[3].equals("count")) {
                amount = amount + Integer.parseInt(this.spiritService.get(spirit, key));
            }
        }

        return amount;
    }

    /**
     * Handles the fish event.
     *
     * @param event the event
     */
    @EventHandler
    public void onFishEvent(final @NonNull PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.FISHING) {
            final @NonNull Player player = event.getPlayer();
            final @Nullable Item item = this.itemService.item(player.getInventory().getItemInMainHand());

            if (item == null) return;

            if (item instanceof TheWavyEffectItem) {
                event.getHook().setMaxWaitTime(Math.max(100, 500 - this.getTotalFishCaught(this.spiritService.spirit(player.getUniqueId()))));
                event.getHook().setMinWaitTime(20);
            }
        }

        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            final @NonNull Player player = event.getPlayer();
            final @Nullable Entity caughtEntity = event.getCaught();

            if (caughtEntity == null) {
                return;
            }

            caughtEntity.remove();

            final @NonNull Map<Item, Integer> itemMap = this.loot.roll();

            for (final var entry : itemMap.entrySet()) {
                final @NonNull Item item = entry.getKey();
                final int amount = entry.getValue();
                final @NonNull ItemStack itemStack = item.itemStack();

                itemStack.setAmount(amount);
                player.getInventory().addItem(itemStack);

                saveFishData(player, item, amount);
            }

        }
    }

    /**
     * Save fish data.
     *
     * @param player the player
     * @param item the item
     * @param amount the amount
     */
    private void saveFishData(final @NonNull Player player,
                              final @NonNull Item item,
                              final int amount) {
        final @NonNull Spirit spirit = this.spiritService.spirit(player.getUniqueId());

        final @NonNull String id = item.id();

        final @NonNull String key = "fishing.catch."+id+".count";

        int currentAmount = Integer.parseInt(this.spiritService.getOrDefault(spirit, key, "0"));

        currentAmount += amount;

        this.spiritService.set(spirit, key, Integer.toString(currentAmount));
    }

}
