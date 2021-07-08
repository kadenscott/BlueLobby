package dev.kscott.bluelobby.games.targets;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

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
        this.npc.data().set("target", true);
        this.npc.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    public @NonNull UUID uuid() {
        return this.npc.getUniqueId();
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
        this.npc.despawn();
        this.game.registry().deregister(this.npc);
    }

}
