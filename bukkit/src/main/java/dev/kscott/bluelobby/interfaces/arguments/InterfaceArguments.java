package dev.kscott.bluelobby.interfaces.arguments;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Holds arguments to be passed down to the interface transformation.
 */
public class InterfaceArguments {

    /**
     * The argument map.
     */
    private final @NonNull Map<String, Supplier<Object>> argumentMap;

    /**
     * Constructs {@code InterfaceArguments}.
     */
    protected InterfaceArguments() {
        this.argumentMap = new HashMap<>();
    }

    /**
     * Returns a new {@code InterfaceArguments} with {@code key} and {@code value}.
     *
     * @param key   the key
     * @param value the value
     * @return the InterfaceArguments
     */
    public static InterfaceArguments with(final @NonNull String key, final @NonNull Object value) {
        return new InterfaceArguments().and(key, () -> value);
    }

    /**
     * Returns a new {@code InterfaceArguments} with {@code key} and {@code value}.
     *
     * @param key   the key
     * @param value the value
     * @return the InterfaceArguments
     */
    public static InterfaceArguments with(final @NonNull String key, final @NonNull Supplier<Object> value) {
        return new InterfaceArguments().and(key, value);
    }

    /**
     * Returns an empty {@code InterfaceArguments}.
     *
     * @return the InterfaceArguments
     */
    public static InterfaceArguments empty() {
        return new InterfaceArguments();
    }

    /**
     * Adds an argument to the map.
     *
     * @param key   the key
     * @param value the value
     * @return the argument map
     */
    public @NonNull InterfaceArguments and(final @NonNull String key, final @NonNull Object value) {
        this.argumentMap.put(key, () -> value);
        return this;
    }

    /**
     * Adds an argument to the map.
     *
     * @param key   the key
     * @param value the value
     * @return the argument map
     */
    public @NonNull InterfaceArguments and(final @NonNull String key, final @NonNull Supplier<Object> value) {
        this.argumentMap.put(key, value);
        return this;
    }

    /**
     * Returns the object with the given key. Throws an exception if key is invalid.
     *
     * @param key the key
     * @return the object
     */
    public <T> @NonNull T get(final @NonNull String key) {
        final Object value = this.argumentMap.get(key);

        if (value == null) {
            throw new NullPointerException("No such object stored in the context: " + key);
        }

        return (T) this.argumentMap.get(key).get();
    }

}
