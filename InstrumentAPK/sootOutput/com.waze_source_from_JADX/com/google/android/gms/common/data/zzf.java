package com.google.android.gms.common.data;

import dalvik.annotation.Signature;
import java.util.ArrayList;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzf<T> extends AbstractDataBuffer<T> {
    private boolean Hh = false;
    private ArrayList<Integer> Hi;

    protected zzf(DataHolder $r1) throws  {
        super($r1);
    }

    private void zzavi() throws  {
        synchronized (this) {
            boolean $z0 = this.Hh;
            this = this;
            if (!$z0) {
                int $i0 = this.DW.getCount();
                this.Hi = new ArrayList();
                if ($i0 > 0) {
                    this.Hi.add(Integer.valueOf(0));
                    String $r4 = zzavh();
                    String $r5 = this.DW.getString($r4, 0, this.DW.zzic(0));
                    int $i1 = 1;
                    while ($i1 < $i0) {
                        int $i2 = this.DW.zzic($i1);
                        String $r6 = this.DW.getString($r4, $i1, $i2);
                        String $r7 = $r6;
                        if ($r6 == null) {
                            throw new NullPointerException(new StringBuilder(String.valueOf($r4).length() + 78).append("Missing value for markerColumn: ").append($r4).append(", at row: ").append($i1).append(", for window: ").append($i2).toString());
                        }
                        if ($r6.equals($r5)) {
                            $r7 = $r5;
                        } else {
                            this.Hi.add(Integer.valueOf($i1));
                        }
                        $i1++;
                        $r5 = $r7;
                    }
                }
                this.Hh = true;
            }
        }
    }

    public final T get(@Signature({"(I)TT;"}) int $i0) throws  {
        zzavi();
        return zzw(zzie($i0), zzif($i0));
    }

    public int getCount() throws  {
        zzavi();
        return this.Hi.size();
    }

    protected abstract String zzavh() throws ;

    protected String zzavj() throws  {
        return null;
    }

    int zzie(int $i0) throws  {
        if ($i0 >= 0 && $i0 < this.Hi.size()) {
            return ((Integer) this.Hi.get($i0)).intValue();
        }
        throw new IllegalArgumentException("Position " + $i0 + " is out of bounds for this buffer");
    }

    protected int zzif(int $i0) throws  {
        if ($i0 < 0 || $i0 == this.Hi.size()) {
            return 0;
        }
        int $i1 = $i0 == this.Hi.size() + -1 ? this.DW.getCount() - ((Integer) this.Hi.get($i0)).intValue() : ((Integer) this.Hi.get($i0 + 1)).intValue() - ((Integer) this.Hi.get($i0)).intValue();
        if ($i1 != 1) {
            return $i1;
        }
        $i0 = zzie($i0);
        int $i2 = this.DW.zzic($i0);
        String $r5 = zzavj();
        return ($r5 == null || this.DW.getString($r5, $i0, $i2) != null) ? $i1 : 0;
    }

    protected abstract T zzw(@Signature({"(II)TT;"}) int i, @Signature({"(II)TT;"}) int i2) throws ;
}
