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
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;

public class AppCompatButton extends Button implements TintableBackgroundView {
    private final AppCompatBackgroundHelper mBackgroundTintHelper;
    private final AppCompatDrawableManager mDrawableManager;
    private final AppCompatTextHelper mTextHelper;

    public AppCompatButton(Context $r1) throws  {
        this($r1, null);
    }

    public AppCompatButton(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, C0192R.attr.buttonStyle);
    }

    public AppCompatButton(Context $r1, AttributeSet $r2, int $i0) throws  {
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

    public void onInitializeAccessibilityEvent(AccessibilityEvent $r1) throws  {
        super.onInitializeAccessibilityEvent($r1);
        $r1.setClassName(Button.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo $r1) throws  {
        super.onInitializeAccessibilityNodeInfo($r1);
        $r1.setClassName(Button.class.getName());
    }

    public void setSupportAllCaps(boolean $z0) throws  {
        if (this.mTextHelper != null) {
            this.mTextHelper.setAllCaps($z0);
        }
    }
}
