package com.facebook.internal;

import com.facebook.FacebookRequestError.Category;
import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public final class FacebookRequestErrorClassification {
    public static final int EC_APP_TOO_MANY_CALLS = 4;
    public static final int EC_INVALID_SESSION = 102;
    public static final int EC_INVALID_TOKEN = 190;
    public static final int EC_RATE = 9;
    public static final int EC_SERVICE_UNAVAILABLE = 2;
    public static final int EC_TOO_MANY_USER_ACTION_CALLS = 341;
    public static final int EC_USER_TOO_MANY_CALLS = 17;
    public static final String KEY_LOGIN_RECOVERABLE = "login_recoverable";
    public static final String KEY_NAME = "name";
    public static final String KEY_OTHER = "other";
    public static final String KEY_RECOVERY_MESSAGE = "recovery_message";
    public static final String KEY_TRANSIENT = "transient";
    private static FacebookRequestErrorClassification defaultInstance;
    private final Map<Integer, Set<Integer>> loginRecoverableErrors;
    private final String loginRecoverableRecoveryMessage;
    private final Map<Integer, Set<Integer>> otherErrors;
    private final String otherRecoveryMessage;
    private final Map<Integer, Set<Integer>> transientErrors;
    private final String transientRecoveryMessage;

    static class C05231 extends HashMap<Integer, Set<Integer>> {
        C05231() throws  {
            put(Integer.valueOf(2), null);
            put(Integer.valueOf(4), null);
            put(Integer.valueOf(9), null);
            put(Integer.valueOf(17), null);
            put(Integer.valueOf(341), null);
        }
    }

    static class C05242 extends HashMap<Integer, Set<Integer>> {
        C05242() throws  {
            put(Integer.valueOf(102), null);
            put(Integer.valueOf(190), null);
        }
    }

    FacebookRequestErrorClassification(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Map<Integer, Set<Integer>> $r1, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Map<Integer, Set<Integer>> $r2, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Map<Integer, Set<Integer>> $r3, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6) throws  {
        this.otherErrors = $r1;
        this.transientErrors = $r2;
        this.loginRecoverableErrors = $r3;
        this.otherRecoveryMessage = $r4;
        this.transientRecoveryMessage = $r5;
        this.loginRecoverableRecoveryMessage = $r6;
    }

    public Map<Integer, Set<Integer>> getOtherErrors() throws  {
        return this.otherErrors;
    }

    public Map<Integer, Set<Integer>> getTransientErrors() throws  {
        return this.transientErrors;
    }

    public Map<Integer, Set<Integer>> getLoginRecoverableErrors() throws  {
        return this.loginRecoverableErrors;
    }

    public String getRecoveryMessage(Category $r1) throws  {
        switch ($r1) {
            case OTHER:
                return this.otherRecoveryMessage;
            case LOGIN_RECOVERABLE:
                return this.loginRecoverableRecoveryMessage;
            case TRANSIENT:
                return this.transientRecoveryMessage;
            default:
                return null;
        }
    }

    public Category classify(int $i0, int $i1, boolean $z0) throws  {
        if ($z0) {
            return Category.TRANSIENT;
        }
        Set $r4;
        if (this.otherErrors != null && this.otherErrors.containsKey(Integer.valueOf($i0))) {
            $r4 = (Set) this.otherErrors.get(Integer.valueOf($i0));
            if ($r4 == null || $r4.contains(Integer.valueOf($i1))) {
                return Category.OTHER;
            }
        }
        if (this.loginRecoverableErrors != null && this.loginRecoverableErrors.containsKey(Integer.valueOf($i0))) {
            $r4 = (Set) this.loginRecoverableErrors.get(Integer.valueOf($i0));
            if ($r4 == null || $r4.contains(Integer.valueOf($i1))) {
                return Category.LOGIN_RECOVERABLE;
            }
        }
        if (this.transientErrors != null && this.transientErrors.containsKey(Integer.valueOf($i0))) {
            $r4 = (Set) this.transientErrors.get(Integer.valueOf($i0));
            if ($r4 == null || $r4.contains(Integer.valueOf($i1))) {
                return Category.TRANSIENT;
            }
        }
        return Category.OTHER;
    }

    public static synchronized FacebookRequestErrorClassification getDefaultErrorClassification() throws  {
        Class cls = FacebookRequestErrorClassification.class;
        synchronized (cls) {
            try {
                if (defaultInstance == null) {
                    defaultInstance = getDefaultErrorClassificationImpl();
                }
                FacebookRequestErrorClassification $r0 = defaultInstance;
                return $r0;
            } finally {
                cls = FacebookRequestErrorClassification.class;
            }
        }
    }

    private static FacebookRequestErrorClassification getDefaultErrorClassificationImpl() throws  {
        return new FacebookRequestErrorClassification(null, new C05231(), new C05242(), null, null, null);
    }

    private static Map<Integer, Set<Integer>> parseJSONDefinition(@Signature({"(", "Lorg/json/JSONObject;", ")", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;>;"}) JSONObject $r0) throws  {
        JSONArray $r1 = $r0.optJSONArray("items");
        if ($r1.length() == 0) {
            return null;
        }
        HashMap $r2 = new HashMap();
        for (int $i0 = 0; $i0 < $r1.length(); $i0++) {
            $r0 = $r1.optJSONObject($i0);
            if ($r0 != null) {
                int $i1 = $r0.optInt(QueryParams.CODE);
                if ($i1 != 0) {
                    HashSet $r3 = null;
                    JSONArray $r4 = $r0.optJSONArray("subcodes");
                    if ($r4 != null && $r4.length() > 0) {
                        $r3 = new HashSet();
                        for (int $i2 = 0; $i2 < $r4.length(); $i2++) {
                            int $i3 = $r4.optInt($i2);
                            if ($i3 != 0) {
                                $r3.add(Integer.valueOf($i3));
                            }
                        }
                    }
                    $r2.put(Integer.valueOf($i1), $r3);
                }
            }
        }
        return $r2;
    }

    public static FacebookRequestErrorClassification createFromJSON(JSONArray $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        Map $r2 = null;
        Map $r3 = null;
        Map $r4 = null;
        String $r5 = null;
        String $r6 = null;
        String $r7 = null;
        for (int $i0 = 0; $i0 < $r0.length(); $i0++) {
            JSONObject $r8 = $r0.optJSONObject($i0);
            if ($r8 != null) {
                String $r9 = $r8.optString("name");
                if ($r9 != null) {
                    if ($r9.equalsIgnoreCase(KEY_OTHER)) {
                        $r5 = $r8.optString(KEY_RECOVERY_MESSAGE, null);
                        $r2 = parseJSONDefinition($r8);
                    } else if ($r9.equalsIgnoreCase(KEY_TRANSIENT)) {
                        $r6 = $r8.optString(KEY_RECOVERY_MESSAGE, null);
                        $r3 = parseJSONDefinition($r8);
                    } else if ($r9.equalsIgnoreCase(KEY_LOGIN_RECOVERABLE)) {
                        $r7 = $r8.optString(KEY_RECOVERY_MESSAGE, null);
                        $r4 = parseJSONDefinition($r8);
                    }
                }
            }
        }
        return new FacebookRequestErrorClassification($r2, $r3, $r4, $r5, $r6, $r7);
    }
}
