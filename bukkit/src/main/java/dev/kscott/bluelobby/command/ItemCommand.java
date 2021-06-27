package dev.kscott.bluelobby.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import dev.kscott.bluelobby.items.SpearItem;
import dev.kscott.interfaces.paper.PlayerViewer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

public class ItemCommand implements BaseCommand {

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
    public ItemCommand(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;

    }

    /**
     * Registers the /item command.
     *
     * @param manager CommandManager to register with
     */
    @Override
    public void register(final @NonNull CommandManager<@NonNull CommandSender> manager) {
        final Command.Builder<CommandSender> builder = manager.commandBuilder("item");

        manager.command(builder.handler(this::handleItemCommand));
    }

    /**
     * Handles the /item command.
     *
     * @param context command context
     */
    private void handleItemCommand(final @NonNull CommandContext<CommandSender> context) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final @NonNull CommandSender sender = context.getSender();

                if (sender instanceof Player player) {
                    final @NonNull SpearItem spearItem = new SpearItem();
                    player.getInventory().addItem(spearItem.itemStack());
                }
            }
        }.runTask(this.plugin);
    }

}
