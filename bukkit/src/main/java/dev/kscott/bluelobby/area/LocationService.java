package dev.kscott.bluelobby.area;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Holds locations for various parts of the plugin.
 */
@Singleton
public class LocationService {

    private final @NonNull Location spawn;
    private final @NonNull Location pond;
    private final @NonNull Area targets;
    private final @NonNull RegionContainer regionContainer;
    private final @NonNull World lobbyWorld;
    private final @NonNull Map<String, Area> areaMap;

    /**
     * Constructs LocationRegistry.
     *
     * @param lobbyWorld the lobby world
     */
    @Inject
    public LocationService(
            final @NonNull @Named("lobbyWorld") World lobbyWorld
    ) {
        this.regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();

        this.lobbyWorld = lobbyWorld;
        this.spawn = new Location(lobbyWorld, -22.5, 199, 16.5, 0, 0);
        this.pond = new Location(lobbyWorld, -107.5, 184, 164.5, -50, 0);
        this.targets = new Area("area_targets");
        this.areaMap = new HashMap<>();
    }

    public boolean inArea(final @NonNull Player player, final @NonNull String id) {
        final @NonNull RegionQuery query = this.regionContainer.createQuery();

        for (final @NonNull ProtectedRegion region : query.getApplicableRegions(BukkitAdapter.adapt(player.getLocation()))) {
            if (region.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    public @NonNull Area area(final @NonNull Player player) {
        final @NonNull RegionQuery query = this.regionContainer.createQuery();

        for (final @NonNull ProtectedRegion region : query.getApplicableRegions(BukkitAdapter.adapt(player.getLocation()))) {
            try {
                return this.area(region.getId());
            } catch (final Exception ignored) {}
        }

        throw new NullPointerException("The player was not in an area.");
    }

    public @NonNull Area area(final @NonNull String id) {
        final @Nullable Area area = this.areaMap.get(id);

        return Objects.requireNonNull(area);
    }

    /**
     * Returns the region with an area id.
     * @param id the area's id
     * @return the region
     */
    public @NonNull ProtectedRegion region(final @NonNull String id) {
        for (final @NonNull ProtectedRegion region : this.regionContainer.get(BukkitAdapter.adapt(this.lobbyWorld)).getRegions().values()) {
            if (region.getId().equals(id)) {
                return region;
            }
        }

        throw new NullPointerException("No region with the given id was found.");
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
