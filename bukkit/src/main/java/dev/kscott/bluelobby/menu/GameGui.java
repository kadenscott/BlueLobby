package dev.kscott.bluelobby.menu;

import com.github.stefvanschie.inventoryframework.gui.type.StonecutterGui;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * The /games menu.
 */
public class GameGui extends StonecutterGui {

    /**
     * Constructs a new GUI.
     */
    public GameGui() {
        super(LegacyComponentSerializer.legacySection().serialize(Component.text("mc.kaden.sh >> games").style(Constants.Chat.STYLE_HOVER)));
    }
}