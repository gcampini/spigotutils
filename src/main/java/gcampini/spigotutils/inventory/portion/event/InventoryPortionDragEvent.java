package gcampini.spigotutils.inventory.portion.event;

import gcampini.spigotutils.inventory.portion.InventoryPortion;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Gil CAMPINI
 */
public class InventoryPortionDragEvent extends InventoryPortionEvent<InventoryDragEvent> {

    public InventoryPortionDragEvent(@NotNull InventoryPortion portion, @NotNull InventoryDragEvent event) {
        super(portion, event);
    }

    @NotNull
    public Set<Integer> getPortionSlots() {
        return event.getInventorySlots().stream().map(slot -> {
            int[] mapping = portion.getMapping();
            for (int s = 0; s < mapping.length; s++) {
                if (mapping[s] == slot) return s;
            }
            return -1;
        }).filter(slot -> slot != -1).collect(Collectors.toSet());
    }

}
