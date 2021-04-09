package gcampini.spigotutils.command.argument;

import org.bukkit.command.CommandSender;

import java.util.*;

/**
 * Represents a well know argument.
 * Use this argument if you expect a known string input as a command argument.
 *
 * @author Gil CAMPINI
 */
public class WellKnownArgument implements TabCompletableCommandArgument<String> {

    /**
     * All valid aliases for this argument.
     */
    private final List<String> aliases = new ArrayList<>();

    /**
     * Creates a {@code WellKnowArgument} instance.
     *
     * @param argument the expected string
     * @param aliases  possible aliases
     */
    public WellKnownArgument(String argument, String... aliases) {
        this.aliases.add(Objects.requireNonNull(argument, "base argument is null"));
        this.aliases.addAll(Arrays.asList(aliases));
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
        return Objects.equals(aliases.get(0), that.aliases.get(0));
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliases.get(0));
    }

}
