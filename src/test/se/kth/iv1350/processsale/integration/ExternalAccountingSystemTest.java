package se.kth.iv1350.processsale.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.processsale.model.Sale;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ExternalAccountingSystemTest {
    private ExternalAccountingSystem instanceToTest;
    private ByteArrayOutputStream printoutBuffer;
    private PrintStream originalSysOut;

    @BeforeEach
    void setUp() {
        instanceToTest = new ExternalAccountingSystem();

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
    void updateAccountingFromSale() {
        Sale testSale = new Sale();

        instanceToTest.updateAccountingFromSale(testSale.createDTOFromSale());
        String printout = printoutBuffer.toString();
        String expectedOutput = "Sent sale info to external accounting system.";

        assertTrue(printout.contains(expectedOutput), "Accounting did not update correctly");
    }
}