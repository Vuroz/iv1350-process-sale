package se.kth.iv1350.processsale.model;

import se.kth.iv1350.processsale.model.dto.ItemDTO;

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
     * @return the new running total
     */
    public double addItem(ItemDTO item) {
        System.out.printf("Add %.2f item with item id %s:\n", item.getQuantity(), item.getIdentifier());
        System.out.printf("Item ID: %s\n", item.getIdentifier());
        System.out.printf("Item name: %s\n", item.getName());
        System.out.printf("Item cost: %.2f SEK\n", item.getPrice());
        System.out.printf("VAT: %.2f%s\n", item.getVat(), "%");
        System.out.printf("Item description: %s\n", item.getDescription());

        items.add(item);
        runningTotal += item.getPrice() * item.getQuantity();

        System.out.printf("\nTotal cost (incl VAT): %.2f SEK\n", runningTotal);
        System.out.printf("Total VAT: %.2f SEK\n\n", calculateTotalVAT());

        return runningTotal;
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
        for (ItemDTO item : items) {
            totalVAT += item.getPrice() * item.getQuantity() * item.getVat() / 100.0;
        }

        return totalVAT;
    }

    /*
     * Getters
     */
    public ArrayList<ItemDTO> getItems() {
        return items;
    }

    public double getRunningTotal() {
        return runningTotal;
    }

    public Receipt getReceipt() {
        return receipt;
    }
}
