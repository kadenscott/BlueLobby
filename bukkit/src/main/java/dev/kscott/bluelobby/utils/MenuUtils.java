package dev.kscott.bluelobby.utils;

import broccolai.corn.paper.PaperItemBuilder;
import dev.kscott.bluelobby.menu.MenuService;
import dev.kscott.bluelobby.menu.server.GamesMenu;
import dev.kscott.bluelobby.menu.server.WarpsMenu;
import dev.kscott.bluelobby.menu.server.readme.ReadmeMenu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.interfaces.core.click.ClickHandler;
import org.incendo.interfaces.core.transform.Transform;
import org.incendo.interfaces.paper.PlayerViewer;
import org.incendo.interfaces.paper.click.ChestClickContext;
import org.incendo.interfaces.paper.element.ItemStackElement;
import org.incendo.interfaces.paper.pane.ChestPane;
import org.incendo.interfaces.paper.view.ChildView;

import java.util.List;

/**
 * Utility methods for menu operations.
 */
public class MenuUtils {

    /**
     * Returns a transformation adding the bottom bar.
     *
     * @param y           the y coordinate of the bar
     * @param menuService the menu service
     * @param menuId      the id of the menu this bar belongs to
     * @return the transform
     */
    public static @NonNull Transform<ChestPane> bottomBar(final int y,
                                                          final @NonNull MenuService menuService,
                                                          final @NonNull String menuId) {
        return (pane, view) -> {
            @NonNull ChestPane tempPane = pane;

            // Add the black background.
            for (int x = 0; x < ChestPane.MINECRAFT_CHEST_WIDTH; x++) {
                tempPane = tempPane.element(ItemStackElement.of(
                        PaperItemBuilder.paper(Material.BLACK_STAINED_GLASS_PANE)
                                .name(Component.empty())
                                .build()
                ), x, y);
            }

            // Games icon
            final @NonNull ItemStack gamesPageIcon = PaperItemBuilder.paper(Material.MUSIC_DISC_MALL)
                    .name(Component.text()
                            .append(menuId.equals("games") ?
                                    Component.text("??? ")
                                            .style(Constants.Chat.STYLE_DEFAULT) :
                                    Component.empty())
                            .append(MiniMessage.get().parse("<gradient:#f28a8a:#f5c38e>Games</gradient>")
                                    .decoration(TextDecoration.ITALIC, false))
                            .asComponent())
                    .loreComponents(List.of(
                            Component.empty(),
                            Component.text("Click to see all")
                                    .style(Constants.Chat.STYLE_DEFAULT),
                            Component.text("of the playable games.")
                                    .style(Constants.Chat.STYLE_DEFAULT)
                    ))
                    .flags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_POTION_EFFECTS)
                    .enchant(Enchantment.DAMAGE_ALL, menuId.equals("games") ? 1 : -1)
                    .build();

            // Readme icon
            final @NonNull ItemStack readmePageIcon = PaperItemBuilder.paper(Material.BOOK)
                    .name(Component.text()
                            .append(menuId.equals("readme") ?
                                    Component.text("??? ")
                                            .style(Constants.Chat.STYLE_DEFAULT) :
                                    Component.empty())
                            .append(MiniMessage.get().parse("<gradient:#73b8e6:#66dea6>README.md</gradient>")
                                    .decoration(TextDecoration.ITALIC, false))
                            .asComponent())
                    .loreComponents(List.of(
                            Component.empty(),
                            Component.text("Click to see help")
                                    .style(Constants.Chat.STYLE_DEFAULT),
                            Component.text("information, commands,")
                                    .style(Constants.Chat.STYLE_DEFAULT),
                            Component.text("and more.")
                                    .style(Constants.Chat.STYLE_DEFAULT)
                    ))
                    .flags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_POTION_EFFECTS)
                    .enchant(Enchantment.DAMAGE_ALL, menuId.equals("readme") ? 1 : -1)
                    .build();

            // Warp icon
            final @NonNull ItemStack warpPageIcon = PaperItemBuilder.paper(Material.ENDER_EYE)
                    .name(Component.text()
                            .append(menuId.equals("warps") ?
                                    Component.text("??? ")
                                            .style(Constants.Chat.STYLE_DEFAULT) :
                                    Component.empty())
                            .append(MiniMessage.get().parse("<gradient:#c587f5:#efa0fa>Warps</gradient>")
                                    .decoration(TextDecoration.ITALIC, false))
                            .asComponent())
                    .loreComponents(List.of(
                            Component.empty(),
                            Component.text("Click to see the warps.")
                                    .style(Constants.Chat.STYLE_DEFAULT)
                    ))
                    .flags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_POTION_EFFECTS)
                    .enchant(Enchantment.DAMAGE_ALL, menuId.equals("warps") ? 1 : -1)
                    .build();

            return tempPane
                    .element(ItemStackElement.of(readmePageIcon, (ctx) -> {
                        menuService.get(ReadmeMenu.class).open(((PlayerViewer) ctx.viewer()).player());
                    }), 3, y)
                    .element(ItemStackElement.of(gamesPageIcon, (ctx) -> {
                        menuService.get(GamesMenu.class).open(((PlayerViewer) ctx.viewer()).player());
                    }), 4, y)
                    .element(ItemStackElement.of(warpPageIcon, (ctx) -> {
                        menuService.get(WarpsMenu.class).open(((PlayerViewer) ctx.viewer()).player());
                    }), 5, y);
        };
    }

    /**
     * Returns a transformation that adds a black bar at the given y height.
     *
     * @param y the y coordinate
     * @return the transform
     */
    public static @NonNull Transform<ChestPane> blackBar(final int y) {
        return (pane, view) -> {
            @NonNull ChestPane tempPane = pane;

            // Add the black background.
            for (int x = 0; x < ChestPane.MINECRAFT_CHEST_WIDTH; x++) {
                tempPane = tempPane.element(ItemStackElement.of(
                        PaperItemBuilder.paper(Material.BLACK_STAINED_GLASS_PANE)
                                .name(Component.empty())
                                .build()
                ), x, y);
            }

            return tempPane;
        };
    }

    /**
     * Returns a transformation that adds a close button.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the back button transformation
     */
    public static @NonNull Transform<ChestPane> closeButton(final int x,
                                                            final int y) {
        return (pane, view) -> {
            final @NonNull Component backIconTitle = Component.text("Close").color(Constants.Chat.COLOUR_RED)
                    .decoration(TextDecoration.ITALIC, false);

            return pane.element(ItemStackElement.of(PaperItemBuilder.paper(Material.RED_STAINED_GLASS_PANE)
                    .name(backIconTitle)
                    .build(), (context) -> {
                if (context.view() instanceof ChildView child) {
                    if (child.hasParent()) {
                        child.back();
                        return;
                    }
                }

                context.viewer().close();
            }), x, y);
        };

    }

}
