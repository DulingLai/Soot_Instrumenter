package com.google.gson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JsonArray extends JsonElement implements Iterable<JsonElement> {
    private final List<JsonElement> elements = new ArrayList();

    JsonArray deepCopy() throws  {
        JsonArray $r1 = new JsonArray();
        for (JsonElement deepCopy : this.elements) {
            $r1.add(deepCopy.deepCopy());
        }
        return $r1;
    }

    public void add(Boolean $r1) throws  {
        JsonElement $r3;
        List $r2 = this.elements;
        if ($r1 == null) {
            $r3 = JsonNull.INSTANCE;
        } else {
            $r3 = r4;
            JsonElement r4 = new JsonPrimitive($r1);
        }
        $r2.add($r3);
    }

    public void add(Character $r1) throws  {
        JsonElement $r3;
        List $r2 = this.elements;
        if ($r1 == null) {
            $r3 = JsonNull.INSTANCE;
        } else {
            $r3 = r4;
            JsonElement r4 = new JsonPrimitive($r1);
        }
        $r2.add($r3);
    }

    public void add(Number $r1) throws  {
        JsonElement $r3;
        List $r2 = this.elements;
        if ($r1 == null) {
            $r3 = JsonNull.INSTANCE;
        } else {
            $r3 = r4;
            JsonElement r4 = new JsonPrimitive($r1);
        }
        $r2.add($r3);
    }

    public void add(String $r1) throws  {
        JsonElement $r3;
        List $r2 = this.elements;
        if ($r1 == null) {
            $r3 = JsonNull.INSTANCE;
        } else {
            $r3 = r4;
            JsonElement r4 = new JsonPrimitive($r1);
        }
        $r2.add($r3);
    }

    public void add(JsonElement $r2) throws  {
        if ($r2 == null) {
            $r2 = JsonNull.INSTANCE;
        }
        this.elements.add($r2);
    }

    public void addAll(JsonArray $r1) throws  {
        this.elements.addAll($r1.elements);
    }

    public JsonElement set(int $i0, JsonElement $r1) throws  {
        return (JsonElement) this.elements.set($i0, $r1);
    }

    public boolean remove(JsonElement $r1) throws  {
        return this.elements.remove($r1);
    }

    public JsonElement remove(int $i0) throws  {
        return (JsonElement) this.elements.remove($i0);
    }

    public boolean contains(JsonElement $r1) throws  {
        return this.elements.contains($r1);
    }

    public int size() throws  {
        return this.elements.size();
    }

    public Iterator<JsonElement> iterator() throws  {
        return this.elements.iterator();
    }

    public JsonElement get(int $i0) throws  {
        return (JsonElement) this.elements.get($i0);
    }

    public Number getAsNumber() throws  {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsNumber();
        }
        throw new IllegalStateException();
    }

    public String getAsString() throws  {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsString();
        }
        throw new IllegalStateException();
    }

    public double getAsDouble() throws  {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsDouble();
        }
        throw new IllegalStateException();
    }

    public BigDecimal getAsBigDecimal() throws  {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsBigDecimal();
        }
        throw new IllegalStateException();
    }

    public BigInteger getAsBigInteger() throws  {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsBigInteger();
        }
        throw new IllegalStateException();
    }

    public float getAsFloat() throws  {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsFloat();
        }
        throw new IllegalStateException();
    }

    public long getAsLong() throws  {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsLong();
        }
        throw new IllegalStateException();
    }

    public int getAsInt() throws  {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsInt();
        }
        throw new IllegalStateException();
    }

    public byte getAsByte() throws  {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsByte();
        }
        throw new IllegalStateException();
    }

    public char getAsCharacter() throws  {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsCharacter();
        }
        throw new IllegalStateException();
    }

    public short getAsShort() throws  {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsShort();
        }
        throw new IllegalStateException();
    }

    public boolean getAsBoolean() throws  {
        if (this.elements.size() == 1) {
            return ((JsonElement) this.elements.get(0)).getAsBoolean();
        }
        throw new IllegalStateException();
    }

    public boolean equals(Object $r2) throws  {
        return $r2 == this || (($r2 instanceof JsonArray) && ((JsonArray) $r2).elements.equals(this.elements));
    }

    public int hashCode() throws  {
        return this.elements.hashCode();
    }
}
