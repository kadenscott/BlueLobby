package dev.kscott.bluelobby.menu.server;

import broccolai.corn.paper.PaperItemBuilder;
import com.google.inject.Inject;
import dev.kscott.bluelobby.area.ServerLocation;
import dev.kscott.bluelobby.menu.Menu;
import dev.kscott.bluelobby.menu.MenuService;
import dev.kscott.bluelobby.utils.Constants;
import dev.kscott.bluelobby.utils.MenuUtils;
import dev.kscott.bluelobby.warp.WarpService;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.incendo.interfaces.core.view.InterfaceView;
import org.incendo.interfaces.paper.element.ClickHandler;
import org.incendo.interfaces.paper.element.ItemStackElement;
import org.incendo.interfaces.paper.pane.ChestPane;
import org.incendo.interfaces.paper.transform.PaperTransform;
import org.incendo.interfaces.paper.type.ChestInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The warp menu for the server.
 */
public class WarpsMenu implements Menu<ChestInterface> {

    /**
     * The menu service.
     */
    private final @NonNull MenuService menuService;

    /**
     * The warp service.
     */
    private final @NonNull WarpService warpService;

    /**
     * Constructs {@code WarpMenu}.
     *
     * @param menuService the menu service
     * @param warpService the warp service
     */
    @Inject
    public WarpsMenu(final @NonNull MenuService menuService,
                     final @NonNull WarpService warpService) {
        this.menuService = menuService;
        this.warpService = warpService;
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
                .title(Component.text()
                        .append(Component.text("● ")
                                .color(Constants.Chat.COLOUR_DARK_GRAY))
                        .append(MiniMessage.get().parse("<gradient:#740bbf:#bd28b5>Warps</gradient>"))
                        .asComponent())
                .addTransform(PaperTransform.chestFill(ItemStackElement.of(Constants.Items.MENU_BACKGROUND.build())))
                .addTransform(MenuUtils.bottomBar(3, this.menuService, "warps"))
                .addTransform(this::addWarpIcons)
                .addTransform(MenuUtils.closeButton(0, 3))
                .clickHandler(ClickHandler.cancel())
                .build();
    }

    private @NonNull ChestPane addWarpIcons(final @NonNull ChestPane pane, final @NonNull InterfaceView view) {
        int warpsX = 0;
        int warpsY = 0;
        int warpsLength = 9;
        int warpsHeight = 2;

        @NonNull ChestPane tempPane = pane;

        final @NonNull List<ServerLocation> warps = this.warpService.warps();

        for (int y = warpsY; y < warpsHeight; y++) {
            for (int x = warpsX; x < warpsLength; x++) {

                int index = y * 9 + x;

                if (warps.size() - 1 < index) {
                    break;
                }

                final @NonNull ServerLocation warp = warps.get(index);

                final @NonNull List<Component> lore = new ArrayList<>();

                if (warp.description().size() != 0) {
                    lore.add(Component.empty());

                    for (final @NonNull Component component : warp.description()) {
                        lore.add(component
                                .color(Constants.Chat.COLOUR_ORANGE)
                                .decoration(TextDecoration.ITALIC, true)
                        );
                    }
                }

                lore.add(Component.empty());
                lore.add(Component.text("Click to visit")
                        .style(Constants.Chat.STYLE_DEFAULT));

                final @NonNull ItemStack warpItem = PaperItemBuilder.paper(warp.icon())
                        .name(Component.text(warp.name())
                                .decoration(TextDecoration.ITALIC, false)
                                .color(Constants.Chat.COLOUR_LIGHT_GREEN))
                        .loreComponents(lore)
                        .flags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE)
                        .build();

                tempPane = tempPane.element(ItemStackElement.of(warpItem, (clickEvent, clickView) -> {
                    final @NonNull Player player = (Player) clickEvent.getWhoClicked();

                    this.warpService.warp(player, warp);
                }), x, y);
            }
        }

        return tempPane;
    }
}
