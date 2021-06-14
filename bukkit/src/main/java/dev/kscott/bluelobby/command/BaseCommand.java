package dev.kscott.bluelobby.command;

import cloud.commandframework.CommandManager;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a command.
 */
public interface BaseCommand {

    /**
     * Registers this command with {@code commandManager}.
     *
     * @param manager CommandManager to register with
     */
    void register(final @NonNull CommandManager<@NonNull CommandSender> manager);

}
