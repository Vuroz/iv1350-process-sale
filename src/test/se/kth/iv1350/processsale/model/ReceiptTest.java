package se.kth.iv1350.processsale.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.processsale.model.dto.ItemDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {
    private Receipt instanceToTest;
    private ArrayList<ItemDTO> items;

    @BeforeEach
    void setUp() {
        items = new ArrayList<>();
        items.add(new ItemDTO("abc123", "BigWheel Oatmeal", "BigWheel Oatmeal 500g, whole grain oats, high fiber, gluten free", 29.90, 6.0, 1.0));
        items.add(new ItemDTO("def456", "YouGoGo Blueberry", "YouGoGo Blueberry 240g, low sugar yoghurt, blueberry flavor", 14.90, 6.0, 1));
        instanceToTest = new Receipt(items, 500.0, 125.0, LocalDateTime.now());
    }

    @AfterEach
    void tearDown() {
        instanceToTest = null;
        items = null;
    }

    @Test
    void collectItemDTOToReceiptItem() {
        HashMap<String, Receipt.ReceiptItem> receiptItems = instanceToTest.collectItemDTOToReceiptItem(items);
        assertEquals(2, receiptItems.size());
        assertTrue(receiptItems.containsKey("abc123"), "abc123 not in receiptItems");
        assertTrue(receiptItems.containsKey("def456"), "def456 not in receiptItems");
    }

    @Test
    void receiptItem() {
        Receipt.ReceiptItem receiptItem = instanceToTest.new ReceiptItem("BigWheel Oatmeal", 1.0, 29.90);
        assertEquals("BigWheel Oatmeal", receiptItem.getName(), "ReceiptItem name is not correct");
        assertEquals(1.0, receiptItem.getQuantity(), "ReceiptItem quantity is not correct");
        assertEquals(29.90, receiptItem.getPrice(), "ReceiptItem price is not correct");

        receiptItem.updateQuantity(2.0);
        assertEquals(3.0, receiptItem.getQuantity(), "ReceiptItem quantity is not correct after update");
    }
}