package com.google.android.gms.internal;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: dalvik_source_com.waze.apk */
final class zzauj implements zzaut<Date>, zzavc<Date> {
    private final DateFormat bXg;
    private final DateFormat bXh;
    private final DateFormat bXi;

    zzauj() throws  {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    public zzauj(int $i0, int $i1) throws  {
        this(DateFormat.getDateTimeInstance($i0, $i1, Locale.US), DateFormat.getDateTimeInstance($i0, $i1));
    }

    zzauj(String $r1) throws  {
        this(new SimpleDateFormat($r1, Locale.US), new SimpleDateFormat($r1));
    }

    zzauj(DateFormat $r1, DateFormat $r2) throws  {
        this.bXg = $r1;
        this.bXh = $r2;
        this.bXi = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        this.bXi.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Date zza(com.google.android.gms.internal.zzauu r10) throws  {
        /*
        r9 = this;
        r0 = r9.bXh;
        monitor-enter(r0);
        r1 = r9.bXh;
        r2 = r10.hc();	 Catch:{ ParseException -> 0x000f }
        r3 = r1.parse(r2);	 Catch:{ ParseException -> 0x000f }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001c }
        return r3;
    L_0x000f:
        r4 = move-exception;
        r1 = r9.bXg;
        r2 = r10.hc();	 Catch:{ ParseException -> 0x001f }
        r3 = r1.parse(r2);	 Catch:{ ParseException -> 0x001f }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001c }
        return r3;
    L_0x001c:
        r5 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001c }
        throw r5;
    L_0x001f:
        r6 = move-exception;
        r1 = r9.bXi;
        r2 = r10.hc();	 Catch:{ ParseException -> 0x002c }
        r3 = r1.parse(r2);	 Catch:{ ParseException -> 0x002c }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001c }
        return r3;
    L_0x002c:
        r7 = move-exception;
        r8 = new com.google.android.gms.internal.zzavd;	 Catch:{ Throwable -> 0x001c }
        r2 = r10.hc();	 Catch:{ Throwable -> 0x001c }
        r8.<init>(r2, r7);	 Catch:{ Throwable -> 0x001c }
        throw r8;	 Catch:{ Throwable -> 0x001c }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzauj.zza(com.google.android.gms.internal.zzauu):java.util.Date");
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder();
        $r1.append(zzauj.class.getSimpleName());
        $r1.append('(').append(this.bXh.getClass().getSimpleName()).append(')');
        return $r1.toString();
    }

    public zzauu zza(Date $r1, Type type, zzavb com_google_android_gms_internal_zzavb) throws  {
        zzava $r5;
        synchronized (this.bXh) {
            $r5 = new zzava(this.bXg.format($r1));
        }
        return $r5;
    }

    public Date zza(zzauu $r1, Type $r2, zzaus com_google_android_gms_internal_zzaus) throws zzauy {
        if ($r1 instanceof zzava) {
            Date $r5 = zza($r1);
            if ($r2 == Date.class) {
                return $r5;
            }
            if ($r2 == Timestamp.class) {
                return new Timestamp($r5.getTime());
            }
            if ($r2 == java.sql.Date.class) {
                return new java.sql.Date($r5.getTime());
            }
            String $r10 = String.valueOf(getClass());
            String $r11 = String.valueOf($r2);
            int $i1 = (String.valueOf($r10).length() + 23) + String.valueOf($r11).length();
            int i = $i1;
            throw new IllegalArgumentException($r10 + " cannot deserialize to " + $r11);
        }
        throw new zzauy("The date should be a string value");
    }

    public /* synthetic */ Object zzb(zzauu $r1, Type $r2, zzaus $r3) throws zzauy {
        return zza($r1, $r2, $r3);
    }
}
