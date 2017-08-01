package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import java.lang.ref.WeakReference;

public final class ViewStubCompat extends View {
    private OnInflateListener mInflateListener;
    private int mInflatedId;
    private WeakReference<View> mInflatedViewRef;
    private LayoutInflater mInflater;
    private int mLayoutResource;

    public interface OnInflateListener {
        void onInflate(ViewStubCompat viewStubCompat, View view) throws ;
    }

    public ViewStubCompat(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public ViewStubCompat(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mLayoutResource = 0;
        TypedArray $r4 = $r1.obtainStyledAttributes($r2, C0192R.styleable.ViewStubCompat, $i0, 0);
        this.mInflatedId = $r4.getResourceId(C0192R.styleable.ViewStubCompat_android_inflatedId, -1);
        this.mLayoutResource = $r4.getResourceId(C0192R.styleable.ViewStubCompat_android_layout, 0);
        setId($r4.getResourceId(C0192R.styleable.ViewStubCompat_android_id, -1));
        $r4.recycle();
        setVisibility(8);
        setWillNotDraw(true);
    }

    public int getInflatedId() throws  {
        return this.mInflatedId;
    }

    public void setInflatedId(int $i0) throws  {
        this.mInflatedId = $i0;
    }

    public int getLayoutResource() throws  {
        return this.mLayoutResource;
    }

    public void setLayoutResource(int $i0) throws  {
        this.mLayoutResource = $i0;
    }

    public void setLayoutInflater(LayoutInflater $r1) throws  {
        this.mInflater = $r1;
    }

    public LayoutInflater getLayoutInflater() throws  {
        return this.mInflater;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) throws  {
        setMeasuredDimension(0, 0);
    }

    public void draw(Canvas canvas) throws  {
    }

    protected void dispatchDraw(Canvas canvas) throws  {
    }

    public void setVisibility(int $i0) throws  {
        if (this.mInflatedViewRef != null) {
            View $r3 = (View) this.mInflatedViewRef.get();
            if ($r3 != null) {
                $r3.setVisibility($i0);
                return;
            }
            throw new IllegalStateException("setVisibility called on un-referenced view");
        }
        super.setVisibility($i0);
        if ($i0 == 0 || $i0 == 4) {
            inflate();
        }
    }

    public View inflate() throws  {
        ViewParent $r1 = getParent();
        if ($r1 == null || !($r1 instanceof ViewGroup)) {
            throw new IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");
        } else if (this.mLayoutResource != 0) {
            LayoutInflater $r3;
            ViewGroup $r2 = (ViewGroup) $r1;
            if (this.mInflater != null) {
                $r3 = this.mInflater;
            } else {
                $r3 = LayoutInflater.from(getContext());
            }
            View $r4 = $r3.inflate(this.mLayoutResource, $r2, false);
            if (this.mInflatedId != -1) {
                $r4.setId(this.mInflatedId);
            }
            int $i0 = $r2.indexOfChild(this);
            $r2.removeViewInLayout(this);
            LayoutParams $r5 = getLayoutParams();
            if ($r5 != null) {
                $r2.addView($r4, $i0, $r5);
            } else {
                $r2.addView($r4, $i0);
            }
            this.mInflatedViewRef = new WeakReference($r4);
            if (this.mInflateListener == null) {
                return $r4;
            }
            this.mInflateListener.onInflate(this, $r4);
            return $r4;
        } else {
            throw new IllegalArgumentException("ViewStub must have a valid layoutResource");
        }
    }

    public void setOnInflateListener(OnInflateListener $r1) throws  {
        this.mInflateListener = $r1;
    }
}
