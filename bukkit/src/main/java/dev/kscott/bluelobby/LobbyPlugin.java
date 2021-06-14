package dev.kscott.bluelobby;

import broccolai.corn.adventure.AdventureItemBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.kscott.bluelobby.inject.CommandModule;
import dev.kscott.bluelobby.inject.PluginModule;
import dev.kscott.bluelobby.listeners.PlayerCrouchListener;
import dev.kscott.bluelobby.listeners.PlayerJoinListener;
import dev.kscott.bluelobby.listeners.PlayerOpenGuiListener;
import dev.kscott.bluelobby.listeners.ServerListPingListener;
import dev.kscott.bluelobby.menu.GameGuiRecipeHolder;
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
     * The plugin's injector.
     */
    private @MonotonicNonNull Injector injector;

    /**
     * Enables the plugin.
     */
    @Override
    public void onEnable() {
        this.injector = Guice.createInjector(
                new PluginModule(this),
                new CommandModule(this)
        );

        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerCrouchListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerJoinListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerOpenGuiListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(ServerListPingListener.class), this);

        GameGuiRecipeHolder.registerRecipes(this);
    }

}
