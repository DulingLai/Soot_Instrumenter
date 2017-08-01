package com.abaltatech.mcs.approuter;

public class AppDescriptor {
    public final AppID AppID;
    public IStartupConfig Config;
    public final DataQueueMsg MessageQueue;
    IAppServer Server;

    public AppDescriptor(AppID $r1, IStartupConfig $r2) throws  {
        if ($r1 == null || $r2 == null) {
            throw new IllegalArgumentException();
        }
        this.AppID = $r1;
        this.Config = $r2;
        this.Server = null;
        this.MessageQueue = new DataQueueMsg(32);
    }
}
