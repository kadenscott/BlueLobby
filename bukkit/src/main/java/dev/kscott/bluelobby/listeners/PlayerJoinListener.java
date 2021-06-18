package dev.kscott.bluelobby.listeners;

import dev.kscott.bluelobby.location.LocationRegistry;
import dev.kscott.bluelobby.utils.Constants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

/**
 * Listens on the player join event.
 */
public class PlayerJoinListener implements Listener {

    /**
     * The plugin's reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The location registry.
     */
    private final @NonNull LocationRegistry locationRegistry;

    /**
     * Constructs {@code PlayerJoinListener}.
     *
     * @param plugin the plugin's reference
     */
    @Inject
    public PlayerJoinListener(final @NonNull JavaPlugin plugin,
                              final @NonNull LocationRegistry locationRegistry) {
        this.plugin = plugin;
        this.locationRegistry = locationRegistry;
    }

    /**
     * Handles the player join event.
     *
     * @param event the event
     */
    @EventHandler
    public void handlePlayerJoin(final @NonNull PlayerJoinEvent event) {
        final @NonNull Player player = event.getPlayer();

        player.teleportAsync(this.locationRegistry.spawn());

        Constants.Books.showIntroBook(player);

        // Send the default pling 10 ticks after the player joins.
        new BukkitRunnable() {
            @Override
            public void run() {
                Constants.Sounds.playSoundForPlayer(player, Constants.Sounds.DEFAULT_CHIME);
            }
        }.runTaskLater(this.plugin, 10);
    }
}
