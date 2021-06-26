package dev.kscott.bluelobby.menu;

import broccolai.corn.paper.PaperItemBuilder;
import dev.kscott.bluelobby.games.GameManager;
import dev.kscott.bluelobby.utils.Constants;
import dev.kscott.interfaces.paper.element.ItemStackElement;
import dev.kscott.interfaces.paper.pane.ChestPane;
import dev.kscott.interfaces.paper.transform.PaperTransform;
import dev.kscott.interfaces.paper.type.ChestInterface;
import net.kyori.adventure.text.Component;
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
@Singleton
public class MenuInterface extends ChestInterface {

    /**
     * The game manager.
     */
    private final @NonNull GameManager gameManager;

    /**
     * Constructs {@code MenuInterface}.
     */
    @Inject
    public MenuInterface(final @NonNull GameManager gameManager) {
        super(
                1,
                Constants.Chat.SERVER_NAME,
                List.of(
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
                                            .text("Help information, credits, and more.")
                                            .style(Constants.Chat.STYLE_DEFAULT)
                                    )
                                    .build();

                            final @NonNull ItemStack bonkItem = PaperItemBuilder.paper(Material.BLAZE_ROD)
                                    .name(Component.text("BONK").style(Constants.Chat.STYLE_NO_ITALICS).color(Constants.Chat.COLOUR_YELLOW).decoration(TextDecoration.BOLD, true))
                                    .build();

                            final @NonNull ItemStack unkItem1 = PaperItemBuilder.paper(Material.NETHERITE_BLOCK)
                                    .name(Component.text("???????").style(Constants.Chat.STYLE_NO_ITALICS).color(Constants.Chat.COLOUR_LIGHT_BLUE).decoration(TextDecoration.BOLD, true))
                                    .build();

                            final @NonNull ItemStack unkItem2 = PaperItemBuilder.paper(Material.NETHERITE_SWORD)
                                    .name(Component.text("????? ????").style(Constants.Chat.STYLE_NO_ITALICS).color(Constants.Chat.COLOUR_RED).decoration(TextDecoration.BOLD, true))
                                    .flags(ItemFlag.HIDE_ATTRIBUTES)
                                    .build();


                            @NonNull ChestPane paneTemp = pane.element(ItemStackElement.of(readmeItem, (clickEvent, clickView) -> {
                                gameManager.bonk((Player) clickEvent.getWhoClicked());
                            }), 0, 0);

                            paneTemp = paneTemp.element(ItemStackElement.of(bonkItem, (clickEvent, clickView) -> {
                                gameManager.bonk((Player) clickEvent.getWhoClicked());
                            }), 3, 0);

                            paneTemp = paneTemp.element(ItemStackElement.of(unkItem1, (clickEvent, clickView) -> {
                                gameManager.bonk((Player) clickEvent.getWhoClicked());
                            }), 4, 0);

                            paneTemp = paneTemp.element(ItemStackElement.of(unkItem2, (clickEvent, clickView) -> {
                                gameManager.bonk((Player) clickEvent.getWhoClicked());
                            }), 5, 0);



                            return paneTemp;
                        }
                ),
                false,
                0,
                (event, view) -> event.setCancelled(true));

        this.gameManager = gameManager;
    }
}
