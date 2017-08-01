package com.google.android.gms.auth.api.credentials;

import android.accounts.Account;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzab;

/* compiled from: dalvik_source_com.waze.apk */
public final class IdentityProviders {
    public static final String FACEBOOK = "https://www.facebook.com";
    public static final String GOOGLE = "https://accounts.google.com";
    public static final String LINKEDIN = "https://www.linkedin.com";
    public static final String MICROSOFT = "https://login.live.com";
    public static final String PAYPAL = "https://www.paypal.com";
    public static final String TWITTER = "https://twitter.com";
    public static final String YAHOO = "https://login.yahoo.com";

    private IdentityProviders() throws  {
    }

    @Nullable
    public static final String getIdentityProviderForAccount(@NonNull Account $r0) throws  {
        zzab.zzb((Object) $r0, (Object) "account cannot be null");
        return "com.google".equals($r0.type) ? GOOGLE : "com.facebook.auth.login".equals($r0.type) ? FACEBOOK : null;
    }
}
