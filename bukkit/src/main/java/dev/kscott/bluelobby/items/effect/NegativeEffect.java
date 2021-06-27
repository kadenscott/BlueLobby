package dev.kscott.bluelobby.items.effect;

import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a positive effect.
 */
public class NegativeEffect implements Effect {

    private final @NonNull Component effectText;

    public NegativeEffect(final @NonNull Component effectText) {
        this.effectText = effectText;
    }

    @Override
    public @NonNull Component text() {
        return Component.text()
                .append(Component.text("⭐")
                        .color(Constants.Chat.COLOUR_DARK_RED)
                        .decoration(TextDecoration.BOLD, true))
                .append(Component.text(" ")
                        .color(Constants.Chat.COLOUR_DARK_GRAY))
                .append(this.effectText
                        .color(Constants.Chat.COLOUR_RED))
                .asComponent();
    }
}
