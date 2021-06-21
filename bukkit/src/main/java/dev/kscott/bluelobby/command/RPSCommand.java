package dev.kscott.bluelobby.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import dev.kscott.bluelobby.games.rps.RPSManager;
import dev.kscott.bluelobby.menu.core.MenuGui;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

public class RPSCommand implements BaseCommand {
    /**
     * JavaPlugin reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The RPSManager.
     */
    private final @NonNull RPSManager rpsManager;

    /**
     * Constructs {@code MenuCommand}.
     *
     * @param plugin the plugin reference
     */
    @Inject
    public RPSCommand(
            final @NonNull JavaPlugin plugin,
            final @NonNull RPSManager rpsManager
    ) {
        this.plugin = plugin;
        this.rpsManager = rpsManager;
    }

    /**
     * Registers the /menu command.
     *
     * @param manager CommandManager to register with
     */
    @Override
    public void register(final @NonNull CommandManager<@NonNull CommandSender> manager) {
        final Command.Builder<CommandSender> builder = manager.commandBuilder("rps");

        manager.command(builder.handler(this::handleMainCommand));
    }

    /**
     * Handles the /menu command.
     *
     * @param context command context
     */
    private void handleMainCommand(final @NonNull CommandContext<CommandSender> context) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final @NonNull CommandSender sender = context.getSender();

                if (sender instanceof Player player) {
                    final @NonNull Gui gui = MenuGui.create();

                    // TODO send opening message

                    gui.show(player);
                }
            }
        }.runTask(this.plugin);
    }
}
