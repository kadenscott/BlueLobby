package dev.kscott.bluelobby.listeners;

import com.google.inject.Inject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.FormatFlagsConversionMismatchException;

public class FishingListener implements Listener {

    public FishingListener() {

    }

    @EventHandler
    public void onFishEvent(final @NonNull PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.REEL_IN) {
            event.getCaught().setVelocity(event.getCaught().getVelocity().multiply(1.2));
        }
    }

}
