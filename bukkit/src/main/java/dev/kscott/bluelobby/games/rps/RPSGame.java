package dev.kscott.bluelobby.games.rps;

import dev.kscott.bluelobby.menu.rps.RPSMenu;
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
    private final @NonNull RPSPlayer challenger;

    /**
     * The challenger's opponent.
     */
    private final @NonNull RPSPlayer opponent;

    /**
     * The game menu.
     */
    private final @NonNull RPSMenu menu;

    /**
     * The game tick runnable.
     */
    private @Nullable BukkitRunnable tickRunnable;

    /**
     * The current state of the game.
     *
     * @see RPSGame.State
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
        this.state = State.INITIALIZE;
        this.menu = new RPSMenu(this);
        this.challenger = new RPSPlayer(challenger, RPSPlayer.Type.CHALLENGER, this);
        this.opponent = new RPSPlayer(opponent, RPSPlayer.Type.OPPONENT, this);
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

    /**
     * Runs the game tick.
     */
    public void tick() {
        if (this.state == State.INITIALIZE) {
            this.state = State.WAITING_FOR_OPPONENT;
            return;
        }

        if (this.state == State.WAITING_FOR_OPPONENT) {
            return;
        }

        if (this.state == State.GAME_OVER) {
            this.tickRunnable.cancel();
            return;
        }
    }

    /**
     * Returns the challenger.
     *
     * @return the challenger
     */
    public @NonNull RPSPlayer challenger() {
        return this.challenger;
    }

    /**
     * Returns the opponent.
     *
     * @return the opponent
     */
    public @NonNull RPSPlayer opponent() {
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
     * Returns true if the provided player is in this game, false if not.
     *
     * @param player the player
     * @return true if the provided player is in this game, false if not.
     */
    public boolean hasPlayer(final @NonNull Player player) {
        return challenger.player() == player || opponent.player() == player;
    }

    /**
     * Represents the state of the game.
     */
    public enum State {
        /**
         * The state of the game during the game's first tick.
         */
        INITIALIZE,

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
         * When the game has ended.
         */
        GAME_OVER
    }

}
