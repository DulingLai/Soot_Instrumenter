package android.support.v4.content;

import android.content.Context;
import android.os.Binder;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.app.AppOpsManagerCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PermissionChecker {
    public static final int PERMISSION_DENIED = -1;
    public static final int PERMISSION_DENIED_APP_OP = -2;
    public static final int PERMISSION_GRANTED = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionResult {
    }

    private PermissionChecker() throws  {
    }

    public static int checkPermission(@NonNull Context $r0, @NonNull String $r1, int $i0, int $i1, String $r2) throws  {
        if ($r0.checkPermission($r1, $i0, $i1) == -1) {
            return -1;
        }
        $r1 = AppOpsManagerCompat.permissionToOp($r1);
        if ($r1 == null) {
            return 0;
        }
        if ($r2 == null) {
            String[] $r4 = $r0.getPackageManager().getPackagesForUid($i1);
            if ($r4 == null) {
                return -1;
            }
            if ($r4.length <= 0) {
                return -1;
            }
            $r2 = $r4[0];
        }
        return AppOpsManagerCompat.noteProxyOp($r0, $r1, $r2) != 0 ? -2 : 0;
    }

    public static int checkSelfPermission(@NonNull Context $r0, @NonNull String $r1) throws  {
        return checkPermission($r0, $r1, Process.myPid(), Process.myUid(), $r0.getPackageName());
    }

    public static int checkCallingPermission(@NonNull Context $r0, @NonNull String $r1, String $r2) throws  {
        return Binder.getCallingPid() == Process.myPid() ? -1 : checkPermission($r0, $r1, Binder.getCallingPid(), Binder.getCallingUid(), $r2);
    }

    public static int checkCallingOrSelfPermission(@NonNull Context $r0, @NonNull String $r1) throws  {
        return checkPermission($r0, $r1, Binder.getCallingPid(), Binder.getCallingUid(), Binder.getCallingPid() == Process.myPid() ? $r0.getPackageName() : null);
    }
}
