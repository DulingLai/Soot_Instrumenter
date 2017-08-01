package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public final class zznk extends zzawz {
    private static volatile zznk[] iR;
    public Integer iS;
    public String iT;
    public String iU;
    public String iV;
    public Boolean iW;
    public Boolean iX;
    public Integer iY;
    public Boolean iZ;
    public String ja;
    public Boolean jb;
    public String pageId;

    public zznk() throws  {
        zzafh();
    }

    public static zznk[] zzafg() throws  {
        if (iR == null) {
            synchronized (zzawx.cbB) {
                if (iR == null) {
                    iR = new zznk[0];
                }
            }
        }
        return iR;
    }

    protected int computeSerializedSize() throws  {
        int $i0 = super.computeSerializedSize();
        int $i1 = $i0;
        if (this.iS != null) {
            $i1 = $i0 + zzawr.zzax(1, this.iS.intValue());
        }
        if (this.iT != null) {
            $i1 += zzawr.zzt(2, this.iT);
        }
        if (this.iU != null) {
            $i1 += zzawr.zzt(3, this.iU);
        }
        if (this.iV != null) {
            $i1 += zzawr.zzt(4, this.iV);
        }
        if (this.iW != null) {
            $i1 += zzawr.zzn(5, this.iW.booleanValue());
        }
        if (this.iX != null) {
            $i1 += zzawr.zzn(6, this.iX.booleanValue());
        }
        if (this.iY != null) {
            $i1 += zzawr.zzax(7, this.iY.intValue());
        }
        if (this.pageId != null) {
            $i1 += zzawr.zzt(8, this.pageId);
        }
        if (this.iZ != null) {
            $i1 += zzawr.zzn(9, this.iZ.booleanValue());
        }
        if (this.ja != null) {
            $i1 += zzawr.zzt(10, this.ja);
        }
        return this.jb != null ? $i1 + zzawr.zzn(11, this.jb.booleanValue()) : $i1;
    }

    public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
        return zzt($r1);
    }

    public void writeTo(zzawr $r1) throws IOException {
        if (this.iS != null) {
            $r1.zzav(1, this.iS.intValue());
        }
        if (this.iT != null) {
            $r1.zzs(2, this.iT);
        }
        if (this.iU != null) {
            $r1.zzs(3, this.iU);
        }
        if (this.iV != null) {
            $r1.zzs(4, this.iV);
        }
        if (this.iW != null) {
            $r1.zzm(5, this.iW.booleanValue());
        }
        if (this.iX != null) {
            $r1.zzm(6, this.iX.booleanValue());
        }
        if (this.iY != null) {
            $r1.zzav(7, this.iY.intValue());
        }
        if (this.pageId != null) {
            $r1.zzs(8, this.pageId);
        }
        if (this.iZ != null) {
            $r1.zzm(9, this.iZ.booleanValue());
        }
        if (this.ja != null) {
            $r1.zzs(10, this.ja);
        }
        if (this.jb != null) {
            $r1.zzm(11, this.jb.booleanValue());
        }
        super.writeTo($r1);
    }

    public zznk zzafh() throws  {
        this.iT = null;
        this.iU = null;
        this.iV = null;
        this.iW = null;
        this.iX = null;
        this.iY = null;
        this.pageId = null;
        this.iZ = null;
        this.ja = null;
        this.jb = null;
        this.cbC = -1;
        return this;
    }

    public zznk zzt(zzawq $r1) throws IOException {
        while (true) {
            int $i0 = $r1.ie();
            switch ($i0) {
                case 0:
                    break;
                case 8:
                    $i0 = $r1.ii();
                    switch ($i0) {
                        case 1:
                        case 2:
                        case 3:
                            this.iS = Integer.valueOf($i0);
                            break;
                        default:
                            continue;
                    }
                case 18:
                    this.iT = $r1.readString();
                    continue;
                case 26:
                    this.iU = $r1.readString();
                    continue;
                case 34:
                    this.iV = $r1.readString();
                    continue;
                case 40:
                    this.iW = Boolean.valueOf($r1.ik());
                    continue;
                case 48:
                    this.iX = Boolean.valueOf($r1.ik());
                    continue;
                case 56:
                    this.iY = Integer.valueOf($r1.ii());
                    continue;
                case 66:
                    this.pageId = $r1.readString();
                    continue;
                case 72:
                    this.iZ = Boolean.valueOf($r1.ik());
                    continue;
                case 82:
                    this.ja = $r1.readString();
                    continue;
                case 88:
                    this.jb = Boolean.valueOf($r1.ik());
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
