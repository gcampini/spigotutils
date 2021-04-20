package gcampini.spigotutils.itemstack;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public ItemStackBuilder(@NotNull ItemStack stack) {
        this.stack = new ItemStack(stack);
    }

    public ItemStackBuilder(@NotNull Material material) {
        this.stack = new ItemStack(material);
    }

    public ItemStackBuilder() {
        this(Material.AIR);
    }

    @NotNull
    public ItemStackBuilder meta(@NotNull Consumer<ItemMeta> modifier) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) modifier.accept(meta);
        stack.setItemMeta(meta);
        return this;
    }

    @NotNull
    public ItemStackBuilder data(@NotNull Consumer<PersistentDataContainer> modifier) {
        return meta(meta -> modifier.accept(meta.getPersistentDataContainer()));
    }

    @NotNull
    public ItemStackBuilder type(@Nullable Material type) {
        if (type == null) type = Material.AIR;
        stack.setType(type);
        return this;
    }

    @NotNull
    public ItemStackBuilder amount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    @NotNull
    public ItemStackBuilder name(@Nullable String name) {
        return meta(meta -> meta.setDisplayName(name));
    }

    @NotNull
    public ItemStackBuilder lore(@Nullable List<String> lore) {
        return meta(meta -> meta.setLore(lore));
    }

    @NotNull
    public ItemStackBuilder lore(@NotNull String... lore) {
        return lore(Arrays.asList(lore));
    }

    @NotNull
    public ItemStack build() {
        return new ItemStack(stack);
    }

}
