package gcampini.spigotutils;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

/**
 * @author Gil CAMPINI
 */
public class TestPlugin extends JavaPlugin {

    private static TestPlugin instance;

    public static final String TEST_COMMAND_USAGE = "/test ...";

    public TestPlugin() {
        super();
    }

    protected TestPlugin(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    public static JavaPlugin get() {
        if (instance == null) instance = MockBukkit.loadWith(TestPlugin.class, new File("src/test/java/gcampini/spigotutils/plugin.yml"));
        return instance;
    }

}
