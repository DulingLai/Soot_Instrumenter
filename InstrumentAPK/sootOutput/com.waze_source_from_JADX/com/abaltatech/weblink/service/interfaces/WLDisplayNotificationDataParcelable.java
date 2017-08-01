package com.abaltatech.weblink.service.interfaces;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class WLDisplayNotificationDataParcelable implements Parcelable {
    public static final Creator<WLDisplayNotificationDataParcelable> CREATOR = new C03951();
    private String bitmapURL;
    private boolean hasCancelButton;
    private int remainingTimeout;
    private String title;

    static class C03951 implements Creator<WLDisplayNotificationDataParcelable> {
        C03951() throws  {
        }

        public WLDisplayNotificationDataParcelable createFromParcel(Parcel $r1) throws  {
            return new WLDisplayNotificationDataParcelable($r1);
        }

        public WLDisplayNotificationDataParcelable[] newArray(int $i0) throws  {
            return new WLDisplayNotificationDataParcelable[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public WLDisplayNotificationDataParcelable() throws  {
        this.title = "";
        this.bitmapURL = "";
        this.hasCancelButton = false;
        this.remainingTimeout = 1000;
    }

    public WLDisplayNotificationDataParcelable(Parcel $r1) throws  {
        boolean $z0 = true;
        this.title = $r1.readString();
        this.bitmapURL = $r1.readString();
        if ($r1.readByte() != (byte) 1) {
            $z0 = false;
        }
        this.hasCancelButton = $z0;
        this.remainingTimeout = $r1.readInt();
    }

    public String getTitle() throws  {
        return this.title;
    }

    public void setTitle(String $r1) throws  {
        this.title = $r1;
    }

    public String getBitmapURL() throws  {
        return this.bitmapURL;
    }

    public void setBitmapURL(String $r1) throws  {
        this.bitmapURL = $r1;
    }

    public boolean getHasCancelButton() throws  {
        return this.hasCancelButton;
    }

    public void setHasCancelButton(boolean $z0) throws  {
        this.hasCancelButton = $z0;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.title);
        $r1.writeString(this.bitmapURL);
        $r1.writeByte((byte) (this.hasCancelButton));
        $r1.writeInt(this.remainingTimeout);
    }
}
