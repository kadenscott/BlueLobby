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
        new BukkitRunnable() {
            @Override
            public void run() {
                final @NonNull Player player = event.getPlayer();

                final @NonNull List<Component> motd = Constants.Chat.MOTD;

                for (final @NonNull Component component : motd) {
                    player.sendMessage(component);
                }

                Constants.Sounds.playSoundForPlayer(player, Constants.Sounds.DEFAULT_CHIME);
            }
        }.runTaskLater(this.plugin, 20 * 2);
    }

}
