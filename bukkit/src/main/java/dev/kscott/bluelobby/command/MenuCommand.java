package dev.kscott.bluelobby.command;

import broccolai.corn.paper.PaperItemBuilder;
import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import dev.kscott.bluelobby.interfaces.Interface;
import dev.kscott.bluelobby.interfaces.arguments.InterfaceArguments;
import dev.kscott.bluelobby.interfaces.element.Element;
import dev.kscott.bluelobby.interfaces.pane.GridPane;
import dev.kscott.bluelobby.interfaces.paper.ChestInterface;
import dev.kscott.bluelobby.interfaces.transformation.Transformation;
import dev.kscott.bluelobby.interfaces.view.ChestView;
import dev.kscott.bluelobby.interfaces.view.View;
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
                    final @NonNull ItemStack bgItem = PaperItemBuilder.paper(Material.BLACK_STAINED_GLASS_PANE).build();
                    final @NonNull ItemStack diamondItem = PaperItemBuilder.paper(Material.DIAMOND).build();

                    ChestInterface menuInterface = Interface.chest(4)
                            .updating(true) // Sets this interface to updating
                            .updateTicks(2) // This interface will now update every 2 ticks
                            // Fill the background with bgItem
                            .transform(Transformation.gridFill(Element.item(
                                    bgItem,
                                    (event, view) -> event.setCancelled(true)))
                            )
                            .transform(Transformation.gridItem(Element.item(diamondItem), 1, 1)) // Add an item and x=1 y=1
                            // Adds a clock timer (which will update every 2 ticks)
                            .transform(Transformation.grid((grid, view) -> {
                                // Get arguments
                                final @NonNull ChestView chestView = (ChestView) view;
                                final @NonNull Long time = chestView.arguments().get("time");

                                // Add clock element
                                grid.element(Element.item(
                                        PaperItemBuilder.paper(Material.CLOCK)
                                                .name(Component.text("Time: "+time))
                                                .build()
                                ), 1, 2);
                            }))
                            .title(Component.text("/menu"));

                    // Opens the menu with the time argument given.
                    // Since InterfaceArguments accept a supplier, passing in System::currentTimeMillis will
                    // provide the latest time every interface update.
                    menuInterface.open(player, InterfaceArguments.with("time", System::currentTimeMillis));
                }
            }
        }.runTask(this.plugin);
    }
}
