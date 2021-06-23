package dev.kscott.bluelobby.command;

import broccolai.corn.paper.PaperItemBuilder;
import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import dev.kscott.bluelobby.interfaces.Interface;
import dev.kscott.bluelobby.interfaces.element.Element;
import dev.kscott.bluelobby.interfaces.transformation.Transformation;
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
        System.out.println("/menu called");

        new BukkitRunnable() {
            @Override
            public void run() {
                System.out.println("/menu called");
                final @NonNull CommandSender sender = context.getSender();

                if (sender instanceof Player player) {
                    final @NonNull ItemStack bgItem = PaperItemBuilder.paper(Material.BLACK_STAINED_GLASS_PANE).build();
                    final @NonNull ItemStack diamondItem = PaperItemBuilder.paper(Material.DIAMOND).build();

                    System.out.println("player");
                    Interface.chest(4)
                            .transform(Transformation.gridFill(Element.item(bgItem)))
                            .transform(Transformation.gridItem(Element.item(diamondItem), 1, 1))
                            .title(Component.text("/menu"))
                            .open(player);
                }
            }
        }.runTask(this.plugin);
    }
}
