package com.abaltatech.weblinkserver;

public interface WLServerDelegate {
    void onClientConnectedWithScreenSize(int i, int i2) throws ;

    void onClientDisconnected() throws ;
}
