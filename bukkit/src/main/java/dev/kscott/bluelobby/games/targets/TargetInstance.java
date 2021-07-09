package dev.kscott.bluelobby.games.targets;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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

        for (final @NonNull Player player : Bukkit.getOnlinePlayers()) {
            player.hidePlayer(game.plugin(), (Player) this.npc.getEntity());
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
        this.npc.despawn();
        this.game.registry().deregister(this.npc);
    }

    public @NonNull Location location() {
        return this.location.clone();
    }

}
