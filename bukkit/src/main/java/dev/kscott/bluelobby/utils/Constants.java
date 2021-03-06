package dev.kscott.bluelobby.utils;

import broccolai.corn.paper.PaperItemBuilder;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Stores constant values.
 */
public class Constants {

    /**
     * Stores constant chat messages.
     */
    public static class Chat {

        /**
         * MiniMessage instance.
         */
        private static final @NonNull MiniMessage miniMessage = MiniMessage.get();

        /**
         * The light gray text colour.
         */
        public static final @NonNull TextColor COLOUR_LIGHT_GRAY = TextColor.color(185, 185, 185);

        /**
         * The dark gray text colour.
         */
        public static final @NonNull TextColor COLOUR_DARK_GRAY = TextColor.color(52, 52, 52);

        /**
         * The light blue text colour.
         */
        public static final @NonNull TextColor COLOUR_LIGHT_BLUE = TextColor.color(66, 170, 236);

        public static final @NonNull TextColor COLOUR_LIGHT_GREEN = TextColor.color(126, 242, 126);

        public static final @NonNull TextColor COLOUR_DARK_GREEN = TextColor.color(26, 143, 26);

        /**
         * The dark blue text colour.
         */
        public static final @NonNull TextColor COLOUR_DARK_BLUE = TextColor.color(36, 67, 121);

        /**
         * The purple text colour.
         */
        public static final @NonNull TextColor COLOUR_PURPLE = TextColor.color(150, 70, 174);

        /**
         * The pink text colour.
         */
        public static final @NonNull TextColor COLOUR_PINK = TextColor.color(245, 144, 226);

        /**
         * The orange text colour.
         */
        public static final @NonNull TextColor COLOUR_ORANGE = TextColor.color(213, 124, 45);

        /**
         * The red text colour.
         */
        public static final @NonNull TextColor COLOUR_RED = TextColor.color(229, 66, 62);

        public static final @NonNull TextColor COLOUR_DARK_RED = TextColor.color(133, 0, 0);

        /**
         * The yellow text colour.
         */
        public static final @NonNull TextColor COLOUR_YELLOW = TextColor.color(237, 213, 107);

        /**
         * The light yellow text colour.
         */
        public static final @NonNull TextColor COLOUR_YELLOW_LIGHT = TextColor.color(255, 215, 41);

        /**
         * The default text style.
         */
        public static final @NonNull Style STYLE_DEFAULT = Style.style(COLOUR_LIGHT_GRAY).decoration(TextDecoration.ITALIC, false);

        /**
         * A style with no italics.
         */
        public static final @NonNull Style STYLE_NO_ITALICS = Style.empty().toBuilder().decoration(TextDecoration.ITALIC, false).build();

        /**
         * The default text style.
         */
        public static final @NonNull Style STYLE_DARK = Style.style(COLOUR_DARK_GRAY).decoration(TextDecoration.ITALIC, false);

        /**
         * The text style for links.
         */
        public static final @NonNull Style STYLE_LINK = Style.style(COLOUR_LIGHT_BLUE).decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false);

        public static final @NonNull Style STYLE_HOVER = Style.style(COLOUR_LIGHT_GRAY).decorate(TextDecoration.ITALIC);

        /**
         * The text style for commands.
         */
        public static final @NonNull Style STYLE_COMMAND = Style.style(COLOUR_DARK_BLUE).decoration(TextDecoration.ITALIC, false);
        /**
         * The name of the server.
         */
        public static final @NonNull Component SERVER_NAME = miniMessage.parse("<gradient:#FF512F:#DD2476:#fc6767><bold>mc.ksc.sh</bold></gradient>");

        /**
         * The name of the server.
         */
        public static final @NonNull Component OWNER_NAME = miniMessage.parse("<gradient:#00d2ff:#3a7bd5><bold>Bluely</bold></gradient>");

