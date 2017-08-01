package com.google.android.gms.common.api;

import android.app.Activity;
import android.content.IntentSender.SendIntentException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.internal.zzab;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class ResolvingResultCallbacks<R extends Result> extends ResultCallbacks<R> {
    private final int CN;
    private final Activity mActivity;

    protected ResolvingResultCallbacks(@NonNull Activity $r1, int $i0) throws  {
        this.mActivity = (Activity) zzab.zzb((Object) $r1, (Object) "Activity must not be null");
        this.CN = $i0;
    }

    public final void onFailure(@NonNull Status $r1) throws  {
        if ($r1.hasResolution()) {
            try {
                $r1.startResolutionForResult(this.mActivity, this.CN);
                return;
            } catch (SendIntentException $r3) {
                Log.e("ResolvingResultCallback", "Failed to start resolution", $r3);
                onUnresolvableFailure(new Status(8));
                return;
            }
        }
        onUnresolvableFailure($r1);
    }

    public abstract void onSuccess(@NonNull @Signature({"(TR;)V"}) R r) throws ;

    public abstract void onUnresolvableFailure(@NonNull Status status) throws ;
}
