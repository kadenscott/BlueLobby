package dev.kscott.bluelobby.interfaces.paper;

import dev.kscott.bluelobby.interfaces.Interface;
import dev.kscott.bluelobby.interfaces.arguments.InterfaceArguments;
import dev.kscott.bluelobby.interfaces.pane.GridPane;
import dev.kscott.bluelobby.interfaces.transformation.Transformation;
import dev.kscott.bluelobby.interfaces.view.ChestView;
import dev.kscott.bluelobby.interfaces.view.View;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ChestInterface implements Interface<GridPane> {

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
    public ChestInterface(
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
    public @NonNull ChestInterface title(@NonNull ComponentLike title) {
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
    public @NonNull ChestInterface transform(@NonNull Transformation<GridPane> transformation) {
        this.transformations.add(transformation);
        return this;
    }

    /**
     * Opens this interface for a player and returns the {@link View}.
     *
     * @param player the player
     * @return the view
     */
    public @NonNull View open(final @NonNull Player player, final @NonNull InterfaceArguments arguments) {
        final @NonNull ChestView view = new ChestView(this, player, arguments);

        view.open(player);

        return view;
    }

    /**
     * Opens this interface for a player and returns the {@link View}.
     *
     * @param player the player
     * @return the view
     */
    public @NonNull View open(final @NonNull Player player) {
        final @NonNull ChestView view = new ChestView(this, player, InterfaceArguments.empty());

        view.open(player);

        return view;
    }

    /**
     * Returns the interface's length.
     *
     * @return the length
     */
    public int length() {
        return this.length;
    }

    /**
     * Returns the interface's height
     *
     * @return the height
     */
    public int height() {
        return this.height;
    }

    /**
     * Returns an immutable list of transformations on this interface.
     *
     * @return the list of transformations
     */
    public @NonNull List<Transformation<GridPane>> transformations() {
        return List.copyOf(this.transformations);
    }
}
