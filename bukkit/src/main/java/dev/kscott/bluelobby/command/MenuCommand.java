package dev.kscott.bluelobby.command;

import broccolai.corn.paper.PaperItemBuilder;
import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import com.google.inject.Injector;
import dev.kscott.bluelobby.menu.MenuInterface;
import dev.kscott.interfaces.core.arguments.HashMapInterfaceArgument;
import dev.kscott.interfaces.core.arguments.InterfaceArgument;
import dev.kscott.interfaces.paper.PlayerViewer;
import dev.kscott.interfaces.paper.element.ClickHandler;
import dev.kscott.interfaces.paper.element.ItemStackElement;
import dev.kscott.interfaces.paper.transform.PaperTransform;
import dev.kscott.interfaces.paper.type.ChestInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The /menu command class.
 */
public class MenuCommand implements BaseCommand {

    /**
     * JavaPlugin reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The menu interface.
     */
    private final @NonNull ChestInterface menuInterface;

    /**
     * Constructs {@code MenuCommand}.
     *
     * @param plugin the plugin reference
     */
    @Inject
    public MenuCommand(final @NonNull JavaPlugin plugin,
                       final @NonNull Injector injector) {
        this.plugin = plugin;
        this.menuInterface = injector.getInstance(MenuInterface.class);

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
                    menuInterface.open(PlayerViewer.of(player));
                }
            }
        }.runTask(this.plugin);
    }
}
