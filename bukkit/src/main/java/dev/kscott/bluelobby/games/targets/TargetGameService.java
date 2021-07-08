package dev.kscott.bluelobby.games.targets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import dev.kscott.bluelobby.area.LocationService;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.MetadataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Manages the target game.
 */
@Singleton
public class TargetGameService implements Listener {

    private final @NonNull List<Location> targetLocations;
    private final @NonNull JavaPlugin plugin;
    private final @NonNull LocationService locationService;
    private final @NonNull World world;
    private final @NonNull NPCRegistry npcRegistry;
    private final @NonNull UUID gameUuid;
    private final @NonNull List<TargetInstance> targets;
    private boolean active;

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

        this.npcRegistry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());

        this.targetLocations = List.of(
                new Location(world, 32.5, 184, 144.5, -126F, 0F),
                new Location(world, 27.5, 184, 138.5, -75F, 0F)
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

    public void tick() {

    }

    @EventHandler
    public void onTargetHit(final @NonNull NPCDamageByEntityEvent event) {
        final @NonNull NPC npc = event.getNPC();

        final @NonNull MetadataStore data = npc.data();

        if (data.get("target", false)) {
            if (data.has("game") && data.get("game").equals(this.gameUuid)) {
                final @NonNull Iterator<TargetInstance> iterator = this.targets.iterator();

                while (iterator.hasNext()) {
                    final @NonNull TargetInstance target = iterator.next();

                    if (target.uuid().equals(npc.getUniqueId())) {
                        target.remove();
                        iterator.remove();
                    }
                }
            }
        }
    }

    public @NonNull NPCRegistry registry() {
        return this.registry();
    }

    public @NonNull UUID uuid() {
        return this.gameUuid;
    }

}
