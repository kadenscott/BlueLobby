package dev.kscott.bluelobby.menu.rps;

import dev.kscott.bluelobby.games.rps.RPSGame;
import dev.kscott.bluelobby.games.rps.RPSPlayer;
import dev.kscott.bluelobby.menu.Menu;
import dev.kscott.bluelobby.utils.Constants;
import dev.kscott.interfaces.core.arguments.HashMapInterfaceArgument;
import dev.kscott.interfaces.core.arguments.InterfaceArgument;
import dev.kscott.interfaces.core.view.InterfaceView;
import dev.kscott.interfaces.paper.PlayerViewer;
import dev.kscott.interfaces.paper.element.ItemStackElement;
import dev.kscott.interfaces.paper.pane.ChestPane;
import dev.kscott.interfaces.paper.transform.PaperTransform;
import dev.kscott.interfaces.paper.type.ChestInterface;
import dev.kscott.interfaces.paper.view.ChestView;
import org.checkerframework.checker.nullness.qual.NonNull;

public class RPSMenu implements Menu<ChestInterface> {

    /**
     * The RPS game.
     */
    private final @NonNull RPSGame rpsGame;

    /**
     * Constructs {@code RPSMenu}.
     *
     * @param rpsGame the RPS game
     */
    public RPSMenu(final @NonNull RPSGame rpsGame) {
        this.rpsGame = rpsGame;
    }

    /**
     * Returns the interface.
     *
     * @return the interface
     */
    public @NonNull ChestInterface get() {
        return ChestInterface.builder()
                .addTransform(PaperTransform.chestFill(ItemStackElement.of(Constants.Items.MENU_BACKGROUND.build())))
                .addTransform(this::transformWaitingForOpponent)
                .build();
    }

    /**
     * Opens this interface for a player.
     *
     * @param player the player
     */
    public void open(final @NonNull RPSPlayer player) {
        get().open(PlayerViewer.of(player.player()), HashMapInterfaceArgument.with("player", player).build());
    }

    private @NonNull ChestPane transformWaitingForOpponent(final @NonNull ChestPane pane, final @NonNull InterfaceView view) {
        final @NonNull RPSPlayer player = view.argument().get("player");

        return pane;
    }
}
