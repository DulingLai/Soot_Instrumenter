package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.sqlite.CursorWrapper;
import dalvik.annotation.Signature;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

@KeepName
/* compiled from: dalvik_source_com.waze.apk */
public final class DataHolder extends AbstractSafeParcelable implements Closeable {
    public static final Creator<DataHolder> CREATOR = new zze();
    private static final Builder Ha = new C06951(new String[0], null);
    private final String[] GS;
    Bundle GT;
    private final CursorWindow[] GU;
    private final Bundle GV;
    int[] GW;
    int GX;
    private Object GY;
    private boolean GZ;
    boolean mClosed;
    private final int mVersionCode;
    private final int xL;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private final String[] GS;
        private final ArrayList<HashMap<String, Object>> Hb;
        private final String Hc;
        private final HashMap<Object, Integer> Hd;
        private boolean He;
        private String Hf;

        private Builder(String[] $r1, String $r2) throws  {
            this.GS = (String[]) zzab.zzag($r1);
            this.Hb = new ArrayList();
            this.Hc = $r2;
            this.Hd = new HashMap();
            this.He = false;
            this.Hf = null;
        }

        private int zza(@Signature({"(", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;)I"}) HashMap<String, Object> $r1) throws  {
            if (this.Hc == null) {
                return -1;
            }
            Object $r3 = $r1.get(this.Hc);
            if ($r3 == null) {
                return -1;
            }
            Integer $r5 = (Integer) this.Hd.get($r3);
            if ($r5 != null) {
                return $r5.intValue();
            }
            this.Hd.put($r3, Integer.valueOf(this.Hb.size()));
            return -1;
        }

        private void zzavg() throws  {
            if (this.Hc != null) {
                this.Hd.clear();
                int $i0 = this.Hb.size();
                for (int $i1 = 0; $i1 < $i0; $i1++) {
                    Object $r4 = ((HashMap) this.Hb.get($i1)).get(this.Hc);
                    if ($r4 != null) {
                        this.Hd.put($r4, Integer.valueOf($i1));
                    }
                }
            }
        }

        private boolean zzgn(String $r1) throws  {
            com.google.android.gms.common.internal.zzb.zzac($r1);
            return this.He && $r1.equals(this.Hf);
        }

        public DataHolder build(int $i0) throws  {
            return new DataHolder(this, $i0, null);
        }

        public DataHolder build(int $i0, Bundle $r1) throws  {
            return new DataHolder(this, $i0, $r1, -1);
        }

        public DataHolder build(int $i0, Bundle $r1, int $i1) throws  {
            return new DataHolder(this, $i0, $r1, $i1);
        }

        public boolean containsRowWithValue(String $r1, Object $r2) throws  {
            int $i0 = this.Hb.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                if (zzaa.equal(((HashMap) this.Hb.get($i1)).get($r1), $r2)) {
                    return true;
                }
            }
            return false;
        }

        public Builder descendingSort(String $r1) throws  {
            if (zzgn($r1)) {
                return this;
            }
            sort($r1);
            Collections.reverse(this.Hb);
            return this;
        }

        public int getCount() throws  {
            return this.Hb.size();
        }

        public void modifyUniqueRowValue(Object $r1, String $r2, Object $r3) throws  {
            if (this.Hc != null) {
                Integer $r7 = (Integer) this.Hd.get($r1);
                if ($r7 != null) {
                    ((HashMap) this.Hb.get($r7.intValue())).put($r2, $r3);
                }
            }
        }

        public Builder removeRowsWithValue(String $r1, Object $r2) throws  {
            for (int $i0 = this.Hb.size() - 1; $i0 >= 0; $i0--) {
                if (zzaa.equal(((HashMap) this.Hb.get($i0)).get($r1), $r2)) {
                    this.Hb.remove($i0);
                }
            }
            return this;
        }

        public Builder sort(String $r1) throws  {
            if (zzgn($r1)) {
                return this;
            }
            Collections.sort(this.Hb, new zza($r1));
            zzavg();
            this.He = true;
            this.Hf = $r1;
            return this;
        }

        public Builder withRow(ContentValues $r1) throws  {
            com.google.android.gms.common.internal.zzb.zzac($r1);
            HashMap $r2 = new HashMap($r1.size());
            for (Entry $r6 : $r1.valueSet()) {
                $r2.put((String) $r6.getKey(), $r6.getValue());
            }
            return withRow($r2);
        }

