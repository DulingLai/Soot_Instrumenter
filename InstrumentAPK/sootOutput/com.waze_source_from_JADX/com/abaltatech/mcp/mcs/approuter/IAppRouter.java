package com.abaltatech.mcp.mcs.approuter;

import com.abaltatech.mcp.mcs.common.MCSException;

public interface IAppRouter {
    void appServerStarted(AppID appID, IAppServer iAppServer) throws MCSException;

    void appServerStopped(AppID appID) throws MCSException;

    AppID findAppByName(String str) throws MCSException;

    Object getAppContext() throws ;

    AppID registerApp(String str, IStartupConfig iStartupConfig) throws MCSException;

    void sendAppResponse(AppMessage appMessage) throws ;

    void unregisterApp(AppID appID) throws MCSException;
}
