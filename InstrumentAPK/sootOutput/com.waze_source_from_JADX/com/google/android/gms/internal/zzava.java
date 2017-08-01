package com.google.android.gms.internal;

import java.math.BigInteger;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzava extends zzauu {
    private static final Class<?>[] bXM = new Class[]{Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    private Object aVj;

    public zzava(Boolean $r1) throws  {
        setValue($r1);
    }

    public zzava(Number $r1) throws  {
        setValue($r1);
    }

    public zzava(String $r1) throws  {
        setValue($r1);
    }

    private static boolean zza(zzava $r0) throws  {
        if (!($r0.aVj instanceof Number)) {
            return false;
        }
        Number $r2 = (Number) $r0.aVj;
        return ($r2 instanceof BigInteger) || ($r2 instanceof Long) || ($r2 instanceof Integer) || ($r2 instanceof Short) || ($r2 instanceof Byte);
    }

    private static boolean zzcy(Object $r0) throws  {
        if ($r0 instanceof String) {
            return true;
        }
        Class $r2 = $r0.getClass();
        for (Class $r3 : bXM) {
            if ($r3.isAssignableFrom($r2)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object $r1) throws  {
        boolean $z0 = false;
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        zzava $r4 = (zzava) $r1;
        if (this.aVj == null) {
            return $r4.aVj == null;
        } else {
            if (zza(this) && zza($r4)) {
                return hb().longValue() == $r4.hb().longValue();
            } else {
                Object $r12 = this.aVj;
                if ($r12 instanceof Number) {
                    $r12 = $r4.aVj;
                    if ($r12 instanceof Number) {
                        double $d0 = hb().doubleValue();
                        double $d1 = $r4.hb().doubleValue();
                        if ($d0 == $d1 || (Double.isNaN($d0) && Double.isNaN($d1))) {
                            $z0 = true;
                        }
                        return $z0;
                    }
                }
                return this.aVj.equals($r4.aVj);
            }
        }
    }

    public int hashCode() throws  {
        if (this.aVj == null) {
            return 31;
        }
        long $l1;
        if (zza(this)) {
            $l1 = hb().longValue();
            return (int) ($l1 ^ ($l1 >>> 32));
        } else if (!(this.aVj instanceof Number)) {
            return this.aVj.hashCode();
        } else {
            $l1 = Double.doubleToLongBits(hb().doubleValue());
            return (int) ($l1 ^ ($l1 >>> 32));
        }
    }

    public Number hb() throws  {
        return this.aVj instanceof String ? new zzavr((String) this.aVj) : (Number) this.aVj;
    }

    public String hc() throws  {
        return hq() ? hb().toString() : hp() ? ho().toString() : (String) this.aVj;
    }

    public double hd() throws  {
        return hq() ? hb().doubleValue() : Double.parseDouble(hc());
    }

    public long he() throws  {
        return hq() ? hb().longValue() : Long.parseLong(hc());
    }

    public int hf() throws  {
        return hq() ? hb().intValue() : Integer.parseInt(hc());
    }

    public boolean hg() throws  {
        return hp() ? ho().booleanValue() : Boolean.parseBoolean(hc());
    }

    Boolean ho() throws  {
        return (Boolean) this.aVj;
    }

    public boolean hp() throws  {
        return this.aVj instanceof Boolean;
    }

    public boolean hq() throws  {
        return this.aVj instanceof Number;
    }

    public boolean hr() throws  {
        return this.aVj instanceof String;
    }

    void setValue(Object $r1) throws  {
        if ($r1 instanceof Character) {
            this.aVj = String.valueOf(((Character) $r1).charValue());
            return;
        }
        boolean $z0 = ($r1 instanceof Number) || zzcy($r1);
        zzavm.zzbn($z0);
        this.aVj = $r1;
    }
}
