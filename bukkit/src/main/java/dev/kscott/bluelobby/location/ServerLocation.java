package dev.kscott.bluelobby.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

/**
 * Represents a location on the server.
 */
public class ServerLocation {

    /**
     * The world name.
     */
    private final @NonNull String worldName;

    /**
     * The x coordinate.
     */
    private final double x;

    /**
     * The y coordinate
     */
    private final double y;

    /**
     * The z coordinate.
     */
    private final double z;

    /**
     * The yaw.
     */
    private final double yaw;

    /**
     * The pitch.
     */
    private final double pitch;

    /**
     * Constructs {@code ServerLocation}.
     *
     * @param x         the x coordinate
     * @param y         the y coordinate
     * @param z         the z coordinate
     * @param yaw       the yaw
     * @param pitch     the pitch
     * @param worldName the name of the world this location is in
     */
    public ServerLocation(
            final double x,
            final double y,
            final double z,
            final double yaw,
            final double pitch,
            final @NonNull String worldName
    ) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.worldName = worldName;
    }

    /**
     * Constructs {@code ServerLocation}.
     *
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @param z     the z coordinate
     * @param yaw   the yaw
     * @param pitch the pitch
     */
    public ServerLocation(
            final double x,
            final double y,
            final double z,
            final double yaw,
            final double pitch
    ) {
        this(x, y, z, yaw, pitch, "world");
    }

    /**
     * Constructs {@code ServerLocation}.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public ServerLocation(
            final double x,
            final double y,
            final double z
    ) {
        this(x, y, z, 0, 0, "world");
    }

    /**
     * Constructs {@code ServerLocation}.
     *
     * @param x         the x coordinate
     * @param y         the y coordinate
     * @param z         the z coordinate
     * @param worldName the world name
     */
    public ServerLocation(
            final double x,
            final double y,
            final double z,
            final @NonNull String worldName
    ) {
        this(x, y, z, 0, 0, worldName);
    }

    /**
     * Returns the x coordinate.
     *
     * @return the x coordiante
     */
    public double x() {
        return this.x;
    }

    /**
     * Returns the y coordinate.
     *
     * @return the y coordiante
     */
    public double y() {
        return this.y;
    }

    /**
     * Returns the z coordinate.
     *
     * @return the z coordiante
     */
    public double z() {
        return this.z;
    }

    /**
     * Returns the yaw.
     *
     * @return the yaw
     */
    public double yaw() {
        return this.yaw;
    }

    /**
     * Returns the pitch.
     *
     * @return the pitch
     */
    public double pitch() {
        return this.pitch;
    }

    /**
     * Returns the world name.
     *
     * @return the world name
     */
    public @NonNull String worldName() {
        return this.worldName;
    }

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
