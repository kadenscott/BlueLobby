package dev.kscott.bluelobby.games;

import com.google.inject.Singleton;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

@Singleton
public class GameManager {

    public void sendPlayer(final @NonNull Player player) {
        player.sendMessage(Component.text("Sending you to Bonk..."));
    }

}
