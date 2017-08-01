package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CarpoolRideState implements Parcelable {
    public static final Creator<CarpoolRideState> CREATOR = new C15241();
    public int entry;
    long time;

    static class C15241 implements Creator<CarpoolRideState> {
        C15241() throws  {
        }

        public CarpoolRideState createFromParcel(Parcel $r1) throws  {
            return new CarpoolRideState($r1);
        }

        public CarpoolRideState[] newArray(int $i0) throws  {
            return new CarpoolRideState[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    protected CarpoolRideState(Parcel $r1) throws  {
        this.entry = $r1.readInt();
        this.time = $r1.readLong();
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeInt(this.entry);
        $r1.writeLong(this.time);
    }
}
