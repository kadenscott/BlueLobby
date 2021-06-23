package dev.kscott.bluelobby.interfaces;

import dev.kscott.bluelobby.interfaces.pane.Pane;
import dev.kscott.bluelobby.interfaces.paper.ChestInterface;
import dev.kscott.bluelobby.interfaces.transformation.Transform;
import dev.kscott.bluelobby.interfaces.view.View;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a user interface.
 */
public interface Interface<T extends Pane> {

    /**
     * Returns a ChestUI.
     *
     * @param rows the ui's rows
     * @return the ChestUI
     */
    static @NonNull ChestInterface chest(final int rows) {
        return new ChestInterface(rows);
    }

    /**
     * Adds a transformation to this UI.
     *
     * @param transformation the transformation
     * @return this UI
     */
    @NonNull Interface<T> transform(final @NonNull Transform<T> transformation);

    /**
     * Opens the UI for the player.
     *
     * @param player the player
     * @return the ui view
     */
    @NonNull View open(final @NonNull Player player);

}
