package se.kth.iv1350.processsale.integration.discount;

import se.kth.iv1350.processsale.model.dto.ItemDTO;

import java.util.ArrayList;

/**
 * This class is a dummy discount matcher that returns a discount for a customer based on the customers' id.
 */
public class DiscountPersonalPercentageMatcher implements DiscountMatcher {
    /**
     * Returns a discount based on the customers' id.
     */
    @Override
    public DiscountResultDTO getDiscount(ArrayList<ItemDTO> itemsInSale, double runningTotal, String customerID) {
        double discountAmount = 0;
        if (customerID.equals("1")) discountAmount = 0.15;
        return new DiscountResultDTO(DiscountType.DISCOUNT_PERSONAL_PERCENTAGE, discountAmount);
    }
}
