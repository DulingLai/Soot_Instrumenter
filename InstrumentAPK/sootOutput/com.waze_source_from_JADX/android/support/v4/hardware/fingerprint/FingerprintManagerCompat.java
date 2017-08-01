package android.support.v4.hardware.fingerprint;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.AuthenticationResultInternal;
import android.support.v4.os.CancellationSignal;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

public final class FingerprintManagerCompat {
    static final FingerprintManagerCompatImpl IMPL;
    private Context mContext;

    private interface FingerprintManagerCompatImpl {
        void authenticate(Context context, CryptoObject cryptoObject, int i, CancellationSignal cancellationSignal, AuthenticationCallback authenticationCallback, Handler handler) throws ;

        boolean hasEnrolledFingerprints(Context context) throws ;

        boolean isHardwareDetected(Context context) throws ;
    }

    private static class Api23FingerprintManagerCompatImpl implements FingerprintManagerCompatImpl {
        public boolean hasEnrolledFingerprints(Context $r1) throws  {
            return FingerprintManagerCompatApi23.hasEnrolledFingerprints($r1);
        }

        public boolean isHardwareDetected(Context $r1) throws  {
            return FingerprintManagerCompatApi23.isHardwareDetected($r1);
        }

        public void authenticate(Context $r1, CryptoObject $r2, int $i0, CancellationSignal $r3, AuthenticationCallback $r4, Handler $r5) throws  {
            FingerprintManagerCompatApi23.authenticate($r1, wrapCryptoObject($r2), $i0, $r3 != null ? $r3.getCancellationSignalObject() : null, wrapCallback($r4), $r5);
        }

        private static android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject wrapCryptoObject(CryptoObject $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            if ($r0.getCipher() != null) {
                return new android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject($r0.getCipher());
            }
            if ($r0.getSignature() != null) {
                return new android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject($r0.getSignature());
            }
            return $r0.getMac() != null ? new android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject($r0.getMac()) : null;
        }

        private static CryptoObject unwrapCryptoObject(android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.CryptoObject $r0) throws  {
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

        private static android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.AuthenticationCallback wrapCallback(final AuthenticationCallback $r0) throws  {
            return new android.support.v4.hardware.fingerprint.FingerprintManagerCompatApi23.AuthenticationCallback() {
                public void onAuthenticationError(int $i0, CharSequence $r1) throws  {
                    $r0.onAuthenticationError($i0, $r1);
                }

                public void onAuthenticationHelp(int $i0, CharSequence $r1) throws  {
                    $r0.onAuthenticationHelp($i0, $r1);
                }

                public void onAuthenticationSucceeded(AuthenticationResultInternal $r1) throws  {
                    $r0.onAuthenticationSucceeded(new AuthenticationResult(Api23FingerprintManagerCompatImpl.unwrapCryptoObject($r1.getCryptoObject())));
                }

                public void onAuthenticationFailed() throws  {
                    $r0.onAuthenticationFailed();
                }
            };
        }
    }

    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int errMsgId, CharSequence errString) throws  {
        }

        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) throws  {
        }

        public void onAuthenticationSucceeded(AuthenticationResult result) throws  {
        }

        public void onAuthenticationFailed() throws  {
        }
    }

    public static final class AuthenticationResult {
        private CryptoObject mCryptoObject;

        public AuthenticationResult(CryptoObject $r1) throws  {
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

    private static class LegacyFingerprintManagerCompatImpl implements FingerprintManagerCompatImpl {
        public boolean hasEnrolledFingerprints(Context context) throws  {
            return false;
        }

        public boolean isHardwareDetected(Context context) throws  {
            return false;
        }

        public void authenticate(Context context, CryptoObject crypto, int flags, CancellationSignal cancel, AuthenticationCallback callback, Handler handler) throws  {
        }
    }

    public static FingerprintManagerCompat from(Context $r0) throws  {
        return new FingerprintManagerCompat($r0);
    }

    private FingerprintManagerCompat(Context $r1) throws  {
        this.mContext = $r1;
    }

    static {
        if (VERSION.SDK_INT >= 23) {
            IMPL = new Api23FingerprintManagerCompatImpl();
        } else {
            IMPL = new LegacyFingerprintManagerCompatImpl();
        }
    }

    public boolean hasEnrolledFingerprints() throws  {
        return IMPL.hasEnrolledFingerprints(this.mContext);
    }

    public boolean isHardwareDetected() throws  {
        return IMPL.isHardwareDetected(this.mContext);
    }

    public void authenticate(@Nullable CryptoObject $r1, int $i0, @Nullable CancellationSignal $r2, @NonNull AuthenticationCallback $r3, @Nullable Handler $r4) throws  {
        IMPL.authenticate(this.mContext, $r1, $i0, $r2, $r3, $r4);
    }
}
