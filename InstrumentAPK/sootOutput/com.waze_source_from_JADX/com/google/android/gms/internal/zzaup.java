package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzaup {
    private zzavp bXC = zzavp.bYm;
    private zzave bXD = zzave.DEFAULT;
    private zzaun bXE = zzaum.IDENTITY;
    private final Map<Type, zzauq<?>> bXF = new HashMap();
    private final List<zzavh> bXG = new ArrayList();
    private int bXH = 2;
    private int bXI = 2;
    private boolean bXJ = true;
    private final List<zzavh> bXs = new ArrayList();

    private void zza(@Signature({"(", "Ljava/lang/String;", "II", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "II", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) int $i0, @Signature({"(", "Ljava/lang/String;", "II", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) int $i1, @Signature({"(", "Ljava/lang/String;", "II", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzavh;", ">;)V"}) List<zzavh> $r2) throws  {
        Object $r5;
        if ($r1 != null && !"".equals($r1.trim())) {
            $r5 = new zzauj($r1);
        } else if ($i0 != 2 && $i1 != 2) {
            $r5 = new zzauj($i0, $i1);
        } else {
            return;
        }
        $r2.add(zzavf.zza(zzawk.zzq(Date.class), $r5));
        $r2.add(zzavf.zza(zzawk.zzq(Timestamp.class), $r5));
        $r2.add(zzavf.zza(zzawk.zzq(java.sql.Date.class), $r5));
    }

    public zzaup gZ() throws  {
        this.bXJ = false;
        return this;
    }

    public zzauo ha() throws  {
        ArrayList $r3 = new ArrayList();
        $r3.addAll(this.bXs);
        Collections.reverse($r3);
        $r3.addAll(this.bXG);
        zza(null, this.bXH, this.bXI, $r3);
        return new zzauo(this.bXC, this.bXE, this.bXF, false, false, false, this.bXJ, false, false, this.bXD, $r3);
    }

    public zzaup zza(Type $r1, Object $r2) throws  {
        boolean $z0 = ($r2 instanceof zzavc) || ($r2 instanceof zzaut) || ($r2 instanceof zzauq) || ($r2 instanceof zzavg);
        zzavm.zzbn($z0);
        if ($r2 instanceof zzauq) {
            this.bXF.put($r1, (zzauq) $r2);
        }
        if (($r2 instanceof zzavc) || ($r2 instanceof zzaut)) {
            this.bXs.add(zzavf.zzb(zzawk.zzl($r1), $r2));
        }
        if (!($r2 instanceof zzavg)) {
            return this;
        }
        this.bXs.add(zzawj.zza(zzawk.zzl($r1), (zzavg) $r2));
        return this;
    }

    public zzaup zza(zzauk... $r1) throws  {
        for (zzauk $r3 : $r1) {
            this.bXC = this.bXC.zza($r3, true, true);
        }
        return this;
    }

    public zzaup zzi(int... $r1) throws  {
        this.bXC = this.bXC.zzj($r1);
        return this;
    }
}
