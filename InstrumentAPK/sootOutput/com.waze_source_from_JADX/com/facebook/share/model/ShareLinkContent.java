package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;

public final class ShareLinkContent extends ShareContent<ShareLinkContent, Builder> {
    public static final Creator<ShareLinkContent> CREATOR = new C06131();
    private final String contentDescription;
    private final String contentTitle;
    private final Uri imageUrl;

    static class C06131 implements Creator<ShareLinkContent> {
        C06131() throws  {
        }

        public ShareLinkContent createFromParcel(Parcel $r1) throws  {
            return new ShareLinkContent($r1);
        }

        public ShareLinkContent[] newArray(int $i0) throws  {
            return new ShareLinkContent[$i0];
        }
    }

    public static final class Builder extends com.facebook.share.model.ShareContent.Builder<ShareLinkContent, Builder> {
        private String contentDescription;
        private String contentTitle;
        private Uri imageUrl;

        public Builder setContentDescription(@Nullable String $r1) throws  {
            this.contentDescription = $r1;
            return this;
        }

        public Builder setContentTitle(@Nullable String $r1) throws  {
            this.contentTitle = $r1;
            return this;
        }

        public Builder setImageUrl(@Nullable Uri $r1) throws  {
            this.imageUrl = $r1;
            return this;
        }

        public ShareLinkContent build() throws  {
            return new ShareLinkContent();
        }

        public Builder readFrom(ShareLinkContent $r0) throws  {
            return $r0 == null ? this : ((Builder) super.readFrom((ShareContent) $r0)).setContentDescription($r0.getContentDescription()).setImageUrl($r0.getImageUrl()).setContentTitle($r0.getContentTitle());
        }

        public Builder readFrom(Parcel $r1) throws  {
            return readFrom((ShareLinkContent) $r1.readParcelable(ShareLinkContent.class.getClassLoader()));
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    private ShareLinkContent(Builder $r1) throws  {
        super((com.facebook.share.model.ShareContent.Builder) $r1);
        this.contentDescription = $r1.contentDescription;
        this.contentTitle = $r1.contentTitle;
        this.imageUrl = $r1.imageUrl;
    }

    ShareLinkContent(Parcel $r1) throws  {
        super($r1);
        this.contentDescription = $r1.readString();
        this.contentTitle = $r1.readString();
        this.imageUrl = (Uri) $r1.readParcelable(Uri.class.getClassLoader());
    }

    public String getContentDescription() throws  {
        return this.contentDescription;
    }

    @Nullable
    public String getContentTitle() throws  {
        return this.contentTitle;
    }

    @Nullable
    public Uri getImageUrl() throws  {
        return this.imageUrl;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        super.writeToParcel($r1, $i0);
        $r1.writeString(this.contentDescription);
        $r1.writeString(this.contentTitle);
        $r1.writeParcelable(this.imageUrl, 0);
    }
}
