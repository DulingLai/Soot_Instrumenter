package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;

class MediaDescriptionCompatApi21 {

    static class Builder {
        Builder() throws  {
        }

        public static Object newInstance() throws  {
            return new android.media.MediaDescription.Builder();
        }

        public static void setMediaId(Object $r1, String $r0) throws  {
            ((android.media.MediaDescription.Builder) $r1).setMediaId($r0);
        }

        public static void setTitle(Object $r1, CharSequence $r0) throws  {
            ((android.media.MediaDescription.Builder) $r1).setTitle($r0);
        }

        public static void setSubtitle(Object $r1, CharSequence $r0) throws  {
            ((android.media.MediaDescription.Builder) $r1).setSubtitle($r0);
        }

        public static void setDescription(Object $r1, CharSequence $r0) throws  {
            ((android.media.MediaDescription.Builder) $r1).setDescription($r0);
        }

        public static void setIconBitmap(Object $r1, Bitmap $r0) throws  {
            ((android.media.MediaDescription.Builder) $r1).setIconBitmap($r0);
        }

        public static void setIconUri(Object $r1, Uri $r0) throws  {
            ((android.media.MediaDescription.Builder) $r1).setIconUri($r0);
        }

        public static void setExtras(Object $r1, Bundle $r0) throws  {
            ((android.media.MediaDescription.Builder) $r1).setExtras($r0);
        }

        public static Object build(Object $r1) throws  {
            return ((android.media.MediaDescription.Builder) $r1).build();
        }
    }

    MediaDescriptionCompatApi21() throws  {
    }

    public static String getMediaId(Object $r1) throws  {
        return ((MediaDescription) $r1).getMediaId();
    }

    public static CharSequence getTitle(Object $r1) throws  {
        return ((MediaDescription) $r1).getTitle();
    }

    public static CharSequence getSubtitle(Object $r1) throws  {
        return ((MediaDescription) $r1).getSubtitle();
    }

    public static CharSequence getDescription(Object $r1) throws  {
        return ((MediaDescription) $r1).getDescription();
    }

    public static Bitmap getIconBitmap(Object $r1) throws  {
        return ((MediaDescription) $r1).getIconBitmap();
    }

    public static Uri getIconUri(Object $r1) throws  {
        return ((MediaDescription) $r1).getIconUri();
    }

    public static Bundle getExtras(Object $r1) throws  {
        return ((MediaDescription) $r1).getExtras();
    }

    public static void writeToParcel(Object $r1, Parcel $r0, int $i0) throws  {
        ((MediaDescription) $r1).writeToParcel($r0, $i0);
    }

    public static Object fromParcel(Parcel $r0) throws  {
        return MediaDescription.CREATOR.createFromParcel($r0);
    }
}
