package android.support.v4.text;

import java.nio.CharBuffer;
import java.util.Locale;

public final class TextDirectionHeuristicsCompat {
    public static final TextDirectionHeuristicCompat ANYRTL_LTR = new TextDirectionHeuristicInternal(AnyStrong.INSTANCE_RTL, false);
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_LTR = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, false);
    public static final TextDirectionHeuristicCompat FIRSTSTRONG_RTL = new TextDirectionHeuristicInternal(FirstStrong.INSTANCE, true);
    public static final TextDirectionHeuristicCompat LOCALE = TextDirectionHeuristicLocale.INSTANCE;
    public static final TextDirectionHeuristicCompat LTR = new TextDirectionHeuristicInternal(null, false);
    public static final TextDirectionHeuristicCompat RTL = new TextDirectionHeuristicInternal(null, true);
    private static final int STATE_FALSE = 1;
    private static final int STATE_TRUE = 0;
    private static final int STATE_UNKNOWN = 2;

    private interface TextDirectionAlgorithm {
        int checkRtl(CharSequence charSequence, int i, int i2) throws ;
    }

    private static class AnyStrong implements TextDirectionAlgorithm {
        public static final AnyStrong INSTANCE_LTR = new AnyStrong(false);
        public static final AnyStrong INSTANCE_RTL = new AnyStrong(true);
        private final boolean mLookForRtl;

        public int checkRtl(CharSequence $r1, int $i0, int $i1) throws  {
            boolean $z0 = false;
            $i0 += $i1;
            for (int $i2 = $i0; $i2 < $i0; $i2++) {
                switch (TextDirectionHeuristicsCompat.isRtlText(Character.getDirectionality($r1.charAt($i2)))) {
                    case 0:
                        if (!this.mLookForRtl) {
                            $z0 = true;
                            break;
                        }
                        return 0;
                    case 1:
                        if (this.mLookForRtl) {
                            $z0 = true;
                            break;
                        }
                        return 1;
                    default:
                        break;
                }
            }
            if ($z0) {
                return !this.mLookForRtl ? 0 : 1;
            } else {
                return 2;
            }
        }

        private AnyStrong(boolean $z0) throws  {
            this.mLookForRtl = $z0;
        }
    }

    private static class FirstStrong implements TextDirectionAlgorithm {
        public static final FirstStrong INSTANCE = new FirstStrong();

        public int checkRtl(CharSequence $r1, int $i0, int $i1) throws  {
            int $i2 = 2;
            $i0 += $i1;
            for (int $i3 = $i0; $i3 < $i0 && $i2 == 2; $i3++) {
                $i2 = TextDirectionHeuristicsCompat.isRtlTextOrFormat(Character.getDirectionality($r1.charAt($i3)));
            }
            return $i2;
        }

        private FirstStrong() throws  {
        }
    }

    private static abstract class TextDirectionHeuristicImpl implements TextDirectionHeuristicCompat {
        private final TextDirectionAlgorithm mAlgorithm;

        protected abstract boolean defaultIsRtl() throws ;

        public TextDirectionHeuristicImpl(TextDirectionAlgorithm $r1) throws  {
            this.mAlgorithm = $r1;
        }

        public boolean isRtl(char[] $r1, int $i0, int $i1) throws  {
            return isRtl(CharBuffer.wrap($r1), $i0, $i1);
        }

        public boolean isRtl(CharSequence $r1, int $i0, int $i1) throws  {
            if ($r1 == null || $i0 < 0 || $i1 < 0 || $r1.length() - $i1 < $i0) {
                throw new IllegalArgumentException();
            } else if (this.mAlgorithm == null) {
                return defaultIsRtl();
            } else {
                return doCheck($r1, $i0, $i1);
            }
        }

        private boolean doCheck(CharSequence $r1, int $i0, int $i1) throws  {
            switch (this.mAlgorithm.checkRtl($r1, $i0, $i1)) {
                case 0:
                    return true;
                case 1:
                    return false;
                default:
                    return defaultIsRtl();
            }
        }
    }

    private static class TextDirectionHeuristicInternal extends TextDirectionHeuristicImpl {
        private final boolean mDefaultIsRtl;

        private TextDirectionHeuristicInternal(TextDirectionAlgorithm $r1, boolean $z0) throws  {
            super($r1);
            this.mDefaultIsRtl = $z0;
        }

        protected boolean defaultIsRtl() throws  {
            return this.mDefaultIsRtl;
        }
    }

    private static class TextDirectionHeuristicLocale extends TextDirectionHeuristicImpl {
        public static final TextDirectionHeuristicLocale INSTANCE = new TextDirectionHeuristicLocale();

        public TextDirectionHeuristicLocale() throws  {
            super(null);
        }

        protected boolean defaultIsRtl() throws  {
            return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        }
    }

    private static int isRtlText(int $i0) throws  {
        switch ($i0) {
            case 0:
                return 1;
            case 1:
            case 2:
                return 0;
            default:
                return 2;
        }
    }

    private static int isRtlTextOrFormat(int $i0) throws  {
        switch ($i0) {
            case 0:
            case 14:
            case 15:
                return 1;
            case 1:
            case 2:
            case 16:
            case 17:
                return 0;
            default:
                return 2;
        }
    }

    private TextDirectionHeuristicsCompat() throws  {
    }
}
