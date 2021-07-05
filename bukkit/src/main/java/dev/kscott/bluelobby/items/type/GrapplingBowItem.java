package dev.kscott.bluelobby.items.type;

import dev.kscott.bluelobby.items.Item;
import dev.kscott.bluelobby.items.Tiered;
import dev.kscott.bluelobby.items.effect.Effect;
import dev.kscott.bluelobby.items.effect.NeutralEffect;
import dev.kscott.bluelobby.items.rarity.Tier;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.Buildable;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class GrapplingBowItem implements Item, Tiered {
    @Override
    public @NonNull Component title() {
        return Component.text("Grappling Bow")
                .style(Constants.Chat.STYLE_DEFAULT);
    }

    @Override
    public @NonNull String id() {
        return "grappling_bow";
    }

    @Override
    public @NonNull List<Component> description() {
        return List.of();
    }

    @Override
    public @NonNull List<Effect> effects() {
        return List.of(
                new NeutralEffect(Component.text("Shoot to ride an arrow through the sky"))
        );
    }

    @Override
    public @NonNull Material material() {
        return Material.BOW;
    }

    @Override
    public @NonNull Tier tier() {
        return Tier.UNCOMMON;
    }
}
