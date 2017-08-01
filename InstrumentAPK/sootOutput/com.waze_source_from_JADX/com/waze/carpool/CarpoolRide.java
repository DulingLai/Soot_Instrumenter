package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CarpoolRide implements Parcelable {
    public static final Creator<CarpoolRide> CREATOR = new C14951();
    String drive_id;
    boolean driver_reviewed;
    boolean is_real_time_ride;
    public CarpoolItinerary itinerary;
    String proxy_number;
    CarpoolUserData rider;
    int rider_endorsement;
    public int state;
    String uuid;

    static class C14951 implements Creator<CarpoolRide> {
        C14951() throws  {
        }

        public CarpoolRide createFromParcel(Parcel $r1) throws  {
            return new CarpoolRide($r1);
        }

        public CarpoolRide[] newArray(int $i0) throws  {
            return new CarpoolRide[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    protected CarpoolRide(Parcel $r1) throws  {
        boolean $z0 = true;
        this.uuid = $r1.readString();
        this.itinerary = (CarpoolItinerary) $r1.readParcelable(CarpoolItinerary.class.getClassLoader());
        this.state = $r1.readInt();
        this.rider_endorsement = $r1.readInt();
        this.driver_reviewed = $r1.readByte() != (byte) 0;
        this.proxy_number = $r1.readString();
        this.drive_id = $r1.readString();
        if ($r1.readByte() == (byte) 0) {
            $z0 = false;
        }
        this.is_real_time_ride = $z0;
        this.rider = (CarpoolUserData) $r1.readParcelable(CarpoolUserData.class.getClassLoader());
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        boolean $z0 = true;
        $r1.writeString(this.uuid);
        $r1.writeParcelable(this.itinerary, $i0);
        $r1.writeInt(this.state);
        $r1.writeInt(this.rider_endorsement);
        $r1.writeByte((byte) (this.driver_reviewed));
        $r1.writeString(this.proxy_number);
        $r1.writeString(this.drive_id);
        if (!this.is_real_time_ride) {
            $z0 = false;
        }
        $r1.writeByte((byte) $z0);
        $r1.writeParcelable(this.rider, $i0);
    }

    public String getId() throws  {
        return this.uuid;
    }

    public long getTime() throws  {
        return this.itinerary.window_start_time;
    }

    public long getSortTime(CarpoolDrive $r1) throws  {
        long $l0 = getTime();
        if ($r1 == null) {
            return $l0;
        }
        long $l1 = $r1.getTime();
        $l0 = 3600 - ($l0 - $l1);
        if ($l0 < 60) {
            $l0 = 60;
        }
        if ($l0 > 7200) {
            $l0 = 7200;
        }
        return $l1 + ($l0 / 60);
    }

    public boolean isValid() throws  {
        return (this.uuid == null || this.uuid.isEmpty()) ? false : true;
    }

    public boolean isPending() throws  {
        if (this.state != 1) {
            return this.state == 13;
        } else {
            return true;
        }
    }

    public boolean isCanceled() throws  {
        return this.state == 6 || this.state == 2 || this.state == 15 || this.state == 3;
    }

    public String getProxyNumber() throws  {
        return this.proxy_number;
    }

    public boolean isEmpty() throws  {
        return this.uuid == null || this.uuid.isEmpty() || this.state == 0;
    }

    public int getState() throws  {
        return this.state;
    }

    public CarpoolLocation getPickupLocation() throws  {
        if (this.itinerary != null) {
            return this.itinerary.pickup;
        }
        return null;
    }

    public CarpoolLocation getDropOffLocation() throws  {
        if (this.itinerary != null) {
            return this.itinerary.dropoff;
        }
        return null;
    }

    public boolean hasDrive() throws  {
        return (this.drive_id == null || this.drive_id.isEmpty()) ? false : true;
    }

    public String getDriveId() throws  {
        return this.drive_id;
    }

    public boolean isRealTimeRide() throws  {
        return this.is_real_time_ride;
    }

    public void setIsRealTime(boolean $z0) throws  {
        this.is_real_time_ride = $z0;
    }

    public CarpoolUserData getRider() throws  {
        return this.rider;
    }
}
