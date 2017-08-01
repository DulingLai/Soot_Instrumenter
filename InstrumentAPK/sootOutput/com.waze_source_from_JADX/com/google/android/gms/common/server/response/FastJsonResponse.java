package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.server.converter.ConverterWrapper;
import com.google.android.gms.common.server.response.FastParser.ParseException;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.common.util.zzn;
import com.google.android.gms.people.PeopleConstants.Endpoints;
import dalvik.annotation.Signature;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class FastJsonResponse {
    protected static final String QUOTE = "\"";
    private byte[] KY;
    private boolean KZ;
    private int zzbim;

    /* compiled from: dalvik_source_com.waze.apk */
    public interface FieldConverter<I, O> {
        O convert(@Signature({"(TI;)TO;"}) I i) throws ;

        I convertBack(@Signature({"(TO;)TI;"}) O o) throws ;

        int getTypeIn() throws ;

        int getTypeOut() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Field<I, O> extends AbstractSafeParcelable {
        public static final FieldCreator CREATOR = new FieldCreator();
        private FieldMappingDictionary La;
        private FieldConverter<I, O> Lb;
        protected final Class<? extends FastJsonResponse> mConcreteType;
        protected final String mConcreteTypeName;
        protected final String mOutputFieldName;
        protected final int mSafeParcelableFieldId;
        protected final int mTypeIn;
        protected final boolean mTypeInArray;
        protected final int mTypeOut;
        protected final boolean mTypeOutArray;
        private final int mVersionCode;

        Field(int $i0, int $i1, boolean $z0, int $i2, boolean $z1, String $r1, int $i3, String $r2, ConverterWrapper $r3) throws  {
            this.mVersionCode = $i0;
            this.mTypeIn = $i1;
            this.mTypeInArray = $z0;
            this.mTypeOut = $i2;
            this.mTypeOutArray = $z1;
            this.mOutputFieldName = $r1;
            this.mSafeParcelableFieldId = $i3;
            if ($r2 == null) {
                this.mConcreteType = null;
                this.mConcreteTypeName = null;
            } else {
                this.mConcreteType = SafeParcelResponse.class;
                this.mConcreteTypeName = $r2;
            }
            if ($r3 == null) {
                this.Lb = null;
            } else {
                this.Lb = $r3.zzaxr();
            }
        }

        protected Field(@Signature({"(IZIZ", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<TI;TO;>;)V"}) int $i0, @Signature({"(IZIZ", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<TI;TO;>;)V"}) boolean $z0, @Signature({"(IZIZ", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<TI;TO;>;)V"}) int $i1, @Signature({"(IZIZ", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<TI;TO;>;)V"}) boolean $z1, @Signature({"(IZIZ", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<TI;TO;>;)V"}) String $r1, @Signature({"(IZIZ", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<TI;TO;>;)V"}) int $i2, @Signature({"(IZIZ", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<TI;TO;>;)V"}) Class<? extends FastJsonResponse> $r2, @Signature({"(IZIZ", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<+", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<TI;TO;>;)V"}) FieldConverter<I, O> $r3) throws  {
            this.mVersionCode = 1;
            this.mTypeIn = $i0;
            this.mTypeInArray = $z0;
            this.mTypeOut = $i1;
            this.mTypeOutArray = $z1;
            this.mOutputFieldName = $r1;
            this.mSafeParcelableFieldId = $i2;
            this.mConcreteType = $r2;
            if ($r2 == null) {
                this.mConcreteTypeName = null;
            } else {
                this.mConcreteTypeName = $r2.getCanonicalName();
            }
            this.Lb = $r3;
        }

        public static Field<byte[], byte[]> forBase64(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<[B[B>;"}) String $r0) throws  {
            return new Field(8, false, 8, false, $r0, -1, null, null);
        }

        public static Field<byte[], byte[]> forBase64(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<[B[B>;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<[B[B>;"}) int $i0) throws  {
            return new Field(8, false, 8, false, $r0, $i0, null, null);
        }

        public static Field<byte[], byte[]> forBase64UrlSafe(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<[B[B>;"}) String $r0) throws  {
            return new Field(9, false, 9, false, $r0, -1, null, null);
        }

        public static Field<byte[], byte[]> forBase64UrlSafe(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<[B[B>;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<[B[B>;"}) int $i0) throws  {
            return new Field(9, false, 9, false, $r0, $i0, null, null);
        }

        public static Field<BigDecimal, BigDecimal> forBigDecimal(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/math/BigDecimal;", "Ljava/math/BigDecimal;", ">;"}) String $r0) throws  {
            return new Field(5, false, 5, false, $r0, -1, null, null);
        }

        public static Field<BigDecimal, BigDecimal> forBigDecimal(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/math/BigDecimal;", "Ljava/math/BigDecimal;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/math/BigDecimal;", "Ljava/math/BigDecimal;", ">;"}) int $i0) throws  {
            return new Field(5, false, 5, false, $r0, $i0, null, null);
        }

        public static Field<ArrayList<BigDecimal>, ArrayList<BigDecimal>> forBigDecimals(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;>;"}) String $r0) throws  {
            return new Field(5, true, 5, true, $r0, -1, null, null);
        }

        public static Field<ArrayList<BigDecimal>, ArrayList<BigDecimal>> forBigDecimals(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;>;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;>;"}) int $i0) throws  {
            return new Field(5, true, 5, true, $r0, $i0, null, null);
        }

        public static Field<BigInteger, BigInteger> forBigInteger(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/math/BigInteger;", "Ljava/math/BigInteger;", ">;"}) String $r0) throws  {
            return new Field(1, false, 1, false, $r0, -1, null, null);
        }

        public static Field<BigInteger, BigInteger> forBigInteger(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/math/BigInteger;", "Ljava/math/BigInteger;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/math/BigInteger;", "Ljava/math/BigInteger;", ">;"}) int $i0) throws  {
            return new Field(1, false, 1, false, $r0, $i0, null, null);
        }

        public static Field<ArrayList<BigInteger>, ArrayList<BigInteger>> forBigIntegers(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;>;"}) String $r0) throws  {
            return new Field(0, true, 1, true, $r0, -1, null, null);
        }

        public static Field<ArrayList<BigInteger>, ArrayList<BigInteger>> forBigIntegers(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;>;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;>;"}) int $i0) throws  {
            return new Field(0, true, 1, true, $r0, $i0, null, null);
        }

        public static Field<Boolean, Boolean> forBoolean(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Boolean;", "Ljava/lang/Boolean;", ">;"}) String $r0) throws  {
            return new Field(6, false, 6, false, $r0, -1, null, null);
        }

        public static Field<Boolean, Boolean> forBoolean(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Boolean;", "Ljava/lang/Boolean;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Boolean;", "Ljava/lang/Boolean;", ">;"}) int $i0) throws  {
            return new Field(6, false, 6, false, $r0, $i0, null, null);
        }

        public static Field<ArrayList<Boolean>, ArrayList<Boolean>> forBooleans(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;>;"}) String $r0) throws  {
            return new Field(6, true, 6, true, $r0, -1, null, null);
        }

        public static Field<ArrayList<Boolean>, ArrayList<Boolean>> forBooleans(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;>;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;>;"}) int $i0) throws  {
            return new Field(6, true, 6, true, $r0, $i0, null, null);
        }

        public static <T extends FastJsonResponse> Field<T, T> forConcreteType(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<TT;TT;>;"}) String $r0, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<TT;TT;>;"}) int $i0, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<TT;TT;>;"}) Class<T> $r1) throws  {
            return new Field(11, false, 11, false, $r0, $i0, $r1, null);
        }

        public static <T extends FastJsonResponse> Field<T, T> forConcreteType(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<TT;TT;>;"}) String $r0, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<TT;TT;>;"}) Class<T> $r1) throws  {
            return new Field(11, false, 11, false, $r0, -1, $r1, null);
        }

        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> forConcreteTypeArray(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<TT;>;", "Ljava/util/ArrayList", "<TT;>;>;"}) String $r0, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<TT;>;", "Ljava/util/ArrayList", "<TT;>;>;"}) int $i0, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<TT;>;", "Ljava/util/ArrayList", "<TT;>;>;"}) Class<T> $r1) throws  {
            return new Field(11, true, 11, true, $r0, $i0, $r1, null);
        }

        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> forConcreteTypeArray(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<TT;>;", "Ljava/util/ArrayList", "<TT;>;>;"}) String $r0, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<TT;>;", "Ljava/util/ArrayList", "<TT;>;>;"}) Class<T> $r1) throws  {
            return new Field(11, true, 11, true, $r0, -1, $r1, null);
        }

        public static Field<Double, Double> forDouble(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Double;", "Ljava/lang/Double;", ">;"}) String $r0) throws  {
            return new Field(4, false, 4, false, $r0, -1, null, null);
        }

        public static Field<Double, Double> forDouble(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Double;", "Ljava/lang/Double;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Double;", "Ljava/lang/Double;", ">;"}) int $i0) throws  {
            return new Field(4, false, 4, false, $r0, $i0, null, null);
        }

        public static Field<ArrayList<Double>, ArrayList<Double>> forDoubles(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;>;"}) String $r0) throws  {
            return new Field(4, true, 4, true, $r0, -1, null, null);
        }

        public static Field<ArrayList<Double>, ArrayList<Double>> forDoubles(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;>;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;>;"}) int $i0) throws  {
            return new Field(4, true, 4, true, $r0, $i0, null, null);
        }

        public static Field<Float, Float> forFloat(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Float;", "Ljava/lang/Float;", ">;"}) String $r0) throws  {
            return new Field(3, false, 3, false, $r0, -1, null, null);
        }

        public static Field<Float, Float> forFloat(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Float;", "Ljava/lang/Float;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Float;", "Ljava/lang/Float;", ">;"}) int $i0) throws  {
            return new Field(3, false, 3, false, $r0, $i0, null, null);
        }

        public static Field<ArrayList<Float>, ArrayList<Float>> forFloats(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;>;"}) String $r0) throws  {
            return new Field(3, true, 3, true, $r0, -1, null, null);
        }

        public static Field<ArrayList<Float>, ArrayList<Float>> forFloats(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;>;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;>;"}) int $i0) throws  {
            return new Field(3, true, 3, true, $r0, $i0, null, null);
        }

        public static Field<Integer, Integer> forInteger(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Integer;", "Ljava/lang/Integer;", ">;"}) String $r0) throws  {
            return new Field(0, false, 0, false, $r0, -1, null, null);
        }

        public static Field<Integer, Integer> forInteger(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Integer;", "Ljava/lang/Integer;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Integer;", "Ljava/lang/Integer;", ">;"}) int $i0) throws  {
            return new Field(0, false, 0, false, $r0, $i0, null, null);
        }

        public static Field<ArrayList<Integer>, ArrayList<Integer>> forIntegers(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;>;"}) String $r0) throws  {
            return new Field(0, true, 0, true, $r0, -1, null, null);
        }

        public static Field<ArrayList<Integer>, ArrayList<Integer>> forIntegers(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;>;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;>;"}) int $i0) throws  {
            return new Field(0, true, 0, true, $r0, $i0, null, null);
        }

        public static Field<Long, Long> forLong(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Long;", "Ljava/lang/Long;", ">;"}) String $r0) throws  {
            return new Field(2, false, 2, false, $r0, -1, null, null);
        }

        public static Field<Long, Long> forLong(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Long;", "Ljava/lang/Long;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Long;", "Ljava/lang/Long;", ">;"}) int $i0) throws  {
            return new Field(2, false, 2, false, $r0, $i0, null, null);
        }

        public static Field<ArrayList<Long>, ArrayList<Long>> forLongs(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;>;"}) String $r0) throws  {
            return new Field(2, true, 2, true, $r0, -1, null, null);
        }

        public static Field<ArrayList<Long>, ArrayList<Long>> forLongs(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;>;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;>;"}) int $i0) throws  {
            return new Field(2, true, 2, true, $r0, $i0, null, null);
        }

        public static Field<String, String> forString(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) String $r0) throws  {
            return new Field(7, false, 7, false, $r0, -1, null, null);
        }

        public static Field<String, String> forString(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) int $i0) throws  {
            return new Field(7, false, 7, false, $r0, $i0, null, null);
        }

        public static Field<HashMap<String, String>, HashMap<String, String>> forStringMap(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;>;"}) String $r0) throws  {
            return new Field(10, false, 10, false, $r0, -1, null, null);
        }

        public static Field<HashMap<String, String>, HashMap<String, String>> forStringMap(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;>;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;>;"}) int $i0) throws  {
            return new Field(10, false, 10, false, $r0, $i0, null, null);
        }

        public static Field<ArrayList<String>, ArrayList<String>> forStrings(@Signature({"(", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;>;"}) String $r0) throws  {
            return new Field(7, true, 7, true, $r0, -1, null, null);
        }

        public static Field<ArrayList<String>, ArrayList<String>> forStrings(@Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;>;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;>;"}) int $i0) throws  {
            return new Field(7, true, 7, true, $r0, $i0, null, null);
        }

        public static Field withConverter(@Signature({"(", "Ljava/lang/String;", "I", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<**>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "I", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<**>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) int $i0, @Signature({"(", "Ljava/lang/String;", "I", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<**>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) FieldConverter<?, ?> $r1, @Signature({"(", "Ljava/lang/String;", "I", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<**>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) boolean $z0) throws  {
            return new Field($r1.getTypeIn(), $z0, $r1.getTypeOut(), false, $r0, $i0, null, $r1);
        }

        public static <T extends FieldConverter> Field withConverter(@Signature({"<T::", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter;", ">(", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<TT;>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) String $r0, @Signature({"<T::", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter;", ">(", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<TT;>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) int $i0, @Signature({"<T::", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter;", ">(", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<TT;>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) Class<T> $r1, @Signature({"<T::", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter;", ">(", "Ljava/lang/String;", "I", "Ljava/lang/Class", "<TT;>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) boolean $z0) throws  {
            try {
                return withConverter($r0, $i0, (FieldConverter) $r1.newInstance(), $z0);
            } catch (InstantiationException $r5) {
                throw new RuntimeException($r5);
            } catch (IllegalAccessException $r7) {
                throw new RuntimeException($r7);
            }
        }

        public static Field withConverter(@Signature({"(", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<**>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<**>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) FieldConverter<?, ?> $r1, @Signature({"(", "Ljava/lang/String;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter", "<**>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) boolean $z0) throws  {
            return withConverter($r0, -1, (FieldConverter) $r1, $z0);
        }

        public static <T extends FieldConverter> Field withConverter(@Signature({"<T::", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) String $r0, @Signature({"<T::", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) Class<T> $r1, @Signature({"<T::", "Lcom/google/android/gms/common/server/response/FastJsonResponse$FieldConverter;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;Z)", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;"}) boolean $z0) throws  {
            return withConverter($r0, -1, (Class) $r1, $z0);
        }

        public O convert(@Signature({"(TI;)TO;"}) I $r1) throws  {
            return this.Lb.convert($r1);
        }

        public I convertBack(@Signature({"(TO;)TI;"}) O $r1) throws  {
            return this.Lb.convertBack($r1);
        }

        public Field<I, O> copyForDictionary() throws  {
            return new Field(this.mVersionCode, this.mTypeIn, this.mTypeInArray, this.mTypeOut, this.mTypeOutArray, this.mOutputFieldName, this.mSafeParcelableFieldId, this.mConcreteTypeName, zzaxv());
        }

        public Class<? extends FastJsonResponse> getConcreteType() throws  {
            return this.mConcreteType;
        }

        public Map<String, Field<?, ?>> getConcreteTypeFieldMappingFromDictionary() throws  {
            zzab.zzag(this.mConcreteTypeName);
            zzab.zzag(this.La);
            return this.La.getFieldMapping(this.mConcreteTypeName);
        }

        public String getOutputFieldName() throws  {
            return this.mOutputFieldName;
        }

        public int getSafeParcelableFieldId() throws  {
            return this.mSafeParcelableFieldId;
        }

        public int getTypeIn() throws  {
            return this.mTypeIn;
        }

        public int getTypeOut() throws  {
            return this.mTypeOut;
        }

        public int getVersionCode() throws  {
            return this.mVersionCode;
        }

        public boolean hasConverter() throws  {
            return this.Lb != null;
        }

        public boolean isTypeInArray() throws  {
            return this.mTypeInArray;
        }

        public boolean isTypeOutArray() throws  {
            return this.mTypeOutArray;
        }

        public boolean isValidSafeParcelableFieldId() throws  {
            return this.mSafeParcelableFieldId != -1;
        }

        public FastJsonResponse newConcreteTypeInstance() throws InstantiationException, IllegalAccessException {
            if (this.mConcreteType != SafeParcelResponse.class) {
                return (FastJsonResponse) this.mConcreteType.newInstance();
            }
            zzab.zzb(this.La, (Object) "The field mapping dictionary must be set if the concrete type is a SafeParcelResponse object.");
            return new SafeParcelResponse(this.La, this.mConcreteTypeName);
        }

        public void setFieldMappingDictionary(FieldMappingDictionary $r1) throws  {
            this.La = $r1;
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder();
            $r1.append("Field\n");
            $r1.append("            versionCode=").append(this.mVersionCode).append('\n');
            $r1.append("                 typeIn=").append(this.mTypeIn).append('\n');
            $r1.append("            typeInArray=").append(this.mTypeInArray).append('\n');
            $r1.append("                typeOut=").append(this.mTypeOut).append('\n');
            $r1.append("           typeOutArray=").append(this.mTypeOutArray).append('\n');
            $r1.append("        outputFieldName=").append(this.mOutputFieldName).append('\n');
            $r1.append("      safeParcelFieldId=").append(this.mSafeParcelableFieldId).append('\n');
            $r1.append("       concreteTypeName=").append(zzaxu()).append('\n');
            if (getConcreteType() != null) {
                $r1.append("     concreteType.class=").append(getConcreteType().getCanonicalName()).append('\n');
            }
            $r1.append("          converterName=").append(this.Lb == null ? "null" : this.Lb.getClass().getCanonicalName()).append('\n');
            return $r1.toString();
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            FieldCreator $r2 = CREATOR;
            FieldCreator.zza(this, $r1, $i0);
        }

        String zzaxu() throws  {
            return this.mConcreteTypeName == null ? null : this.mConcreteTypeName;
        }

        ConverterWrapper zzaxv() throws  {
            return this.Lb == null ? null : ConverterWrapper.zza(this.Lb);
        }
    }

    public static InputStream getUnzippedStream(byte[] $r0) throws  {
        ByteArrayInputStream $r1 = new ByteArrayInputStream($r0);
        if (IOUtils.isGzipByteBuffer($r0)) {
            try {
                return new GZIPInputStream($r1);
            } catch (IOException e) {
            }
        }
        return $r1;
    }

    private <I, O> void zza(@Signature({"<I:", "Ljava/lang/Object;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<TI;TO;>;TI;)V"}) Field<I, O> $r1, @Signature({"<I:", "Ljava/lang/Object;", "O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<TI;TO;>;TI;)V"}) I $r2) throws  {
        String $r3 = $r1.getOutputFieldName();
        Object $r22 = $r1.convert($r2);
        switch ($r1.getTypeOut()) {
            case 0:
                if (zzj($r3, $r22)) {
                    setIntegerInternal($r1, $r3, ((Integer) $r22).intValue());
                    return;
                }
                return;
            case 1:
                setBigIntegerInternal($r1, $r3, (BigInteger) $r22);
                return;
            case 2:
                if (zzj($r3, $r22)) {
                    setLongInternal($r1, $r3, ((Long) $r22).longValue());
                    return;
                }
                return;
            case 3:
                break;
            case 4:
                if (zzj($r3, $r22)) {
                    setDoubleInternal($r1, $r3, ((Double) $r22).doubleValue());
                    return;
                }
                return;
            case 5:
                setBigDecimalInternal($r1, $r3, (BigDecimal) $r22);
                return;
            case 6:
                if (zzj($r3, $r22)) {
                    setBooleanInternal($r1, $r3, ((Boolean) $r22).booleanValue());
                    return;
                }
                return;
            case 7:
                setStringInternal($r1, $r3, (String) $r22);
                return;
            case 8:
            case 9:
                if (zzj($r3, $r22)) {
                    setDecodedBytesInternal($r1, $r3, (byte[]) $r22);
                    return;
                }
                return;
            default:
                break;
        }
        throw new IllegalStateException("Unsupported type for conversion: " + $r1.getTypeOut());
    }

    private void zza(StringBuilder $r1, Field $r2, Object $r3) throws  {
        if ($r2.getTypeIn() == 11) {
            $r1.append(((FastJsonResponse) $r2.getConcreteType().cast($r3)).toString());
        } else if ($r2.getTypeIn() == 7) {
            $r1.append(QUOTE);
            $r1.append(zzn.zzhf((String) $r3));
            $r1.append(QUOTE);
        } else {
            $r1.append($r3);
        }
    }

    private void zza(@Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;", "Ljava/util/ArrayList", "<", "Ljava/lang/Object;", ">;)V"}) StringBuilder $r1, @Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;", "Ljava/util/ArrayList", "<", "Ljava/lang/Object;", ">;)V"}) Field $r2, @Signature({"(", "Ljava/lang/StringBuilder;", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field;", "Ljava/util/ArrayList", "<", "Ljava/lang/Object;", ">;)V"}) ArrayList<Object> $r3) throws  {
        $r1.append("[");
        int $i1 = $r3.size();
        for (int $i0 = 0; $i0 < $i1; $i0++) {
            if ($i0 > 0) {
                $r1.append(",");
            }
            Object $r4 = $r3.get($i0);
            if ($r4 != null) {
                zza($r1, $r2, $r4);
            }
        }
        $r1.append("]");
    }

    private <O> boolean zzj(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "TO;)Z"}) String $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "TO;)Z"}) O $r2) throws  {
        if ($r2 != null) {
            return true;
        }
        if (Log.isLoggable("FastJsonResponse", 6)) {
            Log.e("FastJsonResponse", new StringBuilder(String.valueOf($r1).length() + 58).append("Output field (").append($r1).append(") has a null value, but expected a primitive").toString());
        }
        return false;
    }

    public <T extends FastJsonResponse> void addConcreteType(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "TT;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "TT;)V"}) T t) throws  {
        throw new UnsupportedOperationException("Concrete type not supported");
    }

    public <T extends FastJsonResponse> void addConcreteTypeArray(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) String str, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) ArrayList<T> arrayList) throws  {
        throw new UnsupportedOperationException("Concrete type array not supported");
    }

    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) Field<?, ?> field, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) String $r2, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<TT;>;)V"}) ArrayList<T> $r3) throws  {
        addConcreteTypeArray($r2, $r3);
    }

    public <T extends FastJsonResponse> void addConcreteTypeInternal(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) Field<?, ?> field, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) String $r2, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "TT;)V"}) T $r3) throws  {
        addConcreteType($r2, $r3);
    }

    public HashMap<String, Object> getConcreteTypeArrays() throws  {
        return null;
    }

    public HashMap<String, Object> getConcreteTypes() throws  {
        return null;
    }

    public abstract Map<String, Field<?, ?>> getFieldMappings() throws ;

    protected Object getFieldValue(Field $r1) throws  {
        String $r2 = $r1.getOutputFieldName();
        if ($r1.getConcreteType() != null) {
            zzab.zza(getValueObject($r1.getOutputFieldName()) == null, "Concrete field shouldn't be value object: %s", $r1.getOutputFieldName());
            HashMap $r7 = $r1.isTypeOutArray() ? getConcreteTypeArrays() : getConcreteTypes();
            if ($r7 != null) {
                return $r7.get($r2);
            }
            try {
                char $c0 = Character.toUpperCase($r2.charAt(0));
                $r2 = String.valueOf($r2.substring(1));
                return getClass().getMethod(new StringBuilder(String.valueOf($r2).length() + 4).append(Endpoints.ENDPOINT_GET).append($c0).append($r2).toString(), new Class[0]).invoke(this, new Object[0]);
            } catch (Exception $r11) {
                throw new RuntimeException($r11);
            }
        }
        return getValueObject($r1.getOutputFieldName());
    }

    protected <O, I> I getOriginalValue(@Signature({"<O:", "Ljava/lang/Object;", "I:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<TI;TO;>;", "Ljava/lang/Object;", ")TI;"}) Field<I, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", "I:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<TI;TO;>;", "Ljava/lang/Object;", ")TI;"}) Object $r2) throws  {
        return $r1.Lb != null ? $r1.convertBack($r2) : $r2;
    }

    public byte[] getResponseBody() throws  {
        GZIPInputStream $r1;
        byte[] $r3;
        Throwable $r8;
        zzab.zzbm(this.KZ);
        try {
            $r1 = new GZIPInputStream(new ByteArrayInputStream(this.KY));
            $r3 = new byte[4096];
            try {
                ByteArrayOutputStream $r4 = new ByteArrayOutputStream();
                while (true) {
                    int $i0 = $r1.read($r3, 0, 4096);
                    if ($i0 == -1) {
                        break;
                    }
                    $r4.write($r3, 0, $i0);
                }
                $r4.flush();
                $r3 = $r4.toByteArray();
                if ($r1 == null) {
                    return $r3;
                }
                try {
                    $r1.close();
                    return $r3;
                } catch (IOException e) {
                    return $r3;
                }
            } catch (IOException e2) {
                try {
                    $r3 = this.KY;
                    if ($r1 != null) {
                        return $r3;
                    }
                    try {
                        $r1.close();
                        return $r3;
                    } catch (IOException e3) {
                        return $r3;
                    }
                } catch (Throwable th) {
                    $r8 = th;
                    if ($r1 != null) {
                        try {
                            $r1.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw $r8;
                }
            }
        } catch (IOException e5) {
            $r1 = null;
            $r3 = this.KY;
            if ($r1 != null) {
                return $r3;
            }
            $r1.close();
            return $r3;
        } catch (Throwable $r7) {
            $r1 = null;
            $r8 = $r7;
            if ($r1 != null) {
                $r1.close();
            }
            throw $r8;
        }
    }

    public int getResponseCode() throws  {
        zzab.zzbm(this.KZ);
        return this.zzbim;
    }

    protected abstract Object getValueObject(String str) throws ;

    protected boolean isConcreteTypeArrayFieldSet(String str) throws  {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }

    protected boolean isConcreteTypeFieldSet(String str) throws  {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected boolean isFieldSet(Field $r1) throws  {
        return $r1.getTypeOut() == 11 ? $r1.isTypeOutArray() ? isConcreteTypeArrayFieldSet($r1.getOutputFieldName()) : isConcreteTypeFieldSet($r1.getOutputFieldName()) : isPrimitiveFieldSet($r1.getOutputFieldName());
    }

    protected abstract boolean isPrimitiveFieldSet(String str) throws ;

    public <T extends FastJsonResponse> void parseNetworkResponse(@Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(I[B)V"}) int $i0, @Signature({"<T:", "Lcom/google/android/gms/common/server/response/FastJsonResponse;", ">(I[B)V"}) byte[] $r1) throws ParseException {
        this.zzbim = $i0;
        this.KY = $r1;
        this.KZ = true;
        InputStream $r2 = getUnzippedStream($r1);
        try {
            new FastParser().parse($r2, this);
        } finally {
            try {
                $r2.close();
            } catch (IOException e) {
            }
        }
    }

    public final <O> void setBigDecimal(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/math/BigDecimal;", "TO;>;", "Ljava/math/BigDecimal;", ")V"}) Field<BigDecimal, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/math/BigDecimal;", "TO;>;", "Ljava/math/BigDecimal;", ")V"}) BigDecimal $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setBigDecimalInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setBigDecimal(String str, BigDecimal bigDecimal) throws  {
        throw new UnsupportedOperationException("BigDecimal not supported");
    }

    protected void setBigDecimalInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/math/BigDecimal;", ")V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/math/BigDecimal;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/math/BigDecimal;", ")V"}) BigDecimal $r3) throws  {
        setBigDecimal($r2, $r3);
    }

    public final <O> void setBigDecimals(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;)V"}) Field<ArrayList<BigDecimal>, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;)V"}) ArrayList<BigDecimal> $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setBigDecimalsInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setBigDecimals(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;)V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;)V"}) ArrayList<BigDecimal> arrayList) throws  {
        throw new UnsupportedOperationException("BigDecimal list not supported");
    }

    protected void setBigDecimalsInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigDecimal;", ">;)V"}) ArrayList<BigDecimal> $r3) throws  {
        setBigDecimals($r2, (ArrayList) $r3);
    }

    public final <O> void setBigInteger(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/math/BigInteger;", "TO;>;", "Ljava/math/BigInteger;", ")V"}) Field<BigInteger, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/math/BigInteger;", "TO;>;", "Ljava/math/BigInteger;", ")V"}) BigInteger $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setBigIntegerInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setBigInteger(String str, BigInteger bigInteger) throws  {
        throw new UnsupportedOperationException("BigInteger not supported");
    }

    protected void setBigIntegerInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/math/BigInteger;", ")V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/math/BigInteger;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/math/BigInteger;", ")V"}) BigInteger $r3) throws  {
        setBigInteger($r2, $r3);
    }

    public final <O> void setBigIntegers(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;)V"}) Field<ArrayList<BigInteger>, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;)V"}) ArrayList<BigInteger> $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setBigIntegersInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setBigIntegers(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;)V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;)V"}) ArrayList<BigInteger> arrayList) throws  {
        throw new UnsupportedOperationException("BigInteger list not supported");
    }

    protected void setBigIntegersInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/math/BigInteger;", ">;)V"}) ArrayList<BigInteger> $r3) throws  {
        setBigIntegers($r2, (ArrayList) $r3);
    }

    public final <O> void setBoolean(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Boolean;", "TO;>;Z)V"}) Field<Boolean, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Boolean;", "TO;>;Z)V"}) boolean $z0) throws  {
        if ($r1.Lb != null) {
            zza($r1, Boolean.valueOf($z0));
        } else {
            setBooleanInternal($r1, $r1.getOutputFieldName(), $z0);
        }
    }

    protected void setBoolean(String str, boolean z) throws  {
        throw new UnsupportedOperationException("Boolean not supported");
    }

    protected void setBooleanInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Z)V"}) boolean $z0) throws  {
        setBoolean($r2, $z0);
    }

    public final <O> void setBooleans(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;)V"}) Field<ArrayList<Boolean>, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;)V"}) ArrayList<Boolean> $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setBooleansInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setBooleans(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;)V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;)V"}) ArrayList<Boolean> arrayList) throws  {
        throw new UnsupportedOperationException("Boolean list not supported");
    }

    protected void setBooleansInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Boolean;", ">;)V"}) ArrayList<Boolean> $r3) throws  {
        setBooleans($r2, (ArrayList) $r3);
    }

    public final <O> void setDecodedBytes(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<[BTO;>;[B)V"}) Field<byte[], O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<[BTO;>;[B)V"}) byte[] $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setDecodedBytesInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setDecodedBytes(String str, byte[] bArr) throws  {
        throw new UnsupportedOperationException("byte[] not supported");
    }

    protected void setDecodedBytesInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "[B)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "[B)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "[B)V"}) byte[] $r3) throws  {
        setDecodedBytes($r2, $r3);
    }

    public final <O> void setDouble(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Double;", "TO;>;D)V"}) Field<Double, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Double;", "TO;>;D)V"}) double $d0) throws  {
        if ($r1.Lb != null) {
            zza($r1, Double.valueOf($d0));
        } else {
            setDoubleInternal($r1, $r1.getOutputFieldName(), $d0);
        }
    }

    protected void setDouble(String str, double d) throws  {
        throw new UnsupportedOperationException("Double not supported");
    }

    protected void setDoubleInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "D)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "D)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "D)V"}) double $d0) throws  {
        setDouble($r2, $d0);
    }

    public final <O> void setDoubles(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;)V"}) Field<ArrayList<Double>, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;)V"}) ArrayList<Double> $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setDoublesInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setDoubles(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;)V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;)V"}) ArrayList<Double> arrayList) throws  {
        throw new UnsupportedOperationException("Double list not supported");
    }

    protected void setDoublesInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Double;", ">;)V"}) ArrayList<Double> $r3) throws  {
        setDoubles($r2, (ArrayList) $r3);
    }

    public final <O> void setFloat(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Float;", "TO;>;F)V"}) Field<Float, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Float;", "TO;>;F)V"}) float $f0) throws  {
        if ($r1.Lb != null) {
            zza($r1, Float.valueOf($f0));
        } else {
            setFloatInternal($r1, $r1.getOutputFieldName(), $f0);
        }
    }

    protected void setFloat(String str, float f) throws  {
        throw new UnsupportedOperationException("Float not supported");
    }

    protected void setFloatInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "F)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "F)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "F)V"}) float $f0) throws  {
        setFloat($r2, $f0);
    }

    public final <O> void setFloats(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;)V"}) Field<ArrayList<Float>, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;)V"}) ArrayList<Float> $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setFloatsInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setFloats(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;)V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;)V"}) ArrayList<Float> arrayList) throws  {
        throw new UnsupportedOperationException("Float list not supported");
    }

    protected void setFloatsInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Float;", ">;)V"}) ArrayList<Float> $r3) throws  {
        setFloats($r2, (ArrayList) $r3);
    }

    public final <O> void setInteger(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Integer;", "TO;>;I)V"}) Field<Integer, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Integer;", "TO;>;I)V"}) int $i0) throws  {
        if ($r1.Lb != null) {
            zza($r1, Integer.valueOf($i0));
        } else {
            setIntegerInternal($r1, $r1.getOutputFieldName(), $i0);
        }
    }

    protected void setInteger(String str, int i) throws  {
        throw new UnsupportedOperationException("Integer not supported");
    }

    protected void setIntegerInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "I)V"}) int $i0) throws  {
        setInteger($r2, $i0);
    }

    public final <O> void setIntegers(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;)V"}) Field<ArrayList<Integer>, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;)V"}) ArrayList<Integer> $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setIntegersInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setIntegers(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;)V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;)V"}) ArrayList<Integer> arrayList) throws  {
        throw new UnsupportedOperationException("Integer list not supported");
    }

    protected void setIntegersInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Integer;", ">;)V"}) ArrayList<Integer> $r3) throws  {
        setIntegers($r2, (ArrayList) $r3);
    }

    public final <O> void setLong(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Long;", "TO;>;J)V"}) Field<Long, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/Long;", "TO;>;J)V"}) long $l0) throws  {
        if ($r1.Lb != null) {
            zza($r1, Long.valueOf($l0));
        } else {
            setLongInternal($r1, $r1.getOutputFieldName(), $l0);
        }
    }

    protected void setLong(String str, long j) throws  {
        throw new UnsupportedOperationException("Long not supported");
    }

    protected void setLongInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "J)V"}) long $l0) throws  {
        setLong($r2, $l0);
    }

    public final <O> void setLongs(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;)V"}) Field<ArrayList<Long>, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;)V"}) ArrayList<Long> $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setLongsInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setLongs(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;)V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;)V"}) ArrayList<Long> arrayList) throws  {
        throw new UnsupportedOperationException("Long list not supported");
    }

    protected void setLongsInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/Long;", ">;)V"}) ArrayList<Long> $r3) throws  {
        setLongs($r2, (ArrayList) $r3);
    }

    public final <O> void setString(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/String;", "TO;>;", "Ljava/lang/String;", ")V"}) Field<String, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/lang/String;", "TO;>;", "Ljava/lang/String;", ")V"}) String $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setStringInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setString(String str, String str2) throws  {
        throw new UnsupportedOperationException("String not supported");
    }

    protected void setStringInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3) throws  {
        setString($r2, $r3);
    }

    public final <O> void setStringMap(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;TO;>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Field<Map<String, String>, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;TO;>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setStringMapInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setStringMap(@Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> map) throws  {
        throw new UnsupportedOperationException("String map not supported");
    }

    protected void setStringMapInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r3) throws  {
        setStringMap($r2, (Map) $r3);
    }

    public final <O> void setStrings(@Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) Field<ArrayList<String>, O> $r1, @Signature({"<O:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;TO;>;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) ArrayList<String> $r2) throws  {
        if ($r1.Lb != null) {
            zza($r1, $r2);
        } else {
            setStringsInternal($r1, $r1.getOutputFieldName(), $r2);
        }
    }

    protected void setStrings(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) ArrayList<String> arrayList) throws  {
        throw new UnsupportedOperationException("String list not supported");
    }

    protected void setStringsInternal(@Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) Field<?, ?> field, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Lcom/google/android/gms/common/server/response/FastJsonResponse$Field", "<**>;", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) ArrayList<String> $r3) throws  {
        setStrings($r2, (ArrayList) $r3);
    }

    public java.lang.String toString() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:13:0x0066
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
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
        r22 = this;
        r0 = r22;
        r1 = r0.getFieldMappings();
        r2 = new java.lang.StringBuilder;
        r3 = 100;
        r2.<init>(r3);
        r4 = r1.keySet();
        r5 = r4.iterator();
    L_0x0015:
        r6 = r5.hasNext();
        if (r6 == 0) goto L_0x00dc;
    L_0x001b:
        r7 = r5.next();
        r9 = r7;
        r9 = (java.lang.String) r9;
        r8 = r9;
        r7 = r1.get(r8);
        r11 = r7;
        r11 = (com.google.android.gms.common.server.response.FastJsonResponse.Field) r11;
        r10 = r11;
        r0 = r22;
        r6 = r0.isFieldSet(r10);
        if (r6 == 0) goto L_0x0015;
    L_0x0033:
        r0 = r22;
        r7 = r0.getFieldValue(r10);
        r0 = r22;
        r7 = r0.getOriginalValue(r10, r7);
        r12 = r2.length();
        if (r12 != 0) goto L_0x006a;
    L_0x0045:
        r13 = "{";
        r2.append(r13);
    L_0x004b:
        r13 = "\"";
        r14 = r2.append(r13);
        r14 = r14.append(r8);
        goto L_0x0059;
    L_0x0056:
        goto L_0x0015;
    L_0x0059:
        r13 = "\":";
        r14.append(r13);
        if (r7 != 0) goto L_0x0078;
    L_0x0060:
        r13 = "null";
        r2.append(r13);
        goto L_0x0015;
        goto L_0x006a;
    L_0x0067:
        goto L_0x0015;
    L_0x006a:
        r13 = ",";
        r2.append(r13);
        goto L_0x004b;
        goto L_0x0078;
    L_0x0071:
        goto L_0x0015;
        goto L_0x0078;
    L_0x0075:
        goto L_0x0015;
    L_0x0078:
        r12 = r10.getTypeOut();
        switch(r12) {
            case 8: goto L_0x0092;
            case 9: goto L_0x00ae;
            case 10: goto L_0x00ca;
            default: goto L_0x007f;
        };
    L_0x007f:
        goto L_0x0080;
    L_0x0080:
        r6 = r10.isTypeInArray();
        if (r6 == 0) goto L_0x00d6;
    L_0x0086:
        r16 = r7;
        r16 = (java.util.ArrayList) r16;
        r15 = r16;
        r0 = r22;
        r0.zza(r2, r10, r15);
        goto L_0x0015;
    L_0x0092:
        r13 = "\"";
        r14 = r2.append(r13);
        r18 = r7;
        r18 = (byte[]) r18;
        r17 = r18;
        r0 = r17;
        r8 = com.google.android.gms.common.util.zzc.encode(r0);
        r14 = r14.append(r8);
        r13 = "\"";
        r14.append(r13);
        goto L_0x0056;
    L_0x00ae:
        r13 = "\"";
        r14 = r2.append(r13);
        r19 = r7;
        r19 = (byte[]) r19;
        r17 = r19;
        r0 = r17;
        r8 = com.google.android.gms.common.util.zzc.zzq(r0);
        r14 = r14.append(r8);
        r13 = "\"";
        r14.append(r13);
        goto L_0x0067;
    L_0x00ca:
        r21 = r7;
        r21 = (java.util.HashMap) r21;
        r20 = r21;
        r0 = r20;
        com.google.android.gms.common.util.zzo.zza(r2, r0);
        goto L_0x0071;
    L_0x00d6:
        r0 = r22;
        r0.zza(r2, r10, r7);
        goto L_0x0075;
    L_0x00dc:
        r12 = r2.length();
        if (r12 <= 0) goto L_0x00ed;
    L_0x00e2:
        r13 = "}";
        r2.append(r13);
    L_0x00e8:
        r8 = r2.toString();
        return r8;
    L_0x00ed:
        r13 = "{}";
        r2.append(r13);
        goto L_0x00e8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.server.response.FastJsonResponse.toString():java.lang.String");
    }

    public zza<? extends FastJsonResponse> zzaxt() throws  {
        return null;
    }
}
