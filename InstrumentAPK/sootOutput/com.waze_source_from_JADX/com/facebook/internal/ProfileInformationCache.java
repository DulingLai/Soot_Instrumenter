package com.facebook.internal;

import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

class ProfileInformationCache {
    private static final ConcurrentHashMap<String, JSONObject> infoCache = new ConcurrentHashMap();

    ProfileInformationCache() throws  {
    }

    public static JSONObject getProfileInformation(String $r0) throws  {
        return (JSONObject) infoCache.get($r0);
    }

    public static void putProfileInformation(String $r0, JSONObject $r1) throws  {
        infoCache.put($r0, $r1);
    }
}
