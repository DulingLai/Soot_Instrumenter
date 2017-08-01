package com.waze;

import android.app.Activity;
import java.util.concurrent.Executor;

public class ActivityNativeManagerBase implements Executor {
    protected Activity mContext = null;

    public void initNativeLayer() throws  {
        Logger.m43w("Override this method to initialize the native layer");
    }

    public void postOnUI(Runnable $r1) throws  {
        if (this.mContext != null) {
            this.mContext.runOnUiThread($r1);
        }
    }

    public void post(Runnable $r1) throws  {
        NativeManager.Post($r1);
    }

    public void execute(Runnable $r1) throws  {
        post($r1);
    }

    public void setContext(Activity $r1) throws  {
        this.mContext = $r1;
    }

    protected ActivityNativeManagerBase() throws  {
    }

    protected ActivityNativeManagerBase(Activity $r1) throws  {
        this.mContext = $r1;
    }
}
