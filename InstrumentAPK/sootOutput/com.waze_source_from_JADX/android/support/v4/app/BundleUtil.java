package android.support.v4.app;

import android.os.Bundle;
import android.os.Parcelable;
import java.util.Arrays;

class BundleUtil {
    BundleUtil() throws  {
    }

    public static Bundle[] getBundleArrayFromBundle(Bundle $r0, String $r1) throws  {
        Parcelable[] $r2 = $r0.getParcelableArray($r1);
        if (($r2 instanceof Bundle[]) || $r2 == null) {
            return (Bundle[]) $r2;
        }
        Bundle[] $r3 = (Bundle[]) Arrays.copyOf($r2, $r2.length, Bundle[].class);
        $r0.putParcelableArray($r1, $r3);
        return $r3;
    }
}
