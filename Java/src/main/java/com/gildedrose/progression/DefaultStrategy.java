package com.gildedrose.progression;

import com.gildedrose.Item;

public class DefaultStrategy implements ProgressionStrategy {
    @Override
    public void progress(Item item) {
        item.sellIn--;
        int decrement = item.sellIn >= 0 ? 1 : 2; // Items degrade twice as fast beyond the sell-in date
        item.quality = Math.max(0, item.quality - decrement); // Ensure item quality won't go below zero after decrement
    }
}
