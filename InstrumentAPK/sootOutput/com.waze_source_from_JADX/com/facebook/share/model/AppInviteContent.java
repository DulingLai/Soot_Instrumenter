package com.facebook.share.model;

import android.os.Parcel;

public final class AppInviteContent implements ShareModel {
    private final String applinkUrl;
    private final String previewImageUrl;

    public static class Builder implements ShareModelBuilder<AppInviteContent, Builder> {
        private String applinkUrl;
        private String previewImageUrl;

        public Builder setApplinkUrl(String $r1) throws  {
            this.applinkUrl = $r1;
            return this;
        }

        public Builder setPreviewImageUrl(String $r1) throws  {
            this.previewImageUrl = $r1;
            return this;
        }

        public AppInviteContent build() throws  {
            return new AppInviteContent();
        }

        public Builder readFrom(AppInviteContent $r0) throws  {
            return $r0 == null ? this : setApplinkUrl($r0.getApplinkUrl()).setPreviewImageUrl($r0.getPreviewImageUrl());
        }

        public Builder readFrom(Parcel $r1) throws  {
            return readFrom((AppInviteContent) $r1.readParcelable(AppInviteContent.class.getClassLoader()));
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    private AppInviteContent(Builder $r1) throws  {
        this.applinkUrl = $r1.applinkUrl;
        this.previewImageUrl = $r1.previewImageUrl;
    }

    AppInviteContent(Parcel $r1) throws  {
        this.applinkUrl = $r1.readString();
        this.previewImageUrl = $r1.readString();
    }

    public String getApplinkUrl() throws  {
        return this.applinkUrl;
    }

    public String getPreviewImageUrl() throws  {
        return this.previewImageUrl;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.applinkUrl);
        $r1.writeString(this.previewImageUrl);
    }
}
