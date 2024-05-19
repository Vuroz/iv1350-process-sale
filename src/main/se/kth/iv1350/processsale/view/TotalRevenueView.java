package se.kth.iv1350.processsale.view;

import se.kth.iv1350.processsale.model.RevenueObserver;

/**
 * Represents a view that displays the total revenue.
 */
public class TotalRevenueView implements RevenueObserver {

    /**
     * Called when the total revenue has changed.
     *
     * @param newTotalRevenue The new total revenue.
     */
    @Override
    public void update(double newTotalRevenue) {
        System.out.println("---- TOTAL REVENUE HAS CHANGED ----");
        System.out.printf("   NEW TOTAL REVENUE: %.2f SEK\n", newTotalRevenue);
        System.out.println("-----------------------------------");
    }
}
