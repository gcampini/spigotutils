package gcampini.spigotutils;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.BeforeAll;

/**
 * @author Gil CAMPINI
 */
public class ServerInvolvingTest {

    protected static ServerMock server;

    @BeforeAll
    public static void setupServer() {
        server = MockBukkit.getOrCreateMock();
    }

}
