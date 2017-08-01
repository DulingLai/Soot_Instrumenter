package com.facebook.share.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;

public final class ShareVideoContent extends ShareContent<ShareVideoContent, Builder> implements ShareModel {
    public static final Creator<ShareVideoContent> CREATOR = new C06201();
    private final String contentDescription;
    private final String contentTitle;
    private final SharePhoto previewPhoto;
    private final ShareVideo video;

    static class C06201 implements Creator<ShareVideoContent> {
        C06201() throws  {
        }

        public ShareVideoContent createFromParcel(Parcel $r1) throws  {
            return new ShareVideoContent($r1);
        }

        public ShareVideoContent[] newArray(int $i0) throws  {
            return new ShareVideoContent[$i0];
        }
    }

    public static final class Builder extends com.facebook.share.model.ShareContent.Builder<ShareVideoContent, Builder> {
        private String contentDescription;
        private String contentTitle;
        private SharePhoto previewPhoto;
        private ShareVideo video;

        public Builder setContentDescription(@Nullable String $r1) throws  {
            this.contentDescription = $r1;
            return this;
        }

        public Builder setContentTitle(@Nullable String $r1) throws  {
            this.contentTitle = $r1;
            return this;
        }

        public Builder setPreviewPhoto(@Nullable SharePhoto $r1) throws  {
            this.previewPhoto = $r1 == null ? null : new com.facebook.share.model.SharePhoto.Builder().readFrom($r1).build();
            return this;
        }

        public Builder setVideo(@Nullable ShareVideo $r1) throws  {
            if ($r1 == null) {
                return this;
            }
            this.video = new com.facebook.share.model.ShareVideo.Builder().readFrom($r1).build();
            return this;
        }

        public ShareVideoContent build() throws  {
            return new ShareVideoContent();
        }

        public Builder readFrom(ShareVideoContent $r0) throws  {
            return $r0 == null ? this : ((Builder) super.readFrom((ShareContent) $r0)).setContentDescription($r0.getContentDescription()).setContentTitle($r0.getContentTitle()).setPreviewPhoto($r0.getPreviewPhoto()).setVideo($r0.getVideo());
        }

        public Builder readFrom(Parcel $r1) throws  {
            return readFrom((ShareVideoContent) $r1.readParcelable(ShareVideoContent.class.getClassLoader()));
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    private ShareVideoContent(Builder $r1) throws  {
        super((com.facebook.share.model.ShareContent.Builder) $r1);
        this.contentDescription = $r1.contentDescription;
        this.contentTitle = $r1.contentTitle;
        this.previewPhoto = $r1.previewPhoto;
        this.video = $r1.video;
    }

    ShareVideoContent(Parcel $r1) throws  {
        super($r1);
        this.contentDescription = $r1.readString();
        this.contentTitle = $r1.readString();
        com.facebook.share.model.SharePhoto.Builder $r3 = new com.facebook.share.model.SharePhoto.Builder().readFrom($r1);
        if ($r3.getImageUrl() == null && $r3.getBitmap() == null) {
            this.previewPhoto = null;
        } else {
            this.previewPhoto = $r3.build();
        }
        this.video = new com.facebook.share.model.ShareVideo.Builder().readFrom($r1).build();
    }

    @Nullable
    public String getContentDescription() throws  {
        return this.contentDescription;
    }

    @Nullable
    public String getContentTitle() throws  {
        return this.contentTitle;
    }

    @Nullable
    public SharePhoto getPreviewPhoto() throws  {
        return this.previewPhoto;
    }

    @Nullable
    public ShareVideo getVideo() throws  {
        return this.video;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        super.writeToParcel($r1, $i0);
        $r1.writeString(this.contentDescription);
        $r1.writeString(this.contentTitle);
        $r1.writeParcelable(this.previewPhoto, 0);
        $r1.writeParcelable(this.video, 0);
    }
}
