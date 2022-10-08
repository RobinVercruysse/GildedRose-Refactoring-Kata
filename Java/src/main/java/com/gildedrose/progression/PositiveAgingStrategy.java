package com.gildedrose.progression;

import com.gildedrose.Item;

public class PositiveAgingStrategy implements ProgressionStrategy {
    @Override
    public void progress(Item item) {
        item.sellIn--;
        int increment = item.sellIn >= 0 ? 1 : 2; // Items increase in quality twice as fast beyond sell-in date
        item.quality = Math.min(50, item.quality + increment); // Ensure item quality won't go over 50 after increment
    }
}
