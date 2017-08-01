package com.google.android.gms.internal;

import java.math.BigDecimal;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzavr extends Number {
    private final String value;

    public zzavr(String $r1) throws  {
        this.value = $r1;
    }

    public double doubleValue() throws  {
        return Double.parseDouble(this.value);
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof zzavr)) {
            return false;
        }
        zzavr $r2 = (zzavr) $r1;
        return this.value == $r2.value || this.value.equals($r2.value);
    }

    public float floatValue() throws  {
        return Float.parseFloat(this.value);
    }

    public int hashCode() throws  {
        return this.value.hashCode();
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

    public String toString() throws  {
        return this.value;
    }
}
