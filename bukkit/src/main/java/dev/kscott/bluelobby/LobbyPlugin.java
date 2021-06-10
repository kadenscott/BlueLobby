package dev.kscott.bluelobby;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.kscott.bluelobby.inject.PluginModule;
import dev.kscott.bluelobby.listeners.PlayerCrouchListener;
import dev.kscott.bluelobby.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

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
    }

}
