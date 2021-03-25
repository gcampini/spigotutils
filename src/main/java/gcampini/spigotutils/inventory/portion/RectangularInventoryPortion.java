package gcampini.spigotutils.inventory.portion;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

/**
 * @author Gil CAMPINI
 */
public class RectangularInventoryPortion extends InventoryPortion {

    private final int width, height;

    public enum InventorySeekingPoint {

        TOP_LEFT(0, 0),
        TOP_RIGHT(1, 0),
        BOTTOM_RIGHT(1, 1),
        BOTTOM_LEFT(0, 1),
        CENTER(0.5, 0.5);

        private final double x, y;

        InventorySeekingPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }

        private int toSlot(RectangularInventoryPortion portion) {
            return (int) (y * (portion.height - 1) * portion.width + x * (portion.width - 1));
        }

    }

    /**
     * Creates a {@code RectangularInventoryPortion} from an origin slot and dimension.
     * It does not check whether the portion goes beyond the bounds of the parent inventory.
     * TODO check if possible
     *
     * @param inventory the parent inventory
     * @param origin    the origin slot number of the parent inventory (top left slot of the portion)
     * @param width     the width of the portion
     * @param height    the height of the portion
     */
    public RectangularInventoryPortion(Inventory inventory, int origin, int width, int height) {
        super(inventory, map(origin, width, height));
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a {@code RectangularInventoryPortion} of the whole inventory.
     *
     * @param inventory the parent inventory
     * @throws InventoryNotRectangularException if the inventory is not rectangular
     */
    public RectangularInventoryPortion(Inventory inventory) throws InventoryNotRectangularException {
        this(inventory, 0, getWidth(inventory), getHeight(inventory));
    }

    private static int getWidth(Inventory inventory) throws InventoryNotRectangularException {
        Objects.requireNonNull(inventory, "inventory is null");
        if (inventory instanceof RectangularInventoryPortion) {
            return ((RectangularInventoryPortion) inventory).getWidth();
        } else if (inventory instanceof InventoryPortion) {
            throw new IllegalArgumentException("cannot determine dimension because the inventory is an InventoryPortion, not necessarily a RectangularInventoryPortion");
        }
        return switch (Objects.requireNonNull(inventory, "inventory is null").getType()) {
            case BARREL, CHEST, ENDER_CHEST, PLAYER -> 9;
            case HOPPER -> 5;
            case DROPPER, DISPENSER, WORKBENCH -> 3;
            default -> throw new InventoryNotRectangularException();
        };
    }

    private static int getHeight(Inventory inventory) throws InventoryNotRectangularException {
        return inventory.getSize() / getWidth(inventory);
    }

    private static int[] map(int origin, int width, int height) {
        int i = 0;
        int[] mapping = new int[width * height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++, i++) {
                mapping[i] = origin + row * width + col;
            }
        }
        return mapping;
    }

    public void setItem(int x, int y, ItemStack stack) {
        if (x < 0 || x > width || y < 0 || y > height) throw new IndexOutOfBoundsException("coords out of bounds");
        setItem(y * width + x, stack);
    }

    public void setItem(InventorySeekingPoint seekingPoint, ItemStack stack) {
        setItem(seekingPoint, 0, stack);
    }

    public void setItem(InventorySeekingPoint seekingPoint, int offset, ItemStack stack) {
        setItem(seekingPoint.toSlot(this) + offset, stack);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
