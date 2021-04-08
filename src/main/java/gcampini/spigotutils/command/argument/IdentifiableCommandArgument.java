package gcampini.spigotutils.command.argument;

import gcampini.spigotutils.command.CommandInputs;

/**
 * Represent an identifiable command argument.
 * For an argument evaluation to be stored in {@link CommandInputs}, it needs to implement this interface.
 *
 * @author Gil CAMPINI
 */
public interface IdentifiableCommandArgument<T> extends CommandArgument<T> {

    /**
     * Return the identifier of this argument.
     *
     * @return the id
     */
    String id();

}
