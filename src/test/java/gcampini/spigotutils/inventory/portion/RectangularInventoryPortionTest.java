package gcampini.spigotutils.inventory.portion;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link RectangularInventoryPortion}.
 *
 * @author Gil CAMPINI
 */
public class RectangularInventoryPortionTest {

    private static ServerMock server;

    @BeforeAll
    public static void setup() {
        server = MockBukkit.getOrCreateMock();
    }

    @Test
    public void testConstructor() {
        //Inventory furnace = server.createInventory(server.addPlayer(), InventoryType.FURNACE);
        Inventory chest = server.createInventory(server.addPlayer(), InventoryType.CHEST);
        Inventory dropper = server.createInventory(server.addPlayer(), InventoryType.DROPPER);
        // Failure
        //assertThrows(InventoryNotRectangularException.class, () -> new RectangularInventoryPortion(furnace));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> new RectangularInventoryPortion(chest, 0, 9, 4));
        // Success
        assertDoesNotThrow(() -> new RectangularInventoryPortion(chest));
        assertDoesNotThrow(() -> new RectangularInventoryPortion(dropper));
        assertDoesNotThrow(() -> new RectangularInventoryPortion(dropper, 2, 2, 2));
    }

}
