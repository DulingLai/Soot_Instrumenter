package com.google.android.gms.common.stats;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Debug;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.google.android.gms.common.stats.zzc.zza;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzs;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb {
    private static final Object JL = new Object();
    private static zzb LU;
    private static Integer Ma;
    private final List<String> LV;
    private final List<String> LW;
    private final List<String> LX;
    private final List<String> LY;
    private zze LZ;
    private zze Mb;

    private zzb() throws  {
        zzb com_google_android_gms_common_stats_zzb = this;
        if (getLogLevel() == zzd.LOG_LEVEL_OFF) {
            this.LV = Collections.EMPTY_LIST;
            this.LW = Collections.EMPTY_LIST;
            this.LX = Collections.EMPTY_LIST;
            this.LY = Collections.EMPTY_LIST;
            return;
        }
        String $r4 = (String) zza.Mf.get();
        this.LV = $r4 == null ? Collections.EMPTY_LIST : Arrays.asList($r4.split(","));
        $r4 = (String) zza.Mg.get();
        this.LW = $r4 == null ? Collections.EMPTY_LIST : Arrays.asList($r4.split(","));
        $r4 = (String) zza.Mh.get();
        this.LX = $r4 == null ? Collections.EMPTY_LIST : Arrays.asList($r4.split(","));
        $r4 = (String) zza.Mi.get();
        this.LY = $r4 == null ? Collections.EMPTY_LIST : Arrays.asList($r4.split(","));
        this.LZ = new zze(1024, ((Long) zza.Mj.get()).longValue());
        this.Mb = new zze(1024, ((Long) zza.Mj.get()).longValue());
    }

    private static int getLogLevel() throws  {
        if (Ma == null) {
            try {
                Ma = Integer.valueOf(zzd.zzzz() ? ((Integer) zza.Me.get()).intValue() : zzd.LOG_LEVEL_OFF);
            } catch (SecurityException e) {
                Ma = Integer.valueOf(zzd.LOG_LEVEL_OFF);
            }
        }
        return Ma.intValue();
    }

    private static String zza(StackTraceElement[] $r0, int $i0) throws  {
        if ($i0 + 4 >= $r0.length) {
            return "<bottom of call stack>";
        }
        StackTraceElement $r1 = $r0[$i0 + 4];
        String $r2 = String.valueOf($r1.getClassName());
        String $r3 = String.valueOf($r1.getMethodName());
        return new StringBuilder((String.valueOf($r2).length() + 13) + String.valueOf($r3).length()).append($r2).append(FileUploadSession.SEPARATOR).append($r3).append(":").append($r1.getLineNumber()).toString();
    }

    private void zza(Context $r1, String $r2, int $i0, String $r3, String $r4, String $r5, String $r6) throws  {
        long $l1 = System.currentTimeMillis();
        String $r7 = null;
        if (!((getLogLevel() & zzd.Mo) == 0 || $i0 == 13)) {
            $r7 = zzx(3, 5);
        }
        long $l4 = 0;
        if ((getLogLevel() & zzd.Mq) != 0) {
            $l4 = Debug.getNativeHeapAllocatedSize();
        }
        int $i2;
        if ($i0 == 1 || $i0 == 4 || $i0 == 14) {
            $i2 = new ConnectionEvent($l1, $i0, null, null, null, null, $r7, $r2, SystemClock.elapsedRealtime(), $l4);
        } else {
            $i2 = new ConnectionEvent($l1, $i0, $r3, $r4, $r5, $r6, $r7, $r2, SystemClock.elapsedRealtime(), $l4);
        }
        $r1.startService(new Intent().setComponent(zzd.Mk).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", $r8));
    }

    private void zza(Context $r1, String $r2, String $r3, Intent $r4, int $i0) throws  {
        String $r5 = null;
        if (zzayk() && this.LZ != null) {
            String $r8;
            String $r7;
            if ($i0 != 4 && $i0 != 1) {
                ServiceInfo $r9 = zzd($r1, $r4);
                if ($r9 == null) {
                    Log.w("ConnectionTracker", String.format("Client %s made an invalid request %s", new Object[]{$r3, $r4.toUri(0)}));
                    return;
                }
                $r8 = $r9.processName;
                $r7 = $r9.name;
                String $r11 = zzs.zzazn();
                $r5 = $r11;
                if (zzb($r11, $r3, $r8, $r7)) {
                    this.LZ.zzha($r2);
                } else {
                    return;
                }
            } else if (this.LZ.zzhb($r2)) {
                $r7 = null;
                $r8 = null;
            } else {
                return;
            }
            zza($r1, $r2, $i0, $r5, $r3, $r8, $r7);
        }
    }

    public static zzb zzayj() throws  {
        synchronized (JL) {
            if (LU == null) {
                LU = new zzb();
            }
        }
        return LU;
    }

    private boolean zzayk() throws  {
        return false;
    }

    private String zzb(ServiceConnection $r1) throws  {
        return String.valueOf((((long) Process.myPid()) << 32) | ((long) System.identityHashCode($r1)));
    }

    private boolean zzb(String $r1, String $r2, String $r3, String $r4) throws  {
        return (this.LV.contains($r1) || this.LW.contains($r2) || this.LX.contains($r3) || this.LY.contains($r4) || ($r3.equals($r1) && (getLogLevel() & zzd.Mp) != 0)) ? false : true;
    }

    private boolean zzc(Context $r1, Intent $r2) throws  {
        ComponentName $r3 = $r2.getComponent();
        return $r3 == null ? false : zzd.zzr($r1, $r3.getPackageName());
    }

    private static ServiceInfo zzd(Context $r0, Intent $r1) throws  {
        List<ResolveInfo> $r3 = $r0.getPackageManager().queryIntentServices($r1, 128);
        if ($r3 == null || $r3.size() == 0) {
            Log.w("ConnectionTracker", String.format("There are no handler of this intent: %s\n Stack trace: %s", new Object[]{$r1.toUri(0), zzx(3, 20)}));
            return null;
        } else if ($r3.size() <= 1) {
            return ((ResolveInfo) $r3.get(0)).serviceInfo;
        } else {
            Log.w("ConnectionTracker", String.format("Multiple handlers found for this intent: %s\n Stack trace: %s", new Object[]{$r1.toUri(0), zzx(3, 20)}));
            for (ResolveInfo resolveInfo : $r3) {
                Log.w("ConnectionTracker", resolveInfo.serviceInfo.name);
            }
            return null;
        }
    }

    private static String zzx(int $i1, int $i0) throws  {
        StackTraceElement[] $r2 = Thread.currentThread().getStackTrace();
        StringBuffer $r0 = new StringBuffer();
        $i0 += $i1;
        while ($i1 < $i0) {
            $r0.append(zza($r2, $i1)).append(" ");
            $i1++;
        }
        return $r0.toString();
    }

    @SuppressLint({"UntrackedBindService"})
    public void zza(Context $r1, ServiceConnection $r2) throws  {
        $r1.unbindService($r2);
        zza($r1, zzb($r2), null, null, 1);
    }

    public void zza(Context $r1, ServiceConnection $r2, String $r3, Intent $r4) throws  {
        zza($r1, zzb($r2), $r3, $r4, 3);
    }

    public boolean zza(Context $r1, Intent $r2, ServiceConnection $r3, int $i0) throws  {
        return zza($r1, $r1.getClass().getName(), $r2, $r3, $i0);
    }

    @SuppressLint({"UntrackedBindService"})
    public boolean zza(Context $r1, String $r2, Intent $r3, ServiceConnection $r4, int $i0) throws  {
        if (zzc($r1, $r3)) {
            Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
            return false;
        }
        boolean $z0 = $r1.bindService($r3, $r4, $i0);
        if ($z0) {
            zza($r1, zzb($r4), $r2, $r3, 2);
        }
        return $z0;
    }

    public void zzb(Context $r1, ServiceConnection $r2) throws  {
        zza($r1, zzb($r2), null, null, 4);
    }
}
