package dev.kscott.bluelobby.menu;

import broccolai.corn.paper.PaperItemBuilder;
import com.google.inject.Injector;
import dev.kscott.bluelobby.location.LocationRegistry;
import dev.kscott.bluelobby.location.ServerLocation;
import dev.kscott.bluelobby.utils.Constants;
import dev.kscott.bluelobby.warp.WarpService;
import dev.kscott.interfaces.core.view.InterfaceView;
import dev.kscott.interfaces.core.view.InterfaceViewer;
import dev.kscott.interfaces.paper.PlayerViewer;
import dev.kscott.interfaces.paper.element.ClickHandler;
import dev.kscott.interfaces.paper.element.ItemStackElement;
import dev.kscott.interfaces.paper.pane.ChestPane;
import dev.kscott.interfaces.paper.transform.PaperTransform;
import dev.kscott.interfaces.paper.type.ChestInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * The warp menu.
 */
public class WarpInterfaceProvider {

    /**
     * The chest interface.
     */
    private final @NonNull ChestInterface chestInterface;

    /**
     * The warp service.
     */
    private final @NonNull WarpService warpService;

    /**
     * The injector.
     */
    private final @NonNull Injector injector;

    /**
     * Constructs {@code WarpInterfaceProvider}.
     *
     * @param warpService the warp service
     * @param injector the injector
     */
    @Inject
    public WarpInterfaceProvider(final @NonNull WarpService warpService,
                                 final @NonNull Injector injector) {

        this.injector = injector;
        this.warpService = warpService;

        this.chestInterface = ChestInterface.builder()
                .rows(4)
                .title(Component.text("Warps"))
                .updates(false, 0)
                .addTransform(PaperTransform.chestFill(ItemStackElement.of(Constants.Items.MENU_BACKGROUND.build())))
                .addTransform(this::addWarpIcons)
                .addTransform(this::addBackIcon)
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

                final @NonNull ItemStack warpItem = PaperItemBuilder.paper(warp.material())
                        .name(Component.text(warp.name())
                                .decoration(TextDecoration.ITALIC, false)
                                .color(Constants.Chat.COLOUR_LIGHT_GREEN))
                        .loreComponents(
                                Component.text("Click to visit")
                                        .style(Constants.Chat.STYLE_DEFAULT)
                        )
                        .flags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE)
                        .build();

                tempPane = tempPane.element(ItemStackElement.of(warpItem, (clickEvent, clickView) -> {
                    this.warpService.warp((Player) clickEvent.getWhoClicked(), warp);
                }), x, y);
            }
        }

        return tempPane;
    }

    public @NonNull ChestInterface get() {
        return this.chestInterface;
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
        final int y = 3;

        final @NonNull Component backIconTitle = Component.text("Click to go back").color(Constants.Chat.COLOUR_RED)
                .decoration(TextDecoration.ITALIC, false);

        return pane.element(ItemStackElement.of(PaperItemBuilder.paper(Material.RED_STAINED_GLASS_PANE)
                        .name(backIconTitle)
                        .build(),
                (clickEvent, clickView) -> injector.getInstance(MenuInterface.class).open((PlayerViewer) clickView.viewer())), x, y);
    }


}
