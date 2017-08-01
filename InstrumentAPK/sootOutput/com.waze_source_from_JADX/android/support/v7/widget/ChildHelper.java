package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import java.util.ArrayList;
import java.util.List;

class ChildHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "ChildrenHelper";
    final Bucket mBucket = new Bucket();
    final Callback mCallback;
    final List<View> mHiddenViews = new ArrayList();

    static class Bucket {
        static final int BITS_PER_WORD = 64;
        static final long LAST_BIT = Long.MIN_VALUE;
        long mData = 0;
        Bucket next;

        void clear(int r1) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v7.widget.ChildHelper.Bucket.clear(int):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-long
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 6 more
*/
            /*
            // Can't load method instructions.
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ChildHelper.Bucket.clear(int):void");
        }

        Bucket() throws  {
        }

        void set(int $i0) throws  {
            if ($i0 >= 64) {
                ensureNext();
                this.next.set($i0 - 64);
                return;
            }
            this.mData |= 1 << $i0;
        }

        private void ensureNext() throws  {
            if (this.next == null) {
                this.next = new Bucket();
            }
        }

        boolean get(int $i0) throws  {
            if ($i0 < 64) {
                return (this.mData & (1 << $i0)) != 0;
            } else {
                ensureNext();
                return this.next.get($i0 - 64);
            }
        }

        void reset() throws  {
            this.mData = 0;
            if (this.next != null) {
                this.next.reset();
            }
        }

        void insert(int $i0, boolean $z0) throws  {
            if ($i0 >= 64) {
                ensureNext();
                this.next.insert($i0 - 64, $z0);
                return;
            }
            boolean $z1 = (this.mData & LAST_BIT) != 0;
            long $l1 = (1 << $i0) - 1;
            this.mData = (this.mData & $l1) | ((this.mData & (-1 ^ $l1)) << 1);
            if ($z0) {
                set($i0);
            } else {
                clear($i0);
            }
            if ($z1 || this.next != null) {
                ensureNext();
                this.next.insert(0, $z1);
            }
        }

        boolean remove(int $i0) throws  {
            if ($i0 >= 64) {
                ensureNext();
                return this.next.remove($i0 - 64);
            }
            long $l1 = 1 << $i0;
            boolean $z0 = (this.mData & $l1) != 0;
            this.mData &= -1 ^ $l1;
            long $l2 = $l1 - 1;
            this.mData = (this.mData & $l2) | Long.rotateRight(this.mData & (-1 ^ $l2), 1);
            if (this.next == null) {
                return $z0;
            }
            if (this.next.get(0)) {
                set(63);
            }
            this.next.remove(0);
            return $z0;
        }

        int countOnesBefore(int $i0) throws  {
            if (this.next == null) {
                if ($i0 >= 64) {
                    return Long.bitCount(this.mData);
                }
                return Long.bitCount(this.mData & ((1 << $i0) - 1));
            } else if ($i0 < 64) {
                return Long.bitCount(this.mData & ((1 << $i0) - 1));
            } else {
                return this.next.countOnesBefore($i0 - 64) + Long.bitCount(this.mData);
            }
        }

        public String toString() throws  {
            return this.next == null ? Long.toBinaryString(this.mData) : this.next.toString() + "xx" + Long.toBinaryString(this.mData);
        }
    }

    interface Callback {
        void addView(View view, int i) throws ;

        void attachViewToParent(View view, int i, LayoutParams layoutParams) throws ;

        void detachViewFromParent(int i) throws ;

        View getChildAt(int i) throws ;

        int getChildCount() throws ;

        ViewHolder getChildViewHolder(View view) throws ;

        int indexOfChild(View view) throws ;

        void onEnteredHiddenState(View view) throws ;

        void onLeftHiddenState(View view) throws ;

        void removeAllViews() throws ;

        void removeViewAt(int i) throws ;
    }

    ChildHelper(Callback $r1) throws  {
        this.mCallback = $r1;
    }

    private void hideViewInternal(View $r1) throws  {
        this.mHiddenViews.add($r1);
        this.mCallback.onEnteredHiddenState($r1);
    }

    private boolean unhideViewInternal(View $r1) throws  {
        if (!this.mHiddenViews.remove($r1)) {
            return false;
        }
        this.mCallback.onLeftHiddenState($r1);
        return true;
    }

    void addView(View $r1, boolean $z0) throws  {
        addView($r1, -1, $z0);
    }

    void addView(View $r1, int $i0, boolean $z0) throws  {
        if ($i0 < 0) {
            $i0 = this.mCallback.getChildCount();
        } else {
            $i0 = getOffset($i0);
        }
        this.mBucket.insert($i0, $z0);
        if ($z0) {
            hideViewInternal($r1);
        }
        this.mCallback.addView($r1, $i0);
    }

    private int getOffset(int $i0) throws  {
        if ($i0 < 0) {
            return -1;
        }
        int $i3 = this.mCallback.getChildCount();
        int $i2 = $i0;
        while ($i2 < $i3) {
            int $i1 = $i0 - ($i2 - this.mBucket.countOnesBefore($i2));
            if ($i1 == 0) {
                while (this.mBucket.get($i2)) {
                    $i2++;
                }
                return $i2;
            }
            $i2 += $i1;
        }
        return -1;
    }

    void removeView(View $r1) throws  {
        int $i0 = this.mCallback.indexOfChild($r1);
        if ($i0 >= 0) {
            if (this.mBucket.remove($i0)) {
                unhideViewInternal($r1);
            }
            this.mCallback.removeViewAt($i0);
        }
    }

    void removeViewAt(int $i0) throws  {
        $i0 = getOffset($i0);
        View $r2 = this.mCallback.getChildAt($i0);
        if ($r2 != null) {
            if (this.mBucket.remove($i0)) {
                unhideViewInternal($r2);
            }
            this.mCallback.removeViewAt($i0);
        }
    }

    View getChildAt(int $i0) throws  {
        return this.mCallback.getChildAt(getOffset($i0));
    }

    void removeAllViewsUnfiltered() throws  {
        this.mBucket.reset();
        for (int $i0 = this.mHiddenViews.size() - 1; $i0 >= 0; $i0--) {
            this.mCallback.onLeftHiddenState((View) this.mHiddenViews.get($i0));
            this.mHiddenViews.remove($i0);
        }
        this.mCallback.removeAllViews();
    }

    View findHiddenNonRemovedView(int $i0, int $i1) throws  {
        int $i2 = this.mHiddenViews.size();
        for (int $i3 = 0; $i3 < $i2; $i3++) {
            View $r3 = (View) this.mHiddenViews.get($i3);
            ViewHolder $r5 = this.mCallback.getChildViewHolder($r3);
            if ($r5.getLayoutPosition() == $i0 && !$r5.isInvalid() && !$r5.isRemoved() && ($i1 == -1 || $r5.getItemViewType() == $i1)) {
                return $r3;
            }
        }
        return null;
    }

    void attachViewToParent(View $r1, int $i0, LayoutParams $r2, boolean $z0) throws  {
        if ($i0 < 0) {
            $i0 = this.mCallback.getChildCount();
        } else {
            $i0 = getOffset($i0);
        }
        this.mBucket.insert($i0, $z0);
        if ($z0) {
            hideViewInternal($r1);
        }
        this.mCallback.attachViewToParent($r1, $i0, $r2);
    }

    int getChildCount() throws  {
        return this.mCallback.getChildCount() - this.mHiddenViews.size();
    }

    int getUnfilteredChildCount() throws  {
        return this.mCallback.getChildCount();
    }

    View getUnfilteredChildAt(int $i0) throws  {
        return this.mCallback.getChildAt($i0);
    }

    void detachViewFromParent(int $i0) throws  {
        $i0 = getOffset($i0);
        this.mBucket.remove($i0);
        this.mCallback.detachViewFromParent($i0);
    }

    int indexOfChild(View $r1) throws  {
        int $i0 = this.mCallback.indexOfChild($r1);
        if ($i0 == -1) {
            return -1;
        }
        return !this.mBucket.get($i0) ? $i0 - this.mBucket.countOnesBefore($i0) : -1;
    }

    boolean isHidden(View $r1) throws  {
        return this.mHiddenViews.contains($r1);
    }

    void hide(View $r1) throws  {
        int $i0 = this.mCallback.indexOfChild($r1);
        if ($i0 < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + $r1);
        }
        this.mBucket.set($i0);
        hideViewInternal($r1);
    }

    void unhide(View $r1) throws  {
        int $i0 = this.mCallback.indexOfChild($r1);
        if ($i0 < 0) {
            throw new IllegalArgumentException("view is not a child, cannot hide " + $r1);
        } else if (this.mBucket.get($i0)) {
            this.mBucket.clear($i0);
            unhideViewInternal($r1);
        } else {
            throw new RuntimeException("trying to unhide a view that was not hidden" + $r1);
        }
    }

    public String toString() throws  {
        return this.mBucket.toString() + ", hidden list:" + this.mHiddenViews.size();
    }

    boolean removeViewIfHidden(View $r1) throws  {
        int $i0 = this.mCallback.indexOfChild($r1);
        if ($i0 == -1) {
            if (unhideViewInternal($r1)) {
                return true;
            }
            return true;
        } else if (!this.mBucket.get($i0)) {
            return false;
        } else {
            this.mBucket.remove($i0);
            if (unhideViewInternal($r1)) {
                this.mCallback.removeViewAt($i0);
            } else {
                this.mCallback.removeViewAt($i0);
            }
            return true;
        }
    }
}
