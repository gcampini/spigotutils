package gcampini.spigotutils.itemstack;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class for easily building {@link ItemStack}s.
 *
 * @author Gil CAMPINI
 */
public class ItemStackBuilder {

    private final ItemStack stack;

    public ItemStackBuilder(ItemStack stack) {
        this.stack = new ItemStack(stack);
    }

    public ItemStackBuilder(Material material) {
        this.stack = new ItemStack(material);
    }

    public ItemStackBuilder() {
        this(Material.AIR);
    }

    public ItemStackBuilder meta(Consumer<ItemMeta> modifier) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) modifier.accept(meta);
        stack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder type(Material type) {
        stack.setType(type);
        return this;
    }

    public ItemStackBuilder amount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder name(String name) {
        return meta(meta -> meta.setDisplayName(name));
    }

    public ItemStackBuilder lore(List<String> lore) {
        return meta(meta -> meta.setLore(lore));
    }

    public ItemStackBuilder lore(String ...lore) {
        return lore(Arrays.asList(lore));
    }

    public ItemStack build() {
        return new ItemStack(stack);
    }

}
