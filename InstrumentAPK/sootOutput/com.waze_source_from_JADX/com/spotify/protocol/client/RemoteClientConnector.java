package com.spotify.protocol.client;

public interface RemoteClientConnector {

    public interface ConnectionCallback {
        void onConnected(RemoteClient remoteClient) throws ;

        void onConnectionFailed(Throwable th) throws ;
    }

    void connect(ConnectionCallback connectionCallback) throws ;

    void disconnect() throws ;
}
