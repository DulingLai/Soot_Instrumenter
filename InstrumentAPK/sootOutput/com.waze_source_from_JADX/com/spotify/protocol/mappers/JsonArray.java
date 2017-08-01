package com.spotify.protocol.mappers;

public interface JsonArray {
    int getIntAt(int i) throws ;

    JsonObject getObjectAt(int i) throws ;

    String getStringAt(int i) throws ;
}
