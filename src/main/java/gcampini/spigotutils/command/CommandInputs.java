package gcampini.spigotutils.command;

import gcampini.spigotutils.command.argument.IdentifiableCommandArgument;

import java.util.HashMap;

/**
 * Represents the input arguments of a command when evaluated.
 * Only {@link IdentifiableCommandArgument} will be stored as inputs since each input needs to be identifiable by a key.
 * Exposes utility classes to retrieve inputs.
 *
 * @author Gil CAMPINI
 */
public class CommandInputs extends HashMap<String, Object> {

    /**
     * Gets an input by its id and tries to parse it.
     * If the parse fails, {@code null} is returned.
     *
     * @param key the key
     * @param <T> the parse type
     * @return the parsed value if parse succeed, null otherwise
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        Object input = super.get(key);
        try {
            return (T) input;
        } catch (Exception e) {
            return null;
        }
    }

}
