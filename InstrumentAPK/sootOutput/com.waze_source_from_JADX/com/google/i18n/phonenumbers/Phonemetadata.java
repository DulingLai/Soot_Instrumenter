package com.google.i18n.phonenumbers;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

public final class Phonemetadata {

    public static class NumberFormat implements Externalizable {
        private static final long serialVersionUID = 1;
        private String domesticCarrierCodeFormattingRule_ = "";
        private String format_ = "";
        private boolean hasDomesticCarrierCodeFormattingRule;
        private boolean hasFormat;
        private boolean hasNationalPrefixFormattingRule;
        private boolean hasNationalPrefixOptionalWhenFormatting;
        private boolean hasPattern;
        private List<String> leadingDigitsPattern_ = new ArrayList();
        private String nationalPrefixFormattingRule_ = "";
        private boolean nationalPrefixOptionalWhenFormatting_ = false;
        private String pattern_ = "";

        public static final class Builder extends NumberFormat {
            public NumberFormat build() throws  {
                return this;
            }

            public Builder mergeFrom(NumberFormat $r1) throws  {
                if ($r1.hasPattern()) {
                    setPattern($r1.getPattern());
                }
                if ($r1.hasFormat()) {
                    setFormat($r1.getFormat());
                }
                for (int $i0 = 0; $i0 < $r1.leadingDigitsPatternSize(); $i0++) {
                    addLeadingDigitsPattern($r1.getLeadingDigitsPattern($i0));
                }
                if ($r1.hasNationalPrefixFormattingRule()) {
                    setNationalPrefixFormattingRule($r1.getNationalPrefixFormattingRule());
                }
                if ($r1.hasDomesticCarrierCodeFormattingRule()) {
                    setDomesticCarrierCodeFormattingRule($r1.getDomesticCarrierCodeFormattingRule());
                }
                if (!$r1.hasNationalPrefixOptionalWhenFormatting()) {
                    return this;
                }
                setNationalPrefixOptionalWhenFormatting($r1.isNationalPrefixOptionalWhenFormatting());
                return this;
            }
        }

        public static Builder newBuilder() throws  {
            return new Builder();
        }

        public boolean hasPattern() throws  {
            return this.hasPattern;
        }

        public String getPattern() throws  {
            return this.pattern_;
        }

        public NumberFormat setPattern(String $r1) throws  {
            this.hasPattern = true;
            this.pattern_ = $r1;
            return this;
        }

        public boolean hasFormat() throws  {
            return this.hasFormat;
        }

        public String getFormat() throws  {
            return this.format_;
        }

        public NumberFormat setFormat(String $r1) throws  {
            this.hasFormat = true;
            this.format_ = $r1;
            return this;
        }

        public List<String> leadingDigitPatterns() throws  {
            return this.leadingDigitsPattern_;
        }

        public int leadingDigitsPatternSize() throws  {
            return this.leadingDigitsPattern_.size();
        }

        public String getLeadingDigitsPattern(int $i0) throws  {
            return (String) this.leadingDigitsPattern_.get($i0);
        }

        public NumberFormat addLeadingDigitsPattern(String $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.leadingDigitsPattern_.add($r1);
            return this;
        }

        public boolean hasNationalPrefixFormattingRule() throws  {
            return this.hasNationalPrefixFormattingRule;
        }

        public String getNationalPrefixFormattingRule() throws  {
            return this.nationalPrefixFormattingRule_;
        }

        public NumberFormat setNationalPrefixFormattingRule(String $r1) throws  {
            this.hasNationalPrefixFormattingRule = true;
            this.nationalPrefixFormattingRule_ = $r1;
            return this;
        }

        public NumberFormat clearNationalPrefixFormattingRule() throws  {
            this.hasNationalPrefixFormattingRule = false;
            this.nationalPrefixFormattingRule_ = "";
            return this;
        }

        public boolean hasNationalPrefixOptionalWhenFormatting() throws  {
            return this.hasNationalPrefixOptionalWhenFormatting;
        }

        public boolean isNationalPrefixOptionalWhenFormatting() throws  {
            return this.nationalPrefixOptionalWhenFormatting_;
        }

        public NumberFormat setNationalPrefixOptionalWhenFormatting(boolean $z0) throws  {
            this.hasNationalPrefixOptionalWhenFormatting = true;
            this.nationalPrefixOptionalWhenFormatting_ = $z0;
            return this;
        }

        public boolean hasDomesticCarrierCodeFormattingRule() throws  {
            return this.hasDomesticCarrierCodeFormattingRule;
        }

