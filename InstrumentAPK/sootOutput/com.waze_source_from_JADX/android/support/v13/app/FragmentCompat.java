package android.support.v13.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.Arrays;

public class FragmentCompat {
    static final FragmentCompatImpl IMPL;

    interface FragmentCompatImpl {
        void requestPermissions(Fragment fragment, String[] strArr, int i) throws ;

        void setMenuVisibility(Fragment fragment, boolean z) throws ;

        void setUserVisibleHint(Fragment fragment, boolean z) throws ;

        boolean shouldShowRequestPermissionRationale(Fragment fragment, String str) throws ;
    }

    static class BaseFragmentCompatImpl implements FragmentCompatImpl {
        public boolean shouldShowRequestPermissionRationale(Fragment fragment, String permission) throws  {
            return false;
        }

        BaseFragmentCompatImpl() throws  {
        }

        public void setMenuVisibility(Fragment f, boolean visible) throws  {
        }

        public void setUserVisibleHint(Fragment f, boolean deferStart) throws  {
        }

        public void requestPermissions(final Fragment $r1, final String[] $r2, final int $i0) throws  {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() throws  {
                    int[] $r1 = new int[$r2.length];
                    Activity $r4 = $r1.getActivity();
                    if ($r4 != null) {
                        PackageManager $r5 = $r4.getPackageManager();
                        String $r6 = $r4.getPackageName();
                        int $i0 = $r2.length;
                        for (int $i1 = 0; $i1 < $i0; $i1++) {
                            $r1[$i1] = $r5.checkPermission($r2[$i1], $r6);
                        }
                    } else {
                        Arrays.fill($r1, -1);
                    }
                    ((OnRequestPermissionsResultCallback) $r1).onRequestPermissionsResult($i0, $r2, $r1);
                }
            });
        }
    }

    static class ICSFragmentCompatImpl extends BaseFragmentCompatImpl {
        ICSFragmentCompatImpl() throws  {
        }

        public void setMenuVisibility(Fragment $r1, boolean $z0) throws  {
            FragmentCompatICS.setMenuVisibility($r1, $z0);
        }
    }

    static class ICSMR1FragmentCompatImpl extends ICSFragmentCompatImpl {
        ICSMR1FragmentCompatImpl() throws  {
        }

        public void setUserVisibleHint(Fragment $r1, boolean $z0) throws  {
            FragmentCompatICSMR1.setUserVisibleHint($r1, $z0);
        }
    }

    static class MncFragmentCompatImpl extends ICSMR1FragmentCompatImpl {
        MncFragmentCompatImpl() throws  {
        }

        public void requestPermissions(Fragment $r1, String[] $r2, int $i0) throws  {
            FragmentCompat23.requestPermissions($r1, $r2, $i0);
        }

        public boolean shouldShowRequestPermissionRationale(Fragment $r1, String $r2) throws  {
            return FragmentCompat23.shouldShowRequestPermissionRationale($r1, $r2);
        }
    }

    public interface OnRequestPermissionsResultCallback {
        void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) throws ;
    }

    static {
        if (VERSION.SDK_INT >= 23) {
            IMPL = new MncFragmentCompatImpl();
        } else if (VERSION.SDK_INT >= 15) {
            IMPL = new ICSMR1FragmentCompatImpl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new ICSFragmentCompatImpl();
        } else {
            IMPL = new BaseFragmentCompatImpl();
        }
    }

    public static void setMenuVisibility(Fragment $r0, boolean $z0) throws  {
        IMPL.setMenuVisibility($r0, $z0);
    }

    public static void setUserVisibleHint(Fragment $r0, boolean $z0) throws  {
        IMPL.setUserVisibleHint($r0, $z0);
    }

    public static void requestPermissions(@NonNull Fragment $r0, @NonNull String[] $r1, int $i0) throws  {
        IMPL.requestPermissions($r0, $r1, $i0);
    }

    public static boolean shouldShowRequestPermissionRationale(@NonNull Fragment $r0, @NonNull String $r1) throws  {
        return IMPL.shouldShowRequestPermissionRationale($r0, $r1);
    }
}
