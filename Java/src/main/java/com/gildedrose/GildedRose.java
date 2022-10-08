package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            // This line highlights an important assumption made during this exercise,
            // being that items can only belong to a single category, and have only a single progression strategy apply.
            // If this assumption was incorrect, perhaps the decorator pattern would be more suitable.
            // Then there would need to be logic to deal with incompatible progression (such as legendary + conjured),
            // but this is not described in the requirements.
            ItemCategory category = ItemCategory.getCategoryForItem(item);
            category.getStrategy().progress(item);
        }
    }
}
