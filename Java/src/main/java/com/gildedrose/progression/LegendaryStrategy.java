package com.gildedrose.progression;

import com.gildedrose.Item;

public class LegendaryStrategy implements ProgressionStrategy {
    @Override
    public void progress(Item item) {
        // Legendary items never have to be sold, so sellIn remains untouched,
        // and their quality is always 80
        item.quality = 80;
    }
}
