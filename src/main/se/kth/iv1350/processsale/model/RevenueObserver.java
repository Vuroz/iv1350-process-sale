package se.kth.iv1350.processsale.model;

/**
 * An interface for classes that want to observe the total revenue.
 */
public interface RevenueObserver {
    /**
     * Invoked when the total revenue has been updated.
     *
     * @param newTotalRevenue The new total revenue.
     */
    void update(double newTotalRevenue);
}
