package gcampini.spigotutils.inventory.portion.event;

import gcampini.spigotutils.inventory.portion.InventoryPortion;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Gil CAMPINI
 */
public class InventoryPortionClickEvent extends InventoryPortionEvent<InventoryClickEvent> {

    public InventoryPortionClickEvent(@NotNull InventoryPortion portion, @NotNull InventoryClickEvent event) {
        super(portion, event);
    }

    public int getPortionSlot() {
        int[] mapping = portion.getMapping();
        for (int slot = 0; slot < mapping.length; slot++) {
            if (mapping[slot] == event.getSlot()) return slot;
        }
        return -1;
    }

}
