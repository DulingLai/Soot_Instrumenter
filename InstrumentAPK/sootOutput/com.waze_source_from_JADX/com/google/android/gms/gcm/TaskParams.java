package com.google.android.gms.gcm;

import android.os.Bundle;

/* compiled from: dalvik_source_com.waze.apk */
public class TaskParams {
    private final Bundle extras;
    private final String tag;

    public TaskParams(String $r1) throws  {
        this($r1, null);
    }

    public TaskParams(String $r1, Bundle $r2) throws  {
        this.tag = $r1;
        this.extras = $r2;
    }

    public Bundle getExtras() throws  {
        return this.extras;
    }

    public String getTag() throws  {
        return this.tag;
    }
}
