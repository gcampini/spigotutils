package gcampini.spigotutils.inventory.lock;

import gcampini.spigotutils.inventory.portion.InventoryPortion;
import gcampini.spigotutils.inventory.portion.event.InventoryPortionClickEvent;
import gcampini.spigotutils.inventory.portion.event.InventoryPortionDragEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gil CAMPINI
 */
public class InventoryLocker implements Listener {

    private static final Map<Inventory, List<InventoryLocker>> LOCKERS = new HashMap<>();

    private final Plugin plugin;
    private final InventoryPortion portion;

    private boolean locked;

    public InventoryLocker(@NotNull Plugin plugin, @NotNull InventoryPortion portion) {
        this.plugin = plugin;
        this.portion = portion;
        Inventory parent = portion.getParent();
        List<InventoryLocker> lockers = LOCKERS.getOrDefault(parent, new ArrayList<>());
        lockers.add(this);
        LOCKERS.put(parent, lockers);
    }

    public void lock() throws UnsupportedOperationException {
        if (locked) throw new UnsupportedOperationException("locker already active");
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        locked = true;
    }

    public void unlock() throws UnsupportedOperationException {
        if (!locked) throw new UnsupportedOperationException("locker already inactive");
        HandlerList.unregisterAll(this);
        locked = false;
    }

    public boolean isLocked() {
        return locked;
    }

    protected boolean shouldLock(InventoryPortionClickEvent event) {
        return event.getPortionSlot() != -1;
    }

    protected boolean shouldLock(InventoryPortionDragEvent event) {
        return !event.getPortionSlots().isEmpty();
    }

    @NotNull
    public InventoryPortion getPortion() {
        return portion;
    }

    @NotNull
    public Plugin getPlugin() {
        return plugin;
    }

    @NotNull
    public static List<InventoryLocker> getLockers(@NotNull Inventory inventory) {
        return LOCKERS.getOrDefault(inventory, new ArrayList<>());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || !event.getClickedInventory().equals(portion.getParent())) return;
        if (shouldLock(new InventoryPortionClickEvent(portion, event))) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!event.getInventory().equals(portion.getParent())) return;
        if (shouldLock(new InventoryPortionDragEvent(portion, event))) event.setCancelled(true);
    }

}
