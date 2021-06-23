package dev.kscott.bluelobby.interfaces.element;

import dev.kscott.bluelobby.interfaces.view.View;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ItemStackElement extends Element {

    private final @NonNull ItemStack itemStack;

    private final @NonNull BiConsumer<InventoryClickEvent, View> clickHandler;

    protected ItemStackElement(final @NonNull ItemStack itemStack, final @NonNull BiConsumer<InventoryClickEvent, View> clickHandler) {
        this.itemStack = itemStack;
        this.clickHandler = clickHandler;
    }

    public @NonNull ItemStack itemStack() {
        return itemStack;
    }

    public void handleClick(final @NonNull InventoryClickEvent event, final @NonNull View view) {
        this.clickHandler.accept(event, view);
    }

}
