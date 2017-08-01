package android.support.v4.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;

class DocumentsContractApi19 {
    private static final String TAG = "DocumentFile";

    public static boolean exists(android.content.Context r21, android.net.Uri r22) throws  {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.ssa.SSATransform.placePhi(SSATransform.java:82)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:50)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = r21;
        r6 = r0.getContentResolver();
        r7 = 0;
        r9 = 1;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r8 = new java.lang.String[r9];	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r9 = 0;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r10 = "document_id";	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r8[r9] = r10;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r12 = 0;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r13 = 0;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r14 = 0;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r0 = r6;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r1 = r22;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r2 = r8;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r3 = r12;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r4 = r13;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r5 = r14;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r11 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r7 = r11;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r15 = r11.getCount();	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        if (r15 <= 0) goto L_0x002a;
    L_0x0024:
        r16 = 1;
    L_0x0026:
        closeQuietly(r11);
        return r16;
    L_0x002a:
        r16 = 0;
        goto L_0x0026;
    L_0x002d:
        r17 = move-exception;
        r18 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r0 = r18;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r0.<init>();	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r10 = "Failed query: ";	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r0 = r18;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r18 = r0.append(r10);	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r0 = r18;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r1 = r17;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r18 = r0.append(r1);	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r0 = r18;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r19 = r0.toString();	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r10 = "DocumentFile";	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        r0 = r19;	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        android.util.Log.w(r10, r0);	 Catch:{ Exception -> 0x002d, Throwable -> 0x0057 }
        closeQuietly(r7);
        r9 = 0;
        return r9;
    L_0x0057:
        r20 = move-exception;
        closeQuietly(r7);
        throw r20;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.provider.DocumentsContractApi19.exists(android.content.Context, android.net.Uri):boolean");
    }

    DocumentsContractApi19() throws  {
    }

    public static boolean isDocumentUri(Context $r0, Uri $r1) throws  {
        return DocumentsContract.isDocumentUri($r0, $r1);
    }

    public static String getName(Context $r0, Uri $r1) throws  {
        return queryForString($r0, $r1, "_display_name", null);
    }

    private static String getRawType(Context $r0, Uri $r1) throws  {
        return queryForString($r0, $r1, "mime_type", null);
    }

    public static String getType(Context $r0, Uri $r1) throws  {
        String $r2 = getRawType($r0, $r1);
        return "vnd.android.document/directory".equals($r2) ? null : $r2;
    }

    public static boolean isDirectory(Context $r0, Uri $r1) throws  {
        return "vnd.android.document/directory".equals(getRawType($r0, $r1));
    }

    public static boolean isFile(Context $r0, Uri $r1) throws  {
        String $r2 = getRawType($r0, $r1);
        return ("vnd.android.document/directory".equals($r2) || TextUtils.isEmpty($r2)) ? false : true;
    }

    public static long lastModified(Context $r0, Uri $r1) throws  {
        return queryForLong($r0, $r1, "last_modified", 0);
    }

    public static long length(Context $r0, Uri $r1) throws  {
        return queryForLong($r0, $r1, "_size", 0);
    }

    public static boolean canRead(Context $r0, Uri $r1) throws  {
        if ($r0.checkCallingOrSelfUriPermission($r1, 1) != 0) {
            return false;
        }
        return !TextUtils.isEmpty(getRawType($r0, $r1));
    }

    public static boolean canWrite(Context $r0, Uri $r1) throws  {
        if ($r0.checkCallingOrSelfUriPermission($r1, 2) != 0) {
            return false;
        }
        String $r2 = getRawType($r0, $r1);
        int $i0 = queryForInt($r0, $r1, "flags", 0);
        if (TextUtils.isEmpty($r2)) {
            return false;
        }
        if (($i0 & 4) != 0) {
            return true;
        }
        if ("vnd.android.document/directory".equals($r2) && ($i0 & 8) != 0) {
            return true;
        }
        if (TextUtils.isEmpty($r2)) {
            return false;
        }
        return ($i0 & 2) != 0;
    }

    public static boolean delete(Context $r0, Uri $r1) throws  {
        return DocumentsContract.deleteDocument($r0.getContentResolver(), $r1);
    }

    private static String queryForString(Context $r0, Uri $r1, String $r2, String $r4) throws  {
        Cursor $r6 = null;
        Cursor $r8;
        try {
            $r8 = $r0.getContentResolver().query($r1, new String[]{$r2}, null, null, null);
            $r6 = $r8;
            if (!$r8.moveToFirst() || $r8.isNull(0)) {
                closeQuietly($r8);
                return $r4;
            }
            $r2 = $r8.getString(0);
            return $r2;
        } catch (Exception $r3) {
            $r2 = "Failed query: " + $r3;
            Log.w(TAG, $r2);
            return $r4;
        } finally {
            closeQuietly(
/*
Method generation error in method: android.support.v4.provider.DocumentsContractApi19.queryForString(android.content.Context, android.net.Uri, java.lang.String, java.lang.String):java.lang.String
jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0062: INVOKE  (wrap: android.database.Cursor
  ?: MERGE  (r7_3 '$r6' android.database.Cursor) = (r7_2 '$r6' android.database.Cursor), (r10_0 '$r8' android.database.Cursor)) android.support.v4.provider.DocumentsContractApi19.closeQuietly(java.lang.AutoCloseable):void type: STATIC in method: android.support.v4.provider.DocumentsContractApi19.queryForString(android.content.Context, android.net.Uri, java.lang.String, java.lang.String):java.lang.String
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:203)
	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:100)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:50)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:297)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:183)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:328)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:265)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:228)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:118)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:83)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: MERGE  (r7_3 '$r6' android.database.Cursor) = (r7_2 '$r6' android.database.Cursor), (r10_0 '$r8' android.database.Cursor) in method: android.support.v4.provider.DocumentsContractApi19.queryForString(android.content.Context, android.net.Uri, java.lang.String, java.lang.String):java.lang.String
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:101)
	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:679)
	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:649)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:343)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 21 more
Caused by: jadx.core.utils.exceptions.CodegenException: MERGE can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:211)
	... 26 more

*/

            private static int queryForInt(Context $r0, Uri $r1, String $r2, int $i0) throws  {
                return (int) queryForLong($r0, $r1, $r2, (long) $i0);
            }

            private static long queryForLong(Context $r0, Uri $r1, String $r2, long $l0) throws  {
                Cursor $r5 = null;
                Cursor $r7;
                try {
                    $r7 = $r0.getContentResolver().query($r1, new String[]{$r2}, null, null, null);
                    $r5 = $r7;
                    if (!$r7.moveToFirst() || $r7.isNull(0)) {
                        closeQuietly($r7);
                        return $l0;
                    }
                    $l0 = $r7.getLong(0);
                    return $l0;
                } catch (Exception $r3) {
                    Log.w(TAG, "Failed query: " + $r3);
                    return $l0;
                } finally {
                    closeQuietly(
/*
Method generation error in method: android.support.v4.provider.DocumentsContractApi19.queryForLong(android.content.Context, android.net.Uri, java.lang.String, long):long
jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0062: INVOKE  (wrap: android.database.Cursor
  ?: MERGE  (r7_3 '$r5' android.database.Cursor) = (r7_2 '$r5' android.database.Cursor), (r10_0 '$r7' android.database.Cursor)) android.support.v4.provider.DocumentsContractApi19.closeQuietly(java.lang.AutoCloseable):void type: STATIC in method: android.support.v4.provider.DocumentsContractApi19.queryForLong(android.content.Context, android.net.Uri, java.lang.String, long):long
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:203)
	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:100)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:50)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:93)
	at jadx.core.codegen.RegionGen.makeTryCatch(RegionGen.java:297)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:63)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:183)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:328)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:265)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:228)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:118)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:83)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: MERGE  (r7_3 '$r5' android.database.Cursor) = (r7_2 '$r5' android.database.Cursor), (r10_0 '$r7' android.database.Cursor) in method: android.support.v4.provider.DocumentsContractApi19.queryForLong(android.content.Context, android.net.Uri, java.lang.String, long):long
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:101)
	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:679)
	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:649)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:343)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 21 more
Caused by: jadx.core.utils.exceptions.CodegenException: MERGE can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:211)
	... 26 more

*/

                    private static void closeQuietly(AutoCloseable $r0) throws  {
                        if ($r0 != null) {
                            try {
                                $r0.close();
                            } catch (RuntimeException $r1) {
                                throw $r1;
                            } catch (Exception e) {
                            }
                        }
                    }
                }
