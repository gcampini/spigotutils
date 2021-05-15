package gcampini.spigotutils.inventory.portion.event;

import gcampini.spigotutils.inventory.portion.InventoryPortion;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Gil CAMPINI
 */
public abstract class InventoryPortionEvent<T extends InventoryInteractEvent> {

    protected final InventoryPortion portion;
    protected final T event;

    public InventoryPortionEvent(@NotNull InventoryPortion portion, @NotNull T event) {
        this.portion = portion;
        this.event = event;
    }

    @NotNull
    public InventoryPortion getPortion() {
        return portion;
    }

    @NotNull
    public T getEvent() {
        return event;
    }

}
