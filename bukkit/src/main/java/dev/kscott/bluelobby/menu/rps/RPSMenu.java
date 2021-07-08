package dev.kscott.bluelobby.menu.rps;

import broccolai.corn.paper.PaperItemBuilder;
import dev.kscott.bluelobby.games.rps.RPSGame;
import dev.kscott.bluelobby.games.rps.RPSPlayer;
import dev.kscott.bluelobby.menu.Menu;
import dev.kscott.bluelobby.menu.MenuService;
import dev.kscott.bluelobby.utils.SkullCreator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.interfaces.core.arguments.HashMapInterfaceArgument;
import org.incendo.interfaces.core.view.InterfaceView;
import org.incendo.interfaces.paper.PlayerViewer;
import org.incendo.interfaces.paper.element.ItemStackElement;
import org.incendo.interfaces.paper.pane.ChestPane;
import org.incendo.interfaces.paper.transform.PaperTransform;
import org.incendo.interfaces.paper.type.ChestInterface;
import org.incendo.interfaces.paper.view.ChestView;

/**
 * The menu for a rock-paper-scissors game.
 */
public class RPSMenu implements Menu<ChestInterface> {

    /**
     * The menu service.
     */
    private final @NonNull MenuService menuService;

    /**
     * The RPS game.
     */
    private final @NonNull RPSGame rpsGame;

    /**
     * Constructs {@code RPSMenu}.
     *
     * @param rpsGame the RPS game
     */
    public RPSMenu(final @NonNull RPSGame rpsGame,
                   final @NonNull MenuService menuService) {
        this.rpsGame = rpsGame;
        this.menuService = menuService;
    }

    /**
     * Returns the interface.
     *
     * @return the interface
     */
    public @NonNull ChestInterface get() {
        return ChestInterface.builder()
                .rows(3)
                .addTransform(PaperTransform.chestFill(ItemStackElement.of(PaperItemBuilder.paper(Material.BLACK_STAINED_GLASS_PANE).name(Component.empty()).build())))
                .addTransform(this::transformWaitingForChoices)
                .addTransform(this::transformRunningCountdown)
                .addTransform(this::transformGameOver)
                .build();
    }

    /**
     * Opens this interface for a player.
     *
     * @param player the player
     */
    public @NonNull ChestView open(final @NonNull RPSPlayer player) {
        return get().open(PlayerViewer.of(player.player()), HashMapInterfaceArgument.with("player", player).build());
    }

    private @NonNull ChestPane transformWaitingForChoices(final @NonNull ChestPane pane, final @NonNull InterfaceView view) {
        final @NonNull RPSPlayer player = view.argument().get("player");

        final @NonNull RPSGame game = player.game();

        if (game.state() != RPSGame.State.WAITING_FOR_CHOICES) {
            return pane;
        }

        @NonNull ChestPane tempPane = pane;

        tempPane = addChoiceView(game, RPSPlayer.Type.OPPONENT, (ChestView) view, tempPane);
        tempPane = addChoiceView(game, RPSPlayer.Type.CHALLENGER, (ChestView) view, tempPane);

        return tempPane;
    }

    private @NonNull ChestPane transformRunningCountdown(final @NonNull ChestPane pane, final @NonNull InterfaceView view) {
        final @NonNull RPSPlayer player = view.argument().get("player");

        final @NonNull RPSGame game = player.game();

        if (game.state() != RPSGame.State.RUNNING_COUNTDOWN) {
            return pane;
        }

        @NonNull ChestPane tempPane = pane;

        return switch (game.countdown()) {
            case 3 -> {
                final int length = ChestPane.MINECRAFT_CHEST_WIDTH;
                final int height = pane.rows();

                for (int x = 0; x < length; x++) {
                    for (int y = 0; y < height; y++) {
                        tempPane = tempPane.element(ItemStackElement.of(PaperItemBuilder.paper(Material.RED_STAINED_GLASS_PANE).name(Component.empty()).build()), x, y);
                    }
                }

                final @NonNull ItemStackElement rockItemElement = ItemStackElement.of(
                        PaperItemBuilder.paper(Material.COBBLESTONE)
                                .name(Component.text("Rock").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false))
                                .build()
                );

                yield tempPane.element(rockItemElement, 4, 1);
            }
            case 2 -> {
                final int length = ChestPane.MINECRAFT_CHEST_WIDTH;
                final int height = pane.rows();

                for (int x = 0; x < length; x++) {
                    for (int y = 0; y < height; y++) {
                        tempPane = tempPane.element(ItemStackElement.of(PaperItemBuilder.paper(Material.ORANGE_STAINED_GLASS_PANE).name(Component.empty()).build()), x, y);
                    }
                }

                final @NonNull ItemStackElement paperItemElement = ItemStackElement.of(
                        PaperItemBuilder.paper(Material.PAPER)
                                .name(Component.text("Paper").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false))
                                .build()
                );

                yield tempPane.element(paperItemElement, 4, 1);
            }
            case 1 -> {
                final int length = ChestPane.MINECRAFT_CHEST_WIDTH;
                final int height = pane.rows();

                for (int x = 0; x < length; x++) {
                    for (int y = 0; y < height; y++) {
                        tempPane = tempPane.element(ItemStackElement.of(PaperItemBuilder.paper(Material.GREEN_STAINED_GLASS_PANE).name(Component.empty()).build()), x, y);
                    }
                }

                final @NonNull ItemStackElement scissorsItemElement = ItemStackElement.of(
                        PaperItemBuilder.paper(Material.SHEARS)
                                .name(Component.text("Scissors").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false))
                                .build()
                );

