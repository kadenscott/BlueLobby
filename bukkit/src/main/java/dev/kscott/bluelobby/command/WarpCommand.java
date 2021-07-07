package dev.kscott.bluelobby.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import org.incendo.interfaces.paper.PlayerViewer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

/**
 * The /warp command class.
 */
public class WarpCommand implements BaseCommand {

    /**
     * JavaPlugin reference.
     */
    private final @NonNull JavaPlugin plugin;


    /**
     * Constructs {@code WarpCommand}.
     *
     * @param plugin the plugin reference
     */
    @Inject
    public WarpCommand(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Registers the /warp command.
     *
     * @param manager CommandManager to register with
     */
    @Override
    public void register(final @NonNull CommandManager<@NonNull CommandSender> manager) {
        final Command.Builder<CommandSender> builder = manager.commandBuilder("warp", "warps");

        manager.command(builder.handler(this::handleWarpCommand));
    }

    /**
     * Handles the /warp command.
     *
     * @param context command context
     */
    private void handleWarpCommand(final @NonNull CommandContext<CommandSender> context) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final @NonNull CommandSender sender = context.getSender();

                if (sender instanceof Player player) {
                }
            }
        }.runTask(this.plugin);
    }
}
