package com.google.i18n.phonenumbers;

import com.waze.strings.DisplayStrings;
import java.io.Serializable;

public final class Phonenumber {

    public static class PhoneNumber implements Serializable {
        private static final long serialVersionUID = 1;
        private CountryCodeSource countryCodeSource_ = CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN;
        private int countryCode_ = 0;
        private String extension_ = "";
        private boolean hasCountryCode;
        private boolean hasCountryCodeSource;
        private boolean hasExtension;
        private boolean hasItalianLeadingZero;
        private boolean hasNationalNumber;
        private boolean hasNumberOfLeadingZeros;
        private boolean hasPreferredDomesticCarrierCode;
        private boolean hasRawInput;
        private boolean italianLeadingZero_ = false;
        private long nationalNumber_ = 0;
        private int numberOfLeadingZeros_ = 1;
        private String preferredDomesticCarrierCode_ = "";
        private String rawInput_ = "";

        public enum CountryCodeSource {
            FROM_NUMBER_WITH_PLUS_SIGN,
            FROM_NUMBER_WITH_IDD,
            FROM_NUMBER_WITHOUT_PLUS_SIGN,
            FROM_DEFAULT_COUNTRY
        }

        public boolean hasCountryCode() throws  {
            return this.hasCountryCode;
        }

        public int getCountryCode() throws  {
            return this.countryCode_;
        }

        public PhoneNumber setCountryCode(int $i0) throws  {
            this.hasCountryCode = true;
            this.countryCode_ = $i0;
            return this;
        }

        public PhoneNumber clearCountryCode() throws  {
            this.hasCountryCode = false;
            this.countryCode_ = 0;
            return this;
        }

        public boolean hasNationalNumber() throws  {
            return this.hasNationalNumber;
        }

        public long getNationalNumber() throws  {
            return this.nationalNumber_;
        }

        public PhoneNumber setNationalNumber(long $l0) throws  {
            this.hasNationalNumber = true;
            this.nationalNumber_ = $l0;
            return this;
        }

        public PhoneNumber clearNationalNumber() throws  {
            this.hasNationalNumber = false;
            this.nationalNumber_ = 0;
            return this;
        }

        public boolean hasExtension() throws  {
            return this.hasExtension;
        }

        public String getExtension() throws  {
            return this.extension_;
        }

        public PhoneNumber setExtension(String $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasExtension = true;
            this.extension_ = $r1;
            return this;
        }

        public PhoneNumber clearExtension() throws  {
            this.hasExtension = false;
            this.extension_ = "";
            return this;
        }

        public boolean hasItalianLeadingZero() throws  {
            return this.hasItalianLeadingZero;
        }

        public boolean isItalianLeadingZero() throws  {
            return this.italianLeadingZero_;
        }

        public PhoneNumber setItalianLeadingZero(boolean $z0) throws  {
            this.hasItalianLeadingZero = true;
            this.italianLeadingZero_ = $z0;
            return this;
        }

        public PhoneNumber clearItalianLeadingZero() throws  {
            this.hasItalianLeadingZero = false;
            this.italianLeadingZero_ = false;
            return this;
        }

        public boolean hasNumberOfLeadingZeros() throws  {
            return this.hasNumberOfLeadingZeros;
        }

        public int getNumberOfLeadingZeros() throws  {
            return this.numberOfLeadingZeros_;
        }

        public PhoneNumber setNumberOfLeadingZeros(int $i0) throws  {
            this.hasNumberOfLeadingZeros = true;
            this.numberOfLeadingZeros_ = $i0;
            return this;
        }

        public PhoneNumber clearNumberOfLeadingZeros() throws  {
            this.hasNumberOfLeadingZeros = false;
            this.numberOfLeadingZeros_ = 1;
            return this;
        }

        public boolean hasRawInput() throws  {
            return this.hasRawInput;
        }

        public String getRawInput() throws  {
            return this.rawInput_;
        }

        public PhoneNumber setRawInput(String $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasRawInput = true;
            this.rawInput_ = $r1;
            return this;
        }

        public PhoneNumber clearRawInput() throws  {
            this.hasRawInput = false;
            this.rawInput_ = "";
            return this;
        }

        public boolean hasCountryCodeSource() throws  {
            return this.hasCountryCodeSource;
        }

        public CountryCodeSource getCountryCodeSource() throws  {
            return this.countryCodeSource_;
        }

        public PhoneNumber setCountryCodeSource(CountryCodeSource $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasCountryCodeSource = true;
            this.countryCodeSource_ = $r1;
            return this;
        }

        public PhoneNumber clearCountryCodeSource() throws  {
            this.hasCountryCodeSource = false;
            this.countryCodeSource_ = CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN;
            return this;
        }

