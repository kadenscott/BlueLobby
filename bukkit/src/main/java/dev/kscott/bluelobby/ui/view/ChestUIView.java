package dev.kscott.bluelobby.ui.view;

import dev.kscott.bluelobby.ui.UI;
import dev.kscott.bluelobby.ui.pane.GridPane;
import dev.kscott.bluelobby.ui.paper.ChestPane;
import dev.kscott.bluelobby.ui.paper.ChestUI;
import dev.kscott.bluelobby.ui.transformation.Transformation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class ChestUIView implements UIView, InventoryHolder {

    private final @NonNull ChestUI ui;

    private final @NonNull Inventory inventory;

    /**
     * Constructs {@code ChestUIView}.
     *
     * @param ui the ui
     */
    public ChestUIView(final @NonNull ChestUI ui) {
        this.ui = ui;

        @NonNull ChestPane pane = new ChestPane(ui.length(), ui.height());

        for (final @NonNull Transformation<GridPane> transformation : ui.transformations()) {
            pane = (ChestPane) transformation.transform(pane);
        }

        this.inventory = Bukkit.createInventory(this, ui.height()*9);
    }

    public void open(final @NonNull Player player) {
        System.out.println("Opening "+this.inventory.toString());
        player.openInventory(this.inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
