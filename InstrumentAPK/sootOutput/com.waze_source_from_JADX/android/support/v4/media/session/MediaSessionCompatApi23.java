package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import dalvik.annotation.Signature;

class MediaSessionCompatApi23 {

    public interface Callback extends Callback {
        void onPlayFromUri(Uri uri, Bundle bundle) throws ;
    }

    static class CallbackProxy<T extends Callback> extends CallbackProxy<T> {
        public CallbackProxy(@Signature({"(TT;)V"}) T $r1) throws  {
            super($r1);
        }

        public void onPlayFromUri(Uri $r1, Bundle $r2) throws  {
            ((Callback) this.mCallback).onPlayFromUri($r1, $r2);
        }
    }

    MediaSessionCompatApi23() throws  {
    }

    public static Object createCallback(Callback $r0) throws  {
        return new CallbackProxy($r0);
    }
}
