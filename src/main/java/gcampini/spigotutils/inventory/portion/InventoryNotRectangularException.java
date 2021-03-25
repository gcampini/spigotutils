package gcampini.spigotutils.inventory.portion;

/**
 * @author Gil CAMPINI
 */
public class InventoryNotRectangularException extends Exception {

    public InventoryNotRectangularException() {
        super("the inventory does not have a rectangular shape");
    }

}
