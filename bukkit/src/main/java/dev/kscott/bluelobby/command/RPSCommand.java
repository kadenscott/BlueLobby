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
    private final @NonNull RPSService rpsService;

    /**
     * Constructs {@code RPSCommand}.
     *
     * @param plugin the plugin reference
     * @param rpsService the rps service
     */
    @Inject
    public RPSCommand(
            final @NonNull JavaPlugin plugin,
            final @NonNull RPSService rpsService
    ) {
        this.plugin = plugin;
        this.rpsService = rpsService;
    }

    /**
     * Registers the /rps command.
     *
     * @param manager CommandManager to register with
     */
    @Override
    public void register(final @NonNull CommandManager<@NonNull CommandSender> manager) {
        final Command.Builder<CommandSender> builder = manager.commandBuilder("rps");

        manager.command(builder.literal("invite")
                .argument(PlayerArgument.of("opponent"))
                .handler(this::handleInvite));

        manager.command(builder.literal("accept")
                .argument(PlayerArgument.of("challenger"))
                .handler(this::handleAccept));
    }

    /**
     * Handles the /rps command.
     *
     * @param context command context
     */
    private void handleInvite(final @NonNull CommandContext<CommandSender> context) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final @NonNull CommandSender sender = context.getSender();

                if (sender instanceof Player player) {
                    final @NonNull Player opponent = context.get("opponent");
                    rpsService.invite(player, opponent);
                }
            }
        }.runTask(this.plugin);
    }

    /**
     * Handles /rps accept command.
     *
     * @param context command context
     */
    private void handleAccept(final @NonNull CommandContext<CommandSender> context) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final @NonNull CommandSender sender = context.getSender();

                if (sender instanceof Player player) {
                    final @NonNull Player opponent = context.get("challenger");
                    rpsService.accept(player, opponent);
                }
            }
        }.runTask(this.plugin);

    }
}
