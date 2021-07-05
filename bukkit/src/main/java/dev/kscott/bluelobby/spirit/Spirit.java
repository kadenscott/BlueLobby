package dev.kscott.bluelobby.spirit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.UUID;

public class Spirit {

    private final @NonNull UUID uuid;

    private final @NonNull Player player;

    public Spirit(final @NonNull Player player) {
        this.player = player;
        this.uuid = this.player.getUniqueId();
    }

    public @NonNull UUID uuid() {
        return this.uuid;
    }

    public @NonNull Player player() {
        return this.player;
    }

}
