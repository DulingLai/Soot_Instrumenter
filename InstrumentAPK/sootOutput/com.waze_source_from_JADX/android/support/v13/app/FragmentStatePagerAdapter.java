package android.support.v13.app;

import android.app.Fragment;
import android.app.Fragment.SavedState;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public abstract class FragmentStatePagerAdapter extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentStatePagerAdapter";
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments = new ArrayList();
    private ArrayList<SavedState> mSavedState = new ArrayList();

    public abstract Fragment getItem(int i) throws ;

    public FragmentStatePagerAdapter(FragmentManager $r1) throws  {
        this.mFragmentManager = $r1;
    }

    public void startUpdate(ViewGroup container) throws  {
    }

    public Object instantiateItem(ViewGroup $r1, int $i0) throws  {
        Fragment $r4;
        if (this.mFragments.size() > $i0) {
            $r4 = (Fragment) this.mFragments.get($i0);
            if ($r4 != null) {
                return $r4;
            }
        }
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        $r4 = getItem($i0);
        if (this.mSavedState.size() > $i0) {
            SavedState $r7 = (SavedState) this.mSavedState.get($i0);
            if ($r7 != null) {
                $r4.setInitialSavedState($r7);
            }
        }
        while (this.mFragments.size() <= $i0) {
            this.mFragments.add(null);
        }
        FragmentCompat.setMenuVisibility($r4, false);
        FragmentCompat.setUserVisibleHint($r4, false);
        this.mFragments.set($i0, $r4);
        this.mCurTransaction.add($r1.getId(), $r4);
        return $r4;
    }

    public void destroyItem(ViewGroup container, int $i0, Object $r2) throws  {
        Fragment $r4 = (Fragment) $r2;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        while (this.mSavedState.size() <= $i0) {
            this.mSavedState.add(null);
        }
        this.mSavedState.set($i0, $r4.isAdded() ? this.mFragmentManager.saveFragmentInstanceState($r4) : null);
        this.mFragments.set($i0, null);
        this.mCurTransaction.remove($r4);
    }

    public void setPrimaryItem(ViewGroup container, int position, Object $r2) throws  {
        Fragment $r3 = (Fragment) $r2;
        if ($r3 != this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                FragmentCompat.setMenuVisibility(this.mCurrentPrimaryItem, false);
                FragmentCompat.setUserVisibleHint(this.mCurrentPrimaryItem, false);
            }
            if ($r3 != null) {
                FragmentCompat.setMenuVisibility($r3, true);
                FragmentCompat.setUserVisibleHint($r3, true);
            }
            this.mCurrentPrimaryItem = $r3;
        }
    }

    public void finishUpdate(ViewGroup container) throws  {
        if (this.mCurTransaction != null) {
            this.mCurTransaction.commitAllowingStateLoss();
            this.mCurTransaction = null;
            this.mFragmentManager.executePendingTransactions();
        }
    }

    public boolean isViewFromObject(View $r1, Object $r3) throws  {
        return ((Fragment) $r3).getView() == $r1;
    }

    public Parcelable saveState() throws  {
        Bundle $r2 = null;
        if (this.mSavedState.size() > 0) {
            $r2 = new Bundle();
            SavedState[] $r1 = new SavedState[this.mSavedState.size()];
            this.mSavedState.toArray($r1);
            $r2.putParcelableArray("states", $r1);
        }
        for (int $i0 = 0; $i0 < this.mFragments.size(); $i0++) {
            Fragment $r5 = (Fragment) this.mFragments.get($i0);
            if ($r5 != null && $r5.isAdded()) {
                if ($r2 == null) {
                    $r2 = new Bundle();
                }
                this.mFragmentManager.putFragment($r2, "f" + $i0, $r5);
            }
        }
        return $r2;
    }

    public void restoreState(Parcelable $r1, ClassLoader $r2) throws  {
        if ($r1 != null) {
            int $i0;
            Bundle $r3 = (Bundle) $r1;
            $r3.setClassLoader($r2);
            Parcelable[] $r4 = $r3.getParcelableArray("states");
            this.mSavedState.clear();
            this.mFragments.clear();
            if ($r4 != null) {
                for (Parcelable $r12 : $r4) {
                    this.mSavedState.add((SavedState) $r12);
                }
            }
            for (String $r10 : $r3.keySet()) {
                if ($r10.startsWith("f")) {
                    $i0 = Integer.parseInt($r10.substring(1));
                    FragmentManager $r122 = this.mFragmentManager;
                    Fragment $r13 = $r122.getFragment($r3, $r10);
                    if ($r13 != null) {
                        while (this.mFragments.size() <= $i0) {
                            this.mFragments.add(null);
                        }
                        FragmentCompat.setMenuVisibility($r13, false);
                        this.mFragments.set($i0, $r13);
                    } else {
                        Log.w(TAG, "Bad fragment at key " + $r10);
                    }
                }
            }
        }
    }
}
