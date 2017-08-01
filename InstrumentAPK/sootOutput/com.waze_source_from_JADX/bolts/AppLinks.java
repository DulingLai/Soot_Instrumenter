package bolts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public final class AppLinks {
    static final String KEY_NAME_APPLINK_DATA = "al_applink_data";
    static final String KEY_NAME_EXTRAS = "extras";
    static final String KEY_NAME_TARGET = "target_url";

    public static Bundle getAppLinkData(Intent $r0) throws  {
        return $r0.getBundleExtra(KEY_NAME_APPLINK_DATA);
    }

    public static Bundle getAppLinkExtras(Intent $r0) throws  {
        Bundle $r1 = getAppLinkData($r0);
        return $r1 == null ? null : $r1.getBundle(KEY_NAME_EXTRAS);
    }

    public static Uri getTargetUrl(Intent $r0) throws  {
        Bundle $r1 = getAppLinkData($r0);
        if ($r1 != null) {
            String $r2 = $r1.getString(KEY_NAME_TARGET);
            if ($r2 != null) {
                return Uri.parse($r2);
            }
        }
        return $r0.getData();
    }

    public static Uri getTargetUrlFromInboundIntent(Context $r0, Intent $r1) throws  {
        Bundle $r2 = getAppLinkData($r1);
        if ($r2 == null) {
            return null;
        }
        String $r3 = $r2.getString(KEY_NAME_TARGET);
        if ($r3 == null) {
            return null;
        }
        MeasurementEvent.sendBroadcastEvent($r0, MeasurementEvent.APP_LINK_NAVIGATE_IN_EVENT_NAME, $r1, null);
        return Uri.parse($r3);
    }
}
