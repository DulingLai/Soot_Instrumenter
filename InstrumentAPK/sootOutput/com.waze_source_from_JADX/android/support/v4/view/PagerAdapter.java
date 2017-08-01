package android.support.v4.view;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

public abstract class PagerAdapter {
    public static final int POSITION_NONE = -2;
    public static final int POSITION_UNCHANGED = -1;
    private final DataSetObservable mObservable = new DataSetObservable();
    private DataSetObserver mViewPagerObserver;

    public abstract int getCount() throws ;

    public int getItemPosition(Object object) throws  {
        return -1;
    }

    public CharSequence getPageTitle(int position) throws  {
        return null;
    }

    public float getPageWidth(int position) throws  {
        return 1.0f;
    }

    public abstract boolean isViewFromObject(View view, Object obj) throws ;

    public Parcelable saveState() throws  {
        return null;
    }

    public void startUpdate(ViewGroup $r1) throws  {
        startUpdate((View) $r1);
    }

    public Object instantiateItem(ViewGroup $r1, int $i0) throws  {
        return instantiateItem((View) $r1, $i0);
    }

    public void destroyItem(ViewGroup $r1, int $i0, Object $r2) throws  {
        destroyItem((View) $r1, $i0, $r2);
    }

    public void setPrimaryItem(ViewGroup $r1, int $i0, Object $r2) throws  {
        setPrimaryItem((View) $r1, $i0, $r2);
    }

    public void finishUpdate(ViewGroup $r1) throws  {
        finishUpdate((View) $r1);
    }

    public void startUpdate(View container) throws  {
    }

    public Object instantiateItem(View container, int position) throws  {
        throw new UnsupportedOperationException("Required method instantiateItem was not overridden");
    }

    public void destroyItem(View container, int position, Object object) throws  {
        throw new UnsupportedOperationException("Required method destroyItem was not overridden");
    }

    public void setPrimaryItem(View container, int position, Object object) throws  {
    }

    public void finishUpdate(View container) throws  {
    }

    public void restoreState(Parcelable state, ClassLoader loader) throws  {
    }

    public void notifyDataSetChanged() throws  {
        synchronized (this) {
            if (this.mViewPagerObserver != null) {
                this.mViewPagerObserver.onChanged();
            }
        }
        this.mObservable.notifyChanged();
    }

    public void registerDataSetObserver(DataSetObserver $r1) throws  {
        this.mObservable.registerObserver($r1);
    }

    public void unregisterDataSetObserver(DataSetObserver $r1) throws  {
        this.mObservable.unregisterObserver($r1);
    }

    void setViewPagerObserver(DataSetObserver $r1) throws  {
        synchronized (this) {
            this.mViewPagerObserver = $r1;
        }
    }
}
