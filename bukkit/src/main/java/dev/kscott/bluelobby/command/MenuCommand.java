package dev.kscott.bluelobby.command;

import broccolai.corn.paper.PaperItemBuilder;
import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import dev.kscott.bluelobby.utils.Constants;
import dev.kscott.interfaces.core.arguments.HashMapInterfaceArgument;
import dev.kscott.interfaces.paper.PlayerViewer;
import dev.kscott.interfaces.paper.element.ItemStackElement;
import dev.kscott.interfaces.paper.transform.PaperTransform;
import dev.kscott.interfaces.paper.type.ChestInterface;
import dev.kscott.interfaces.paper.view.ChestView;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
        new BukkitRunnable() {
            @Override
            public void run() {
                final @NonNull CommandSender sender = context.getSender();

                if (sender instanceof Player player) {
                    ChestInterface menuInterface = ChestInterface.builder()
                            .rows(4)
                            .topClickHandler((event, view) -> {
                                event.setCancelled(true);
                            })
                            // Fill the background with bgItem
                            .addTransform(PaperTransform.chestFill(ItemStackElement.of(
                                    Constants.Items.MENU_BACKGROUND.build(),
                                    (event, view) -> event.setCancelled(true)))
                            )
                            // Adds a clock timer (which will update every 2 ticks)
                            .addTransform((pane, view) -> {
                                // Get arguments
                                final @NonNull ChestView chestView = (ChestView) view;
                                final @NonNull Long time = chestView.argument().get("time");

                                // Add clock element
                                pane = pane.element(ItemStackElement.of(
                                        PaperItemBuilder.paper(Material.CLOCK)
                                                .name(Component.text("Time: " + time))
                                                .build()
                                ), 1, 2);

                                return pane;
                            })
                            .title(Component.text("/menu"))
                            .build();

                    // Opens the menu with the time argument given.
                    // Since InterfaceArguments accept a supplier, passing in System::currentTimeMillis will
                    // provide the latest time every interface update.
                    menuInterface.open(PlayerViewer.of(player), HashMapInterfaceArgument.with("time", System::currentTimeMillis).build());
                }
            }
        }.runTask(this.plugin);
    }
}
