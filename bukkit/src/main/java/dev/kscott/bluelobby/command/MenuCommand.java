package dev.kscott.bluelobby.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import dev.kscott.bluelobby.menu.GameGui;
import dev.kscott.bluelobby.menu.MenuGui;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The /menu command class.
 */
public class MenuCommand implements BaseCommand{

    /**
     * Registers the /menu command.
     *
     * @param manager CommandManager to register with
     */
    @Override
    public void register(final @NonNull CommandManager<@NonNull CommandSender> manager) {
        final Command.Builder<CommandSender> builder = manager.commandBuilder("menu");

        manager.command(builder.handler(this::handleMenuCommand));
    }

    /**
     * Handles the /menu command.
     *
     * @param context command context
     */
    private void handleMenuCommand(final @NonNull CommandContext<CommandSender> context) {
        final @NonNull CommandSender sender = context.getSender();

        if (sender instanceof Player player) {
            final @NonNull Gui gui = MenuGui.create();

            // TODO send opening message

            gui.show(player);
        }
    }
}
