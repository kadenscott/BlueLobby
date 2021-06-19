package dev.kscott.bluelobby.ui.api.frame;

import dev.kscott.bluelobby.ui.api.transformation.Transformation;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public interface Transformable {

    /**
     * Returns an ordered list of transformations.
     * <p>
     * The first transformation is applied first, and the last transformation is applied last.
     *
     * @return transformations list
     */
    @NonNull List<Transformation> transformations();

    /**
     * Adds a transformation to this UI
     */
    void transformation();

}
