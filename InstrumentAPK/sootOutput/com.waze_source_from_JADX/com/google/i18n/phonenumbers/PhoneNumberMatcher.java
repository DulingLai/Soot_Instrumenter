package com.google.i18n.phonenumbers;

import com.google.i18n.phonenumbers.PhoneNumberUtil.Leniency;
import com.google.i18n.phonenumbers.PhoneNumberUtil.MatchType;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonemetadata.NumberFormat;
import com.google.i18n.phonenumbers.Phonemetadata.PhoneMetadata;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource;
import java.lang.Character.UnicodeBlock;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class PhoneNumberMatcher implements Iterator<PhoneNumberMatch> {
    private static final Pattern[] INNER_MATCHES = new Pattern[]{Pattern.compile("/+(.*)"), Pattern.compile("(\\([^(]*)"), Pattern.compile("(?:\\p{Z}-|-\\p{Z})\\p{Z}*(.+)"), Pattern.compile("[‒-―－]\\p{Z}*(.+)"), Pattern.compile("\\.+\\p{Z}*([^.]+)"), Pattern.compile("\\p{Z}+(\\P{Z}+)")};
    private static final Pattern LEAD_CLASS;
    private static final Pattern MATCHING_BRACKETS;
    private static final Pattern PATTERN;
    private static final Pattern PUB_PAGES = Pattern.compile("\\d{1,5}-+\\d{1,5}\\s{0,4}\\(\\d{1,4}");
    private static final Pattern SLASH_SEPARATED_DATES = Pattern.compile("(?:(?:[0-3]?\\d/[01]?\\d)|(?:[01]?\\d/[0-3]?\\d))/(?:[12]\\d)?\\d{2}");
    private static final Pattern TIME_STAMPS = Pattern.compile("[12]\\d{3}[-/]?[01]\\d[-/]?[0-3]\\d +[0-2]\\d$");
    private static final Pattern TIME_STAMPS_SUFFIX = Pattern.compile(":[0-5]\\d");
    private PhoneNumberMatch lastMatch = null;
    private final Leniency leniency;
    private long maxTries;
    private final PhoneNumberUtil phoneUtil;
    private final String preferredRegion;
    private int searchIndex = 0;
    private State state = State.NOT_READY;
    private final CharSequence text;

    interface NumberGroupingChecker {
        boolean checkGroups(PhoneNumberUtil phoneNumberUtil, PhoneNumber phoneNumber, StringBuilder stringBuilder, String[] strArr) throws ;
    }

    private enum State {
        NOT_READY,
        READY,
        DONE
    }

    private com.google.i18n.phonenumbers.PhoneNumberMatch extractInnerMatch(java.lang.String r21, int r22) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:11:0x0046 in {10, 12, 13, 16, 19, 20} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r20 = this;
        r2 = INNER_MATCHES;
        r3 = r2.length;
        r4 = 0;
    L_0x0004:
        if (r4 >= r3) goto L_0x008c;
    L_0x0006:
        r5 = r2[r4];
        r0 = r21;
        r6 = r5.matcher(r0);
        r7 = 1;
    L_0x000f:
        r8 = r6.find();
        if (r8 == 0) goto L_0x0089;
    L_0x0015:
        r0 = r20;
        r9 = r0.maxTries;
        r12 = 0;
        r11 = (r9 > r12 ? 1 : (r9 == r12 ? 0 : -1));
        if (r11 <= 0) goto L_0x0089;
    L_0x001f:
        if (r7 == 0) goto L_0x0056;
    L_0x0021:
        r5 = com.google.i18n.phonenumbers.PhoneNumberUtil.UNWANTED_END_CHAR_PATTERN;
        r14 = r6.start();
        r16 = 0;
        r0 = r21;
        r1 = r16;
        r15 = r0.substring(r1, r14);
        r17 = trimAfterFirstMatch(r5, r15);
        r0 = r17;
        r15 = r0.toString();
        r0 = r20;
        r1 = r22;
        r18 = r0.parseAndVerify(r15, r1);
        if (r18 == 0) goto L_0x004a;
    L_0x0045:
        return r18;
        goto L_0x004a;
    L_0x0047:
        goto L_0x0004;
    L_0x004a:
        r0 = r20;
        r9 = r0.maxTries;
        r12 = 1;
        r9 = r9 - r12;
        r0 = r20;
        r0.maxTries = r9;
        r7 = 0;
    L_0x0056:
        r5 = com.google.i18n.phonenumbers.PhoneNumberUtil.UNWANTED_END_CHAR_PATTERN;
        r16 = 1;
        r0 = r16;
        r15 = r6.group(r0);
        r17 = trimAfterFirstMatch(r5, r15);
        r0 = r17;
        r15 = r0.toString();
        r16 = 1;
        r0 = r16;
        r14 = r6.start(r0);
        r0 = r22;
        r14 = r14 + r0;
        r0 = r20;
        r18 = r0.parseAndVerify(r15, r14);
        if (r18 != 0) goto L_0x008f;
    L_0x007d:
        r0 = r20;
        r9 = r0.maxTries;
        r12 = 1;
        r9 = r9 - r12;
        r0 = r20;
        r0.maxTries = r9;
        goto L_0x000f;
    L_0x0089:
        r4 = r4 + 1;
        goto L_0x0047;
    L_0x008c:
        r19 = 0;
        return r19;
    L_0x008f:
        return r18;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.i18n.phonenumbers.PhoneNumberMatcher.extractInnerMatch(java.lang.String, int):com.google.i18n.phonenumbers.PhoneNumberMatch");
    }

    private com.google.i18n.phonenumbers.PhoneNumberMatch parseAndVerify(java.lang.String r21, int r22) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0050 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r20 = this;
        r3 = MATCHING_BRACKETS;
        r0 = r21;	 Catch:{ NumberParseException -> 0x00e5 }
        r4 = r3.matcher(r0);	 Catch:{ NumberParseException -> 0x00e5 }
        r5 = r4.matches();	 Catch:{ NumberParseException -> 0x00e5 }
        if (r5 == 0) goto L_0x00e8;
    L_0x000e:
        r3 = PUB_PAGES;	 Catch:{ NumberParseException -> 0x00e5 }
        r0 = r21;	 Catch:{ NumberParseException -> 0x00e5 }
        r4 = r3.matcher(r0);	 Catch:{ NumberParseException -> 0x00e5 }
        r5 = r4.find();	 Catch:{ NumberParseException -> 0x00e5 }
        if (r5 == 0) goto L_0x001e;
    L_0x001c:
        r6 = 0;
        return r6;
    L_0x001e:
        r0 = r20;
        r7 = r0.leniency;
        r8 = com.google.i18n.phonenumbers.PhoneNumberUtil.Leniency.VALID;	 Catch:{ NumberParseException -> 0x00e5 }
        r9 = r7.compareTo(r8);	 Catch:{ NumberParseException -> 0x00e5 }
        if (r9 < 0) goto L_0x0076;
    L_0x002a:
        if (r22 <= 0) goto L_0x0050;	 Catch:{ NumberParseException -> 0x00e5 }
    L_0x002c:
        r3 = LEAD_CLASS;	 Catch:{ NumberParseException -> 0x00e5 }
        r0 = r21;	 Catch:{ NumberParseException -> 0x00e5 }
        r4 = r3.matcher(r0);	 Catch:{ NumberParseException -> 0x00e5 }
        r5 = r4.lookingAt();	 Catch:{ NumberParseException -> 0x00e5 }
        if (r5 != 0) goto L_0x0050;	 Catch:{ NumberParseException -> 0x00e5 }
    L_0x003a:
        r0 = r20;
        r10 = r0.text;
        r9 = r22 + -1;	 Catch:{ NumberParseException -> 0x00e5 }
        r11 = r10.charAt(r9);	 Catch:{ NumberParseException -> 0x00e5 }
        r5 = isInvalidPunctuationSymbol(r11);	 Catch:{ NumberParseException -> 0x00e5 }
        if (r5 != 0) goto L_0x00ea;	 Catch:{ NumberParseException -> 0x00e5 }
    L_0x004a:
        r5 = isLatinLetter(r11);	 Catch:{ NumberParseException -> 0x00e5 }
        if (r5 != 0) goto L_0x00ec;	 Catch:{ NumberParseException -> 0x00e5 }
    L_0x0050:
        r0 = r21;	 Catch:{ NumberParseException -> 0x00e5 }
        r9 = r0.length();	 Catch:{ NumberParseException -> 0x00e5 }
        r9 = r22 + r9;
        r0 = r20;	 Catch:{ NumberParseException -> 0x00e5 }
        r10 = r0.text;	 Catch:{ NumberParseException -> 0x00e5 }
        r12 = r10.length();	 Catch:{ NumberParseException -> 0x00e5 }
        if (r9 >= r12) goto L_0x0076;
    L_0x0062:
        r0 = r20;	 Catch:{ NumberParseException -> 0x00e5 }
        r10 = r0.text;	 Catch:{ NumberParseException -> 0x00e5 }
        r11 = r10.charAt(r9);	 Catch:{ NumberParseException -> 0x00e5 }
        r5 = isInvalidPunctuationSymbol(r11);	 Catch:{ NumberParseException -> 0x00e5 }
        if (r5 != 0) goto L_0x00ee;	 Catch:{ NumberParseException -> 0x00e5 }
    L_0x0070:
        r5 = isLatinLetter(r11);	 Catch:{ NumberParseException -> 0x00e5 }
        if (r5 != 0) goto L_0x00f0;
    L_0x0076:
        r0 = r20;
        r13 = r0.phoneUtil;
        r0 = r20;	 Catch:{ NumberParseException -> 0x00e5 }
        r14 = r0.preferredRegion;	 Catch:{ NumberParseException -> 0x00e5 }
        r0 = r21;	 Catch:{ NumberParseException -> 0x00e5 }
        r15 = r13.parseAndKeepRawInput(r0, r14);	 Catch:{ NumberParseException -> 0x00e5 }
        r0 = r20;	 Catch:{ NumberParseException -> 0x00e5 }
        r13 = r0.phoneUtil;	 Catch:{ NumberParseException -> 0x00e5 }
        r9 = r15.getCountryCode();	 Catch:{ NumberParseException -> 0x00e5 }
        r14 = r13.getRegionCodeForCountryCode(r9);	 Catch:{ NumberParseException -> 0x00e5 }
        r16 = "IL";	 Catch:{ NumberParseException -> 0x00e5 }
        r0 = r16;	 Catch:{ NumberParseException -> 0x00e5 }
        r5 = r14.equals(r0);	 Catch:{ NumberParseException -> 0x00e5 }
        if (r5 == 0) goto L_0x00c0;
    L_0x009a:
        r0 = r20;	 Catch:{ NumberParseException -> 0x00e5 }
        r13 = r0.phoneUtil;	 Catch:{ NumberParseException -> 0x00e5 }
        r14 = r13.getNationalSignificantNumber(r15);	 Catch:{ NumberParseException -> 0x00e5 }
        r9 = r14.length();	 Catch:{ NumberParseException -> 0x00e5 }
        r17 = 4;
        r0 = r17;
        if (r9 != r0) goto L_0x00c0;
    L_0x00ac:
        if (r22 == 0) goto L_0x00f2;
    L_0x00ae:
        if (r22 <= 0) goto L_0x00c0;
    L_0x00b0:
        r0 = r20;
        r10 = r0.text;
        r9 = r22 + -1;	 Catch:{ NumberParseException -> 0x00e5 }
        r11 = r10.charAt(r9);	 Catch:{ NumberParseException -> 0x00e5 }
        r17 = 42;
        r0 = r17;
        if (r11 != r0) goto L_0x00f4;
    L_0x00c0:
        r0 = r20;
        r7 = r0.leniency;
        r0 = r20;	 Catch:{ NumberParseException -> 0x00e5 }
        r13 = r0.phoneUtil;	 Catch:{ NumberParseException -> 0x00e5 }
        r0 = r21;	 Catch:{ NumberParseException -> 0x00e5 }
        r5 = r7.verify(r15, r0, r13);	 Catch:{ NumberParseException -> 0x00e5 }
        if (r5 == 0) goto L_0x00f6;	 Catch:{ NumberParseException -> 0x00e5 }
    L_0x00d0:
        r15.clearCountryCodeSource();	 Catch:{ NumberParseException -> 0x00e5 }
        r15.clearRawInput();	 Catch:{ NumberParseException -> 0x00e5 }
        r15.clearPreferredDomesticCarrierCode();	 Catch:{ NumberParseException -> 0x00e5 }
        r18 = new com.google.i18n.phonenumbers.PhoneNumberMatch;	 Catch:{ NumberParseException -> 0x00e5 }
        r0 = r18;	 Catch:{ NumberParseException -> 0x00e5 }
        r1 = r22;	 Catch:{ NumberParseException -> 0x00e5 }
        r2 = r21;	 Catch:{ NumberParseException -> 0x00e5 }
        r0.<init>(r1, r2, r15);	 Catch:{ NumberParseException -> 0x00e5 }
        return r18;
    L_0x00e5:
        r19 = move-exception;
        r6 = 0;
        return r6;
    L_0x00e8:
        r6 = 0;
        return r6;
    L_0x00ea:
        r6 = 0;
        return r6;
    L_0x00ec:
        r6 = 0;
        return r6;
    L_0x00ee:
        r6 = 0;
        return r6;
    L_0x00f0:
        r6 = 0;
        return r6;
    L_0x00f2:
        r6 = 0;
        return r6;
    L_0x00f4:
        r6 = 0;
        return r6;
    L_0x00f6:
        r6 = 0;
        return r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.i18n.phonenumbers.PhoneNumberMatcher.parseAndVerify(java.lang.String, int):com.google.i18n.phonenumbers.PhoneNumberMatch");
    }

    static {
        String $r3 = "[^" + "(\\[（［" + ")\\]）］" + "]";
        MATCHING_BRACKETS = Pattern.compile("(?:[" + "(\\[（［" + "])?(?:" + $r3 + "+[" + ")\\]）］" + "])?" + $r3 + "+(?:[" + "(\\[（［" + "]" + $r3 + "+[" + ")\\]）］" + "])" + limit(0, 3) + $r3 + "*");
        $r3 = limit(0, 2);
        String $r5 = limit(0, 4);
        String $r4 = limit(0, 20);
        $r5 = "[-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～]" + $r5;
        String $r6 = "\\p{Nd}" + limit(1, 20);
        String $r7 = "[" + ("(\\[（［" + "+＋") + "]";
        LEAD_CLASS = Pattern.compile($r7);
        PATTERN = Pattern.compile("(?:" + $r7 + $r5 + ")" + $r3 + $r6 + "(?:" + $r5 + $r6 + ")" + $r4 + "(?:" + PhoneNumberUtil.EXTN_PATTERNS_FOR_MATCHING + ")?", 66);
    }

    private static String limit(int $i0, int $i1) throws  {
        if ($i0 >= 0 && $i1 > 0 && $i1 >= $i0) {
            return "{" + $i0 + "," + $i1 + "}";
        }
        throw new IllegalArgumentException();
    }

    PhoneNumberMatcher(PhoneNumberUtil $r1, CharSequence $r4, String $r2, Leniency $r3, long $l0) throws  {
        if ($r1 == null || $r3 == null) {
            throw new NullPointerException();
        } else if ($l0 < 0) {
            throw new IllegalArgumentException();
        } else {
            this.phoneUtil = $r1;
            if ($r4 == null) {
                $r4 = "";
            }
            this.text = $r4;
            this.preferredRegion = $r2;
            this.leniency = $r3;
            this.maxTries = $l0;
        }
    }

    private PhoneNumberMatch find(int $i0) throws  {
        Matcher $r3 = PATTERN.matcher(this.text);
        while (this.maxTries > 0 && $r3.find($i0)) {
            $i0 = $r3.start();
            CharSequence $r2 = trimAfterFirstMatch(PhoneNumberUtil.SECOND_NUMBER_START_PATTERN, this.text.subSequence($i0, $r3.end()));
            PhoneNumberMatch $r4 = extractMatch($r2, $i0);
            if ($r4 != null) {
                return $r4;
            }
            $i0 += $r2.length();
            this.maxTries--;
        }
        return null;
    }

    private static CharSequence trimAfterFirstMatch(Pattern $r0, CharSequence $r1) throws  {
        Matcher $r2 = $r0.matcher($r1);
        if ($r2.find()) {
            return $r1.subSequence(0, $r2.start());
        }
        return $r1;
    }

    static boolean isLatinLetter(char $c0) throws  {
        if (!Character.isLetter($c0) && Character.getType($c0) != 6) {
            return false;
        }
        UnicodeBlock $r0 = UnicodeBlock.of($c0);
        return $r0.equals(UnicodeBlock.BASIC_LATIN) || $r0.equals(UnicodeBlock.LATIN_1_SUPPLEMENT) || $r0.equals(UnicodeBlock.LATIN_EXTENDED_A) || $r0.equals(UnicodeBlock.LATIN_EXTENDED_ADDITIONAL) || $r0.equals(UnicodeBlock.LATIN_EXTENDED_B) || $r0.equals(UnicodeBlock.COMBINING_DIACRITICAL_MARKS);
    }

    private static boolean isInvalidPunctuationSymbol(char $c0) throws  {
        return $c0 == '%' || Character.getType($c0) == 26;
    }

    private PhoneNumberMatch extractMatch(CharSequence $r1, int $i0) throws  {
        if (SLASH_SEPARATED_DATES.matcher($r1).find()) {
            return null;
        }
        if (TIME_STAMPS.matcher($r1).find()) {
            if (TIME_STAMPS_SUFFIX.matcher(this.text.toString().substring($r1.length() + $i0)).lookingAt()) {
                return null;
            }
        }
        String $r5 = $r1.toString();
        PhoneNumberMatch $r6 = parseAndVerify($r5, $i0);
        return $r6 == null ? extractInnerMatch($r5, $i0) : $r6;
    }

    static boolean allNumberGroupsRemainGrouped(PhoneNumberUtil $r0, PhoneNumber $r1, StringBuilder $r2, String[] $r3) throws  {
        int $i0 = 0;
        if ($r1.getCountryCodeSource() != CountryCodeSource.FROM_DEFAULT_COUNTRY) {
            String $r6 = Integer.toString($r1.getCountryCode());
            $i0 = $r2.indexOf($r6) + $r6.length();
        }
        int $i1 = 0;
        while ($i1 < $r3.length) {
            $i0 = $r2.indexOf($r3[$i1], $i0);
            if ($i0 < 0) {
                return false;
            }
            $i0 += $r3[$i1].length();
            if ($i1 != 0 || $i0 >= $r2.length() || $r0.getNddPrefixForRegion($r0.getRegionCodeForCountryCode($r1.getCountryCode()), true) == null || !Character.isDigit($r2.charAt($i0))) {
                $i1++;
            } else {
                return $r2.substring($i0 - $r3[$i1].length()).startsWith($r0.getNationalSignificantNumber($r1));
            }
        }
        return $r2.substring($i0).contains($r1.getExtension());
    }

    static boolean allNumberGroupsAreExactlyPresent(PhoneNumberUtil $r0, PhoneNumber $r1, StringBuilder $r2, String[] $r3) throws  {
        int $i0;
        String[] $r6 = PhoneNumberUtil.NON_DIGITS_PATTERN.split($r2.toString());
        if ($r1.hasExtension()) {
            $i0 = $r6.length - 2;
        } else {
            $i0 = $r6.length - 1;
        }
        if ($r6.length == 1) {
            return true;
        }
        if ($r6[$i0].contains($r0.getNationalSignificantNumber($r1))) {
            return true;
        }
        int $i1 = $r3.length - 1;
        while ($i1 > 0 && $i0 >= 0) {
            if (!$r6[$i0].equals($r3[$i1])) {
                return false;
            }
            $i1--;
            $i0--;
        }
        return $i0 >= 0 && $r6[$i0].endsWith($r3[0]);
    }

    private static String[] getNationalNumberGroups(PhoneNumberUtil $r0, PhoneNumber $r1, NumberFormat $r2) throws  {
        if ($r2 != null) {
            return $r0.formatNsnUsingPattern($r0.getNationalSignificantNumber($r1), $r2, PhoneNumberFormat.RFC3966).split("-");
        }
        String $r4 = $r0.format($r1, PhoneNumberFormat.RFC3966);
        int $i0 = $r4.indexOf(59);
        int $i1 = $i0;
        if ($i0 < 0) {
            $i1 = $r4.length();
        }
        return $r4.substring($r4.indexOf(45) + 1, $i1).split("-");
    }

    static boolean checkNumberGroupingIsValid(PhoneNumber $r0, String $r1, PhoneNumberUtil $r2, NumberGroupingChecker $r3) throws  {
        StringBuilder $r4 = PhoneNumberUtil.normalizeDigits($r1, true);
        if ($r3.checkGroups($r2, $r0, $r4, getNationalNumberGroups($r2, $r0, null))) {
            return true;
        }
        PhoneMetadata $r6 = MetadataManager.getAlternateFormatsForCountry($r0.getCountryCode());
        if ($r6 != null) {
            for (NumberFormat nationalNumberGroups : $r6.numberFormats()) {
                if ($r3.checkGroups($r2, $r0, $r4, getNationalNumberGroups($r2, $r0, nationalNumberGroups))) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean containsMoreThanOneSlashInNationalNumber(PhoneNumber $r0, String $r1) throws  {
        int $i0 = $r1.indexOf(47);
        if ($i0 < 0) {
            return false;
        }
        int $i1 = $r1.indexOf(47, $i0 + 1);
        if ($i1 < 0) {
            return false;
        }
        boolean $z0 = $r0.getCountryCodeSource() == CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN || $r0.getCountryCodeSource() == CountryCodeSource.FROM_NUMBER_WITHOUT_PLUS_SIGN;
        if ($z0 && PhoneNumberUtil.normalizeDigitsOnly($r1.substring(0, $i0)).equals(Integer.toString($r0.getCountryCode()))) {
            return $r1.substring($i1 + 1).contains("/");
        }
        return true;
    }

    static boolean containsOnlyValidXChars(PhoneNumber $r0, String $r1, PhoneNumberUtil $r2) throws  {
        int $i0 = 0;
        while ($i0 < $r1.length() - 1) {
            char $c2 = $r1.charAt($i0);
            if ($c2 == 'x' || $c2 == 'X') {
                $c2 = $r1.charAt($i0 + 1);
                if ($c2 == 'x' || $c2 == 'X') {
                    $i0++;
                    if ($r2.isNumberMatch($r0, $r1.substring($i0)) != MatchType.NSN_MATCH) {
                        return false;
                    }
                } else if (!PhoneNumberUtil.normalizeDigitsOnly($r1.substring($i0)).equals($r0.getExtension())) {
                    return false;
                }
            }
            $i0++;
        }
        return true;
    }

    static boolean isNationalPrefixPresentIfRequired(PhoneNumber $r0, PhoneNumberUtil $r1) throws  {
        if ($r0.getCountryCodeSource() != CountryCodeSource.FROM_DEFAULT_COUNTRY) {
            return true;
        }
        PhoneMetadata $r6 = $r1.getMetadataForRegion($r1.getRegionCodeForCountryCode($r0.getCountryCode()));
        if ($r6 == null) {
            return true;
        }
        NumberFormat $r8 = $r1.chooseFormattingPatternForNumber($r6.numberFormats(), $r1.getNationalSignificantNumber($r0));
        if ($r8 == null) {
            return true;
        }
        if ($r8.getNationalPrefixFormattingRule().length() <= 0) {
            return true;
        }
        if ($r8.isNationalPrefixOptionalWhenFormatting()) {
            return true;
        }
        return !PhoneNumberUtil.formattingRuleHasFirstGroupOnly($r8.getNationalPrefixFormattingRule()) ? $r1.maybeStripNationalPrefixAndCarrierCode(new StringBuilder(PhoneNumberUtil.normalizeDigitsOnly($r0.getRawInput())), $r6, null) : true;
    }

    public boolean hasNext() throws  {
        if (this.state == State.NOT_READY) {
            this.lastMatch = find(this.searchIndex);
            if (this.lastMatch == null) {
                this.state = State.DONE;
            } else {
                this.searchIndex = this.lastMatch.end();
                this.state = State.READY;
            }
        }
        if (this.state == State.READY) {
            return true;
        }
        return false;
    }

    public PhoneNumberMatch next() throws  {
        if (hasNext()) {
            PhoneNumberMatch r3 = this.lastMatch;
            this.lastMatch = null;
            this.state = State.NOT_READY;
            return r3;
        }
        throw new NoSuchElementException();
    }

    public void remove() throws  {
        throw new UnsupportedOperationException();
    }
}