        public boolean hasPreferredDomesticCarrierCode() throws  {
            return this.hasPreferredDomesticCarrierCode;
        }

        public String getPreferredDomesticCarrierCode() throws  {
            return this.preferredDomesticCarrierCode_;
        }

        public PhoneNumber setPreferredDomesticCarrierCode(String $r1) throws  {
            if ($r1 == null) {
                throw new NullPointerException();
            }
            this.hasPreferredDomesticCarrierCode = true;
            this.preferredDomesticCarrierCode_ = $r1;
            return this;
        }

        public PhoneNumber clearPreferredDomesticCarrierCode() throws  {
            this.hasPreferredDomesticCarrierCode = false;
            this.preferredDomesticCarrierCode_ = "";
            return this;
        }

        public final PhoneNumber clear() throws  {
            clearCountryCode();
            clearNationalNumber();
            clearExtension();
            clearItalianLeadingZero();
            clearNumberOfLeadingZeros();
            clearRawInput();
            clearCountryCodeSource();
            clearPreferredDomesticCarrierCode();
            return this;
        }

        public PhoneNumber mergeFrom(PhoneNumber $r1) throws  {
            if ($r1.hasCountryCode()) {
                setCountryCode($r1.getCountryCode());
            }
            if ($r1.hasNationalNumber()) {
                setNationalNumber($r1.getNationalNumber());
            }
            if ($r1.hasExtension()) {
                setExtension($r1.getExtension());
            }
            if ($r1.hasItalianLeadingZero()) {
                setItalianLeadingZero($r1.isItalianLeadingZero());
            }
            if ($r1.hasNumberOfLeadingZeros()) {
                setNumberOfLeadingZeros($r1.getNumberOfLeadingZeros());
            }
            if ($r1.hasRawInput()) {
                setRawInput($r1.getRawInput());
            }
            if ($r1.hasCountryCodeSource()) {
                setCountryCodeSource($r1.getCountryCodeSource());
            }
            if (!$r1.hasPreferredDomesticCarrierCode()) {
                return this;
            }
            setPreferredDomesticCarrierCode($r1.getPreferredDomesticCarrierCode());
            return this;
        }

        public boolean exactlySameAs(PhoneNumber $r1) throws  {
            if ($r1 == null) {
                return false;
            }
            if (this != $r1) {
                return this.countryCode_ == $r1.countryCode_ && this.nationalNumber_ == $r1.nationalNumber_ && this.extension_.equals($r1.extension_) && this.italianLeadingZero_ == $r1.italianLeadingZero_ && this.numberOfLeadingZeros_ == $r1.numberOfLeadingZeros_ && this.rawInput_.equals($r1.rawInput_) && this.countryCodeSource_ == $r1.countryCodeSource_ && this.preferredDomesticCarrierCode_.equals($r1.preferredDomesticCarrierCode_) && hasPreferredDomesticCarrierCode() == $r1.hasPreferredDomesticCarrierCode();
            } else {
                return true;
            }
        }

        public boolean equals(Object $r1) throws  {
            return ($r1 instanceof PhoneNumber) && exactlySameAs((PhoneNumber) $r1);
        }

        public int hashCode() throws  {
            short $s0 = (short) 1231;
            int $i1 = (((((((((((((((getCountryCode() + DisplayStrings.DS_DRIVER_PROFILE_WORKPLACE_ADD) * 53) + Long.valueOf(getNationalNumber()).hashCode()) * 53) + getExtension().hashCode()) * 53) + (isItalianLeadingZero() ? (short) 1231 : (short) 1237)) * 53) + getNumberOfLeadingZeros()) * 53) + getRawInput().hashCode()) * 53) + getCountryCodeSource().hashCode()) * 53) + getPreferredDomesticCarrierCode().hashCode()) * 53;
            if (!hasPreferredDomesticCarrierCode()) {
                $s0 = (short) 1237;
            }
            return $i1 + $s0;
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder();
            $r1.append("Country Code: ").append(this.countryCode_);
            $r1.append(" National Number: ").append(this.nationalNumber_);
            if (hasItalianLeadingZero() && isItalianLeadingZero()) {
                $r1.append(" Leading Zero(s): true");
            }
            if (hasNumberOfLeadingZeros()) {
                $r1.append(" Number of leading zeros: ").append(this.numberOfLeadingZeros_);
            }
            if (hasExtension()) {
                $r1.append(" Extension: ").append(this.extension_);
            }
            if (hasCountryCodeSource()) {
                $r1.append(" Country Code Source: ").append(this.countryCodeSource_);
            }
            if (hasPreferredDomesticCarrierCode()) {
                $r1.append(" Preferred Domestic Carrier Code: ").append(this.preferredDomesticCarrierCode_);
            }
            return $r1.toString();
        }
    }

    private Phonenumber() throws  {
    }
}
