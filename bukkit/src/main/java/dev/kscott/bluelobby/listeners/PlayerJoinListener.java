package dev.kscott.bluelobby.listeners;

import dev.kscott.bluelobby.area.LocationService;
import dev.kscott.bluelobby.utils.Constants;
import org.incendo.interfaces.paper.PlayerViewer;
import org.incendo.interfaces.paper.transform.PaperTransform;
import org.incendo.interfaces.paper.type.BookInterface;
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
     * The interface to show the player when they first log in.
     */
    private final @NonNull BookInterface welcomeInterface;

    /**
     * The plugin's reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The location registry.
     */
    private final @NonNull LocationService locationService;

    /**
     * Constructs {@code PlayerJoinListener}.
     *
     * @param plugin the plugin's reference
     */
    @Inject
    public PlayerJoinListener(final @NonNull JavaPlugin plugin,
                              final @NonNull LocationService locationService) {
        this.plugin = plugin;
        this.locationService = locationService;
        this.welcomeInterface = BookInterface.builder()
                .addTransform(PaperTransform.bookText(Constants.Chat.MOTD_BOOK))
                .build();
    }

    /**
     * Handles the player join event.
     *
     * @param event the event
     */
    @EventHandler
    public void handlePlayerJoin(final @NonNull PlayerJoinEvent event) {
        final @NonNull Player player = event.getPlayer();

        player.teleportAsync(this.locationService.spawn());

        this.welcomeInterface.open(PlayerViewer.of(player));

        // Send the default pling 10 ticks after the player joins.
        new BukkitRunnable() {
            @Override
            public void run() {
                Constants.Sounds.playSoundForPlayer(player, Constants.Sounds.DEFAULT_CHIME);
            }
        }.runTaskLater(this.plugin, 10);
    }
}
