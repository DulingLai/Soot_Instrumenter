package com.spotify.protocol.client;

public interface Result<T> {
    T getData() throws ;

    Throwable getError() throws ;

    String getErrorMessage() throws ;

    boolean isSuccessful() throws ;
}
