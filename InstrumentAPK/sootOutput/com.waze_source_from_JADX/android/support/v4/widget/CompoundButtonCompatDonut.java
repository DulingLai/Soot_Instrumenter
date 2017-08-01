package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.CompoundButton;
import java.lang.reflect.Field;

class CompoundButtonCompatDonut {
    private static final String TAG = "CompoundButtonCompatDonut";
    private static Field sButtonDrawableField;
    private static boolean sButtonDrawableFieldFetched;

    CompoundButtonCompatDonut() throws  {
    }

    static void setButtonTintList(CompoundButton $r1, ColorStateList $r0) throws  {
        if ($r1 instanceof TintableCompoundButton) {
            ((TintableCompoundButton) $r1).setSupportButtonTintList($r0);
        }
    }

    static ColorStateList getButtonTintList(CompoundButton $r1) throws  {
        if ($r1 instanceof TintableCompoundButton) {
            return ((TintableCompoundButton) $r1).getSupportButtonTintList();
        }
        return null;
    }

    static void setButtonTintMode(CompoundButton $r1, Mode $r0) throws  {
        if ($r1 instanceof TintableCompoundButton) {
            ((TintableCompoundButton) $r1).setSupportButtonTintMode($r0);
        }
    }

    static Mode getButtonTintMode(CompoundButton $r1) throws  {
        if ($r1 instanceof TintableCompoundButton) {
            return ((TintableCompoundButton) $r1).getSupportButtonTintMode();
        }
        return null;
    }

    static Drawable getButtonDrawable(CompoundButton $r0) throws  {
        if (!sButtonDrawableFieldFetched) {
            try {
                sButtonDrawableField = CompoundButton.class.getDeclaredField("mButtonDrawable");
                sButtonDrawableField.setAccessible(true);
            } catch (NoSuchFieldException $r5) {
                Log.i(TAG, "Failed to retrieve mButtonDrawable field", $r5);
            }
            sButtonDrawableFieldFetched = true;
        }
        if (sButtonDrawableField != null) {
            try {
                return (Drawable) sButtonDrawableField.get($r0);
            } catch (IllegalAccessException $r6) {
                Log.i(TAG, "Failed to get button drawable via reflection", $r6);
                sButtonDrawableField = null;
            }
        }
        return null;
    }
}
