package com.waze.view.popups;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyPageAdapter extends PagerAdapter {
    ViewGroup Container;
    int mNumberOfViews;
    View[] mViews;

    public MyPageAdapter(int nCount, View[] Views) {
        this.mNumberOfViews = nCount;
        this.mViews = Views;
    }

    public int getCount() {
        return this.mNumberOfViews;
    }

    public void finishUpdate(ViewGroup container) {
        this.Container = container;
        super.finishUpdate(container);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        if (this.mViews[position] == null || this.mViews[position].getParent() != null) {
            return null;
        }
        container.addView(this.mViews[position]);
        return this.mViews[position];
    }

    public void detachAllItems() {
        for (int i = 0; i < this.mNumberOfViews; i++) {
            destroyItem(this.Container, i, this.mViews[i]);
        }
        this.mNumberOfViews = 0;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object != null) {
            container.removeView((View) object);
        }
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
