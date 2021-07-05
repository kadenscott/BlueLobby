package dev.kscott.bluelobby.items.type;

import dev.kscott.bluelobby.items.Item;
import dev.kscott.bluelobby.items.Tiered;
import dev.kscott.bluelobby.items.effect.Effect;
import dev.kscott.bluelobby.items.rarity.Tier;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.CompactNumberFormat;
import java.util.List;

public class ArrowItem implements Item, Tiered {
    @Override
    public @NonNull Component title() {
        return Component.text("Arrow")
                .style(Constants.Chat.STYLE_DEFAULT);
    }

    @Override
    public @NonNull String id() {
        return "arrow";
    }

    @Override
    public @NonNull List<Component> description() {
        return List.of(Component.text("The arrowhead has a"),
                Component.text("strange engraving."));
    }

    @Override
    public @NonNull List<Effect> effects() {
        return List.of();
    }

    @Override
    public @NonNull Material material() {
        return Material.ARROW;
    }

    @Override
    public @NonNull Tier tier() {
        return Tier.COMMON;
    }
}
