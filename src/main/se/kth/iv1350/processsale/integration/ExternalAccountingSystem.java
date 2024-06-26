package se.kth.iv1350.processsale.integration;

import se.kth.iv1350.processsale.model.dto.SaleDTO;

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
    public void updateAccountingFromSale(SaleDTO sale) {
        System.out.println("Sent sale info to external accounting system.");
    }
}
