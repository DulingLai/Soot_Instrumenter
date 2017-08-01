package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import com.google.android.gms.internal.zzwj;
import java.util.concurrent.Callable;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zza<T> {

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zza extends zza<Boolean> {

        /* compiled from: dalvik_source_com.waze.apk */
        class C07301 implements Callable<Boolean> {
            final /* synthetic */ String Ns;
            final /* synthetic */ SharedPreferences afW;
            final /* synthetic */ Boolean afX;

            C07301(SharedPreferences $r1, String $r2, Boolean $r3) throws  {
                this.afW = $r1;
                this.Ns = $r2;
                this.afX = $r3;
            }

            public /* synthetic */ Object call() throws Exception {
                return zztt();
            }

            public Boolean zztt() throws  {
                return Boolean.valueOf(this.afW.getBoolean(this.Ns, this.afX.booleanValue()));
            }
        }

        public static Boolean zza(SharedPreferences $r0, String $r1, Boolean $r2) throws  {
            return (Boolean) zzwj.zzb(new C07301($r0, $r1, $r2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zzb extends zza<Integer> {

        /* compiled from: dalvik_source_com.waze.apk */
        class C07311 implements Callable<Integer> {
            final /* synthetic */ String Ns;
            final /* synthetic */ SharedPreferences afW;
            final /* synthetic */ Integer afY;

            C07311(SharedPreferences $r1, String $r2, Integer $r3) throws  {
                this.afW = $r1;
                this.Ns = $r2;
                this.afY = $r3;
            }

            public /* synthetic */ Object call() throws Exception {
                return zzale();
            }

            public Integer zzale() throws  {
                return Integer.valueOf(this.afW.getInt(this.Ns, this.afY.intValue()));
            }
        }

        public static Integer zza(SharedPreferences $r0, String $r1, Integer $r2) throws  {
            return (Integer) zzwj.zzb(new C07311($r0, $r1, $r2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zzc extends zza<Long> {

        /* compiled from: dalvik_source_com.waze.apk */
        class C07321 implements Callable<Long> {
            final /* synthetic */ String Ns;
            final /* synthetic */ SharedPreferences afW;
            final /* synthetic */ Long afZ;

            C07321(SharedPreferences $r1, String $r2, Long $r3) throws  {
                this.afW = $r1;
                this.Ns = $r2;
                this.afZ = $r3;
            }

            public /* synthetic */ Object call() throws Exception {
                return zzbli();
            }

            public Long zzbli() throws  {
                return Long.valueOf(this.afW.getLong(this.Ns, this.afZ.longValue()));
            }
        }

        public static Long zza(SharedPreferences $r0, String $r1, Long $r2) throws  {
            return (Long) zzwj.zzb(new C07321($r0, $r1, $r2));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zzd extends zza<String> {

        /* compiled from: dalvik_source_com.waze.apk */
        class C07331 implements Callable<String> {
            final /* synthetic */ String Ns;
            final /* synthetic */ SharedPreferences afW;
            final /* synthetic */ String aga;

            C07331(SharedPreferences $r1, String $r2, String $r3) throws  {
                this.afW = $r1;
                this.Ns = $r2;
                this.aga = $r3;
            }

            public /* synthetic */ Object call() throws Exception {
                return zzzx();
            }

            public String zzzx() throws  {
                return this.afW.getString(this.Ns, this.aga);
            }
        }

        public static String zza(SharedPreferences $r0, String $r1, String $r2) throws  {
            return (String) zzwj.zzb(new C07331($r0, $r1, $r2));
        }
    }
}
