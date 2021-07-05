package dev.kscott.bluelobby.items.type.fish;

import dev.kscott.bluelobby.items.Item;
import dev.kscott.bluelobby.items.Tiered;
import dev.kscott.bluelobby.items.effect.Effect;
import dev.kscott.bluelobby.items.rarity.Tier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class FishItem implements Item, Tiered {

    private final @NonNull Component title;

    private final @NonNull String id;

    private final @NonNull List<Component> description;

    private final @NonNull List<Effect> effects;

    private final @NonNull Material material;

    private final @NonNull Tier tier;

    public FishItem(final @NonNull Component title,
                    final @NonNull String id,
                    final @NonNull List<Component> description,
                    final @NonNull Tier tier) {
        this.title = title.decoration(TextDecoration.ITALIC, false);
        this.id = id;
        this.material = Material.TROPICAL_FISH;
        this.effects = List.of();
        this.description = description;
        this.tier = tier;
    }
    public FishItem(final @NonNull Component title,
                    final @NonNull String id,
                    final @NonNull List<Component> description,
                    final @NonNull Tier tier,
                    final @NonNull Material material) {
        this.title = title.decoration(TextDecoration.ITALIC, false);
        this.id = id;
        this.effects = List.of();
        this.description = description;
        this.tier = tier;
        this.material = material;
    }


    @Override
    public @NonNull Component title() {
        return this.title;
    }

    @Override
    public @NonNull String id() {
        return this.id;
    }

    @Override
    public @NonNull List<Component> description() {
        return this.description;
    }

    @Override
    public @NonNull List<Effect> effects() {
        return this.effects;
    }

    @Override
    public @NonNull Material material() {
        return this.material;
    }

    /**
     * Returns the tier.
     *
     * @return the tier
     */
    public @NonNull Tier tier() {
        return this.tier;
    }
}
