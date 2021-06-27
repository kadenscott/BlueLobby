package dev.kscott.bluelobby.items;

import broccolai.corn.paper.PaperItemBuilder;
import dev.kscott.bluelobby.items.effect.Effect;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SpearItem implements Item {

    private final @NonNull Component title = Component.text()
            .append(Component.text("The Spear")
                    .color(Constants.Chat.COLOUR_RED)
                    .decoration(TextDecoration.BOLD, true)
                    .decoration(TextDecoration.ITALIC, false))
            .asComponent();

    private final @NonNull List<Component> description = List.of(
            Component.text("From far away.")
    );

    private final @NonNull List<Effect> effects = List.of();

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
