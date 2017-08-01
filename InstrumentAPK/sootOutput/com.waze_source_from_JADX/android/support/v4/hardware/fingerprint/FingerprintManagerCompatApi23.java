package android.support.v4.hardware.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintManager.AuthenticationResult;
import android.os.CancellationSignal;
import android.os.Handler;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

public final class FingerprintManagerCompatApi23 {

    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int errMsgId, CharSequence errString) throws  {
        }

        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) throws  {
        }

        public void onAuthenticationSucceeded(AuthenticationResultInternal result) throws  {
        }

        public void onAuthenticationFailed() throws  {
        }
    }

    public static final class AuthenticationResultInternal {
        private CryptoObject mCryptoObject;

        public AuthenticationResultInternal(CryptoObject $r1) throws  {
            this.mCryptoObject = $r1;
        }

        public CryptoObject getCryptoObject() throws  {
            return this.mCryptoObject;
        }
    }

    public static class CryptoObject {
        private final Cipher mCipher;
        private final Mac mMac;
        private final Signature mSignature;

        public CryptoObject(Signature $r1) throws  {
            this.mSignature = $r1;
            this.mCipher = null;
            this.mMac = null;
        }

        public CryptoObject(Cipher $r1) throws  {
            this.mCipher = $r1;
            this.mSignature = null;
            this.mMac = null;
        }

        public CryptoObject(Mac $r1) throws  {
            this.mMac = $r1;
            this.mCipher = null;
            this.mSignature = null;
        }

        public Signature getSignature() throws  {
            return this.mSignature;
        }

        public Cipher getCipher() throws  {
            return this.mCipher;
        }

        public Mac getMac() throws  {
            return this.mMac;
        }
    }

    private static FingerprintManager getFingerprintManager(Context $r0) throws  {
        return (FingerprintManager) $r0.getSystemService(FingerprintManager.class);
    }

    public static boolean hasEnrolledFingerprints(Context $r0) throws  {
        return getFingerprintManager($r0).hasEnrolledFingerprints();
    }

    public static boolean isHardwareDetected(Context $r0) throws  {
        return getFingerprintManager($r0).isHardwareDetected();
    }

    public static void authenticate(Context $r0, CryptoObject $r1, int $i0, Object $r2, AuthenticationCallback $r3, Handler $r4) throws  {
        getFingerprintManager($r0).authenticate(wrapCryptoObject($r1), (CancellationSignal) $r2, $i0, wrapCallback($r3), $r4);
    }

    private static android.hardware.fingerprint.FingerprintManager.CryptoObject wrapCryptoObject(CryptoObject $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        if ($r0.getCipher() != null) {
            return new android.hardware.fingerprint.FingerprintManager.CryptoObject($r0.getCipher());
        }
        if ($r0.getSignature() != null) {
            return new android.hardware.fingerprint.FingerprintManager.CryptoObject($r0.getSignature());
        }
        return $r0.getMac() != null ? new android.hardware.fingerprint.FingerprintManager.CryptoObject($r0.getMac()) : null;
    }

    private static CryptoObject unwrapCryptoObject(android.hardware.fingerprint.FingerprintManager.CryptoObject $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        if ($r0.getCipher() != null) {
            return new CryptoObject($r0.getCipher());
        }
        if ($r0.getSignature() != null) {
            return new CryptoObject($r0.getSignature());
        }
        return $r0.getMac() != null ? new CryptoObject($r0.getMac()) : null;
    }

    private static android.hardware.fingerprint.FingerprintManager.AuthenticationCallback wrapCallback(final AuthenticationCallback $r0) throws  {
        return new android.hardware.fingerprint.FingerprintManager.AuthenticationCallback() {
            public void onAuthenticationError(int $i0, CharSequence $r1) throws  {
                $r0.onAuthenticationError($i0, $r1);
            }

            public void onAuthenticationHelp(int $i0, CharSequence $r1) throws  {
                $r0.onAuthenticationHelp($i0, $r1);
            }

            public void onAuthenticationSucceeded(AuthenticationResult $r1) throws  {
                $r0.onAuthenticationSucceeded(new AuthenticationResultInternal(FingerprintManagerCompatApi23.unwrapCryptoObject($r1.getCryptoObject())));
            }

            public void onAuthenticationFailed() throws  {
                $r0.onAuthenticationFailed();
            }
        };
    }
}
