package android.support.v4.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;
import java.util.ArrayList;

class DocumentsContractApi21 {
    private static final String TAG = "DocumentFile";

    DocumentsContractApi21() throws  {
    }

    public static Uri createFile(Context $r0, Uri $r1, String $r2, String $r3) throws  {
        return DocumentsContract.createDocument($r0.getContentResolver(), $r1, $r2, $r3);
    }

    public static Uri createDirectory(Context $r0, Uri $r1, String $r2) throws  {
        return createFile($r0, $r1, "vnd.android.document/directory", $r2);
    }

    public static Uri prepareTreeUri(Uri $r0) throws  {
        return DocumentsContract.buildDocumentUriUsingTree($r0, DocumentsContract.getTreeDocumentId($r0));
    }

    public static Uri[] listFiles(Context $r0, Uri $r1) throws  {
        ContentResolver $r4 = $r0.getContentResolver();
        Uri $r6 = DocumentsContract.buildChildDocumentsUriUsingTree($r1, DocumentsContract.getDocumentId($r1));
        ArrayList $r3 = new ArrayList();
        Cursor $r7 = null;
        Cursor $r9;
        try {
            $r9 = $r4.query($r6, new String[]{"document_id"}, null, null, null);
            $r7 = $r9;
            while ($r9.moveToNext()) {
                $r3.add(DocumentsContract.buildDocumentUriUsingTree($r1, $r9.getString(0)));
            }
        } catch (Exception $r2) {
            Log.w(TAG, "Failed query: " + $r2);
        } finally {
            closeQuietly(
/*
Method generation error in method: android.support.v4.provider.DocumentsContractApi21.listFiles(android.content.Context, android.net.Uri):android.net.Uri[]
jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0087: INVOKE  (wrap: android.database.Cursor
  ?: MERGE  (r10_3 '$r7' android.database.Cursor) = (r10_2 '$r7' android.database.Cursor), (r14_0 '$r9' android.database.Cursor)) android.support.v4.provider.DocumentsContractApi21.closeQuietly(java.lang.AutoCloseable):void type: STATIC in method: android.support.v4.provider.DocumentsContractApi21.listFiles(android.content.Context, android.net.Uri):android.net.Uri[]
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
Caused by: jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: MERGE  (r10_3 '$r7' android.database.Cursor) = (r10_2 '$r7' android.database.Cursor), (r14_0 '$r9' android.database.Cursor) in method: android.support.v4.provider.DocumentsContractApi21.listFiles(android.content.Context, android.net.Uri):android.net.Uri[]
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

            public static Uri renameTo(Context $r0, Uri $r1, String $r2) throws  {
                return DocumentsContract.renameDocument($r0.getContentResolver(), $r1, $r2);
            }

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
