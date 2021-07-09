package dev.kscott.bluelobby.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onNPCDeath(final @NonNull PlayerDeathEvent event) {
        if (event.getEntity().hasMetadata("NPC")) {
            event.deathMessage(Component.empty());
        }
    }

}
