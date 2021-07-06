package dev.kscott.bluelobby.area;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Area {

    /**
     * The id of this area (and the corresponding WorldGuard region).
     */
    private final @NonNull String id;

    /**
     * Constructs {@code Area}
     *
     * @param id the id
     */
    public Area(final @NonNull String id) {
        this.id = id;
    }

    public @NonNull String id() {
        return this.id;
    }

}
