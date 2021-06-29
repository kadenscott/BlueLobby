package dev.kscott.bluelobby.menu;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import dev.kscott.interfaces.core.Interface;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Utility methods for interacting with server menus.
 */
@Singleton
public class MenuService {
    
    private final @NonNull Injector injector;

    @Inject
    public MenuService(final @NonNull Injector injector) {
        this.injector = injector;
    }

    /**
     * Returns an interface.
     *
     * @param clazz the interface's class
     * @param <T> the
     * @return
     */
    public <T extends Interface> T get(Class<T> clazz) {

    }

}
