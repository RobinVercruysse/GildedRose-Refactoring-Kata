package com.gildedrose.progression;

import com.gildedrose.Item;

public class ConjuredStrategy implements ProgressionStrategy {
    @Override
    public void progress(Item item) {
        item.sellIn--;
        int decrement = item.sellIn >= 0 ? 2 : 4;
        item.quality = Math.max(0, item.quality - decrement); // Ensure item quality won't go below zero after decrement
    }
}
