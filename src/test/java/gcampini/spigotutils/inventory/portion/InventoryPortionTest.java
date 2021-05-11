package gcampini.spigotutils.inventory.portion;

import gcampini.spigotutils.ServerInvolvingTest;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link InventoryPortion}.
 *
 * @author Gil CAMPINI
 */
public class InventoryPortionTest extends ServerInvolvingTest {

    @Test
    public void testConstructor() {
        Random random = new Random();
        InventoryType[] types = new InventoryType[]{InventoryType.CHEST, InventoryType.DROPPER, InventoryType.HOPPER};
        Inventory inventory = server.createInventory(server.addPlayer(), types[random.nextInt(types.length)]);
        // Failure
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> new InventoryPortion(inventory, new int[]{-1}));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> new InventoryPortion(inventory, new int[]{inventory.getSize()}));
        // Success
        int[] mapping = new int[inventory.getSize()];
        for (int slot = 0; slot < mapping.length; slot++) mapping[slot] = inventory.getSize() - slot - 1;
        assertDoesNotThrow(() -> new InventoryPortion(inventory, mapping));
    }

}
