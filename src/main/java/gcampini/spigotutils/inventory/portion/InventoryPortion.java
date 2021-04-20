package gcampini.spigotutils.inventory.portion;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Class representing a portion (or a view, or a sub-inventory) of a parent inventory.
 * It implements {@link Inventory} because it is meant to be an sub-{@code Inventory} in an {@code Inventory}, so to speak.
 * This class is called InventoryPortion instead of InventoryView in an attempt to avoid any confusion as the Spigot API
 * already has an {@link InventoryView} class representing something else.
 * All changes made in the portion will be reflected in the parent inventory and vice-versa.
 *
 * @author Gil CAMPINI
 */
public class InventoryPortion implements Inventory {

    /**
     * The parent inventory.
     */
    protected final Inventory parent;

    /**
     * The slots mapping.
     * Each index of the mapping array is associated with a slot number of the parent {@code inventory}.
     */
    private final int[] mapping;

    /**
     * Creates an instance of {@code InventoryPortion}.
     *
     * @param inventory the parent inventory
     * @param mapping   the slot mapping ([4, 5] means slot 0 of the portion maps to slot 4 of the parent, and 1 maps to 5)
     * @throws ArrayIndexOutOfBoundsException if mapping contains an out of bounds slot
     */
    public InventoryPortion(Inventory inventory, int[] mapping) throws ArrayIndexOutOfBoundsException {
        this.parent = Objects.requireNonNull(inventory, "inventory is null");
        Objects.requireNonNull(mapping, "mapping is null");
        for (int slot : mapping) {
            if (slot < 0 || slot >= inventory.getSize())
                throw new ArrayIndexOutOfBoundsException("mapping contains an out of bounds slot");
        }
        this.mapping = mapping;
    }

    private void requireNotOutOfBounds(int slot) {
        if (slot < 0 || slot >= getSize()) throw new IndexOutOfBoundsException("slot is out of bounds");
    }

    @Override
    public int getSize() {
        return mapping.length;
    }

    @Override
    public int getMaxStackSize() {
        return parent.getMaxStackSize();
    }

    @Override
    public void setMaxStackSize(int i) {
        parent.setMaxStackSize(i);
    }

    @Override
    public ItemStack getItem(int slot) {
        requireNotOutOfBounds(slot);
        return parent.getItem(mapping[slot]);
    }

