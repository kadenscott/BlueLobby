package dev.kscott.bluelobby.games.rps;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides the menus for an RPSGame.
 */
public class RPSGameMenuProvider {

    /**
     * The RPS game.
     */
    private final @NonNull RPSGame game;

    /**
     * Constructs RPSGameMenuProvider.
     *
     * @param game the RPS game
     */
    public RPSGameMenuProvider(
            final @NonNull RPSGame game
    ) {
        this.game = game;
    }

    public void menu() {
        final RPSGame.State state = this.game.state();
    }

}
