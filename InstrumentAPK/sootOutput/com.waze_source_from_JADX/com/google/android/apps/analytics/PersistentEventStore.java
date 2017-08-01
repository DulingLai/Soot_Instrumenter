package com.google.android.apps.analytics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import com.google.android.apps.analytics.Item.Builder;
import com.waze.ResManager;
import java.security.SecureRandom;

/* compiled from: dalvik_source_com.waze.apk */
class PersistentEventStore implements EventStore {
    private static final String ACCOUNT_ID = "account_id";
    private static final String ACTION = "action";
    private static final String CATEGORY = "category";
    private static final String CREATE_CUSTOM_VARIABLES_TABLE = ("CREATE TABLE custom_variables (" + String.format(" '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,", new Object[]{CUSTOMVAR_ID}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{EVENT_ID}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{CUSTOMVAR_INDEX}) + String.format(" '%s' CHAR(64) NOT NULL,", new Object[]{CUSTOMVAR_NAME}) + String.format(" '%s' CHAR(64) NOT NULL,", new Object[]{CUSTOMVAR_VALUE}) + String.format(" '%s' INTEGER NOT NULL);", new Object[]{CUSTOMVAR_SCOPE}));
    private static final String CREATE_CUSTOM_VAR_CACHE_TABLE = ("CREATE TABLE custom_var_cache (" + String.format(" '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,", new Object[]{CUSTOMVAR_ID}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{EVENT_ID}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{CUSTOMVAR_INDEX}) + String.format(" '%s' CHAR(64) NOT NULL,", new Object[]{CUSTOMVAR_NAME}) + String.format(" '%s' CHAR(64) NOT NULL,", new Object[]{CUSTOMVAR_VALUE}) + String.format(" '%s' INTEGER NOT NULL);", new Object[]{CUSTOMVAR_SCOPE}));
    private static final String CREATE_EVENTS_TABLE = ("CREATE TABLE events (" + String.format(" '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,", new Object[]{EVENT_ID}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{"user_id"}) + String.format(" '%s' CHAR(256) NOT NULL,", new Object[]{ACCOUNT_ID}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{RANDOM_VAL}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{TIMESTAMP_FIRST}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{TIMESTAMP_PREVIOUS}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{TIMESTAMP_CURRENT}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{VISITS}) + String.format(" '%s' CHAR(256) NOT NULL,", new Object[]{CATEGORY}) + String.format(" '%s' CHAR(256) NOT NULL,", new Object[]{"action"}) + String.format(" '%s' CHAR(256), ", new Object[]{"label"}) + String.format(" '%s' INTEGER,", new Object[]{VALUE}) + String.format(" '%s' INTEGER,", new Object[]{SCREEN_WIDTH}) + String.format(" '%s' INTEGER);", new Object[]{SCREEN_HEIGHT}));
    private static final String CREATE_INSTALL_REFERRER_TABLE = "CREATE TABLE install_referrer (referrer TEXT PRIMARY KEY NOT NULL);";
    private static final String CREATE_ITEM_EVENTS_TABLE = ("CREATE TABLE item_events (" + String.format(" '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,", new Object[]{ITEM_ID}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{EVENT_ID}) + String.format(" '%s' TEXT NOT NULL,", new Object[]{ORDER_ID}) + String.format(" '%s' TEXT NOT NULL,", new Object[]{ITEM_SKU}) + String.format(" '%s' TEXT,", new Object[]{ITEM_NAME}) + String.format(" '%s' TEXT,", new Object[]{ITEM_CATEGORY}) + String.format(" '%s' TEXT NOT NULL,", new Object[]{ITEM_PRICE}) + String.format(" '%s' TEXT NOT NULL);", new Object[]{ITEM_COUNT}));
    private static final String CREATE_SESSION_TABLE = ("CREATE TABLE session (" + String.format(" '%s' INTEGER PRIMARY KEY,", new Object[]{TIMESTAMP_FIRST}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{TIMESTAMP_PREVIOUS}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{TIMESTAMP_CURRENT}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{VISITS}) + String.format(" '%s' INTEGER NOT NULL);", new Object[]{STORE_ID}));
    private static final String CREATE_TRANSACTION_EVENTS_TABLE = ("CREATE TABLE transaction_events (" + String.format(" '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,", new Object[]{TRANSACTION_ID}) + String.format(" '%s' INTEGER NOT NULL,", new Object[]{EVENT_ID}) + String.format(" '%s' TEXT NOT NULL,", new Object[]{ORDER_ID}) + String.format(" '%s' TEXT,", new Object[]{STORE_NAME}) + String.format(" '%s' TEXT NOT NULL,", new Object[]{TOTAL_COST}) + String.format(" '%s' TEXT,", new Object[]{TOTAL_TAX}) + String.format(" '%s' TEXT);", new Object[]{SHIPPING_COST}));
    private static final String CUSTOMVAR_ID = "cv_id";
    private static final String CUSTOMVAR_INDEX = "cv_index";
    private static final String CUSTOMVAR_NAME = "cv_name";
    private static final String CUSTOMVAR_SCOPE = "cv_scope";
    private static final String CUSTOMVAR_VALUE = "cv_value";
    private static final String CUSTOM_VARIABLE_COLUMN_TYPE = "CHAR(64) NOT NULL";
    private static final String DATABASE_NAME = "google_analytics.db";
    private static final int DATABASE_VERSION = 3;
    private static final String EVENT_ID = "event_id";
    private static final String ITEM_CATEGORY = "item_category";
    private static final String ITEM_COUNT = "item_count";
    private static final String ITEM_ID = "item_id";
    private static final String ITEM_NAME = "item_name";
    private static final String ITEM_PRICE = "item_price";
    private static final String ITEM_SKU = "item_sku";
    private static final String LABEL = "label";
    private static final int MAX_EVENTS = 1000;
    private static final String ORDER_ID = "order_id";
    private static final String RANDOM_VAL = "random_val";
    private static final String REFERRER = "referrer";
    private static final String SCREEN_HEIGHT = "screen_height";
    private static final String SCREEN_WIDTH = "screen_width";
    private static final String SHIPPING_COST = "tran_shippingcost";
    private static final String STORE_ID = "store_id";
    private static final String STORE_NAME = "tran_storename";
    private static final String TIMESTAMP_CURRENT = "timestamp_current";
    private static final String TIMESTAMP_FIRST = "timestamp_first";
    private static final String TIMESTAMP_PREVIOUS = "timestamp_previous";
    private static final String TOTAL_COST = "tran_totalcost";
    private static final String TOTAL_TAX = "tran_totaltax";
    private static final String TRANSACTION_ID = "tran_id";
    private static final String USER_ID = "user_id";
    private static final String VALUE = "value";
    private static final String VISITS = "visits";
    private SQLiteStatement compiledCountStatement = null;
    private DataBaseHelper databaseHelper;
    private int numStoredEvents;
    private boolean sessionUpdated;
    private int storeId;
    private long timestampCurrent;
    private long timestampFirst;
    private long timestampPrevious;
    private boolean useStoredVisitorVars;
    private int visits;

