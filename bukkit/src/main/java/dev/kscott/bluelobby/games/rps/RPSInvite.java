package dev.kscott.bluelobby.games.rps;

import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A record that represents an RPS game invite.
 */
public record RPSInvite(@NonNull Player challenger,
                        @NonNull Player opponent,
                        long time) {

}
