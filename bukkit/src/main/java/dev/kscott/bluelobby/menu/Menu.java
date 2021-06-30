package dev.kscott.bluelobby.menu;

import dev.kscott.interfaces.core.Interface;
import dev.kscott.interfaces.core.view.InterfaceView;
import dev.kscott.interfaces.core.view.InterfaceViewer;
import dev.kscott.interfaces.paper.PlayerViewer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * An interface that represents a menu.
 */
public interface Menu<T extends Interface> {

    /**
     * Returns this menu's interface.
     *
     * @return the interface
     */
    @NonNull T get();

    /**
     * Opens the menu.
     *
     * @param player the player
     */
    default void open(final @NonNull Player player) {
        get().open(PlayerViewer.of(player));
    }

}