    /* compiled from: dalvik_source_com.waze.apk */
    static class DataBaseHelper extends SQLiteOpenHelper {
        private final int databaseVersion;

        public DataBaseHelper(Context $r1) throws  {
            this($r1, PersistentEventStore.DATABASE_NAME, 3);
        }

        public DataBaseHelper(Context $r1, String $r2) throws  {
            this($r1, $r2, 3);
        }

        DataBaseHelper(Context $r1, String $r2, int $i0) throws  {
            super($r1, $r2, null, $i0);
            this.databaseVersion = $i0;
        }

        private void createECommerceTables(SQLiteDatabase $r1) throws  {
            $r1.execSQL("DROP TABLE IF EXISTS transaction_events;");
            $r1.execSQL(PersistentEventStore.CREATE_TRANSACTION_EVENTS_TABLE);
            $r1.execSQL("DROP TABLE IF EXISTS item_events;");
            $r1.execSQL(PersistentEventStore.CREATE_ITEM_EVENTS_TABLE);
        }

        void createCustomVariableTables(SQLiteDatabase $r1) throws  {
            $r1.execSQL("DROP TABLE IF EXISTS custom_variables;");
            $r1.execSQL(PersistentEventStore.CREATE_CUSTOM_VARIABLES_TABLE);
            $r1.execSQL("DROP TABLE IF EXISTS custom_var_cache;");
            $r1.execSQL(PersistentEventStore.CREATE_CUSTOM_VAR_CACHE_TABLE);
            for (int $i0 = 1; $i0 <= 5; $i0++) {
                ContentValues $r3 = new ContentValues();
                $r3.put(PersistentEventStore.EVENT_ID, Integer.valueOf(0));
                $r3.put(PersistentEventStore.CUSTOMVAR_INDEX, Integer.valueOf($i0));
                $r3.put(PersistentEventStore.CUSTOMVAR_NAME, "");
                $r3.put(PersistentEventStore.CUSTOMVAR_SCOPE, Integer.valueOf(3));
                $r3.put(PersistentEventStore.CUSTOMVAR_VALUE, "");
                $r1.insert("custom_var_cache", PersistentEventStore.EVENT_ID, $r3);
            }
        }

        public void onCreate(SQLiteDatabase $r1) throws  {
            $r1.execSQL("DROP TABLE IF EXISTS events;");
            $r1.execSQL(PersistentEventStore.CREATE_EVENTS_TABLE);
            $r1.execSQL("DROP TABLE IF EXISTS session;");
            $r1.execSQL(PersistentEventStore.CREATE_SESSION_TABLE);
            $r1.execSQL("DROP TABLE IF EXISTS install_referrer;");
            $r1.execSQL(PersistentEventStore.CREATE_INSTALL_REFERRER_TABLE);
            if (this.databaseVersion > 1) {
                createCustomVariableTables($r1);
            }
            if (this.databaseVersion > 2) {
                createECommerceTables($r1);
            }
        }

        public void onDowngrade(SQLiteDatabase sQLiteDatabase, int $i0, int $i1) throws  {
            Log.w(GoogleAnalyticsTracker.LOG_TAG, "Downgrading database version from " + $i0 + " to " + $i1 + " not recommended.");
        }

        public void onUpgrade(SQLiteDatabase $r1, int $i0, int $i1) throws  {
            if ($i0 < 2 && $i1 > 1) {
                createCustomVariableTables($r1);
            }
            if ($i0 < 3 && $i1 > 2) {
                createECommerceTables($r1);
            }
        }
    }

    PersistentEventStore(DataBaseHelper $r1) throws  {
        this.databaseHelper = $r1;
        try {
            $r1.getWritableDatabase().close();
        } catch (SQLiteException $r3) {
            Log.e(GoogleAnalyticsTracker.LOG_TAG, $r3.toString());
        }
    }

    public void deleteEvent(long $l0) throws  {
        String $r2 = "event_id=" + $l0;
        try {
            SQLiteDatabase $r4 = this.databaseHelper.getWritableDatabase();
            if ($r4.delete("events", $r2, null) != 0) {
                this.numStoredEvents--;
                $r4.delete("custom_variables", $r2, null);
                $r4.delete("transaction_events", $r2, null);
                $r4.delete("item_events", $r2, null);
            }
        } catch (SQLiteException $r5) {
            Log.e(GoogleAnalyticsTracker.LOG_TAG, $r5.toString());
        }
    }

    CustomVariableBuffer getCustomVariables(long $l0) throws  {
        Cursor cursor;
        SQLiteException $r10;
        Throwable $r11;
        CustomVariableBuffer $r1 = new CustomVariableBuffer();
        try {
            Cursor $r6 = this.databaseHelper.getReadableDatabase().query("custom_variables", null, "event_id=" + $l0, null, null, null, null);
            cursor = $r6;
            while ($r6.moveToNext()) {
                try {
                    $r1.setCustomVariable(new CustomVariable($r6.getInt($r6.getColumnIndex(CUSTOMVAR_INDEX)), $r6.getString($r6.getColumnIndex(CUSTOMVAR_NAME)), $r6.getString($r6.getColumnIndex(CUSTOMVAR_VALUE)), $r6.getInt($r6.getColumnIndex(CUSTOMVAR_SCOPE))));
                } catch (SQLiteException e) {
                    $r10 = e;
                }
            }
            if ($r6 == null) {
                return $r1;
            }
            $r6.close();
            return $r1;
        } catch (SQLiteException e2) {
            $r10 = e2;
            cursor = null;
            try {
                Log.e(GoogleAnalyticsTracker.LOG_TAG, $r10.toString());
                if (cursor == null) {
                    return $r1;
                }
                cursor.close();
                return $r1;
            } catch (Throwable th) {
                $r11 = th;
                if (cursor != null) {
                    cursor.close();
                }
                throw $r11;
            }
        } catch (Throwable th2) {
            $r11 = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw $r11;
        }
    }

