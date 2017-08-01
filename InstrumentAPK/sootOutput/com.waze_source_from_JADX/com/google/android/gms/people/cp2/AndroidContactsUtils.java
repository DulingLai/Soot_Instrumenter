package com.google.android.gms.people.cp2;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.people.model.AggregatedPerson;
import java.util.Iterator;

/* compiled from: dalvik_source_com.waze.apk */
public class AndroidContactsUtils {
    public static final String TAG = "PeopleCp2Helper";

    private AndroidContactsUtils() throws  {
    }

    public static Uri getContactLookupUri(Context $r0, AggregatedPerson $r1) throws  {
        try {
            Long $r2 = zza($r1);
            if ($r2 != null) {
                return getContactLookupUriFromAndroidContactId($r0, $r2.longValue());
            }
            if ($r1.getGaiaId() != null) {
                return getContactLookupUriFromGaiaId($r0, $r1.getOwnerAccountName(), $r1.getGaiaId());
            }
            return null;
        } catch (SecurityException e) {
        }
    }

    @android.support.annotation.RequiresPermission("android.permission.READ_CONTACTS")
    public static android.net.Uri getContactLookupUri(android.content.Context r10, com.google.android.gms.people.model.AutocompleteEntry r11) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0013 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = r11.getAndroidContactId();	 Catch:{ SecurityException -> 0x0039 }
        r3 = 0;
        r2 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1));
        if (r2 <= 0) goto L_0x0013;	 Catch:{ SecurityException -> 0x0039 }
    L_0x000a:
        r0 = r11.getAndroidContactId();	 Catch:{ SecurityException -> 0x0039 }
        r5 = getContactLookupUriFromAndroidContactId(r10, r0);	 Catch:{ SecurityException -> 0x0039 }
        return r5;
    L_0x0013:
        r6 = r11.getFocusContactId();	 Catch:{ SecurityException -> 0x0039 }
        if (r6 == 0) goto L_0x0026;	 Catch:{ SecurityException -> 0x0039 }
    L_0x0019:
        r6 = r11.getOwnerAccountName();	 Catch:{ SecurityException -> 0x0039 }
        r7 = r11.getFocusContactId();	 Catch:{ SecurityException -> 0x0039 }
        r5 = getContactLookupUriFromFocusContactId(r10, r6, r7);	 Catch:{ SecurityException -> 0x0039 }
        return r5;
    L_0x0026:
        r6 = r11.getGaiaId();	 Catch:{ SecurityException -> 0x0039 }
        if (r6 == 0) goto L_0x003a;	 Catch:{ SecurityException -> 0x0039 }
    L_0x002c:
        r6 = r11.getOwnerAccountName();	 Catch:{ SecurityException -> 0x0039 }
        r7 = r11.getGaiaId();	 Catch:{ SecurityException -> 0x0039 }
        r5 = getContactLookupUriFromGaiaId(r10, r6, r7);	 Catch:{ SecurityException -> 0x0039 }
        return r5;
    L_0x0039:
        r8 = move-exception;
    L_0x003a:
        r9 = 0;
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.cp2.AndroidContactsUtils.getContactLookupUri(android.content.Context, com.google.android.gms.people.model.AutocompleteEntry):android.net.Uri");
    }

    @RequiresPermission("android.permission.READ_CONTACTS")
    public static Uri getContactLookupUriFromAndroidContactId(Context $r0, long $l0) throws  {
        try {
            return zza.getContactLookupUriFromAndroidContactId($r0, $l0);
        } catch (SecurityException e) {
            return null;
        }
    }

    @RequiresPermission("android.permission.READ_CONTACTS")
    public static Uri getContactLookupUriFromFocusContactId(Context $r0, String $r1, String $r2) throws  {
        try {
            return zza.getContactLookupUriFromAndroidContactId($r0, zza.zzh($r0, $r1, $r2));
        } catch (SecurityException e) {
            return null;
        }
    }

    @RequiresPermission("android.permission.READ_CONTACTS")
    public static Uri getContactLookupUriFromGaiaId(Context $r0, String $r1, String $r2) throws  {
        try {
            return zza.getContactLookupUriFromAndroidContactId($r0, zza.zzi($r0, $r1, $r2));
        } catch (SecurityException e) {
            return null;
        }
    }

    @RequiresPermission("android.permission.READ_CONTACTS")
    public static String getPhotoUriFromFocusContactId(Context $r0, String $r1, String $r2) throws  {
        try {
            return zza.zzj($r0, $r1, $r2);
        } catch (SecurityException e) {
            return null;
        }
    }

    private static Long zza(AggregatedPerson $r0) throws  {
        Iterable $r2 = $r0.getContactIds();
        if ($r2 == null) {
            return null;
        }
        Iterator $r3 = $r2.iterator();
        return $r3.hasNext() ? (Long) $r3.next() : null;
    }
}
