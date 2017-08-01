package com.waze;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SDKNavigationItem implements Parcelable {
    public static final Creator<SDKNavigationItem> CREATOR = new C12951();
    public String address;
    public String distance;
    public int instruction;
    public int instructionExit;

    static class C12951 implements Creator<SDKNavigationItem> {
        C12951() throws  {
        }

        public SDKNavigationItem createFromParcel(Parcel $r1) throws  {
            return new SDKNavigationItem($r1);
        }

        public SDKNavigationItem[] newArray(int $i0) throws  {
            return new SDKNavigationItem[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public SDKNavigationItem(String $r1, String $r2, int $i0, int $i1) throws  {
        this.distance = $r1;
        this.address = $r2;
        this.instruction = $i0;
        this.instructionExit = $i1;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.distance);
        $r1.writeString(this.address);
        $r1.writeInt(this.instruction);
        $r1.writeInt(this.instructionExit);
    }

    public SDKNavigationItem(Parcel $r1) throws  {
        this.distance = $r1.readString();
        this.address = $r1.readString();
        this.instruction = $r1.readInt();
        this.instructionExit = $r1.readInt();
    }
}
