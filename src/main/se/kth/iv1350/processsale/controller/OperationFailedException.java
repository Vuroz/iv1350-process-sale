package se.kth.iv1350.processsale.controller;

/**
 * Thrown when an operation fails, and the reason is given in the message
 */
public class OperationFailedException extends RuntimeException {
    /**
     * Creates a new instance representing the condition described in the specified message.
     *
     * @param message A message that describes what went wrong.
     */
    public OperationFailedException(String message) {
        super(message);
    }
}
