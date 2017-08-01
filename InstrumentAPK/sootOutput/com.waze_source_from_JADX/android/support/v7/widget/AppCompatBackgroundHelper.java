package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.view.View;

class AppCompatBackgroundHelper {
    private TintInfo mBackgroundTint;
    private final AppCompatDrawableManager mDrawableManager;
    private TintInfo mInternalBackgroundTint;
    private TintInfo mTmpInfo;
    private final View mView;

    AppCompatBackgroundHelper(View $r1, AppCompatDrawableManager $r2) throws  {
        this.mView = $r1;
        this.mDrawableManager = $r2;
    }

    void loadFromAttributes(AttributeSet $r1, int $i0) throws  {
        TypedArray $r5 = this.mView.getContext().obtainStyledAttributes($r1, C0192R.styleable.ViewBackgroundHelper, $i0, 0);
        try {
            if ($r5.hasValue(C0192R.styleable.ViewBackgroundHelper_android_background)) {
                ColorStateList $r7 = this.mDrawableManager.getTintList(this.mView.getContext(), $r5.getResourceId(C0192R.styleable.ViewBackgroundHelper_android_background, -1));
                if ($r7 != null) {
                    setInternalBackgroundTint($r7);
                }
            }
            if ($r5.hasValue(C0192R.styleable.ViewBackgroundHelper_backgroundTint)) {
                ViewCompat.setBackgroundTintList(this.mView, $r5.getColorStateList(C0192R.styleable.ViewBackgroundHelper_backgroundTint));
            }
            if ($r5.hasValue(C0192R.styleable.ViewBackgroundHelper_backgroundTintMode)) {
                ViewCompat.setBackgroundTintMode(this.mView, DrawableUtils.parseTintMode($r5.getInt(C0192R.styleable.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
            $r5.recycle();
        } catch (Throwable th) {
            $r5.recycle();
        }
    }

    void onSetBackgroundResource(int $i0) throws  {
        setInternalBackgroundTint(this.mDrawableManager != null ? this.mDrawableManager.getTintList(this.mView.getContext(), $i0) : null);
    }

    void onSetBackgroundDrawable(Drawable background) throws  {
        setInternalBackgroundTint(null);
    }

    void setSupportBackgroundTintList(ColorStateList $r1) throws  {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintList = $r1;
        this.mBackgroundTint.mHasTintList = true;
        applySupportBackgroundTint();
    }

    ColorStateList getSupportBackgroundTintList() throws  {
        return this.mBackgroundTint != null ? this.mBackgroundTint.mTintList : null;
    }

    void setSupportBackgroundTintMode(Mode $r1) throws  {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        this.mBackgroundTint.mTintMode = $r1;
        this.mBackgroundTint.mHasTintMode = true;
        applySupportBackgroundTint();
    }

    Mode getSupportBackgroundTintMode() throws  {
        return this.mBackgroundTint != null ? this.mBackgroundTint.mTintMode : null;
    }

    void applySupportBackgroundTint() throws  {
        Drawable $r2 = this.mView.getBackground();
        if ($r2 == null) {
            return;
        }
        if (VERSION.SDK_INT != 21 || !applyFrameworkTintUsingColorFilter($r2)) {
            if (this.mBackgroundTint != null) {
                AppCompatDrawableManager.tintDrawable($r2, this.mBackgroundTint, this.mView.getDrawableState());
            } else if (this.mInternalBackgroundTint != null) {
                AppCompatDrawableManager.tintDrawable($r2, this.mInternalBackgroundTint, this.mView.getDrawableState());
            }
        }
    }

    void setInternalBackgroundTint(ColorStateList $r1) throws  {
        if ($r1 != null) {
            if (this.mInternalBackgroundTint == null) {
                this.mInternalBackgroundTint = new TintInfo();
            }
            this.mInternalBackgroundTint.mTintList = $r1;
            this.mInternalBackgroundTint.mHasTintList = true;
        } else {
            this.mInternalBackgroundTint = null;
        }
        applySupportBackgroundTint();
    }

    private boolean applyFrameworkTintUsingColorFilter(@NonNull Drawable $r1) throws  {
        if (this.mTmpInfo == null) {
            this.mTmpInfo = new TintInfo();
        }
        TintInfo $r2 = this.mTmpInfo;
        $r2.clear();
        ColorStateList $r4 = ViewCompat.getBackgroundTintList(this.mView);
        if ($r4 != null) {
            $r2.mHasTintList = true;
            $r2.mTintList = $r4;
        }
        Mode $r5 = ViewCompat.getBackgroundTintMode(this.mView);
        if ($r5 != null) {
            $r2.mHasTintMode = true;
            $r2.mTintMode = $r5;
        }
        if (!$r2.mHasTintList && !$r2.mHasTintMode) {
            return false;
        }
        AppCompatDrawableManager.tintDrawable($r1, $r2, this.mView.getDrawableState());
        return true;
    }
}
