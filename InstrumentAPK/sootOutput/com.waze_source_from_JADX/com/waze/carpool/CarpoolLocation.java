package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.waze.navigate.AddressItem;
import com.waze.strings.DisplayStrings;

public class CarpoolLocation implements Parcelable {
    public static final Creator<CarpoolLocation> CREATOR = new C14281();
    public String address;
    String country_code;
    public int lat;
    public int lon;
    public String placeName;
    int type;

    static class C14281 implements Creator<CarpoolLocation> {
        C14281() throws  {
        }

        public CarpoolLocation createFromParcel(Parcel $r1) throws  {
            return new CarpoolLocation($r1);
        }

        public CarpoolLocation[] newArray(int $i0) throws  {
            return new CarpoolLocation[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    protected CarpoolLocation(Parcel $r1) throws  {
        this.address = $r1.readString();
        this.placeName = $r1.readString();
        this.lon = $r1.readInt();
        this.lat = $r1.readInt();
        this.country_code = $r1.readString();
        this.type = $r1.readInt();
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.address);
        $r1.writeString(this.placeName);
        $r1.writeInt(this.lon);
        $r1.writeInt(this.lat);
        $r1.writeString(this.country_code);
        $r1.writeInt(this.type);
    }

    public int getAddressItemType() throws  {
        if (this.type == 1) {
            return 1;
        }
        if (this.type == 2) {
            return 3;
        }
        return this.type == 3 ? 5 : 9;
    }

    public boolean isHome() throws  {
        if (getAddressItemType() == 1) {
            return true;
        }
        if ("home".equalsIgnoreCase(this.placeName)) {
            return true;
        }
        return DisplayStrings.displayString(287).equalsIgnoreCase(this.placeName);
    }

    public boolean isWork() throws  {
        if (getAddressItemType() == 3) {
            return true;
        }
        if ("work".equalsIgnoreCase(this.placeName)) {
            return true;
        }
        return DisplayStrings.displayString(288).equalsIgnoreCase(this.placeName);
    }

    public AddressItem toAddressItem() throws  {
        int $i0 = this.lon;
        Integer $r5 = Integer.valueOf($i0);
        $i0 = this.lat;
        return new AddressItem($r5, Integer.valueOf($i0), this.placeName, this.address, this.address, null, null, null, Integer.valueOf(99), null, Integer.valueOf(getAddressItemType()), null, null, null, null, null, null, null, null, null, null, null);
    }

    public boolean isValid() throws  {
        return (this.lon == 0 && this.lat == 0) ? false : true;
    }
}
