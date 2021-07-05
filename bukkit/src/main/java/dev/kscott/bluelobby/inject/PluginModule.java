package dev.kscott.bluelobby.inject;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import dev.kscott.bluelobby.LobbyPlugin;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Provides the plugin.
 */
public final class PluginModule extends AbstractModule {

    /**
     * The plugin's reference.
     */
    private final @NonNull LobbyPlugin plugin;

    /**
     * The luckperms reference.
     */
    private final @NonNull LuckPerms luckPerms;

    /**
     * Constructs {@code PluginModule}.
     *
     * @param plugin the plugin's reference
     */
    public PluginModule(final @NonNull LobbyPlugin plugin) {
        this.plugin = plugin;
        final @Nullable RegisteredServiceProvider<LuckPerms> lpRsp = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

        if (lpRsp == null) {
            throw new NullPointerException("The LuckPerms service provider was null.");
        }

        this.luckPerms = lpRsp.getProvider();
    }

    @Override
    public void configure() {
        this.bind(LobbyPlugin.class).toInstance(this.plugin);
        this.bind(JavaPlugin.class).toInstance(this.plugin);

        this.bind(World.class).annotatedWith(Names.named("lobbyWorld")).toInstance(this.plugin.getServer().getWorld("world"));

        this.bind(LuckPerms.class).toInstance(this.luckPerms);
    }
}
