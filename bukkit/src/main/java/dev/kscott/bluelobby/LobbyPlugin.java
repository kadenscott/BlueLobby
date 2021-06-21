package dev.kscott.bluelobby;

import com.google.inject.Injector;
import dev.kscott.bluelobby.utils.LobbyPlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

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
        System.out.println(Bukkit.getServer());
//
//        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerJoinListener.class), this);
//        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerOpenGuiListener.class), this);
//        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(ServerListPingListener.class), this);
//
//        this.injector.getInstance(CommandService.class);

        final @NonNull LobbyPlaceholderExpansion placeholderExpansion = new LobbyPlaceholderExpansion(this);
        final @Nullable Plugin placeholderApiPlugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");

        if (placeholderApiPlugin != null && placeholderApiPlugin.isEnabled()) {
            placeholderExpansion.register();
        }
//        this.injector.getInstance(HologramManager.class).loadHolograms();

//        GameGuiRecipeHolder.registerRecipes(this);
    }

}
