package dev.kscott.bluelobby.items.type.fish;

import dev.kscott.bluelobby.items.rarity.Tier;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

public class SalmonFishItem extends FishItem {

    public SalmonFishItem() {
        super(Component.text("Salmon")
                        .color(Constants.Chat.COLOUR_PINK),
                "fish_salmon",
                List.of(),
                Tier.COMMON,
                Material.SALMON);
    }
}
