package com.google.gson;

import com.google.gson.internal.C$Gson$Preconditions;
import com.google.gson.internal.LazilyParsedNumber;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class JsonPrimitive extends JsonElement {
    private static final Class<?>[] PRIMITIVE_TYPES = new Class[]{Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    private Object value;

    public JsonPrimitive(Boolean $r1) throws  {
        setValue($r1);
    }

    public JsonPrimitive(Number $r1) throws  {
        setValue($r1);
    }

    public JsonPrimitive(String $r1) throws  {
        setValue($r1);
    }

    public JsonPrimitive(Character $r1) throws  {
        setValue($r1);
    }

    JsonPrimitive(Object $r1) throws  {
        setValue($r1);
    }

    JsonPrimitive deepCopy() throws  {
        return this;
    }

    void setValue(Object $r1) throws  {
        if ($r1 instanceof Character) {
            this.value = String.valueOf(((Character) $r1).charValue());
            return;
        }
        boolean $z0 = ($r1 instanceof Number) || isPrimitiveOrString($r1);
        C$Gson$Preconditions.checkArgument($z0);
        this.value = $r1;
    }

    public boolean isBoolean() throws  {
        return this.value instanceof Boolean;
    }

    Boolean getAsBooleanWrapper() throws  {
        return (Boolean) this.value;
    }

    public boolean getAsBoolean() throws  {
        if (isBoolean()) {
            return getAsBooleanWrapper().booleanValue();
        }
        return Boolean.parseBoolean(getAsString());
    }

    public boolean isNumber() throws  {
        return this.value instanceof Number;
    }

    public Number getAsNumber() throws  {
        return this.value instanceof String ? new LazilyParsedNumber((String) this.value) : (Number) this.value;
    }

    public boolean isString() throws  {
        return this.value instanceof String;
    }

    public String getAsString() throws  {
        if (isNumber()) {
            return getAsNumber().toString();
        }
        if (isBoolean()) {
            return getAsBooleanWrapper().toString();
        }
        return (String) this.value;
    }

    public double getAsDouble() throws  {
        return isNumber() ? getAsNumber().doubleValue() : Double.parseDouble(getAsString());
    }

    public BigDecimal getAsBigDecimal() throws  {
        return this.value instanceof BigDecimal ? (BigDecimal) this.value : new BigDecimal(this.value.toString());
    }

    public BigInteger getAsBigInteger() throws  {
        return this.value instanceof BigInteger ? (BigInteger) this.value : new BigInteger(this.value.toString());
    }

    public float getAsFloat() throws  {
        return isNumber() ? getAsNumber().floatValue() : Float.parseFloat(getAsString());
    }

    public long getAsLong() throws  {
        return isNumber() ? getAsNumber().longValue() : Long.parseLong(getAsString());
    }

    public short getAsShort() throws  {
        return isNumber() ? getAsNumber().shortValue() : Short.parseShort(getAsString());
    }

    public int getAsInt() throws  {
        return isNumber() ? getAsNumber().intValue() : Integer.parseInt(getAsString());
    }

    public byte getAsByte() throws  {
        return isNumber() ? getAsNumber().byteValue() : Byte.parseByte(getAsString());
    }

    public char getAsCharacter() throws  {
        return getAsString().charAt(0);
    }

    private static boolean isPrimitiveOrString(Object $r0) throws  {
        if ($r0 instanceof String) {
            return true;
        }
        Class $r3 = $r0.getClass();
        for (Class $r2 : PRIMITIVE_TYPES) {
            if ($r2.isAssignableFrom($r3)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() throws  {
        if (this.value == null) {
            return 31;
        }
        long $l1;
        if (isIntegral(this)) {
            $l1 = getAsNumber().longValue();
            return (int) (($l1 >>> 32) ^ $l1);
        } else if (!(this.value instanceof Number)) {
            return this.value.hashCode();
        } else {
            $l1 = Double.doubleToLongBits(getAsNumber().doubleValue());
            return (int) (($l1 >>> 32) ^ $l1);
        }
    }

    public boolean equals(Object $r1) throws  {
        boolean $z0 = false;
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        JsonPrimitive $r4 = (JsonPrimitive) $r1;
        if (this.value == null) {
            if ($r4.value != null) {
                return false;
            }
            return true;
        } else if (!isIntegral(this) || !isIntegral($r4)) {
            Object $r12 = this.value;
            if ($r12 instanceof Number) {
                $r12 = $r4.value;
                if ($r12 instanceof Number) {
                    double $d0 = getAsNumber().doubleValue();
                    double $d1 = $r4.getAsNumber().doubleValue();
                    if ($d0 == $d1 || (Double.isNaN($d0) && Double.isNaN($d1))) {
                        $z0 = true;
                    }
                    return $z0;
                }
            }
            return this.value.equals($r4.value);
        } else if (getAsNumber().longValue() != $r4.getAsNumber().longValue()) {
            return false;
        } else {
            return true;
        }
    }

    private static boolean isIntegral(JsonPrimitive $r0) throws  {
        if (!($r0.value instanceof Number)) {
            return false;
        }
        Number $r2 = (Number) $r0.value;
        return ($r2 instanceof BigInteger) || ($r2 instanceof Long) || ($r2 instanceof Integer) || ($r2 instanceof Short) || ($r2 instanceof Byte);
    }
}
