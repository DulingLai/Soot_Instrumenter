package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public final class zznl extends zzawz {
    private static volatile zznl[] jc;
    public String jd;
    public String je;
    public Boolean jf;
    public Boolean jg;
    public Integer jh;
    public Integer ji;
    public String name;
    public String path;
    public String value;

    public zznl() throws  {
        zzafj();
    }

    public static zznl[] zzafi() throws  {
        if (jc == null) {
            synchronized (zzawx.cbB) {
                if (jc == null) {
                    jc = new zznl[0];
                }
            }
        }
        return jc;
    }

    protected int computeSerializedSize() throws  {
        int $i0 = super.computeSerializedSize();
        int $i1 = $i0;
        if (this.name != null) {
            $i1 = $i0 + zzawr.zzt(1, this.name);
        }
        if (this.value != null) {
            $i1 += zzawr.zzt(2, this.value);
        }
        if (this.jd != null) {
            $i1 += zzawr.zzt(3, this.jd);
        }
        if (this.je != null) {
            $i1 += zzawr.zzt(4, this.je);
        }
        if (this.path != null) {
            $i1 += zzawr.zzt(5, this.path);
        }
        if (this.jf != null) {
            $i1 += zzawr.zzn(6, this.jf.booleanValue());
        }
        if (this.jg != null) {
            $i1 += zzawr.zzn(7, this.jg.booleanValue());
        }
        if (this.jh != null) {
            $i1 += zzawr.zzax(8, this.jh.intValue());
        }
        return this.ji != null ? $i1 + zzawr.zzax(9, this.ji.intValue()) : $i1;
    }

    public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
        return zzu($r1);
    }

    public void writeTo(zzawr $r1) throws IOException {
        if (this.name != null) {
            $r1.zzs(1, this.name);
        }
        if (this.value != null) {
            $r1.zzs(2, this.value);
        }
        if (this.jd != null) {
            $r1.zzs(3, this.jd);
        }
        if (this.je != null) {
            $r1.zzs(4, this.je);
        }
        if (this.path != null) {
            $r1.zzs(5, this.path);
        }
        if (this.jf != null) {
            $r1.zzm(6, this.jf.booleanValue());
        }
        if (this.jg != null) {
            $r1.zzm(7, this.jg.booleanValue());
        }
        if (this.jh != null) {
            $r1.zzav(8, this.jh.intValue());
        }
        if (this.ji != null) {
            $r1.zzav(9, this.ji.intValue());
        }
        super.writeTo($r1);
    }

    public zznl zzafj() throws  {
        this.name = null;
        this.value = null;
        this.jd = null;
        this.je = null;
        this.path = null;
        this.jf = null;
        this.jg = null;
        this.jh = null;
        this.cbC = -1;
        return this;
    }

    public zznl zzu(zzawq $r1) throws IOException {
        while (true) {
            int $i0 = $r1.ie();
            switch ($i0) {
                case 0:
                    break;
                case 10:
                    this.name = $r1.readString();
                    continue;
                case 18:
                    this.value = $r1.readString();
                    continue;
                case 26:
                    this.jd = $r1.readString();
                    continue;
                case 34:
                    this.je = $r1.readString();
                    continue;
                case 42:
                    this.path = $r1.readString();
                    continue;
                case 48:
                    this.jf = Boolean.valueOf($r1.ik());
                    continue;
                case 56:
                    this.jg = Boolean.valueOf($r1.ik());
                    continue;
                case 64:
                    this.jh = Integer.valueOf($r1.ii());
                    continue;
                case 72:
                    $i0 = $r1.ii();
                    switch ($i0) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            this.ji = Integer.valueOf($i0);
                            break;
                        default:
                            continue;
                    }
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
