package dev.kscott.bluelobby.command;

import cloud.commandframework.CommandManager;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * Manages commands.
 */
public final class CommandService {

    /**
     * A list of {@link BaseCommand}s to register.
     */
    private static final @NonNull List<Class<? extends BaseCommand>> COMMANDS = List.of(
            MenuCommand.class
    );

    /**
     * Constructs {@code CommandService}.
     *
     * @param injector       the Guice injector
     * @param commandManager the CommandManager
     */
    @Inject
    public CommandService(
            final @NonNull Injector injector,
            final @NonNull CommandManager<CommandSender> commandManager
    ) {
        for (final @NonNull Class<? extends BaseCommand> klazz : COMMANDS) {
            injector.getInstance(klazz).register(commandManager);
        }
    }

}
