package com.google.android.apps.analytics;

/* compiled from: dalvik_source_com.waze.apk */
public class Item {
    private final String itemCategory;
    private final long itemCount;
    private final String itemName;
    private final double itemPrice;
    private final String itemSKU;
    private final String orderId;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private String itemCategory = null;
        private final long itemCount;
        private String itemName = null;
        private final double itemPrice;
        private final String itemSKU;
        private final String orderId;

        public Builder(String $r1, String $r2, double $d0, long $l0) throws  {
            if ($r1 == null || $r1.trim().length() == 0) {
                throw new IllegalArgumentException("orderId must not be empty or null");
            } else if ($r2 == null || $r2.trim().length() == 0) {
                throw new IllegalArgumentException("itemSKU must not be empty or null");
            } else {
                this.orderId = $r1;
                this.itemSKU = $r2;
                this.itemPrice = $d0;
                this.itemCount = $l0;
            }
        }

        public Item build() throws  {
            return new Item();
        }

        public Builder setItemCategory(String $r1) throws  {
            this.itemCategory = $r1;
            return this;
        }

        public Builder setItemName(String $r1) throws  {
            this.itemName = $r1;
            return this;
        }
    }

    private Item(Builder $r1) throws  {
        this.orderId = $r1.orderId;
        this.itemSKU = $r1.itemSKU;
        this.itemPrice = $r1.itemPrice;
        this.itemCount = $r1.itemCount;
        this.itemName = $r1.itemName;
        this.itemCategory = $r1.itemCategory;
    }

    String getItemCategory() throws  {
        return this.itemCategory;
    }

    long getItemCount() throws  {
        return this.itemCount;
    }

    String getItemName() throws  {
        return this.itemName;
    }

    double getItemPrice() throws  {
        return this.itemPrice;
    }

    String getItemSKU() throws  {
        return this.itemSKU;
    }

    String getOrderId() throws  {
        return this.orderId;
    }
}
