package dev.kscott.bluelobby.items.type.fish;

import broccolai.corn.paper.PaperItemBuilder;
import dev.kscott.bluelobby.items.Item;
import dev.kscott.bluelobby.items.Tiered;
import dev.kscott.bluelobby.items.effect.Effect;
import dev.kscott.bluelobby.items.effect.NeutralEffect;
import dev.kscott.bluelobby.items.effect.PositiveEffect;
import dev.kscott.bluelobby.items.rarity.Tier;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TheWavyEffectItem implements Item, Tiered {

    private final @NonNull Component title = Component.text()
            .append(Component.text()
                    .append(MiniMessage.get().parse("<gradient:#c20e0e:#f7981b>The Wavy Effect</gradient>"))
                    .color(Constants.Chat.COLOUR_RED)
                    .decoration(TextDecoration.ITALIC, false)
                    .decoration(TextDecoration.BOLD, true))
            .asComponent();

    private final @NonNull List<Component> description = List.of(
            Component.text("A well kept, sturdy fishing rod.")
    );

    private final @NonNull List<Effect> effects = List.of(
            new PositiveEffect(List.of(Component.text("Catches fish faster, based on how"),
                    Component.text("many fish you've caught in total."))),
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
        return "professional_rod_1";
    }

    @Override
    public @NonNull Tier tier() {
        return Tier.EPIC;
    }

    /**
     * Returns the {@link ItemStack}.
     *
     * @return the {@link ItemStack}
     */
    @Override
    public @NonNull ItemStack itemStack() {
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

        lore.add(Component.empty());

        lore.add(Component.text()
                .append(Component.text("› ")
                        .color(Constants.Chat.COLOUR_LIGHT_GRAY))
                .append(tier().title())
                .asComponent()
                .decoration(TextDecoration.ITALIC, false));

        final @NonNull ItemStack itemStack = PaperItemBuilder.paper(this.material())
                .name(this.title())
                .loreComponents(lore)
                .enchant(Enchantment.DAMAGE_ALL, 1)
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

}
