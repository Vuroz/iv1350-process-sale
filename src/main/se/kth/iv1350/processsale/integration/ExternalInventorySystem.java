package se.kth.iv1350.processsale.integration;

import se.kth.iv1350.processsale.model.Sale;
import se.kth.iv1350.processsale.model.dto.ItemDTO;

import java.util.HashMap;

/**
 * Represents an external inventory system. Contains a dummy database for process sale
 */
public class ExternalInventorySystem {
    private HashMap<String, String> itemInformationTable;
    private HashMap<String, Double> itemAmountTable;

    /**
     * Constructor, initializes a new instance of this class. Creates a dummy database for process sale
     */
    public ExternalInventorySystem() {
        itemInformationTable = new HashMap<>();
        itemInformationTable.put("1337", "Milk|1L 3.5% Fat|12.95|12.0");
        itemInformationTable.put("1", "Baked Bread|Freshly baked bread, baked daily in the store, 1kg|49.95|12.0");
        itemInformationTable.put("3op", "Shampoo|500mL, only natural ingredients, 7in1, anti dandruff|19.95|25.0");
        itemInformationTable.put("42", "Meat|Beef tenderloin, 300grams, superdeal|69.95|12.0");
        itemInformationTable.put("5", "Potato Chips|Kettle-cooked in sunflower oil, 150g|58.99|12.0");
        itemInformationTable.put("abc123", "BigWheel Oatmeal|BigWheel Oatmeal 500g, whole grain oats, high fiber, gluten free|29.90|6.0");
        itemInformationTable.put("def456", "YouGoGo Blueberry|YouGoGo Blueberry 240g, low sugar yoghurt, blueberry flavor|14.90|6.0");

        itemAmountTable = new HashMap<>();
        itemAmountTable.put("1337", 200.0);
        itemAmountTable.put("1", 200.0);
        itemAmountTable.put("3op", 200.0);
        itemAmountTable.put("42", 200.0);
        itemAmountTable.put("5", 200.0);
        itemAmountTable.put("abc123", 200.0);
        itemAmountTable.put("def456", 200.0);

    }

    /**
     * Returns item information about the specifier <code>identifier</code> from the database
     *
     * @param itemIdentifier the identifier to query the information
     * @return information regarding the <code>identifier</code>
     */
    public String getInformation(String itemIdentifier) {
        return itemInformationTable.get(itemIdentifier);
    }

    /**
     * Updates store inventory based on the finished sale
     *
     * @param sale the finished sale to use as base when updating
     */
    public void updateInventoryFromSale(Sale sale) {
        HashMap<String, Double> amountInSale = new HashMap<>();

        for (ItemDTO item : sale.getItems()) {
            if (!amountInSale.containsKey(item.getIdentifier())) {
                amountInSale.put(item.getIdentifier(), 0.0);
            }
            amountInSale.put(item.getIdentifier(), amountInSale.get(item.getIdentifier()) + item.getQuantity());
        }

        for (HashMap.Entry<String, Double> entry: amountInSale.entrySet()) {
            System.out.printf("Told external inventory system to decrease quantity of item %s by %.2f units\n", entry.getKey(), entry.getValue());
        }
    }
}
