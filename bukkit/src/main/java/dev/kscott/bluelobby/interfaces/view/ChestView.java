package dev.kscott.bluelobby.interfaces.view;

import dev.kscott.bluelobby.interfaces.arguments.InterfaceArguments;
import dev.kscott.bluelobby.interfaces.element.Element;
import dev.kscott.bluelobby.interfaces.element.ItemStackElement;
import dev.kscott.bluelobby.interfaces.pane.GridPane;
import dev.kscott.bluelobby.interfaces.paper.ChestInterface;
import dev.kscott.bluelobby.interfaces.paper.ChestPane;
import dev.kscott.bluelobby.interfaces.transformation.Transformation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class ChestView implements View, InventoryHolder {

    /**
     * The parent interface this view originated from.
     */
    private final @NonNull ChestInterface parentInterface;

    /**
     * The inventory of this view.
     */
    private final @NonNull Inventory inventory;

    /**
     * The interface's arguments.
     */
    private final @NonNull InterfaceArguments arguments;

    /**
     * The viewer.
     */
    private final @NonNull Player viewer;

    /**
     * Constructs {@code ChestUIView}.
     *
     * @param parentInterface the interface
     * @param viewer          the viewer
     * @param arguments       the interface arguments
     */
    public ChestView(
            final @NonNull ChestInterface parentInterface,
            final @NonNull Player viewer,
            final @NonNull InterfaceArguments arguments
    ) {
        // Assing variables
        this.parentInterface = parentInterface;
        this.viewer = viewer;
        this.arguments = arguments;

        @NonNull ChestPane pane = new ChestPane(parentInterface.length(), parentInterface.height());

        for (final @NonNull Transformation<GridPane> transformation : parentInterface.transformations()) {
            transformation.accept(pane, this);
        }

        // Create the inventory
        this.inventory = Bukkit.createInventory(this, parentInterface.height() * 9, parentInterface.title());

        // Place the inventory elements
        for (int x = 0; x < pane.length(); x++) {
            for (int y = 0; y < pane.height(); y++) {

                // Get element, add if ItemStackElement
                final @NonNull Element element = pane.element(x, y);
                if (element instanceof ItemStackElement itemStackElement) {
                    inventory.setItem(y * 9 + x, itemStackElement.itemStack());
                } else {
                    inventory.addItem(new ItemStack(Material.AIR));
                }
            }
        }
    }

    /**
     * Opens the inventory for a player.
     *
     * @param player the player
     */
    public void open(final @NonNull Player player) {
        player.openInventory(this.inventory);
    }

    /**
     * Returns the inventory.
     *
     * @return the inventory
     */
    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Returns the arguments.
     *
     * @return the arguments
     */
    public @NonNull InterfaceArguments arguments() {
        return arguments;
    }

    /**
     * Returns the viewer.
     *
     * @return the viewer
     */
    public @NonNull Player viewer() {
        return viewer;
    }
}
