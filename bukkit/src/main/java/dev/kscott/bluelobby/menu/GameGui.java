package dev.kscott.bluelobby.menu;

import broccolai.corn.adventure.AdventureItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.StonecutterGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * The /games menu.
 */
public class GameGui {

    public static @NonNull Gui create() {
        final @NonNull StonecutterGui gui = new StonecutterGui(LegacyComponentSerializer.legacySection().serialize(Component.text("mc.kaden.sh >> games").style(Constants.Chat.STYLE_DARK)));

        final @NonNull StaticPane pane = new StaticPane(0, 0, 1, 1);

        pane.addItem(new GuiItem(
                AdventureItemBuilder.adventure(Material.DARK_OAK_SIGN)
                        .name(Component.text()
                                .append(Constants.Chat.SERVER_NAME)
                                .decoration(TextDecoration.ITALIC, false)
                                .asComponent()
                        )
                        .loreComponents(List.of(
                                Component.text("Use this menu to select your a game!").style(Constants.Chat.STYLE_DEFAULT),
                                Component.empty(),
                                Component.text("Current active games:").style(Constants.Chat.STYLE_DEFAULT),
                                Component.text("- Bonk").color(Constants.Chat.COLOUR_LIGHT_BLUE).decoration(TextDecoration.ITALIC, false)
                        ))
                        .build()
        ), 0, 0);

        gui.getInputComponent().addPane(pane);

        gui.setOnGlobalClick(event -> {
//            event.setCancelled(true);
            System.out.println(event.getRawSlot());
        });

        return gui;
    }
}