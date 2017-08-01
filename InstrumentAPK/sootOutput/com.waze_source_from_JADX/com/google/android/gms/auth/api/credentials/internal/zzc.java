package com.google.android.gms.auth.api.credentials.internal;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.credentials.PasswordSpecification;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzc {
    public static Intent zza(Context context, HintRequest $r1, PasswordSpecification $r2) throws  {
        return new Intent("com.google.android.gms.auth.api.credentials.PICKER").putExtra("com.google.android.gms.credentials.RequestType", "Hints").putExtra("com.google.android.gms.credentials.HintRequest", $r1).putExtra("com.google.android.gms.credentials.PasswordSpecification", $r2);
    }
}
