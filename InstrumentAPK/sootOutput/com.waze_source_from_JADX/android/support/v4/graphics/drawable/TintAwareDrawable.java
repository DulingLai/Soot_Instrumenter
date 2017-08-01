package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.ColorInt;

public interface TintAwareDrawable {
    void setTint(@ColorInt int i) throws ;

    void setTintList(ColorStateList colorStateList) throws ;

    void setTintMode(Mode mode) throws ;
}
