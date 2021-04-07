package gcampini.spigotutils.command.argument;

/**
 * @author Gil CAMPINI
 */
public interface CommandArgument<T> {

    /**
     * Evaluates the string input and eventually parses it to be returned.
     *
     * @param arg the string input
     * @return the parsed value or null if input not concerned by this argument
     * @throws IllegalArgumentException for any custom parse error
     */
    T evaluate(String arg) throws IllegalArgumentException;

}
