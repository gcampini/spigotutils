package gcampini.spigotutils.command.argument;

import org.bukkit.command.CommandSender;

import java.util.*;

/**
 * @author Gil CAMPINI
 */
public class WellKnownArgument implements TabCompletableCommandArgument<String> {

    private final List<String> aliases;

    public WellKnownArgument(String... aliases) {
        this.aliases = Arrays.asList(aliases);
    }

    @Override
    public String evaluate(String arg) throws IllegalArgumentException {
        return aliases.contains(arg) ? arg : null;
    }

    @Override
    public List<String> possibilities(CommandSender sender) {
        return new ArrayList<>(aliases);
    }

    @Override
    public String toString() {
        return String.join("|", aliases);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WellKnownArgument that = (WellKnownArgument) o;
        return Objects.equals(aliases, that.aliases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliases);
    }

}
