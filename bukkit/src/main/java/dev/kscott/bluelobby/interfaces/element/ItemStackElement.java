package dev.kscott.bluelobby.interfaces.element;

import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ItemStackElement extends Element {

    private final @NonNull ItemStack itemStack;

    protected ItemStackElement(final @NonNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public @NonNull ItemStack itemStack() {
        return itemStack;
    }

}
