package com.facebook.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookSdk;
import com.facebook.login.DefaultAudience;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public final class NativeProtocol {
    public static final String ACTION_APPINVITE_DIALOG = "com.facebook.platform.action.request.APPINVITES_DIALOG";
    public static final String ACTION_FEED_DIALOG = "com.facebook.platform.action.request.FEED_DIALOG";
    public static final String ACTION_LIKE_DIALOG = "com.facebook.platform.action.request.LIKE_DIALOG";
    public static final String ACTION_MESSAGE_DIALOG = "com.facebook.platform.action.request.MESSAGE_DIALOG";
    public static final String ACTION_OGACTIONPUBLISH_DIALOG = "com.facebook.platform.action.request.OGACTIONPUBLISH_DIALOG";
    public static final String ACTION_OGMESSAGEPUBLISH_DIALOG = "com.facebook.platform.action.request.OGMESSAGEPUBLISH_DIALOG";
    public static final String AUDIENCE_EVERYONE = "everyone";
    public static final String AUDIENCE_FRIENDS = "friends";
    public static final String AUDIENCE_ME = "only_me";
    public static final String BRIDGE_ARG_ACTION_ID_STRING = "action_id";
    public static final String BRIDGE_ARG_APP_NAME_STRING = "app_name";
    public static final String BRIDGE_ARG_ERROR_BUNDLE = "error";
    public static final String BRIDGE_ARG_ERROR_CODE = "error_code";
    public static final String BRIDGE_ARG_ERROR_DESCRIPTION = "error_description";
    public static final String BRIDGE_ARG_ERROR_JSON = "error_json";
    public static final String BRIDGE_ARG_ERROR_SUBCODE = "error_subcode";
    public static final String BRIDGE_ARG_ERROR_TYPE = "error_type";
    private static final String CONTENT_SCHEME = "content://";
    public static final String ERROR_APPLICATION_ERROR = "ApplicationError";
    public static final String ERROR_NETWORK_ERROR = "NetworkError";
    public static final String ERROR_PERMISSION_DENIED = "PermissionDenied";
    public static final String ERROR_PROTOCOL_ERROR = "ProtocolError";
    public static final String ERROR_SERVICE_DISABLED = "ServiceDisabled";
    public static final String ERROR_UNKNOWN_ERROR = "UnknownError";
    public static final String ERROR_USER_CANCELED = "UserCanceled";
    public static final String EXTRA_ACCESS_TOKEN = "com.facebook.platform.extra.ACCESS_TOKEN";
    public static final String EXTRA_APPLICATION_ID = "com.facebook.platform.extra.APPLICATION_ID";
    public static final String EXTRA_APPLICATION_NAME = "com.facebook.platform.extra.APPLICATION_NAME";
    public static final String EXTRA_DIALOG_COMPLETE_KEY = "com.facebook.platform.extra.DID_COMPLETE";
    public static final String EXTRA_DIALOG_COMPLETION_GESTURE_KEY = "com.facebook.platform.extra.COMPLETION_GESTURE";
    public static final String EXTRA_EXPIRES_SECONDS_SINCE_EPOCH = "com.facebook.platform.extra.EXPIRES_SECONDS_SINCE_EPOCH";
    public static final String EXTRA_GET_INSTALL_DATA_PACKAGE = "com.facebook.platform.extra.INSTALLDATA_PACKAGE";
    public static final String EXTRA_PERMISSIONS = "com.facebook.platform.extra.PERMISSIONS";
    public static final String EXTRA_PROTOCOL_ACTION = "com.facebook.platform.protocol.PROTOCOL_ACTION";
    public static final String EXTRA_PROTOCOL_BRIDGE_ARGS = "com.facebook.platform.protocol.BRIDGE_ARGS";
    public static final String EXTRA_PROTOCOL_CALL_ID = "com.facebook.platform.protocol.CALL_ID";
    public static final String EXTRA_PROTOCOL_METHOD_ARGS = "com.facebook.platform.protocol.METHOD_ARGS";
    public static final String EXTRA_PROTOCOL_METHOD_RESULTS = "com.facebook.platform.protocol.RESULT_ARGS";
    public static final String EXTRA_PROTOCOL_VERSION = "com.facebook.platform.protocol.PROTOCOL_VERSION";
    static final String EXTRA_PROTOCOL_VERSIONS = "com.facebook.platform.extra.PROTOCOL_VERSIONS";
    public static final String EXTRA_USER_ID = "com.facebook.platform.extra.USER_ID";
    private static final NativeAppInfo FACEBOOK_APP_INFO = new KatanaAppInfo();
    private static final String FACEBOOK_PROXY_AUTH_ACTIVITY = "com.facebook.katana.ProxyAuth";
    public static final String FACEBOOK_PROXY_AUTH_APP_ID_KEY = "client_id";
    public static final String FACEBOOK_PROXY_AUTH_E2E_KEY = "e2e";
    public static final String FACEBOOK_PROXY_AUTH_PERMISSIONS_KEY = "scope";
    private static final String FACEBOOK_TOKEN_REFRESH_ACTIVITY = "com.facebook.katana.platform.TokenRefreshService";
    public static final String IMAGE_URL_KEY = "url";
    public static final String IMAGE_USER_GENERATED_KEY = "user_generated";
    static final String INTENT_ACTION_PLATFORM_ACTIVITY = "com.facebook.platform.PLATFORM_ACTIVITY";
    static final String INTENT_ACTION_PLATFORM_SERVICE = "com.facebook.platform.PLATFORM_SERVICE";
    private static final List<Integer> KNOWN_PROTOCOL_VERSIONS = Arrays.asList(new Integer[]{Integer.valueOf(PROTOCOL_VERSION_20141218), Integer.valueOf(PROTOCOL_VERSION_20141107), Integer.valueOf(PROTOCOL_VERSION_20141028), Integer.valueOf(PROTOCOL_VERSION_20141001), Integer.valueOf(PROTOCOL_VERSION_20140701), Integer.valueOf(PROTOCOL_VERSION_20140324), Integer.valueOf(PROTOCOL_VERSION_20140204), Integer.valueOf(PROTOCOL_VERSION_20131107), Integer.valueOf(PROTOCOL_VERSION_20130618), Integer.valueOf(PROTOCOL_VERSION_20130502), Integer.valueOf(PROTOCOL_VERSION_20121101)});
    public static final int MESSAGE_GET_ACCESS_TOKEN_REPLY = 65537;
    public static final int MESSAGE_GET_ACCESS_TOKEN_REQUEST = 65536;
    public static final int MESSAGE_GET_INSTALL_DATA_REPLY = 65541;
    public static final int MESSAGE_GET_INSTALL_DATA_REQUEST = 65540;
    public static final int MESSAGE_GET_LIKE_STATUS_REPLY = 65543;
    public static final int MESSAGE_GET_LIKE_STATUS_REQUEST = 65542;
    static final int MESSAGE_GET_PROTOCOL_VERSIONS_REPLY = 65539;
    static final int MESSAGE_GET_PROTOCOL_VERSIONS_REQUEST = 65538;
    public static final int NO_PROTOCOL_AVAILABLE = -1;
    public static final String OPEN_GRAPH_CREATE_OBJECT_KEY = "fbsdk:create_object";
    private static final String PLATFORM_PROVIDER = ".provider.PlatformProvider";
    private static final String PLATFORM_PROVIDER_VERSIONS = ".provider.PlatformProvider/versions";
    private static final String PLATFORM_PROVIDER_VERSION_COLUMN = "version";
    public static final int PROTOCOL_VERSION_20121101 = 20121101;
    public static final int PROTOCOL_VERSION_20130502 = 20130502;
    public static final int PROTOCOL_VERSION_20130618 = 20130618;
    public static final int PROTOCOL_VERSION_20131107 = 20131107;
    public static final int PROTOCOL_VERSION_20140204 = 20140204;
    public static final int PROTOCOL_VERSION_20140324 = 20140324;
    public static final int PROTOCOL_VERSION_20140701 = 20140701;
    public static final int PROTOCOL_VERSION_20141001 = 20141001;
    public static final int PROTOCOL_VERSION_20141028 = 20141028;
    public static final int PROTOCOL_VERSION_20141107 = 20141107;
    public static final int PROTOCOL_VERSION_20141218 = 20141218;
    public static final String RESULT_ARGS_ACCESS_TOKEN = "access_token";
    public static final String RESULT_ARGS_DIALOG_COMPLETE_KEY = "didComplete";
    public static final String RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY = "completionGesture";
    public static final String RESULT_ARGS_EXPIRES_SECONDS_SINCE_EPOCH = "expires_seconds_since_epoch";
    public static final String RESULT_ARGS_PERMISSIONS = "permissions";
    public static final String STATUS_ERROR_CODE = "com.facebook.platform.status.ERROR_CODE";
    public static final String STATUS_ERROR_DESCRIPTION = "com.facebook.platform.status.ERROR_DESCRIPTION";
    public static final String STATUS_ERROR_JSON = "com.facebook.platform.status.ERROR_JSON";
    public static final String STATUS_ERROR_SUBCODE = "com.facebook.platform.status.ERROR_SUBCODE";
    public static final String STATUS_ERROR_TYPE = "com.facebook.platform.status.ERROR_TYPE";
    public static final String WEB_DIALOG_ACTION = "action";
    public static final String WEB_DIALOG_IS_FALLBACK = "is_fallback";
    public static final String WEB_DIALOG_PARAMS = "params";
    public static final String WEB_DIALOG_URL = "url";
    private static Map<String, List<NativeAppInfo>> actionToAppInfoMap = buildActionToAppInfoMap();
    private static List<NativeAppInfo> facebookAppInfoList = buildFacebookAppList();
    private static AtomicBoolean protocolVersionsAsyncUpdating = new AtomicBoolean(false);

    static class C05351 implements Runnable {
        C05351() throws  {
        }

        public void run() throws  {
            try {
                for (NativeAppInfo access$600 : NativeProtocol.facebookAppInfoList) {
                    access$600.fetchAvailableVersions(true);
                }
            } finally {
                NativeProtocol.protocolVersionsAsyncUpdating.set(false);
            }
        }
    }

    private static abstract class NativeAppInfo {
        private static final String FBI_HASH = "a4b7452e2ed8f5f191058ca7bbfd26b0d3214bfc";
        private static final String FBL_HASH = "5e8f16062ea3cd2c4a0d547876baa6f38cabf625";
        private static final String FBR_HASH = "8a3c4b262d721acd49a4bf97d5213199c86fa2b9";
        private static final HashSet<String> validAppSignatureHashes = buildAppSignatureHashes();
        private TreeSet<Integer> availableVersions;

        protected abstract String getPackage() throws ;

        private NativeAppInfo() throws  {
        }

        private static HashSet<String> buildAppSignatureHashes() throws  {
            HashSet $r0 = new HashSet();
            $r0.add(FBR_HASH);
            $r0.add(FBI_HASH);
            $r0.add(FBL_HASH);
            return $r0;
        }

        public boolean validateSignature(Context $r1, String $r2) throws  {
            String $r3 = Build.BRAND;
            int $i0 = $r1.getApplicationInfo().flags;
            if ($r3.startsWith("generic") && ($i0 & 2) != 0) {
                return true;
            }
            try {
                for (Signature $r5 : $r1.getPackageManager().getPackageInfo($r2, 64).signatures) {
                    if (validAppSignatureHashes.contains(Utility.sha1hash($r5.toByteArray()))) {
                        return true;
                    }
                }
                return false;
            } catch (NameNotFoundException e) {
                return false;
            }
        }

        public TreeSet<Integer> getAvailableVersions() throws  {
            if (this.availableVersions == null) {
                fetchAvailableVersions(false);
            }
            return this.availableVersions;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private synchronized void fetchAvailableVersions(boolean r3) throws  {
            /*
            r2 = this;
            monitor-enter(r2);
            if (r3 != 0) goto L_0x0007;
        L_0x0003:
            r0 = r2.availableVersions;	 Catch:{ Throwable -> 0x000f }
            if (r0 != 0) goto L_0x000d;
        L_0x0007:
            r0 = com.facebook.internal.NativeProtocol.fetchAllAvailableProtocolVersionsForAppInfo(r2);	 Catch:{ Throwable -> 0x000f }
            r2.availableVersions = r0;	 Catch:{ Throwable -> 0x000f }
        L_0x000d:
            monitor-exit(r2);
            return;
        L_0x000f:
            r1 = move-exception;
            monitor-exit(r2);
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.NativeProtocol.NativeAppInfo.fetchAvailableVersions(boolean):void");
        }
    }

    private static class KatanaAppInfo extends NativeAppInfo {
        static final String KATANA_PACKAGE = "com.facebook.katana";

        protected String getPackage() throws  {
            return KATANA_PACKAGE;
        }

        private KatanaAppInfo() throws  {
            super();
        }
    }

    private static class MessengerAppInfo extends NativeAppInfo {
        static final String MESSENGER_PACKAGE = "com.facebook.orca";

        protected String getPackage() throws  {
            return "com.facebook.orca";
        }

        private MessengerAppInfo() throws  {
            super();
        }
    }

    private static class WakizashiAppInfo extends NativeAppInfo {
        static final String WAKIZASHI_PACKAGE = "com.facebook.wakizashi";

        protected String getPackage() throws  {
            return WAKIZASHI_PACKAGE;
        }

        private WakizashiAppInfo() throws  {
            super();
        }
    }

    private static java.util.TreeSet<java.lang.Integer> fetchAllAvailableProtocolVersionsForAppInfo(@dalvik.annotation.Signature({"(", "Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", ")", "Ljava/util/TreeSet", "<", "Ljava/lang/Integer;", ">;"}) com.facebook.internal.NativeProtocol.NativeAppInfo r26) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x008c in list []
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
        r6 = new java.util.TreeSet;
        r6.<init>();
        r7 = com.facebook.FacebookSdk.getApplicationContext();
        r8 = r7.getContentResolver();
        r10 = 1;
        r9 = new java.lang.String[r10];
        r10 = 0;
        r11 = "version";
        r9[r10] = r11;
        r0 = r26;
        r12 = buildPlatformProviderVersionURI(r0);
        r13 = 0;
        r7 = com.facebook.FacebookSdk.getApplicationContext();	 Catch:{ Throwable -> 0x0085 }
        r14 = r7.getPackageManager();	 Catch:{ Throwable -> 0x0085 }
        r15 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0085 }
        r15.<init>();	 Catch:{ Throwable -> 0x0085 }
        r0 = r26;	 Catch:{ Throwable -> 0x0085 }
        r16 = r0.getPackage();	 Catch:{ Throwable -> 0x0085 }
        r0 = r16;	 Catch:{ Throwable -> 0x0085 }
        r15 = r15.append(r0);	 Catch:{ Throwable -> 0x0085 }
        r11 = ".provider.PlatformProvider";	 Catch:{ Throwable -> 0x0085 }
        r15 = r15.append(r11);	 Catch:{ Throwable -> 0x0085 }
        r16 = r15.toString();	 Catch:{ Throwable -> 0x0085 }
        r10 = 0;	 Catch:{ Throwable -> 0x0085 }
        r0 = r16;	 Catch:{ Throwable -> 0x0085 }
        r17 = r14.resolveContentProvider(r0, r10);	 Catch:{ Throwable -> 0x0085 }
        if (r17 == 0) goto L_0x008c;	 Catch:{ Throwable -> 0x0085 }
    L_0x0049:
        r19 = 0;	 Catch:{ Throwable -> 0x0085 }
        r20 = 0;	 Catch:{ Throwable -> 0x0085 }
        r21 = 0;	 Catch:{ Throwable -> 0x0085 }
        r0 = r8;	 Catch:{ Throwable -> 0x0085 }
        r1 = r12;	 Catch:{ Throwable -> 0x0085 }
        r2 = r9;	 Catch:{ Throwable -> 0x0085 }
        r3 = r19;	 Catch:{ Throwable -> 0x0085 }
        r4 = r20;	 Catch:{ Throwable -> 0x0085 }
        r5 = r21;	 Catch:{ Throwable -> 0x0085 }
        r18 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x0085 }
        r13 = r18;
        if (r18 == 0) goto L_0x008c;	 Catch:{ Throwable -> 0x0085 }
    L_0x0060:
        r0 = r18;	 Catch:{ Throwable -> 0x0085 }
        r22 = r0.moveToNext();	 Catch:{ Throwable -> 0x0085 }
        if (r22 == 0) goto L_0x008c;	 Catch:{ Throwable -> 0x0085 }
    L_0x0068:
        r11 = "version";	 Catch:{ Throwable -> 0x0085 }
        r0 = r18;	 Catch:{ Throwable -> 0x0085 }
        r23 = r0.getColumnIndex(r11);	 Catch:{ Throwable -> 0x0085 }
        r0 = r18;	 Catch:{ Throwable -> 0x0085 }
        r1 = r23;	 Catch:{ Throwable -> 0x0085 }
        r23 = r0.getInt(r1);	 Catch:{ Throwable -> 0x0085 }
        r0 = r23;	 Catch:{ Throwable -> 0x0085 }
        r24 = java.lang.Integer.valueOf(r0);	 Catch:{ Throwable -> 0x0085 }
        r0 = r24;	 Catch:{ Throwable -> 0x0085 }
        r6.add(r0);	 Catch:{ Throwable -> 0x0085 }
        goto L_0x0060;
    L_0x0085:
        r25 = move-exception;
        if (r13 == 0) goto L_0x008b;
    L_0x0088:
        r13.close();
    L_0x008b:
        throw r25;
    L_0x008c:
        if (r13 == 0) goto L_0x0092;
    L_0x008e:
        r13.close();
        return r6;
    L_0x0092:
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.NativeProtocol.fetchAllAvailableProtocolVersionsForAppInfo(com.facebook.internal.NativeProtocol$NativeAppInfo):java.util.TreeSet<java.lang.Integer>");
    }

    private static List<NativeAppInfo> buildFacebookAppList() throws  {
        ArrayList $r0 = new ArrayList();
        $r0.add(FACEBOOK_APP_INFO);
        $r0.add(new WakizashiAppInfo());
        return $r0;
    }

    private static Map<String, List<NativeAppInfo>> buildActionToAppInfoMap() throws  {
        HashMap $r0 = new HashMap();
        ArrayList $r1 = new ArrayList();
        $r1.add(new MessengerAppInfo());
        $r0.put(ACTION_OGACTIONPUBLISH_DIALOG, facebookAppInfoList);
        $r0.put(ACTION_FEED_DIALOG, facebookAppInfoList);
        $r0.put(ACTION_LIKE_DIALOG, facebookAppInfoList);
        $r0.put(ACTION_APPINVITE_DIALOG, facebookAppInfoList);
        $r0.put(ACTION_MESSAGE_DIALOG, $r1);
        $r0.put(ACTION_OGMESSAGEPUBLISH_DIALOG, $r1);
        return $r0;
    }

    static Intent validateActivityIntent(Context $r0, Intent $r2, NativeAppInfo $r1) throws  {
        if ($r2 == null) {
            return null;
        }
        ResolveInfo $r4 = $r0.getPackageManager().resolveActivity($r2, 0);
        if ($r4 == null) {
            return null;
        }
        return !$r1.validateSignature($r0, $r4.activityInfo.packageName) ? null : $r2;
    }

    static Intent validateServiceIntent(Context $r0, Intent $r2, NativeAppInfo $r1) throws  {
        if ($r2 == null) {
            return null;
        }
        ResolveInfo $r4 = $r0.getPackageManager().resolveService($r2, 0);
        if ($r4 == null) {
            return null;
        }
        return !$r1.validateSignature($r0, $r4.serviceInfo.packageName) ? null : $r2;
    }

    public static Intent createProxyAuthIntent(@dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "ZZ", "Lcom/facebook/login/DefaultAudience;", ")", "Landroid/content/Intent;"}) Context $r0, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "ZZ", "Lcom/facebook/login/DefaultAudience;", ")", "Landroid/content/Intent;"}) String $r1, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "ZZ", "Lcom/facebook/login/DefaultAudience;", ")", "Landroid/content/Intent;"}) Collection<String> $r2, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "ZZ", "Lcom/facebook/login/DefaultAudience;", ")", "Landroid/content/Intent;"}) String $r3, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "ZZ", "Lcom/facebook/login/DefaultAudience;", ")", "Landroid/content/Intent;"}) boolean $z0, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "ZZ", "Lcom/facebook/login/DefaultAudience;", ")", "Landroid/content/Intent;"}) boolean $z1, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "ZZ", "Lcom/facebook/login/DefaultAudience;", ")", "Landroid/content/Intent;"}) DefaultAudience $r4) throws  {
        for (NativeAppInfo $r8 : facebookAppInfoList) {
            Intent $r9 = new Intent().setClassName($r8.getPackage(), FACEBOOK_PROXY_AUTH_ACTIVITY).putExtra("client_id", $r1);
            if (!Utility.isNullOrEmpty((Collection) $r2)) {
                $r9.putExtra("scope", TextUtils.join(",", $r2));
            }
            if (!Utility.isNullOrEmpty($r3)) {
                $r9.putExtra("e2e", $r3);
            }
            $r9.putExtra("response_type", ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN_AND_SIGNED_REQUEST);
            $r9.putExtra(ServerProtocol.DIALOG_PARAM_RETURN_SCOPES, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
            if ($z1) {
                $r9.putExtra(ServerProtocol.DIALOG_PARAM_DEFAULT_AUDIENCE, $r4.getNativeProtocolAudience());
            }
            $r9.putExtra(ServerProtocol.DIALOG_PARAM_LEGACY_OVERRIDE, ServerProtocol.GRAPH_API_VERSION);
            if ($z0) {
                $r9.putExtra(ServerProtocol.DIALOG_PARAM_AUTH_TYPE, ServerProtocol.DIALOG_REREQUEST_AUTH_TYPE);
            }
            $r9 = validateActivityIntent($r0, $r9, $r8);
            if ($r9 != null) {
                return $r9;
            }
        }
        return null;
    }

    public static Intent createTokenRefreshIntent(Context $r0) throws  {
        for (NativeAppInfo $r4 : facebookAppInfoList) {
            Intent $r5 = validateServiceIntent($r0, new Intent().setClassName($r4.getPackage(), FACEBOOK_TOKEN_REFRESH_ACTIVITY), $r4);
            if ($r5 != null) {
                return $r5;
            }
        }
        return null;
    }

    public static final int getLatestKnownVersion() throws  {
        return ((Integer) KNOWN_PROTOCOL_VERSIONS.get(0)).intValue();
    }

    private static Intent findActivityIntent(Context $r0, String $r1, String $r2) throws  {
        List<NativeAppInfo> $r5 = (List) actionToAppInfoMap.get($r2);
        if ($r5 == null) {
            return null;
        }
        Intent $r6 = null;
        for (NativeAppInfo $r8 : $r5) {
            Intent $r9 = validateActivityIntent($r0, new Intent().setAction($r1).setPackage($r8.getPackage()).addCategory("android.intent.category.DEFAULT"), $r8);
            $r6 = $r9;
            if ($r9 != null) {
                return $r9;
            }
        }
        return $r6;
    }

    public static boolean isVersionCompatibleWithBucketedIntent(int $i0) throws  {
        return KNOWN_PROTOCOL_VERSIONS.contains(Integer.valueOf($i0)) && $i0 >= PROTOCOL_VERSION_20140701;
    }

    public static Intent createPlatformActivityIntent(Context $r0, String $r1, String $r2, int $i0, Bundle $r3) throws  {
        Intent $r4 = findActivityIntent($r0, INTENT_ACTION_PLATFORM_ACTIVITY, $r2);
        if ($r4 == null) {
            return null;
        }
        setupProtocolRequestIntent($r4, $r1, $r2, $i0, $r3);
        return $r4;
    }

    public static void setupProtocolRequestIntent(Intent $r0, String $r1, String $r2, int $i0, Bundle $r3) throws  {
        String $r5 = FacebookSdk.getApplicationId();
        String $r6 = FacebookSdk.getApplicationName();
        $r0.putExtra(EXTRA_PROTOCOL_VERSION, $i0).putExtra(EXTRA_PROTOCOL_ACTION, $r2).putExtra(EXTRA_APPLICATION_ID, $r5);
        if (isVersionCompatibleWithBucketedIntent($i0)) {
            Bundle $r4 = new Bundle();
            $r4.putString("action_id", $r1);
            Utility.putNonEmptyString($r4, BRIDGE_ARG_APP_NAME_STRING, $r6);
            $r0.putExtra(EXTRA_PROTOCOL_BRIDGE_ARGS, $r4);
            if ($r3 == null) {
                $r3 = new Bundle();
            }
            $r0.putExtra(EXTRA_PROTOCOL_METHOD_ARGS, $r3);
            return;
        }
        $r0.putExtra(EXTRA_PROTOCOL_CALL_ID, $r1);
        if (!Utility.isNullOrEmpty($r6)) {
            $r0.putExtra(EXTRA_APPLICATION_NAME, $r6);
        }
        $r0.putExtras($r3);
    }

    public static Intent createProtocolResultIntent(Intent $r0, Bundle $r1, FacebookException $r2) throws  {
        UUID $r4 = getCallIdFromIntent($r0);
        if ($r4 == null) {
            return null;
        }
        Intent $r5 = new Intent();
        $r5.putExtra(EXTRA_PROTOCOL_VERSION, getProtocolVersionFromIntent($r0));
        Bundle $r3 = new Bundle();
        $r3.putString("action_id", $r4.toString());
        if ($r2 != null) {
            $r3.putBundle("error", createBundleForException($r2));
        }
        $r5.putExtra(EXTRA_PROTOCOL_BRIDGE_ARGS, $r3);
        if ($r1 == null) {
            return $r5;
        }
        $r5.putExtra(EXTRA_PROTOCOL_METHOD_RESULTS, $r1);
        return $r5;
    }

    public static Intent createPlatformServiceIntent(Context $r0) throws  {
        for (NativeAppInfo $r4 : facebookAppInfoList) {
            Intent $r5 = validateServiceIntent($r0, new Intent(INTENT_ACTION_PLATFORM_SERVICE).setPackage($r4.getPackage()).addCategory("android.intent.category.DEFAULT"), $r4);
            if ($r5 != null) {
                return $r5;
            }
        }
        return null;
    }

    public static int getProtocolVersionFromIntent(Intent $r0) throws  {
        return $r0.getIntExtra(EXTRA_PROTOCOL_VERSION, 0);
    }

    public static UUID getCallIdFromIntent(Intent $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        String $r1 = null;
        if (isVersionCompatibleWithBucketedIntent(getProtocolVersionFromIntent($r0))) {
            Bundle $r2 = $r0.getBundleExtra(EXTRA_PROTOCOL_BRIDGE_ARGS);
            if ($r2 != null) {
                $r1 = $r2.getString("action_id");
            }
        } else {
            $r1 = $r0.getStringExtra(EXTRA_PROTOCOL_CALL_ID);
        }
        if ($r1 == null) {
            return null;
        }
        try {
            return UUID.fromString($r1);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static Bundle getBridgeArgumentsFromIntent(Intent $r0) throws  {
        return !isVersionCompatibleWithBucketedIntent(getProtocolVersionFromIntent($r0)) ? null : $r0.getBundleExtra(EXTRA_PROTOCOL_BRIDGE_ARGS);
    }

    public static Bundle getMethodArgumentsFromIntent(Intent $r0) throws  {
        if (isVersionCompatibleWithBucketedIntent(getProtocolVersionFromIntent($r0))) {
            return $r0.getBundleExtra(EXTRA_PROTOCOL_METHOD_ARGS);
        }
        return $r0.getExtras();
    }

    public static Bundle getSuccessResultsFromIntent(Intent $r0) throws  {
        int $i0 = getProtocolVersionFromIntent($r0);
        Bundle $r1 = $r0.getExtras();
        return (!isVersionCompatibleWithBucketedIntent($i0) || $r1 == null) ? $r1 : $r1.getBundle(EXTRA_PROTOCOL_METHOD_RESULTS);
    }

    public static boolean isErrorResult(Intent $r0) throws  {
        Bundle $r1 = getBridgeArgumentsFromIntent($r0);
        if ($r1 != null) {
            return $r1.containsKey("error");
        }
        return $r0.hasExtra(STATUS_ERROR_TYPE);
    }

    public static Bundle getErrorDataFromResultIntent(Intent $r0) throws  {
        if (!isErrorResult($r0)) {
            return null;
        }
        Bundle $r1 = getBridgeArgumentsFromIntent($r0);
        if ($r1 != null) {
            return $r1.getBundle("error");
        }
        return $r0.getExtras();
    }

    public static FacebookException getExceptionFromErrorData(Bundle $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        String $r2 = $r0.getString(BRIDGE_ARG_ERROR_TYPE);
        String $r3 = $r2;
        if ($r2 == null) {
            $r3 = $r0.getString(STATUS_ERROR_TYPE);
        }
        $r2 = $r0.getString(BRIDGE_ARG_ERROR_DESCRIPTION);
        String $r4 = $r2;
        if ($r2 == null) {
            $r4 = $r0.getString(STATUS_ERROR_DESCRIPTION);
        }
        if ($r3 == null || !$r3.equalsIgnoreCase(ERROR_USER_CANCELED)) {
            return new FacebookException($r4);
        }
        return new FacebookOperationCanceledException($r4);
    }

    public static Bundle createBundleForException(FacebookException $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        Bundle $r2 = new Bundle();
        $r2.putString(BRIDGE_ARG_ERROR_DESCRIPTION, $r0.toString());
        if (!($r0 instanceof FacebookOperationCanceledException)) {
            return $r2;
        }
        $r2.putString(BRIDGE_ARG_ERROR_TYPE, ERROR_USER_CANCELED);
        return $r2;
    }

    public static int getLatestAvailableProtocolVersionForService(int $i0) throws  {
        return getLatestAvailableProtocolVersionForAppInfoList(facebookAppInfoList, new int[]{$i0});
    }

    public static int getLatestAvailableProtocolVersionForAction(String $r0, int[] $r1) throws  {
        return getLatestAvailableProtocolVersionForAppInfoList((List) actionToAppInfoMap.get($r0), $r1);
    }

    private static int getLatestAvailableProtocolVersionForAppInfoList(@dalvik.annotation.Signature({"(", "Ljava/util/List", "<", "Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", ">;[I)I"}) List<NativeAppInfo> $r0, @dalvik.annotation.Signature({"(", "Ljava/util/List", "<", "Lcom/facebook/internal/NativeProtocol$NativeAppInfo;", ">;[I)I"}) int[] $r1) throws  {
        updateAllAvailableProtocolVersionsAsync();
        if ($r0 == null) {
            return -1;
        }
        for (NativeAppInfo availableVersions : $r0) {
            int $i0 = computeLatestAvailableVersionFromVersionSpec(availableVersions.getAvailableVersions(), getLatestKnownVersion(), $r1);
            if ($i0 != -1) {
                return $i0;
            }
        }
        return -1;
    }

    public static void updateAllAvailableProtocolVersionsAsync() throws  {
        if (protocolVersionsAsyncUpdating.compareAndSet(false, true)) {
            FacebookSdk.getExecutor().execute(new C05351());
        }
    }

    public static int computeLatestAvailableVersionFromVersionSpec(@dalvik.annotation.Signature({"(", "Ljava/util/TreeSet", "<", "Ljava/lang/Integer;", ">;I[I)I"}) TreeSet<Integer> $r0, @dalvik.annotation.Signature({"(", "Ljava/util/TreeSet", "<", "Ljava/lang/Integer;", ">;I[I)I"}) int $i0, @dalvik.annotation.Signature({"(", "Ljava/util/TreeSet", "<", "Ljava/lang/Integer;", ">;I[I)I"}) int[] $r1) throws  {
        int $i2 = $r1.length - 1;
        Iterator $r2 = $r0.descendingIterator();
        int $i3 = -1;
        while ($r2.hasNext()) {
            int $i4 = ((Integer) $r2.next()).intValue();
            int $i1 = Math.max($i3, $i4);
            $i3 = $i1;
            while ($i2 >= 0 && $r1[$i2] > $i4) {
                $i2--;
            }
            if ($i2 < 0) {
                return -1;
            }
            if ($r1[$i2] == $i4) {
                return $i2 % 2 == 0 ? Math.min($i1, $i0) : -1;
            }
        }
        return -1;
    }

    private static Uri buildPlatformProviderVersionURI(NativeAppInfo $r0) throws  {
        return Uri.parse(CONTENT_SCHEME + $r0.getPackage() + PLATFORM_PROVIDER_VERSIONS);
    }
}
