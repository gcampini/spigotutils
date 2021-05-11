package gcampini.spigotutils.inventory.portion;

import gcampini.spigotutils.ServerInvolvingTest;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link RectangularInventoryPortion}.
 *
 * @author Gil CAMPINI
 */
public class RectangularInventoryPortionTest extends ServerInvolvingTest {

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

    @Test
    public void testDimension() throws InventoryNotRectangularException {
        Inventory chest = server.createInventory(server.addPlayer(), InventoryType.CHEST);
        RectangularInventoryPortion portion = new RectangularInventoryPortion(chest);
        assertEquals(9, portion.getWidth());
        assertEquals(3, portion.getHeight());
        assertEquals(9 * 3, portion.getSize());
    }

}
