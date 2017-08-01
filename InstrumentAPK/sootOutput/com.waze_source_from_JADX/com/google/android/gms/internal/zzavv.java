package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Writer;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzavv {

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza extends Writer {
        private final Appendable bYP;
        private final zza bYQ;

        /* compiled from: dalvik_source_com.waze.apk */
        static class zza implements CharSequence {
            char[] bYR;

            zza() throws  {
            }

            public char charAt(int $i0) throws  {
                return this.bYR[$i0];
            }

            public int length() throws  {
                return this.bYR.length;
            }

            public CharSequence subSequence(int $i0, int $i1) throws  {
                return new String(this.bYR, $i0, $i1 - $i0);
            }
        }

        private zza(Appendable $r1) throws  {
            this.bYQ = new zza();
            this.bYP = $r1;
        }

        public void close() throws  {
        }

        public void flush() throws  {
        }

        public void write(int $i0) throws IOException {
            this.bYP.append((char) $i0);
        }

        public void write(char[] $r1, int $i0, int $i1) throws IOException {
            this.bYQ.bYR = $r1;
            this.bYP.append(this.bYQ, $i0, $i0 + $i1);
        }
    }

    public static Writer zza(Appendable $r0) throws  {
        return $r0 instanceof Writer ? (Writer) $r0 : new zza($r0);
    }

    public static void zzb(zzauu $r0, zzawn $r1) throws IOException {
        zzawj.cao.zza($r1, $r0);
    }

    public static zzauu zzh(zzawl $r0) throws zzauy {
        boolean $z0 = true;
        try {
            $r0.hC();
            $z0 = false;
            return (zzauu) zzawj.cao.zzb($r0);
        } catch (Throwable $r4) {
            if ($z0) {
                return zzauw.bXK;
            }
            throw new zzavd($r4);
        } catch (Throwable $r6) {
            throw new zzavd($r6);
        } catch (Throwable $r7) {
            throw new zzauv($r7);
        } catch (Throwable $r9) {
            throw new zzavd($r9);
        }
    }
}
