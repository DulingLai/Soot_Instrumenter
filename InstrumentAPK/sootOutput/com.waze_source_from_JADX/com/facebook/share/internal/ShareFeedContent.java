package com.facebook.share.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.share.model.ShareContent;

public class ShareFeedContent extends ShareContent<ShareFeedContent, Builder> {
    public static final Creator<ShareFeedContent> CREATOR = new C05961();
    private final String link;
    private final String linkCaption;
    private final String linkDescription;
    private final String linkName;
    private final String mediaSource;
    private final String picture;
    private final String toId;

    static class C05961 implements Creator<ShareFeedContent> {
        C05961() throws  {
        }

        public ShareFeedContent createFromParcel(Parcel $r1) throws  {
            return new ShareFeedContent($r1);
        }

        public ShareFeedContent[] newArray(int $i0) throws  {
            return new ShareFeedContent[$i0];
        }
    }

    public static final class Builder extends com.facebook.share.model.ShareContent.Builder<ShareFeedContent, Builder> {
        private String link;
        private String linkCaption;
        private String linkDescription;
        private String linkName;
        private String mediaSource;
        private String picture;
        private String toId;

        public Builder setToId(String $r1) throws  {
            this.toId = $r1;
            return this;
        }

        public Builder setLink(String $r1) throws  {
            this.link = $r1;
            return this;
        }

        public Builder setLinkName(String $r1) throws  {
            this.linkName = $r1;
            return this;
        }

        public Builder setLinkCaption(String $r1) throws  {
            this.linkCaption = $r1;
            return this;
        }

        public Builder setLinkDescription(String $r1) throws  {
            this.linkDescription = $r1;
            return this;
        }

        public Builder setPicture(String $r1) throws  {
            this.picture = $r1;
            return this;
        }

        public Builder setMediaSource(String $r1) throws  {
            this.mediaSource = $r1;
            return this;
        }

        public ShareFeedContent build() throws  {
            return new ShareFeedContent();
        }

        public Builder readFrom(ShareFeedContent $r0) throws  {
            return $r0 == null ? this : ((Builder) super.readFrom((ShareContent) $r0)).setToId($r0.getToId()).setLink($r0.getLink()).setLinkName($r0.getLinkName()).setLinkCaption($r0.getLinkCaption()).setLinkDescription($r0.getLinkDescription()).setPicture($r0.getPicture()).setMediaSource($r0.getMediaSource());
        }

        public Builder readFrom(Parcel $r1) throws  {
            return readFrom((ShareFeedContent) $r1.readParcelable(ShareFeedContent.class.getClassLoader()));
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    private ShareFeedContent(Builder $r1) throws  {
        super((com.facebook.share.model.ShareContent.Builder) $r1);
        this.toId = $r1.toId;
        this.link = $r1.link;
        this.linkName = $r1.linkName;
        this.linkCaption = $r1.linkCaption;
        this.linkDescription = $r1.linkDescription;
        this.picture = $r1.picture;
        this.mediaSource = $r1.mediaSource;
    }

    ShareFeedContent(Parcel $r1) throws  {
        super($r1);
        this.toId = $r1.readString();
        this.link = $r1.readString();
        this.linkName = $r1.readString();
        this.linkCaption = $r1.readString();
        this.linkDescription = $r1.readString();
        this.picture = $r1.readString();
        this.mediaSource = $r1.readString();
    }

    public String getToId() throws  {
        return this.toId;
    }

    public String getLink() throws  {
        return this.link;
    }

    public String getLinkName() throws  {
        return this.linkName;
    }

    public String getLinkCaption() throws  {
        return this.linkCaption;
    }

    public String getLinkDescription() throws  {
        return this.linkDescription;
    }

    public String getPicture() throws  {
        return this.picture;
    }

    public String getMediaSource() throws  {
        return this.mediaSource;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        super.writeToParcel($r1, $i0);
        $r1.writeString(this.toId);
        $r1.writeString(this.link);
        $r1.writeString(this.linkName);
        $r1.writeString(this.linkCaption);
        $r1.writeString(this.linkDescription);
        $r1.writeString(this.picture);
        $r1.writeString(this.mediaSource);
    }
}
