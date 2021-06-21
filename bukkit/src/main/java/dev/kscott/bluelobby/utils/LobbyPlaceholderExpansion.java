package dev.kscott.bluelobby.utils;

import dev.kscott.bluelobby.LobbyPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

/**
 * Registers lobby-related placeholder expansions.
 */
public class LobbyPlaceholderExpansion extends PlaceholderExpansion {

    /**
     * The plugin.
     */
    private final @NonNull LobbyPlugin plugin;

    /**
     * Constructs {@code LobbyPlaceholderExpansion}.
     *
     * @param plugin the plugin
     */
    @Inject
    public LobbyPlaceholderExpansion(final @NonNull LobbyPlugin plugin) {
        this.plugin = plugin;

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.register();
        }
    }

    @Override
    public @NotNull String getIdentifier() {
        return "bluelobby";
    }

    @Override
    public @NotNull String getAuthor() {
        return "bluely <kscottdev@gmail.com>";
    }

    @Override
    public @NotNull String getVersion() {
        return null;
    }
}
