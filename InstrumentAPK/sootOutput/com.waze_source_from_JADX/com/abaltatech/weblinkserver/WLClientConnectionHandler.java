package com.abaltatech.weblinkserver;

interface WLClientConnectionHandler {
    void onClientConnected() throws ;

    void onClientDisconnected() throws ;
}
