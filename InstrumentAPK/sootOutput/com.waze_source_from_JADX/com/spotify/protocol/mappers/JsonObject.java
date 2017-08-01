package com.spotify.protocol.mappers;

import dalvik.annotation.Signature;

public interface JsonObject {
    <T> T getAs(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> cls) throws JsonMappingException;

    String toJson() throws JsonMappingException;
}
