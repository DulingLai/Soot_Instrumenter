package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.waze.messages.QuestionData;

class CarpoolDriveMatchInfo implements Parcelable {
    public static final Creator<CarpoolDriveMatchInfo> CREATOR = new C13971();
    double detour_distance_meters;
    public String detour_distance_string;
    int detour_duration_seconds;
    public CarpoolLocation dropoff;
    double dropoff_to_destination_distance_meters;
    int dropoff_to_destination_duration_seconds;
    double origin_to_pickup_distance_meters;
    int origin_to_pickup_duration_seconds;
    public CarpoolLocation pickup;
    long pickup_time_seconds;
    int pickup_to_dropoff_duration_seconds;
    public QuestionData question;
    int reference_total_fee_minor_units;
    int total_fee_minor_units;

    static class C13971 implements Creator<CarpoolDriveMatchInfo> {
        C13971() throws  {
        }

        public CarpoolDriveMatchInfo createFromParcel(Parcel $r1) throws  {
            return new CarpoolDriveMatchInfo($r1);
        }

        public CarpoolDriveMatchInfo[] newArray(int $i0) throws  {
            return new CarpoolDriveMatchInfo[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public CarpoolDriveMatchInfo() throws  {
        this.question = new QuestionData();
    }

    protected CarpoolDriveMatchInfo(Parcel $r1) throws  {
        this.pickup = (CarpoolLocation) $r1.readParcelable(CarpoolLocation.class.getClassLoader());
        this.dropoff = (CarpoolLocation) $r1.readParcelable(CarpoolLocation.class.getClassLoader());
        this.pickup_time_seconds = $r1.readLong();
        this.detour_duration_seconds = $r1.readInt();
        this.detour_distance_meters = $r1.readDouble();
        this.detour_distance_string = $r1.readString();
        this.origin_to_pickup_duration_seconds = $r1.readInt();
        this.origin_to_pickup_distance_meters = $r1.readDouble();
        this.dropoff_to_destination_duration_seconds = $r1.readInt();
        this.dropoff_to_destination_distance_meters = $r1.readDouble();
        this.pickup_to_dropoff_duration_seconds = $r1.readInt();
        this.total_fee_minor_units = $r1.readInt();
        this.reference_total_fee_minor_units = $r1.readInt();
        this.question = (QuestionData) $r1.readParcelable(QuestionData.class.getClassLoader());
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        $r1.writeParcelable(this.pickup, $i0);
        $r1.writeParcelable(this.dropoff, $i0);
        $r1.writeLong(this.pickup_time_seconds);
        $r1.writeInt(this.detour_duration_seconds);
        $r1.writeDouble(this.detour_distance_meters);
        $r1.writeString(this.detour_distance_string);
        $r1.writeInt(this.origin_to_pickup_duration_seconds);
        $r1.writeDouble(this.origin_to_pickup_distance_meters);
        $r1.writeInt(this.dropoff_to_destination_duration_seconds);
        $r1.writeDouble(this.dropoff_to_destination_distance_meters);
        $r1.writeInt(this.pickup_to_dropoff_duration_seconds);
        $r1.writeInt(this.total_fee_minor_units);
        $r1.writeInt(this.reference_total_fee_minor_units);
        $r1.writeParcelable(this.question, $i0);
    }
}
