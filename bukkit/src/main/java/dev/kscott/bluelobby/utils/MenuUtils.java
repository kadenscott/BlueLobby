package dev.kscott.bluelobby.utils;

import broccolai.corn.paper.PaperItemBuilder;
import dev.kscott.bluelobby.menu.MenuInterface;
import dev.kscott.interfaces.core.transform.Transform;
import dev.kscott.interfaces.paper.PlayerViewer;
import dev.kscott.interfaces.paper.element.ItemStackElement;
import dev.kscott.interfaces.paper.pane.ChestPane;
import dev.kscott.interfaces.paper.type.ChestInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Utility methods for menu operations.
 */
public class MenuUtils {

    /**
     * Returns a transformation that adds a back button, returning to the provided interface.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param returnInterface the interface to open when
     * @return the back button transformation
     */
    public static @NonNull Transform<ChestPane> backButton(final int x,
                                                           final int y,
                                                           final @NonNull ChestInterface returnInterface) {
        return (pane, view) -> {
            final @NonNull Component backIconTitle = Component.text("Click to go back").color(Constants.Chat.COLOUR_RED)
                    .decoration(TextDecoration.ITALIC, false);

            return pane.element(ItemStackElement.of(PaperItemBuilder.paper(Material.RED_STAINED_GLASS_PANE)
                            .name(backIconTitle)
                            .build(),
                    (clickEvent, clickView) -> returnInterface.open((PlayerViewer) clickView.viewer())), x, y);
        };

    }

}
