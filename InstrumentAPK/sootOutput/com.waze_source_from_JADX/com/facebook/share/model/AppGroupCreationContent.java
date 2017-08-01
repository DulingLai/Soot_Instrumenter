package com.facebook.share.model;

import android.os.Parcel;

public final class AppGroupCreationContent implements ShareModel {
    private final String description;
    private final String name;
    private AppGroupPrivacy privacy;

    public enum AppGroupPrivacy {
        Open,
        Closed
    }

    public static class Builder implements ShareModelBuilder<AppGroupCreationContent, Builder> {
        private String description;
        private String name;
        private AppGroupPrivacy privacy;

        public Builder setName(String $r1) throws  {
            this.name = $r1;
            return this;
        }

        public Builder setDescription(String $r1) throws  {
            this.description = $r1;
            return this;
        }

        public Builder setAppGroupPrivacy(AppGroupPrivacy $r1) throws  {
            this.privacy = $r1;
            return this;
        }

        public AppGroupCreationContent build() throws  {
            return new AppGroupCreationContent();
        }

        public Builder readFrom(AppGroupCreationContent $r0) throws  {
            return $r0 == null ? this : setName($r0.getName()).setDescription($r0.getDescription()).setAppGroupPrivacy($r0.getAppGroupPrivacy());
        }

        public Builder readFrom(Parcel $r1) throws  {
            return readFrom((AppGroupCreationContent) $r1.readParcelable(AppGroupCreationContent.class.getClassLoader()));
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    private AppGroupCreationContent(Builder $r1) throws  {
        this.name = $r1.name;
        this.description = $r1.description;
        this.privacy = $r1.privacy;
    }

    AppGroupCreationContent(Parcel $r1) throws  {
        this.name = $r1.readString();
        this.description = $r1.readString();
        this.privacy = (AppGroupPrivacy) $r1.readSerializable();
    }

    public String getName() throws  {
        return this.name;
    }

    public String getDescription() throws  {
        return this.description;
    }

    public AppGroupPrivacy getAppGroupPrivacy() throws  {
        return this.privacy;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.name);
        $r1.writeString(this.description);
        $r1.writeSerializable(this.privacy);
    }
}
