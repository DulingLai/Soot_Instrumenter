package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.CompoundButton;

public final class CompoundButtonCompat {
    private static final CompoundButtonCompatImpl IMPL;

    interface CompoundButtonCompatImpl {
        Drawable getButtonDrawable(CompoundButton compoundButton) throws ;

        ColorStateList getButtonTintList(CompoundButton compoundButton) throws ;

        Mode getButtonTintMode(CompoundButton compoundButton) throws ;

        void setButtonTintList(CompoundButton compoundButton, ColorStateList colorStateList) throws ;

        void setButtonTintMode(CompoundButton compoundButton, Mode mode) throws ;
    }

    static class BaseCompoundButtonCompat implements CompoundButtonCompatImpl {
        BaseCompoundButtonCompat() throws  {
        }

        public void setButtonTintList(CompoundButton $r1, ColorStateList $r2) throws  {
            CompoundButtonCompatDonut.setButtonTintList($r1, $r2);
        }

        public ColorStateList getButtonTintList(CompoundButton $r1) throws  {
            return CompoundButtonCompatDonut.getButtonTintList($r1);
        }

        public void setButtonTintMode(CompoundButton $r1, Mode $r2) throws  {
            CompoundButtonCompatDonut.setButtonTintMode($r1, $r2);
        }

        public Mode getButtonTintMode(CompoundButton $r1) throws  {
            return CompoundButtonCompatDonut.getButtonTintMode($r1);
        }

        public Drawable getButtonDrawable(CompoundButton $r1) throws  {
            return CompoundButtonCompatDonut.getButtonDrawable($r1);
        }
    }

    static class LollipopCompoundButtonImpl extends BaseCompoundButtonCompat {
        LollipopCompoundButtonImpl() throws  {
        }

        public void setButtonTintList(CompoundButton $r1, ColorStateList $r2) throws  {
            CompoundButtonCompatLollipop.setButtonTintList($r1, $r2);
        }

        public ColorStateList getButtonTintList(CompoundButton $r1) throws  {
            return CompoundButtonCompatLollipop.getButtonTintList($r1);
        }

        public void setButtonTintMode(CompoundButton $r1, Mode $r2) throws  {
            CompoundButtonCompatLollipop.setButtonTintMode($r1, $r2);
        }

        public Mode getButtonTintMode(CompoundButton $r1) throws  {
            return CompoundButtonCompatLollipop.getButtonTintMode($r1);
        }
    }

    static class Api23CompoundButtonImpl extends LollipopCompoundButtonImpl {
        Api23CompoundButtonImpl() throws  {
        }

        public Drawable getButtonDrawable(CompoundButton $r1) throws  {
            return CompoundButtonCompatApi23.getButtonDrawable($r1);
        }
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 23) {
            IMPL = new Api23CompoundButtonImpl();
        } else if ($i0 >= 21) {
            IMPL = new LollipopCompoundButtonImpl();
        } else {
            IMPL = new BaseCompoundButtonCompat();
        }
    }

    private CompoundButtonCompat() throws  {
    }

    public static void setButtonTintList(@NonNull CompoundButton $r0, @Nullable ColorStateList $r1) throws  {
        IMPL.setButtonTintList($r0, $r1);
    }

    @Nullable
    public static ColorStateList getButtonTintList(@NonNull CompoundButton $r0) throws  {
        return IMPL.getButtonTintList($r0);
    }

    public static void setButtonTintMode(@NonNull CompoundButton $r0, @Nullable Mode $r1) throws  {
        IMPL.setButtonTintMode($r0, $r1);
    }

    @Nullable
    public static Mode getButtonTintMode(@NonNull CompoundButton $r0) throws  {
        return IMPL.getButtonTintMode($r0);
    }

    @Nullable
    public static Drawable getButtonDrawable(@NonNull CompoundButton $r0) throws  {
        return IMPL.getButtonDrawable($r0);
    }
}
