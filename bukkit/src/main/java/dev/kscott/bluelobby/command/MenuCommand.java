package dev.kscott.bluelobby.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.context.CommandContext;
import com.destroystokyo.paper.ParticleBuilder;
import dev.kscott.bluelobby.menu.MenuService;
import dev.kscott.bluelobby.menu.server.GamesMenu;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.interfaces.paper.PlayerViewer;
import org.incendo.interfaces.paper.element.ChatLineElement;
import org.incendo.interfaces.paper.element.ClickableTextElement;
import org.incendo.interfaces.paper.element.TextElement;
import org.incendo.interfaces.paper.type.ChatInterface;

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

    private final @NonNull ChatInterface motdInterface;

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

        this.motdInterface = ChatInterface.builder()
                .addTransform((p, v) -> p
                        .element(ChatLineElement.blank())
                        .element(ChatLineElement.blank())
                        .element(ChatLineElement.of(TextElement.of(
                                Component.text()
                                        .append(Component.text("Welcome to "))
                                        .append(Constants.Chat.SERVER_NAME)
                                        .append(Component.text("!"))
                                        .style(Constants.Chat.STYLE_DEFAULT)
                                        .asComponent())))
                        .element(ChatLineElement.blank())
                        .element(ChatLineElement.of(
                                ClickableTextElement.of(
                                        Component.text()
                                                .append(Component.text("Click to get "))
                                                .append(Component.text("sparkles")
                                                        .color(TextColor.color(0x7289DA))
                                                        .decoration(TextDecoration.BOLD, true))
                                                .append(Component.text("."))
                                                .style(Constants.Chat.STYLE_DEFAULT)
                                                .asComponent(),
                                        Component.text()
                                                .append(Component.text("Click to spawn particles").style(Constants.Chat.STYLE_LINK))
                                                .append(Component.text("."))
                                                .style(Constants.Chat.STYLE_DEFAULT)
                                                .asComponent(),
                                        (ctx) -> {
                                            final @NonNull Player player = ((PlayerViewer) ctx.viewer()).player();
                                            new ParticleBuilder(Particle.REDSTONE)
                                                    .location(player.getLocation())
                                                    .count(10)
                                                    .offset(0.8, 1.5, 0.8)
                                                    .color(224, 180, 76)
                                                    .spawn();
                                        }
                                )))
                        .element(ChatLineElement.blank())
                        .element(ChatLineElement.blank()))
                .build();

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

        manager.command(builder.literal("motd").handler(this::handleMotdCommand));
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

    /**
     * Handles the /menu motd command.
     *
     * @param context command context
     */
    private void handleMotdCommand(final @NonNull CommandContext<CommandSender> context) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final @NonNull CommandSender sender = context.getSender();

                if (sender instanceof Player player) {
                    motdInterface.open(PlayerViewer.of(player));
                }
            }
        }.runTask(this.plugin);
    }
}
