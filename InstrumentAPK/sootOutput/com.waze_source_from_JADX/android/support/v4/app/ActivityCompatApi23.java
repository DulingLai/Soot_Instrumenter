package android.support.v4.app;

import android.app.Activity;

/* compiled from: ActivityCompat23 */
class ActivityCompatApi23 {

    /* compiled from: ActivityCompat23 */
    public interface RequestPermissionsRequestCodeValidator {
        void validateRequestPermissionsRequestCode(int i) throws ;
    }

    ActivityCompatApi23() throws  {
    }

    public static void requestPermissions(Activity $r0, String[] $r1, int $i0) throws  {
        if ($r0 instanceof RequestPermissionsRequestCodeValidator) {
            ((RequestPermissionsRequestCodeValidator) $r0).validateRequestPermissionsRequestCode($i0);
        }
        $r0.requestPermissions($r1, $i0);
    }

    public static boolean shouldShowRequestPermissionRationale(Activity $r0, String $r1) throws  {
        return $r0.shouldShowRequestPermissionRationale($r1);
    }
}
