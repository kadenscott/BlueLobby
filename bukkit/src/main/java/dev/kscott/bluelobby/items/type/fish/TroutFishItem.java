package dev.kscott.bluelobby.items.type.fish;

import dev.kscott.bluelobby.items.rarity.Tier;
import net.kyori.adventure.text.Component;

import java.util.List;

public class TroutFishItem extends FishItem {

    public TroutFishItem() {
        super(Component.text("Trout"), "fish_trout", List.of(), Tier.LEGENDARY);
    }
}
