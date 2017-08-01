package android.support.v4.util;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile {
    private final File mBackupName;
    private final File mBaseName;

    public byte[] readFully() throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x000b in list []
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
        r9 = this;
        r0 = r9.openRead();
        r1 = 0;
        r2 = r0.available();	 Catch:{ Throwable -> 0x002b }
        r3 = new byte[r2];	 Catch:{ Throwable -> 0x002b }
    L_0x000b:
        r2 = r3.length;	 Catch:{ Throwable -> 0x002b }
        r2 = r2 - r1;	 Catch:{ Throwable -> 0x002b }
        r2 = r0.read(r3, r1, r2);	 Catch:{ Throwable -> 0x002b }
        if (r2 > 0) goto L_0x0017;
    L_0x0013:
        r0.close();
        return r3;
    L_0x0017:
        r1 = r1 + r2;
        r2 = r0.available();	 Catch:{ Throwable -> 0x002b }
        r4 = r3.length;	 Catch:{ Throwable -> 0x002b }
        r4 = r4 - r1;
        if (r2 <= r4) goto L_0x000b;	 Catch:{ Throwable -> 0x002b }
    L_0x0020:
        r2 = r1 + r2;	 Catch:{ Throwable -> 0x002b }
        r5 = new byte[r2];	 Catch:{ Throwable -> 0x002b }
        r6 = 0;	 Catch:{ Throwable -> 0x002b }
        r7 = 0;	 Catch:{ Throwable -> 0x002b }
        java.lang.System.arraycopy(r3, r6, r5, r7, r1);	 Catch:{ Throwable -> 0x002b }
        r3 = r5;
        goto L_0x000b;
    L_0x002b:
        r8 = move-exception;
        r0.close();
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.AtomicFile.readFully():byte[]");
    }

    public AtomicFile(File $r1) throws  {
        this.mBaseName = $r1;
        this.mBackupName = new File($r1.getPath() + ".bak");
    }

    public File getBaseFile() throws  {
        return this.mBaseName;
    }

    public void delete() throws  {
        this.mBaseName.delete();
        this.mBackupName.delete();
    }

    public FileOutputStream startWrite() throws IOException {
        if (this.mBaseName.exists()) {
            if (this.mBackupName.exists()) {
                this.mBaseName.delete();
            } else if (!this.mBaseName.renameTo(this.mBackupName)) {
                Log.w("AtomicFile", "Couldn't rename file " + this.mBaseName + " to backup file " + this.mBackupName);
            }
        }
        try {
            return new FileOutputStream(this.mBaseName);
        } catch (FileNotFoundException e) {
            if (this.mBaseName.getParentFile().mkdirs()) {
                try {
                    return new FileOutputStream(this.mBaseName);
                } catch (FileNotFoundException e2) {
                    throw new IOException("Couldn't create " + this.mBaseName);
                }
            }
            throw new IOException("Couldn't create directory " + this.mBaseName);
        }
    }

    public void finishWrite(FileOutputStream $r1) throws  {
        if ($r1 != null) {
            sync($r1);
            try {
                $r1.close();
                this.mBackupName.delete();
            } catch (IOException $r2) {
                Log.w("AtomicFile", "finishWrite: Got exception:", $r2);
            }
        }
    }

    public void failWrite(FileOutputStream $r1) throws  {
        if ($r1 != null) {
            sync($r1);
            try {
                $r1.close();
                this.mBaseName.delete();
                this.mBackupName.renameTo(this.mBaseName);
            } catch (IOException $r2) {
                Log.w("AtomicFile", "failWrite: Got exception:", $r2);
            }
        }
    }

    public FileInputStream openRead() throws FileNotFoundException {
        if (this.mBackupName.exists()) {
            this.mBaseName.delete();
            this.mBackupName.renameTo(this.mBaseName);
        }
        return new FileInputStream(this.mBaseName);
    }

    static boolean sync(FileOutputStream $r0) throws  {
        if ($r0 != null) {
            try {
                $r0.getFD().sync();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
