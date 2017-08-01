package com.google.android.gms.people.model;

import com.google.android.gms.people.internal.zze;

/* compiled from: dalvik_source_com.waze.apk */
public interface PhoneNumber extends ValueAndType {
    public static final Iterable<PhoneNumber> EMPTY_PHONES = new zze();

    String getType() throws ;

    String getValue() throws ;
}
