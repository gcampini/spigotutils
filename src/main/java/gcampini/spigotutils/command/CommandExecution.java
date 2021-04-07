package gcampini.spigotutils.command;

import org.bukkit.command.CommandSender;

/**
 * @author Gil CAMPINI
 */
@FunctionalInterface
public interface CommandExecution<T extends CommandSender> {

    /**
     * Executes the command with a given sender and command inputs.
     *
     * @param sender the sender
     * @param inputs the command inputs
     */
    void execute(T sender, CommandInputs inputs);

    /**
     * Tries to cast the sender before calling {@execute}.
     *
     * @param sender the non-casted sender
     * @param inputs the command inputs
     * @throws ClassCastException if the sender is not of type {@code T}
     */
    @SuppressWarnings("unchecked")
    default void tryExecute(CommandSender sender, CommandInputs inputs) throws ClassCastException {
        execute((T) sender, inputs);
    }

}
