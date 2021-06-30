package dev.kscott.bluelobby.menu;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Provides methods for interacting with menus.
 */
@Singleton
public class MenuService {

    // TODO fix memory leak  by removing UUID value when player leaves
    /**
     * Stores the id of the last open (server) menu for a player.
     */
    private final @NonNull Map<UUID, String> lastOpenMenuMap;

    /**
     * The map holding menus.
     */
    private final @NonNull Map<Class<? extends Menu>, Menu> menuMap;

    /**
     * The injector.
     */
    private final @NonNull Injector injector;

    /**
     * Constructs {@code MenuService}.
     */
    @Inject
    public MenuService(final @NonNull Injector injector) {
        this.injector = injector;
        this.menuMap = new HashMap<>();
        this.lastOpenMenuMap = new HashMap<>();
    }

    public void lastOpenMenu(final @NonNull Player player, final @NonNull String menuId) {
        this.lastOpenMenuMap.put(player.getUniqueId(), menuId);
    }

    public @NonNull String lastOpenMenu(final @NonNull Player player) {
        return this.lastOpenMenuMap.getOrDefault(player.getUniqueId(), "unknown");
    }

    /**
     * Returns a menu.
     *
     * @param clazz the class
     * @param <T> the menu's type
     * @return the menu
     */
    public @NonNull <T extends Menu> T get(final @NonNull Class<? extends Menu> clazz) {
        return (T) this.injector.getInstance(clazz);
    }

}