        /**
         * The first line of the ping motd.
         */
        public static final @NonNull Component PING_MOTD_LINE_1 = Component.text()
                .append(Component.text("                       "))
                .append(Component.text("??? ").color(TextColor.color(137, 219, 240)))
                .append(SERVER_NAME)
                .append(Component.text(" ???").color(TextColor.color(137, 219, 240)))
                .asComponent();

        /**
         * The second line of the ping motd.
         */
        public static final @NonNull Component PING_MOTD_LINE_2 = Component.text()
                .append(Component.text("                        "))
                .append(Component.text("- ").style(Chat.STYLE_DARK))
                .append(Component.text("by ").style(Chat.STYLE_DEFAULT))
                .append(OWNER_NAME)
                .append(Component.text(" -").style(Chat.STYLE_DARK))
                .asComponent();

        /**
         * A bar with 15 characters.
         */
        public static final @NonNull Component BAR_MEDIUM = miniMessage.parse(" <gradient:#03c2fc:#244379:#03c2fc><strikethrough>                           </strikethrough></gradient> ");
        /**
         * The motd.
         */
        public static final @NonNull Component MOTD_BOOK = Component.text()
                .append(BAR_MEDIUM)
                .append(Component.newline())
                .append(Component.text()
                        .append(Component.text("        "))
                        .append(SERVER_NAME)
                        .append(Component.newline())
                        .append(Component.text("        by "))
                        .append(OWNER_NAME)
                        .style(STYLE_DARK)
                )
                .append(Component.newline())
                .append(Component.newline())
                .append(Component.newline())
                .append(Component.text()
                        .append(Component.text("  Run "))
                        .append(Component.text("/play").color(COLOUR_LIGHT_BLUE))
                        .append(Component.text(" (or click)"))
                        .append(Component.newline())
                        .append(Component.text("   to open the game"))
                        .append(Component.newline())
                        .append(Component.text("           menu."))
                        .style(STYLE_COMMAND)
                        .clickEvent(ClickEvent.runCommand("/play"))
                        .hoverEvent(HoverEvent.showText(Component.text("Click to open the server menu").style(STYLE_HOVER)))
                )
                .append(Component.newline())
                .append(Component.newline())
                .append(Component.text()
                        .append(Component.text("  Website: "))
                        .append(Component.text("kaden.sh").style(STYLE_LINK))
                        .append(Component.newline())
                        .append(Component.text("Discord: "))
                        .append(Component.text("chat.ksc.sh").style(STYLE_LINK))
                )
                .append(Component.newline())
                .append(Component.newline())
                .append(BAR_MEDIUM)
                .build();
    }

    /**
     * Stores constant sound values.
     */
    public static class Sounds {

        /**
         * The default chime.
         */
        public static final @NonNull Sound DEFAULT_CHIME = Sound.sound(org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING, Sound.Source.AMBIENT, 1f, 1f);

        /**
         * Plays a Bukkit {@code Sound} to a player.
         *
         * @param player the player
         * @param sound  the sound
         */
        public static void playSoundForPlayer(final @NonNull Player player, final @NonNull Sound sound) {
            player.playSound(sound);
        }

    }

    /**
     * Stores constant books.
     */

    public static class Books {

        /**
         * Returns the intro book.
         *
         * @return the intro book
         */
        public static @NonNull Book introBook() {
            final @NonNull Book book = Book.book(Component.empty(), Component.empty(), Chat.MOTD_BOOK);

            return book;
        }

        /**
         * Shows the intro book to a player.
         *
         * @param player the player
         */
        public static void showIntroBook(final @NonNull Player player) {
            player.openBook(introBook());
        }

    }

    /**
     * Stores constant item stuff.
     */
    public static class Items {

        /**
         * The menu background item builder.
         */
        public static final @NonNull PaperItemBuilder MENU_BACKGROUND = PaperItemBuilder
                .paper(Material.GRAY_STAINED_GLASS_PANE)
                .name(Component.empty());


    }
}
