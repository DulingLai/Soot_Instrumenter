package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class FastSafeParcelableJsonResponse extends FastJsonResponse implements SafeParcelable {
    public final int describeContents() throws  {
        return 0;
    }

    public Object getValueObject(String str) throws  {
        return null;
    }

    public boolean isPrimitiveFieldSet(String str) throws  {
        return false;
    }

    public byte[] toByteArray() throws  {
        Parcel $r1 = Parcel.obtain();
        writeToParcel($r1, 0);
        byte[] $r2 = $r1.marshall();
        $r1.recycle();
        return $r2;
    }
}
