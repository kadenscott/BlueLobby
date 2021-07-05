package dev.kscott.bluelobby.spirit;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedDataManager;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.MetaNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Singleton
public class SpiritService {

    private final @NonNull LuckPerms luckPerms;

    /**
     * The map of online player spirits.
     */
    private final @NonNull Map<UUID, Spirit> spiritMap;

    @Inject
    public SpiritService(final @NonNull LuckPerms luckPerms) {
        this.spiritMap = new HashMap<>();
        this.luckPerms = luckPerms;
    }

    public @NonNull Spirit spirit(final @NonNull UUID uuid) {
        if (spiritMap.containsKey(uuid)) {
            return spiritMap.get(uuid);
        }

        final @NonNull Player player = Bukkit.getPlayer(uuid);

        if (player == null) {
            throw new NullPointerException("UUID was of a player who was not online.");
        }

        final @NonNull Spirit spirit = new Spirit(player);

        this.spiritMap.put(uuid, spirit);

        return spirit;
    }

    /**
     * Gets a piece of data from a spirit's meta.
     *
     * @param spirit the spirit
     * @param key    the key
     * @return the value
     * @throws NullPointerException if something is null
     */
    public @NonNull String get(final @NonNull Spirit spirit,
                               final @NonNull String key) {
        final @Nullable CachedDataManager dataManager = this.luckPerms.getUserManager().getUser(spirit.uuid())
                .getCachedData();

        if (dataManager == null) {
            throw new NullPointerException("User's CachedDataManager was null.");
        }

        final @Nullable String value = dataManager.getMetaData().getMetaValue(key);

        return Objects.requireNonNull(value);
    }

    /**
     * Returns the value at the key, or the default value if null.
     *
     * @param spirit the spirit
     * @param key    the key
     * @param def    the default value
     * @return the value
     */
    public @NonNull String getOrDefault(final @NonNull Spirit spirit,
                                        final @NonNull String key,
                                        final @NonNull String def) {
        try {
            return get(spirit, key);
        } catch (final @NonNull NullPointerException e) {
            return def;
        }
    }

    /**
     * Set a piece of data of a spirit's meta.
     *
     * @param spirit the spirit
     * @param key    the key
     * @param value  the value
     */
    public void set(final @NonNull Spirit spirit,
                    final @NonNull String key,
                    final @NonNull String value) {
        final @NonNull User user = this.luckPerms.getUserManager().getUser(spirit.uuid());

        MetaNode node = MetaNode.builder(key, value).build();

        user.data().clear(NodeType.META.predicate(mn -> mn.getMetaKey().equals(key)));
        user.data().add(node);

        luckPerms.getUserManager().saveUser(user);
    }


}
