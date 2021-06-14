package dev.kscott.bluelobby.menu;

import broccolai.corn.adventure.AdventureItemBuilder;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the recipes for the /game gui.
 */
public interface GameGuiRecipeHolder {

    /**
     * Registers the recipes for the /game menu.
     * <p>
     * Called when the server first starts.
     *
     * @param plugin the plugin reference
     */
    static void registerRecipes(final @NonNull JavaPlugin plugin) {
        final @NonNull NamespacedKey gameKey = new NamespacedKey(plugin, "game");

        final @NonNull List<StonecuttingRecipe> recipes = new ArrayList<>();

        //////* Bonk game recipe *//////
        final @NonNull NamespacedKey bonkItemKey = new NamespacedKey(plugin, "bonk");

        final @NonNull ItemStack bonkGameItem = AdventureItemBuilder.adventure(Material.BLAZE_ROD)
                .enchant(Enchantment.DAMAGE_ALL, 1)
                .flags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_POTION_EFFECTS)
                .name(Component.text("BONK").color(TextColor.color(255, 231, 166))
                        .decoration(TextDecoration.ITALIC, false)
                        .decoration(TextDecoration.BOLD, true)
                )
                .loreComponents(List.of(
                        Component.text("The classic game").style(Constants.Chat.STYLE_DEFAULT),
                        Component.empty(),
                        Component.text()
                                .append(Component.text("0").color(TextColor.color(245, 240, 240)).decoration(TextDecoration.ITALIC, false))
                                .append(Component.text("/").color(TextColor.color(130, 130, 130)).decoration(TextDecoration.ITALIC, false))
                                .append(Component.text("40").color(TextColor.color(245, 240, 240)).decoration(TextDecoration.ITALIC, false))
                                .append(Component.text(" players bonkin'").style(Constants.Chat.STYLE_DEFAULT))
                                .asComponent()
                ))
                .data(gameKey, PersistentDataType.STRING, "bonk")
                .build();

        final @NonNull StonecuttingRecipe bonkGameRecipe = new StonecuttingRecipe(bonkItemKey, bonkGameItem, Material.DARK_OAK_SIGN);

        // Add recipes to list
        recipes.add(bonkGameRecipe);

        recipes.forEach(Bukkit::addRecipe);
    }

}
