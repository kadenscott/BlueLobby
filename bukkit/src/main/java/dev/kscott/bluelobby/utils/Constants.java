package dev.kscott.bluelobby.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

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
         * The default text colour.
         */
        public static final @NonNull TextColor COLOUR_DARK_GRAY = TextColor.color(52, 52, 52);

        /**
         * The light blue text colour.
         */
        public static final @NonNull TextColor COLOUR_LIGHT_BLUE = TextColor.color(31, 197, 242);

        /**
         * The light blue text colour.
         */
        public static final @NonNull TextColor COLOUR_DARK_BLUE = TextColor.color(36, 67, 121);

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
         * The name of the server.
         */
        public static final @NonNull Component SERVER_NAME = miniMessage.parse("<#f5a5a5><bold>mc.ksc.sh</bold></#f5a5a5>");
        /**
         * A bar with 15 characters.
         */
        private static final @NonNull Component barMedium = miniMessage.parse(" <gradient:#03c2fc:#244379:#03c2fc><strikethrough>               </strikethrough></gradient> ");
        /**
         * The motd.
         */
        public static final @NonNull List<Component> MOTD = List.of(
                barMedium.append(SERVER_NAME).append(barMedium),
                Component.text("Welcome to ").style(STYLE_DEFAULT)
                        .append(SERVER_NAME)
                        .append(Component.text("! This server hosts a variety of my projects.").style(STYLE_DEFAULT)),
                Component.text("Use ").style(STYLE_DEFAULT)
                        .append(Component.text("/play").style(STYLE_COMMAND))
                        .append(Component.text(" to see the available projects.").style(STYLE_DEFAULT)),
                Component.empty(),
                Component.text("Currently hosted projects: ").style(STYLE_DEFAULT),
                Component.text("-").color(COLOUR_DARK_BLUE).append(Component.text(" Bonk").style(STYLE_LINK)),
                Component.empty(),
                Component.text("Website: ").style(STYLE_DEFAULT)
                        .append(Component.text("kaden.sh").style(STYLE_LINK))
                        .append(Component.text(" | ").color(COLOUR_DARK_GRAY))
                        .append(Component.text(" Discord: ").style(STYLE_DEFAULT))
                        .append(Component.text("chat.ksc.sh").style(STYLE_LINK)),
                barMedium.append(miniMessage.parse(" <#f5a5a5><bold>mc.ksc.sh</bold></#f5a5a5> ")).append(barMedium)
        );

    }

    /**
     * Stores constant sound values.
     */
    public static class Sounds {

        /**
         * The default chime.
         */
        public static final @NonNull Sound DEFAULT_CHIME = Sound.BLOCK_NOTE_BLOCK_CHIME;

        /**
         * Plays a Bukkit {@code Sound} to a player.
         *
         * @param player the player
         * @param sound  the sound
         */
        public static void playSoundForPlayer(final @NonNull Player player, final @NonNull Sound sound) {
            player.playSound(player.getLocation(), sound, 1f, 1f);
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
        public static @NonNull ItemStack introBook() {
            final @NonNull ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);

            final @Nullable ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.BOOK);

            final BookMeta.BookMetaBuilder bookMetaBuilder = ((BookMeta) itemMeta).toBuilder();

            bookMetaBuilder.addPage(Component.join(Component.newline(), Chat.MOTD));

            final @NonNull BookMeta bookMeta = bookMetaBuilder.build();

            itemStack.setItemMeta(bookMeta);

            return itemStack;
        }

        /**
         * Shows the intro book to a player.
         * @param player the player
         */
        public static void showIntroBook(final @NonNull Player player) {
            player.openBook(introBook());
        }

    }

}
