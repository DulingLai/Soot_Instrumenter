package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;

public final class ShareVideo extends ShareMedia {
    public static final Creator<ShareVideo> CREATOR = new C06191();
    private final Uri localUrl;

    static class C06191 implements Creator<ShareVideo> {
        C06191() throws  {
        }

        public ShareVideo createFromParcel(Parcel $r1) throws  {
            return new ShareVideo($r1);
        }

        public ShareVideo[] newArray(int $i0) throws  {
            return new ShareVideo[$i0];
        }
    }

    public static final class Builder extends com.facebook.share.model.ShareMedia.Builder<ShareVideo, Builder> {
        private Uri localUrl;

        public Builder setLocalUrl(@Nullable Uri $r1) throws  {
            this.localUrl = $r1;
            return this;
        }

        public ShareVideo build() throws  {
            return new ShareVideo();
        }

        public Builder readFrom(ShareVideo $r0) throws  {
            return $r0 == null ? this : ((Builder) super.readFrom((ShareMedia) $r0)).setLocalUrl($r0.getLocalUrl());
        }

        public Builder readFrom(Parcel $r1) throws  {
            return readFrom((ShareVideo) $r1.readParcelable(ShareVideo.class.getClassLoader()));
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    private ShareVideo(Builder $r1) throws  {
        super((com.facebook.share.model.ShareMedia.Builder) $r1);
        this.localUrl = $r1.localUrl;
    }

    ShareVideo(Parcel $r1) throws  {
        super($r1);
        this.localUrl = (Uri) $r1.readParcelable(Uri.class.getClassLoader());
    }

    @Nullable
    public Uri getLocalUrl() throws  {
        return this.localUrl;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        super.writeToParcel($r1, $i0);
        $r1.writeParcelable(this.localUrl, 0);
    }
}
