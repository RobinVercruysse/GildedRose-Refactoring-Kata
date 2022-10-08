package com.gildedrose.progression;

import com.gildedrose.Item;

public class PassStrategy implements ProgressionStrategy {
    @Override
    public void progress(Item item) {
        item.sellIn--;
        if (item.sellIn >= 0) {
            int increment;
            if (item.sellIn >= 10) {
                increment = 1;
            } else if (item.sellIn >= 5) {
                increment = 2;
            } else {
                increment = 3;
            }
            item.quality = Math.min(50, item.quality + increment); // Ensure item quality won't go over 50 after increment
        } else {
            item.quality = 0;
        }

    }
}
