package dev.kscott.bluelobby.location;

import org.bukkit.Location;
import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Holds locations for various parts of the plugin.
 */
public class LocationRegistry {

    /**
     * The lobby world.
     */
    private final @NonNull World lobbyWorld;

    /**
     * The spawn location.
     */
    private final @NonNull Location spawn;

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

        this.spawn = new Location(lobbyWorld, 0.5, 66, 0.5, 180, 0);
    }

    /**
     * Returns the spawn location.
     *
     * @return the spawn location
     */
    public @NonNull Location spawn() {
        return spawn;
    }
}
