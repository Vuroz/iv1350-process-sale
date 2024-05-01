package se.kth.iv1350.processsale.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.processsale.integration.ExternalInventorySystem;
import se.kth.iv1350.processsale.model.dto.ItemDTO;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller instanceToTest;
    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;

    @BeforeEach
    void setUp() {
        instanceToTest = new Controller();

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
    void startNewSale() {
        instanceToTest.startNewSale();
        assertNotNull(instanceToTest.getSale(), "Sale is null after initialization");
    }

    @Test
    void addItemToSale() {
        instanceToTest.startNewSale();
        instanceToTest.addItemToSale("abc123", 1.0);

        ArrayList<ItemDTO> items = instanceToTest.getSale().getItems();
        assertEquals(1, items.size(), "Item list size should be 1 after adding 1 item");

        ItemDTO item = items.get(0);
        assertEquals("abc123", item.getIdentifier(), "Item identifier is invalid");
        assertEquals("BigWheel Oatmeal", item.getName(), "Item name is invalid");
        assertEquals("BigWheel Oatmeal 500g, whole grain oats, high fiber, gluten free", item.getDescription(), "Item description is invalid");
        assertEquals(29.90, item.getPrice(), "Item price is invalid");
        assertEquals(6.0, item.getVat(), "Item VAT is invalid");
        assertEquals(1.0, item.getQuantity(), "Item quantity is invalid");

        instanceToTest.addItemToSale("def456", 1.0);
        assertEquals(2, items.size(), "Item list size should be 2 after adding another item");
    }

    @Test
    void endSale() {
        instanceToTest.startNewSale();
        instanceToTest.addItemToSale("abc123", 1.0);
        double runningTotal = instanceToTest.endSale();

        assertEquals(29.90, runningTotal, "Running total isn't correct");
    }

    @Test
    void pay() {
        instanceToTest.startNewSale();
        instanceToTest.addItemToSale("abc123", 1.0);
        instanceToTest.endSale();
        double change = instanceToTest.pay(100.0);

        String printout = printoutBuffer.toString();
        assertTrue(printout.contains("Customer pays 100"), "Invalid payment amount");

        assertEquals(100.0 - 29.90, change, "Invalid change amount");
    }
}