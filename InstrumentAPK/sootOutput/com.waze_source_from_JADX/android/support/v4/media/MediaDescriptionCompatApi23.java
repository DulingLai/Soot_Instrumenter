package android.support.v4.media;

import android.media.MediaDescription;
import android.net.Uri;

class MediaDescriptionCompatApi23 extends MediaDescriptionCompatApi21 {

    static class Builder extends Builder {
        Builder() throws  {
        }

        public static void setMediaUri(Object $r1, Uri $r0) throws  {
            ((android.media.MediaDescription.Builder) $r1).setMediaUri($r0);
        }
    }

    MediaDescriptionCompatApi23() throws  {
    }

    public static Uri getMediaUri(Object $r1) throws  {
        return ((MediaDescription) $r1).getMediaUri();
    }
}
