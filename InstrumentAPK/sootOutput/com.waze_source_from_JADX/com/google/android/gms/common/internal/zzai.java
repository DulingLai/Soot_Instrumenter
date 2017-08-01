package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.C0643R;

/* compiled from: dalvik_source_com.waze.apk */
public class zzai {
    private final Resources Kn;
    private final String Ko = this.Kn.getResourcePackageName(C0643R.string.common_google_play_services_unknown_issue);

    public zzai(Context $r1) throws  {
        zzab.zzag($r1);
        this.Kn = $r1.getResources();
    }

    public String getString(String $r1) throws  {
        int $i0 = this.Kn.getIdentifier($r1, "string", this.Ko);
        return $i0 == 0 ? null : this.Kn.getString($i0);
    }
}
