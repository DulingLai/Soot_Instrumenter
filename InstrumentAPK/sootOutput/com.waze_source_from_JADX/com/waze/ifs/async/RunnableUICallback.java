package com.waze.ifs.async;

import com.waze.AppService;

public abstract class RunnableUICallback extends RunnableCallback {
    public RunnableUICallback() throws  {
        super(AppService.getInstance());
    }
}
