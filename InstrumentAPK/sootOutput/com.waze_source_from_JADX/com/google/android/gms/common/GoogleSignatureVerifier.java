package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzs;

/* compiled from: dalvik_source_com.waze.apk */
public class GoogleSignatureVerifier {
    private static GoogleSignatureVerifier BX;
    private final Context mContext;

    private GoogleSignatureVerifier(Context $r1) throws  {
        this.mContext = $r1.getApplicationContext();
    }

    public static GoogleSignatureVerifier getInstance(Context $r0) throws  {
        zzab.zzag($r0);
        synchronized (GoogleSignatureVerifier.class) {
            try {
                if (BX == null) {
                    zzc.init($r0);
                    BX = new GoogleSignatureVerifier($r0);
                }
            } catch (Throwable th) {
                while (true) {
                    Class cls = GoogleSignatureVerifier.class;
                }
            }
        }
        return BX;
    }

    private boolean zzb(PackageInfo $r1, boolean $z0) throws  {
        if ($r1.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return false;
        }
        zzb $r2 = new zzb($r1.signatures[0].toByteArray());
        for (zzs equals : $z0 ? zzc.zzarc() : zzc.zzard()) {
            if ($r2.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPackageGoogleSigned(PackageManager packageManager, PackageInfo $r2) throws  {
        if ($r2 == null) {
            return false;
        }
        if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext)) {
            return zzb($r2, true);
        }
        boolean $z0 = zzb($r2, false);
        if ($z0 || !zzb($r2, true)) {
            return $z0;
        }
        Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        return $z0;
    }

    public boolean isPackageGoogleSigned(PackageManager $r1, String $r2) throws  {
        try {
            return isPackageGoogleSigned($r1, $r1.getPackageInfo($r2, 64));
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public boolean isUidGoogleSigned(PackageManager $r1, int $i0) throws  {
        String[] $r2 = $r1.getPackagesForUid($i0);
        if ($r2 == null) {
            return false;
        }
        if ($r2.length == 0) {
            return false;
        }
        for (String $r3 : $r2) {
            if (isPackageGoogleSigned($r1, $r3)) {
                return true;
            }
        }
        return false;
    }

    public void verifyPackageIsGoogleSigned(PackageManager $r1, String $r2) throws SecurityException {
        if (!isPackageGoogleSigned($r1, $r2)) {
            String $r4 = "Signature check failed for ";
            $r2 = String.valueOf($r2);
            throw new SecurityException($r2.length() != 0 ? $r4.concat($r2) : new String("Signature check failed for "));
        }
    }

    public void verifyUidIsGoogleSigned(PackageManager $r1, int $i0) throws SecurityException {
        if ($r1 == null) {
            throw new SecurityException("Unknown error: invalid Package Manager");
        } else if (!isUidGoogleSigned($r1, $i0)) {
            throw new SecurityException("Uid is not Google Signed");
        }
    }

    zza zza(PackageInfo $r1, zza... $r2) throws  {
        if ($r1.signatures == null) {
            return null;
        }
        if ($r1.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        zzb $r5 = new zzb($r1.signatures[0].toByteArray());
        for (int $i0 = 0; $i0 < $r2.length; $i0++) {
            if ($r2[$i0].equals($r5)) {
                return $r2[$i0];
            }
        }
        return null;
    }

    public boolean zza(PackageInfo $r1, boolean $z0) throws  {
        if (!($r1 == null || $r1.signatures == null)) {
            zza $r4;
            if ($z0) {
                $r4 = zza($r1, zzd.BO);
            } else {
                $r4 = zza($r1, zzd.BO[0]);
            }
            if ($r4 != null) {
                return true;
            }
        }
        return false;
    }

    public boolean zza(PackageManager packageManager, PackageInfo $r2) throws  {
        if ($r2 == null) {
            return false;
        }
        if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext)) {
            return zza($r2, true);
        }
        boolean $z0 = zza($r2, false);
        if ($z0 || !zza($r2, true)) {
            return $z0;
        }
        Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        return $z0;
    }
}
