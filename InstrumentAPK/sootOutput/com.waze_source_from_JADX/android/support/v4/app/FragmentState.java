package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;

/* compiled from: Fragment */
final class FragmentState implements Parcelable {
    public static final Creator<FragmentState> CREATOR = new C00271();
    final Bundle mArguments;
    final String mClassName;
    final int mContainerId;
    final boolean mDetached;
    final int mFragmentId;
    final boolean mFromLayout;
    final int mIndex;
    Fragment mInstance;
    final boolean mRetainInstance;
    Bundle mSavedFragmentState;
    final String mTag;

    /* compiled from: Fragment */
    static class C00271 implements Creator<FragmentState> {
        C00271() throws  {
        }

        public FragmentState createFromParcel(Parcel $r1) throws  {
            return new FragmentState($r1);
        }

        public FragmentState[] newArray(int $i0) throws  {
            return new FragmentState[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public FragmentState(Fragment $r1) throws  {
        this.mClassName = $r1.getClass().getName();
        this.mIndex = $r1.mIndex;
        this.mFromLayout = $r1.mFromLayout;
        this.mFragmentId = $r1.mFragmentId;
        this.mContainerId = $r1.mContainerId;
        this.mTag = $r1.mTag;
        this.mRetainInstance = $r1.mRetainInstance;
        this.mDetached = $r1.mDetached;
        this.mArguments = $r1.mArguments;
    }

    public FragmentState(Parcel $r1) throws  {
        boolean $z1;
        boolean $z0 = true;
        this.mClassName = $r1.readString();
        this.mIndex = $r1.readInt();
        this.mFromLayout = $r1.readInt() != 0;
        this.mFragmentId = $r1.readInt();
        this.mContainerId = $r1.readInt();
        this.mTag = $r1.readString();
        if ($r1.readInt() != 0) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        this.mRetainInstance = $z1;
        if ($r1.readInt() == 0) {
            $z0 = false;
        }
        this.mDetached = $z0;
        this.mArguments = $r1.readBundle();
        this.mSavedFragmentState = $r1.readBundle();
    }

    public Fragment instantiate(FragmentHostCallback $r1, Fragment $r2) throws  {
        if (this.mInstance != null) {
            return this.mInstance;
        }
        Context $r4 = $r1.getContext();
        if (this.mArguments != null) {
            this.mArguments.setClassLoader($r4.getClassLoader());
        }
        this.mInstance = Fragment.instantiate($r4, this.mClassName, this.mArguments);
        if (this.mSavedFragmentState != null) {
            this.mSavedFragmentState.setClassLoader($r4.getClassLoader());
            this.mInstance.mSavedFragmentState = this.mSavedFragmentState;
        }
        this.mInstance.setIndex(this.mIndex, $r2);
        this.mInstance.mFromLayout = this.mFromLayout;
        this.mInstance.mRestored = true;
        this.mInstance.mFragmentId = this.mFragmentId;
        this.mInstance.mContainerId = this.mContainerId;
        this.mInstance.mTag = this.mTag;
        this.mInstance.mRetainInstance = this.mRetainInstance;
        this.mInstance.mDetached = this.mDetached;
        this.mInstance.mFragmentManager = $r1.mFragmentManager;
        if (FragmentManagerImpl.DEBUG) {
            Log.v("FragmentManager", "Instantiated fragment " + this.mInstance);
        }
        return this.mInstance;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        byte $b2;
        byte $b1 = (byte) 1;
        $r1.writeString(this.mClassName);
        $r1.writeInt(this.mIndex);
        $r1.writeInt(this.mFromLayout ? (byte) 1 : (byte) 0);
        $r1.writeInt(this.mFragmentId);
        $r1.writeInt(this.mContainerId);
        $r1.writeString(this.mTag);
        if (this.mRetainInstance) {
            $b2 = (byte) 1;
        } else {
            $b2 = (byte) 0;
        }
        $r1.writeInt($b2);
        if (!this.mDetached) {
            $b1 = (byte) 0;
        }
        $r1.writeInt($b1);
        $r1.writeBundle(this.mArguments);
        $r1.writeBundle(this.mSavedFragmentState);
    }
}
