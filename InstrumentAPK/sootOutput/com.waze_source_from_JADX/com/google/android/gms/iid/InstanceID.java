package com.google.android.gms.iid;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public class InstanceID {
    public static final String ERROR_BACKOFF = "RETRY_LATER";
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_MISSING_INSTANCEID_SERVICE = "MISSING_INSTANCEID_SERVICE";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String ERROR_TIMEOUT = "TIMEOUT";
    public static final String OPTION_SUBTYPE = "subtype";
    static Map<String, InstanceID> atB = new HashMap();
    private static zzc atC;
    private static Rpc atD;
    static String atH;
    KeyPair atE;
    String atF = "";
    long atG;
    Context mContext;

    protected InstanceID(Context $r1, String $r2, Bundle bundle) throws  {
        this.mContext = $r1.getApplicationContext();
        this.atF = $r2;
    }

    public static InstanceID getInstance(Context $r0) throws  {
        return getInstance($r0, null);
    }

    public static synchronized InstanceID getInstance(Context $r0, Bundle $r1) throws  {
        InstanceID $r8;
        synchronized (InstanceID.class) {
            String $r2;
            if ($r1 == null) {
                try {
                    $r2 = "";
                } catch (Throwable th) {
                    Class cls = InstanceID.class;
                }
            } else {
                $r2 = $r1.getString(OPTION_SUBTYPE);
            }
            if ($r2 == null) {
                $r2 = "";
            }
            $r0 = $r0.getApplicationContext();
            if (atC == null) {
                atC = new zzc($r0);
                atD = new Rpc($r0);
            }
            atH = Integer.toString(zzcz($r0));
            $r8 = (InstanceID) atB.get($r2);
            if ($r8 == null) {
                $r8 = new InstanceID($r0, $r2, $r1);
                atB.put($r2, $r8);
            }
        }
        return $r8;
    }

    static String zza(KeyPair $r0) throws  {
        try {
            byte[] $r2 = MessageDigest.getInstance("SHA1").digest($r0.getPublic().getEncoded());
            $r2[0] = (byte) ((($r2[0] & (byte) 15) + 112) & 255);
            return Base64.encodeToString($r2, 0, 8, 11);
        } catch (NoSuchAlgorithmException e) {
            Log.w("InstanceID", "Unexpected error, device missing required alghorithms");
            return null;
        }
    }

    static String zzad(byte[] $r0) throws  {
        return Base64.encodeToString($r0, 11);
    }

    static int zzcz(Context $r0) throws  {
        try {
            return $r0.getPackageManager().getPackageInfo($r0.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException $r4) {
            String $r2 = String.valueOf($r4);
            Log.w("InstanceID", new StringBuilder(String.valueOf($r2).length() + 38).append("Never happens: can't find own package ").append($r2).toString());
            return 0;
        }
    }

    static String zzda(Context $r0) throws  {
        try {
            return $r0.getPackageManager().getPackageInfo($r0.getPackageName(), 0).versionName;
        } catch (NameNotFoundException $r4) {
            String $r2 = String.valueOf($r4);
            Log.w("InstanceID", new StringBuilder(String.valueOf($r2).length() + 38).append("Never happens: can't find own package ").append($r2).toString());
            return null;
        }
    }

    public void deleteInstanceID() throws IOException {
        deleteToken("*", "*", null);
        zzbrt();
    }

    public void deleteToken(String $r1, String $r2) throws IOException {
        deleteToken($r1, $r2, null);
    }

    public void deleteToken(String $r2, String $r1, Bundle $r3) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        atC.zzi(this.atF, $r2, $r1);
        if ($r3 == null) {
            $r3 = new Bundle();
        }
        $r3.putString("sender", $r2);
        if ($r1 != null) {
            $r3.putString("scope", $r1);
        }
        $r3.putString("subscription", $r2);
        $r3.putString("delete", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        $r3.putString("X-delete", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        $r3.putString(OPTION_SUBTYPE, "".equals(this.atF) ? $r2 : this.atF);
        if (!"".equals(this.atF)) {
            $r2 = this.atF;
        }
        $r3.putString("X-subtype", $r2);
        atD.zzy(atD.zza($r3, zzbrs()));
    }

    public long getCreationTime() throws  {
        if (this.atG == 0) {
            String $r1 = atC.get(this.atF, "cre");
            if ($r1 != null) {
                this.atG = Long.parseLong($r1);
            }
        }
        return this.atG;
    }

    public String getId() throws  {
        return zza(zzbrs());
    }

    public String getToken(String $r1, String $r2) throws IOException {
        return getToken($r1, $r2, null);
    }

    public String getToken(String $r1, String $r2, Bundle $r4) throws IOException {
        boolean $z0 = false;
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        }
        boolean $z1 = true;
        String $r3 = zzbrw() ? null : atC.zzh(this.atF, $r1, $r2);
        if ($r3 != null) {
            return $r3;
        }
        if ($r4 == null) {
            Bundle bundle = new Bundle();
        }
        if ($r4.getString("ttl") != null) {
            $z1 = false;
        }
        if (!"jwt".equals($r4.getString("type"))) {
            $z0 = $z1;
        }
        String $r9 = zzb($r1, $r2, $r4);
        if ($r9 == null || !$z0) {
            return $r9;
        }
        atC.zza(this.atF, $r1, $r2, $r9, atH);
        return $r9;
    }

    public String zzb(String $r1, String $r2, Bundle $r3) throws IOException {
        if ($r2 != null) {
            $r3.putString("scope", $r2);
        }
        $r3.putString("sender", $r1);
        $r2 = "".equals(this.atF) ? $r1 : this.atF;
        if (!$r3.containsKey("legacy.register")) {
            $r3.putString("subscription", $r1);
            $r3.putString(OPTION_SUBTYPE, $r2);
            $r3.putString("X-subscription", $r1);
            $r3.putString("X-subtype", $r2);
        }
        return atD.zzy(atD.zza($r3, zzbrs()));
    }

    KeyPair zzbrs() throws  {
        if (this.atE == null) {
            this.atE = atC.zzkc(this.atF);
        }
        if (this.atE == null) {
            this.atG = System.currentTimeMillis();
            this.atE = atC.zze(this.atF, this.atG);
        }
        return this.atE;
    }

    public void zzbrt() throws  {
        this.atG = 0;
        atC.zzkd(this.atF);
        this.atE = null;
    }

    public zzc zzbru() throws  {
        return atC;
    }

    public Rpc zzbrv() throws  {
        return atD;
    }

    boolean zzbrw() throws  {
        String $r2 = atC.get("appVersion");
        if ($r2 == null) {
            return true;
        }
        if (!$r2.equals(atH)) {
            return true;
        }
        $r2 = atC.get("lastToken");
        if ($r2 == null) {
            return true;
        }
        return (System.currentTimeMillis() / 1000) - Long.valueOf(Long.parseLong($r2)).longValue() > 604800;
    }
}
