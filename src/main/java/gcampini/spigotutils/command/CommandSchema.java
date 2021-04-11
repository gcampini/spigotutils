package gcampini.spigotutils.command;

import com.google.common.base.Strings;
import gcampini.spigotutils.command.argument.CommandArgument;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Represents a specific command with arguments, an execution and an eventual permission.
 *
 * @author Gil CAMPINI
 */
public abstract class CommandSchema<T extends CommandSender> implements CommandExecution<T> {

    private final CommandArgument<?>[] arguments;
    @Nullable
    private final String permission;
    @Nullable
    private final String description;

    public CommandSchema(@Nullable String description, @Nullable String permission, CommandArgument<?>... arguments) {
        this.description = Strings.emptyToNull(description);
        this.permission = Strings.emptyToNull(permission);
        this.arguments = arguments;
    }

    public CommandSchema(@Nullable String permission, CommandArgument<?>... arguments) {
        this(null, permission, arguments);
    }

    public CommandSchema(CommandArgument<?>... arguments) {
        this(null, arguments);
    }

    public CommandArgument<?>[] getArguments() {
        return arguments;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public String getPermission() {
        return permission;
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
