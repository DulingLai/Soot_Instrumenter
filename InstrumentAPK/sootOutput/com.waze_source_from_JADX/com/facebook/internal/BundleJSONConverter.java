package com.facebook.internal;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BundleJSONConverter {
    private static final Map<Class<?>, Setter> SETTERS = new HashMap();

    public interface Setter {
        void setOnBundle(Bundle bundle, String str, Object obj) throws JSONException;

        void setOnJSON(JSONObject jSONObject, String str, Object obj) throws JSONException;
    }

    static class C05121 implements Setter {
        C05121() throws  {
        }

        public void setOnBundle(Bundle $r1, String $r2, Object $r3) throws JSONException {
            $r1.putBoolean($r2, ((Boolean) $r3).booleanValue());
        }

        public void setOnJSON(JSONObject $r1, String $r2, Object $r3) throws JSONException {
            $r1.put($r2, $r3);
        }
    }

    static class C05132 implements Setter {
        C05132() throws  {
        }

        public void setOnBundle(Bundle $r1, String $r2, Object $r3) throws JSONException {
            $r1.putInt($r2, ((Integer) $r3).intValue());
        }

        public void setOnJSON(JSONObject $r1, String $r2, Object $r3) throws JSONException {
            $r1.put($r2, $r3);
        }
    }

    static class C05143 implements Setter {
        C05143() throws  {
        }

        public void setOnBundle(Bundle $r1, String $r2, Object $r3) throws JSONException {
            $r1.putLong($r2, ((Long) $r3).longValue());
        }

        public void setOnJSON(JSONObject $r1, String $r2, Object $r3) throws JSONException {
            $r1.put($r2, $r3);
        }
    }

    static class C05154 implements Setter {
        C05154() throws  {
        }

        public void setOnBundle(Bundle $r1, String $r2, Object $r3) throws JSONException {
            $r1.putDouble($r2, ((Double) $r3).doubleValue());
        }

        public void setOnJSON(JSONObject $r1, String $r2, Object $r3) throws JSONException {
            $r1.put($r2, $r3);
        }
    }

    static class C05165 implements Setter {
        C05165() throws  {
        }

        public void setOnBundle(Bundle $r1, String $r2, Object $r3) throws JSONException {
            $r1.putString($r2, (String) $r3);
        }

        public void setOnJSON(JSONObject $r1, String $r2, Object $r3) throws JSONException {
            $r1.put($r2, $r3);
        }
    }

    static class C05176 implements Setter {
        C05176() throws  {
        }

        public void setOnBundle(Bundle bundle, String key, Object value) throws JSONException {
            throw new IllegalArgumentException("Unexpected type from JSON");
        }

        public void setOnJSON(JSONObject $r1, String $r2, Object $r5) throws JSONException {
            JSONArray $r3 = new JSONArray();
            for (String $r4 : (String[]) $r5) {
                $r3.put($r4);
            }
            $r1.put($r2, $r3);
        }
    }

    static class C05187 implements Setter {
        C05187() throws  {
        }

        public void setOnBundle(Bundle $r1, String $r2, Object $r3) throws JSONException {
            JSONArray $r5 = (JSONArray) $r3;
            ArrayList $r4 = new ArrayList();
            if ($r5.length() == 0) {
                $r1.putStringArrayList($r2, $r4);
                return;
            }
            int $i0 = 0;
            while ($i0 < $r5.length()) {
                $r3 = $r5.get($i0);
                if ($r3 instanceof String) {
                    $r4.add((String) $r3);
                    $i0++;
                } else {
                    throw new IllegalArgumentException("Unexpected type in an array: " + $r3.getClass());
                }
            }
            $r1.putStringArrayList($r2, $r4);
        }

        public void setOnJSON(JSONObject json, String key, Object value) throws JSONException {
            throw new IllegalArgumentException("JSONArray's are not supported in bundles.");
        }
    }

    static {
        SETTERS.put(Boolean.class, new C05121());
        SETTERS.put(Integer.class, new C05132());
        SETTERS.put(Long.class, new C05143());
        SETTERS.put(Double.class, new C05154());
        SETTERS.put(String.class, new C05165());
        SETTERS.put(String[].class, new C05176());
        SETTERS.put(JSONArray.class, new C05187());
    }

    public static JSONObject convertToJSON(Bundle $r0) throws JSONException {
        JSONObject $r1 = new JSONObject();
        for (String $r6 : $r0.keySet()) {
            Object $r5 = $r0.get($r6);
            if ($r5 != null) {
                if ($r5 instanceof List) {
                    JSONArray $r2 = new JSONArray();
                    for (String put : (List) $r5) {
                        $r2.put(put);
                    }
                    $r1.put($r6, $r2);
                } else if ($r5 instanceof Bundle) {
                    $r1.put($r6, convertToJSON((Bundle) $r5));
                } else {
                    Setter $r15 = (Setter) SETTERS.get($r5.getClass());
                    if ($r15 == null) {
                        throw new IllegalArgumentException("Unsupported type: " + $r5.getClass());
                    }
                    $r15.setOnJSON($r1, $r6, $r5);
                }
            }
        }
        return $r1;
    }

    public static Bundle convertToBundle(JSONObject $r0) throws JSONException {
        Bundle $r1 = new Bundle();
        Iterator $r2 = $r0.keys();
        while ($r2.hasNext()) {
            String $r4 = (String) $r2.next();
            Object $r3 = $r0.get($r4);
            if (!($r3 == null || $r3 == JSONObject.NULL)) {
                if ($r3 instanceof JSONObject) {
                    $r1.putBundle($r4, convertToBundle((JSONObject) $r3));
                } else {
                    Setter $r10 = (Setter) SETTERS.get($r3.getClass());
                    if ($r10 == null) {
                        throw new IllegalArgumentException("Unsupported type: " + $r3.getClass());
                    }
                    $r10.setOnBundle($r1, $r4, $r3);
                }
            }
        }
        return $r1;
    }
}
