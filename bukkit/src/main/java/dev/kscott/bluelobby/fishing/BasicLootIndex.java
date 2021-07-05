package dev.kscott.bluelobby.fishing;

import dev.kscott.bluelobby.items.Item;
import dev.kscott.bluelobby.items.type.ArrowItem;
import dev.kscott.bluelobby.items.type.GrapplingBowItem;
import dev.kscott.bluelobby.items.type.fish.TheWavyEffectItem;
import dev.kscott.bluelobby.items.type.fish.SalmonFishItem;
import dev.kscott.bluelobby.items.type.fish.StingrayFishItem;
import dev.kscott.bluelobby.items.type.fish.TroutFishItem;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The basic loot index.
 */
public class BasicLootIndex implements LootIndex {

    private final @NonNull Random random;

    /**
     * Stores what items are in this index and their chances to drop.
     */
    private final @NonNull List<LootItem> lootSet;

    /**
     * Constructs {@code BasicLootIndex}.
     */
    public BasicLootIndex() {
        this.lootSet = new ArrayList<>();
        this.random = new Random();

        this.lootSet.add(new LootItem(new TroutFishItem(), 1, 1));
        this.lootSet.add(new LootItem(new SalmonFishItem(), 10, 1));
        this.lootSet.add(new LootItem(new StingrayFishItem(), 10, 1));

        this.lootSet.add(new LootItem(new TheWavyEffectItem(), 0.5, 1));

        this.lootSet.add(new LootItem(new ArrowItem(), 5, 3, 6));
        this.lootSet.add(new LootItem(new GrapplingBowItem(), 3, 1));
    }

    @Override
    public @NonNull Set<LootItem> index() {
        return new HashSet<>(this.lootSet);
    }

    @Override
    public @NonNull Map<Item, Integer> roll() {
        double cumulative = 0;

        for (final @NonNull LootItem lootItem : this.lootSet) {
            cumulative = cumulative + lootItem.chance();
        }

        double randomValue = ThreadLocalRandom.current().nextDouble(0, cumulative);

        double current = 0;

        final @NonNull Map<Item, Integer> itemMap = new HashMap<>();

        for (final @NonNull LootItem lootItem : this.lootSet) {
            current = current + lootItem.chance();

            if (current > randomValue) {
                itemMap.put(lootItem.item(), 1);
                break;
            }
        }

        return itemMap;
    }
}
