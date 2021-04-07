package gcampini.spigotutils.command.argument;

/**
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
