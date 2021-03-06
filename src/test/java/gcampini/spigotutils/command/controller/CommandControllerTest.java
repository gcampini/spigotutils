package gcampini.spigotutils.command.controller;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import gcampini.spigotutils.TestPlugin;
import gcampini.spigotutils.command.CommandHandler;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Gil CAMPINI
 */
public class CommandControllerTest {

    private static ServerMock server;
    private static JavaPlugin plugin;

    private PluginCommand command;

    @BeforeAll
    public static void setup() {
        server = MockBukkit.getOrCreateMock();
        plugin = TestPlugin.get();
    }

    @BeforeEach
    public void each() {
        command = plugin.getCommand("test");
        CommandHandler handler = CommandHandler.getHandler(command);
        if (handler != null) handler.unload();
        server.setPlayers(0);
    }

    @Test
    public void testBuid() {
        assertDoesNotThrow(() -> {
            TestCommandController controller = new TestCommandController();
            new CommandHandler(command, controller);
            PlayerMock player = server.addPlayer();
            player.performCommand("test");
            assertEquals("test", player.nextMessage());
            player.setHealth(20);
            player.performCommand("test health d");
            assertEquals(TestPlugin.TEST_COMMAND_USAGE, player.nextMessage());
            assertEquals(20, player.getHealth());
            player.performCommand("test health 2");
            assertEquals("updated", player.nextMessage());
            assertEquals(2, player.getHealth());
        });
    }

}
