package android.support.v4.text;

import java.util.Locale;

public final class BidiFormatter {
    private static final int DEFAULT_FLAGS = 2;
    private static final BidiFormatter DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    private static final BidiFormatter DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    private static TextDirectionHeuristicCompat DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
    private static final int DIR_LTR = -1;
    private static final int DIR_RTL = 1;
    private static final int DIR_UNKNOWN = 0;
    private static final String EMPTY_STRING = "";
    private static final int FLAG_STEREO_RESET = 2;
    private static final char LRE = '‪';
    private static final char LRM = '‎';
    private static final String LRM_STRING = Character.toString(LRM);
    private static final char PDF = '‬';
    private static final char RLE = '‫';
    private static final char RLM = '‏';
    private static final String RLM_STRING = Character.toString(RLM);
    private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
    private final int mFlags;
    private final boolean mIsRtlContext;

    public static final class Builder {
        private int mFlags;
        private boolean mIsRtlContext;
        private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;

        public Builder() throws  {
            initialize(BidiFormatter.isRtlLocale(Locale.getDefault()));
        }

        public Builder(boolean $z0) throws  {
            initialize($z0);
        }

        public Builder(Locale $r1) throws  {
            initialize(BidiFormatter.isRtlLocale($r1));
        }

        private void initialize(boolean $z0) throws  {
            this.mIsRtlContext = $z0;
            this.mTextDirectionHeuristicCompat = BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }

        public Builder stereoReset(boolean $z0) throws  {
            if ($z0) {
                this.mFlags |= 2;
                return this;
            }
            this.mFlags &= -3;
            return this;
        }

        public Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat $r1) throws  {
            this.mTextDirectionHeuristicCompat = $r1;
            return this;
        }

        private static BidiFormatter getDefaultInstanceFromContext(boolean $z0) throws  {
            return $z0 ? BidiFormatter.DEFAULT_RTL_INSTANCE : BidiFormatter.DEFAULT_LTR_INSTANCE;
        }

