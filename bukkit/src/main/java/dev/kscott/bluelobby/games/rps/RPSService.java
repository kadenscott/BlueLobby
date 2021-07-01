package dev.kscott.bluelobby.games.rps;


import com.google.inject.Singleton;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all running rock paper scissors games.
 */
@Singleton
public class RPSService {

    /**
     * The list of active games.
     */
    private final @NonNull List<RPSGame> games;

    /**
     * The plugin.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * Constructs {@code RPSService}.
     */
    public RPSService(final @NonNull JavaPlugin plugin) {
        this.games = new ArrayList<>();
        this.plugin = plugin;
    }

    /**
     * Creates a new rock-paper-scissors game.
     *
     * @param challenger the challenger
     * @param opponent the opponent
     * @return the game
     * @throws IllegalArgumentException if a player is already in a game
     */
    public @NonNull RPSGame create(final @NonNull Player challenger, final @NonNull Player opponent) {
        if (playing(challenger)) {
            throw new IllegalArgumentException("Player '"+challenger.getName()+"' (challenger) is already playing a game.");
        }

        if (playing(opponent)) {
            throw new IllegalArgumentException("Player '"+opponent.getName()+"' (opponent) is already playing a game.");
        }

        final @NonNull RPSGame game = new RPSGame(this.plugin, challenger, opponent);

        return game;
    }

    /**
     * Returns true if the provided player is playing a game, false if not.
     *
     * @param player the player
     * @return true if player is playing game, false if not
     */
    public boolean playing(final @NonNull Player player) {
        return game(player) != null;
    }

    /**
     * Returns the game, if any, that the provided player is in.
     *
     * @param player the player
     * @return the game
     */
    public @Nullable RPSGame game(final @NonNull Player player) {
        for (final @NonNull RPSGame game : games) {
            if (game.hasPlayer(player)) {
                return game;
            }
        }

        return null;
    }

}
