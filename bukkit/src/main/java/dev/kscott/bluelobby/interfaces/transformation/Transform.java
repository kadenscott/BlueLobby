package dev.kscott.bluelobby.interfaces.transformation;

import dev.kscott.bluelobby.interfaces.element.ItemStackElement;
import dev.kscott.bluelobby.interfaces.pane.GridPane;
import dev.kscott.bluelobby.interfaces.pane.Pane;
import dev.kscott.bluelobby.interfaces.view.View;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.BiConsumer;

/**
 * Applies a provided transformation function to a pane.
 *
 * @param <T> the pane type
 */
public class Transform<T extends Pane> implements BiConsumer<T, View> {

    /**
     * The transformation function.
     */
    private final @NonNull BiConsumer<T, View> function;

    /**
     * Constructs {@code Transformation}.
     *
     * @param transformer the transformation function.
     */
    private Transform(final @NonNull BiConsumer<T, View> transformer) {
        this.function = transformer;
    }

    /**
     * Returns a new {@link GridPane} transformation with the provided transformer.
     *
     * @param transformer the transformer
     * @return the transformation
     */
    public static Transform<GridPane> grid(final @NonNull BiConsumer<GridPane, View> transformer) {
        return new Transform<>(transformer);
    }

    /**
     * Puts an element at the x/y position.
     *
     * @param element the element
     * @param x       x coordinate
     * @param y       y coordinate
     * @return the transformation
     */
    public static Transform<GridPane> gridItem(final @NonNull ItemStackElement element, final int x, final int y) {
        return new Transform<>((pane, view) -> {
            pane.element(element, x, y);
        });
    }

    /**
     * Returns a new {@link GridPane} transformation that fills the pane with one element.
     *
     * @param element the element
     * @return the transformation
     */
    public static Transform<GridPane> gridFill(final @NonNull ItemStackElement element) {
        return new Transform<>((pane, view) -> {
            for (int i = 0; i < pane.length(); i++) {
                for (int j = 0; j < pane.height(); j++) {
                    pane.element(element, i, j);
                }
            }
        });
    }

    /**
     * Applies the transformation to a pane.
     *
     * @param pane the pane
     */
    public void accept(final @NonNull T pane, final @NonNull View view) {
        this.function.accept(pane, view);
    }

}
