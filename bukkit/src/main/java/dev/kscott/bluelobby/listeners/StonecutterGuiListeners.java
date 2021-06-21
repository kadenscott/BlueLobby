package dev.kscott.bluelobby.listeners;

import dev.kscott.bluelobby.menu.core.GameGui;
import io.papermc.paper.event.player.PlayerStonecutterRecipeSelectEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.StonecutterInventory;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.inject.Inject;

/**
 * Stonecutter gui listeners.
 */
public class StonecutterGuiListeners implements Listener {

    /**
     * The plugin.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The NamespacedKey for game gui items.
     */
    private final @NonNull NamespacedKey gameGuiKey;

    /**
     * Constructs StonecutterGuiListeners.
     *
     * @param plugin the plugin
     */
    @Inject
    public StonecutterGuiListeners(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.gameGuiKey = new NamespacedKey(plugin, "game");
    }

//    @EventHandler
//    public void onPlayerMove(final @NonNull PlayerMoveEvent event) {
//        System.out.println(event.getPlayer());
//        final @NonNull Inventory inv = Bukkit.createInventory(null, InventoryType.STONECUTTER);
//        event.getPlayer().openStonecutter(new Location(Bukkit.getWorld("world"), 0, 0, 0), true).title();
//    }

    /**
     * Handles the recipe select event.
     *
     * @param event the event
     */
    @EventHandler
    public void onRecipeSelectEvent(final @NonNull PlayerStonecutterRecipeSelectEvent event) {
        System.out.println("event fired");

        final @NonNull StonecutterInventory inventory = event.getStonecutterInventory();

        if (!(inventory.getHolder() instanceof GameGui)) {
            System.out.println("not gg, return");
            return;
        }

        final @NonNull StonecuttingRecipe recipe = event.getStonecuttingRecipe();

        final @NonNull ItemStack itemStack = recipe.getResult();

        final @Nullable ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            System.out.println("null im, return");
            return;
        }

        final @NonNull PersistentDataContainer data =  itemMeta.getPersistentDataContainer();

        if (data.has(gameGuiKey, PersistentDataType.STRING)) {
            final @NonNull String gameId = data.get(gameGuiKey, PersistentDataType.STRING);

            System.out.println("has gameid, "+gameId);

            final @NonNull Player player = event.getPlayer();

            player.sendMessage(Component.text("Sending you to "+gameId+"..."));
        }
    }

}
