package se.kth.iv1350.processsale.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.processsale.integration.InventoryConnectionFailedException;
import se.kth.iv1350.processsale.integration.NoSuchItemException;
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
        try {
            instanceToTest.addItemToSale("abc123", 1.0);
        } catch (NoSuchItemException e) {
            throw new RuntimeException(e);
        }

        ArrayList<ItemDTO> items = instanceToTest.getSale().getItems();
        assertEquals(1, items.size(), "Item list size should be 1 after adding 1 item");

        ItemDTO item = items.get(0);
        assertEquals("abc123", item.getIdentifier(), "Item identifier is invalid");
        assertEquals("BigWheel Oatmeal", item.getName(), "Item name is invalid");
        assertEquals("BigWheel Oatmeal 500g, whole grain oats, high fiber, gluten free", item.getDescription(), "Item description is invalid");
        assertEquals(29.90, item.getPrice(), "Item price is invalid");
        assertEquals(6.0, item.getVat(), "Item VAT is invalid");
        assertEquals(1.0, item.getQuantity(), "Item quantity is invalid");

        try {
            instanceToTest.addItemToSale("def456", 1.0);
        } catch (NoSuchItemException e) {
            throw new RuntimeException(e);
        }

        items = instanceToTest.getSale().getItems();
        assertEquals(2, items.size(), "Item list size should be 2 after adding another item");

        NoSuchItemException exception = assertThrows(NoSuchItemException.class, () -> {
            instanceToTest.addItemToSale("A invalid item ID", 99.0);
        }, "Inventory returned information about an item which does not exist");

        assertEquals("Item with ID: A invalid item ID, could not be found", exception.getMessage(), "Exception message was not correct");

        OperationFailedException noConnectionException = assertThrows(OperationFailedException.class, () -> {
            instanceToTest.addItemToSale("WILL_FAIL", 99.0);
        }, "Inventory returned information when it should have created an exception");

        assertEquals("Could not connect to the external inventory system, try again", noConnectionException.getMessage(), "Exception message was not correct");
    }

    @Test
    void endSale() {
        instanceToTest.startNewSale();
        try {
            instanceToTest.addItemToSale("abc123", 1.0);
        } catch (NoSuchItemException e) {
            throw new RuntimeException(e);
        }

        double runningTotal = instanceToTest.endSale();

        assertEquals(29.90, runningTotal, "Running total isn't correct");
    }

    @Test
    void pay() {
        instanceToTest.startNewSale();
        try {
            instanceToTest.addItemToSale("abc123", 1.0);
        } catch (NoSuchItemException e) {
            throw new RuntimeException(e);
        }

        instanceToTest.endSale();
        double change = instanceToTest.pay(100.0);

        String printout = printoutBuffer.toString();
        assertTrue(printout.contains(String.format("Cash:  %40.2f SEK", 100.0)), "Invalid payment amount");

        assertEquals(100.0 - 29.90, change, "Invalid change amount");
    }
}