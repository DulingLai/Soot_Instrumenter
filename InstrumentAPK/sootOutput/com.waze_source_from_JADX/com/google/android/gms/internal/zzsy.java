package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.dynamic.zzg.zza;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/* compiled from: dalvik_source_com.waze.apk */
public class zzsy extends zzg<zzsw> {
    private static zzsy KH;

    protected zzsy() throws  {
        super("com.google.android.gms.common.net.SocketFactoryCreatorImpl");
    }

    public static zzsy zzaxo() throws  {
        if (KH == null) {
            KH = new zzsy();
        }
        return KH;
    }

    public SSLSocketFactory zza(Context $r1, KeyManager[] $r2, TrustManager[] $r3, String $r4) throws  {
        Exception $r11;
        try {
            return (SSLSocketFactory) zze.zzae(((zzsw) zzcm($r1)).zza(zze.zzan($r1), zze.zzan($r2), zze.zzan($r3), $r4));
        } catch (RemoteException e) {
            $r11 = e;
            throw new RuntimeException($r11);
        } catch (zza e2) {
            $r11 = e2;
            throw new RuntimeException($r11);
        }
    }

    public SSLSocketFactory zza(Context $r1, KeyManager[] $r2, TrustManager[] $r3, boolean $z0) throws  {
        Exception $r10;
        try {
            return (SSLSocketFactory) zze.zzae(((zzsw) zzcm($r1)).zza(zze.zzan($r1), zze.zzan($r2), zze.zzan($r3), $z0));
        } catch (RemoteException e) {
            $r10 = e;
            throw new RuntimeException($r10);
        } catch (zza e2) {
            $r10 = e2;
            throw new RuntimeException($r10);
        }
    }

    protected /* synthetic */ Object zzd(IBinder $r1) throws  {
        return zzhe($r1);
    }

    protected zzsw zzhe(IBinder $r1) throws  {
        return zzsw.zza.zzhd($r1);
    }
}
