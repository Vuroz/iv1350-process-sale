package se.kth.iv1350.processsale.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class is a DTO that contains information about a sale.
 */
public class SaleDTO {
    private final LocalDateTime saleTime;
    private final ArrayList<ItemDTO> items;
    private final ItemDTO lastItemAdded;
    private final double runningTotal, totalVat;

    /**
     * Creates an instance of <code>SaleDTO</code>.
     *
     * @param saleTime The time of the sale.
     * @param items The items that were scanned during the sale.
     * @param lastItemAdded The last item that was added to the sale.
     * @param runningTotal The running total of the sale.
     * @param totalVat The total VAT of the sale.
     */
    public SaleDTO(LocalDateTime saleTime, ArrayList<ItemDTO> items, ItemDTO lastItemAdded, double runningTotal, double totalVat) {
        this.saleTime = saleTime;
        this.items = items;
        this.lastItemAdded = lastItemAdded;
        this.runningTotal = runningTotal;
        this.totalVat = totalVat;
    }

    /*
     * Getters
     */
    public LocalDateTime getSaleTime() {
        return saleTime;
    }

    public ArrayList<ItemDTO> getItems() {
        return items;
    }

    public ItemDTO getLastItemAdded() {
        return lastItemAdded;
    }

    public double getRunningTotal() {
        return runningTotal;
    }

    public double getTotalVat() {
        return totalVat;
    }
}
