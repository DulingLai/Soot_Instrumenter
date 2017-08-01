package com.waze.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public abstract class CustomPathSQLiteOpenHelper {
    private static final String TAG = CustomPathSQLiteOpenHelper.class.getSimpleName();
    private SQLiteDatabase mDatabase = null;
    private final String mDir;
    private final CursorFactory mFactory;
    private boolean mIsInitializing = false;
    private final String mName;
    private final int mNewVersion;

    public synchronized android.database.sqlite.SQLiteDatabase getWritableDatabase() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x009d in list []
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
        r15 = this;
        monitor-enter(r15);
        r0 = r15.mDatabase;
        if (r0 == 0) goto L_0x0010;
    L_0x0005:
        r0 = r15.mDatabase;
        r1 = r0.isOpen();
        if (r1 != 0) goto L_0x001f;
    L_0x000d:
        r2 = 0;
        r15.mDatabase = r2;
    L_0x0010:
        r1 = r15.mIsInitializing;
        if (r1 == 0) goto L_0x002b;
    L_0x0014:
        r3 = new java.lang.IllegalStateException;
        r4 = "getWritableDatabase called recursively";
        r3.<init>(r4);
        throw r3;
    L_0x001c:
        r5 = move-exception;
        monitor-exit(r15);
        throw r5;
    L_0x001f:
        r0 = r15.mDatabase;
        r1 = r0.isReadOnly();
        if (r1 != 0) goto L_0x0010;
    L_0x0027:
        r0 = r15.mDatabase;
    L_0x0029:
        monitor-exit(r15);
        return r0;
    L_0x002b:
        r0 = 0;
        r6 = 1;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r15.mIsInitializing = r6;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r7 = r15.mName;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        if (r7 != 0) goto L_0x0065;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
    L_0x0033:
        r2 = 0;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r0 = android.database.sqlite.SQLiteDatabase.create(r2);	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
    L_0x0038:
        r8 = r0.getVersion();	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r9 = r15.mNewVersion;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        if (r8 == r9) goto L_0x0053;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
    L_0x0040:
        r0.beginTransaction();	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        if (r8 != 0) goto L_0x0089;
    L_0x0045:
        r15.onCreate(r0);	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
    L_0x0048:
        r8 = r15.mNewVersion;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r0.setVersion(r8);	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r0.setTransactionSuccessful();	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r0.endTransaction();	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
    L_0x0053:
        r15.onOpen(r0);	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r6 = 0;
        r15.mIsInitializing = r6;
        r10 = r15.mDatabase;
        if (r10 == 0) goto L_0x0062;
    L_0x005d:
        r10 = r15.mDatabase;	 Catch:{ Exception -> 0x00aa }
        r10.close();	 Catch:{ Exception -> 0x00aa }
    L_0x0062:
        r15.mDatabase = r0;
        goto L_0x0029;
    L_0x0065:
        r11 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r11.<init>();	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r7 = r15.mDir;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r11 = r11.append(r7);	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r4 = "/";	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r11 = r11.append(r4);	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r7 = r15.mName;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r11 = r11.append(r7);	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r7 = r11.toString();	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r2 = 0;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r6 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r0 = android.database.sqlite.SQLiteDatabase.openDatabase(r7, r2, r6);	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        goto L_0x0038;
    L_0x0089:
        r9 = r15.mNewVersion;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        if (r8 <= r9) goto L_0x009e;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
    L_0x008d:
        r9 = r15.mNewVersion;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r15.onDowngrade(r0, r8, r9);	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        goto L_0x0048;
    L_0x0093:
        r12 = move-exception;
        r0.endTransaction();	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        throw r12;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
    L_0x0098:
        r13 = move-exception;
        r6 = 0;
        r15.mIsInitializing = r6;
        goto L_0x00a4;
    L_0x009d:
        throw r13;
    L_0x009e:
        r9 = r15.mNewVersion;	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        r15.onUpgrade(r0, r8, r9);	 Catch:{ Throwable -> 0x0093, Throwable -> 0x0098 }
        goto L_0x0048;
    L_0x00a4:
        if (r0 == 0) goto L_0x009d;
    L_0x00a6:
        r0.close();
        goto L_0x009d;
    L_0x00aa:
        r14 = move-exception;
        goto L_0x0062;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.db.CustomPathSQLiteOpenHelper.getWritableDatabase():android.database.sqlite.SQLiteDatabase");
    }

    public abstract void onCreate(SQLiteDatabase sQLiteDatabase) throws ;

    public abstract void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) throws ;

    public CustomPathSQLiteOpenHelper(String $r1, String $r2, CursorFactory $r3, int $i0) throws  {
        if ($i0 < 1) {
            throw new IllegalArgumentException("Version must be >= 1, was " + $i0);
        }
        this.mDir = $r1;
        this.mName = $r2;
        this.mFactory = $r3;
        this.mNewVersion = $i0;
    }

    public String getDatabaseName() throws  {
        return this.mName;
    }

    public synchronized SQLiteDatabase getReadableDatabase() throws  {
        SQLiteDatabase $r5;
        SQLiteDatabase $r2 = this.mDatabase;
        this = this;
        if ($r2 != null) {
            this = this;
            if (this.mDatabase.isOpen()) {
                $r5 = this.mDatabase;
                return $r5;
            }
            this.mDatabase = null;
        }
        if (this.mIsInitializing) {
            throw new IllegalStateException("getReadableDatabase called recursively");
        }
        try {
            $r5 = getWritableDatabase();
        } catch (SQLiteException $r1) {
            if (this.mName == null) {
                throw $r1;
            }
            Log.e(TAG, "Couldn't open " + this.mName + " for writing (will try read-only):", $r1);
            $r5 = null;
            this.mIsInitializing = true;
            String $r6 = this.mDir + "/" + this.mName;
            $r2 = SQLiteDatabase.openDatabase($r6, this.mFactory, 1);
            $r5 = $r2;
            if ($r2.getVersion() != this.mNewVersion) {
                throw new SQLiteException("Can't upgrade read-only database from version " + $r2.getVersion() + " to " + this.mNewVersion + ": " + $r6);
            }
            onOpen($r2);
            Log.w(TAG, "Opened " + this.mName + " in read-only mode");
            this.mDatabase = $r2;
            $r5 = this.mDatabase;
            this.mIsInitializing = false;
            if ($r2 != null) {
                SQLiteDatabase $r11 = this.mDatabase;
                if ($r2 != $r11) {
                    $r2.close();
                }
            }
        } catch (Throwable th) {
            this.mIsInitializing = false;
            if ($r5 != null) {
                CustomPathSQLiteOpenHelper $r22 = this;
                this = $r22;
                if ($r5 != $r22.mDatabase) {
                    $r5.close();
                }
            }
        }
        return $r5;
    }

    public synchronized void close() throws  {
        if (this.mIsInitializing) {
            throw new IllegalStateException("Closed during initialization");
        } else if (this.mDatabase != null && this.mDatabase.isOpen()) {
            this.mDatabase.close();
            this.mDatabase = null;
        }
    }

    public void onDowngrade(SQLiteDatabase db, int $i0, int $i1) throws  {
        throw new SQLiteException("Can't downgrade database from version " + $i0 + " to " + $i1);
    }

    public void onOpen(SQLiteDatabase db) throws  {
    }
}
