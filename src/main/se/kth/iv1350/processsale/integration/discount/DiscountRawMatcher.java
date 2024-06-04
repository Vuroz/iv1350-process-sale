package se.kth.iv1350.processsale.integration.discount;

import se.kth.iv1350.processsale.model.dto.ItemDTO;

import java.util.ArrayList;

/**
 * This is a dummy class that represents a discount matcher that returns a discount based on the items in the sale.
 */
public class DiscountRawMatcher implements DiscountMatcher {
    /**
     * Returns a discount based on the items in the sale.
     */
    @Override
    public DiscountResultDTO getDiscount(ArrayList<ItemDTO> itemsInSale, double runningTotal, String customerID) {
        double discountAmount = 0;
        if (itemsInSale.size() > 2) discountAmount = 25.0;
        return new DiscountResultDTO(DiscountType.DISCOUNT_RAW, discountAmount);
    }
}
