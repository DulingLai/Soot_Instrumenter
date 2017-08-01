package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.widget.TextView;

public final class TextViewCompat {
    static final TextViewCompatImpl IMPL;

    interface TextViewCompatImpl {
        int getMaxLines(TextView textView) throws ;

        int getMinLines(TextView textView) throws ;

        void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) throws ;

        void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) throws ;

        void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) throws ;

        void setTextAppearance(@NonNull TextView textView, @StyleRes int i) throws ;
    }

    static class BaseTextViewCompatImpl implements TextViewCompatImpl {
        BaseTextViewCompatImpl() throws  {
        }

        public void setCompoundDrawablesRelative(@NonNull TextView $r1, @Nullable Drawable $r2, @Nullable Drawable $r3, @Nullable Drawable $r4, @Nullable Drawable $r5) throws  {
            $r1.setCompoundDrawables($r2, $r3, $r4, $r5);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView $r1, @Nullable Drawable $r2, @Nullable Drawable $r3, @Nullable Drawable $r4, @Nullable Drawable $r5) throws  {
            $r1.setCompoundDrawablesWithIntrinsicBounds($r2, $r3, $r4, $r5);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView $r1, @DrawableRes int $i0, @DrawableRes int $i1, @DrawableRes int $i2, @DrawableRes int $i3) throws  {
            $r1.setCompoundDrawablesWithIntrinsicBounds($i0, $i1, $i2, $i3);
        }

        public int getMaxLines(TextView $r1) throws  {
            return TextViewCompatDonut.getMaxLines($r1);
        }

        public int getMinLines(TextView $r1) throws  {
            return TextViewCompatDonut.getMinLines($r1);
        }

        public void setTextAppearance(TextView $r1, @StyleRes int $i0) throws  {
            TextViewCompatDonut.setTextAppearance($r1, $i0);
        }
    }

    static class JbTextViewCompatImpl extends BaseTextViewCompatImpl {
        JbTextViewCompatImpl() throws  {
        }

        public int getMaxLines(TextView $r1) throws  {
            return TextViewCompatJb.getMaxLines($r1);
        }

        public int getMinLines(TextView $r1) throws  {
            return TextViewCompatJb.getMinLines($r1);
        }
    }

    static class JbMr1TextViewCompatImpl extends JbTextViewCompatImpl {
        JbMr1TextViewCompatImpl() throws  {
        }

        public void setCompoundDrawablesRelative(@NonNull TextView $r1, @Nullable Drawable $r2, @Nullable Drawable $r3, @Nullable Drawable $r4, @Nullable Drawable $r5) throws  {
            TextViewCompatJbMr1.setCompoundDrawablesRelative($r1, $r2, $r3, $r4, $r5);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView $r1, @Nullable Drawable $r2, @Nullable Drawable $r3, @Nullable Drawable $r4, @Nullable Drawable $r5) throws  {
            TextViewCompatJbMr1.setCompoundDrawablesRelativeWithIntrinsicBounds($r1, $r2, $r3, $r4, $r5);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView $r1, @DrawableRes int $i0, @DrawableRes int $i1, @DrawableRes int $i2, @DrawableRes int $i3) throws  {
            TextViewCompatJbMr1.setCompoundDrawablesRelativeWithIntrinsicBounds($r1, $i0, $i1, $i2, $i3);
        }
    }

    static class JbMr2TextViewCompatImpl extends JbMr1TextViewCompatImpl {
        JbMr2TextViewCompatImpl() throws  {
        }

        public void setCompoundDrawablesRelative(@NonNull TextView $r1, @Nullable Drawable $r2, @Nullable Drawable $r3, @Nullable Drawable $r4, @Nullable Drawable $r5) throws  {
            TextViewCompatJbMr2.setCompoundDrawablesRelative($r1, $r2, $r3, $r4, $r5);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView $r1, @Nullable Drawable $r2, @Nullable Drawable $r3, @Nullable Drawable $r4, @Nullable Drawable $r5) throws  {
            TextViewCompatJbMr2.setCompoundDrawablesRelativeWithIntrinsicBounds($r1, $r2, $r3, $r4, $r5);
        }

        public void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView $r1, @DrawableRes int $i0, @DrawableRes int $i1, @DrawableRes int $i2, @DrawableRes int $i3) throws  {
            TextViewCompatJbMr2.setCompoundDrawablesRelativeWithIntrinsicBounds($r1, $i0, $i1, $i2, $i3);
        }
    }

    static class Api23TextViewCompatImpl extends JbMr2TextViewCompatImpl {
        Api23TextViewCompatImpl() throws  {
        }

        public void setTextAppearance(@NonNull TextView $r1, @StyleRes int $i0) throws  {
            TextViewCompatApi23.setTextAppearance($r1, $i0);
        }
    }

    private TextViewCompat() throws  {
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 23) {
            IMPL = new Api23TextViewCompatImpl();
        } else if ($i0 >= 18) {
            IMPL = new JbMr2TextViewCompatImpl();
        } else if ($i0 >= 17) {
            IMPL = new JbMr1TextViewCompatImpl();
        } else if ($i0 >= 16) {
            IMPL = new JbTextViewCompatImpl();
        } else {
            IMPL = new BaseTextViewCompatImpl();
        }
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView $r0, @Nullable Drawable $r1, @Nullable Drawable $r2, @Nullable Drawable $r3, @Nullable Drawable $r4) throws  {
        IMPL.setCompoundDrawablesRelative($r0, $r1, $r2, $r3, $r4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView $r0, @Nullable Drawable $r1, @Nullable Drawable $r2, @Nullable Drawable $r3, @Nullable Drawable $r4) throws  {
        IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds($r0, $r1, $r2, $r3, $r4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView $r0, @DrawableRes int $i0, @DrawableRes int $i1, @DrawableRes int $i2, @DrawableRes int $i3) throws  {
        IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds($r0, $i0, $i1, $i2, $i3);
    }

    public static int getMaxLines(@NonNull TextView $r0) throws  {
        return IMPL.getMaxLines($r0);
    }

    public static int getMinLines(@NonNull TextView $r0) throws  {
        return IMPL.getMinLines($r0);
    }

    public static void setTextAppearance(@NonNull TextView $r0, @StyleRes int $i0) throws  {
        IMPL.setTextAppearance($r0, $i0);
    }
}
