package dev.kscott.bluelobby.menu;

import broccolai.corn.adventure.AdventureItemBuilder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.StonecutterGui;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;

import java.util.List;

/**
 * The /games menu.
 */
public class GameGui extends StonecutterGui {

    /**
     * Constructs a new GUI.
     */
    public GameGui() {
        super(LegacyComponentSerializer.legacySection().serialize(Component.text("mc.kaden.sh >> games").style(Constants.Chat.STYLE_HOVER)));

        this.getInputComponent().setItem(new GuiItem(
                AdventureItemBuilder.adventure(Material.DARK_OAK_SIGN)
                        .name(Component.text()
                                .append(Constants.Chat.SERVER_NAME)
                                .append(Component.text(" games").color(Constants.Chat.COLOUR_LIGHT_BLUE))
                                .asComponent()
                        )
                        .loreComponents(List.of(
                                Component.text("Use this menu to select your a game!").style(Constants.Chat.STYLE_DEFAULT),
                                Component.empty(),
                                Component.text("Current active games:").style(Constants.Chat.STYLE_DEFAULT),
                                Component.text("- Bonk").color(Constants.Chat.COLOUR_LIGHT_BLUE)
                        ))
                        .build()
        ), 0, 0);
    }
}