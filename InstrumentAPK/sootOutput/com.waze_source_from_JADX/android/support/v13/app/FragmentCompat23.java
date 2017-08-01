package android.support.v13.app;

import android.app.Fragment;

class FragmentCompat23 {
    FragmentCompat23() throws  {
    }

    public static void requestPermissions(Fragment $r0, String[] $r1, int $i0) throws  {
        $r0.requestPermissions($r1, $i0);
    }

    public static boolean shouldShowRequestPermissionRationale(Fragment $r0, String $r1) throws  {
        return $r0.shouldShowRequestPermissionRationale($r1);
    }
}
