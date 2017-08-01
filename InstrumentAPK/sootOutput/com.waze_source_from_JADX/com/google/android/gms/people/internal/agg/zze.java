package com.google.android.gms.people.internal.agg;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.people.PeopleConstants.Endpoints;
import com.google.android.gms.people.internal.agg.zzb.zza;
import com.google.android.gms.people.internal.agg.zzd.zzd;
import com.google.android.gms.people.internal.zzh;
import com.google.android.gms.people.internal.zzi;
import com.google.android.gms.people.internal.zzp;
import com.google.android.gms.people.internal.zzt;
import com.google.android.gms.people.internal.zzv;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: dalvik_source_com.waze.apk */
class zze extends zzd {
    private static final String[] aSk = new String[]{"contact_id"};

    public zze(Context $r1, zzd $r2, boolean $z0, int $i0, Bundle $r3, Bundle $r4, String $r5) throws  {
        super($r1, $r2, $z0, $i0, $r3, $r4, $r5);
    }

    private String zzcfx() throws  {
        if (!this.aRY) {
            return "";
        }
        StringBuilder $r1 = new StringBuilder();
        $r1.append("contact_id IN(");
        Cursor $r7 = this.mContext.getContentResolver().query(Data.CONTENT_URI, aSk, zzcfy(), null, null);
        if ($r7 == null) {
            return null;
        }
        boolean $z0 = true;
        while ($r7.moveToNext()) {
            if (!$z0) {
                $r1.append(",");
            }
            $r1.append($r7.getLong(0));
            $z0 = false;
            try {
            } finally {
                $r7.close();
            }
        }
        $r1.append(")");
        return $r1.toString();
    }

    private String zzcfy() throws  {
        zzab.zzbm(this.aRY);
        DataHolder $r2 = zzcfq();
        zzab.zzag($r2);
        StringBuilder $r1 = new StringBuilder();
        $r1.append("data1 IN(");
        zzc $r3 = new zzc($r2);
        boolean $z0 = true;
        while ($r3.moveToNext()) {
            if (!$z0) {
                $r1.append(",");
            }
            $z0 = false;
            DatabaseUtils.appendEscapedSQLString($r1, $r3.getString("value"));
        }
        $r1.append(")");
        return $r1.toString();
    }

    protected zza zza(zzc $r1, zzc $r2, Cursor $r3) throws  {
        zzab.zzag($r1);
        zzab.zzag($r3);
        zzi $r4 = new zzi();
        zzi $r5 = new zzi();
        int $i0 = $r1.getCount();
        $r3.getCount();
        HashMap $r7 = new HashMap();
        this.aRZ.zzrl("people-map start");
        zzd.zza($r1, $r7);
        this.aRZ.zzrl("people-map finish");
        zzv com_google_android_gms_people_internal_zzv = new zzv();
        zzh com_google_android_gms_people_internal_zzh = new zzh();
        HashMap hashMap = new HashMap();
        zzb($r2, hashMap);
        this.aRZ.zzrl("contact-map start");
        int $i1 = zza($r3, com_google_android_gms_people_internal_zzv, com_google_android_gms_people_internal_zzh, hashMap);
        this.aRZ.zzrl("contact-map finish");
        if (zzp.zzcfd()) {
            zzp.zzal("PeopleAggregator", "#people=" + $i0 + ", #contacts=" + $i1);
        }
        this.aRZ.zzrl("merge start");
        $r1.zzaco(0);
        $r3.moveToPosition(0);
        ArrayList $r13 = zzb.zzayv();
        while (true) {
            boolean $z0 = !$r1.isAfterLast();
            boolean $z1 = !$r3.isAfterLast();
            if ($z0 || $z1) {
                String $r12;
                $i0 = ($z0 && $z1) ? zzbu($z0 ? $r1.getString("name") : null, $z1 ? $r3.getString(1) : null) : $z0 ? -1 : 1;
                if ($i0 <= 0) {
                    $i1 = $r1.getPosition();
                    $r12 = $r1.getString(Endpoints.KEY_TARGET_GAIA_ID);
                    $r4.zzacl($i1);
                    $r13.add($r12);
                    if ($r12 == null || com_google_android_gms_people_internal_zzv.zzrm($r12) == 0) {
                        $r5.zzcer();
                    } else {
                        $r5.zza(com_google_android_gms_people_internal_zzv, $r12);
                    }
                    $r1.moveToNext();
                }
                if ($i0 >= 0) {
                    $i0 = $r3.getPosition();
                    $i1 = com_google_android_gms_people_internal_zzh.zzack($i0);
                    if ($i1 == 0) {
                        $r4.zzcer();
                        $r5.zzacl($i0);
                        $r13.add(null);
                    } else {
                        for (int $i2 = 0; $i2 < $i1; $i2++) {
                            $r12 = com_google_android_gms_people_internal_zzh.zzai($i0, $i2);
                            if (!$r7.containsKey($r12)) {
                                $r4.zzcer();
                                $r5.zzacl($i0);
                                $r13.add($r12);
                            }
                        }
                    }
                    zzb.zzc($r3);
                }
            } else {
                this.aRZ.zzrl("merge finish");
                return new zza($r1.aSj, $r3, this.mContext, $r4.size(), $r4, $r5, $r13, hashMap, this.aLH, this.aRW, this.aRX);
            }
        }
    }

    protected Cursor zzcft() throws  {
        String $r2 = zzcfx();
        if ($r2 == null) {
            return null;
        }
        Cursor $r1;
        zzt $r6;
        Context $r7;
        if (!zzd.aSf || VERSION.SDK_INT < 18) {
            $r6 = new zzt();
            boolean $z0 = this.aLE;
            $r7 = this.mContext;
            zzb.zza($r6, $z0, $r7);
            zzb.zza($r6);
            $r6.zzrj($r2);
            $r6.zzrj("(data1 IS NOT NULL AND data1!='')");
            $r7 = this.mContext;
            $r1 = $r7.getContentResolver().query(Data.CONTENT_URI, zzb.aRI, $r6.toString(), null, "display_name COLLATE LOCALIZED,contact_id");
        } else {
            Uri $r3 = zza.CONTENT_URI.buildUpon().appendQueryParameter("visible_contacts_only", String.valueOf(!this.aLE)).build();
            $r6 = new zzt();
            $r6.zzrj(zzb.zzcfp());
            $r6.zzrj($r2);
            $r6.zzrj("(data1 IS NOT NULL AND data1!='')");
            $r7 = this.mContext;
            $r1 = $r7.getContentResolver().query($r3, zzb.aRI, $r6.toString(), null, "display_name COLLATE LOCALIZED,contact_id");
        }
        if ($r1 == null) {
            return $r1;
        }
        $r1.getCount();
        return $r1;
    }
}
