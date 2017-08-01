package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

public final class MediaMetadataCompat implements Parcelable {
    public static final Creator<MediaMetadataCompat> CREATOR = new C00731();
    private static final ArrayMap<String, Integer> METADATA_KEYS_TYPE = new ArrayMap();
    public static final String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
    public static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    public static final String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
    public static final String METADATA_KEY_ALBUM_ART_URI = "android.media.metadata.ALBUM_ART_URI";
    public static final String METADATA_KEY_ART = "android.media.metadata.ART";
    public static final String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
    public static final String METADATA_KEY_ART_URI = "android.media.metadata.ART_URI";
    public static final String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
    public static final String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
    public static final String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
    public static final String METADATA_KEY_DATE = "android.media.metadata.DATE";
    public static final String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
    public static final String METADATA_KEY_DISPLAY_DESCRIPTION = "android.media.metadata.DISPLAY_DESCRIPTION";
    public static final String METADATA_KEY_DISPLAY_ICON = "android.media.metadata.DISPLAY_ICON";
    public static final String METADATA_KEY_DISPLAY_ICON_URI = "android.media.metadata.DISPLAY_ICON_URI";
    public static final String METADATA_KEY_DISPLAY_SUBTITLE = "android.media.metadata.DISPLAY_SUBTITLE";
    public static final String METADATA_KEY_DISPLAY_TITLE = "android.media.metadata.DISPLAY_TITLE";
    public static final String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
    public static final String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
    public static final String METADATA_KEY_MEDIA_ID = "android.media.metadata.MEDIA_ID";
    public static final String METADATA_KEY_NUM_TRACKS = "android.media.metadata.NUM_TRACKS";
    public static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    public static final String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
    public static final String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
    public static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    public static final String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
    public static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";
    private static final int METADATA_TYPE_BITMAP = 2;
    private static final int METADATA_TYPE_LONG = 0;
    private static final int METADATA_TYPE_RATING = 3;
    private static final int METADATA_TYPE_TEXT = 1;
    private static final String[] PREFERRED_BITMAP_ORDER = new String[]{METADATA_KEY_DISPLAY_ICON, METADATA_KEY_ART, METADATA_KEY_ALBUM_ART};
    private static final String[] PREFERRED_DESCRIPTION_ORDER = new String[]{METADATA_KEY_TITLE, METADATA_KEY_ARTIST, METADATA_KEY_ALBUM, METADATA_KEY_ALBUM_ARTIST, METADATA_KEY_WRITER, METADATA_KEY_AUTHOR, METADATA_KEY_COMPOSER};
    private static final String[] PREFERRED_URI_ORDER = new String[]{METADATA_KEY_DISPLAY_ICON_URI, METADATA_KEY_ART_URI, METADATA_KEY_ALBUM_ART_URI};
    private static final String TAG = "MediaMetadata";
    private final Bundle mBundle;
    private MediaDescriptionCompat mDescription;
    private Object mMetadataObj;

    static class C00731 implements Creator<MediaMetadataCompat> {
        C00731() throws  {
        }

        public MediaMetadataCompat createFromParcel(Parcel $r1) throws  {
            return new MediaMetadataCompat($r1);
        }

        public MediaMetadataCompat[] newArray(int $i0) throws  {
            return new MediaMetadataCompat[$i0];
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BitmapKey {
    }

    public static final class Builder {
        private final Bundle mBundle;

        public Builder() throws  {
            this.mBundle = new Bundle();
        }

        public Builder(MediaMetadataCompat $r1) throws  {
            this.mBundle = new Bundle($r1.mBundle);
        }

        public Builder putText(String $r1, CharSequence $r2) throws  {
            if (!MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey($r1) || ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get($r1)).intValue() == 1) {
                this.mBundle.putCharSequence($r1, $r2);
                return this;
            }
            throw new IllegalArgumentException("The " + $r1 + " key cannot be used to put a CharSequence");
        }

        public Builder putString(String $r1, String $r2) throws  {
            if (!MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey($r1) || ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get($r1)).intValue() == 1) {
                this.mBundle.putCharSequence($r1, $r2);
                return this;
            }
            throw new IllegalArgumentException("The " + $r1 + " key cannot be used to put a String");
        }

