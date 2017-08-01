package com.google.android.gms.common.stats;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.stats.zzc.zzb;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzi;
import dalvik.annotation.Signature;
import java.util.Arrays;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzh {
    private static zzh MF = new zzh();
    private static Boolean MG;
    private static String TAG = "WakeLockTracker";

    public static zzh zzayu() throws  {
        return MF;
    }

    private static boolean zzcd(Context $r0) throws  {
        if (MG == null) {
            MG = Boolean.valueOf(zzce($r0));
        }
        return MG.booleanValue();
    }

    private static boolean zzce(Context $r0) throws  {
        try {
            if (!zzd.zzzz()) {
                return GoogleSignatureVerifier.getInstance($r0).isUidGoogleSigned($r0.getPackageManager(), Process.myUid());
            } else {
                return ((Integer) zzb.Me.get()).intValue() != zzd.LOG_LEVEL_OFF;
            }
        } catch (SecurityException e) {
            return false;
        }
    }

    public void zza(Context $r1, Intent $r2, String $r3, String $r4, String $r5, int $i0, String $r6) throws  {
        zza($r1, $r2, $r3, $r4, $r5, $i0, Arrays.asList(new String[]{$r6}));
    }

    public void zza(@Signature({"(", "Landroid/content/Context;", "Landroid/content/Intent;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Landroid/content/Intent;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) Intent $r2, @Signature({"(", "Landroid/content/Context;", "Landroid/content/Intent;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Landroid/content/Context;", "Landroid/content/Intent;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r4, @Signature({"(", "Landroid/content/Context;", "Landroid/content/Intent;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r5, @Signature({"(", "Landroid/content/Context;", "Landroid/content/Intent;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(", "Landroid/content/Context;", "Landroid/content/Intent;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r6) throws  {
        zza($r1, $r2.getStringExtra("WAKE_LOCK_KEY"), 7, $r3, $r4, $r5, $i0, $r6);
    }

    public void zza(@Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i0, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r4, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r5, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) int $i1, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r6) throws  {
        zza($r1, $r2, $i0, $r3, $r4, $r5, $i1, $r6, 0);
    }

    public void zza(@Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J)V"}) String $r2, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J)V"}) int $i0, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J)V"}) String $r3, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J)V"}) String $r4, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J)V"}) String $r5, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J)V"}) int $i1, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J)V"}) List<String> $r6, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "I", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "I", "Ljava/util/List", "<", "Ljava/lang/String;", ">;J)V"}) long $l2) throws  {
        if (!zzcd($r1)) {
            return;
        }
        if (TextUtils.isEmpty($r2)) {
            $r3 = TAG;
            $r4 = "missing wakeLock key. ";
            $r2 = String.valueOf($r2);
            if ($r2.length() != 0) {
                $r2 = $r4.concat($r2);
            } else {
                String str = new String("missing wakeLock key. ");
            }
            Log.e($r3, $r2);
            return;
        }
        long $l3 = System.currentTimeMillis();
        if (7 == $i0 || 8 == $i0 || 10 == $i0 || 11 == $i0) {
            WakeLockEvent wakeLockEvent = new WakeLockEvent($l3, $i0, $r3, $i1, zzf.zzac($r6), $r2, SystemClock.elapsedRealtime(), zzi.zzch($r1), $r4, zzf.zzhc($r1.getPackageName()), zzi.zzci($r1), $l2, $r5);
            try {
                $r1.startService(new Intent().setComponent(zzd.Mk).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", wakeLockEvent));
            } catch (Throwable $r11) {
                Log.wtf(TAG, $r11);
            }
        }
    }

    public void zzf(Context $r1, Intent $r2) throws  {
        zza($r1, $r2.getStringExtra("WAKE_LOCK_KEY"), 8, null, null, null, 0, null);
    }
}
