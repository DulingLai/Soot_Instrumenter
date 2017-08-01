package com.facebook.internal;

import com.facebook.FacebookSdk;
import java.util.Collection;

public final class ServerProtocol {
    private static final String DIALOG_AUTHORITY_FORMAT = "m.%s";
    public static final String DIALOG_CANCEL_URI = "fbconnect://cancel";
    public static final String DIALOG_PARAM_ACCESS_TOKEN = "access_token";
    public static final String DIALOG_PARAM_APP_ID = "app_id";
    public static final String DIALOG_PARAM_AUTH_TYPE = "auth_type";
    public static final String DIALOG_PARAM_CLIENT_ID = "client_id";
    public static final String DIALOG_PARAM_DEFAULT_AUDIENCE = "default_audience";
    public static final String DIALOG_PARAM_DISPLAY = "display";
    public static final String DIALOG_PARAM_DISPLAY_TOUCH = "touch";
    public static final String DIALOG_PARAM_E2E = "e2e";
    public static final String DIALOG_PARAM_LEGACY_OVERRIDE = "legacy_override";
    public static final String DIALOG_PARAM_REDIRECT_URI = "redirect_uri";
    public static final String DIALOG_PARAM_RESPONSE_TYPE = "response_type";
    public static final String DIALOG_PARAM_RETURN_SCOPES = "return_scopes";
    public static final String DIALOG_PARAM_SCOPE = "scope";
    public static final String DIALOG_PARAM_SDK_VERSION = "sdk";
    public static final String DIALOG_PATH = "dialog/";
    public static final String DIALOG_REDIRECT_URI = "fbconnect://success";
    public static final String DIALOG_REREQUEST_AUTH_TYPE = "rerequest";
    public static final String DIALOG_RESPONSE_TYPE_TOKEN_AND_SIGNED_REQUEST = "token,signed_request";
    public static final String DIALOG_RETURN_SCOPES_TRUE = "true";
    public static final String FALLBACK_DIALOG_DISPLAY_VALUE_TOUCH = "touch";
    public static final String FALLBACK_DIALOG_PARAM_APP_ID = "app_id";
    public static final String FALLBACK_DIALOG_PARAM_BRIDGE_ARGS = "bridge_args";
    public static final String FALLBACK_DIALOG_PARAM_KEY_HASH = "android_key_hash";
    public static final String FALLBACK_DIALOG_PARAM_METHOD_ARGS = "method_args";
    public static final String FALLBACK_DIALOG_PARAM_METHOD_RESULTS = "method_results";
    public static final String FALLBACK_DIALOG_PARAM_VERSION = "version";
    public static final String GRAPH_API_VERSION = "v2.4";
    private static final String GRAPH_URL_FORMAT = "https://graph.%s";
    private static final String GRAPH_VIDEO_URL_FORMAT = "https://graph-video.%s";
    private static final String TAG = ServerProtocol.class.getName();
    public static final String errorConnectionFailure = "CONNECTION_FAILURE";
    public static final Collection<String> errorsProxyAuthDisabled = Utility.unmodifiableCollection("service_disabled", "AndroidAuthKillSwitchException");
    public static final Collection<String> errorsUserCanceled = Utility.unmodifiableCollection("access_denied", "OAuthAccessDeniedException");

    public static final String getAPIVersion() throws  {
        return GRAPH_API_VERSION;
    }

    public static android.os.Bundle getQueryParamsForPlatformActivityIntentWebFallback(java.lang.String r15, int r16, android.os.Bundle r17) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0056 in list [B:17:0x0069]
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
        r1 = com.facebook.FacebookSdk.getApplicationContext();
        r2 = com.facebook.FacebookSdk.getApplicationSignature(r1);
        r3 = com.facebook.internal.Utility.isNullOrEmpty(r2);
        if (r3 == 0) goto L_0x0010;
    L_0x000e:
        r4 = 0;
        return r4;
    L_0x0010:
        r5 = new android.os.Bundle;
        r5.<init>();
        r6 = "android_key_hash";
        r5.putString(r6, r2);
        r2 = com.facebook.FacebookSdk.getApplicationId();
        r6 = "app_id";
        r5.putString(r6, r2);
        r6 = "version";
        r0 = r16;
        r5.putInt(r6, r0);
        r6 = "display";
        r7 = "touch";
        r5.putString(r6, r7);
        r8 = new android.os.Bundle;
        r8.<init>();
        r6 = "action_id";
        r8.putString(r6, r15);
        if (r17 != 0) goto L_0x0046;
    L_0x003f:
        r17 = new android.os.Bundle;
        r0 = r17;
        r0.<init>();
    L_0x0046:
        r9 = com.facebook.internal.BundleJSONConverter.convertToJSON(r8);	 Catch:{ JSONException -> 0x0069 }
        r0 = r17;	 Catch:{ JSONException -> 0x0069 }
        r10 = com.facebook.internal.BundleJSONConverter.convertToJSON(r0);	 Catch:{ JSONException -> 0x0069 }
        if (r9 == 0) goto L_0x0054;
    L_0x0052:
        if (r10 != 0) goto L_0x0056;	 Catch:{ JSONException -> 0x0069 }
    L_0x0054:
        r4 = 0;
        return r4;
    L_0x0056:
        r15 = r9.toString();	 Catch:{ JSONException -> 0x0069 }
        r6 = "bridge_args";	 Catch:{ JSONException -> 0x0069 }
        r5.putString(r6, r15);	 Catch:{ JSONException -> 0x0069 }
        r15 = r10.toString();	 Catch:{ JSONException -> 0x0069 }
        r6 = "method_args";	 Catch:{ JSONException -> 0x0069 }
        r5.putString(r6, r15);	 Catch:{ JSONException -> 0x0069 }
        return r5;
    L_0x0069:
        r11 = move-exception;
        r12 = com.facebook.LoggingBehavior.DEVELOPER_ERRORS;
        r15 = TAG;
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r6 = "Error creating Url -- ";
        r13 = r13.append(r6);
        r13 = r13.append(r11);
        r2 = r13.toString();
        r14 = 6;
        com.facebook.internal.Logger.log(r12, r14, r15, r2);
        r4 = 0;
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.ServerProtocol.getQueryParamsForPlatformActivityIntentWebFallback(java.lang.String, int, android.os.Bundle):android.os.Bundle");
    }

    public static final String getDialogAuthority() throws  {
        return String.format(DIALOG_AUTHORITY_FORMAT, new Object[]{FacebookSdk.getFacebookDomain()});
    }

    public static final String getGraphUrlBase() throws  {
        return String.format(GRAPH_URL_FORMAT, new Object[]{FacebookSdk.getFacebookDomain()});
    }

    public static final String getGraphVideoUrlBase() throws  {
        return String.format(GRAPH_VIDEO_URL_FORMAT, new Object[]{FacebookSdk.getFacebookDomain()});
    }
}
