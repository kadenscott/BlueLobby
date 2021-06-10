package dev.kscott.bluelobby.inject;

import com.google.inject.AbstractModule;
import dev.kscott.bluelobby.LobbyPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides the plugin.
 */
public final class PluginModule extends AbstractModule {

    /**
     * The plugin's reference.
     */
    private final @NonNull LobbyPlugin plugin;

    /**
     * Constructs {@code PluginModule}.
     *
     * @param plugin the plugin's reference
     */
    public PluginModule(final @NonNull LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void configure() {
        this.bind(LobbyPlugin.class).toInstance(this.plugin);
        this.bind(JavaPlugin.class).toInstance(this.plugin);
    }

}
