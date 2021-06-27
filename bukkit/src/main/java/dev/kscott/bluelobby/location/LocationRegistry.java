package dev.kscott.bluelobby.location;

import org.bukkit.Location;
import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Holds locations for various parts of the plugin.
 */
@Singleton
public class LocationRegistry {

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
        this.spawn = new Location(lobbyWorld, -22.5, 199, 16.5, 0, 0);
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
