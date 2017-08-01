package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CarpoolItinerary implements Parcelable {
    public static final Creator<CarpoolItinerary> CREATOR = new C14271();
    CarpoolLocation dropoff;
    CarpoolLocation pickup;
    String ride_note;
    String time_zone_id;
    public int window_duration_seconds;
    public long window_start_time;

    static class C14271 implements Creator<CarpoolItinerary> {
        C14271() throws  {
        }

        public CarpoolItinerary createFromParcel(Parcel $r1) throws  {
            return new CarpoolItinerary($r1);
        }

        public CarpoolItinerary[] newArray(int $i0) throws  {
            return new CarpoolItinerary[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    protected CarpoolItinerary(Parcel $r1) throws  {
        this.pickup = (CarpoolLocation) $r1.readParcelable(CarpoolLocation.class.getClassLoader());
        this.dropoff = (CarpoolLocation) $r1.readParcelable(CarpoolLocation.class.getClassLoader());
        this.window_start_time = $r1.readLong();
        this.window_duration_seconds = $r1.readInt();
        this.time_zone_id = $r1.readString();
        this.ride_note = $r1.readString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        $r1.writeParcelable(this.pickup, $i0);
        $r1.writeParcelable(this.dropoff, $i0);
        $r1.writeLong(this.window_start_time);
        $r1.writeInt(this.window_duration_seconds);
        $r1.writeString(this.time_zone_id);
        $r1.writeString(this.ride_note);
    }
}
