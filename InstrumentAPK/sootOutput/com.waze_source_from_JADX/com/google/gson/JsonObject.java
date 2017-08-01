package com.google.gson;

import com.google.gson.internal.LinkedTreeMap;
import java.util.Map.Entry;
import java.util.Set;

public final class JsonObject extends JsonElement {
    private final LinkedTreeMap<String, JsonElement> members = new LinkedTreeMap();

    JsonObject deepCopy() throws  {
        JsonObject $r1 = new JsonObject();
        for (Entry $r6 : this.members.entrySet()) {
            $r1.add((String) $r6.getKey(), ((JsonElement) $r6.getValue()).deepCopy());
        }
        return $r1;
    }

    public void add(String $r1, JsonElement $r3) throws  {
        if ($r3 == null) {
            $r3 = JsonNull.INSTANCE;
        }
        this.members.put($r1, $r3);
    }

    public JsonElement remove(String $r1) throws  {
        return (JsonElement) this.members.remove($r1);
    }

    public void addProperty(String $r1, String $r2) throws  {
        add($r1, createJsonElement($r2));
    }

    public void addProperty(String $r1, Number $r2) throws  {
        add($r1, createJsonElement($r2));
    }

    public void addProperty(String $r1, Boolean $r2) throws  {
        add($r1, createJsonElement($r2));
    }

    public void addProperty(String $r1, Character $r2) throws  {
        add($r1, createJsonElement($r2));
    }

    private JsonElement createJsonElement(Object $r1) throws  {
        return $r1 == null ? JsonNull.INSTANCE : new JsonPrimitive($r1);
    }

    public Set<Entry<String, JsonElement>> entrySet() throws  {
        return this.members.entrySet();
    }

    public boolean has(String $r1) throws  {
        return this.members.containsKey($r1);
    }

    public JsonElement get(String $r1) throws  {
        return (JsonElement) this.members.get($r1);
    }

    public JsonPrimitive getAsJsonPrimitive(String $r1) throws  {
        return (JsonPrimitive) this.members.get($r1);
    }

    public JsonArray getAsJsonArray(String $r1) throws  {
        return (JsonArray) this.members.get($r1);
    }

    public JsonObject getAsJsonObject(String $r1) throws  {
        return (JsonObject) this.members.get($r1);
    }

    public boolean equals(Object $r2) throws  {
        return $r2 == this || (($r2 instanceof JsonObject) && ((JsonObject) $r2).members.equals(this.members));
    }

    public int hashCode() throws  {
        return this.members.hashCode();
    }
}
