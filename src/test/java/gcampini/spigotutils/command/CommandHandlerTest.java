package gcampini.spigotutils.command;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import gcampini.spigotutils.PluginInvolvingTest;
import gcampini.spigotutils.command.argument.WellKnownArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link CommandHandler}
 *
 * @author Gil CAMPINI
 */
public class CommandHandlerTest extends PluginInvolvingTest {

    private final static String USAGE = "/test ...";

    private CommandHandler handler;

    @BeforeEach
    public void each() {
        PluginCommand command = plugin.getCommand("test");
        CommandHandler handler = CommandHandler.getHandler(command);
        if (handler != null) handler.unload();
        assert command != null;
        this.handler = new CommandHandler(command);
        server.setPlayers(0);
    }

    @Test
    public void testCommandSender() {
        String successMessage = "success!";
        handler.add(new CommandSchema<ConsoleCommandSender>() {
            @Override
            public void execute(ConsoleCommandSender sender, CommandInputs inputs) {
                server.broadcastMessage(successMessage);
            }
        });
        // Failure
        PlayerMock player = server.addPlayer();
        player.performCommand("test");
        assertEquals("The command cannot be executed in this context (PlayerMock).", player.nextMessage());
        // Success
        server.dispatchCommand(server.getConsoleSender(), "test");
        assertEquals(successMessage, player.nextMessage());
    }

    @Test
    public void testCommandNotFound() {
        String successMessage = "success!";
        handler.add(new CommandSchema<CommandSender>(
                new WellKnownArgument("first")
        ) {
            @Override
            public void execute(CommandSender sender, CommandInputs inputs) {
                server.broadcastMessage(successMessage);
            }
        });
        // Failure
        PlayerMock player = server.addPlayer();
        player.performCommand("test firs"); // Unknown command
        assertEquals(USAGE, player.nextMessage());
        player.performCommand("test"); // Unknown command
        assertEquals(USAGE, player.nextMessage());
        player.performCommand("test first more"); // Unknown command
        assertEquals(USAGE, player.nextMessage());
        // Success
        player.performCommand("test first"); // Known command
        assertEquals(successMessage, player.nextMessage());
    }

}
