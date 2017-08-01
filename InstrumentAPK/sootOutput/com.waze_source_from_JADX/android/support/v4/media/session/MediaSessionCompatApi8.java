package android.support.v4.media.session;

import android.content.ComponentName;
import android.content.Context;
import android.media.AudioManager;

class MediaSessionCompatApi8 {
    MediaSessionCompatApi8() throws  {
    }

    public static void registerMediaButtonEventReceiver(Context $r0, ComponentName $r1) throws  {
        ((AudioManager) $r0.getSystemService("audio")).registerMediaButtonEventReceiver($r1);
    }

    public static void unregisterMediaButtonEventReceiver(Context $r0, ComponentName $r1) throws  {
        ((AudioManager) $r0.getSystemService("audio")).unregisterMediaButtonEventReceiver($r1);
    }
}
