package android.support.v4.text;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

class ICUCompatApi23 {
    private static final String TAG = "ICUCompatIcs";
    private static Method sAddLikelySubtagsMethod = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", new Class[]{Locale.class});

    ICUCompatApi23() throws  {
    }

    static {
        try {
        } catch (Exception $r0) {
            throw new IllegalStateException($r0);
        }
    }

    public static String maximizeAndGetScript(Locale $r0) throws  {
        try {
            return ((Locale) sAddLikelySubtagsMethod.invoke(null, new Object[]{$r0})).getScript();
        } catch (InvocationTargetException $r6) {
            Log.w(TAG, $r6);
            return $r0.getScript();
        } catch (IllegalAccessException $r7) {
            Log.w(TAG, $r7);
            return $r0.getScript();
        }
    }
}
