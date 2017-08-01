package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.TintableBackgroundView;
import android.util.AttributeSet;
import android.widget.TextView;

public class AppCompatTextView extends TextView implements TintableBackgroundView {
    private AppCompatBackgroundHelper mBackgroundTintHelper;
    private AppCompatDrawableManager mDrawableManager;
    private AppCompatTextHelper mTextHelper;

    public AppCompatTextView(Context $r1) throws  {
        this($r1, null);
    }

    public AppCompatTextView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 16842884);
    }

    public AppCompatTextView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super(TintContextWrapper.wrap($r1), $r2, $i0);
        this.mDrawableManager = AppCompatDrawableManager.get();
        this.mBackgroundTintHelper = new AppCompatBackgroundHelper(this, this.mDrawableManager);
        this.mBackgroundTintHelper.loadFromAttributes($r2, $i0);
        this.mTextHelper = AppCompatTextHelper.create(this);
        this.mTextHelper.loadFromAttributes($r2, $i0);
        this.mTextHelper.applyCompoundDrawablesTints();
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

    public void setTextAppearance(Context $r1, int $i0) throws  {
        super.setTextAppearance($r1, $i0);
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetTextAppearance($r1, $i0);
        }
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
}
