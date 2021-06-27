package dev.kscott.bluelobby.items;

import dev.kscott.bluelobby.items.effect.Effect;
import dev.kscott.bluelobby.items.effect.SpecialEffect;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class SpearItem implements Item {

    private final @NonNull Component title = Component.text()
            .append(Component.text("The Spear")
                    .color(Constants.Chat.COLOUR_RED)
                    .decoration(TextDecoration.ITALIC, false))
            .asComponent();

    private final @NonNull List<Component> description = List.of(
            Component.text("From far away.")
    );

    private final @NonNull List<Effect> effects = List.of(
            new SpecialEffect(Component.text("Devastates Absolute Terror"))
    );

    @Override
    public @NonNull Component title() {
        return this.title;
    }

    @Override
    public @NonNull List<Component> description() {
        return List.copyOf(this.description);
    }

    @Override
    public @NonNull List<Effect> effects() {
        return List.copyOf(this.effects);
    }
}
