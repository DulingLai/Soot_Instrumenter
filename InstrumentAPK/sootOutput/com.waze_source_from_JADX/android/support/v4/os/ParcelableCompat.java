package android.support.v4.os;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import dalvik.annotation.Signature;

public final class ParcelableCompat {

    static class CompatCreator<T> implements Creator<T> {
        final ParcelableCompatCreatorCallbacks<T> mCallbacks;

        public CompatCreator(@Signature({"(", "Landroid/support/v4/os/ParcelableCompatCreatorCallbacks", "<TT;>;)V"}) ParcelableCompatCreatorCallbacks<T> $r1) throws  {
            this.mCallbacks = $r1;
        }

        public T createFromParcel(@Signature({"(", "Landroid/os/Parcel;", ")TT;"}) Parcel $r1) throws  {
            return this.mCallbacks.createFromParcel($r1, null);
        }

        public T[] newArray(@Signature({"(I)[TT;"}) int $i0) throws  {
            return this.mCallbacks.newArray($i0);
        }
    }

    public static <T> Creator<T> newCreator(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Landroid/support/v4/os/ParcelableCompatCreatorCallbacks", "<TT;>;)", "Landroid/os/Parcelable$Creator", "<TT;>;"}) ParcelableCompatCreatorCallbacks<T> $r0) throws  {
        if (VERSION.SDK_INT >= 13) {
            return ParcelableCompatCreatorHoneycombMR2Stub.instantiate($r0);
        }
        return new CompatCreator($r0);
    }

    private ParcelableCompat() throws  {
    }
}
