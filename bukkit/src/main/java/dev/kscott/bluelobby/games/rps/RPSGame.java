package dev.kscott.bluelobby.games.rps;

import dev.kscott.bluelobby.menu.MenuService;
import dev.kscott.bluelobby.menu.rps.RPSMenu;
import dev.kscott.bluelobby.utils.Constants;
import org.incendo.interfaces.paper.view.ChestView;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an active RPS game.
 */
public class RPSGame {

    /**
     * Minecraft's target TPS.
     */
    private static final int TICKS_PER_SECOND = 20;

    /**
     * The plugin.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The menu service.
     */
    private final @NonNull MenuService menuService;

    /**
     * The RPS service.
     */
    private final @NonNull RPSService rpsService;

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
     * Stores the latest opened view of a player.
     */
    private final @NonNull Map<RPSPlayer.Type, ChestView> viewMap;

    /**
     * A map that holds the players' choices.
     */
    private final @NonNull Map<RPSPlayer.Type, Choice> playerChoiceMap;
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
     * Counts how many ticks have elapsed. Set to 0 every 20 increments.
     */
    private int tickCounter;

    /**
     * The game countdown.
     */
    private int gameCountdown;

    /**
     * Constructs RPSGame.
     *
     * @param plugin      the plugin
     * @param menuService the menu service
     * @param rpsService  the rps service
     * @param challenger  the challenger
     * @param opponent    the opponent
     */
    public RPSGame(
            final @NonNull JavaPlugin plugin,
            final @NonNull MenuService menuService,
            final @NonNull RPSService rpsService,
            final @NonNull Player challenger,
            final @NonNull Player opponent
    ) {
        this.plugin = plugin;
        this.state = State.INITIALIZE;
        this.menuService = menuService;
        this.rpsService = rpsService;

        this.playerChoiceMap = new HashMap<>();
        this.viewMap = new HashMap<>();

        this.menu = new RPSMenu(this, this.menuService);
        this.challenger = new RPSPlayer(challenger, RPSPlayer.Type.CHALLENGER, this);
        this.opponent = new RPSPlayer(opponent, RPSPlayer.Type.OPPONENT, this);
        this.tickCounter = 0;
        this.gameCountdown = 0;
    }

    /**
     * Starts the game.
     */
    public void start() {
        this.tickRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                // If this is the 20th tick, reset back to 0.
                if (tickCounter >= 20) {
                    tickCounter = 0;
                }

                tick();

                System.out.println(state);

                updateMenus();

                // Increment the tick counter.
                tickCounter++;
            }
        };

        this.tickRunnable.runTaskTimer(this.plugin, 0, 1);
    }

    /**
     * Runs the game tick.
     */
    public void tick() {
        if (this.state == State.INITIALIZE) {
            this.state = State.WAITING_FOR_CHOICES;
            return;
        }

        if (this.state == State.WAITING_FOR_CHOICES) {
            if (chosen(RPSPlayer.Type.OPPONENT) && chosen(RPSPlayer.Type.CHALLENGER)) {
                this.state = State.RUNNING_COUNTDOWN;
                this.gameCountdown = 3;
                this.tickCounter = 0;
            }

            return;
        }

        if (this.state == State.RUNNING_COUNTDOWN) {
            if (tickCounter == 0) {
                gameCountdown = gameCountdown - 1;
            }

            if (gameCountdown <= 0) {
                this.state = State.GAME_OVER;
            }

            return;
        }

        if (this.state == State.GAME_OVER) {
            this.tickRunnable.cancel();
            rpsService.end(this);

            return;
        }
    }

    public void openMenu(final RPSPlayer.@NonNull Type type) {
        this.viewMap.put(type, this.menu.open(type == RPSPlayer.Type.OPPONENT ? opponent : challenger));
    }

    public void updateMenus() {
        final @Nullable ChestView opponentView = this.viewMap.get(RPSPlayer.Type.OPPONENT);
        final @Nullable ChestView challengerView = this.viewMap.get(RPSPlayer.Type.CHALLENGER);

        if (opponentView != null && opponentView.viewing()) {
            this.openMenu(RPSPlayer.Type.OPPONENT);
        }

        if (challengerView != null && challengerView.viewing()) {
            this.openMenu(RPSPlayer.Type.CHALLENGER);
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
     * Returns the current countdown.
     *
     * @return the countdown
     */
    public int countdown() {
        return this.gameCountdown;
    }

    /**
     * Sets the choice of the player.
     *
     * @param type   the type
     * @param choice the choice
     * @throws IllegalArgumentException if the player has already chosen
     */
    public void choice(final RPSPlayer.@NonNull Type type, final RPSGame.@NonNull Choice choice) throws IllegalArgumentException {
        if (chosen(type)) {
            throw new IllegalArgumentException(type.name() + " has already chosen.");
        }
        this.playerChoiceMap.put(type, choice);
    }

    /**
     * Returns the choice of the player. Will be null if the player hasn't chosen.
     *
     * @param player the player
     * @return the choice
     * @see RPSGame#chosen(RPSPlayer)
     */
    public @Nullable Choice choice(final @NonNull RPSPlayer player) {
        return this.playerChoiceMap.get(player.type());
    }

    /**
     * Returns true if the player has chosen, false if not
     *
     * @param player the player
     * @return true if the player has chosen, false if not
     */
    public boolean chosen(final @NonNull RPSPlayer player) {
        return chosen(player.type());
    }

    /**
     * Returns true if the player has chosen, false if not
     *
     * @param type the type
     * @return true if the player has chosen, false if not
     */
    public boolean chosen(final RPSPlayer.@NonNull Type type) {
        return this.playerChoiceMap.containsKey(type);
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

    /**
     * Represents a choice the player could make.
     */
    public enum Choice {
        /**
         * The choice of rock.
         */
        ROCK(Component.text("Rock").color(Constants.Chat.COLOUR_LIGHT_GRAY), Material.STONE),

        /**
         * The choice of paper.
         */
        PAPER(Component.text("Rock").color(NamedTextColor.WHITE), Material.PAPER),

        /**
         * The choice of scissors.
         */
        SCISSORS(Component.text("Scissors").color(Constants.Chat.COLOUR_LIGHT_GRAY), Material.SHEARS);

        /**
         * The title.
         */
        private final @NonNull Component title;

        /**
         * The choice's icon.
         */
        private @NonNull Material icon;

        /**
         * Constructs {@code Choice}.
         *
         * @param title the title
         * @param icon  the icon
         */
        Choice(final @NonNull Component title,
               final @NonNull Material icon) {
            this.title = title;
            this.icon = icon;
        }

        /**
         * Returns the icon.
         *
         * @return the icon
         */
        public @NonNull Material icon() {
            return this.icon;
        }

        /**
         * Returns the title.
         *
         * @return the title
         */
        public @NonNull Component title() {
            return this.title;
        }
    }

}