        public Builder putLong(String $r1, long $l0) throws  {
            if (!MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey($r1) || ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get($r1)).intValue() == 0) {
                this.mBundle.putLong($r1, $l0);
                return this;
            }
            throw new IllegalArgumentException("The " + $r1 + " key cannot be used to put a long");
        }

        public Builder putRating(String $r1, RatingCompat $r2) throws  {
            if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey($r1) && ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get($r1)).intValue() != 3) {
                throw new IllegalArgumentException("The " + $r1 + " key cannot be used to put a Rating");
            } else if (VERSION.SDK_INT >= 21) {
                this.mBundle.putParcelable($r1, (Parcelable) $r2.getRating());
                return this;
            } else {
                this.mBundle.putParcelable($r1, $r2);
                return this;
            }
        }

        public Builder putBitmap(String $r1, Bitmap $r2) throws  {
            if (!MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey($r1) || ((Integer) MediaMetadataCompat.METADATA_KEYS_TYPE.get($r1)).intValue() == 2) {
                this.mBundle.putParcelable($r1, $r2);
                return this;
            }
            throw new IllegalArgumentException("The " + $r1 + " key cannot be used to put a Bitmap");
        }

        public MediaMetadataCompat build() throws  {
            return new MediaMetadataCompat(this.mBundle);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LongKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RatingKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextKey {
    }

    public int describeContents() throws  {
        return 0;
    }

    static {
        METADATA_KEYS_TYPE.put(METADATA_KEY_TITLE, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_ARTIST, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_DURATION, Integer.valueOf(0));
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_AUTHOR, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_WRITER, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMPOSER, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMPILATION, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_DATE, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_YEAR, Integer.valueOf(0));
        METADATA_KEYS_TYPE.put(METADATA_KEY_GENRE, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_TRACK_NUMBER, Integer.valueOf(0));
        METADATA_KEYS_TYPE.put(METADATA_KEY_NUM_TRACKS, Integer.valueOf(0));
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISC_NUMBER, Integer.valueOf(0));
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ARTIST, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART, Integer.valueOf(2));
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART_URI, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART, Integer.valueOf(2));
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART_URI, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_USER_RATING, Integer.valueOf(3));
        METADATA_KEYS_TYPE.put(METADATA_KEY_RATING, Integer.valueOf(3));
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_TITLE, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_SUBTITLE, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_DESCRIPTION, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON, Integer.valueOf(2));
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON_URI, Integer.valueOf(1));
        METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_ID, Integer.valueOf(1));
    }

    private MediaMetadataCompat(Bundle $r1) throws  {
        this.mBundle = new Bundle($r1);
    }

    private MediaMetadataCompat(Parcel $r1) throws  {
        this.mBundle = $r1.readBundle();
    }

    public boolean containsKey(String $r1) throws  {
        return this.mBundle.containsKey($r1);
    }

    public CharSequence getText(String $r1) throws  {
        return this.mBundle.getCharSequence($r1);
    }

    public String getString(String $r1) throws  {
        CharSequence $r3 = this.mBundle.getCharSequence($r1);
        if ($r3 != null) {
            return $r3.toString();
        }
        return null;
    }

    public long getLong(String $r1) throws  {
        return this.mBundle.getLong($r1, 0);
    }

    public RatingCompat getRating(String $r1) throws  {
        if (VERSION.SDK_INT < 21) {
            return (RatingCompat) this.mBundle.getParcelable($r1);
        }
        try {
            return RatingCompat.fromRating(this.mBundle.getParcelable($r1));
        } catch (Exception $r2) {
            Log.w(TAG, "Failed to retrieve a key as Rating.", $r2);
            return null;
        }
    }

    public Bitmap getBitmap(String $r1) throws  {
        try {
            return (Bitmap) this.mBundle.getParcelable($r1);
        } catch (Exception $r2) {
            Log.w(TAG, "Failed to retrieve a key as Bitmap.", $r2);
            return null;
        }
    }

    public MediaDescriptionCompat getDescription() throws  {
        MediaDescriptionCompat $r3 = this.mDescription;
        this = this;
        if ($r3 != null) {
            return this.mDescription;
        }
        int $i2;
        String $r4 = getString(METADATA_KEY_MEDIA_ID);
        CharSequence[] $r2 = new CharSequence[3];
        Bitmap $r5 = null;
        Uri $r6 = null;
        CharSequence $r7 = getText(METADATA_KEY_DISPLAY_TITLE);
        if (TextUtils.isEmpty($r7)) {
            $i2 = 0;
            int $i1 = 0;
            while (true) {
                int $i0 = $r2.length;
                if ($i2 >= $i0) {
                    break;
                }
                $i0 = PREFERRED_DESCRIPTION_ORDER.length;
                if ($i1 >= $i0) {
                    break;
                }
                int $i02 = $i1 + 1;
                $r7 = getText(PREFERRED_DESCRIPTION_ORDER[$i1]);
                if (!TextUtils.isEmpty($r7)) {
                    $r2[$i2] = $r7;
                    $i2++;
                }
                $i1 = $i02;
            }
        } else {
            $r2[0] = $r7;
            $r2[1] = getText(METADATA_KEY_DISPLAY_SUBTITLE);
            $r2[2] = getText(METADATA_KEY_DISPLAY_DESCRIPTION);
        }
        for (String $r9 : PREFERRED_BITMAP_ORDER) {
            String $r92;
            Bitmap $r10 = getBitmap($r92);
            if ($r10 != null) {
                $r5 = $r10;
                break;
            }
        }
        for (String $r922 : PREFERRED_URI_ORDER) {
            $r922 = getString($r922);
            if (!TextUtils.isEmpty($r922)) {
                $r6 = Uri.parse($r922);
                break;
            }
        }
        android.support.v4.media.MediaDescriptionCompat.Builder $r1 = new android.support.v4.media.MediaDescriptionCompat.Builder();
        $r1.setMediaId($r4);
        $r1.setTitle($r2[0]);
        $r1.setSubtitle($r2[1]);
        $r1.setDescription($r2[2]);
        $r1.setIconBitmap($r5);
        $r1.setIconUri($r6);
        this.mDescription = $r1.build();
        return this.mDescription;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeBundle(this.mBundle);
    }

    public int size() throws  {
        return this.mBundle.size();
    }

    public Set<String> keySet() throws  {
        return this.mBundle.keySet();
    }

    public Bundle getBundle() throws  {
        return this.mBundle;
    }

    public static MediaMetadataCompat fromMediaMetadata(Object $r0) throws  {
        if ($r0 == null || VERSION.SDK_INT < 21) {
            return null;
        }
        Parcel $r2 = Parcel.obtain();
        MediaMetadataCompatApi21.writeToParcel($r0, $r2, 0);
        $r2.setDataPosition(0);
        MediaMetadataCompat $r1 = (MediaMetadataCompat) CREATOR.createFromParcel($r2);
        $r2.recycle();
        $r1.mMetadataObj = $r0;
        return $r1;
    }

    public Object getMediaMetadata() throws  {
        if (this.mMetadataObj != null || VERSION.SDK_INT < 21) {
            return this.mMetadataObj;
        }
        Parcel $r2 = Parcel.obtain();
        writeToParcel($r2, 0);
        $r2.setDataPosition(0);
        this.mMetadataObj = MediaMetadataCompatApi21.createFromParcel($r2);
        $r2.recycle();
        return this.mMetadataObj;
    }
}
