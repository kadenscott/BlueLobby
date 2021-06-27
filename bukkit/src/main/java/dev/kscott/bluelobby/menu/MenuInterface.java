package dev.kscott.bluelobby.menu;

import broccolai.corn.paper.PaperItemBuilder;
import com.google.inject.Injector;
import dev.kscott.bluelobby.games.GameManager;
import dev.kscott.bluelobby.menu.readme.ReadmeInterface;
import dev.kscott.bluelobby.utils.Constants;
import dev.kscott.interfaces.core.transform.Transform;
import dev.kscott.interfaces.paper.PlayerViewer;
import dev.kscott.interfaces.paper.element.ItemStackElement;
import dev.kscott.interfaces.paper.pane.ChestPane;
import dev.kscott.interfaces.paper.transform.PaperTransform;
import dev.kscott.interfaces.paper.type.ChestInterface;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * The main /menu interface.
 */
public class MenuInterface extends ChestInterface {

    private final @NonNull GameManager gameManager;

    private final @NonNull Injector injector;

    /**
     * Constructs {@code MenuInterface}.
     */
    @Inject
    public MenuInterface(final @NonNull GameManager gameManager,
                         final @NonNull Injector injector) {
        super(
                1,
                Constants.Chat.SERVER_NAME,
                List.of(),
                false,
                0,
                (event, view) -> event.setCancelled(true));

        this.gameManager = gameManager;
        this.injector = injector;
    }

    @Override
    public @NonNull List<Transform<ChestPane>> transformations() {
        return List.of(
                // Fill bg
                PaperTransform.chestFill(ItemStackElement.of(Constants.Items.MENU_BACKGROUND.build())),
                // Add items
                (pane, view) -> {
                    final @NonNull ItemStack readmeItem = PaperItemBuilder.paper(Material.PAPER)
                            .name(Component.text()
                                    .append(
                                            Component.text("⚡ ")
                                                    .color(Constants.Chat.COLOUR_YELLOW_LIGHT)
                                                    .decoration(TextDecoration.BOLD, true),
                                            Component.text("README.md")
                                                    .style(Style.style(Constants.Chat.COLOUR_LIGHT_GRAY))
                                                    .style(Constants.Chat.STYLE_NO_ITALICS)
                                    ).asComponent())
                            .loreComponents(Component
                                    .text("Help, credits, and more.")
                                    .style(Constants.Chat.STYLE_DEFAULT)
                            )
                            .build();

                    final @NonNull ItemStack bonkItem = PaperItemBuilder.paper(Material.BLAZE_ROD)
                            .name(Component.text()
                                    .append(Component.text("▶ ").style(Constants.Chat.STYLE_NO_ITALICS).color(Constants.Chat.COLOUR_LIGHT_GRAY).decoration(TextDecoration.BOLD, true))
                                    .append(Component.text("Bonk").style(Constants.Chat.STYLE_NO_ITALICS).color(Constants.Chat.COLOUR_YELLOW))
                                    .append(Component.text(" ◀").style(Constants.Chat.STYLE_NO_ITALICS).color(Constants.Chat.COLOUR_LIGHT_GRAY).decoration(TextDecoration.BOLD, true))
                                    .asComponent())
                            .build();

                    final @NonNull ItemStack unkItem1 = PaperItemBuilder.paper(Material.NETHERITE_BLOCK)
                            .name(Component.text()
                                    .append(Component.text("▶ ").style(Constants.Chat.STYLE_NO_ITALICS).color(Constants.Chat.COLOUR_LIGHT_GRAY).decoration(TextDecoration.BOLD, true))
                                    .append(Component.text("???????").style(Constants.Chat.STYLE_NO_ITALICS).color(Constants.Chat.COLOUR_LIGHT_BLUE))
                                    .append(Component.text(" ◀").style(Constants.Chat.STYLE_NO_ITALICS).color(Constants.Chat.COLOUR_LIGHT_GRAY).decoration(TextDecoration.BOLD, true))
                                    .asComponent())
                            .build();

                    final @NonNull ItemStack unkItem2 = PaperItemBuilder.paper(Material.NETHERITE_SWORD)
                            .name(Component.text()
                                    .append(Component.text("▶ ").style(Constants.Chat.STYLE_NO_ITALICS).color(Constants.Chat.COLOUR_LIGHT_GRAY).decoration(TextDecoration.BOLD, true))
                                    .append(Component.text("????? ????").style(Constants.Chat.STYLE_NO_ITALICS).color(Constants.Chat.COLOUR_RED))
                                    .append(Component.text(" ◀").style(Constants.Chat.STYLE_NO_ITALICS).color(Constants.Chat.COLOUR_LIGHT_GRAY).decoration(TextDecoration.BOLD, true))
                                    .asComponent())
                            .flags(ItemFlag.HIDE_ATTRIBUTES)
                            .build();


                    @NonNull ChestPane paneTemp = pane.element(ItemStackElement.of(readmeItem, (clickEvent, clickView) -> {
                        this.injector.getInstance(ReadmeInterface.class).open((PlayerViewer) clickView.viewer());
                    }), 0, 0);

                    paneTemp = paneTemp.element(ItemStackElement.of(bonkItem, (clickEvent, clickView) -> {
                        gameManager.bonk((Player) clickEvent.getWhoClicked());
                    }), 3, 0);

                    paneTemp = paneTemp.element(ItemStackElement.of(unkItem1), 4, 0);

                    paneTemp = paneTemp.element(ItemStackElement.of(unkItem2), 5, 0);

                    return paneTemp;
                }
        );
    }
}
