package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

class AppCompatTextHelperV17 extends AppCompatTextHelper {
    private static final int[] VIEW_ATTRS_v17 = new int[]{16843666, 16843667};
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableStartTint;

    AppCompatTextHelperV17(TextView $r1) throws  {
        super($r1);
    }

    void loadFromAttributes(AttributeSet $r1, int $i0) throws  {
        super.loadFromAttributes($r1, $i0);
        Context $r3 = this.mView.getContext();
        AppCompatDrawableManager $r4 = AppCompatDrawableManager.get();
        TypedArray $r6 = $r3.obtainStyledAttributes($r1, VIEW_ATTRS_v17, $i0, 0);
        if ($r6.hasValue(0)) {
            this.mDrawableStartTint = AppCompatTextHelper.createTintInfo($r3, $r4, $r6.getResourceId(0, 0));
        }
        if ($r6.hasValue(1)) {
            this.mDrawableEndTint = AppCompatTextHelper.createTintInfo($r3, $r4, $r6.getResourceId(1, 0));
        }
        $r6.recycle();
    }

    void applyCompoundDrawablesTints() throws  {
        super.applyCompoundDrawablesTints();
        if (this.mDrawableStartTint != null || this.mDrawableEndTint != null) {
            Drawable[] $r1 = this.mView.getCompoundDrawablesRelative();
            applyCompoundDrawableTint($r1[0], this.mDrawableStartTint);
            applyCompoundDrawableTint($r1[2], this.mDrawableEndTint);
        }
    }
}
