package com.google.i18n.phonenumbers.internal;

import com.google.i18n.phonenumbers.Phonemetadata.PhoneNumberDesc;
import com.google.i18n.phonenumbers.RegexCache;
import java.util.regex.Matcher;

public final class RegexBasedMatcher implements MatcherApi {
    private final RegexCache regexCache = new RegexCache(100);

    public static MatcherApi create() throws  {
        return new RegexBasedMatcher();
    }

    private RegexBasedMatcher() throws  {
    }

    public boolean matchesNationalNumber(String $r1, PhoneNumberDesc $r2, boolean $z0) throws  {
        Matcher $r6 = this.regexCache.getPatternForRegex($r2.getNationalNumberPattern()).matcher($r1);
        return $r6.matches() || ($z0 && $r6.lookingAt());
    }
}
