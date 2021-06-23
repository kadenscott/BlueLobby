package dev.kscott.bluelobby.ui.paper;

import dev.kscott.bluelobby.ui.UI;
import dev.kscott.bluelobby.ui.pane.GridPane;
import dev.kscott.bluelobby.ui.transformation.Transformation;
import dev.kscott.bluelobby.ui.view.ChestUIView;
import dev.kscott.bluelobby.ui.view.UIView;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ChestUI implements UI<GridPane> {

    /**
     * Transformations list.
     */
    private final @NonNull List<Transformation<GridPane>> transformations;

    /**
     * The length of the UI.
     */
    private final int length;

    /**
     * The height of the UI.
     */
    private final int height;

    /**
     * The title.
     */
    private @NonNull Component title;

    /**
     * Constructs {@code ChestUI}.
     *
     * @param rows the amount of rows for the ui
     */
    public ChestUI(
            final int rows
    ) {
        this.length = 9;
        this.height = rows;
        this.title = Component.empty();
        this.transformations = new ArrayList<>();
    }

    /**
     * Sets the title of the ChestUI.
     *
     * @param title the title
     * @return the UI
     */
    public @NonNull ChestUI title(@NonNull ComponentLike title) {
        this.title = title.asComponent();
        return this;
    }

    /**
     * Returns the title of the ChestUI.
     *
     * @return the title
     */
    public @NonNull Component title() {
        return this.title;
    }

    /**
     * Appends a transformation to the transformation list.
     *
     * @param transformation the transformation
     * @return the UI
     */
    @Override
    public @NonNull ChestUI transform(@NonNull Transformation<GridPane> transformation) {
        this.transformations.add(transformation);
        return this;
    }

    public @NonNull UIView open(@NonNull Player player) {
        final @NonNull ChestUIView view = new ChestUIView(this);

        view.open(player);

        return view;
    }

    public int length() {
        return this.length;
    }

    public int height() {
        return this.height;
    }

    public @NonNull List<Transformation<GridPane>> transformations() {
        return this.transformations;
    }
}
