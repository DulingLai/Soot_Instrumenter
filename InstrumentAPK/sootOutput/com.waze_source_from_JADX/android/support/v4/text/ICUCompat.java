package android.support.v4.text;

import android.os.Build.VERSION;
import java.util.Locale;

public final class ICUCompat {
    private static final ICUCompatImpl IMPL;

    interface ICUCompatImpl {
        String maximizeAndGetScript(Locale locale) throws ;
    }

    static class ICUCompatImplBase implements ICUCompatImpl {
        public String maximizeAndGetScript(Locale locale) throws  {
            return null;
        }

        ICUCompatImplBase() throws  {
        }
    }

    static class ICUCompatImplIcs implements ICUCompatImpl {
        ICUCompatImplIcs() throws  {
        }

        public String maximizeAndGetScript(Locale $r1) throws  {
            return ICUCompatIcs.maximizeAndGetScript($r1);
        }
    }

    static class ICUCompatImplLollipop implements ICUCompatImpl {
        ICUCompatImplLollipop() throws  {
        }

        public String maximizeAndGetScript(Locale $r1) throws  {
            return ICUCompatApi23.maximizeAndGetScript($r1);
        }
    }

    static {
        int $i0 = VERSION.SDK_INT;
        if ($i0 >= 21) {
            IMPL = new ICUCompatImplLollipop();
        } else if ($i0 >= 14) {
            IMPL = new ICUCompatImplIcs();
        } else {
            IMPL = new ICUCompatImplBase();
        }
    }

    public static String maximizeAndGetScript(Locale $r0) throws  {
        return IMPL.maximizeAndGetScript($r0);
    }

    private ICUCompat() throws  {
    }
}
