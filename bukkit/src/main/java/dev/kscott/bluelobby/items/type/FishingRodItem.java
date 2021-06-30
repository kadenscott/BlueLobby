package dev.kscott.bluelobby.items.type;

import dev.kscott.bluelobby.items.Item;
import dev.kscott.bluelobby.items.effect.Effect;
import dev.kscott.bluelobby.items.effect.NeutralEffect;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class FishingRodItem implements Item {

    private final @NonNull Component title = Component.text()
            .append(Component.text("Fishing Rod")
                    .color(Constants.Chat.COLOUR_LIGHT_GRAY)
                    .decoration(TextDecoration.ITALIC, false))
            .asComponent();

    private final @NonNull List<Component> description = List.of(
            Component.text("A standard rod")
    );

    private final @NonNull List<Effect> effects = List.of(
            new NeutralEffect(Component.text()
                    .append(Component.text("Press "))
                    .append(Component.keybind().keybind("key.use")
                            .color(Constants.Chat.COLOUR_LIGHT_BLUE))
                    .append(Component.text(" to cast a bobber"))
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
