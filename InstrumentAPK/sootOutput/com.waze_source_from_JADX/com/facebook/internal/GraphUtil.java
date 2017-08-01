package com.facebook.internal;

import com.facebook.FacebookException;
import org.json.JSONArray;
import org.json.JSONObject;

public class GraphUtil {
    private static final String[] dateFormats = new String[]{"yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd"};

    public static JSONObject createOpenGraphActionForPost(String $r0) throws  {
        JSONObject $r1 = new JSONObject();
        if ($r0 == null) {
            return $r1;
        }
        try {
            $r1.put("type", $r0);
            return $r1;
        } catch (Throwable $r2) {
            throw new FacebookException("An error occurred while setting up the open graph action", $r2);
        }
    }

    public static JSONObject createOpenGraphObjectForPost(String $r0) throws  {
        return createOpenGraphObjectForPost($r0, null, null, null, null, null, null);
    }

    public static JSONObject createOpenGraphObjectForPost(String $r0, String $r1, String $r2, String $r3, String $r4, JSONObject $r5, String $r6) throws  {
        JSONObject $r10 = new JSONObject();
        if ($r0 != null) {
            try {
                $r10.put("type", $r0);
            } catch (Throwable $r7) {
                throw new FacebookException("An error occurred while setting up the graph object", $r7);
            }
        }
        $r10.put("title", $r1);
        if ($r2 != null) {
            JSONObject $r8 = new JSONObject();
            $r8.put("url", $r2);
            JSONArray $r9 = new JSONArray();
            $r9.put($r8);
            $r10.put("image", $r9);
        }
        $r10.put("url", $r3);
        $r10.put("description", $r4);
        $r10.put(NativeProtocol.OPEN_GRAPH_CREATE_OBJECT_KEY, true);
        if ($r5 != null) {
            $r10.put("data", $r5);
        }
        if ($r6 == null) {
            return $r10;
        }
        $r10.put("id", $r6);
        return $r10;
    }

    public static boolean isOpenGraphObjectForPost(JSONObject $r0) throws  {
        return $r0 != null ? $r0.optBoolean(NativeProtocol.OPEN_GRAPH_CREATE_OBJECT_KEY) : false;
    }
}
