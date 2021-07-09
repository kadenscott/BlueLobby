package dev.kscott.bluelobby.listeners;

import com.destroystokyo.paper.ParticleBuilder;
import dev.kscott.bluelobby.area.LocationService;
import dev.kscott.bluelobby.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
 * Listens on the player join event.
 */
public class PlayerJoinListener implements Listener {

    /**
     * The interface to show the player when they first log in.
     */
    private final @NonNull ChatInterface welcomeInterface;

    /**
     * The plugin's reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The location registry.
     */
    private final @NonNull LocationService locationService;

    /**
     * Constructs {@code PlayerJoinListener}.
     *
     * @param plugin the plugin's reference
     */
    @Inject
    public PlayerJoinListener(final @NonNull JavaPlugin plugin,
                              final @NonNull LocationService locationService) {
        this.plugin = plugin;
        this.locationService = locationService;
        this.welcomeInterface = ChatInterface.builder()
                .addTransform((p, v) -> p
                        .element(ChatLineElement.empty())
                        .element(ChatLineElement.empty())
                        .element(ChatLineElement.of(TextElement.of(
                                Component.text()
                                        .append(Component.text("Welcome to "))
                                        .append(Constants.Chat.SERVER_NAME)
                                        .append(Component.text("!"))
                                        .style(Constants.Chat.STYLE_DEFAULT)
                                        .asComponent())))
                        .element(ChatLineElement.empty())
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
                                                .append(Component.text("You will be prompted to visit "))
                                                .append(Component.text("chat.ksc.sh").style(Constants.Chat.STYLE_LINK))
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
                        .element(ChatLineElement.empty())
                        .element(ChatLineElement.empty()))
                .build();
    }

    /**
     * Handles the player join event.
     *
     * @param event the event
     */
    @EventHandler
    public void handlePlayerJoin(final @NonNull PlayerJoinEvent event) {
        final @NonNull Player player = event.getPlayer();

        player.teleportAsync(this.locationService.spawn());

        this.welcomeInterface.open(PlayerViewer.of(player));

        // Send the default pling 10 ticks after the player joins.
        new BukkitRunnable() {
            @Override
            public void run() {
                Constants.Sounds.playSoundForPlayer(player, Constants.Sounds.DEFAULT_CHIME);
            }
        }.runTaskLater(this.plugin, 10);
    }
}
