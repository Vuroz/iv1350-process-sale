package se.kth.iv1350.processsale.integration.discount;

/**
 * This class represents the result of a discount calculation.
 */
public class DiscountResultDTO {
    private DiscountType discountType;
    private double discountAmount;

    /**
     * Creates a new instance of a discount result.
     * 
     * @param discountType The type of discount.
     * @param discountAmount The amount of the discount.
     */
    public DiscountResultDTO(DiscountType discountType, double discountAmount) {
        this.discountType = discountType;
        this.discountAmount = discountAmount;
    }

    /*
     * Getters
     */
    public DiscountType getDiscountType() {
        return discountType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}
