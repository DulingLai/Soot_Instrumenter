package com.spotify.protocol.client;

import com.spotify.protocol.error.SpotifyAppRemoteException;

public interface AppProtocolIo {

    public interface DataInput {
        void onData(byte[] bArr, int i) throws ;
    }

    PendingResult<Void> connect() throws ;

    void disconnect() throws ;

    boolean isConnected() throws ;

    boolean isConnecting() throws ;

    void setDataInput(DataInput dataInput) throws ;

    void writeData(byte[] bArr, int i) throws SpotifyAppRemoteException;
}
