package com.facebook.appevents;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import bolts.AppLinks;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.FetchedAppSettings;
import com.facebook.internal.Validate;
import dalvik.annotation.Signature;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppEventsLogger {
    public static final String ACTION_APP_EVENTS_FLUSHED = "com.facebook.sdk.APP_EVENTS_FLUSHED";
    public static final String APP_EVENTS_EXTRA_FLUSH_RESULT = "com.facebook.sdk.APP_EVENTS_FLUSH_RESULT";
    public static final String APP_EVENTS_EXTRA_NUM_EVENTS_FLUSHED = "com.facebook.sdk.APP_EVENTS_NUM_EVENTS_FLUSHED";
    public static final String APP_EVENT_PREFERENCES = "com.facebook.sdk.appEventPreferences";
    private static final int APP_SUPPORTS_ATTRIBUTION_ID_RECHECK_PERIOD_IN_SECONDS = 86400;
    private static final int FLUSH_APP_SESSION_INFO_IN_SECONDS = 30;
    private static final int FLUSH_PERIOD_IN_SECONDS = 15;
    private static final int NUM_LOG_EVENTS_TO_TRY_TO_FLUSH_AFTER = 100;
    private static final String SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT = "_fbSourceApplicationHasBeenSet";
    private static final String TAG = AppEventsLogger.class.getCanonicalName();
    private static String anonymousAppDeviceGUID;
    private static Context applicationContext;
    private static ScheduledThreadPoolExecutor backgroundExecutor;
    private static FlushBehavior flushBehavior = FlushBehavior.AUTO;
    private static boolean isOpenedByApplink;
    private static boolean requestInFlight;
    private static String sourceApplication;
    private static Map<AccessTokenAppIdPair, SessionEventsState> stateMap = new ConcurrentHashMap();
    private static Object staticLock = new Object();
    private final AccessTokenAppIdPair accessTokenAppId;
    private final String contextName;

    static class C05003 implements Runnable {
        C05003() throws  {
        }

        public void run() throws  {
            if (AppEventsLogger.getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY) {
                AppEventsLogger.flushAndWait(FlushReason.TIMER);
            }
        }
    }

    static class C05014 implements Runnable {
        C05014() throws  {
        }

        public void run() throws  {
            HashSet<String> $r1 = new HashSet();
            synchronized (AppEventsLogger.staticLock) {
                for (AccessTokenAppIdPair applicationId : AppEventsLogger.stateMap.keySet()) {
                    $r1.add(applicationId.getApplicationId());
                }
            }
            for (String queryAppSettings : $r1) {
                Utility.queryAppSettings(queryAppSettings, true);
            }
        }
    }

    private static class AccessTokenAppIdPair implements Serializable {
        private static final long serialVersionUID = 1;
        private final String accessTokenString;
        private final String applicationId;

        private static class SerializationProxyV1 implements Serializable {
            private static final long serialVersionUID = -2488473066578201069L;
            private final String accessTokenString;
            private final String appId;

            private SerializationProxyV1(String $r1, String $r2) throws  {
                this.accessTokenString = $r1;
                this.appId = $r2;
            }

            private Object readResolve() throws  {
                return new AccessTokenAppIdPair(this.accessTokenString, this.appId);
            }
        }

        AccessTokenAppIdPair(AccessToken $r1) throws  {
            this($r1.getToken(), FacebookSdk.getApplicationId());
        }

        AccessTokenAppIdPair(String $r2, String $r1) throws  {
            if (Utility.isNullOrEmpty($r2)) {
                $r2 = null;
            }
            this.accessTokenString = $r2;
            this.applicationId = $r1;
        }

        String getAccessTokenString() throws  {
            return this.accessTokenString;
        }

        String getApplicationId() throws  {
            return this.applicationId;
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = this.accessTokenString == null ? 0 : this.accessTokenString.hashCode();
            if (this.applicationId != null) {
                $i0 = this.applicationId.hashCode();
            }
            return $i1 ^ $i0;
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof AccessTokenAppIdPair)) {
                return false;
            }
            AccessTokenAppIdPair $r2 = (AccessTokenAppIdPair) $r1;
            if (Utility.areObjectsEqual($r2.accessTokenString, this.accessTokenString)) {
                return Utility.areObjectsEqual($r2.applicationId, this.applicationId);
            } else {
                return false;
            }
        }

        private Object writeReplace() throws  {
            return new SerializationProxyV1(this.accessTokenString, this.applicationId);
        }
    }

    static class AppEvent implements Serializable {
        private static final long serialVersionUID = 1;
        private static final HashSet<String> validatedIdentifiers = new HashSet();
        private boolean isImplicit;
        private JSONObject jsonObject;
        private String name;

        private static class SerializationProxyV1 implements Serializable {
            private static final long serialVersionUID = -2488473066578201069L;
            private final boolean isImplicit;
            private final String jsonString;

            private SerializationProxyV1(String $r1, boolean $z0) throws  {
                this.jsonString = $r1;
                this.isImplicit = $z0;
            }

            private Object readResolve() throws JSONException {
                return new AppEvent(this.jsonString, this.isImplicit);
            }
        }

        public AppEvent(String $r1, String $r2, Double $r3, Bundle $r4, boolean $z0) throws  {
            LoggingBehavior loggingBehavior;
            AppEvent appEvent = this;
            try {
                Object[] $r11;
                validateIdentifier($r2);
                this.name = $r2;
                this.isImplicit = $z0;
                this.jsonObject = new JSONObject();
                this.jsonObject.put("_eventName", $r2);
                this.jsonObject.put("_logTime", System.currentTimeMillis() / 1000);
                this.jsonObject.put("_ui", $r1);
                if ($r3 != null) {
                    this.jsonObject.put("_valueToSum", $r3.doubleValue());
                }
                if (this.isImplicit) {
                    this.jsonObject.put("_implicitlyLogged", AppEventsConstants.EVENT_PARAM_VALUE_YES);
                }
                if ($r4 != null) {
                    for (String $r12 : $r4.keySet()) {
                        validateIdentifier($r12);
                        Object $r10 = $r4.get($r12);
                        if (($r10 instanceof String) || ($r10 instanceof Number)) {
                            this.jsonObject.put($r12, $r10.toString());
                        } else {
                            $r11 = new Object[2];
                            $r11[0] = $r10;
                            $r11[1] = $r12;
                            try {
                                throw new FacebookException(String.format("Parameter value '%s' for key '%s' should be a string or a numeric type.", $r11));
                            } catch (FacebookException $r5) {
                                loggingBehavior = LoggingBehavior.APP_EVENTS;
                                Logger.log(loggingBehavior, "AppEvents", "Invalid app event name or parameter:", $r5.toString());
                                this.jsonObject = null;
                                return;
                            }
                        }
                    }
                }
                if (!this.isImplicit) {
                    LoggingBehavior $r122 = LoggingBehavior.APP_EVENTS;
                    $r11 = new Object[1];
                    $r11[0] = this.jsonObject.toString();
                    Logger.log($r122, "AppEvents", "Created app event '%s'", $r11);
                }
            } catch (JSONException $r6) {
                loggingBehavior = LoggingBehavior.APP_EVENTS;
                Logger.log(loggingBehavior, "AppEvents", "JSON encoding for app event failed: '%s'", $r6.toString());
                this.jsonObject = null;
            }
        }

        public String getName() throws  {
            return this.name;
        }

        private AppEvent(String $r1, boolean $z0) throws JSONException {
            this.jsonObject = new JSONObject($r1);
            this.isImplicit = $z0;
        }

        public boolean getIsImplicit() throws  {
            return this.isImplicit;
        }

        public JSONObject getJSONObject() throws  {
            return this.jsonObject;
        }

        private void validateIdentifier(String $r1) throws FacebookException {
            if ($r1 == null || $r1.length() == 0 || $r1.length() > 40) {
                if ($r1 == null) {
                    $r1 = "<None Provided>";
                }
                throw new FacebookException(String.format(Locale.ROOT, "Identifier '%s' must be less than %d characters", new Object[]{$r1, Integer.valueOf(40)}));
            }
            synchronized (validatedIdentifiers) {
                boolean $z0 = validatedIdentifiers.contains($r1);
            }
            if (!$z0) {
                if ($r1.matches("^[0-9a-zA-Z_]+[0-9a-zA-Z _-]*$")) {
                    synchronized (validatedIdentifiers) {
                        validatedIdentifiers.add($r1);
                    }
                    return;
                }
                throw new FacebookException(String.format("Skipping event named '%s' due to illegal name - must be under 40 chars and alphanumeric, _, - or space, and not start with a space or hyphen.", new Object[]{$r1}));
            }
        }

        private Object writeReplace() throws  {
            return new SerializationProxyV1(this.jsonObject.toString(), this.isImplicit);
        }

        public String toString() throws  {
            return String.format("\"%s\", implicit: %b, json: %s", new Object[]{this.jsonObject.optString("_eventName"), Boolean.valueOf(this.isImplicit), this.jsonObject.toString()});
        }
    }

    public enum FlushBehavior {
        AUTO,
        EXPLICIT_ONLY
    }

    private enum FlushReason {
        EXPLICIT,
        TIMER,
        SESSION_CHANGE,
        PERSISTED_EVENTS,
        EVENT_THRESHOLD,
        EAGER_FLUSHING_EVENT
    }

    private enum FlushResult {
        SUCCESS,
        SERVER_ERROR,
        NO_CONNECTIVITY,
        UNKNOWN_ERROR
    }

    private static class FlushStatistics {
        public int numEvents;
        public FlushResult result;

        private FlushStatistics() throws  {
            this.numEvents = 0;
            this.result = FlushResult.SUCCESS;
        }
    }

    static class PersistedAppSessionInfo {
        private static final String PERSISTED_SESSION_INFO_FILENAME = "AppEventsLogger.persistedsessioninfo";
        private static final Runnable appSessionInfoFlushRunnable = new C05051();
        private static Map<AccessTokenAppIdPair, FacebookTimeSpentData> appSessionInfoMap;
        private static boolean hasChanges = false;
        private static boolean isLoaded = false;
        private static final Object staticLock = new Object();

        static class C05051 implements Runnable {
            C05051() throws  {
            }

            public void run() throws  {
                PersistedAppSessionInfo.saveAppSessionInformation(AppEventsLogger.applicationContext);
            }
        }

        PersistedAppSessionInfo() throws  {
        }

        private static void restoreAppSessionInformation(Context $r0) throws  {
            Exception $r11;
            Throwable $r15;
            ObjectInputStream $r3 = null;
            synchronized (staticLock) {
                try {
                    if (!isLoaded) {
                        ObjectInputStream $r1 = new ObjectInputStream($r0.openFileInput(PERSISTED_SESSION_INFO_FILENAME));
                        try {
                        } catch (FileNotFoundException e) {
                            $r3 = $r1;
                            Utility.closeQuietly($r3);
                            $r0.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                            if (appSessionInfoMap == null) {
                                appSessionInfoMap = new HashMap();
                            }
                            isLoaded = true;
                            hasChanges = false;
                        }
                        try {
                            appSessionInfoMap = (HashMap) $r1.readObject();
                            Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "App session info loaded");
                        } catch (Exception e2) {
                            $r11 = e2;
                            $r3 = $r1;
                            try {
                                Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + $r11.toString());
                                Utility.closeQuietly($r3);
                                $r0.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                                if (appSessionInfoMap == null) {
                                    appSessionInfoMap = new HashMap();
                                }
                                isLoaded = true;
                                hasChanges = false;
                            } catch (Throwable th) {
                                $r15 = th;
                                Utility.closeQuietly($r3);
                                $r0.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                                if (appSessionInfoMap == null) {
                                    appSessionInfoMap = new HashMap();
                                }
                                isLoaded = true;
                                hasChanges = false;
                                throw $r15;
                            }
                        } catch (Throwable th2) {
                            $r15 = th2;
                            $r3 = $r1;
                            Utility.closeQuietly($r3);
                            $r0.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                            if (appSessionInfoMap == null) {
                                appSessionInfoMap = new HashMap();
                            }
                            isLoaded = true;
                            hasChanges = false;
                            throw $r15;
                        }
                        try {
                            Utility.closeQuietly($r1);
                            $r0.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                            if (appSessionInfoMap == null) {
                                appSessionInfoMap = new HashMap();
                            }
                            isLoaded = true;
                            hasChanges = false;
                        } catch (Throwable th3) {
                            Throwable $r10 = th3;
                            throw $r10;
                        }
                    }
                } catch (FileNotFoundException e3) {
                } catch (Exception e4) {
                    $r11 = e4;
                    Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + $r11.toString());
                    Utility.closeQuietly($r3);
                    $r0.deleteFile(PERSISTED_SESSION_INFO_FILENAME);
                    if (appSessionInfoMap == null) {
                        appSessionInfoMap = new HashMap();
                    }
                    isLoaded = true;
                    hasChanges = false;
                } catch (Throwable th4) {
                    $r10 = th4;
                    throw $r10;
                }
            }
        }

        static void saveAppSessionInformation(Context $r0) throws  {
            Exception $r8;
            Throwable $r13;
            Throwable $r12;
            ObjectOutputStream $r3 = null;
            synchronized (staticLock) {
                try {
                    if (hasChanges) {
                        try {
                            ObjectOutputStream $r1 = new ObjectOutputStream(new BufferedOutputStream($r0.openFileOutput(PERSISTED_SESSION_INFO_FILENAME, 0)));
                            try {
                                try {
                                    $r1.writeObject(appSessionInfoMap);
                                    hasChanges = false;
                                    Logger.log(LoggingBehavior.APP_EVENTS, "AppEvents", "App session info saved");
                                } catch (Exception e) {
                                    $r8 = e;
                                    $r3 = $r1;
                                    try {
                                        Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + $r8.toString());
                                        Utility.closeQuietly($r3);
                                    } catch (Throwable th) {
                                        $r13 = th;
                                        Utility.closeQuietly($r3);
                                        throw $r13;
                                    }
                                }
                                try {
                                    Utility.closeQuietly($r1);
                                } catch (Throwable th2) {
                                    $r12 = th2;
                                    throw $r12;
                                }
                            } catch (Throwable th3) {
                                $r13 = th3;
                                $r3 = $r1;
                                Utility.closeQuietly($r3);
                                throw $r13;
                            }
                        } catch (Exception e2) {
                            $r8 = e2;
                            Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + $r8.toString());
                            Utility.closeQuietly($r3);
                        }
                    }
                } catch (Throwable th4) {
                    $r12 = th4;
                    throw $r12;
                }
            }
        }

        static void onResume(Context $r0, AccessTokenAppIdPair $r1, AppEventsLogger $r2, long $l0, String $r3) throws  {
            synchronized (staticLock) {
                getTimeSpentData($r0, $r1).onResume($r2, $l0, $r3);
                onTimeSpentDataUpdate();
            }
        }

        static void onSuspend(Context $r0, AccessTokenAppIdPair $r1, AppEventsLogger $r2, long $l0) throws  {
            synchronized (staticLock) {
                getTimeSpentData($r0, $r1).onSuspend($r2, $l0);
                onTimeSpentDataUpdate();
            }
        }

        private static FacebookTimeSpentData getTimeSpentData(Context $r0, AccessTokenAppIdPair $r1) throws  {
            restoreAppSessionInformation($r0);
            FacebookTimeSpentData $r4 = (FacebookTimeSpentData) appSessionInfoMap.get($r1);
            if ($r4 != null) {
                return $r4;
            }
            $r4 = new FacebookTimeSpentData();
            appSessionInfoMap.put($r1, $r4);
            return $r4;
        }

        private static void onTimeSpentDataUpdate() throws  {
            if (!hasChanges) {
                hasChanges = true;
                AppEventsLogger.backgroundExecutor.schedule(appSessionInfoFlushRunnable, 30, TimeUnit.SECONDS);
            }
        }
    }

    static class PersistedEvents {
        static final String PERSISTED_EVENTS_FILENAME = "AppEventsLogger.persistedevents";
        private static Object staticLock = new Object();
        private Context context;
        private HashMap<AccessTokenAppIdPair, List<AppEvent>> persistedEvents = new HashMap();

        private PersistedEvents(Context $r1) throws  {
            this.context = $r1;
        }

        public static PersistedEvents readAndClearStore(Context $r0) throws  {
            PersistedEvents $r1;
            synchronized (staticLock) {
                $r1 = new PersistedEvents($r0);
                $r1.readAndClearStore();
            }
            return $r1;
        }

        public static void persistEvents(Context $r0, AccessTokenAppIdPair $r1, SessionEventsState $r2) throws  {
            HashMap $r3 = new HashMap();
            $r3.put($r1, $r2);
            persistEvents($r0, $r3);
        }

        public static void persistEvents(@Signature({"(", "Landroid/content/Context;", "Ljava/util/Map", "<", "Lcom/facebook/appevents/AppEventsLogger$AccessTokenAppIdPair;", "Lcom/facebook/appevents/AppEventsLogger$SessionEventsState;", ">;)V"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Ljava/util/Map", "<", "Lcom/facebook/appevents/AppEventsLogger$AccessTokenAppIdPair;", "Lcom/facebook/appevents/AppEventsLogger$SessionEventsState;", ">;)V"}) Map<AccessTokenAppIdPair, SessionEventsState> $r1) throws  {
            synchronized (staticLock) {
                PersistedEvents $r3 = readAndClearStore($r0);
                for (Entry $r7 : $r1.entrySet()) {
                    List $r9 = ((SessionEventsState) $r7.getValue()).getEventsToPersist();
                    if ($r9.size() != 0) {
                        $r3.addEvents((AccessTokenAppIdPair) $r7.getKey(), $r9);
                    }
                }
                $r3.write();
            }
        }

        public Set<AccessTokenAppIdPair> keySet() throws  {
            return this.persistedEvents.keySet();
        }

        public List<AppEvent> getEvents(@Signature({"(", "Lcom/facebook/appevents/AppEventsLogger$AccessTokenAppIdPair;", ")", "Ljava/util/List", "<", "Lcom/facebook/appevents/AppEventsLogger$AppEvent;", ">;"}) AccessTokenAppIdPair $r1) throws  {
            return (List) this.persistedEvents.get($r1);
        }

        private void write() throws  {
            Exception $r7;
            Throwable $r11;
            ObjectOutputStream $r2 = null;
            try {
                ObjectOutputStream $r1 = new ObjectOutputStream(new BufferedOutputStream(this.context.openFileOutput(PERSISTED_EVENTS_FILENAME, 0)));
                try {
                    $r1.writeObject(this.persistedEvents);
                    Utility.closeQuietly($r1);
                } catch (Exception e) {
                    $r7 = e;
                    $r2 = $r1;
                    try {
                        Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + $r7.toString());
                        Utility.closeQuietly($r2);
                    } catch (Throwable th) {
                        $r11 = th;
                        Utility.closeQuietly($r2);
                        throw $r11;
                    }
                } catch (Throwable th2) {
                    $r11 = th2;
                    $r2 = $r1;
                    Utility.closeQuietly($r2);
                    throw $r11;
                }
            } catch (Exception e2) {
                $r7 = e2;
                Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + $r7.toString());
                Utility.closeQuietly($r2);
            }
        }

        private void readAndClearStore() throws  {
            Exception $r10;
            Throwable $r14;
            ObjectInputStream $r2 = null;
            try {
                ObjectInputStream $r1 = new ObjectInputStream(new BufferedInputStream(this.context.openFileInput(PERSISTED_EVENTS_FILENAME)));
                try {
                } catch (FileNotFoundException e) {
                    $r2 = $r1;
                    Utility.closeQuietly($r2);
                }
                try {
                    HashMap $r7 = (HashMap) $r1.readObject();
                    this.context.getFileStreamPath(PERSISTED_EVENTS_FILENAME).delete();
                    this.persistedEvents = $r7;
                    Utility.closeQuietly($r1);
                } catch (Exception e2) {
                    $r10 = e2;
                    $r2 = $r1;
                    try {
                        Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + $r10.toString());
                        Utility.closeQuietly($r2);
                    } catch (Throwable th) {
                        $r14 = th;
                        Utility.closeQuietly($r2);
                        throw $r14;
                    }
                } catch (Throwable th2) {
                    $r14 = th2;
                    $r2 = $r1;
                    Utility.closeQuietly($r2);
                    throw $r14;
                }
            } catch (FileNotFoundException e3) {
                Utility.closeQuietly($r2);
            } catch (Exception e4) {
                $r10 = e4;
                Log.d(AppEventsLogger.TAG, "Got unexpected exception: " + $r10.toString());
                Utility.closeQuietly($r2);
            }
        }

        public void addEvents(@Signature({"(", "Lcom/facebook/appevents/AppEventsLogger$AccessTokenAppIdPair;", "Ljava/util/List", "<", "Lcom/facebook/appevents/AppEventsLogger$AppEvent;", ">;)V"}) AccessTokenAppIdPair $r1, @Signature({"(", "Lcom/facebook/appevents/AppEventsLogger$AccessTokenAppIdPair;", "Ljava/util/List", "<", "Lcom/facebook/appevents/AppEventsLogger$AppEvent;", ">;)V"}) List<AppEvent> $r2) throws  {
            if (!this.persistedEvents.containsKey($r1)) {
                this.persistedEvents.put($r1, new ArrayList());
            }
            ((List) this.persistedEvents.get($r1)).addAll($r2);
        }
    }

    static class SessionEventsState {
        public static final String ENCODED_EVENTS_KEY = "encoded_events";
        public static final String EVENT_COUNT_KEY = "event_count";
        public static final String NUM_SKIPPED_KEY = "num_skipped";
        private final int MAX_ACCUMULATED_LOG_EVENTS = 1000;
        private List<AppEvent> accumulatedEvents = new ArrayList();
        private String anonymousAppDeviceGUID;
        private AttributionIdentifiers attributionIdentifiers;
        private List<AppEvent> inFlightEvents = new ArrayList();
        private int numSkippedEventsDueToFullBuffer;
        private String packageName;

        private void populateRequest(com.facebook.GraphRequest r14, int r15, org.json.JSONArray r16, boolean r17) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x001a in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r13 = this;
            r1 = com.facebook.internal.AppEventsLoggerUtility.GraphAPIActivityType.CUSTOM_APP_EVENTS;
            r2 = r13.attributionIdentifiers;
            r3 = r13.anonymousAppDeviceGUID;
            r4 = com.facebook.appevents.AppEventsLogger.applicationContext;	 Catch:{ JSONException -> 0x0041 }
            r0 = r17;	 Catch:{ JSONException -> 0x0041 }
            r5 = com.facebook.internal.AppEventsLoggerUtility.getJSONObjectForGraphAPICall(r1, r2, r3, r0, r4);	 Catch:{ JSONException -> 0x0041 }
            r6 = r5;
            r7 = r13.numSkippedEventsDueToFullBuffer;
            if (r7 <= 0) goto L_0x001a;	 Catch:{ JSONException -> 0x0041 }
        L_0x0015:
            r8 = "num_skipped_events";	 Catch:{ JSONException -> 0x0041 }
            r5.put(r8, r15);	 Catch:{ JSONException -> 0x0041 }
        L_0x001a:
            r14.setGraphObject(r6);
            r9 = r14.getParameters();
            r10 = r9;
            if (r9 != 0) goto L_0x0029;
        L_0x0024:
            r10 = new android.os.Bundle;
            r10.<init>();
        L_0x0029:
            r0 = r16;
            r3 = r0.toString();
            if (r3 == 0) goto L_0x003d;
        L_0x0031:
            r11 = r13.getStringAsByteArray(r3);
            r8 = "custom_events_file";
            r10.putByteArray(r8, r11);
            r14.setTag(r3);
        L_0x003d:
            r14.setParameters(r10);
            return;
        L_0x0041:
            r12 = move-exception;
            r6 = new org.json.JSONObject;
            r6.<init>();
            goto L_0x001a;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.AppEventsLogger.SessionEventsState.populateRequest(com.facebook.GraphRequest, int, org.json.JSONArray, boolean):void");
        }

        public SessionEventsState(AttributionIdentifiers $r1, String $r2, String $r3) throws  {
            this.attributionIdentifiers = $r1;
            this.packageName = $r2;
            this.anonymousAppDeviceGUID = $r3;
        }

        public synchronized void addEvent(AppEvent $r1) throws  {
            if (this.accumulatedEvents.size() + this.inFlightEvents.size() >= 1000) {
                this.numSkippedEventsDueToFullBuffer++;
            } else {
                this.accumulatedEvents.add($r1);
            }
        }

        public synchronized int getAccumulatedEventCount() throws  {
            return this.accumulatedEvents.size();
        }

        public synchronized void clearInFlightAndStats(boolean $z0) throws  {
            if ($z0) {
                this.accumulatedEvents.addAll(this.inFlightEvents);
            }
            this.inFlightEvents.clear();
            this.numSkippedEventsDueToFullBuffer = 0;
        }

        public int populateRequest(GraphRequest $r1, boolean $z0, boolean $z1) throws  {
            synchronized (this) {
                int $i0 = this.numSkippedEventsDueToFullBuffer;
                this.inFlightEvents.addAll(this.accumulatedEvents);
                this.accumulatedEvents.clear();
                JSONArray $r2 = new JSONArray();
                for (AppEvent $r7 : this.inFlightEvents) {
                    if ($z0 || !$r7.getIsImplicit()) {
                        $r2.put($r7.getJSONObject());
                    }
                }
                if ($r2.length() == 0) {
                    return 0;
                }
                populateRequest($r1, $i0, $r2, $z1);
                return $r2.length();
            }
        }

        public synchronized List<AppEvent> getEventsToPersist() throws  {
            List r3;
            r3 = this.accumulatedEvents;
            this.accumulatedEvents = new ArrayList();
            return r3;
        }

        public synchronized void accumulatePersistedEvents(@Signature({"(", "Ljava/util/List", "<", "Lcom/facebook/appevents/AppEventsLogger$AppEvent;", ">;)V"}) List<AppEvent> $r1) throws  {
            this.accumulatedEvents.addAll($r1);
        }

        private byte[] getStringAsByteArray(String $r1) throws  {
            try {
                return $r1.getBytes("UTF-8");
            } catch (Exception $r2) {
                Utility.logd("Encoding exception: ", $r2);
                return null;
            }
        }
    }

    public static void activateApp(Context $r0) throws  {
        FacebookSdk.sdkInitialize($r0);
        activateApp($r0, Utility.getMetadataApplicationId($r0));
    }

    public static void activateApp(Context $r0, String $r1) throws  {
        if ($r0 == null || $r1 == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        if ($r0 instanceof Activity) {
            setSourceApplication((Activity) $r0);
        } else {
            resetSourceApplication();
            Log.d(AppEventsLogger.class.getName(), "To set source application the context of activateApp must be an instance of Activity");
        }
        FacebookSdk.publishInstallAsync($r0, $r1);
        final AppEventsLogger $r2 = new AppEventsLogger($r0, $r1, null);
        final long $l0 = System.currentTimeMillis();
        $r1 = getSourceApplication();
        backgroundExecutor.execute(new Runnable() {
            public void run() throws  {
                $r2.logAppSessionResumeEvent($l0, $r1);
            }
        });
    }

    public static void deactivateApp(Context $r0) throws  {
        deactivateApp($r0, Utility.getMetadataApplicationId($r0));
    }

    public static void deactivateApp(Context $r0, String $r1) throws  {
        if ($r0 == null || $r1 == null) {
            throw new IllegalArgumentException("Both context and applicationId must be non-null");
        }
        resetSourceApplication();
        final AppEventsLogger $r2 = new AppEventsLogger($r0, $r1, null);
        final long $l0 = System.currentTimeMillis();
        backgroundExecutor.execute(new Runnable() {
            public void run() throws  {
                $r2.logAppSessionSuspendEvent($l0);
            }
        });
    }

    private void logAppSessionResumeEvent(long $l0, String $r1) throws  {
        PersistedAppSessionInfo.onResume(applicationContext, this.accessTokenAppId, this, $l0, $r1);
    }

    private void logAppSessionSuspendEvent(long $l0) throws  {
        PersistedAppSessionInfo.onSuspend(applicationContext, this.accessTokenAppId, this, $l0);
    }

    public static AppEventsLogger newLogger(Context $r0) throws  {
        return new AppEventsLogger($r0, null, null);
    }

    public static AppEventsLogger newLogger(Context $r0, AccessToken $r1) throws  {
        return new AppEventsLogger($r0, null, $r1);
    }

    public static AppEventsLogger newLogger(Context $r0, String $r1, AccessToken $r2) throws  {
        return new AppEventsLogger($r0, $r1, $r2);
    }

    public static AppEventsLogger newLogger(Context $r0, String $r1) throws  {
        return new AppEventsLogger($r0, $r1, null);
    }

    public static FlushBehavior getFlushBehavior() throws  {
        FlushBehavior r2;
        synchronized (staticLock) {
            r2 = flushBehavior;
        }
        return r2;
    }

    public static void setFlushBehavior(FlushBehavior $r0) throws  {
        synchronized (staticLock) {
            flushBehavior = $r0;
        }
    }

    public void logEvent(String $r1) throws  {
        logEvent($r1, null);
    }

    public void logEvent(String $r1, double $d0) throws  {
        logEvent($r1, $d0, null);
    }

    public void logEvent(String $r1, Bundle $r2) throws  {
        logEvent($r1, null, $r2, false);
    }

    public void logEvent(String $r1, double $d0, Bundle $r2) throws  {
        logEvent($r1, Double.valueOf($d0), $r2, false);
    }

    public void logPurchase(BigDecimal $r1, Currency $r2) throws  {
        logPurchase($r1, $r2, null);
    }

    public void logPurchase(BigDecimal $r1, Currency $r2, Bundle $r3) throws  {
        if ($r1 == null) {
            notifyDeveloperError("purchaseAmount cannot be null");
        } else if ($r2 == null) {
            notifyDeveloperError("currency cannot be null");
        } else {
            if ($r3 == null) {
                $r3 = new Bundle();
            }
            $r3.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, $r2.getCurrencyCode());
            logEvent(AppEventsConstants.EVENT_NAME_PURCHASED, $r1.doubleValue(), $r3);
            eagerFlush();
        }
    }

    public void flush() throws  {
        flush(FlushReason.EXPLICIT);
    }

    public static void onContextStop() throws  {
        PersistedEvents.persistEvents(applicationContext, stateMap);
    }

    public boolean isValidForAccessToken(AccessToken $r1) throws  {
        return this.accessTokenAppId.equals(new AccessTokenAppIdPair($r1));
    }

    public void logSdkEvent(String $r1, Double $r2, Bundle $r3) throws  {
        logEvent($r1, $r2, $r3, true);
    }

    public String getApplicationId() throws  {
        return this.accessTokenAppId.getApplicationId();
    }

    private AppEventsLogger(Context $r1, String $r2, AccessToken $r3) throws  {
        Validate.notNull($r1, "context");
        this.contextName = Utility.getActivityName($r1);
        if ($r3 == null) {
            $r3 = AccessToken.getCurrentAccessToken();
        }
        if ($r3 == null || !($r2 == null || $r2.equals($r3.getApplicationId()))) {
            if ($r2 == null) {
                $r2 = Utility.getMetadataApplicationId($r1);
            }
            this.accessTokenAppId = new AccessTokenAppIdPair(null, $r2);
        } else {
            this.accessTokenAppId = new AccessTokenAppIdPair($r3);
        }
        synchronized (staticLock) {
            if (applicationContext == null) {
                applicationContext = $r1.getApplicationContext();
            }
        }
        initializeTimersIfNeeded();
    }

    private static void initializeTimersIfNeeded() throws  {
        synchronized (staticLock) {
            if (backgroundExecutor != null) {
                return;
            }
            backgroundExecutor = new ScheduledThreadPoolExecutor(1);
            C05003 $r0 = new C05003();
            backgroundExecutor.scheduleAtFixedRate($r0, 0, 15, TimeUnit.SECONDS);
            C05014 c05014 = new C05014();
            backgroundExecutor.scheduleAtFixedRate(c05014, 0, 86400, TimeUnit.SECONDS);
        }
    }

    private void logEvent(String $r1, Double $r2, Bundle $r3, boolean $z0) throws  {
        logEvent(applicationContext, new AppEvent(this.contextName, $r1, $r2, $r3, $z0), this.accessTokenAppId);
    }

    private static void logEvent(final Context $r0, final AppEvent $r1, final AccessTokenAppIdPair $r2) throws  {
        FacebookSdk.getExecutor().execute(new Runnable() {
            public void run() throws  {
                AppEventsLogger.getSessionEventsState($r0, $r2).addEvent($r1);
                AppEventsLogger.flushIfNecessary();
            }
        });
    }

    static void eagerFlush() throws  {
        if (getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY) {
            flush(FlushReason.EAGER_FLUSHING_EVENT);
        }
    }

    private static void flushIfNecessary() throws  {
        synchronized (staticLock) {
            if (getFlushBehavior() != FlushBehavior.EXPLICIT_ONLY && getAccumulatedEventCount() > 100) {
                flush(FlushReason.EVENT_THRESHOLD);
            }
        }
    }

    private static int getAccumulatedEventCount() throws  {
        int $i0;
        synchronized (staticLock) {
            $i0 = 0;
            for (SessionEventsState accumulatedEventCount : stateMap.values()) {
                $i0 += accumulatedEventCount.getAccumulatedEventCount();
            }
        }
        return $i0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.facebook.appevents.AppEventsLogger.SessionEventsState getSessionEventsState(android.content.Context r11, com.facebook.appevents.AppEventsLogger.AccessTokenAppIdPair r12) throws  {
        /*
        r0 = stateMap;
        r1 = r0.get(r12);
        r3 = r1;
        r3 = (com.facebook.appevents.AppEventsLogger.SessionEventsState) r3;
        r2 = r3;
        r4 = 0;
        if (r2 != 0) goto L_0x0011;
    L_0x000d:
        r4 = com.facebook.internal.AttributionIdentifiers.getAttributionIdentifiers(r11);
    L_0x0011:
        r1 = staticLock;
        monitor-enter(r1);
        r0 = stateMap;	 Catch:{ Throwable -> 0x0036 }
        r5 = r0.get(r12);	 Catch:{ Throwable -> 0x0036 }
        r6 = r5;
        r6 = (com.facebook.appevents.AppEventsLogger.SessionEventsState) r6;	 Catch:{ Throwable -> 0x0036 }
        r2 = r6;
        r7 = r2;
        if (r2 != 0) goto L_0x0034;
    L_0x0021:
        r2 = new com.facebook.appevents.AppEventsLogger$SessionEventsState;	 Catch:{ Throwable -> 0x0036 }
        r8 = r11.getPackageName();	 Catch:{ Throwable -> 0x0036 }
        r9 = getAnonymousAppDeviceGUID(r11);	 Catch:{ Throwable -> 0x0036 }
        r2.<init>(r4, r8, r9);	 Catch:{ Throwable -> 0x0036 }
        r0 = stateMap;	 Catch:{ Throwable -> 0x0039 }
        r0.put(r12, r2);	 Catch:{ Throwable -> 0x0039 }
        r7 = r2;
    L_0x0034:
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0036 }
        return r7;
    L_0x0036:
        r10 = move-exception;
    L_0x0037:
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0036 }
        throw r10;
    L_0x0039:
        r10 = move-exception;
        goto L_0x0037;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.AppEventsLogger.getSessionEventsState(android.content.Context, com.facebook.appevents.AppEventsLogger$AccessTokenAppIdPair):com.facebook.appevents.AppEventsLogger$SessionEventsState");
    }

    private static SessionEventsState getSessionEventsState(AccessTokenAppIdPair $r0) throws  {
        SessionEventsState $r4;
        synchronized (staticLock) {
            $r4 = (SessionEventsState) stateMap.get($r0);
        }
        return $r4;
    }

    private static void flush(final FlushReason $r0) throws  {
        FacebookSdk.getExecutor().execute(new Runnable() {
            public void run() throws  {
                AppEventsLogger.flushAndWait($r0);
            }
        });
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void flushAndWait(com.facebook.appevents.AppEventsLogger.FlushReason r18) throws  {
        /*
        r1 = staticLock;
        monitor-enter(r1);
        r2 = requestInFlight;	 Catch:{ Throwable -> 0x004a }
        if (r2 == 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r1);	 Catch:{ Throwable -> 0x004a }
        return;
    L_0x0009:
        r3 = 1;
        requestInFlight = r3;	 Catch:{ Throwable -> 0x004a }
        r4 = new java.util.HashSet;	 Catch:{ Throwable -> 0x004a }
        r5 = stateMap;	 Catch:{ Throwable -> 0x004a }
        r6 = r5.keySet();	 Catch:{ Throwable -> 0x004a }
        r4.<init>(r6);	 Catch:{ Throwable -> 0x004a }
        monitor-exit(r1);	 Catch:{ Throwable -> 0x004a }
        accumulatePersistedEvents();
        r7 = 0;
        r0 = r18;
        r7 = buildAndExecuteRequests(r0, r4);	 Catch:{ Exception -> 0x004d }
    L_0x0022:
        r1 = staticLock;
        monitor-enter(r1);
        r3 = 0;
        requestInFlight = r3;	 Catch:{ Throwable -> 0x0058 }
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0058 }
        if (r7 == 0) goto L_0x005b;
    L_0x002b:
        r8 = new android.content.Intent;
        r9 = "com.facebook.sdk.APP_EVENTS_FLUSHED";
        r8.<init>(r9);
        r10 = r7.numEvents;
        r9 = "com.facebook.sdk.APP_EVENTS_NUM_EVENTS_FLUSHED";
        r8.putExtra(r9, r10);
        r11 = r7.result;
        r9 = "com.facebook.sdk.APP_EVENTS_FLUSH_RESULT";
        r8.putExtra(r9, r11);
        r12 = applicationContext;
        r13 = android.support.v4.content.LocalBroadcastManager.getInstance(r12);
        r13.sendBroadcast(r8);
        return;
    L_0x004a:
        r14 = move-exception;
        monitor-exit(r1);	 Catch:{ Throwable -> 0x004a }
        throw r14;
    L_0x004d:
        r15 = move-exception;
        r16 = TAG;
        r9 = "Caught unexpected exception while flushing: ";
        r0 = r16;
        com.facebook.internal.Utility.logd(r0, r9, r15);
        goto L_0x0022;
    L_0x0058:
        r17 = move-exception;
        monitor-exit(r1);	 Catch:{ Throwable -> 0x0058 }
        throw r17;
    L_0x005b:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.appevents.AppEventsLogger.flushAndWait(com.facebook.appevents.AppEventsLogger$FlushReason):void");
    }

    private static FlushStatistics buildAndExecuteRequests(@Signature({"(", "Lcom/facebook/appevents/AppEventsLogger$FlushReason;", "Ljava/util/Set", "<", "Lcom/facebook/appevents/AppEventsLogger$AccessTokenAppIdPair;", ">;)", "Lcom/facebook/appevents/AppEventsLogger$FlushStatistics;"}) FlushReason $r0, @Signature({"(", "Lcom/facebook/appevents/AppEventsLogger$FlushReason;", "Ljava/util/Set", "<", "Lcom/facebook/appevents/AppEventsLogger$AccessTokenAppIdPair;", ">;)", "Lcom/facebook/appevents/AppEventsLogger$FlushStatistics;"}) Set<AccessTokenAppIdPair> $r1) throws  {
        FlushStatistics $r3 = new FlushStatistics();
        boolean $z0 = FacebookSdk.getLimitEventAndDataUsage(applicationContext);
        ArrayList<GraphRequest> $r2 = new ArrayList();
        for (AccessTokenAppIdPair $r7 : $r1) {
            SessionEventsState $r8 = getSessionEventsState($r7);
            if ($r8 != null) {
                GraphRequest $r9 = buildRequestForSession($r7, $r8, $z0, $r3);
                if ($r9 != null) {
                    $r2.add($r9);
                }
            }
        }
        if ($r2.size() <= 0) {
            return null;
        }
        LoggingBehavior $r10 = LoggingBehavior.APP_EVENTS;
        String $r11 = TAG;
        Object[] $r12 = new Object[2];
        int $i0 = $r3.numEvents;
        $r12[0] = Integer.valueOf($i0);
        $r12[1] = $r0.toString();
        Logger.log($r10, $r11, "Flushing %d events due to %s.", $r12);
        for (GraphRequest executeAndWait : $r2) {
            executeAndWait.executeAndWait();
        }
        return $r3;
    }

    private static GraphRequest buildRequestForSession(AccessTokenAppIdPair $r0, SessionEventsState $r1, boolean $z0, FlushStatistics $r2) throws  {
        FetchedAppSettings $r4 = Utility.queryAppSettings($r0.getApplicationId(), false);
        final GraphRequest $r6 = GraphRequest.newPostRequest(null, String.format("%s/activities", new Object[]{$r3}), null, null);
        Bundle $r7 = $r6.getParameters();
        Bundle $r8 = $r7;
        if ($r7 == null) {
            $r8 = new Bundle();
        }
        $r8.putString("access_token", $r0.getAccessTokenString());
        $r6.setParameters($r8);
        if ($r4 == null) {
            return null;
        }
        int $i0 = $r1.populateRequest($r6, $r4.supportsImplicitLogging(), $z0);
        if ($i0 == 0) {
            return null;
        }
        $r2.numEvents += $i0;
        final AccessTokenAppIdPair accessTokenAppIdPair = $r0;
        final SessionEventsState sessionEventsState = $r1;
        final FlushStatistics flushStatistics = $r2;
        $r6.setCallback(new Callback() {
            public void onCompleted(GraphResponse $r1) throws  {
                AppEventsLogger.handleResponse(accessTokenAppIdPair, $r6, $r1, sessionEventsState, flushStatistics);
            }
        });
        return $r6;
    }

    private static void handleResponse(AccessTokenAppIdPair $r0, GraphRequest $r1, GraphResponse $r2, SessionEventsState $r3, FlushStatistics $r4) throws  {
        boolean $z0;
        FacebookRequestError $r7 = $r2.getError();
        String $r8 = "Success";
        FlushResult $r9 = FlushResult.SUCCESS;
        if ($r7 != null) {
            if ($r7.getErrorCode() == -1) {
                $r8 = "Failed: No Connectivity";
                $r9 = FlushResult.NO_CONNECTIVITY;
            } else {
                $r8 = String.format("Failed:\n  Response: %s\n  Error %s", new Object[]{$r2.toString(), $r7.toString()});
                $r9 = FlushResult.SERVER_ERROR;
            }
        }
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.APP_EVENTS)) {
            String $r12;
            try {
                $r12 = new JSONArray((String) $r1.getTag()).toString(2);
            } catch (JSONException e) {
                $r12 = "<Can't encode events for debug logging>";
            }
            Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Flush completed\nParams: %s\n  Result: %s\n  Events JSON: %s", $r1.getGraphObject().toString(), $r8, $r12);
        }
        if ($r7 != null) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        $r3.clearInFlightAndStats($z0);
        if ($r9 == FlushResult.NO_CONNECTIVITY) {
            PersistedEvents.persistEvents(applicationContext, $r0, $r3);
        }
        if ($r9 != FlushResult.SUCCESS && $r4.result != FlushResult.NO_CONNECTIVITY) {
            $r4.result = $r9;
        }
    }

    private static int accumulatePersistedEvents() throws  {
        PersistedEvents $r1 = PersistedEvents.readAndClearStore(applicationContext);
        int $i0 = 0;
        for (AccessTokenAppIdPair $r5 : $r1.keySet()) {
            SessionEventsState $r6 = getSessionEventsState(applicationContext, $r5);
            List $r7 = $r1.getEvents($r5);
            $r6.accumulatePersistedEvents($r7);
            $i0 += $r7.size();
        }
        return $i0;
    }

    private static void notifyDeveloperError(String $r0) throws  {
        Logger.log(LoggingBehavior.DEVELOPER_ERRORS, "AppEvents", $r0);
    }

    private static void setSourceApplication(Activity $r0) throws  {
        ComponentName $r1 = $r0.getCallingActivity();
        if ($r1 != null) {
            String $r2 = $r1.getPackageName();
            if ($r2.equals($r0.getPackageName())) {
                resetSourceApplication();
                return;
            }
            sourceApplication = $r2;
        }
        Intent $r4 = $r0.getIntent();
        if ($r4 == null || $r4.getBooleanExtra(SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, false)) {
            resetSourceApplication();
            return;
        }
        Bundle $r5 = AppLinks.getAppLinkData($r4);
        if ($r5 == null) {
            resetSourceApplication();
            return;
        }
        isOpenedByApplink = true;
        $r5 = $r5.getBundle("referer_app_link");
        if ($r5 == null) {
            sourceApplication = null;
            return;
        }
        sourceApplication = $r5.getString("package");
        $r4.putExtra(SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
    }

    static void setSourceApplication(String $r0, boolean $z0) throws  {
        sourceApplication = $r0;
        isOpenedByApplink = $z0;
    }

    static String getSourceApplication() throws  {
        String $r0 = "Unclassified";
        if (isOpenedByApplink) {
            $r0 = "Applink";
        }
        if (sourceApplication != null) {
            return $r0 + "(" + sourceApplication + ")";
        }
        return $r0;
    }

    static void resetSourceApplication() throws  {
        sourceApplication = null;
        isOpenedByApplink = false;
    }

    public static String getAnonymousAppDeviceGUID(Context $r0) throws  {
        if (anonymousAppDeviceGUID == null) {
            synchronized (staticLock) {
                if (anonymousAppDeviceGUID == null) {
                    anonymousAppDeviceGUID = $r0.getSharedPreferences(APP_EVENT_PREFERENCES, 0).getString("anonymousAppDeviceGUID", null);
                    if (anonymousAppDeviceGUID == null) {
                        anonymousAppDeviceGUID = "XZ" + UUID.randomUUID().toString();
                        $r0.getSharedPreferences(APP_EVENT_PREFERENCES, 0).edit().putString("anonymousAppDeviceGUID", anonymousAppDeviceGUID).apply();
                    }
                }
            }
        }
        return anonymousAppDeviceGUID;
    }
}
