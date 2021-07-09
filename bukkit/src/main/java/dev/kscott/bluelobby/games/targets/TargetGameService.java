package dev.kscott.bluelobby.games.targets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import dev.kscott.bluelobby.area.LocationService;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.MetadataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

/**
 * Manages the target game.
 */
@Singleton
public class TargetGameService implements Listener {

    private static final @NonNull String REGION_ID = "area_targets";

    private final @NonNull JavaPlugin plugin;
    private final @NonNull List<Location> targetLocations;
    private final @NonNull LocationService locationService;
    private final @NonNull World world;
    private final @NonNull NPCRegistry npcRegistry;
    private final @NonNull UUID gameUuid;
    private final @NonNull List<TargetInstance> targets;
    private final @NonNull Set<UUID> players;
    private final int targetCount;

    private boolean active;
    private long lastTargetSpawnTimestamp;

    /**
     * Constructs {@code TargetGameService}.
     *
     * @param locationService the location service
     * @param world           the target game world
     * @param plugin          the plugin
     */
    @Inject
    public TargetGameService(
            final @NonNull LocationService locationService,
            final @NonNull @Named("lobbyWorld") World world,
            final @NonNull JavaPlugin plugin
    ) {
        this.locationService = locationService;
        this.targets = new ArrayList<>();
        this.plugin = plugin;
        this.world = world;
        this.gameUuid = UUID.randomUUID();
        this.players = new HashSet<>();
        this.targetCount = 2;

        this.npcRegistry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());

        this.targetLocations = List.of(
                new Location(world, 32.5, 184, 144.5, -126F, 0F),
                new Location(world, 27.5, 184, 138.5, -75F, 0F),
                new Location(world, 29.5, 184, 126.5, -28F, 0F),
                new Location(world, 28.5, 184, 131.5, -40F, 0F),
                new Location(world, 35.5, 184, 125.5, -33F, 0F),
                new Location(world, 22.5, 184, 126.5, -37, 0F)
        );
    }

    public void start() {
        this.active = false;
        new BukkitRunnable() {
            @Override
            public void run() {
                tick();
            }
        }.runTaskTimer(this.plugin, 0, 0);
    }

    public @NonNull NPCRegistry registry() {
        return this.npcRegistry;
    }

    public @NonNull JavaPlugin plugin() {
        return this.plugin;
    }

    public @NonNull UUID uuid() {
        return this.gameUuid;
    }

    public void tick() {
        final long spawnDelta = System.currentTimeMillis() - this.lastTargetSpawnTimestamp;

        if (spawnDelta >= 2000) {
            if (this.targetCount > this.targets.size()) {
                loadTarget();
            }
        }
    }

    public boolean isPlaying(final @NonNull Player player) {
        return this.players.contains(player.getUniqueId());
    }

    public boolean hasTarget(final @NonNull Location location) {
        for (final @NonNull TargetInstance target : this.targets) {
            if (target.location().equals(location)) {
                return true;
            }
        }

        return false;
    }

    public @NonNull TargetInstance target(final @NonNull UUID uuid) {
        for (final @NonNull TargetInstance target : this.targets) {
            if (target.npc().getEntity().getUniqueId().equals(uuid)) {
                return target;
            }
        }

        throw new NullPointerException("No target with uuid found.");
    }

    public void handlePlayerJoin(final @NonNull Player player) {
        for (final @NonNull NPC npc : this.npcRegistry) {
            if (npc.getEntity() instanceof Player playerNpc) {
                player.showPlayer(this.plugin, playerNpc);
            }
        }
        this.players.add(player.getUniqueId());

        player.sendMessage(Component.text("Welcome to targets."));
    }

    public void handlePlayerLeave(final @NonNull Player player) {
        for (final @NonNull NPC npc : this.npcRegistry) {
            if (npc.getEntity() instanceof Player playerNpc) {
                player.hidePlayer(this.plugin, playerNpc);
            }
        }
        this.players.remove(player.getUniqueId());

        player.sendMessage(Component.text("Thanks for playing targets."));
    }

    private void loadTarget() {
        final @NonNull List<Location> shuffledList = new ArrayList<>(this.targetLocations);
        Collections.shuffle(shuffledList);

        for (final @NonNull Location location : shuffledList) {
            if (!hasTarget(location)) {
                final @NonNull TargetInstance target = new TargetInstance(location, this);
                this.targets.add(target);
                this.lastTargetSpawnTimestamp = System.currentTimeMillis();
                return;
            }
        }
    }

    @EventHandler
    private void onRegionEntryOrExit(final @NonNull PlayerMoveEvent event) {
        if (event.hasChangedBlock()) {
            final @NonNull Player player = event.getPlayer();

            if (locationService.inArea(player, REGION_ID) && !isPlaying(player)) {
                handlePlayerJoin(player);
                return;
            }

            if (!locationService.inArea(player, REGION_ID) && isPlaying(player)) {
                handlePlayerLeave(player);
                return;
            }
        }
    }

    @EventHandler
    private void onTargetHit(final @NonNull EntityDamageByEntityEvent event) {
        if (!event.getEntity().hasMetadata("NPC")) return;

        final @NonNull NPC npc = this.registry().getNPC(event.getEntity());

        final @NonNull MetadataStore data = npc.data();

        if (data.get("target", "false").equals("true")) {
            final @Nullable String gameUuid = data.get("game", null);

            if (gameUuid == null) {
                return;
            }

            if (gameUuid.equals(this.gameUuid.toString())) {
                final @NonNull TargetInstance target = this.target(event.getEntity().getUniqueId());

                final @NonNull Entity targetEntity = target.npc().getEntity();

                this.targets.removeIf(t -> t.npc().getEntity().getUniqueId().equals(event.getEntity().getUniqueId()));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        target.remove();
                    }
                }.runTask(this.plugin);
            }
        }
    }

    /**
     * Hides all active targets from the player.
     *
     * @param event the event
     */
    @EventHandler
    public void onPlayerJoin(final @NonNull PlayerJoinEvent event) {
        for (final @NonNull TargetInstance target : this.targets) {
            event.getPlayer().hidePlayer(this.plugin, (Player) target.npc().getEntity());
        }
    }

}
