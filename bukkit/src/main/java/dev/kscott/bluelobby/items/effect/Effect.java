package dev.kscott.bluelobby.items.effect;

import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A class that represents an item's effect.
 */
public interface Effect {

    /**
     * Returns the formatted pretty text of this effect.
     *
     * @return the formatted text
     */
    @NonNull Component text();

}
