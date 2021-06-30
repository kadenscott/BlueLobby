package dev.kscott.bluelobby.items;

import com.google.inject.Inject;
import dev.kscott.bluelobby.items.type.FishingRodItem;
import dev.kscott.bluelobby.items.type.SpearItem;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Singleton
public class ItemService {

    private final @NonNull Map<String, Item> itemMap;

    private final @NonNull JavaPlugin plugin;

    @Inject
    public ItemService(final @NonNull JavaPlugin plugin) {
        this.itemMap = new HashMap<>();
        this.plugin = plugin;

        this.register(new FishingRodItem());
        this.register(new SpearItem());
    }

    /**
     * Returns the item with the given id.
     *
     * @param id the id
     * @return null
     * @throws NullPointerException when no item could be found
     */
    public @NonNull Item item(final @NonNull String id) {
        return Objects.requireNonNull(this.itemMap.get(id));
    }

    /**
     * Attempts to match the {@link ItemStack} to an {@link Item} and return it.
     * <p>
     * If no matching {@link Item} is found, null will be returned.
     *
     * @param itemStack the {@link ItemStack}
     * @return the {@link Item}
     */
    public @Nullable Item item(final @NonNull ItemStack itemStack) {
        final @Nullable ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) {
            return null;
        }

        final @NonNull PersistentDataContainer data = meta.getPersistentDataContainer();

        if (!data.has(Item.KEY_ITEM_ID, PersistentDataType.STRING)) {
            return null;
        }

        final @NonNull String id = Objects.requireNonNull(data.get(Item.KEY_ITEM_ID, PersistentDataType.STRING));

        return this.item(id);
    }

    /**
     * Registers an item.
     *
     * @param item the item
     */
    public void register(final @NonNull Item item) {
        final @NonNull String id = item.id();

        this.itemMap.put(id, item);
    }

    /**
     * Returns a random item.
     *
     * @return item
     */
    public @NonNull Item random() {
        return this.item("spear");
    }

}
