package android.support.v7.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

public class AppCompatCheckedTextView extends CheckedTextView {
    private static final int[] TINT_ATTRS = new int[]{16843016};
    private AppCompatDrawableManager mDrawableManager;
    private AppCompatTextHelper mTextHelper;

    public AppCompatCheckedTextView(Context $r1) throws  {
        this($r1, null);
    }

    public AppCompatCheckedTextView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 16843720);
    }

    public AppCompatCheckedTextView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super(TintContextWrapper.wrap($r1), $r2, $i0);
        this.mTextHelper = AppCompatTextHelper.create(this);
        this.mTextHelper.loadFromAttributes($r2, $i0);
        this.mTextHelper.applyCompoundDrawablesTints();
        this.mDrawableManager = AppCompatDrawableManager.get();
        TintTypedArray $r6 = TintTypedArray.obtainStyledAttributes(getContext(), $r2, TINT_ATTRS, $i0, 0);
        setCheckMarkDrawable($r6.getDrawable(0));
        $r6.recycle();
    }

    public void setCheckMarkDrawable(@DrawableRes int $i0) throws  {
        if (this.mDrawableManager != null) {
            setCheckMarkDrawable(this.mDrawableManager.getDrawable(getContext(), $i0));
        } else {
            super.setCheckMarkDrawable($i0);
        }
    }

    public void setTextAppearance(Context $r1, int $i0) throws  {
        super.setTextAppearance($r1, $i0);
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetTextAppearance($r1, $i0);
        }
    }

    protected void drawableStateChanged() throws  {
        super.drawableStateChanged();
        if (this.mTextHelper != null) {
            this.mTextHelper.applyCompoundDrawablesTints();
        }
    }
}
