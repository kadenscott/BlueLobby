package dev.kscott.bluelobby.items;

import broccolai.corn.paper.PaperItemBuilder;
import dev.kscott.bluelobby.items.effect.Effect;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface that represents a custom item in Minecraft.
 */
public interface Item {

    /**
     * Returns the title.
     *
     * @return the title
     */
    @NonNull Component title();

    /**
     * Returns the description.
     *
     * @return the description
     */
    @NonNull List<Component> description();

    /**
     * Returns the {@link ItemStack}.
     *
     * @return the {@link ItemStack}
     */
    default @NonNull ItemStack itemStack() {
        final @NonNull List<Component> lore = new ArrayList<>();

        final @NonNull List<Effect> effects = this.effects();

        if (effects.size() != 0) {
            lore.add(Component.text("Effects:")
                    .style(Constants.Chat.STYLE_DEFAULT));

            // TODO sort effects by type (in order: positive, negative, special)

            for (final @NonNull Effect effect : effects) {
                lore.add(Component.text()
                        .append(effect.text())
                        .decoration(TextDecoration.ITALIC, false)
                        .asComponent());
            }
        }

        lore.add(Component.empty());

        final @NonNull List<Component> description = this.description();

        if (description.size() != 0) {
            for (final @NonNull Component text : description) {
                lore.add(Component.text()
                        .append(text)
                        .color(Constants.Chat.COLOUR_ORANGE)
                        .decoration(TextDecoration.ITALIC, true)
                        .asComponent());
            }
        }

        return PaperItemBuilder.paper(Material.TRIDENT)
                .name(this.title())
                .loreComponents(lore)
                .build();
    }

    /**
     * Returns this item's effects.
     *
     * @return the effect
     */
    @NonNull List<Effect> effects();

}
