package android.support.v4.widget;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.widget.ListView;

public final class ListViewCompat {
    public static void scrollListBy(@NonNull ListView $r0, int $i0) throws  {
        if (VERSION.SDK_INT >= 19) {
            ListViewCompatKitKat.scrollListBy($r0, $i0);
        } else {
            ListViewCompatDonut.scrollListBy($r0, $i0);
        }
    }

    private ListViewCompat() throws  {
    }
}
