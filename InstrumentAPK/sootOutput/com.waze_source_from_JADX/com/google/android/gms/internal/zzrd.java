package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.google.android.gms.C0643R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzai;
import com.google.android.gms.common.internal.zzz;

@Deprecated
/* compiled from: dalvik_source_com.waze.apk */
public final class zzrd {
    private static zzrd FD;
    private static Object zzanj = new Object();
    private final String FE;
    private final Status FF;
    private final String FG;
    private final String FH;
    private final String FI;
    private final boolean FJ;
    private final boolean FK;
    private final String zzciy;

    zzrd(Context $r1) throws  {
        boolean $z0 = true;
        Resources $r2 = $r1.getResources();
        int $i0 = $r2.getIdentifier("google_app_measurement_enable", "integer", $r2.getResourcePackageName(C0643R.string.common_google_play_services_unknown_issue));
        if ($i0 != 0) {
            boolean $z1 = $r2.getInteger($i0) != 0;
            if ($z1) {
                $z0 = false;
            }
            this.FK = $z0;
            $z0 = $z1;
        } else {
            this.FK = false;
        }
        this.FJ = $z0;
        zzai $r4 = new zzai($r1);
        this.FG = $r4.getString("firebase_database_url");
        this.FI = $r4.getString("google_storage_bucket");
        this.FH = $r4.getString("gcm_defaultSenderId");
        this.FE = $r4.getString("google_api_key");
        String $r3 = zzz.zzca($r1);
        String $r5 = $r3;
        if ($r3 == null) {
            $r5 = $r4.getString("google_app_id");
        }
        if (TextUtils.isEmpty($r5)) {
            this.FF = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.zzciy = null;
            return;
        }
        this.zzciy = $r5;
        this.FF = Status.CQ;
    }

    zzrd(String $r1, boolean $z0) throws  {
        this($r1, $z0, null, null, null);
    }

    zzrd(String $r1, boolean $z0, String $r2, String $r3, String $r4) throws  {
        this.zzciy = $r1;
        this.FE = null;
        this.FF = Status.CQ;
        this.FJ = $z0;
        this.FK = !$z0;
        this.FG = $r2;
        this.FH = $r4;
        this.FI = $r3;
    }

    public static String zzaue() throws  {
        return zzge("getGoogleAppId").zzciy;
    }

    public static boolean zzauf() throws  {
        return zzge("isMeasurementExplicitlyDisabled").FK;
    }

    public static Status zzbv(Context $r0) throws  {
        Status r4;
        zzab.zzb((Object) $r0, (Object) "Context must not be null.");
        synchronized (zzanj) {
            if (FD == null) {
                FD = new zzrd($r0);
            }
            r4 = FD.FF;
        }
        return r4;
    }

    public static Status zzc(Context $r0, String $r1, boolean $z0) throws  {
        zzab.zzb((Object) $r0, (Object) "Context must not be null.");
        zzab.zzi($r1, "App ID must be nonempty.");
        synchronized (zzanj) {
            if (FD != null) {
                Status $r3 = FD.zzgd($r1);
                return $r3;
            }
            FD = new zzrd($r1, $z0);
            $r3 = FD.FF;
            return $r3;
        }
    }

    private static zzrd zzge(String $r0) throws  {
        zzrd $r2;
        synchronized (zzanj) {
            if (FD == null) {
                throw new IllegalStateException(new StringBuilder(String.valueOf($r0).length() + 34).append("Initialize must be called before ").append($r0).append(FileUploadSession.SEPARATOR).toString());
            }
            $r2 = FD;
        }
        return $r2;
    }

    Status zzgd(String $r1) throws  {
        if (this.zzciy == null || this.zzciy.equals($r1)) {
            return Status.CQ;
        }
        $r1 = this.zzciy;
        return new Status(10, new StringBuilder(String.valueOf($r1).length() + 97).append("Initialize was called with two different Google App IDs.  Only the first app ID will be used: '").append($r1).append("'.").toString());
    }
}
