package com.google.android.gms.iid;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/* compiled from: dalvik_source_com.waze.apk */
public class zza {
    public static KeyPair zzbrr() throws  {
        try {
            KeyPairGenerator $r0 = KeyPairGenerator.getInstance("RSA");
            $r0.initialize(2048);
            return $r0.generateKeyPair();
        } catch (NoSuchAlgorithmException $r2) {
            throw new AssertionError($r2);
        }
    }
}
