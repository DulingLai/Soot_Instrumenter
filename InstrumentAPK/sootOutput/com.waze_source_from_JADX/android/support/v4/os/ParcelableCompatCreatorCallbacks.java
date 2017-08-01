package android.support.v4.os;

import android.os.Parcel;
import dalvik.annotation.Signature;

public interface ParcelableCompatCreatorCallbacks<T> {
    T createFromParcel(@Signature({"(", "Landroid/os/Parcel;", "Ljava/lang/ClassLoader;", ")TT;"}) Parcel parcel, @Signature({"(", "Landroid/os/Parcel;", "Ljava/lang/ClassLoader;", ")TT;"}) ClassLoader classLoader) throws ;

    T[] newArray(@Signature({"(I)[TT;"}) int i) throws ;
}
