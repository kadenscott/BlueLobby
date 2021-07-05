package dev.kscott.bluelobby.items.rarity;

import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Holds all the item tiers.
 */
public enum Tier {
    /**
     * The common tier.
     * Gray
     */
    COMMON("common", Component.text("COMMON")
            .color(Constants.Chat.COLOUR_LIGHT_GRAY)),

    /**
     * The uncommon tier.
     * Yellow
     */
    UNCOMMON("uncommon", Component.text("UNCOMMON")
            .color(Constants.Chat.COLOUR_YELLOW)),

    /**
     * The rare tier.
     * Orange
     */
    RARE("rare", MiniMessage.get().parse("<gradient:#e08422:#dbc763>RARE</gradient>")
            .decoration(TextDecoration.BOLD, true)),

    /**
     * The epic tier.
     * Purple
     */
    EPIC("epic", MiniMessage.get().parse("<gradient:#9c57c9:#db7fcd>EPIC</gradient>")
            .decoration(TextDecoration.BOLD, true)),

    /**
     * Rainbow
     */
    LEGENDARY("legendary", MiniMessage.get().parse("<rainbow>LEGENDARY</rainbow>")
            .decoration(TextDecoration.BOLD, true));

    /**
     * The string text of this tier.
     */
    private final @NonNull String text;

    /**
     * The component text of this tier.
     */
    private final @NonNull Component title;

    /**
     * Constructs {@code Tier}.
     *
     * @param text the text
     * @param title the title
     */
    Tier(final @NonNull String text,
         final @NonNull Component title) {
        this.text = text;
        this.title = title;
    }

    /**
     * Returns the text.
     *
     * @return the text
     */
    public @NonNull String text() {
        return this.text;
    }

    /**
     * Returns the title.
     *
     * @return the title
     */
    public @NonNull Component title() {
        return this.title;
    }
}
