package dev.kscott.bluelobby.lobby;

import dev.kscott.bluelobby.LobbyPlugin;
import dev.kscott.bluelobby.location.LocationRegistry;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Manages holograms.
 */
@Singleton
public class HologramManager {

    /**
     * The plugin.
     */
    private final @NonNull LobbyPlugin plugin;

    /**
     * The location registry.
     */
    private final @NonNull LocationRegistry locationRegistry;

    /**
     * Constructs HologramManager.
     *
     * @param plugin the plugin
     * @param locationRegistry the location registry
     */
    @Inject
    public HologramManager(
            final @NonNull LobbyPlugin plugin,
            final @NonNull LocationRegistry locationRegistry
    ) {
        this.locationRegistry = locationRegistry;
        this.plugin = plugin;
    }

    public void loadHolograms() {
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            plugin.getLogger().severe("Failed to load holograms - HolographicDisplays is not installed.");
            return;
        }
    }

}
