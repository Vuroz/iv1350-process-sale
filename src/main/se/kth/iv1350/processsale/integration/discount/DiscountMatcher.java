package se.kth.iv1350.processsale.integration.discount;

import se.kth.iv1350.processsale.model.dto.ItemDTO;

import java.util.ArrayList;

/**
 * This interface is used to match discounts to a sale USING THE STRATEGY PATTERN
 */
public interface DiscountMatcher {
    DiscountResultDTO getDiscount(ArrayList<ItemDTO> itemsInSale, double runningTotal, String customerID);
}

