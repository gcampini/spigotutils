package gcampini.spigotutils.command;

import java.util.HashMap;

/**
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
