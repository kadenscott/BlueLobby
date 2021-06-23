package dev.kscott.bluelobby.interfaces.element;

import dev.kscott.bluelobby.interfaces.view.View;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class Element {

    public static @NonNull Element empty() {
        return new Element();
    }

    public static @NonNull ItemStackElement item(final @NonNull ItemStack itemStack) {
        return new ItemStackElement(itemStack, (event, view) -> {});
    }

    public static @NonNull ItemStackElement item(final @NonNull ItemStack itemStack, final @NonNull BiConsumer<InventoryClickEvent, View> clickHandler) {
        return new ItemStackElement(itemStack, clickHandler);
    }

}
