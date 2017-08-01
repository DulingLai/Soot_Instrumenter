package com.google.android.gms.internal;

import java.io.Reader;
import java.io.StringReader;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzauz {
    public zzauu zza(Reader $r1) throws zzauv, zzavd {
        try {
            zzawl $r2 = new zzawl($r1);
            zzauu $r3 = zzh($r2);
            if ($r3.hk() || $r2.hC() == zzawm.END_DOCUMENT) {
                return $r3;
            }
            throw new zzavd("Did not consume the entire document.");
        } catch (Throwable $r7) {
            throw new zzavd($r7);
        } catch (Throwable $r8) {
            throw new zzauv($r8);
        } catch (Throwable $r10) {
            throw new zzavd($r10);
        }
    }

    public zzauu zzh(zzawl $r1) throws zzauv, zzavd {
        String $r5;
        boolean $z0 = $r1.isLenient();
        $r1.setLenient(true);
        try {
            zzauu $r2 = zzavv.zzh($r1);
            $r1.setLenient($z0);
            return $r2;
        } catch (StackOverflowError $r3) {
            $r5 = String.valueOf($r1);
            throw new zzauy(new StringBuilder(String.valueOf($r5).length() + 36).append("Failed parsing JSON source: ").append($r5).append(" to Json").toString(), $r3);
        } catch (OutOfMemoryError $r9) {
            $r5 = String.valueOf($r1);
            throw new zzauy(new StringBuilder(String.valueOf($r5).length() + 36).append("Failed parsing JSON source: ").append($r5).append(" to Json").toString(), $r9);
        } catch (Throwable th) {
            $r1.setLenient($z0);
        }
    }

    public zzauu zzym(String $r1) throws zzavd {
        return zza(new StringReader($r1));
    }
}
