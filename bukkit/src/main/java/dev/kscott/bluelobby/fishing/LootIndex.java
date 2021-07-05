package dev.kscott.bluelobby.fishing;

import dev.kscott.bluelobby.items.Item;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;
import java.util.Set;

/**
 * A loot index.
 */
public interface LootIndex {

    /**
     * Returns a set containing all loot items in this index.
     *
     * @return the index map
     */
    @NonNull Set<LootItem> index();

    /**
     * Returns a map containing the result of a roll of the table. The key is an item and the value is how many items.
     *
     * @return the returned items
     */
    @NonNull Map<Item, Integer> roll();
}