        public Builder withRow(@Signature({"(", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;)", "Lcom/google/android/gms/common/data/DataHolder$Builder;"}) HashMap<String, Object> $r1) throws  {
            com.google.android.gms.common.internal.zzb.zzac($r1);
            int $i0 = zza((HashMap) $r1);
            if ($i0 == -1) {
                this.Hb.add($r1);
            } else {
                this.Hb.remove($i0);
                this.Hb.add($i0, $r1);
            }
            this.He = false;
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06951 extends Builder {
        C06951(String[] $r1, String $r2) throws  {
            super($r1, $r2);
        }

        public Builder withRow(ContentValues contentValues) throws  {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }

        public Builder withRow(@Signature({"(", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;)", "Lcom/google/android/gms/common/data/DataHolder$Builder;"}) HashMap<String, Object> hashMap) throws  {
            throw new UnsupportedOperationException("Cannot add data to empty builder");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza implements Comparator<HashMap<String, Object>> {
        private final String Hg;

        zza(String $r1) throws  {
            this.Hg = (String) zzab.zzag($r1);
        }

        public /* synthetic */ int compare(Object $r1, Object $r2) throws  {
            return zza((HashMap) $r1, (HashMap) $r2);
        }

        public int zza(@Signature({"(", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;)I"}) HashMap<String, Object> $r1, @Signature({"(", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;)I"}) HashMap<String, Object> $r2) throws  {
            Object $r4 = zzab.zzag($r1.get(this.Hg));
            Object $r5 = zzab.zzag($r2.get(this.Hg));
            if ($r4.equals($r5)) {
                return 0;
            }
            if ($r4 instanceof Boolean) {
                return ((Boolean) $r4).compareTo((Boolean) $r5);
            }
            if ($r4 instanceof Long) {
                return ((Long) $r4).compareTo((Long) $r5);
            }
            if ($r4 instanceof Integer) {
                return ((Integer) $r4).compareTo((Integer) $r5);
            }
            if ($r4 instanceof String) {
                return ((String) $r4).compareTo((String) $r5);
            }
            if ($r4 instanceof Double) {
                return ((Double) $r4).compareTo((Double) $r5);
            }
            if ($r4 instanceof Float) {
                return ((Float) $r4).compareTo((Float) $r5);
            }
            String $r3 = String.valueOf($r4);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf($r3).length() + 24).append("Unknown type for lValue ").append($r3).toString());
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class zzb extends RuntimeException {
        public zzb(String $r1) throws  {
            super($r1);
        }
    }

    DataHolder(int $i0, String[] $r1, CursorWindow[] $r2, int $i1, Bundle $r3) throws  {
        this.mClosed = false;
        this.GZ = true;
        this.mVersionCode = $i0;
        this.GS = $r1;
        this.GU = $r2;
        this.xL = $i1;
        this.GV = $r3;
    }

    public DataHolder(Cursor $r1, int $i0, Bundle $r2) throws  {
        this(new CursorWrapper($r1), $i0, $r2);
    }

    private DataHolder(Builder $r1, int $i0, Bundle $r2) throws  {
        this($r1.GS, zza($r1, -1), $i0, $r2);
    }

    private DataHolder(Builder $r1, int $i0, Bundle $r2, int $i1) throws  {
        this($r1.GS, zza($r1, $i1), $i0, $r2);
    }

    public DataHolder(CursorWrapper $r1, int $i0, Bundle $r2) throws  {
        this($r1.getColumnNames(), zza($r1), $i0, $r2);
    }

    public DataHolder(String[] $r1, CursorWindow[] $r2, int $i0, Bundle $r3) throws  {
        this.mClosed = false;
        this.GZ = true;
        this.mVersionCode = 1;
        this.GS = (String[]) zzab.zzag($r1);
        this.GU = (CursorWindow[]) zzab.zzag($r2);
        this.xL = $i0;
        this.GV = $r3;
        validateContents();
    }

    public static Builder builder(String[] $r0) throws  {
        return new Builder($r0, null);
    }

    public static Builder builder(String[] $r0, String $r1) throws  {
        zzab.zzag($r1);
        return new Builder($r0, $r1);
    }

    public static DataHolder empty(int $i0) throws  {
        return empty($i0, null);
    }

    public static DataHolder empty(int $i0, Bundle $r0) throws  {
        return new DataHolder(Ha, $i0, $r0);
    }

    private static android.database.CursorWindow[] zza(com.google.android.gms.common.data.DataHolder.Builder r50, int r51) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0229 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4 = 0;
        r0 = r50;
        r5 = r0.GS;
        r6 = r5.length;
        if (r6 != 0) goto L_0x000e;
    L_0x000a:
        r8 = 0;
        r7 = new android.database.CursorWindow[r8];
        return r7;
    L_0x000e:
        if (r51 < 0) goto L_0x001e;
    L_0x0010:
        r0 = r50;
        r9 = r0.Hb;
        r6 = r9.size();
        r0 = r51;
        if (r0 < r6) goto L_0x00af;
    L_0x001e:
        r0 = r50;
        r10 = r0.Hb;
    L_0x0024:
        r51 = r10.size();
        r11 = new android.database.CursorWindow;
        r8 = 0;
        r11.<init>(r8);
        r9 = new java.util.ArrayList;
        r9.<init>();
        r9.add(r11);
        r0 = r50;
        r5 = r0.GS;
        r6 = r5.length;
        r11.setNumColumns(r6);
        r6 = 0;
        r12 = 0;
    L_0x0042:
        r0 = r51;
        if (r6 >= r0) goto L_0x0281;
    L_0x0046:
        r13 = r11.allocRow();	 Catch:{ RuntimeException -> 0x0210 }
        if (r13 != 0) goto L_0x00bd;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x004c:
        r14 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0210 }
        r8 = 72;	 Catch:{ RuntimeException -> 0x0210 }
        r14.<init>(r8);	 Catch:{ RuntimeException -> 0x0210 }
        r15 = "Allocating additional cursor window for large data set (row ";	 Catch:{ RuntimeException -> 0x0210 }
        r14 = r14.append(r15);	 Catch:{ RuntimeException -> 0x0210 }
        r14 = r14.append(r6);	 Catch:{ RuntimeException -> 0x0210 }
        r15 = ")";	 Catch:{ RuntimeException -> 0x0210 }
        r14 = r14.append(r15);	 Catch:{ RuntimeException -> 0x0210 }
        r16 = r14.toString();	 Catch:{ RuntimeException -> 0x0210 }
        r15 = "DataHolder";	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r16;	 Catch:{ RuntimeException -> 0x0210 }
        android.util.Log.d(r15, r0);	 Catch:{ RuntimeException -> 0x0210 }
        goto L_0x0072;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x006f:
        goto L_0x0024;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0072:
        r11 = new android.database.CursorWindow;	 Catch:{ RuntimeException -> 0x0210 }
        r8 = 0;	 Catch:{ RuntimeException -> 0x0210 }
        r11.<init>(r8);	 Catch:{ RuntimeException -> 0x0210 }
        r11.setStartPosition(r6);	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r50;	 Catch:{ RuntimeException -> 0x0210 }
        r5 = r0.GS;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r5.length;	 Catch:{ RuntimeException -> 0x0210 }
        r17 = r0;	 Catch:{ RuntimeException -> 0x0210 }
        r11.setNumColumns(r0);	 Catch:{ RuntimeException -> 0x0210 }
        r9.add(r11);	 Catch:{ RuntimeException -> 0x0210 }
        r13 = r11.allocRow();	 Catch:{ RuntimeException -> 0x0210 }
        if (r13 != 0) goto L_0x00bd;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0090:
        r15 = "DataHolder";	 Catch:{ RuntimeException -> 0x0210 }
        r18 = "Unable to allocate row to hold data.";	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r18;	 Catch:{ RuntimeException -> 0x0210 }
        android.util.Log.e(r15, r0);	 Catch:{ RuntimeException -> 0x0210 }
        r9.remove(r11);	 Catch:{ RuntimeException -> 0x0210 }
        r51 = r9.size();	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r51;	 Catch:{ RuntimeException -> 0x0210 }
        r7 = new android.database.CursorWindow[r0];	 Catch:{ RuntimeException -> 0x0210 }
        r19 = r9.toArray(r7);	 Catch:{ RuntimeException -> 0x0210 }
        r20 = r19;	 Catch:{ RuntimeException -> 0x0210 }
        r20 = (android.database.CursorWindow[]) r20;	 Catch:{ RuntimeException -> 0x0210 }
        r7 = r20;	 Catch:{ RuntimeException -> 0x0210 }
        return r7;
    L_0x00af:
        r0 = r50;
        r9 = r0.Hb;
        r8 = 0;
        r0 = r51;
        r10 = r9.subList(r8, r0);
        goto L_0x006f;
    L_0x00bd:
        r21 = r10.get(r6);	 Catch:{ RuntimeException -> 0x0210 }
        r23 = r21;	 Catch:{ RuntimeException -> 0x0210 }
        r23 = (java.util.Map) r23;	 Catch:{ RuntimeException -> 0x0210 }
        r22 = r23;	 Catch:{ RuntimeException -> 0x0210 }
        r17 = 0;
        r13 = 1;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x00ca:
        r0 = r50;	 Catch:{ RuntimeException -> 0x0210 }
        r5 = r0.GS;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r5.length;	 Catch:{ RuntimeException -> 0x0210 }
        r24 = r0;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r17;
        r1 = r24;
        if (r0 >= r1) goto L_0x0229;
    L_0x00d9:
        if (r13 == 0) goto L_0x0229;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x00db:
        r0 = r50;	 Catch:{ RuntimeException -> 0x0210 }
        r5 = r0.GS;	 Catch:{ RuntimeException -> 0x0210 }
        r16 = r5[r17];	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r22;	 Catch:{ RuntimeException -> 0x0210 }
        r1 = r16;	 Catch:{ RuntimeException -> 0x0210 }
        r21 = r0.get(r1);	 Catch:{ RuntimeException -> 0x0210 }
        if (r21 != 0) goto L_0x00f6;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x00ed:
        r0 = r17;	 Catch:{ RuntimeException -> 0x0210 }
        r13 = r11.putNull(r6, r0);	 Catch:{ RuntimeException -> 0x0210 }
    L_0x00f3:
        r17 = r17 + 1;
        goto L_0x00ca;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x00f6:
        r0 = r21;
        r13 = r0 instanceof java.lang.String;
        if (r13 == 0) goto L_0x010b;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x00fc:
        r25 = r21;	 Catch:{ RuntimeException -> 0x0210 }
        r25 = (java.lang.String) r25;	 Catch:{ RuntimeException -> 0x0210 }
        r16 = r25;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r16;	 Catch:{ RuntimeException -> 0x0210 }
        r1 = r17;	 Catch:{ RuntimeException -> 0x0210 }
        r13 = r11.putString(r0, r6, r1);	 Catch:{ RuntimeException -> 0x0210 }
        goto L_0x00f3;
    L_0x010b:
        r0 = r21;
        r13 = r0 instanceof java.lang.Long;
        if (r13 == 0) goto L_0x0126;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0111:
        r27 = r21;	 Catch:{ RuntimeException -> 0x0210 }
        r27 = (java.lang.Long) r27;	 Catch:{ RuntimeException -> 0x0210 }
        r26 = r27;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r26;	 Catch:{ RuntimeException -> 0x0210 }
        r28 = r0.longValue();	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r28;	 Catch:{ RuntimeException -> 0x0210 }
        r2 = r17;	 Catch:{ RuntimeException -> 0x0210 }
        r13 = r11.putLong(r0, r6, r2);	 Catch:{ RuntimeException -> 0x0210 }
        goto L_0x00f3;
    L_0x0126:
        r0 = r21;
        r13 = r0 instanceof java.lang.Integer;
        if (r13 == 0) goto L_0x014c;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x012c:
        r31 = r21;	 Catch:{ RuntimeException -> 0x0210 }
        r31 = (java.lang.Integer) r31;	 Catch:{ RuntimeException -> 0x0210 }
        r30 = r31;	 Catch:{ RuntimeException -> 0x0210 }
        goto L_0x0136;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0133:
        goto L_0x00f3;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0136:
        r0 = r30;	 Catch:{ RuntimeException -> 0x0210 }
        r24 = r0.intValue();	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r24;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = (long) r0;	 Catch:{ RuntimeException -> 0x0210 }
        r28 = r0;	 Catch:{ RuntimeException -> 0x0210 }
        goto L_0x0145;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0142:
        goto L_0x00f3;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0145:
        r2 = r17;	 Catch:{ RuntimeException -> 0x0210 }
        r13 = r11.putLong(r0, r6, r2);	 Catch:{ RuntimeException -> 0x0210 }
        goto L_0x00f3;
    L_0x014c:
        r0 = r21;
        r13 = r0 instanceof java.lang.Boolean;
        if (r13 == 0) goto L_0x0172;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0152:
        goto L_0x0156;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0153:
        goto L_0x00f3;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0156:
        r33 = r21;	 Catch:{ RuntimeException -> 0x0210 }
        r33 = (java.lang.Boolean) r33;	 Catch:{ RuntimeException -> 0x0210 }
        r32 = r33;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r32;	 Catch:{ RuntimeException -> 0x0210 }
        r13 = r0.booleanValue();	 Catch:{ RuntimeException -> 0x0210 }
        if (r13 == 0) goto L_0x016f;
    L_0x0164:
        r28 = 1;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0166:
        r0 = r28;	 Catch:{ RuntimeException -> 0x0210 }
        r2 = r17;	 Catch:{ RuntimeException -> 0x0210 }
        r13 = r11.putLong(r0, r6, r2);	 Catch:{ RuntimeException -> 0x0210 }
        goto L_0x00f3;
    L_0x016f:
        r28 = 0;
        goto L_0x0166;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0172:
        r0 = r21;
        r13 = r0 instanceof byte[];
        if (r13 == 0) goto L_0x0187;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0178:
        r35 = r21;	 Catch:{ RuntimeException -> 0x0210 }
        r35 = (byte[]) r35;	 Catch:{ RuntimeException -> 0x0210 }
        r34 = r35;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r34;	 Catch:{ RuntimeException -> 0x0210 }
        r1 = r17;	 Catch:{ RuntimeException -> 0x0210 }
        r13 = r11.putBlob(r0, r6, r1);	 Catch:{ RuntimeException -> 0x0210 }
        goto L_0x0133;
    L_0x0187:
        r0 = r21;
        r13 = r0 instanceof java.lang.Double;
        if (r13 == 0) goto L_0x01a2;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x018d:
        r37 = r21;	 Catch:{ RuntimeException -> 0x0210 }
        r37 = (java.lang.Double) r37;	 Catch:{ RuntimeException -> 0x0210 }
        r36 = r37;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r36;	 Catch:{ RuntimeException -> 0x0210 }
        r38 = r0.doubleValue();	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r38;	 Catch:{ RuntimeException -> 0x0210 }
        r2 = r17;	 Catch:{ RuntimeException -> 0x0210 }
        r13 = r11.putDouble(r0, r6, r2);	 Catch:{ RuntimeException -> 0x0210 }
        goto L_0x0142;
    L_0x01a2:
        r0 = r21;
        r13 = r0 instanceof java.lang.Float;
        if (r13 == 0) goto L_0x01c0;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x01a8:
        r41 = r21;	 Catch:{ RuntimeException -> 0x0210 }
        r41 = (java.lang.Float) r41;	 Catch:{ RuntimeException -> 0x0210 }
        r40 = r41;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r40;	 Catch:{ RuntimeException -> 0x0210 }
        r42 = r0.floatValue();	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r42;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = (double) r0;	 Catch:{ RuntimeException -> 0x0210 }
        r38 = r0;	 Catch:{ RuntimeException -> 0x0210 }
        r2 = r17;	 Catch:{ RuntimeException -> 0x0210 }
        r13 = r11.putDouble(r0, r6, r2);	 Catch:{ RuntimeException -> 0x0210 }
        goto L_0x0153;
    L_0x01c0:
        r43 = new java.lang.IllegalArgumentException;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r21;	 Catch:{ RuntimeException -> 0x0210 }
        r44 = java.lang.String.valueOf(r0);	 Catch:{ RuntimeException -> 0x0210 }
        r14 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r16;	 Catch:{ RuntimeException -> 0x0210 }
        r45 = java.lang.String.valueOf(r0);	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r45;	 Catch:{ RuntimeException -> 0x0210 }
        r51 = r0.length();	 Catch:{ RuntimeException -> 0x0210 }
        r51 = r51 + 32;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r44;	 Catch:{ RuntimeException -> 0x0210 }
        r45 = java.lang.String.valueOf(r0);	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r45;	 Catch:{ RuntimeException -> 0x0210 }
        r6 = r0.length();	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r51;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r0 + r6;	 Catch:{ RuntimeException -> 0x0210 }
        r51 = r0;	 Catch:{ RuntimeException -> 0x0210 }
        r14.<init>(r0);	 Catch:{ RuntimeException -> 0x0210 }
        r15 = "Unsupported object for column ";	 Catch:{ RuntimeException -> 0x0210 }
        r14 = r14.append(r15);	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r16;	 Catch:{ RuntimeException -> 0x0210 }
        r14 = r14.append(r0);	 Catch:{ RuntimeException -> 0x0210 }
        r15 = ": ";	 Catch:{ RuntimeException -> 0x0210 }
        r14 = r14.append(r15);	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r44;	 Catch:{ RuntimeException -> 0x0210 }
        r14 = r14.append(r0);	 Catch:{ RuntimeException -> 0x0210 }
        r16 = r14.toString();	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r43;	 Catch:{ RuntimeException -> 0x0210 }
        r1 = r16;	 Catch:{ RuntimeException -> 0x0210 }
        r0.<init>(r1);	 Catch:{ RuntimeException -> 0x0210 }
        throw r43;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0210:
        r46 = move-exception;
        r51 = r9.size();
    L_0x0215:
        r0 = r51;
        if (r4 >= r0) goto L_0x0280;
    L_0x0219:
        r21 = r9.get(r4);
        r47 = r21;
        r47 = (android.database.CursorWindow) r47;
        r11 = r47;
        r11.close();
        r4 = r4 + 1;
        goto L_0x0215;
    L_0x0229:
        if (r13 != 0) goto L_0x027e;
    L_0x022b:
        if (r12 == 0) goto L_0x0237;
    L_0x022d:
        r48 = new com.google.android.gms.common.data.DataHolder$zzb;
        r15 = "Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.";	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r48;	 Catch:{ RuntimeException -> 0x0210 }
        r0.<init>(r15);	 Catch:{ RuntimeException -> 0x0210 }
        throw r48;	 Catch:{ RuntimeException -> 0x0210 }
    L_0x0237:
        r14 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0210 }
        r8 = 74;	 Catch:{ RuntimeException -> 0x0210 }
        r14.<init>(r8);	 Catch:{ RuntimeException -> 0x0210 }
        r15 = "Couldn't populate window data for row ";	 Catch:{ RuntimeException -> 0x0210 }
        r14 = r14.append(r15);	 Catch:{ RuntimeException -> 0x0210 }
        r14 = r14.append(r6);	 Catch:{ RuntimeException -> 0x0210 }
        r15 = " - allocating new window.";	 Catch:{ RuntimeException -> 0x0210 }
        r14 = r14.append(r15);	 Catch:{ RuntimeException -> 0x0210 }
        r16 = r14.toString();	 Catch:{ RuntimeException -> 0x0210 }
        r15 = "DataHolder";	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r16;	 Catch:{ RuntimeException -> 0x0210 }
        android.util.Log.d(r15, r0);	 Catch:{ RuntimeException -> 0x0210 }
        r11.freeLastRow();	 Catch:{ RuntimeException -> 0x0210 }
        r11 = new android.database.CursorWindow;	 Catch:{ RuntimeException -> 0x0210 }
        r8 = 0;	 Catch:{ RuntimeException -> 0x0210 }
        r11.<init>(r8);	 Catch:{ RuntimeException -> 0x0210 }
        r11.setStartPosition(r6);	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r50;	 Catch:{ RuntimeException -> 0x0210 }
        r5 = r0.GS;	 Catch:{ RuntimeException -> 0x0210 }
        r0 = r5.length;	 Catch:{ RuntimeException -> 0x0210 }
        r17 = r0;	 Catch:{ RuntimeException -> 0x0210 }
        r11.setNumColumns(r0);	 Catch:{ RuntimeException -> 0x0210 }
        r9.add(r11);	 Catch:{ RuntimeException -> 0x0210 }
        r6 = r6 + -1;
        r12 = 1;
        goto L_0x027b;
    L_0x0278:
        goto L_0x0042;
    L_0x027b:
        r6 = r6 + 1;
        goto L_0x0278;
    L_0x027e:
        r12 = 0;
        goto L_0x027b;
    L_0x0280:
        throw r46;
    L_0x0281:
        r51 = r9.size();
        r0 = r51;
        r7 = new android.database.CursorWindow[r0];
        r19 = r9.toArray(r7);
        r49 = r19;
        r49 = (android.database.CursorWindow[]) r49;
        r7 = r49;
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.data.DataHolder.zza(com.google.android.gms.common.data.DataHolder$Builder, int):android.database.CursorWindow[]");
    }

    private static android.database.CursorWindow[] zza(com.google.android.gms.common.sqlite.CursorWrapper r13) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0051 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = new java.util.ArrayList;
        r0.<init>();
        r1 = r13.getCount();	 Catch:{ Throwable -> 0x005e }
        r2 = r13.getWindow();	 Catch:{ Throwable -> 0x005e }
        if (r2 == 0) goto L_0x0071;	 Catch:{ Throwable -> 0x005e }
    L_0x000f:
        r3 = r2.getStartPosition();	 Catch:{ Throwable -> 0x005e }
        if (r3 != 0) goto L_0x0071;	 Catch:{ Throwable -> 0x005e }
    L_0x0015:
        r2.acquireReference();	 Catch:{ Throwable -> 0x005e }
        r4 = 0;	 Catch:{ Throwable -> 0x005e }
        r13.setWindow(r4);	 Catch:{ Throwable -> 0x005e }
        r0.add(r2);	 Catch:{ Throwable -> 0x005e }
        r3 = r2.getNumRows();	 Catch:{ Throwable -> 0x005e }
    L_0x0023:
        if (r3 >= r1) goto L_0x003f;	 Catch:{ Throwable -> 0x005e }
    L_0x0025:
        r5 = r13.moveToPosition(r3);	 Catch:{ Throwable -> 0x005e }
        if (r5 == 0) goto L_0x003f;	 Catch:{ Throwable -> 0x005e }
    L_0x002b:
        r2 = r13.getWindow();	 Catch:{ Throwable -> 0x005e }
        r6 = r2;
        if (r2 == 0) goto L_0x0051;	 Catch:{ Throwable -> 0x005e }
    L_0x0032:
        r2.acquireReference();	 Catch:{ Throwable -> 0x005e }
        r4 = 0;	 Catch:{ Throwable -> 0x005e }
        r13.setWindow(r4);	 Catch:{ Throwable -> 0x005e }
    L_0x0039:
        r3 = r6.getNumRows();	 Catch:{ Throwable -> 0x005e }
        if (r3 != 0) goto L_0x0063;
    L_0x003f:
        r13.close();
        r1 = r0.size();
        r7 = new android.database.CursorWindow[r1];
        r8 = r0.toArray(r7);
        r9 = r8;
        r9 = (android.database.CursorWindow[]) r9;
        r7 = r9;
        return r7;
    L_0x0051:
        r6 = new android.database.CursorWindow;	 Catch:{ Throwable -> 0x005e }
        r10 = 0;	 Catch:{ Throwable -> 0x005e }
        r6.<init>(r10);	 Catch:{ Throwable -> 0x005e }
        r6.setStartPosition(r3);	 Catch:{ Throwable -> 0x005e }
        r13.fillWindow(r3, r6);	 Catch:{ Throwable -> 0x005e }
        goto L_0x0039;
    L_0x005e:
        r11 = move-exception;
        r13.close();
        throw r11;
    L_0x0063:
        r0.add(r6);	 Catch:{ Throwable -> 0x005e }
        r3 = r6.getStartPosition();	 Catch:{ Throwable -> 0x005e }
        r12 = r6.getNumRows();	 Catch:{ Throwable -> 0x005e }
        r3 = r12 + r3;
        goto L_0x0023;
    L_0x0071:
        r3 = 0;
        goto L_0x0023;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.data.DataHolder.zza(com.google.android.gms.common.sqlite.CursorWrapper):android.database.CursorWindow[]");
    }

    private void zzi(String $r1, int $i0) throws  {
        if (this.GT == null || !this.GT.containsKey($r1)) {
            String $r2 = "No such column: ";
            $r1 = String.valueOf($r1);
            throw new IllegalArgumentException($r1.length() != 0 ? $r2.concat($r1) : new String("No such column: "));
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if ($i0 < 0 || $i0 >= this.GX) {
            throw new CursorIndexOutOfBoundsException($i0, this.GX);
        }
    }

    public void close() throws  {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (CursorWindow $r2 : this.GU) {
                    $r2.close();
                }
            }
        }
    }

