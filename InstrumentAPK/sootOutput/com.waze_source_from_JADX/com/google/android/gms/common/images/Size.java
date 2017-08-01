package com.google.android.gms.common.images;

/* compiled from: dalvik_source_com.waze.apk */
public final class Size {
    private final int zzaiq;
    private final int zzair;

    public Size(int $i0, int $i1) throws  {
        this.zzaiq = $i0;
        this.zzair = $i1;
    }

    public static Size parseSize(String $r0) throws NumberFormatException {
        if ($r0 == null) {
            throw new IllegalArgumentException("string must not be null");
        }
        int $i0 = $r0.indexOf(42);
        int $i1 = $i0;
        if ($i0 < 0) {
            $i1 = $r0.indexOf(120);
        }
        if ($i1 < 0) {
            throw zzgo($r0);
        }
        try {
            return new Size(Integer.parseInt($r0.substring(0, $i1)), Integer.parseInt($r0.substring($i1 + 1)));
        } catch (NumberFormatException e) {
            throw zzgo($r0);
        }
    }

    private static NumberFormatException zzgo(String $r0) throws  {
        throw new NumberFormatException(new StringBuilder(String.valueOf($r0).length() + 16).append("Invalid Size: \"").append($r0).append("\"").toString());
    }

    public boolean equals(Object $r1) throws  {
        boolean $z0 = true;
        if ($r1 == null) {
            return false;
        }
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof Size)) {
            return false;
        }
        Size $r2 = (Size) $r1;
        if (!(this.zzaiq == $r2.zzaiq && this.zzair == $r2.zzair)) {
            $z0 = false;
        }
        return $z0;
    }

    public int getHeight() throws  {
        return this.zzair;
    }

    public int getWidth() throws  {
        return this.zzaiq;
    }

    public int hashCode() throws  {
        return this.zzair ^ ((this.zzaiq << 16) | (this.zzaiq >>> 16));
    }

    public String toString() throws  {
        int $i1 = this.zzaiq;
        return $i1 + "x" + this.zzair;
    }
}
