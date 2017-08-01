package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.Nullable;

public interface TintableBackgroundView {
    @Nullable
    ColorStateList getSupportBackgroundTintList() throws ;

    @Nullable
    Mode getSupportBackgroundTintMode() throws ;

    void setSupportBackgroundTintList(@Nullable ColorStateList colorStateList) throws ;

    void setSupportBackgroundTintMode(@Nullable Mode mode) throws ;
}
