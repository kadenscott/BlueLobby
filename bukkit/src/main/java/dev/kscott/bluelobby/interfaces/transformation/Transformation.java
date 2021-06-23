package dev.kscott.bluelobby.interfaces.transformation;

import dev.kscott.bluelobby.interfaces.element.ItemStackElement;
import dev.kscott.bluelobby.interfaces.pane.GridPane;
import dev.kscott.bluelobby.interfaces.pane.Pane;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;

/**
 * Applies a provided transformation function to a pane.
 *
 * @param <T> the pane type
 */
public class Transformation<T extends Pane> {

    /**
     * Returns a new {@link GridPane} transformation with the given.
     *
     * @param function the transformation function
     * @return the transformation
     */
    public static Transformation<GridPane> grid(final @NonNull Function<GridPane, GridPane> function) {
        return new Transformation<>(function);
    }

    public static Transformation<GridPane> gridItem(final @NonNull ItemStackElement element, final int x, final int y) {
        return new Transformation<>(pane -> {
            pane.element(element, x, y);
            return pane;
        });
    }

    /**
     * Returns a new {@link GridPane} transformations that fills the pane with one element.
     *
     * @param element the element
     * @return the transformation
     */
    public static Transformation<GridPane> gridFill(final @NonNull ItemStackElement element) {
        return new Transformation<>(pane -> {
            for (int i = 0; i < pane.length(); i++) {
                for (int j = 0; j < pane.height(); j++) {
                    pane.element(element, i, j);
                }
            }
            return pane;
        });
    }

    /**
     * The transformation function.
     */
    private final @NonNull Function<T, T> function;

    /**
     * Constructs {@code Transformation}.
     *
     * @param function the transformation function.
     */
    private Transformation(final @NonNull Function<T, T> function) {
        this.function = function;
    }

    /**
     * Applies a transformation to a pane and returns the transformed pane.
     *
     * @param pane the pane
     * @return the transformed pane
     */
    public T transform(final @NonNull T pane) {
        return this.function.apply(pane);
    }

}
