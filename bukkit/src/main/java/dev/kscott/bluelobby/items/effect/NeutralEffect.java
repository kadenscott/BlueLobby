package dev.kscott.bluelobby.items.effect;

import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a positive effect.
 */
public class NeutralEffect implements Effect {

    private final @NonNull List<Component> effectText;

    public NeutralEffect(final @NonNull Component effectText) {
        this.effectText = new ArrayList<>();
        this.effectText.add(effectText);
    }

    public NeutralEffect(final @NonNull List<Component> effectText) {
        this.effectText = effectText;
    }

    @Override
    public @NonNull List<Component> text() {
        if (effectText.isEmpty()) {
            return List.of();
        }

        if (effectText.size() == 1) {
            final @NonNull Component text = effectText.get(0);
            return List.of(Component.text()
                    .append(Component.text("♦")
                            .color(Constants.Chat.COLOUR_DARK_GRAY)
                            .decoration(TextDecoration.BOLD, true))
                    .append(Component.text(" ")
                            .color(Constants.Chat.COLOUR_DARK_GRAY))
                    .append(text
                            .color(Constants.Chat.COLOUR_LIGHT_GRAY))
                            .decoration(TextDecoration.ITALIC, false)
                    .asComponent());

        }

        final @NonNull List<Component> list = new ArrayList<>();

        for (int i = 0; i < this.effectText.size(); i++) {
            final TextComponent.@NonNull Builder builder = Component.text();

            if (i == 0) {
                builder.append(Component.text("♦")
                        .color(Constants.Chat.COLOUR_DARK_GRAY)
                        .decoration(TextDecoration.BOLD, true));

                builder.append(Component.text(" ")
                        .color(Constants.Chat.COLOUR_DARK_GRAY));
            }

            builder.append(Component.text()
                    .append(i == 0 ? Component.empty() : Component.text("   "))
                    .append(this.effectText.get(i).color(Constants.Chat.COLOUR_LIGHT_GRAY)));
            list.add(builder.decoration(TextDecoration.ITALIC, false).asComponent());
        }

        return list;
    }
}
