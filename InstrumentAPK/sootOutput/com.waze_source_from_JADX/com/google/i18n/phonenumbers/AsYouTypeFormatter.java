package com.google.i18n.phonenumbers;

import com.google.i18n.phonenumbers.Phonemetadata.NumberFormat;
import com.google.i18n.phonenumbers.Phonemetadata.PhoneMetadata;
import com.waze.analytics.AnalyticsEvents;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AsYouTypeFormatter {
    private static final Pattern CHARACTER_CLASS_PATTERN = Pattern.compile("\\[([^\\[\\]])*\\]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(DIGIT_PLACEHOLDER);
    private static final String DIGIT_PLACEHOLDER = " ";
    private static final Pattern ELIGIBLE_FORMAT_PATTERN = Pattern.compile("[-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～]*(\\$\\d[-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～]*)+");
    private static final PhoneMetadata EMPTY_METADATA = new PhoneMetadata().setInternationalPrefix(AnalyticsEvents.ANALYTICS_EVENT_NETWORK_MODE_NA);
    private static final int MIN_LEADING_DIGITS_LENGTH = 3;
    private static final Pattern NATIONAL_PREFIX_SEPARATORS_PATTERN = Pattern.compile("[- ]");
    private static final char SEPARATOR_BEFORE_NATIONAL_NUMBER = ' ';
    private static final Pattern STANDALONE_DIGIT_PATTERN = Pattern.compile("\\d(?=[^,}][^,}])");
    private boolean ableToFormat = true;
    private StringBuilder accruedInput = new StringBuilder();
    private StringBuilder accruedInputWithoutFormatting = new StringBuilder();
    private String currentFormattingPattern = "";
    private PhoneMetadata currentMetadata;
    private String currentOutput = "";
    private String defaultCountry;
    private PhoneMetadata defaultMetadata;
    private String extractedNationalPrefix = "";
    private StringBuilder formattingTemplate = new StringBuilder();
    private boolean inputHasFormatting = false;
    private boolean isCompleteNumber = false;
    private boolean isExpectingCountryCallingCode = false;
    private int lastMatchPosition = 0;
    private StringBuilder nationalNumber = new StringBuilder();
    private int originalPosition = 0;
    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    private int positionToRemember = 0;
    private List<NumberFormat> possibleFormats = new ArrayList();
    private StringBuilder prefixBeforeNationalNumber = new StringBuilder();
    private RegexCache regexCache = new RegexCache(64);
    private boolean shouldAddSpaceAfterNationalPrefix = false;

    AsYouTypeFormatter(String $r1) throws  {
        this.defaultCountry = $r1;
        this.currentMetadata = getMetadataForRegion(this.defaultCountry);
        this.defaultMetadata = this.currentMetadata;
    }

    private PhoneMetadata getMetadataForRegion(String $r1) throws  {
        PhoneMetadata $r3 = this.phoneUtil.getMetadataForRegion(this.phoneUtil.getRegionCodeForCountryCode(this.phoneUtil.getCountryCodeForRegion($r1)));
        return $r3 != null ? $r3 : EMPTY_METADATA;
    }

    private boolean maybeCreateNewTemplate() throws  {
        Iterator $r2 = this.possibleFormats.iterator();
        while ($r2.hasNext()) {
            NumberFormat $r4 = (NumberFormat) $r2.next();
            String $r5 = $r4.getPattern();
            if (this.currentFormattingPattern.equals($r5)) {
                return false;
            }
            if (createFormattingTemplate($r4)) {
                this.currentFormattingPattern = $r5;
                this.shouldAddSpaceAfterNationalPrefix = NATIONAL_PREFIX_SEPARATORS_PATTERN.matcher($r4.getNationalPrefixFormattingRule()).find();
                this.lastMatchPosition = 0;
                return true;
            }
            $r2.remove();
        }
        this.ableToFormat = false;
        return false;
    }

    private void getAvailableFormats(String $r1) throws  {
        List $r3;
        if (!this.isCompleteNumber || this.currentMetadata.intlNumberFormatSize() <= 0) {
            $r3 = this.currentMetadata.numberFormats();
        } else {
            $r3 = this.currentMetadata.intlNumberFormats();
        }
        boolean $z0 = this.currentMetadata.hasNationalPrefix();
        for (NumberFormat $r6 : $r3) {
            if ((!$z0 || this.isCompleteNumber || $r6.isNationalPrefixOptionalWhenFormatting() || PhoneNumberUtil.formattingRuleHasFirstGroupOnly($r6.getNationalPrefixFormattingRule())) && isFormatEligible($r6.getFormat())) {
                this.possibleFormats.add($r6);
            }
        }
        narrowDownPossibleFormats($r1);
    }

    private boolean isFormatEligible(String $r1) throws  {
        return ELIGIBLE_FORMAT_PATTERN.matcher($r1).matches();
    }

    private void narrowDownPossibleFormats(String $r1) throws  {
        int $i0 = $r1.length() - 3;
        Iterator $r3 = this.possibleFormats.iterator();
        while ($r3.hasNext()) {
            NumberFormat $r5 = (NumberFormat) $r3.next();
            if ($r5.leadingDigitsPatternSize() != 0) {
                if (!this.regexCache.getPatternForRegex($r5.getLeadingDigitsPattern(Math.min($i0, $r5.leadingDigitsPatternSize() - 1))).matcher($r1).lookingAt()) {
                    $r3.remove();
                }
            }
        }
    }

    private boolean createFormattingTemplate(NumberFormat $r1) throws  {
        String $r2 = $r1.getPattern();
        if ($r2.indexOf(124) != -1) {
            return false;
        }
        $r2 = STANDALONE_DIGIT_PATTERN.matcher(CHARACTER_CLASS_PATTERN.matcher($r2).replaceAll("\\\\d")).replaceAll("\\\\d");
        this.formattingTemplate.setLength(0);
        $r2 = getFormattingTemplate($r2, $r1.getFormat());
        if ($r2.length() <= 0) {
            return false;
        }
        this.formattingTemplate.append($r2);
        return true;
    }

    private String getFormattingTemplate(String $r1, String $r2) throws  {
        Matcher $r5 = this.regexCache.getPatternForRegex($r1).matcher("999999999999999");
        $r5.find();
        String $r6 = $r5.group();
        if ($r6.length() < this.nationalNumber.length()) {
            return "";
        }
        return $r6.replaceAll($r1, $r2).replaceAll("9", DIGIT_PLACEHOLDER);
    }

    public void clear() throws  {
        this.currentOutput = "";
        this.accruedInput.setLength(0);
        this.accruedInputWithoutFormatting.setLength(0);
        this.formattingTemplate.setLength(0);
        this.lastMatchPosition = 0;
        this.currentFormattingPattern = "";
        this.prefixBeforeNationalNumber.setLength(0);
        this.extractedNationalPrefix = "";
        this.nationalNumber.setLength(0);
        this.ableToFormat = true;
        this.inputHasFormatting = false;
        this.positionToRemember = 0;
        this.originalPosition = 0;
        this.isCompleteNumber = false;
        this.isExpectingCountryCallingCode = false;
        this.possibleFormats.clear();
        this.shouldAddSpaceAfterNationalPrefix = false;
        if (!this.currentMetadata.equals(this.defaultMetadata)) {
            this.currentMetadata = getMetadataForRegion(this.defaultCountry);
        }
    }

    public String inputDigit(char $c0) throws  {
        this.currentOutput = inputDigitWithOptionToRememberPosition($c0, false);
        return this.currentOutput;
    }

    public String inputDigitAndRememberPosition(char $c0) throws  {
        this.currentOutput = inputDigitWithOptionToRememberPosition($c0, true);
        return this.currentOutput;
    }

    private String inputDigitWithOptionToRememberPosition(char $c0, boolean $z0) throws  {
        this.accruedInput.append($c0);
        if ($z0) {
            this.originalPosition = this.accruedInput.length();
        }
        if (isDigitOrLeadingPlusSign($c0)) {
            $c0 = normalizeAndAccrueDigitsAndPlusSign($c0, $z0);
        } else {
            this.ableToFormat = false;
            this.inputHasFormatting = true;
        }
        if (this.ableToFormat) {
            switch (this.accruedInputWithoutFormatting.length()) {
                case 0:
                case 1:
                case 2:
                    return this.accruedInput.toString();
                case 3:
                    if (attemptToExtractIdd()) {
                        this.isExpectingCountryCallingCode = true;
                        break;
                    }
                    this.extractedNationalPrefix = removeNationalPrefixFromNationalNumber();
                    return attemptToChooseFormattingPattern();
                default:
                    break;
            }
            if (this.isExpectingCountryCallingCode) {
                if (attemptToExtractCountryCallingCode()) {
                    this.isExpectingCountryCallingCode = false;
                }
                return this.prefixBeforeNationalNumber + this.nationalNumber.toString();
            } else if (this.possibleFormats.size() <= 0) {
                return attemptToChooseFormattingPattern();
            } else {
                String $r2 = inputDigitHelper($c0);
                String $r5 = attemptToFormatAccruedDigits();
                if ($r5.length() > 0) {
                    return $r5;
                }
                narrowDownPossibleFormats(this.nationalNumber.toString());
                if (maybeCreateNewTemplate()) {
                    return inputAccruedNationalNumber();
                }
                if (this.ableToFormat) {
                    $r2 = appendNationalNumber($r2);
                } else {
                    $r2 = this.accruedInput.toString();
                }
                return $r2;
            }
        } else if (this.inputHasFormatting) {
            return this.accruedInput.toString();
        } else {
            if (attemptToExtractIdd()) {
                if (attemptToExtractCountryCallingCode()) {
                    return attemptToChoosePatternWithPrefixExtracted();
                }
            } else if (ableToExtractLongerNdd()) {
                this.prefixBeforeNationalNumber.append(SEPARATOR_BEFORE_NATIONAL_NUMBER);
                return attemptToChoosePatternWithPrefixExtracted();
            }
            return this.accruedInput.toString();
        }
    }

    private String attemptToChoosePatternWithPrefixExtracted() throws  {
        this.ableToFormat = true;
        this.isExpectingCountryCallingCode = false;
        this.possibleFormats.clear();
        this.lastMatchPosition = 0;
        this.formattingTemplate.setLength(0);
        this.currentFormattingPattern = "";
        return attemptToChooseFormattingPattern();
    }

    String getExtractedNationalPrefix() throws  {
        return this.extractedNationalPrefix;
    }

    private boolean ableToExtractLongerNdd() throws  {
        if (this.extractedNationalPrefix.length() > 0) {
            this.nationalNumber.insert(0, this.extractedNationalPrefix);
            this.prefixBeforeNationalNumber.setLength(this.prefixBeforeNationalNumber.lastIndexOf(this.extractedNationalPrefix));
        }
        return !this.extractedNationalPrefix.equals(removeNationalPrefixFromNationalNumber());
    }

    private boolean isDigitOrLeadingPlusSign(char $c0) throws  {
        if (Character.isDigit($c0)) {
            return true;
        }
        return this.accruedInput.length() == 1 && PhoneNumberUtil.PLUS_CHARS_PATTERN.matcher(Character.toString($c0)).matches();
    }

    String attemptToFormatAccruedDigits() throws  {
        for (NumberFormat $r4 : this.possibleFormats) {
            Matcher $r9 = this.regexCache.getPatternForRegex($r4.getPattern()).matcher(this.nationalNumber);
            if ($r9.matches()) {
                this.shouldAddSpaceAfterNationalPrefix = NATIONAL_PREFIX_SEPARATORS_PATTERN.matcher($r4.getNationalPrefixFormattingRule()).find();
                return appendNationalNumber($r9.replaceAll($r4.getFormat()));
            }
        }
        return "";
    }

    public int getRememberedPosition() throws  {
        if (!this.ableToFormat) {
            return this.originalPosition;
        }
        int $i1 = 0;
        int $i0 = 0;
        while ($i1 < this.positionToRemember && $i0 < this.currentOutput.length()) {
            if (this.accruedInputWithoutFormatting.charAt($i1) == this.currentOutput.charAt($i0)) {
                $i1++;
            }
            $i0++;
        }
        return $i0;
    }

    private String appendNationalNumber(String $r1) throws  {
        int $i0 = this.prefixBeforeNationalNumber.length();
        if (!this.shouldAddSpaceAfterNationalPrefix || $i0 <= 0 || this.prefixBeforeNationalNumber.charAt($i0 - 1) == SEPARATOR_BEFORE_NATIONAL_NUMBER) {
            return this.prefixBeforeNationalNumber + $r1;
        }
        return new String(this.prefixBeforeNationalNumber) + SEPARATOR_BEFORE_NATIONAL_NUMBER + $r1;
    }

    private String attemptToChooseFormattingPattern() throws  {
        if (this.nationalNumber.length() < 3) {
            return appendNationalNumber(this.nationalNumber.toString());
        }
        getAvailableFormats(this.nationalNumber.toString());
        String $r2 = attemptToFormatAccruedDigits();
        if ($r2.length() > 0) {
            return $r2;
        }
        return maybeCreateNewTemplate() ? inputAccruedNationalNumber() : this.accruedInput.toString();
    }

    private String inputAccruedNationalNumber() throws  {
        int $i0 = this.nationalNumber.length();
        if ($i0 <= 0) {
            return this.prefixBeforeNationalNumber.toString();
        }
        String $r2 = "";
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r2 = inputDigitHelper(this.nationalNumber.charAt($i1));
        }
        return this.ableToFormat ? appendNationalNumber($r2) : this.accruedInput.toString();
    }

    private boolean isNanpaNumberWithNationalPrefix() throws  {
        return this.currentMetadata.getCountryCode() == 1 && this.nationalNumber.charAt(0) == '1' && this.nationalNumber.charAt(1) != '0' && this.nationalNumber.charAt(1) != '1';
    }

    private String removeNationalPrefixFromNationalNumber() throws  {
        int $i0 = 0;
        if (isNanpaNumberWithNationalPrefix()) {
            $i0 = 1;
            this.prefixBeforeNationalNumber.append('1').append(SEPARATOR_BEFORE_NATIONAL_NUMBER);
            this.isCompleteNumber = true;
        } else if (this.currentMetadata.hasNationalPrefixForParsing()) {
            Matcher $r6 = this.regexCache.getPatternForRegex(this.currentMetadata.getNationalPrefixForParsing()).matcher(this.nationalNumber);
            if ($r6.lookingAt() && $r6.end() > 0) {
                this.isCompleteNumber = true;
                int $i1 = $r6.end();
                $i0 = $i1;
                this.prefixBeforeNationalNumber.append(this.nationalNumber.substring(0, $i1));
            }
        }
        String $r2 = this.nationalNumber.substring(0, $i0);
        this.nationalNumber.delete(0, $i0);
        return $r2;
    }

    private boolean attemptToExtractIdd() throws  {
        Matcher $r6 = this.regexCache.getPatternForRegex("\\+|" + this.currentMetadata.getInternationalPrefix()).matcher(this.accruedInputWithoutFormatting);
        if (!$r6.lookingAt()) {
            return false;
        }
        this.isCompleteNumber = true;
        int $i0 = $r6.end();
        this.nationalNumber.setLength(0);
        this.nationalNumber.append(this.accruedInputWithoutFormatting.substring($i0));
        this.prefixBeforeNationalNumber.setLength(0);
        this.prefixBeforeNationalNumber.append(this.accruedInputWithoutFormatting.substring(0, $i0));
        if (this.accruedInputWithoutFormatting.charAt(0) == '+') {
            return true;
        }
        this.prefixBeforeNationalNumber.append(SEPARATOR_BEFORE_NATIONAL_NUMBER);
        return true;
    }

    private boolean attemptToExtractCountryCallingCode() throws  {
        if (this.nationalNumber.length() == 0) {
            return false;
        }
        StringBuilder $r1 = new StringBuilder();
        int $i0 = this.phoneUtil.extractCountryCode(this.nationalNumber, $r1);
        if ($i0 == 0) {
            return false;
        }
        this.nationalNumber.setLength(0);
        this.nationalNumber.append($r1);
        String $r4 = this.phoneUtil.getRegionCodeForCountryCode($i0);
        if (PhoneNumberUtil.REGION_CODE_FOR_NON_GEO_ENTITY.equals($r4)) {
            this.currentMetadata = this.phoneUtil.getMetadataForNonGeographicalRegion($i0);
        } else if (!$r4.equals(this.defaultCountry)) {
            this.currentMetadata = getMetadataForRegion($r4);
        }
        this.prefixBeforeNationalNumber.append(Integer.toString($i0)).append(SEPARATOR_BEFORE_NATIONAL_NUMBER);
        this.extractedNationalPrefix = "";
        return true;
    }

    private char normalizeAndAccrueDigitsAndPlusSign(char $c0, boolean $z0) throws  {
        char $c1;
        if ($c0 == '+') {
            $c1 = $c0;
            this.accruedInputWithoutFormatting.append($c0);
        } else {
            $c0 = Character.forDigit(Character.digit($c0, 10), 10);
            $c1 = $c0;
            this.accruedInputWithoutFormatting.append($c0);
            this.nationalNumber.append($c0);
        }
        if (!$z0) {
            return $c1;
        }
        this.positionToRemember = this.accruedInputWithoutFormatting.length();
        return $c1;
    }

    private String inputDigitHelper(char $c0) throws  {
        Matcher $r3 = DIGIT_PATTERN.matcher(this.formattingTemplate);
        if ($r3.find(this.lastMatchPosition)) {
            String $r4 = $r3.replaceFirst(Character.toString($c0));
            this.formattingTemplate.replace(0, $r4.length(), $r4);
            this.lastMatchPosition = $r3.start();
            return this.formattingTemplate.substring(0, this.lastMatchPosition + 1);
        }
        if (this.possibleFormats.size() == 1) {
            this.ableToFormat = false;
        }
        this.currentFormattingPattern = "";
        return this.accruedInput.toString();
    }
}
