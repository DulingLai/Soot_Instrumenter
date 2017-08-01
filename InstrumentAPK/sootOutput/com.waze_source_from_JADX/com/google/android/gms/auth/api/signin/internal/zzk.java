package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.zzab;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;

/* compiled from: dalvik_source_com.waze.apk */
public class zzk {
    private static final Lock gj = new ReentrantLock();
    private static zzk gk;
    private final Lock gl = new ReentrantLock();
    private final SharedPreferences gm;

    zzk(Context $r1) throws  {
        this.gm = $r1.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    private String zzad(String $r1, String $r2) throws  {
        String $r3 = String.valueOf(":");
        return new StringBuilder(((String.valueOf($r1).length() + 0) + String.valueOf($r3).length()) + String.valueOf($r2).length()).append($r1).append($r3).append($r2).toString();
    }

    public static zzk zzbc(Context $r0) throws  {
        zzab.zzag($r0);
        gj.lock();
        try {
            if (gk == null) {
                gk = new zzk($r0.getApplicationContext());
            }
            zzk $r2 = gk;
            return $r2;
        } finally {
            gj.unlock();
        }
    }

    void zza(GoogleSignInAccount $r1, GoogleSignInOptions $r2) throws  {
        zzab.zzag($r1);
        zzab.zzag($r2);
        String $r3 = $r1.zzaee();
        zzac(zzad("googleSignInAccount", $r3), $r1.zzaeg());
        zzac(zzad("googleSignInOptions", $r3), $r2.zzaef());
    }

    protected void zzac(String $r1, String $r2) throws  {
        this.gl.lock();
        try {
            this.gm.edit().putString($r1, $r2).apply();
        } finally {
            this.gl.unlock();
        }
    }

    public GoogleSignInAccount zzafb() throws  {
        return zzet(zzev("defaultGoogleSignInAccount"));
    }

    public GoogleSignInOptions zzafc() throws  {
        return zzeu(zzev("defaultGoogleSignInAccount"));
    }

    public void zzafd() throws  {
        String $r1 = zzev("defaultGoogleSignInAccount");
        zzex("defaultGoogleSignInAccount");
        zzew($r1);
    }

    public void zzb(GoogleSignInAccount $r1, GoogleSignInOptions $r2) throws  {
        zzab.zzag($r1);
        zzab.zzag($r2);
        zzac("defaultGoogleSignInAccount", $r1.zzaee());
        zza($r1, $r2);
    }

    GoogleSignInAccount zzet(String $r1) throws  {
        if (TextUtils.isEmpty($r1)) {
            return null;
        }
        $r1 = zzev(zzad("googleSignInAccount", $r1));
        if ($r1 == null) {
            return null;
        }
        try {
            return GoogleSignInAccount.zzep($r1);
        } catch (JSONException e) {
            return null;
        }
    }

    GoogleSignInOptions zzeu(String $r1) throws  {
        if (TextUtils.isEmpty($r1)) {
            return null;
        }
        $r1 = zzev(zzad("googleSignInOptions", $r1));
        if ($r1 == null) {
            return null;
        }
        try {
            return GoogleSignInOptions.zzer($r1);
        } catch (JSONException e) {
            return null;
        }
    }

    protected String zzev(String $r1) throws  {
        this.gl.lock();
        try {
            $r1 = this.gm.getString($r1, null);
            return $r1;
        } finally {
            this.gl.unlock();
        }
    }

    void zzew(String $r1) throws  {
        if (!TextUtils.isEmpty($r1)) {
            zzex(zzad("googleSignInAccount", $r1));
            zzex(zzad("googleSignInOptions", $r1));
        }
    }

    protected void zzex(String $r1) throws  {
        this.gl.lock();
        try {
            this.gm.edit().remove($r1).apply();
        } finally {
            this.gl.unlock();
        }
    }
}
