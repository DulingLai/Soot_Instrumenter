package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzawy extends IOException {
    public zzawy(String $r1) throws  {
        super($r1);
    }

    static zzawy iB() throws  {
        return new zzawy("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either than the input has been truncated or that an embedded message misreported its own length.");
    }

    static zzawy iC() throws  {
        return new zzawy("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    static zzawy iD() throws  {
        return new zzawy("CodedInputStream encountered a malformed varint.");
    }

    static zzawy iE() throws  {
        return new zzawy("Protocol message contained an invalid tag (zero).");
    }

    static zzawy iF() throws  {
        return new zzawy("Protocol message end-group tag did not match expected tag.");
    }

    static zzawy iG() throws  {
        return new zzawy("Protocol message tag had invalid wire type.");
    }

    static zzawy iH() throws  {
        return new zzawy("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }
}
