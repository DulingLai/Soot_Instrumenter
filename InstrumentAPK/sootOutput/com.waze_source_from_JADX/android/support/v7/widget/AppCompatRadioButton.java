package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TintableCompoundButton;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class AppCompatRadioButton extends RadioButton implements TintableCompoundButton {
    private AppCompatCompoundButtonHelper mCompoundButtonHelper;
    private AppCompatDrawableManager mDrawableManager;

    public AppCompatRadioButton(Context $r1) throws  {
        this($r1, null);
    }

    public AppCompatRadioButton(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, C0192R.attr.radioButtonStyle);
    }

    public AppCompatRadioButton(Context $r1, AttributeSet $r2, int $i0) throws  {
        super(TintContextWrapper.wrap($r1), $r2, $i0);
        this.mDrawableManager = AppCompatDrawableManager.get();
        this.mCompoundButtonHelper = new AppCompatCompoundButtonHelper(this, this.mDrawableManager);
        this.mCompoundButtonHelper.loadFromAttributes($r2, $i0);
    }

    public void setButtonDrawable(Drawable $r1) throws  {
        super.setButtonDrawable($r1);
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.onSetButtonDrawable();
        }
    }

    public void setButtonDrawable(@DrawableRes int $i0) throws  {
        setButtonDrawable(this.mDrawableManager != null ? this.mDrawableManager.getDrawable(getContext(), $i0) : ContextCompat.getDrawable(getContext(), $i0));
    }

    public int getCompoundPaddingLeft() throws  {
        int $i0 = super.getCompoundPaddingLeft();
        return this.mCompoundButtonHelper != null ? this.mCompoundButtonHelper.getCompoundPaddingLeft($i0) : $i0;
    }

    public void setSupportButtonTintList(@Nullable ColorStateList $r1) throws  {
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.setSupportButtonTintList($r1);
        }
    }

    @Nullable
    public ColorStateList getSupportButtonTintList() throws  {
        return this.mCompoundButtonHelper != null ? this.mCompoundButtonHelper.getSupportButtonTintList() : null;
    }

    public void setSupportButtonTintMode(@Nullable Mode $r1) throws  {
        if (this.mCompoundButtonHelper != null) {
            this.mCompoundButtonHelper.setSupportButtonTintMode($r1);
        }
    }

    @Nullable
    public Mode getSupportButtonTintMode() throws  {
        return this.mCompoundButtonHelper != null ? this.mCompoundButtonHelper.getSupportButtonTintMode() : null;
    }
}
