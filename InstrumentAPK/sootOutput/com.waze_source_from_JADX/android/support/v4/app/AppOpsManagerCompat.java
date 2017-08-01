package android.support.v4.app;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

public final class AppOpsManagerCompat {
    private static final AppOpsManagerImpl IMPL;
    public static final int MODE_ALLOWED = 0;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_IGNORED = 1;

    private static class AppOpsManagerImpl {
        public int noteOp(Context context, String op, int uid, String packageName) throws  {
            return 1;
        }

        public int noteProxyOp(Context context, String op, String proxiedPackageName) throws  {
            return 1;
        }

        public String permissionToOp(String permission) throws  {
            return null;
        }

        private AppOpsManagerImpl() throws  {
        }
    }

    private static class AppOpsManager23 extends AppOpsManagerImpl {
        private AppOpsManager23() throws  {
            super();
        }

        public String permissionToOp(String $r1) throws  {
            return AppOpsManagerCompat23.permissionToOp($r1);
        }

        public int noteOp(Context $r1, String $r2, int $i0, String $r3) throws  {
            return AppOpsManagerCompat23.noteOp($r1, $r2, $i0, $r3);
        }

        public int noteProxyOp(Context $r1, String $r2, String $r3) throws  {
            return AppOpsManagerCompat23.noteProxyOp($r1, $r2, $r3);
        }
    }

    static {
        if (VERSION.SDK_INT >= 23) {
            IMPL = new AppOpsManager23();
        } else {
            IMPL = new AppOpsManagerImpl();
        }
    }

    private AppOpsManagerCompat() throws  {
    }

    public static String permissionToOp(@NonNull String $r0) throws  {
        return IMPL.permissionToOp($r0);
    }

    public static int noteOp(@NonNull Context $r0, @NonNull String $r1, int $i0, @NonNull String $r2) throws  {
        return IMPL.noteOp($r0, $r1, $i0, $r2);
    }

    public static int noteProxyOp(@NonNull Context $r0, @NonNull String $r1, @NonNull String $r2) throws  {
        return IMPL.noteProxyOp($r0, $r1, $r2);
    }
}
