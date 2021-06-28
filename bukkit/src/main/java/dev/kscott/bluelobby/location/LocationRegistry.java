package dev.kscott.bluelobby.location;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

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
     * The pond location.
     */
    private final @NonNull Location pond;

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
        this.pond = new Location(lobbyWorld, -107.5, 184, 164.5, -50, 0);
    }

    /**
     * Returns the spawn location.
     *
     * @return the spawn location
     */
    public @NonNull Location spawn() {
        return spawn;
    }

    /**
     * Returns the pond location.
     *
     * @return the pond location
     */
    public @NonNull Location pond() {
        return pond;
    }

    /**
     * Returns the list of warps.
     *
     * @return the list of warps
     */
    public @NonNull List<ServerLocation> warps() {
        return this.warps();
    }

}
