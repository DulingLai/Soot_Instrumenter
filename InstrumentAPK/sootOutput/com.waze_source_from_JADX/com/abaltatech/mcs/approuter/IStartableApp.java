package com.abaltatech.mcs.approuter;

public interface IStartableApp {
    public static final int Busy = 3;
    public static final int NotStarted = 0;
    public static final int Started = 2;
    public static final int Starting = 1;

    int getAppState() throws ;

    void startApp(AppID appID) throws ;

    void stopApp() throws ;
}
