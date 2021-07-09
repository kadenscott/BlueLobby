package dev.kscott.bluelobby.games.targets;

import com.destroystokyo.paper.ParticleBuilder;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.checkerframework.checker.nullness.qual.NonNull;

public class TargetInstance {

    private final long creationTimestamp;
    private final @NonNull Location location;
    private final @NonNull TargetGameService game;
    private final @NonNull NPC npc;

    /**
     * Constructs {@code TargetInstance}
     *
     * @param location the location
     * @param game     the game this target is from
     */
    public TargetInstance(
            final @NonNull Location location,
            final @NonNull TargetGameService game
    ) {
        this.creationTimestamp = System.currentTimeMillis();
        this.location = location;
        this.game = game;

        this.npc = this.game.registry().createNPC(EntityType.PLAYER, "Target");
        this.npc.data().set("target", "true");
        this.npc.data().set("game", game.uuid().toString());
        this.npc.setProtected(false);
        this.npc.spawn(location);
        ((LivingEntity) this.npc.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 99999, 1));

        for (final @NonNull Player player : Bukkit.getOnlinePlayers()) {
            if (!this.game.isPlaying(player)) {
                player.hidePlayer(game.plugin(), (Player) this.npc.getEntity());
            }
        }
    }

    public @NonNull NPC npc() {
        return this.npc;
    }

    /**
     * Returns the amount of milliseconds since this target's creation.
     *
     * @return ms since creaation
     */
    public long millisSinceCreation() {
        return System.currentTimeMillis() - this.creationTimestamp;
    }

    /**
     * Removes the target.
     */
    public void remove() {
        final @NonNull Entity targetEntity = this.npc.getEntity();

        final @NonNull Vector currentVelocity = targetEntity.getVelocity();

        targetEntity.setVelocity(currentVelocity.clone().add(new Vector(0, 3, 0)));

        new BukkitRunnable() {
            @Override
            public void run() {
                new ParticleBuilder(Particle.REDSTONE)
                        .receivers(game.players())
                        .count(20)
                        .color(Color.fromRGB(186, 242, 245))
                        .offset(0.5, 1.5, 0.5)
                        .location(targetEntity.getLocation())
                        .spawn()
                        .color(Color.fromRGB(82, 155, 168))
                        .spawn();

                game.registry().deregister(npc);
            }
        }.runTaskLater(game.plugin(), 15);
    }

    public @NonNull Location location() {
        return this.location.clone();
    }

}
