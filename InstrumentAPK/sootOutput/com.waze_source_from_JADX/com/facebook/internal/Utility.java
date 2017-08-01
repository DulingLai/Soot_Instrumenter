package com.facebook.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import dalvik.annotation.Signature;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class Utility {
    private static final String APPLICATION_FIELDS = "fields";
    private static final String APP_SETTINGS_PREFS_KEY_FORMAT = "com.facebook.internal.APP_SETTINGS.%s";
    private static final String APP_SETTINGS_PREFS_STORE = "com.facebook.internal.preferences.APP_SETTINGS";
    private static final String APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES = "android_sdk_error_categories";
    private static final String APP_SETTING_DIALOG_CONFIGS = "android_dialog_configs";
    private static final String[] APP_SETTING_FIELDS = new String[]{APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING, APP_SETTING_NUX_CONTENT, APP_SETTING_NUX_ENABLED, APP_SETTING_DIALOG_CONFIGS, APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES};
    private static final String APP_SETTING_NUX_CONTENT = "gdpv4_nux_content";
    private static final String APP_SETTING_NUX_ENABLED = "gdpv4_nux_enabled";
    private static final String APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING = "supports_implicit_sdk_logging";
    public static final int DEFAULT_STREAM_BUFFER_SIZE = 8192;
    private static final String DIALOG_CONFIG_DIALOG_NAME_FEATURE_NAME_SEPARATOR = "\\|";
    private static final String DIALOG_CONFIG_NAME_KEY = "name";
    private static final String DIALOG_CONFIG_URL_KEY = "url";
    private static final String DIALOG_CONFIG_VERSIONS_KEY = "versions";
    private static final String EXTRA_APP_EVENTS_INFO_FORMAT_VERSION = "a2";
    private static final int GINGERBREAD_MR1 = 10;
    private static final String HASH_ALGORITHM_MD5 = "MD5";
    private static final String HASH_ALGORITHM_SHA1 = "SHA-1";
    static final String LOG_TAG = "FacebookSDK";
    private static final int REFRESH_TIME_FOR_EXTENDED_DEVICE_INFO_MILLIS = 1800000;
    private static final String URL_SCHEME = "https";
    private static final String UTF8 = "UTF-8";
    private static long availableExternalStorageGB = -1;
    private static String carrierName = noCarrierConstant;
    private static String deviceTimezone = "";
    private static Map<String, FetchedAppSettings> fetchedAppSettings = new ConcurrentHashMap();
    private static AtomicBoolean loadingSettings = new AtomicBoolean(false);
    private static final String noCarrierConstant = "NoCarrier";
    private static int numCPUCores = 0;
    private static long timestampOfLastCheck = -1;
    private static long totalExternalStorageGB = -1;

    public interface GraphMeRequestWithCacheCallback {
        void onFailure(FacebookException facebookException) throws ;

        void onSuccess(JSONObject jSONObject) throws ;
    }

    static class C05393 implements FilenameFilter {
        C05393() throws  {
        }

        public boolean accept(File dir, String $r2) throws  {
            return Pattern.matches("cpu[0-9]+", $r2);
        }
    }

    public static class DialogFeatureConfig {
        private String dialogName;
        private Uri fallbackUrl;
        private String featureName;
        private int[] featureVersionSpec;

        private static DialogFeatureConfig parseDialogConfig(JSONObject $r0) throws  {
            String $r1 = $r0.optString("name");
            if (Utility.isNullOrEmpty($r1)) {
                return null;
            }
            String[] $r4 = $r1.split(Utility.DIALOG_CONFIG_DIALOG_NAME_FEATURE_NAME_SEPARATOR);
            if ($r4.length != 2) {
                return null;
            }
            $r1 = $r4[0];
            String $r2 = $r4[1];
            if (Utility.isNullOrEmpty($r1)) {
                return null;
            }
            if (Utility.isNullOrEmpty($r2)) {
                return null;
            }
            String $r5 = $r0.optString("url");
            Uri $r6 = null;
            if (!Utility.isNullOrEmpty($r5)) {
                $r6 = Uri.parse($r5);
            }
            return new DialogFeatureConfig($r1, $r2, $r6, parseVersionSpec($r0.optJSONArray(Utility.DIALOG_CONFIG_VERSIONS_KEY)));
        }

        private static int[] parseVersionSpec(JSONArray $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            int $i0 = $r0.length();
            int[] $r2 = new int[$i0];
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                int $i2 = $r0.optInt($i1, -1);
                int $i3 = $i2;
                if ($i2 == -1) {
                    String $r3 = $r0.optString($i1);
                    if (!Utility.isNullOrEmpty($r3)) {
                        try {
                            $i3 = Integer.parseInt($r3);
                        } catch (Exception $r1) {
                            Utility.logd(Utility.LOG_TAG, $r1);
                            $i3 = -1;
                        }
                    }
                }
                $r2[$i1] = $i3;
            }
            return $r2;
        }

        private DialogFeatureConfig(String $r1, String $r2, Uri $r3, int[] $r4) throws  {
            this.dialogName = $r1;
            this.featureName = $r2;
            this.fallbackUrl = $r3;
            this.featureVersionSpec = $r4;
        }

        public String getDialogName() throws  {
            return this.dialogName;
        }

        public String getFeatureName() throws  {
            return this.featureName;
        }

        public Uri getFallbackUrl() throws  {
            return this.fallbackUrl;
        }

        public int[] getVersionSpec() throws  {
            return this.featureVersionSpec;
        }
    }

    public static class FetchedAppSettings {
        private Map<String, Map<String, DialogFeatureConfig>> dialogConfigMap;
        private FacebookRequestErrorClassification errorClassification;
        private String nuxContent;
        private boolean nuxEnabled;
        private boolean supportsImplicitLogging;

        private FetchedAppSettings(@Signature({"(Z", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/internal/Utility$DialogFeatureConfig;", ">;>;", "Lcom/facebook/internal/FacebookRequestErrorClassification;", ")V"}) boolean $z0, @Signature({"(Z", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/internal/Utility$DialogFeatureConfig;", ">;>;", "Lcom/facebook/internal/FacebookRequestErrorClassification;", ")V"}) String $r1, @Signature({"(Z", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/internal/Utility$DialogFeatureConfig;", ">;>;", "Lcom/facebook/internal/FacebookRequestErrorClassification;", ")V"}) boolean $z1, @Signature({"(Z", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/internal/Utility$DialogFeatureConfig;", ">;>;", "Lcom/facebook/internal/FacebookRequestErrorClassification;", ")V"}) Map<String, Map<String, DialogFeatureConfig>> $r2, @Signature({"(Z", "Ljava/lang/String;", "Z", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/internal/Utility$DialogFeatureConfig;", ">;>;", "Lcom/facebook/internal/FacebookRequestErrorClassification;", ")V"}) FacebookRequestErrorClassification $r3) throws  {
            this.supportsImplicitLogging = $z0;
            this.nuxContent = $r1;
            this.nuxEnabled = $z1;
            this.dialogConfigMap = $r2;
            this.errorClassification = $r3;
        }

        public boolean supportsImplicitLogging() throws  {
            return this.supportsImplicitLogging;
        }

        public String getNuxContent() throws  {
            return this.nuxContent;
        }

        public boolean getNuxEnabled() throws  {
            return this.nuxEnabled;
        }

        public Map<String, Map<String, DialogFeatureConfig>> getDialogConfigurations() throws  {
            return this.dialogConfigMap;
        }

        public FacebookRequestErrorClassification getErrorClassification() throws  {
            return this.errorClassification;
        }
    }

    public interface Mapper<T, K> {
        K apply(@Signature({"(TT;)TK;"}) T t) throws ;
    }

    public interface Predicate<T> {
        boolean apply(@Signature({"(TT;)Z"}) T t) throws ;
    }

    static java.util.Map<java.lang.String, java.lang.Object> convertJSONObjectToHashMap(@dalvik.annotation.Signature({"(", "Lorg/json/JSONObject;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;"}) org.json.JSONObject r11) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0025 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
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
        r0 = new java.util.HashMap;
        r0.<init>();
        r1 = r11.names();
        r2 = 0;
    L_0x000a:
        r3 = r1.length();
        if (r2 >= r3) goto L_0x002b;
    L_0x0010:
        r4 = r1.getString(r2);	 Catch:{ JSONException -> 0x002c }
        r5 = r11.get(r4);	 Catch:{ JSONException -> 0x002c }
        r6 = r5;
        r7 = r5 instanceof org.json.JSONObject;
        if (r7 == 0) goto L_0x0025;	 Catch:{ JSONException -> 0x002c }
    L_0x001d:
        r9 = r5;	 Catch:{ JSONException -> 0x002c }
        r9 = (org.json.JSONObject) r9;	 Catch:{ JSONException -> 0x002c }
        r8 = r9;	 Catch:{ JSONException -> 0x002c }
        r6 = convertJSONObjectToHashMap(r8);	 Catch:{ JSONException -> 0x002c }
    L_0x0025:
        r0.put(r4, r6);	 Catch:{ JSONException -> 0x002c }
    L_0x0028:
        r2 = r2 + 1;
        goto L_0x000a;
    L_0x002b:
        return r0;
    L_0x002c:
        r10 = move-exception;
        goto L_0x0028;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.Utility.convertJSONObjectToHashMap(org.json.JSONObject):java.util.Map<java.lang.String, java.lang.Object>");
    }

    public static long getContentSize(android.net.Uri r19) throws  {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
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
        r6 = 0;
        r7 = com.facebook.FacebookSdk.getApplicationContext();	 Catch:{ Throwable -> 0x002c }
        r8 = r7.getContentResolver();	 Catch:{ Throwable -> 0x002c }
        r10 = 0;	 Catch:{ Throwable -> 0x002c }
        r11 = 0;	 Catch:{ Throwable -> 0x002c }
        r12 = 0;	 Catch:{ Throwable -> 0x002c }
        r13 = 0;	 Catch:{ Throwable -> 0x002c }
        r0 = r8;	 Catch:{ Throwable -> 0x002c }
        r1 = r19;	 Catch:{ Throwable -> 0x002c }
        r2 = r10;	 Catch:{ Throwable -> 0x002c }
        r3 = r11;	 Catch:{ Throwable -> 0x002c }
        r4 = r12;	 Catch:{ Throwable -> 0x002c }
        r5 = r13;	 Catch:{ Throwable -> 0x002c }
        r9 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x002c }
        r6 = r9;	 Catch:{ Throwable -> 0x002c }
        r15 = "_size";	 Catch:{ Throwable -> 0x002c }
        r14 = r9.getColumnIndex(r15);	 Catch:{ Throwable -> 0x002c }
        r9.moveToFirst();	 Catch:{ Throwable -> 0x002c }
        r16 = r9.getLong(r14);	 Catch:{ Throwable -> 0x002c }
        if (r9 == 0) goto L_0x0033;
    L_0x0028:
        r9.close();
        return r16;
    L_0x002c:
        r18 = move-exception;
        if (r6 == 0) goto L_0x0032;
    L_0x002f:
        r6.close();
    L_0x0032:
        throw r18;
    L_0x0033:
        return r16;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.Utility.getContentSize(android.net.Uri):long");
    }

    public static boolean putJSONValueInBundle(android.os.Bundle r34, java.lang.String r35, java.lang.Object r36) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:15:0x0052
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
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
        if (r36 != 0) goto L_0x000b;
    L_0x0002:
        r0 = r34;
        r1 = r35;
        r0.remove(r1);
    L_0x0009:
        r5 = 1;
        return r5;
    L_0x000b:
        r0 = r36;
        r6 = r0 instanceof java.lang.Boolean;
        if (r6 == 0) goto L_0x0022;
    L_0x0011:
        r8 = r36;
        r8 = (java.lang.Boolean) r8;
        r7 = r8;
        r6 = r7.booleanValue();
        r0 = r34;
        r1 = r35;
        r0.putBoolean(r1, r6);
        goto L_0x0009;
    L_0x0022:
        r0 = r36;
        r6 = r0 instanceof boolean[];
        if (r6 == 0) goto L_0x0037;
    L_0x0028:
        r10 = r36;
        r10 = (boolean[]) r10;
        r9 = r10;
        r9 = (boolean[]) r9;
        r0 = r34;
        r1 = r35;
        r0.putBooleanArray(r1, r9);
        goto L_0x0009;
    L_0x0037:
        r0 = r36;
        r6 = r0 instanceof java.lang.Double;
        if (r6 == 0) goto L_0x0056;
    L_0x003d:
        r12 = r36;
        r12 = (java.lang.Double) r12;
        r11 = r12;
        r13 = r11.doubleValue();
        goto L_0x004a;
    L_0x0047:
        goto L_0x0009;
    L_0x004a:
        r0 = r34;
        r1 = r35;
        r0.putDouble(r1, r13);
        goto L_0x0009;
        goto L_0x0056;
    L_0x0053:
        goto L_0x0009;
    L_0x0056:
        r0 = r36;
        r6 = r0 instanceof double[];
        if (r6 == 0) goto L_0x0074;
    L_0x005c:
        r16 = r36;
        r16 = (double[]) r16;
        r15 = r16;
        goto L_0x0066;
    L_0x0063:
        goto L_0x0009;
    L_0x0066:
        r15 = (double[]) r15;
        r0 = r34;
        r1 = r35;
        r0.putDoubleArray(r1, r15);
        goto L_0x0009;
        goto L_0x0074;
    L_0x0071:
        goto L_0x0009;
    L_0x0074:
        r0 = r36;
        r6 = r0 instanceof java.lang.Integer;
        if (r6 == 0) goto L_0x0094;
    L_0x007a:
        goto L_0x007e;
    L_0x007b:
        goto L_0x0009;
    L_0x007e:
        r18 = r36;
        r18 = (java.lang.Integer) r18;
        r17 = r18;
        r0 = r17;
        r19 = r0.intValue();
        r0 = r34;
        r1 = r35;
        r2 = r19;
        r0.putInt(r1, r2);
        goto L_0x0047;
    L_0x0094:
        r0 = r36;
        r6 = r0 instanceof int[];
        if (r6 == 0) goto L_0x00ac;
    L_0x009a:
        r21 = r36;
        r21 = (int[]) r21;
        r20 = r21;
        r20 = (int[]) r20;
        r0 = r34;
        r1 = r35;
        r2 = r20;
        r0.putIntArray(r1, r2);
        goto L_0x0053;
    L_0x00ac:
        r0 = r36;
        r6 = r0 instanceof java.lang.Long;
        if (r6 == 0) goto L_0x00c8;
    L_0x00b2:
        r23 = r36;
        r23 = (java.lang.Long) r23;
        r22 = r23;
        r0 = r22;
        r24 = r0.longValue();
        r0 = r34;
        r1 = r35;
        r2 = r24;
        r0.putLong(r1, r2);
        goto L_0x0063;
    L_0x00c8:
        r0 = r36;
        r6 = r0 instanceof long[];
        if (r6 == 0) goto L_0x00e0;
    L_0x00ce:
        r27 = r36;
        r27 = (long[]) r27;
        r26 = r27;
        r26 = (long[]) r26;
        r0 = r34;
        r1 = r35;
        r2 = r26;
        r0.putLongArray(r1, r2);
        goto L_0x0071;
    L_0x00e0:
        r0 = r36;
        r6 = r0 instanceof java.lang.String;
        if (r6 == 0) goto L_0x00f6;
    L_0x00e6:
        r29 = r36;
        r29 = (java.lang.String) r29;
        r28 = r29;
        r0 = r34;
        r1 = r35;
        r2 = r28;
        r0.putString(r1, r2);
        goto L_0x007b;
    L_0x00f6:
        r0 = r36;
        r6 = r0 instanceof org.json.JSONArray;
        if (r6 == 0) goto L_0x0116;
    L_0x00fc:
        r31 = r36;
        r31 = (org.json.JSONArray) r31;
        r30 = r31;
        r0 = r30;
        r28 = r0.toString();
        goto L_0x010c;
    L_0x0109:
        goto L_0x0009;
    L_0x010c:
        r0 = r34;
        r1 = r35;
        r2 = r28;
        r0.putString(r1, r2);
        goto L_0x0109;
    L_0x0116:
        r0 = r36;
        r6 = r0 instanceof org.json.JSONObject;
        if (r6 == 0) goto L_0x0136;
    L_0x011c:
        r33 = r36;
        r33 = (org.json.JSONObject) r33;
        r32 = r33;
        r0 = r32;
        r28 = r0.toString();
        goto L_0x012c;
    L_0x0129:
        goto L_0x0009;
    L_0x012c:
        r0 = r34;
        r1 = r35;
        r2 = r28;
        r0.putString(r1, r2);
        goto L_0x0129;
    L_0x0136:
        r5 = 0;
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.Utility.putJSONValueInBundle(android.os.Bundle, java.lang.String, java.lang.Object):boolean");
    }

    public static int[] intersectRanges(int[] $r0, int[] $r2) throws  {
        if ($r0 == null) {
            return $r2;
        }
        if ($r2 == null) {
            return $r0;
        }
        int[] $r1 = new int[($r0.length + $r2.length)];
        int $i2 = 0;
        int $i3 = 0;
        int $i4 = 0;
        while ($i3 < $r0.length && $i4 < $r2.length) {
            int $i5 = Integer.MIN_VALUE;
            int $i6 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            int $i0 = $r0[$i3];
            int $i7 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            int $i1 = $r2[$i4];
            int $i8 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            if ($i3 < $r0.length - 1) {
                $i7 = $r0[$i3 + 1];
            }
            if ($i4 < $r2.length - 1) {
                $i8 = $r2[$i4 + 1];
            }
            if ($i0 < $i1) {
                if ($i7 > $i1) {
                    $i5 = $i1;
                    if ($i7 > $i8) {
                        $i6 = $i8;
                        $i4 += 2;
                    } else {
                        $i6 = $i7;
                        $i3 += 2;
                    }
                } else {
                    $i3 += 2;
                }
            } else if ($i8 > $i0) {
                $i5 = $i0;
                if ($i8 > $i7) {
                    $i6 = $i7;
                    $i3 += 2;
                } else {
                    $i6 = $i8;
                    $i4 += 2;
                }
            } else {
                $i4 += 2;
            }
            if ($i5 != Integer.MIN_VALUE) {
                $i0 = $i2 + 1;
                $r1[$i2] = $i5;
                if ($i6 == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
                    $i2 = $i0;
                    break;
                }
                $i2 = $i0 + 1;
                $r1[$i0] = $i6;
            }
        }
        return Arrays.copyOf($r1, $i2);
    }

    public static <T> boolean isSubset(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/Collection", "<TT;>;", "Ljava/util/Collection", "<TT;>;)Z"}) Collection<T> $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/Collection", "<TT;>;", "Ljava/util/Collection", "<TT;>;)Z"}) Collection<T> $r1) throws  {
        if ($r1 != null && $r1.size() != 0) {
            HashSet $r2 = new HashSet($r1);
            for (T $r4 : $r0) {
                if (!$r2.contains($r4)) {
                    return false;
                }
            }
            return true;
        } else if ($r0 == null || $r0.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static <T> boolean isNullOrEmpty(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/Collection", "<TT;>;)Z"}) Collection<T> $r0) throws  {
        return $r0 == null || $r0.size() == 0;
    }

    public static boolean isNullOrEmpty(String $r0) throws  {
        return $r0 == null || $r0.length() == 0;
    }

    public static String coerceValueIfNullOrEmpty(String $r0, String $r1) throws  {
        return isNullOrEmpty($r0) ? $r1 : $r0;
    }

    public static <T> Collection<T> unmodifiableCollection(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;)", "Ljava/util/Collection", "<TT;>;"}) T... $r0) throws  {
        return Collections.unmodifiableCollection(Arrays.asList($r0));
    }

    public static <T> ArrayList<T> arrayList(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;)", "Ljava/util/ArrayList", "<TT;>;"}) T... $r0) throws  {
        ArrayList $r1 = new ArrayList($r0.length);
        for (Object $r2 : $r0) {
            $r1.add($r2);
        }
        return $r1;
    }

    public static <T> HashSet<T> hashSet(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;)", "Ljava/util/HashSet", "<TT;>;"}) T... $r0) throws  {
        HashSet $r1 = new HashSet($r0.length);
        for (Object $r2 : $r0) {
            $r1.add($r2);
        }
        return $r1;
    }

    public static String md5hash(String $r0) throws  {
        return hashWithAlgorithm(HASH_ALGORITHM_MD5, $r0);
    }

    public static String sha1hash(String $r0) throws  {
        return hashWithAlgorithm(HASH_ALGORITHM_SHA1, $r0);
    }

    public static String sha1hash(byte[] $r0) throws  {
        return hashWithAlgorithm(HASH_ALGORITHM_SHA1, $r0);
    }

    private static String hashWithAlgorithm(String $r0, String $r1) throws  {
        return hashWithAlgorithm($r0, $r1.getBytes());
    }

    private static String hashWithAlgorithm(String $r0, byte[] $r1) throws  {
        try {
            return hashBytes(MessageDigest.getInstance($r0), $r1);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String hashBytes(MessageDigest $r0, byte[] $r1) throws  {
        $r0.update($r1);
        $r1 = $r0.digest();
        StringBuilder $r2 = new StringBuilder();
        for (byte $b0 : $r1) {
            $r2.append(Integer.toHexString(($b0 >> (byte) 4) & (byte) 15));
            $r2.append(Integer.toHexString(($b0 >> (byte) 0) & (byte) 15));
        }
        return $r2.toString();
    }

    public static Uri buildUri(String $r0, String $r1, Bundle $r2) throws  {
        Builder $r3 = new Builder();
        $r3.scheme(URL_SCHEME);
        $r3.authority($r0);
        $r3.path($r1);
        if ($r2 != null) {
            for (String $r02 : $r2.keySet()) {
                Object $r6 = $r2.get($r02);
                if ($r6 instanceof String) {
                    $r3.appendQueryParameter($r02, (String) $r6);
                }
            }
        }
        return $r3.build();
    }

    public static Bundle parseUrlQueryString(String $r0) throws  {
        Bundle $r2 = new Bundle();
        if (isNullOrEmpty($r0)) {
            return $r2;
        }
        for (String $r02 : $r02.split("&")) {
            String[] $r4 = $r02.split("=");
            if ($r4.length == 2) {
                try {
                    $r2.putString(URLDecoder.decode($r4[0], UTF8), URLDecoder.decode($r4[1], UTF8));
                } catch (Exception $r1) {
                    logd(LOG_TAG, $r1);
                }
            } else if ($r4.length == 1) {
                $r2.putString(URLDecoder.decode($r4[0], UTF8), "");
            }
        }
        return $r2;
    }

    public static void putNonEmptyString(Bundle $r0, String $r1, String $r2) throws  {
        if (!isNullOrEmpty($r2)) {
            $r0.putString($r1, $r2);
        }
    }

    public static void putCommaSeparatedStringList(@Signature({"(", "Landroid/os/Bundle;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) Bundle $r0, @Signature({"(", "Landroid/os/Bundle;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Landroid/os/Bundle;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) ArrayList<String> $r2) throws  {
        if ($r2 != null) {
            StringBuilder $r3 = new StringBuilder();
            Iterator $r4 = $r2.iterator();
            while ($r4.hasNext()) {
                $r3.append((String) $r4.next());
                $r3.append(",");
            }
            String $r6 = "";
            if ($r3.length() > 0) {
                $r6 = $r3.substring(0, $r3.length() - 1);
            }
            $r0.putString($r1, $r6);
        }
    }

    public static void putUri(Bundle $r0, String $r1, Uri $r2) throws  {
        if ($r2 != null) {
            putNonEmptyString($r0, $r1, $r2.toString());
        }
    }

    public static void closeQuietly(Closeable $r0) throws  {
        if ($r0 != null) {
            try {
                $r0.close();
            } catch (IOException e) {
            }
        }
    }

    public static void disconnectQuietly(URLConnection $r0) throws  {
        if ($r0 instanceof HttpURLConnection) {
            ((HttpURLConnection) $r0).disconnect();
        }
    }

    public static String getMetadataApplicationId(Context $r0) throws  {
        Validate.notNull($r0, "context");
        FacebookSdk.sdkInitialize($r0);
        return FacebookSdk.getApplicationId();
    }

    public static Object getStringPropertyAsJSON(JSONObject $r3, String $r0, String $r1) throws JSONException {
        Object $r4 = $r3.opt($r0);
        Object $r5 = $r4;
        if ($r4 != null && ($r4 instanceof String)) {
            $r5 = new JSONTokener((String) $r4).nextValue();
        }
        if ($r5 == null || ($r5 instanceof JSONObject) || ($r5 instanceof JSONArray)) {
            return $r5;
        }
        if ($r1 != null) {
            $r3 = new JSONObject();
            $r3.putOpt($r1, $r5);
            return $r3;
        }
        throw new FacebookException("Got an unexpected non-JSON object.");
    }

    public static String readStreamToString(InputStream $r0) throws IOException {
        Throwable $r7;
        BufferedInputStream $r5 = null;
        InputStreamReader $r6 = null;
        try {
            InputStreamReader $r3;
            BufferedInputStream $r2 = new BufferedInputStream($r0);
            try {
                $r3 = new InputStreamReader($r2);
            } catch (Throwable th) {
                $r7 = th;
                $r5 = $r2;
                closeQuietly($r5);
                closeQuietly($r6);
                throw $r7;
            }
            try {
                StringBuilder $r4 = new StringBuilder();
                char[] $r1 = new char[2048];
                while (true) {
                    int $i0 = $r3.read($r1);
                    if ($i0 != -1) {
                        $r4.append($r1, 0, $i0);
                    } else {
                        String $r8 = $r4.toString();
                        closeQuietly($r2);
                        closeQuietly($r3);
                        return $r8;
                    }
                }
            } catch (Throwable th2) {
                $r7 = th2;
                $r6 = $r3;
                $r5 = $r2;
                closeQuietly($r5);
                closeQuietly($r6);
                throw $r7;
            }
        } catch (Throwable th3) {
            $r7 = th3;
            closeQuietly($r5);
            closeQuietly($r6);
            throw $r7;
        }
    }

    public static int copyAndCloseInputStream(InputStream $r0, OutputStream $r1) throws IOException {
        Throwable $r5;
        BufferedInputStream $r4 = null;
        int $i1 = 0;
        try {
            BufferedInputStream $r3 = new BufferedInputStream($r0);
            try {
                byte[] $r2 = new byte[8192];
                while (true) {
                    int $i0 = $r3.read($r2);
                    if ($i0 == -1) {
                        break;
                    }
                    $r1.write($r2, 0, $i0);
                    $i1 += $i0;
                }
                if ($r3 != null) {
                    $r3.close();
                }
                if ($r0 == null) {
                    return $i1;
                }
                $r0.close();
                return $i1;
            } catch (Throwable th) {
                $r5 = th;
                $r4 = $r3;
                if ($r4 != null) {
                    $r4.close();
                }
                if ($r0 != null) {
                    $r0.close();
                }
                throw $r5;
            }
        } catch (Throwable th2) {
            $r5 = th2;
            if ($r4 != null) {
                $r4.close();
            }
            if ($r0 != null) {
                $r0.close();
            }
            throw $r5;
        }
    }

    public static boolean stringsEqualOrEmpty(String $r0, String $r1) throws  {
        boolean $z0 = TextUtils.isEmpty($r0);
        boolean $z1 = TextUtils.isEmpty($r1);
        if ($z0 && $z1) {
            return true;
        }
        return ($z0 || $z1) ? false : $r0.equals($r1);
    }

    private static void clearCookiesForDomain(Context $r0, String $r1) throws  {
        CookieSyncManager.createInstance($r0).sync();
        CookieManager $r4 = CookieManager.getInstance();
        String $r2 = $r4.getCookie($r1);
        if ($r2 != null) {
            for (String $r22 : $r22.split(";")) {
                String[] $r6 = $r22.split("=");
                if ($r6.length > 0) {
                    $r4.setCookie($r1, $r6[0].trim() + "=;expires=Sat, 1 Jan 2000 00:00:01 UTC;");
                }
            }
            $r4.removeExpiredCookie();
        }
    }

    public static void clearFacebookCookies(Context $r0) throws  {
        clearCookiesForDomain($r0, "facebook.com");
        clearCookiesForDomain($r0, ".facebook.com");
        clearCookiesForDomain($r0, "https://facebook.com");
        clearCookiesForDomain($r0, "https://.facebook.com");
    }

    public static void logd(String $r0, Exception $r1) throws  {
        if (FacebookSdk.isDebugEnabled() && $r0 != null && $r1 != null) {
            Log.d($r0, $r1.getClass().getSimpleName() + ": " + $r1.getMessage());
        }
    }

    public static void logd(String $r0, String $r1) throws  {
        if (FacebookSdk.isDebugEnabled() && $r0 != null && $r1 != null) {
            Log.d($r0, $r1);
        }
    }

    public static void logd(String $r0, String $r1, Throwable $r2) throws  {
        if (FacebookSdk.isDebugEnabled() && !isNullOrEmpty($r0)) {
            Log.d($r0, $r1, $r2);
        }
    }

    public static <T> boolean areObjectsEqual(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;)Z"}) T $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;TT;)Z"}) T $r1) throws  {
        if ($r0 == null) {
            return $r1 == null;
        } else {
            return $r0.equals($r1);
        }
    }

    public static boolean hasSameId(JSONObject $r0, JSONObject $r1) throws  {
        if ($r0 == null) {
            return false;
        }
        if ($r1 == null) {
            return false;
        }
        if (!$r0.has("id")) {
            return false;
        }
        if (!$r1.has("id")) {
            return false;
        }
        if ($r0.equals($r1)) {
            return true;
        }
        String $r2 = $r0.optString("id");
        String $r3 = $r1.optString("id");
        if ($r2 != null) {
            return $r3 != null ? $r2.equals($r3) : false;
        } else {
            return false;
        }
    }

    public static void loadAppSettingsAsync(Context $r0, String $r1) throws  {
        boolean $z0 = loadingSettings.compareAndSet(false, true);
        if (!isNullOrEmpty($r1) && !fetchedAppSettings.containsKey($r1) && $z0) {
            final String $r7 = String.format(APP_SETTINGS_PREFS_KEY_FORMAT, new Object[]{$r1});
            final String str = $r1;
            final Context context = $r0;
            FacebookSdk.getExecutor().execute(new Runnable() {
                public void run() throws  {
                    JSONObject $r2 = Utility.getAppSettingsQueryResponse(str);
                    if ($r2 != null) {
                        Utility.parseAppSettingsFromJSON(str, $r2);
                        context.getSharedPreferences(Utility.APP_SETTINGS_PREFS_STORE, 0).edit().putString($r7, $r2.toString()).apply();
                    }
                    Utility.loadingSettings.set(false);
                }
            });
            $r7 = $r0.getSharedPreferences(APP_SETTINGS_PREFS_STORE, 0).getString($r7, null);
            if (!isNullOrEmpty($r7)) {
                JSONObject $r11 = null;
                try {
                    $r11 = new JSONObject($r7);
                } catch (Exception $r2) {
                    logd(LOG_TAG, $r2);
                }
                if ($r11 != null) {
                    parseAppSettingsFromJSON($r1, $r11);
                }
            }
        }
    }

    public static FetchedAppSettings getAppSettingsWithoutQuery(String $r0) throws  {
        return $r0 != null ? (FetchedAppSettings) fetchedAppSettings.get($r0) : null;
    }

    public static FetchedAppSettings queryAppSettings(String $r0, boolean $z0) throws  {
        if (!$z0 && fetchedAppSettings.containsKey($r0)) {
            return (FetchedAppSettings) fetchedAppSettings.get($r0);
        }
        JSONObject $r4 = getAppSettingsQueryResponse($r0);
        if ($r4 == null) {
            return null;
        }
        return parseAppSettingsFromJSON($r0, $r4);
    }

    private static FetchedAppSettings parseAppSettingsFromJSON(String $r0, JSONObject $r1) throws  {
        FacebookRequestErrorClassification $r4;
        JSONArray $r3 = $r1.optJSONArray(APP_SETTING_ANDROID_SDK_ERROR_CATEGORIES);
        if ($r3 == null) {
            $r4 = FacebookRequestErrorClassification.getDefaultErrorClassification();
        } else {
            $r4 = FacebookRequestErrorClassification.createFromJSON($r3);
        }
        FetchedAppSettings fetchedAppSettings = new FetchedAppSettings($r1.optBoolean(APP_SETTING_SUPPORTS_IMPLICIT_SDK_LOGGING, false), $r1.optString(APP_SETTING_NUX_CONTENT, ""), $r1.optBoolean(APP_SETTING_NUX_ENABLED, false), parseDialogConfigurations($r1.optJSONObject(APP_SETTING_DIALOG_CONFIGS)), $r4);
        fetchedAppSettings.put($r0, fetchedAppSettings);
        return fetchedAppSettings;
    }

    private static JSONObject getAppSettingsQueryResponse(String $r0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putString("fields", TextUtils.join(",", APP_SETTING_FIELDS));
        GraphRequest $r4 = GraphRequest.newGraphPathRequest(null, $r0, null);
        $r4.setSkipClientToken(true);
        $r4.setParameters($r1);
        return $r4.executeAndWait().getJSONObject();
    }

    public static DialogFeatureConfig getDialogFeatureConfig(String $r0, String $r1, String $r2) throws  {
        if (isNullOrEmpty($r1)) {
            return null;
        }
        if (isNullOrEmpty($r2)) {
            return null;
        }
        FetchedAppSettings $r6 = (FetchedAppSettings) fetchedAppSettings.get($r0);
        if ($r6 == null) {
            return null;
        }
        Map $r4 = (Map) $r6.getDialogConfigurations().get($r1);
        return $r4 != null ? (DialogFeatureConfig) $r4.get($r2) : null;
    }

    private static Map<String, Map<String, DialogFeatureConfig>> parseDialogConfigurations(@Signature({"(", "Lorg/json/JSONObject;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/internal/Utility$DialogFeatureConfig;", ">;>;"}) JSONObject $r0) throws  {
        HashMap $r1 = r7;
        HashMap r7 = new HashMap();
        if ($r0 == null) {
            return $r1;
        }
        JSONArray $r2 = $r0.optJSONArray("data");
        if ($r2 == null) {
            return $r1;
        }
        for (int $i0 = 0; $i0 < $r2.length(); $i0++) {
            DialogFeatureConfig $r3 = DialogFeatureConfig.parseDialogConfig($r2.optJSONObject($i0));
            if ($r3 != null) {
                String $r4 = $r3.getDialogName();
                Map $r6 = (Map) $r1.get($r4);
                if ($r6 == null) {
                    $r6 = r7;
                    r7 = new HashMap();
                    $r1.put($r4, $r6);
                }
                $r6.put($r3.getFeatureName(), $r3);
            }
        }
        return $r1;
    }

    public static String safeGetStringFromResponse(JSONObject $r0, String $r1) throws  {
        return $r0 != null ? $r0.optString($r1, "") : "";
    }

    public static JSONObject tryGetJSONObjectFromResponse(JSONObject $r0, String $r1) throws  {
        return $r0 != null ? $r0.optJSONObject($r1) : null;
    }

    public static JSONArray tryGetJSONArrayFromResponse(JSONObject $r0, String $r1) throws  {
        return $r0 != null ? $r0.optJSONArray($r1) : null;
    }

    public static void clearCaches(Context $r0) throws  {
        ImageDownloader.clearCache($r0);
    }

    public static void deleteDirectory(File $r0) throws  {
        if ($r0.exists()) {
            if ($r0.isDirectory()) {
                for (File $r1 : $r0.listFiles()) {
                    deleteDirectory($r1);
                }
            }
            $r0.delete();
        }
    }

    public static <T> List<T> asListNoNulls(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;)", "Ljava/util/List", "<TT;>;"}) T... $r0) throws  {
        ArrayList $r1 = new ArrayList();
        for (Object $r2 : $r0) {
            if ($r2 != null) {
                $r1.add($r2);
            }
        }
        return $r1;
    }

    public static List<String> jsonArrayToStringList(@Signature({"(", "Lorg/json/JSONArray;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) JSONArray $r0) throws JSONException {
        ArrayList $r1 = new ArrayList();
        for (int $i0 = 0; $i0 < $r0.length(); $i0++) {
            $r1.add($r0.getString($i0));
        }
        return $r1;
    }

    public static Set<String> jsonArrayToSet(@Signature({"(", "Lorg/json/JSONArray;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) JSONArray $r0) throws JSONException {
        HashSet $r1 = new HashSet();
        for (int $i0 = 0; $i0 < $r0.length(); $i0++) {
            $r1.add($r0.getString($i0));
        }
        return $r1;
    }

    public static void setAppEventAttributionParameters(JSONObject $r0, AttributionIdentifiers $r1, String $r2, boolean $z0) throws JSONException {
        boolean $z1 = true;
        if (!($r1 == null || $r1.getAttributionId() == null)) {
            $r0.put("attribution", $r1.getAttributionId());
        }
        if (!($r1 == null || $r1.getAndroidAdvertiserId() == null)) {
            $r0.put("advertiser_id", $r1.getAndroidAdvertiserId());
            $r0.put("advertiser_tracking_enabled", !$r1.isTrackingLimited());
        }
        if (!($r1 == null || $r1.getAndroidInstallerPackage() == null)) {
            $r0.put("installer_package", $r1.getAndroidInstallerPackage());
        }
        $r0.put("anon_id", $r2);
        if ($z0) {
            $z1 = false;
        }
        $r0.put("application_tracking_enabled", $z1);
    }

    public static void setAppEventExtendedDeviceInfoParameters(JSONObject $r0, Context $r1) throws JSONException {
        Locale $r11;
        JSONArray $r4 = new JSONArray();
        $r4.put(EXTRA_APP_EVENTS_INFO_FORMAT_VERSION);
        refreshPeriodicExtendedDeviceInfo($r1);
        String $r5 = $r1.getPackageName();
        int $i0 = -1;
        String $r6 = "";
        try {
            PackageInfo $r8 = $r1.getPackageManager().getPackageInfo($r5, 0);
            $i0 = $r8.versionCode;
            $r6 = $r8.versionName;
        } catch (NameNotFoundException e) {
        }
        $r4.put($r5);
        $r4.put($i0);
        $r4.put($r6);
        $r4.put(VERSION.RELEASE);
        $r4.put(Build.MODEL);
        try {
            $r11 = $r1.getResources().getConfiguration().locale;
        } catch (Exception e2) {
            $r11 = Locale.getDefault();
        }
        $r4.put($r11.getLanguage() + "_" + $r11.getCountry());
        $r4.put(deviceTimezone);
        $r4.put(carrierName);
        int $i1 = 0;
        $i0 = 0;
        double $d0 = 0.0d;
        try {
            WindowManager $r14 = (WindowManager) $r1.getSystemService("window");
            if ($r14 != null) {
                Display $r15 = $r14.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                $r15.getMetrics(displayMetrics);
                $i1 = displayMetrics.widthPixels;
                $i0 = displayMetrics.heightPixels;
                double $d02 = displayMetrics.density;
                float $f0 = $d02;
                $d0 = (double) $d02;
            }
        } catch (Exception e3) {
        }
        $r4.put($i1);
        $r4.put($i0);
        $r4.put(String.format("%.2f", new Object[]{Double.valueOf($d0)}));
        $r4.put(refreshBestGuessNumberOfCPUCores());
        $r4.put(totalExternalStorageGB);
        $r4.put(availableExternalStorageGB);
        JSONObject jSONObject = $r0;
        jSONObject.put("extinfo", $r4.toString());
    }

    public static Method getMethodQuietly(@Signature({"(", "Ljava/lang/Class", "<*>;", "Ljava/lang/String;", "[", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Method;"}) Class<?> $r0, @Signature({"(", "Ljava/lang/Class", "<*>;", "Ljava/lang/String;", "[", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Method;"}) String $r1, @Signature({"(", "Ljava/lang/Class", "<*>;", "Ljava/lang/String;", "[", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Method;"}) Class<?>... $r2) throws  {
        try {
            return $r0.getMethod($r1, $r2);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Method getMethodQuietly(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Method;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Method;"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/Class", "<*>;)", "Ljava/lang/reflect/Method;"}) Class<?>... $r2) throws  {
        try {
            return getMethodQuietly(Class.forName($r0), $r1, (Class[]) $r2);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Object invokeMethodQuietly(Object $r0, Method $r1, Object... $r2) throws  {
        try {
            return $r1.invoke($r0, $r2);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e2) {
            return null;
        }
    }

    public static String getActivityName(Context $r0) throws  {
        if ($r0 == null) {
            return "null";
        }
        if ($r0 == $r0.getApplicationContext()) {
            return "unknown";
        }
        return $r0.getClass().getSimpleName();
    }

    public static <T> List<T> filter(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/List", "<TT;>;", "Lcom/facebook/internal/Utility$Predicate", "<TT;>;)", "Ljava/util/List", "<TT;>;"}) List<T> $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/List", "<TT;>;", "Lcom/facebook/internal/Utility$Predicate", "<TT;>;)", "Ljava/util/List", "<TT;>;"}) Predicate<T> $r1) throws  {
        if ($r0 == null) {
            return null;
        }
        ArrayList $r2 = new ArrayList();
        for (Object $r4 : $r0) {
            if ($r1.apply($r4)) {
                $r2.add($r4);
            }
        }
        if ($r2.size() == 0) {
            $r2 = null;
        }
        return $r2;
    }

    public static <T, K> List<K> map(@Signature({"<T:", "Ljava/lang/Object;", "K:", "Ljava/lang/Object;", ">(", "Ljava/util/List", "<TT;>;", "Lcom/facebook/internal/Utility$Mapper", "<TT;TK;>;)", "Ljava/util/List", "<TK;>;"}) List<T> $r0, @Signature({"<T:", "Ljava/lang/Object;", "K:", "Ljava/lang/Object;", ">(", "Ljava/util/List", "<TT;>;", "Lcom/facebook/internal/Utility$Mapper", "<TT;TK;>;)", "Ljava/util/List", "<TK;>;"}) Mapper<T, K> $r1) throws  {
        if ($r0 == null) {
            return null;
        }
        ArrayList $r2 = new ArrayList();
        for (T $r4 : $r0) {
            Object $r42 = $r1.apply($r4);
            if ($r42 != null) {
                $r2.add($r42);
            }
        }
        if ($r2.size() == 0) {
            $r2 = null;
        }
        return $r2;
    }

    public static String getUriString(Uri $r0) throws  {
        return $r0 == null ? null : $r0.toString();
    }

    public static boolean isWebUri(Uri $r0) throws  {
        return $r0 != null && ("http".equalsIgnoreCase($r0.getScheme()) || URL_SCHEME.equalsIgnoreCase($r0.getScheme()));
    }

    public static boolean isContentUri(Uri $r0) throws  {
        return $r0 != null && "content".equalsIgnoreCase($r0.getScheme());
    }

    public static boolean isFileUri(Uri $r0) throws  {
        return $r0 != null && "file".equalsIgnoreCase($r0.getScheme());
    }

    public static Date getBundleLongAsDate(Bundle $r0, String $r1, Date $r2) throws  {
        if ($r0 == null) {
            return null;
        }
        long $l1;
        Object $r5 = $r0.get($r1);
        if ($r5 instanceof Long) {
            $l1 = ((Long) $r5).longValue();
        } else if (!($r5 instanceof String)) {
            return null;
        } else {
            try {
                $l1 = Long.parseLong((String) $r5);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        if ($l1 == 0) {
            return new Date(Long.MAX_VALUE);
        }
        return new Date($r2.getTime() + (1000 * $l1));
    }

    public static void writeStringMapToParcel(@Signature({"(", "Landroid/os/Parcel;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Parcel $r0, @Signature({"(", "Landroid/os/Parcel;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r1) throws  {
        if ($r1 == null) {
            $r0.writeInt(-1);
            return;
        }
        $r0.writeInt($r1.size());
        for (Entry $r5 : $r1.entrySet()) {
            $r0.writeString((String) $r5.getKey());
            $r0.writeString((String) $r5.getValue());
        }
    }

    public static Map<String, String> readStringMapFromParcel(@Signature({"(", "Landroid/os/Parcel;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) Parcel $r0) throws  {
        int $i0 = $r0.readInt();
        if ($i0 < 0) {
            return null;
        }
        HashMap $r1 = new HashMap();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r1.put($r0.readString(), $r0.readString());
        }
        return $r1;
    }

    public static boolean isCurrentAccessToken(AccessToken $r0) throws  {
        return $r0 != null ? $r0.equals(AccessToken.getCurrentAccessToken()) : false;
    }

    public static void getGraphMeRequestWithCacheAsync(final String $r0, final GraphMeRequestWithCacheCallback $r1) throws  {
        JSONObject $r3 = ProfileInformationCache.getProfileInformation($r0);
        if ($r3 != null) {
            $r1.onSuccess($r3);
            return;
        }
        C05382 $r2 = new Callback() {
            public void onCompleted(GraphResponse $r1) throws  {
                if ($r1.getError() != null) {
                    $r1.onFailure($r1.getError().getException());
                    return;
                }
                ProfileInformationCache.putProfileInformation($r0, $r1.getJSONObject());
                $r1.onSuccess($r1.getJSONObject());
            }
        };
        GraphRequest $r4 = getGraphMeRequestWithCache($r0);
        $r4.setCallback($r2);
        $r4.executeAsync();
    }

    public static JSONObject awaitGetGraphMeRequestWithCache(String $r0) throws  {
        JSONObject $r1 = ProfileInformationCache.getProfileInformation($r0);
        if ($r1 != null) {
            return $r1;
        }
        GraphResponse $r3 = getGraphMeRequestWithCache($r0).executeAndWait();
        if ($r3.getError() != null) {
            return null;
        }
        return $r3.getJSONObject();
    }

    private static GraphRequest getGraphMeRequestWithCache(String $r0) throws  {
        Bundle $r2 = new Bundle();
        $r2.putString("fields", "id,name,first_name,middle_name,last_name,link");
        $r2.putString("access_token", $r0);
        return new GraphRequest(null, "me", $r2, HttpMethod.GET, null);
    }

    private static int refreshBestGuessNumberOfCPUCores() throws  {
        if (numCPUCores > 0) {
            return numCPUCores;
        }
        if (VERSION.SDK_INT <= 10) {
            numCPUCores = 1;
            return numCPUCores;
        }
        try {
            numCPUCores = new File("/sys/devices/system/cpu/").listFiles(new C05393()).length;
        } catch (Exception e) {
        }
        if (numCPUCores <= 0) {
            numCPUCores = Math.max(Runtime.getRuntime().availableProcessors(), 1);
        }
        return numCPUCores;
    }

    private static void refreshPeriodicExtendedDeviceInfo(Context $r0) throws  {
        if (timestampOfLastCheck == -1 || System.currentTimeMillis() - timestampOfLastCheck >= 1800000) {
            timestampOfLastCheck = System.currentTimeMillis();
            refreshTimezone();
            refreshCarrierName($r0);
            refreshTotalExternalStorage();
            refreshAvailableExternalStorage();
        }
    }

    private static void refreshTimezone() throws  {
        try {
            TimeZone $r0 = TimeZone.getDefault();
            deviceTimezone = $r0.getDisplayName($r0.inDaylightTime(new Date()), 0);
        } catch (Exception e) {
        }
    }

    private static void refreshCarrierName(Context $r0) throws  {
        if (carrierName.equals(noCarrierConstant)) {
            try {
                carrierName = ((TelephonyManager) $r0.getSystemService("phone")).getNetworkOperatorName();
            } catch (Exception e) {
            }
        }
    }

    private static boolean externalStorageExists() throws  {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    private static void refreshAvailableExternalStorage() throws  {
        try {
            if (externalStorageExists()) {
                StatFs $r0 = new StatFs(Environment.getExternalStorageDirectory().getPath());
                availableExternalStorageGB = ((long) $r0.getAvailableBlocks()) * ((long) $r0.getBlockSize());
            }
            availableExternalStorageGB = convertBytesToGB((double) availableExternalStorageGB);
        } catch (Exception e) {
        }
    }

    private static void refreshTotalExternalStorage() throws  {
        try {
            if (externalStorageExists()) {
                StatFs $r0 = new StatFs(Environment.getExternalStorageDirectory().getPath());
                totalExternalStorageGB = ((long) $r0.getBlockCount()) * ((long) $r0.getBlockSize());
            }
            totalExternalStorageGB = convertBytesToGB((double) totalExternalStorageGB);
        } catch (Exception e) {
        }
    }

    private static long convertBytesToGB(double $d0) throws  {
        return Math.round($d0 / 1.073741824E9d);
    }
}