                yield tempPane.element(scissorsItemElement, 4, 1);
            }
            default -> tempPane;
        };
    }

    private @NonNull ChestPane transformGameOver(final @NonNull ChestPane pane, final @NonNull InterfaceView view) {
        final @NonNull RPSPlayer player = view.argument().get("player");

        if (player.game().state() != RPSGame.State.GAME_OVER) {
            return pane;
        }

        return pane;
    }

    /**
     * Adds the player's choice stuff to the pane.
     *
     * @param game the game
     * @param type the player type
     * @param view the view
     * @param pane the pane
     * @return the pane
     */
    private @NonNull ChestPane addChoiceView(final @NonNull RPSGame game,
                                             final RPSPlayer.@NonNull Type type,
                                             final @NonNull ChestView view,
                                             final @NonNull ChestPane pane) {
        final @NonNull RPSPlayer player = type == RPSPlayer.Type.OPPONENT ? game.opponent() : game.challenger();
        final boolean isViewer = view.viewer().player() == player.player();

        final int x = type == RPSPlayer.Type.CHALLENGER ? 0 : 4;

        if (isViewer) {
            final @NonNull ItemStackElement rockItemElement = ItemStackElement.of(
                    PaperItemBuilder.paper(Material.COBBLESTONE)
                            .name(Component.text("Rock").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false))
                            .flags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE)
                            .enchant(Enchantment.DAMAGE_ALL, rpsGame.choice(player) == RPSGame.Choice.ROCK ? 1 : -1)
                            .build(),
                    (ctx) -> {
                        if (rpsGame.chosen(player)) return;

                        rpsGame.choice(player.type(), RPSGame.Choice.ROCK);
                    }
            );

            final @NonNull ItemStackElement paperItemElement = ItemStackElement.of(
                    PaperItemBuilder.paper(Material.PAPER)
                            .name(Component.text("Paper").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false))
                            .flags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE)
                            .enchant(Enchantment.DAMAGE_ALL, rpsGame.choice(player) == RPSGame.Choice.PAPER ? 1 : -1)
                            .build(),
                    (ctx) -> {
                        if (rpsGame.chosen(player)) return;

                        rpsGame.choice(player.type(), RPSGame.Choice.PAPER);
                    }
            );

            final @NonNull ItemStackElement scissorsItemElement = ItemStackElement.of(
                    PaperItemBuilder.paper(Material.SHEARS)
                            .name(Component.text("Scissors").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false))
                            .flags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE)
                            .enchant(Enchantment.DAMAGE_ALL, rpsGame.choice(player) == RPSGame.Choice.SCISSORS ? 1 : -1)
                            .build(),
                    (ctx) -> {
                        if (rpsGame.chosen(player)) return;

                        rpsGame.choice(player.type(), RPSGame.Choice.SCISSORS);
                    }
            );
            return pane
                    .element(rockItemElement, x, 1)
                    .element(paperItemElement, x + 1, 1)
                    .element(scissorsItemElement, x + 2, 1);
        } else {
            final @NonNull ItemStackElement headElement = ItemStackElement.of(
                    PaperItemBuilder.paper(SkullCreator.itemFromUuid(player.uuid()))
                            .name(Component.text()
                                    .append(type == RPSPlayer.Type.CHALLENGER ?
                                            // Challenger name
                                            MiniMessage.get()
                                                    .parse("<gradient:#e07070:#bf2121><bold>" + player.player().getName() + "</bold></gradient>") :
                                            // Opponent name
                                            MiniMessage.get()
                                                    .parse("<gradient:#2157a3:#709fe0><bold>" + player.player().getName() + "</bold></gradient>"))
                                    .decoration(TextDecoration.ITALIC, false)
                                    .asComponent())
                            .build()
            );

            final @NonNull ItemStackElement bgElement = ItemStackElement.of(
                    PaperItemBuilder.paper(type == RPSPlayer.Type.CHALLENGER ? Material.RED_STAINED_GLASS_PANE : Material.BLUE_STAINED_GLASS_PANE)
                            .name(Component.empty())
                            .build()
            );

            @NonNull ChestPane tempPane = pane;

//            tempPane = PaperTransform.chestRect(bgElement, x, 0, 3, 3).apply(pane, view);

            return tempPane
                    .element(headElement, x + 1, 1);
        }

    }

}
