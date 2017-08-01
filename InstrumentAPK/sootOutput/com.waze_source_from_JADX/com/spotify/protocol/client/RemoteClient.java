package com.spotify.protocol.client;

import dalvik.annotation.Signature;

public interface RemoteClient {
    <T> CallResult<T> call(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) String str, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Class<T> cls) throws ;

    <T> CallResult<T> call(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Object;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) String str, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Object;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Object obj, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Object;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Class<T> cls) throws ;

    void goodbye() throws ;

    <T> CallResult<T> hello(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Class<T> cls) throws ;

    <T> Subscription<T> subscribe(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/Subscription", "<TT;>;"}) String str, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/Subscription", "<TT;>;"}) Class<T> cls) throws ;

    <T> void unsubscribe(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/spotify/protocol/client/Subscription", "<TT;>;)V"}) Subscription<T> subscription) throws ;
}
