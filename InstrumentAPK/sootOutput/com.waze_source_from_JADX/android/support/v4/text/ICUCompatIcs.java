package android.support.v4.text;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

class ICUCompatIcs {
    private static final String TAG = "ICUCompatIcs";
    private static Method sAddLikelySubtagsMethod;
    private static Method sGetScriptMethod;

    ICUCompatIcs() throws  {
    }

    static {
        try {
            Class $r1 = Class.forName("libcore.icu.ICU");
            if ($r1 != null) {
                sGetScriptMethod = $r1.getMethod("getScript", new Class[]{String.class});
                sAddLikelySubtagsMethod = $r1.getMethod("addLikelySubtags", new Class[]{String.class});
            }
        } catch (Exception $r0) {
            sGetScriptMethod = null;
            sAddLikelySubtagsMethod = null;
            Log.w(TAG, $r0);
        }
    }

    public static String maximizeAndGetScript(Locale $r0) throws  {
        String $r1 = addLikelySubtags($r0);
        if ($r1 != null) {
            return getScript($r1);
        }
        return null;
    }

    private static String getScript(String $r0) throws  {
        if (sGetScriptMethod != null) {
            try {
                return (String) sGetScriptMethod.invoke(null, new Object[]{$r0});
            } catch (IllegalAccessException $r4) {
                Log.w(TAG, $r4);
            } catch (InvocationTargetException $r5) {
                Log.w(TAG, $r5);
            }
        }
        return null;
    }

    private static String addLikelySubtags(Locale $r0) throws  {
        String $r2 = $r0.toString();
        if (sAddLikelySubtagsMethod != null) {
            try {
                return (String) sAddLikelySubtagsMethod.invoke(null, new Object[]{$r2});
            } catch (IllegalAccessException $r5) {
                Log.w(TAG, $r5);
            } catch (InvocationTargetException $r6) {
                Log.w(TAG, $r6);
            }
        }
        return $r2;
    }
}
