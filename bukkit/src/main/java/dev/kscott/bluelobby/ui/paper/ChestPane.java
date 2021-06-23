package dev.kscott.bluelobby.ui.paper;

import dev.kscott.bluelobby.ui.element.Element;
import dev.kscott.bluelobby.ui.pane.GridPane;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A pane that holds a 2d grid of elements.
 */
public class ChestPane implements GridPane {

    /**
     * The length of the pane.
     */
    private final int length;

    /**
     * The height of the pane.
     */
    private final int height;

    /**
     * The element 2d array.
     */
    private final @NonNull Element[][] elements;

    /**
     * Constructs {@code GridPane}.
     *
     * @param length the length of the pane
     * @param height the height of the pane
     */
    public ChestPane(final int length, final int height) {
        this.length = length;
        this.height = height;
        this.elements = new Element[length][];

        // Initialize element grid
        for (int i = 0; i < length; i++) {
            this.elements[i] = new Element[height];

            for (int j = 0; j < height; j++) {
                this.elements[i][j] = Element.empty();
            }
        }
    }

    /**
     * Returns the length of the pane.
     *
     * @return the length
     */
    @Override
    public int length() {
        return 0;
    }

    /**
     * Returns the height of the pane.
     *
     * @return the height
     */
    @Override
    public int height() {
        return 0;
    }

    /**
     * Sets an element at the given position.
     *
     * @param element element
     * @param x       the x position
     * @param y       the y position
     */
    @Override
    public void element(final @NonNull Element element, final int x, final int y) {
        this.elements[x][y] = element;
    }

    /**
     * Returns an element at the given position.
     *
     * @param x the x position
     * @param y the y position
     * @return the element
     */
    @Override
    public @NonNull Element element(final int x, final int y) {
        return this.elements[x][y];
    }
}
