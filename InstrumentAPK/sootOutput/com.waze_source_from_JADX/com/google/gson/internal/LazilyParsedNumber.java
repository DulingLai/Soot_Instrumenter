package com.google.gson.internal;

import java.io.ObjectStreamException;
import java.math.BigDecimal;

public final class LazilyParsedNumber extends Number {
    private final String value;

    public LazilyParsedNumber(String $r1) throws  {
        this.value = $r1;
    }

    public int intValue() throws  {
        try {
            return Integer.parseInt(this.value);
        } catch (NumberFormatException e) {
            try {
                return (int) Long.parseLong(this.value);
            } catch (NumberFormatException e2) {
                return new BigDecimal(this.value).intValue();
            }
        }
    }

    public long longValue() throws  {
        try {
            return Long.parseLong(this.value);
        } catch (NumberFormatException e) {
            return new BigDecimal(this.value).longValue();
        }
    }

    public float floatValue() throws  {
        return Float.parseFloat(this.value);
    }

    public double doubleValue() throws  {
        return Double.parseDouble(this.value);
    }

    public String toString() throws  {
        return this.value;
    }

    private Object writeReplace() throws ObjectStreamException {
        return new BigDecimal(this.value);
    }

    public int hashCode() throws  {
        return this.value.hashCode();
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof LazilyParsedNumber)) {
            return false;
        }
        LazilyParsedNumber $r2 = (LazilyParsedNumber) $r1;
        return this.value == $r2.value || this.value.equals($r2.value);
    }
}
