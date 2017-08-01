package com.android.volley;

import java.util.Collections;
import java.util.Map;

public interface Cache {

    public static class Entry {
        public byte[] data;
        public String etag;
        public Map<String, String> responseHeaders = Collections.emptyMap();
        public long serverDate;
        public long softTtl;
        public long ttl;

        public boolean isExpired() throws  {
            return this.ttl < System.currentTimeMillis();
        }

        public boolean refreshNeeded() throws  {
            return this.softTtl < System.currentTimeMillis();
        }
    }

    void clear() throws ;

    Entry get(String str) throws ;

    void initialize() throws ;

    void invalidate(String str, boolean z) throws ;

    void put(String str, Entry entry) throws ;

    void remove(String str) throws ;
}
