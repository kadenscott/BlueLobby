package dev.kscott.bluelobby.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import dev.kscott.bluelobby.ui.Interface;
import dev.kscott.bluelobby.ui.paper.ChestInterface;
import dev.kscott.bluelobby.ui.transformation.Transformation;
import net.kyori.adventure.text.Component;
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
     * Constructs {@code MenuCommand}.
     *
     * @param plugin the plugin reference
     */
    @Inject
    public MenuCommand(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
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
        System.out.println("/menu called");

        new BukkitRunnable() {
            @Override
            public void run() {
                System.out.println("/menu called");
                final @NonNull CommandSender sender = context.getSender();

                if (sender instanceof Player player) {
                    System.out.println("player");
                    ChestInterface ui = Interface.chest(4)
                            .transform(Transformation.grid(pane -> {
                                return pane;
                            }))
                            .title(Component.text(""));

                    System.out.println(ui);

                    ui.open(player);
                }
            }
        }.runTask(this.plugin);
    }
}
