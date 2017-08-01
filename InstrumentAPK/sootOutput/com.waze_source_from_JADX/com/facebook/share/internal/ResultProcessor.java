package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.AppCall;

public abstract class ResultProcessor {
    private FacebookCallback appCallback;

    public abstract void onSuccess(AppCall appCall, Bundle bundle) throws ;

    public ResultProcessor(FacebookCallback $r1) throws  {
        this.appCallback = $r1;
    }

    public void onCancel(AppCall appCall) throws  {
        if (this.appCallback != null) {
            this.appCallback.onCancel();
        }
    }

    public void onError(AppCall appCall, FacebookException $r2) throws  {
        if (this.appCallback != null) {
            this.appCallback.onError($r2);
        }
    }
}
