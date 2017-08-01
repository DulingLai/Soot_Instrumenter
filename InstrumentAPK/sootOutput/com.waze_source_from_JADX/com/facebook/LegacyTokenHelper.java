package com.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

final class LegacyTokenHelper {
    public static final String APPLICATION_ID_KEY = "com.facebook.TokenCachingStrategy.ApplicationId";
    public static final String DECLINED_PERMISSIONS_KEY = "com.facebook.TokenCachingStrategy.DeclinedPermissions";
    public static final String DEFAULT_CACHE_KEY = "com.facebook.SharedPreferencesTokenCachingStrategy.DEFAULT_KEY";
    public static final String EXPIRATION_DATE_KEY = "com.facebook.TokenCachingStrategy.ExpirationDate";
    private static final long INVALID_BUNDLE_MILLISECONDS = Long.MIN_VALUE;
    private static final String IS_SSO_KEY = "com.facebook.TokenCachingStrategy.IsSSO";
    private static final String JSON_VALUE = "value";
    private static final String JSON_VALUE_ENUM_TYPE = "enumType";
    private static final String JSON_VALUE_TYPE = "valueType";
    public static final String LAST_REFRESH_DATE_KEY = "com.facebook.TokenCachingStrategy.LastRefreshDate";
    public static final String PERMISSIONS_KEY = "com.facebook.TokenCachingStrategy.Permissions";
    private static final String TAG = LegacyTokenHelper.class.getSimpleName();
    public static final String TOKEN_KEY = "com.facebook.TokenCachingStrategy.Token";
    public static final String TOKEN_SOURCE_KEY = "com.facebook.TokenCachingStrategy.AccessTokenSource";
    private static final String TYPE_BOOLEAN = "bool";
    private static final String TYPE_BOOLEAN_ARRAY = "bool[]";
    private static final String TYPE_BYTE = "byte";
    private static final String TYPE_BYTE_ARRAY = "byte[]";
    private static final String TYPE_CHAR = "char";
    private static final String TYPE_CHAR_ARRAY = "char[]";
    private static final String TYPE_DOUBLE = "double";
    private static final String TYPE_DOUBLE_ARRAY = "double[]";
    private static final String TYPE_ENUM = "enum";
    private static final String TYPE_FLOAT = "float";
    private static final String TYPE_FLOAT_ARRAY = "float[]";
    private static final String TYPE_INTEGER = "int";
    private static final String TYPE_INTEGER_ARRAY = "int[]";
    private static final String TYPE_LONG = "long";
    private static final String TYPE_LONG_ARRAY = "long[]";
    private static final String TYPE_SHORT = "short";
    private static final String TYPE_SHORT_ARRAY = "short[]";
    private static final String TYPE_STRING = "string";
    private static final String TYPE_STRING_LIST = "stringList";
    private SharedPreferences cache;
    private String cacheKey;

