package gcampini.spigotutils.command;

import gcampini.spigotutils.command.argument.CommandArgument;
import gcampini.spigotutils.command.argument.TabCompletableCommandArgument;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Handles all sub command of a base {@link Command}.
 *
 * @author Gil CAMPINI
 */
public class CommandHandler extends CommandNode implements CommandExecutor, TabCompleter {

    public final static Map<PluginCommand, CommandHandler> HANDLERS = new HashMap<>();

    private final PluginCommand command;

    public CommandHandler(PluginCommand command, CommandSchema<?>... schemas) {
        super(null, null, null);
        this.command = Objects.requireNonNull(command, "command is null");
        if (HANDLERS.containsKey(command)) throw new IllegalArgumentException("command already has an handler");
        command.setExecutor(this);
        command.setTabCompleter(this);
        for (CommandSchema<?> schema : schemas) add(schema);
        HANDLERS.put(command, this);
    }

    public void add(CommandSchema<?> schema) {
        Collection<CommandNode> currentNodes = nodes;
        CommandArgument<?>[] arguments = schema.getArguments();

        if (arguments.length == 0) {
            setExecution(schema);
            return;
        }

        for (int depth = 0; depth < arguments.length; depth++) {
            CommandArgument<?> argument = arguments[depth];
            boolean last = depth == arguments.length - 1;
            CommandNode next = null;
            for (CommandNode node : currentNodes) {
                if (node.getArgument().equals(argument)) {
                    currentNodes = node.getNodes();
                    next = node;
                    break;
                }
            }
            if (next == null) {
                next = new CommandNode(argument, last ? schema : null, last ? schema.getPermission() : null);
                currentNodes.add(next);
            }
            currentNodes = next.getNodes();
        }

    }

    public void unload() {
        command.setExecutor(null);
        command.setTabCompleter(null);
        HANDLERS.remove(command);
    }

    public static CommandHandler getHandler(PluginCommand command) {
        return HANDLERS.get(command);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String base, @NotNull String[] args) {
        CommandInputs inputs = new CommandInputs();
        try {
            CommandNode node = get(args, inputs);
            if (node == null) return false;
            String permission = node.getPermission();
            if (permission != null && !sender.hasPermission(permission)) {
                // TODO: Give the possibility to change permission message
                sender.sendMessage("You don't have permission to perform this command.");
                return true;
            }
            CommandExecution<?> execution = node.getExecution();
            if (execution == null) return false;
            execution.tryExecute(sender, inputs);
        } catch (IllegalArgumentException exception) {
            sender.sendMessage(exception.getMessage());
        } catch (ClassCastException exception) {
            sender.sendMessage("The command cannot be executed in this context (" + sender.getClass().getSimpleName() + ").");
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        String[] newArgs = new String[args.length == 0 ? 0 : args.length - 1];
        System.arraycopy(args, 0, newArgs, 0, newArgs.length);
        CommandNode node = get(newArgs, null);
        if (node == null) return null;
        List<String> possibilities = new ArrayList<>();
        for (CommandNode child : node.nodes) {
            String permission = node.getPermission();
            if (permission != null && !sender.hasPermission(permission)) continue;
            CommandArgument<?> argument = child.getArgument();
            if (argument instanceof TabCompletableCommandArgument) {
                TabCompletableCommandArgument<?> completable = (TabCompletableCommandArgument<?>) argument;
                possibilities.addAll((completable).possibilities(sender));
            }
        }
        return possibilities;
    }

}
