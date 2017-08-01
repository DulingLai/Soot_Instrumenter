package com.spotify.protocol;

import com.spotify.protocol.mappers.JsonArray;
import com.spotify.protocol.mappers.JsonObject;

public class WampMessage {
    private final JsonArray mJsonArray;

    public WampMessage(JsonArray $r1) throws  {
        this.mJsonArray = $r1;
    }

    public int getAction() throws  {
        return this.mJsonArray.getIntAt(0);
    }

    public String getStringAt(int $i0) throws  {
        return this.mJsonArray.getStringAt($i0);
    }

    public int getIntAt(int $i0) throws  {
        return this.mJsonArray.getIntAt($i0);
    }

    public JsonObject getObjectAt(int $i0) throws  {
        return this.mJsonArray.getObjectAt($i0);
    }
}
