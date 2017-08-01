package com.google.android.gms.people.internal;

import android.text.TextUtils;

/* compiled from: dalvik_source_com.waze.apk */
public class zzt {
    private final StringBuilder aRk = new StringBuilder();
    private boolean aRl = false;

    public String toString() throws  {
        return this.aRk.toString();
    }

    public void zzri(String $r1) throws  {
        if (!TextUtils.isEmpty($r1)) {
            this.aRk.append($r1);
        }
    }

    public void zzrj(String $r1) throws  {
        if (!TextUtils.isEmpty($r1)) {
            if (this.aRl) {
                this.aRk.append(" AND ");
            }
            this.aRk.append($r1);
            this.aRl = true;
        }
    }
}
