package com.abaltatech.protocoldispatcher;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public enum EVideoChannelError implements Parcelable {
    VCE_PUBLIC_NOTIFICATION_ERROR,
    VCE_ERROR_OPEN_SOCKET_CONNECTION,
    VCE_SOCKET_CONNECTION_CLOSED;
    
    public static final Creator<EVideoChannelError> CREATOR = null;

    class C03891 implements Creator<EVideoChannelError> {
        C03891() throws  {
        }

        public EVideoChannelError createFromParcel(Parcel $r1) throws  {
            return EVideoChannelError.values()[$r1.readInt()];
        }

        public EVideoChannelError[] newArray(int $i0) throws  {
            return new EVideoChannelError[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    static {
        CREATOR = new C03891();
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeInt(ordinal());
    }
}
