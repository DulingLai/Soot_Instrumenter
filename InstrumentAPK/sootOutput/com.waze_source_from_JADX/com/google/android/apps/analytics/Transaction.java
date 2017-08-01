package com.google.android.apps.analytics;

/* compiled from: dalvik_source_com.waze.apk */
public class Transaction {
    private final String orderId;
    private final double shippingCost;
    private final String storeName;
    private final double totalCost;
    private final double totalTax;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private final String orderId;
        private double shippingCost = 0.0d;
        private String storeName = null;
        private final double totalCost;
        private double totalTax = 0.0d;

        public Builder(String $r1, double $d0) throws  {
            if ($r1 == null || $r1.trim().length() == 0) {
                throw new IllegalArgumentException("orderId must not be empty or null");
            }
            this.orderId = $r1;
            this.totalCost = $d0;
        }

        public Transaction build() throws  {
            return new Transaction();
        }

        public Builder setShippingCost(double $d0) throws  {
            this.shippingCost = $d0;
            return this;
        }

        public Builder setStoreName(String $r1) throws  {
            this.storeName = $r1;
            return this;
        }

        public Builder setTotalTax(double $d0) throws  {
            this.totalTax = $d0;
            return this;
        }
    }

    private Transaction(Builder $r1) throws  {
        this.orderId = $r1.orderId;
        this.totalCost = $r1.totalCost;
        this.storeName = $r1.storeName;
        this.totalTax = $r1.totalTax;
        this.shippingCost = $r1.shippingCost;
    }

    String getOrderId() throws  {
        return this.orderId;
    }

    double getShippingCost() throws  {
        return this.shippingCost;
    }

    String getStoreName() throws  {
        return this.storeName;
    }

    double getTotalCost() throws  {
        return this.totalCost;
    }

    double getTotalTax() throws  {
        return this.totalTax;
    }
}
