package dev.kscott.bluelobby;

import broccolai.corn.adventure.AdventureItemBuilder;
import broccolai.corn.paper.PaperItemBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.kscott.bluelobby.inject.PluginModule;
import dev.kscott.bluelobby.listeners.PlayerCrouchListener;
import dev.kscott.bluelobby.listeners.PlayerJoinListener;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * The main entrypoint for the BlueLobby.
 */
public final class LobbyPlugin extends JavaPlugin {

    /**
     * The plugin's injector.
     */
    private @MonotonicNonNull Injector injector;

    /**
     * Enables the plugin.
     */
    @Override
    public void onEnable() {
        this.injector = Guice.createInjector(new PluginModule(this));

        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerCrouchListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerJoinListener.class), this);

        this.registerGamesGuiRecipes();
    }

    /**
     * Registers the stonecutter recipes for the /games gui.
     */
    private void registerGamesGuiRecipes() {
        final @NonNull NamespacedKey gameNameKey = new NamespacedKey(this, "game_name");

        //////* Bonk game recipe *//////
        final @NonNull NamespacedKey bonkItemKey = new NamespacedKey(this, "bonk");

        final @NonNull ItemStack bonkGameItem = AdventureItemBuilder.adventure(Material.BLAZE_ROD)
                .enchant(Enchantment.DAMAGE_ALL, 1)
                .flags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_POTION_EFFECTS)
                .name(Component.text("BONK").color(TextColor.color(247, 168, 168)))
                .loreComponents(List.of(
                        Component.text("The classic game").style(Constants.Chat.STYLE_DEFAULT),
                        Component.empty(),
                        Component.text()
                                .append(Component.text("0").color(TextColor.color(245, 240, 240)))
                                .append(Component.text("/").color(TextColor.color(130, 130, 130)))
                                .append(Component.text("40").color(TextColor.color(245, 240, 240)))
                                .append(Component.text(" players bonkin'").style(Constants.Chat.STYLE_DEFAULT))
                                .asComponent()
                ))
                .build();

        final @NonNull StonecuttingRecipe bonkGameRecipe = new StonecuttingRecipe(bonkItemKey, bonkGameItem, Material.DARK_OAK_SIGN);

        /////* Recipe registration */////
        Bukkit.addRecipe(bonkGameRecipe);
    }

}
