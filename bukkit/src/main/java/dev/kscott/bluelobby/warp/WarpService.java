package dev.kscott.bluelobby.warp;

import dev.kscott.bluelobby.location.LocationRegistry;
import dev.kscott.bluelobby.location.ServerLocation;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.value.qual.IntRangeFromGTENegativeOne;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class providing methods for interacting with warps.
 */
@Singleton
public class WarpService {

    private final @NonNull Map<String, ServerLocation> warps;

    private final @NonNull LocationRegistry locationRegistry;

    /**
     * Constructs {@code WarpService}.
     *
     * @param locationRegistry the location registry
     */
    @Inject
    public WarpService(
            final @NonNull LocationRegistry locationRegistry
    ) {
        this.locationRegistry = locationRegistry;

        this.warps = Map.of(
                "spawn",
                new ServerLocation(
                        "Spawn",
                        Material.GRASS_BLOCK,
                        this.locationRegistry.spawn().getX(),
                        this.locationRegistry.spawn().getY(),
                        this.locationRegistry.spawn().getZ(),
                        this.locationRegistry.spawn().getYaw(),
                        this.locationRegistry.spawn().getPitch(),
                        this.locationRegistry.pond().getWorld().getName()
                ),
                "pond",
                new ServerLocation(
                        "The Pond",
                        Material.LARGE_FERN,
                        this.locationRegistry.pond().getX(),
                        this.locationRegistry.pond().getY(),
                        this.locationRegistry.pond().getZ(),
                        this.locationRegistry.pond().getYaw(),
                        this.locationRegistry.pond().getPitch(),
                        this.locationRegistry.pond().getWorld().getName()
                )
        );
    }

    public boolean warp(final @NonNull Player player, final @NonNull ServerLocation warp) {

        player.sendMessage(Component.text()
                .append(Component.text("Warping you to "))
                .append(Component.text(warp.name()).color(Constants.Chat.COLOUR_LIGHT_BLUE))
                .append(Component.text("."))
                .style(Constants.Chat.STYLE_DEFAULT));

        player.teleportAsync(warp.location());

        return true;
    }

    /**
     * The list of warps.
     *
     * @return the list of warps
     */
    public @NonNull List<ServerLocation> warps() {
        return new ArrayList<>(this.warps.values());
    }

}