    public void copyToBuffer(String $r1, int $i0, int $i1, CharArrayBuffer $r2) throws  {
        zzi($r1, $i0);
        this.GU[$i1].copyStringToBuffer($i0, this.GT.getInt($r1), $r2);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.GZ && this.GU.length > 0 && !isClosed()) {
                String $r3;
                if (this.GY == null) {
                    $r3 = "internal object: ";
                    String $r4 = String.valueOf(toString());
                    $r3 = $r4.length() != 0 ? $r3.concat($r4) : new String("internal object: ");
                } else {
                    $r3 = this.GY.toString();
                }
                Log.e("DataBuffer", new StringBuilder(String.valueOf($r3).length() + 161).append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (").append($r3).append(")").toString());
                close();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public boolean getBoolean(String $r1, int $i0, int $i1) throws  {
        zzi($r1, $i0);
        return Long.valueOf(this.GU[$i1].getLong($i0, this.GT.getInt($r1))).longValue() == 1;
    }

    public byte[] getByteArray(String $r1, int $i0, int $i1) throws  {
        zzi($r1, $i0);
        return this.GU[$i1].getBlob($i0, this.GT.getInt($r1));
    }

    public int getCount() throws  {
        return this.GX;
    }

    public double getDouble(String $r1, int $i0, int $i1) throws  {
        zzi($r1, $i0);
        return this.GU[$i1].getDouble($i0, this.GT.getInt($r1));
    }

    public float getFloat(String $r1, int $i0, int $i1) throws  {
        zzi($r1, $i0);
        return this.GU[$i1].getFloat($i0, this.GT.getInt($r1));
    }

    public int getInteger(String $r1, int $i0, int $i1) throws  {
        zzi($r1, $i0);
        return this.GU[$i1].getInt($i0, this.GT.getInt($r1));
    }

    public long getLong(String $r1, int $i0, int $i1) throws  {
        zzi($r1, $i0);
        return this.GU[$i1].getLong($i0, this.GT.getInt($r1));
    }

    public int getStatusCode() throws  {
        return this.xL;
    }

    public String getString(String $r1, int $i0, int $i1) throws  {
        zzi($r1, $i0);
        return this.GU[$i1].getString($i0, this.GT.getInt($r1));
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public boolean hasColumn(String $r1) throws  {
        return this.GT.containsKey($r1);
    }

    public boolean hasNull(String $r1, int $i0, int $i1) throws  {
        zzi($r1, $i0);
        return this.GU[$i1].isNull($i0, this.GT.getInt($r1));
    }

    public boolean isClosed() throws  {
        boolean z0;
        synchronized (this) {
            z0 = this.mClosed;
        }
        return z0;
    }

    public void logCursorMetadataForDebugging() throws  {
        Log.d("DataHolder", "*******************************************");
        String str = "DataHolder";
        Log.d(str, "num cursor windows : " + this.GU.length);
        str = "DataHolder";
        Log.d(str, "total number of objects in holder: " + this.GX);
        str = "DataHolder";
        Log.d(str, "total mumber of windowOffsets: " + this.GW.length);
        for (int $i0 = 0; $i0 < this.GW.length; $i0++) {
            str = "DataHolder";
            Log.d(str, "offset for window " + $i0 + " : " + this.GW[$i0]);
            Log.d("DataHolder", "num rows for window " + $i0 + " : " + this.GU[$i0].getNumRows());
            str = "DataHolder";
            Log.d(str, "start pos for window " + $i0 + " : " + this.GU[$i0].getStartPosition());
        }
        Log.d("DataHolder", "*******************************************");
    }

    public Uri parseUri(String $r1, int $i0, int $i1) throws  {
        $r1 = getString($r1, $i0, $i1);
        return $r1 == null ? null : Uri.parse($r1);
    }

    public void validateContents() throws  {
        int $i1;
        this.GT = new Bundle();
        for ($i1 = 0; $i1 < this.GS.length; $i1++) {
            this.GT.putInt(this.GS[$i1], $i1);
        }
        this.GW = new int[this.GU.length];
        $i1 = 0;
        for (int $i0 = 0; $i0 < this.GU.length; $i0++) {
            this.GW[$i0] = $i1;
            $i1 += this.GU[$i0].getNumRows() - ($i1 - this.GU[$i0].getStartPosition());
        }
        this.GX = $i1;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zze.zza(this, $r1, $i0);
    }

    public void zzaa(Object $r1) throws  {
        this.GY = $r1;
    }

    public Bundle zzava() throws  {
        return this.GV;
    }

    String[] zzave() throws  {
        return this.GS;
    }

    CursorWindow[] zzavf() throws  {
        return this.GU;
    }

    public int zzic(int $i0) throws  {
        int $i1 = 0;
        boolean $z0 = $i0 >= 0 && $i0 < this.GX;
        zzab.zzbm($z0);
        while ($i1 < this.GW.length) {
            if ($i0 < this.GW[$i1]) {
                $i1--;
                break;
            }
            $i1++;
        }
        return $i1 == this.GW.length ? $i1 - 1 : $i1;
    }
}
