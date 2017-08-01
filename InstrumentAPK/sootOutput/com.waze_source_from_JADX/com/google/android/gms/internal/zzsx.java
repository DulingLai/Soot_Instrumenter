package com.google.android.gms.internal;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* compiled from: dalvik_source_com.waze.apk */
public class zzsx extends SSLSocketFactory {
    private static final TrustManager[] Kv = new TrustManager[]{new C08601()};
    byte[] KA = null;
    byte[] KB = null;
    PrivateKey KC = null;
    final int KD;
    final boolean KE;
    final boolean KF;
    final String KG;
    SSLSocketFactory Kw = null;
    SSLSocketFactory Kx = null;
    TrustManager[] Ky = null;
    KeyManager[] Kz = null;
    final Context mContext;

    /* compiled from: dalvik_source_com.waze.apk */
    class C08601 implements X509TrustManager {
        C08601() throws  {
        }

        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws  {
        }

        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws  {
        }

        public X509Certificate[] getAcceptedIssuers() throws  {
            return null;
        }
    }

    private zzsx(Context $r1, int $i0, boolean $z0, boolean $z1, String $r2) throws  {
        this.mContext = $r1.getApplicationContext();
        this.KD = $i0;
        this.KE = $z0;
        this.KF = $z1;
        this.KG = $r2;
    }

    public static void zza(Socket $r2, String $r0) throws IOException {
        if ($r2 instanceof SSLSocket) {
            SSLSocket $r4 = (SSLSocket) $r2;
            $r4.startHandshake();
            SSLSession $r5 = $r4.getSession();
            if ($r5 == null) {
                throw new SSLException("Cannot verify SSL socket without session");
            } else if (!HttpsURLConnection.getDefaultHostnameVerifier().verify($r0, $r5)) {
                String $r1 = "Cannot verify hostname: ";
                $r0 = String.valueOf($r0);
                throw new SSLPeerUnverifiedException($r0.length() != 0 ? $r1.concat($r0) : new String("Cannot verify hostname: "));
            } else {
                return;
            }
        }
        throw new IllegalArgumentException("Attempt to verify non-SSL socket");
    }

    private synchronized SSLSocketFactory zzaxn() throws  {
        SSLSocketFactory $r1;
        if (this.KF) {
            if (this.KG != null) {
                if (this.Kx == null) {
                    this.Kx = zzsy.zzaxo().zza(this.mContext, null, null, this.KG);
                }
            } else if (this.Kx == null) {
                this.Kx = zzsy.zzaxo().zza(this.mContext, null, null, this.KE);
            }
            $r1 = this.Kx;
        } else {
            if (this.Kw == null) {
                Log.w("SSLCertificateSocketFactory", "Bypassing SSL security checks at caller's request");
                this.Kw = zzsy.zzaxo().zza(this.mContext, null, Kv, this.KE);
            }
            $r1 = this.Kw;
        }
        return $r1;
    }

    public static SSLSocketFactory zzc(int $i0, Context $r0) throws  {
        return new zzsx($r0, $i0, true, true, null);
    }

    public Socket createSocket() throws IOException {
        Socket $r2 = zzaxn().createSocket();
        zza($r2, null);
        zzb($r2, null);
        zza($r2, this.KD);
        zza($r2, null);
        return $r2;
    }

    public Socket createSocket(String $r1, int $i0) throws IOException {
        Socket $r3 = zzaxn().createSocket($r1, $i0);
        zza($r3, null);
        zzb($r3, null);
        zza($r3, this.KD);
        zza($r3, null);
        if (!this.KF) {
            return $r3;
        }
        zza($r3, $r1);
        return $r3;
    }

    public Socket createSocket(String $r1, int $i0, InetAddress $r2, int $i1) throws IOException {
        Socket $r4 = zzaxn().createSocket($r1, $i0, $r2, $i1);
        zza($r4, null);
        zzb($r4, null);
        zza($r4, this.KD);
        zza($r4, null);
        if (!this.KF) {
            return $r4;
        }
        zza($r4, $r1);
        return $r4;
    }

    public Socket createSocket(InetAddress $r1, int $i0) throws IOException {
        Socket $r3 = zzaxn().createSocket($r1, $i0);
        zza($r3, null);
        zzb($r3, null);
        zza($r3, this.KD);
        zza($r3, null);
        return $r3;
    }

    public Socket createSocket(InetAddress $r1, int $i0, InetAddress $r2, int $i1) throws IOException {
        Socket $r4 = zzaxn().createSocket($r1, $i0, $r2, $i1);
        zza($r4, null);
        zzb($r4, null);
        zza($r4, this.KD);
        zza($r4, null);
        return $r4;
    }

    public Socket createSocket(Socket $r1, String $r2, int $i0, boolean $z0) throws IOException {
        $r1 = zzaxn().createSocket($r1, $r2, $i0, $z0);
        zza($r1, null);
        zzb($r1, null);
        zza($r1, this.KD);
        zza($r1, null);
        if (!this.KF) {
            return $r1;
        }
        zza($r1, $r2);
        return $r1;
    }

    public String[] getDefaultCipherSuites() throws  {
        return zzaxn().getDefaultCipherSuites();
    }

    public String[] getSupportedCipherSuites() throws  {
        return zzaxn().getSupportedCipherSuites();
    }

