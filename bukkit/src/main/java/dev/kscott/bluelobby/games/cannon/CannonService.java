package dev.kscott.bluelobby.games.cannon;

import com.destroystokyo.paper.ParticleBuilder;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.sk89q.worldedit.math.MathUtils;
import io.netty.util.internal.MathUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.List;

public class CannonService {

    private final @NonNull Location location;
    private final @NonNull JavaPlugin plugin;

    private int cannonThreshold;

    @Inject
    public CannonService(final @NonNull JavaPlugin plugin,
                         final @NonNull @Named("lobbyWorld") World world) {
        this.plugin = plugin;
        this.location = new Location(world, -89, 198, 21);
        this.cannonThreshold = 0;

        new BukkitRunnable() {
            @Override
            public void run() {
                final Collection<LivingEntity> nearbyEntites = location.getNearbyLivingEntities(1.7);
                if (nearbyEntites.isEmpty()) {
                    if (cannonThreshold > 0) {
                        cannonThreshold = cannonThreshold - 1;
                        return;
                    }
                } else {
                    cannonThreshold++;
                }

                if (cannonThreshold > 10) {
                    cannonThreshold = 10;
                }

                if (cannonThreshold == 10) {
                    new ParticleBuilder(Particle.REDSTONE)
                            .color(0, 0, 0)
                            .offset(1, 1.2, 1)
                            .location(location)
                            .count(50)
                            .allPlayers()
                            .spawn();

                    location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);

                    nearbyEntites.forEach(entity -> {
                        entity.setVelocity(entity.getLocation().getDirection().multiply(5).add(new Vector(0, 1, 0)));
                    });

                    cannonThreshold = 0;
                } else if (cannonThreshold > 0) {
                    int colorValue = (int) transform(0, 10, 0, 150, cannonThreshold);

                    new ParticleBuilder(Particle.REDSTONE)
                            .color(colorValue, colorValue, colorValue)
                            .color(colorValue, colorValue, colorValue)
                            .offset(1, 1.2, 1)
                            .location(location)
                            .count(15)
                            .allPlayers()
                            .spawn();

                    location.getWorld().playSound(location, Sound.BLOCK_NOTE_BLOCK_CHIME, 1, (float) transform(0, 10, 0, 1, cannonThreshold));
                }
            }
        }.runTaskTimer(this.plugin, 0, 5);
    }

    private double transform(int min1, int max1, int min2, int max2, double num) {
        return (num-min1)/(max1-min1) * (max2-min2) + max2;
    }

}
