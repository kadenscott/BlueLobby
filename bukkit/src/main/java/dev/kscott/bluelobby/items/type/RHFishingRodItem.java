package dev.kscott.bluelobby.items.type;

import dev.kscott.bluelobby.items.Item;
import dev.kscott.bluelobby.items.effect.Effect;
import dev.kscott.bluelobby.items.effect.NegativeEffect;
import dev.kscott.bluelobby.items.effect.NeutralEffect;
import dev.kscott.bluelobby.items.effect.SpecialEffect;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class RHFishingRodItem implements Item {

    private final @NonNull Component title = Component.text()
            .append(Component.text("RH's Fishing Rod")
                    .color(Constants.Chat.COLOUR_RED)
                    .decoration(TextDecoration.ITALIC, false))
            .asComponent();

    private final @NonNull List<Component> description = List.of(
            Component.text("A standard rod.")
    );

    private final @NonNull List<Effect> effects = List.of(
            new SpecialEffect(Component.text()
                    .append(Component.text("Sends players flying as you reel them in"))
                    .asComponent()
            )
    );

    @Override
    public @NonNull Component title() {
        return this.title;
    }

    @Override
    public @NonNull List<Component> description() {
        return List.copyOf(this.description);
    }

    @Override
    public @NonNull List<Effect> effects() {
        return List.copyOf(this.effects);
    }

    @Override
    public @NonNull Material material() {
        return Material.FISHING_ROD;
    }

    @Override
    public @NonNull String id() {
        return "fishing_rod";
    }

}
