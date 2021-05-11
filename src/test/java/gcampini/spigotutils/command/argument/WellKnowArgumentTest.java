package gcampini.spigotutils.command.argument;

import gcampini.spigotutils.ServerInvolvingTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link WellKnownArgument}
 *
 * @author Gil CAMPINI
 */
public class WellKnowArgumentTest extends ServerInvolvingTest {

    @Test
    public void toStringTest() {
        WellKnownArgument argument = new WellKnownArgument("first", "f", "1st");
        assertEquals("first|f|1st", argument.toString());
    }

    @Test
    public void tabCompletionTest() {
        WellKnownArgument argument = new WellKnownArgument("first", "f", "1st");
        List<String> aliases = new ArrayList<>();
        aliases.add("first");
        aliases.add("f");
        aliases.add("1st");
        assertEquals(aliases, argument.possibilities(server.addPlayer()));
    }

}
