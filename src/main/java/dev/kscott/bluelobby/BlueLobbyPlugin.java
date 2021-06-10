package dev.kscott.bluelobby;

import dev.kscott.bluelobby.listeners.PlayerCrouchListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main entrypoint for the BlueLobby.
 */
public final class BlueLobbyPlugin extends JavaPlugin {

    /**
     * Enables the plugin.
     */
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new PlayerCrouchListener(), this);
    }

    /**
     * Disables the plugin.
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
