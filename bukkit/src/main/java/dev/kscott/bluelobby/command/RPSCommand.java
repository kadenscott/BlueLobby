package dev.kscott.bluelobby.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.bukkit.parsers.PlayerArgument;
import cloud.commandframework.context.CommandContext;
import dev.kscott.bluelobby.games.rps.RPSService;
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
    private final @NonNull RPSService rpsManager;

    /**
     * Constructs {@code RPSCommand}.
     *
     * @param plugin the plugin reference
     */
    @Inject
    public RPSCommand(
            final @NonNull JavaPlugin plugin,
            final @NonNull RPSService rpsManager
    ) {
        this.plugin = plugin;
        this.rpsManager = rpsManager;
    }

    /**
     * Registers the /rps command.
     *
     * @param manager CommandManager to register with
     */
    @Override
    public void register(final @NonNull CommandManager<@NonNull CommandSender> manager) {
        final Command.Builder<CommandSender> builder = manager.commandBuilder("rps");

        manager.command(builder
                .argument(PlayerArgument.of("opponent"))
                .handler(this::handleMainCommand));
    }

    /**
     * Handles the /rps command.
     *
     * @param context command context
     */
    private void handleMainCommand(final @NonNull CommandContext<CommandSender> context) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final @NonNull CommandSender sender = context.getSender();

                if (sender instanceof Player player) {
                    final @NonNull Player opponent = context.get("opponent");
                    player.sendMessage("Invited "+opponent.getName()+" to play rock paper scissors.");
                }
            }
        }.runTask(this.plugin);
    }
}
