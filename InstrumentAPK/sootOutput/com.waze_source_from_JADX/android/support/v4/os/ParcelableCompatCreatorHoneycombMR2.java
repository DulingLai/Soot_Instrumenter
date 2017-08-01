package android.support.v4.os;

import android.os.Parcel;
import android.os.Parcelable.ClassLoaderCreator;
import dalvik.annotation.Signature;

/* compiled from: ParcelableCompatHoneycombMR2 */
class ParcelableCompatCreatorHoneycombMR2<T> implements ClassLoaderCreator<T> {
    private final ParcelableCompatCreatorCallbacks<T> mCallbacks;

    public ParcelableCompatCreatorHoneycombMR2(@Signature({"(", "Landroid/support/v4/os/ParcelableCompatCreatorCallbacks", "<TT;>;)V"}) ParcelableCompatCreatorCallbacks<T> $r1) throws  {
        this.mCallbacks = $r1;
    }

    public T createFromParcel(@Signature({"(", "Landroid/os/Parcel;", ")TT;"}) Parcel $r1) throws  {
        return this.mCallbacks.createFromParcel($r1, null);
    }

    public T createFromParcel(@Signature({"(", "Landroid/os/Parcel;", "Ljava/lang/ClassLoader;", ")TT;"}) Parcel $r1, @Signature({"(", "Landroid/os/Parcel;", "Ljava/lang/ClassLoader;", ")TT;"}) ClassLoader $r2) throws  {
        return this.mCallbacks.createFromParcel($r1, $r2);
    }

    public T[] newArray(@Signature({"(I)[TT;"}) int $i0) throws  {
        return this.mCallbacks.newArray($i0);
    }
}
