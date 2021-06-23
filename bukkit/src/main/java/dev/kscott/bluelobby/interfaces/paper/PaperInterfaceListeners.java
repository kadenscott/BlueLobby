package dev.kscott.bluelobby.interfaces.paper;

import dev.kscott.bluelobby.interfaces.Interface;
import dev.kscott.bluelobby.interfaces.element.Element;
import dev.kscott.bluelobby.interfaces.element.ItemStackElement;
import dev.kscott.bluelobby.interfaces.view.ChestView;
import dev.kscott.bluelobby.interfaces.view.View;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * Event listeners used by interfaces.
 */
public class PaperInterfaceListeners implements Listener {

    /**
     * The set of open views.
     */
    private final @NonNull Set<View> openViews;

    /**
     * The plugin.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * Constructs {@code PaperInterfaceListeners}.
     *
     * @param plugin the plugin
     */
    @Inject
    public PaperInterfaceListeners(final @NonNull JavaPlugin plugin) {
        this.openViews = new HashSet<>();
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(final @NonNull InventoryClickEvent event) {
        final @NonNull Inventory inventory = event.getInventory();

        final @Nullable InventoryHolder holder = inventory.getHolder();

        if (holder == null) {
            return;
        }

        if (holder instanceof ChestView chestView) {
            int slot = event.getRawSlot();
            int x = slot % 9;
            int y = slot / 9;

            final @NonNull Element element = chestView.chestPane().element(x, y);

            if (element instanceof ItemStackElement itemStackElement) {
                itemStackElement.handleClick(event, chestView);
            }
        }
    }

    /**
     * Handles the inventory open event.
     *
     * @param event the event
     */
    @EventHandler
    public void onInventoryOpen(final @NonNull InventoryOpenEvent event) {
        final @NonNull Inventory inventory = event.getInventory();

        final @Nullable InventoryHolder holder = inventory.getHolder();

        if (holder == null) {
            return;
        }

        if (holder instanceof View view) {
            openViews.add(view);

            if (view instanceof ChestView chestView) {
                if (chestView.parentInterface().updating()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (chestView.isViewing()) {
                                chestView.parentInterface().open(chestView.viewer(), chestView.arguments());
                            }
                        }
                    }.runTaskLater(this.plugin, chestView.parentInterface().updateTicks());
                }
            }
        }
    }

    /**
     * Handles the inventory close event.
     *
     * @param event the event
     */
    @EventHandler
    public void onInventoryClose(final @NonNull InventoryCloseEvent event) {
        final @NonNull Inventory inventory = event.getInventory();

        final @Nullable InventoryHolder holder = inventory.getHolder();

        if (holder == null) {
            return;
        }

        if (holder instanceof View view) {
            openViews.remove(view);
        }
    }

}
