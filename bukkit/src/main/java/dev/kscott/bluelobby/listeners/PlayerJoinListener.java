package dev.kscott.bluelobby.listeners;

import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.List;

/**
 * Listens on the player join event.
 */
public class PlayerJoinListener implements Listener {

    /**
     * Handles the player join event.
     *
     * @param event the event
     */
    @EventHandler
    public void handlePlayerJoin(final @NonNull PlayerJoinEvent event) {
        final @NonNull Player player = event.getPlayer();

        final @NonNull List<Component> motd = Constants.Chat.motd;

        for (final @NonNull Component component : motd) {
            player.sendMessage(component);
        }
    }

}
