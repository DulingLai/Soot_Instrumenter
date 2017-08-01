package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public final class zznm extends zzawz {
    public zznn jj;

    public zznm() throws  {
        zzafk();
    }

    public static zznm zzk(byte[] $r0) throws zzawy {
        return (zznm) zzawz.zza(new zznm(), $r0);
    }

    protected int computeSerializedSize() throws  {
        int $i0 = super.computeSerializedSize();
        return this.jj != null ? $i0 + zzawr.zzc(1, this.jj) : $i0;
    }

    public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
        return zzv($r1);
    }

    public void writeTo(zzawr $r1) throws IOException {
        if (this.jj != null) {
            $r1.zza(1, this.jj);
        }
        super.writeTo($r1);
    }

    public zznm zzafk() throws  {
        this.jj = null;
        this.cbC = -1;
        return this;
    }

    public zznm zzv(zzawq $r1) throws IOException {
        while (true) {
            int $i0 = $r1.ie();
            switch ($i0) {
                case 0:
                    break;
                case 10:
                    if (this.jj == null) {
                        this.jj = new zznn();
                    }
                    $r1.zza(this.jj);
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
