package dev.kscott.bluelobby.menu;

import com.google.inject.Inject;
import dev.kscott.bluelobby.menu.server.GamesMenu;
import dev.kscott.bluelobby.menu.server.ReadmeMenu;
import dev.kscott.bluelobby.menu.server.WarpsMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Events for menu functionality.
 */
public class MenuListener implements Listener {

    /**
     * The menu service.
     */
    private final @NonNull MenuService menuService;

    /**
     * Constructs {@code MenuListener}.
     *
     * @param menuService the menu service
     */
    @Inject
    public MenuListener(final @NonNull MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * Opens the main menu if the player switches offhand while crouching.
     *
     * @param event the event
     */
    @EventHandler
    public void onOffhandSwitch(final @NonNull PlayerSwapHandItemsEvent event) {
        final @NonNull Player player = event.getPlayer();

        final boolean crouching = player.isSneaking();

        if (crouching) {
            final @NonNull String lastOpenMenu = this.menuService.lastOpenMenu(player);

            switch (lastOpenMenu) {
                case "readme" -> {
                    this.menuService.get(ReadmeMenu.class).open(player);
                }
                case "warps" -> {
                    this.menuService.get(WarpsMenu.class).open(player);
                }
                default -> {
                    this.menuService.get(GamesMenu.class).open(player);
                }
            }
        }
    }

}
