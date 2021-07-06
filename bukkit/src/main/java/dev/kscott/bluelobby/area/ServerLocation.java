package dev.kscott.bluelobby.area;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * Represents a location on the server.
 */
public record ServerLocation(
        @NonNull String name,
        @NonNull String worldName,
        @NonNull List<Component> description,
        @NonNull Material icon,
        double x,
        double y,
        double z,
        double yaw,
        double pitch
) {
    /**
     * Returns the world.
     *
     * @return the world
     */
    public @NonNull World world() {
        return Objects.requireNonNull(Bukkit.getWorld(this.worldName));
    }

    /**
     * Returns the location.
     *
     * @return the location
     */
    public @NonNull Location location() {
        return new Location(
                this.world(),
                this.x,
                this.y,
                this.z,
                (float) this.yaw,
                (float) this.pitch
        );
    }

}
