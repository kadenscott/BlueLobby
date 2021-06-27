package dev.kscott.bluelobby.menu.readme;

import broccolai.corn.paper.PaperItemBuilder;
import com.google.inject.Injector;
import dev.kscott.bluelobby.menu.MenuInterface;
import dev.kscott.bluelobby.utils.Constants;
import dev.kscott.interfaces.core.transform.Transform;
import dev.kscott.interfaces.core.view.InterfaceView;
import dev.kscott.interfaces.paper.PlayerViewer;
import dev.kscott.interfaces.paper.element.ClickHandler;
import dev.kscott.interfaces.paper.element.ItemStackElement;
import dev.kscott.interfaces.paper.pane.ChestPane;
import dev.kscott.interfaces.paper.transform.PaperTransform;
import dev.kscott.interfaces.paper.type.ChestInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import java.util.List;

/**
 * The main /readme interface.
 */
public class ReadmeInterface extends ChestInterface {

    private final @NonNull Injector injector;

    @Inject
    public ReadmeInterface(final @NonNull Injector injector) {
        super(
                1,
                Component.text("README.md"),
                List.of(),
                false,
                0,
                ClickHandler.cancel()
        );

        this.injector = injector;
    }

    @Override
    public @NonNull List<Transform<ChestPane>> transformations() {
        return List.of(
                PaperTransform.chestFill(ItemStackElement.of(Constants.Items.MENU_BACKGROUND.build())),
                this::addBackIcon,
                this::addCommandsIcon,
                this::addAboutIcon
        );
    }

    /**
     * Adds the back icon to the pane.
     *
     * @param pane the pane
     * @param view the view
     * @return the pane
     */
    private @NonNull ChestPane addBackIcon(final @NonNull ChestPane pane, final @NonNull InterfaceView view) {
        // Coords of back icon
        final int x = 0;
        final int y = 0;

        final @NonNull Component backIconTitle = Component.text("Click to go back").color(Constants.Chat.COLOUR_RED)
                .decoration(TextDecoration.ITALIC, false);

        return pane.element(ItemStackElement.of(PaperItemBuilder.paper(Material.RED_STAINED_GLASS_PANE)
                        .name(backIconTitle)
                        .build(),
                (clickEvent, clickView) -> injector.getInstance(MenuInterface.class).open((PlayerViewer) clickView.viewer())), x, y);
    }

    /**
     * Adds the about icon to the pane.
     *
     * @param pane the pane
     * @param view the view
     * @return the pane
     */
    private @NonNull ChestPane addAboutIcon(final @NonNull ChestPane pane, final @NonNull InterfaceView view) {
        // Coords of back icon
        final int x = 4;
        final int y = 0;

        final @NonNull Component title = Component.text("About")
                .color(Constants.Chat.COLOUR_LIGHT_BLUE)
                .decoration(TextDecoration.ITALIC, false);

        final @NonNull List<Component> lore = List.of(
                Component.text("Click to view the").style(Constants.Chat.STYLE_DEFAULT),
                Component.text("about menu.").style(Constants.Chat.STYLE_DEFAULT)
        );

        return pane.element(ItemStackElement.of(PaperItemBuilder.paper(Material.BOOK)
                        .name(title)
                        .loreComponents(lore)
                        .build(),
                (clickEvent, clickView) -> injector.getInstance(AboutInterface.class).open((PlayerViewer) clickView.viewer())), x, y);
    }

    /**
     * Adds the about icon to the pane.
     *
     * @param pane the pane
     * @param view the view
     * @return the pane
     */
    private @NonNull ChestPane addCommandsIcon(final @NonNull ChestPane pane, final @NonNull InterfaceView view) {
        // Coords of back icon
        final int x = 3;
        final int y = 0;

        final @NonNull Component title = Component.text("Commands")
                .color(Constants.Chat.COLOUR_LIGHT_BLUE)
                .decoration(TextDecoration.ITALIC, false);

        final @NonNull List<Component> lore = List.of(
                Component.text("Click to view the").style(Constants.Chat.STYLE_DEFAULT),
                Component.text("commands.").style(Constants.Chat.STYLE_DEFAULT)
        );

        return pane.element(ItemStackElement.of(PaperItemBuilder.paper(Material.REPEATING_COMMAND_BLOCK)
                        .name(title)
                        .loreComponents(lore)
                        .build(),
                (clickEvent, clickView) -> injector.getInstance(AboutInterface.class).open((PlayerViewer) clickView.viewer())), x, y);
    }

}
