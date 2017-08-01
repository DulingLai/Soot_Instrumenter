package com.facebook;

import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.FetchedAppSettings;
import java.net.HttpURLConnection;
import org.json.JSONObject;

public final class FacebookRequestError {
    private static final String BODY_KEY = "body";
    private static final String CODE_KEY = "code";
    private static final String ERROR_CODE_FIELD_KEY = "code";
    private static final String ERROR_CODE_KEY = "error_code";
    private static final String ERROR_IS_TRANSIENT_KEY = "is_transient";
    private static final String ERROR_KEY = "error";
    private static final String ERROR_MESSAGE_FIELD_KEY = "message";
    private static final String ERROR_MSG_KEY = "error_msg";
    private static final String ERROR_REASON_KEY = "error_reason";
    private static final String ERROR_SUB_CODE_KEY = "error_subcode";
    private static final String ERROR_TYPE_FIELD_KEY = "type";
    private static final String ERROR_USER_MSG_KEY = "error_user_msg";
    private static final String ERROR_USER_TITLE_KEY = "error_user_title";
    static final Range HTTP_RANGE_SUCCESS = new Range(200, 299);
    public static final int INVALID_ERROR_CODE = -1;
    public static final int INVALID_HTTP_STATUS_CODE = -1;
    private final Object batchRequestResult;
    private final Category category;
    private final HttpURLConnection connection;
    private final int errorCode;
    private final String errorMessage;
    private final String errorRecoveryMessage;
    private final String errorType;
    private final String errorUserMessage;
    private final String errorUserTitle;
    private final FacebookException exception;
    private final JSONObject requestResult;
    private final JSONObject requestResultBody;
    private final int requestStatusCode;
    private final int subErrorCode;

    public enum Category {
        LOGIN_RECOVERABLE,
        OTHER,
        TRANSIENT
    }

    private static class Range {
        private final int end;
        private final int start;

        private Range(int $i0, int $i1) throws  {
            this.start = $i0;
            this.end = $i1;
        }

        boolean contains(int $i0) throws  {
            return this.start <= $i0 && $i0 <= this.end;
        }
    }

    private FacebookRequestError(int $i0, int $i1, int $i2, String $r1, String $r2, String $r3, String $r4, boolean $z0, JSONObject $r5, JSONObject $r6, Object $r7, HttpURLConnection $r8, FacebookException $r9) throws  {
        Category $r11;
        this.requestStatusCode = $i0;
        this.errorCode = $i1;
        this.subErrorCode = $i2;
        this.errorType = $r1;
        this.errorMessage = $r2;
        this.requestResultBody = $r5;
        this.requestResult = $r6;
        this.batchRequestResult = $r7;
        this.connection = $r8;
        this.errorUserTitle = $r3;
        this.errorUserMessage = $r4;
        boolean $z1 = false;
        if ($r9 != null) {
            this.exception = $r9;
            $z1 = true;
        } else {
            this.exception = new FacebookServiceException(this, $r2);
        }
        FacebookRequestErrorClassification $r10 = getErrorClassification();
        if ($z1) {
            $r11 = Category.OTHER;
        } else {
            $r11 = $r10.classify($i1, $i2, $z0);
        }
        this.category = $r11;
        this.errorRecoveryMessage = $r10.getRecoveryMessage(this.category);
    }

    FacebookRequestError(HttpURLConnection $r1, Exception $r2) throws  {
        this(-1, -1, -1, null, null, null, null, false, null, null, null, $r1, $r2 instanceof FacebookException ? (FacebookException) $r2 : new FacebookException((Throwable) $r2));
    }

    public FacebookRequestError(int $i0, String $r1, String $r2) throws  {
        this(-1, $i0, -1, $r1, $r2, null, null, false, null, null, null, null, null);
    }

    public Category getCategory() throws  {
        return this.category;
    }

    public int getRequestStatusCode() throws  {
        return this.requestStatusCode;
    }

    public int getErrorCode() throws  {
        return this.errorCode;
    }

    public int getSubErrorCode() throws  {
        return this.subErrorCode;
    }

    public String getErrorType() throws  {
        return this.errorType;
    }

