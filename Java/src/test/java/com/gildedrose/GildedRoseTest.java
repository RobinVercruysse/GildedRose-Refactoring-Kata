package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

    @Test
    void testAddItem() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals("foo", app.items[0].name);
    }

    @Test
    void testAddMultipleItems() {
        Item[] items = new Item[] {
            new Item("foo", 0, 0),
            new Item("bar", 1, 2),
            new Item("baz", 10, 20)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(items.length, app.items.length);
        assertTrue(Arrays.asList(items).containsAll(Arrays.asList(app.items)));
    }

    // There are many scenarios to be tested with the same assertions, so a parameterized test is more suitable
    @ParameterizedTest
    @MethodSource("itemProgressionProvider")
    void testProgressDays(String itemName, int itemSellIn, int itemQuality, int daysToProgress, int expectedSellIn,
                          int expectedQuality) {
        Item item = new Item(itemName, itemSellIn, itemQuality);
        GildedRose app = new GildedRose(new Item[]{ item });

        for (int i = 0; i < daysToProgress; i++) {
            app.updateQuality();
        }

        // Use the item object in the test subject instead of the one provided to the constructor
        // in case the test subject makes a copy of the object
        Item progressedItem = app.items[0];
        assertEquals(expectedSellIn, progressedItem.sellIn);
        assertEquals(expectedQuality, progressedItem.quality);
    }

    private static Collection<Arguments> itemProgressionProvider() {
        return Arrays.asList(
            // The quality of an item degrades once after 1 day (before sell-in date)
            createItemProgressArguments("foo", 1, 5, 1, 0, 4),
            // The quality of an item degrades by 3 after 3 days (before sell-in date)
            createItemProgressArguments("foo", 4, 5, 3, 1, 2),
            // The quality of an item degrades twice after 1 day (after sell-in date)
            createItemProgressArguments("foo", 0, 5, 1, -1, 3),
            // The quality of an item degrades by 6 after 3 days (after sell-in date)
            createItemProgressArguments("foo", 0, 10, 3, -3, 4),
            // The quality of an item degrades by 6 after 3 days (after sell-in date), but can't be negative
            createItemProgressArguments("foo", 0, 5, 3, -3, 0),
            // The quality of an item is never negative
            createItemProgressArguments("foo", 1, 0, 1, 0, 0),
            // The quality of an item degrades by 7 after 5 days (3 of which before sell-in, 2 after sell-in date)
            createItemProgressArguments("foo", 3, 10, 5, -2, 3),
            // Aged Brie increases in quality once before sell-in date
            createItemProgressArguments("Aged Brie", 1, 5, 1, 0, 6),
            // Aged Brie increases in quality twice after sell-in date
            createItemProgressArguments("Aged Brie", 0, 5, 1, -1, 7),
            // The quality of an item is never more than 50
            createItemProgressArguments("Aged Brie", 0, 48, 2, -2, 50),
            // Sulfuras never has to be sold or decreases in quality, and always has a quality of 80
            createItemProgressArguments("Sulfuras, Hand of Ragnaros", 10, 80, 20, 10, 80),
            // Sulfuras never has to be sold or decreases in quality, and always has a quality of 80
            createItemProgressArguments("Sulfuras, Hand of Ragnaros", -1, 80, 1, -1, 80),
            // Backstage passes increase in quality as the sell-in date approaches (over 10 days to go)
            createItemProgressArguments("Backstage passes to a TAFKAL80ETC concert", 20, 10, 1, 19, 11),
            // Backstage passes increase in quality as the sell-in date approaches (between 5 and 10 days to go)
            createItemProgressArguments("Backstage passes to a TAFKAL80ETC concert", 8, 10, 1, 7, 12),
            // Backstage passes increase in quality as the sell-in date approaches (under 5 days to go)
            createItemProgressArguments("Backstage passes to a TAFKAL80ETC concert", 4, 10, 1, 3, 13),
            // Backstage passes quality drops to zero after sell-in date
            createItemProgressArguments("Backstage passes to a TAFKAL80ETC concert", 0, 10, 1, -1, 0),
            // Backstage passes increase in quality as the sell-in date approaches
            createItemProgressArguments("Backstage passes to a TAFKAL80ETC concert", 11, 10, 11, 0, 36),
            // Backstage passes increase in quality as the sell-in date approaches, but never above 50
            createItemProgressArguments("Backstage passes to a TAFKAL80ETC concert", 11, 30, 11, 0, 50),
            // Conjured items decrease in quality twice after 1 day (before sell-in date)
            createItemProgressArguments("Conjured Mana Cake", 1, 10, 1, 0, 8),
            // Conjured items decrease in quality 4 times after 1 day (after sell-in date)
            createItemProgressArguments("Conjured Mana Cake", 0, 10, 1, -1, 6),
            // Conjured items decrease in quality twice as fast as normal items
            createItemProgressArguments("Conjured Mana Cake", 1, 10, 2, -1, 4),
            // Conjured items decrease in quality twice as fast as normal items, but never below zero
            createItemProgressArguments("Conjured Mana Cake", 1, 10, 4, -3, 0)
        );
    }

    // Building an arguments provider using same-type values would be hard to read.
    // This helper method enables IDEs to show parameter name hints,
    // increasing legibility while writing scenarios, which helps avoid annoying little mistakes.
    private static Arguments createItemProgressArguments(String itemName, int itemSellIn, int itemQuality,
                                                         int daysToProgress, int expectedSellIn, int expectedQuality) {
        return Arguments.of(itemName, itemSellIn, itemQuality, daysToProgress, expectedSellIn, expectedQuality);
    }
}
