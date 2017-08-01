package com.google.android.gms.common.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.google.android.gms.internal.zzri;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzi implements OnClickListener {

    /* compiled from: dalvik_source_com.waze.apk */
    class C07071 extends zzi {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ Intent val$intent;
        final /* synthetic */ int val$requestCode;

        C07071(Activity $r1, Intent $r2, int $i0) throws  {
            this.val$activity = $r1;
            this.val$intent = $r2;
            this.val$requestCode = $i0;
        }

        public void zzawo() throws  {
            this.val$activity.startActivityForResult(this.val$intent, this.val$requestCode);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07082 extends zzi {
        final /* synthetic */ Fragment val$fragment;
        final /* synthetic */ Intent val$intent;
        final /* synthetic */ int val$requestCode;

        C07082(Fragment $r1, Intent $r2, int $i0) throws  {
            this.val$fragment = $r1;
            this.val$intent = $r2;
            this.val$requestCode = $i0;
        }

        public void zzawo() throws  {
            this.val$fragment.startActivityForResult(this.val$intent, this.val$requestCode);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07093 extends zzi {
        final /* synthetic */ zzri Jp;
        final /* synthetic */ Intent val$intent;
        final /* synthetic */ int val$requestCode;

        C07093(zzri $r1, Intent $r2, int $i0) throws  {
            this.Jp = $r1;
            this.val$intent = $r2;
            this.val$requestCode = $i0;
        }

        @TargetApi(11)
        public void zzawo() throws  {
            this.Jp.startActivityForResult(this.val$intent, this.val$requestCode);
        }
    }

    public static zzi zza(Activity $r0, Intent $r1, int $i0) throws  {
        return new C07071($r0, $r1, $i0);
    }

    public static zzi zza(@NonNull Fragment $r0, Intent $r1, int $i0) throws  {
        return new C07082($r0, $r1, $i0);
    }

    public static zzi zza(@NonNull zzri $r0, Intent $r1, int $i0) throws  {
        return new C07093($r0, $r1, $i0);
    }

    public void onClick(DialogInterface $r1, int i) throws  {
        try {
            zzawo();
            $r1.dismiss();
        } catch (ActivityNotFoundException e) {
            Log.e("DialogRedirect", "Can't redirect to app settings for Google Play services");
        }
    }

    public abstract void zzawo() throws ;
}
