package com.google.android.gms.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzaa extends ByteArrayOutputStream {
    private final zzu zzbp;

    public zzaa(zzu $r1, int $i0) throws  {
        this.zzbp = $r1;
        this.buf = this.zzbp.zzb(Math.max($i0, 256));
    }

    private void zzd(int $i0) throws  {
        if (this.count + $i0 > this.buf.length) {
            byte[] $r1 = this.zzbp.zzb((this.count + $i0) * 2);
            System.arraycopy(this.buf, 0, $r1, 0, this.count);
            this.zzbp.zza(this.buf);
            this.buf = $r1;
        }
    }

    public void close() throws IOException {
        this.zzbp.zza(this.buf);
        this.buf = null;
        super.close();
    }

    public void finalize() throws  {
        this.zzbp.zza(this.buf);
    }

    public synchronized void write(int $i0) throws  {
        zzd(1);
        super.write($i0);
    }

    public synchronized void write(byte[] $r1, int $i0, int $i1) throws  {
        zzd($i1);
        super.write($r1, $i0, $i1);
    }
}
