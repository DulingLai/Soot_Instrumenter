package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: dalvik_source_com.waze.apk */
public class FieldMappingDictionary extends AbstractSafeParcelable {
    public static final FieldMappingDictionaryCreator CREATOR = new FieldMappingDictionaryCreator();
    private final HashMap<String, Map<String, Field<?, ?>>> Lx;
    private final ArrayList<Entry> Ly;
    private final String Lz;
    private final int mVersionCode;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Entry extends AbstractSafeParcelable {
        public static final FieldMappingDictionaryEntryCreator CREATOR = new FieldMappingDictionaryEntryCreator();
        final ArrayList<FieldMapPair> LA;
        final String className;
        final int versionCode;

        Entry(@Signature({"(I", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/server/response/FieldMappingDictionary$FieldMapPair;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/server/response/FieldMappingDictionary$FieldMapPair;", ">;)V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/server/response/FieldMappingDictionary$FieldMapPair;", ">;)V"}) ArrayList<FieldMapPair> $r2) throws  {
            this.versionCode = $i0;
            this.className = $r1;
            this.LA = $r2;
        }

        Entry(@Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;)V"}) Map<String, Field<?, ?>> $r2) throws  {
            this.versionCode = 1;
            this.className = $r1;
            this.LA = zzau($r2);
        }

        private static ArrayList<FieldMapPair> zzau(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;)", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/server/response/FieldMappingDictionary$FieldMapPair;", ">;"}) Map<String, Field<?, ?>> $r0) throws  {
            if ($r0 == null) {
                return null;
            }
            ArrayList $r1 = new ArrayList();
            for (String $r6 : $r0.keySet()) {
                $r1.add(new FieldMapPair($r6, (Field) $r0.get($r6)));
            }
            return $r1;
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            FieldMappingDictionaryEntryCreator $r2 = CREATOR;
            FieldMappingDictionaryEntryCreator.zza(this, $r1, $i0);
        }

        HashMap<String, Field<?, ?>> zzaxx() throws  {
            HashMap $r1 = new HashMap();
            int $i0 = this.LA.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                FieldMapPair $r5 = (FieldMapPair) this.LA.get($i1);
                $r1.put($r5.zzca, $r5.LB);
            }
            return $r1;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class FieldMapPair extends AbstractSafeParcelable {
        public static final FieldMapPairCreator CREATOR = new FieldMapPairCreator();
        final Field<?, ?> LB;
        final int versionCode;
        final String zzca;

        FieldMapPair(@Signature({"(I", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;)V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;)V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;)V"}) Field<?, ?> $r2) throws  {
            this.versionCode = $i0;
            this.zzca = $r1;
            this.LB = $r2;
        }

        FieldMapPair(@Signature({"(", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;)V"}) Field<?, ?> $r2) throws  {
            this.versionCode = 1;
            this.zzca = $r1;
            this.LB = $r2;
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            FieldMapPairCreator $r2 = CREATOR;
            FieldMapPairCreator.zza(this, $r1, $i0);
        }
    }

    FieldMappingDictionary(@Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/server/response/FieldMappingDictionary$Entry;", ">;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/server/response/FieldMappingDictionary$Entry;", ">;", "Ljava/lang/String;", ")V"}) ArrayList<Entry> $r1, @Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/server/response/FieldMappingDictionary$Entry;", ">;", "Ljava/lang/String;", ")V"}) String $r2) throws  {
        this.mVersionCode = $i0;
        this.Ly = null;
        this.Lx = zzi($r1);
        this.Lz = (String) zzab.zzag($r2);
        linkFields();
    }

    public FieldMappingDictionary(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;)V"}) Class<? extends FastJsonResponse> $r1) throws  {
        this.mVersionCode = 1;
        this.Ly = null;
        this.Lx = new HashMap();
        this.Lz = $r1.getCanonicalName();
    }

    private static HashMap<String, Map<String, Field<?, ?>>> zzi(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/google/android/gms/common/server/response/FieldMappingDictionary$Entry;", ">;)", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;>;"}) ArrayList<Entry> $r0) throws  {
        HashMap $r1 = new HashMap();
        int $i0 = $r0.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            Entry $r4 = (Entry) $r0.get($i1);
            $r1.put($r4.className, $r4.zzaxx());
        }
        return $r1;
    }

    public void copyInternalFieldMappings() throws  {
        for (String $r5 : this.Lx.keySet()) {
            Map $r6 = (Map) this.Lx.get($r5);
            HashMap $r1 = new HashMap();
            for (String $r8 : $r6.keySet()) {
                $r1.put($r8, ((Field) $r6.get($r8)).copyForDictionary());
            }
            this.Lx.put($r5, $r1);
        }
    }

    public Map<String, Field<?, ?>> getFieldMapping(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;)", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;"}) Class<? extends FastJsonResponse> $r1) throws  {
        return (Map) this.Lx.get($r1.getCanonicalName());
    }

    public Map<String, Field<?, ?>> getFieldMapping(@Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;"}) String $r1) throws  {
        return (Map) this.Lx.get($r1);
    }

    public String getRootClassName() throws  {
        return this.Lz;
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public boolean hasFieldMappingForClass(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;)Z"}) Class<? extends FastJsonResponse> $r1) throws  {
        return this.Lx.containsKey($r1.getCanonicalName());
    }

    public void linkFields() throws  {
        for (String $r5 : this.Lx.keySet()) {
            Map $r6 = (Map) this.Lx.get($r5);
            for (String str : $r6.keySet()) {
                ((Field) $r6.get(str)).setFieldMappingDictionary(this);
            }
        }
    }

    public void put(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;)V"}) Class<? extends FastJsonResponse> $r1, @Signature({"(", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;)V"}) Map<String, Field<?, ?>> $r2) throws  {
        this.Lx.put($r1.getCanonicalName(), $r2);
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder();
        for (String $r6 : this.Lx.keySet()) {
            $r1.append($r6).append(":\n");
            Map $r8 = (Map) this.Lx.get($r6);
            for (String $r62 : $r8.keySet()) {
                $r1.append("  ").append($r62).append(": ");
                $r1.append($r8.get($r62));
            }
        }
        return $r1.toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        FieldMappingDictionaryCreator $r2 = CREATOR;
        FieldMappingDictionaryCreator.zza(this, $r1, $i0);
    }

    ArrayList<Entry> zzaxw() throws  {
        ArrayList $r1 = new ArrayList();
        for (String $r7 : this.Lx.keySet()) {
            $r1.add(new Entry($r7, (Map) this.Lx.get($r7)));
        }
        return $r1;
    }
}
