package dev.kscott.bluelobby.ui.api.transformation;

import dev.kscott.bluelobby.ui.api.frame.Frame;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface Transformation {

    @NonNull Frame transform(@NonNull Frame frame);

}
