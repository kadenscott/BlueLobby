package dev.kscott.bluelobby.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
         * A bar with 15 characters.
         */
        private static final @NonNull Component barMedium = miniMessage.parse("<gradient:#03c2fc:#0c7cc2:#03c2fc>               <strikethrough>          </strikethrough></gradient>");

        public static final @NonNull Component[] motd = {
                barMedium.append(miniMessage.parse(" <#a5bcc9><bold>mc.ksc.sh</bold></#a5bcc9> ")).append(barMedium),
                Component.empty(),
                barMedium.append(miniMessage.parse(" <#a5bcc9><bold>mc.ksc.sh</bold></#a5bcc9> ")).append(barMedium)
        };

    }

}
