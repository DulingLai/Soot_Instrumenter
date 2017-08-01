package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.Rating;
import android.os.Parcel;
import dalvik.annotation.Signature;
import java.util.Set;

class MediaMetadataCompatApi21 {

    public static class Builder {
        public static Object newInstance() throws  {
            return new android.media.MediaMetadata.Builder();
        }

        public static void putBitmap(Object $r2, String $r0, Bitmap $r1) throws  {
            ((android.media.MediaMetadata.Builder) $r2).putBitmap($r0, $r1);
        }

        public static void putLong(Object $r1, String $r0, long $l0) throws  {
            ((android.media.MediaMetadata.Builder) $r1).putLong($r0, $l0);
        }

        public static void putRating(Object $r1, String $r0, Object $r2) throws  {
            ((android.media.MediaMetadata.Builder) $r1).putRating($r0, (Rating) $r2);
        }

        public static void putText(Object $r2, String $r0, CharSequence $r1) throws  {
            ((android.media.MediaMetadata.Builder) $r2).putText($r0, $r1);
        }

        public static void putString(Object $r2, String $r0, String $r1) throws  {
            ((android.media.MediaMetadata.Builder) $r2).putString($r0, $r1);
        }

        public static Object build(Object $r1) throws  {
            return ((android.media.MediaMetadata.Builder) $r1).build();
        }
    }

    MediaMetadataCompatApi21() throws  {
    }

    public static Set<String> keySet(@Signature({"(", "Ljava/lang/Object;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) Object $r1) throws  {
        return ((MediaMetadata) $r1).keySet();
    }

    public static Bitmap getBitmap(Object $r2, String $r0) throws  {
        return ((MediaMetadata) $r2).getBitmap($r0);
    }

    public static long getLong(Object $r1, String $r0) throws  {
        return ((MediaMetadata) $r1).getLong($r0);
    }

    public static Object getRating(Object $r2, String $r0) throws  {
        return ((MediaMetadata) $r2).getRating($r0);
    }

    public static CharSequence getText(Object $r2, String $r0) throws  {
        return ((MediaMetadata) $r2).getText($r0);
    }

    public static void writeToParcel(Object $r1, Parcel $r0, int $i0) throws  {
        ((MediaMetadata) $r1).writeToParcel($r0, $i0);
    }

    public static Object createFromParcel(Parcel $r0) throws  {
        return MediaMetadata.CREATOR.createFromParcel($r0);
    }
}
