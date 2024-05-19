package se.kth.iv1350.processsale.model;

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
    private Receipt receipt;

    /**
     * The constructor, called when creating a new instance of this class.
     */
    public Sale() {
        items = new ArrayList<>();
        runningTotal = 0.0;
        totalVAT = 0.0;
        saleTime = LocalDateTime.now();
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

        receipt = new Receipt(items, runningTotal, totalVAT, saleTime);

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
