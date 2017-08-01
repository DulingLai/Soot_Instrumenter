package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.appcompat.C0192R;
import android.support.v7.text.AllCapsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

class AppCompatTextHelper {
    private static final int[] TEXT_APPEARANCE_ATTRS = new int[]{C0192R.attr.textAllCaps};
    private static final int[] VIEW_ATTRS = new int[]{16842804, 16843119, 16843117, 16843120, 16843118};
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableTopTint;
    final TextView mView;

    static AppCompatTextHelper create(TextView $r0) throws  {
        if (VERSION.SDK_INT >= 17) {
            return new AppCompatTextHelperV17($r0);
        }
        return new AppCompatTextHelper($r0);
    }

    AppCompatTextHelper(TextView $r1) throws  {
        this.mView = $r1;
    }

    void loadFromAttributes(AttributeSet $r1, int $i0) throws  {
        Context $r3 = this.mView.getContext();
        AppCompatDrawableManager $r4 = AppCompatDrawableManager.get();
        TypedArray $r6 = $r3.obtainStyledAttributes($r1, VIEW_ATTRS, $i0, 0);
        int $i1 = $r6.getResourceId(0, -1);
        if ($r6.hasValue(1)) {
            this.mDrawableLeftTint = createTintInfo($r3, $r4, $r6.getResourceId(1, 0));
        }
        if ($r6.hasValue(2)) {
            this.mDrawableTopTint = createTintInfo($r3, $r4, $r6.getResourceId(2, 0));
        }
        if ($r6.hasValue(3)) {
            this.mDrawableRightTint = createTintInfo($r3, $r4, $r6.getResourceId(3, 0));
        }
        if ($r6.hasValue(4)) {
            this.mDrawableBottomTint = createTintInfo($r3, $r4, $r6.getResourceId(4, 0));
        }
        $r6.recycle();
        if (!(this.mView.getTransformationMethod() instanceof PasswordTransformationMethod)) {
            boolean $z1 = false;
            boolean $z0 = false;
            if ($i1 != -1) {
                $r6 = $r3.obtainStyledAttributes($i1, C0192R.styleable.TextAppearance);
                if ($r6.hasValue(C0192R.styleable.TextAppearance_textAllCaps)) {
                    $z0 = true;
                    $z1 = $r6.getBoolean(C0192R.styleable.TextAppearance_textAllCaps, false);
                }
                $r6.recycle();
            }
            $r6 = $r3.obtainStyledAttributes($r1, TEXT_APPEARANCE_ATTRS, $i0, 0);
            if ($r6.hasValue(0)) {
                $z0 = true;
                $z1 = $r6.getBoolean(0, false);
            }
            $r6.recycle();
            if ($z0) {
                setAllCaps($z1);
            }
        }
    }

    void onSetTextAppearance(Context $r1, int $i0) throws  {
        TypedArray $r3 = $r1.obtainStyledAttributes($i0, TEXT_APPEARANCE_ATTRS);
        if ($r3.hasValue(0)) {
            setAllCaps($r3.getBoolean(0, false));
        }
        $r3.recycle();
    }

    void setAllCaps(boolean $z0) throws  {
        this.mView.setTransformationMethod($z0 ? new AllCapsTransformationMethod(this.mView.getContext()) : null);
    }

    void applyCompoundDrawablesTints() throws  {
        if (this.mDrawableLeftTint != null || this.mDrawableTopTint != null || this.mDrawableRightTint != null || this.mDrawableBottomTint != null) {
            Drawable[] $r1 = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint($r1[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint($r1[1], this.mDrawableTopTint);
            applyCompoundDrawableTint($r1[2], this.mDrawableRightTint);
            applyCompoundDrawableTint($r1[3], this.mDrawableBottomTint);
        }
    }

    final void applyCompoundDrawableTint(Drawable $r1, TintInfo $r2) throws  {
        if ($r1 != null && $r2 != null) {
            AppCompatDrawableManager.tintDrawable($r1, $r2, this.mView.getDrawableState());
        }
    }

    protected static TintInfo createTintInfo(Context $r0, AppCompatDrawableManager $r1, int $i0) throws  {
        ColorStateList $r2 = $r1.getTintList($r0, $i0);
        if ($r2 == null) {
            return null;
        }
        TintInfo $r3 = new TintInfo();
        $r3.mHasTintList = true;
        $r3.mTintList = $r2;
        return $r3;
    }
}
