package dev.kscott.bluelobby.items;

import dev.kscott.bluelobby.items.rarity.Tier;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an item that has a tier.
 */
public interface Tiered {

    /**
     * Returns the item's tier.
     *
     * @return the tier
     */
    @NonNull Tier tier();
    
}
