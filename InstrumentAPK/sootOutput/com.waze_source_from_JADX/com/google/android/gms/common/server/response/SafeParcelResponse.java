package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import android.util.SparseArray;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.common.util.zzc;
import com.google.android.gms.common.util.zzn;
import com.google.android.gms.common.util.zzo;
import dalvik.annotation.Signature;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class SafeParcelResponse extends FastSafeParcelableJsonResponse {
    public static final zzb CREATOR = new zzb();
    private final Parcel LC;
    private final int LD;
    private int LF;
    private int LG;
    private final FieldMappingDictionary La;
    private final String mClassName;
    private final int mVersionCode;

    SafeParcelResponse(int $i0, Parcel $r1, FieldMappingDictionary $r2) throws  {
        this.mVersionCode = $i0;
        this.LC = (Parcel) zzab.zzag($r1);
        this.LD = 2;
        this.La = $r2;
        if (this.La == null) {
            this.mClassName = null;
        } else {
            this.mClassName = this.La.getRootClassName();
        }
        this.LF = 2;
    }

    public SafeParcelResponse(FieldMappingDictionary $r1, String $r2) throws  {
        this.mVersionCode = 1;
        this.LC = Parcel.obtain();
        this.LD = 0;
        this.La = (FieldMappingDictionary) zzab.zzag($r1);
        this.mClassName = (String) zzab.zzag($r2);
        this.LF = 0;
    }

    private void zza(StringBuilder $r1, int $i0, Object $r2) throws  {
        switch ($i0) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                $r1.append($r2);
                return;
            case 7:
                $r1.append("\"").append(zzn.zzhf($r2.toString())).append("\"");
                return;
            case 8:
                $r1.append("\"").append(zzc.encode((byte[]) $r2)).append("\"");
                return;
            case 9:
                $r1.append("\"").append(zzc.zzq((byte[]) $r2));
                $r1.append("\"");
                return;
            case 10:
                zzo.zza($r1, (HashMap) $r2);
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown type = " + $i0);
        }
    }

    private void zza(@Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) StringBuilder $r1, @Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) Field<?, ?> $r2, @Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) Parcel $r3, @Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) int $i0) throws  {
        switch ($r2.getTypeOut()) {
            case 0:
                zzb($r1, (Field) $r2, getOriginalValue($r2, Integer.valueOf(zza.zzg($r3, $i0))));
                return;
            case 1:
                zzb($r1, (Field) $r2, getOriginalValue($r2, zza.zzk($r3, $i0)));
                return;
            case 2:
                zzb($r1, (Field) $r2, getOriginalValue($r2, Long.valueOf(zza.zzi($r3, $i0))));
                return;
            case 3:
                zzb($r1, (Field) $r2, getOriginalValue($r2, Float.valueOf(zza.zzl($r3, $i0))));
                return;
            case 4:
                zzb($r1, (Field) $r2, getOriginalValue($r2, Double.valueOf(zza.zzn($r3, $i0))));
                return;
            case 5:
                zzb($r1, (Field) $r2, getOriginalValue($r2, zza.zzp($r3, $i0)));
                return;
            case 6:
                zzb($r1, (Field) $r2, getOriginalValue($r2, Boolean.valueOf(zza.zzc($r3, $i0))));
                return;
            case 7:
                zzb($r1, (Field) $r2, getOriginalValue($r2, zza.zzq($r3, $i0)));
                return;
            case 8:
            case 9:
                zzb($r1, (Field) $r2, getOriginalValue($r2, zza.zzt($r3, $i0)));
                return;
            case 10:
                zzb($r1, (Field) $r2, getOriginalValue($r2, zzu(zza.zzs($r3, $i0))));
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown field out type = " + $r2.getTypeOut());
        }
    }

    private void zza(@Signature({"(", "Ljava/lang/StringBuilder;", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) StringBuilder $r1, @Signature({"(", "Ljava/lang/StringBuilder;", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) String $r2, @Signature({"(", "Ljava/lang/StringBuilder;", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) Field<?, ?> $r3, @Signature({"(", "Ljava/lang/StringBuilder;", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) Parcel $r4, @Signature({"(", "Ljava/lang/StringBuilder;", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) int $i0) throws  {
        $r1.append("\"").append($r2).append("\":");
        if ($r3.hasConverter()) {
            zza($r1, $r3, $r4, $i0);
        } else {
            zzb($r1, $r3, $r4, $i0);
        }
    }

    private void zza(@Signature({"(", "Ljava/lang/StringBuilder;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;", "Landroid/os/Parcel;", ")V"}) StringBuilder $r1, @Signature({"(", "Ljava/lang/StringBuilder;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;", "Landroid/os/Parcel;", ")V"}) Map<String, Field<?, ?>> $r2, @Signature({"(", "Ljava/lang/StringBuilder;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;", "Landroid/os/Parcel;", ")V"}) Parcel $r3) throws  {
        SparseArray $r4 = zzav($r2);
        $r1.append('{');
        int $i0 = zza.zzdz($r3);
        boolean $z0 = false;
        while ($r3.dataPosition() < $i0) {
            int $i1 = zza.zzdy($r3);
            Entry $r6 = (Entry) $r4.get(zza.zziv($i1));
            if ($r6 != null) {
                if ($z0) {
                    $r1.append(",");
                }
                zza($r1, (String) $r6.getKey(), (Field) $r6.getValue(), $r3, $i1);
                $z0 = true;
            }
        }
        if ($r3.dataPosition() != $i0) {
            throw new zza.zza("Overread allowed size end=" + $i0, $r3);
        } else {
            $r1.append('}');
        }
    }

    private static SparseArray<Entry<String, Field<?, ?>>> zzav(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;)", "Landroid/util/SparseArray", "<", "Ljava/util/Map$Entry", "<", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;>;>;"}) Map<String, Field<?, ?>> $r0) throws  {
        SparseArray $r1 = new SparseArray();
        for (Entry $r5 : $r0.entrySet()) {
            $r1.put(((Field) $r5.getValue()).getSafeParcelableFieldId(), $r5);
        }
        return $r1;
    }

    private void zzb(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;)V"}) Field<?, ?> $r1) throws  {
        if (!$r1.isValidSafeParcelableFieldId()) {
            throw new IllegalStateException("Field does not have a valid safe parcelable field id.");
        } else if (this.LC == null) {
            throw new IllegalStateException("Internal Parcel object is null.");
        } else {
            switch (this.LF) {
                case 0:
                    this.LG = zzb.zzea(this.LC);
                    this.LF = 1;
                    return;
                case 1:
                    return;
                case 2:
                    throw new IllegalStateException("Attempted to parse JSON with a SafeParcelResponse object that is already filled with data.");
                default:
                    throw new IllegalStateException("Unknown parse state in SafeParcelResponse.");
            }
        }
    }

    private void zzb(@Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) StringBuilder $r1, @Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) Field<?, ?> $r2, @Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) Parcel $r3, @Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Landroid/os/Parcel;", "I)V"}) int $i0) throws  {
        if ($r2.isTypeOutArray()) {
            $r1.append("[");
            switch ($r2.getTypeOut()) {
                case 0:
                    com.google.android.gms.common.util.zzb.zza($r1, zza.zzw($r3, $i0));
                    break;
                case 1:
                    com.google.android.gms.common.util.zzb.zza($r1, zza.zzy($r3, $i0));
                    break;
                case 2:
                    com.google.android.gms.common.util.zzb.zza($r1, zza.zzx($r3, $i0));
                    break;
                case 3:
                    com.google.android.gms.common.util.zzb.zza($r1, zza.zzz($r3, $i0));
                    break;
                case 4:
                    com.google.android.gms.common.util.zzb.zza($r1, zza.zzaa($r3, $i0));
                    break;
                case 5:
                    com.google.android.gms.common.util.zzb.zza($r1, zza.zzab($r3, $i0));
                    break;
                case 6:
                    com.google.android.gms.common.util.zzb.zza($r1, zza.zzv($r3, $i0));
                    break;
                case 7:
                    com.google.android.gms.common.util.zzb.zza($r1, zza.zzac($r3, $i0));
                    break;
                case 8:
                case 9:
                case 10:
                    throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                case 11:
                    Parcel[] $r14 = zza.zzag($r3, $i0);
                    $i0 = $r14.length;
                    for (int $i1 = 0; $i1 < $i0; $i1++) {
                        if ($i1 > 0) {
                            $r1.append(",");
                        }
                        $r14[$i1].setDataPosition(0);
                        zza($r1, $r2.getConcreteTypeFieldMappingFromDictionary(), $r14[$i1]);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown field type out.");
            }
            $r1.append("]");
            return;
        }
        String $r18;
        byte[] $r19;
        switch ($r2.getTypeOut()) {
            case 0:
                $r1.append(zza.zzg($r3, $i0));
                return;
            case 1:
                $r1.append(zza.zzk($r3, $i0));
                return;
            case 2:
                $r1.append(zza.zzi($r3, $i0));
                return;
            case 3:
                $r1.append(zza.zzl($r3, $i0));
                return;
            case 4:
                $r1.append(zza.zzn($r3, $i0));
                return;
            case 5:
                $r1.append(zza.zzp($r3, $i0));
                return;
            case 6:
                $r1.append(zza.zzc($r3, $i0));
                return;
            case 7:
                $r18 = zza.zzq($r3, $i0);
                $r1.append("\"").append(zzn.zzhf($r18)).append("\"");
                return;
            case 8:
                $r19 = zza.zzt($r3, $i0);
                $r1.append("\"").append(zzc.encode($r19)).append("\"");
                return;
            case 9:
                $r19 = zza.zzt($r3, $i0);
                $r1.append("\"").append(zzc.zzq($r19));
                $r1.append("\"");
                return;
            case 10:
                Bundle $r21 = zza.zzs($r3, $i0);
                Set<String> $r22 = $r21.keySet();
                $r22.size();
                $r1.append("{");
                boolean $z0 = true;
                for (String $r182 : $r22) {
                    if (!$z0) {
                        $r1.append(",");
                    }
                    $r1.append("\"").append($r182).append("\"");
                    $r1.append(":");
                    $r1.append("\"").append(zzn.zzhf($r21.getString($r182))).append("\"");
                    $z0 = false;
                }
                $r1.append("}");
                return;
            case 11:
                $r3 = zza.zzaf($r3, $i0);
                $r3.setDataPosition(0);
                zza($r1, $r2.getConcreteTypeFieldMappingFromDictionary(), $r3);
                return;
            default:
                throw new IllegalStateException("Unknown field type out");
        }
    }

    private void zzb(@Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/Object;", ")V"}) StringBuilder $r1, @Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/Object;", ")V"}) Field<?, ?> $r2, @Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/Object;", ")V"}) Object $r3) throws  {
        if ($r2.isTypeInArray()) {
            zzb($r1, (Field) $r2, (ArrayList) $r3);
        } else {
            zza($r1, $r2.getTypeIn(), $r3);
        }
    }

    private void zzb(@Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/util/ArrayList", "<*>;)V"}) StringBuilder $r1, @Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/util/ArrayList", "<*>;)V"}) Field<?, ?> $r2, @Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/util/ArrayList", "<*>;)V"}) ArrayList<?> $r3) throws  {
        $r1.append("[");
        int $i0 = $r3.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if ($i1 != 0) {
                $r1.append(",");
            }
            zza($r1, $r2.getTypeIn(), $r3.get($i1));
        }
        $r1.append("]");
    }

    public static HashMap<String, String> zzu(@Signature({"(", "Landroid/os/Bundle;", ")", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) Bundle $r0) throws  {
        HashMap $r1 = new HashMap();
        for (String $r5 : $r0.keySet()) {
            $r1.put($r5, $r0.getString($r5));
        }
        return $r1;
    }

    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) ArrayList<T> $r3) throws  {
        zzb($r1);
        ArrayList $r4 = new ArrayList();
        $r3.size();
        Iterator $r5 = $r3.iterator();
        while ($r5.hasNext()) {
            $r4.add(((SafeParcelResponse) ((FastJsonResponse) $r5.next())).zzaxy());
        }
        zzb.zzd(this.LC, $r1.getSafeParcelableFieldId(), $r4, true);
    }

    public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> $r1, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r4) throws  {
        zzb($r1);
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), ((SafeParcelResponse) $r4).zzaxy(), true);
    }

    public Map<String, Field<?, ?>> getFieldMappings() throws  {
        return this.La == null ? null : this.La.getFieldMapping(this.mClassName);
    }

    public Object getValueObject(String str) throws  {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public boolean isPrimitiveFieldSet(String str) throws  {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    protected void setBigDecimalInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/math/BigDecimal;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/math/BigDecimal;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/math/BigDecimal;", ")V"}) BigDecimal $r3) throws  {
        zzb($r1);
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r3, true);
    }

    protected void setBigDecimalsInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;)V"}) ArrayList<BigDecimal> $r3) throws  {
        zzb($r1);
        int $i0 = $r3.size();
        BigDecimal[] $r4 = new BigDecimal[$i0];
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r4[$i1] = (BigDecimal) $r3.get($i1);
        }
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r4, true);
    }

    protected void setBigIntegerInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/math/BigInteger;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/math/BigInteger;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/math/BigInteger;", ")V"}) BigInteger $r3) throws  {
        zzb($r1);
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r3, true);
    }

    protected void setBigIntegersInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;)V"}) ArrayList<BigInteger> $r3) throws  {
        zzb($r1);
        int $i0 = $r3.size();
        BigInteger[] $r4 = new BigInteger[$i0];
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r4[$i1] = (BigInteger) $r3.get($i1);
        }
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r4, true);
    }

    protected void setBooleanInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
        zzb($r1);
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $z0);
    }

    protected void setBooleansInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;)V"}) ArrayList<Boolean> $r3) throws  {
        zzb($r1);
        int $i0 = $r3.size();
        boolean[] $r4 = new boolean[$i0];
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r4[$i1] = ((Boolean) $r3.get($i1)).booleanValue();
        }
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r4, true);
    }

    protected void setDecodedBytesInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "[B)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "[B)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "[B)V"}) byte[] $r3) throws  {
        zzb($r1);
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r3, true);
    }

    protected void setDoubleInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "D)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "D)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "D)V"}) double $d0) throws  {
        zzb($r1);
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $d0);
    }

    protected void setDoublesInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;)V"}) ArrayList<Double> $r3) throws  {
        zzb($r1);
        int $i0 = $r3.size();
        double[] $r4 = new double[$i0];
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r4[$i1] = ((Double) $r3.get($i1)).doubleValue();
        }
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r4, true);
    }

    protected void setFloatInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "F)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "F)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "F)V"}) float $f0) throws  {
        zzb($r1);
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $f0);
    }

    protected void setFloatsInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;)V"}) ArrayList<Float> $r3) throws  {
        zzb($r1);
        int $i0 = $r3.size();
        float[] $r4 = new float[$i0];
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r4[$i1] = ((Float) $r3.get($i1)).floatValue();
        }
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r4, true);
    }

    protected void setIntegerInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) int $i0) throws  {
        zzb($r1);
        zzb.zzc(this.LC, $r1.getSafeParcelableFieldId(), $i0);
    }

    protected void setIntegersInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;)V"}) ArrayList<Integer> $r3) throws  {
        zzb($r1);
        int $i0 = $r3.size();
        int[] $r4 = new int[$i0];
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r4[$i1] = ((Integer) $r3.get($i1)).intValue();
        }
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r4, true);
    }

    protected void setLongInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) long $l0) throws  {
        zzb($r1);
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $l0);
    }

    protected void setLongsInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;)V"}) ArrayList<Long> $r3) throws  {
        zzb($r1);
        int $i0 = $r3.size();
        long[] $r4 = new long[$i0];
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r4[$i1] = ((Long) $r3.get($i1)).longValue();
        }
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r4, true);
    }

    protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
        zzb($r1);
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r3, true);
    }

    protected void setStringMapInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r3) throws  {
        zzb($r1);
        Bundle $r4 = new Bundle();
        for (String str2 : $r3.keySet()) {
            $r4.putString(str2, (String) $r3.get(str2));
        }
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r4, true);
    }

    protected void setStringsInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) Field<?, ?> $r1, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) String str, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) ArrayList<String> $r3) throws  {
        zzb($r1);
        int $i0 = $r3.size();
        String[] $r4 = new String[$i0];
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r4[$i1] = (String) $r3.get($i1);
        }
        zzb.zza(this.LC, $r1.getSafeParcelableFieldId(), $r4, true);
    }

    public String toString() throws  {
        zzab.zzb(this.La, (Object) "Cannot convert to JSON on client side.");
        Parcel $r3 = zzaxy();
        $r3.setDataPosition(0);
        StringBuilder $r4 = new StringBuilder(100);
        zza($r4, this.La.getFieldMapping(this.mClassName), $r3);
        return $r4.toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb $r2 = CREATOR;
        zzb.zza(this, $r1, $i0);
    }

    public Parcel zzaxy() throws  {
        switch (this.LF) {
            case 0:
                this.LG = zzb.zzea(this.LC);
                zzb.zzaj(this.LC, this.LG);
                this.LF = 2;
                break;
            case 1:
                zzb.zzaj(this.LC, this.LG);
                this.LF = 2;
                break;
            default:
                break;
        }
        return this.LC;
    }

    FieldMappingDictionary zzaxz() throws  {
        switch (this.LD) {
            case 0:
                return null;
            case 1:
                return this.La;
            case 2:
                return this.La;
            default:
                throw new IllegalStateException("Invalid creation type: " + this.LD);
        }
    }
}
