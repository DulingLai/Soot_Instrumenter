package com.google.android.gms.people.internal.agg;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.people.PeopleConstants.Endpoints;
import com.google.android.gms.people.internal.zzh;
import com.google.android.gms.people.internal.zzp;
import com.google.android.gms.people.internal.zzu;
import com.google.android.gms.people.internal.zzv;
import com.google.android.gms.people.model.AggregatedPersonBuffer;
import dalvik.annotation.Signature;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzd {
    static volatile boolean aSf = true;
    protected final boolean aLE;
    protected final int aLH;
    protected final String aLI;
    private final zzd aRV;
    protected final Bundle aRW;
    protected final Bundle aRX;
    protected final boolean aRY;
    protected final zzu aRZ;
    private DataHolder aRr;
    private Cursor aRs;
    private boolean aSa;
    private ConnectionResult aSb;
    private DataHolder aSc;
    private boolean aSd;
    private Exception aSe;
    private boolean aSg;
    private final Collator aSh = Collator.getInstance();
    protected final Context mContext;
    private final Object zzaix = new Object();

    /* compiled from: dalvik_source_com.waze.apk */
    private class zza extends Thread {
        final /* synthetic */ zzd aSi;

        public zza(zzd $r1) throws  {
            this.aSi = $r1;
            super("PeopleAggregator-aggregator");
        }

        public final void run() throws  {
            try {
                this.aSi.zzcfw();
            } catch (Exception $r2) {
                zzp.zzd("PeopleAggregator", "Unknown exception during aggregation", $r2);
                this.aSi.zzcfu();
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private class zzb extends Thread {
        final /* synthetic */ zzd aSi;

        public zzb(zzd $r1) throws  {
            this.aSi = $r1;
            super("PeopleAggregator-contacts");
        }

        public final void run() throws  {
            this.aSi.aRZ.zzrl("contacts query start");
            try {
                this.aSi.zza(this.aSi.zzcft(), null);
            } catch (Exception $r5) {
                zzp.zzd("PeopleAggregator", "Error while quering contacts", $r5);
                this.aSi.zza(null, $r5);
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    protected static class zzc {
        private int GM = -1;
        public final DataHolder aSj;
        private final int auS;

        public zzc(DataHolder $r1) throws  {
            this.aSj = $r1;
            this.auS = $r1.getCount();
        }

        public int getCount() throws  {
            return this.auS;
        }

        public int getPosition() throws  {
            return this.GM;
        }

        public String getString(String $r1) throws  {
            return this.aSj.getString($r1, this.GM, this.aSj.zzic(this.GM));
        }

        public boolean isAfterLast() throws  {
            return this.GM >= this.auS;
        }

        public boolean moveToNext() throws  {
            this.GM++;
            return this.GM >= 0 && this.GM < this.auS;
        }

        public void zzaco(int $i0) throws  {
            this.GM = $i0;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zzd {
        void zza(int i, Bundle bundle, AggregatedPersonBuffer aggregatedPersonBuffer) throws ;
    }

    protected zzd(Context $r1, zzd $r2, boolean $z0, int $i0, Bundle $r3, Bundle $r4, String $r5) throws  {
        this.mContext = $r1;
        this.aRV = $r2;
        this.aLE = $z0;
        this.aLH = $i0;
        this.aRW = $r3;
        this.aRX = $r4;
        this.aRY = !TextUtils.isEmpty($r5);
        if (!this.aRY) {
            $r5 = null;
        }
        this.aLI = $r5;
        this.aRZ = zzp.zzcfd() ? zzu.zzrk("aggregator") : zzu.zzcfi();
    }

    public static zzd zza(Context $r0, zzd $r1, boolean $z0, int $i0, Bundle $r2, Bundle $r3, String $r4, String $r5) throws  {
        if (TextUtils.isEmpty($r4)) {
            return new zze($r0, $r1, $z0, $i0, $r2, $r3, $r5);
        }
        if (TextUtils.isEmpty($r5)) {
            return new zzf($r0, $r1, $z0, $i0, $r2, $r3, $r4);
        }
        throw new IllegalArgumentException("Search aggregation doesn't support filtering by gaia-id");
    }

    protected static void zza(@Signature({"(", "Lcom/google/android/gms/people/internal/agg/zzd$zzc;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/Integer;", ">;)V"}) zzc $r0, @Signature({"(", "Lcom/google/android/gms/people/internal/agg/zzd$zzc;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/Integer;", ">;)V"}) HashMap<String, Integer> $r1) throws  {
        $r0.zzaco(-1);
        while ($r0.moveToNext()) {
            String $r2 = $r0.getString(Endpoints.KEY_TARGET_GAIA_ID);
            if (!TextUtils.isEmpty($r2)) {
                $r1.put($r2, Integer.valueOf($r0.getPosition()));
            }
        }
    }

    private void zzcfs() throws  {
        try {
            new zzb(this).start();
        } catch (Exception $r2) {
            zzp.zzd("PeopleAggregator", "Unable to start thread", $r2);
            zza(null, $r2);
        }
    }

    private void zzcfu() throws  {
        synchronized (this.zzaix) {
            zzab.zzbn(this.aSa);
            zzab.zzbn(this.aSd);
            if (this.aRr != null) {
                this.aRr.close();
            }
            if (this.aSc != null) {
                this.aSc.close();
            }
            if (this.aRs != null) {
                this.aRs.close();
            }
            if (this.aSg) {
                return;
            }
            this.aSg = true;
            this.aRV.zza(8, null, null);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void zzcfv() throws  {
        /*
        r8 = this;
        r0 = r8.zzaix;
        monitor-enter(r0);
        r1 = r8.aSa;	 Catch:{ Throwable -> 0x001a }
        if (r1 == 0) goto L_0x000b;
    L_0x0007:
        r1 = r8.aSd;	 Catch:{ Throwable -> 0x001a }
        if (r1 != 0) goto L_0x000d;
    L_0x000b:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001a }
        return;
    L_0x000d:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001a }
        r2 = r8.aSb;
        r1 = r2.isSuccess();
        if (r1 != 0) goto L_0x001d;
    L_0x0016:
        r8.zzcfu();
        return;
    L_0x001a:
        r3 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001a }
        throw r3;
    L_0x001d:
        r4 = new com.google.android.gms.people.internal.agg.zzd$zza;
        r4.<init>(r8);	 Catch:{ Exception -> 0x0026 }
        r4.start();	 Catch:{ Exception -> 0x0026 }
        return;
    L_0x0026:
        r5 = move-exception;
        r6 = "PeopleAggregator";
        r7 = "Unable to start thread";
        com.google.android.gms.people.internal.zzp.zzd(r6, r7, r5);
        r8.zzcfu();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.internal.agg.zzd.zzcfv():void");
    }

    private void zzcfw() throws  {
        Cursor $r4;
        zzab.zzbn(this.aSb.isSuccess());
        this.aRZ.zzrl("agg start");
        if (this.aRs != null) {
            $r4 = this.aRs;
        } else {
            Object $r42 = r11;
            MatrixCursor r11 = new MatrixCursor(zzb.aRI);
        }
        zza $r7 = zza(new zzc(this.aRr), new zzc(this.aSc), $r4);
        this.aRZ.zzrl("agg finish");
        this.aRZ.zzab("PeopleAggregator", 0);
        this.aRV.zza(0, null, $r7);
    }

    public static void zzdc(boolean $z0) throws  {
        aSf = $z0;
    }

    protected int zza(@Signature({"(", "Landroid/database/Cursor;", "Lcom/google/android/gms/people/internal/zzv;", "Lcom/google/android/gms/people/internal/zzh;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)I"}) Cursor $r1, @Signature({"(", "Landroid/database/Cursor;", "Lcom/google/android/gms/people/internal/zzv;", "Lcom/google/android/gms/people/internal/zzh;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)I"}) zzv $r2, @Signature({"(", "Landroid/database/Cursor;", "Lcom/google/android/gms/people/internal/zzv;", "Lcom/google/android/gms/people/internal/zzh;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)I"}) zzh $r3, @Signature({"(", "Landroid/database/Cursor;", "Lcom/google/android/gms/people/internal/zzv;", "Lcom/google/android/gms/people/internal/zzh;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)I"}) HashMap<String, String> $r4) throws  {
        int $i0 = -1;
        long $l1 = -1;
        $r1.moveToPosition(-1);
        ArrayList $r5 = new ArrayList(3);
        ArrayList $r6 = new ArrayList(6);
        int $i2 = 0;
        while ($r1.moveToNext()) {
            long $l3 = $r1.getLong(0);
            if ($l3 != $l1) {
                $r5.clear();
                $r6.clear();
                $i2++;
                $i0 = $r1.getPosition();
                $l1 = $l3;
            }
            String $r7 = $r1.getString(2);
            if ("vnd.android.cursor.item/email_v2".equals($r7) || "vnd.android.cursor.item/phone_v2".equals($r7)) {
                $r7 = $r1.getString(3);
                if (!TextUtils.isEmpty($r7)) {
                    if (!$r6.contains($r7)) {
                        $r6.add($r7);
                        $r7 = (String) $r4.get($r7);
                        if (!(TextUtils.isEmpty($r7) || $r5.contains($r7))) {
                            $r5.add($r7);
                            $r2.put($r7, $i0);
                            $r3.zza(Integer.valueOf($i0), $r7);
                        }
                    }
                }
            }
        }
        return $i2;
    }

    protected abstract zza zza(zzc com_google_android_gms_people_internal_agg_zzd_zzc, zzc com_google_android_gms_people_internal_agg_zzd_zzc2, Cursor cursor) throws ;

    void zza(Cursor $r1, Exception $r2) throws  {
        if ($r1 != null) {
            this.aRZ.zzrl("contacts loaded");
        } else {
            this.aRZ.zzrl("contacts load failure");
        }
        if (zzp.zzcfd()) {
            String $r4 = String.valueOf($r2);
            zzp.zzal("PeopleAggregator", new StringBuilder(String.valueOf($r4).length() + 46).append("Contacts loaded.  exception=").append($r4).append("  size=").append($r1 == null ? -1 : $r1.getCount()).toString());
        }
        synchronized (this.zzaix) {
            this.aSd = true;
            this.aRs = $r1;
            this.aSe = $r2;
        }
        zzcfv();
    }

    public void zza(ConnectionResult $r1, DataHolder[] $r2) throws  {
        if ($r1.isSuccess()) {
            this.aRZ.zzrl("people loaded");
        } else {
            this.aRZ.zzrl("people load failure");
        }
        if (zzp.zzcfd()) {
            String $r4 = String.valueOf($r1);
            int $i0 = ($r2 == null || $r2.length < 2 || $r2[0] == null) ? -1 : $r2[0].getCount();
            zzp.zzal("PeopleAggregator", new StringBuilder(String.valueOf($r4).length() + 41).append("People loaded.  status=").append($r4).append("  size=").append($i0).toString());
        }
        synchronized (this.zzaix) {
            this.aSa = true;
            this.aSb = $r1;
            if (this.aSb.isSuccess()) {
                this.aRr = $r2[0];
                this.aSc = $r2[1];
            }
        }
        if (!this.aRY) {
            zzcfv();
        } else if (this.aSb.isSuccess()) {
            zzcfs();
        } else {
            synchronized (this.zzaix) {
                this.aSd = true;
            }
            zzcfu();
        }
    }

    protected void zzb(@Signature({"(", "Lcom/google/android/gms/people/internal/agg/zzd$zzc;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) zzc $r1, @Signature({"(", "Lcom/google/android/gms/people/internal/agg/zzd$zzc;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) HashMap<String, String> $r2) throws  {
        $r1.zzaco(-1);
        while ($r1.moveToNext()) {
            $r2.put($r1.getString("value"), $r1.getString(Endpoints.KEY_TARGET_GAIA_ID));
        }
    }

    protected int zzbu(String $r1, String $r2) throws  {
        return TextUtils.isEmpty($r1) ? TextUtils.isEmpty($r2) ? 0 : -1 : TextUtils.isEmpty($r2) ? 1 : this.aSh.compare($r1, $r2);
    }

    protected DataHolder zzcfq() throws  {
        return this.aSc;
    }

    public void zzcfr() throws  {
        if (!this.aRY) {
            zzcfs();
        }
    }

    protected abstract Cursor zzcft() throws ;
}
