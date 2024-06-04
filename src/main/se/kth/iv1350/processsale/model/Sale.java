package se.kth.iv1350.processsale.model;

import se.kth.iv1350.processsale.model.dto.DiscountDTO;
import se.kth.iv1350.processsale.model.dto.ItemDTO;
import se.kth.iv1350.processsale.model.dto.SaleDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a sale of items. Contains information about the items sold, the total amount to pay, the total amount
 * paid in VAT, and the time of the sale.
 */
public class Sale {
    private LocalDateTime saleTime;
    private ArrayList<ItemDTO> items;
    private double runningTotal, totalVAT;
    private DiscountDTO discount;
    private Receipt receipt;
    private ArrayList<RevenueObserver> revenueObservers;
    private static double totalRevenue = 0.0;

    /**
     * The constructor, called when creating a new instance of this class.
     */
    public Sale() {
        items = new ArrayList<>();
        revenueObservers = new ArrayList<>();
        runningTotal = 0.0;
        totalVAT = 0.0;
        saleTime = LocalDateTime.now();
        discount = new DiscountDTO(0.0, 0.0, 0.0);
    }

    /**
     * Adds an item to the current sale
     *
     * @param item the item to add
     * @return a copy of the sale after the item has been added
     */
    public SaleDTO addItem(ItemDTO item) {
        items.add(item);
        runningTotal += item.getPrice() * item.getQuantity();

        return createDTOFromSale();
    }

    /**
     * Ends the current tale
     *
     * @return the running total for the sale
     */
    public double endSale() {
        totalVAT = calculateTotalVAT();

        receipt = new Receipt(items, runningTotal, totalVAT, saleTime, discount);

        notifyObservers();

        return runningTotal;
    }

    /**
     * Calculates the total amount paid in VAT
     *
     * @return total amount paid in VAT
     */
    private double calculateTotalVAT() {
        totalVAT = 0;
        for (ItemDTO item : items) {
            totalVAT += item.getPrice() * item.getQuantity() * item.getVat() / 100.0;
        }

        return totalVAT;
    }

    /**
     * Creates a DTO from the current sale
     *
     * @return the DTO
     */
    public SaleDTO createDTOFromSale() {
        ArrayList<ItemDTO> copyOfItems = deepCopyItems();
        ItemDTO lastItemAdded = null;
        if (!copyOfItems.isEmpty()) {
            lastItemAdded = copyOfItems.get(copyOfItems.size() - 1);
        }

        return new SaleDTO(LocalDateTime.from(saleTime), copyOfItems, lastItemAdded, runningTotal, calculateTotalVAT());
    }

    /**
     * Deep copies the current list of Items
     * 
     * @return A <code>ArrayList</code> of {@link ItemDTO}s
     */
    private ArrayList<ItemDTO> deepCopyItems() {
        ArrayList<ItemDTO> copyOfItems = new ArrayList<>();
        for (ItemDTO itemDTO : items) {
            copyOfItems.add(new ItemDTO(itemDTO.getIdentifier(),
                    itemDTO.getName(),
                    itemDTO.getDescription(),
                    itemDTO.getPrice(),
                    itemDTO.getVat(),
                    itemDTO.getQuantity()));
        }
        return copyOfItems;
    }

    /**
     * Adds a revenue observer to the list
     * 
     * @param observer The observer to add
     */
    public void addObserver(RevenueObserver observer) {
        this.revenueObservers.add(observer);
    }

    /**
     * Applies a discount to the sale
     * 
     * @param discountDTO The discount to apply
     */
    public void applyDiscount(DiscountDTO discountDTO) {
        this.discount = discountDTO;
    }

    /**
     * Notifies observers of a revenue change
     */
    private void notifyObservers() {
        totalRevenue += (((this.getRunningTotal() * (1 - discount.getPercentage())) - discount.getRaw()) * (1 - discount.getPersonalPercentage()));
        for (RevenueObserver revenueObserver : revenueObservers) {
            revenueObserver.update(totalRevenue);
        }
    }

    /*
     * Getters
     */
    public ArrayList<ItemDTO> getItems() {
        return deepCopyItems();
    }

    public double getRunningTotal() {
        return runningTotal;
    }

    public Receipt getReceipt() {
        return receipt;
    }
}
