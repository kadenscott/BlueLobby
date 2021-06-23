package dev.kscott.bluelobby.interfaces.pane;

import dev.kscott.bluelobby.interfaces.element.Element;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a pane that contains a grid of elements
 */
public interface GridPane extends Pane {

    /**
     * Returns this pane's length.
     *
     * @return the pane length
     */
    int length();

    /**
     * Return this pane's height.
     *
     * @return the pane height
     */
    int height();

    /**
     * Sets an element at the x/y position.
     *
     * @param element element
     * @param x the x position
     * @param y the y position
     */
    void element(final @NonNull Element element, final int x, final int y);

    /**
     * Returns the element at the x/y position.
     *
     * @param x the x position
     * @param y the y position
     *
     * @return the element
     */
    @NonNull Element element(final int x, final int y);

    /**
     * Returns the 2d element array. An "empty" element in the array is the value of {@link Element#empty()}.
     *
     * @return the 2d element array
     */
    @NonNull Element[][] elements();
}
