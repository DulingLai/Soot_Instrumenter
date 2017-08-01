package com.spotify.protocol.mappers;

public interface JsonMapper {
    String toJson(Object obj) throws JsonMappingException;

    JsonArray toJsonArray(String str) throws JsonMappingException;
}
