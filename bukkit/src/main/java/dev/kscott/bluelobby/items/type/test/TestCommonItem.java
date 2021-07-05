package dev.kscott.bluelobby.items.type.test;

import dev.kscott.bluelobby.items.Item;
import dev.kscott.bluelobby.items.Tiered;
import dev.kscott.bluelobby.items.effect.*;
import dev.kscott.bluelobby.items.rarity.Tier;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class TestCommonItem implements Item, Tiered {
    @Override
    public @NonNull Component title() {
        return Component.text("Test");
    }

    @Override
    public @NonNull String id() {
        return "test_1";
    }

    @Override
    public @NonNull List<Component> description() {
        return List.of(Component.text("Desc test 1"), Component.text("Desc test 2"));
    }

    @Override
    public @NonNull List<Effect> effects() {
        return List.of(
                new PositiveEffect(Component.text("A positive test.")),
                new NeutralEffect(Component.text("A neutral test.")),
                new NegativeEffect(Component.text("A negative test.")),
                new SpecialEffect(Component.text("A special test."))
        );
    }

    @Override
    public @NonNull Material material() {
        return Material.PAPER;
    }

    @Override
    public @NonNull Tier tier() {
        return Tier.COMMON;
    }
}
