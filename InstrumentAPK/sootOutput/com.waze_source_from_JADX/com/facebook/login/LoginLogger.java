package com.facebook.login;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import com.facebook.appevents.AppEventsLogger;
import dalvik.annotation.Signature;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

class LoginLogger {
    static final String EVENT_EXTRAS_DEFAULT_AUDIENCE = "default_audience";
    static final String EVENT_EXTRAS_FACEBOOK_VERSION = "facebookVersion";
    static final String EVENT_EXTRAS_IS_REAUTHORIZE = "isReauthorize";
    static final String EVENT_EXTRAS_LOGIN_BEHAVIOR = "login_behavior";
    static final String EVENT_EXTRAS_MISSING_INTERNET_PERMISSION = "no_internet_permission";
    static final String EVENT_EXTRAS_NEW_PERMISSIONS = "new_permissions";
    static final String EVENT_EXTRAS_NOT_TRIED = "not_tried";
    static final String EVENT_EXTRAS_PERMISSIONS = "permissions";
    static final String EVENT_EXTRAS_REQUEST_CODE = "request_code";
    static final String EVENT_EXTRAS_TRY_LOGIN_ACTIVITY = "try_login_activity";
    static final String EVENT_NAME_LOGIN_COMPLETE = "fb_mobile_login_complete";
    static final String EVENT_NAME_LOGIN_METHOD_COMPLETE = "fb_mobile_login_method_complete";
    static final String EVENT_NAME_LOGIN_METHOD_START = "fb_mobile_login_method_start";
    static final String EVENT_NAME_LOGIN_START = "fb_mobile_login_start";
    static final String EVENT_PARAM_AUTH_LOGGER_ID = "0_auth_logger_id";
    static final String EVENT_PARAM_ERROR_CODE = "4_error_code";
    static final String EVENT_PARAM_ERROR_MESSAGE = "5_error_message";
    static final String EVENT_PARAM_EXTRAS = "6_extras";
    static final String EVENT_PARAM_LOGIN_RESULT = "2_result";
    static final String EVENT_PARAM_METHOD = "3_method";
    static final String EVENT_PARAM_METHOD_RESULT_SKIPPED = "skipped";
    static final String EVENT_PARAM_TIMESTAMP = "1_timestamp_ms";
    static final String FACEBOOK_PACKAGE_NAME = "com.facebook.katana";
    private final AppEventsLogger appEventsLogger;
    private String applicationId;
    private String facebookVersion;

