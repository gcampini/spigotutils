package gcampini.spigotutils.command.argument;

/**
 * @author Gil CAMPINI
 */
public class IntegerArgument extends InputCommandArgument<Integer> {

    public IntegerArgument(String id) {
        super(id);
    }

    @Override
    public Integer evaluate(String arg) throws IllegalArgumentException {
        try {
            return Integer.valueOf(arg);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
