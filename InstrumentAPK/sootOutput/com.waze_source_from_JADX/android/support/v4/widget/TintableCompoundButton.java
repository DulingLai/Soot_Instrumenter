package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.Nullable;

public interface TintableCompoundButton {
    @Nullable
    ColorStateList getSupportButtonTintList() throws ;

    @Nullable
    Mode getSupportButtonTintMode() throws ;

    void setSupportButtonTintList(@Nullable ColorStateList colorStateList) throws ;

    void setSupportButtonTintMode(@Nullable Mode mode) throws ;
}