    public void logStartLogin(com.facebook.login.LoginClient.Request r13) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0053 in list []
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
        r12 = this;
        r0 = r13.getAuthId();
        r1 = newAuthorizationLoggingBundle(r0);
        r2 = new org.json.JSONObject;
        r2.<init>();	 Catch:{ JSONException -> 0x0065 }
        r3 = r13.getLoginBehavior();	 Catch:{ JSONException -> 0x0065 }
        r0 = r3.toString();	 Catch:{ JSONException -> 0x0065 }
        r4 = "login_behavior";	 Catch:{ JSONException -> 0x0065 }
        r2.put(r4, r0);	 Catch:{ JSONException -> 0x0065 }
        r5 = com.facebook.login.LoginClient.getLoginRequestCode();	 Catch:{ JSONException -> 0x0065 }
        r4 = "request_code";	 Catch:{ JSONException -> 0x0065 }
        r2.put(r4, r5);	 Catch:{ JSONException -> 0x0065 }
        r6 = r13.getPermissions();	 Catch:{ JSONException -> 0x0065 }
        r4 = ",";	 Catch:{ JSONException -> 0x0065 }
        r0 = android.text.TextUtils.join(r4, r6);	 Catch:{ JSONException -> 0x0065 }
        r4 = "permissions";	 Catch:{ JSONException -> 0x0065 }
        r2.put(r4, r0);	 Catch:{ JSONException -> 0x0065 }
        r7 = r13.getDefaultAudience();	 Catch:{ JSONException -> 0x0065 }
        r0 = r7.toString();	 Catch:{ JSONException -> 0x0065 }
        r4 = "default_audience";	 Catch:{ JSONException -> 0x0065 }
        r2.put(r4, r0);	 Catch:{ JSONException -> 0x0065 }
        r8 = r13.isRerequest();	 Catch:{ JSONException -> 0x0065 }
        r4 = "isReauthorize";	 Catch:{ JSONException -> 0x0065 }
        r2.put(r4, r8);	 Catch:{ JSONException -> 0x0065 }
        r0 = r12.facebookVersion;
        if (r0 == 0) goto L_0x0053;	 Catch:{ JSONException -> 0x0065 }
    L_0x004c:
        r0 = r12.facebookVersion;	 Catch:{ JSONException -> 0x0065 }
        r4 = "facebookVersion";	 Catch:{ JSONException -> 0x0065 }
        r2.put(r4, r0);	 Catch:{ JSONException -> 0x0065 }
    L_0x0053:
        r0 = r2.toString();	 Catch:{ JSONException -> 0x0065 }
        r4 = "6_extras";	 Catch:{ JSONException -> 0x0065 }
        r1.putString(r4, r0);	 Catch:{ JSONException -> 0x0065 }
    L_0x005c:
        r9 = r12.appEventsLogger;
        r4 = "fb_mobile_login_start";
        r10 = 0;
        r9.logSdkEvent(r4, r10, r1);
        return;
    L_0x0065:
        r11 = move-exception;
        goto L_0x005c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.login.LoginLogger.logStartLogin(com.facebook.login.LoginClient$Request):void");
    }

    LoginLogger(Context $r1, String $r2) throws  {
        this.applicationId = $r2;
        this.appEventsLogger = AppEventsLogger.newLogger($r1, $r2);
        try {
            PackageManager $r4 = $r1.getPackageManager();
            if ($r4 != null) {
                PackageInfo $r5 = $r4.getPackageInfo(FACEBOOK_PACKAGE_NAME, 0);
                if ($r5 != null) {
                    this.facebookVersion = $r5.versionName;
                }
            }
        } catch (NameNotFoundException e) {
        }
    }

    public String getApplicationId() throws  {
        return this.applicationId;
    }

    static Bundle newAuthorizationLoggingBundle(String $r0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putLong(EVENT_PARAM_TIMESTAMP, System.currentTimeMillis());
        $r1.putString(EVENT_PARAM_AUTH_LOGGER_ID, $r0);
        $r1.putString(EVENT_PARAM_METHOD, "");
        $r1.putString(EVENT_PARAM_LOGIN_RESULT, "");
        $r1.putString(EVENT_PARAM_ERROR_MESSAGE, "");
        $r1.putString(EVENT_PARAM_ERROR_CODE, "");
        $r1.putString(EVENT_PARAM_EXTRAS, "");
        return $r1;
    }

    public void logCompleteLogin(@Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Lcom/facebook/login/LoginClient$Result$Code;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/Exception;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Lcom/facebook/login/LoginClient$Result$Code;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/Exception;", ")V"}) Map<String, String> $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Lcom/facebook/login/LoginClient$Result$Code;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/Exception;", ")V"}) Code $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Lcom/facebook/login/LoginClient$Result$Code;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/Exception;", ")V"}) Map<String, String> $r4, @Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Lcom/facebook/login/LoginClient$Result$Code;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/Exception;", ")V"}) Exception $r5) throws  {
        Bundle $r6 = newAuthorizationLoggingBundle($r1);
        if ($r3 != null) {
            $r6.putString(EVENT_PARAM_LOGIN_RESULT, $r3.getLoggingValue());
        }
        if (!($r5 == null || $r5.getMessage() == null)) {
            $r6.putString(EVENT_PARAM_ERROR_MESSAGE, $r5.getMessage());
        }
        JSONObject $r7 = null;
        if (!$r2.isEmpty()) {
            $r7 = new JSONObject($r2);
        }
        if ($r4 != null) {
            if ($r7 == null) {
                $r7 = new JSONObject();
            }
            try {
                for (Entry $r11 : $r4.entrySet()) {
                    $r7.put((String) $r11.getKey(), $r11.getValue());
                }
            } catch (JSONException e) {
            }
        }
        if ($r7 != null) {
            $r6.putString(EVENT_PARAM_EXTRAS, $r7.toString());
        }
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_COMPLETE, null, $r6);
    }

    public void logAuthorizationMethodStart(String $r1, String $r2) throws  {
        Bundle $r3 = newAuthorizationLoggingBundle($r1);
        $r3.putString(EVENT_PARAM_METHOD, $r2);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_METHOD_START, null, $r3);
    }

    public void logAuthorizationMethodComplete(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r4, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r5, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r6) throws  {
        Bundle $r8 = newAuthorizationLoggingBundle($r1);
        if ($r3 != null) {
            $r8.putString(EVENT_PARAM_LOGIN_RESULT, $r3);
        }
        if ($r4 != null) {
            $r8.putString(EVENT_PARAM_ERROR_MESSAGE, $r4);
        }
        if ($r5 != null) {
            $r8.putString(EVENT_PARAM_ERROR_CODE, $r5);
        }
        if (!($r6 == null || $r6.isEmpty())) {
            $r8.putString(EVENT_PARAM_EXTRAS, new JSONObject($r6).toString());
        }
        $r8.putString(EVENT_PARAM_METHOD, $r2);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_METHOD_COMPLETE, null, $r8);
    }

    public void logUnexpectedError(String $r1, String $r2) throws  {
        logUnexpectedError($r1, $r2, "");
    }

    public void logUnexpectedError(String $r1, String $r2, String $r3) throws  {
        Bundle $r4 = newAuthorizationLoggingBundle("");
        $r4.putString(EVENT_PARAM_LOGIN_RESULT, Code.ERROR.getLoggingValue());
        $r4.putString(EVENT_PARAM_ERROR_MESSAGE, $r2);
        $r4.putString(EVENT_PARAM_METHOD, $r3);
        this.appEventsLogger.logSdkEvent($r1, null, $r4);
    }
}
