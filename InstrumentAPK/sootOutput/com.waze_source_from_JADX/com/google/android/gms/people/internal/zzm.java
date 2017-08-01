package com.google.android.gms.people.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzab;
import java.util.regex.Pattern;

/* compiled from: dalvik_source_com.waze.apk */
public class zzm {
    public static final zzm aQh = new zzm();
    private Pattern[] aQi = new Pattern[0];
    private String[] aQj = new String[0];

    private zzm() throws  {
    }

    public synchronized void zza(String[] $r1, String[] $r2) throws  {
        synchronized (this) {
            zzab.zzbn($r1.length == $r2.length);
            this.aQi = new Pattern[$r1.length];
            this.aQj = $r2;
            for (int $i0 = 0; $i0 < $r1.length; $i0++) {
                this.aQi[$i0] = Pattern.compile($r1[$i0]);
            }
        }
    }

    public void zzau(Bundle $r1) throws  {
        zza($r1.getStringArray("config.url_uncompress.patterns"), $r1.getStringArray("config.url_uncompress.replacements"));
    }

    public synchronized String zzqx(String $r1) throws  {
        if (TextUtils.isEmpty($r1)) {
            $r1 = null;
        } else {
            for (int $i0 = 0; $i0 < this.aQi.length; $i0++) {
                $r1 = this.aQi[$i0].matcher($r1).replaceAll(this.aQj[$i0]);
            }
        }
        return $r1;
    }
}
