package dev.kscott.bluelobby;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.kscott.bluelobby.command.CommandService;
import dev.kscott.bluelobby.inject.CommandModule;
import dev.kscott.bluelobby.inject.PluginModule;
import dev.kscott.bluelobby.listeners.PlayerJoinListener;
import dev.kscott.bluelobby.listeners.PlayerOpenGuiListener;
import dev.kscott.bluelobby.listeners.ServerListPingListener;
import dev.kscott.bluelobby.lobby.HologramManager;
import dev.kscott.bluelobby.menu.GameGuiRecipeHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

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

        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerJoinListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(PlayerOpenGuiListener.class), this);
        this.getServer().getPluginManager().registerEvents(this.injector.getInstance(ServerListPingListener.class), this);

        this.injector.getInstance(CommandService.class);

//        this.injector.getInstance(HologramManager.class).loadHolograms();

        GameGuiRecipeHolder.registerRecipes(this);
    }

}
