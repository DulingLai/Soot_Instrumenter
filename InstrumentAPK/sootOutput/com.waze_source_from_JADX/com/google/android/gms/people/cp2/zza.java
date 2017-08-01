package com.google.android.gms.people.cp2;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.RawContacts;
import android.text.TextUtils;
import com.google.android.gms.people.internal.zzp;
import com.google.android.gms.people.internal.zzq;
import java.util.Arrays;

/* compiled from: dalvik_source_com.waze.apk */
public class zza {
    private static final String[] aMe = new String[]{"_id"};
    private static final String[] aMf = new String[]{"contact_id"};
    private static final String[] aMg = new String[]{"lookup"};
    private static final String[] aMh = new String[]{"photo_uri"};
    static final String[] aMi = new String[]{"photo_id"};
    static final String[] aMj = new String[]{"data15"};

    public static Uri getContactLookupUriFromAndroidContactId(Context $r0, long $l0) throws  {
        String $r2 = zza($r0, $l0, aMg);
        return $r2 == null ? null : Contacts.CONTENT_LOOKUP_URI.buildUpon().appendPath($r2).appendPath(String.valueOf($l0)).build();
    }

    private static long zza(Context $r0, String $r1, String $r2, String[] $r3) throws  {
        if (TextUtils.isEmpty($r1)) {
            return -1;
        }
        $r2 = zzq.zzrh($r2);
        Cursor $r8 = $r0.getContentResolver().query(RawContacts.CONTENT_URI, $r3, VERSION.SDK_INT >= 15 ? "account_name=?1 AND account_type='com.google' AND sourceid=?2 AND data_set IS NULL" : "account_name=?1 AND account_type='com.google' AND sourceid=?2", zzq.zzbt($r1, $r2), null);
        if ($r8 == null) {
            zzp.zzan(AndroidContactsUtils.TAG, "Contacts query failed.");
            return -1;
        }
        try {
            if ($r8.moveToFirst()) {
                long $l0 = (long) $r8.getInt(0);
                return $l0;
            }
            $r8.close();
            zzp.zzal(AndroidContactsUtils.TAG, "Raw contact not found.");
            return -1;
        } finally {
            $r8.close();
        }
    }

    private static String zza(Context $r0, long $l0, String[] $r1) throws  {
        if ($l0 < 0) {
            return null;
        }
        Cursor $r4 = $r0.getContentResolver().query(ContentUris.withAppendedId(Contacts.CONTENT_URI, $l0), $r1, null, null, null);
        if ($r4 == null) {
            zzp.zzan(AndroidContactsUtils.TAG, "Contacts query failed.");
            return null;
        }
        try {
            if ($r4.moveToFirst()) {
                String $r5 = $r4.getString(0);
                if (TextUtils.isEmpty($r5)) {
                    $r5 = String.valueOf(Arrays.toString($r1));
                    zzp.zzal(AndroidContactsUtils.TAG, new StringBuilder(String.valueOf($r5).length() + 44).append("Contacts query with projection = ").append($r5).append(" not found.").toString());
                    return null;
                }
                $r4.close();
                return $r5;
            }
            $r4.close();
            zzp.zzal(AndroidContactsUtils.TAG, "Contact not found.");
            return null;
        } finally {
            $r4.close();
        }
    }

    private static long zzb(Context $r0, String $r1, String $r2, String[] $r3) throws  {
        if (TextUtils.isEmpty($r1)) {
            return -1;
        }
        if (VERSION.SDK_INT < 15) {
            return -1;
        }
        $r2 = zzq.zzrb($r2);
        Cursor $r7 = $r0.getContentResolver().query(RawContacts.CONTENT_URI, $r3, "account_name=?1 AND account_type='com.google' AND sourceid=?2 AND data_set IS 'plus'", zzq.zzbt($r1, $r2), null);
        if ($r7 == null) {
            zzp.zzan(AndroidContactsUtils.TAG, "Contacts query failed.");
            return -1;
        }
        try {
            if ($r7.moveToFirst()) {
                long $l0 = (long) $r7.getInt(0);
                return $l0;
            }
            $r7.close();
            zzp.zzal(AndroidContactsUtils.TAG, "Raw contact not found.");
            return -1;
        } finally {
            $r7.close();
        }
    }

    public static long zzh(Context $r0, String $r1, String $r2) throws  {
        return zza($r0, $r1, $r2, aMf);
    }

    public static long zzi(Context $r0, String $r1, String $r2) throws  {
        return zzb($r0, $r1, $r2, aMf);
    }

    public static String zzj(Context $r0, String $r1, String $r2) throws  {
        return zza($r0, zzh($r0, $r1, $r2), aMh);
    }
}