    void zza(Socket $r1, int $i0) throws  {
        String $r11;
        Throwable $r14;
        if ($r1 != null) {
            try {
                Class $r2 = $r1.getClass();
                Class[] $r3 = new Class[1];
                $r3[0] = Integer.TYPE;
                $r2.getMethod("setHandshakeTimeout", $r3).invoke($r1, new Object[]{Integer.valueOf($i0)});
            } catch (InvocationTargetException $r8) {
                Throwable $r9 = $r8.getCause();
                if ($r9 instanceof RuntimeException) {
                    throw ((RuntimeException) $r9);
                }
                $r11 = String.valueOf($r1.getClass());
                throw new RuntimeException(new StringBuilder(String.valueOf($r11).length() + 46).append("Failed to invoke setSocketHandshakeTimeout on ").append($r11).toString(), $r8);
            } catch (NoSuchMethodException e) {
                $r14 = e;
                $r11 = String.valueOf($r1.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf($r11).length() + 45).append($r11).append(" does not implement setSocketHandshakeTimeout").toString(), $r14);
            } catch (IllegalAccessException e2) {
                $r14 = e2;
                $r11 = String.valueOf($r1.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf($r11).length() + 45).append($r11).append(" does not implement setSocketHandshakeTimeout").toString(), $r14);
            }
        }
    }

    void zza(Socket $r1, PrivateKey $r2) throws  {
        String $r10;
        Throwable $r13;
        if ($r1 != null) {
            try {
                Class $r3 = $r1.getClass();
                Class[] $r4 = new Class[1];
                $r4[0] = PrivateKey.class;
                Method $r5 = $r3.getMethod("setChannelIdPrivateKey", $r4);
                Object[] $r6 = new Object[1];
                $r6[0] = $r2;
                $r5.invoke($r1, $r6);
            } catch (InvocationTargetException $r7) {
                Throwable $r8 = $r7.getCause();
                if ($r8 instanceof RuntimeException) {
                    throw ((RuntimeException) $r8);
                }
                $r10 = String.valueOf($r1.getClass());
                throw new RuntimeException(new StringBuilder(String.valueOf($r10).length() + 43).append("Failed to invoke setChannelIdPrivateKey on ").append($r10).toString(), $r7);
            } catch (NoSuchMethodException e) {
                $r13 = e;
                $r10 = String.valueOf($r1.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf($r10).length() + 42).append($r10).append(" does not implement setChannelIdPrivateKey").toString(), $r13);
            } catch (IllegalAccessException e2) {
                $r13 = e2;
                $r10 = String.valueOf($r1.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf($r10).length() + 42).append($r10).append(" does not implement setChannelIdPrivateKey").toString(), $r13);
            }
        }
    }

    void zza(Socket $r1, byte[] $r2) throws  {
        String $r10;
        Throwable $r13;
        if ($r1 != null) {
            try {
                Class $r3 = $r1.getClass();
                Class[] $r4 = new Class[1];
                $r4[0] = byte[].class;
                Method $r5 = $r3.getMethod("setNpnProtocols", $r4);
                Object[] $r6 = new Object[1];
                $r6[0] = $r2;
                $r5.invoke($r1, $r6);
            } catch (InvocationTargetException $r7) {
                Throwable $r8 = $r7.getCause();
                if ($r8 instanceof RuntimeException) {
                    throw ((RuntimeException) $r8);
                }
                $r10 = String.valueOf($r1.getClass());
                throw new RuntimeException(new StringBuilder(String.valueOf($r10).length() + 36).append("Failed to invoke setNpnProtocols on ").append($r10).toString(), $r7);
            } catch (NoSuchMethodException e) {
                $r13 = e;
                $r10 = String.valueOf($r1.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf($r10).length() + 35).append($r10).append(" does not implement setNpnProtocols").toString(), $r13);
            } catch (IllegalAccessException e2) {
                $r13 = e2;
                $r10 = String.valueOf($r1.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf($r10).length() + 35).append($r10).append(" does not implement setNpnProtocols").toString(), $r13);
            }
        }
    }

    void zzb(Socket $r1, byte[] $r2) throws  {
        String $r10;
        Throwable $r13;
        if ($r1 != null) {
            try {
                Class $r3 = $r1.getClass();
                Class[] $r4 = new Class[1];
                $r4[0] = byte[].class;
                Method $r5 = $r3.getMethod("setAlpnProtocols", $r4);
                Object[] $r6 = new Object[1];
                $r6[0] = $r2;
                $r5.invoke($r1, $r6);
            } catch (InvocationTargetException $r7) {
                Throwable $r8 = $r7.getCause();
                if ($r8 instanceof RuntimeException) {
                    throw ((RuntimeException) $r8);
                }
                $r10 = String.valueOf($r1.getClass());
                throw new RuntimeException(new StringBuilder(String.valueOf($r10).length() + 37).append("Failed to invoke setAlpnProtocols on ").append($r10).toString(), $r7);
            } catch (NoSuchMethodException e) {
                $r13 = e;
                $r10 = String.valueOf($r1.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf($r10).length() + 36).append($r10).append(" does not implement setAlpnProtocols").toString(), $r13);
            } catch (IllegalAccessException e2) {
                $r13 = e2;
                $r10 = String.valueOf($r1.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf($r10).length() + 36).append($r10).append(" does not implement setAlpnProtocols").toString(), $r13);
            }
        }
    }
}
