package com.google.android.apps.analytics;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import com.google.android.apps.analytics.Dispatcher.Callbacks;
import com.google.android.apps.analytics.Transaction.Builder;
import java.util.HashMap;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleAnalyticsTracker {
    private static final GoogleAnalyticsTracker INSTANCE = new GoogleAnalyticsTracker();
    public static final String LOG_TAG = "GoogleAnalyticsTracker";
    public static final String PRODUCT = "GoogleAnalytics";
    public static final String VERSION = "1.2";
    public static final String WIRE_VERSION = "4.6ma";
    private String accountId;
    private ConnectivityManager connetivityManager;
    private CustomVariableBuffer customVariableBuffer;
    private boolean debug = false;
    private int dispatchPeriod;
    private Runnable dispatchRunner = new C06381();
    private Dispatcher dispatcher;
    private boolean dispatcherIsBusy;
    private boolean dryRun = false;
    private EventStore eventStore;
    private Handler handler;
    private Map<String, Map<String, Item>> itemMap = new HashMap();
    private Context parent;
    private boolean powerSaveMode;
    private Map<String, Transaction> transactionMap = new HashMap();
    private String userAgentProduct = PRODUCT;
    private String userAgentVersion = VERSION;

    /* compiled from: dalvik_source_com.waze.apk */
    class C06381 implements Runnable {
        C06381() throws  {
        }

        public void run() throws  {
            GoogleAnalyticsTracker.this.dispatch();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    final class DispatcherCallbacks implements Callbacks {

        /* compiled from: dalvik_source_com.waze.apk */
        class C06391 implements Runnable {
            C06391() throws  {
            }

            public void run() throws  {
                GoogleAnalyticsTracker.this.dispatchFinished();
            }
        }

        DispatcherCallbacks() throws  {
        }

        public void dispatchFinished() throws  {
            GoogleAnalyticsTracker.this.handler.post(new C06391());
        }

        public void eventDispatched(long $l0) throws  {
            GoogleAnalyticsTracker.this.eventStore.deleteEvent($l0);
        }
    }

    private GoogleAnalyticsTracker() throws  {
    }

    private void cancelPendingDispatches() throws  {
        this.handler.removeCallbacks(this.dispatchRunner);
    }

    private void createEvent(String $r1, String $r2, String $r3, String $r4, int $i0) throws  {
        Event event = new Event(this.eventStore.getStoreId(), $r1, $r2, $r3, $r4, $i0, this.parent.getResources().getDisplayMetrics().widthPixels, this.parent.getResources().getDisplayMetrics().heightPixels);
        CustomVariableBuffer $r10 = this.customVariableBuffer;
        event.setCustomVariableBuffer($r10);
        this.customVariableBuffer = new CustomVariableBuffer();
        this.eventStore.putEvent(event);
        resetPowerSaveMode();
    }

    public static GoogleAnalyticsTracker getInstance() throws  {
        return INSTANCE;
    }

    private void maybeScheduleNextDispatch() throws  {
        if (this.dispatchPeriod >= 0 && this.handler.postDelayed(this.dispatchRunner, (long) (this.dispatchPeriod * 1000)) && this.debug) {
            Log.v(LOG_TAG, "Scheduled next dispatch");
        }
    }

    private void resetPowerSaveMode() throws  {
        if (this.powerSaveMode) {
            this.powerSaveMode = false;
            maybeScheduleNextDispatch();
        }
    }

    public void addItem(Item $r1) throws  {
        if (((Transaction) this.transactionMap.get($r1.getOrderId())) == null) {
            Log.i(LOG_TAG, "No transaction with orderId " + $r1.getOrderId() + " found, creating one");
            this.transactionMap.put($r1.getOrderId(), new Builder($r1.getOrderId(), 0.0d).build());
        }
        Map $r2 = (Map) this.itemMap.get($r1.getOrderId());
        if ($r2 == null) {
            $r2 = r11;
            HashMap r11 = new HashMap();
            this.itemMap.put($r1.getOrderId(), $r2);
        }
        $r2.put($r1.getItemSKU(), $r1);
    }

    public void addTransaction(Transaction $r1) throws  {
        this.transactionMap.put($r1.getOrderId(), $r1);
    }

    public void clearTransactions() throws  {
        this.transactionMap.clear();
        this.itemMap.clear();
    }

    public boolean dispatch() throws  {
        if (this.debug) {
            Log.v(LOG_TAG, "Called dispatch");
        }
        if (this.dispatcherIsBusy) {
            if (this.debug) {
                Log.v(LOG_TAG, "...but dispatcher was busy");
            }
            maybeScheduleNextDispatch();
            return false;
        }
        NetworkInfo $r2 = this.connetivityManager.getActiveNetworkInfo();
        if ($r2 == null || !$r2.isAvailable()) {
            if (this.debug) {
                Log.v(LOG_TAG, "...but there was no network available");
            }
            maybeScheduleNextDispatch();
            return false;
        } else if (this.eventStore.getNumStoredEvents() != 0) {
            Event[] $r4 = this.eventStore.peekEvents();
            this.dispatcher.dispatchEvents($r4);
            this.dispatcherIsBusy = true;
            maybeScheduleNextDispatch();
            if (this.debug) {
                Log.v(LOG_TAG, "Sending " + $r4.length + " to dispatcher");
            }
            return true;
        } else {
            this.powerSaveMode = true;
            if (!this.debug) {
                return false;
            }
            Log.v(LOG_TAG, "...but there was nothing to dispatch");
            return false;
        }
    }

    void dispatchFinished() throws  {
        this.dispatcherIsBusy = false;
    }

    public boolean getDebug() throws  {
        return this.debug;
    }

    Dispatcher getDispatcher() throws  {
        return this.dispatcher;
    }

    EventStore getEventStore() throws  {
        return this.eventStore;
    }

    public String getVisitorCustomVar(int $i0) throws  {
        if ($i0 >= 1 && $i0 <= 5) {
            return this.eventStore.getVisitorCustomVar($i0);
        }
        throw new IllegalArgumentException(CustomVariable.INDEX_ERROR_MSG);
    }

    public boolean isDryRun() throws  {
        return this.dryRun;
    }

    public boolean setCustomVar(int $i0, String $r1, String $r2) throws  {
        return setCustomVar($i0, $r1, $r2, 3);
    }

    public boolean setCustomVar(int $i0, String $r1, String $r2, int $i1) throws  {
        try {
            CustomVariable $r3 = new CustomVariable($i0, $r1, $r2, $i1);
            if (this.customVariableBuffer == null) {
                this.customVariableBuffer = new CustomVariableBuffer();
            }
            this.customVariableBuffer.setCustomVariable($r3);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void setDebug(boolean $z0) throws  {
        this.debug = $z0;
    }

    public void setDispatchPeriod(int $i0) throws  {
        int $i1 = this.dispatchPeriod;
        this.dispatchPeriod = $i0;
        if ($i1 <= 0) {
            maybeScheduleNextDispatch();
        } else if ($i1 > 0) {
            cancelPendingDispatches();
            maybeScheduleNextDispatch();
        }
    }

    public void setDryRun(boolean $z0) throws  {
        this.dryRun = $z0;
        if (this.dispatcher != null) {
            this.dispatcher.setDryRun($z0);
        }
    }

    public void setProductVersion(String $r1, String $r2) throws  {
        this.userAgentProduct = $r1;
        this.userAgentVersion = $r2;
    }

    public void start(String $r1, int $i0, Context $r2) throws  {
        EventStore $r3;
        Dispatcher $r5;
        if (this.eventStore == null) {
            $r3 = r9;
            PersistentEventStore r9 = new PersistentEventStore(new DataBaseHelper($r2));
        } else {
            $r3 = this.eventStore;
        }
        if (this.dispatcher == null) {
            $r5 = r10;
            NetworkDispatcher r10 = new NetworkDispatcher(this.userAgentProduct, this.userAgentVersion);
            $r5.setDryRun(this.dryRun);
        } else {
            $r5 = this.dispatcher;
        }
        start($r1, $i0, $r2, $r3, $r5);
    }

    void start(String $r1, int $i0, Context $r2, EventStore $r3, Dispatcher $r4) throws  {
        start($r1, $i0, $r2, $r3, $r4, new DispatcherCallbacks());
    }

    void start(String $r1, int $i0, Context $r2, EventStore $r3, Dispatcher $r4, Callbacks $r5) throws  {
        this.accountId = $r1;
        this.parent = $r2;
        this.eventStore = $r3;
        this.eventStore.startNewVisit();
        this.dispatcher = $r4;
        this.dispatcher.init($r5, this.eventStore.getReferrer());
        this.dispatcherIsBusy = false;
        if (this.connetivityManager == null) {
            this.connetivityManager = (ConnectivityManager) this.parent.getSystemService("connectivity");
        }
        if (this.handler == null) {
            this.handler = new Handler($r2.getMainLooper());
        } else {
            cancelPendingDispatches();
        }
        setDispatchPeriod($i0);
    }

    public void start(String $r1, Context $r2) throws  {
        start($r1, -1, $r2);
    }

    public void stop() throws  {
        this.dispatcher.stop();
        cancelPendingDispatches();
    }

    public void trackEvent(String $r1, String $r2, String $r3, int $i0) throws  {
        createEvent(this.accountId, $r1, $r2, $r3, $i0);
    }

    public void trackPageView(String $r1) throws  {
        createEvent(this.accountId, "__##GOOGLEPAGEVIEW##__", $r1, null, -1);
    }

    public void trackTransactions() throws  {
        for (Transaction $r5 : this.transactionMap.values()) {
            EventStore $r7 = this.eventStore;
            int $i0 = $r7.getStoreId();
            String $r8 = this.accountId;
            Context $r9 = this.parent;
            int $i1 = $r9.getResources().getDisplayMetrics().widthPixels;
            $r9 = this.parent;
            Event event = new Event($i0, $r8, "__##GOOGLETRANSACTION##__", "", "", 0, $i1, $r9.getResources().getDisplayMetrics().heightPixels);
            event.setTransaction($r5);
            $r7 = this.eventStore;
            EventStore $r72 = $r7;
            $r7.putEvent(event);
            Map $r1 = (Map) this.itemMap.get($r5.getOrderId());
            if ($r1 != null) {
                for (Item $r13 : $r1.values()) {
                    $r7 = this.eventStore;
                    $i0 = $r7.getStoreId();
                    $r8 = this.accountId;
                    $r9 = this.parent;
                    $i1 = $r9.getResources().getDisplayMetrics().widthPixels;
                    $r9 = this.parent;
                    event = new Event($i0, $r8, "__##GOOGLEITEM##__", "", "", 0, $i1, $r9.getResources().getDisplayMetrics().heightPixels);
                    event.setItem($r13);
                    $r7 = this.eventStore;
                    $r72 = $r7;
                    $r7.putEvent(event);
                }
            }
        }
        clearTransactions();
        resetPowerSaveMode();
    }
}
