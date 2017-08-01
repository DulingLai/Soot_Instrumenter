package com.google.android.gms.people.model;

import com.google.android.gms.people.internal.zze;

/* compiled from: dalvik_source_com.waze.apk */
public interface EmailAddress extends Affinities, ValueAndType {
    public static final Iterable<EmailAddress> EMPTY_EMAILS = new zze();

    String getType() throws ;

    String getValue() throws ;
}
