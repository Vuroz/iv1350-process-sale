package se.kth.iv1350.processsale.integration.discount;

import se.kth.iv1350.processsale.model.dto.ItemDTO;

import java.util.ArrayList;

/**
 * This is a dummy class that represents a discount matcher that matches a percentage discount based on the running total.
 */
public class DiscountPercentageMatcher implements DiscountMatcher {
    /**
     * Returns a discount based on the running total.
     */
    @Override
    public DiscountResultDTO getDiscount(ArrayList<ItemDTO> itemsInSale, double runningTotal, String customerID) {
        double discountAmount = 0;
        /**/ if (runningTotal > 80.0) discountAmount = 0.20;
        else if (runningTotal > 50.0) discountAmount = 0.15;
        else if (runningTotal > 25.0) discountAmount = 0.10;
        else if (runningTotal > 10.0) discountAmount = 0.05;
        return new DiscountResultDTO(DiscountType.DISCOUNT_PERCENTAGE, discountAmount);
    }
}
