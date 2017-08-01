package android.support.v4.app;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class FragmentPagerAdapter extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "FragmentPagerAdapter";
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private final FragmentManager mFragmentManager;

    public abstract Fragment getItem(int i) throws ;

    public Parcelable saveState() throws  {
        return null;
    }

    public FragmentPagerAdapter(FragmentManager $r1) throws  {
        this.mFragmentManager = $r1;
    }

    public void startUpdate(ViewGroup container) throws  {
    }

    public Object instantiateItem(ViewGroup $r1, int $i0) throws  {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        long $l1 = getItemId($i0);
        Fragment $r5 = this.mFragmentManager.findFragmentByTag(makeFragmentName($r1.getId(), $l1));
        Fragment $r6 = $r5;
        if ($r5 != null) {
            this.mCurTransaction.attach($r5);
        } else {
            $r5 = getItem($i0);
            $r6 = $r5;
            this.mCurTransaction.add($r1.getId(), $r5, makeFragmentName($r1.getId(), $l1));
        }
        if ($r6 == this.mCurrentPrimaryItem) {
            return $r6;
        }
        $r6.setMenuVisibility(false);
        $r6.setUserVisibleHint(false);
        return $r6;
    }

    public void destroyItem(ViewGroup container, int position, Object $r3) throws  {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        this.mCurTransaction.detach((Fragment) $r3);
    }

    public void setPrimaryItem(ViewGroup container, int position, Object $r2) throws  {
        Fragment $r3 = (Fragment) $r2;
        if ($r3 != this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                this.mCurrentPrimaryItem.setMenuVisibility(false);
                this.mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if ($r3 != null) {
                $r3.setMenuVisibility(true);
                $r3.setUserVisibleHint(true);
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

    public void restoreState(Parcelable state, ClassLoader loader) throws  {
    }

    public long getItemId(int $i0) throws  {
        return (long) $i0;
    }

    private static String makeFragmentName(int $i0, long $l1) throws  {
        return "android:switcher:" + $i0 + ":" + $l1;
    }
}
