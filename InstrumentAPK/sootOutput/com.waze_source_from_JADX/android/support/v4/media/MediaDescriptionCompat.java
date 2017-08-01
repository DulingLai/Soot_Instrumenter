package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public final class MediaDescriptionCompat implements Parcelable {
    public static final Creator<MediaDescriptionCompat> CREATOR = new C00721();
    public static final String DESCRIPTION_KEY_MEDIA_URI = "android.support.v4.media.description.MEDIA_URI";
    public static final String DESCRIPTION_KEY_NULL_BUNDLE_FLAG = "android.support.v4.media.description.NULL_BUNDLE_FLAG";
    private final CharSequence mDescription;
    private Object mDescriptionObj;
    private final Bundle mExtras;
    private final Bitmap mIcon;
    private final Uri mIconUri;
    private final String mMediaId;
    private final Uri mMediaUri;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;

    static class C00721 implements Creator<MediaDescriptionCompat> {
        C00721() throws  {
        }

        public MediaDescriptionCompat createFromParcel(Parcel $r1) throws  {
            if (VERSION.SDK_INT < 21) {
                return new MediaDescriptionCompat($r1);
            }
            return MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.fromParcel($r1));
        }

        public MediaDescriptionCompat[] newArray(int $i0) throws  {
            return new MediaDescriptionCompat[$i0];
        }
    }

    public static final class Builder {
        private CharSequence mDescription;
        private Bundle mExtras;
        private Bitmap mIcon;
        private Uri mIconUri;
        private String mMediaId;
        private Uri mMediaUri;
        private CharSequence mSubtitle;
        private CharSequence mTitle;

        public Builder setMediaId(@Nullable String $r1) throws  {
            this.mMediaId = $r1;
            return this;
        }

        public Builder setTitle(@Nullable CharSequence $r1) throws  {
            this.mTitle = $r1;
            return this;
        }

        public Builder setSubtitle(@Nullable CharSequence $r1) throws  {
            this.mSubtitle = $r1;
            return this;
        }

        public Builder setDescription(@Nullable CharSequence $r1) throws  {
            this.mDescription = $r1;
            return this;
        }

        public Builder setIconBitmap(@Nullable Bitmap $r1) throws  {
            this.mIcon = $r1;
            return this;
        }

        public Builder setIconUri(@Nullable Uri $r1) throws  {
            this.mIconUri = $r1;
            return this;
        }

        public Builder setExtras(@Nullable Bundle $r1) throws  {
            this.mExtras = $r1;
            return this;
        }

        public Builder setMediaUri(@Nullable Uri $r1) throws  {
            this.mMediaUri = $r1;
            return this;
        }

        public MediaDescriptionCompat build() throws  {
            return new MediaDescriptionCompat(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    private MediaDescriptionCompat(String $r1, CharSequence $r2, CharSequence $r3, CharSequence $r4, Bitmap $r5, Uri $r6, Bundle $r7, Uri $r8) throws  {
        this.mMediaId = $r1;
        this.mTitle = $r2;
        this.mSubtitle = $r3;
        this.mDescription = $r4;
        this.mIcon = $r5;
        this.mIconUri = $r6;
        this.mExtras = $r7;
        this.mMediaUri = $r8;
    }

    private MediaDescriptionCompat(Parcel $r1) throws  {
        MediaDescriptionCompat mediaDescriptionCompat = this;
        this.mMediaId = $r1.readString();
        this.mTitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel($r1);
        this.mSubtitle = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel($r1);
        this.mDescription = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel($r1);
        this.mIcon = (Bitmap) $r1.readParcelable(null);
        this.mIconUri = (Uri) $r1.readParcelable(null);
        this.mExtras = $r1.readBundle();
        this.mMediaUri = (Uri) $r1.readParcelable(null);
    }

    @Nullable
    public String getMediaId() throws  {
        return this.mMediaId;
    }

    @Nullable
    public CharSequence getTitle() throws  {
        return this.mTitle;
    }

    @Nullable
    public CharSequence getSubtitle() throws  {
        return this.mSubtitle;
    }

    @Nullable
    public CharSequence getDescription() throws  {
        return this.mDescription;
    }

    @Nullable
    public Bitmap getIconBitmap() throws  {
        return this.mIcon;
    }

    @Nullable
    public Uri getIconUri() throws  {
        return this.mIconUri;
    }

    @Nullable
    public Bundle getExtras() throws  {
        return this.mExtras;
    }

    @Nullable
    public Uri getMediaUri() throws  {
        return this.mMediaUri;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        if (VERSION.SDK_INT < 21) {
            $r1.writeString(this.mMediaId);
            TextUtils.writeToParcel(this.mTitle, $r1, $i0);
            TextUtils.writeToParcel(this.mSubtitle, $r1, $i0);
            TextUtils.writeToParcel(this.mDescription, $r1, $i0);
            $r1.writeParcelable(this.mIcon, $i0);
            $r1.writeParcelable(this.mIconUri, $i0);
            $r1.writeBundle(this.mExtras);
            $r1.writeParcelable(this.mMediaUri, $i0);
            return;
        }
        MediaDescriptionCompatApi21.writeToParcel(getMediaDescription(), $r1, $i0);
    }

    public String toString() throws  {
        return this.mTitle + ", " + this.mSubtitle + ", " + this.mDescription;
    }

    public Object getMediaDescription() throws  {
        if (this.mDescriptionObj != null || VERSION.SDK_INT < 21) {
            return this.mDescriptionObj;
        }
        Object $r1 = Builder.newInstance();
        Builder.setMediaId($r1, this.mMediaId);
        Builder.setTitle($r1, this.mTitle);
        Builder.setSubtitle($r1, this.mSubtitle);
        Builder.setDescription($r1, this.mDescription);
        Builder.setIconBitmap($r1, this.mIcon);
        Builder.setIconUri($r1, this.mIconUri);
        Bundle $r6 = this.mExtras;
        if (VERSION.SDK_INT < 23 && this.mMediaUri != null) {
            if ($r6 == null) {
                $r6 = new Bundle();
                $r6.putBoolean(DESCRIPTION_KEY_NULL_BUNDLE_FLAG, true);
            }
            $r6.putParcelable(DESCRIPTION_KEY_MEDIA_URI, this.mMediaUri);
        }
        Builder.setExtras($r1, $r6);
        if (VERSION.SDK_INT >= 23) {
            Builder.setMediaUri($r1, this.mMediaUri);
        }
        this.mDescriptionObj = Builder.build($r1);
        return this.mDescriptionObj;
    }

    public static MediaDescriptionCompat fromMediaDescription(Object $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        if (VERSION.SDK_INT < 21) {
            return null;
        }
        Uri $r5;
        Builder $r1 = new Builder();
        $r1.setMediaId(MediaDescriptionCompatApi21.getMediaId($r0));
        $r1.setTitle(MediaDescriptionCompatApi21.getTitle($r0));
        $r1.setSubtitle(MediaDescriptionCompatApi21.getSubtitle($r0));
        $r1.setDescription(MediaDescriptionCompatApi21.getDescription($r0));
        $r1.setIconBitmap(MediaDescriptionCompatApi21.getIconBitmap($r0));
        $r1.setIconUri(MediaDescriptionCompatApi21.getIconUri($r0));
        Bundle $r6 = MediaDescriptionCompatApi21.getExtras($r0);
        Bundle $r7 = $r6;
        if ($r6 == null) {
            $r5 = null;
        } else {
            $r5 = (Uri) $r6.getParcelable(DESCRIPTION_KEY_MEDIA_URI);
        }
        if ($r5 != null) {
            if ($r6.containsKey(DESCRIPTION_KEY_NULL_BUNDLE_FLAG) && $r6.size() == 2) {
                $r7 = null;
            } else {
                $r6.remove(DESCRIPTION_KEY_MEDIA_URI);
                $r6.remove(DESCRIPTION_KEY_NULL_BUNDLE_FLAG);
            }
        }
        $r1.setExtras($r7);
        if ($r5 != null) {
            $r1.setMediaUri($r5);
        } else if (VERSION.SDK_INT >= 23) {
            $r1.setMediaUri(MediaDescriptionCompatApi23.getMediaUri($r0));
        }
        MediaDescriptionCompat $r8 = $r1.build();
        $r8.mDescriptionObj = $r0;
        return $r8;
    }
}
