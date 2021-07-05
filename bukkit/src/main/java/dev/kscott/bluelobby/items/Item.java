package dev.kscott.bluelobby.items;

import broccolai.corn.paper.PaperItemBuilder;
import dev.kscott.bluelobby.items.effect.Effect;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface that represents a custom item in Minecraft.
 */
public interface Item {

    /**
     * The NamespacedKey of "item:id".
     */
    @NonNull NamespacedKey KEY_ITEM_ID = NamespacedKey.fromString("item:id", null);

    /**
     * Returns the title.
     *
     * @return the title
     */
    @NonNull Component title();

    /**
     * Returns the string id of this item.
     *
     * @return the id
     */
    @NonNull String id();

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
            lore.add(Component.empty());

            // TODO sort effects by type (in order: positive, negative, special)

            for (final @NonNull Effect effect : effects) {
                lore.addAll(effect.text());
            }
        }

        final @NonNull List<Component> description = this.description();

        if (description.size() != 0) {
            lore.add(Component.empty());

            for (final @NonNull Component text : description) {
                lore.add(Component.text()
                        .append(text)
                        .color(Constants.Chat.COLOUR_ORANGE)
                        .decoration(TextDecoration.ITALIC, true)
                        .asComponent());
            }
        }

        if (this instanceof Tiered tiered) {
            lore.add(Component.empty());

            lore.add(Component.text()
                    .append(Component.text("› ")
                            .color(Constants.Chat.COLOUR_LIGHT_GRAY))
                    .append(tiered.tier().title())
                    .asComponent()
                    .decoration(TextDecoration.ITALIC, false));
        }

        final @NonNull ItemStack itemStack = PaperItemBuilder.paper(this.material())
                .name(this.title())
                .loreComponents(lore)
                .flags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DYE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_UNBREAKABLE)
                .data(KEY_ITEM_ID, PersistentDataType.STRING, this.id())
                .build();


        if (itemStack.hasItemMeta()) {
            final @NonNull ItemMeta meta = itemStack.getItemMeta();
            meta.setUnbreakable(true);
            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }

    /**
     * Returns this item's effects.
     *
     * @return the effect
     */
    @NonNull List<Effect> effects();

    /**
     * Returns the material of this item.
     *
     * @return the material
     */
    @NonNull Material material();

}
