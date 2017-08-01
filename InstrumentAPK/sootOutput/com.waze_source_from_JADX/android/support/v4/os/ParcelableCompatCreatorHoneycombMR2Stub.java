package android.support.v4.os;

import android.os.Parcelable.Creator;
import dalvik.annotation.Signature;

/* compiled from: ParcelableCompatHoneycombMR2 */
class ParcelableCompatCreatorHoneycombMR2Stub {
    ParcelableCompatCreatorHoneycombMR2Stub() throws  {
    }

    static <T> Creator<T> instantiate(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Landroid/support/v4/os/ParcelableCompatCreatorCallbacks", "<TT;>;)", "Landroid/os/Parcelable$Creator", "<TT;>;"}) ParcelableCompatCreatorCallbacks<T> $r0) throws  {
        return new ParcelableCompatCreatorHoneycombMR2($r0);
    }
}
