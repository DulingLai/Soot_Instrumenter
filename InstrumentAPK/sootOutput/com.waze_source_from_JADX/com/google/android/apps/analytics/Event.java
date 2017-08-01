package com.google.android.apps.analytics;

/* compiled from: dalvik_source_com.waze.apk */
class Event {
    static final String INSTALL_EVENT_CATEGORY = "__##GOOGLEINSTALL##__";
    static final String ITEM_CATEGORY = "__##GOOGLEITEM##__";
    static final String PAGEVIEW_EVENT_CATEGORY = "__##GOOGLEPAGEVIEW##__";
    static final String TRANSACTION_CATEGORY = "__##GOOGLETRANSACTION##__";
    final String accountId;
    final String action;
    final String category;
    CustomVariableBuffer customVariableBuffer;
    final long eventId;
    private Item item;
    final String label;
    final int randomVal;
    final int screenHeight;
    final int screenWidth;
    final int timestampCurrent;
    final int timestampFirst;
    final int timestampPrevious;
    private Transaction transaction;
    final int userId;
    final int value;
    final int visits;

    Event(int $i0, String $r1, String $r2, String $r3, String $r4, int $i1, int $i2, int $i3) throws  {
        this(-1, $i0, $r1, -1, -1, -1, -1, -1, $r2, $r3, $r4, $i1, $i2, $i3);
    }

    Event(long $l0, int $i1, String $r1, int $i2, int $i3, int $i4, int $i5, int $i6, String $r2, String $r3, String $r4, int $i7, int $i8, int $i9) throws  {
        this.eventId = $l0;
        this.userId = $i1;
        this.accountId = $r1;
        this.randomVal = $i2;
        this.timestampFirst = $i3;
        this.timestampPrevious = $i4;
        this.timestampCurrent = $i5;
        this.visits = $i6;
        this.category = $r2;
        this.action = $r3;
        this.label = $r4;
        this.value = $i7;
        this.screenHeight = $i9;
        this.screenWidth = $i8;
    }

    public CustomVariableBuffer getCustomVariableBuffer() throws  {
        return this.customVariableBuffer;
    }

    public Item getItem() throws  {
        return this.item;
    }

    public Transaction getTransaction() throws  {
        return this.transaction;
    }

    public void setCustomVariableBuffer(CustomVariableBuffer $r1) throws  {
        this.customVariableBuffer = $r1;
    }

    public void setItem(Item $r1) throws  {
        if (this.category.equals(ITEM_CATEGORY)) {
            this.item = $r1;
            return;
        }
        throw new IllegalStateException("Attempted to add an item to an event of type " + this.category);
    }

    public void setTransaction(Transaction $r1) throws  {
        if (this.category.equals(TRANSACTION_CATEGORY)) {
            this.transaction = $r1;
            return;
        }
        throw new IllegalStateException("Attempted to add a transction to an event of type " + this.category);
    }

    public String toString() throws  {
        return "id:" + this.eventId + " " + "random:" + this.randomVal + " " + "timestampCurrent:" + this.timestampCurrent + " " + "timestampPrevious:" + this.timestampPrevious + " " + "timestampFirst:" + this.timestampFirst + " " + "visits:" + this.visits + " " + "value:" + this.value + " " + "category:" + this.category + " " + "action:" + this.action + " " + "label:" + this.label + " " + "width:" + this.screenWidth + " " + "height:" + this.screenHeight;
    }
}
