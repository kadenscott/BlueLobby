package dev.kscott.bluelobby.fishing;

import com.google.inject.Inject;
import dev.kscott.bluelobby.items.ItemService;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Listeners for the fishing mechanic.
 */
public class FishingListener implements Listener {

    /**
     * The item service.
     */
    private final @NonNull ItemService itemService;

    /**
     * Constructs {@code FishingListener}.
     *
     * @param itemService the item service
     */
    @Inject
    public FishingListener(final @NonNull ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Handles the fish event.
     *
     * @param event the event
     */
    @EventHandler
    public void onFishEvent(final @NonNull PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            final @Nullable Entity caughtEntity = event.getCaught();

            if (caughtEntity == null) {
                return;
            }

            caughtEntity.remove();

            event.getPlayer().getInventory().addItem(this.itemService.random().itemStack());
        }
    }

}
