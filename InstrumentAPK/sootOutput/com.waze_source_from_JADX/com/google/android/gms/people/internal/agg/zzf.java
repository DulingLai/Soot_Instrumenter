package com.google.android.gms.people.internal.agg;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.MergeCursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.people.PeopleConstants.Endpoints;
import com.google.android.gms.people.internal.agg.zzd.zzd;
import com.google.android.gms.people.internal.zzh;
import com.google.android.gms.people.internal.zzi;
import com.google.android.gms.people.internal.zzp;
import com.google.android.gms.people.internal.zzt;
import com.google.android.gms.people.internal.zzv;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: dalvik_source_com.waze.apk */
class zzf extends zzd {
    public static final String[] aSl = new String[]{"contact_id"};
    private final String zzaoj;

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza extends CursorWrapper {
        private int aSm;

        public zza(Cursor $r1, int $i0) throws  {
            super($r1);
            this.aSm = $i0;
        }

        public int getCount() throws  {
            return Math.min(super.getCount(), this.aSm);
        }
    }

    public zzf(Context $r1, zzd $r2, boolean $z0, int $i0, Bundle $r3, Bundle $r4, String $r5) throws  {
        super($r1, $r2, $z0, $i0, $r3, $r4, null);
        this.zzaoj = $r5;
    }

    private Cursor zzcfz() throws  {
        Uri $r1 = Phone.CONTENT_FILTER_URI.buildUpon().appendPath(this.zzaoj).appendQueryParameter("limit", Integer.toString(100)).build();
        Cursor $r7 = this.mContext.getContentResolver().query($r1, aSl, "(data1 IS NOT NULL AND data1!='')", null, null);
        $r1 = Email.CONTENT_FILTER_URI.buildUpon().appendPath(this.zzaoj).appendQueryParameter("limit", Integer.toString(100)).build();
        Cursor $r8 = this.mContext.getContentResolver().query($r1, aSl, "(data1 IS NOT NULL AND data1!='')", null, null);
        zza com_google_android_gms_people_internal_agg_zzf_zza = new zza($r7, 100);
        com_google_android_gms_people_internal_agg_zzf_zza = new zza($r8, 100);
        return new MergeCursor(new Cursor[]{com_google_android_gms_people_internal_agg_zzf_zza, com_google_android_gms_people_internal_agg_zzf_zza});
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
        ArrayList $r13 = zzb.zzayv();
        $r1.zzaco(-1);
        while ($r1.moveToNext()) {
            $i0 = $r1.getPosition();
            String $r12 = $r1.getString(Endpoints.KEY_TARGET_GAIA_ID);
            $r4.zzacl($i0);
            $r13.add($r12);
            if ($r12 == null || com_google_android_gms_people_internal_zzv.zzrm($r12) == 0) {
                $r5.zzcer();
            } else {
                $r5.zza(com_google_android_gms_people_internal_zzv, $r12);
            }
        }
        $r3.moveToPosition(0);
        while (!$r3.isAfterLast()) {
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
        this.aRZ.zzrl("merge finish");
        return new zza($r1.aSj, $r3, this.mContext, $r4.size(), $r4, $r5, $r13, hashMap, this.aLH, this.aRW, this.aRX);
    }

    protected Cursor zzcft() throws  {
        Cursor $r1;
        boolean $z0 = false;
        zzt $r5;
        if (!zzd.aSf || VERSION.SDK_INT < 18) {
            $r5 = new zzt();
            zzb.zza($r5, this.aLE, this.mContext);
            zzb.zza($r5);
            this.aRZ.zzrl("lookup start");
            $r1 = zzcfz();
            if ($r1 == null) {
                return null;
            }
            try {
                int $i0 = $r1.getCount();
                this.aRZ.zzrl("lookup finish");
                if ($i0 == 0) {
                    return null;
                }
                $r5.zzrj("contact_id IN (");
                String $r4 = "";
                while ($r1.moveToNext()) {
                    $r5.zzri($r4);
                    $r5.zzri(Long.toString($r1.getLong(0)));
                    $r4 = ",";
                }
                $r5.zzri(")");
                $r1.close();
                $r1 = this.mContext.getContentResolver().query(Data.CONTENT_URI, zzb.aRI, $r5.toString(), null, "display_name COLLATE LOCALIZED,contact_id");
            } finally {
                $r1.close();
            }
        } else {
            Builder $r3 = com.google.android.gms.people.internal.agg.zzb.zza.CONTENT_FILTER_URI.buildUpon().appendPath(this.zzaoj);
            if (!this.aLE) {
                $z0 = true;
            }
            Uri $r2 = $r3.appendQueryParameter("visible_contacts_only", String.valueOf($z0)).build();
            $r5 = new zzt();
            $r5.zzrj(zzb.zzcfp());
            $r5.zzrj("(data1 IS NOT NULL AND data1!='')");
            $r1 = this.mContext.getContentResolver().query($r2, zzb.aRI, $r5.toString(), null, "display_name COLLATE LOCALIZED,contact_id");
        }
        if ($r1 == null) {
            return $r1;
        }
        $r1.getCount();
        return $r1;
    }
}
