package se.kth.iv1350.processsale.integration;

import se.kth.iv1350.processsale.integration.discount.*;
import se.kth.iv1350.processsale.model.dto.DiscountDTO;
import se.kth.iv1350.processsale.model.dto.ItemDTO;

import java.util.ArrayList;

/**
 * This is a dummy database for discounts. It contains a hardcoded table of discounts for customers.
 */
public class DiscountDatabase {
    private ArrayList<DiscountMatcher> discountMatchers;

    public DiscountDatabase() {
        discountMatchers = new ArrayList<>();
        discountMatchers.add(new DiscountPercentageMatcher());
        discountMatchers.add(new DiscountRawMatcher());
        discountMatchers.add(new DiscountPersonalPercentageMatcher());

    }

    /**
     * Returns the discount for a customer.
     * 
     * @param itemsInSale The items in the sale.
     * @param runningTotal The running total of the sale.
     * @param customerID The customer ID.
     * @return A {@link DiscountDTO} containing the discount information.
     */
    public DiscountDTO getDiscount(ArrayList<ItemDTO> itemsInSale, double runningTotal, String customerID) {
        double percentage = 0, raw = 0, personal = 0;
        for (DiscountMatcher discountMatcher : discountMatchers) {
            DiscountResultDTO discountResult = discountMatcher.getDiscount(itemsInSale, runningTotal, customerID);
            switch (discountResult.getDiscountType()) {
                case DISCOUNT_PERCENTAGE:
                    percentage = discountResult.getDiscountAmount();
                    break;
                case DISCOUNT_RAW:
                    raw = discountResult.getDiscountAmount();
                    break;
                case DISCOUNT_PERSONAL_PERCENTAGE:
                    personal = discountResult.getDiscountAmount();
                    break;
                default:
                    break;
            }
        }
        return new DiscountDTO(percentage, raw, personal);
    }
}
