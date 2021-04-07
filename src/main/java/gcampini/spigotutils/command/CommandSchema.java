package gcampini.spigotutils.command;

import gcampini.spigotutils.command.argument.CommandArgument;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * @author Gil CAMPINI
 */
public abstract class CommandSchema<T extends CommandSender> implements CommandExecution<T> {

    private final CommandArgument<?>[] arguments;

    public CommandSchema(CommandArgument<?>... arguments) {
        this.arguments = arguments;
    }

    public CommandArgument<?>[] getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandSchema<?> that = (CommandSchema<?>) o;
        return Arrays.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arguments);
    }

}
