package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CarpoolDriveItinerary implements Parcelable {
    public static final Creator<CarpoolDriveItinerary> CREATOR = new C13961();
    CarpoolLocation destination;
    String driver_id;
    boolean free_offer;
    CarpoolLocation origin;
    int window_duration_sec;
    long window_start_sec;

    static class C13961 implements Creator<CarpoolDriveItinerary> {
        C13961() throws  {
        }

        public CarpoolDriveItinerary createFromParcel(Parcel $r1) throws  {
            return new CarpoolDriveItinerary($r1);
        }

        public CarpoolDriveItinerary[] newArray(int $i0) throws  {
            return new CarpoolDriveItinerary[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    protected CarpoolDriveItinerary(Parcel $r1) throws  {
        this.driver_id = $r1.readString();
        this.origin = (CarpoolLocation) $r1.readParcelable(CarpoolLocation.class.getClassLoader());
        this.destination = (CarpoolLocation) $r1.readParcelable(CarpoolLocation.class.getClassLoader());
        this.window_start_sec = $r1.readLong();
        this.window_duration_sec = $r1.readInt();
        this.free_offer = $r1.readByte() != (byte) 0;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        $r1.writeString(this.driver_id);
        $r1.writeParcelable(this.origin, $i0);
        $r1.writeParcelable(this.destination, $i0);
        $r1.writeLong(this.window_start_sec);
        $r1.writeInt(this.window_duration_sec);
        $r1.writeByte((byte) (this.free_offer));
    }
}
