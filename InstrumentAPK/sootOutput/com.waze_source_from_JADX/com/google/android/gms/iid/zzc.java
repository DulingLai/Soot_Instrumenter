package com.google.android.gms.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.util.zzw;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc {
    SharedPreferences auf;
    Context zzagf;

    public zzc(Context $r1) throws  {
        this($r1, "com.google.android.gms.appid");
    }

    public zzc(Context $r1, String $r2) throws  {
        this.zzagf = $r1;
        this.auf = $r1.getSharedPreferences($r2, 4);
        $r2 = String.valueOf($r2);
        String $r4 = String.valueOf("-no-backup");
        zzka($r4.length() != 0 ? $r2.concat($r4) : new String($r2));
    }

    private String zzg(String $r1, String $r2, String $r3) throws  {
        String $r4 = String.valueOf("|T|");
        return new StringBuilder((((String.valueOf($r1).length() + 1) + String.valueOf($r4).length()) + String.valueOf($r2).length()) + String.valueOf($r3).length()).append($r1).append($r4).append($r2).append("|").append($r3).toString();
    }

    private void zzka(String $r1) throws  {
        File $r4 = new File(zzw.getNoBackupFilesDir(this.zzagf), $r1);
        if (!$r4.exists()) {
            try {
                if ($r4.createNewFile() && !isEmpty()) {
                    Log.i("InstanceID/Store", "App restored, clearing state");
                    InstanceIDListenerService.zza(this.zzagf, this);
                }
            } catch (IOException $r5) {
                if (Log.isLoggable("InstanceID/Store", 3)) {
                    $r1 = "Error creating file in no backup dir: ";
                    String $r6 = String.valueOf($r5.getMessage());
                    Log.d("InstanceID/Store", $r6.length() != 0 ? $r1.concat($r6) : new String("Error creating file in no backup dir: "));
                }
            }
        }
    }

    synchronized String get(String $r1) throws  {
        return this.auf.getString($r1, null);
    }

    synchronized String get(String $r1, String $r2) throws  {
        SharedPreferences $r3;
        String $r4;
        $r3 = this.auf;
        $r4 = String.valueOf("|S|");
        return $r3.getString(new StringBuilder(((String.valueOf($r1).length() + 0) + String.valueOf($r4).length()) + String.valueOf($r2).length()).append($r1).append($r4).append($r2).toString(), null);
    }

    public boolean isEmpty() throws  {
        return this.auf.getAll().isEmpty();
    }

    synchronized void zza(Editor $r1, String $r2, String $r3, String $r4) throws  {
        String $r5 = String.valueOf("|S|");
        $r1.putString(new StringBuilder(((String.valueOf($r2).length() + 0) + String.valueOf($r5).length()) + String.valueOf($r3).length()).append($r2).append($r5).append($r3).toString(), $r4);
    }

    public synchronized void zza(String $r1, String $r2, String $r3, String $r4, String $r5) throws  {
        $r1 = zzg($r1, $r2, $r3);
        Editor $r7 = this.auf.edit();
        $r7.putString($r1, $r4);
        $r7.putString("appVersion", $r5);
        $r7.putString("lastToken", Long.toString(System.currentTimeMillis() / 1000));
        $r7.commit();
    }

    public synchronized void zzbry() throws  {
        this.auf.edit().clear().commit();
    }

    synchronized KeyPair zze(String $r1, long $l0) throws  {
        KeyPair $r2;
        $r2 = zza.zzbrr();
        Editor $r4 = this.auf.edit();
        zza($r4, $r1, "|P|", InstanceID.zzad($r2.getPublic().getEncoded()));
        zza($r4, $r1, "|K|", InstanceID.zzad($r2.getPrivate().getEncoded()));
        zza($r4, $r1, "cre", Long.toString($l0));
        $r4.commit();
        return $r2;
    }

    public synchronized String zzh(String $r1, String $r2, String $r3) throws  {
        return this.auf.getString(zzg($r1, $r2, $r3), null);
    }

    public synchronized void zzi(String $r1, String $r2, String $r3) throws  {
        $r1 = zzg($r1, $r2, $r3);
        Editor $r5 = this.auf.edit();
        $r5.remove($r1);
        $r5.commit();
    }

    public synchronized void zzkb(String $r1) throws  {
        Editor $r3 = this.auf.edit();
        for (String $r8 : this.auf.getAll().keySet()) {
            if ($r8.startsWith($r1)) {
                $r3.remove($r8);
            }
        }
        $r3.commit();
    }

    public KeyPair zzkc(String $r1) throws  {
        return zzkf($r1);
    }

    void zzkd(String $r1) throws  {
        zzkb(String.valueOf($r1).concat("|"));
    }

    public void zzke(String $r1) throws  {
        zzkb(String.valueOf($r1).concat("|T|"));
    }

    KeyPair zzkf(String $r1) throws  {
        GeneralSecurityException $r11;
        Context context;
        Context $r13;
        String $r2 = get($r1, "|P|");
        $r1 = get($r1, "|K|");
        if ($r1 == null) {
            return null;
        }
        try {
            byte[] $r4 = Base64.decode($r2, 8);
            byte[] $r5 = Base64.decode($r1, 8);
            KeyFactory $r6 = KeyFactory.getInstance("RSA");
            return new KeyPair($r6.generatePublic(new X509EncodedKeySpec($r4)), $r6.generatePrivate(new PKCS8EncodedKeySpec($r5)));
        } catch (InvalidKeySpecException e) {
            $r11 = e;
            $r1 = String.valueOf($r11);
            Log.w("InstanceID/Store", new StringBuilder(String.valueOf($r1).length() + 19).append("Invalid key stored ").append($r1).toString());
            context = this.zzagf;
            $r13 = context;
            InstanceIDListenerService.zza(context, this);
            return null;
        } catch (NoSuchAlgorithmException e2) {
            $r11 = e2;
            $r1 = String.valueOf($r11);
            Log.w("InstanceID/Store", new StringBuilder(String.valueOf($r1).length() + 19).append("Invalid key stored ").append($r1).toString());
            context = this.zzagf;
            $r13 = context;
            InstanceIDListenerService.zza(context, this);
            return null;
        }
    }
}
