package android.support.v4.text;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Locale;

public final class TextUtilsCompat {
    private static String ARAB_SCRIPT_SUBTAG = "Arab";
    private static String HEBR_SCRIPT_SUBTAG = "Hebr";
    private static final TextUtilsCompatImpl IMPL;
    public static final Locale ROOT = new Locale("", "");

    private static class TextUtilsCompatImpl {
        private TextUtilsCompatImpl() throws  {
        }

        @NonNull
        public String htmlEncode(@NonNull String $r1) throws  {
            StringBuilder $r2 = new StringBuilder();
            for (int $i0 = 0; $i0 < $r1.length(); $i0++) {
                char $c2 = $r1.charAt($i0);
                switch ($c2) {
                    case '\"':
                        $r2.append("&quot;");
                        break;
                    case '&':
                        $r2.append("&amp;");
                        break;
                    case '\'':
                        $r2.append("&#39;");
                        break;
                    case '<':
                        $r2.append("&lt;");
                        break;
                    case '>':
                        $r2.append("&gt;");
                        break;
                    default:
                        $r2.append($c2);
                        break;
                }
            }
            return $r2.toString();
        }

        public int getLayoutDirectionFromLocale(@Nullable Locale $r1) throws  {
            if (!($r1 == null || $r1.equals(TextUtilsCompat.ROOT))) {
                String $r3 = ICUCompat.maximizeAndGetScript($r1);
                if ($r3 == null) {
                    return getLayoutDirectionFromFirstChar($r1);
                }
                if ($r3.equalsIgnoreCase(TextUtilsCompat.ARAB_SCRIPT_SUBTAG) || $r3.equalsIgnoreCase(TextUtilsCompat.HEBR_SCRIPT_SUBTAG)) {
                    return 1;
                }
            }
            return 0;
        }

        private static int getLayoutDirectionFromFirstChar(@NonNull Locale $r0) throws  {
            switch (Character.getDirectionality($r0.getDisplayName($r0).charAt(0))) {
                case (byte) 1:
                case (byte) 2:
                    return 1;
                default:
                    return 0;
            }
        }
    }

    private static class TextUtilsCompatJellybeanMr1Impl extends TextUtilsCompatImpl {
        private TextUtilsCompatJellybeanMr1Impl() throws  {
            super();
        }

        @NonNull
        public String htmlEncode(@NonNull String $r1) throws  {
            return TextUtilsCompatJellybeanMr1.htmlEncode($r1);
        }

        public int getLayoutDirectionFromLocale(@Nullable Locale $r1) throws  {
            return TextUtilsCompatJellybeanMr1.getLayoutDirectionFromLocale($r1);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            IMPL = new TextUtilsCompatJellybeanMr1Impl();
        } else {
            IMPL = new TextUtilsCompatImpl();
        }
    }

    @NonNull
    public static String htmlEncode(@NonNull String $r0) throws  {
        return IMPL.htmlEncode($r0);
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale $r0) throws  {
        return IMPL.getLayoutDirectionFromLocale($r0);
    }

    private TextUtilsCompat() throws  {
    }
}
