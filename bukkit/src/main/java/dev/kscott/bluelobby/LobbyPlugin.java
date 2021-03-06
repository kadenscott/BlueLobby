package dev.kscott.bluelobby;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.kscott.bluelobby.area.LocationService;
import dev.kscott.bluelobby.command.CommandService;
import dev.kscott.bluelobby.fishing.FishingService;
import dev.kscott.bluelobby.games.cannon.CannonService;
import dev.kscott.bluelobby.games.targets.TargetGameService;
import dev.kscott.bluelobby.holograms.HologramManager;
import dev.kscott.bluelobby.inject.CommandModule;
import dev.kscott.bluelobby.inject.PluginModule;
import dev.kscott.bluelobby.listeners.BowShootListener;
import dev.kscott.bluelobby.listeners.EntityDeathListener;
import dev.kscott.bluelobby.listeners.PlayerJoinListener;
import dev.kscott.bluelobby.listeners.ServerListPingListener;
import dev.kscott.bluelobby.lobby.CrystalService;
import dev.kscott.bluelobby.menu.MenuListener;
import dev.kscott.bluelobby.utils.LobbyPlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.interfaces.paper.PaperInterfaceListeners;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The main entrypoint for the BlueLobby.
 */
public final class LobbyPlugin extends JavaPlugin {

    private @MonotonicNonNull Injector injector;
    private final @NonNull String version;
    private final @NonNull String name;

    public LobbyPlugin() {
        @NonNull String version = "1.3.3.7";
        @NonNull String name = "Unidentified Server";
        try (InputStream input = new FileInputStream("game.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            version = prop.getProperty("server.version", "1.3.3.7");
            name = prop.getProperty("server.name", "Unidentified Server");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.version = version;
        this.name = name;
    }

    /**
     * Enables the plugin.
     */
    @Override
    public void onEnable() {
        this.injector = Guice.createInjector(
                new PluginModule(this),
                new CommandModule(this)
        );

        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerJoinListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(ServerListPingListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(FishingService.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(BowShootListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(EntityDeathListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(MenuListener.class), this);

        PaperInterfaceListeners.install(this);

        this.injector.getInstance(CommandService.class);

        final @NonNull LobbyPlaceholderExpansion placeholderExpansion = new LobbyPlaceholderExpansion(this);
        final @Nullable Plugin placeholderApiPlugin = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");

        if (placeholderApiPlugin != null && placeholderApiPlugin.isEnabled()) {
            placeholderExpansion.register();
        }

        this.injector.getInstance(HologramManager.class).loadHolograms();
        this.injector.getInstance(CrystalService.class);
        this.injector.getInstance(CannonService.class);

        final @NonNull LocationService locationService = this.injector.getInstance(LocationService.class);

        this.setupGames();
    }

    private void setupGames() {
        // Targets
        final @NonNull TargetGameService targets = this.injector.getInstance(TargetGameService.class);
        targets.start();
        this.getServer().getPluginManager().registerEvents(targets, this);
    }

    public @NonNull String name() {
        return this.name;
    }

    public @NonNull String version() {
        return this.version;
    }

}
