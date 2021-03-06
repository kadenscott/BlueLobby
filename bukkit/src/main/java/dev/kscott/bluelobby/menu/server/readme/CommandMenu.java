package dev.kscott.bluelobby.menu.server.readme;

import broccolai.corn.paper.PaperItemBuilder;
import com.google.inject.Inject;
import dev.kscott.bluelobby.menu.Menu;
import dev.kscott.bluelobby.menu.MenuService;
import dev.kscott.bluelobby.utils.Constants;
import dev.kscott.bluelobby.utils.MenuUtils;
import org.incendo.interfaces.core.view.InterfaceView;
import org.incendo.interfaces.core.click.ClickHandler;
import org.incendo.interfaces.paper.element.ItemStackElement;
import org.incendo.interfaces.paper.pane.ChestPane;
import org.incendo.interfaces.paper.transform.PaperTransform;
import org.incendo.interfaces.paper.type.ChestInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * The command menu for the server.
 */
public class CommandMenu implements Menu<ChestInterface> {

    /**
     * The menu service.
     */
    private final @NonNull MenuService menuService;

    /**
     * Constructs {@code CommandMenu}.
     *
     * @param menuService the menu service
     */
    @Inject
    public CommandMenu(final @NonNull MenuService menuService) {
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
                .title(Component.text()
                        .append(Component.text("● ")
                                .color(Constants.Chat.COLOUR_DARK_GRAY))
                        .append(MiniMessage.get().parse("<gradient:#305eb3:#17804f>Commands</gradient>"))
                        .asComponent())
                .addTransform(PaperTransform.chestFill(ItemStackElement.of(Constants.Items.MENU_BACKGROUND.build())))
                .addTransform(MenuUtils.blackBar(3))
                .addTransform(MenuUtils.closeButton(0, 3))
                .clickHandler(ClickHandler.cancel())
                .build();
    }

    private @NonNull ChestPane transformAddIcons(final @NonNull ChestPane pane, final @NonNull InterfaceView view) {
        final int y = 1;

        @NonNull ChestPane tempPane = pane;

        final @NonNull ItemStack commandsIcon = PaperItemBuilder.paper(Material.COMMAND_BLOCK)
                .name(Component.text("")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(Constants.Chat.COLOUR_YELLOW)
                )
                .loreComponents(
                        List.of(Component.text("Click to view the commands").style(Constants.Chat.STYLE_DEFAULT))
                )
                .build();

        return tempPane.element(ItemStackElement.of(commandsIcon), 4, y);
//                .element(ItemStackElement.of(unk1Icon), 4, y)
//                .element(ItemStackElement.of(unk2Icon), 5, y);
    }
}
