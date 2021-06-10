package dev.kscott.bluelobby.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * Stores constant values.
 */
public class Constants {

    /**
     * Stores constant chat messages.
     */
    public static class Chat {

        /**
         * The default text colour.
         */
        public static final @NonNull TextColor COLOUR_DEFAULT = TextColor.color(185, 185, 185);

        /**
         * The light blue text colour.
         */
        public static final @NonNull TextColor COLOUR_LIGHT_BLUE = TextColor.color(31, 197, 242);

        /**
         * The pink text colour.
         */
        public static final @NonNull TextColor COLOUR_PINK = TextColor.color(237, 159, 232);

        /**
         * The default text style.
         */
        public static final @NonNull Style STYLE_DEFAULT = Style.style(COLOUR_DEFAULT);

        /**
         * The text style for links.
         */
        public static final @NonNull Style STYLE_LINK = Style.style(COLOUR_LIGHT_BLUE);

        /**
         * The text style for commands.
         */
        public static final @NonNull Style STYLE_COMMAND = Style.style(COLOUR_PINK);

        /**
         * MiniMessage instance.
         */
        private static final @NonNull MiniMessage miniMessage = MiniMessage.get();

        /**
         * A bar with 15 characters.
         */
        private static final @NonNull Component barMedium = miniMessage.parse(" <gradient:#03c2fc:#0c7cc2:#03c2fc><strikethrough>               </strikethrough></gradient> ");

        /**
         * The name of the server.
         */
        public static final @NonNull Component SERVER_NAME = Component.text("<#f5a5a5><bold>mc.ksc.sh</bold></#f5a5a5>");

        /**
         * The motd.
         */
        public static final @NonNull List<Component> MOTD = List.of(
                barMedium.append(SERVER_NAME).append(barMedium),
                Component.empty(),
                Component.text("Welcome to ").style(STYLE_DEFAULT)
                        .append(SERVER_NAME)
                        .append(Component.text("! This server hosts a variety of my projects.").style(STYLE_DEFAULT)),
                Component.text("Use ").style(STYLE_DEFAULT)
                        .append(Component.text("/play").style(STYLE_COMMAND))
                        .append(Component.text(" to see the available projects.").style(STYLE_DEFAULT)),
                Component.empty(),
                Component.text("Currently hosted projects: ").style(STYLE_DEFAULT),
                Component.text("- Bonk").style(STYLE_LINK),
                Component.text("- Fight Club").style(STYLE_LINK),
                Component.empty(),
                Component.text("Website: ").style(STYLE_DEFAULT).append(Component.text("kaden.sh").style(STYLE_LINK)),
                Component.text("Discord: ").style(STYLE_DEFAULT).append(Component.text("chat.ksc.sh").style(STYLE_LINK)),
                Component.text("YouTube: ").style(STYLE_DEFAULT).append(Component.text("yt.ksc.sh").style(STYLE_LINK)),
                Component.empty(),
                barMedium.append(miniMessage.parse(" <#f5a5a5><bold>mc.ksc.sh</bold></#f5a5a5> ")).append(barMedium)
        );

    }

}
