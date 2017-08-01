package com.google.android.gms.people.identity.internal;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.PhoneLookup;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.util.zzr;
import com.google.android.gms.internal.zzyq;
import com.google.android.gms.internal.zzyt;
import com.google.android.gms.people.identity.PersonFactory.ContactData;
import com.google.android.gms.people.identity.PersonFactory.ExternalContactData;
import com.google.android.gms.people.identity.PersonFactory.RawContactData;
import com.google.android.gms.people.internal.zzp;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzb {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface zza {
        void zza(Status status, ContactData[] contactDataArr) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private interface zzb {
        public static final String[] aNk = new String[]{"contact_id"};
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzc implements Runnable {
        private static final String[] aNl;
        private final zza aNm;
        private final String aNn;
        private final Set<String>[] aNo;
        private final Context mContext;

        static {
            ArrayList $r0 = new ArrayList();
            $r0.add("data_id");
            $r0.add("mimetype");
            $r0.add("data1");
            $r0.add("data2");
            $r0.add("data3");
            $r0.add("data4");
            $r0.add("data5");
            $r0.add("data6");
            $r0.add("data7");
            $r0.add("data8");
            $r0.add("data9");
            $r0.add("data10");
            $r0.add("data11");
            $r0.add("data12");
            $r0.add("data13");
            $r0.add("data14");
            $r0.add("data15");
            $r0.add("is_primary");
            $r0.add("contact_id");
            $r0.add("account_type");
            if (zzr.zzazd()) {
                $r0.add("data_set");
            }
            if (zzr.zzazk()) {
                $r0.add("times_used");
            }
            aNl = (String[]) $r0.toArray(new String[0]);
        }

        public zzc(@Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) zza $r1, @Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Context $r2, @Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Set<String>[] $r4) throws  {
            this.aNm = $r1;
            this.mContext = $r2;
            this.aNn = $r3;
            this.aNo = $r4;
        }

        private ContactData zza(@Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzyq;", ">;)", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;"}) Set<String> $r1, @Signature({"(", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzyq;", ">;)", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;"}) List<zzyq> $r2) throws  {
            List $r3 = new ArrayList();
            if ($r1 != null) {
                for (String zza : $r1) {
                    zza($r3, zza, $r2);
                }
            }
            return $r3.isEmpty() ? null : new ContactData($r3);
        }

        private ExternalContactData zza(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzyq;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/database/Cursor;", ")", "Lcom/google/android/gms/people/identity/PersonFactory$ExternalContactData;"}) List<zzyq> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzyq;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/database/Cursor;", ")", "Lcom/google/android/gms/people/identity/PersonFactory$ExternalContactData;"}) String $r2, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzyq;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/database/Cursor;", ")", "Lcom/google/android/gms/people/identity/PersonFactory$ExternalContactData;"}) String $r3, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzyq;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/database/Cursor;", ")", "Lcom/google/android/gms/people/identity/PersonFactory$ExternalContactData;"}) String $r4, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzyq;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Landroid/database/Cursor;", ")", "Lcom/google/android/gms/people/identity/PersonFactory$ExternalContactData;"}) Cursor $r5) throws  {
            for (zzyq $r8 : $r1) {
                if (zzaa.equal($r8.accountType, $r3) && zzaa.equal($r8.aMK, $r4)) {
                    zzyt $r10 = $r8.zzmz($r2);
                    if ($r10 != null) {
                        Uri $r11 = ContentUris.withAppendedId(Data.CONTENT_URI, $r5.getLong(0));
                        $r2 = $r5.getString($r5.getColumnIndex($r10.aNi));
                        $r4 = $r5.getString($r5.getColumnIndex($r10.aNj));
                        return new ExternalContactData($r11, $r2, $r8.iconRes, $r4, $r10.aML, $r10.mimeType, $r8.titleRes, $r8.accountType);
                    }
                }
            }
            return null;
        }

        @TargetApi(14)
        private static String zza(int $i0, Cursor $r0) throws  {
            if (zzr.zzazd()) {
                if ($r0.getType($i0) == 0) {
                    return null;
                }
                if ($r0.getType($i0) == 4) {
                    return new String($r0.getBlob($i0));
                }
            } else if ($r0.isNull($i0)) {
                return null;
            } else {
                try {
                    if ($r0.getBlob($i0) != null) {
                        return null;
                    }
                } catch (Exception e) {
                }
            }
            return $r0.getString($i0);
        }

        @RequiresPermission("android.permission.READ_CONTACTS")
        private void zza(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/people/identity/PersonFactory$RawContactData;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzyq;", ">;)V"}) List<RawContactData> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/people/identity/PersonFactory$RawContactData;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzyq;", ">;)V"}) String $r2, @Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/people/identity/PersonFactory$RawContactData;", ">;", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/google/android/gms/internal/zzyq;", ">;)V"}) List<zzyq> $r3) throws  {
            if (zzr.zzaza()) {
                Cursor $r9 = this.mContext.getContentResolver().query(Contacts.CONTENT_URI.buildUpon().appendEncodedPath($r2).appendEncodedPath("entities").build(), aNl, null, null, null);
                if ($r9 != null) {
                    while ($r9.moveToNext()) {
                        String $r10 = $r9.getString(0);
                        if ($r10 != null) {
                            String $r11 = $r9.getString(1);
                            String[] $r4 = new String[15];
                            int i = 0;
                            int $i1 = 2;
                            while ($i1 <= 16) {
                                try {
                                    int $i2 = i + 1;
                                    $r4[i] = zza($i1, $r9);
                                    $i1++;
                                    i = $i2;
                                } finally {
                                    try {
                                        $r9.close();
                                    } catch (Exception e) {
                                    }
                                }
                            }
                            $r1.add(new RawContactData($r2, $r2, $r11, zzr.zzazk() ? $r9.getInt(21) : zznc($r10), $r4, false, $r9.getInt(17) == 1, zza($r3, $r11, $r9.getString(19), zzr.zzazd() ? $r9.getString(20) : null, $r9)));
                        }
                    }
                }
            }
        }

        @TargetApi(18)
        @RequiresPermission("android.permission.READ_CONTACTS")
        private int zznc(String $r1) throws  {
            if (!zzr.zzazg()) {
                return 0;
            }
            Uri $r2 = ContentUris.withAppendedId(Data.CONTENT_URI, Long.parseLong($r1));
            Cursor $r6 = this.mContext.getContentResolver().query($r2, new String[]{"times_used"}, null, null, null);
            if ($r6 == null) {
                zzp.zzan("ContactsDataLoader", "null getTimesUsed cursor");
                return 0;
            }
            try {
                if ($r6.moveToFirst()) {
                    int $i1 = $r6.getInt(0);
                    return $i1;
                }
                $r6.close();
                return 0;
            } finally {
                $r6.close();
            }
        }

        @android.support.annotation.RequiresPermission("android.permission.READ_CONTACTS")
        public void run() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:35:0x0091 in {15, 20, 22, 27, 29, 31, 34, 36, 38, 59, 62} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r25 = this;
            r0 = r25;
            r3 = r0.mContext;
            r4 = com.google.android.gms.internal.zzyr.zzdj(r3);
            r5 = r4.zzccy();
            r0 = r25;
            r6 = r0.aNo;
            r7 = r6.length;
            r8 = new com.google.android.gms.people.identity.PersonFactory.ContactData[r7];
            r7 = 0;
        L_0x0014:
            r9 = r8.length;	 Catch:{ Throwable -> 0x008c }
            if (r7 >= r9) goto L_0x011c;	 Catch:{ Throwable -> 0x008c }
        L_0x0017:
            r10 = new java.util.HashSet;	 Catch:{ Throwable -> 0x008c }
            r10.<init>();	 Catch:{ SecurityException -> 0x004e }
            r0 = r25;
            r6 = r0.aNo;
            r11 = r6[r7];	 Catch:{ SecurityException -> 0x004e }
            r12 = r11.iterator();	 Catch:{ SecurityException -> 0x004e }
        L_0x0026:
            r13 = r12.hasNext();	 Catch:{ SecurityException -> 0x004e }
            if (r13 == 0) goto L_0x010d;	 Catch:{ SecurityException -> 0x004e }
        L_0x002c:
            r14 = r12.next();	 Catch:{ SecurityException -> 0x004e }
            r16 = r14;	 Catch:{ SecurityException -> 0x004e }
            r16 = (java.lang.String) r16;	 Catch:{ SecurityException -> 0x004e }
            r15 = r16;	 Catch:{ SecurityException -> 0x004e }
            r13 = com.google.android.gms.people.internal.zzq.zzre(r15);	 Catch:{ SecurityException -> 0x004e }
            if (r13 == 0) goto L_0x0070;	 Catch:{ SecurityException -> 0x004e }
        L_0x003c:
            r0 = r25;	 Catch:{ SecurityException -> 0x004e }
            r3 = r0.mContext;	 Catch:{ SecurityException -> 0x004e }
            r17 = com.google.android.gms.people.internal.zzq.zzrc(r15);	 Catch:{ SecurityException -> 0x004e }
            r0 = r17;	 Catch:{ SecurityException -> 0x004e }
            r11 = com.google.android.gms.people.identity.internal.zzb.zzab(r3, r0);	 Catch:{ SecurityException -> 0x004e }
            r10.addAll(r11);	 Catch:{ SecurityException -> 0x004e }
            goto L_0x0026;
        L_0x004e:
            r18 = move-exception;
            r19 = "ContactsDataLoader";	 Catch:{ Throwable -> 0x008c }
            r20 = "Error querying contact data:";	 Catch:{ Throwable -> 0x008c }
            r0 = r19;	 Catch:{ Throwable -> 0x008c }
            r1 = r20;	 Catch:{ Throwable -> 0x008c }
            r2 = r18;	 Catch:{ Throwable -> 0x008c }
            com.google.android.gms.people.internal.zzp.zzd(r0, r1, r2);	 Catch:{ Throwable -> 0x008c }
            r0 = r25;
            r0 = r0.aNm;
            r21 = r0;
            r22 = com.google.android.gms.common.api.Status.CQ;
            goto L_0x0068;
        L_0x0065:
            goto L_0x0026;
        L_0x0068:
            r0 = r21;
            r1 = r22;
            r0.zza(r1, r8);
            return;
        L_0x0070:
            r13 = com.google.android.gms.people.internal.zzq.zzrf(r15);	 Catch:{ SecurityException -> 0x004e }
            if (r13 == 0) goto L_0x00a5;	 Catch:{ SecurityException -> 0x004e }
        L_0x0076:
            goto L_0x007a;	 Catch:{ SecurityException -> 0x004e }
        L_0x0077:
            goto L_0x0026;	 Catch:{ SecurityException -> 0x004e }
        L_0x007a:
            r17 = com.google.android.gms.people.internal.zzq.zzra(r15);	 Catch:{ SecurityException -> 0x004e }
            r0 = r17;	 Catch:{ SecurityException -> 0x004e }
            r11 = com.google.android.gms.people.identity.internal.zzb.zzna(r0);	 Catch:{ SecurityException -> 0x004e }
            goto L_0x0088;	 Catch:{ SecurityException -> 0x004e }
        L_0x0085:
            goto L_0x0026;	 Catch:{ SecurityException -> 0x004e }
        L_0x0088:
            r10.addAll(r11);	 Catch:{ SecurityException -> 0x004e }
            goto L_0x0026;
        L_0x008c:
            r23 = move-exception;
            goto L_0x0095;
        L_0x008e:
            goto L_0x0026;
            goto L_0x0095;
        L_0x0092:
            goto L_0x0026;
        L_0x0095:
            r0 = r25;
            r0 = r0.aNm;
            r21 = r0;
            r22 = com.google.android.gms.common.api.Status.CQ;
            r0 = r21;
            r1 = r22;
            r0.zza(r1, r8);
            throw r23;
        L_0x00a5:
            r13 = com.google.android.gms.people.identity.internal.zzc.zzni(r15);	 Catch:{ SecurityException -> 0x004e }
            if (r13 == 0) goto L_0x00bd;	 Catch:{ SecurityException -> 0x004e }
        L_0x00ab:
            r0 = r25;	 Catch:{ SecurityException -> 0x004e }
            r3 = r0.mContext;	 Catch:{ SecurityException -> 0x004e }
            r17 = com.google.android.gms.people.identity.internal.zzc.zznl(r15);	 Catch:{ SecurityException -> 0x004e }
            r0 = r17;	 Catch:{ SecurityException -> 0x004e }
            r11 = com.google.android.gms.people.identity.internal.zzb.zzac(r3, r0);	 Catch:{ SecurityException -> 0x004e }
            r10.addAll(r11);	 Catch:{ SecurityException -> 0x004e }
            goto L_0x0065;	 Catch:{ SecurityException -> 0x004e }
        L_0x00bd:
            r13 = com.google.android.gms.people.identity.internal.zzc.zznh(r15);	 Catch:{ SecurityException -> 0x004e }
            if (r13 == 0) goto L_0x00db;	 Catch:{ SecurityException -> 0x004e }
        L_0x00c3:
            r0 = r25;	 Catch:{ Throwable -> 0x008c }
            r3 = r0.mContext;	 Catch:{ Throwable -> 0x008c }
            r0 = r25;	 Catch:{ SecurityException -> 0x004e }
            r0 = r0.aNn;	 Catch:{ SecurityException -> 0x004e }
            r17 = r0;	 Catch:{ SecurityException -> 0x004e }
            r15 = com.google.android.gms.people.identity.internal.zzc.zznj(r15);	 Catch:{ SecurityException -> 0x004e }
            r0 = r17;	 Catch:{ SecurityException -> 0x004e }
            r11 = com.google.android.gms.people.identity.internal.zzb.zzk(r3, r0, r15);	 Catch:{ SecurityException -> 0x004e }
            r10.addAll(r11);	 Catch:{ SecurityException -> 0x004e }
            goto L_0x0077;	 Catch:{ SecurityException -> 0x004e }
        L_0x00db:
            r13 = com.google.android.gms.people.identity.internal.zzc.zznd(r15);	 Catch:{ SecurityException -> 0x004e }
            if (r13 == 0) goto L_0x00ef;	 Catch:{ SecurityException -> 0x004e }
        L_0x00e1:
            r17 = com.google.android.gms.people.identity.internal.zzc.zzne(r15);	 Catch:{ SecurityException -> 0x004e }
            r0 = r17;	 Catch:{ SecurityException -> 0x004e }
            r11 = com.google.android.gms.people.identity.internal.zzc.zzng(r0);	 Catch:{ SecurityException -> 0x004e }
            r10.addAll(r11);	 Catch:{ SecurityException -> 0x004e }
            goto L_0x0085;	 Catch:{ SecurityException -> 0x004e }
        L_0x00ef:
            r13 = com.google.android.gms.people.internal.zzq.zzrg(r15);	 Catch:{ SecurityException -> 0x004e }
            if (r13 == 0) goto L_0x0101;	 Catch:{ SecurityException -> 0x004e }
        L_0x00f5:
            r19 = "ContactsDataLoader";	 Catch:{ SecurityException -> 0x004e }
            r20 = "Unknown qualified ID type";	 Catch:{ SecurityException -> 0x004e }
            r0 = r19;	 Catch:{ SecurityException -> 0x004e }
            r1 = r20;	 Catch:{ SecurityException -> 0x004e }
            com.google.android.gms.people.internal.zzp.zzan(r0, r1);	 Catch:{ SecurityException -> 0x004e }
            goto L_0x008e;	 Catch:{ SecurityException -> 0x004e }
        L_0x0101:
            r19 = "ContactsDataLoader";	 Catch:{ SecurityException -> 0x004e }
            r20 = "Invalid qualified ID";	 Catch:{ SecurityException -> 0x004e }
            r0 = r19;	 Catch:{ SecurityException -> 0x004e }
            r1 = r20;	 Catch:{ SecurityException -> 0x004e }
            com.google.android.gms.people.internal.zzp.zzan(r0, r1);	 Catch:{ SecurityException -> 0x004e }
            goto L_0x0092;	 Catch:{ SecurityException -> 0x004e }
        L_0x010d:
            r0 = r25;	 Catch:{ SecurityException -> 0x004e }
            r24 = r0.zza(r10, r5);	 Catch:{ SecurityException -> 0x004e }
            r8[r7] = r24;	 Catch:{ Throwable -> 0x008c }
            goto L_0x0119;	 Catch:{ Throwable -> 0x008c }
        L_0x0116:
            goto L_0x0014;	 Catch:{ Throwable -> 0x008c }
        L_0x0119:
            r7 = r7 + 1;
            goto L_0x0116;
        L_0x011c:
            r0 = r25;
            r0 = r0.aNm;
            r21 = r0;
            r22 = com.google.android.gms.common.api.Status.CQ;
            r0 = r21;
            r1 = r22;
            r0.zza(r1, r8);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.zzb.zzc.run():void");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zzd implements Runnable {
        private final zza aNm;
        private final String aNn;
        private final Set<String> aNp;
        private final Context mContext;

        /* compiled from: dalvik_source_com.waze.apk */
        private interface zza {
            public static final String[] aNk = new String[]{"_id", "display_name", "photo_id"};
        }

        public zzd(@Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) zza $r1, @Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Context $r2, @Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Set<String> $r4) throws  {
            this.aNm = $r1;
            this.mContext = $r2;
            this.aNn = $r3;
            this.aNp = $r4;
        }

        @android.support.annotation.RequiresPermission("android.permission.READ_CONTACTS")
        public void run() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:45:0x0121
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r47 = this;
            r9 = new java.util.ArrayList;
            r9.<init>();
            r10 = new java.util.HashSet;
            r10.<init>();	 Catch:{ SecurityException -> 0x003e }
            r0 = r47;	 Catch:{ SecurityException -> 0x003e }
            r11 = r0.aNp;	 Catch:{ SecurityException -> 0x003e }
            r12 = r11.iterator();	 Catch:{ SecurityException -> 0x003e }
        L_0x0012:
            r13 = r12.hasNext();	 Catch:{ SecurityException -> 0x003e }
            if (r13 == 0) goto L_0x0131;	 Catch:{ SecurityException -> 0x003e }
        L_0x0018:
            r14 = r12.next();	 Catch:{ SecurityException -> 0x003e }
            r16 = r14;	 Catch:{ SecurityException -> 0x003e }
            r16 = (java.lang.String) r16;	 Catch:{ SecurityException -> 0x003e }
            r15 = r16;	 Catch:{ SecurityException -> 0x003e }
            r13 = com.google.android.gms.people.internal.zzq.zzre(r15);	 Catch:{ SecurityException -> 0x003e }
            if (r13 == 0) goto L_0x0076;	 Catch:{ SecurityException -> 0x003e }
        L_0x0028:
            r0 = r47;	 Catch:{ SecurityException -> 0x003e }
            r0 = r0.mContext;	 Catch:{ SecurityException -> 0x003e }
            r17 = r0;	 Catch:{ SecurityException -> 0x003e }
            r18 = com.google.android.gms.people.internal.zzq.zzrc(r15);	 Catch:{ SecurityException -> 0x003e }
            r0 = r17;	 Catch:{ SecurityException -> 0x003e }
            r1 = r18;	 Catch:{ SecurityException -> 0x003e }
            r11 = com.google.android.gms.people.identity.internal.zzb.zzab(r0, r1);	 Catch:{ SecurityException -> 0x003e }
            r10.addAll(r11);	 Catch:{ SecurityException -> 0x003e }
            goto L_0x0012;
        L_0x003e:
            r19 = move-exception;
            r20 = "ContactsDataLoader";	 Catch:{ Throwable -> 0x0092 }
            r21 = "Error querying contact data:";	 Catch:{ Throwable -> 0x0092 }
            r0 = r20;	 Catch:{ Throwable -> 0x0092 }
            r1 = r21;	 Catch:{ Throwable -> 0x0092 }
            r2 = r19;	 Catch:{ Throwable -> 0x0092 }
            com.google.android.gms.people.internal.zzp.zzd(r0, r1, r2);	 Catch:{ Throwable -> 0x0092 }
            r0 = r47;
            r0 = r0.aNm;
            r22 = r0;
            r23 = com.google.android.gms.common.api.Status.CQ;
            r24 = r9.size();
            r0 = r24;
            r0 = new com.google.android.gms.people.identity.PersonFactory.ContactData[r0];
            r25 = r0;
            r26 = r9.toArray(r0);
            r27 = r26;
            r27 = (com.google.android.gms.people.identity.PersonFactory.ContactData[]) r27;
            r25 = r27;
            goto L_0x006c;
        L_0x0069:
            goto L_0x0012;
        L_0x006c:
            r0 = r22;
            r1 = r23;
            r2 = r25;
            r0.zza(r1, r2);
            return;
        L_0x0076:
            r13 = com.google.android.gms.people.internal.zzq.zzrf(r15);	 Catch:{ SecurityException -> 0x003e }
            if (r13 == 0) goto L_0x00b9;	 Catch:{ SecurityException -> 0x003e }
        L_0x007c:
            r18 = com.google.android.gms.people.internal.zzq.zzra(r15);	 Catch:{ SecurityException -> 0x003e }
            goto L_0x0084;	 Catch:{ SecurityException -> 0x003e }
        L_0x0081:
            goto L_0x0012;	 Catch:{ SecurityException -> 0x003e }
        L_0x0084:
            r0 = r18;	 Catch:{ SecurityException -> 0x003e }
            r11 = com.google.android.gms.people.identity.internal.zzb.zzna(r0);	 Catch:{ SecurityException -> 0x003e }
            r10.addAll(r11);	 Catch:{ SecurityException -> 0x003e }
            goto L_0x0091;	 Catch:{ SecurityException -> 0x003e }
        L_0x008e:
            goto L_0x0012;	 Catch:{ SecurityException -> 0x003e }
        L_0x0091:
            goto L_0x0012;
        L_0x0092:
            r28 = move-exception;
            r0 = r47;
            r0 = r0.aNm;
            r22 = r0;
            r23 = com.google.android.gms.common.api.Status.CQ;
            r24 = r9.size();
            r0 = r24;
            r0 = new com.google.android.gms.people.identity.PersonFactory.ContactData[r0];
            r25 = r0;
            r26 = r9.toArray(r0);
            r29 = r26;
            r29 = (com.google.android.gms.people.identity.PersonFactory.ContactData[]) r29;
            r25 = r29;
            r0 = r22;
            r1 = r23;
            r2 = r25;
            r0.zza(r1, r2);
            throw r28;
        L_0x00b9:
            r13 = com.google.android.gms.people.identity.internal.zzc.zzni(r15);	 Catch:{ SecurityException -> 0x003e }
            if (r13 == 0) goto L_0x00d5;	 Catch:{ SecurityException -> 0x003e }
        L_0x00bf:
            r0 = r47;	 Catch:{ SecurityException -> 0x003e }
            r0 = r0.mContext;	 Catch:{ SecurityException -> 0x003e }
            r17 = r0;	 Catch:{ SecurityException -> 0x003e }
            r18 = com.google.android.gms.people.identity.internal.zzc.zznl(r15);	 Catch:{ SecurityException -> 0x003e }
            r0 = r17;	 Catch:{ SecurityException -> 0x003e }
            r1 = r18;	 Catch:{ SecurityException -> 0x003e }
            r11 = com.google.android.gms.people.identity.internal.zzb.zzac(r0, r1);	 Catch:{ SecurityException -> 0x003e }
            r10.addAll(r11);	 Catch:{ SecurityException -> 0x003e }
            goto L_0x0069;	 Catch:{ SecurityException -> 0x003e }
        L_0x00d5:
            r13 = com.google.android.gms.people.identity.internal.zzc.zznh(r15);	 Catch:{ SecurityException -> 0x003e }
            if (r13 == 0) goto L_0x00f7;	 Catch:{ SecurityException -> 0x003e }
        L_0x00db:
            r0 = r47;	 Catch:{ Throwable -> 0x0092 }
            r0 = r0.mContext;	 Catch:{ Throwable -> 0x0092 }
            r17 = r0;	 Catch:{ Throwable -> 0x0092 }
            r0 = r47;	 Catch:{ SecurityException -> 0x003e }
            r0 = r0.aNn;	 Catch:{ SecurityException -> 0x003e }
            r18 = r0;	 Catch:{ SecurityException -> 0x003e }
            r15 = com.google.android.gms.people.identity.internal.zzc.zznj(r15);	 Catch:{ SecurityException -> 0x003e }
            r0 = r17;	 Catch:{ SecurityException -> 0x003e }
            r1 = r18;	 Catch:{ SecurityException -> 0x003e }
            r11 = com.google.android.gms.people.identity.internal.zzb.zzk(r0, r1, r15);	 Catch:{ SecurityException -> 0x003e }
            r10.addAll(r11);	 Catch:{ SecurityException -> 0x003e }
            goto L_0x0081;	 Catch:{ SecurityException -> 0x003e }
        L_0x00f7:
            r13 = com.google.android.gms.people.identity.internal.zzc.zznd(r15);	 Catch:{ SecurityException -> 0x003e }
            if (r13 == 0) goto L_0x010b;	 Catch:{ SecurityException -> 0x003e }
        L_0x00fd:
            r18 = com.google.android.gms.people.identity.internal.zzc.zzne(r15);	 Catch:{ SecurityException -> 0x003e }
            r0 = r18;	 Catch:{ SecurityException -> 0x003e }
            r11 = com.google.android.gms.people.identity.internal.zzc.zzng(r0);	 Catch:{ SecurityException -> 0x003e }
            r10.addAll(r11);	 Catch:{ SecurityException -> 0x003e }
            goto L_0x008e;	 Catch:{ SecurityException -> 0x003e }
        L_0x010b:
            r13 = com.google.android.gms.people.internal.zzq.zzrg(r15);	 Catch:{ SecurityException -> 0x003e }
            if (r13 == 0) goto L_0x0125;	 Catch:{ SecurityException -> 0x003e }
        L_0x0111:
            goto L_0x0115;	 Catch:{ SecurityException -> 0x003e }
        L_0x0112:
            goto L_0x0012;	 Catch:{ SecurityException -> 0x003e }
        L_0x0115:
            r20 = "ContactsDataLoader";	 Catch:{ SecurityException -> 0x003e }
            r21 = "Unknown qualified ID type";	 Catch:{ SecurityException -> 0x003e }
            r0 = r20;	 Catch:{ SecurityException -> 0x003e }
            r1 = r21;	 Catch:{ SecurityException -> 0x003e }
            com.google.android.gms.people.internal.zzp.zzan(r0, r1);	 Catch:{ SecurityException -> 0x003e }
            goto L_0x0112;	 Catch:{ SecurityException -> 0x003e }
            goto L_0x0125;	 Catch:{ SecurityException -> 0x003e }
        L_0x0122:
            goto L_0x0012;	 Catch:{ SecurityException -> 0x003e }
        L_0x0125:
            r20 = "ContactsDataLoader";	 Catch:{ SecurityException -> 0x003e }
            r21 = "Invalid qualified ID";	 Catch:{ SecurityException -> 0x003e }
            r0 = r20;	 Catch:{ SecurityException -> 0x003e }
            r1 = r21;	 Catch:{ SecurityException -> 0x003e }
            com.google.android.gms.people.internal.zzp.zzan(r0, r1);	 Catch:{ SecurityException -> 0x003e }
            goto L_0x0122;	 Catch:{ SecurityException -> 0x003e }
        L_0x0131:
            r0 = r47;	 Catch:{ SecurityException -> 0x003e }
            r0 = r0.mContext;	 Catch:{ SecurityException -> 0x003e }
            r17 = r0;	 Catch:{ SecurityException -> 0x003e }
            r30 = r0.getContentResolver();	 Catch:{ SecurityException -> 0x003e }
            r31 = android.provider.ContactsContract.Contacts.CONTENT_URI;
            r32 = com.google.android.gms.people.identity.internal.zzb.zzd.zza.aNk;	 Catch:{ SecurityException -> 0x003e }
            r34 = 0;	 Catch:{ SecurityException -> 0x003e }
            r35 = 0;	 Catch:{ SecurityException -> 0x003e }
            r36 = 0;	 Catch:{ SecurityException -> 0x003e }
            r0 = r30;	 Catch:{ SecurityException -> 0x003e }
            r1 = r31;	 Catch:{ SecurityException -> 0x003e }
            r2 = r32;	 Catch:{ SecurityException -> 0x003e }
            r3 = r34;	 Catch:{ SecurityException -> 0x003e }
            r4 = r35;	 Catch:{ SecurityException -> 0x003e }
            r5 = r36;	 Catch:{ SecurityException -> 0x003e }
            r33 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ SecurityException -> 0x003e }
            r37 = -1;	 Catch:{ Throwable -> 0x01eb }
            r0 = r33;	 Catch:{ Throwable -> 0x01eb }
            r1 = r37;	 Catch:{ Throwable -> 0x01eb }
            r0.move(r1);	 Catch:{ Throwable -> 0x01eb }
        L_0x015e:
            r0 = r33;	 Catch:{ Throwable -> 0x01eb }
            r13 = r0.moveToNext();	 Catch:{ Throwable -> 0x01eb }
            if (r13 == 0) goto L_0x01f2;	 Catch:{ Throwable -> 0x01eb }
        L_0x0166:
            r37 = 0;	 Catch:{ Throwable -> 0x01eb }
            r0 = r33;	 Catch:{ Throwable -> 0x01eb }
            r1 = r37;	 Catch:{ Throwable -> 0x01eb }
            r18 = r0.getString(r1);	 Catch:{ Throwable -> 0x01eb }
            r0 = r18;	 Catch:{ Throwable -> 0x01eb }
            r13 = r10.contains(r0);	 Catch:{ Throwable -> 0x01eb }
            if (r13 != 0) goto L_0x015e;	 Catch:{ Throwable -> 0x01eb }
        L_0x0178:
            r32 = com.google.android.gms.people.identity.internal.zzb.zzd.zza.aNk;	 Catch:{ Throwable -> 0x01eb }
            r0 = r32;	 Catch:{ Throwable -> 0x01eb }
            r0 = r0.length;	 Catch:{ Throwable -> 0x01eb }
            r24 = r0;	 Catch:{ Throwable -> 0x01eb }
            r0 = new java.lang.String[r0];	 Catch:{ Throwable -> 0x01eb }
            r32 = r0;	 Catch:{ Throwable -> 0x01eb }
            r24 = 0;	 Catch:{ Throwable -> 0x01eb }
        L_0x0185:
            r38 = com.google.android.gms.people.identity.internal.zzb.zzd.zza.aNk;	 Catch:{ Throwable -> 0x01eb }
            r0 = r38;	 Catch:{ Throwable -> 0x01eb }
            r0 = r0.length;	 Catch:{ Throwable -> 0x01eb }
            r39 = r0;	 Catch:{ Throwable -> 0x01eb }
            r0 = r24;	 Catch:{ Throwable -> 0x01eb }
            r1 = r39;	 Catch:{ Throwable -> 0x01eb }
            if (r0 >= r1) goto L_0x01a3;	 Catch:{ Throwable -> 0x01eb }
        L_0x0192:
            r0 = r33;	 Catch:{ Throwable -> 0x01eb }
            r1 = r24;	 Catch:{ Throwable -> 0x01eb }
            r18 = r0.getString(r1);	 Catch:{ Throwable -> 0x01eb }
            r32[r24] = r18;	 Catch:{ Throwable -> 0x01eb }
            r24 = r24 + 1;
            goto L_0x0185;	 Catch:{ Throwable -> 0x01eb }
            goto L_0x01a3;	 Catch:{ Throwable -> 0x01eb }
        L_0x01a0:
            goto L_0x015e;	 Catch:{ Throwable -> 0x01eb }
        L_0x01a3:
            r40 = new com.google.android.gms.people.identity.PersonFactory$ContactData;	 Catch:{ Throwable -> 0x01eb }
            r37 = 1;	 Catch:{ Throwable -> 0x01eb }
            r0 = r37;	 Catch:{ Throwable -> 0x01eb }
            r0 = new com.google.android.gms.people.identity.PersonFactory.RawContactData[r0];	 Catch:{ Throwable -> 0x01eb }
            r41 = r0;	 Catch:{ Throwable -> 0x01eb }
            r42 = new com.google.android.gms.people.identity.PersonFactory$RawContactData;	 Catch:{ Throwable -> 0x01eb }
            r37 = 0;	 Catch:{ Throwable -> 0x01eb }
            r0 = r33;	 Catch:{ Throwable -> 0x01eb }
            r1 = r37;	 Catch:{ Throwable -> 0x01eb }
            r18 = r0.getString(r1);	 Catch:{ Throwable -> 0x01eb }
            r34 = 0;	 Catch:{ Throwable -> 0x01eb }
            r35 = 0;	 Catch:{ Throwable -> 0x01eb }
            r37 = 0;	 Catch:{ Throwable -> 0x01eb }
            r43 = 1;	 Catch:{ Throwable -> 0x01eb }
            r44 = 1;	 Catch:{ Throwable -> 0x01eb }
            r36 = 0;	 Catch:{ Throwable -> 0x01eb }
            r0 = r42;	 Catch:{ Throwable -> 0x01eb }
            r1 = r18;	 Catch:{ Throwable -> 0x01eb }
            r2 = r34;	 Catch:{ Throwable -> 0x01eb }
            r3 = r35;	 Catch:{ Throwable -> 0x01eb }
            r4 = r37;	 Catch:{ Throwable -> 0x01eb }
            r5 = r32;	 Catch:{ Throwable -> 0x01eb }
            r6 = r43;	 Catch:{ Throwable -> 0x01eb }
            r7 = r44;	 Catch:{ Throwable -> 0x01eb }
            r8 = r36;	 Catch:{ Throwable -> 0x01eb }
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ Throwable -> 0x01eb }
            r37 = 0;	 Catch:{ Throwable -> 0x01eb }
            r41[r37] = r42;	 Catch:{ Throwable -> 0x01eb }
            r0 = r40;	 Catch:{ Throwable -> 0x01eb }
            r1 = r41;	 Catch:{ Throwable -> 0x01eb }
            r0.<init>(r1);	 Catch:{ Throwable -> 0x01eb }
            r0 = r40;	 Catch:{ Throwable -> 0x01eb }
            r9.add(r0);	 Catch:{ Throwable -> 0x01eb }
            goto L_0x01a0;
        L_0x01eb:
            r45 = move-exception;
            r0 = r33;	 Catch:{ SecurityException -> 0x003e }
            r0.close();	 Catch:{ SecurityException -> 0x003e }
            throw r45;	 Catch:{ SecurityException -> 0x003e }
        L_0x01f2:
            r0 = r33;	 Catch:{ SecurityException -> 0x003e }
            r0.close();	 Catch:{ SecurityException -> 0x003e }
            r0 = r47;
            r0 = r0.aNm;
            r22 = r0;
            r23 = com.google.android.gms.common.api.Status.CQ;
            r24 = r9.size();
            r0 = r24;
            r0 = new com.google.android.gms.people.identity.PersonFactory.ContactData[r0];
            r25 = r0;
            r26 = r9.toArray(r0);
            r46 = r26;
            r46 = (com.google.android.gms.people.identity.PersonFactory.ContactData[]) r46;
            r25 = r46;
            r0 = r22;
            r1 = r23;
            r2 = r25;
            r0.zza(r1, r2);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.zzb.zzd.run():void");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private interface zze {
        public static final String[] aNk = new String[]{"_id"};
    }

    public static void zza(@Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) zza $r0, @Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Context $r1, @Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Set<String> $r3) throws  {
        new Thread(new zzd($r0, $r1, $r2, $r3)).start();
    }

    public static void zza(@Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) zza $r0, @Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Context $r1, @Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/people/identity/internal/zzb$zza;", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Set<String>[] $r3) throws  {
        new Thread(new zzc($r0, $r1, $r2, $r3)).start();
    }

    @RequiresPermission("android.permission.READ_CONTACTS")
    private static Set<String> zzab(@Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) String $r1) throws  {
        if (TextUtils.isEmpty($r1)) {
            zzp.zzan("ContactsDataLoader", "empty email address");
            return Collections.emptySet();
        }
        Uri $r4 = Uri.withAppendedPath(Email.CONTENT_LOOKUP_URI, Uri.encode($r1));
        Cursor $r7 = $r0.getContentResolver().query($r4, zzb.aNk, null, null, null);
        if ($r7 == null) {
            zzp.zzan("ContactsDataLoader", "null retrieveContactsFromEmailId cursor");
            return Collections.emptySet();
        }
        try {
            HashSet hashSet = new HashSet();
            while ($r7.moveToNext()) {
                hashSet.add($r7.getString(0));
            }
            return hashSet;
        } finally {
            $r7.close();
        }
    }

    @RequiresPermission("android.permission.READ_CONTACTS")
    private static Set<String> zzac(@Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) String $r1) throws  {
        if (TextUtils.isEmpty($r1)) {
            zzp.zzan("ContactsDataLoader", "empty phone number");
            return Collections.emptySet();
        }
        Uri $r4 = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode($r1));
        Cursor $r7 = $r0.getContentResolver().query($r4, zze.aNk, null, null, null);
        if ($r7 == null) {
            zzp.zzan("ContactsDataLoader", "null retrieveContactsFromPhoneNumberId cursor");
            return Collections.emptySet();
        }
        try {
            HashSet hashSet = new HashSet();
            while ($r7.moveToNext()) {
                hashSet.add($r7.getString(0));
            }
            return hashSet;
        } finally {
            $r7.close();
        }
    }

    private static Set<String> zzk(@Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) String $r1, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) String $r2) throws  {
        if (TextUtils.isEmpty($r2)) {
            zzp.zzan("ContactsDataLoader", "empty focus ID");
            return Collections.emptySet();
        }
        long $l0 = com.google.android.gms.people.cp2.zza.zzh($r0, $r1, $r2);
        return $l0 >= 0 ? Collections.singleton(String.valueOf($l0)) : Collections.emptySet();
    }

    private static Set<String> zzna(@Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) String str) throws  {
        return Collections.emptySet();
    }
}
