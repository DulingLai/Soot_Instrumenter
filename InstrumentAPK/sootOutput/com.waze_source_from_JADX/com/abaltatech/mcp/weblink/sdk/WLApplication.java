package com.abaltatech.mcp.weblink.sdk;

import android.app.Application;

public class WLApplication extends Application {
    protected static WLApplication ms_instance;

    public WLApplication() throws  {
        ms_instance = this;
    }

    public static WLApplication instance() throws  {
        return ms_instance;
    }

    public void onCreate() throws  {
        super.onCreate();
        WEBLINK.instance.init(this);
    }
}
