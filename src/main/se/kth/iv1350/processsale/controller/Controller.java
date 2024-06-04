package se.kth.iv1350.processsale.controller;

import se.kth.iv1350.processsale.integration.*;
import se.kth.iv1350.processsale.model.RevenueObserver;
import se.kth.iv1350.processsale.model.Sale;
import se.kth.iv1350.processsale.model.dto.DiscountDTO;
import se.kth.iv1350.processsale.model.dto.ItemDTO;
import se.kth.iv1350.processsale.model.dto.SaleDTO;

import java.util.ArrayList;

/**
 * This is the application's only controller. All calls to the model pass through here.
 */
public class Controller {
    private Sale sale;
    private ExternalAccountingSystem externalAccountingSystem;
    private ExternalInventorySystem externalInventorySystem;
    private DiscountDatabase discountDatabase;
    private ArrayList<RevenueObserver> revenueObservers;



    /**
     * Creates a new instance of this class, which also initializes the external systems.
     */
    public Controller() {
        externalInventorySystem = new ExternalInventorySystem();
        externalAccountingSystem = new ExternalAccountingSystem();
        discountDatabase = new DiscountDatabase();
        revenueObservers = new ArrayList<>();
    }

    /**
     * Starts a new sale, overwriting the previous one.
     */
    public void startNewSale() {
        sale = new Sale();
        for (RevenueObserver revenueObserver : revenueObservers) {
            sale.addObserver(revenueObserver);
        }
    }

    /**
     * Adds the item to sale using its itemIdentifier
     *
     * @param itemIdentifier the identifier of the item to add to sale
     * @param quantity quantity of the item to add
     * @return a copy of the current sale after the item has been added
     * @throws NoSuchItemException if the item does not exist in the inventory
     * @throws OperationFailedException if the operation failed
     */
    public SaleDTO addItemToSale(String itemIdentifier, double quantity) throws NoSuchItemException, OperationFailedException {
        try {
            String itemInformation = externalInventorySystem.getInformation(itemIdentifier);
            ItemDTO item = parseItemInformation(itemIdentifier, itemInformation, quantity);
            return sale.addItem(item);
        } catch (InventoryConnectionFailedException e) {
            throw new OperationFailedException("Could not connect to the external inventory system, try again");
        }
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
     * Applies a discount to the sale
     * 
     * @param customerID the ID of the customer
     */
    public void applyDiscountToSale(String customerID) {
        DiscountDTO discountDTO = discountDatabase.getDiscount(sale.getItems(), sale.getRunningTotal(), customerID);
        sale.applyDiscount(discountDTO);
    }

    /**
     * Adds a revenue observer to the list of revenue observers
     *
     * @param revenueObserver the revenue observer to add
     */
    public void addRevenueObserver(RevenueObserver revenueObserver) {
        revenueObservers.add(revenueObserver);
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
        externalAccountingSystem.updateAccountingFromSale(sale.createDTOFromSale());
        externalInventorySystem.updateInventoryFromSale(sale.createDTOFromSale());

        sale.getReceipt().printReceipt(cashTotal);

        return cashTotal - sale.getRunningTotal();
    }

    /*
     * Getters
     */
    Sale getSale() { // ONLY USED FOR TESTING AND THEREFORE PACKAGE PRIVATE
        return sale;
    }
}
