package dev.kscott.bluelobby.ui.paper.frame;

import dev.kscott.bluelobby.ui.api.frame.RectangleFrame;
import dev.kscott.bluelobby.ui.api.transformation.Transformation;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class ChestFrame implements RectangleFrame {

    private @NonNull Inventory inventory;

    public ChestFrame() {

    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public int width() {
        return 0;
    }

    @Override
    public @NonNull List<Transformation> transformations() {
        return null;
    }

    @Override
    public void transformation() {

    }
}
