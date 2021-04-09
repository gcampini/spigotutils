package gcampini.spigotutils.command;

import gcampini.spigotutils.command.argument.CommandArgument;
import gcampini.spigotutils.command.argument.InputCommandArgument;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Gil CAMPINI
 */
class CommandNode {

    private final CommandArgument<?> argument;
    @Nullable
    private CommandExecution<?> execution;
    @Nullable
    private final String permission;
    protected final Collection<CommandNode> nodes;

    CommandNode(CommandArgument<?> argument, @Nullable CommandExecution<?> execution, @Nullable String permission) {
        this.argument = argument;
        this.execution = execution;
        this.permission = permission;
        this.nodes = new ArrayList<>();
    }

    public CommandNode get(String[] args, @Nullable CommandInputs inputs) {
        return get(args, 0, inputs);
    }

    private CommandNode get(String[] args, int depth, @Nullable CommandInputs inputs) throws IllegalArgumentException {
        if (depth == args.length) return this;
        String arg = args[depth];
        for (CommandNode node : nodes) {
            CommandArgument<?> argument = node.getArgument();
            Object parsed = argument.evaluate(arg);
            if (parsed == null) continue;
            if (inputs != null && argument instanceof InputCommandArgument<?>) {
                inputs.put(((InputCommandArgument<?>) argument).id(), parsed);
            }
            return node.get(args, depth + 1, inputs);
        }
        return null;
    }

    @Nullable
    public CommandExecution<?> getExecution() {
        return execution;
    }

    @Nullable
    public String getPermission() {
        return permission;
    }

    void setExecution(@Nullable CommandExecution<?> execution) {
        this.execution = execution;
    }

    public CommandArgument<?> getArgument() {
        return argument;
    }

    public Collection<CommandNode> getNodes() {
        return nodes;
    }

}
