package dev.kscott.bluelobby.menu;

import dev.kscott.interfaces.core.Interface;
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

}
