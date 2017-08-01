package com.google.android.gms.common.server.response;

import dalvik.annotation.Signature;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class FastMapJsonResponse extends FastJsonResponse {
    private final HashMap<String, Object> Lc = new HashMap();

    public Object getValueObject(String $r1) throws  {
        return this.Lc.get($r1);
    }

    public HashMap<String, Object> getValues() throws  {
        return this.Lc;
    }

    protected boolean isPrimitiveFieldSet(String $r1) throws  {
        return this.Lc.containsKey($r1);
    }

    public void setBigDecimal(String $r1, BigDecimal $r2) throws  {
        this.Lc.put($r1, $r2);
    }

    public void setBigDecimals(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;)V"}) ArrayList<BigDecimal> $r2) throws  {
        this.Lc.put($r1, $r2);
    }

    public void setBigInteger(String $r1, BigInteger $r2) throws  {
        this.Lc.put($r1, $r2);
    }

    public void setBigIntegers(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;)V"}) ArrayList<BigInteger> $r2) throws  {
        this.Lc.put($r1, $r2);
    }

    public void setBoolean(String $r1, boolean $z0) throws  {
        this.Lc.put($r1, Boolean.valueOf($z0));
    }

    public void setBooleans(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;)V"}) ArrayList<Boolean> $r2) throws  {
        this.Lc.put($r1, $r2);
    }

    public void setDecodedBytes(String $r1, byte[] $r2) throws  {
        this.Lc.put($r1, $r2);
    }

    public void setDouble(String $r1, double $d0) throws  {
        this.Lc.put($r1, Double.valueOf($d0));
    }

    public void setDoubles(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;)V"}) ArrayList<Double> $r2) throws  {
        this.Lc.put($r1, $r2);
    }

    protected void setFloat(String $r1, float $f0) throws  {
        this.Lc.put($r1, Float.valueOf($f0));
    }

    protected void setFloats(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;)V"}) ArrayList<Float> $r2) throws  {
        this.Lc.put($r1, $r2);
    }

    public void setInteger(String $r1, int $i0) throws  {
        this.Lc.put($r1, Integer.valueOf($i0));
    }

    public void setIntegers(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;)V"}) ArrayList<Integer> $r2) throws  {
        this.Lc.put($r1, $r2);
    }

    public void setLong(String $r1, long $l0) throws  {
        this.Lc.put($r1, Long.valueOf($l0));
    }

    public void setLongs(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;)V"}) ArrayList<Long> $r2) throws  {
        this.Lc.put($r1, $r2);
    }

    public void setString(String $r1, String $r2) throws  {
        this.Lc.put($r1, $r2);
    }

    public void setStringMap(@Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r2) throws  {
        this.Lc.put($r1, $r2);
    }

    public void setStrings(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) ArrayList<String> $r2) throws  {
        this.Lc.put($r1, $r2);
    }
}
