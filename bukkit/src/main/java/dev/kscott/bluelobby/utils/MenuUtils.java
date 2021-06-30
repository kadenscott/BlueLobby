package dev.kscott.bluelobby.utils;

import broccolai.corn.paper.PaperItemBuilder;
import dev.kscott.bluelobby.menu.MenuService;
import dev.kscott.bluelobby.menu.server.GamesMenu;
import dev.kscott.bluelobby.menu.server.ReadmeMenu;
import dev.kscott.bluelobby.menu.server.WarpsMenu;
import dev.kscott.interfaces.core.transform.Transform;
import dev.kscott.interfaces.paper.element.ClickHandler;
import dev.kscott.interfaces.paper.element.ItemStackElement;
import dev.kscott.interfaces.paper.pane.ChestPane;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

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
                                    Component.text("● ")
                                            .style(Constants.Chat.STYLE_DEFAULT):
                                    Component.empty())
                            .append(Component.text("GAMES.md")
                                    .color(Constants.Chat.COLOUR_LIGHT_BLUE)
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
                                    Component.text("● ")
                                            .style(Constants.Chat.STYLE_DEFAULT):
                                    Component.empty())
                            .append(Component.text("README.md")
                                    .color(Constants.Chat.COLOUR_LIGHT_BLUE)
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
                                    Component.text("● ")
                                            .style(Constants.Chat.STYLE_DEFAULT):
                                    Component.empty())
                            .append(Component.text("WARPS.md")
                                    .color(Constants.Chat.COLOUR_LIGHT_BLUE)
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
                    .element(ItemStackElement.of(readmePageIcon, (clickEvent, clickView) -> {
                        menuService.get(ReadmeMenu.class).open((Player) clickEvent.getWhoClicked());
                    }), 3, y)
                    .element(ItemStackElement.of(gamesPageIcon, (clickEvent, clickView) -> {
                        menuService.get(GamesMenu.class).open((Player) clickEvent.getWhoClicked());
                    }), 4, y)
                    .element(ItemStackElement.of(warpPageIcon, (clickEvent, clickView) -> {
                        menuService.get(WarpsMenu.class).open((Player) clickEvent.getWhoClicked());
                    }), 5, y);
        };
    }


    /**
     * Returns a transformation that adds a back button with the provided click handler.
     *
     * @param x       the x coordinate
     * @param y       the y coordinate
     * @param handler the click handler
     * @return the back button transformation
     */
    public static @NonNull Transform<ChestPane> backButton(final int x,
                                                           final int y,
                                                           final @NonNull ClickHandler handler) {
        return (pane, view) -> {
            final @NonNull Component backIconTitle = Component.text("Back").color(Constants.Chat.COLOUR_RED)
                    .decoration(TextDecoration.ITALIC, false);

            return pane.element(ItemStackElement.of(PaperItemBuilder.paper(Material.RED_STAINED_GLASS_PANE)
                    .name(backIconTitle)
                    .build(), handler), x, y);
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
                    .build(), (clickEvent, clickView) -> {
                clickEvent.getWhoClicked().closeInventory(InventoryCloseEvent.Reason.PLUGIN);
            }), x, y);
        };

    }

}
