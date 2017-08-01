package com.spotify.sdk.android.authentication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SpotifyNativeAuthUtil {
    static final String EXTRA_ERROR = "ERROR";
    static final String EXTRA_REPLY = "REPLY";
    private static final String EXTRA_VERSION = "VERSION";
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    static final String KEY_ACCESS_TOKEN = "ACCESS_TOKEN";
    static final String KEY_AUTHORIZATION_CODE = "AUTHORIZATION_CODE";
    static final String KEY_CLIENT_ID = "CLIENT_ID";
    static final String KEY_EXPIRES_IN = "EXPIRES_IN";
    static final String KEY_REDIRECT_URI = "REDIRECT_URI";
    static final String KEY_REQUESTED_SCOPES = "SCOPES";
    static final String KEY_RESPONSE_TYPE = "RESPONSE_TYPE";
    private static final int PROTOCOL_VERSION = 1;
    static final String RESPONSE_TYPE_CODE = "code";
    static final String RESPONSE_TYPE_TOKEN = "token";
    private static final String SPOTIFY_AUTH_ACTIVITY_ACTION = "com.spotify.sso.action.START_AUTH_FLOW";
    private static final String SPOTIFY_PACKAGE_NAME = "com.spotify.music";
    private static final String[] SPOTIFY_PACKAGE_SUFFIXES = new String[]{".debug", ".canary", ".partners", ""};
    private static final String[] SPOTIFY_SIGNATURE_HASH = new String[]{"80abdd17dcc4cb3a33815d354355bf87c9378624", "88df4d670ed5e01fc7b3eff13b63258628ff5a00", "d834ae340d1e854c5f4092722f9788216d9221e5", "1cbedd9e7345f64649bad2b493a20d9eea955352", "4b3d76a2de89033ea830f476a1f815692938e33b"};
    private Activity mContextActivity;
    private AuthenticationRequest mRequest;

    public SpotifyNativeAuthUtil(Activity $r1, AuthenticationRequest $r2) throws  {
        this.mContextActivity = $r1;
        this.mRequest = $r2;
    }

    public boolean startAuthActivity() throws  {
        Intent $r2 = createAuthActivityIntent();
        if ($r2 == null) {
            return false;
        }
        $r2.putExtra(EXTRA_VERSION, 1);
        $r2.putExtra(KEY_CLIENT_ID, this.mRequest.getClientId());
        $r2.putExtra(KEY_REDIRECT_URI, this.mRequest.getRedirectUri());
        $r2.putExtra(KEY_RESPONSE_TYPE, this.mRequest.getResponseType());
        $r2.putExtra(KEY_REQUESTED_SCOPES, this.mRequest.getScopes());
        try {
            this.mContextActivity.startActivityForResult($r2, 1138);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private Intent createAuthActivityIntent() throws  {
        Intent $r3 = null;
        for (String $r1 : SPOTIFY_PACKAGE_SUFFIXES) {
            Intent $r5 = tryResolveActivity("com.spotify.music" + $r1);
            $r3 = $r5;
            if ($r5 != null) {
                return $r5;
            }
        }
        return $r3;
    }

    private Intent tryResolveActivity(String $r1) throws  {
        Intent $r2 = new Intent(SPOTIFY_AUTH_ACTIVITY_ACTION);
        $r2.setPackage($r1);
        ComponentName $r5 = $r2.resolveActivity(this.mContextActivity.getPackageManager());
        if ($r5 == null) {
            return null;
        }
        return !validateSignature($r5.getPackageName()) ? null : $r2;
    }

    @SuppressLint({"PackageManagerGetSignatures"})
    private boolean validateSignature(String $r1) throws  {
        PackageInfo $r7 = this.mContextActivity.getPackageManager().getPackageInfo($r1, 64);
        if ($r7.signatures == null) {
            return false;
        }
        Signature[] $r3 = $r7.signatures;
        int $i0 = $r3.length;
        int $i2 = 0;
        while ($i2 < $i0) {
            try {
                String $r8 = sha1Hash($r3[$i2].toCharsString());
                for (String $r12 : SPOTIFY_SIGNATURE_HASH) {
                    if ($r12.equals($r8)) {
                        return true;
                    }
                }
                $i2++;
            } catch (NameNotFoundException e) {
                return false;
            }
        }
        return false;
    }

    public void stopAuthActivity() throws  {
        this.mContextActivity.finishActivity(1138);
    }

    private static String sha1Hash(String $r0) throws  {
        try {
            MessageDigest $r1 = MessageDigest.getInstance("SHA-1");
            byte[] $r2 = $r0.getBytes("UTF-8");
            $r1.update($r2, 0, $r2.length);
            return bytesToHex($r1.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (UnsupportedEncodingException e2) {
            return null;
        }
    }

    private static String bytesToHex(byte[] $r0) throws  {
        char[] $r1 = new char[($r0.length * 2)];
        for (int $i1 = 0; $i1 < $r0.length; $i1++) {
            short $s0 = $r0[$i1] & (short) 255;
            $r1[$i1 * 2] = HEX_ARRAY[$s0 >>> (short) 4];
            $r1[($i1 * 2) + 1] = HEX_ARRAY[$s0 & (short) 15];
        }
        return new String($r1);
    }
}