        public BidiFormatter build() throws  {
            if (this.mFlags == 2 && this.mTextDirectionHeuristicCompat == BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC) {
                return getDefaultInstanceFromContext(this.mIsRtlContext);
            }
            return new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat);
        }
    }

    private static class DirectionalityEstimator {
        private static final byte[] DIR_TYPE_CACHE = new byte[1792];
        private static final int DIR_TYPE_CACHE_SIZE = 1792;
        private int charIndex;
        private final boolean isHtml;
        private char lastChar;
        private final int length;
        private final String text;

        static {
            for (int $i1 = 0; $i1 < 1792; $i1++) {
                DIR_TYPE_CACHE[$i1] = Character.getDirectionality($i1);
            }
        }

        DirectionalityEstimator(String $r1, boolean $z0) throws  {
            this.text = $r1;
            this.isHtml = $z0;
            this.length = $r1.length();
        }

        int getEntryDir() throws  {
            this.charIndex = 0;
            int $i1 = 0;
            byte $b2 = (byte) 0;
            int $i3 = 0;
            while (this.charIndex < this.length && $i3 == 0) {
                switch (dirTypeForward()) {
                    case (byte) 0:
                        if ($i1 == 0) {
                            return -1;
                        }
                        $i3 = $i1;
                        continue;
                    case (byte) 1:
                    case (byte) 2:
                        if ($i1 == 0) {
                            return 1;
                        }
                        $i3 = $i1;
                        continue;
                    case (byte) 3:
                    case (byte) 4:
                    case (byte) 5:
                    case (byte) 6:
                    case (byte) 7:
                    case (byte) 8:
                    case (byte) 10:
                    case (byte) 11:
                    case (byte) 12:
                    case (byte) 13:
                        break;
                    case (byte) 9:
                        break;
                    case (byte) 14:
                    case (byte) 15:
                        $i1++;
                        $b2 = (byte) -1;
                        continue;
                    case (byte) 16:
                    case (byte) 17:
                        $i1++;
                        $b2 = (byte) 1;
                        continue;
                    case (byte) 18:
                        $i1--;
                        $b2 = (byte) 0;
                        continue;
                    default:
                        break;
                }
                $i3 = $i1;
            }
            if ($i3 == 0) {
                return 0;
            }
            if ($b2 != (byte) 0) {
                return $b2;
            }
            while (this.charIndex > 0) {
                switch (dirTypeBackward()) {
                    case (byte) 14:
                    case (byte) 15:
                        if ($i3 != $i1) {
                            $i1--;
                            break;
                        }
                        return -1;
                    case (byte) 16:
                    case (byte) 17:
                        if ($i3 != $i1) {
                            $i1--;
                            break;
                        }
                        return 1;
                    case (byte) 18:
                        $i1++;
                        break;
                    default:
                        break;
                }
            }
            return 0;
        }

        int getExitDir() throws  {
            this.charIndex = this.length;
            int $i1 = 0;
            int $i2 = 0;
            while (this.charIndex > 0) {
                switch (dirTypeBackward()) {
                    case (byte) 0:
                        if ($i1 == 0) {
                            return -1;
                        }
                        if ($i2 == 0) {
                            $i2 = $i1;
                            break;
                        }
                        continue;
                    case (byte) 1:
                    case (byte) 2:
                        if ($i1 == 0) {
                            return 1;
                        }
                        if ($i2 == 0) {
                            $i2 = $i1;
                            break;
                        }
                        continue;
                    case (byte) 3:
                    case (byte) 4:
                    case (byte) 5:
                    case (byte) 6:
                    case (byte) 7:
                    case (byte) 8:
                    case (byte) 10:
                    case (byte) 11:
                    case (byte) 12:
                    case (byte) 13:
                        break;
                    case (byte) 9:
                        break;
                    case (byte) 14:
                    case (byte) 15:
                        if ($i2 == $i1) {
                            return -1;
                        }
                        $i1--;
                        continue;
                    case (byte) 16:
                    case (byte) 17:
                        if ($i2 == $i1) {
                            return 1;
                        }
                        $i1--;
                        continue;
                    case (byte) 18:
                        $i1++;
                        continue;
                    default:
                        break;
                }
                if ($i2 == 0) {
                    $i2 = $i1;
                }
            }
            return 0;
        }

        private static byte getCachedDirectionality(char $c0) throws  {
            return $c0 < '܀' ? DIR_TYPE_CACHE[$c0] : Character.getDirectionality($c0);
        }

        byte dirTypeForward() throws  {
            this.lastChar = this.text.charAt(this.charIndex);
            if (Character.isHighSurrogate(this.lastChar)) {
                int $i0 = Character.codePointAt(this.text, this.charIndex);
                this.charIndex += Character.charCount($i0);
                return Character.getDirectionality($i0);
            }
            this.charIndex++;
            byte $b4 = getCachedDirectionality(this.lastChar);
            if (!this.isHtml) {
                return $b4;
            }
            if (this.lastChar == '<') {
                return skipTagForward();
            }
            return this.lastChar == '&' ? skipEntityForward() : $b4;
        }

        byte dirTypeBackward() throws  {
            this.lastChar = this.text.charAt(this.charIndex - 1);
            if (Character.isLowSurrogate(this.lastChar)) {
                int $i0 = Character.codePointBefore(this.text, this.charIndex);
                this.charIndex -= Character.charCount($i0);
                return Character.getDirectionality($i0);
            }
            this.charIndex--;
            byte $b4 = getCachedDirectionality(this.lastChar);
            if (!this.isHtml) {
                return $b4;
            }
            if (this.lastChar == '>') {
                return skipTagBackward();
            }
            return this.lastChar == ';' ? skipEntityBackward() : $b4;
        }

        private byte skipTagForward() throws  {
            int $i0 = this.charIndex;
            while (this.charIndex < this.length) {
                String $r1 = this.text;
                int $i2 = this.charIndex;
                this.charIndex = $i2 + 1;
                this.lastChar = $r1.charAt($i2);
                if (this.lastChar == '>') {
                    return (byte) 12;
                }
                if (this.lastChar == '\"' || this.lastChar == '\'') {
                    char $c1 = this.lastChar;
                    while (this.charIndex < this.length) {
                        $r1 = this.text;
                        $i2 = this.charIndex;
                        this.charIndex = $i2 + 1;
                        char $c4 = $r1.charAt($i2);
                        this.lastChar = $c4;
                        if ($c4 == $c1) {
                            break;
                        }
                    }
                }
            }
            this.charIndex = $i0;
            this.lastChar = '<';
            return (byte) 13;
        }

        private byte skipTagBackward() throws  {
            int $i0 = this.charIndex;
            while (this.charIndex > 0) {
                String $r1 = this.text;
                int $i2 = this.charIndex - 1;
                this.charIndex = $i2;
                this.lastChar = $r1.charAt($i2);
                if (this.lastChar == '<') {
                    return (byte) 12;
                }
                if (this.lastChar == '>') {
                    break;
                } else if (this.lastChar == '\"' || this.lastChar == '\'') {
                    char $c1 = this.lastChar;
                    while (this.charIndex > 0) {
                        $r1 = this.text;
                        $i2 = this.charIndex - 1;
                        this.charIndex = $i2;
                        char $c3 = $r1.charAt($i2);
                        this.lastChar = $c3;
                        if ($c3 == $c1) {
                            break;
                        }
                    }
                }
            }
            this.charIndex = $i0;
            this.lastChar = '>';
            return (byte) 13;
        }

        private byte skipEntityForward() throws  {
            while (this.charIndex < this.length) {
                String $r1 = this.text;
                int $i2 = this.charIndex;
                this.charIndex = $i2 + 1;
                char $c1 = $r1.charAt($i2);
                this.lastChar = $c1;
                if ($c1 == ';') {
                    break;
                }
            }
            return (byte) 12;
        }

        private byte skipEntityBackward() throws  {
            int $i0 = this.charIndex;
            while (this.charIndex > 0) {
                String $r1 = this.text;
                int $i2 = this.charIndex - 1;
                this.charIndex = $i2;
                this.lastChar = $r1.charAt($i2);
                if (this.lastChar != '&') {
                    if (this.lastChar == ';') {
                        break;
                    }
                }
                return (byte) 12;
            }
            this.charIndex = $i0;
            this.lastChar = ';';
            return (byte) 13;
        }
    }

    public static BidiFormatter getInstance() throws  {
        return new Builder().build();
    }

    public static BidiFormatter getInstance(boolean $z0) throws  {
        return new Builder($z0).build();
    }

    public static BidiFormatter getInstance(Locale $r0) throws  {
        return new Builder($r0).build();
    }

    private BidiFormatter(boolean $z0, int $i0, TextDirectionHeuristicCompat $r1) throws  {
        this.mIsRtlContext = $z0;
        this.mFlags = $i0;
        this.mDefaultTextDirectionHeuristicCompat = $r1;
    }

    public boolean isRtlContext() throws  {
        return this.mIsRtlContext;
    }

    public boolean getStereoReset() throws  {
        return (this.mFlags & 2) != 0;
    }

    private String markAfter(String $r1, TextDirectionHeuristicCompat $r2) throws  {
        boolean $z0 = $r2.isRtl((CharSequence) $r1, 0, $r1.length());
        if (this.mIsRtlContext || (!$z0 && getExitDir($r1) != 1)) {
            return (!this.mIsRtlContext || ($z0 && getExitDir($r1) != -1)) ? "" : RLM_STRING;
        } else {
            return LRM_STRING;
        }
    }

    private String markBefore(String $r1, TextDirectionHeuristicCompat $r2) throws  {
        boolean $z0 = $r2.isRtl((CharSequence) $r1, 0, $r1.length());
        if (this.mIsRtlContext || (!$z0 && getEntryDir($r1) != 1)) {
            return (!this.mIsRtlContext || ($z0 && getEntryDir($r1) != -1)) ? "" : RLM_STRING;
        } else {
            return LRM_STRING;
        }
    }

    public boolean isRtl(String $r1) throws  {
        return this.mDefaultTextDirectionHeuristicCompat.isRtl((CharSequence) $r1, 0, $r1.length());
    }

    public String unicodeWrap(String $r1, TextDirectionHeuristicCompat $r2, boolean $z0) throws  {
        if ($r1 == null) {
            return null;
        }
        boolean $z1 = $r2.isRtl((CharSequence) $r1, 0, $r1.length());
        StringBuilder $r3 = new StringBuilder();
        if (getStereoReset() && $z0) {
            if ($z1) {
                $r2 = TextDirectionHeuristicsCompat.RTL;
            } else {
                $r2 = TextDirectionHeuristicsCompat.LTR;
            }
            $r3.append(markBefore($r1, $r2));
        }
        if ($z1 != this.mIsRtlContext) {
            char $c1;
            if ($z1) {
                $c1 = RLE;
            } else {
                $c1 = LRE;
            }
            $r3.append($c1);
            $r3.append($r1);
            $r3.append(PDF);
        } else {
            $r3.append($r1);
        }
        if ($z0) {
            if ($z1) {
                $r2 = TextDirectionHeuristicsCompat.RTL;
            } else {
                $r2 = TextDirectionHeuristicsCompat.LTR;
            }
            $r3.append(markAfter($r1, $r2));
        }
        return $r3.toString();
    }

    public String unicodeWrap(String $r1, TextDirectionHeuristicCompat $r2) throws  {
        return unicodeWrap($r1, $r2, true);
    }

    public String unicodeWrap(String $r1, boolean $z0) throws  {
        return unicodeWrap($r1, this.mDefaultTextDirectionHeuristicCompat, $z0);
    }

    public String unicodeWrap(String $r1) throws  {
        return unicodeWrap($r1, this.mDefaultTextDirectionHeuristicCompat, true);
    }

    private static boolean isRtlLocale(Locale $r0) throws  {
        return TextUtilsCompat.getLayoutDirectionFromLocale($r0) == 1;
    }

    private static int getExitDir(String $r0) throws  {
        return new DirectionalityEstimator($r0, false).getExitDir();
    }

    private static int getEntryDir(String $r0) throws  {
        return new DirectionalityEstimator($r0, false).getEntryDir();
    }
}
