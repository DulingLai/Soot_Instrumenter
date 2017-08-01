package com.android.volley.toolbox;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;

public class NoCache implements Cache {
    public Entry get(String key) throws  {
        return null;
    }

    public void clear() throws  {
    }

    public void put(String key, Entry entry) throws  {
    }

    public void invalidate(String key, boolean fullExpire) throws  {
    }

    public void remove(String key) throws  {
    }

    public void initialize() throws  {
    }
}