    private void serializeKey(java.lang.String r57, android.os.Bundle r58, android.content.SharedPreferences.Editor r59) throws org.json.JSONException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:29:0x009a
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
        r56 = this;
        r3 = 0;
        r0 = r58;
        r1 = r57;
        r4 = r0.get(r1);
        if (r4 != 0) goto L_0x000c;
    L_0x000b:
        return;
    L_0x000c:
        r5 = 0;
        r6 = 0;
        r7 = new org.json.JSONObject;
        r7.<init>();
        r8 = r4 instanceof java.lang.Byte;
        if (r8 == 0) goto L_0x0043;
    L_0x0017:
        r5 = "byte";
        r10 = r4;
        r10 = (java.lang.Byte) r10;
        r9 = r10;
        r3 = r9.intValue();
        r11 = "value";
        r7.put(r11, r3);
    L_0x0027:
        if (r5 == 0) goto L_0x0283;
    L_0x0029:
        r11 = "valueType";
        r7.put(r11, r5);
        if (r6 == 0) goto L_0x0037;
    L_0x0031:
        r11 = "value";
        r7.putOpt(r11, r6);
    L_0x0037:
        r5 = r7.toString();
        r0 = r59;
        r1 = r57;
        r0.putString(r1, r5);
        return;
    L_0x0043:
        r8 = r4 instanceof java.lang.Short;
        if (r8 == 0) goto L_0x0058;
    L_0x0047:
        r5 = "short";
        r13 = r4;
        r13 = (java.lang.Short) r13;
        r12 = r13;
        r3 = r12.intValue();
        r11 = "value";
        r7.put(r11, r3);
        goto L_0x0027;
    L_0x0058:
        r8 = r4 instanceof java.lang.Integer;
        if (r8 == 0) goto L_0x0073;
    L_0x005c:
        r5 = "int";
        r15 = r4;
        r15 = (java.lang.Integer) r15;
        r14 = r15;
        r3 = r14.intValue();
        goto L_0x006c;
    L_0x0069:
        goto L_0x0027;
    L_0x006c:
        r11 = "value";
        r7.put(r11, r3);
        goto L_0x0027;
    L_0x0073:
        r8 = r4 instanceof java.lang.Long;
        if (r8 == 0) goto L_0x009e;
    L_0x0077:
        goto L_0x007b;
    L_0x0078:
        goto L_0x0027;
    L_0x007b:
        r5 = "long";
        r17 = r4;
        r17 = (java.lang.Long) r17;
        r16 = r17;
        goto L_0x0087;
    L_0x0084:
        goto L_0x0027;
    L_0x0087:
        r0 = r16;
        r18 = r0.longValue();
        goto L_0x0091;
    L_0x008e:
        goto L_0x0027;
    L_0x0091:
        r11 = "value";
        r0 = r18;
        r7.put(r11, r0);
        goto L_0x0027;
        goto L_0x009e;
    L_0x009b:
        goto L_0x0027;
    L_0x009e:
        r8 = r4 instanceof java.lang.Float;
        if (r8 == 0) goto L_0x00b9;
    L_0x00a2:
        r5 = "float";
        r21 = r4;
        r21 = (java.lang.Float) r21;
        r20 = r21;
        r0 = r20;
        r22 = r0.doubleValue();
        r11 = "value";
        r0 = r22;
        r7.put(r11, r0);
        goto L_0x0069;
    L_0x00b9:
        r8 = r4 instanceof java.lang.Double;
        if (r8 == 0) goto L_0x00d4;
    L_0x00bd:
        r5 = "double";
        r25 = r4;
        r25 = (java.lang.Double) r25;
        r24 = r25;
        r0 = r24;
        r22 = r0.doubleValue();
        r11 = "value";
        r0 = r22;
        r7.put(r11, r0);
        goto L_0x0078;
    L_0x00d4:
        r8 = r4 instanceof java.lang.Boolean;
        if (r8 == 0) goto L_0x00ed;
    L_0x00d8:
        r5 = "bool";
        r27 = r4;
        r27 = (java.lang.Boolean) r27;
        r26 = r27;
        r0 = r26;
        r8 = r0.booleanValue();
        r11 = "value";
        r7.put(r11, r8);
        goto L_0x0084;
    L_0x00ed:
        r8 = r4 instanceof java.lang.Character;
        if (r8 == 0) goto L_0x0100;
    L_0x00f1:
        r5 = "char";
        r28 = r4.toString();
        r11 = "value";
        r0 = r28;
        r7.put(r11, r0);
        goto L_0x008e;
    L_0x0100:
        r8 = r4 instanceof java.lang.String;
        if (r8 == 0) goto L_0x0116;
    L_0x0104:
        r5 = "string";
        r29 = r4;
        r29 = (java.lang.String) r29;
        r28 = r29;
        r11 = "value";
        r0 = r28;
        r7.put(r11, r0);
        goto L_0x009b;
    L_0x0116:
        r8 = r4 instanceof java.lang.Enum;
        if (r8 == 0) goto L_0x013e;
    L_0x011a:
        r5 = "enum";
        r28 = r4.toString();
        r11 = "value";
        r0 = r28;
        r7.put(r11, r0);
        r30 = r4.getClass();
        r0 = r30;
        r28 = r0.getName();
        goto L_0x0136;
    L_0x0133:
        goto L_0x0027;
    L_0x0136:
        r11 = "enumType";
        r0 = r28;
        r7.put(r11, r0);
        goto L_0x0133;
    L_0x013e:
        r6 = new org.json.JSONArray;
        r6.<init>();
        r8 = r4 instanceof byte[];
        if (r8 == 0) goto L_0x0164;
    L_0x0147:
        r5 = "byte[]";
        r32 = r4;
        r32 = (byte[]) r32;
        r31 = r32;
        r31 = (byte[]) r31;
        r0 = r31;
        r0 = r0.length;
        r33 = r0;
    L_0x0156:
        r0 = r33;
        if (r3 >= r0) goto L_0x0027;
    L_0x015a:
        r34 = r31[r3];
        r0 = r34;
        r6.put(r0);
        r3 = r3 + 1;
        goto L_0x0156;
    L_0x0164:
        r8 = r4 instanceof short[];
        if (r8 == 0) goto L_0x0185;
    L_0x0168:
        r5 = "short[]";
        r36 = r4;
        r36 = (short[]) r36;
        r35 = r36;
        r35 = (short[]) r35;
        r0 = r35;
        r0 = r0.length;
        r33 = r0;
    L_0x0177:
        r0 = r33;
        if (r3 >= r0) goto L_0x0027;
    L_0x017b:
        r37 = r35[r3];
        r0 = r37;
        r6.put(r0);
        r3 = r3 + 1;
        goto L_0x0177;
    L_0x0185:
        r8 = r4 instanceof int[];
        if (r8 == 0) goto L_0x01a6;
    L_0x0189:
        r5 = "int[]";
        r39 = r4;
        r39 = (int[]) r39;
        r38 = r39;
        r38 = (int[]) r38;
        r0 = r38;
        r0 = r0.length;
        r33 = r0;
    L_0x0198:
        r0 = r33;
        if (r3 >= r0) goto L_0x0027;
    L_0x019c:
        r40 = r38[r3];
        r0 = r40;
        r6.put(r0);
        r3 = r3 + 1;
        goto L_0x0198;
    L_0x01a6:
        r8 = r4 instanceof long[];
        if (r8 == 0) goto L_0x01c7;
    L_0x01aa:
        r5 = "long[]";
        r42 = r4;
        r42 = (long[]) r42;
        r41 = r42;
        r41 = (long[]) r41;
        r0 = r41;
        r0 = r0.length;
        r33 = r0;
    L_0x01b9:
        r0 = r33;
        if (r3 >= r0) goto L_0x0027;
    L_0x01bd:
        r18 = r41[r3];
        r0 = r18;
        r6.put(r0);
        r3 = r3 + 1;
        goto L_0x01b9;
    L_0x01c7:
        r8 = r4 instanceof float[];
        if (r8 == 0) goto L_0x01eb;
    L_0x01cb:
        r5 = "float[]";
        r44 = r4;
        r44 = (float[]) r44;
        r43 = r44;
        r43 = (float[]) r43;
        r0 = r43;
        r0 = r0.length;
        r33 = r0;
    L_0x01da:
        r0 = r33;
        if (r3 >= r0) goto L_0x0027;
    L_0x01de:
        r45 = r43[r3];
        r0 = r45;
        r0 = (double) r0;
        r22 = r0;
        r6.put(r0);
        r3 = r3 + 1;
        goto L_0x01da;
    L_0x01eb:
        r8 = r4 instanceof double[];
        if (r8 == 0) goto L_0x020c;
    L_0x01ef:
        r5 = "double[]";
        r47 = r4;
        r47 = (double[]) r47;
        r46 = r47;
        r46 = (double[]) r46;
        r0 = r46;
        r0 = r0.length;
        r33 = r0;
    L_0x01fe:
        r0 = r33;
        if (r3 >= r0) goto L_0x0027;
    L_0x0202:
        r22 = r46[r3];
        r0 = r22;
        r6.put(r0);
        r3 = r3 + 1;
        goto L_0x01fe;
    L_0x020c:
        r8 = r4 instanceof boolean[];
        if (r8 == 0) goto L_0x022b;
    L_0x0210:
        r5 = "bool[]";
        r49 = r4;
        r49 = (boolean[]) r49;
        r48 = r49;
        r48 = (boolean[]) r48;
        r0 = r48;
        r0 = r0.length;
        r33 = r0;
    L_0x021f:
        r0 = r33;
        if (r3 >= r0) goto L_0x0027;
    L_0x0223:
        r8 = r48[r3];
        r6.put(r8);
        r3 = r3 + 1;
        goto L_0x021f;
    L_0x022b:
        r8 = r4 instanceof char[];
        if (r8 == 0) goto L_0x0252;
    L_0x022f:
        r5 = "char[]";
        r51 = r4;
        r51 = (char[]) r51;
        r50 = r51;
        r50 = (char[]) r50;
        r0 = r50;
        r0 = r0.length;
        r33 = r0;
    L_0x023e:
        r0 = r33;
        if (r3 >= r0) goto L_0x0027;
    L_0x0242:
        r52 = r50[r3];
        r0 = r52;
        r28 = java.lang.String.valueOf(r0);
        r0 = r28;
        r6.put(r0);
        r3 = r3 + 1;
        goto L_0x023e;
    L_0x0252:
        r8 = r4 instanceof java.util.List;
        if (r8 == 0) goto L_0x0281;
    L_0x0256:
        r5 = "stringList";
        r54 = r4;
        r54 = (java.util.List) r54;
        r53 = r54;
        r0 = r53;
        r55 = r0.iterator();
    L_0x0265:
        r0 = r55;
        r8 = r0.hasNext();
        if (r8 == 0) goto L_0x0027;
    L_0x026d:
        r0 = r55;
        r4 = r0.next();
        r4 = (java.lang.String) r4;
        if (r4 != 0) goto L_0x0279;
    L_0x0277:
        r4 = org.json.JSONObject.NULL;
    L_0x0279:
        r6.put(r4);
        goto L_0x0265;
        goto L_0x0281;
    L_0x027e:
        goto L_0x0027;
    L_0x0281:
        r6 = 0;
        goto L_0x027e;
    L_0x0283:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.LegacyTokenHelper.serializeKey(java.lang.String, android.os.Bundle, android.content.SharedPreferences$Editor):void");
    }

    public LegacyTokenHelper(Context $r1) throws  {
        this($r1, null);
    }

    public LegacyTokenHelper(Context $r1, String $r2) throws  {
        Validate.notNull($r1, "context");
        if (Utility.isNullOrEmpty($r2)) {
            $r2 = DEFAULT_CACHE_KEY;
        }
        this.cacheKey = $r2;
        Context $r3 = $r1.getApplicationContext();
        if ($r3 != null) {
            $r1 = $r3;
        }
        this.cache = $r1.getSharedPreferences(this.cacheKey, 0);
    }

    public Bundle load() throws  {
        Bundle $r3 = new Bundle();
        for (String $r9 : this.cache.getAll().keySet()) {
            try {
                deserializeKey($r9, $r3);
            } catch (JSONException $r1) {
                Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error reading cached value for key: '" + $r9 + "' -- " + $r1);
                return null;
            }
        }
        return $r3;
    }

    public void save(Bundle $r1) throws  {
        Validate.notNull($r1, "bundle");
        Editor $r5 = this.cache.edit();
        for (String $r9 : $r1.keySet()) {
            try {
                serializeKey($r9, $r1, $r5);
            } catch (JSONException $r2) {
                Logger.log(LoggingBehavior.CACHE, 5, TAG, "Error processing value for key: '" + $r9 + "' -- " + $r2);
                return;
            }
        }
        $r5.apply();
    }

    public void clear() throws  {
        this.cache.edit().clear().apply();
    }

    public static boolean hasTokenInformation(Bundle $r0) throws  {
        if ($r0 == null) {
            return false;
        }
        String $r1 = $r0.getString(TOKEN_KEY);
        if ($r1 == null) {
            return false;
        }
        if ($r1.length() != 0) {
            return $r0.getLong(EXPIRATION_DATE_KEY, 0) != 0;
        } else {
            return false;
        }
    }

    public static String getToken(Bundle $r0) throws  {
        Validate.notNull($r0, "bundle");
        return $r0.getString(TOKEN_KEY);
    }

    public static void putToken(Bundle $r0, String $r1) throws  {
        Validate.notNull($r0, "bundle");
        Validate.notNull($r1, JSON_VALUE);
        $r0.putString(TOKEN_KEY, $r1);
    }

    public static Date getExpirationDate(Bundle $r0) throws  {
        Validate.notNull($r0, "bundle");
        return getDate($r0, EXPIRATION_DATE_KEY);
    }

    public static void putExpirationDate(Bundle $r0, Date $r1) throws  {
        Validate.notNull($r0, "bundle");
        Validate.notNull($r1, JSON_VALUE);
        putDate($r0, EXPIRATION_DATE_KEY, $r1);
    }

    public static long getExpirationMilliseconds(Bundle $r0) throws  {
        Validate.notNull($r0, "bundle");
        return $r0.getLong(EXPIRATION_DATE_KEY);
    }

    public static void putExpirationMilliseconds(Bundle $r0, long $l0) throws  {
        Validate.notNull($r0, "bundle");
        $r0.putLong(EXPIRATION_DATE_KEY, $l0);
    }

    public static Set<String> getPermissions(@Signature({"(", "Landroid/os/Bundle;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) Bundle $r0) throws  {
        Validate.notNull($r0, "bundle");
        ArrayList $r1 = $r0.getStringArrayList(PERMISSIONS_KEY);
        return $r1 == null ? null : new HashSet($r1);
    }

    public static void putPermissions(@Signature({"(", "Landroid/os/Bundle;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Bundle $r0, @Signature({"(", "Landroid/os/Bundle;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Collection<String> $r1) throws  {
        Validate.notNull($r0, "bundle");
        Validate.notNull($r1, JSON_VALUE);
        $r0.putStringArrayList(PERMISSIONS_KEY, new ArrayList($r1));
    }

    public static void putDeclinedPermissions(@Signature({"(", "Landroid/os/Bundle;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Bundle $r0, @Signature({"(", "Landroid/os/Bundle;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)V"}) Collection<String> $r1) throws  {
        Validate.notNull($r0, "bundle");
        Validate.notNull($r1, JSON_VALUE);
        $r0.putStringArrayList(DECLINED_PERMISSIONS_KEY, new ArrayList($r1));
    }

    public static AccessTokenSource getSource(Bundle $r0) throws  {
        Validate.notNull($r0, "bundle");
        if ($r0.containsKey(TOKEN_SOURCE_KEY)) {
            return (AccessTokenSource) $r0.getSerializable(TOKEN_SOURCE_KEY);
        }
        return $r0.getBoolean(IS_SSO_KEY) ? AccessTokenSource.FACEBOOK_APPLICATION_WEB : AccessTokenSource.WEB_VIEW;
    }

    public static void putSource(Bundle $r0, AccessTokenSource $r1) throws  {
        Validate.notNull($r0, "bundle");
        $r0.putSerializable(TOKEN_SOURCE_KEY, $r1);
    }

    public static Date getLastRefreshDate(Bundle $r0) throws  {
        Validate.notNull($r0, "bundle");
        return getDate($r0, LAST_REFRESH_DATE_KEY);
    }

    public static void putLastRefreshDate(Bundle $r0, Date $r1) throws  {
        Validate.notNull($r0, "bundle");
        Validate.notNull($r1, JSON_VALUE);
        putDate($r0, LAST_REFRESH_DATE_KEY, $r1);
    }

    public static long getLastRefreshMilliseconds(Bundle $r0) throws  {
        Validate.notNull($r0, "bundle");
        return $r0.getLong(LAST_REFRESH_DATE_KEY);
    }

    public static void putLastRefreshMilliseconds(Bundle $r0, long $l0) throws  {
        Validate.notNull($r0, "bundle");
        $r0.putLong(LAST_REFRESH_DATE_KEY, $l0);
    }

    public static String getApplicationId(Bundle $r0) throws  {
        Validate.notNull($r0, "bundle");
        return $r0.getString(APPLICATION_ID_KEY);
    }

    public static void putApplicationId(Bundle $r0, String $r1) throws  {
        Validate.notNull($r0, "bundle");
        $r0.putString(APPLICATION_ID_KEY, $r1);
    }

    static Date getDate(Bundle $r0, String $r1) throws  {
        if ($r0 == null) {
            return null;
        }
        long $l1 = $r0.getLong($r1, INVALID_BUNDLE_MILLISECONDS);
        return $l1 != INVALID_BUNDLE_MILLISECONDS ? new Date($l1) : null;
    }

    static void putDate(Bundle $r0, String $r1, Date $r2) throws  {
        $r0.putLong($r1, $r2.getTime());
    }

    private void deserializeKey(String $r1, Bundle $r2) throws JSONException {
        JSONObject $r3 = new JSONObject(this.cache.getString($r1, "{}"));
        String $r6 = $r3.getString(JSON_VALUE_TYPE);
        if ($r6.equals(TYPE_BOOLEAN)) {
            $r2.putBoolean($r1, $r3.getBoolean(JSON_VALUE));
        } else if ($r6.equals(TYPE_BOOLEAN_ARRAY)) {
            $r7 = $r3.getJSONArray(JSON_VALUE);
            boolean[] $r8 = new boolean[$r7.length()];
            for ($i0 = 0; $i0 < $r8.length; $i0++) {
                $r8[$i0] = $r7.getBoolean($i0);
            }
            $r2.putBooleanArray($r1, $r8);
        } else if ($r6.equals(TYPE_BYTE)) {
            $r2.putByte($r1, (byte) $r3.getInt(JSON_VALUE));
        } else if ($r6.equals(TYPE_BYTE_ARRAY)) {
            $r7 = $r3.getJSONArray(JSON_VALUE);
            byte[] $r9 = new byte[$r7.length()];
            for ($i0 = 0; $i0 < $r9.length; $i0++) {
                $r9[$i0] = (byte) $r7.getInt($i0);
            }
            $r2.putByteArray($r1, $r9);
        } else if ($r6.equals(TYPE_SHORT)) {
            $r2.putShort($r1, (short) $r3.getInt(JSON_VALUE));
        } else if ($r6.equals(TYPE_SHORT_ARRAY)) {
            $r7 = $r3.getJSONArray(JSON_VALUE);
            short[] $r10 = new short[$r7.length()];
            for ($i0 = 0; $i0 < $r10.length; $i0++) {
                $r10[$i0] = (short) $r7.getInt($i0);
            }
            $r2.putShortArray($r1, $r10);
        } else if ($r6.equals(TYPE_INTEGER)) {
            $r2.putInt($r1, $r3.getInt(JSON_VALUE));
        } else if ($r6.equals(TYPE_INTEGER_ARRAY)) {
            $r7 = $r3.getJSONArray(JSON_VALUE);
            int[] $r11 = new int[$r7.length()];
            for ($i0 = 0; $i0 < $r11.length; $i0++) {
                $r11[$i0] = $r7.getInt($i0);
            }
            $r2.putIntArray($r1, $r11);
        } else if ($r6.equals(TYPE_LONG)) {
            $r2.putLong($r1, $r3.getLong(JSON_VALUE));
        } else if ($r6.equals(TYPE_LONG_ARRAY)) {
            $r7 = $r3.getJSONArray(JSON_VALUE);
            long[] $r12 = new long[$r7.length()];
            for ($i0 = 0; $i0 < $r12.length; $i0++) {
                $r12[$i0] = $r7.getLong($i0);
            }
            $r2.putLongArray($r1, $r12);
        } else if ($r6.equals(TYPE_FLOAT)) {
            $r2.putFloat($r1, (float) $r3.getDouble(JSON_VALUE));
        } else if ($r6.equals(TYPE_FLOAT_ARRAY)) {
            $r7 = $r3.getJSONArray(JSON_VALUE);
            float[] $r13 = new float[$r7.length()];
            for ($i0 = 0; $i0 < $r13.length; $i0++) {
                $r13[$i0] = (float) $r7.getDouble($i0);
            }
            $r2.putFloatArray($r1, $r13);
        } else if ($r6.equals(TYPE_DOUBLE)) {
            $r2.putDouble($r1, $r3.getDouble(JSON_VALUE));
        } else if ($r6.equals(TYPE_DOUBLE_ARRAY)) {
            $r7 = $r3.getJSONArray(JSON_VALUE);
            double[] $r14 = new double[$r7.length()];
            for ($i0 = 0; $i0 < $r14.length; $i0++) {
                $r14[$i0] = $r7.getDouble($i0);
            }
            $r2.putDoubleArray($r1, $r14);
        } else if ($r6.equals(TYPE_CHAR)) {
            $r6 = $r3.getString(JSON_VALUE);
            if ($r6 != null && $r6.length() == 1) {
                $r2.putChar($r1, $r6.charAt(0));
            }
        } else if ($r6.equals(TYPE_CHAR_ARRAY)) {
            $r7 = $r3.getJSONArray(JSON_VALUE);
            char[] $r15 = new char[$r7.length()];
            for ($i0 = 0; $i0 < $r15.length; $i0++) {
                $r6 = $r7.getString($i0);
                if ($r6 != null && $r6.length() == 1) {
                    $r15[$i0] = $r6.charAt(0);
                }
            }
            $r2.putCharArray($r1, $r15);
        } else if ($r6.equals(TYPE_STRING)) {
            $r2.putString($r1, $r3.getString(JSON_VALUE));
        } else if ($r6.equals(TYPE_STRING_LIST)) {
            $r7 = $r3.getJSONArray(JSON_VALUE);
            $i0 = $r7.length();
            ArrayList arrayList = new ArrayList($i0);
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                Object $r16 = $r7.get($i1);
                arrayList.add($i1, $r16 == JSONObject.NULL ? null : (String) $r16);
            }
            $r2.putStringArrayList($r1, arrayList);
        } else if ($r6.equals(TYPE_ENUM)) {
            try {
                $r2.putSerializable($r1, Enum.valueOf(Class.forName($r3.getString(JSON_VALUE_ENUM_TYPE)), $r3.getString(JSON_VALUE)));
            } catch (ClassNotFoundException e) {
            } catch (IllegalArgumentException e2) {
            }
        }
    }
}
