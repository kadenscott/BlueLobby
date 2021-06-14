package dev.kscott.bluelobby;

import broccolai.corn.adventure.AdventureItemBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.kscott.bluelobby.inject.PluginModule;
import dev.kscott.bluelobby.listeners.PlayerCrouchListener;
import dev.kscott.bluelobby.listeners.PlayerJoinListener;
import dev.kscott.bluelobby.listeners.PlayerOpenGuiListener;
import dev.kscott.bluelobby.listeners.ServerListPingListener;
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
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * The main entrypoint for the BlueLobby.
 */
public final class LobbyPlugin extends JavaPlugin {

    /**
     * The game name key.
     */
    private final @NonNull NamespacedKey gameKey;

    /**
     * The plugin's injector.
     */
    private @MonotonicNonNull Injector injector;

    /**
     * Constructs {@code LobbyPlugin}.
     */
    public LobbyPlugin() {
        this.gameKey = new NamespacedKey(this, "game");
    }

    /**
     * Enables the plugin.
     */
    @Override
    public void onEnable() {
        this.injector = Guice.createInjector(new PluginModule(this));

        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerCrouchListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerJoinListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerOpenGuiListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(ServerListPingListener.class), this);

        this.registerGamesGuiRecipes();
    }

    /**
     * Registers the stonecutter recipes for the /games gui.
     */
    private void registerGamesGuiRecipes() {

        //////* Bonk game recipe *//////
        final @NonNull NamespacedKey bonkItemKey = new NamespacedKey(this, "bonk");

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
                .data(this.gameKey, PersistentDataType.STRING, "bonk")
                .build();

        final @NonNull StonecuttingRecipe bonkGameRecipe = new StonecuttingRecipe(bonkItemKey, bonkGameItem, Material.DARK_OAK_SIGN);

        /////* Recipe registration */////
        Bukkit.addRecipe(bonkGameRecipe);
    }

    /**
     * Returns the game name key.
     *
     * @return the game name key
     */
    private @NonNull NamespacedKey gameKey() {
        return this.gameKey;
    }

}
