package com.facebook.internal;

import android.annotation.SuppressLint;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class JsonUtil {

    private static final class JSONObjectEntry implements Entry<String, Object> {
        private final String key;
        private final Object value;

        JSONObjectEntry(String $r1, Object $r2) throws  {
            this.key = $r1;
            this.value = $r2;
        }

        @SuppressLint({"FieldGetter"})
        public String getKey() throws  {
            return this.key;
        }

        public Object getValue() throws  {
            return this.value;
        }

        public Object setValue(Object object) throws  {
            throw new UnsupportedOperationException("JSONObjectEntry is immutable");
        }
    }

    JsonUtil() throws  {
    }

    static void jsonObjectClear(JSONObject $r0) throws  {
        Iterator $r1 = $r0.keys();
        while ($r1.hasNext()) {
            $r1.next();
            $r1.remove();
        }
    }

    static boolean jsonObjectContainsValue(JSONObject $r0, Object $r1) throws  {
        Iterator $r2 = $r0.keys();
        while ($r2.hasNext()) {
            Object $r3 = $r0.opt((String) $r2.next());
            if ($r3 != null && $r3.equals($r1)) {
                return true;
            }
        }
        return false;
    }

    static Set<Entry<String, Object>> jsonObjectEntrySet(@Signature({"(", "Lorg/json/JSONObject;", ")", "Ljava/util/Set", "<", "Ljava/util/Map$Entry", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;>;"}) JSONObject $r0) throws  {
        HashSet $r1 = new HashSet();
        Iterator $r2 = $r0.keys();
        while ($r2.hasNext()) {
            String $r4 = (String) $r2.next();
            $r1.add(new JSONObjectEntry($r4, $r0.opt($r4)));
        }
        return $r1;
    }

    static Set<String> jsonObjectKeySet(@Signature({"(", "Lorg/json/JSONObject;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) JSONObject $r0) throws  {
        HashSet $r1 = new HashSet();
        Iterator $r2 = $r0.keys();
        while ($r2.hasNext()) {
            $r1.add($r2.next());
        }
        return $r1;
    }

    static void jsonObjectPutAll(@Signature({"(", "Lorg/json/JSONObject;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;)V"}) JSONObject $r0, @Signature({"(", "Lorg/json/JSONObject;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;)V"}) Map<String, Object> $r1) throws  {
        for (Entry $r6 : $r1.entrySet()) {
            try {
                $r0.putOpt((String) $r6.getKey(), $r6.getValue());
            } catch (JSONException $r2) {
                throw new IllegalArgumentException($r2);
            }
        }
    }

    static Collection<Object> jsonObjectValues(@Signature({"(", "Lorg/json/JSONObject;", ")", "Ljava/util/Collection", "<", "Ljava/lang/Object;", ">;"}) JSONObject $r0) throws  {
        ArrayList $r1 = new ArrayList();
        Iterator $r2 = $r0.keys();
        while ($r2.hasNext()) {
            $r1.add($r0.opt((String) $r2.next()));
        }
        return $r1;
    }
}
