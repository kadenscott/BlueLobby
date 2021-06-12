package dev.kscott.bluelobby.listeners;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Handles server list pings.
 */
public class ServerListPingListener implements Listener {

    /**
     * Displays fancy server motd.
     *
     * @param event the event
     */
    @EventHandler
    public void serverListPing(final @NonNull PaperServerListPingEvent event) {
        event.motd(Component.text()
                .append(Constants.Chat.PING_MOTD_LINE_1)
                .append(Component.newline())
                .append(Constants.Chat.PING_MOTD_LINE_2)
                .asComponent());

        event.setMaxPlayers(50);
    }

}
