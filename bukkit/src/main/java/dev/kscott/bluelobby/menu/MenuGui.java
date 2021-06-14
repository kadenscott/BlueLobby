package dev.kscott.bluelobby.menu;

import broccolai.corn.paper.PaperItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The /menu gui.
 */
public class MenuGui {

    /**
     * Returns the /menu gui.
     *
     * @return the /menu gui
     */
    public static @NonNull Gui create() {
        final @NonNull ChestGui gui = new ChestGui(1, LegacyComponentSerializer.legacySection().serialize(Constants.Chat.SERVER_NAME));

        // Exit Item
        final @NonNull GuiItem exitItem = new GuiItem(
                PaperItemBuilder.paper(Material.BARRIER)
                        .name(Constants.Chat.SERVER_NAME)
                        .loreComponents(Component.text("Click to go back")
                                .color(NamedTextColor.RED)
                                .decoration(TextDecoration.ITALIC, false))
                        .build(),
                event -> event.getWhoClicked().closeInventory(InventoryCloseEvent.Reason.PLUGIN)
        );

        final @NonNull GuiItem gamesItem = new GuiItem(
                PaperItemBuilder.paper(Material.DIAMOND)
                        .name(Component.text("Games").decoration(TextDecoration.ITALIC, false))
                        .loreComponents(Component.text("Click to play a game!")
                                .color(NamedTextColor.BLUE)
                                .decoration(TextDecoration.ITALIC, false))
                        .build(),
                event -> {
                    final @NonNull Player player = (Player) event.getWhoClicked();

                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                    GameGui.create().show(player);
                }
        );

        // Pane
        final @NonNull StaticPane pane = new StaticPane(0, 0, 9, 1);

        pane.addItem(exitItem, 0, 0);
        pane.addItem(gamesItem, 4, 0);

        gui.addPane(pane);

        return gui;
    }

}
