package se.kth.iv1350.processsale.integration;

/**
 * Thrown when an item with a specific item identifier could not be found in the inventory system.
 */
public class NoSuchItemException extends Exception {
    /**
     * Creates a new instance of the exception with a message specifying which item could not be found.
     * 
     * @param itemIdentifier The identifier of the item that could not be found.
     */
    public NoSuchItemException(String itemIdentifier) {
        super(String.format("Item with ID: %s, could not be found", itemIdentifier));
    }
}
