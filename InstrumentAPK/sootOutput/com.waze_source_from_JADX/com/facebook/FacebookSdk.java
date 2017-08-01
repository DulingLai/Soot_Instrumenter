package com.facebook;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.BoltsMeasurementEventListener;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public final class FacebookSdk {
    public static final String APPLICATION_ID_PROPERTY = "com.facebook.sdk.ApplicationId";
    public static final String APPLICATION_NAME_PROPERTY = "com.facebook.sdk.ApplicationName";
    private static final String ATTRIBUTION_PREFERENCES = "com.facebook.sdk.attributionTracking";
    static final String CALLBACK_OFFSET_CHANGED_AFTER_INIT = "The callback request code offset can't be updated once the SDK is initialized.";
    static final String CALLBACK_OFFSET_NEGATIVE = "The callback request code offset can't be negative.";
    public static final String CLIENT_TOKEN_PROPERTY = "com.facebook.sdk.ClientToken";
    private static final int DEFAULT_CORE_POOL_SIZE = 5;
    private static final int DEFAULT_KEEP_ALIVE = 1;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new C04821();
    private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new LinkedBlockingQueue(10);
    private static final String FACEBOOK_COM = "facebook.com";
    private static final Object LOCK = new Object();
    private static final int MAX_REQUEST_CODE_RANGE = 100;
    private static final String PUBLISH_ACTIVITY_PATH = "%s/activities";
    private static final String TAG = FacebookSdk.class.getCanonicalName();
    public static final String WEB_DIALOG_THEME = "com.facebook.sdk.WebDialogTheme";
    private static volatile String appClientToken;
    private static Context applicationContext;
    private static volatile String applicationId;
    private static volatile String applicationName;
    private static File cacheDir;
    private static int callbackRequestCodeOffset = 64206;
    private static volatile Executor executor;
    private static volatile String facebookDomain = FACEBOOK_COM;
    private static volatile boolean isDebugEnabled = false;
    private static boolean isLegacyTokenUpgradeSupported = false;
    private static final HashSet<LoggingBehavior> loggingBehaviors = new HashSet(Arrays.asList(new LoggingBehavior[]{LoggingBehavior.DEVELOPER_ERRORS}));
    private static AtomicLong onProgressThreshold = new AtomicLong(65536);
    private static Boolean sdkInitialized = Boolean.valueOf(false);
    private static volatile int webDialogTheme;

    static class C04821 implements ThreadFactory {
        private final AtomicInteger counter = new AtomicInteger(0);

        C04821() throws  {
        }

        public Thread newThread(Runnable $r1) throws  {
            return new Thread($r1, "FacebookSdk #" + this.counter.incrementAndGet());
        }
    }

    public interface InitializeCallback {
        void onInitialized() throws ;
    }

    public static String getSdkVersion() throws  {
        return FacebookSdkVersion.BUILD;
    }

    public static synchronized void sdkInitialize(Context $r0, int $i0) throws  {
        Class cls = FacebookSdk.class;
        synchronized (cls) {
            try {
                sdkInitialize($r0, $i0, null);
            } finally {
                cls = FacebookSdk.class;
            }
        }
    }

    public static synchronized void sdkInitialize(Context $r0, int $i0, InitializeCallback callback) throws  {
        Class cls = FacebookSdk.class;
        synchronized (cls) {
            try {
                if (sdkInitialized.booleanValue() && $i0 != callbackRequestCodeOffset) {
                    throw new FacebookException(CALLBACK_OFFSET_CHANGED_AFTER_INIT);
                } else if ($i0 < 0) {
                    throw new FacebookException(CALLBACK_OFFSET_NEGATIVE);
                } else {
                    callbackRequestCodeOffset = $i0;
                    sdkInitialize($r0);
                }
            } finally {
                cls = FacebookSdk.class;
            }
        }
    }

    public static synchronized void sdkInitialize(Context $r0) throws  {
        Class cls = FacebookSdk.class;
        synchronized (cls) {
            try {
                sdkInitialize($r0, null);
            } finally {
                cls = FacebookSdk.class;
            }
        }
    }

    public static synchronized void sdkInitialize(Context $r0, final InitializeCallback $r1) throws  {
        synchronized (FacebookSdk.class) {
            try {
                if (!sdkInitialized.booleanValue()) {
                    Validate.notNull($r0, "applicationContext");
                    Validate.hasFacebookActivity($r0, false);
                    Validate.hasInternetPermissions($r0, false);
                    applicationContext = $r0.getApplicationContext();
                    loadDefaultsFromMetadata(applicationContext);
                    Utility.loadAppSettingsAsync(applicationContext, applicationId);
                    NativeProtocol.updateAllAvailableProtocolVersionsAsync();
                    BoltsMeasurementEventListener.getInstance(applicationContext);
                    cacheDir = applicationContext.getCacheDir();
                    getExecutor().execute(new FutureTask(new Callable<Void>() {
                        public Void call() throws Exception {
                            AccessTokenManager.getInstance().loadCurrentAccessToken();
                            ProfileManager.getInstance().loadCurrentProfile();
                            if (AccessToken.getCurrentAccessToken() != null && Profile.getCurrentProfile() == null) {
                                Profile.fetchProfileForCurrentAccessToken();
                            }
                            if ($r1 != null) {
                                $r1.onInitialized();
                            }
                            return null;
                        }
                    }));
                    sdkInitialized = Boolean.valueOf(true);
                } else if ($r1 != null) {
                    $r1.onInitialized();
                }
            } catch (Throwable th) {
                Class cls = FacebookSdk.class;
            }
        }
    }

    public static synchronized boolean isInitialized() throws  {
        Class cls = FacebookSdk.class;
        synchronized (cls) {
            try {
                boolean $z0 = sdkInitialized.booleanValue();
                return $z0;
            } finally {
                cls = FacebookSdk.class;
            }
        }
    }

    public static Set<LoggingBehavior> getLoggingBehaviors() throws  {
        Set $r2;
        synchronized (loggingBehaviors) {
            $r2 = Collections.unmodifiableSet(new HashSet(loggingBehaviors));
        }
        return $r2;
    }

    public static void addLoggingBehavior(LoggingBehavior $r0) throws  {
        synchronized (loggingBehaviors) {
            loggingBehaviors.add($r0);
            updateGraphDebugBehavior();
        }
    }

    public static void removeLoggingBehavior(LoggingBehavior $r0) throws  {
        synchronized (loggingBehaviors) {
            loggingBehaviors.remove($r0);
        }
    }

    public static void clearLoggingBehaviors() throws  {
        synchronized (loggingBehaviors) {
            loggingBehaviors.clear();
        }
    }

    public static boolean isLoggingBehaviorEnabled(LoggingBehavior $r0) throws  {
        boolean $z0;
        synchronized (loggingBehaviors) {
            $z0 = isDebugEnabled() && loggingBehaviors.contains($r0);
        }
        return $z0;
    }

    public static boolean isDebugEnabled() throws  {
        return isDebugEnabled;
    }

    public static void setIsDebugEnabled(boolean $z0) throws  {
        isDebugEnabled = $z0;
    }

    public static boolean isLegacyTokenUpgradeSupported() throws  {
        return isLegacyTokenUpgradeSupported;
    }

    private static void updateGraphDebugBehavior() throws  {
        if (loggingBehaviors.contains(LoggingBehavior.GRAPH_API_DEBUG_INFO) && !loggingBehaviors.contains(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            loggingBehaviors.add(LoggingBehavior.GRAPH_API_DEBUG_WARNING);
        }
    }

    public static void setLegacyTokenUpgradeSupported(boolean $z0) throws  {
        isLegacyTokenUpgradeSupported = $z0;
    }

    public static Executor getExecutor() throws  {
        synchronized (LOCK) {
            if (executor == null) {
                Executor $r4 = getAsyncTaskExecutor();
                Executor $r5 = $r4;
                if ($r4 == null) {
                    $r5 = r0;
                    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, DEFAULT_WORK_QUEUE, DEFAULT_THREAD_FACTORY);
                }
                executor = $r5;
            }
        }
        return executor;
    }

    public static void setExecutor(Executor $r0) throws  {
        Validate.notNull($r0, "executor");
        synchronized (LOCK) {
            executor = $r0;
        }
    }

    public static String getFacebookDomain() throws  {
        return facebookDomain;
    }

    public static void setFacebookDomain(String $r0) throws  {
        Log.w(TAG, "WARNING: Calling setFacebookDomain from non-DEBUG code.");
        facebookDomain = $r0;
    }

    public static Context getApplicationContext() throws  {
        Validate.sdkInitialized();
        return applicationContext;
    }

    private static Executor getAsyncTaskExecutor() throws  {
        try {
            try {
                Object $r2 = AsyncTask.class.getField("THREAD_POOL_EXECUTOR").get(null);
                if ($r2 == null) {
                    return null;
                }
                if ($r2 instanceof Executor) {
                    return (Executor) $r2;
                }
                return null;
            } catch (IllegalAccessException e) {
                return null;
            }
        } catch (NoSuchFieldException e2) {
            return null;
        }
    }

    public static void publishInstallAsync(Context $r0, final String $r1) throws  {
        $r0 = $r0.getApplicationContext();
        getExecutor().execute(new Runnable() {
            public void run() throws  {
                FacebookSdk.publishInstallAndWaitForResponse($r0, $r1);
            }
        });
    }

    static com.facebook.GraphResponse publishInstallAndWaitForResponse(android.content.Context r40, java.lang.String r41) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.facebook.FacebookSdk.publishInstallAndWaitForResponse(android.content.Context, java.lang.String):com.facebook.GraphResponse. bs: [B:3:0x0006, B:12:0x006a]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        if (r40 == 0) goto L_0x0004;
    L_0x0002:
        if (r41 != 0) goto L_0x0020;
    L_0x0004:
        r4 = new java.lang.IllegalArgumentException;
        r5 = "Both context and applicationId must be non-null";	 Catch:{ Exception -> 0x000c }
        r4.<init>(r5);	 Catch:{ Exception -> 0x000c }
        throw r4;	 Catch:{ Exception -> 0x000c }
    L_0x000c:
        r6 = move-exception;
        r5 = "Facebook-publish";
        com.facebook.internal.Utility.logd(r5, r6);
        r7 = new com.facebook.GraphResponse;
        r8 = new com.facebook.FacebookRequestError;
        r9 = 0;
        r8.<init>(r9, r6);
        r9 = 0;
        r10 = 0;
        r7.<init>(r9, r10, r8);
        return r7;
    L_0x0020:
        r0 = r40;	 Catch:{ Exception -> 0x000c }
        r11 = com.facebook.internal.AttributionIdentifiers.getAttributionIdentifiers(r0);	 Catch:{ Exception -> 0x000c }
        r5 = "com.facebook.sdk.attributionTracking";	 Catch:{ Exception -> 0x000c }
        r13 = 0;	 Catch:{ Exception -> 0x000c }
        r0 = r40;	 Catch:{ Exception -> 0x000c }
        r12 = r0.getSharedPreferences(r5, r13);	 Catch:{ Exception -> 0x000c }
        r14 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x000c }
        r14.<init>();	 Catch:{ Exception -> 0x000c }
        r0 = r41;	 Catch:{ Exception -> 0x000c }
        r14 = r14.append(r0);	 Catch:{ Exception -> 0x000c }
        r5 = "ping";	 Catch:{ Exception -> 0x000c }
        r14 = r14.append(r5);	 Catch:{ Exception -> 0x000c }
        r15 = r14.toString();	 Catch:{ Exception -> 0x000c }
        r14 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x000c }
        r14.<init>();	 Catch:{ Exception -> 0x000c }
        r0 = r41;	 Catch:{ Exception -> 0x000c }
        r14 = r14.append(r0);	 Catch:{ Exception -> 0x000c }
        r5 = "json";	 Catch:{ Exception -> 0x000c }
        r14 = r14.append(r5);	 Catch:{ Exception -> 0x000c }
        r16 = r14.toString();	 Catch:{ Exception -> 0x000c }
        r19 = 0;	 Catch:{ Exception -> 0x000c }
        r0 = r19;	 Catch:{ Exception -> 0x000c }
        r17 = r12.getLong(r15, r0);	 Catch:{ Exception -> 0x000c }
        r9 = 0;	 Catch:{ Exception -> 0x000c }
        r0 = r16;	 Catch:{ Exception -> 0x000c }
        r21 = r12.getString(r0, r9);	 Catch:{ Exception -> 0x000c }
        r22 = com.facebook.internal.AppEventsLoggerUtility.GraphAPIActivityType.MOBILE_INSTALL_EVENT;
        r0 = r40;	 Catch:{ JSONException -> 0x00dc }
        r23 = com.facebook.appevents.AppEventsLogger.getAnonymousAppDeviceGUID(r0);	 Catch:{ JSONException -> 0x00dc }
        r0 = r40;	 Catch:{ JSONException -> 0x00dc }
        r24 = getLimitEventAndDataUsage(r0);	 Catch:{ JSONException -> 0x00dc }
        r0 = r22;	 Catch:{ JSONException -> 0x00dc }
        r1 = r23;	 Catch:{ JSONException -> 0x00dc }
        r2 = r24;	 Catch:{ JSONException -> 0x00dc }
        r3 = r40;	 Catch:{ JSONException -> 0x00dc }
        r25 = com.facebook.internal.AppEventsLoggerUtility.getJSONObjectForGraphAPICall(r0, r11, r1, r2, r3);	 Catch:{ JSONException -> 0x00dc }
        r13 = 1;	 Catch:{ Exception -> 0x000c }
        r0 = new java.lang.Object[r13];	 Catch:{ Exception -> 0x000c }
        r26 = r0;	 Catch:{ Exception -> 0x000c }
        r13 = 0;	 Catch:{ Exception -> 0x000c }
        r26[r13] = r41;	 Catch:{ Exception -> 0x000c }
        r5 = "%s/activities";	 Catch:{ Exception -> 0x000c }
        r0 = r26;	 Catch:{ Exception -> 0x000c }
        r41 = java.lang.String.format(r5, r0);	 Catch:{ Exception -> 0x000c }
        r9 = 0;	 Catch:{ Exception -> 0x000c }
        r10 = 0;	 Catch:{ Exception -> 0x000c }
        r0 = r41;	 Catch:{ Exception -> 0x000c }
        r1 = r25;	 Catch:{ Exception -> 0x000c }
        r27 = com.facebook.GraphRequest.newPostRequest(r9, r0, r1, r10);	 Catch:{ Exception -> 0x000c }
        r19 = 0;
        r28 = (r17 > r19 ? 1 : (r17 == r19 ? 0 : -1));
        if (r28 == 0) goto L_0x00f7;
    L_0x00a2:
        r29 = 0;
        if (r21 == 0) goto L_0x00b1;
    L_0x00a6:
        r25 = new org.json.JSONObject;
        r0 = r25;	 Catch:{ JSONException -> 0x012b }
        r1 = r21;	 Catch:{ JSONException -> 0x012b }
        r0.<init>(r1);	 Catch:{ JSONException -> 0x012b }
        r29 = r25;
    L_0x00b1:
        if (r29 != 0) goto L_0x00e9;
    L_0x00b3:
        r30 = new com.facebook.GraphRequestBatch;
        r13 = 1;	 Catch:{ Exception -> 0x000c }
        r0 = new com.facebook.GraphRequest[r13];	 Catch:{ Exception -> 0x000c }
        r31 = r0;	 Catch:{ Exception -> 0x000c }
        r13 = 0;	 Catch:{ Exception -> 0x000c }
        r31[r13] = r27;	 Catch:{ Exception -> 0x000c }
        r0 = r30;	 Catch:{ Exception -> 0x000c }
        r1 = r31;	 Catch:{ Exception -> 0x000c }
        r0.<init>(r1);	 Catch:{ Exception -> 0x000c }
        r5 = "true";	 Catch:{ Exception -> 0x000c }
        r9 = 0;	 Catch:{ Exception -> 0x000c }
        r0 = r30;	 Catch:{ Exception -> 0x000c }
        r32 = com.facebook.GraphResponse.createResponsesFromString(r5, r9, r0);	 Catch:{ Exception -> 0x000c }
        r13 = 0;	 Catch:{ Exception -> 0x000c }
        r0 = r32;	 Catch:{ Exception -> 0x000c }
        r33 = r0.get(r13);	 Catch:{ Exception -> 0x000c }
        r34 = r33;	 Catch:{ Exception -> 0x000c }
        r34 = (com.facebook.GraphResponse) r34;	 Catch:{ Exception -> 0x000c }
        r7 = r34;	 Catch:{ Exception -> 0x000c }
        return r7;
    L_0x00dc:
        r35 = move-exception;
        r36 = new com.facebook.FacebookException;	 Catch:{ Exception -> 0x000c }
        r5 = "An error occurred while publishing install.";	 Catch:{ Exception -> 0x000c }
        r0 = r36;	 Catch:{ Exception -> 0x000c }
        r1 = r35;	 Catch:{ Exception -> 0x000c }
        r0.<init>(r5, r1);	 Catch:{ Exception -> 0x000c }
        throw r36;	 Catch:{ Exception -> 0x000c }
    L_0x00e9:
        r7 = new com.facebook.GraphResponse;	 Catch:{ Exception -> 0x000c }
        r9 = 0;	 Catch:{ Exception -> 0x000c }
        r10 = 0;	 Catch:{ Exception -> 0x000c }
        r37 = 0;	 Catch:{ Exception -> 0x000c }
        r0 = r37;	 Catch:{ Exception -> 0x000c }
        r1 = r29;	 Catch:{ Exception -> 0x000c }
        r7.<init>(r9, r10, r0, r1);	 Catch:{ Exception -> 0x000c }
        return r7;
    L_0x00f7:
        r0 = r27;	 Catch:{ Exception -> 0x000c }
        r7 = r0.executeAndWait();	 Catch:{ Exception -> 0x000c }
        r38 = r12.edit();	 Catch:{ Exception -> 0x000c }
        r17 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x000c }
        r0 = r38;	 Catch:{ Exception -> 0x000c }
        r1 = r17;	 Catch:{ Exception -> 0x000c }
        r0.putLong(r15, r1);	 Catch:{ Exception -> 0x000c }
        r25 = r7.getJSONObject();	 Catch:{ Exception -> 0x000c }
        if (r25 == 0) goto L_0x0125;	 Catch:{ Exception -> 0x000c }
    L_0x0112:
        r25 = r7.getJSONObject();	 Catch:{ Exception -> 0x000c }
        r0 = r25;	 Catch:{ Exception -> 0x000c }
        r41 = r0.toString();	 Catch:{ Exception -> 0x000c }
        r0 = r38;	 Catch:{ Exception -> 0x000c }
        r1 = r16;	 Catch:{ Exception -> 0x000c }
        r2 = r41;	 Catch:{ Exception -> 0x000c }
        r0.putString(r1, r2);	 Catch:{ Exception -> 0x000c }
    L_0x0125:
        r0 = r38;	 Catch:{ Exception -> 0x000c }
        r0.apply();	 Catch:{ Exception -> 0x000c }
        return r7;
    L_0x012b:
        r39 = move-exception;
        goto L_0x00b1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.FacebookSdk.publishInstallAndWaitForResponse(android.content.Context, java.lang.String):com.facebook.GraphResponse");
    }

    public static boolean getLimitEventAndDataUsage(Context $r0) throws  {
        Validate.sdkInitialized();
        return $r0.getSharedPreferences(AppEventsLogger.APP_EVENT_PREFERENCES, 0).getBoolean("limitEventUsage", false);
    }

    public static void setLimitEventAndDataUsage(Context $r0, boolean $z0) throws  {
        $r0.getSharedPreferences(AppEventsLogger.APP_EVENT_PREFERENCES, 0).edit().putBoolean("limitEventUsage", $z0).apply();
    }

    public static long getOnProgressThreshold() throws  {
        Validate.sdkInitialized();
        return onProgressThreshold.get();
    }

    public static void setOnProgressThreshold(long $l0) throws  {
        onProgressThreshold.set($l0);
    }

    static void loadDefaultsFromMetadata(Context $r0) throws  {
        if ($r0 != null) {
            try {
                ApplicationInfo $r4 = $r0.getPackageManager().getApplicationInfo($r0.getPackageName(), 128);
                if ($r4 != null && $r4.metaData != null) {
                    if (applicationId == null) {
                        Object $r6 = $r4.metaData.get(APPLICATION_ID_PROPERTY);
                        if ($r6 instanceof String) {
                            String $r3 = (String) $r6;
                            if ($r3.toLowerCase(Locale.ROOT).startsWith("fb")) {
                                applicationId = $r3.substring(2);
                            } else {
                                applicationId = $r3;
                            }
                            applicationId = (String) $r6;
                        } else if ($r6 instanceof Integer) {
                            throw new FacebookException("App Ids cannot be directly placed in the manfiest.They mut be prexied by 'fb' or be placed in the string resource file.");
                        }
                    }
                    if (applicationName == null) {
                        applicationName = $r4.metaData.getString(APPLICATION_NAME_PROPERTY);
                    }
                    if (appClientToken == null) {
                        appClientToken = $r4.metaData.getString(CLIENT_TOKEN_PROPERTY);
                    }
                    if (webDialogTheme == 0) {
                        setWebDialogTheme($r4.metaData.getInt(WEB_DIALOG_THEME));
                    }
                }
            } catch (NameNotFoundException e) {
            }
        }
    }

    public static String getApplicationSignature(Context $r0) throws  {
        Validate.sdkInitialized();
        if ($r0 == null) {
            return null;
        }
        PackageManager $r2 = $r0.getPackageManager();
        if ($r2 == null) {
            return null;
        }
        try {
            PackageInfo $r4 = $r2.getPackageInfo($r0.getPackageName(), 64);
            Signature[] $r1 = $r4.signatures;
            if ($r1 == null) {
                return null;
            }
            if ($r1.length == 0) {
                return null;
            }
            try {
                MessageDigest $r5 = MessageDigest.getInstance("SHA-1");
                $r5.update($r4.signatures[0].toByteArray());
                return Base64.encodeToString($r5.digest(), 9);
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        } catch (NameNotFoundException e2) {
            return null;
        }
    }

    public static String getApplicationId() throws  {
        Validate.sdkInitialized();
        return applicationId;
    }

    public static void setApplicationId(String $r0) throws  {
        applicationId = $r0;
    }

    public static String getApplicationName() throws  {
        Validate.sdkInitialized();
        return applicationName;
    }

    public static void setApplicationName(String $r0) throws  {
        applicationName = $r0;
    }

    public static String getClientToken() throws  {
        Validate.sdkInitialized();
        return appClientToken;
    }

    public static void setClientToken(String $r0) throws  {
        appClientToken = $r0;
    }

    public static int getWebDialogTheme() throws  {
        Validate.sdkInitialized();
        return webDialogTheme;
    }

    public static void setWebDialogTheme(int $i0) throws  {
        webDialogTheme = $i0;
    }

    public static File getCacheDir() throws  {
        Validate.sdkInitialized();
        return cacheDir;
    }

    public static void setCacheDir(File $r0) throws  {
        cacheDir = $r0;
    }

    public static int getCallbackRequestCodeOffset() throws  {
        Validate.sdkInitialized();
        return callbackRequestCodeOffset;
    }

    public static boolean isFacebookRequestCode(int $i0) throws  {
        return $i0 >= callbackRequestCodeOffset && $i0 < callbackRequestCodeOffset + 100;
    }
}
