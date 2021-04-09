package gcampini.spigotutils.itemstack.interaction;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

/**
 * @author Gil CAMPINI
 */
class ComposedItemStackInteraction<T extends Event> implements ItemStackInteraction<T> {

    private final Collection<ItemStackInteraction<T>> children;

    ComposedItemStackInteraction(Collection<ItemStackInteraction<T>> children) {
        this.children = children;
    }

    @Override
    public ItemStack extractItemStack(T event) {
        for (ItemStackInteraction<T> interaction : children) {
            ItemStack extracted = interaction.extractItemStack(event);
            if (extracted != null) return extracted;
        }
        return null;
    }

    public Collection<ItemStackInteraction<T>> getChildren() {
        return children;
    }

}
