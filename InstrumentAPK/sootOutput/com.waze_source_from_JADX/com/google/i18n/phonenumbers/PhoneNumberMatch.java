package com.google.i18n.phonenumbers;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import java.util.Arrays;

public final class PhoneNumberMatch {
    private final PhoneNumber number;
    private final String rawString;
    private final int start;

    PhoneNumberMatch(int $i0, String $r1, PhoneNumber $r2) throws  {
        if ($i0 < 0) {
            throw new IllegalArgumentException("Start index must be >= 0.");
        } else if ($r1 == null || $r2 == null) {
            throw new NullPointerException();
        } else {
            this.start = $i0;
            this.rawString = $r1;
            this.number = $r2;
        }
    }

    public PhoneNumber number() throws  {
        return this.number;
    }

    public int start() throws  {
        return this.start;
    }

    public int end() throws  {
        return this.start + this.rawString.length();
    }

    public String rawString() throws  {
        return this.rawString;
    }

    public int hashCode() throws  {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.start), this.rawString, this.number});
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof PhoneNumberMatch)) {
            return false;
        }
        PhoneNumberMatch $r2 = (PhoneNumberMatch) $r1;
        return this.rawString.equals($r2.rawString) && this.start == $r2.start && this.number.equals($r2.number);
    }

    public String toString() throws  {
        return "PhoneNumberMatch [" + start() + "," + end() + ") " + this.rawString;
    }
}
