package se.kth.iv1350.processsale.model;

import se.kth.iv1350.processsale.model.dto.ItemDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a receipt of a sale. Contains information about the items sold, the total amount to pay, the total amount
 * paid in VAT, and the time of the sale.
 */
public class Receipt {
    private final ArrayList<ItemDTO> items;
    private final double runningTotal;
    private final double totalVAT;
    private final LocalDateTime saleTime;

    /**
     * Constructor, Creates the receipt based on items, the running total, and the total amount in VAT
     *
     * @param items All the items sold
     * @param total The total amount to pay
     * @param totalVAT The total amount paid in VAT
     * @param saleTime The time of the sale
     */
    public Receipt(ArrayList<ItemDTO> items, double total, double totalVAT, LocalDateTime saleTime) {
        this.items = items;
        this.runningTotal = total;
        this.totalVAT = totalVAT;
        this.saleTime = saleTime;
    }

    /**
     * Prints the receipt of the sale to <code>stdout</code>
     *
     * @param cashAmount the amount of cash the customer paid
     */
    public void printReceipt(double cashAmount) {
        System.out.println("------------------ Begin receipt ------------------");
        System.out.printf("Time of Sale: %s\n\n", saleTime.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")));

        HashMap<String, ReceiptItem> receiptItems = collectItemDTOToReceiptItem(items);

        for (ReceiptItem receiptItem : receiptItems.values()) {
            System.out.printf("%-20s %2.2f x %3.2f %13.2f SEK\n", receiptItem.getName(), receiptItem.getQuantity(), receiptItem.getPrice(), receiptItem.getQuantity() * receiptItem.getPrice());
        }

        System.out.printf("\nTotal:%41.2f SEK\n", runningTotal);
        System.out.printf("VAT: %-30.2f\n", totalVAT);

        System.out.printf("\nCash:  %40.2f SEK\n", cashAmount);
        System.out.printf("Change:%40.2f SEK\n", cashAmount - runningTotal);
        System.out.println("------------------- End receipt -------------------");
    }

    /**
     * Collects {@link ItemDTO}s into a hash map of {@link ReceiptItem}s. Items with the same <code>identifier</code>
     * gets their quantities added up.
     *
     * @param items list of {@link ItemDTO} to collect.
     * @return {@link HashMap} with the identifiers as keys and {@link ReceiptItem} as values
     */
    HashMap<String, ReceiptItem> collectItemDTOToReceiptItem(ArrayList<ItemDTO> items) { // PACKAGE PRIVATE SO TESTS ARE POSSIBLE
        HashMap<String, ReceiptItem> receiptItems = new HashMap<>();

        for (ItemDTO item : items) {
            if (!receiptItems.containsKey(item.getIdentifier())) {
                receiptItems.put(item.getIdentifier(), new ReceiptItem(item.getName(), item.getQuantity(), item.getPrice()));
            } else {
                receiptItems.get(item.getIdentifier()).updateQuantity(item.getQuantity());
            }
        }

        return receiptItems;
    }

    /**
     * Inner helper-class only used inside of {@link Receipt} to collect {@link ItemDTO}s
     */
    class ReceiptItem { // PACKAGE PRIVATE SO TESTS ARE POSSIBLE
        private String name;
        private double quantity;
        private double price;

        /**
         * Constructor for this class
         *
         * @param name the name of the item
         * @param quantity the initial quantity of the item
         * @param price the price of the item
         */
        ReceiptItem(String name, double quantity, double price) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        /**
         * Increases the quantity of this class
         *
         * @param quantityToAdd the quantity to add
         */
        void updateQuantity(double quantityToAdd) {
            this.quantity += quantityToAdd;
        }

        /*
         * Getters below
         */
        String getName() {
            return name;
        }

        double getQuantity() {
            return quantity;
        }

        double getPrice() {
            return price;
        }
    }
}
