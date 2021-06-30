package dev.kscott.bluelobby.warp;

import com.destroystokyo.paper.ParticleBuilder;
import dev.kscott.bluelobby.location.LocationRegistry;
import dev.kscott.bluelobby.location.ServerLocation;
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
import java.util.Map;

/**
 * A class providing methods for interacting with warps.
 */
@Singleton
public class WarpService {

    private final @NonNull List<ServerLocation> warps;

    private final @NonNull LocationRegistry locationRegistry;

    private final @NonNull JavaPlugin plugin;

    /**
     * Constructs {@code WarpService}.
     *
     * @param locationRegistry the location registry
     */
    @Inject
    public WarpService(
            final @NonNull LocationRegistry locationRegistry,
            final @NonNull JavaPlugin plugin
    ) {
        this.locationRegistry = locationRegistry;

        this.warps = List.of(
                new ServerLocation(
                        "Spawn",
                        this.locationRegistry.pond().getWorld().getName(),
                        List.of(""),
                        Material.GRASS_BLOCK,
                        this.locationRegistry.spawn().getX(),
                        this.locationRegistry.spawn().getY(),
                        this.locationRegistry.spawn().getZ(),
                        this.locationRegistry.spawn().getYaw(),
                        this.locationRegistry.spawn().getPitch()
                ),
                new ServerLocation(
                        "The Pond",
                        this.locationRegistry.pond().getWorld().getName(),
                        List.of("A beautiful pond full of fish."),
                        Material.LARGE_FERN,
                        this.locationRegistry.pond().getX(),
                        this.locationRegistry.pond().getY(),
                        this.locationRegistry.pond().getZ(),
                        this.locationRegistry.pond().getYaw(),
                        this.locationRegistry.pond().getPitch()
                ),
                new ServerLocation(
                        "The Crystal",
                        this.locationRegistry.spawn().getWorld().getName(),
                        List.of("A beautiful pond full of fish."),
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
