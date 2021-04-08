package gcampini.spigotutils.command.argument;

/**
 * Represents the base interface for all command arguments.
 *
 * @author Gil CAMPINI
 */
public interface CommandArgument<T> {

    /**
     * Evaluates the string input and eventually parses it to be returned.
     * TODO: More explanation + example
     *
     * @param arg the string input
     * @return the parsed value or null if the input is not concerned by this argument
     * @throws IllegalArgumentException for any custom parse error
     */
    T evaluate(String arg) throws IllegalArgumentException;

}
