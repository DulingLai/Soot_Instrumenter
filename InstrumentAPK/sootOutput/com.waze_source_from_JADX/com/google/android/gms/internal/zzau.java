package com.google.android.gms.internal;

import com.google.android.gms.auth.firstparty.proximity.data.PermitAccess;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: dalvik_source_com.waze.apk */
public class zzau {
    private static Cipher zzagb = null;
    private static final Object zzagc = new Object();
    private static final Object zzagd = new Object();
    private final SecureRandom zzaga;

    /* compiled from: dalvik_source_com.waze.apk */
    public class zza extends Exception {
        final /* synthetic */ zzau zzage;

        public zza(zzau $r1) throws  {
            this.zzage = $r1;
        }

        public zza(zzau $r1, Throwable $r2) throws  {
            this.zzage = $r1;
            super($r2);
        }
    }

    public zzau(SecureRandom $r1) throws  {
        this.zzaga = $r1;
    }

    private Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher $r2;
        synchronized (zzagd) {
            if (zzagb == null) {
                zzagb = Cipher.getInstance("AES/CBC/PKCS5Padding");
            }
            $r2 = zzagb;
        }
        return $r2;
    }

    static void zzh(byte[] $r0) throws  {
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            $r0[$i0] = (byte) ($r0[$i0] ^ (byte) 68);
        }
    }

    public byte[] zzc(byte[] $r1, String $r2) throws zza {
        if ($r1.length != 16) {
            throw new zza(this);
        }
        try {
            byte[] $r5 = zzaj.zza($r2, false);
            if ($r5.length <= 16) {
                throw new zza(this);
            }
            ByteBuffer $r7 = ByteBuffer.allocate($r5.length);
            $r7.put($r5);
            $r7.flip();
            byte[] $r8 = new byte[16];
            $r5 = new byte[($r5.length - 16)];
            $r7.get($r8);
            $r7.get($r5);
            SecretKeySpec $r9 = new SecretKeySpec($r1, PermitAccess.TYPE_AES);
            synchronized (zzagc) {
                getCipher().init(2, $r9, new IvParameterSpec($r8));
                $r1 = getCipher().doFinal($r5);
            }
            return $r1;
        } catch (NoSuchAlgorithmException $r6) {
            throw new zza(this, $r6);
        } catch (InvalidKeyException $r13) {
            throw new zza(this, $r13);
        } catch (Throwable $r14) {
            throw new zza(this, $r14);
        } catch (Throwable $r15) {
            throw new zza(this, $r15);
        } catch (Throwable $r16) {
            throw new zza(this, $r16);
        } catch (Throwable $r17) {
            throw new zza(this, $r17);
        } catch (Throwable $r18) {
            throw new zza(this, $r18);
        }
    }

    public String zzd(byte[] $r1, byte[] $r2) throws zza {
        if ($r1.length != 16) {
            throw new zza(this);
        }
        try {
            SecretKeySpec $r5 = new SecretKeySpec($r1, PermitAccess.TYPE_AES);
            synchronized (zzagc) {
                getCipher().init(1, $r5, this.zzaga);
                $r1 = getCipher().doFinal($r2);
                $r2 = getCipher().getIV();
            }
            int $i0 = $r1.length + $r2.length;
            ByteBuffer $r8 = ByteBuffer.allocate($i0);
            $r8.put($r2).put($r1);
            $r8.flip();
            byte[] $r12 = new byte[$i0];
            $r1 = $r12;
            $r8.get($r12);
            return zzaj.zza($r1, false);
        } catch (NoSuchAlgorithmException $r122) {
            throw new zza(this, $r122);
        } catch (Throwable $r13) {
            throw new zza(this, $r13);
        } catch (Throwable $r14) {
            throw new zza(this, $r14);
        } catch (Throwable $r15) {
            throw new zza(this, $r15);
        } catch (Throwable $r16) {
            throw new zza(this, $r16);
        }
    }

    public byte[] zzl(String $r1) throws zza {
        try {
            byte[] $r2 = zzaj.zza($r1, false);
            if ($r2.length != 32) {
                throw new zza(this);
            }
            ByteBuffer $r5 = ByteBuffer.wrap($r2, 4, 16);
            $r2 = new byte[16];
            $r5.get($r2);
            zzh($r2);
            return $r2;
        } catch (IllegalArgumentException $r4) {
            throw new zza(this, $r4);
        }
    }
}
