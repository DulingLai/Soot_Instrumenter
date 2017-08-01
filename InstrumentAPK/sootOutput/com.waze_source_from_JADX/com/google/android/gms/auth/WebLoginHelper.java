package com.google.android.gms.auth;

import android.accounts.Account;
import android.content.Context;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.CookieManager;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.internal.zzawy;
import com.google.android.gms.internal.zznl;
import com.google.android.gms.internal.zznm;
import com.google.android.gms.internal.zznn;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/* compiled from: dalvik_source_com.waze.apk */
public class WebLoginHelper {
    private final zza ej;
    private final CookieManager ek;
    private final Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class RecoverableException extends Exception {
        private final String el;

        private RecoverableException(String $r1) throws  {
            this.el = $r1;
        }

        public String getRecoveryUrl() throws  {
            return this.el;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class zza {
        zza() throws  {
        }

        public zznm zza(Context $r1, Account $r2, String $r3) throws IOException, GoogleAuthException {
            try {
                return zznm.zzk(Base64.decode(GoogleAuthUtilLight.getToken($r1, $r2, $r3), 9));
            } catch (zzawy $r6) {
                throw new GoogleAuthException("Couldn't read data from server.", $r6);
            }
        }
    }

    public WebLoginHelper(Context $r1) throws  {
        this($r1, new zza(), CookieManager.getInstance());
    }

    WebLoginHelper(Context $r1, zza $r2, CookieManager $r3) throws  {
        this.mContext = $r1;
        this.ej = $r2;
        this.ek = $r3;
    }

    public static WebLoginHelper create(Context $r0) throws  {
        return new WebLoginHelper($r0);
    }

    static String zza(zznl $r0) throws  {
        StringBuilder $r1 = new StringBuilder($r0.name).append('=');
        if (!TextUtils.isEmpty($r0.value)) {
            $r1.append($r0.value);
        }
        if (zzc($r0.jg)) {
            $r1.append(";HttpOnly");
        }
        if (zzc($r0.jf)) {
            $r1.append(";Secure");
        }
        if (!TextUtils.isEmpty($r0.jd)) {
            $r1.append(";Domain=").append($r0.jd);
        }
        if (!TextUtils.isEmpty($r0.path)) {
            $r1.append(";Path=").append($r0.path);
        }
        if ($r0.jh != null && $r0.jh.intValue() > 0) {
            $r1.append(";Max-Age=").append($r0.jh);
        }
        return $r1.toString();
    }

    private void zza(zznm $r1) throws RecoverableException, IOException, GoogleAuthException {
        if ($r1 == null || $r1.jj == null) {
            throw new GoogleAuthException("Invalid response.");
        }
        zznn $r2 = $r1.jj;
        switch ($r2.jk.intValue()) {
            case 1:
                zza($r2.jl);
                return;
            case 2:
                throw new IOException("Request failed, but server said RETRY.");
            case 3:
            case 4:
                break;
            case 5:
                zza($r2.jl);
                zza($r2.jn);
                return;
            default:
                break;
        }
        String $r5 = String.valueOf($r2);
        Log.w("WebLoginHelper", new StringBuilder(String.valueOf($r5).length() + 21).append("Unexpected response: ").append($r5).toString());
        $r5 = String.valueOf($r2.jk);
        throw new GoogleAuthException(new StringBuilder(String.valueOf($r5).length() + 25).append("Unknown response status: ").append($r5).toString());
    }

    private void zza(zznl[] $r1) throws  {
        int $i0 = $r1;
        int length = $i0.length;
        $r1 = $i0;
        for (int $i1 = 0; $i1 < length; $i1++) {
            zznl $r2 = $r1[$i1];
            String $r3 = !TextUtils.isEmpty($r2.je) ? $r2.je : $r2.jd;
            if (TextUtils.isEmpty($r3) || TextUtils.isEmpty($r2.name) || TextUtils.isEmpty($r2.value)) {
                Log.w("WebLoginHelper", "Invalid cookie.");
            } else {
                String $r4 = zzc($r2.jf) ? "https" : "http";
                $r3 = new StringBuilder((String.valueOf($r4).length() + 3) + String.valueOf($r3).length()).append($r4).append("://").append($r3).toString();
                $r4 = zza($r2);
                String $r7 = "Setting cookie for url: ";
                String $r8 = String.valueOf($r3);
                Log.d("WebLoginHelper", $r8.length() != 0 ? $r7.concat($r8) : new String("Setting cookie for url: "));
                this.ek.setCookie($r3, $r4);
            }
        }
    }

    private void zza(com.google.android.gms.internal.zznn.zza[] $r1) throws RecoverableException, GoogleAuthException {
        for (com.google.android.gms.internal.zznn.zza $r2 : $r1) {
            switch ($r2.jk.intValue()) {
                case 1:
                case 3:
                    break;
                case 2:
                    throw new RecoverableException($r2.url);
                default:
                    String $r4 = String.valueOf($r2.jk);
                    Log.w("WebLoginHelper", new StringBuilder(String.valueOf($r4).length() + 36).append("Unrecognized failed account status: ").append($r4).toString());
                    break;
            }
        }
        throw new GoogleAuthException("Authorization failed, but no recoverable accounts.");
    }

    static String zzb(String... $r0) throws  {
        String $r2;
        String $r4;
        Builder $r1 = new Builder();
        int $i0 = $r0.length;
        int $i1 = 0;
        while ($i1 < $i0) {
            $r2 = $r0[$i1];
            try {
                URL $r3 = new URL($r2);
                $r4 = String.valueOf($r3.getProtocol());
                $r2 = String.valueOf($r3.getHost());
                $r1.appendQueryParameter("url", new StringBuilder((String.valueOf($r4).length() + 3) + String.valueOf($r2).length()).append($r4).append("://").append($r2).toString());
                $i1++;
            } catch (MalformedURLException e) {
                $r4 = "Invalid URL: ";
                $r2 = String.valueOf($r2);
                throw new IllegalArgumentException($r2.length() != 0 ? $r4.concat($r2) : new String("Invalid URL: "));
            }
        }
        $r4 = "weblogin:";
        $r2 = String.valueOf($r1.build().getQuery());
        return $r2.length() != 0 ? $r4.concat($r2) : new String("weblogin:");
    }

    private static boolean zzc(Boolean $r0) throws  {
        return $r0 != null && $r0.booleanValue();
    }

    public void getAndSetCookies(Account $r1, String... $r2) throws RecoverableException, IOException, GoogleAuthException {
        zzab.zzag($r1);
        boolean $z0 = $r2 != null && $r2.length > 0;
        zzab.zzb($z0, (Object) "Must have at least one URL.");
        zza(this.ej.zza(this.mContext, $r1, zzb($r2)));
    }
}
