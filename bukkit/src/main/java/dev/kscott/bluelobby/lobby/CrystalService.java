package dev.kscott.bluelobby.lobby;

import com.destroystokyo.paper.ParticleBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.kscott.bluelobby.area.LocationService;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class CrystalService {

    private final @NonNull LocationService locationService;

    private final @NonNull JavaPlugin plugin;

    private final @NonNull Location crystalOrigin;

    private final @NonNull Random random;

    @Inject
    public CrystalService(final @NonNull JavaPlugin plugin,
                          final @NonNull LocationService locationService) {
        this.locationService = locationService;
        this.plugin = plugin;

        this.crystalOrigin = new Location(Bukkit.getWorld("world"), -22.5, 205, 58.5);

        this.start();
        this.random = new Random();
    }

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                int times = random.nextInt(10);
                for (int i = 0; i < times; i++) {
                    spawnEffect();
                }
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    private void spawnEffect() {
        final int xDelta = ThreadLocalRandom.current().nextInt(-15, 15);
        final int yDelta = ThreadLocalRandom.current().nextInt(-6, 6);
        final int zDelta = ThreadLocalRandom.current().nextInt(-15, 15);

        final @NonNull  Location location = new Location(this.crystalOrigin.getWorld(),
                this.crystalOrigin.getX() + xDelta,
                this.crystalOrigin.getY() + yDelta,
                this.crystalOrigin.getZ() + zDelta
        );

        new ParticleBuilder(Particle.REDSTONE)
                .data(new Particle.DustOptions(Color.fromRGB(225, 42, 235), 3))
                .extra(1)
                .offset(1.3, 1, 1.3)
                .count(random.nextInt(2))
                .location(location)
                .allPlayers()
                .spawn();
    }
}
