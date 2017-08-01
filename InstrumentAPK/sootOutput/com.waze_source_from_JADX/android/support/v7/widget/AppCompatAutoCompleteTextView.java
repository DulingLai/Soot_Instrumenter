package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.TintableBackgroundView;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class AppCompatAutoCompleteTextView extends AutoCompleteTextView implements TintableBackgroundView {
    private static final int[] TINT_ATTRS = new int[]{16843126};
    private AppCompatBackgroundHelper mBackgroundTintHelper;
    private AppCompatDrawableManager mDrawableManager;
    private AppCompatTextHelper mTextHelper;

    public AppCompatAutoCompleteTextView(Context $r1) throws  {
        this($r1, null);
    }

    public AppCompatAutoCompleteTextView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, C0192R.attr.autoCompleteTextViewStyle);
    }

    public AppCompatAutoCompleteTextView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super(TintContextWrapper.wrap($r1), $r2, $i0);
        this.mDrawableManager = AppCompatDrawableManager.get();
        TintTypedArray $r5 = TintTypedArray.obtainStyledAttributes(getContext(), $r2, TINT_ATTRS, $i0, 0);
        if ($r5.hasValue(0)) {
            setDropDownBackgroundDrawable($r5.getDrawable(0));
        }
        $r5.recycle();
        this.mBackgroundTintHelper = new AppCompatBackgroundHelper(this, this.mDrawableManager);
        this.mBackgroundTintHelper.loadFromAttributes($r2, $i0);
        this.mTextHelper = AppCompatTextHelper.create(this);
        this.mTextHelper.loadFromAttributes($r2, $i0);
        this.mTextHelper.applyCompoundDrawablesTints();
    }

    public void setDropDownBackgroundResource(@DrawableRes int $i0) throws  {
        if (this.mDrawableManager != null) {
            setDropDownBackgroundDrawable(this.mDrawableManager.getDrawable(getContext(), $i0));
        } else {
            super.setDropDownBackgroundResource($i0);
        }
    }

    public void setBackgroundResource(@DrawableRes int $i0) throws  {
        super.setBackgroundResource($i0);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource($i0);
        }
    }

    public void setBackgroundDrawable(Drawable $r1) throws  {
        super.setBackgroundDrawable($r1);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable($r1);
        }
    }

    public void setSupportBackgroundTintList(@Nullable ColorStateList $r1) throws  {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList($r1);
        }
    }

    @Nullable
    public ColorStateList getSupportBackgroundTintList() throws  {
        return this.mBackgroundTintHelper != null ? this.mBackgroundTintHelper.getSupportBackgroundTintList() : null;
    }

    public void setSupportBackgroundTintMode(@Nullable Mode $r1) throws  {
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode($r1);
        }
    }

    @Nullable
    public Mode getSupportBackgroundTintMode() throws  {
        return this.mBackgroundTintHelper != null ? this.mBackgroundTintHelper.getSupportBackgroundTintMode() : null;
    }

    protected void drawableStateChanged() throws  {
        super.drawableStateChanged();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint();
        }
        if (this.mTextHelper != null) {
            this.mTextHelper.applyCompoundDrawablesTints();
        }
    }

    public void setTextAppearance(Context $r1, int $i0) throws  {
        super.setTextAppearance($r1, $i0);
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetTextAppearance($r1, $i0);
        }
    }
}
