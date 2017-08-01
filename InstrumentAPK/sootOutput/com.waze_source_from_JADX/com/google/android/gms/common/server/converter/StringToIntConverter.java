package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse.FieldConverter;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: dalvik_source_com.waze.apk */
public final class StringToIntConverter extends AbstractSafeParcelable implements FieldConverter<String, Integer> {
    public static final zzb CREATOR = new zzb();
    private final HashMap<String, Integer> KT;
    private final SparseArray<String> KU;
    private final ArrayList<Entry> KV;
    private final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Entry extends AbstractSafeParcelable {
        public static final zzc CREATOR = new zzc();
        final int KW;
        final String stringValue;
        final int versionCode;

        Entry(int $i0, String $r1, int $i1) throws  {
            this.versionCode = $i0;
            this.stringValue = $r1;
            this.KW = $i1;
        }

        Entry(String $r1, int $i0) throws  {
            this.versionCode = 1;
            this.stringValue = $r1;
            this.KW = $i0;
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            zzc $r2 = CREATOR;
            zzc.zza(this, $r1, $i0);
        }
    }

    public StringToIntConverter() throws  {
        this.mVersionCode = 1;
        this.KT = new HashMap();
        this.KU = new SparseArray();
        this.KV = null;
    }

    StringToIntConverter(@Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/server/converter/StringToIntConverter$Entry;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/server/converter/StringToIntConverter$Entry;", ">;)V"}) ArrayList<Entry> $r1) throws  {
        this.mVersionCode = $i0;
        this.KT = new HashMap();
        this.KU = new SparseArray();
        this.KV = null;
        zzh($r1);
    }

    private void zzh(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/server/converter/StringToIntConverter$Entry;", ">;)V"}) ArrayList<Entry> $r1) throws  {
        Iterator $r3 = $r1.iterator();
        while ($r3.hasNext()) {
            Entry $r5 = (Entry) $r3.next();
            zzj($r5.stringValue, $r5.KW);
        }
    }

    public /* synthetic */ Object convert(Object $r2) throws  {
        return zzgz((String) $r2);
    }

    public /* synthetic */ Object convertBack(Object $r2) throws  {
        return zze((Integer) $r2);
    }

    public int getTypeIn() throws  {
        return 7;
    }

    public int getTypeOut() throws  {
        return 0;
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb $r2 = CREATOR;
        zzb.zza(this, $r1, $i0);
    }

    ArrayList<Entry> zzaxs() throws  {
        ArrayList $r1 = new ArrayList();
        for (String $r7 : this.KT.keySet()) {
            $r1.add(new Entry($r7, ((Integer) this.KT.get($r7)).intValue()));
        }
        return $r1;
    }

    public String zze(Integer $r1) throws  {
        String $r4 = (String) this.KU.get($r1.intValue());
        return ($r4 == null && this.KT.containsKey("gms_unknown")) ? "gms_unknown" : $r4;
    }

    public Integer zzgz(String $r1) throws  {
        Integer $r4 = (Integer) this.KT.get($r1);
        return $r4 == null ? (Integer) this.KT.get("gms_unknown") : $r4;
    }

    public StringToIntConverter zzj(String $r1, int $i0) throws  {
        this.KT.put($r1, Integer.valueOf($i0));
        this.KU.put($i0, $r1);
        return this;
    }
}
