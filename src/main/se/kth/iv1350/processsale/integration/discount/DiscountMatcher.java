package se.kth.iv1350.processsale.integration.discount;

import se.kth.iv1350.processsale.model.dto.ItemDTO;

import java.util.ArrayList;

/**
 * This interface is used to match discounts to a sale USING THE STRATEGY PATTERN
 */
public interface DiscountMatcher {
    /**
     * This method is used to get a discount that may be applied to the sale
     * 
     * @param itemsInSale The items that are in the sale
     * @param runningTotal The running total of the sale
     * @param customerID The customer ID of the customer
     * @return The resulting {@link DiscountResultDTO} that may be applied to the sale
     */
    DiscountResultDTO getDiscount(ArrayList<ItemDTO> itemsInSale, double runningTotal, String customerID);
}

