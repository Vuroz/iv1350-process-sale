package se.kth.iv1350.processsale.model.dto;

/**
 * Represents an item in a sale. Contains information about the item's identifier, name, description, price, VAT, and
 * quantity.
 */
public class ItemDTO {
    private final String identifier;
    private final String name;
    private final String description;
    private final double price;
    private final double vat;
    private final double quantity;

    /**
     * Creates a new instance of this class
     *
     * @param identifier the item's identifier
     * @param name the item's name
     * @param description the item's description
     * @param price the item's price
     * @param vat the item's VAT
     * @param quantity the item's quantity
     */
    public ItemDTO(String identifier, String name, String description, double price, double vat, double quantity) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.price = price;
        this.vat = vat;
        this.quantity = quantity;
    }

    /*
     * Getters
     */
    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getVat() {
        return vat;
    }

    public double getQuantity() {
        return quantity;
    }
}
