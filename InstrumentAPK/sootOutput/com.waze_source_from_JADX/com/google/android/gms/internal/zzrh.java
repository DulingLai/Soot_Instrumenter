package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: dalvik_source_com.waze.apk */
public class zzrh {
    protected final zzri FM;

    protected zzrh(zzri $r1) throws  {
        this.FM = $r1;
    }

    protected static zzri zzc(zzrg $r0) throws  {
        return $r0.zzaug() ? zzrx.zzb($r0.zzaui()) : zzrj.zzu($r0.zzauh());
    }

    protected static zzri zzt(Activity $r0) throws  {
        return zzc(new zzrg($r0));
    }

    @MainThread
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) throws  {
    }

    public Activity getActivity() throws  {
        return this.FM.zzauk();
    }

    @MainThread
    public void onActivityResult(int i, int i2, Intent intent) throws  {
    }

    @MainThread
    public void onCreate(Bundle bundle) throws  {
    }

    @MainThread
    public void onSaveInstanceState(Bundle bundle) throws  {
    }

    @MainThread
    public void onStart() throws  {
    }

    @MainThread
    public void onStop() throws  {
    }
}
