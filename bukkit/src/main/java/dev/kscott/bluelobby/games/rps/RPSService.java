package dev.kscott.bluelobby.games.rps;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.kscott.bluelobby.menu.MenuService;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
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
     * The list of pending invites.
     */
    private final @NonNull List<RPSInvite> invites;

    /**
     * The plugin.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The menu service.
     */
    private final @NonNull MenuService menuService;

    /**
     * Constructs {@code RPSService}.
     */
    @Inject
    public RPSService(final @NonNull JavaPlugin plugin,
                      final @NonNull MenuService menuService) {
        this.games = new ArrayList<>();
        this.plugin = plugin;
        this.invites = new ArrayList<>();
        this.menuService = menuService;
    }

    /**
     * Invites a player to a rock-paper-scissors game.
     *
     * @param challenger the challenger
     * @param opponent   the opponent
     * @return the invite
     */
    public @NonNull RPSInvite invite(final @NonNull Player challenger, final @NonNull Player opponent) {
        final @NonNull RPSInvite invite = new RPSInvite(challenger, opponent, System.currentTimeMillis());

        challenger.sendMessage(Component.text("You invited " + opponent.getName() + " to a game of rock-paper-scissors.")
                .style(Constants.Chat.STYLE_DEFAULT));

        opponent.sendMessage(Component.text("You were invited to play rock-paper-scissors by " + challenger.getName() + ".")
                .style(Constants.Chat.STYLE_DEFAULT));
        opponent.sendMessage(Component.text("Click here to accept the invite.")
                .style(Constants.Chat.STYLE_COMMAND)
                .clickEvent(ClickEvent.runCommand("/rps accept " + challenger.getName()))
                .hoverEvent(HoverEvent.showText(Component.text("Click to run /rps accept " + challenger.getName())
                        .style(Constants.Chat.STYLE_COMMAND))));

        this.invites.add(invite);

        return invite;
    }

    /**
     * Accepts a pending game invite.
     *
     * @param challenger the challenger (the one who invited)
     * @param opponent   the opponent (the one who was invited)
     * @return the game
     * @throws IllegalArgumentException if one of the players aren't online or are already in a game
     */
    public @NonNull RPSGame accept(final @NonNull Player challenger, final @NonNull Player opponent) {
        final @NonNull RPSGame game = create(challenger, opponent);

        this.invites.removeIf(invite -> invite.challenger() == challenger && invite.opponent() == opponent);

        game.start();

        game.openMenu(RPSPlayer.Type.OPPONENT);
        game.openMenu(RPSPlayer.Type.CHALLENGER);

        return game;
    }

    /**
     * Creates a new rock-paper-scissors game.
     *
     * @param challenger the challenger
     * @param opponent   the opponent
     * @return the game
     * @throws IllegalArgumentException if a player is already in a game
     */
    public @NonNull RPSGame create(final @NonNull Player challenger, final @NonNull Player opponent) {
        if (playing(challenger)) {
            throw new IllegalArgumentException("Player '" + challenger.getName() + "' (challenger) is already playing a game.");
        }

        if (playing(opponent)) {
            throw new IllegalArgumentException("Player '" + opponent.getName() + "' (opponent) is already playing a game.");
        }

        final @NonNull RPSGame game = new RPSGame(this.plugin, this.menuService, this, challenger, opponent);

        this.games.add(game);

        return game;
    }

    public void end(final @NonNull RPSGame rpsGame) {
        this.games.remove(rpsGame);
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