    @Override
    public void setItem(int slot, ItemStack itemStack) {
        requireNotOutOfBounds(slot);
        parent.setItem(mapping[slot], itemStack);
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> addItem(ItemStack... itemStacks) throws IllegalArgumentException {
        if (itemStacks == null || Arrays.stream(itemStacks).anyMatch(Objects::isNull))
            throw new IllegalArgumentException();
        // TODO
        return new HashMap<>();
    }

    @Override
    public @NotNull HashMap<Integer, ItemStack> removeItem(ItemStack... itemStacks) throws IllegalArgumentException {
        // TODO
        return new HashMap<>();
    }

    @Override
    public ItemStack[] getContents() {
        ItemStack[] stacks = new ItemStack[getSize()];
        for (int slot = 0; slot < getSize(); slot++) {
            stacks[slot] = getItem(slot);
        }
        return stacks;
    }

    @Override
    public void setContents(@NotNull ItemStack[] itemStacks) throws IllegalArgumentException {
        if (itemStacks.length > getSize()) throw new IllegalArgumentException();
        clear();
        for (int slot = 0; slot < itemStacks.length; slot++) {
            setItem(slot, itemStacks[slot]);
        }
    }

    @Override
    public ItemStack[] getStorageContents() {
        // TODO
        return new ItemStack[0];
    }

    @Override
    public void setStorageContents(ItemStack[] itemStacks) throws IllegalArgumentException {
        // TODO
    }

    @Override
    public boolean contains(@NotNull Material material) throws IllegalArgumentException {
        return contains(material, 1);
    }

    @Override
    public boolean contains(ItemStack itemStack) {
        return contains(itemStack, 1);
    }

    @Override
    public boolean contains(@NotNull Material material, int amount) throws IllegalArgumentException {
        if (amount < 1) return true;
        int found = 0;
        for (int slot = 0; slot < getSize(); slot++) {
            ItemStack stack = getItem(slot);
            if (stack != null && stack.getType() == material) found += 1;
            if (found >= amount) return true;
        }
        return false;
    }

    @Override
    public boolean contains(ItemStack itemStack, int amount) {
        if (itemStack == null) return false;
        if (amount < 1) return true;
        int found = 0;
        for (int slot = 0; slot < getSize(); slot++) {
            if (itemStack.equals(getItem(slot))) found += 1;
            if (found >= amount) return true;
        }
        return false;
    }

    @Override
    public boolean containsAtLeast(ItemStack itemStack, int amount) {
        if (itemStack == null) return false;
        if (amount < 1) return true;
        int found = 0;
        for (int slot = 0; slot < getSize(); slot++) {
            ItemStack current = getItem(slot);
            if (current != null && itemStack.isSimilar(current)) found += current.getAmount();
            if (found >= amount) return true;
        }
        return false;
    }

    @Override
    public @NotNull HashMap<Integer, ? extends ItemStack> all(@NotNull Material material) throws IllegalArgumentException {
        HashMap<Integer, ItemStack> map = new HashMap<>();
        for (int slot = 0; slot < getSize(); slot++) {
            ItemStack current = getItem(slot);
            if (current != null && current.getType() == material) map.put(slot, current);
        }
        return map;
    }

    @Override
    public @NotNull HashMap<Integer, ? extends ItemStack> all(ItemStack itemStack) {
        HashMap<Integer, ItemStack> map = new HashMap<>();
        if (itemStack == null) return map;
        for (int slot = 0; slot < getSize(); slot++) {
            ItemStack current = getItem(slot);
            if (itemStack.equals(current)) map.put(slot, current);
        }
        return map;
    }

    @Override
    public int first(@NotNull Material material) throws IllegalArgumentException {
        if (material == Material.AIR) return firstEmpty();
        for (int slot = 0; slot < getSize(); slot++) {
            ItemStack stack = getItem(slot);
            if (stack != null && stack.getType() == material) return slot;
        }
        return -1;
    }

    @Override
    public int first(@NotNull ItemStack itemStack) {
        if (itemStack.getType() == Material.AIR) return firstEmpty();
        for (int slot = 0; slot < getSize(); slot++) {
            if (itemStack.equals(getItem(slot))) return slot;
        }
        return -1;
    }

    @Override
    public int firstEmpty() {
        for (int slot = 0; slot < getSize(); slot++) {
            ItemStack stack = getItem(slot);
            if (stack == null || stack.getType() == Material.AIR) return slot;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        for (int slot = 0; slot < getSize(); slot++) {
            ItemStack stack = getItem(slot);
            if (stack != null && stack.getType() != Material.AIR) return false;
        }
        return true;
    }

    @Override
    public void remove(@NotNull Material material) throws IllegalArgumentException {
        if (material == Material.AIR) return;
        for (int slot = 0; slot < getSize(); slot++) {
            ItemStack stack = getItem(slot);
            if (stack != null && stack.getType() == material) clear(slot);
        }
    }

    @Override
    public void remove(@NotNull ItemStack itemStack) {
        for (int slot = 0; slot < getSize(); slot++) {
            if (itemStack.equals(getItem(slot))) clear(slot);
        }
    }

    @Override
    public void clear(int slot) {
        requireNotOutOfBounds(slot);
        setItem(slot, null);
    }

    @Override
    public void clear() {
        setContents(new ItemStack[0]);
    }

    @Override
    @NotNull
    public List<HumanEntity> getViewers() {
        return parent.getViewers();
    }

    @Override
    @NotNull
    public InventoryType getType() {
        return parent.getType();
    }

    @Override
    public InventoryHolder getHolder() {
        return parent.getHolder();
    }

    @Override
    @NotNull
    public ListIterator<ItemStack> iterator() {
        return iterator(0);
    }

    @Override
    @NotNull
    public ListIterator<ItemStack> iterator(int index) {
        requireNotOutOfBounds(index);
        return Arrays.asList(getContents()).listIterator(index);
    }

    @Override
    public Location getLocation() {
        return parent.getLocation();
    }

    /**
     * Returns the parent inventory, meaning the inventory containing the portion.
     *
     * @return the parent inventory
     */
    public Inventory getParent() {
        return parent;
    }

}
