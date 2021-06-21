package dev.kscott.bluelobby.utils;

import dev.kscott.bluelobby.LobbyPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
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
        return "1.0.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    /**
     * Parses the placeholder and returns the requested value.
     *
     * @param player the player
     * @param identifier the identifier
     * @return the value
     */
    @Override
    public @Nullable String onPlaceholderRequest(final @NonNull Player player, final @NonNull String identifier) {
        return switch (identifier) {
            case "server_name" -> {
                yield LegacyComponentSerializer.legacySection().serialize(Constants.Chat.SERVER_NAME);
            }
            case "owner_name" -> {
                yield LegacyComponentSerializer.legacySection().serialize(Constants.Chat.OWNER_NAME);
            }
            default -> {
                yield null;
            }
        };
    }
}
