package com.google.i18n.phonenumbers.internal;

import com.google.i18n.phonenumbers.Phonemetadata.PhoneNumberDesc;

public interface MatcherApi {
    boolean matchesNationalNumber(String str, PhoneNumberDesc phoneNumberDesc, boolean z) throws ;
}
