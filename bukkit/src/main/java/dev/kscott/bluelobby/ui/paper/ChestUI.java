package dev.kscott.bluelobby.ui.paper;

import dev.kscott.bluelobby.ui.api.UI;
import dev.kscott.bluelobby.ui.api.frame.Frame;
import dev.kscott.bluelobby.ui.api.transformation.Transformation;
import dev.kscott.bluelobby.ui.paper.frame.ChestFrame;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A chest UI.
 */
public class ChestUI implements UI {

    /**
     * The UI's frame.
     */
    private final @NonNull Frame frame;

    /**
     * The list of transformations.
     */
    private final @NonNull List<Transformation> transformations;

    /**
     * Constructs the Chest UI.
     */
    public ChestUI() {
        this.transformations = new ArrayList<>();
        this.frame = new ChestFrame();
    }

    /**
     * Appends a transformation to the frame.
     */
    public void transformation(final @NonNull Transformation transformation) {

    }

    @Override
    public void open(Player player) {

    }

    public @NonNull List<Transformation> transformations() {
        return List.copyOf(this.transformations);
    }
}
