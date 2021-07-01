package dev.kscott.bluelobby.games.rps;

import org.bukkit.entity.Player;
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
     * The player.
     */
    private final @NonNull Player player;

    /**
     * This player's type.
     */
    private final @NonNull Type type;

    /**
     * The game.
     */
    private final @NonNull RPSGame game;

    /**
     * Constructs {@code RPSPlayer}.
     *
     * @param player the player
     */
    public RPSPlayer(final @NonNull Player player,
                     final @NonNull Type type,
                     final @NonNull RPSGame game) {
        this.uuid = player.getUniqueId();
        this.player = player;
        this.type = type;
        this.game = game;
    }

    /**
     * Returns the UUID.
     *
     * @return the UUID of the player
     */
    public @NonNull UUID uuid() {
        return this.uuid;
    }

    /**
     * Returns the player.
     *
     * @return the player
     */
    public @NonNull Player player() {
        return this.player;
    }

    /**
     * Returns the game.
     *
     * @return the game
     */
    public @NonNull RPSGame game() {
        return this.game;
    }

    /**
     * An enum representing the player's type.
     */
    public enum Type {
        /**
         * The type of a player who challenged the opponent, i.e. the one who started the game/sent the first invite.
         */
        CHALLENGER,
        /**
         * The type of a player who accepted the invite and joined the game.
         */
        OPPONENT
    }

}
