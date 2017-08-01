package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepName;

@KeepName
/* compiled from: dalvik_source_com.waze.apk */
public final class BinderWrapper implements Parcelable {
    public static final Creator<BinderWrapper> CREATOR = new C06961();
    private IBinder zzakb;

    /* compiled from: dalvik_source_com.waze.apk */
    class C06961 implements Creator<BinderWrapper> {
        C06961() throws  {
        }

        public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
            return zzds($r1);
        }

        public /* synthetic */ Object[] newArray(int $i0) throws  {
            return zzim($i0);
        }

        public BinderWrapper zzds(Parcel $r1) throws  {
            return new BinderWrapper($r1);
        }

        public BinderWrapper[] zzim(int $i0) throws  {
            return new BinderWrapper[$i0];
        }
    }

    public BinderWrapper() throws  {
        this.zzakb = null;
    }

    public BinderWrapper(IBinder $r1) throws  {
        this.zzakb = null;
        this.zzakb = $r1;
    }

    private BinderWrapper(Parcel $r1) throws  {
        this.zzakb = null;
        this.zzakb = $r1.readStrongBinder();
    }

    public int describeContents() throws  {
        return 0;
    }

    public void writeToParcel(Parcel $r1, int i) throws  {
        $r1.writeStrongBinder(this.zzakb);
    }
}
