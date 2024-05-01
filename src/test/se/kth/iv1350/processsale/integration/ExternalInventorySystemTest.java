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
        assertEquals("BigWheel Oatmeal|BigWheel Oatmeal 500g, whole grain oats, high fiber, gluten free|29.90|6.0", instanceToTest.getInformation("abc123"));
    }

    @Test
    void updateInventoryFromSale() {
        Sale testSale = new Sale();
        ItemDTO testItem = new ItemDTO("abc123", "BigWheel Oatmeal", "BigWheel Oatmeal 500g, whole grain oats, high fiber, gluten free", 29.90, 6.0, 2.0);
        testSale.addItem(testItem);

        instanceToTest.updateInventoryFromSale(testSale);
        String printout = printoutBuffer.toString();
        String expectedOutput = "decrease quantity of item abc123 by 2";

        assertTrue(printout.contains(expectedOutput), "Inventory did not update correctly");
    }
}