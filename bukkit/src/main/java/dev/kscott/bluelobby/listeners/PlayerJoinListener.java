package dev.kscott.bluelobby.listeners;

import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import java.util.List;

/**
 * Listens on the player join event.
 */
public class PlayerJoinListener implements Listener {

    /**
     * The plugin's reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * Constructs {@code PlayerJoinListener}.
     *
     * @param plugin the plugin's reference
     */
    @Inject
    public PlayerJoinListener(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles the player join event.
     *
     * @param event the event
     */
    @EventHandler
    public void handlePlayerJoin(final @NonNull PlayerJoinEvent event) {
        final @NonNull Player player = event.getPlayer();

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
