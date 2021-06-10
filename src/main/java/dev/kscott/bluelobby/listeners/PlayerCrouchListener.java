package dev.kscott.bluelobby.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Listens for player crouches.
 */
public class PlayerCrouchListener implements Listener {

    /**
     * Handles PlayerCrouchEvent.
     *
     * @param event the event
     */
    @EventHandler
    public void playerCrouchListener(final @NonNull PlayerToggleSneakEvent event) {
        event.getPlayer().sendMessage(Component.text("You sneaked"));
    }

}
