package gcampini.spigotutils.command.argument;

/**
 * @author Gil CAMPINI
 */
public class StringArgument extends InputCommandArgument<String> {

    public StringArgument(String id) {
        super(id);
    }

    @Override
    public String evaluate(String arg) throws IllegalArgumentException {
        return arg;
    }

}
