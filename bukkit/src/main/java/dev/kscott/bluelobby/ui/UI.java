package dev.kscott.bluelobby.ui;

import dev.kscott.bluelobby.ui.pane.Pane;
import dev.kscott.bluelobby.ui.paper.ChestUI;
import dev.kscott.bluelobby.ui.transformation.Transformation;
import dev.kscott.bluelobby.ui.view.UIView;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a user interface.
 */
public interface UI<T extends Pane> {

    /**
     * Returns a ChestUI.
     *
     * @param rows the ui's rows
     * @return the ChestUI
     */
    static @NonNull ChestUI chest(final int rows) {
        return new ChestUI(rows);
    }

    /**
     * Adds a transformation to this UI.
     *
     * @param transformation the transformation
     * @return this UI
     */
    @NonNull UI<T> transform(final @NonNull Transformation<T> transformation);

    /**
     * Opens the UI for the player.
     *
     * @param player the player
     * @return the ui view
     */
    @NonNull UIView open(final @NonNull Player player);

}
