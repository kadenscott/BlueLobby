package dev.kscott.bluelobby.menu.server;

import broccolai.corn.paper.PaperItemBuilder;
import com.google.inject.Inject;
import dev.kscott.bluelobby.menu.Menu;
import dev.kscott.bluelobby.menu.MenuService;
import dev.kscott.bluelobby.utils.Constants;
import dev.kscott.bluelobby.utils.MenuUtils;
import dev.kscott.interfaces.core.view.InterfaceView;
import dev.kscott.interfaces.paper.element.ClickHandler;
import dev.kscott.interfaces.paper.element.ItemStackElement;
import dev.kscott.interfaces.paper.pane.ChestPane;
import dev.kscott.interfaces.paper.transform.PaperTransform;
import dev.kscott.interfaces.paper.type.ChestInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * The main menu for the server.
 */
public class GamesMenu implements Menu<ChestInterface> {

    /**
     * The menu service.
     */
    private final @NonNull MenuService menuService;

    /**
     * Constructs {@code MainMenu}.
     *
     * @param menuService the menu service
     */
    @Inject
    public GamesMenu(final @NonNull MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * Returns the interface.
     *
     * @return the interface
     */
    @Override
    public @NonNull ChestInterface get() {
        return ChestInterface.builder()
                .rows(4)
                .title(Component.text("GAMES.md")
                        .color(Constants.Chat.COLOUR_LIGHT_BLUE)
                        .decorate(TextDecoration.BOLD))
                .addTransform(PaperTransform.chestFill(ItemStackElement.of(Constants.Items.MENU_BACKGROUND.build())))
                .addTransform(MenuUtils.bottomBar(3, this.menuService, "games"))
                .addTransform(this::transformAddGameIcons)
                .addTransform(MenuUtils.closeButton(0, 3))
                .clickHandler(ClickHandler.cancel())
                .build();
    }

    private @NonNull ChestPane transformAddGameIcons(final @NonNull ChestPane pane, final @NonNull InterfaceView view) {
        final int y = 1;

        @NonNull ChestPane tempPane = pane;

        final @NonNull ItemStack bonkIcon = PaperItemBuilder.paper(Material.BLAZE_ROD)
                .name(Component.text("Bonk")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(Constants.Chat.COLOUR_YELLOW)
                )
                .loreComponents(List.of(
                        Component.text("Click to play").style(Constants.Chat.STYLE_DEFAULT)
                ))
                .build();

        final @NonNull ItemStack unk1Icon = PaperItemBuilder.paper(Material.PAINTING)
                .name(Component.text("????????")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(Constants.Chat.COLOUR_YELLOW)
                )
                .loreComponents(
                        List.of(Component.text("Coming soon")
                                .style(Constants.Chat.STYLE_DEFAULT)
                                .color(Constants.Chat.COLOUR_RED)
                        ))
                .build();


        final @NonNull ItemStack unk2Icon = PaperItemBuilder.paper(Material.NETHERITE_SWORD)
                .flags(
                        ItemFlag.HIDE_ENCHANTS,
                        ItemFlag.HIDE_DESTROYS,
                        ItemFlag.HIDE_ATTRIBUTES,
                        ItemFlag.HIDE_DYE,
                        ItemFlag.HIDE_PLACED_ON,
                        ItemFlag.HIDE_UNBREAKABLE
                )
                .name(Component.text("????? ???")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(Constants.Chat.COLOUR_YELLOW)
                )
                .loreComponents(List.of(Component.text("Coming soon")
                        .style(Constants.Chat.STYLE_DEFAULT)
                        .color(Constants.Chat.COLOUR_RED)))
                .build();

        return tempPane.element(ItemStackElement.of(bonkIcon), 3, y)
                .element(ItemStackElement.of(unk1Icon), 4, y)
                .element(ItemStackElement.of(unk2Icon), 5, y);
    }
}
