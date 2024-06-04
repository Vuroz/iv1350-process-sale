package se.kth.iv1350.processsale.model.dto;

/**
 * Represents a discount in a sale. Contains information about the discount's percentage and raw value.
 */
public class DiscountDTO {
    private double percentage;
    private double raw;
    private double personalPercentage;

    /**
     * Creates a new instance of this class
     * @param percentage the percentage discount
     * @param raw the raw discount
     */
    public DiscountDTO(double percentage, double raw, double personalPercentage) {
        this.percentage = percentage;
        this.raw = raw;
        this.personalPercentage = personalPercentage;
    }

    /*
     * Getters
     */
    public double getPercentage() {
        return percentage;
    }

    public double getRaw() {
        return raw;
    }

    public double getPersonalPercentage() {
        return personalPercentage;
    }
}
