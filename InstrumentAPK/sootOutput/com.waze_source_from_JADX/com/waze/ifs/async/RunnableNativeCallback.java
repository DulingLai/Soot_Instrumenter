package com.waze.ifs.async;

import com.waze.NativeManager;

public abstract class RunnableNativeCallback extends RunnableCallback {
    public RunnableNativeCallback() throws  {
        super(NativeManager.getInstance());
    }
}
