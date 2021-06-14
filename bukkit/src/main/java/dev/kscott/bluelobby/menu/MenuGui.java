package dev.kscott.bluelobby.menu;

import broccolai.corn.adventure.AdventureItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

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
                AdventureItemBuilder.adventure(Material.BARRIER)
                        .name(Constants.Chat.SERVER_NAME)
                        .loreComponents(List.of(
                                Component.text("")
                        ))
                        .build(),
                event -> event.getWhoClicked().closeInventory(InventoryCloseEvent.Reason.PLUGIN)
        );

        // Pane
        final @NonNull StaticPane pane = new StaticPane(0, 0, 9, 1);

        pane.addItem(exitItem, 0, 0);

        gui.addPane(pane);

        return gui;
    }

}
