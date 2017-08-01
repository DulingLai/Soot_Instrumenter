package com.google.i18n.phonenumbers;

import com.abaltatech.mcp.mcs.utils.FilenameUtils;
import com.facebook.appevents.AppEventsConstants;
import com.google.i18n.phonenumbers.NumberParseException.ErrorType;
import com.google.i18n.phonenumbers.Phonemetadata.NumberFormat;
import com.google.i18n.phonenumbers.Phonemetadata.NumberFormat.Builder;
import com.google.i18n.phonenumbers.Phonemetadata.PhoneMetadata;
import com.google.i18n.phonenumbers.Phonemetadata.PhoneNumberDesc;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberUtil {
    private static final Map<Character, Character> ALL_PLUS_NUMBER_GROUPING_SYMBOLS;
    private static final Map<Character, Character> ALPHA_MAPPINGS;
    private static final Map<Character, Character> ALPHA_PHONE_MAPPINGS;
    private static final Pattern CAPTURING_DIGIT_PATTERN = Pattern.compile("(\\p{Nd})");
    private static final String CAPTURING_EXTN_DIGITS = "(\\p{Nd}{1,7})";
    private static final Pattern CC_PATTERN = Pattern.compile("\\$CC");
    private static final String COLOMBIA_MOBILE_TO_FIXED_LINE_PREFIX = "3";
    private static final String DEFAULT_EXTN_PREFIX = " ext. ";
    private static final Map<Character, Character> DIALLABLE_CHAR_MAPPINGS;
    private static final String DIGITS = "\\p{Nd}";
    private static final Pattern EXTN_PATTERN = Pattern.compile("(?:" + EXTN_PATTERNS_FOR_PARSING + ")$", 66);
    static final String EXTN_PATTERNS_FOR_MATCHING = createExtnPattern("xｘ#＃~～");
    private static final String EXTN_PATTERNS_FOR_PARSING = createExtnPattern(",;" + "xｘ#＃~～");
    private static final Pattern FG_PATTERN = Pattern.compile("\\$FG");
    private static final Pattern FIRST_GROUP_ONLY_PREFIX_PATTERN = Pattern.compile("\\(?\\$1\\)?");
    private static final Pattern FIRST_GROUP_PATTERN = Pattern.compile("(\\$\\d)");
    private static final Set<Integer> GEO_MOBILE_COUNTRIES;
    private static final Set<Integer> GEO_MOBILE_COUNTRIES_WITHOUT_MOBILE_AREA_CODES;
    private static final int MAX_INPUT_STRING_LENGTH = 250;
    static final int MAX_LENGTH_COUNTRY_CODE = 3;
    static final int MAX_LENGTH_FOR_NSN = 17;
    private static final int MIN_LENGTH_FOR_NSN = 2;
    private static final Map<Integer, String> MOBILE_TOKEN_MAPPINGS;
    private static final int NANPA_COUNTRY_CODE = 1;
    static final Pattern NON_DIGITS_PATTERN = Pattern.compile("(\\D+)");
    private static final Pattern NP_PATTERN = Pattern.compile("\\$NP");
    static final String PLUS_CHARS = "+＋";
    static final Pattern PLUS_CHARS_PATTERN = Pattern.compile("[+＋]+");
    static final char PLUS_SIGN = '+';
    static final int REGEX_FLAGS = 66;
    public static final String REGION_CODE_FOR_NON_GEO_ENTITY = "001";
    private static final String RFC3966_EXTN_PREFIX = ";ext=";
    private static final String RFC3966_ISDN_SUBADDRESS = ";isub=";
    private static final String RFC3966_PHONE_CONTEXT = ";phone-context=";
    private static final String RFC3966_PREFIX = "tel:";
    private static final String SECOND_NUMBER_START = "[\\\\/] *x";
    static final Pattern SECOND_NUMBER_START_PATTERN = Pattern.compile(SECOND_NUMBER_START);
    private static final Pattern SEPARATOR_PATTERN = Pattern.compile("[-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～]+");
    private static final char STAR_SIGN = '*';
    private static final Pattern UNIQUE_INTERNATIONAL_PREFIX = Pattern.compile("[\\d]+(?:[~⁓∼～][\\d]+)?");
    private static final String UNKNOWN_REGION = "ZZ";
    private static final String UNWANTED_END_CHARS = "[[\\P{N}&&\\P{L}]&&[^#]]+$";
    static final Pattern UNWANTED_END_CHAR_PATTERN = Pattern.compile(UNWANTED_END_CHARS);
    private static final String VALID_ALPHA = (Arrays.toString(ALPHA_MAPPINGS.keySet().toArray()).replaceAll("[, \\[\\]]", "") + Arrays.toString(ALPHA_MAPPINGS.keySet().toArray()).toLowerCase().replaceAll("[, \\[\\]]", ""));
    private static final Pattern VALID_ALPHA_PHONE_PATTERN = Pattern.compile("(?:.*?[A-Za-z]){3}.*");
    private static final String VALID_PHONE_NUMBER = ("\\p{Nd}{2}|[+＋]*+(?:[-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～*]*\\p{Nd}){3,}[-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～*" + VALID_ALPHA + DIGITS + "]*");
    private static final Pattern VALID_PHONE_NUMBER_PATTERN = Pattern.compile(VALID_PHONE_NUMBER + "(?:" + EXTN_PATTERNS_FOR_PARSING + ")?", 66);
    static final String VALID_PUNCTUATION = "-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～";
    private static final String VALID_START_CHAR = "[+＋\\p{Nd}]";
    private static final Pattern VALID_START_CHAR_PATTERN = Pattern.compile(VALID_START_CHAR);
    private static PhoneNumberUtil instance = null;
    private static final Logger logger = Logger.getLogger(PhoneNumberUtil.class.getName());
    private final Map<Integer, List<String>> countryCallingCodeToRegionCodeMap;
    private final Set<Integer> countryCodesForNonGeographicalRegion = new HashSet();
    private final MetadataSource metadataSource;
    private final Set<String> nanpaRegions = new HashSet(35);
    private final RegexCache regexCache = new RegexCache(100);
    private final Set<String> supportedRegions = new HashSet(320);

    static /* synthetic */ class C10612 {
        static final /* synthetic */ int[] f40xd187ceb9;
        static final /* synthetic */ int[] f41xa7892e7c;
        static final /* synthetic */ int[] f42x9de98e3a;

        static {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:58:0x00dc
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.values();
            r1 = r0.length;
            r2 = new int[r1];
            f41xa7892e7c = r2;
            r2 = f41xa7892e7c;	 Catch:{ NoSuchFieldError -> 0x0141 }
            r3 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.PREMIUM_RATE;	 Catch:{ NoSuchFieldError -> 0x0141 }
            r1 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x0141 }
            r4 = 1;
            r2[r1] = r4;
        L_0x0014:
            r2 = f41xa7892e7c;	 Catch:{ NoSuchFieldError -> 0x013b }
            r3 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.TOLL_FREE;	 Catch:{ NoSuchFieldError -> 0x013b }
            r1 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x013b }
            r4 = 2;
            r2[r1] = r4;
        L_0x001f:
            r2 = f41xa7892e7c;	 Catch:{ NoSuchFieldError -> 0x0135 }
            r3 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.MOBILE;	 Catch:{ NoSuchFieldError -> 0x0135 }
            r1 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x0135 }
            r4 = 3;
            r2[r1] = r4;
        L_0x002a:
            r2 = f41xa7892e7c;	 Catch:{ NoSuchFieldError -> 0x012f }
            r3 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.FIXED_LINE;	 Catch:{ NoSuchFieldError -> 0x012f }
            r1 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x012f }
            r4 = 4;
            r2[r1] = r4;
        L_0x0035:
            r2 = f41xa7892e7c;	 Catch:{ NoSuchFieldError -> 0x0129 }
            r3 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE;	 Catch:{ NoSuchFieldError -> 0x0129 }
            r1 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x0129 }
            r4 = 5;
            r2[r1] = r4;
        L_0x0040:
            r2 = f41xa7892e7c;	 Catch:{ NoSuchFieldError -> 0x0123 }
            r3 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.SHARED_COST;	 Catch:{ NoSuchFieldError -> 0x0123 }
            r1 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x0123 }
            r4 = 6;
            r2[r1] = r4;
        L_0x004b:
            r2 = f41xa7892e7c;	 Catch:{ NoSuchFieldError -> 0x0121 }
            r3 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.VOIP;	 Catch:{ NoSuchFieldError -> 0x0121 }
            r1 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x0121 }
            r4 = 7;
            r2[r1] = r4;
        L_0x0056:
            r2 = f41xa7892e7c;	 Catch:{ NoSuchFieldError -> 0x011f }
            r3 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.PERSONAL_NUMBER;	 Catch:{ NoSuchFieldError -> 0x011f }
            r1 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x011f }
            r4 = 8;
            r2[r1] = r4;
        L_0x0062:
            r2 = f41xa7892e7c;	 Catch:{ NoSuchFieldError -> 0x011d }
            r3 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.PAGER;	 Catch:{ NoSuchFieldError -> 0x011d }
            r1 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x011d }
            r4 = 9;
            r2[r1] = r4;
        L_0x006e:
            r2 = f41xa7892e7c;	 Catch:{ NoSuchFieldError -> 0x011b }
            r3 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.UAN;	 Catch:{ NoSuchFieldError -> 0x011b }
            r1 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x011b }
            r4 = 10;
            r2[r1] = r4;
        L_0x007a:
            r2 = f41xa7892e7c;	 Catch:{ NoSuchFieldError -> 0x0119 }
            r3 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.VOICEMAIL;	 Catch:{ NoSuchFieldError -> 0x0119 }
            r1 = r3.ordinal();	 Catch:{ NoSuchFieldError -> 0x0119 }
            r4 = 11;
            r2[r1] = r4;
        L_0x0086:
            r5 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.values();
            r1 = r5.length;
            r2 = new int[r1];
            f40xd187ceb9 = r2;
            r2 = f40xd187ceb9;	 Catch:{ NoSuchFieldError -> 0x0117 }
            r6 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164;	 Catch:{ NoSuchFieldError -> 0x0117 }
            r1 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x0117 }
            r4 = 1;
            r2[r1] = r4;
        L_0x009a:
            r2 = f40xd187ceb9;	 Catch:{ NoSuchFieldError -> 0x0115 }
            r6 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL;	 Catch:{ NoSuchFieldError -> 0x0115 }
            r1 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x0115 }
            r4 = 2;
            r2[r1] = r4;
        L_0x00a5:
            r2 = f40xd187ceb9;	 Catch:{ NoSuchFieldError -> 0x0113 }
            r6 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.RFC3966;	 Catch:{ NoSuchFieldError -> 0x0113 }
            r1 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x0113 }
            r4 = 3;
            r2[r1] = r4;
        L_0x00b0:
            r2 = f40xd187ceb9;	 Catch:{ NoSuchFieldError -> 0x0111 }
            r6 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL;	 Catch:{ NoSuchFieldError -> 0x0111 }
            r1 = r6.ordinal();	 Catch:{ NoSuchFieldError -> 0x0111 }
            r4 = 4;
            r2[r1] = r4;
        L_0x00bb:
            r7 = com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource.values();
            r1 = r7.length;
            r2 = new int[r1];
            goto L_0x00c6;
        L_0x00c3:
            goto L_0x004b;
        L_0x00c6:
            f42x9de98e3a = r2;
            goto L_0x00cc;
        L_0x00c9:
            goto L_0x0056;
        L_0x00cc:
            r2 = f42x9de98e3a;	 Catch:{ NoSuchFieldError -> 0x010f }
            goto L_0x00d2;	 Catch:{ NoSuchFieldError -> 0x010f }
        L_0x00cf:
            goto L_0x0062;	 Catch:{ NoSuchFieldError -> 0x010f }
        L_0x00d2:
            r8 = com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN;	 Catch:{ NoSuchFieldError -> 0x010f }
            goto L_0x00d8;	 Catch:{ NoSuchFieldError -> 0x010f }
        L_0x00d5:
            goto L_0x006e;	 Catch:{ NoSuchFieldError -> 0x010f }
        L_0x00d8:
            goto L_0x00e0;	 Catch:{ NoSuchFieldError -> 0x010f }
        L_0x00d9:
            goto L_0x007a;	 Catch:{ NoSuchFieldError -> 0x010f }
            goto L_0x00e0;	 Catch:{ NoSuchFieldError -> 0x010f }
        L_0x00dd:
            goto L_0x0086;	 Catch:{ NoSuchFieldError -> 0x010f }
        L_0x00e0:
            r1 = r8.ordinal();	 Catch:{ NoSuchFieldError -> 0x010f }
            r4 = 1;
            r2[r1] = r4;
        L_0x00e7:
            r2 = f42x9de98e3a;	 Catch:{ NoSuchFieldError -> 0x010d }
            r8 = com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_IDD;	 Catch:{ NoSuchFieldError -> 0x010d }
            r1 = r8.ordinal();	 Catch:{ NoSuchFieldError -> 0x010d }
            r4 = 2;
            r2[r1] = r4;
        L_0x00f2:
            r2 = f42x9de98e3a;	 Catch:{ NoSuchFieldError -> 0x010b }
            r8 = com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITHOUT_PLUS_SIGN;	 Catch:{ NoSuchFieldError -> 0x010b }
            r1 = r8.ordinal();	 Catch:{ NoSuchFieldError -> 0x010b }
            r4 = 3;
            r2[r1] = r4;
        L_0x00fd:
            r2 = f42x9de98e3a;	 Catch:{ NoSuchFieldError -> 0x0109 }
            r8 = com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource.FROM_DEFAULT_COUNTRY;	 Catch:{ NoSuchFieldError -> 0x0109 }
            r1 = r8.ordinal();	 Catch:{ NoSuchFieldError -> 0x0109 }
            r4 = 4;
            r2[r1] = r4;
            return;
        L_0x0109:
            r9 = move-exception;
            return;
        L_0x010b:
            r10 = move-exception;
            goto L_0x00fd;
        L_0x010d:
            r11 = move-exception;
            goto L_0x00f2;
        L_0x010f:
            r12 = move-exception;
            goto L_0x00e7;
        L_0x0111:
            r13 = move-exception;
            goto L_0x00bb;
        L_0x0113:
            r14 = move-exception;
            goto L_0x00b0;
        L_0x0115:
            r15 = move-exception;
            goto L_0x00a5;
        L_0x0117:
            r16 = move-exception;
            goto L_0x009a;
        L_0x0119:
            r17 = move-exception;
            goto L_0x00dd;
        L_0x011b:
            r18 = move-exception;
            goto L_0x00d9;
        L_0x011d:
            r19 = move-exception;
            goto L_0x00d5;
        L_0x011f:
            r20 = move-exception;
            goto L_0x00cf;
        L_0x0121:
            r21 = move-exception;
            goto L_0x00c9;
        L_0x0123:
            r22 = move-exception;
            goto L_0x00c3;
            goto L_0x0129;
        L_0x0126:
            goto L_0x0040;
        L_0x0129:
            r23 = move-exception;
            goto L_0x0126;
            goto L_0x012f;
        L_0x012c:
            goto L_0x0035;
        L_0x012f:
            r24 = move-exception;
            goto L_0x012c;
            goto L_0x0135;
        L_0x0132:
            goto L_0x002a;
        L_0x0135:
            r25 = move-exception;
            goto L_0x0132;
            goto L_0x013b;
        L_0x0138:
            goto L_0x001f;
        L_0x013b:
            r26 = move-exception;
            goto L_0x0138;
            goto L_0x0141;
        L_0x013e:
            goto L_0x0014;
        L_0x0141:
            r27 = move-exception;
            goto L_0x013e;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.i18n.phonenumbers.PhoneNumberUtil.2.<clinit>():void");
        }
    }

    public enum Leniency {
        POSSIBLE {
            boolean verify(PhoneNumber $r1, String candidate, PhoneNumberUtil $r3) throws  {
                return $r3.isPossibleNumber($r1);
            }
        },
        VALID {
            boolean verify(PhoneNumber $r1, String $r2, PhoneNumberUtil $r3) throws  {
                return ($r3.isValidNumber($r1) && PhoneNumberMatcher.containsOnlyValidXChars($r1, $r2, $r3)) ? PhoneNumberMatcher.isNationalPrefixPresentIfRequired($r1, $r3) : false;
            }
        },
        STRICT_GROUPING {

            class C10641 implements NumberGroupingChecker {
                C10641() throws  {
                }

                public boolean checkGroups(PhoneNumberUtil $r1, PhoneNumber $r2, StringBuilder $r3, String[] $r4) throws  {
                    return PhoneNumberMatcher.allNumberGroupsRemainGrouped($r1, $r2, $r3, $r4);
                }
            }

            boolean verify(PhoneNumber $r1, String $r2, PhoneNumberUtil $r3) throws  {
                return ($r3.isValidNumber($r1) && PhoneNumberMatcher.containsOnlyValidXChars($r1, $r2, $r3) && !PhoneNumberMatcher.containsMoreThanOneSlashInNationalNumber($r1, $r2) && PhoneNumberMatcher.isNationalPrefixPresentIfRequired($r1, $r3)) ? PhoneNumberMatcher.checkNumberGroupingIsValid($r1, $r2, $r3, new C10641()) : false;
            }
        },
        EXACT_GROUPING {

            class C10661 implements NumberGroupingChecker {
                C10661() throws  {
                }

                public boolean checkGroups(PhoneNumberUtil $r1, PhoneNumber $r2, StringBuilder $r3, String[] $r4) throws  {
                    return PhoneNumberMatcher.allNumberGroupsAreExactlyPresent($r1, $r2, $r3, $r4);
                }
            }

            boolean verify(PhoneNumber $r1, String $r2, PhoneNumberUtil $r3) throws  {
                return ($r3.isValidNumber($r1) && PhoneNumberMatcher.containsOnlyValidXChars($r1, $r2, $r3) && !PhoneNumberMatcher.containsMoreThanOneSlashInNationalNumber($r1, $r2) && PhoneNumberMatcher.isNationalPrefixPresentIfRequired($r1, $r3)) ? PhoneNumberMatcher.checkNumberGroupingIsValid($r1, $r2, $r3, new C10661()) : false;
            }
        };

        abstract boolean verify(PhoneNumber phoneNumber, String str, PhoneNumberUtil phoneNumberUtil) throws ;
    }

    public enum MatchType {
        NOT_A_NUMBER,
        NO_MATCH,
        SHORT_NSN_MATCH,
        NSN_MATCH,
        EXACT_MATCH
    }

    public enum PhoneNumberFormat {
        E164,
        INTERNATIONAL,
        NATIONAL,
        RFC3966
    }

    public enum PhoneNumberType {
        FIXED_LINE,
        MOBILE,
        FIXED_LINE_OR_MOBILE,
        TOLL_FREE,
        PREMIUM_RATE,
        SHARED_COST,
        VOIP,
        PERSONAL_NUMBER,
        PAGER,
        UAN,
        VOICEMAIL,
        UNKNOWN
    }

    public enum ValidationResult {
        IS_POSSIBLE,
        INVALID_COUNTRY_CODE,
        TOO_SHORT,
        TOO_LONG
    }

    private void parseHelper(java.lang.String r29, java.lang.String r30, boolean r31, boolean r32, com.google.i18n.phonenumbers.Phonenumber.PhoneNumber r33) throws com.google.i18n.phonenumbers.NumberParseException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:38:0x00f4 in {2, 6, 10, 15, 17, 20, 23, 27, 31, 39, 40, 44, 46, 59, 63, 67, 69} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r28 = this;
        if (r29 != 0) goto L_0x000c;
    L_0x0002:
        r6 = new com.google.i18n.phonenumbers.NumberParseException;
        r7 = com.google.i18n.phonenumbers.NumberParseException.ErrorType.NOT_A_NUMBER;
        r8 = "The phone number supplied was null.";
        r6.<init>(r7, r8);
        throw r6;
    L_0x000c:
        r0 = r29;
        r9 = r0.length();
        r10 = 250; // 0xfa float:3.5E-43 double:1.235E-321;
        if (r9 <= r10) goto L_0x0020;
    L_0x0016:
        r6 = new com.google.i18n.phonenumbers.NumberParseException;
        r7 = com.google.i18n.phonenumbers.NumberParseException.ErrorType.TOO_LONG;
        r8 = "The string supplied was too long to parse.";
        r6.<init>(r7, r8);
        throw r6;
    L_0x0020:
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r0 = r28;
        r1 = r29;
        r0.buildNationalNumberForParsing(r1, r11);
        r12 = r11.toString();
        r13 = isViablePhoneNumber(r12);
        if (r13 != 0) goto L_0x0040;
    L_0x0036:
        r6 = new com.google.i18n.phonenumbers.NumberParseException;
        r7 = com.google.i18n.phonenumbers.NumberParseException.ErrorType.NOT_A_NUMBER;
        r8 = "The string supplied did not seem to be a phone number.";
        r6.<init>(r7, r8);
        throw r6;
    L_0x0040:
        if (r32 == 0) goto L_0x005a;
    L_0x0042:
        r12 = r11.toString();
        r0 = r28;
        r1 = r30;
        r32 = r0.checkRegionForParsing(r12, r1);
        if (r32 != 0) goto L_0x005a;
    L_0x0050:
        r6 = new com.google.i18n.phonenumbers.NumberParseException;
        r7 = com.google.i18n.phonenumbers.NumberParseException.ErrorType.INVALID_COUNTRY_CODE;
        r8 = "Missing or invalid default region.";
        r6.<init>(r7, r8);
        throw r6;
    L_0x005a:
        if (r31 == 0) goto L_0x0063;
    L_0x005c:
        r0 = r33;
        r1 = r29;
        r0.setRawInput(r1);
    L_0x0063:
        r0 = r28;
        r29 = r0.maybeStripExtension(r11);
        r0 = r29;
        r9 = r0.length();
        if (r9 <= 0) goto L_0x0078;
    L_0x0071:
        r0 = r33;
        r1 = r29;
        r0.setExtension(r1);
    L_0x0078:
        r0 = r28;
        r1 = r30;
        r14 = r0.getMetadataForRegion(r1);
        r15 = r14;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r29 = r11.toString();	 Catch:{ NumberParseException -> 0x00c8 }
        r0 = r28;	 Catch:{ NumberParseException -> 0x00c8 }
        r1 = r29;	 Catch:{ NumberParseException -> 0x00c8 }
        r2 = r14;	 Catch:{ NumberParseException -> 0x00c8 }
        r3 = r16;	 Catch:{ NumberParseException -> 0x00c8 }
        r4 = r31;	 Catch:{ NumberParseException -> 0x00c8 }
        r5 = r33;	 Catch:{ NumberParseException -> 0x00c8 }
        r9 = r0.maybeExtractCountryCode(r1, r2, r3, r4, r5);	 Catch:{ NumberParseException -> 0x00c8 }
    L_0x009b:
        if (r9 == 0) goto L_0x012a;
    L_0x009d:
        r0 = r28;
        r29 = r0.getRegionCodeForCountryCode(r9);
        r0 = r29;
        r1 = r30;
        r32 = r0.equals(r1);
        if (r32 != 0) goto L_0x00b5;
    L_0x00ad:
        r0 = r28;
        r1 = r29;
        r15 = r0.getMetadataForRegionOrCallingCode(r9, r1);
    L_0x00b5:
        r0 = r16;
        r9 = r0.length();
        r10 = 2;
        if (r9 >= r10) goto L_0x0146;
    L_0x00be:
        r6 = new com.google.i18n.phonenumbers.NumberParseException;
        r7 = com.google.i18n.phonenumbers.NumberParseException.ErrorType.TOO_SHORT_NSN;
        r8 = "The string supplied is too short to be a phone number.";
        r6.<init>(r7, r8);
        throw r6;
    L_0x00c8:
        r6 = move-exception;
        r17 = PLUS_CHARS_PATTERN;
        r29 = r11.toString();
        r0 = r17;
        r1 = r29;
        r18 = r0.matcher(r1);
        r7 = r6.getErrorType();
        r19 = com.google.i18n.phonenumbers.NumberParseException.ErrorType.INVALID_COUNTRY_CODE;
        r0 = r19;
        if (r7 != r0) goto L_0x0118;
    L_0x00e1:
        r0 = r18;
        r32 = r0.lookingAt();
        if (r32 == 0) goto L_0x0118;
    L_0x00e9:
        r0 = r18;
        r9 = r0.end();
        r29 = r11.substring(r9);
        goto L_0x00fb;
        goto L_0x00f8;
    L_0x00f5:
        goto L_0x00b5;
    L_0x00f8:
        goto L_0x00b5;
    L_0x00fb:
        r0 = r28;
        r1 = r29;
        r2 = r14;
        r3 = r16;
        r4 = r31;
        r5 = r33;
        r20 = r0.maybeExtractCountryCode(r1, r2, r3, r4, r5);
        r9 = r20;
        if (r20 != 0) goto L_0x009b;
    L_0x010e:
        r6 = new com.google.i18n.phonenumbers.NumberParseException;
        r7 = com.google.i18n.phonenumbers.NumberParseException.ErrorType.INVALID_COUNTRY_CODE;
        r8 = "Could not interpret numbers after plus-sign.";
        r6.<init>(r7, r8);
        throw r6;
    L_0x0118:
        r21 = new com.google.i18n.phonenumbers.NumberParseException;
        r7 = r6.getErrorType();
        r29 = r6.getMessage();
        r0 = r21;
        r1 = r29;
        r0.<init>(r7, r1);
        throw r21;
    L_0x012a:
        normalize(r11);
        r0 = r16;
        r0.append(r11);
        if (r30 == 0) goto L_0x013e;
    L_0x0134:
        r9 = r14.getCountryCode();
        r0 = r33;
        r0.setCountryCode(r9);
        goto L_0x00f5;
    L_0x013e:
        if (r31 == 0) goto L_0x00b5;
    L_0x0140:
        r0 = r33;
        r0.clearCountryCodeSource();
        goto L_0x00f8;
    L_0x0146:
        if (r15 == 0) goto L_0x018e;
    L_0x0148:
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r22 = new java.lang.StringBuilder;
        r0 = r22;
        r1 = r16;
        r0.<init>(r1);
        r0 = r28;
        r1 = r22;
        r0.maybeStripNationalPrefixAndCarrierCode(r1, r15, r11);
        r0 = r22;
        r29 = r0.toString();
        r23 = r15.getGeneralDesc();
        r0 = r28;
        r1 = r29;
        r2 = r23;
        r24 = r0.testNumberLength(r1, r2);
        r25 = com.google.i18n.phonenumbers.PhoneNumberUtil.ValidationResult.TOO_SHORT;
        r0 = r24;
        r1 = r25;
        if (r0 == r1) goto L_0x018e;
    L_0x0179:
        r16 = r22;
        if (r31 == 0) goto L_0x018e;
    L_0x017d:
        r9 = r11.length();
        if (r9 <= 0) goto L_0x018e;
    L_0x0183:
        r29 = r11.toString();
        r0 = r33;
        r1 = r29;
        r0.setPreferredDomesticCarrierCode(r1);
    L_0x018e:
        r0 = r16;
        r9 = r0.length();
        r10 = 2;
        if (r9 >= r10) goto L_0x01a1;
    L_0x0197:
        r6 = new com.google.i18n.phonenumbers.NumberParseException;
        r7 = com.google.i18n.phonenumbers.NumberParseException.ErrorType.TOO_SHORT_NSN;
        r8 = "The string supplied is too short to be a phone number.";
        r6.<init>(r7, r8);
        throw r6;
    L_0x01a1:
        r10 = 17;
        if (r9 <= r10) goto L_0x01af;
    L_0x01a5:
        r6 = new com.google.i18n.phonenumbers.NumberParseException;
        r7 = com.google.i18n.phonenumbers.NumberParseException.ErrorType.TOO_LONG;
        r8 = "The string supplied is too long to be a phone number.";
        r6.<init>(r7, r8);
        throw r6;
    L_0x01af:
        r0 = r16;
        r29 = r0.toString();
        r0 = r29;
        r1 = r33;
        setItalianLeadingZerosForPhoneNumber(r0, r1);
        r0 = r16;
        r29 = r0.toString();
        r0 = r29;
        r26 = java.lang.Long.parseLong(r0);
        r0 = r33;
        r1 = r26;
        r0.setNationalNumber(r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.i18n.phonenumbers.PhoneNumberUtil.parseHelper(java.lang.String, java.lang.String, boolean, boolean, com.google.i18n.phonenumbers.Phonenumber$PhoneNumber):void");
    }

    public java.lang.String formatInOriginalFormat(com.google.i18n.phonenumbers.Phonenumber.PhoneNumber r19, java.lang.String r20) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:31:0x00b8
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r18 = this;
        r0 = r19;
        r3 = r0.hasRawInput();
        if (r3 == 0) goto L_0x0023;
    L_0x0008:
        r0 = r18;
        r1 = r19;
        r3 = r0.hasUnexpectedItalianLeadingZero(r1);
        if (r3 != 0) goto L_0x001c;
    L_0x0012:
        r0 = r18;
        r1 = r19;
        r3 = r0.hasFormattingPatternForNumber(r1);
        if (r3 != 0) goto L_0x0023;
    L_0x001c:
        r0 = r19;
        r20 = r0.getRawInput();
        return r20;
    L_0x0023:
        r0 = r19;
        r3 = r0.hasCountryCodeSource();
        if (r3 != 0) goto L_0x0036;
    L_0x002b:
        r4 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL;
        r0 = r18;
        r1 = r19;
        r20 = r0.format(r1, r4);
        return r20;
    L_0x0036:
        r5 = com.google.i18n.phonenumbers.PhoneNumberUtil.C10612.f42x9de98e3a;
        r0 = r19;
        r6 = r0.getCountryCodeSource();
        r7 = r6.ordinal();
        r7 = r5[r7];
        switch(r7) {
            case 1: goto L_0x008c;
            case 2: goto L_0x0097;
            case 3: goto L_0x00a2;
            default: goto L_0x0047;
        };
    L_0x0047:
        goto L_0x0048;
    L_0x0048:
        r0 = r19;
        r7 = r0.getCountryCode();
        r0 = r18;
        r8 = r0.getRegionCodeForCountryCode(r7);
        r10 = 1;
        r0 = r18;
        r9 = r0.getNddPrefixForRegion(r8, r10);
        r4 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL;
        r0 = r18;
        r1 = r19;
        r20 = r0.format(r1, r4);
        if (r9 == 0) goto L_0x006d;
    L_0x0067:
        r7 = r9.length();
        if (r7 != 0) goto L_0x00bc;
    L_0x006d:
        r0 = r19;
        r8 = r0.getRawInput();
        if (r20 == 0) goto L_0x0131;
    L_0x0075:
        r7 = r8.length();
        if (r7 <= 0) goto L_0x0132;
    L_0x007b:
        r0 = r20;
        r9 = normalizeDiallableCharsOnly(r0);
        r11 = normalizeDiallableCharsOnly(r8);
        r3 = r9.equals(r11);
        if (r3 != 0) goto L_0x0133;
    L_0x008b:
        return r8;
    L_0x008c:
        r4 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL;
        r0 = r18;
        r1 = r19;
        r20 = r0.format(r1, r4);
        goto L_0x006d;
    L_0x0097:
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r20 = r0.formatOutOfCountryCallingNumber(r1, r2);
        goto L_0x006d;
    L_0x00a2:
        r4 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL;
        r0 = r18;
        r1 = r19;
        r20 = r0.format(r1, r4);
        goto L_0x00b0;
    L_0x00ad:
        goto L_0x006d;
    L_0x00b0:
        r10 = 1;
        r0 = r20;
        r20 = r0.substring(r10);
        goto L_0x006d;
        goto L_0x00bc;
    L_0x00b9:
        goto L_0x006d;
    L_0x00bc:
        r0 = r19;
        r11 = r0.getRawInput();
        r0 = r18;
        r3 = r0.rawInputContainsNationalPrefix(r11, r9, r8);
        if (r3 == 0) goto L_0x00cf;
    L_0x00ca:
        goto L_0x006d;
        goto L_0x00cf;
    L_0x00cc:
        goto L_0x006d;
    L_0x00cf:
        r0 = r18;
        r12 = r0.getMetadataForRegion(r8);
        r0 = r18;
        r1 = r19;
        r8 = r0.getNationalSignificantNumber(r1);
        r13 = r12.numberFormats();
        r0 = r18;
        r14 = r0.chooseFormattingPatternForNumber(r13, r8);
        if (r14 != 0) goto L_0x00ea;
    L_0x00e9:
        goto L_0x006d;
    L_0x00ea:
        r8 = r14.getNationalPrefixFormattingRule();
        r15 = "$1";
        r7 = r8.indexOf(r15);
        if (r7 > 0) goto L_0x00f7;
    L_0x00f6:
        goto L_0x00ad;
    L_0x00f7:
        r10 = 0;
        r8 = r8.substring(r10, r7);
        r8 = normalizeDigitsOnly(r8);
        r7 = r8.length();
        if (r7 != 0) goto L_0x0107;
    L_0x0106:
        goto L_0x00b9;
    L_0x0107:
        r16 = com.google.i18n.phonenumbers.Phonemetadata.NumberFormat.newBuilder();
        r0 = r16;
        r0.mergeFrom(r14);
        r0 = r16;
        r0.clearNationalPrefixFormattingRule();
        r17 = new java.util.ArrayList;
        r10 = 1;
        r0 = r17;
        r0.<init>(r10);
        r0 = r17;
        r1 = r16;
        r0.add(r1);
        r4 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL;
        r0 = r18;
        r1 = r19;
        r2 = r17;
        r20 = r0.formatByPattern(r1, r4, r2);
        goto L_0x00cc;
    L_0x0131:
        return r20;
    L_0x0132:
        return r20;
    L_0x0133:
        return r20;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.i18n.phonenumbers.PhoneNumberUtil.formatInOriginalFormat(com.google.i18n.phonenumbers.Phonenumber$PhoneNumber, java.lang.String):java.lang.String");
    }

    public java.lang.String formatNumberForMobileDialing(com.google.i18n.phonenumbers.Phonenumber.PhoneNumber r21, java.lang.String r22, boolean r23) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:45:0x00df
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r20 = this;
        r0 = r21;
        r3 = r0.getCountryCode();
        r0 = r20;
        r4 = r0.hasValidCountryCallingCode(r3);
        if (r4 != 0) goto L_0x0020;
    L_0x000e:
        r0 = r21;
        r23 = r0.hasRawInput();
        if (r23 == 0) goto L_0x001d;
    L_0x0016:
        r0 = r21;
        r22 = r0.getRawInput();
        return r22;
    L_0x001d:
        r5 = "";
        return r5;
    L_0x0020:
        r6 = "";
        r7 = new com.google.i18n.phonenumbers.Phonenumber$PhoneNumber;
        r7.<init>();
        r0 = r21;
        r21 = r7.mergeFrom(r0);
        r0 = r21;
        r21 = r0.clearExtension();
        r0 = r20;
        r8 = r0.getRegionCodeForCountryCode(r3);
        r0 = r20;
        r1 = r21;
        r9 = r0.getNumberType(r1);
        r10 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.UNKNOWN;
        if (r9 == r10) goto L_0x0074;
    L_0x0045:
        r11 = 1;
    L_0x0046:
        r0 = r22;
        r4 = r0.equals(r8);
        if (r4 == 0) goto L_0x016c;
    L_0x004e:
        r10 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.FIXED_LINE;
        if (r9 == r10) goto L_0x005a;
    L_0x0052:
        r10 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.MOBILE;
        if (r9 == r10) goto L_0x005a;
    L_0x0056:
        r10 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE;
        if (r9 != r10) goto L_0x0076;
    L_0x005a:
        r4 = 1;
    L_0x005b:
        r5 = "CO";
        r12 = r8.equals(r5);
        if (r12 == 0) goto L_0x0078;
    L_0x0063:
        r10 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.FIXED_LINE;
        if (r9 != r10) goto L_0x0078;
    L_0x0067:
        r5 = "3";
        r0 = r20;
        r1 = r21;
        r6 = r0.formatNationalNumberWithCarrierCode(r1, r5);
    L_0x0071:
        if (r23 == 0) goto L_0x0194;
    L_0x0073:
        return r6;
    L_0x0074:
        r11 = 0;
        goto L_0x0046;
    L_0x0076:
        r4 = 0;
        goto L_0x005b;
    L_0x0078:
        r5 = "BR";
        r12 = r8.equals(r5);
        if (r12 == 0) goto L_0x00a0;
    L_0x0080:
        if (r4 == 0) goto L_0x00a0;
    L_0x0082:
        r0 = r21;
        r22 = r0.getPreferredDomesticCarrierCode();
        r0 = r22;
        r3 = r0.length();
        if (r3 <= 0) goto L_0x009d;
    L_0x0090:
        r5 = "";
        r0 = r20;
        r1 = r21;
        r22 = r0.formatNationalNumberWithPreferredCarrierCode(r1, r5);
    L_0x009a:
        r6 = r22;
        goto L_0x0071;
    L_0x009d:
        r22 = "";
        goto L_0x009a;
    L_0x00a0:
        if (r11 == 0) goto L_0x00e3;
    L_0x00a2:
        r5 = "HU";
        r11 = r8.equals(r5);
        if (r11 == 0) goto L_0x00e3;
    L_0x00aa:
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = 1;
        r0 = r20;
        r22 = r0.getNddPrefixForRegion(r8, r14);
        r0 = r22;
        r13 = r13.append(r0);
        goto L_0x00c0;
    L_0x00bd:
        goto L_0x0071;
    L_0x00c0:
        r5 = " ";
        r13 = r13.append(r5);
        r15 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL;
        goto L_0x00cc;
    L_0x00c9:
        goto L_0x0071;
    L_0x00cc:
        r0 = r20;
        r1 = r21;
        r22 = r0.format(r1, r15);
        r0 = r22;
        r13 = r13.append(r0);
        r6 = r13.toString();
        goto L_0x0071;
        goto L_0x00e3;
    L_0x00e0:
        goto L_0x0071;
    L_0x00e3:
        r14 = 1;
        if (r3 != r14) goto L_0x012e;
    L_0x00e6:
        r0 = r20;
        r1 = r22;
        r16 = r0.getMetadataForRegion(r1);
        r0 = r20;
        r1 = r21;
        r4 = r0.canBeInternationallyDialled(r1);
        if (r4 == 0) goto L_0x0123;
    L_0x00f8:
        r0 = r20;
        r1 = r21;
        r22 = r0.getNationalSignificantNumber(r1);
        r0 = r16;
        r17 = r0.getGeneralDesc();
        r0 = r20;
        r1 = r22;
        r2 = r17;
        r18 = r0.testNumberLength(r1, r2);
        r19 = com.google.i18n.phonenumbers.PhoneNumberUtil.ValidationResult.TOO_SHORT;
        r0 = r18;
        r1 = r19;
        if (r0 == r1) goto L_0x0123;
    L_0x0118:
        r15 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL;
        r0 = r20;
        r1 = r21;
        r6 = r0.format(r1, r15);
        goto L_0x00bd;
    L_0x0123:
        r15 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL;
        r0 = r20;
        r1 = r21;
        r6 = r0.format(r1, r15);
        goto L_0x00c9;
    L_0x012e:
        r5 = "001";
        r11 = r8.equals(r5);
        if (r11 != 0) goto L_0x0148;
    L_0x0136:
        r5 = "MX";
        r11 = r8.equals(r5);
        if (r11 != 0) goto L_0x0146;
    L_0x013e:
        r5 = "CL";
        r11 = r8.equals(r5);
        if (r11 == 0) goto L_0x015d;
    L_0x0146:
        if (r4 == 0) goto L_0x015d;
    L_0x0148:
        r0 = r20;
        r1 = r21;
        r4 = r0.canBeInternationallyDialled(r1);
        if (r4 == 0) goto L_0x015d;
    L_0x0152:
        r15 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL;
        r0 = r20;
        r1 = r21;
        r6 = r0.format(r1, r15);
        goto L_0x00e0;
    L_0x015d:
        r15 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL;
        goto L_0x0163;
    L_0x0160:
        goto L_0x0071;
    L_0x0163:
        r0 = r20;
        r1 = r21;
        r6 = r0.format(r1, r15);
        goto L_0x0160;
    L_0x016c:
        if (r11 == 0) goto L_0x0071;
    L_0x016e:
        r0 = r20;
        r1 = r21;
        r4 = r0.canBeInternationallyDialled(r1);
        if (r4 == 0) goto L_0x0071;
    L_0x0178:
        if (r23 == 0) goto L_0x0185;
    L_0x017a:
        r15 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL;
        r0 = r20;
        r1 = r21;
        r22 = r0.format(r1, r15);
        return r22;
    L_0x0185:
        r15 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164;
        r0 = r20;
        r1 = r21;
        r22 = r0.format(r1, r15);
        return r22;
        goto L_0x0194;
    L_0x0191:
        goto L_0x0073;
    L_0x0194:
        r6 = normalizeDiallableCharsOnly(r6);
        goto L_0x0191;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.i18n.phonenumbers.PhoneNumberUtil.formatNumberForMobileDialing(com.google.i18n.phonenumbers.Phonenumber$PhoneNumber, java.lang.String, boolean):java.lang.String");
    }

    static {
        HashMap $r1 = new HashMap();
        $r1.put(Integer.valueOf(52), AppEventsConstants.EVENT_PARAM_VALUE_YES);
        $r1.put(Integer.valueOf(54), "9");
        MOBILE_TOKEN_MAPPINGS = Collections.unmodifiableMap($r1);
        HashSet $r3 = new HashSet();
        $r3.add(Integer.valueOf(86));
        GEO_MOBILE_COUNTRIES_WITHOUT_MOBILE_AREA_CODES = Collections.unmodifiableSet($r3);
        HashSet $r2 = new HashSet();
        $r2.add(Integer.valueOf(52));
        $r2.add(Integer.valueOf(54));
        $r2.add(Integer.valueOf(55));
        $r2.add(Integer.valueOf(62));
        $r2.addAll($r3);
        GEO_MOBILE_COUNTRIES = Collections.unmodifiableSet($r2);
        $r1 = new HashMap();
        $r1.put(Character.valueOf('0'), Character.valueOf('0'));
        $r1.put(Character.valueOf('1'), Character.valueOf('1'));
        $r1.put(Character.valueOf('2'), Character.valueOf('2'));
        $r1.put(Character.valueOf('3'), Character.valueOf('3'));
        $r1.put(Character.valueOf('4'), Character.valueOf('4'));
        $r1.put(Character.valueOf('5'), Character.valueOf('5'));
        $r1.put(Character.valueOf('6'), Character.valueOf('6'));
        $r1.put(Character.valueOf('7'), Character.valueOf('7'));
        $r1.put(Character.valueOf('8'), Character.valueOf('8'));
        $r1.put(Character.valueOf('9'), Character.valueOf('9'));
        HashMap $r0 = new HashMap(40);
        $r0.put(Character.valueOf('A'), Character.valueOf('2'));
        $r0.put(Character.valueOf('B'), Character.valueOf('2'));
        $r0.put(Character.valueOf('C'), Character.valueOf('2'));
        $r0.put(Character.valueOf('D'), Character.valueOf('3'));
        $r0.put(Character.valueOf('E'), Character.valueOf('3'));
        $r0.put(Character.valueOf('F'), Character.valueOf('3'));
        $r0.put(Character.valueOf('G'), Character.valueOf('4'));
        $r0.put(Character.valueOf('H'), Character.valueOf('4'));
        $r0.put(Character.valueOf('I'), Character.valueOf('4'));
        $r0.put(Character.valueOf('J'), Character.valueOf('5'));
        $r0.put(Character.valueOf('K'), Character.valueOf('5'));
        $r0.put(Character.valueOf('L'), Character.valueOf('5'));
        $r0.put(Character.valueOf('M'), Character.valueOf('6'));
        $r0.put(Character.valueOf('N'), Character.valueOf('6'));
        $r0.put(Character.valueOf('O'), Character.valueOf('6'));
        $r0.put(Character.valueOf('P'), Character.valueOf('7'));
        $r0.put(Character.valueOf('Q'), Character.valueOf('7'));
        $r0.put(Character.valueOf('R'), Character.valueOf('7'));
        $r0.put(Character.valueOf('S'), Character.valueOf('7'));
        $r0.put(Character.valueOf('T'), Character.valueOf('8'));
        $r0.put(Character.valueOf('U'), Character.valueOf('8'));
        $r0.put(Character.valueOf('V'), Character.valueOf('8'));
        $r0.put(Character.valueOf('W'), Character.valueOf('9'));
        $r0.put(Character.valueOf('X'), Character.valueOf('9'));
        $r0.put(Character.valueOf('Y'), Character.valueOf('9'));
        $r0.put(Character.valueOf('Z'), Character.valueOf('9'));
        ALPHA_MAPPINGS = Collections.unmodifiableMap($r0);
        $r0 = new HashMap(100);
        $r0.putAll(ALPHA_MAPPINGS);
        $r0.putAll($r1);
        ALPHA_PHONE_MAPPINGS = Collections.unmodifiableMap($r0);
        $r0 = new HashMap();
        $r0.putAll($r1);
        $r0.put(Character.valueOf(PLUS_SIGN), Character.valueOf(PLUS_SIGN));
        $r0.put(Character.valueOf(STAR_SIGN), Character.valueOf(STAR_SIGN));
        $r0.put(Character.valueOf('#'), Character.valueOf('#'));
        DIALLABLE_CHAR_MAPPINGS = Collections.unmodifiableMap($r0);
        $r0 = new HashMap();
        for (Character charValue : ALPHA_MAPPINGS.keySet()) {
            char $c0 = charValue.charValue();
            $r0.put(Character.valueOf(Character.toLowerCase($c0)), Character.valueOf($c0));
            $r0.put(Character.valueOf($c0), Character.valueOf($c0));
        }
        $r0.putAll($r1);
        $r0.put(Character.valueOf('-'), Character.valueOf('-'));
        $r0.put(Character.valueOf('－'), Character.valueOf('-'));
        $r0.put(Character.valueOf('‐'), Character.valueOf('-'));
        $r0.put(Character.valueOf('‑'), Character.valueOf('-'));
        $r0.put(Character.valueOf('‒'), Character.valueOf('-'));
        $r0.put(Character.valueOf('–'), Character.valueOf('-'));
        $r0.put(Character.valueOf('—'), Character.valueOf('-'));
        $r0.put(Character.valueOf('―'), Character.valueOf('-'));
        $r0.put(Character.valueOf('−'), Character.valueOf('-'));
        $r0.put(Character.valueOf('/'), Character.valueOf('/'));
        $r0.put(Character.valueOf('／'), Character.valueOf('/'));
        $r0.put(Character.valueOf(' '), Character.valueOf(' '));
        $r0.put(Character.valueOf('　'), Character.valueOf(' '));
        $r0.put(Character.valueOf('⁠'), Character.valueOf(' '));
        $r0.put(Character.valueOf(FilenameUtils.EXTENSION_SEPARATOR), Character.valueOf(FilenameUtils.EXTENSION_SEPARATOR));
        $r0.put(Character.valueOf('．'), Character.valueOf(FilenameUtils.EXTENSION_SEPARATOR));
        ALL_PLUS_NUMBER_GROUPING_SYMBOLS = Collections.unmodifiableMap($r0);
    }

    private static String createExtnPattern(String $r0) throws  {
        return ";ext=(\\p{Nd}{1,7})|[  \\t,]*(?:e?xt(?:ensi(?:ó?|ó))?n?|ｅ?ｘｔｎ?|[" + $r0 + "]|int|anexo|ｉｎｔ)[:\\.．]?[  \\t,-]*" + CAPTURING_EXTN_DIGITS + "#?|[- ]+(" + DIGITS + "{1,5})#";
    }

    PhoneNumberUtil(@Signature({"(", "Lcom/google/i18n/phonenumbers/MetadataSource;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;>;)V"}) MetadataSource $r1, @Signature({"(", "Lcom/google/i18n/phonenumbers/MetadataSource;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;>;)V"}) Map<Integer, List<String>> $r2) throws  {
        PhoneNumberUtil phoneNumberUtil = this;
        this.metadataSource = $r1;
        this.countryCallingCodeToRegionCodeMap = $r2;
        for (Entry $r8 : $r2.entrySet()) {
            List $r9 = (List) $r8.getValue();
            if ($r9.size() == 1 && REGION_CODE_FOR_NON_GEO_ENTITY.equals($r9.get(0))) {
                this.countryCodesForNonGeographicalRegion.add($r8.getKey());
            } else {
                this.supportedRegions.addAll($r9);
            }
        }
        if (this.supportedRegions.remove(REGION_CODE_FOR_NON_GEO_ENTITY)) {
            logger.log(Level.WARNING, "invalid metadata (country calling code was mapped to the non-geo entity as well as specific region(s))");
        }
        this.nanpaRegions.addAll((Collection) $r2.get(Integer.valueOf(1)));
    }

    static String extractPossibleNumber(String $r1) throws  {
        Matcher $r3 = VALID_START_CHAR_PATTERN.matcher($r1);
        if (!$r3.find()) {
            return "";
        }
        String $r4 = $r1.substring($r3.start());
        $r1 = $r4;
        $r3 = UNWANTED_END_CHAR_PATTERN.matcher($r4);
        if ($r3.find()) {
            $r4 = $r4.substring(0, $r3.start());
            $r1 = $r4;
            logger.log(Level.FINER, "Stripped trailing characters: " + $r4);
        }
        $r3 = SECOND_NUMBER_START_PATTERN.matcher($r1);
        if ($r3.find()) {
            return $r1.substring(0, $r3.start());
        }
        return $r1;
    }

    static boolean isViablePhoneNumber(String $r0) throws  {
        return $r0.length() < 2 ? false : VALID_PHONE_NUMBER_PATTERN.matcher($r0).matches();
    }

    static String normalize(String $r0) throws  {
        if (VALID_ALPHA_PHONE_PATTERN.matcher($r0).matches()) {
            return normalizeHelper($r0, ALPHA_PHONE_MAPPINGS, true);
        }
        return normalizeDigitsOnly($r0);
    }

    static void normalize(StringBuilder $r0) throws  {
        $r0.replace(0, $r0.length(), normalize($r0.toString()));
    }

    public static String normalizeDigitsOnly(String $r0) throws  {
        return normalizeDigits($r0, false).toString();
    }

    static StringBuilder normalizeDigits(String $r0, boolean $z0) throws  {
        StringBuilder $r1 = new StringBuilder($r0.length());
        for (char $c0 : $r0.toCharArray()) {
            int $i3 = Character.digit($c0, 10);
            if ($i3 != -1) {
                $r1.append($i3);
            } else if ($z0) {
                $r1.append($c0);
            }
        }
        return $r1;
    }

    static String normalizeDiallableCharsOnly(String $r0) throws  {
        return normalizeHelper($r0, DIALLABLE_CHAR_MAPPINGS, true);
    }

    public static String convertAlphaCharactersInNumber(String $r0) throws  {
        return normalizeHelper($r0, ALPHA_PHONE_MAPPINGS, false);
    }

    public int getLengthOfGeographicalAreaCode(PhoneNumber $r1) throws  {
        PhoneMetadata $r3 = getMetadataForRegion(getRegionCodeForNumber($r1));
        if ($r3 == null) {
            return 0;
        }
        if (!$r3.hasNationalPrefix() && !$r1.isItalianLeadingZero()) {
            return 0;
        }
        PhoneNumberType $r4 = getNumberType($r1);
        int $i0 = $r1.getCountryCode();
        if ($r4 == PhoneNumberType.MOBILE && GEO_MOBILE_COUNTRIES_WITHOUT_MOBILE_AREA_CODES.contains(Integer.valueOf($i0))) {
            return 0;
        }
        return isNumberGeographical($r4, $i0) ? getLengthOfNationalDestinationCode($r1) : 0;
    }

    public int getLengthOfNationalDestinationCode(PhoneNumber $r1) throws  {
        PhoneNumber $r2;
        if ($r1.hasExtension()) {
            $r2 = new PhoneNumber();
            $r2.mergeFrom($r1);
            $r2.clearExtension();
        } else {
            $r2 = $r1;
        }
        String[] $r6 = NON_DIGITS_PATTERN.split(format($r2, PhoneNumberFormat.INTERNATIONAL));
        if ($r6.length <= 3) {
            return 0;
        }
        if (getNumberType($r1) != PhoneNumberType.MOBILE || getCountryMobileToken($r1.getCountryCode()).equals("")) {
            return $r6[2].length();
        }
        return $r6[2].length() + $r6[3].length();
    }

    public static String getCountryMobileToken(int $i0) throws  {
        if (MOBILE_TOKEN_MAPPINGS.containsKey(Integer.valueOf($i0))) {
            return (String) MOBILE_TOKEN_MAPPINGS.get(Integer.valueOf($i0));
        }
        return "";
    }

    private static String normalizeHelper(@Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/Character;", "Ljava/lang/Character;", ">;Z)", "Ljava/lang/String;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/Character;", "Ljava/lang/Character;", ">;Z)", "Ljava/lang/String;"}) Map<Character, Character> $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/Character;", "Ljava/lang/Character;", ">;Z)", "Ljava/lang/String;"}) boolean $z0) throws  {
        StringBuilder $r2 = new StringBuilder($r0.length());
        for (int $i0 = 0; $i0 < $r0.length(); $i0++) {
            char $c2 = $r0.charAt($i0);
            Character $r3 = (Character) $r1.get(Character.valueOf(Character.toUpperCase($c2)));
            if ($r3 != null) {
                $r2.append($r3);
            } else if (!$z0) {
                $r2.append($c2);
            }
        }
        return $r2.toString();
    }

    static synchronized void setInstance(PhoneNumberUtil $r0) throws  {
        Class cls = PhoneNumberUtil.class;
        synchronized (cls) {
            try {
                instance = $r0;
            } finally {
                cls = PhoneNumberUtil.class;
            }
        }
    }

    public Set<String> getSupportedRegions() throws  {
        return Collections.unmodifiableSet(this.supportedRegions);
    }

    public Set<Integer> getSupportedGlobalNetworkCallingCodes() throws  {
        return Collections.unmodifiableSet(this.countryCodesForNonGeographicalRegion);
    }

    public static synchronized PhoneNumberUtil getInstance() throws  {
        Class cls = PhoneNumberUtil.class;
        synchronized (cls) {
            try {
                if (instance == null) {
                    setInstance(createInstance(MetadataManager.DEFAULT_METADATA_LOADER));
                }
                PhoneNumberUtil $r0 = instance;
                return $r0;
            } finally {
                cls = PhoneNumberUtil.class;
            }
        }
    }

    public static PhoneNumberUtil createInstance(MetadataLoader $r0) throws  {
        if ($r0 != null) {
            return createInstance(new MultiFileMetadataSourceImpl($r0));
        }
        throw new IllegalArgumentException("metadataLoader could not be null.");
    }

    private static PhoneNumberUtil createInstance(MetadataSource $r0) throws  {
        if ($r0 != null) {
            return new PhoneNumberUtil($r0, CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap());
        }
        throw new IllegalArgumentException("metadataSource could not be null.");
    }

    static boolean formattingRuleHasFirstGroupOnly(String $r0) throws  {
        return $r0.length() == 0 || FIRST_GROUP_ONLY_PREFIX_PATTERN.matcher($r0).matches();
    }

    public boolean isNumberGeographical(PhoneNumber $r1) throws  {
        return isNumberGeographical(getNumberType($r1), $r1.getCountryCode());
    }

    public boolean isNumberGeographical(PhoneNumberType $r1, int $i0) throws  {
        return $r1 == PhoneNumberType.FIXED_LINE || $r1 == PhoneNumberType.FIXED_LINE_OR_MOBILE || (GEO_MOBILE_COUNTRIES.contains(Integer.valueOf($i0)) && $r1 == PhoneNumberType.MOBILE);
    }

    private boolean isValidRegionCode(String $r1) throws  {
        return $r1 != null && this.supportedRegions.contains($r1);
    }

    private boolean hasValidCountryCallingCode(int $i0) throws  {
        return this.countryCallingCodeToRegionCodeMap.containsKey(Integer.valueOf($i0));
    }

    public String format(PhoneNumber $r1, PhoneNumberFormat $r2) throws  {
        if ($r1.getNationalNumber() == 0 && $r1.hasRawInput()) {
            String $r4 = $r1.getRawInput();
            if ($r4.length() > 0) {
                return $r4;
            }
        }
        StringBuilder $r3 = new StringBuilder(20);
        format($r1, $r2, $r3);
        return $r3.toString();
    }

    public void format(PhoneNumber $r1, PhoneNumberFormat $r2, StringBuilder $r3) throws  {
        $r3.setLength(0);
        int $i0 = $r1.getCountryCode();
        String $r4 = getNationalSignificantNumber($r1);
        if ($r2 == PhoneNumberFormat.E164) {
            $r3.append($r4);
            prefixNumberWithCountryCallingCode($i0, PhoneNumberFormat.E164, $r3);
        } else if (hasValidCountryCallingCode($i0)) {
            PhoneMetadata $r7 = getMetadataForRegionOrCallingCode($i0, getRegionCodeForCountryCode($i0));
            $r3.append(formatNsn($r4, $r7, $r2));
            maybeAppendFormattedExtension($r1, $r7, $r2, $r3);
            prefixNumberWithCountryCallingCode($i0, $r2, $r3);
        } else {
            $r3.append($r4);
        }
    }

    public String formatByPattern(@Signature({"(", "Lcom/google/i18n/phonenumbers/Phonenumber$PhoneNumber;", "Lcom/google/i18n/phonenumbers/PhoneNumberUtil$PhoneNumberFormat;", "Ljava/util/List", "<", "Lcom/google/i18n/phonenumbers/Phonemetadata$NumberFormat;", ">;)", "Ljava/lang/String;"}) PhoneNumber $r1, @Signature({"(", "Lcom/google/i18n/phonenumbers/Phonenumber$PhoneNumber;", "Lcom/google/i18n/phonenumbers/PhoneNumberUtil$PhoneNumberFormat;", "Ljava/util/List", "<", "Lcom/google/i18n/phonenumbers/Phonemetadata$NumberFormat;", ">;)", "Ljava/lang/String;"}) PhoneNumberFormat $r2, @Signature({"(", "Lcom/google/i18n/phonenumbers/Phonenumber$PhoneNumber;", "Lcom/google/i18n/phonenumbers/PhoneNumberUtil$PhoneNumberFormat;", "Ljava/util/List", "<", "Lcom/google/i18n/phonenumbers/Phonemetadata$NumberFormat;", ">;)", "Ljava/lang/String;"}) List<NumberFormat> $r3) throws  {
        int $i0 = $r1.getCountryCode();
        String $r5 = getNationalSignificantNumber($r1);
        if (!hasValidCountryCallingCode($i0)) {
            return $r5;
        }
        PhoneMetadata $r7 = getMetadataForRegionOrCallingCode($i0, getRegionCodeForCountryCode($i0));
        StringBuilder $r4 = new StringBuilder(20);
        NumberFormat $r8 = chooseFormattingPatternForNumber($r3, $r5);
        if ($r8 == null) {
            $r4.append($r5);
        } else {
            Builder $r9 = NumberFormat.newBuilder();
            $r9.mergeFrom($r8);
            String $r6 = $r8.getNationalPrefixFormattingRule();
            if ($r6.length() > 0) {
                String $r10 = $r7.getNationalPrefix();
                if ($r10.length() > 0) {
                    $r9.setNationalPrefixFormattingRule(FG_PATTERN.matcher(NP_PATTERN.matcher($r6).replaceFirst($r10)).replaceFirst("\\$1"));
                } else {
                    $r9.clearNationalPrefixFormattingRule();
                }
            }
            $r4.append(formatNsnUsingPattern($r5, $r9, $r2));
        }
        maybeAppendFormattedExtension($r1, $r7, $r2, $r4);
        prefixNumberWithCountryCallingCode($i0, $r2, $r4);
        return $r4.toString();
    }

    public String formatNationalNumberWithCarrierCode(PhoneNumber $r1, String $r2) throws  {
        int $i0 = $r1.getCountryCode();
        String $r4 = getNationalSignificantNumber($r1);
        if (!hasValidCountryCallingCode($i0)) {
            return $r4;
        }
        PhoneMetadata $r6 = getMetadataForRegionOrCallingCode($i0, getRegionCodeForCountryCode($i0));
        StringBuilder $r3 = new StringBuilder(20);
        $r3.append(formatNsn($r4, $r6, PhoneNumberFormat.NATIONAL, $r2));
        maybeAppendFormattedExtension($r1, $r6, PhoneNumberFormat.NATIONAL, $r3);
        prefixNumberWithCountryCallingCode($i0, PhoneNumberFormat.NATIONAL, $r3);
        return $r3.toString();
    }

    private PhoneMetadata getMetadataForRegionOrCallingCode(int $i0, String $r1) throws  {
        if (REGION_CODE_FOR_NON_GEO_ENTITY.equals($r1)) {
            return getMetadataForNonGeographicalRegion($i0);
        }
        return getMetadataForRegion($r1);
    }

    public String formatNationalNumberWithPreferredCarrierCode(PhoneNumber $r1, String $r2) throws  {
        if ($r1.getPreferredDomesticCarrierCode().length() > 0) {
            $r2 = $r1.getPreferredDomesticCarrierCode();
        }
        return formatNationalNumberWithCarrierCode($r1, $r2);
    }

    public String formatOutOfCountryCallingNumber(PhoneNumber $r1, String $r2) throws  {
        if (isValidRegionCode($r2)) {
            int $i0 = $r1.getCountryCode();
            String $r7 = getNationalSignificantNumber($r1);
            if (!hasValidCountryCallingCode($i0)) {
                return $r7;
            }
            if ($i0 == 1) {
                if (isNANPACountry($r2)) {
                    return $i0 + " " + format($r1, PhoneNumberFormat.NATIONAL);
                }
            } else if ($i0 == getCountryCodeForValidRegion($r2)) {
                return format($r1, PhoneNumberFormat.NATIONAL);
            }
            PhoneMetadata $r8 = getMetadataForRegion($r2);
            String $r9 = $r8.getInternationalPrefix();
            $r2 = "";
            if (UNIQUE_INTERNATIONAL_PREFIX.matcher($r9).matches()) {
                $r2 = $r9;
            } else if ($r8.hasPreferredInternationalPrefix()) {
                $r2 = $r8.getPreferredInternationalPrefix();
            }
            $r8 = getMetadataForRegionOrCallingCode($i0, getRegionCodeForCountryCode($i0));
            StringBuilder $r3 = new StringBuilder(formatNsn($r7, $r8, PhoneNumberFormat.INTERNATIONAL));
            maybeAppendFormattedExtension($r1, $r8, PhoneNumberFormat.INTERNATIONAL, $r3);
            if ($r2.length() > 0) {
                $r3.insert(0, " ").insert(0, $i0).insert(0, " ").insert(0, $r2);
            } else {
                prefixNumberWithCountryCallingCode($i0, PhoneNumberFormat.INTERNATIONAL, $r3);
            }
            return $r3.toString();
        }
        logger.log(Level.WARNING, "Trying to format number from invalid region " + $r2 + ". International formatting applied.");
        return format($r1, PhoneNumberFormat.INTERNATIONAL);
    }

    private boolean rawInputContainsNationalPrefix(String $r1, String $r2, String $r3) throws  {
        $r1 = normalizeDigitsOnly($r1);
        if (!$r1.startsWith($r2)) {
            return false;
        }
        try {
            return isValidNumber(parse($r1.substring($r2.length()), $r3));
        } catch (NumberParseException e) {
            return false;
        }
    }

    private boolean hasUnexpectedItalianLeadingZero(PhoneNumber $r1) throws  {
        return $r1.isItalianLeadingZero() && !isLeadingZeroPossible($r1.getCountryCode());
    }

    private boolean hasFormattingPatternForNumber(PhoneNumber $r1) throws  {
        int $i0 = $r1.getCountryCode();
        PhoneMetadata $r3 = getMetadataForRegionOrCallingCode($i0, getRegionCodeForCountryCode($i0));
        if ($r3 == null) {
            return false;
        }
        return chooseFormattingPatternForNumber($r3.numberFormats(), getNationalSignificantNumber($r1)) != null;
    }

    public String formatOutOfCountryKeepingAlphaChars(PhoneNumber $r1, String $r2) throws  {
        String $r4 = $r1.getRawInput();
        if ($r4.length() == 0) {
            return formatOutOfCountryCallingNumber($r1, $r2);
        }
        int $i0 = $r1.getCountryCode();
        if (!hasValidCountryCallingCode($i0)) {
            return $r4;
        }
        String $r6 = normalizeHelper($r4, ALL_PLUS_NUMBER_GROUPING_SYMBOLS, true);
        $r4 = $r6;
        String $r7 = getNationalSignificantNumber($r1);
        if ($r7.length() > 3) {
            int $i1 = $r6.indexOf($r7.substring(0, 3));
            if ($i1 != -1) {
                $r4 = $r6.substring($i1);
            }
        }
        PhoneMetadata $r9 = getMetadataForRegion($r2);
        if ($i0 == 1) {
            if (isNANPACountry($r2)) {
                return $i0 + " " + $r4;
            }
        } else if ($r9 != null && $i0 == getCountryCodeForValidRegion($r2)) {
            NumberFormat $r11 = chooseFormattingPatternForNumber($r9.numberFormats(), $r7);
            if ($r11 == null) {
                return $r4;
            }
            NumberFormat $r12 = NumberFormat.newBuilder();
            $r12.mergeFrom($r11);
            $r12.setPattern("(\\d+)(.*)");
            $r12.setFormat("$1$2");
            return formatNsnUsingPattern($r4, $r12, PhoneNumberFormat.NATIONAL);
        }
        $r7 = "";
        if ($r9 != null) {
            $r7 = $r9.getInternationalPrefix();
            if (!UNIQUE_INTERNATIONAL_PREFIX.matcher($r7).matches()) {
                $r7 = $r9.getPreferredInternationalPrefix();
            }
        }
        StringBuilder $r3 = new StringBuilder($r4);
        maybeAppendFormattedExtension($r1, getMetadataForRegionOrCallingCode($i0, getRegionCodeForCountryCode($i0)), PhoneNumberFormat.INTERNATIONAL, $r3);
        if ($r7.length() > 0) {
            $r3.insert(0, " ").insert(0, $i0).insert(0, " ").insert(0, $r7);
        } else {
            if (!isValidRegionCode($r2)) {
                logger.log(Level.WARNING, "Trying to format number from invalid region " + $r2 + ". International formatting applied.");
            }
            prefixNumberWithCountryCallingCode($i0, PhoneNumberFormat.INTERNATIONAL, $r3);
        }
        return $r3.toString();
    }

    public String getNationalSignificantNumber(PhoneNumber $r1) throws  {
        StringBuilder $r2 = new StringBuilder();
        if ($r1.isItalianLeadingZero()) {
            char[] $r3 = new char[$r1.getNumberOfLeadingZeros()];
            Arrays.fill($r3, '0');
            $r2.append(new String($r3));
        }
        $r2.append($r1.getNationalNumber());
        return $r2.toString();
    }

    private void prefixNumberWithCountryCallingCode(int $i0, PhoneNumberFormat $r1, StringBuilder $r2) throws  {
        switch (C10612.f40xd187ceb9[$r1.ordinal()]) {
            case 1:
                $r2.insert(0, $i0).insert(0, PLUS_SIGN);
                return;
            case 2:
                $r2.insert(0, " ").insert(0, $i0).insert(0, PLUS_SIGN);
                return;
            case 3:
                $r2.insert(0, "-").insert(0, $i0).insert(0, PLUS_SIGN).insert(0, "tel:");
                return;
            default:
                return;
        }
    }

    private String formatNsn(String $r1, PhoneMetadata $r2, PhoneNumberFormat $r3) throws  {
        return formatNsn($r1, $r2, $r3, null);
    }

    private String formatNsn(String $r4, PhoneMetadata $r1, PhoneNumberFormat $r2, String $r3) throws  {
        List $r5;
        if ($r1.intlNumberFormats().size() == 0 || $r2 == PhoneNumberFormat.NATIONAL) {
            $r5 = $r1.numberFormats();
        } else {
            $r5 = $r1.intlNumberFormats();
        }
        NumberFormat $r7 = chooseFormattingPatternForNumber($r5, $r4);
        if ($r7 == null) {
            return $r4;
        }
        return formatNsnUsingPattern($r4, $r7, $r2, $r3);
    }

    NumberFormat chooseFormattingPatternForNumber(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/i18n/phonenumbers/Phonemetadata$NumberFormat;", ">;", "Ljava/lang/String;", ")", "Lcom/google/i18n/phonenumbers/Phonemetadata$NumberFormat;"}) List<NumberFormat> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/i18n/phonenumbers/Phonemetadata$NumberFormat;", ">;", "Ljava/lang/String;", ")", "Lcom/google/i18n/phonenumbers/Phonemetadata$NumberFormat;"}) String $r2) throws  {
        for (NumberFormat $r5 : $r1) {
            int $i0 = $r5.leadingDigitsPatternSize();
            if (($i0 == 0 || this.regexCache.getPatternForRegex($r5.getLeadingDigitsPattern($i0 - 1)).matcher($r2).lookingAt()) && this.regexCache.getPatternForRegex($r5.getPattern()).matcher($r2).matches()) {
                return $r5;
            }
        }
        return null;
    }

    String formatNsnUsingPattern(String $r1, NumberFormat $r2, PhoneNumberFormat $r3) throws  {
        return formatNsnUsingPattern($r1, $r2, $r3, null);
    }

    private String formatNsnUsingPattern(String $r1, NumberFormat $r2, PhoneNumberFormat $r3, String $r4) throws  {
        String $r5 = $r2.getFormat();
        Matcher $r9 = this.regexCache.getPatternForRegex($r2.getPattern()).matcher($r1);
        if ($r3 != PhoneNumberFormat.NATIONAL || $r4 == null || $r4.length() <= 0 || $r2.getDomesticCarrierCodeFormattingRule().length() <= 0) {
            $r1 = $r2.getNationalPrefixFormattingRule();
            if ($r3 != PhoneNumberFormat.NATIONAL || $r1 == null || $r1.length() <= 0) {
                $r1 = $r9.replaceAll($r5);
            } else {
                $r1 = $r9.replaceAll(FIRST_GROUP_PATTERN.matcher($r5).replaceFirst($r1));
            }
        } else {
            $r1 = $r9.replaceAll(FIRST_GROUP_PATTERN.matcher($r5).replaceFirst(CC_PATTERN.matcher($r2.getDomesticCarrierCodeFormattingRule()).replaceFirst($r4)));
        }
        if ($r3 != PhoneNumberFormat.RFC3966) {
            return $r1;
        }
        $r9 = SEPARATOR_PATTERN.matcher($r1);
        if ($r9.lookingAt()) {
            $r1 = $r9.replaceFirst("");
        }
        return $r9.reset($r1).replaceAll("-");
    }

    public PhoneNumber getExampleNumber(String $r1) throws  {
        return getExampleNumberForType($r1, PhoneNumberType.FIXED_LINE);
    }

    public PhoneNumber getInvalidExampleNumber(String $r1) throws  {
        if (isValidRegionCode($r1)) {
            PhoneNumberDesc $r7 = getNumberDescByType(getMetadataForRegion($r1), PhoneNumberType.FIXED_LINE);
            if (!$r7.hasExampleNumber()) {
                return null;
            }
            String $r8 = $r7.getExampleNumber();
            int $i0 = $r8.length() - 1;
            while ($i0 >= 2) {
                try {
                    PhoneNumber $r10 = parse($r8.substring(0, $i0), $r1);
                    if (!isValidNumber($r10)) {
                        return $r10;
                    }
                    $i0--;
                } catch (NumberParseException e) {
                }
            }
            return null;
        }
        logger.log(Level.WARNING, "Invalid or unknown region code provided: " + $r1);
        return null;
    }

    public PhoneNumber getExampleNumberForType(String $r1, PhoneNumberType $r2) throws  {
        if (isValidRegionCode($r1)) {
            PhoneNumberDesc $r8 = getNumberDescByType(getMetadataForRegion($r1), $r2);
            try {
                if ($r8.hasExampleNumber()) {
                    return parse($r8.getExampleNumber(), $r1);
                }
                return null;
            } catch (NumberParseException $r3) {
                logger.log(Level.SEVERE, $r3.toString());
                return null;
            }
        }
        logger.log(Level.WARNING, "Invalid or unknown region code provided: " + $r1);
        return null;
    }

    public PhoneNumber getExampleNumberForType(PhoneNumberType $r1) throws  {
        for (String exampleNumberForType : getSupportedRegions()) {
            PhoneNumber $r7 = getExampleNumberForType(exampleNumberForType, $r1);
            if ($r7 != null) {
                return $r7;
            }
        }
        for (Integer intValue : getSupportedGlobalNetworkCallingCodes()) {
            int $i0 = intValue.intValue();
            PhoneNumberDesc $r10 = getNumberDescByType(getMetadataForNonGeographicalRegion($i0), $r1);
            try {
                if ($r10.hasExampleNumber()) {
                    return parse("+" + $i0 + $r10.getExampleNumber(), UNKNOWN_REGION);
                }
                continue;
            } catch (NumberParseException $r2) {
                logger.log(Level.SEVERE, $r2.toString());
            }
        }
        return null;
    }

    public PhoneNumber getExampleNumberForNonGeoEntity(int $i0) throws  {
        Logger $r11;
        if (getMetadataForNonGeographicalRegion($i0) != null) {
            for (PhoneNumberDesc $r4 : Arrays.asList(new PhoneNumberDesc[]{getMetadataForNonGeographicalRegion($i0).getMobile(), getMetadataForNonGeographicalRegion($i0).getTollFree(), getMetadataForNonGeographicalRegion($i0).getSharedCost(), getMetadataForNonGeographicalRegion($i0).getVoip(), getMetadataForNonGeographicalRegion($i0).getVoicemail(), getMetadataForNonGeographicalRegion($i0).getUan(), getMetadataForNonGeographicalRegion($i0).getPremiumRate()})) {
                if ($r4 != null) {
                    try {
                        if ($r4.hasExampleNumber()) {
                            return parse("+" + $i0 + $r4.getExampleNumber(), UNKNOWN_REGION);
                        }
                        continue;
                    } catch (NumberParseException $r1) {
                        $r11 = logger;
                        $r11.log(Level.SEVERE, $r1.toString());
                    }
                }
            }
        } else {
            $r11 = logger;
            $r11.log(Level.WARNING, "Invalid or unknown country calling code provided: " + $i0);
        }
        return null;
    }

    private void maybeAppendFormattedExtension(PhoneNumber $r1, PhoneMetadata $r2, PhoneNumberFormat $r3, StringBuilder $r4) throws  {
        if ($r1.hasExtension() && $r1.getExtension().length() > 0) {
            if ($r3 == PhoneNumberFormat.RFC3966) {
                $r4.append(RFC3966_EXTN_PREFIX).append($r1.getExtension());
            } else if ($r2.hasPreferredExtnPrefix()) {
                $r4.append($r2.getPreferredExtnPrefix()).append($r1.getExtension());
            } else {
                $r4.append(DEFAULT_EXTN_PREFIX).append($r1.getExtension());
            }
        }
    }

    PhoneNumberDesc getNumberDescByType(PhoneMetadata $r1, PhoneNumberType $r2) throws  {
        switch (C10612.f41xa7892e7c[$r2.ordinal()]) {
            case 1:
                return $r1.getPremiumRate();
            case 2:
                return $r1.getTollFree();
            case 3:
                return $r1.getMobile();
            case 4:
            case 5:
                return $r1.getFixedLine();
            case 6:
                return $r1.getSharedCost();
            case 7:
                return $r1.getVoip();
            case 8:
                return $r1.getPersonalNumber();
            case 9:
                return $r1.getPager();
            case 10:
                return $r1.getUan();
            case 11:
                return $r1.getVoicemail();
            default:
                return $r1.getGeneralDesc();
        }
    }

    public PhoneNumberType getNumberType(PhoneNumber $r1) throws  {
        PhoneMetadata $r3 = getMetadataForRegionOrCallingCode($r1.getCountryCode(), getRegionCodeForNumber($r1));
        if ($r3 == null) {
            return PhoneNumberType.UNKNOWN;
        }
        return getNumberTypeHelper(getNationalSignificantNumber($r1), $r3);
    }

    private PhoneNumberType getNumberTypeHelper(String $r1, PhoneMetadata $r2) throws  {
        if (!isNumberMatchingDesc($r1, $r2.getGeneralDesc())) {
            return PhoneNumberType.UNKNOWN;
        }
        if (isNumberMatchingDesc($r1, $r2.getPremiumRate())) {
            return PhoneNumberType.PREMIUM_RATE;
        }
        if (isNumberMatchingDesc($r1, $r2.getTollFree())) {
            return PhoneNumberType.TOLL_FREE;
        }
        if (isNumberMatchingDesc($r1, $r2.getSharedCost())) {
            return PhoneNumberType.SHARED_COST;
        }
        if (isNumberMatchingDesc($r1, $r2.getVoip())) {
            return PhoneNumberType.VOIP;
        }
        if (isNumberMatchingDesc($r1, $r2.getPersonalNumber())) {
            return PhoneNumberType.PERSONAL_NUMBER;
        }
        if (isNumberMatchingDesc($r1, $r2.getPager())) {
            return PhoneNumberType.PAGER;
        }
        if (isNumberMatchingDesc($r1, $r2.getUan())) {
            return PhoneNumberType.UAN;
        }
        if (isNumberMatchingDesc($r1, $r2.getVoicemail())) {
            return PhoneNumberType.VOICEMAIL;
        }
        if (isNumberMatchingDesc($r1, $r2.getFixedLine())) {
            if ($r2.isSameMobileAndFixedLinePattern()) {
                return PhoneNumberType.FIXED_LINE_OR_MOBILE;
            }
            if (isNumberMatchingDesc($r1, $r2.getMobile())) {
                return PhoneNumberType.FIXED_LINE_OR_MOBILE;
            }
            return PhoneNumberType.FIXED_LINE;
        } else if ($r2.isSameMobileAndFixedLinePattern() || !isNumberMatchingDesc($r1, $r2.getMobile())) {
            return PhoneNumberType.UNKNOWN;
        } else {
            return PhoneNumberType.MOBILE;
        }
    }

    PhoneMetadata getMetadataForRegion(String $r1) throws  {
        return !isValidRegionCode($r1) ? null : this.metadataSource.getMetadataForRegion($r1);
    }

    PhoneMetadata getMetadataForNonGeographicalRegion(int $i0) throws  {
        return !this.countryCallingCodeToRegionCodeMap.containsKey(Integer.valueOf($i0)) ? null : this.metadataSource.getMetadataForNonGeographicalRegion($i0);
    }

    boolean isNumberMatchingDesc(String $r1, PhoneNumberDesc $r2) throws  {
        int $i0 = $r1.length();
        List $r3 = $r2.getPossibleLengthList();
        return ($r3.size() <= 0 || $r3.contains(Integer.valueOf($i0))) ? this.regexCache.getPatternForRegex($r2.getNationalNumberPattern()).matcher($r1).matches() : false;
    }

    public boolean isValidNumber(PhoneNumber $r1) throws  {
        return isValidNumberForRegion($r1, getRegionCodeForNumber($r1));
    }

    public boolean isValidNumberForRegion(PhoneNumber $r1, String $r2) throws  {
        int $i0 = $r1.getCountryCode();
        PhoneMetadata $r4 = getMetadataForRegionOrCallingCode($i0, $r2);
        if ($r4 == null) {
            return false;
        }
        if (REGION_CODE_FOR_NON_GEO_ENTITY.equals($r2) || $i0 == getCountryCodeForValidRegion($r2)) {
            return getNumberTypeHelper(getNationalSignificantNumber($r1), $r4) != PhoneNumberType.UNKNOWN;
        } else {
            return false;
        }
    }

    public String getRegionCodeForNumber(PhoneNumber $r1) throws  {
        int $i0 = $r1.getCountryCode();
        List $r5 = (List) this.countryCallingCodeToRegionCodeMap.get(Integer.valueOf($i0));
        if ($r5 == null) {
            logger.log(Level.INFO, "Missing/invalid country_code (" + $i0 + ") for number " + getNationalSignificantNumber($r1));
            return null;
        } else if ($r5.size() == 1) {
            return (String) $r5.get(0);
        } else {
            return getRegionCodeForNumberFromRegionList($r1, $r5);
        }
    }

    private String getRegionCodeForNumberFromRegionList(@Signature({"(", "Lcom/google/i18n/phonenumbers/Phonenumber$PhoneNumber;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Ljava/lang/String;"}) PhoneNumber $r1, @Signature({"(", "Lcom/google/i18n/phonenumbers/Phonenumber$PhoneNumber;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Ljava/lang/String;"}) List<String> $r2) throws  {
        String $r3 = getNationalSignificantNumber($r1);
        for (String $r6 : $r2) {
            PhoneMetadata $r7 = getMetadataForRegion($r6);
            if ($r7.hasLeadingDigits()) {
                if (this.regexCache.getPatternForRegex($r7.getLeadingDigits()).matcher($r3).lookingAt()) {
                    return $r6;
                }
            } else if (getNumberTypeHelper($r3, $r7) != PhoneNumberType.UNKNOWN) {
                return $r6;
            }
        }
        return null;
    }

    public String getRegionCodeForCountryCode(int $i0) throws  {
        List $r4 = (List) this.countryCallingCodeToRegionCodeMap.get(Integer.valueOf($i0));
        if ($r4 == null) {
            return UNKNOWN_REGION;
        }
        return (String) $r4.get(0);
    }

    public List<String> getRegionCodesForCountryCode(@Signature({"(I)", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) int $i0) throws  {
        List $r4 = (List) this.countryCallingCodeToRegionCodeMap.get(Integer.valueOf($i0));
        if ($r4 == null) {
            $r4 = r5;
            ArrayList r5 = new ArrayList(0);
        }
        return Collections.unmodifiableList($r4);
    }

    public int getCountryCodeForRegion(String $r2) throws  {
        if (isValidRegionCode($r2)) {
            return getCountryCodeForValidRegion($r2);
        }
        Logger $r3 = logger;
        Level $r1 = Level.WARNING;
        StringBuilder $r4 = new StringBuilder().append("Invalid or missing region code (");
        if ($r2 == null) {
            $r2 = "null";
        }
        $r3.log($r1, $r4.append($r2).append(") provided.").toString());
        return 0;
    }

    private int getCountryCodeForValidRegion(String $r1) throws  {
        PhoneMetadata $r2 = getMetadataForRegion($r1);
        if ($r2 != null) {
            return $r2.getCountryCode();
        }
        throw new IllegalArgumentException("Invalid region code: " + $r1);
    }

    public String getNddPrefixForRegion(String $r2, boolean $z0) throws  {
        PhoneMetadata $r3 = getMetadataForRegion($r2);
        if ($r3 == null) {
            Logger $r4 = logger;
            Level $r1 = Level.WARNING;
            StringBuilder $r5 = new StringBuilder().append("Invalid or missing region code (");
            if ($r2 == null) {
                $r2 = "null";
            }
            $r4.log($r1, $r5.append($r2).append(") provided.").toString());
            return null;
        }
        $r2 = $r3.getNationalPrefix();
        if ($r2.length() == 0) {
            return null;
        }
        return $z0 ? $r2.replace("~", "") : $r2;
    }

    public boolean isNANPACountry(String $r1) throws  {
        return this.nanpaRegions.contains($r1);
    }

    boolean isLeadingZeroPossible(int $i0) throws  {
        PhoneMetadata $r2 = getMetadataForRegionOrCallingCode($i0, getRegionCodeForCountryCode($i0));
        return $r2 == null ? false : $r2.isLeadingZeroPossible();
    }

    public boolean isAlphaNumber(String $r1) throws  {
        if (!isViablePhoneNumber($r1)) {
            return false;
        }
        StringBuilder $r2 = new StringBuilder($r1);
        maybeStripExtension($r2);
        return VALID_ALPHA_PHONE_PATTERN.matcher($r2).matches();
    }

    public boolean isPossibleNumber(PhoneNumber $r1) throws  {
        return isPossibleNumberWithReason($r1) == ValidationResult.IS_POSSIBLE;
    }

    private ValidationResult testNumberLength(String $r1, PhoneNumberDesc $r2) throws  {
        List $r3 = $r2.getPossibleLengthList();
        List $r4 = $r2.getPossibleLengthLocalOnlyList();
        int $i0 = $r1.length();
        if ($r4.contains(Integer.valueOf($i0))) {
            return ValidationResult.IS_POSSIBLE;
        }
        int $i1 = ((Integer) $r3.get(0)).intValue();
        if ($i1 == $i0) {
            return ValidationResult.IS_POSSIBLE;
        }
        if ($i1 > $i0) {
            return ValidationResult.TOO_SHORT;
        }
        if (((Integer) $r3.get($r3.size() - 1)).intValue() < $i0) {
            return ValidationResult.TOO_LONG;
        }
        return $r3.subList(1, $r3.size()).contains(Integer.valueOf($i0)) ? ValidationResult.IS_POSSIBLE : ValidationResult.TOO_LONG;
    }

    public ValidationResult isPossibleNumberWithReason(PhoneNumber $r1) throws  {
        String $r2 = getNationalSignificantNumber($r1);
        int $i0 = $r1.getCountryCode();
        if (hasValidCountryCallingCode($i0)) {
            return testNumberLength($r2, getMetadataForRegionOrCallingCode($i0, getRegionCodeForCountryCode($i0)).getGeneralDesc());
        }
        return ValidationResult.INVALID_COUNTRY_CODE;
    }

    public boolean isPossibleNumber(String $r1, String $r2) throws  {
        try {
            return isPossibleNumber(parse($r1, $r2));
        } catch (NumberParseException e) {
            return false;
        }
    }

    public boolean truncateTooLongNumber(PhoneNumber $r1) throws  {
        if (isValidNumber($r1)) {
            return true;
        }
        PhoneNumber $r2 = new PhoneNumber();
        $r2.mergeFrom($r1);
        long $l0 = $r1.getNationalNumber();
        do {
            $l0 /= 10;
            $r2.setNationalNumber($l0);
            if (isPossibleNumberWithReason($r2) == ValidationResult.TOO_SHORT || $l0 == 0) {
                return false;
            }
        } while (!isValidNumber($r2));
        $r1.setNationalNumber($l0);
        return true;
    }

    public AsYouTypeFormatter getAsYouTypeFormatter(String $r1) throws  {
        return new AsYouTypeFormatter($r1);
    }

    int extractCountryCode(StringBuilder $r1, StringBuilder $r2) throws  {
        if ($r1.length() == 0 || $r1.charAt(0) == '0') {
            return 0;
        }
        int $i0 = $r1.length();
        int $i2 = 1;
        while ($i2 <= 3 && $i2 <= $i0) {
            int $i3 = Integer.parseInt($r1.substring(0, $i2));
            if (this.countryCallingCodeToRegionCodeMap.containsKey(Integer.valueOf($i3))) {
                $r2.append($r1.substring($i2));
                return $i3;
            }
            $i2++;
        }
        return 0;
    }

    int maybeExtractCountryCode(String $r1, PhoneMetadata $r2, StringBuilder $r3, boolean $z0, PhoneNumber $r4) throws NumberParseException {
        if ($r1.length() == 0) {
            return 0;
        }
        StringBuilder $r5 = new StringBuilder($r1);
        $r1 = "NonMatch";
        if ($r2 != null) {
            $r1 = $r2.getInternationalPrefix();
        }
        CountryCodeSource $r7 = maybeStripInternationalPrefixAndNormalize($r5, $r1);
        if ($z0) {
            $r4.setCountryCodeSource($r7);
        }
        int $i0;
        if ($r7 == CountryCodeSource.FROM_DEFAULT_COUNTRY) {
            if ($r2 != null) {
                $i0 = $r2.getCountryCode();
                $r1 = String.valueOf($i0);
                String $r11 = $r5.toString();
                if ($r11.startsWith($r1)) {
                    StringBuilder $r6 = new StringBuilder($r11.substring($r1.length()));
                    PhoneNumberDesc $r12 = $r2.getGeneralDesc();
                    Pattern $r14 = this.regexCache.getPatternForRegex($r12.getNationalNumberPattern());
                    maybeStripNationalPrefixAndCarrierCode($r6, $r2, null);
                    if ((!$r14.matcher($r5).matches() && $r14.matcher($r6).matches()) || testNumberLength($r5.toString(), $r12) == ValidationResult.TOO_LONG) {
                        $r3.append($r6);
                        if ($z0) {
                            $r4.setCountryCodeSource(CountryCodeSource.FROM_NUMBER_WITHOUT_PLUS_SIGN);
                        }
                        $r4.setCountryCode($i0);
                        return $i0;
                    }
                }
            }
            $r4.setCountryCode(0);
            return 0;
        } else if ($r5.length() <= 2) {
            throw new NumberParseException(ErrorType.TOO_SHORT_AFTER_IDD, "Phone number had an IDD, but after this was not long enough to be a viable phone number.");
        } else {
            $i0 = extractCountryCode($r5, $r3);
            if ($i0 != 0) {
                $r4.setCountryCode($i0);
                return $i0;
            }
            throw new NumberParseException(ErrorType.INVALID_COUNTRY_CODE, "Country calling code supplied was not recognised.");
        }
    }

    private boolean parsePrefixAsIdd(Pattern $r1, StringBuilder $r2) throws  {
        Matcher $r3 = $r1.matcher($r2);
        if (!$r3.lookingAt()) {
            return false;
        }
        int $i0 = $r3.end();
        $r3 = CAPTURING_DIGIT_PATTERN.matcher($r2.substring($i0));
        if ($r3.find() && normalizeDigitsOnly($r3.group(1)).equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
            return false;
        }
        $r2.delete(0, $i0);
        return true;
    }

    CountryCodeSource maybeStripInternationalPrefixAndNormalize(StringBuilder $r1, String $r2) throws  {
        if ($r1.length() == 0) {
            return CountryCodeSource.FROM_DEFAULT_COUNTRY;
        }
        Matcher $r4 = PLUS_CHARS_PATTERN.matcher($r1);
        if ($r4.lookingAt()) {
            $r1.delete(0, $r4.end());
            normalize($r1);
            return CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN;
        }
        Pattern $r3 = this.regexCache.getPatternForRegex($r2);
        normalize($r1);
        return parsePrefixAsIdd($r3, $r1) ? CountryCodeSource.FROM_NUMBER_WITH_IDD : CountryCodeSource.FROM_DEFAULT_COUNTRY;
    }

    boolean maybeStripNationalPrefixAndCarrierCode(StringBuilder $r1, PhoneMetadata $r2, StringBuilder $r3) throws  {
        int $i0 = $r1.length();
        String $r5 = $r2.getNationalPrefixForParsing();
        if ($i0 == 0) {
            return false;
        }
        if ($r5.length() == 0) {
            return false;
        }
        Matcher $r8 = this.regexCache.getPatternForRegex($r5).matcher($r1);
        if (!$r8.lookingAt()) {
            return false;
        }
        Pattern $r7 = this.regexCache.getPatternForRegex($r2.getGeneralDesc().getNationalNumberPattern());
        boolean $z0 = $r7.matcher($r1).matches();
        int $i1 = $r8.groupCount();
        $r5 = $r2.getNationalPrefixTransformRule();
        if ($r5 != null && $r5.length() != 0 && $r8.group($i1) != null) {
            StringBuilder $r4 = new StringBuilder($r1);
            $r4.replace(0, $i0, $r8.replaceFirst($r5));
            if ($z0 && !$r7.matcher($r4.toString()).matches()) {
                return false;
            }
            if ($r3 != null && $i1 > 1) {
                $r3.append($r8.group(1));
            }
            $r1.replace(0, $r1.length(), $r4.toString());
            return true;
        } else if ($z0 && !$r7.matcher($r1.substring($r8.end())).matches()) {
            return false;
        } else {
            if (!($r3 == null || $i1 <= 0 || $r8.group($i1) == null)) {
                $r3.append($r8.group(1));
            }
            $r1.delete(0, $r8.end());
            return true;
        }
    }

    String maybeStripExtension(StringBuilder $r1) throws  {
        Matcher $r3 = EXTN_PATTERN.matcher($r1);
        if ($r3.find() && isViablePhoneNumber($r1.substring(0, $r3.start()))) {
            int $i1 = $r3.groupCount();
            for (int $i0 = 1; $i0 <= $i1; $i0++) {
                if ($r3.group($i0) != null) {
                    String $r4 = $r3.group($i0);
                    $r1.delete($r3.start(), $r1.length());
                    return $r4;
                }
            }
        }
        return "";
    }

    private boolean checkRegionForParsing(String $r1, String $r2) throws  {
        return isValidRegionCode($r2) || !($r1 == null || $r1.length() == 0 || !PLUS_CHARS_PATTERN.matcher($r1).lookingAt());
    }

    public PhoneNumber parse(String $r1, String $r2) throws NumberParseException {
        PhoneNumber $r3 = new PhoneNumber();
        parse($r1, $r2, $r3);
        return $r3;
    }

    public void parse(String $r1, String $r2, PhoneNumber $r3) throws NumberParseException {
        parseHelper($r1, $r2, false, true, $r3);
    }

    public PhoneNumber parseAndKeepRawInput(String $r1, String $r2) throws NumberParseException {
        PhoneNumber $r3 = new PhoneNumber();
        parseAndKeepRawInput($r1, $r2, $r3);
        return $r3;
    }

    public void parseAndKeepRawInput(String $r1, String $r2, PhoneNumber $r3) throws NumberParseException {
        parseHelper($r1, $r2, true, true, $r3);
    }

    public Iterable<PhoneNumberMatch> findNumbers(@Signature({"(", "Ljava/lang/CharSequence;", "Ljava/lang/String;", ")", "Ljava/lang/Iterable", "<", "Lcom/google/i18n/phonenumbers/PhoneNumberMatch;", ">;"}) CharSequence $r1, @Signature({"(", "Ljava/lang/CharSequence;", "Ljava/lang/String;", ")", "Ljava/lang/Iterable", "<", "Lcom/google/i18n/phonenumbers/PhoneNumberMatch;", ">;"}) String $r2) throws  {
        return findNumbers($r1, $r2, Leniency.VALID, Long.MAX_VALUE);
    }

    public Iterable<PhoneNumberMatch> findNumbers(@Signature({"(", "Ljava/lang/CharSequence;", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/PhoneNumberUtil$Leniency;", "J)", "Ljava/lang/Iterable", "<", "Lcom/google/i18n/phonenumbers/PhoneNumberMatch;", ">;"}) final CharSequence $r1, @Signature({"(", "Ljava/lang/CharSequence;", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/PhoneNumberUtil$Leniency;", "J)", "Ljava/lang/Iterable", "<", "Lcom/google/i18n/phonenumbers/PhoneNumberMatch;", ">;"}) final String $r2, @Signature({"(", "Ljava/lang/CharSequence;", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/PhoneNumberUtil$Leniency;", "J)", "Ljava/lang/Iterable", "<", "Lcom/google/i18n/phonenumbers/PhoneNumberMatch;", ">;"}) final Leniency $r3, @Signature({"(", "Ljava/lang/CharSequence;", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/PhoneNumberUtil$Leniency;", "J)", "Ljava/lang/Iterable", "<", "Lcom/google/i18n/phonenumbers/PhoneNumberMatch;", ">;"}) final long $l0) throws  {
        return new Iterable<PhoneNumberMatch>() {
            public Iterator<PhoneNumberMatch> iterator() throws  {
                return new PhoneNumberMatcher(PhoneNumberUtil.this, $r1, $r2, $r3, $l0);
            }
        };
    }

    static void setItalianLeadingZerosForPhoneNumber(String $r0, PhoneNumber $r1) throws  {
        if ($r0.length() > 1 && $r0.charAt(0) == '0') {
            $r1.setItalianLeadingZero(true);
            int $i0 = 1;
            while ($i0 < $r0.length() - 1 && $r0.charAt($i0) == '0') {
                $i0++;
            }
            if ($i0 != 1) {
                $r1.setNumberOfLeadingZeros($i0);
            }
        }
    }

    private void buildNationalNumberForParsing(String $r1, StringBuilder $r2) throws  {
        int $i1 = $r1.indexOf(RFC3966_PHONE_CONTEXT);
        if ($i1 > 0) {
            int $i0 = $i1 + RFC3966_PHONE_CONTEXT.length();
            if ($r1.charAt($i0) == PLUS_SIGN) {
                int $i3 = $r1.indexOf(59, $i0);
                if ($i3 > 0) {
                    $r2.append($r1.substring($i0, $i3));
                } else {
                    $r2.append($r1.substring($i0));
                }
            }
            $i0 = $r1.indexOf("tel:");
            $r2.append($r1.substring($i0 >= 0 ? $i0 + "tel:".length() : 0, $i1));
        } else {
            $r2.append(extractPossibleNumber($r1));
        }
        $i1 = $r2.indexOf(RFC3966_ISDN_SUBADDRESS);
        if ($i1 > 0) {
            $r2.delete($i1, $r2.length());
        }
    }

    public MatchType isNumberMatch(PhoneNumber $r1, PhoneNumber $r2) throws  {
        PhoneNumber $r3 = new PhoneNumber();
        $r3.mergeFrom($r1);
        $r1 = new PhoneNumber();
        $r1.mergeFrom($r2);
        $r3.clearRawInput();
        $r3.clearCountryCodeSource();
        $r3.clearPreferredDomesticCarrierCode();
        $r1.clearRawInput();
        $r1.clearCountryCodeSource();
        $r1.clearPreferredDomesticCarrierCode();
        if ($r3.hasExtension() && $r3.getExtension().length() == 0) {
            $r3.clearExtension();
        }
        if ($r1.hasExtension() && $r1.getExtension().length() == 0) {
            $r1.clearExtension();
        }
        if ($r3.hasExtension() && $r1.hasExtension() && !$r3.getExtension().equals($r1.getExtension())) {
            return MatchType.NO_MATCH;
        }
        int $i0 = $r3.getCountryCode();
        int $i1 = $r1.getCountryCode();
        if ($i0 == 0 || $i1 == 0) {
            $r3.setCountryCode($i1);
            if ($r3.exactlySameAs($r1)) {
                return MatchType.NSN_MATCH;
            }
            if (isNationalNumberSuffixOfTheOther($r3, $r1)) {
                return MatchType.SHORT_NSN_MATCH;
            }
            return MatchType.NO_MATCH;
        } else if ($r3.exactlySameAs($r1)) {
            return MatchType.EXACT_MATCH;
        } else {
            if ($i0 == $i1 && isNationalNumberSuffixOfTheOther($r3, $r1)) {
                return MatchType.SHORT_NSN_MATCH;
            }
            return MatchType.NO_MATCH;
        }
    }

    private boolean isNationalNumberSuffixOfTheOther(PhoneNumber $r1, PhoneNumber $r2) throws  {
        String $r3 = String.valueOf($r1.getNationalNumber());
        String $r4 = String.valueOf($r2.getNationalNumber());
        return $r3.endsWith($r4) || $r4.endsWith($r3);
    }

    public MatchType isNumberMatch(String $r1, String $r2) throws  {
        try {
            return isNumberMatch(parse($r1, UNKNOWN_REGION), $r2);
        } catch (NumberParseException $r5) {
            if ($r5.getErrorType() == ErrorType.INVALID_COUNTRY_CODE) {
                try {
                    return isNumberMatch(parse($r2, UNKNOWN_REGION), $r1);
                } catch (NumberParseException $r6) {
                    if ($r6.getErrorType() == ErrorType.INVALID_COUNTRY_CODE) {
                        try {
                            PhoneNumber $r3 = new PhoneNumber();
                            PhoneNumber $r4 = new PhoneNumber();
                            parseHelper($r1, null, false, false, $r3);
                            parseHelper($r2, null, false, false, $r4);
                            return isNumberMatch($r3, $r4);
                        } catch (NumberParseException e) {
                            return MatchType.NOT_A_NUMBER;
                        }
                    }
                    return MatchType.NOT_A_NUMBER;
                }
            }
            return MatchType.NOT_A_NUMBER;
        }
    }

    public MatchType isNumberMatch(PhoneNumber $r1, String $r2) throws  {
        try {
            return isNumberMatch($r1, parse($r2, UNKNOWN_REGION));
        } catch (NumberParseException $r4) {
            if ($r4.getErrorType() == ErrorType.INVALID_COUNTRY_CODE) {
                String $r8 = getRegionCodeForCountryCode($r1.getCountryCode());
                try {
                    if ($r8.equals(UNKNOWN_REGION)) {
                        PhoneNumber $r3 = new PhoneNumber();
                        parseHelper($r2, null, false, false, $r3);
                        return isNumberMatch($r1, $r3);
                    }
                    MatchType $r5 = isNumberMatch($r1, parse($r2, $r8));
                    if ($r5 == MatchType.EXACT_MATCH) {
                        return MatchType.NSN_MATCH;
                    }
                    return $r5;
                } catch (NumberParseException e) {
                    return MatchType.NOT_A_NUMBER;
                }
            }
            return MatchType.NOT_A_NUMBER;
        }
    }

    boolean canBeInternationallyDialled(PhoneNumber $r1) throws  {
        PhoneMetadata $r3 = getMetadataForRegion(getRegionCodeForNumber($r1));
        if ($r3 == null) {
            return true;
        }
        return !isNumberMatchingDesc(getNationalSignificantNumber($r1), $r3.getNoInternationalDialling());
    }

    public boolean isMobileNumberPortableRegion(String $r1) throws  {
        PhoneMetadata $r3 = getMetadataForRegion($r1);
        if ($r3 != null) {
            return $r3.isMobileNumberPortableRegion();
        }
        logger.log(Level.WARNING, "Invalid or unknown region code provided: " + $r1);
        return false;
    }
}
