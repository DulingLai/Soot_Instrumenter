package com.waze.navigate;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class NearbyStation implements Parcelable, Serializable {
    public static final Creator<NearbyStation> CREATOR = new C21461();
    private static final long serialVersionUID = 1;
    public String address;
    public String icon;
    public String result;
    public String value;

    static class C21461 implements Creator<NearbyStation> {
        C21461() throws  {
        }

        public NearbyStation createFromParcel(Parcel $r1) throws  {
            return new NearbyStation($r1);
        }

        public NearbyStation[] newArray(int $i0) throws  {
            return new NearbyStation[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public NearbyStation(String $r1, String $r2, String $r3, String $r4) throws  {
        this.result = $r1;
        this.value = $r2;
        this.icon = $r3;
        this.address = $r4;
    }

    private NearbyStation(Parcel $r1) throws  {
        this.result = $r1.readString();
        this.value = $r1.readString();
        this.icon = $r1.readString();
        this.address = $r1.readString();
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.result);
        $r1.writeString(this.value);
        $r1.writeString(this.icon);
        $r1.writeString(this.address);
    }
}
