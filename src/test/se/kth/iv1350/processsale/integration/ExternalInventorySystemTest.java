package se.kth.iv1350.processsale.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.processsale.model.Sale;
import se.kth.iv1350.processsale.model.dto.ItemDTO;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ExternalInventorySystemTest {
    private ExternalInventorySystem instanceToTest;
    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;

    @BeforeEach
    void setUp() {
        instanceToTest = new ExternalInventorySystem();

        printoutBuffer = new ByteArrayOutputStream();
        PrintStream inMemSysOut = new PrintStream(printoutBuffer);
        originalSysOut = System.out;
        System.setOut(inMemSysOut);
    }

    @AfterEach
    void tearDown() {
        instanceToTest = null;

        printoutBuffer = null;
        System.setOut(originalSysOut);
    }

    @Test
    void getInformation() {
        String information;
        try {
            information = instanceToTest.getInformation("abc123");
        } catch (NoSuchItemException e) {
            throw new RuntimeException(e);
        }

        assertEquals("BigWheel Oatmeal|BigWheel Oatmeal 500g, whole grain oats, high fiber, gluten free|29.90|6.0", information);

        NoSuchItemException invalidItemException = assertThrows(NoSuchItemException.class, () -> {
            instanceToTest.getInformation("A invalid item ID");
        }, "Inventory returned information about an item which does not exist");

        assertEquals("Item with ID: A invalid item ID, could not be found", invalidItemException.getMessage(), "Exception message was not correct");

        InventoryConnectionFailedException noConnectionException = assertThrows(InventoryConnectionFailedException.class, () -> {
            instanceToTest.getInformation("WILL_FAIL");
        }, "Inventory returned information when it should have created an exception");

        assertEquals("Could not connect to the external inventory system", noConnectionException.getMessage(), "Exception message was not correct");
    }

    @Test
    void updateInventoryFromSale() {
        Sale testSale = new Sale();
        ItemDTO testItem = new ItemDTO("abc123", "BigWheel Oatmeal", "BigWheel Oatmeal 500g, whole grain oats, high fiber, gluten free", 29.90, 6.0, 2.0);
        testSale.addItem(testItem);

        instanceToTest.updateInventoryFromSale(testSale.createDTOFromSale());
        String printout = printoutBuffer.toString();
        String expectedOutput = "decrease quantity of item abc123 by 2";

        assertTrue(printout.contains(expectedOutput), "Inventory did not update correctly");
    }
}