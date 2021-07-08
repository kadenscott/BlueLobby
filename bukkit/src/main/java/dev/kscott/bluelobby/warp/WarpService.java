package dev.kscott.bluelobby.warp;

import com.destroystokyo.paper.ParticleBuilder;
import dev.kscott.bluelobby.area.LocationService;
import dev.kscott.bluelobby.area.ServerLocation;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * A class providing methods for interacting with warps.
 */
@Singleton
public class WarpService {

    private final @NonNull List<ServerLocation> warps;

    private final @NonNull LocationService locationService;

    private final @NonNull JavaPlugin plugin;

    /**
     * Constructs {@code WarpService}.
     *
     * @param locationService the location registry
     */
    @Inject
    public WarpService(
            final @NonNull LocationService locationService,
            final @NonNull JavaPlugin plugin
    ) {
        this.locationService = locationService;

        this.warps = List.of(
                new ServerLocation(
                        "Spawn",
                        this.locationService.pond().getWorld().getName(),
                        List.of(),
                        Material.GRASS_BLOCK,
                        this.locationService.spawn().getX(),
                        this.locationService.spawn().getY(),
                        this.locationService.spawn().getZ(),
                        this.locationService.spawn().getYaw(),
                        this.locationService.spawn().getPitch()
                ),
                new ServerLocation(
                        "The Pond",
                        this.locationService.pond().getWorld().getName(),
                        List.of(Component.text("A beautiful pond full of fish.")),
                        Material.LARGE_FERN,
                        this.locationService.pond().getX(),
                        this.locationService.pond().getY(),
                        this.locationService.pond().getZ(),
                        this.locationService.pond().getYaw(),
                        this.locationService.pond().getPitch()
                ),
                new ServerLocation(
                        "The Crystal",
                        this.locationService.spawn().getWorld().getName(),
                        List.of(Component.text("The source of all power"), Component.text("for the island.")),
                        Material.END_CRYSTAL,
                        -22.5,
                        197,
                        79.5,
                        -180,
                        0
                )
        );

        this.plugin = plugin;
    }

    public boolean warp(final @NonNull Player player, final @NonNull ServerLocation warp) {

        player.sendMessage(Component.text()
                .append(Component.text("Warping you to "))
                .append(Component.text(warp.name()).color(Constants.Chat.COLOUR_LIGHT_BLUE))
                .append(Component.text("."))
                .style(Constants.Chat.STYLE_DEFAULT));

        new BukkitRunnable() {
            @Override
            public void run() {
                new ParticleBuilder(Particle.REDSTONE)
                        .data(new Particle.DustOptions(Color.fromRGB(225, 42, 235), 3))
                        .extra(1)
                        .offset(1.3, 1, 1.3)
                        .count(10)
                        .location(player.getLocation()
                                .clone()
                                .add(0, 1.8, 0))
                        .receivers(player)
                        .spawn();

                player.playSound(Sound.sound(
                        Key.key("entity.enderman.teleport"),
                        Sound.Source.PLAYER,
                        1,
                        1
                ));
            }
        }.runTaskLater(this.plugin, 3);

        player.teleportAsync(warp.location());

        return true;
    }

    /**
     * The list of warps.
     *
     * @return the list of warps
     */
    public @NonNull List<ServerLocation> warps() {
        return new ArrayList<>(this.warps);
    }

}
