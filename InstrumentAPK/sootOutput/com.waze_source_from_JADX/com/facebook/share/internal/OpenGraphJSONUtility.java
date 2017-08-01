package com.facebook.share.internal;

import android.support.annotation.Nullable;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class OpenGraphJSONUtility {

    public interface PhotoJSONProcessor {
        JSONObject toJSONObject(SharePhoto sharePhoto) throws ;
    }

    public static JSONObject toJSONObject(ShareOpenGraphAction $r0, PhotoJSONProcessor $r1) throws JSONException {
        JSONObject $r2 = new JSONObject();
        for (String $r6 : $r0.keySet()) {
            $r2.put($r6, toJSONValue($r0.get($r6), $r1));
        }
        return $r2;
    }

    private static JSONObject toJSONObject(ShareOpenGraphObject $r0, PhotoJSONProcessor $r1) throws JSONException {
        JSONObject $r2 = new JSONObject();
        for (String $r6 : $r0.keySet()) {
            $r2.put($r6, toJSONValue($r0.get($r6), $r1));
        }
        return $r2;
    }

    private static JSONArray toJSONArray(List $r0, PhotoJSONProcessor $r1) throws JSONException {
        JSONArray $r2 = new JSONArray();
        for (Object $r4 : $r0) {
            $r2.put(toJSONValue($r4, $r1));
        }
        return $r2;
    }

    public static Object toJSONValue(@Nullable Object $r1, PhotoJSONProcessor $r0) throws JSONException {
        if ($r1 == null) {
            return JSONObject.NULL;
        }
        if (($r1 instanceof String) || ($r1 instanceof Boolean) || ($r1 instanceof Double) || ($r1 instanceof Float) || ($r1 instanceof Integer) || ($r1 instanceof Long)) {
            return $r1;
        }
        if ($r1 instanceof SharePhoto) {
            return $r0 != null ? $r0.toJSONObject((SharePhoto) $r1) : null;
        } else {
            if ($r1 instanceof ShareOpenGraphObject) {
                return toJSONObject((ShareOpenGraphObject) $r1, $r0);
            }
            if ($r1 instanceof List) {
                return toJSONArray((List) $r1, $r0);
            }
            throw new IllegalArgumentException("Invalid object found for JSON serialization: " + $r1.toString());
        }
    }

    private OpenGraphJSONUtility() throws  {
    }
}
