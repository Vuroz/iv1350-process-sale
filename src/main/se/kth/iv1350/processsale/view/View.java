package se.kth.iv1350.processsale.view;

import se.kth.iv1350.processsale.controller.Controller;
import se.kth.iv1350.processsale.integration.NoSuchItemException;
import se.kth.iv1350.processsale.model.dto.SaleDTO;
import se.kth.iv1350.processsale.util.LogHandler;

/**
 * This class acts as a stand-in for the view. It has a fixed sequence of operations, calling all the system functions in the controller.
 */
public class View {
    private Controller controller;
    private LogHandler logHandler;

    /**
     * Creates a new instance of this class.
     *
     * @param controller The controller to be used in all the operations
     */
    public View(Controller controller, LogHandler logHandler) {
        this.controller = controller;
        this.logHandler = logHandler;
    }

    /**
     * Prints an error message to the user.
     * 
     * @param prefix The prefix to the error message
     * @param exception The exception that caused the error
     */
    private void showErrorToUser(String prefix, Exception exception) {
        System.out.print(prefix);
        System.out.println(exception.getMessage());
        System.out.println();
    }

    /**
     * Simulates the user actions of adding an item to the sale.
     *
     * @param identifier The identifier of the item to add
     * @param quantity The quantity of the item to add
     */
    private void simulateAddItemToSale(String identifier, double quantity) {
        try {
            SaleDTO saleDTO = controller.addItemToSale(identifier, quantity);
            System.out.printf("Add %.2f item with item id %s:\n", saleDTO.getLastItemAdded().getQuantity(), saleDTO.getLastItemAdded().getIdentifier());
            System.out.printf("Item ID: %s\n", saleDTO.getLastItemAdded().getIdentifier());
            System.out.printf("Item name: %s\n", saleDTO.getLastItemAdded().getName());
            System.out.printf("Item cost: %.2f SEK\n", saleDTO.getLastItemAdded().getPrice());
            System.out.printf("VAT: %.2f%s\n", saleDTO.getLastItemAdded().getVat(), "%");
            System.out.printf("Item description: %s\n", saleDTO.getLastItemAdded().getDescription());
            System.out.printf("\nTotal cost (incl VAT): %.2f SEK\n", saleDTO.getRunningTotal());
            System.out.printf("Total VAT: %.2f SEK\n\n", saleDTO.getTotalVat());
        } catch (NoSuchItemException noSuchItemException) {
            showErrorToUser("Item could not be added to sale: ", noSuchItemException);
        } catch (Exception exception) {
            showErrorToUser("Item could not be added to sale: ", exception);
            logHandler.logException(exception);
        }
    }

    /**
     * Simulates the user actions from the Process Sale scenario.
     */
    public void simulateSale() {
        controller.startNewSale();

        simulateAddItemToSale("abc123", 1.0);
        simulateAddItemToSale("WILL_FAIL", 2.0);
        simulateAddItemToSale("def456", 1.0);
        simulateAddItemToSale("abc123", 2.0);
        simulateAddItemToSale("asdasd", 2.0);

        controller.endSale();

        System.out.printf("Customer pays %.2f SEK:\n", 1000.00);
        double change = controller.pay(1000.0);
        System.out.printf("\nChange to give to the customer: %2.2f SEK", change);
    }
}