package dev.kscott.bluelobby.fishing;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class FishingListener implements Listener {

    @EventHandler
    public void onFishEvent(final @NonNull PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            System.out.println(event.getCaught());
        }
    }

}
