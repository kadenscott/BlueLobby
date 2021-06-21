package dev.kscott.bluelobby.listeners;

import dev.kscott.bluelobby.menu.core.GameGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.StonecutterInventory;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Listens for player-related gui events.
 */
public class PlayerOpenGuiListener implements Listener {

    @EventHandler
    public void onOpenGui(final @NonNull InventoryOpenEvent event) {
        final @NonNull Inventory inventory = event.getInventory();

        if (inventory.getHolder() instanceof GameGui) {
            final @NonNull StonecutterInventory stonecutter = (StonecutterInventory) inventory;
        }
    }

}
