package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzad {

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza extends zzaws<zza> {
        public String stackTrace = null;
        public String zzcj = null;
        public Long zzck = null;
        public String zzcl = null;
        public String zzcm = null;
        public Long zzcn = null;
        public Long zzco = null;
        public String zzcp = null;
        public Long zzcq = null;
        public String zzcr = null;

        protected int computeSerializedSize() throws  {
            int $i0 = super.computeSerializedSize();
            int $i1 = $i0;
            if (this.zzcj != null) {
                $i1 = $i0 + zzawr.zzt(1, this.zzcj);
            }
            if (this.zzck != null) {
                $i1 += zzawr.zzi(2, this.zzck.longValue());
            }
            if (this.stackTrace != null) {
                $i1 += zzawr.zzt(3, this.stackTrace);
            }
            if (this.zzcl != null) {
                $i1 += zzawr.zzt(4, this.zzcl);
            }
            if (this.zzcm != null) {
                $i1 += zzawr.zzt(5, this.zzcm);
            }
            if (this.zzcn != null) {
                $i1 += zzawr.zzi(6, this.zzcn.longValue());
            }
            if (this.zzco != null) {
                $i1 += zzawr.zzi(7, this.zzco.longValue());
            }
            if (this.zzcp != null) {
                $i1 += zzawr.zzt(8, this.zzcp);
            }
            if (this.zzcq != null) {
                $i1 += zzawr.zzi(9, this.zzcq.longValue());
            }
            return this.zzcr != null ? $i1 + zzawr.zzt(10, this.zzcr) : $i1;
        }

        public /* synthetic */ zzawz mergeFrom(zzawq $r1) throws IOException {
            return zza($r1);
        }

        public void writeTo(zzawr $r1) throws IOException {
            if (this.zzcj != null) {
                $r1.zzs(1, this.zzcj);
            }
            if (this.zzck != null) {
                $r1.zzf(2, this.zzck.longValue());
            }
            if (this.stackTrace != null) {
                $r1.zzs(3, this.stackTrace);
            }
            if (this.zzcl != null) {
                $r1.zzs(4, this.zzcl);
            }
            if (this.zzcm != null) {
                $r1.zzs(5, this.zzcm);
            }
            if (this.zzcn != null) {
                $r1.zzf(6, this.zzcn.longValue());
            }
            if (this.zzco != null) {
                $r1.zzf(7, this.zzco.longValue());
            }
            if (this.zzcp != null) {
                $r1.zzs(8, this.zzcp);
            }
            if (this.zzcq != null) {
                $r1.zzf(9, this.zzcq.longValue());
            }
            if (this.zzcr != null) {
                $r1.zzs(10, this.zzcr);
            }
            super.writeTo($r1);
        }

        public zza zza(zzawq $r1) throws IOException {
            while (true) {
                int $i0 = $r1.ie();
                switch ($i0) {
                    case 0:
                        break;
                    case 10:
                        this.zzcj = $r1.readString();
                        continue;
                    case 16:
                        this.zzck = Long.valueOf($r1.ih());
                        continue;
                    case 26:
                        this.stackTrace = $r1.readString();
                        continue;
                    case 34:
                        this.zzcl = $r1.readString();
                        continue;
                    case 42:
                        this.zzcm = $r1.readString();
                        continue;
                    case 48:
                        this.zzcn = Long.valueOf($r1.ih());
                        continue;
                    case 56:
                        this.zzco = Long.valueOf($r1.ih());
                        continue;
                    case 66:
                        this.zzcp = $r1.readString();
                        continue;
                    case 72:
                        this.zzcq = Long.valueOf($r1.ih());
                        continue;
                    case 82:
                        this.zzcr = $r1.readString();
                        continue;
                    default:
                        if (!super.zza($r1, $i0)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }
    }
}
