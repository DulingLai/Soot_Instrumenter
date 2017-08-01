package android.support.v4.media.session;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.view.KeyEvent;
import java.util.List;

public class MediaButtonReceiver extends BroadcastReceiver {
    public void onReceive(Context $r1, Intent $r2) throws  {
        Intent $r4 = new Intent("android.intent.action.MEDIA_BUTTON");
        $r4.setPackage($r1.getPackageName());
        PackageManager $r6 = $r1.getPackageManager();
        List $r7 = $r6.queryIntentServices($r4, 0);
        List $r8 = $r7;
        if ($r7.isEmpty()) {
            $r4.setAction(MediaBrowserServiceCompat.SERVICE_INTERFACE);
            $r8 = $r6.queryIntentServices($r4, 0);
        }
        if ($r8.isEmpty()) {
            throw new IllegalStateException("Could not find any Service that handles android.intent.action.MEDIA_BUTTON or a media browser service implementation");
        } else if ($r8.size() != 1) {
            throw new IllegalStateException("Expected 1 Service that handles " + $r4.getAction() + ", found " + $r8.size());
        } else {
            ResolveInfo $r12 = (ResolveInfo) $r8.get(0);
            ServiceInfo $r13 = $r12.serviceInfo;
            String $r5 = $r13.packageName;
            String $r14 = $r12.serviceInfo;
            String $r132 = $r14;
            $r2.setComponent(new ComponentName($r5, $r14.name));
            $r1.startService($r2);
        }
    }

    public static KeyEvent handleIntent(MediaSessionCompat $r0, Intent $r1) throws  {
        if ($r0 == null || $r1 == null || !"android.intent.action.MEDIA_BUTTON".equals($r1.getAction()) || !$r1.hasExtra("android.intent.extra.KEY_EVENT")) {
            return null;
        }
        KeyEvent $r4 = (KeyEvent) $r1.getParcelableExtra("android.intent.extra.KEY_EVENT");
        $r0.getController().dispatchMediaButtonEvent($r4);
        return $r4;
    }
}
