package dev.kscott.bluelobby.items.type.fish;

import dev.kscott.bluelobby.items.rarity.Tier;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;

import java.util.List;

public class StingrayFishItem extends FishItem {

    public StingrayFishItem() {
        super(Component.text("Stingray")
                        .color(Constants.Chat.COLOUR_LIGHT_GRAY),
                "fish_stingray",
                List.of(),
                Tier.COMMON);
    }
}
