package dev.kscott.bluelobby.interfaces.paper;

import dev.kscott.bluelobby.interfaces.element.Element;
import dev.kscott.bluelobby.interfaces.pane.GridPane;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A pane that holds a 2d grid of elements.
 */
public class ChestPane implements GridPane {

    /**
     * Minecraft's chest width.
     */
    public static final int MINECRAFT_CHEST_WIDTH = 9;

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
        return this.length;
    }

    /**
     * Returns the height of the pane.
     *
     * @return the height
     */
    @Override
    public int height() {
        return this.height;
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

    @Override
    public @NonNull Element[][] elements() {
        return this.elements;
    }
}
