package dev.kscott.bluelobby.games.rps;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents an active RPS game.
 */
public class RPSGame {

    /**
     * The plugin.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The player who started this game.
     */
    private final @NonNull Player challenger;

    /**
     * The challenger's opponent.
     */
    private final @NonNull Player opponent;

    /**
     * The menu provider.
     */
    private final @NonNull RPSGameMenuProvider menuProvider;

    /**
     * The game tick runnable.
     */
    private @Nullable BukkitRunnable tickRunnable;

    /**
     * The current state of the game.
     * See: {@link RPSGame.State}.
     */
    private @NonNull State state;

    /**
     * Constructs RPSGame.
     *
     * @param plugin     the plugin
     * @param challenger the challenger
     * @param opponent   the opponent
     */
    public RPSGame(
            final @NonNull JavaPlugin plugin,
            final @NonNull Player challenger,
            final @NonNull Player opponent
    ) {
        this.plugin = plugin;
        this.challenger = challenger;
        this.opponent = opponent;
        this.state = State.WAITING_FOR_OPPONENT;
        this.menuProvider = new RPSGameMenuProvider(this);
    }

    /**
     * Starts the game.
     */
    public void start() {
        this.tickRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                tick();
            }
        };

        this.tickRunnable.runTaskTimer(this.plugin, 0, 1);
    }

    public void tick() {

    }

    /**
     * Returns the challenger.
     *
     * @return the challenger
     */
    public @NonNull Player challenger() {
        return this.challenger;
    }

    /**
     * Returns the opponent.
     *
     * @return the opponent
     */
    public @NonNull Player opponent() {
        return this.opponent;
    }

    /**
     * Returns the game state.
     *
     * @return the game state
     */
    public @NonNull State state() {
        return this.state;
    }

    /**
     * Represents the state of the game.
     */
    public enum State {
        /**
         * When the game is waiting for the opponent to accept the invitation.
         */
        WAITING_FOR_OPPONENT,

        /**
         * When the game is waiting for both players to choose their option.
         */
        WAITING_FOR_CHOICES,

        /**
         * When the game is displaying the "3... 2... 1..." countdown.
         */
        RUNNING_COUNTDOWN,

        /**
         *
         */
        GAME_OVER
    }

}
