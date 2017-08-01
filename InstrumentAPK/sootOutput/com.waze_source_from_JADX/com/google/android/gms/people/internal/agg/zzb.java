package com.google.android.gms.people.internal.agg;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Groups;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.people.internal.zzp;
import com.google.android.gms.people.internal.zzt;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzb {
    public static final String[] aRI = new String[]{"contact_id", "display_name", "mimetype", "data1", "data2", "data3"};
    private static boolean aRJ = false;
    private static boolean aRK = false;

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zza {
        public static final Uri CONTENT_FILTER_URI = Uri.withAppendedPath(CONTENT_URI, "filter");
        public static final Uri CONTENT_URI = Uri.withAppendedPath(Data.CONTENT_URI, "contactables");
    }

    public static final void zza(zzt $r0) throws  {
        $r0.zzrj("(mimetype IN ('vnd.android.cursor.item/email_v2','vnd.android.cursor.item/phone_v2'))");
    }

    public static final void zza(zzt $r0, boolean $z0, Context $r1) throws  {
        if (!$z0) {
            if (VERSION.SDK_INT < 11) {
                $r0.zzrj("(in_visible_group=1)");
            } else if (zzdl($r1)) {
                $r0.zzrj("(contact_id IN (SELECT _id FROM default_directory))");
            }
        }
        String $r2 = zzcfp();
        if (!TextUtils.isEmpty($r2)) {
            $r0.zzrj($r2);
        }
    }

    public static boolean zzc(Cursor $r0) throws  {
        if ($r0.isAfterLast()) {
            return false;
        }
        long $l0 = $r0.getLong(0);
        while ($r0.moveToNext()) {
            if ($l0 != $r0.getLong(0)) {
                return true;
            }
        }
        return false;
    }

    public static final String zzcfp() throws  {
        return VERSION.SDK_INT < 14 ? null : "((data_set IS NULL) OR (account_type='com.google' AND data_set!='plus'))";
    }

    public static boolean zzd(Cursor $r0) throws  {
        boolean $z0 = true;
        zzab.zzbn(!$r0.isBeforeFirst());
        if ($r0.isAfterLast()) {
            return false;
        }
        long $l0 = $r0.getLong(0);
        if (!$r0.moveToNext()) {
            return false;
        }
        if ($l0 != $r0.getLong(0)) {
            $z0 = false;
        }
        return $z0;
    }

    private static final synchronized boolean zzdl(Context $r0) throws  {
        boolean $z0;
        Cursor cursor;
        Throwable $r8;
        synchronized (zzb.class) {
            try {
                if (aRK) {
                    $z0 = aRJ;
                } else {
                    aRK = true;
                    try {
                        Cursor $r3 = $r0.getContentResolver().query(Groups.CONTENT_URI, null, "EXISTS (SELECT _id FROM default_directory LIMIT 1)", null, null);
                        cursor = $r3;
                        if ($r3 != null) {
                            aRJ = true;
                        }
                        if ($r3 != null) {
                            $r3.close();
                        }
                    } catch (Exception $r5) {
                        cursor = null;
                        zzp.zzan("PeopleAggregator", "Error occurred when checking for default_directory table.");
                        zzp.zzal("PeopleAggregator", $r5.getMessage());
                    } catch (Throwable th) {
                        $r8 = th;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw $r8;
                    }
                    $z0 = aRJ;
                }
            } catch (Throwable th2) {
                Class cls = zzb.class;
            }
        }
        return $z0;
    }
}
