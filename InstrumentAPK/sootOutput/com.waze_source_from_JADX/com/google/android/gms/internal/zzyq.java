package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzyq {
    private static final String[] aMJ = new String[]{"android.provider.ALTERNATE_CONTACTS_STRUCTURE", "android.provider.CONTACTS_STRUCTURE"};
    public String aMK;
    public String aML;
    private ArrayList<zzyt> aMM;
    private HashMap<String, zzyt> aMN;
    private final boolean aMO;
    private String aMP;
    private String aMQ;
    private String aMR;
    private String aMS;
    private int aMT;
    private String aMU;
    private String aMV;
    private String aMW;
    private int aMX;
    private String aMY;
    private String aMZ;
    private List<String> aNa;
    private String aNb;
    private String aNc;
    private String aNd;
    private boolean aNe;
    public String accountType;
    public int iconRes;
    public int titleRes;
    private boolean zzanl;

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza extends Exception {
        public zza(String $r1) throws  {
            super($r1);
        }

        public zza(String $r1, Exception $r2) throws  {
            super($r1, $r2);
        }
    }

    public zzyq(Context $r1, String $r2, boolean $z0) throws  {
        this($r1, $r2, $z0, null);
    }

    zzyq(android.content.Context r11, java.lang.String r12, boolean r13, android.content.res.XmlResourceParser r14) throws  {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r10 = this;
        r10.<init>();
        r0 = 0;
        r10.accountType = r0;
        r0 = 0;
        r10.aMK = r0;
        r1 = -1;
        r10.titleRes = r1;
        r1 = -1;
        r10.iconRes = r1;
        r2 = new java.util.ArrayList;
        r2.<init>();
        r10.aMM = r2;
        r3 = new java.util.HashMap;
        r3.<init>();
        r10.aMN = r3;
        r10.aMO = r13;
        r10.aML = r12;
        r11.getPackageManager();
        if (r14 != 0) goto L_0x002a;
    L_0x0026:
        r14 = r10.zzaa(r11, r12);
    L_0x002a:
        if (r14 == 0) goto L_0x002f;
    L_0x002c:
        r10.zza(r11, r14);	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
    L_0x002f:
        if (r14 == 0) goto L_0x0034;
    L_0x0031:
        r14.close();
    L_0x0034:
        r2 = new java.util.ArrayList;
        r2.<init>();
        r10.aNa = r2;
        r4 = r10.aMS;
        r6 = "inviteContactActionLabel";
        r5 = zzb(r11, r4, r12, r6);
        r10.aMT = r5;
        r4 = r10.aMW;
        r6 = "viewGroupActionLabel";
        r5 = zzb(r11, r4, r12, r6);
        r10.aMX = r5;
        r4 = r10.aNb;
        r6 = "accountTypeLabel";
        r5 = zzb(r11, r4, r12, r6);
        r10.titleRes = r5;
        r4 = r10.aNc;
        r6 = "accountTypeIcon";
        r5 = zzb(r11, r4, r12, r6);
        r10.iconRes = r5;
        r1 = 1;
        r10.zzanl = r1;
        return;
    L_0x0068:
        r7 = move-exception;
        r8 = new java.lang.StringBuilder;	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        r8.<init>();	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        r6 = "Problem reading XML";	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        r8.append(r6);	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        if (r14 == 0) goto L_0x0081;	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
    L_0x0075:
        r6 = " in line ";	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        r8.append(r6);	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        r5 = r14.getLineNumber();	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        r8.append(r5);	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
    L_0x0081:
        r6 = " for external package ";	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        r8.append(r6);	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        r8.append(r12);	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        r12 = r8.toString();	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        r6 = "ExAccountType";	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        android.util.Log.e(r6, r12, r7);	 Catch:{ zza -> 0x0068, Throwable -> 0x0098 }
        if (r14 == 0) goto L_0x009f;
    L_0x0094:
        r14.close();
        return;
    L_0x0098:
        r9 = move-exception;
        if (r14 == 0) goto L_0x009e;
    L_0x009b:
        r14.close();
    L_0x009e:
        throw r9;
    L_0x009f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzyq.<init>(android.content.Context, java.lang.String, boolean, android.content.res.XmlResourceParser):void");
    }

    private zzyt zza(zzyt $r1) throws zza {
        if ($r1.mimeType == null) {
            throw new zza("null is not a valid mime type");
        } else if (this.aMN.get($r1.mimeType) != null) {
            String $r2 = $r1.mimeType;
            throw new zza(new StringBuilder(String.valueOf($r2).length() + 34).append("mime type '").append($r2).append("' is already registered").toString());
        } else {
            $r1.aML = this.aML;
            this.aMM.add($r1);
            this.aMN.put($r1.mimeType, $r1);
            return $r1;
        }
    }

    private void zza(android.content.Context r25, org.xmlpull.v1.XmlPullParser r26) throws com.google.android.gms.internal.zzyq.zza {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x01cb in list []
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
        r24 = this;
        r0 = r26;
        r2 = android.util.Xml.asAttributeSet(r0);
    L_0x0006:
        r0 = r26;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r3 = r0.next();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r4 = 2;
        if (r3 == r4) goto L_0x0012;
    L_0x000f:
        r4 = 1;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r3 != r4) goto L_0x0006;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x0012:
        r4 = 2;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r3 == r4) goto L_0x0026;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x0015:
        r5 = new java.lang.IllegalStateException;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r6 = "No start tag found";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r5.<init>(r6);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        throw r5;
    L_0x001d:
        r7 = move-exception;
        r8 = new com.google.android.gms.internal.zzyq$zza;
        r6 = "Problem reading XML";
        r8.<init>(r6, r7);
        throw r8;
    L_0x0026:
        r0 = r26;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r9 = r0.getName();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r10 = "ContactsAccountType";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r10.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 != 0) goto L_0x0065;
    L_0x0034:
        r10 = "ContactsSource";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r10.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 != 0) goto L_0x0065;
    L_0x003c:
        r5 = new java.lang.IllegalStateException;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r6 = "Top level element must be ContactsAccountType, not ";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r10 = java.lang.String.valueOf(r6);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r9 = java.lang.String.valueOf(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r3 = r9.length();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r3 == 0) goto L_0x005f;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x004e:
        r9 = r10.concat(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x0052:
        r5.<init>(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        throw r5;
    L_0x0056:
        r12 = move-exception;
        r8 = new com.google.android.gms.internal.zzyq$zza;
        r6 = "Problem reading XML";
        r8.<init>(r6, r12);
        throw r8;
    L_0x005f:
        r9 = new java.lang.String;
        r9.<init>(r10);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        goto L_0x0052;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x0065:
        r4 = 1;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r24;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0.aNe = r4;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r26;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r3 = r0.getAttributeCount();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r13 = 0;
    L_0x0071:
        if (r13 >= r3) goto L_0x01cb;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x0073:
        r0 = r26;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r9 = r0.getAttributeName(r13);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r26;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r10 = r0.getAttributeValue(r13);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r6 = "ExAccountType";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r4 = 3;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = android.util.Log.isLoggable(r6, r4);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x00bd;
    L_0x0088:
        r14 = new java.lang.StringBuilder;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r15 = java.lang.String.valueOf(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r16 = r15.length();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r16 = r16 + 1;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r15 = java.lang.String.valueOf(r10);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r17 = r15.length();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r16;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r1 = r17;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r0 + r1;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r16 = r0;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r14.<init>(r0);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r14 = r14.append(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r6 = "=";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r14 = r14.append(r6);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r14 = r14.append(r10);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r15 = r14.toString();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r6 = "ExAccountType";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        android.util.Log.d(r6, r15);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x00bd:
        r15 = "editContactActivity";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x00cc;
    L_0x00c5:
        r0 = r24;
        r0.aMP = r10;
    L_0x00c9:
        r13 = r13 + 1;
        goto L_0x0071;
    L_0x00cc:
        r15 = "createContactActivity";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x00d9;
    L_0x00d4:
        r0 = r24;
        r0.aMQ = r10;
        goto L_0x00c9;
    L_0x00d9:
        r15 = "inviteContactActivity";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x00e6;
    L_0x00e1:
        r0 = r24;
        r0.aMR = r10;
        goto L_0x00c9;
    L_0x00e6:
        r15 = "inviteContactActionLabel";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x00f3;
    L_0x00ee:
        r0 = r24;
        r0.aMS = r10;
        goto L_0x00c9;
    L_0x00f3:
        r15 = "viewContactNotifyService";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x0101;
    L_0x00fc:
        r0 = r24;
        r0.aMU = r10;
        goto L_0x00c9;
    L_0x0101:
        r15 = "viewGroupActivity";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x011b;
    L_0x010a:
        goto L_0x010e;
    L_0x010b:
        goto L_0x00c9;
    L_0x010e:
        goto L_0x0112;
    L_0x010f:
        goto L_0x00c9;
    L_0x0112:
        r0 = r24;
        r0.aMV = r10;
        goto L_0x011a;
    L_0x0117:
        goto L_0x00c9;
    L_0x011a:
        goto L_0x00c9;
    L_0x011b:
        r15 = "viewGroupActionLabel";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        goto L_0x0122;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x011f:
        goto L_0x00c9;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x0122:
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x0135;
    L_0x0128:
        goto L_0x012c;
    L_0x0129:
        goto L_0x00c9;
    L_0x012c:
        r0 = r24;
        r0.aMW = r10;
        goto L_0x0134;
    L_0x0131:
        goto L_0x00c9;
    L_0x0134:
        goto L_0x00c9;
    L_0x0135:
        r15 = "viewStreamItemActivity";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        goto L_0x013c;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x0139:
        goto L_0x00c9;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x013c:
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x014c;
    L_0x0142:
        goto L_0x0146;
    L_0x0143:
        goto L_0x00c9;
    L_0x0146:
        r0 = r24;
        r0.aMY = r10;
        goto L_0x00c9;
    L_0x014c:
        r15 = "viewStreamItemPhotoActivity";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x015a;
    L_0x0155:
        r0 = r24;
        r0.aMZ = r10;
        goto L_0x010b;
    L_0x015a:
        r15 = "dataSet";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x0167;
    L_0x0162:
        r0 = r24;
        r0.aMK = r10;
        goto L_0x010f;
    L_0x0167:
        r15 = "extensionPackageNames";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x0179;
    L_0x016f:
        r0 = r24;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r0.aNa;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r18 = r0;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0.add(r10);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        goto L_0x0117;
    L_0x0179:
        r15 = "accountType";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x0186;
    L_0x0181:
        r0 = r24;
        r0.accountType = r10;
        goto L_0x011f;
    L_0x0186:
        r15 = "accountTypeLabel";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x0193;
    L_0x018e:
        r0 = r24;
        r0.aNb = r10;
        goto L_0x0129;
    L_0x0193:
        r15 = "accountTypeIcon";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x01a0;
    L_0x019b:
        r0 = r24;
        r0.aNc = r10;
        goto L_0x0131;
    L_0x01a0:
        r15 = "readOnly";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r15.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x01ad;
    L_0x01a8:
        r0 = r24;
        r0.aNd = r10;
        goto L_0x0139;
    L_0x01ad:
        r10 = "Unsupported attribute ";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r9 = java.lang.String.valueOf(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r16 = r9.length();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r16 == 0) goto L_0x01c3;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x01b9:
        r9 = r10.concat(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x01bd:
        r6 = "ExAccountType";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        android.util.Log.e(r6, r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        goto L_0x0143;
    L_0x01c3:
        r9 = new java.lang.String;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r6 = "Unsupported attribute ";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r9.<init>(r6);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        goto L_0x01bd;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x01cb:
        r0 = r26;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r3 = r0.getDepth();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x01d1:
        r0 = r26;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r13 = r0.next();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r4 = 3;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r13 != r4) goto L_0x01e4;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x01da:
        r0 = r26;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r16 = r0.getDepth();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r16;
        if (r0 <= r3) goto L_0x0278;
    L_0x01e4:
        r4 = 1;
        if (r13 == r4) goto L_0x0279;
    L_0x01e7:
        r4 = 2;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r13 != r4) goto L_0x01d1;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x01ea:
        r0 = r26;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r13 = r0.getDepth();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r16 = r3 + 1;
        r0 = r16;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r13 != r0) goto L_0x01d1;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x01f6:
        r0 = r26;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r9 = r0.getName();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r10 = "ContactsDataKind";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r11 = r10.equals(r9);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r11 == 0) goto L_0x01d1;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x0204:
        r4 = 3;
        r0 = new int[r4];
        r19 = r0;
        r4 = 0;
        r20 = 16842790; // 0x1010026 float:2.3693665E-38 double:8.321444E-317;
        r19[r4] = r20;
        r4 = 1;
        r20 = 16843426; // 0x10102a2 float:2.3695447E-38 double:8.321758E-317;
        r19[r4] = r20;
        r4 = 2;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r20 = 16843427; // 0x10102a3 float:2.369545E-38 double:8.3217586E-317;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r19[r4] = r20;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        goto L_0x021f;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x021c:
        goto L_0x01d1;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x021f:
        r0 = r25;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r1 = r19;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r21 = r0.obtainStyledAttributes(r2, r1);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r21 != 0) goto L_0x0233;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x0229:
        r6 = "ExAccountType";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r22 = "Failed to obtain ContactsDataKind styled attributes";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r22;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        android.util.Log.e(r6, r0);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        goto L_0x01d1;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x0233:
        r4 = 0;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r21;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r9 = r0.getString(r4);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r9 != 0) goto L_0x0246;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x023c:
        r6 = "ExAccountType";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r22 = "Failed to obtain mimeType from ContactsDataKind styled attributes";	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r22;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        android.util.Log.e(r6, r0);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        goto L_0x01d1;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x0246:
        r23 = new com.google.android.gms.internal.zzyt;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r23;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0.<init>();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r23;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0.mimeType = r9;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r4 = 1;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r21;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r9 = r0.getString(r4);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r9 == 0) goto L_0x025e;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x025a:
        r0 = r23;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0.aNi = r9;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x025e:
        r4 = 2;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r21;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r9 = r0.getString(r4);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        if (r9 == 0) goto L_0x026b;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x0267:
        r0 = r23;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0.aNj = r9;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
    L_0x026b:
        r0 = r21;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0.recycle();	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0 = r24;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r1 = r23;	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        r0.zza(r1);	 Catch:{ XmlPullParserException -> 0x001d, IOException -> 0x0056 }
        goto L_0x021c;
    L_0x0278:
        return;
    L_0x0279:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzyq.zza(android.content.Context, org.xmlpull.v1.XmlPullParser):void");
    }

    private XmlResourceParser zzaa(Context $r1, String $r2) throws  {
        PackageManager $r3 = $r1.getPackageManager();
        List<ResolveInfo> $r5 = $r3.queryIntentServices(new Intent("android.content.SyncAdapter").setPackage($r2), 132);
        if ($r5 != null) {
            for (ResolveInfo resolveInfo : $r5) {
                ServiceInfo $r9 = resolveInfo.serviceInfo;
                if ($r9 != null) {
                    String[] $r10 = aMJ;
                    int $i0 = $r10.length;
                    int $i1 = 0;
                    while ($i1 < $i0) {
                        XmlResourceParser $r11 = $r9.loadXmlMetaData($r3, $r10[$i1]);
                        if ($r11 == null) {
                            $i1++;
                        } else if (!Log.isLoggable("ExAccountType", 3)) {
                            return $r11;
                        } else {
                            Log.d("ExAccountType", String.format("Metadata loaded from: %s, %s, %s", new Object[]{$r9.packageName, $r9.name, $r2}));
                            return $r11;
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }

    static int zzb(Context $r0, String $r1, String $r2, String $r3) throws  {
        if (TextUtils.isEmpty($r1)) {
            return -1;
        }
        if ($r1.charAt(0) != '@') {
            Log.e("ExAccountType", String.valueOf($r3).concat(" must be a resource name beginning with '@'"));
            return -1;
        }
        try {
            int $i1 = $r0.getPackageManager().getResourcesForApplication($r2).getIdentifier($r1.substring(1), null, $r2);
            if ($i1 != 0) {
                return $i1;
            }
            Log.e("ExAccountType", new StringBuilder((String.valueOf($r1).length() + 29) + String.valueOf($r2).length()).append("Unable to load ").append($r1).append(" from package ").append($r2).toString());
            return -1;
        } catch (NameNotFoundException e) {
            $r1 = "Unable to load package ";
            $r2 = String.valueOf($r2);
            Log.e("ExAccountType", $r2.length() != 0 ? $r1.concat($r2) : new String("Unable to load package "));
            return -1;
        }
    }

    public final boolean isInitialized() throws  {
        return this.zzanl;
    }

    public String toString() throws  {
        return String.format("AccountType<accountType=%s, dataSet=%s, resourcePackgeName=%s>", new Object[]{this.accountType, this.aMK, this.aML});
    }

    public boolean zzccw() throws  {
        return this.aNe;
    }

    public List<String> zzccx() throws  {
        return this.aNa;
    }

    public zzyt zzmz(String $r1) throws  {
        return (zzyt) this.aMN.get($r1);
    }
}
