package com.abaltatech.mcs.approuter;

public interface IAppServer extends IStartableApp {
    String getAppName() throws ;

    AppMessage onNewRequest(AppMessage appMessage) throws ;
}
