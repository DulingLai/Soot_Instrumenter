package com.abaltatech.weblinkserver;

interface WLBrowserEventHandler {
    boolean onGoBack() throws ;

    boolean onGoForward() throws ;

    boolean onGoHome() throws ;

    boolean onGoUrl(String str) throws ;
}
