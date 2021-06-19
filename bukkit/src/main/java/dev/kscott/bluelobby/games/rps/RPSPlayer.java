package dev.kscott.bluelobby.games.rps;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Represents a rock paper scissors player.
 */
public class RPSPlayer {

    /**
     * The Minecraft UUID of this player.
     */
    private final @NonNull UUID uuid;

    /**
     * Constructs {@code RPSPlayer}.
     *
     * @param uuid the uuid of the player
     */
    public RPSPlayer(final @NonNull UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Returns the UUID.
     * @return the UUID of the player
     */
    public @NonNull UUID uuid() {
        return this.uuid;
    }

}
