package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import android.util.Log;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class ResultCallbacks<R extends Result> implements ResultCallback<R> {
    public abstract void onFailure(@NonNull Status status) throws ;

    public final void onResult(@NonNull @Signature({"(TR;)V"}) R $r1) throws  {
        Status $r2 = $r1.getStatus();
        if ($r2.isSuccess()) {
            onSuccess($r1);
            return;
        }
        onFailure($r2);
        if ($r1 instanceof Releasable) {
            try {
                ((Releasable) $r1).release();
            } catch (RuntimeException $r4) {
                String $r5 = String.valueOf($r1);
                Log.w("ResultCallbacks", new StringBuilder(String.valueOf($r5).length() + 18).append("Unable to release ").append($r5).toString(), $r4);
            }
        }
    }

    public abstract void onSuccess(@NonNull @Signature({"(TR;)V"}) R r) throws ;
}
