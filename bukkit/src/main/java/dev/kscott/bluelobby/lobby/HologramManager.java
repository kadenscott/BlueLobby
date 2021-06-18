package dev.kscott.bluelobby.lobby;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import dev.kscott.bluelobby.LobbyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Named;
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
     * The lobby world.
     */
    private final @NonNull World lobbyWorld;

    /**
     * Constructs HologramManager.
     *
     * @param plugin     the plugin
     * @param lobbyWorld the lobby world
     */
    @Inject
    public HologramManager(
            final @NonNull LobbyPlugin plugin,
            final @NonNull @Named("lobbyWorld") World lobbyWorld
    ) {
        this.lobbyWorld = lobbyWorld;
        this.plugin = plugin;
    }

    public void loadHolograms() {
        if (!Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
            plugin.getLogger().severe("Failed to load holograms - HolographicDisplays is not installed.");
            return;
        }

        Hologram hologram = HologramsAPI.createHologram(plugin, new Location(lobbyWorld, 0.5, 68, -4.5));
        hologram.appendTextLine("test");
    }

}
