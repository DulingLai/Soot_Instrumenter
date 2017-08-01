package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CarpoolRideMessages implements Parcelable {
    public static final Creator<CarpoolRideMessages> CREATOR = new C15231();
    CarpoolMessage[] messages;
    String ride_id;

    static class C15231 implements Creator<CarpoolRideMessages> {
        C15231() throws  {
        }

        public CarpoolRideMessages createFromParcel(Parcel $r1) throws  {
            return new CarpoolRideMessages($r1);
        }

        public CarpoolRideMessages[] newArray(int $i0) throws  {
            return new CarpoolRideMessages[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public CarpoolRideMessages(String $r1, CarpoolMessage[] $r2) throws  {
        this.ride_id = $r1;
        this.messages = $r2;
    }

    protected CarpoolRideMessages(Parcel $r1) throws  {
        this.ride_id = $r1.readString();
        this.messages = (CarpoolMessage[]) $r1.createTypedArray(CarpoolMessage.CREATOR);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        $r1.writeString(this.ride_id);
        $r1.writeTypedArray(this.messages, $i0);
    }

    public boolean hasMessages() throws  {
        return this.messages != null && this.messages.length > 0;
    }

    public int numMessages() throws  {
        return this.messages != null ? this.messages.length : 0;
    }

    public CarpoolMessage lastMessage() throws  {
        if (hasMessages()) {
            return this.messages[this.messages.length - 1];
        }
        return null;
    }
}
