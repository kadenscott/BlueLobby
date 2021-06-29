package dev.kscott.bluelobby.menu.readme.credits;

import broccolai.corn.paper.PaperItemBuilder;
import com.google.inject.Injector;
import dev.kscott.bluelobby.menu.MenuInterface;
import dev.kscott.bluelobby.menu.readme.ReadmeInterface;
import dev.kscott.bluelobby.utils.Constants;
import dev.kscott.bluelobby.utils.MenuUtils;
import dev.kscott.interfaces.core.view.InterfaceView;
import dev.kscott.interfaces.paper.PlayerViewer;
import dev.kscott.interfaces.paper.element.ItemStackElement;
import dev.kscott.interfaces.paper.pane.ChestPane;
import dev.kscott.interfaces.paper.transform.PaperTransform;
import dev.kscott.interfaces.paper.type.ChestInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

/**
 * The warp menu.
 */
public class CreditsProvider {

    /**
     * The chest interface.
     */
    private final @NonNull ChestInterface chestInterface;

    /**
     * The injector.
     */
    private final @NonNull Injector injector;

    /**
     * Constructs {@code WarpInterfaceProvider}.
     *
     * @param injector the injector
     */
    @Inject
    public CreditsProvider(final @NonNull Injector injector) {
        this.injector = injector;

        this.chestInterface = ChestInterface.builder()
                .rows(1)
                .title(Component.text("Credits"))
                .updates(false, 0)
                .addTransform(PaperTransform.chestFill(ItemStackElement.of(Constants.Items.MENU_BACKGROUND.build())))
                .addTransform(this::addPluginsIcon)
                .addTransform(MenuUtils.backButton(0, 3, this.injector.getInstance(ReadmeInterface.class)))
                .build();

    }

    private @NonNull ChestPane addPluginsIcon(final @NonNull ChestPane pane, final @NonNull InterfaceView view) {
        int x = 3;
        int y = 0;

        final @NonNull ItemStack itemStack = PaperItemBuilder.paper(Material.BREWING_STAND)
                .name(Component.text("Software")
                        .decoration(TextDecoration.ITALIC, false)
                        .color(Constants.Chat.COLOUR_DARK_BLUE))
                .loreComponents(
                        Component.text()
                                .append(Constants.Chat.SERVER_NAME)
                                .append(Component.text(" is proudly powered"))
                                .decoration(TextDecoration.ITALIC, false)
                                .color(Constants.Chat.COLOUR_LIGHT_BLUE)
                                .asComponent(),
                        Component.text()
                                .append(Component.text("by open source software."))
                                .decoration(TextDecoration.ITALIC, false)
                                .color(Constants.Chat.COLOUR_LIGHT_BLUE)
                                .asComponent(),
                        Component.empty(),
                        Component.text("Click to view information")
                                .style(Constants.Chat.STYLE_DEFAULT),
                        Component.text()
                                .append(Component.text("about the plugins and software "))
                                .asComponent()
                                .style(Constants.Chat.STYLE_DEFAULT))
                .build();

        return pane.element(ItemStackElement.of(itemStack, (clickEvent, clickView) -> {
            // Open plugins menu
        }), x, y);
    }

    public @NonNull ChestInterface get() {
        return this.chestInterface;
    }


}