        public String getDomesticCarrierCodeFormattingRule() throws  {
            return this.domesticCarrierCodeFormattingRule_;
        }

        public NumberFormat setDomesticCarrierCodeFormattingRule(String $r1) throws  {
            this.hasDomesticCarrierCodeFormattingRule = true;
            this.domesticCarrierCodeFormattingRule_ = $r1;
            return this;
        }

        public void writeExternal(ObjectOutput $r1) throws IOException {
            $r1.writeUTF(this.pattern_);
            $r1.writeUTF(this.format_);
            int $i0 = leadingDigitsPatternSize();
            $r1.writeInt($i0);
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                $r1.writeUTF((String) this.leadingDigitsPattern_.get($i1));
            }
            $r1.writeBoolean(this.hasNationalPrefixFormattingRule);
            if (this.hasNationalPrefixFormattingRule) {
                $r1.writeUTF(this.nationalPrefixFormattingRule_);
            }
            $r1.writeBoolean(this.hasDomesticCarrierCodeFormattingRule);
            if (this.hasDomesticCarrierCodeFormattingRule) {
                $r1.writeUTF(this.domesticCarrierCodeFormattingRule_);
            }
            $r1.writeBoolean(this.nationalPrefixOptionalWhenFormatting_);
        }

        public void readExternal(ObjectInput $r1) throws IOException {
            setPattern($r1.readUTF());
            setFormat($r1.readUTF());
            int $i0 = $r1.readInt();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                this.leadingDigitsPattern_.add($r1.readUTF());
            }
            if ($r1.readBoolean()) {
                setNationalPrefixFormattingRule($r1.readUTF());
            }
            if ($r1.readBoolean()) {
                setDomesticCarrierCodeFormattingRule($r1.readUTF());
            }
            setNationalPrefixOptionalWhenFormatting($r1.readBoolean());
        }
    }

    public static class PhoneMetadata implements Externalizable {
        private static final long serialVersionUID = 1;
        private PhoneNumberDesc carrierSpecific_ = null;
        private int countryCode_ = 0;
        private PhoneNumberDesc emergency_ = null;
        private PhoneNumberDesc fixedLine_ = null;
        private PhoneNumberDesc generalDesc_ = null;
        private boolean hasCarrierSpecific;
        private boolean hasCountryCode;
        private boolean hasEmergency;
        private boolean hasFixedLine;
        private boolean hasGeneralDesc;
        private boolean hasId;
        private boolean hasInternationalPrefix;
        private boolean hasLeadingDigits;
        private boolean hasLeadingZeroPossible;
        private boolean hasMainCountryForCode;
        private boolean hasMobile;
        private boolean hasMobileNumberPortableRegion;
        private boolean hasNationalPrefix;
        private boolean hasNationalPrefixForParsing;
        private boolean hasNationalPrefixTransformRule;
        private boolean hasNoInternationalDialling;
        private boolean hasPager;
        private boolean hasPersonalNumber;
        private boolean hasPreferredExtnPrefix;
        private boolean hasPreferredInternationalPrefix;
        private boolean hasPremiumRate;
        private boolean hasSameMobileAndFixedLinePattern;
        private boolean hasSharedCost;
        private boolean hasShortCode;
        private boolean hasStandardRate;
        private boolean hasTollFree;
        private boolean hasUan;
        private boolean hasVoicemail;
        private boolean hasVoip;
        private String id_ = "";
        private String internationalPrefix_ = "";
        private List<NumberFormat> intlNumberFormat_ = new ArrayList();
        private String leadingDigits_ = "";
        private boolean leadingZeroPossible_ = false;
        private boolean mainCountryForCode_ = false;
        private boolean mobileNumberPortableRegion_ = false;
        private PhoneNumberDesc mobile_ = null;
        private String nationalPrefixForParsing_ = "";
        private String nationalPrefixTransformRule_ = "";
        private String nationalPrefix_ = "";
        private PhoneNumberDesc noInternationalDialling_ = null;
        private List<NumberFormat> numberFormat_ = new ArrayList();
        private PhoneNumberDesc pager_ = null;
        private PhoneNumberDesc personalNumber_ = null;
        private String preferredExtnPrefix_ = "";
        private String preferredInternationalPrefix_ = "";
        private PhoneNumberDesc premiumRate_ = null;
        private boolean sameMobileAndFixedLinePattern_ = false;
        private PhoneNumberDesc sharedCost_ = null;
        private PhoneNumberDesc shortCode_ = null;
        private PhoneNumberDesc standardRate_ = null;
        private PhoneNumberDesc tollFree_ = null;
        private PhoneNumberDesc uan_ = null;
        private PhoneNumberDesc voicemail_ = null;
        private PhoneNumberDesc voip_ = null;

        public static final class Builder extends PhoneMetadata {
            public PhoneMetadata build() throws  {
                return this;
            }
        }

        public static Builder newBuilder() throws  {
            return new Builder();
        }

        public boolean hasGeneralDesc() throws  {
            return this.hasGeneralDesc;
        }

        public PhoneNumberDesc getGeneralDesc() throws  {
            return this.generalDesc_;
        }

        public PhoneMetadata setGeneralDesc(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasGeneralDesc = true;
            this.generalDesc_ = $r1;
            return this;
        }

        public boolean hasFixedLine() throws  {
            return this.hasFixedLine;
        }

        public PhoneNumberDesc getFixedLine() throws  {
            return this.fixedLine_;
        }

        public PhoneMetadata setFixedLine(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasFixedLine = true;
            this.fixedLine_ = $r1;
            return this;
        }

        public boolean hasMobile() throws  {
            return this.hasMobile;
        }

        public PhoneNumberDesc getMobile() throws  {
            return this.mobile_;
        }

        public PhoneMetadata setMobile(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasMobile = true;
            this.mobile_ = $r1;
            return this;
        }

        public boolean hasTollFree() throws  {
            return this.hasTollFree;
        }

        public PhoneNumberDesc getTollFree() throws  {
            return this.tollFree_;
        }

        public PhoneMetadata setTollFree(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasTollFree = true;
            this.tollFree_ = $r1;
            return this;
        }

        public boolean hasPremiumRate() throws  {
            return this.hasPremiumRate;
        }

        public PhoneNumberDesc getPremiumRate() throws  {
            return this.premiumRate_;
        }

        public PhoneMetadata setPremiumRate(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasPremiumRate = true;
            this.premiumRate_ = $r1;
            return this;
        }

        public boolean hasSharedCost() throws  {
            return this.hasSharedCost;
        }

        public PhoneNumberDesc getSharedCost() throws  {
            return this.sharedCost_;
        }

        public PhoneMetadata setSharedCost(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasSharedCost = true;
            this.sharedCost_ = $r1;
            return this;
        }

        public boolean hasPersonalNumber() throws  {
            return this.hasPersonalNumber;
        }

        public PhoneNumberDesc getPersonalNumber() throws  {
            return this.personalNumber_;
        }

        public PhoneMetadata setPersonalNumber(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasPersonalNumber = true;
            this.personalNumber_ = $r1;
            return this;
        }

        public boolean hasVoip() throws  {
            return this.hasVoip;
        }

        public PhoneNumberDesc getVoip() throws  {
            return this.voip_;
        }

        public PhoneMetadata setVoip(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasVoip = true;
            this.voip_ = $r1;
            return this;
        }

        public boolean hasPager() throws  {
            return this.hasPager;
        }

        public PhoneNumberDesc getPager() throws  {
            return this.pager_;
        }

        public PhoneMetadata setPager(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasPager = true;
            this.pager_ = $r1;
            return this;
        }

        public boolean hasUan() throws  {
            return this.hasUan;
        }

        public PhoneNumberDesc getUan() throws  {
            return this.uan_;
        }

        public PhoneMetadata setUan(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasUan = true;
            this.uan_ = $r1;
            return this;
        }

        public boolean hasEmergency() throws  {
            return this.hasEmergency;
        }

        public PhoneNumberDesc getEmergency() throws  {
            return this.emergency_;
        }

        public PhoneMetadata setEmergency(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasEmergency = true;
            this.emergency_ = $r1;
            return this;
        }

        public boolean hasVoicemail() throws  {
            return this.hasVoicemail;
        }

        public PhoneNumberDesc getVoicemail() throws  {
            return this.voicemail_;
        }

        public PhoneMetadata setVoicemail(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasVoicemail = true;
            this.voicemail_ = $r1;
            return this;
        }

        public boolean hasShortCode() throws  {
            return this.hasShortCode;
        }

        public PhoneNumberDesc getShortCode() throws  {
            return this.shortCode_;
        }

        public PhoneMetadata setShortCode(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasShortCode = true;
            this.shortCode_ = $r1;
            return this;
        }

        public boolean hasStandardRate() throws  {
            return this.hasStandardRate;
        }

        public PhoneNumberDesc getStandardRate() throws  {
            return this.standardRate_;
        }

        public PhoneMetadata setStandardRate(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasStandardRate = true;
            this.standardRate_ = $r1;
            return this;
        }

        public boolean hasCarrierSpecific() throws  {
            return this.hasCarrierSpecific;
        }

        public PhoneNumberDesc getCarrierSpecific() throws  {
            return this.carrierSpecific_;
        }

        public PhoneMetadata setCarrierSpecific(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasCarrierSpecific = true;
            this.carrierSpecific_ = $r1;
            return this;
        }

        public boolean hasNoInternationalDialling() throws  {
            return this.hasNoInternationalDialling;
        }

        public PhoneNumberDesc getNoInternationalDialling() throws  {
            return this.noInternationalDialling_;
        }

        public PhoneMetadata setNoInternationalDialling(PhoneNumberDesc $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasNoInternationalDialling = true;
            this.noInternationalDialling_ = $r1;
            return this;
        }

        public boolean hasId() throws  {
            return this.hasId;
        }

        public String getId() throws  {
            return this.id_;
        }

        public PhoneMetadata setId(String $r1) throws  {
            this.hasId = true;
            this.id_ = $r1;
            return this;
        }

        public boolean hasCountryCode() throws  {
            return this.hasCountryCode;
        }

        public int getCountryCode() throws  {
            return this.countryCode_;
        }

        public PhoneMetadata setCountryCode(int $i0) throws  {
            this.hasCountryCode = true;
            this.countryCode_ = $i0;
            return this;
        }

        public boolean hasInternationalPrefix() throws  {
            return this.hasInternationalPrefix;
        }

        public String getInternationalPrefix() throws  {
            return this.internationalPrefix_;
        }

        public PhoneMetadata setInternationalPrefix(String $r1) throws  {
            this.hasInternationalPrefix = true;
            this.internationalPrefix_ = $r1;
            return this;
        }

        public boolean hasPreferredInternationalPrefix() throws  {
            return this.hasPreferredInternationalPrefix;
        }

        public String getPreferredInternationalPrefix() throws  {
            return this.preferredInternationalPrefix_;
        }

        public PhoneMetadata setPreferredInternationalPrefix(String $r1) throws  {
            this.hasPreferredInternationalPrefix = true;
            this.preferredInternationalPrefix_ = $r1;
            return this;
        }

        public PhoneMetadata clearPreferredInternationalPrefix() throws  {
            this.hasPreferredInternationalPrefix = false;
            this.preferredInternationalPrefix_ = "";
            return this;
        }

        public boolean hasNationalPrefix() throws  {
            return this.hasNationalPrefix;
        }

        public String getNationalPrefix() throws  {
            return this.nationalPrefix_;
        }

        public PhoneMetadata setNationalPrefix(String $r1) throws  {
            this.hasNationalPrefix = true;
            this.nationalPrefix_ = $r1;
            return this;
        }

        public PhoneMetadata clearNationalPrefix() throws  {
            this.hasNationalPrefix = false;
            this.nationalPrefix_ = "";
            return this;
        }

        public boolean hasPreferredExtnPrefix() throws  {
            return this.hasPreferredExtnPrefix;
        }

        public String getPreferredExtnPrefix() throws  {
            return this.preferredExtnPrefix_;
        }

        public PhoneMetadata setPreferredExtnPrefix(String $r1) throws  {
            this.hasPreferredExtnPrefix = true;
            this.preferredExtnPrefix_ = $r1;
            return this;
        }

        public PhoneMetadata clearPreferredExtnPrefix() throws  {
            this.hasPreferredExtnPrefix = false;
            this.preferredExtnPrefix_ = "";
            return this;
        }

        public boolean hasNationalPrefixForParsing() throws  {
            return this.hasNationalPrefixForParsing;
        }

        public String getNationalPrefixForParsing() throws  {
            return this.nationalPrefixForParsing_;
        }

        public PhoneMetadata setNationalPrefixForParsing(String $r1) throws  {
            this.hasNationalPrefixForParsing = true;
            this.nationalPrefixForParsing_ = $r1;
            return this;
        }

        public boolean hasNationalPrefixTransformRule() throws  {
            return this.hasNationalPrefixTransformRule;
        }

        public String getNationalPrefixTransformRule() throws  {
            return this.nationalPrefixTransformRule_;
        }

        public PhoneMetadata setNationalPrefixTransformRule(String $r1) throws  {
            this.hasNationalPrefixTransformRule = true;
            this.nationalPrefixTransformRule_ = $r1;
            return this;
        }

        public PhoneMetadata clearNationalPrefixTransformRule() throws  {
            this.hasNationalPrefixTransformRule = false;
            this.nationalPrefixTransformRule_ = "";
            return this;
        }

        public boolean hasSameMobileAndFixedLinePattern() throws  {
            return this.hasSameMobileAndFixedLinePattern;
        }

        public boolean isSameMobileAndFixedLinePattern() throws  {
            return this.sameMobileAndFixedLinePattern_;
        }

        public PhoneMetadata setSameMobileAndFixedLinePattern(boolean $z0) throws  {
            this.hasSameMobileAndFixedLinePattern = true;
            this.sameMobileAndFixedLinePattern_ = $z0;
            return this;
        }

        public PhoneMetadata clearSameMobileAndFixedLinePattern() throws  {
            this.hasSameMobileAndFixedLinePattern = false;
            this.sameMobileAndFixedLinePattern_ = false;
            return this;
        }

        public List<NumberFormat> numberFormats() throws  {
            return this.numberFormat_;
        }

        public int numberFormatSize() throws  {
            return this.numberFormat_.size();
        }

        public NumberFormat getNumberFormat(int $i0) throws  {
            return (NumberFormat) this.numberFormat_.get($i0);
        }

        public PhoneMetadata addNumberFormat(NumberFormat $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.numberFormat_.add($r1);
            return this;
        }

        public List<NumberFormat> intlNumberFormats() throws  {
            return this.intlNumberFormat_;
        }

        public int intlNumberFormatSize() throws  {
            return this.intlNumberFormat_.size();
        }

        public NumberFormat getIntlNumberFormat(int $i0) throws  {
            return (NumberFormat) this.intlNumberFormat_.get($i0);
        }

        public PhoneMetadata addIntlNumberFormat(NumberFormat $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.intlNumberFormat_.add($r1);
            return this;
        }

        public PhoneMetadata clearIntlNumberFormat() throws  {
            this.intlNumberFormat_.clear();
            return this;
        }

        public boolean hasMainCountryForCode() throws  {
            return this.hasMainCountryForCode;
        }

        public boolean isMainCountryForCode() throws  {
            return this.mainCountryForCode_;
        }

        public boolean getMainCountryForCode() throws  {
            return this.mainCountryForCode_;
        }

        public PhoneMetadata setMainCountryForCode(boolean $z0) throws  {
            this.hasMainCountryForCode = true;
            this.mainCountryForCode_ = $z0;
            return this;
        }

        public PhoneMetadata clearMainCountryForCode() throws  {
            this.hasMainCountryForCode = false;
            this.mainCountryForCode_ = false;
            return this;
        }

        public boolean hasLeadingDigits() throws  {
            return this.hasLeadingDigits;
        }

        public String getLeadingDigits() throws  {
            return this.leadingDigits_;
        }

        public PhoneMetadata setLeadingDigits(String $r1) throws  {
            this.hasLeadingDigits = true;
            this.leadingDigits_ = $r1;
            return this;
        }

        public boolean hasLeadingZeroPossible() throws  {
            return this.hasLeadingZeroPossible;
        }

        public boolean isLeadingZeroPossible() throws  {
            return this.leadingZeroPossible_;
        }

        public PhoneMetadata setLeadingZeroPossible(boolean $z0) throws  {
            this.hasLeadingZeroPossible = true;
            this.leadingZeroPossible_ = $z0;
            return this;
        }

        public PhoneMetadata clearLeadingZeroPossible() throws  {
            this.hasLeadingZeroPossible = false;
            this.leadingZeroPossible_ = false;
            return this;
        }

        public boolean hasMobileNumberPortableRegion() throws  {
            return this.hasMobileNumberPortableRegion;
        }

        public boolean isMobileNumberPortableRegion() throws  {
            return this.mobileNumberPortableRegion_;
        }

        public PhoneMetadata setMobileNumberPortableRegion(boolean $z0) throws  {
            this.hasMobileNumberPortableRegion = true;
            this.mobileNumberPortableRegion_ = $z0;
            return this;
        }

        public PhoneMetadata clearMobileNumberPortableRegion() throws  {
            this.hasMobileNumberPortableRegion = false;
            this.mobileNumberPortableRegion_ = false;
            return this;
        }

        public void writeExternal(ObjectOutput $r1) throws IOException {
            int $i1;
            $r1.writeBoolean(this.hasGeneralDesc);
            if (this.hasGeneralDesc) {
                this.generalDesc_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasFixedLine);
            if (this.hasFixedLine) {
                this.fixedLine_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasMobile);
            if (this.hasMobile) {
                this.mobile_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasTollFree);
            if (this.hasTollFree) {
                this.tollFree_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasPremiumRate);
            if (this.hasPremiumRate) {
                this.premiumRate_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasSharedCost);
            if (this.hasSharedCost) {
                this.sharedCost_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasPersonalNumber);
            if (this.hasPersonalNumber) {
                this.personalNumber_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasVoip);
            if (this.hasVoip) {
                this.voip_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasPager);
            if (this.hasPager) {
                this.pager_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasUan);
            if (this.hasUan) {
                this.uan_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasEmergency);
            if (this.hasEmergency) {
                this.emergency_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasVoicemail);
            if (this.hasVoicemail) {
                this.voicemail_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasShortCode);
            if (this.hasShortCode) {
                this.shortCode_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasStandardRate);
            if (this.hasStandardRate) {
                this.standardRate_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasCarrierSpecific);
            if (this.hasCarrierSpecific) {
                this.carrierSpecific_.writeExternal($r1);
            }
            $r1.writeBoolean(this.hasNoInternationalDialling);
            if (this.hasNoInternationalDialling) {
                this.noInternationalDialling_.writeExternal($r1);
            }
            $r1.writeUTF(this.id_);
            $r1.writeInt(this.countryCode_);
            $r1.writeUTF(this.internationalPrefix_);
            $r1.writeBoolean(this.hasPreferredInternationalPrefix);
            if (this.hasPreferredInternationalPrefix) {
                $r1.writeUTF(this.preferredInternationalPrefix_);
            }
            $r1.writeBoolean(this.hasNationalPrefix);
            if (this.hasNationalPrefix) {
                $r1.writeUTF(this.nationalPrefix_);
            }
            $r1.writeBoolean(this.hasPreferredExtnPrefix);
            if (this.hasPreferredExtnPrefix) {
                $r1.writeUTF(this.preferredExtnPrefix_);
            }
            $r1.writeBoolean(this.hasNationalPrefixForParsing);
            if (this.hasNationalPrefixForParsing) {
                $r1.writeUTF(this.nationalPrefixForParsing_);
            }
            $r1.writeBoolean(this.hasNationalPrefixTransformRule);
            if (this.hasNationalPrefixTransformRule) {
                $r1.writeUTF(this.nationalPrefixTransformRule_);
            }
            $r1.writeBoolean(this.sameMobileAndFixedLinePattern_);
            int $i0 = numberFormatSize();
            $r1.writeInt($i0);
            for ($i1 = 0; $i1 < $i0; $i1++) {
                ((NumberFormat) this.numberFormat_.get($i1)).writeExternal($r1);
            }
            $i0 = intlNumberFormatSize();
            $r1.writeInt($i0);
            for ($i1 = 0; $i1 < $i0; $i1++) {
                ((NumberFormat) this.intlNumberFormat_.get($i1)).writeExternal($r1);
            }
            $r1.writeBoolean(this.mainCountryForCode_);
            $r1.writeBoolean(this.hasLeadingDigits);
            if (this.hasLeadingDigits) {
                $r1.writeUTF(this.leadingDigits_);
            }
            $r1.writeBoolean(this.leadingZeroPossible_);
            $r1.writeBoolean(this.mobileNumberPortableRegion_);
        }

        public void readExternal(ObjectInput $r1) throws IOException {
            int $i1;
            if ($r1.readBoolean()) {
                PhoneNumberDesc $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setGeneralDesc($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setFixedLine($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setMobile($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setTollFree($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setPremiumRate($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setSharedCost($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setPersonalNumber($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setVoip($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setPager($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setUan($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setEmergency($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setVoicemail($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setShortCode($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setStandardRate($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setCarrierSpecific($r2);
            }
            if ($r1.readBoolean()) {
                $r2 = new PhoneNumberDesc();
                $r2.readExternal($r1);
                setNoInternationalDialling($r2);
            }
            setId($r1.readUTF());
            setCountryCode($r1.readInt());
            setInternationalPrefix($r1.readUTF());
            if ($r1.readBoolean()) {
                setPreferredInternationalPrefix($r1.readUTF());
            }
            if ($r1.readBoolean()) {
                setNationalPrefix($r1.readUTF());
            }
            if ($r1.readBoolean()) {
                setPreferredExtnPrefix($r1.readUTF());
            }
            if ($r1.readBoolean()) {
                setNationalPrefixForParsing($r1.readUTF());
            }
            if ($r1.readBoolean()) {
                setNationalPrefixTransformRule($r1.readUTF());
            }
            setSameMobileAndFixedLinePattern($r1.readBoolean());
            int $i0 = $r1.readInt();
            for ($i1 = 0; $i1 < $i0; $i1++) {
                NumberFormat $r4 = new NumberFormat();
                $r4.readExternal($r1);
                this.numberFormat_.add($r4);
            }
            $i0 = $r1.readInt();
            for ($i1 = 0; $i1 < $i0; $i1++) {
                $r4 = new NumberFormat();
                $r4.readExternal($r1);
                this.intlNumberFormat_.add($r4);
            }
            setMainCountryForCode($r1.readBoolean());
            if ($r1.readBoolean()) {
                setLeadingDigits($r1.readUTF());
            }
            setLeadingZeroPossible($r1.readBoolean());
            setMobileNumberPortableRegion($r1.readBoolean());
        }
    }

    public static class PhoneMetadataCollection implements Externalizable {
        private static final long serialVersionUID = 1;
        private List<PhoneMetadata> metadata_ = new ArrayList();

        public static final class Builder extends PhoneMetadataCollection {
            public PhoneMetadataCollection build() throws  {
                return this;
            }
        }

        public static Builder newBuilder() throws  {
            return new Builder();
        }

        public List<PhoneMetadata> getMetadataList() throws  {
            return this.metadata_;
        }

        public int getMetadataCount() throws  {
            return this.metadata_.size();
        }

        public PhoneMetadataCollection addMetadata(PhoneMetadata $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.metadata_.add($r1);
            return this;
        }

        public void writeExternal(ObjectOutput $r1) throws IOException {
            int $i0 = getMetadataCount();
            $r1.writeInt($i0);
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                ((PhoneMetadata) this.metadata_.get($i1)).writeExternal($r1);
            }
        }

        public void readExternal(ObjectInput $r1) throws IOException {
            int $i0 = $r1.readInt();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                PhoneMetadata $r2 = new PhoneMetadata();
                $r2.readExternal($r1);
                this.metadata_.add($r2);
            }
        }

        public PhoneMetadataCollection clear() throws  {
            this.metadata_.clear();
            return this;
        }
    }

    public static class PhoneNumberDesc implements Externalizable {
        private static final long serialVersionUID = 1;
        private String exampleNumber_ = "";
        private boolean hasExampleNumber;
        private boolean hasNationalNumberPattern;
        private boolean hasPossibleNumberPattern;
        private String nationalNumberPattern_ = "";
        private List<Integer> possibleLengthLocalOnly_ = new ArrayList();
        private List<Integer> possibleLength_ = new ArrayList();
        private String possibleNumberPattern_ = "";

        public static final class Builder extends PhoneNumberDesc {
            public PhoneNumberDesc build() throws  {
                return this;
            }

            public Builder mergeFrom(PhoneNumberDesc $r1) throws  {
                int $i0;
                if ($r1.hasNationalNumberPattern()) {
                    setNationalNumberPattern($r1.getNationalNumberPattern());
                }
                if ($r1.hasPossibleNumberPattern()) {
                    setPossibleNumberPattern($r1.getPossibleNumberPattern());
                }
                for ($i0 = 0; $i0 < $r1.getPossibleLengthCount(); $i0++) {
                    addPossibleLength($r1.getPossibleLength($i0));
                }
                for ($i0 = 0; $i0 < $r1.getPossibleLengthLocalOnlyCount(); $i0++) {
                    addPossibleLengthLocalOnly($r1.getPossibleLengthLocalOnly($i0));
                }
                if (!$r1.hasExampleNumber()) {
                    return this;
                }
                setExampleNumber($r1.getExampleNumber());
                return this;
            }
        }

        public static Builder newBuilder() throws  {
            return new Builder();
        }

        public boolean hasNationalNumberPattern() throws  {
            return this.hasNationalNumberPattern;
        }

        public String getNationalNumberPattern() throws  {
            return this.nationalNumberPattern_;
        }

        public PhoneNumberDesc setNationalNumberPattern(String $r1) throws  {
            this.hasNationalNumberPattern = true;
            this.nationalNumberPattern_ = $r1;
            return this;
        }

        public PhoneNumberDesc clearNationalNumberPattern() throws  {
            this.hasNationalNumberPattern = false;
            this.nationalNumberPattern_ = "";
            return this;
        }

        public boolean hasPossibleNumberPattern() throws  {
            return this.hasPossibleNumberPattern;
        }

        public String getPossibleNumberPattern() throws  {
            return this.possibleNumberPattern_;
        }

        public PhoneNumberDesc setPossibleNumberPattern(String $r1) throws  {
            this.hasPossibleNumberPattern = true;
            this.possibleNumberPattern_ = $r1;
            return this;
        }

        public PhoneNumberDesc clearPossibleNumberPattern() throws  {
            this.hasPossibleNumberPattern = false;
            this.possibleNumberPattern_ = "";
            return this;
        }

        public List<Integer> getPossibleLengthList() throws  {
            return this.possibleLength_;
        }

        public int getPossibleLengthCount() throws  {
            return this.possibleLength_.size();
        }

        public int getPossibleLength(int $i0) throws  {
            return ((Integer) this.possibleLength_.get($i0)).intValue();
        }

        public PhoneNumberDesc addPossibleLength(int $i0) throws  {
            this.possibleLength_.add(Integer.valueOf($i0));
            return this;
        }

        public PhoneNumberDesc clearPossibleLength() throws  {
            this.possibleLength_.clear();
            return this;
        }

        public List<Integer> getPossibleLengthLocalOnlyList() throws  {
            return this.possibleLengthLocalOnly_;
        }

        public int getPossibleLengthLocalOnlyCount() throws  {
            return this.possibleLengthLocalOnly_.size();
        }

        public int getPossibleLengthLocalOnly(int $i0) throws  {
            return ((Integer) this.possibleLengthLocalOnly_.get($i0)).intValue();
        }

        public PhoneNumberDesc addPossibleLengthLocalOnly(int $i0) throws  {
            this.possibleLengthLocalOnly_.add(Integer.valueOf($i0));
            return this;
        }

        public PhoneNumberDesc clearPossibleLengthLocalOnly() throws  {
            this.possibleLengthLocalOnly_.clear();
            return this;
        }

        public boolean hasExampleNumber() throws  {
            return this.hasExampleNumber;
        }

        public String getExampleNumber() throws  {
            return this.exampleNumber_;
        }

        public PhoneNumberDesc setExampleNumber(String $r1) throws  {
            this.hasExampleNumber = true;
            this.exampleNumber_ = $r1;
            return this;
        }

        public PhoneNumberDesc clearExampleNumber() throws  {
            this.hasExampleNumber = false;
            this.exampleNumber_ = "";
            return this;
        }

        public boolean exactlySameAs(PhoneNumberDesc $r1) throws  {
            return this.nationalNumberPattern_.equals($r1.nationalNumberPattern_) && this.possibleNumberPattern_.equals($r1.possibleNumberPattern_) && this.possibleLength_.equals($r1.possibleLength_) && this.possibleLengthLocalOnly_.equals($r1.possibleLengthLocalOnly_) && this.exampleNumber_.equals($r1.exampleNumber_);
        }

        public void writeExternal(ObjectOutput $r1) throws IOException {
            int $i1;
            $r1.writeBoolean(this.hasNationalNumberPattern);
            if (this.hasNationalNumberPattern) {
                $r1.writeUTF(this.nationalNumberPattern_);
            }
            $r1.writeBoolean(this.hasPossibleNumberPattern);
            if (this.hasPossibleNumberPattern) {
                $r1.writeUTF(this.possibleNumberPattern_);
            }
            int $i0 = getPossibleLengthCount();
            $r1.writeInt($i0);
            for ($i1 = 0; $i1 < $i0; $i1++) {
                $r1.writeInt(((Integer) this.possibleLength_.get($i1)).intValue());
            }
            $i0 = getPossibleLengthLocalOnlyCount();
            $r1.writeInt($i0);
            for ($i1 = 0; $i1 < $i0; $i1++) {
                $r1.writeInt(((Integer) this.possibleLengthLocalOnly_.get($i1)).intValue());
            }
            $r1.writeBoolean(this.hasExampleNumber);
            if (this.hasExampleNumber) {
                $r1.writeUTF(this.exampleNumber_);
            }
        }

        public void readExternal(ObjectInput $r1) throws IOException {
            int $i1;
            if ($r1.readBoolean()) {
                setNationalNumberPattern($r1.readUTF());
            }
            if ($r1.readBoolean()) {
                setPossibleNumberPattern($r1.readUTF());
            }
            int $i0 = $r1.readInt();
            for ($i1 = 0; $i1 < $i0; $i1++) {
                this.possibleLength_.add(Integer.valueOf($r1.readInt()));
            }
            $i0 = $r1.readInt();
            for ($i1 = 0; $i1 < $i0; $i1++) {
                this.possibleLengthLocalOnly_.add(Integer.valueOf($r1.readInt()));
            }
            if ($r1.readBoolean()) {
                setExampleNumber($r1.readUTF());
            }
        }
    }

    private Phonemetadata() throws  {
    }
}
