package com.abaltatech.mcp.mcs.approuter;

public interface IStartupConfig {
    boolean isAppStarted() throws ;

    void startApp(AppID appID) throws ;

    String toStorageString() throws ;
}
