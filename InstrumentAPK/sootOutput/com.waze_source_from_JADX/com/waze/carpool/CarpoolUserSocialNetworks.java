package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CarpoolUserSocialNetworks implements Parcelable {
    public static final Creator<CarpoolUserSocialNetworks> CREATOR = new C16141();
    String id;
    String job_title;
    String name;
    int network_type;
    int number_of_friends;
    String photo_url;
    String profile_url;

    static class C16141 implements Creator<CarpoolUserSocialNetworks> {
        C16141() throws  {
        }

        public CarpoolUserSocialNetworks createFromParcel(Parcel $r1) throws  {
            return new CarpoolUserSocialNetworks($r1);
        }

        public CarpoolUserSocialNetworks[] newArray(int $i0) throws  {
            return new CarpoolUserSocialNetworks[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    protected CarpoolUserSocialNetworks(Parcel $r1) throws  {
        this.id = $r1.readString();
        this.name = $r1.readString();
        this.photo_url = $r1.readString();
        this.profile_url = $r1.readString();
        this.job_title = $r1.readString();
        this.number_of_friends = $r1.readInt();
        this.network_type = $r1.readInt();
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.id);
        $r1.writeString(this.name);
        $r1.writeString(this.photo_url);
        $r1.writeString(this.profile_url);
        $r1.writeString(this.job_title);
        $r1.writeInt(this.number_of_friends);
        $r1.writeInt(this.network_type);
    }
}
