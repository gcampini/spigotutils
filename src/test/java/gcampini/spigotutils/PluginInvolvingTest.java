package gcampini.spigotutils;

import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.BeforeAll;

/**
 * @author Gil CAMPINI
 */
public class PluginInvolvingTest extends ServerInvolvingTest {

    protected static JavaPlugin plugin;

    @BeforeAll
    public static void setupPlugin() {
        plugin = TestPlugin.get();
    }

}
