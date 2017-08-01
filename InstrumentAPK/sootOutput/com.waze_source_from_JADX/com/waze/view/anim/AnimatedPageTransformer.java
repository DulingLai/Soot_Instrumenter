package com.waze.view.anim;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.Iterator;

public class AnimatedPageTransformer implements OnPageChangeListener {
    int mCurPage = -1;
    float mCurScroll = -1.0f;
    boolean mFwd = true;
    SparseArray<ArrayList<PageAnimation>> mFwdAnimations = new SparseArray(8);
    SparseArray<ArrayList<PageAnimation>> mRevAnimations = new SparseArray(8);

    private class PageAnimation {
        final Animation f158a;
        final View f159v;

        public PageAnimation(Animation a, View v) {
            this.f158a = a;
            this.f159v = v;
        }
    }

    class PageInterpolator implements Interpolator {
        final int _fromPage;
        final float _fromScroll;
        final float _toScroll;

        public PageInterpolator(int fromPage, float fromScroll, float toScroll) {
            this._fromPage = fromPage;
            this._fromScroll = fromScroll;
            this._toScroll = toScroll;
        }

        public float getInterpolation(float input) {
            if (AnimatedPageTransformer.this.mCurPage < this._fromPage) {
                return 0.0f;
            }
            if (AnimatedPageTransformer.this.mCurPage > this._fromPage) {
                return 1.0f;
            }
            if (AnimatedPageTransformer.this.mCurScroll < this._fromScroll) {
                return 0.0f;
            }
            if (AnimatedPageTransformer.this.mCurScroll > this._toScroll) {
                return 1.0f;
            }
            return (AnimatedPageTransformer.this.mCurScroll - this._fromScroll) / (this._toScroll - this._fromScroll);
        }
    }

    public AnimatedPageTransformer(ViewPager vp) {
        vp.addOnPageChangeListener(this);
    }

    public PageInterpolator getPageInterpolator(int fromPage, float fromScroll, float toScroll) {
        return new PageInterpolator(fromPage, fromScroll, toScroll);
    }

    public void addAnimation(View v, Animation a, int fromPage, float fromScroll, float toScroll, boolean forward, boolean reverse) {
        ArrayList<PageAnimation> animations;
        a.setFillAfter(true);
        a.setFillBefore(true);
        a.setDuration(0);
        a.setRepeatCount(-1);
        a.setInterpolator(new PageInterpolator(fromPage, fromScroll, toScroll));
        PageAnimation pa = new PageAnimation(a, v);
        if (forward) {
            animations = (ArrayList) this.mFwdAnimations.get(fromPage);
            if (animations == null) {
                animations = new ArrayList(8);
                this.mFwdAnimations.put(fromPage, animations);
            }
            animations.add(pa);
        }
        if (reverse) {
            animations = (ArrayList) this.mRevAnimations.get(fromPage);
            if (animations == null) {
                animations = new ArrayList(8);
                this.mRevAnimations.put(fromPage, animations);
            }
            animations.add(pa);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        boolean fwd = true;
        if (this.mCurPage == position) {
            if (positionOffset <= this.mCurScroll) {
                fwd = false;
            }
        } else if (position <= this.mCurPage) {
            fwd = false;
        }
        this.mCurScroll = positionOffset;
        if (this.mCurPage != position || this.mFwd != fwd) {
            Iterator it;
            ArrayList<PageAnimation> animations = (ArrayList) this.mFwdAnimations.get(this.mCurPage);
            if (animations != null) {
                it = animations.iterator();
                while (it.hasNext()) {
                    ((PageAnimation) it.next()).f159v.clearAnimation();
                }
            }
            animations = (ArrayList) this.mRevAnimations.get(this.mCurPage);
            if (animations != null) {
                it = animations.iterator();
                while (it.hasNext()) {
                    ((PageAnimation) it.next()).f159v.clearAnimation();
                }
            }
            PageAnimation pa;
            if (fwd) {
                animations = (ArrayList) this.mFwdAnimations.get(position);
                if (animations != null) {
                    it = animations.iterator();
                    while (it.hasNext()) {
                        pa = (PageAnimation) it.next();
                        pa.f159v.startAnimation(pa.f158a);
                    }
                }
            } else {
                animations = (ArrayList) this.mRevAnimations.get(position);
                if (animations != null) {
                    it = animations.iterator();
                    while (it.hasNext()) {
                        pa = (PageAnimation) it.next();
                        pa.f159v.startAnimation(pa.f158a);
                    }
                }
            }
            this.mCurPage = position;
            this.mFwd = fwd;
        }
    }

    public void onPageSelected(int i) {
    }

    public void onPageScrollStateChanged(int i) {
    }
}
