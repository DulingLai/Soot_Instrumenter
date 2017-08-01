package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;

class MediaControllerCompatApi23 {

    public static class TransportControls extends android.support.v4.media.session.MediaControllerCompatApi21.TransportControls {
        public static void playFromUri(Object $r2, Uri $r0, Bundle $r1) throws  {
            ((android.media.session.MediaController.TransportControls) $r2).playFromUri($r0, $r1);
        }
    }

    MediaControllerCompatApi23() throws  {
    }
}
