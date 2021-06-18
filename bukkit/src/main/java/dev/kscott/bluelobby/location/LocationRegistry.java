package dev.kscott.bluelobby.location;

import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Holds locations for various parts of the plugin.
 */
public class LocationRegistry {

    /**
     * The lobby world.
     */
    private final @NonNull World lobbyWorld;

    /**
     * Constructs LocationRegistry.
     *
     * @param lobbyWorld the lobby world
     */
    @Inject
    public LocationRegistry(
            final @NonNull @Named("lobbyWorld") World lobbyWorld
    ) {
        this.lobbyWorld = lobbyWorld;
    }

}
