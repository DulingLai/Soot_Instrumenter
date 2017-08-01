package com.facebook.share.model;

import android.net.Uri;
import android.os.Parcel;
import android.support.annotation.Nullable;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ShareContent<P extends ShareContent, E extends Builder> implements ShareModel {
    private final Uri contentUrl;
    private final List<String> peopleIds;
    private final String placeId;
    private final String ref;

    public static abstract class Builder<P extends ShareContent, E extends Builder> implements ShareModelBuilder<P, E> {
        private Uri contentUrl;
        private List<String> peopleIds;
        private String placeId;
        private String ref;

        public E setContentUrl(@Nullable @Signature({"(", "Landroid/net/Uri;", ")TE;"}) Uri $r1) throws  {
            this.contentUrl = $r1;
            return this;
        }

        public E setPeopleIds(@Nullable @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)TE;"}) List<String> $r1) throws  {
            List $r12;
            if ($r1 == null) {
                $r12 = null;
            } else {
                $r12 = Collections.unmodifiableList($r1);
            }
            this.peopleIds = $r12;
            return this;
        }

        public E setPlaceId(@Nullable @Signature({"(", "Ljava/lang/String;", ")TE;"}) String $r1) throws  {
            this.placeId = $r1;
            return this;
        }

        public E setRef(@Nullable @Signature({"(", "Ljava/lang/String;", ")TE;"}) String $r1) throws  {
            this.ref = $r1;
            return this;
        }

        public E readFrom(@Signature({"(TP;)TE;"}) P $r0) throws  {
            return $r0 == null ? this : setContentUrl($r0.getContentUrl()).setPeopleIds($r0.getPeopleIds()).setPlaceId($r0.getPlaceId()).setRef($r0.getRef());
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    protected ShareContent(Builder $r1) throws  {
        this.contentUrl = $r1.contentUrl;
        this.peopleIds = $r1.peopleIds;
        this.placeId = $r1.placeId;
        this.ref = $r1.ref;
    }

    protected ShareContent(Parcel $r1) throws  {
        this.contentUrl = (Uri) $r1.readParcelable(Uri.class.getClassLoader());
        this.peopleIds = readUnmodifiableStringList($r1);
        this.placeId = $r1.readString();
        this.ref = $r1.readString();
    }

    @Nullable
    public Uri getContentUrl() throws  {
        return this.contentUrl;
    }

    @Nullable
    public List<String> getPeopleIds() throws  {
        return this.peopleIds;
    }

    @Nullable
    public String getPlaceId() throws  {
        return this.placeId;
    }

    @Nullable
    public String getRef() throws  {
        return this.ref;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeParcelable(this.contentUrl, 0);
        $r1.writeStringList(this.peopleIds);
        $r1.writeString(this.placeId);
        $r1.writeString(this.ref);
    }

    private List<String> readUnmodifiableStringList(@Signature({"(", "Landroid/os/Parcel;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) Parcel $r1) throws  {
        ArrayList $r2 = new ArrayList();
        $r1.readStringList($r2);
        return $r2.size() == 0 ? null : Collections.unmodifiableList($r2);
    }
}
