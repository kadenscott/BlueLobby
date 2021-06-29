package dev.kscott.bluelobby.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.bukkit.parsers.PlayerArgument;
import cloud.commandframework.context.CommandContext;
import dev.kscott.bluelobby.items.ItemService;
import dev.kscott.bluelobby.items.Item;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

/**
 * The class for the /item command.
 */
public class ItemCommand implements BaseCommand {

    /**
     * JavaPlugin reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The item registry.
     */
    private final @NonNull ItemService itemRegistry;

    /**
     * Constructs {@code MenuCommand}.
     *
     * @param plugin the plugin reference
     */
    @Inject
    public ItemCommand(final @NonNull JavaPlugin plugin,
                       final @NonNull ItemService itemRegistry) {
        this.plugin = plugin;
        this.itemRegistry = itemRegistry;
    }

    /**
     * Registers the /item command.
     *
     * @param manager CommandManager to register with
     */
    @Override
    public void register(final @NonNull CommandManager<@NonNull CommandSender> manager) {
        final Command.Builder<CommandSender> builder = manager.commandBuilder("item");

//        manager.command(builder.handler(this::handleItemCommand));
        manager.command(builder.literal("give")
                .argument(PlayerArgument.of("player"))
                .argument(StringArgument.of("item"))
                .handler(this::handleGiveCommand));
    }

    /**
     * Handles the /item give command.
     *
     * @param context command context
     */
    private void handleGiveCommand(final @NonNull CommandContext<CommandSender> context) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final @NonNull Player receivingPlayer = context.get("player");
                final @NonNull String itemId = context.get("item");

                final @NonNull Item item = itemRegistry.item(itemId);

                receivingPlayer.getInventory().addItem(item.itemStack());
            }
        }.runTask(this.plugin);
    }

}
