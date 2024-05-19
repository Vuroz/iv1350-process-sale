package se.kth.iv1350.processsale.integration;

/**
 * Thrown when the connection to the external inventory system fails
 */
public class InventoryConnectionFailedException extends RuntimeException {
    /**
     * Creates a new instance representing the condition described in the specified message.
     *
     * @param message A message that describes what went wrong.
     */
    public InventoryConnectionFailedException(String message) {
        super(message);
    }
}
