package android.support.v4.text;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import java.util.Locale;

class TextUtilsCompatJellybeanMr1 {
    TextUtilsCompatJellybeanMr1() throws  {
    }

    @NonNull
    public static String htmlEncode(@NonNull String $r0) throws  {
        return TextUtils.htmlEncode($r0);
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale $r0) throws  {
        return TextUtils.getLayoutDirectionFromLocale($r0);
    }
}
