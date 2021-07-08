package dev.kscott.bluelobby;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.kscott.bluelobby.area.LocationService;
import dev.kscott.bluelobby.command.CommandService;
import dev.kscott.bluelobby.inject.CommandModule;
import dev.kscott.bluelobby.inject.PluginModule;
import dev.kscott.bluelobby.fishing.FishingService;
import dev.kscott.bluelobby.listeners.BowShootListener;
import dev.kscott.bluelobby.listeners.PlayerJoinListener;
import dev.kscott.bluelobby.listeners.ServerListPingListener;
import dev.kscott.bluelobby.holograms.HologramManager;
import dev.kscott.bluelobby.lobby.CrystalService;
import dev.kscott.bluelobby.menu.MenuListener;
import dev.kscott.bluelobby.utils.LobbyPlaceholderExpansion;
import org.incendo.interfaces.paper.PaperInterfaceListeners;
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
        try {
            this.injector = Guice.createInjector(
                    new PluginModule(this),
                    new CommandModule(this)
            );

            this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerJoinListener.class), this);
            this.getServer().getPluginManager().registerEvents(this.injector.getInstance(ServerListPingListener.class), this);
            this.getServer().getPluginManager().registerEvents(this.injector.getInstance(FishingService.class), this);
            this.getServer().getPluginManager().registerEvents(this.injector.getInstance(BowShootListener.class), this);
            this.getServer().getPluginManager().registerEvents(new PaperInterfaceListeners(this), this);
            this.getServer().getPluginManager().registerEvents(this.injector.getInstance(MenuListener.class), this);

            this.injector.getInstance(CommandService.class);

            final @NonNull LobbyPlaceholderExpansion placeholderExpansion = new LobbyPlaceholderExpansion(this);
            final @Nullable Plugin placeholderApiPlugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");

            if (placeholderApiPlugin != null && placeholderApiPlugin.isEnabled()) {
                placeholderExpansion.register();
            }

            this.injector.getInstance(HologramManager.class).loadHolograms();
            this.injector.getInstance(CrystalService.class);

            final @NonNull LocationService locationService = this.injector.getInstance(LocationService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
