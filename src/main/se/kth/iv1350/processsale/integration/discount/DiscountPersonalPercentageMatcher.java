package se.kth.iv1350.processsale.integration.discount;

import se.kth.iv1350.processsale.model.dto.ItemDTO;

import java.util.ArrayList;

/**
 * This class is a dummy discount matcher that returns a discount for a customer based on the customers' id.
 */
public class DiscountPersonalPercentageMatcher implements DiscountMatcher {
     /**
     * This method is used to get a discount that may be applied to the sale
     * 
     * @param itemsInSale The items that are in the sale
     * @param runningTotal The running total of the sale
     * @param customerID The customer ID of the customer
     * @return The resulting {@link DiscountResultDTO} that may be applied to the sale
     */
    @Override
    public DiscountResultDTO getDiscount(ArrayList<ItemDTO> itemsInSale, double runningTotal, String customerID) {
        double discountAmount = 0;
        if (customerID.equals("1")) discountAmount = 0.15;
        return new DiscountResultDTO(DiscountType.DISCOUNT_PERSONAL_PERCENTAGE, discountAmount);
    }
}
