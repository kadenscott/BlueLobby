package dev.kscott.bluelobby.interfaces.view;

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

    private final @NonNull ChestInterface ui;

    private final @NonNull Inventory inventory;

    /**
     * Constructs {@code ChestUIView}.
     *
     * @param ui the ui
     */
    public ChestView(final @NonNull ChestInterface ui) {
        // Assing variables
        this.ui = ui;

        @NonNull ChestPane pane = new ChestPane(ui.length(), ui.height());

        for (final @NonNull Transformation<GridPane> transformation : ui.transformations()) {
            pane = (ChestPane) transformation.transform(pane);
        }

        // Create the inventory
        this.inventory = Bukkit.createInventory(this, ui.height() * 9, ui.title());

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

    public void open(final @NonNull Player player) {
        System.out.println("Opening " + this.inventory.toString());
        player.openInventory(this.inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
