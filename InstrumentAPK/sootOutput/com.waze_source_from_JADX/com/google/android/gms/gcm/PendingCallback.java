package com.google.android.gms.gcm;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: dalvik_source_com.waze.apk */
public class PendingCallback implements Parcelable {
    public static final Creator<PendingCallback> CREATOR = new C07401();
    final IBinder zzakb;

    /* compiled from: dalvik_source_com.waze.apk */
    class C07401 implements Creator<PendingCallback> {
        C07401() throws  {
        }

        public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
            return zzoi($r1);
        }

        public /* synthetic */ Object[] newArray(int $i0) throws  {
            return zzvk($i0);
        }

        public PendingCallback zzoi(Parcel $r1) throws  {
            return new PendingCallback($r1);
        }

        public PendingCallback[] zzvk(int $i0) throws  {
            return new PendingCallback[$i0];
        }
    }

    public PendingCallback(IBinder $r1) throws  {
        this.zzakb = $r1;
    }

    public PendingCallback(Parcel $r1) throws  {
        this.zzakb = $r1.readStrongBinder();
    }

    public int describeContents() throws  {
        return 0;
    }

    public IBinder getIBinder() throws  {
        return this.zzakb;
    }

    public void writeToParcel(Parcel $r1, int i) throws  {
        $r1.writeStrongBinder(this.zzakb);
    }
}
