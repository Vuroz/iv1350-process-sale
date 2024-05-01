package se.kth.iv1350.processsale.controller;

import se.kth.iv1350.processsale.integration.ExternalAccountingSystem;
import se.kth.iv1350.processsale.integration.ExternalInventorySystem;
import se.kth.iv1350.processsale.model.Sale;
import se.kth.iv1350.processsale.model.dto.ItemDTO;

/**
 * This is the application's only controller. All calls to the model pass through here.
 */
public class Controller {
    private Sale sale;
    private ExternalAccountingSystem externalAccountingSystem;
    private ExternalInventorySystem externalInventorySystem;

    /**
     * Creates a new instance of this class, which also initializes the external systems.
     */
    public Controller() {
        externalInventorySystem = new ExternalInventorySystem();
        externalAccountingSystem = new ExternalAccountingSystem();
    }

    /**
     * Starts a new sale, overwriting the previous one.
     */
    public void startNewSale() {
        sale = new Sale();
    }

    /**
     * Adds the item to sale using its itemIdentifier
     *
     * @param itemIdentifier the identifier of the item to add to sale
     * @param quantity quantity of the item to add
     * @return the running total after the item has been added
     */
    public double addItemToSale(String itemIdentifier, double quantity) {
        String itemInformation = externalInventorySystem.getInformation(itemIdentifier);
        ItemDTO item = parseItemInformation(itemIdentifier, itemInformation, quantity);
        return sale.addItem(item);
    }

    /**
     * Creates a new {@link ItemDTO} from the specifier <code>itemInformation</code>
     *
     * @param itemIdentifier the identifier for the item
     * @param itemInformation the information from the database to convert into an {@link ItemDTO}
     * @param quantity the quantity of this item
     * @return a new {@link ItemDTO} based on the parameters
     */
    private ItemDTO parseItemInformation(String itemIdentifier, String itemInformation, double quantity) {
        String[] itemInformationEntries = itemInformation.split("\\|");
        String name = itemInformationEntries[0];
        String description = itemInformationEntries[1];
        double price = Double.parseDouble(itemInformationEntries[2]);
        double vatPercentage = Double.parseDouble(itemInformationEntries[3]);
        return new ItemDTO(itemIdentifier, name, description, price, vatPercentage, quantity);
    }

    /**
     * Ends the current sale
     *
     * @return the total amount due
     */
    public double endSale() {
        return sale.endSale();
    }

    /**
     * Handles payment for the sale
     *
     * @param cashTotal the total amount of cash paid
     * @return the change to give back
     */
    public double pay(double cashTotal) {
        System.out.printf("Customer pays %.2f SEK:\n", cashTotal);
        externalAccountingSystem.updateAccountingFromSale(sale);
        externalInventorySystem.updateInventoryFromSale(sale);

        sale.getReceipt().printReceipt(cashTotal);

        System.out.printf("\nChange to give to the customer: %2.2f SEK", cashTotal - sale.getRunningTotal());

        return cashTotal - sale.getRunningTotal();
    }

    /*
     * Getters
     */
    Sale getSale() { // ONLY USED FOR TESTING AND THEREFORE PACKAGE PRIVATE
        return sale;
    }
}
