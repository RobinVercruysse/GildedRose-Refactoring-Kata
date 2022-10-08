package com.gildedrose;

import com.gildedrose.progression.*;

public enum ItemCategory {
    DEFAULT(new DefaultStrategy()),
    POSITIVE_AGING(new PositiveAgingStrategy()),
    PASS(new PassStrategy()),
    LEGENDARY(new LegendaryStrategy()),
    CONJURED(new ConjuredStrategy());

    private final ProgressionStrategy strategy;

    ItemCategory(ProgressionStrategy strategy) {
        this.strategy = strategy;
    }

    // Returns the strategy associated with this category.
    // An alternative to linking strategy to category this way would be to implement strategy determination logic elsewhere,
    // for example as a static method in the ProgressionStrategy interface.
    public ProgressionStrategy getStrategy() {
        return strategy;
    }

    // Finds the category that an item belongs to, or assigns it to the default category
    // It would make more sense to make the category a property of the item,
    // but then we first need to deal with the goblin...
    public static ItemCategory getCategoryForItem(Item item) {
        switch (item.name) {
            case "Aged Brie":
                return POSITIVE_AGING;
            case "Backstage passes to a TAFKAL80ETC concert":
                return PASS;
            case "Sulfuras, Hand of Ragnaros":
                return LEGENDARY;
            case "Conjured Mana Cake":
                return CONJURED;
            default:
                return DEFAULT;
        }
    }
}
