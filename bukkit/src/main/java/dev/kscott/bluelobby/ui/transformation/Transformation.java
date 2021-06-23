package dev.kscott.bluelobby.ui.transformation;

import dev.kscott.bluelobby.ui.pane.GridPane;
import dev.kscott.bluelobby.ui.pane.Pane;
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