    Item getItem(long $l0) throws  {
        Cursor $r5;
        Cursor cursor;
        SQLiteException $r10;
        Throwable $r11;
        try {
            $r5 = this.databaseHelper.getReadableDatabase().query("item_events", null, "event_id=" + $l0, null, null, null, null);
            cursor = $r5;
            try {
                if ($r5.moveToFirst()) {
                    Item $r9 = new Builder($r5.getString($r5.getColumnIndex(ORDER_ID)), $r5.getString($r5.getColumnIndex(ITEM_SKU)), $r5.getDouble($r5.getColumnIndex(ITEM_PRICE)), $r5.getLong($r5.getColumnIndex(ITEM_COUNT))).setItemName($r5.getString($r5.getColumnIndex(ITEM_NAME))).setItemCategory($r5.getString($r5.getColumnIndex(ITEM_CATEGORY))).build();
                    if ($r5 == null) {
                        return $r9;
                    }
                    $r5.close();
                    return $r9;
                }
                if ($r5 != null) {
                    $r5.close();
                }
                return null;
            } catch (SQLiteException e) {
                $r10 = e;
                try {
                    Log.e(GoogleAnalyticsTracker.LOG_TAG, $r10.toString());
                    if ($r5 != null) {
                        $r5.close();
                    }
                    return null;
                } catch (Throwable th) {
                    $r11 = th;
                    cursor = $r5;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw $r11;
                }
            } catch (Throwable th2) {
                $r11 = th2;
                if (cursor != null) {
                    cursor.close();
                }
                throw $r11;
            }
        } catch (SQLiteException e2) {
            $r10 = e2;
            $r5 = null;
            Log.e(GoogleAnalyticsTracker.LOG_TAG, $r10.toString());
            if ($r5 != null) {
                $r5.close();
            }
            return null;
        } catch (Throwable th3) {
            $r11 = th3;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw $r11;
        }
    }

    public int getNumStoredEvents() throws  {
        if (this.compiledCountStatement == null) {
            try {
                this.compiledCountStatement = this.databaseHelper.getReadableDatabase().compileStatement("SELECT COUNT(*) from events");
            } catch (SQLiteException $r4) {
                Log.e(GoogleAnalyticsTracker.LOG_TAG, $r4.toString());
                return 0;
            }
        }
        return (int) this.compiledCountStatement.simpleQueryForLong();
    }

