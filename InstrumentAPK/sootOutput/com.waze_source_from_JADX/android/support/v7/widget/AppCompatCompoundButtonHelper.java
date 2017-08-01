package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.widget.CompoundButton;

class AppCompatCompoundButtonHelper {
    private ColorStateList mButtonTintList = null;
    private Mode mButtonTintMode = null;
    private final AppCompatDrawableManager mDrawableManager;
    private boolean mHasButtonTint = false;
    private boolean mHasButtonTintMode = false;
    private boolean mSkipNextApply;
    private final CompoundButton mView;

    interface DirectSetButtonDrawableInterface {
        void setButtonDrawable(Drawable drawable) throws ;
    }

    AppCompatCompoundButtonHelper(CompoundButton $r1, AppCompatDrawableManager $r2) throws  {
        this.mView = $r1;
        this.mDrawableManager = $r2;
    }

    void loadFromAttributes(AttributeSet $r1, int $i0) throws  {
        TypedArray $r5 = this.mView.getContext().obtainStyledAttributes($r1, C0192R.styleable.CompoundButton, $i0, 0);
        try {
            if ($r5.hasValue(C0192R.styleable.CompoundButton_android_button)) {
                $i0 = $r5.getResourceId(C0192R.styleable.CompoundButton_android_button, 0);
                if ($i0 != 0) {
                    this.mView.setButtonDrawable(this.mDrawableManager.getDrawable(this.mView.getContext(), $i0));
                }
            }
            if ($r5.hasValue(C0192R.styleable.CompoundButton_buttonTint)) {
                CompoundButtonCompat.setButtonTintList(this.mView, $r5.getColorStateList(C0192R.styleable.CompoundButton_buttonTint));
            }
            if ($r5.hasValue(C0192R.styleable.CompoundButton_buttonTintMode)) {
                CompoundButtonCompat.setButtonTintMode(this.mView, DrawableUtils.parseTintMode($r5.getInt(C0192R.styleable.CompoundButton_buttonTintMode, -1), null));
            }
            $r5.recycle();
        } catch (Throwable th) {
            $r5.recycle();
        }
    }

    void setSupportButtonTintList(ColorStateList $r1) throws  {
        this.mButtonTintList = $r1;
        this.mHasButtonTint = true;
        applyButtonTint();
    }

    ColorStateList getSupportButtonTintList() throws  {
        return this.mButtonTintList;
    }

    void setSupportButtonTintMode(@Nullable Mode $r1) throws  {
        this.mButtonTintMode = $r1;
        this.mHasButtonTintMode = true;
        applyButtonTint();
    }

    Mode getSupportButtonTintMode() throws  {
        return this.mButtonTintMode;
    }

    void onSetButtonDrawable() throws  {
        if (this.mSkipNextApply) {
            this.mSkipNextApply = false;
            return;
        }
        this.mSkipNextApply = true;
        applyButtonTint();
    }

    void applyButtonTint() throws  {
        Drawable $r2 = CompoundButtonCompat.getButtonDrawable(this.mView);
        if ($r2 == null) {
            return;
        }
        if (this.mHasButtonTint || this.mHasButtonTintMode) {
            $r2 = DrawableCompat.wrap($r2).mutate();
            if (this.mHasButtonTint) {
                DrawableCompat.setTintList($r2, this.mButtonTintList);
            }
            if (this.mHasButtonTintMode) {
                DrawableCompat.setTintMode($r2, this.mButtonTintMode);
            }
            if ($r2.isStateful()) {
                $r2.setState(this.mView.getDrawableState());
            }
            this.mView.setButtonDrawable($r2);
        }
    }

    int getCompoundPaddingLeft(int $i0) throws  {
        if (VERSION.SDK_INT >= 17) {
            return $i0;
        }
        Drawable $r2 = CompoundButtonCompat.getButtonDrawable(this.mView);
        if ($r2 != null) {
            return $i0 + $r2.getIntrinsicWidth();
        }
        return $i0;
    }
}
