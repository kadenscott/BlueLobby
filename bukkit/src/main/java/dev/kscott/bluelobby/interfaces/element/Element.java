package dev.kscott.bluelobby.interfaces.element;

import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Element {

    public static @NonNull Element empty() {
        return new Element();
    }

    public static @NonNull ItemStackElement item(final @NonNull ItemStack itemStack) {
        return new ItemStackElement(itemStack);
    }

}
