package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;

/* compiled from: BackStackRecord */
final class BackStackState implements Parcelable {
    public static final Creator<BackStackState> CREATOR = new C00151();
    final int mBreadCrumbShortTitleRes;
    final CharSequence mBreadCrumbShortTitleText;
    final int mBreadCrumbTitleRes;
    final CharSequence mBreadCrumbTitleText;
    final int mIndex;
    final String mName;
    final int[] mOps;
    final ArrayList<String> mSharedElementSourceNames;
    final ArrayList<String> mSharedElementTargetNames;
    final int mTransition;
    final int mTransitionStyle;

    /* compiled from: BackStackRecord */
    static class C00151 implements Creator<BackStackState> {
        C00151() throws  {
        }

        public BackStackState createFromParcel(Parcel $r1) throws  {
            return new BackStackState($r1);
        }

        public BackStackState[] newArray(int $i0) throws  {
            return new BackStackState[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public BackStackState(BackStackRecord $r1) throws  {
        Op $r2;
        BackStackState backStackState = this;
        int $i0 = 0;
        for ($r2 = $r1.mHead; $r2 != null; $r2 = $r2.next) {
            if ($r2.removed != null) {
                $i0 += $r2.removed.size();
            }
        }
        this.mOps = new int[(($r1.mNumOp * 7) + $i0)];
        if ($r1.mAddToBackStack) {
            $r2 = $r1.mHead;
            int $i1 = 0;
            while ($r2 != null) {
                $i0 = $i1 + 1;
                this.mOps[$i1] = $r2.cmd;
                $i1 = $i0 + 1;
                this.mOps[$i0] = $r2.fragment != null ? $r2.fragment.mIndex : -1;
                $i0 = $i1 + 1;
                this.mOps[$i1] = $r2.enterAnim;
                $i1 = $i0 + 1;
                this.mOps[$i0] = $r2.exitAnim;
                $i0 = $i1 + 1;
                this.mOps[$i1] = $r2.popEnterAnim;
                $i1 = $i0 + 1;
                this.mOps[$i0] = $r2.popExitAnim;
                if ($r2.removed != null) {
                    $i0 = $r2.removed.size();
                    int $i2 = $i1 + 1;
                    this.mOps[$i1] = $i0;
                    $i1 = 0;
                    while ($i1 < $i0) {
                        this.mOps[$i2] = ((Fragment) $r2.removed.get($i1)).mIndex;
                        $i1++;
                        $i2++;
                    }
                    $i0 = $i2;
                } else {
                    $i0 = $i1 + 1;
                    this.mOps[$i1] = 0;
                }
                $r2 = $r2.next;
                $i1 = $i0;
            }
            this.mTransition = $r1.mTransition;
            this.mTransitionStyle = $r1.mTransitionStyle;
            String str = $r1.mName;
            String $r8 = str;
            this.mName = str;
            this.mIndex = $r1.mIndex;
            this.mBreadCrumbTitleRes = $r1.mBreadCrumbTitleRes;
            CharSequence charSequence = $r1.mBreadCrumbTitleText;
            CharSequence $r9 = charSequence;
            this.mBreadCrumbTitleText = charSequence;
            this.mBreadCrumbShortTitleRes = $r1.mBreadCrumbShortTitleRes;
            charSequence = $r1.mBreadCrumbShortTitleText;
            $r9 = charSequence;
            this.mBreadCrumbShortTitleText = charSequence;
            this.mSharedElementSourceNames = $r1.mSharedElementSourceNames;
            this.mSharedElementTargetNames = $r1.mSharedElementTargetNames;
            return;
        }
        throw new IllegalStateException("Not on back stack");
    }

    public BackStackState(Parcel $r1) throws  {
        this.mOps = $r1.createIntArray();
        this.mTransition = $r1.readInt();
        this.mTransitionStyle = $r1.readInt();
        this.mName = $r1.readString();
        this.mIndex = $r1.readInt();
        this.mBreadCrumbTitleRes = $r1.readInt();
        this.mBreadCrumbTitleText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel($r1);
        this.mBreadCrumbShortTitleRes = $r1.readInt();
        this.mBreadCrumbShortTitleText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel($r1);
        this.mSharedElementSourceNames = $r1.createStringArrayList();
        this.mSharedElementTargetNames = $r1.createStringArrayList();
    }

    public BackStackRecord instantiate(FragmentManagerImpl $r1) throws  {
        BackStackRecord $r2 = new BackStackRecord($r1);
        int $i0 = 0;
        int $i2 = 0;
        while ($i0 < this.mOps.length) {
            Op $r3 = new Op();
            int $i1 = $i0 + 1;
            $r3.cmd = this.mOps[$i0];
            if (FragmentManagerImpl.DEBUG) {
                Log.v("FragmentManager", "Instantiate " + $r2 + " op #" + $i2 + " base fragment #" + this.mOps[$i1]);
            }
            $i0 = $i1 + 1;
            $i1 = this.mOps[$i1];
            if ($i1 >= 0) {
                $r3.fragment = (Fragment) $r1.mActive.get($i1);
            } else {
                $r3.fragment = null;
            }
            $i1 = $i0 + 1;
            $r3.enterAnim = this.mOps[$i0];
            $i0 = $i1 + 1;
            $r3.exitAnim = this.mOps[$i1];
            $i1 = $i0 + 1;
            $r3.popEnterAnim = this.mOps[$i0];
            $i0 = $i1 + 1;
            $r3.popExitAnim = this.mOps[$i1];
            $i1 = $i0 + 1;
            $i0 = this.mOps[$i0];
            if ($i0 > 0) {
                $r3.removed = new ArrayList($i0);
                int $i3 = 0;
                while ($i3 < $i0) {
                    if (FragmentManagerImpl.DEBUG) {
                        Log.v("FragmentManager", "Instantiate " + $r2 + " set remove fragment #" + this.mOps[$i1]);
                    }
                    int $i4 = $i1 + 1;
                    $r3.removed.add((Fragment) $r1.mActive.get(this.mOps[$i1]));
                    $i3++;
                    $i1 = $i4;
                }
            }
            $i0 = $i1;
            $r2.mEnterAnim = $r3.enterAnim;
            $r2.mExitAnim = $r3.exitAnim;
            $r2.mPopEnterAnim = $r3.popEnterAnim;
            $r2.mPopExitAnim = $r3.popExitAnim;
            $r2.addOp($r3);
            $i2++;
        }
        $r2.mTransition = this.mTransition;
        $r2.mTransitionStyle = this.mTransitionStyle;
        $r2.mName = this.mName;
        $r2.mIndex = this.mIndex;
        $r2.mAddToBackStack = true;
        $r2.mBreadCrumbTitleRes = this.mBreadCrumbTitleRes;
        CharSequence $r10 = this.mBreadCrumbTitleText;
        $r2.mBreadCrumbTitleText = $r10;
        $r2.mBreadCrumbShortTitleRes = this.mBreadCrumbShortTitleRes;
        $r10 = this.mBreadCrumbShortTitleText;
        $r2.mBreadCrumbShortTitleText = $r10;
        $r2.mSharedElementSourceNames = this.mSharedElementSourceNames;
        $r2.mSharedElementTargetNames = this.mSharedElementTargetNames;
        $r2.bumpBackStackNesting(1);
        return $r2;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeIntArray(this.mOps);
        $r1.writeInt(this.mTransition);
        $r1.writeInt(this.mTransitionStyle);
        $r1.writeString(this.mName);
        $r1.writeInt(this.mIndex);
        $r1.writeInt(this.mBreadCrumbTitleRes);
        TextUtils.writeToParcel(this.mBreadCrumbTitleText, $r1, 0);
        $r1.writeInt(this.mBreadCrumbShortTitleRes);
        TextUtils.writeToParcel(this.mBreadCrumbShortTitleText, $r1, 0);
        $r1.writeStringList(this.mSharedElementSourceNames);
        $r1.writeStringList(this.mSharedElementTargetNames);
    }
}
