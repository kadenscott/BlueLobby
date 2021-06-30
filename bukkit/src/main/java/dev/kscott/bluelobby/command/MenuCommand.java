package dev.kscott.bluelobby.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import dev.kscott.bluelobby.menu.MenuService;
import dev.kscott.bluelobby.menu.server.GamesMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

/**
 * The /menu command class.
 */
public class MenuCommand implements BaseCommand {

    /**
     * JavaPlugin reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The menu service.
     */
    private final @NonNull MenuService menuService;

    /**
     * Constructs {@code MenuCommand}.
     *
     * @param plugin      the plugin reference
     * @param menuService the menu service
     */
    @Inject
    public MenuCommand(final @NonNull JavaPlugin plugin,
                       final @NonNull MenuService menuService) {
        this.plugin = plugin;
        this.menuService = menuService;
    }

    /**
     * Registers the /menu command.
     *
     * @param manager CommandManager to register with
     */
    @Override
    public void register(final @NonNull CommandManager<@NonNull CommandSender> manager) {
        final Command.Builder<CommandSender> builder = manager.commandBuilder("menu", "play");

        manager.command(builder.handler(this::handleMenuCommand));
    }

    /**
     * Handles the /menu command.
     *
     * @param context command context
     */
    private void handleMenuCommand(final @NonNull CommandContext<CommandSender> context) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final @NonNull CommandSender sender = context.getSender();

                if (sender instanceof Player player) {
                    menuService.get(GamesMenu.class).open(player);
                }
            }
        }.runTask(this.plugin);
    }
}