    public String getErrorMessage() throws  {
        if (this.errorMessage != null) {
            return this.errorMessage;
        }
        return this.exception.getLocalizedMessage();
    }

    public String getErrorRecoveryMessage() throws  {
        return this.errorRecoveryMessage;
    }

    public String getErrorUserMessage() throws  {
        return this.errorUserMessage;
    }

    public String getErrorUserTitle() throws  {
        return this.errorUserTitle;
    }

    public JSONObject getRequestResultBody() throws  {
        return this.requestResultBody;
    }

    public JSONObject getRequestResult() throws  {
        return this.requestResult;
    }

    public Object getBatchRequestResult() throws  {
        return this.batchRequestResult;
    }

    public HttpURLConnection getConnection() throws  {
        return this.connection;
    }

    public FacebookException getException() throws  {
        return this.exception;
    }

    public String toString() throws  {
        return "{HttpStatus: " + this.requestStatusCode + ", errorCode: " + this.errorCode + ", errorType: " + this.errorType + ", errorMessage: " + getErrorMessage() + "}";
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.facebook.FacebookRequestError checkResponseAndCreateError(org.json.JSONObject r43, java.lang.Object r44, java.net.HttpURLConnection r45) throws  {
        /*
        r15 = "code";
        r0 = r43;
        r14 = r0.has(r15);	 Catch:{ JSONException -> 0x0181 }
        if (r14 == 0) goto L_0x0182;
    L_0x000a:
        r15 = "code";
        r0 = r43;
        r16 = r0.getInt(r15);	 Catch:{ JSONException -> 0x0181 }
        r15 = "body";
        r18 = "FACEBOOK_NON_JSON_RESULT";
        r0 = r43;
        r1 = r18;
        r17 = com.facebook.internal.Utility.getStringPropertyAsJSON(r0, r15, r1);	 Catch:{ JSONException -> 0x0181 }
        if (r17 == 0) goto L_0x0124;
    L_0x0020:
        r0 = r17;
        r14 = r0 instanceof org.json.JSONObject;
        if (r14 == 0) goto L_0x0124;
    L_0x0026:
        r20 = r17;
        r20 = (org.json.JSONObject) r20;
        r19 = r20;
        r21 = 0;
        r22 = 0;
        r23 = 0;
        r24 = 0;
        r14 = 0;
        r25 = -1;
        r26 = -1;
        r27 = 0;
        r15 = "error";
        r0 = r19;
        r28 = r0.has(r15);	 Catch:{ JSONException -> 0x0181 }
        if (r28 == 0) goto L_0x00d3;
    L_0x0045:
        r15 = "error";
        r29 = 0;
        r0 = r19;
        r1 = r29;
        r17 = com.facebook.internal.Utility.getStringPropertyAsJSON(r0, r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r31 = r17;
        r31 = (org.json.JSONObject) r31;	 Catch:{ JSONException -> 0x0181 }
        r30 = r31;
        r15 = "type";
        r29 = 0;
        r0 = r30;
        r1 = r29;
        r21 = r0.optString(r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r15 = "message";
        r29 = 0;
        r0 = r30;
        r1 = r29;
        r22 = r0.optString(r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r15 = "code";
        r32 = -1;
        r0 = r30;
        r1 = r32;
        r25 = r0.optInt(r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r15 = "error_subcode";
        r32 = -1;
        r0 = r30;
        r1 = r32;
        r26 = r0.optInt(r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r15 = "error_user_msg";
        r29 = 0;
        r0 = r30;
        r1 = r29;
        r23 = r0.optString(r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r15 = "error_user_title";
        r29 = 0;
        r0 = r30;
        r1 = r29;
        r24 = r0.optString(r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r15 = "is_transient";
        r32 = 0;
        r0 = r30;
        r1 = r32;
        r14 = r0.optBoolean(r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r27 = 1;
    L_0x00ae:
        if (r27 == 0) goto L_0x0124;
    L_0x00b0:
        r33 = new com.facebook.FacebookRequestError;	 Catch:{ JSONException -> 0x0181 }
        r29 = 0;
        r0 = r33;
        r1 = r16;
        r2 = r25;
        r3 = r26;
        r4 = r21;
        r5 = r22;
        r6 = r24;
        r7 = r23;
        r8 = r14;
        r9 = r19;
        r10 = r43;
        r11 = r44;
        r12 = r45;
        r13 = r29;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13);	 Catch:{ JSONException -> 0x0181 }
        return r33;
    L_0x00d3:
        r15 = "error_code";
        r0 = r19;
        r28 = r0.has(r15);	 Catch:{ JSONException -> 0x0181 }
        if (r28 != 0) goto L_0x00f1;
    L_0x00dd:
        r15 = "error_msg";
        r0 = r19;
        r28 = r0.has(r15);	 Catch:{ JSONException -> 0x0181 }
        if (r28 != 0) goto L_0x00f1;
    L_0x00e7:
        r15 = "error_reason";
        r0 = r19;
        r28 = r0.has(r15);	 Catch:{ JSONException -> 0x0181 }
        if (r28 == 0) goto L_0x00ae;
    L_0x00f1:
        r15 = "error_reason";
        r29 = 0;
        r0 = r19;
        r1 = r29;
        r21 = r0.optString(r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r15 = "error_msg";
        r29 = 0;
        r0 = r19;
        r1 = r29;
        r22 = r0.optString(r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r15 = "error_code";
        r32 = -1;
        r0 = r19;
        r1 = r32;
        r25 = r0.optInt(r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r15 = "error_subcode";
        r32 = -1;
        r0 = r19;
        r1 = r32;
        r26 = r0.optInt(r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r27 = 1;
        goto L_0x00ae;
    L_0x0124:
        r34 = HTTP_RANGE_SUCCESS;	 Catch:{ JSONException -> 0x0181 }
        r0 = r34;
        r1 = r16;
        r14 = r0.contains(r1);	 Catch:{ JSONException -> 0x0181 }
        if (r14 != 0) goto L_0x0182;
    L_0x0130:
        r33 = new com.facebook.FacebookRequestError;	 Catch:{ JSONException -> 0x0181 }
        r15 = "body";
        r0 = r43;
        r14 = r0.has(r15);	 Catch:{ JSONException -> 0x0181 }
        if (r14 == 0) goto L_0x017e;
    L_0x013c:
        r15 = "body";
        r18 = "FACEBOOK_NON_JSON_RESULT";
        r0 = r43;
        r1 = r18;
        r17 = com.facebook.internal.Utility.getStringPropertyAsJSON(r0, r15, r1);	 Catch:{ JSONException -> 0x0181 }
        r35 = r17;
        r35 = (org.json.JSONObject) r35;
        r19 = r35;
    L_0x014e:
        r32 = -1;
        r36 = -1;
        r29 = 0;
        r37 = 0;
        r38 = 0;
        r39 = 0;
        r40 = 0;
        r41 = 0;
        r0 = r33;
        r1 = r16;
        r2 = r32;
        r3 = r36;
        r4 = r29;
        r5 = r37;
        r6 = r38;
        r7 = r39;
        r8 = r40;
        r9 = r19;
        r10 = r43;
        r11 = r44;
        r12 = r45;
        r13 = r41;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13);	 Catch:{ JSONException -> 0x0181 }
        return r33;
    L_0x017e:
        r19 = 0;
        goto L_0x014e;
    L_0x0181:
        r42 = move-exception;
    L_0x0182:
        r29 = 0;
        return r29;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.FacebookRequestError.checkResponseAndCreateError(org.json.JSONObject, java.lang.Object, java.net.HttpURLConnection):com.facebook.FacebookRequestError");
    }

    static synchronized FacebookRequestErrorClassification getErrorClassification() throws  {
        FacebookRequestErrorClassification $r2;
        synchronized (FacebookRequestError.class) {
            try {
                FetchedAppSettings $r1 = Utility.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
                if ($r1 == null) {
                    $r2 = FacebookRequestErrorClassification.getDefaultErrorClassification();
                } else {
                    $r2 = $r1.getErrorClassification();
                }
            } catch (Throwable th) {
                Class cls = FacebookRequestError.class;
            }
        }
        return $r2;
    }
}
