package se.kth.iv1350.processsale.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.processsale.model.dto.ItemDTO;
import se.kth.iv1350.processsale.model.dto.SaleDTO;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {
    private Sale instanceToTest;
    private ItemDTO item1, item2;

    @BeforeEach
    void setUp() {
        instanceToTest = new Sale();
        item1 = new ItemDTO("abc123",
                "BigWheel Oatmeal",
                "BigWheel Oatmeal 500g, whole grain oats, high fiber, gluten free",
                29.90,
                6.0,
                1.0);
        item2 = new ItemDTO("def456",
                "YouGoGo Blueberry",
                "YouGoGo Blueberry 240g, low sugar yoghurt, blueberry flavor",
                14.90,
                6.0,
                1);
    }

    @AfterEach
    void tearDown() {
        instanceToTest = null;
        item1 = null;
        item2 = null;
    }

    @Test
    void addItem() {
        SaleDTO saleDTO = instanceToTest.addItem(item1);
        assertEquals(29.90, saleDTO.getRunningTotal(), "Running total after adding abc123 should be 29.90");
        assertEquals(1, instanceToTest.getItems().size(), "Size of items after adding abc123 should be 1");
        assertEquals(item1.getIdentifier(), instanceToTest.getItems().get(0).getIdentifier(), "First item in the list should be abc123");

        saleDTO = instanceToTest.addItem(item2);
        assertEquals(44.80, saleDTO.getRunningTotal(), "Running total after adding def456 should be 44.80");
        assertEquals(2, instanceToTest.getItems().size(), "Size of items after adding def456 should be 2");
        assertEquals(item2.getIdentifier(), instanceToTest.getItems().get(1).getIdentifier(), "Second item in the list should be def456");
    }

    @Test
    void endSale() {
        instanceToTest.addItem(item1);
        instanceToTest.addItem(item2);
        double runningTotal = instanceToTest.endSale();
        assertEquals(44.80, runningTotal, "Running total after ending sale should be 44.80");
        assertNotNull(instanceToTest.getReceipt(), "Receipt should not be null after ending sale");
    }
}