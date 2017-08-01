package com.google.i18n.phonenumbers;

import com.google.i18n.phonenumbers.Phonemetadata.PhoneMetadata;
import com.google.i18n.phonenumbers.Phonemetadata.PhoneNumberDesc;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.internal.MatcherApi;
import com.google.i18n.phonenumbers.internal.RegexBasedMatcher;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShortNumberInfo {
    private static final ShortNumberInfo INSTANCE = new ShortNumberInfo(RegexBasedMatcher.create());
    private static final Set<String> REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT = new HashSet();
    private static final Logger logger = Logger.getLogger(ShortNumberInfo.class.getName());
    private final Map<Integer, List<String>> countryCallingCodeToRegionCodeMap = CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap();
    private final MatcherApi matcherApi;

    public enum ShortNumberCost {
        TOLL_FREE,
        STANDARD_RATE,
        PREMIUM_RATE,
        UNKNOWN_COST
    }

    static {
        REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT.add("BR");
        REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT.add("CL");
        REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT.add("NI");
    }

    public static ShortNumberInfo getInstance() throws  {
        return INSTANCE;
    }

    ShortNumberInfo(MatcherApi $r1) throws  {
        this.matcherApi = $r1;
    }

    private List<String> getRegionCodesForCountryCode(@Signature({"(I)", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) int $i0) throws  {
        List $r4 = (List) this.countryCallingCodeToRegionCodeMap.get(Integer.valueOf($i0));
        if ($r4 == null) {
            $r4 = r5;
            ArrayList r5 = new ArrayList(0);
        }
        return Collections.unmodifiableList($r4);
    }

    private boolean regionDialingFromMatchesNumber(PhoneNumber $r1, String $r2) throws  {
        return getRegionCodesForCountryCode($r1.getCountryCode()).contains($r2);
    }

    public boolean isPossibleShortNumberForRegion(PhoneNumber $r1, String $r2) throws  {
        if (!regionDialingFromMatchesNumber($r1, $r2)) {
            return false;
        }
        PhoneMetadata $r3 = MetadataManager.getShortNumberMetadataForRegion($r2);
        if ($r3 == null) {
            return false;
        }
        return $r3.getGeneralDesc().getPossibleLengthList().contains(Integer.valueOf(getNationalSignificantNumber($r1).length()));
    }

    public boolean isPossibleShortNumber(PhoneNumber $r1) throws  {
        List<String> $r2 = getRegionCodesForCountryCode($r1.getCountryCode());
        int $i0 = getNationalSignificantNumber($r1).length();
        for (String shortNumberMetadataForRegion : $r2) {
            PhoneMetadata $r6 = MetadataManager.getShortNumberMetadataForRegion(shortNumberMetadataForRegion);
            if ($r6 != null && $r6.getGeneralDesc().getPossibleLengthList().contains(Integer.valueOf($i0))) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidShortNumberForRegion(PhoneNumber $r1, String $r2) throws  {
        if (!regionDialingFromMatchesNumber($r1, $r2)) {
            return false;
        }
        PhoneMetadata $r3 = MetadataManager.getShortNumberMetadataForRegion($r2);
        if ($r3 == null) {
            return false;
        }
        $r2 = getNationalSignificantNumber($r1);
        return matchesPossibleNumberAndNationalNumber($r2, $r3.getGeneralDesc()) ? matchesPossibleNumberAndNationalNumber($r2, $r3.getShortCode()) : false;
    }

    public boolean isValidShortNumber(PhoneNumber $r1) throws  {
        List $r2 = getRegionCodesForCountryCode($r1.getCountryCode());
        String $r3 = getRegionCodeForShortNumberFromRegionList($r1, $r2);
        return ($r2.size() <= 1 || $r3 == null) ? isValidShortNumberForRegion($r1, $r3) : true;
    }

    public ShortNumberCost getExpectedCostForRegion(PhoneNumber $r1, String $r2) throws  {
        if (!regionDialingFromMatchesNumber($r1, $r2)) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        PhoneMetadata $r3 = MetadataManager.getShortNumberMetadataForRegion($r2);
        if ($r3 == null) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        String $r4 = getNationalSignificantNumber($r1);
        if (!$r3.getGeneralDesc().getPossibleLengthList().contains(Integer.valueOf($r4.length()))) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        if (matchesPossibleNumberAndNationalNumber($r4, $r3.getPremiumRate())) {
            return ShortNumberCost.PREMIUM_RATE;
        }
        if (matchesPossibleNumberAndNationalNumber($r4, $r3.getStandardRate())) {
            return ShortNumberCost.STANDARD_RATE;
        }
        if (matchesPossibleNumberAndNationalNumber($r4, $r3.getTollFree())) {
            return ShortNumberCost.TOLL_FREE;
        }
        if (isEmergencyNumber($r4, $r2)) {
            return ShortNumberCost.TOLL_FREE;
        }
        return ShortNumberCost.UNKNOWN_COST;
    }

    public ShortNumberCost getExpectedCost(PhoneNumber $r1) throws  {
        List<String> $r2 = getRegionCodesForCountryCode($r1.getCountryCode());
        if ($r2.size() == 0) {
            return ShortNumberCost.UNKNOWN_COST;
        }
        if ($r2.size() == 1) {
            return getExpectedCostForRegion($r1, (String) $r2.get(0));
        }
        ShortNumberCost $r3 = ShortNumberCost.TOLL_FREE;
        for (String expectedCostForRegion : $r2) {
            ShortNumberCost $r7 = getExpectedCostForRegion($r1, expectedCostForRegion);
            switch ($r7) {
                case PREMIUM_RATE:
                    return ShortNumberCost.PREMIUM_RATE;
                case UNKNOWN_COST:
                    $r3 = ShortNumberCost.UNKNOWN_COST;
                    break;
                case STANDARD_RATE:
                    if ($r3 == ShortNumberCost.UNKNOWN_COST) {
                        break;
                    }
                    $r3 = ShortNumberCost.STANDARD_RATE;
                    break;
                case TOLL_FREE:
                    break;
                default:
                    logger.log(Level.SEVERE, "Unrecognised cost for region: " + $r7);
                    break;
            }
        }
        return $r3;
    }

    private String getRegionCodeForShortNumberFromRegionList(@Signature({"(", "Lcom/google/i18n/phonenumbers/Phonenumber$PhoneNumber;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Ljava/lang/String;"}) PhoneNumber $r1, @Signature({"(", "Lcom/google/i18n/phonenumbers/Phonenumber$PhoneNumber;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Ljava/lang/String;"}) List<String> $r2) throws  {
        if ($r2.size() == 0) {
            return null;
        }
        if ($r2.size() == 1) {
            return (String) $r2.get(0);
        }
        String $r3 = getNationalSignificantNumber($r1);
        for (String $r6 : $r2) {
            PhoneMetadata $r7 = MetadataManager.getShortNumberMetadataForRegion($r6);
            if ($r7 != null && matchesPossibleNumberAndNationalNumber($r3, $r7.getShortCode())) {
                return $r6;
            }
        }
        return null;
    }

    Set<String> getSupportedRegions() throws  {
        return MetadataManager.getSupportedShortNumberRegions();
    }

    String getExampleShortNumber(String $r1) throws  {
        PhoneMetadata $r2 = MetadataManager.getShortNumberMetadataForRegion($r1);
        if ($r2 == null) {
            return "";
        }
        PhoneNumberDesc $r3 = $r2.getShortCode();
        return $r3.hasExampleNumber() ? $r3.getExampleNumber() : "";
    }

    String getExampleShortNumberForCost(String $r1, ShortNumberCost $r2) throws  {
        PhoneMetadata $r3 = MetadataManager.getShortNumberMetadataForRegion($r1);
        if ($r3 == null) {
            return "";
        }
        PhoneNumberDesc $r4 = null;
        switch ($r2) {
            case PREMIUM_RATE:
                $r4 = $r3.getPremiumRate();
                break;
            case UNKNOWN_COST:
                break;
            case STANDARD_RATE:
                $r4 = $r3.getStandardRate();
                break;
            case TOLL_FREE:
                $r4 = $r3.getTollFree();
                break;
            default:
                break;
        }
        if ($r4 == null || !$r4.hasExampleNumber()) {
            return "";
        }
        return $r4.getExampleNumber();
    }

    public boolean connectsToEmergencyNumber(String $r1, String $r2) throws  {
        return matchesEmergencyNumberHelper($r1, $r2, true);
    }

    public boolean isEmergencyNumber(String $r1, String $r2) throws  {
        return matchesEmergencyNumberHelper($r1, $r2, false);
    }

    private boolean matchesEmergencyNumberHelper(String $r2, String $r1, boolean $z0) throws  {
        boolean $z1 = false;
        $r2 = PhoneNumberUtil.extractPossibleNumber($r2);
        if (PhoneNumberUtil.PLUS_CHARS_PATTERN.matcher($r2).lookingAt()) {
            return false;
        }
        PhoneMetadata $r5 = MetadataManager.getShortNumberMetadataForRegion($r1);
        if ($r5 == null) {
            return false;
        }
        if (!$r5.hasEmergency()) {
            return false;
        }
        $r2 = PhoneNumberUtil.normalizeDigitsOnly($r2);
        PhoneNumberDesc $r6 = $r5.getEmergency();
        if ($z0 && !REGIONS_WHERE_EMERGENCY_NUMBERS_MUST_BE_EXACT.contains($r1)) {
            $z1 = true;
        }
        return this.matcherApi.matchesNationalNumber($r2, $r6, $z1);
    }

    public boolean isCarrierSpecific(PhoneNumber $r1) throws  {
        String $r3 = getRegionCodeForShortNumberFromRegionList($r1, getRegionCodesForCountryCode($r1.getCountryCode()));
        String $r4 = getNationalSignificantNumber($r1);
        PhoneMetadata $r5 = MetadataManager.getShortNumberMetadataForRegion($r3);
        return $r5 != null && matchesPossibleNumberAndNationalNumber($r4, $r5.getCarrierSpecific());
    }

    private static String getNationalSignificantNumber(PhoneNumber $r0) throws  {
        StringBuilder $r1 = new StringBuilder();
        if ($r0.isItalianLeadingZero()) {
            char[] $r2 = new char[$r0.getNumberOfLeadingZeros()];
            Arrays.fill($r2, '0');
            $r1.append(new String($r2));
        }
        $r1.append($r0.getNationalNumber());
        return $r1.toString();
    }

    private boolean matchesPossibleNumberAndNationalNumber(String $r1, PhoneNumberDesc $r2) throws  {
        return ($r2.getPossibleLengthCount() <= 0 || $r2.getPossibleLengthList().contains(Integer.valueOf($r1.length()))) ? this.matcherApi.matchesNationalNumber($r1, $r2, false) : false;
    }
}
