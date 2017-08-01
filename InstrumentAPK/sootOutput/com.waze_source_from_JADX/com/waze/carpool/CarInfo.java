package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CarInfo implements Parcelable {
    public static final Creator<CarInfo> CREATOR = new C13791();
    String color;
    String license_plate;
    String make;
    String model;
    String photo_url;

    static class C13791 implements Creator<CarInfo> {
        C13791() throws  {
        }

        public CarInfo createFromParcel(Parcel $r1) throws  {
            return new CarInfo($r1);
        }

        public CarInfo[] newArray(int $i0) throws  {
            return new CarInfo[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    protected CarInfo(Parcel $r1) throws  {
        this.make = $r1.readString();
        this.model = $r1.readString();
        this.color = $r1.readString();
        this.license_plate = $r1.readString();
        this.photo_url = $r1.readString();
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.make);
        $r1.writeString(this.model);
        $r1.writeString(this.color);
        $r1.writeString(this.license_plate);
        $r1.writeString(this.photo_url);
    }
}
