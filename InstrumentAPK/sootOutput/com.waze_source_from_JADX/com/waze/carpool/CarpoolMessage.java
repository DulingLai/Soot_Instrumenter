package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CarpoolMessage implements Parcelable {
    public static final Creator<CarpoolMessage> CREATOR = new C14291();
    public boolean from_me;
    String id;
    public long sent_seconds;
    public String text;
    public boolean unread;

    static class C14291 implements Creator<CarpoolMessage> {
        C14291() throws  {
        }

        public CarpoolMessage createFromParcel(Parcel $r1) throws  {
            return new CarpoolMessage($r1);
        }

        public CarpoolMessage[] newArray(int $i0) throws  {
            return new CarpoolMessage[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public CarpoolMessage(boolean $z0, String $r1, boolean $z1, long $l0, String $r2) throws  {
        this.from_me = $z0;
        this.id = $r1;
        this.unread = $z1;
        this.sent_seconds = $l0;
        this.text = $r2;
    }

    protected CarpoolMessage(Parcel $r1) throws  {
        boolean $z0 = true;
        this.from_me = $r1.readByte() != (byte) 0;
        this.id = $r1.readString();
        if ($r1.readByte() == (byte) 0) {
            $z0 = false;
        }
        this.unread = $z0;
        this.sent_seconds = $r1.readLong();
        this.text = $r1.readString();
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        boolean $z0 = true;
        $r1.writeByte((byte) (this.from_me));
        $r1.writeString(this.id);
        if (!this.unread) {
            $z0 = false;
        }
        $r1.writeByte((byte) $z0);
        $r1.writeLong(this.sent_seconds);
        $r1.writeString(this.text);
    }
}
