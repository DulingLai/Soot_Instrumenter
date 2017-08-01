package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.WorkSource;
import android.util.Log;
import com.google.android.gms.people.PeopleConstants.Endpoints;
import dalvik.annotation.Signature;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzz {
    private static final Method MZ = zzazq();
    private static final Method Na = zzazr();
    private static final Method Nb = zzazs();
    private static final Method Nc = zzazt();
    private static final Method Nd = zzazu();

    public static int zza(WorkSource $r0) throws  {
        if (Nb != null) {
            try {
                return ((Integer) Nb.invoke($r0, new Object[0])).intValue();
            } catch (Exception $r5) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", $r5);
            }
        }
        return 0;
    }

    public static String zza(WorkSource $r0, int $i0) throws  {
        if (Nd != null) {
            try {
                return (String) Nd.invoke($r0, new Object[]{Integer.valueOf($i0)});
            } catch (Exception $r6) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", $r6);
            }
        }
        return null;
    }

    public static void zza(WorkSource $r0, int $i0, String $r1) throws  {
        if (Na != null) {
            if ($r1 == null) {
                $r1 = "";
            }
            try {
                Na.invoke($r0, new Object[]{Integer.valueOf($i0), $r1});
            } catch (Exception $r5) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", $r5);
            }
        } else if (MZ != null) {
            try {
                MZ.invoke($r0, new Object[]{Integer.valueOf($i0)});
            } catch (Exception $r6) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", $r6);
            }
        }
    }

    private static Method zzazq() throws  {
        try {
            return WorkSource.class.getMethod("add", new Class[]{Integer.TYPE});
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zzazr() throws  {
        if (!zzr.zzazg()) {
            return null;
        }
        try {
            return WorkSource.class.getMethod("add", new Class[]{Integer.TYPE, String.class});
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zzazs() throws  {
        try {
            return WorkSource.class.getMethod("size", new Class[0]);
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zzazt() throws  {
        try {
            return WorkSource.class.getMethod(Endpoints.ENDPOINT_GET, new Class[]{Integer.TYPE});
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zzazu() throws  {
        if (!zzr.zzazg()) {
            return null;
        }
        try {
            return WorkSource.class.getMethod("getName", new Class[]{Integer.TYPE});
        } catch (Exception e) {
            return null;
        }
    }

    public static List<String> zzb(@Signature({"(", "Landroid/os/WorkSource;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) WorkSource $r0) throws  {
        int $i1 = $r0 == null ? 0 : zza($r0);
        if ($i1 == 0) {
            return Collections.EMPTY_LIST;
        }
        ArrayList $r1 = new ArrayList();
        for (int $i0 = 0; $i0 < $i1; $i0++) {
            String $r2 = zza($r0, $i0);
            if (!zzv.zzhh($r2)) {
                $r1.add($r2);
            }
        }
        return $r1;
    }

    public static boolean zzcj(Context $r0) throws  {
        if ($r0 == null) {
            return false;
        }
        PackageManager $r1 = $r0.getPackageManager();
        if ($r1 == null) {
            return false;
        }
        return $r1.checkPermission("android.permission.UPDATE_DEVICE_STATS", $r0.getPackageName()) == 0;
    }

    public static WorkSource zzf(int $i0, String $r0) throws  {
        WorkSource $r1 = new WorkSource();
        zza($r1, $i0, $r0);
        return $r1;
    }

    public static WorkSource zzs(Context $r0, String $r1) throws  {
        if ($r0 == null || $r0.getPackageManager() == null) {
            return null;
        }
        String $r4;
        try {
            ApplicationInfo $r3 = $r0.getPackageManager().getApplicationInfo($r1, 0);
            if ($r3 != null) {
                return zzf($r3.uid, $r1);
            }
            $r4 = "Could not get applicationInfo from package: ";
            $r1 = String.valueOf($r1);
            Log.e("WorkSourceUtil", $r1.length() != 0 ? $r4.concat($r1) : new String("Could not get applicationInfo from package: "));
            return null;
        } catch (NameNotFoundException e) {
            $r4 = "Could not find package: ";
            $r1 = String.valueOf($r1);
            Log.e("WorkSourceUtil", $r1.length() != 0 ? $r4.concat($r1) : new String("Could not find package: "));
            return null;
        }
    }
}
