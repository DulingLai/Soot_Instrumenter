package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public final class zznn extends zzawz {
    public Integer jk;
    public zznl[] jl;
    public zznk[] jm;
    public zza[] jn;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza extends zzawz {
        private static volatile zza[] jo;
        public String ja;
        public Integer jk;
        public String url;

        public zza() throws  {
            zzafn();
        }

        public static zza[] zzafm() throws  {
            if (jo == null) {
                synchronized (zzawx.cbB) {
                    if (jo == null) {
                        jo = new zza[0];
                    }
                }
            }
            return jo;
        }

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.ja != null) {
                $i1 = $i0 + zzawr.zzt(1, this.ja);
            }
            if (this.jk != null) {
                $i1 += zzawr.zzax(2, this.jk.intValue());
            }
            return this.url != null ? $i1 + zzawr.zzt(3, this.url) : $i1;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zzx($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.ja != null) {
                $r1.zzs(1, this.ja);
            }
            if (this.jk != null) {
                $r1.zzav(2, this.jk.intValue());
            }
            if (this.url != null) {
                $r1.zzs(3, this.url);
            }
            super.writeTo($r1);
        }

        public zza zzafn() throws  {
            this.ja = null;
            this.url = null;
            this.cbC = -1;
            return this;
        }

        public zza zzx(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        this.ja = $r1.readString();
                        continue;
                    case 16:
                        $i0 = $r1.ii();
                        switch ($i0) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.jk = Integer.valueOf($i0);
                                break;
                            default:
                                continue;
                        }
                    case 26:
                        this.url = $r1.readString();
                        continue;
                    default:
                        if (!zzaxc.zzb($r1, $i0)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }
    }

    public zznn() throws  {
        zzafl();
    }

    protected int computeSerializedSize() throws  {
        int $i1 = super.computeSerializedSize();
        int $i2 = $i1;
        if (this.jk != null) {
            $i2 = $i1 + zzawr.zzax(1, this.jk.intValue());
        }
        if (this.jl != null && this.jl.length > 0) {
            for (zzawz $r3 : this.jl) {
                if ($r3 != null) {
                    $i2 += zzawr.zzc(2, $r3);
                }
            }
        }
        if (this.jm != null && this.jm.length > 0) {
            for (zzawz $r5 : this.jm) {
                if ($r5 != null) {
                    $i2 += zzawr.zzc(3, $r5);
                }
            }
        }
        if (this.jn == null || this.jn.length <= 0) {
            return $i2;
        }
        for (zzawz $r7 : this.jn) {
            if ($r7 != null) {
                $i2 += zzawr.zzc(4, $r7);
            }
        }
        return $i2;
    }

    public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
        return zzw($r1);
    }

    public void writeTo(zzawr $r1) throws IOException {
        if (this.jk != null) {
            $r1.zzav(1, this.jk.intValue());
        }
        if (this.jl != null && this.jl.length > 0) {
            for (zzawz $r4 : this.jl) {
                if ($r4 != null) {
                    $r1.zza(2, $r4);
                }
            }
        }
        if (this.jm != null && this.jm.length > 0) {
            for (zzawz $r6 : this.jm) {
                if ($r6 != null) {
                    $r1.zza(3, $r6);
                }
            }
        }
        if (this.jn != null && this.jn.length > 0) {
            for (zzawz $r8 : this.jn) {
                if ($r8 != null) {
                    $r1.zza(4, $r8);
                }
            }
        }
        super.writeTo($r1);
    }

    public zznn zzafl() throws  {
        this.jl = zznl.zzafi();
        this.jm = zznk.zzafg();
        this.jn = zza.zzafm();
        this.cbC = -1;
        return this;
    }

    public zznn zzw(zzawq $r1) throws IOException {
        while (true) {
            int $i0 = $r1.ie();
            int $i1;
            switch ($i0) {
                case 0:
                    break;
                case 8:
                    $i0 = $r1.ii();
                    switch ($i0) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            this.jk = Integer.valueOf($i0);
                            break;
                        default:
                            continue;
                    }
                case 18:
                    $i1 = zzaxc.zzc($r1, 18);
                    $i0 = this.jl == null ? 0 : this.jl.length;
                    zznl[] $r3 = new zznl[($i1 + $i0)];
                    if ($i0 != 0) {
                        System.arraycopy(this.jl, 0, $r3, 0, $i0);
                    }
                    while ($i0 < $r3.length - 1) {
                        $r3[$i0] = new zznl();
                        $r1.zza($r3[$i0]);
                        $r1.ie();
                        $i0++;
                    }
                    $r3[$i0] = new zznl();
                    $r1.zza($r3[$i0]);
                    this.jl = $r3;
                    continue;
                case 26:
                    $i1 = zzaxc.zzc($r1, 26);
                    $i0 = this.jm == null ? 0 : this.jm.length;
                    zznk[] $r6 = new zznk[($i1 + $i0)];
                    if ($i0 != 0) {
                        System.arraycopy(this.jm, 0, $r6, 0, $i0);
                    }
                    while ($i0 < $r6.length - 1) {
                        $r6[$i0] = new zznk();
                        $r1.zza($r6[$i0]);
                        $r1.ie();
                        $i0++;
                    }
                    $r6[$i0] = new zznk();
                    $r1.zza($r6[$i0]);
                    this.jm = $r6;
                    continue;
                case 34:
                    $i1 = zzaxc.zzc($r1, 34);
                    $i0 = this.jn == null ? 0 : this.jn.length;
                    zza[] $r9 = new zza[($i1 + $i0)];
                    if ($i0 != 0) {
                        System.arraycopy(this.jn, 0, $r9, 0, $i0);
                    }
                    while ($i0 < $r9.length - 1) {
                        $r9[$i0] = new zza();
                        $r1.zza($r9[$i0]);
                        $r1.ie();
                        $i0++;
                    }
                    $r9[$i0] = new zza();
                    $r1.zza($r9[$i0]);
                    this.jn = $r9;
                    continue;
                default:
                    if (!zzaxc.zzb($r1, $i0)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }
}
