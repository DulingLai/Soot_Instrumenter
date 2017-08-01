package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.TintableBackgroundView;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AppCompatImageView extends ImageView implements TintableBackgroundView {
    private AppCompatBackgroundHelper mBackgroundTintHelper;
    private AppCompatImageHelper mImageHelper;

    public AppCompatImageView(Context $r1) throws  {
        this($r1, null);
    }

    public AppCompatImageView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public AppCompatImageView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super(TintContextWrapper.wrap($r1), $r2, $i0);
        AppCompatDrawableManager $r3 = AppCompatDrawableManager.get();
        this.mBackgroundTintHelper = new AppCompatBackgroundHelper(this, $r3);
        this.mBackgroundTintHelper.loadFromAttributes($r2, $i0);
        this.mImageHelper = new AppCompatImageHelper(this, $r3);
        this.mImageHelper.loadFromAttributes($r2, $i0);
    }

    public void setImageResource(@DrawableRes int $i0) throws  {
        this.mImageHelper.setImageResource($i0);
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
    }
}