package se.kth.iv1350.processsale.integration;

import se.kth.iv1350.processsale.model.Sale;

/**
 * Represents the external accounting system
 */
public class ExternalAccountingSystem {
    public ExternalAccountingSystem() {

    }

    /**
     * Updates the external accounting system based on the sale
     *
     * @param sale the finished sale
     */
    public void updateAccountingFromSale(Sale sale) {
        System.out.println("Sent sale info to external accounting system.");
    }
}
