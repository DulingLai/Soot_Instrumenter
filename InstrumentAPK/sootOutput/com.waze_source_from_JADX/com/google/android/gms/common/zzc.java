package com.google.android.gms.common;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzs;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.common.util.zzl;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzuh;
import dalvik.annotation.Signature;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
class zzc {
    private static zzv BG;
    private static Context BH;
    private static Set<zzs> BI;
    private static Set<zzs> BJ;

    /* compiled from: dalvik_source_com.waze.apk */
    static abstract class zza extends com.google.android.gms.common.internal.zzs.zza {
        private int BK;

        protected zza(byte[] $r1) throws  {
            boolean $z0 = false;
            if ($r1.length != 25) {
                int $i0 = $r1.length;
                String $r2 = String.valueOf(zzl.zza($r1, 0, $r1.length, false));
                Log.wtf("GoogleCertificates", new StringBuilder(String.valueOf($r2).length() + 51).append("Cert hash data has incorrect length (").append($i0).append("):\n").append($r2).toString(), new Exception());
                byte[] $r6 = Arrays.copyOfRange($r1, 0, 25);
                $r1 = $r6;
                if ($r6.length == 25) {
                    $z0 = true;
                }
                zzab.zzb($z0, "cert hash data has incorrect length. length=" + $r6.length);
            }
            this.BK = Arrays.hashCode($r1);
        }

        protected static byte[] zzgc(String $r0) throws  {
            try {
                return $r0.getBytes("ISO-8859-1");
            } catch (UnsupportedEncodingException $r3) {
                throw new AssertionError($r3);
            }
        }

        public boolean equals(Object $r1) throws  {
            if ($r1 == null || !($r1 instanceof zzs)) {
                return false;
            }
            zzs $r2 = (zzs) $r1;
            try {
                if ($r2.zzarf() != hashCode()) {
                    return false;
                }
                com.google.android.gms.dynamic.zzd $r3 = $r2.zzare();
                if ($r3 == null) {
                    return false;
                }
                return Arrays.equals(getBytes(), (byte[]) zze.zzae($r3));
            } catch (RemoteException e) {
                Log.e("GoogleCertificates", "iCertData failed to retrive data from remote");
                return false;
            }
        }

        abstract byte[] getBytes() throws ;

        public int hashCode() throws  {
            return this.BK;
        }

        public com.google.android.gms.dynamic.zzd zzare() throws  {
            return zze.zzan(getBytes());
        }

