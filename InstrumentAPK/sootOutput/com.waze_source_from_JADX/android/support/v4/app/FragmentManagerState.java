package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: FragmentManager */
final class FragmentManagerState implements Parcelable {
    public static final Creator<FragmentManagerState> CREATOR = new C00261();
    FragmentState[] mActive;
    int[] mAdded;
    BackStackState[] mBackStack;

    /* compiled from: FragmentManager */
    static class C00261 implements Creator<FragmentManagerState> {
        C00261() throws  {
        }

        public FragmentManagerState createFromParcel(Parcel $r1) throws  {
            return new FragmentManagerState($r1);
        }

        public FragmentManagerState[] newArray(int $i0) throws  {
            return new FragmentManagerState[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public FragmentManagerState(Parcel $r1) throws  {
        this.mActive = (FragmentState[]) $r1.createTypedArray(FragmentState.CREATOR);
        this.mAdded = $r1.createIntArray();
        this.mBackStack = (BackStackState[]) $r1.createTypedArray(BackStackState.CREATOR);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        $r1.writeTypedArray(this.mActive, $i0);
        $r1.writeIntArray(this.mAdded);
        $r1.writeTypedArray(this.mBackStack, $i0);
    }
}