    public String getReferrer() throws  {
        Cursor cursor;
        SQLiteException $r7;
        Throwable $r8;
        Cursor $r1 = null;
        try {
            try {
                SQLiteDatabase $r3 = this.databaseHelper.getReadableDatabase();
                String[] $r4 = new String[1];
                $r4[0] = "referrer";
                $r1 = $r3.query("install_referrer", $r4, null, null, null, null, null);
                cursor = $r1;
                try {
                    String $r6 = $r1.moveToFirst() ? $r1.getString(0) : null;
                    if ($r1 == null) {
                        return $r6;
                    }
                    $r1.close();
                    return $r6;
                } catch (SQLiteException e) {
                    $r7 = e;
                    try {
                        Log.e(GoogleAnalyticsTracker.LOG_TAG, $r7.toString());
                        if (cursor != null) {
                            cursor.close();
                        }
                        return null;
                    } catch (Throwable th) {
                        $r8 = th;
                        $r1 = cursor;
                        if ($r1 != null) {
                            $r1.close();
                        }
                        throw $r8;
                    }
                }
            } catch (SQLiteException e2) {
                $r7 = e2;
                cursor = null;
                Log.e(GoogleAnalyticsTracker.LOG_TAG, $r7.toString());
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
        } catch (Throwable th2) {
            $r8 = th2;
            if ($r1 != null) {
                $r1.close();
            }
            throw $r8;
        }
    }

    public int getStoreId() throws  {
        return this.storeId;
    }

    Transaction getTransaction(long $l0) throws  {
        Cursor cursor;
        SQLiteException $r9;
        Throwable $r10;
        try {
            Cursor $r5 = this.databaseHelper.getReadableDatabase().query("transaction_events", null, "event_id=" + $l0, null, null, null, null);
            cursor = $r5;
            try {
                if ($r5.moveToFirst()) {
                    Transaction $r8 = new Transaction.Builder($r5.getString($r5.getColumnIndex(ORDER_ID)), $r5.getDouble($r5.getColumnIndex(TOTAL_COST))).setStoreName($r5.getString($r5.getColumnIndex(STORE_NAME))).setTotalTax($r5.getDouble($r5.getColumnIndex(TOTAL_TAX))).setShippingCost($r5.getDouble($r5.getColumnIndex(SHIPPING_COST))).build();
                    if ($r5 == null) {
                        return $r8;
                    }
                    $r5.close();
                    return $r8;
                }
                if ($r5 != null) {
                    $r5.close();
                }
                return null;
            } catch (SQLiteException e) {
                $r9 = e;
                try {
                    Log.e(GoogleAnalyticsTracker.LOG_TAG, $r9.toString());
                    if (cursor != null) {
                        cursor.close();
                    }
                    return null;
                } catch (Throwable th) {
                    $r10 = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw $r10;
                }
            }
        } catch (SQLiteException e2) {
            $r9 = e2;
            cursor = null;
            Log.e(GoogleAnalyticsTracker.LOG_TAG, $r9.toString());
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th2) {
            $r10 = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw $r10;
        }
    }

    public String getVisitorCustomVar(int $i0) throws  {
        SQLiteException $r6;
        Throwable $r7;
        Cursor $r1 = null;
        try {
            SQLiteDatabase $r3 = this.databaseHelper.getReadableDatabase();
            $r1 = $r3.query("custom_var_cache", null, "cv_scope = 1 AND cv_index = " + $i0, null, null, null, null);
            try {
                String $r5;
                if ($r1.getCount() > 0) {
                    $r1.moveToFirst();
                    $r5 = $r1.getString($r1.getColumnIndex(CUSTOMVAR_VALUE));
                } else {
                    $r5 = null;
                }
                $r3.close();
                if ($r1 != null) {
                    $r1.close();
                }
                return $r5;
            } catch (SQLiteException e) {
                $r6 = e;
                try {
                    Log.e(GoogleAnalyticsTracker.LOG_TAG, $r6.toString());
                    if ($r1 != null) {
                        $r1.close();
                    }
                    return null;
                } catch (Throwable th) {
                    $r7 = th;
                    if ($r1 != null) {
                        $r1.close();
                    }
                    throw $r7;
                }
            } catch (Throwable th2) {
                $r7 = th2;
                if ($r1 != null) {
                    $r1.close();
                }
                throw $r7;
            }
        } catch (SQLiteException e2) {
            $r6 = e2;
            $r1 = null;
            Log.e(GoogleAnalyticsTracker.LOG_TAG, $r6.toString());
            if ($r1 != null) {
                $r1.close();
            }
            return null;
        } catch (Throwable th3) {
            $r7 = th3;
            if ($r1 != null) {
                $r1.close();
            }
            throw $r7;
        }
    }

    CustomVariableBuffer getVisitorVarBuffer() throws  {
        CustomVariableBuffer $r1 = new CustomVariableBuffer();
        try {
            Cursor $r4 = this.databaseHelper.getReadableDatabase().query("custom_var_cache", null, "cv_scope=1", null, null, null, null);
            while ($r4.moveToNext()) {
                $r1.setCustomVariable(new CustomVariable($r4.getInt($r4.getColumnIndex(CUSTOMVAR_INDEX)), $r4.getString($r4.getColumnIndex(CUSTOMVAR_NAME)), $r4.getString($r4.getColumnIndex(CUSTOMVAR_VALUE)), $r4.getInt($r4.getColumnIndex(CUSTOMVAR_SCOPE))));
            }
            $r4.close();
            return $r1;
        } catch (SQLiteException $r8) {
            Log.e(GoogleAnalyticsTracker.LOG_TAG, $r8.toString());
            return $r1;
        }
    }

    public Event[] peekEvents() throws  {
        return peekEvents(1000);
    }

    public com.google.android.apps.analytics.Event[] peekEvents(int r55) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x01ea in list [B:40:0x01d1]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r54 = this;
        r16 = new java.util.ArrayList;
        r0 = r16;
        r0.<init>();
        r0 = r54;
        r0 = r0.databaseHelper;
        r17 = r0;
        r18 = r0.getReadableDatabase();	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r0 = r55;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r19 = java.lang.Integer.toString(r0);	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r21 = "events";	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r22 = 0;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r23 = 0;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r24 = 0;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r25 = 0;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r26 = 0;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r27 = "event_id";	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r0 = r18;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r1 = r21;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r2 = r22;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r3 = r23;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r4 = r24;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r5 = r25;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r6 = r26;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r7 = r27;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r8 = r19;	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r20 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ SQLiteException -> 0x0214, Throwable -> 0x020c }
        r28 = r20;
    L_0x003d:
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r29 = r0.moveToNext();	 Catch:{ SQLiteException -> 0x015d }
        if (r29 == 0) goto L_0x01ea;	 Catch:{ Throwable -> 0x01d1 }
    L_0x0045:
        r30 = new com.google.android.apps.analytics.Event;	 Catch:{ SQLiteException -> 0x015d }
        r33 = 0;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r31 = r0.getLong(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 1;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r55 = r0.getInt(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 2;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r19 = r0.getString(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 3;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r34 = r0.getInt(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 4;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r35 = r0.getInt(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 5;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r36 = r0.getInt(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 6;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r37 = r0.getInt(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 7;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r38 = r0.getInt(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 8;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r39 = r0.getString(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 9;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r40 = r0.getString(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 10;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r41 = r0.getString(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 11;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r42 = r0.getInt(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 12;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r43 = r0.getInt(r1);	 Catch:{ SQLiteException -> 0x015d }
        r33 = 13;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r33;	 Catch:{ SQLiteException -> 0x015d }
        r44 = r0.getInt(r1);	 Catch:{ SQLiteException -> 0x015d }
        r0 = r30;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r31;	 Catch:{ SQLiteException -> 0x015d }
        r3 = r55;	 Catch:{ SQLiteException -> 0x015d }
        r4 = r19;	 Catch:{ SQLiteException -> 0x015d }
        r5 = r34;	 Catch:{ SQLiteException -> 0x015d }
        r6 = r35;	 Catch:{ SQLiteException -> 0x015d }
        r7 = r36;	 Catch:{ SQLiteException -> 0x015d }
        r8 = r37;	 Catch:{ SQLiteException -> 0x015d }
        r9 = r38;	 Catch:{ SQLiteException -> 0x015d }
        r10 = r39;	 Catch:{ SQLiteException -> 0x015d }
        r11 = r40;	 Catch:{ SQLiteException -> 0x015d }
        r12 = r41;	 Catch:{ SQLiteException -> 0x015d }
        r13 = r42;	 Catch:{ SQLiteException -> 0x015d }
        r14 = r43;	 Catch:{ SQLiteException -> 0x015d }
        r15 = r44;	 Catch:{ SQLiteException -> 0x015d }
        r0.<init>(r1, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15);	 Catch:{ SQLiteException -> 0x015d }
        r21 = "event_id";	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r21;	 Catch:{ SQLiteException -> 0x015d }
        r55 = r0.getColumnIndex(r1);	 Catch:{ SQLiteException -> 0x015d }
        r0 = r20;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r55;	 Catch:{ SQLiteException -> 0x015d }
        r31 = r0.getLong(r1);	 Catch:{ SQLiteException -> 0x015d }
        r19 = "__##GOOGLETRANSACTION##__";
        r0 = r30;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r0.category;	 Catch:{ SQLiteException -> 0x015d }
        r39 = r0;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r19;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r39;	 Catch:{ SQLiteException -> 0x015d }
        r29 = r0.equals(r1);	 Catch:{ SQLiteException -> 0x015d }
        if (r29 == 0) goto L_0x017d;	 Catch:{ SQLiteException -> 0x015d }
    L_0x0118:
        r0 = r54;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r31;	 Catch:{ SQLiteException -> 0x015d }
        r45 = r0.getTransaction(r1);	 Catch:{ SQLiteException -> 0x015d }
        if (r45 != 0) goto L_0x014a;	 Catch:{ SQLiteException -> 0x015d }
    L_0x0122:
        r46 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x015d }
        r0 = r46;	 Catch:{ SQLiteException -> 0x015d }
        r0.<init>();	 Catch:{ SQLiteException -> 0x015d }
        r21 = "missing expected transaction for event ";	 Catch:{ SQLiteException -> 0x015d }
        r0 = r46;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r21;	 Catch:{ SQLiteException -> 0x015d }
        r46 = r0.append(r1);	 Catch:{ SQLiteException -> 0x015d }
        r0 = r46;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r31;	 Catch:{ SQLiteException -> 0x015d }
        r46 = r0.append(r1);	 Catch:{ SQLiteException -> 0x015d }
        r0 = r46;	 Catch:{ SQLiteException -> 0x015d }
        r19 = r0.toString();	 Catch:{ SQLiteException -> 0x015d }
        r21 = "GoogleAnalyticsTracker";	 Catch:{ SQLiteException -> 0x015d }
        r0 = r21;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r19;	 Catch:{ SQLiteException -> 0x015d }
        android.util.Log.w(r0, r1);	 Catch:{ SQLiteException -> 0x015d }
    L_0x014a:
        r0 = r30;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r45;	 Catch:{ SQLiteException -> 0x015d }
        r0.setTransaction(r1);	 Catch:{ SQLiteException -> 0x015d }
        goto L_0x0155;	 Catch:{ SQLiteException -> 0x015d }
    L_0x0152:
        goto L_0x003d;	 Catch:{ SQLiteException -> 0x015d }
    L_0x0155:
        r0 = r16;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r30;	 Catch:{ SQLiteException -> 0x015d }
        r0.add(r1);	 Catch:{ SQLiteException -> 0x015d }
        goto L_0x0152;
    L_0x015d:
        r47 = move-exception;
    L_0x015e:
        r0 = r47;	 Catch:{ Throwable -> 0x0210 }
        r19 = r0.toString();	 Catch:{ Throwable -> 0x0210 }
        r21 = "GoogleAnalyticsTracker";	 Catch:{ Throwable -> 0x0210 }
        r0 = r21;	 Catch:{ Throwable -> 0x0210 }
        r1 = r19;	 Catch:{ Throwable -> 0x0210 }
        android.util.Log.e(r0, r1);	 Catch:{ Throwable -> 0x0210 }
        r33 = 0;	 Catch:{ Throwable -> 0x0210 }
        r0 = r33;	 Catch:{ Throwable -> 0x0210 }
        r0 = new com.google.android.apps.analytics.Event[r0];	 Catch:{ Throwable -> 0x0210 }
        r48 = r0;	 Catch:{ Throwable -> 0x0210 }
        if (r20 == 0) goto L_0x0218;
    L_0x0177:
        r0 = r20;
        r0.close();
        return r48;
    L_0x017d:
        r19 = "__##GOOGLEITEM##__";
        r0 = r30;
        r0 = r0.category;
        r39 = r0;
        r0 = r19;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r39;	 Catch:{ SQLiteException -> 0x015d }
        r29 = r0.equals(r1);	 Catch:{ SQLiteException -> 0x015d }
        if (r29 == 0) goto L_0x01da;	 Catch:{ SQLiteException -> 0x015d }
    L_0x018f:
        r0 = r54;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r31;	 Catch:{ SQLiteException -> 0x015d }
        r49 = r0.getItem(r1);	 Catch:{ SQLiteException -> 0x015d }
        if (r49 != 0) goto L_0x01c9;	 Catch:{ SQLiteException -> 0x015d }
    L_0x0199:
        r46 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x015d }
        goto L_0x019f;	 Catch:{ SQLiteException -> 0x015d }
    L_0x019c:
        goto L_0x0155;	 Catch:{ SQLiteException -> 0x015d }
    L_0x019f:
        r0 = r46;	 Catch:{ SQLiteException -> 0x015d }
        r0.<init>();	 Catch:{ SQLiteException -> 0x015d }
        r21 = "missing expected item for event ";	 Catch:{ SQLiteException -> 0x015d }
        r0 = r46;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r21;	 Catch:{ SQLiteException -> 0x015d }
        r46 = r0.append(r1);	 Catch:{ SQLiteException -> 0x015d }
        r0 = r46;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r31;	 Catch:{ SQLiteException -> 0x015d }
        r46 = r0.append(r1);	 Catch:{ SQLiteException -> 0x015d }
        r0 = r46;	 Catch:{ SQLiteException -> 0x015d }
        r19 = r0.toString();	 Catch:{ SQLiteException -> 0x015d }
        goto L_0x01c0;	 Catch:{ SQLiteException -> 0x015d }
    L_0x01bd:
        goto L_0x015e;	 Catch:{ SQLiteException -> 0x015d }
    L_0x01c0:
        r21 = "GoogleAnalyticsTracker";	 Catch:{ SQLiteException -> 0x015d }
        r0 = r21;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r19;	 Catch:{ SQLiteException -> 0x015d }
        android.util.Log.w(r0, r1);	 Catch:{ SQLiteException -> 0x015d }
    L_0x01c9:
        r0 = r30;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r49;	 Catch:{ SQLiteException -> 0x015d }
        r0.setItem(r1);	 Catch:{ SQLiteException -> 0x015d }
        goto L_0x0155;
    L_0x01d1:
        r50 = move-exception;
    L_0x01d2:
        if (r28 == 0) goto L_0x01d9;
    L_0x01d4:
        r0 = r28;
        r0.close();
    L_0x01d9:
        throw r50;
    L_0x01da:
        r0 = r54;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r31;	 Catch:{ SQLiteException -> 0x015d }
        r51 = r0.getCustomVariables(r1);	 Catch:{ SQLiteException -> 0x015d }
        r0 = r30;	 Catch:{ SQLiteException -> 0x015d }
        r1 = r51;	 Catch:{ SQLiteException -> 0x015d }
        r0.setCustomVariableBuffer(r1);	 Catch:{ SQLiteException -> 0x015d }
        goto L_0x019c;
    L_0x01ea:
        if (r20 == 0) goto L_0x01f1;
    L_0x01ec:
        r0 = r20;
        r0.close();
    L_0x01f1:
        r0 = r16;
        r55 = r0.size();
        r0 = r55;
        r0 = new com.google.android.apps.analytics.Event[r0];
        r48 = r0;
        r0 = r16;
        r1 = r48;
        r52 = r0.toArray(r1);
        r53 = r52;
        r53 = (com.google.android.apps.analytics.Event[]) r53;
        r48 = r53;
        return r48;
    L_0x020c:
        r50 = move-exception;
        r28 = 0;
        goto L_0x01d2;
    L_0x0210:
        r50 = move-exception;
        r28 = r20;
        goto L_0x01d2;
    L_0x0214:
        r47 = move-exception;
        r20 = 0;
        goto L_0x01bd;
    L_0x0218:
        return r48;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.apps.analytics.PersistentEventStore.peekEvents(int):com.google.android.apps.analytics.Event[]");
    }

    void putCustomVariables(com.google.android.apps.analytics.Event r23, long r24) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0022 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r22 = this;
        r3 = 1;
        r0 = r22;
        r4 = r0.databaseHelper;
        r5 = r4.getWritableDatabase();	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r23;	 Catch:{ SQLiteException -> 0x00d0 }
        r6 = r0.getCustomVariableBuffer();	 Catch:{ SQLiteException -> 0x00d0 }
        r7 = r6;
        r0 = r22;
        r8 = r0.useStoredVisitorVars;
        if (r8 == 0) goto L_0x0043;
    L_0x0016:
        if (r6 != 0) goto L_0x0022;	 Catch:{ SQLiteException -> 0x00d0 }
    L_0x0018:
        r7 = new com.google.android.apps.analytics.CustomVariableBuffer;	 Catch:{ SQLiteException -> 0x00d0 }
        r7.<init>();	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r23;	 Catch:{ SQLiteException -> 0x00d0 }
        r0.setCustomVariableBuffer(r7);	 Catch:{ SQLiteException -> 0x00d0 }
    L_0x0022:
        r0 = r22;	 Catch:{ SQLiteException -> 0x00d0 }
        r6 = r0.getVisitorVarBuffer();	 Catch:{ SQLiteException -> 0x00d0 }
        r9 = 1;
    L_0x0029:
        r10 = 5;	 Catch:{ SQLiteException -> 0x00d0 }
        if (r9 > r10) goto L_0x003e;	 Catch:{ SQLiteException -> 0x00d0 }
    L_0x002c:
        r11 = r6.getCustomVariableAt(r9);	 Catch:{ SQLiteException -> 0x00d0 }
        r12 = r7.getCustomVariableAt(r9);	 Catch:{ SQLiteException -> 0x00d0 }
        if (r11 == 0) goto L_0x003b;
    L_0x0036:
        if (r12 != 0) goto L_0x003b;	 Catch:{ SQLiteException -> 0x00d0 }
    L_0x0038:
        r7.setCustomVariable(r11);	 Catch:{ SQLiteException -> 0x00d0 }
    L_0x003b:
        r9 = r9 + 1;
        goto L_0x0029;
    L_0x003e:
        r10 = 0;
        r0 = r22;
        r0.useStoredVisitorVars = r10;
    L_0x0043:
        if (r7 == 0) goto L_0x00df;
    L_0x0045:
        r10 = 5;	 Catch:{ SQLiteException -> 0x00d0 }
        if (r3 > r10) goto L_0x00e0;	 Catch:{ SQLiteException -> 0x00d0 }
    L_0x0048:
        r8 = r7.isIndexAvailable(r3);	 Catch:{ SQLiteException -> 0x00d0 }
        if (r8 != 0) goto L_0x00cd;	 Catch:{ SQLiteException -> 0x00d0 }
    L_0x004e:
        r11 = r7.getCustomVariableAt(r3);	 Catch:{ SQLiteException -> 0x00d0 }
        r13 = new android.content.ContentValues;	 Catch:{ SQLiteException -> 0x00d0 }
        r13.<init>();	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r24;	 Catch:{ SQLiteException -> 0x00d0 }
        r14 = java.lang.Long.valueOf(r0);	 Catch:{ SQLiteException -> 0x00d0 }
        r15 = "event_id";	 Catch:{ SQLiteException -> 0x00d0 }
        r13.put(r15, r14);	 Catch:{ SQLiteException -> 0x00d0 }
        r9 = r11.getIndex();	 Catch:{ SQLiteException -> 0x00d0 }
        r16 = java.lang.Integer.valueOf(r9);	 Catch:{ SQLiteException -> 0x00d0 }
        r15 = "cv_index";	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r16;	 Catch:{ SQLiteException -> 0x00d0 }
        r13.put(r15, r0);	 Catch:{ SQLiteException -> 0x00d0 }
        r17 = r11.getName();	 Catch:{ SQLiteException -> 0x00d0 }
        r15 = "cv_name";	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r17;	 Catch:{ SQLiteException -> 0x00d0 }
        r13.put(r15, r0);	 Catch:{ SQLiteException -> 0x00d0 }
        r9 = r11.getScope();	 Catch:{ SQLiteException -> 0x00d0 }
        r16 = java.lang.Integer.valueOf(r9);	 Catch:{ SQLiteException -> 0x00d0 }
        goto L_0x0088;	 Catch:{ SQLiteException -> 0x00d0 }
    L_0x0085:
        goto L_0x0045;	 Catch:{ SQLiteException -> 0x00d0 }
    L_0x0088:
        r15 = "cv_scope";	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r16;	 Catch:{ SQLiteException -> 0x00d0 }
        r13.put(r15, r0);	 Catch:{ SQLiteException -> 0x00d0 }
        r17 = r11.getValue();	 Catch:{ SQLiteException -> 0x00d0 }
        r15 = "cv_value";	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r17;	 Catch:{ SQLiteException -> 0x00d0 }
        r13.put(r15, r0);	 Catch:{ SQLiteException -> 0x00d0 }
        r15 = "custom_variables";	 Catch:{ SQLiteException -> 0x00d0 }
        r18 = "event_id";	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r18;	 Catch:{ SQLiteException -> 0x00d0 }
        r5.insert(r15, r0, r13);	 Catch:{ SQLiteException -> 0x00d0 }
        r19 = new java.lang.StringBuilder;	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r19;	 Catch:{ SQLiteException -> 0x00d0 }
        r0.<init>();	 Catch:{ SQLiteException -> 0x00d0 }
        r15 = "cv_index=";	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r19;	 Catch:{ SQLiteException -> 0x00d0 }
        r19 = r0.append(r15);	 Catch:{ SQLiteException -> 0x00d0 }
        r9 = r11.getIndex();	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r19;	 Catch:{ SQLiteException -> 0x00d0 }
        r19 = r0.append(r9);	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r19;	 Catch:{ SQLiteException -> 0x00d0 }
        r17 = r0.toString();	 Catch:{ SQLiteException -> 0x00d0 }
        r15 = "custom_var_cache";	 Catch:{ SQLiteException -> 0x00d0 }
        r20 = 0;	 Catch:{ SQLiteException -> 0x00d0 }
        r0 = r17;	 Catch:{ SQLiteException -> 0x00d0 }
        r1 = r20;	 Catch:{ SQLiteException -> 0x00d0 }
        r5.update(r15, r13, r0, r1);	 Catch:{ SQLiteException -> 0x00d0 }
    L_0x00cd:
        r3 = r3 + 1;
        goto L_0x0085;
    L_0x00d0:
        r21 = move-exception;
        r0 = r21;
        r17 = r0.toString();
        r15 = "GoogleAnalyticsTracker";
        r0 = r17;
        android.util.Log.e(r15, r0);
        return;
    L_0x00df:
        return;
    L_0x00e0:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.apps.analytics.PersistentEventStore.putCustomVariables(com.google.android.apps.analytics.Event, long):void");
    }

    public void putEvent(com.google.android.apps.analytics.Event r41) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x01d8 in list [B:33:0x01af, B:43:0x01cf]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r40 = this;
        r9 = 0;
        r0 = r40;
        r10 = r0.numStoredEvents;
        r11 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        if (r10 < r11) goto L_0x0011;
    L_0x0009:
        r12 = "GoogleAnalyticsTracker";
        r13 = "Store full. Not storing last event.";
        android.util.Log.w(r12, r13);
        return;
    L_0x0011:
        r0 = r40;
        r14 = r0.sessionUpdated;
        if (r14 != 0) goto L_0x001c;
    L_0x0017:
        r0 = r40;
        r0.storeUpdatedSession();
    L_0x001c:
        r0 = r40;
        r15 = r0.databaseHelper;
        r9 = r15.getWritableDatabase();	 Catch:{ SQLiteException -> 0x01e2 }
        r9.beginTransaction();	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r16 = new android.content.ContentValues;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.<init>();	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r41;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r10 = r0.userId;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r17 = java.lang.Integer.valueOf(r10);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "user_id";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r17;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r41;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r0.accountId;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r18 = r0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "account_id";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r18;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r19 = java.lang.Math.random();	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r21 = 4746794007244308480; // 0x41dfffffffc00000 float:NaN double:2.147483647E9;
        r0 = r19;
        r2 = r21;
        r0 = r0 * r2;
        r19 = r0;
        r10 = (int) r0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r17 = java.lang.Integer.valueOf(r10);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "random_val";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r17;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r40;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r0.timestampFirst;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r23 = r0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r25 = java.lang.Long.valueOf(r0);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "timestamp_first";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r25;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r40;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r0.timestampPrevious;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r23 = r0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r25 = java.lang.Long.valueOf(r0);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "timestamp_previous";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r25;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r40;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r0.timestampCurrent;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r23 = r0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r25 = java.lang.Long.valueOf(r0);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "timestamp_current";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r25;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r40;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r10 = r0.visits;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r17 = java.lang.Integer.valueOf(r10);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "visits";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r17;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r41;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r0.category;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r18 = r0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "category";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r18;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r41;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r0.action;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r18 = r0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "action";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r18;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r41;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r0.label;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r18 = r0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "label";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r18;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r41;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r10 = r0.value;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r17 = java.lang.Integer.valueOf(r10);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "value";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r17;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r41;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r10 = r0.screenWidth;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r17 = java.lang.Integer.valueOf(r10);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "screen_width";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r17;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r41;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r10 = r0.screenHeight;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r17 = java.lang.Integer.valueOf(r10);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "screen_height";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r17;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.put(r12, r1);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "events";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r13 = "event_id";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r16;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r23 = r9.insert(r12, r13, r0);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r27 = -1;
        r26 = (r23 > r27 ? 1 : (r23 == r27 ? 0 : -1));
        if (r26 == 0) goto L_0x01d8;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
    L_0x012c:
        r0 = r40;
        r10 = r0.numStoredEvents;
        r10 = r10 + 1;
        r0 = r40;
        r0.numStoredEvents = r10;
        r11 = 1;
        r0 = new java.lang.String[r11];
        r29 = r0;
        r11 = 0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "event_id";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r29[r11] = r12;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "events";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r31 = 0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r32 = 0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r33 = 0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r34 = 0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r13 = "event_id DESC";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r35 = 0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r9;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r12;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r2 = r29;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r3 = r31;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r4 = r32;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r5 = r33;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r6 = r34;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r7 = r13;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r8 = r35;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r30 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r11 = 0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r30;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.moveToPosition(r11);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r11 = 0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r30;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r23 = r0.getLong(r11);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r30;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.close();	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r41;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r0.category;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r18 = r0;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r12 = "__##GOOGLETRANSACTION##__";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r18;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r14 = r0.equals(r12);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        if (r14 == 0) goto L_0x0195;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
    L_0x0183:
        r0 = r40;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r41;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r2 = r23;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.putTransaction(r1, r2);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
    L_0x018c:
        r9.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
    L_0x018f:
        if (r9 == 0) goto L_0x01e4;
    L_0x0191:
        r9.endTransaction();
        return;
    L_0x0195:
        r0 = r41;
        r0 = r0.category;
        r18 = r0;
        r12 = "__##GOOGLEITEM##__";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0 = r18;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r14 = r0.equals(r12);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        if (r14 == 0) goto L_0x01c5;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
    L_0x01a5:
        r0 = r40;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r41;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r2 = r23;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.putItem(r1, r2);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        goto L_0x018c;
    L_0x01af:
        r36 = move-exception;
        r37 = r36;
    L_0x01b2:
        r0 = r37;	 Catch:{ Throwable -> 0x01e0 }
        r18 = r0.toString();	 Catch:{ Throwable -> 0x01e0 }
        r12 = "GoogleAnalyticsTracker";	 Catch:{ Throwable -> 0x01e0 }
        r0 = r18;	 Catch:{ Throwable -> 0x01e0 }
        android.util.Log.e(r12, r0);	 Catch:{ Throwable -> 0x01e0 }
        if (r9 == 0) goto L_0x01e5;
    L_0x01c1:
        r9.endTransaction();
        return;
    L_0x01c5:
        r0 = r40;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r1 = r41;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r2 = r23;	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r0.putCustomVariables(r1, r2);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        goto L_0x018c;
    L_0x01cf:
        r38 = move-exception;
        r39 = r38;
    L_0x01d2:
        if (r9 == 0) goto L_0x01d7;
    L_0x01d4:
        r9.endTransaction();
    L_0x01d7:
        throw r39;
    L_0x01d8:
        r12 = "GoogleAnalyticsTracker";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        r13 = "Error when attempting to add event to database.";	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        android.util.Log.e(r12, r13);	 Catch:{ SQLiteException -> 0x01af, Throwable -> 0x01cf }
        goto L_0x018f;
    L_0x01e0:
        r39 = move-exception;
        goto L_0x01d2;
    L_0x01e2:
        r37 = move-exception;
        goto L_0x01b2;
    L_0x01e4:
        return;
    L_0x01e5:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.apps.analytics.PersistentEventStore.putEvent(com.google.android.apps.analytics.Event):void");
    }

    void putItem(Event $r1, long $l0) throws  {
        Item $r2 = $r1.getItem();
        if ($r2 == null) {
            Log.w(GoogleAnalyticsTracker.LOG_TAG, "missing item details for event " + $l0);
            return;
        }
        try {
            SQLiteDatabase $r6 = this.databaseHelper.getWritableDatabase();
            ContentValues $r7 = new ContentValues();
            $r7.put(EVENT_ID, Long.valueOf($l0));
            $r7.put(ORDER_ID, $r2.getOrderId());
            $r7.put(ITEM_SKU, $r2.getItemSKU());
            $r7.put(ITEM_NAME, $r2.getItemName());
            $r7.put(ITEM_CATEGORY, $r2.getItemCategory());
            $r7.put(ITEM_PRICE, $r2.getItemPrice() + "");
            $r7.put(ITEM_COUNT, $r2.getItemCount() + "");
            $r6.insert("item_events", EVENT_ID, $r7);
        } catch (SQLiteException $r9) {
            Log.e(GoogleAnalyticsTracker.LOG_TAG, $r9.toString());
        }
    }

    void putTransaction(Event $r1, long $l0) throws  {
        Transaction $r2 = $r1.getTransaction();
        if ($r2 == null) {
            Log.w(GoogleAnalyticsTracker.LOG_TAG, "missing transaction details for event " + $l0);
            return;
        }
        try {
            SQLiteDatabase $r6 = this.databaseHelper.getWritableDatabase();
            ContentValues $r7 = new ContentValues();
            $r7.put(EVENT_ID, Long.valueOf($l0));
            $r7.put(ORDER_ID, $r2.getOrderId());
            $r7.put(STORE_NAME, $r2.getStoreName());
            $r7.put(TOTAL_COST, $r2.getTotalCost() + "");
            $r7.put(TOTAL_TAX, $r2.getTotalTax() + "");
            $r7.put(SHIPPING_COST, $r2.getShippingCost() + "");
            $r6.insert("transaction_events", EVENT_ID, $r7);
        } catch (SQLiteException $r9) {
            Log.e(GoogleAnalyticsTracker.LOG_TAG, $r9.toString());
        }
    }

    public void setReferrer(String $r1) throws  {
        try {
            SQLiteDatabase $r3 = this.databaseHelper.getWritableDatabase();
            ContentValues $r4 = new ContentValues();
            $r4.put("referrer", $r1);
            $r3.insert("install_referrer", null, $r4);
        } catch (SQLiteException $r5) {
            Log.e(GoogleAnalyticsTracker.LOG_TAG, $r5.toString());
        }
    }

    public void startNewVisit() throws  {
        Cursor cursor;
        Throwable $r11;
        SQLiteException $r9;
        this.sessionUpdated = false;
        this.useStoredVisitorVars = true;
        this.numStoredEvents = getNumStoredEvents();
        try {
            SQLiteDatabase $r2 = this.databaseHelper.getWritableDatabase();
            Cursor $r3 = $r2.query(ResManager.mSessionFile, null, null, null, null, null, null);
            cursor = $r3;
            try {
                long $l1;
                long j;
                if ($r3.moveToFirst()) {
                    this.timestampFirst = $r3.getLong(0);
                    this.timestampPrevious = $r3.getLong(2);
                    $l1 = System.currentTimeMillis() / 1000;
                    j = $l1;
                    this.timestampCurrent = $l1;
                    this.visits = $r3.getInt(3) + 1;
                    this.storeId = $r3.getInt(4);
                } else {
                    try {
                        $l1 = System.currentTimeMillis() / 1000;
                        j = $l1;
                        this.timestampFirst = $l1;
                        this.timestampPrevious = j;
                        this.timestampCurrent = j;
                        this.visits = 1;
                        this.storeId = new SecureRandom().nextInt() & ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                        $l1 = new ContentValues();
                        $l1 = this.timestampFirst;
                        Long $r7 = Long.valueOf($l1);
                        $l1.put(TIMESTAMP_FIRST, $r7);
                        $l1 = this.timestampPrevious;
                        $r7 = Long.valueOf($l1);
                        $l1.put(TIMESTAMP_PREVIOUS, $r7);
                        $l1 = this.timestampCurrent;
                        $r7 = Long.valueOf($l1);
                        $l1.put(TIMESTAMP_CURRENT, $r7);
                        ContentValues contentValues = $l1;
                        contentValues.put(VISITS, Integer.valueOf(this.visits));
                        contentValues = $l1;
                        contentValues.put(STORE_ID, Integer.valueOf(this.storeId));
                        $r2.insert(ResManager.mSessionFile, TIMESTAMP_FIRST, $l1);
                    } catch (Throwable th) {
                        $r11 = th;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw $r11;
                    }
                }
                if ($r3 != null) {
                    $r3.close();
                }
            } catch (SQLiteException e) {
                $r9 = e;
                Log.e(GoogleAnalyticsTracker.LOG_TAG, $r9.toString());
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (SQLiteException e2) {
            $r9 = e2;
            cursor = null;
            Log.e(GoogleAnalyticsTracker.LOG_TAG, $r9.toString());
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th2) {
            $r11 = th2;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw $r11;
        }
    }

    void storeUpdatedSession() throws  {
        try {
            SQLiteDatabase $r2 = this.databaseHelper.getWritableDatabase();
            ContentValues $r3 = new ContentValues();
            $r3.put(TIMESTAMP_PREVIOUS, Long.valueOf(this.timestampPrevious));
            $r3.put(TIMESTAMP_CURRENT, Long.valueOf(this.timestampCurrent));
            $r3.put(VISITS, Integer.valueOf(this.visits));
            String[] $r6 = new String[1];
            $r6[0] = Long.toString(this.timestampFirst);
            $r2.update(ResManager.mSessionFile, $r3, "timestamp_first=?", $r6);
            this.sessionUpdated = true;
        } catch (SQLiteException $r8) {
            Log.e(GoogleAnalyticsTracker.LOG_TAG, $r8.toString());
        }
    }
}
