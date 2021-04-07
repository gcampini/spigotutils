package gcampini.spigotutils.command.argument;

import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Represents a tab-completable command argument.
 *
 * @author Gil CAMPINI
 */
public interface TabCompletableCommandArgument<T> extends CommandArgument<T> {

    /**
     * Returns a list of all the possibilites for this argument and this {@code CommandSender}.
     *
     * @param sender the command sender
     * @return the possibilities
     */
    List<String> possibilities(CommandSender sender);

}