        public int zzarf() throws  {
            return hashCode();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class zzb extends zza {
        private final byte[] BL;

        zzb(byte[] $r1) throws  {
            super(Arrays.copyOfRange($r1, 0, 25));
            this.BL = $r1;
        }

        byte[] getBytes() throws  {
            return this.BL;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static abstract class zzc extends zza {
        private static final WeakReference<byte[]> BN = new WeakReference(null);
        private WeakReference<byte[]> BM = BN;

        zzc(byte[] $r1) throws  {
            super($r1);
        }

        byte[] getBytes() throws  {
            byte[] $r3;
            synchronized (this) {
                $r3 = (byte[]) this.BM.get();
                if ($r3 == null) {
                    byte[] $r4 = zzarg();
                    $r3 = $r4;
                    this.BM = new WeakReference($r4);
                }
            }
            return $r3;
        }

        protected abstract byte[] zzarg() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static final class zzd {
        static final zza[] BO = new zza[]{new C07211(zza.zzgc("0\u0004C0\u0003+ \u0003\u0002\u0001\u0002\u0002\t\u0000ÂàFdJ00")), new C07222(zza.zzgc("0\u0004¨0\u0003 \u0003\u0002\u0001\u0002\u0002\t\u0000Õ¸l}ÓNõ0"))};

        /* compiled from: dalvik_source_com.waze.apk */
        class C07211 extends zzc {
            C07211(byte[] $r1) throws  {
                super($r1);
            }

            protected byte[] zzarg() throws  {
                return zza.zzgc("0\u0004C0\u0003+ \u0003\u0002\u0001\u0002\u0002\t\u0000ÂàFdJ00\r\u0006\t*H÷\r\u0001\u0001\u0004\u0005\u00000t1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android0\u001e\u0017\r080821231334Z\u0017\r360107231334Z0t1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android0\u0001 0\r\u0006\t*H÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0001\r\u00000\u0001\b\u0002\u0001\u0001\u0000«V.\u0000Ø;¢\b®\no\u0012N)Ú\u0011ò«VÐXâÌ©\u0013\u0003é·TÓrö@§\u001b\u001dË\u0013\tgbNFV§wj\u0019=²å¿·$©\u001ew\u0018\u000ejG¤;3Ù`w\u00181EÌß{.XftÉáV[\u001fLjYU¿òQ¦=«ùÅ\\'\"\"Rèuäø\u0015Jd_qhÀ±¿Æ\u0012ê¿xWi»4ªyÜ~.¢vL®\u0007ØÁqT×î_d¥\u001aD¦\u0002ÂI\u0005AWÜ\u0002Í_\\\u000eUûï\u0019ûã'ð±Q\u0016Å o\u0019ÑõÄÛÂÖ¹?hÌ)yÇ\u000e\u0018«k;ÕÛU*\u000e;LßXûíÁº5à\u0003Á´±\rÒD¨î$ÿý38r«R!^Ú°ü\r\u000b\u0014[j¡y\u0002\u0001\u0003£Ù0Ö0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014Ç}Â!\u0017V%Óßkãä×¥0¦\u0006\u0003U\u001d#\u00040\u0014Ç}Â!\u0017V%Óßkãä×¥¡x¤v0t1\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00140\u0012\u0006\u0003U\u0004\n\u0013\u000bGoogle Inc.1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android\t\u0000ÂàFdJ00\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*H÷\r\u0001\u0001\u0004\u0005\u0000\u0003\u0001\u0001\u0000mÒRÎï0,6\nªÎÏòÌ©\u0004»]z\u0016aø®F²B\u0004ÐÿJhÇí\u001aS\u001eÄYZb<æ\u0007c±g)zzãW\u0012Ä\u0007ò\bðË\u0010)\u0012M{\u0010b\u0019ÀÊ>³ù­_¸qï&âñmDÈÙ l²ð\u0005»?âËD~s\u0010v­E³?`\tê\u0019Áaæ&Aª'\u001dýR(ÅÅ]ÛE'XÖaöÌ\fÌ·5.BLÄ6\\R52÷2Q7Y<JãAôÛAíÚ\r\u000b\u0010q§Ä@ðþ \u001c¶'ÊgCiÐ½/Ù\u0011ÿ\u0006Í¿,ú\u0010Ü\u000f:ãWbHÇïÆLqD\u0017B÷\u0005ÉÞW:õ[9\r×ý¹A1]_u0\u0011&ÿb\u0014\u0010Ài0");
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        class C07222 extends zzc {
            C07222(byte[] $r1) throws  {
                super($r1);
            }

            protected byte[] zzarg() throws  {
                return zza.zzgc("0\u0004¨0\u0003 \u0003\u0002\u0001\u0002\u0002\t\u0000Õ¸l}ÓNõ0\r\u0006\t*H÷\r\u0001\u0001\u0004\u0005\u000001\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*H÷\r\u0001\t\u0001\u0016\u0013android@android.com0\u001e\u0017\r080415233656Z\u0017\r350901233656Z01\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*H÷\r\u0001\t\u0001\u0016\u0013android@android.com0\u0001 0\r\u0006\t*H÷\r\u0001\u0001\u0001\u0005\u0000\u0003\u0001\r\u00000\u0001\b\u0002\u0001\u0001\u0000ÖÎ.\b\n¿â1MÑ³ÏÓ\u0018\\´=3ú\ftá½¶ÑÛ\u0013ö,\\9ßVøF=e¾ÀóÊBk\u0007Å¨íZ9ÁgçkÉ¹'K\u000b\"\u0000\u0019©)\u0015årÅm*0\u001b£oÅü\u0011:ÖËt5¡m#«}úîáeäß\u001f\n½§\nQlN\u0005\u0011Ê|\fU\u0017[ÃuùHÅj®\b¤O¦¤Ý}¿,\n5\"­\u0006¸Ì\u0018^±Uyîøm\b\u000b\u001daÀù¯±ÂëÑ\u0007êE«Ûh£Ç^TÇlSÔ\u000b\u0012\u001dç»Ó\u000eb\f\u0018áªaÛ¼Ý<d_/UóÔÃuì@p©?qQØ6pÁj\u001a¾^òÑ\u0018á¸®ó)ðf¿láD¬èm\u001c\u001b\u000f\u0002\u0001\u0003£ü0ù0\u001d\u0006\u0003U\u001d\u000e\u0004\u0016\u0004\u0014\u001cÅ¾LC<a:\u0015°L¼\u0003òOà²0É\u0006\u0003U\u001d#\u0004Á0¾\u0014\u001cÅ¾LC<a:\u0015°L¼\u0003òOà²¡¤01\u000b0\t\u0006\u0003U\u0004\u0006\u0013\u0002US1\u00130\u0011\u0006\u0003U\u0004\b\u0013\nCalifornia1\u00160\u0014\u0006\u0003U\u0004\u0007\u0013\rMountain View1\u00100\u000e\u0006\u0003U\u0004\n\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u000b\u0013\u0007Android1\u00100\u000e\u0006\u0003U\u0004\u0003\u0013\u0007Android1\"0 \u0006\t*H÷\r\u0001\t\u0001\u0016\u0013android@android.com\t\u0000Õ¸l}ÓNõ0\f\u0006\u0003U\u001d\u0013\u0004\u00050\u0003\u0001\u0001ÿ0\r\u0006\t*H÷\r\u0001\u0001\u0004\u0005\u0000\u0003\u0001\u0001\u0000\u0019Ó\fñ\u0005ûx?L\r}Ò##=@zÏÎ\u0000\b\u001d[×ÆéÖí k\u000e\u0011 \u0006Al¢D\u0013ÒkJ àõ$ÊÒ»\\nL¡\u0001j\u0015n¡ì]ÉZ^:\u0001\u00006ôHÕ\u0010¿.\u001eag:;åm¯\u000bw±Â)ãÂUãèL]#ïº\tËñ; +NZ\"É2cHJ#Òü)ú\u00199u3¯Øª\u0016\u000fBÂÐ\u0016>fCéÁ/ Á33[Àÿk\"ÞÑ­DB)¥9©Nï­«ÐeÎÒK>QåÝ{fx{ï\u0012þû¤Ä#ûOøÌIL\u0002ðõ\u0005\u0016\u0012ÿe)9>FêÅ»!òwÁQª_*¦'Ñè§\n¶\u00035iÞ;¿ÿ|©Ú>\u0012Cö\u000b");
            }
        }
    }

    static synchronized void init(Context $r0) throws  {
        synchronized (zzc.class) {
            try {
                if (BH != null) {
                    Log.w("GoogleCertificates", "GoogleCertificates has been initialized already");
                } else if ($r0 != null) {
                    BH = $r0.getApplicationContext();
                }
            } catch (Throwable th) {
                Class cls = zzc.class;
            }
        }
    }

    private static Set<zzs> zza(@Signature({"([", "Landroid/os/IBinder;", ")", "Ljava/util/Set", "<", "Lcom/google/android/gms/common/internal/zzs;", ">;"}) IBinder[] $r0) throws RemoteException {
        HashSet $r1 = new HashSet($i0);
        for (IBinder $r3 : $r0) {
            zzs $r2 = com.google.android.gms.common.internal.zzs.zza.zzgt($r3);
            if ($r2 == null) {
                Log.e("GoogleCertificates", "iCertData is null, skipping");
            } else {
                $r1.add($r2);
            }
        }
        return $r1;
    }

    private static boolean zzarb() throws  {
        zzab.zzag(BH);
        if (BG == null) {
            try {
                zzuh $r3 = zzuh.zza(BH, zzuh.ace, "com.google.android.gms.googlecertificates");
                Log.d("GoogleCertificates", "com.google.android.gms.googlecertificates module is loaded");
                BG = com.google.android.gms.common.internal.zzv.zza.zzgw($r3.zzii("com.google.android.gms.common.GoogleCertificatesImpl"));
            } catch (com.google.android.gms.internal.zzuh.zza $r5) {
                String $r6 = String.valueOf("Failed to load com.google.android.gms.googlecertificates: ");
                String $r7 = String.valueOf($r5.getMessage());
                Log.e("GoogleCertificates", $r7.length() != 0 ? $r6.concat($r7) : new String($r6));
                return false;
            }
        }
        return true;
    }

    static synchronized Set<zzs> zzarc() throws  {
        Set $r0;
        synchronized (zzc.class) {
            if (BI != null) {
                $r0 = BI;
            } else if (BG != null || zzarb()) {
                try {
                    com.google.android.gms.dynamic.zzd $r2 = BG.zzawz();
                    if ($r2 == null) {
                        Log.e("GoogleCertificates", "Failed to get google certificates from remote");
                        $r0 = Collections.EMPTY_SET;
                    } else {
                        BI = zza((IBinder[]) zze.zzae($r2));
                        for (zza $r6 : zzd.BO) {
                            BI.add($r6);
                        }
                        BI = Collections.unmodifiableSet(BI);
                        $r0 = BI;
                    }
                } catch (RemoteException e) {
                    Log.e("GoogleCertificates", "Failed to retrieve google certificates");
                } catch (Throwable th) {
                    Class cls = zzc.class;
                }
            } else {
                $r0 = Collections.EMPTY_SET;
            }
        }
        return $r0;
    }

    static synchronized Set<zzs> zzard() throws  {
        Set $r0;
        synchronized (zzc.class) {
            try {
                if (BJ != null) {
                    $r0 = BJ;
                } else if (BG != null || zzarb()) {
                    com.google.android.gms.dynamic.zzd $r2 = BG.zzaxa();
                    if ($r2 == null) {
                        Log.d("GoogleCertificates", "Failed to get google release certificates from remote");
                        $r0 = Collections.EMPTY_SET;
                    } else {
                        BJ = zza((IBinder[]) zze.zzae($r2));
                        BJ.add(zzd.BO[0]);
                        BJ = Collections.unmodifiableSet(BJ);
                        $r0 = BJ;
                    }
                } else {
                    $r0 = Collections.EMPTY_SET;
                }
            } catch (RemoteException e) {
                Log.e("GoogleCertificates", "Failed to retrieve google release certificates");
            } catch (Throwable th) {
                Class cls = zzc.class;
            }
        }
        return $r0;
    }
}
