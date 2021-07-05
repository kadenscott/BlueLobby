package dev.kscott.bluelobby.fishing;

import dev.kscott.bluelobby.items.Item;
import org.checkerframework.checker.nullness.qual.NonNull;

public class LootItem {

    private final @NonNull Item item;

    private final double chance;

    private final int min;

    private final int max;

    public LootItem(
            final @NonNull Item item,
            final double chance,
            final int min,
            final int max
    ) {
        this.item = item;
        this.chance = chance;
        this.min = min;
        this.max = max;
    }

    public LootItem(
            final @NonNull Item item,
            final double chance,
            final int count
    ) {
        this.item = item;
        this.chance = chance;
        this.min = count;
        this.max = count;
    }

    public @NonNull Item item() {
        return this.item;
    }

    public double chance() {
        return this.chance;
    }

    public int min() {
        return this.min;
    }

    public int max() {
        return this.max;
    }

}
