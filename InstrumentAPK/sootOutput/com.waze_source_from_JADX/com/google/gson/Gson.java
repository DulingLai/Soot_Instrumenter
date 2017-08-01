package com.google.gson;

import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public final class Gson {
    static final boolean DEFAULT_COMPLEX_MAP_KEYS = false;
    static final boolean DEFAULT_ESCAPE_HTML = true;
    static final boolean DEFAULT_JSON_NON_EXECUTABLE = false;
    static final boolean DEFAULT_LENIENT = false;
    static final boolean DEFAULT_PRETTY_PRINT = false;
    static final boolean DEFAULT_SERIALIZE_NULLS = false;
    static final boolean DEFAULT_SPECIALIZE_FLOAT_VALUES = false;
    private static final String JSON_NON_EXECUTABLE_PREFIX = ")]}'\n";
    private final ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>> calls;
    private final ConstructorConstructor constructorConstructor;
    final JsonDeserializationContext deserializationContext;
    private final List<TypeAdapterFactory> factories;
    private final boolean generateNonExecutableJson;
    private final boolean htmlSafe;
    private final boolean lenient;
    private final boolean prettyPrinting;
    final JsonSerializationContext serializationContext;
    private final boolean serializeNulls;
    private final Map<TypeToken<?>, TypeAdapter<?>> typeTokenCache;

    class C10081 implements JsonDeserializationContext {
        C10081() throws  {
        }

        public <T> T deserialize(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/JsonElement;", "Ljava/lang/reflect/Type;", ")TT;"}) JsonElement $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/JsonElement;", "Ljava/lang/reflect/Type;", ")TT;"}) Type $r2) throws JsonParseException {
            return Gson.this.fromJson($r1, $r2);
        }
    }

    class C10092 implements JsonSerializationContext {
        C10092() throws  {
        }

        public JsonElement serialize(Object $r1) throws  {
            return Gson.this.toJsonTree($r1);
        }

        public JsonElement serialize(Object $r1, Type $r2) throws  {
            return Gson.this.toJsonTree($r1, $r2);
        }
    }

    class C10103 extends TypeAdapter<Number> {
        C10103() throws  {
        }

        public Double read(JsonReader $r1) throws IOException {
            if ($r1.peek() != JsonToken.NULL) {
                return Double.valueOf($r1.nextDouble());
            }
            $r1.nextNull();
            return null;
        }

        public void write(JsonWriter $r1, Number $r2) throws IOException {
            if ($r2 == null) {
                $r1.nullValue();
                return;
            }
            Gson.checkValidFloatingPoint($r2.doubleValue());
            $r1.value($r2);
        }
    }

    class C10114 extends TypeAdapter<Number> {
        C10114() throws  {
        }

        public Float read(JsonReader $r1) throws IOException {
            if ($r1.peek() != JsonToken.NULL) {
                return Float.valueOf((float) $r1.nextDouble());
            }
            $r1.nextNull();
            return null;
        }

        public void write(JsonWriter $r1, Number $r2) throws IOException {
            if ($r2 == null) {
                $r1.nullValue();
                return;
            }
            Gson.checkValidFloatingPoint((double) $r2.floatValue());
            $r1.value($r2);
        }
    }

    static class C10125 extends TypeAdapter<Number> {
        C10125() throws  {
        }

        public Number read(JsonReader $r1) throws IOException {
            if ($r1.peek() != JsonToken.NULL) {
                return Long.valueOf($r1.nextLong());
            }
            $r1.nextNull();
            return null;
        }

        public void write(JsonWriter $r1, Number $r2) throws IOException {
            if ($r2 == null) {
                $r1.nullValue();
            } else {
                $r1.value($r2.toString());
            }
        }
    }

    static class FutureTypeAdapter<T> extends TypeAdapter<T> {
        private TypeAdapter<T> delegate;

        FutureTypeAdapter() throws  {
        }

        public void setDelegate(@Signature({"(", "Lcom/google/gson/TypeAdapter", "<TT;>;)V"}) TypeAdapter<T> $r1) throws  {
            if (this.delegate != null) {
                throw new AssertionError();
            }
            this.delegate = $r1;
        }

        public T read(@Signature({"(", "Lcom/google/gson/stream/JsonReader;", ")TT;"}) JsonReader $r1) throws IOException {
            if (this.delegate != null) {
                return this.delegate.read($r1);
            }
            throw new IllegalStateException();
        }

        public void write(@Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) JsonWriter $r1, @Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) T $r2) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            this.delegate.write($r1, $r2);
        }
    }

    public Gson() throws  {
        this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, false, LongSerializationPolicy.DEFAULT, Collections.emptyList());
    }

    Gson(@Signature({"(", "Lcom/google/gson/internal/Excluder;", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;ZZZZZZZ", "Lcom/google/gson/LongSerializationPolicy;", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) Excluder $r1, @Signature({"(", "Lcom/google/gson/internal/Excluder;", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;ZZZZZZZ", "Lcom/google/gson/LongSerializationPolicy;", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) FieldNamingStrategy $r2, @Signature({"(", "Lcom/google/gson/internal/Excluder;", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;ZZZZZZZ", "Lcom/google/gson/LongSerializationPolicy;", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) Map<Type, InstanceCreator<?>> $r3, @Signature({"(", "Lcom/google/gson/internal/Excluder;", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;ZZZZZZZ", "Lcom/google/gson/LongSerializationPolicy;", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) boolean $z0, @Signature({"(", "Lcom/google/gson/internal/Excluder;", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;ZZZZZZZ", "Lcom/google/gson/LongSerializationPolicy;", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) boolean $z1, @Signature({"(", "Lcom/google/gson/internal/Excluder;", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;ZZZZZZZ", "Lcom/google/gson/LongSerializationPolicy;", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) boolean $z2, @Signature({"(", "Lcom/google/gson/internal/Excluder;", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;ZZZZZZZ", "Lcom/google/gson/LongSerializationPolicy;", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) boolean $z3, @Signature({"(", "Lcom/google/gson/internal/Excluder;", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;ZZZZZZZ", "Lcom/google/gson/LongSerializationPolicy;", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) boolean $z4, @Signature({"(", "Lcom/google/gson/internal/Excluder;", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;ZZZZZZZ", "Lcom/google/gson/LongSerializationPolicy;", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) boolean $z5, @Signature({"(", "Lcom/google/gson/internal/Excluder;", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;ZZZZZZZ", "Lcom/google/gson/LongSerializationPolicy;", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) boolean $z6, @Signature({"(", "Lcom/google/gson/internal/Excluder;", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;ZZZZZZZ", "Lcom/google/gson/LongSerializationPolicy;", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) LongSerializationPolicy $r4, @Signature({"(", "Lcom/google/gson/internal/Excluder;", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;ZZZZZZZ", "Lcom/google/gson/LongSerializationPolicy;", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) List<TypeAdapterFactory> $r5) throws  {
        Gson gson = this;
        this.calls = new ThreadLocal();
        this.typeTokenCache = Collections.synchronizedMap(new HashMap());
        this.deserializationContext = new C10081();
        this.serializationContext = new C10092();
        this.constructorConstructor = new ConstructorConstructor($r3);
        this.serializeNulls = $z0;
        this.generateNonExecutableJson = $z2;
        this.htmlSafe = $z3;
        this.prettyPrinting = $z4;
        this.lenient = $z5;
        ArrayList $r6 = new ArrayList();
        $r6.add(TypeAdapters.JSON_ELEMENT_FACTORY);
        $r6.add(ObjectTypeAdapter.FACTORY);
        $r6.add($r1);
        $r6.addAll($r5);
        $r6.add(TypeAdapters.STRING_FACTORY);
        $r6.add(TypeAdapters.INTEGER_FACTORY);
        $r6.add(TypeAdapters.BOOLEAN_FACTORY);
        $r6.add(TypeAdapters.BYTE_FACTORY);
        $r6.add(TypeAdapters.SHORT_FACTORY);
        TypeAdapter $r14 = longAdapter($r4);
        $r6.add(TypeAdapters.newFactory(Long.TYPE, Long.class, $r14));
        $r6.add(TypeAdapters.newFactory(Double.TYPE, Double.class, doubleAdapter($z6)));
        $r6.add(TypeAdapters.newFactory(Float.TYPE, Float.class, floatAdapter($z6)));
        $r6.add(TypeAdapters.NUMBER_FACTORY);
        $r6.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
        $r6.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
        $r6.add(TypeAdapters.newFactory(AtomicLong.class, atomicLongAdapter($r14)));
        $r6.add(TypeAdapters.newFactory(AtomicLongArray.class, atomicLongArrayAdapter($r14)));
        $r6.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
        $r6.add(TypeAdapters.CHARACTER_FACTORY);
        $r6.add(TypeAdapters.STRING_BUILDER_FACTORY);
        $r6.add(TypeAdapters.STRING_BUFFER_FACTORY);
        $r6.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        $r6.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        $r6.add(TypeAdapters.URL_FACTORY);
        $r6.add(TypeAdapters.URI_FACTORY);
        $r6.add(TypeAdapters.UUID_FACTORY);
        $r6.add(TypeAdapters.CURRENCY_FACTORY);
        $r6.add(TypeAdapters.LOCALE_FACTORY);
        $r6.add(TypeAdapters.INET_ADDRESS_FACTORY);
        $r6.add(TypeAdapters.BIT_SET_FACTORY);
        $r6.add(DateTypeAdapter.FACTORY);
        $r6.add(TypeAdapters.CALENDAR_FACTORY);
        $r6.add(TimeTypeAdapter.FACTORY);
        $r6.add(SqlDateTypeAdapter.FACTORY);
        $r6.add(TypeAdapters.TIMESTAMP_FACTORY);
        $r6.add(ArrayTypeAdapter.FACTORY);
        $r6.add(TypeAdapters.CLASS_FACTORY);
        $r6.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
        $r6.add(new MapTypeAdapterFactory(this.constructorConstructor, $z1));
        $r6.add(new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor));
        $r6.add(TypeAdapters.ENUM_FACTORY);
        $r6.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, $r2, $r1));
        this.factories = Collections.unmodifiableList($r6);
    }

    private TypeAdapter<Number> doubleAdapter(@Signature({"(Z)", "Lcom/google/gson/TypeAdapter", "<", "Ljava/lang/Number;", ">;"}) boolean $z0) throws  {
        if ($z0) {
            return TypeAdapters.DOUBLE;
        }
        return new C10103();
    }

    private TypeAdapter<Number> floatAdapter(@Signature({"(Z)", "Lcom/google/gson/TypeAdapter", "<", "Ljava/lang/Number;", ">;"}) boolean $z0) throws  {
        if ($z0) {
            return TypeAdapters.FLOAT;
        }
        return new C10114();
    }

    static void checkValidFloatingPoint(double $d0) throws  {
        if (Double.isNaN($d0) || Double.isInfinite($d0)) {
            throw new IllegalArgumentException($d0 + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private static TypeAdapter<Number> longAdapter(@Signature({"(", "Lcom/google/gson/LongSerializationPolicy;", ")", "Lcom/google/gson/TypeAdapter", "<", "Ljava/lang/Number;", ">;"}) LongSerializationPolicy $r0) throws  {
        if ($r0 == LongSerializationPolicy.DEFAULT) {
            return TypeAdapters.LONG;
        }
        return new C10125();
    }

    private static TypeAdapter<AtomicLong> atomicLongAdapter(@Signature({"(", "Lcom/google/gson/TypeAdapter", "<", "Ljava/lang/Number;", ">;)", "Lcom/google/gson/TypeAdapter", "<", "Ljava/util/concurrent/atomic/AtomicLong;", ">;"}) final TypeAdapter<Number> $r0) throws  {
        return new TypeAdapter<AtomicLong>() {
            public void write(JsonWriter $r1, AtomicLong $r2) throws IOException {
                $r0.write($r1, Long.valueOf($r2.get()));
            }

            public AtomicLong read(JsonReader $r1) throws IOException {
                return new AtomicLong(((Number) $r0.read($r1)).longValue());
            }
        }.nullSafe();
    }

    private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(@Signature({"(", "Lcom/google/gson/TypeAdapter", "<", "Ljava/lang/Number;", ">;)", "Lcom/google/gson/TypeAdapter", "<", "Ljava/util/concurrent/atomic/AtomicLongArray;", ">;"}) final TypeAdapter<Number> $r0) throws  {
        return new TypeAdapter<AtomicLongArray>() {
            public void write(JsonWriter $r1, AtomicLongArray $r2) throws IOException {
                $r1.beginArray();
                int $i1 = $r2.length();
                for (int $i0 = 0; $i0 < $i1; $i0++) {
                    $r0.write($r1, Long.valueOf($r2.get($i0)));
                }
                $r1.endArray();
            }

            public AtomicLongArray read(JsonReader $r1) throws IOException {
                ArrayList $r3 = new ArrayList();
                $r1.beginArray();
                while ($r1.hasNext()) {
                    $r3.add(Long.valueOf(((Number) $r0.read($r1)).longValue()));
                }
                $r1.endArray();
                int $i1 = $r3.size();
                AtomicLongArray $r2 = new AtomicLongArray($i1);
                for (int $i2 = 0; $i2 < $i1; $i2++) {
                    $r2.set($i2, ((Long) $r3.get($i2)).longValue());
                }
                return $r2;
            }
        }.nullSafe();
    }

    public <T> TypeAdapter<T> getAdapter(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r1) throws  {
        TypeAdapter $r5 = (TypeAdapter) this.typeTokenCache.get($r1);
        if ($r5 != null) {
            return $r5;
        }
        Map $r3 = (Map) this.calls.get();
        boolean $z0 = false;
        if ($r3 == null) {
            $r3 = r15;
            HashMap r15 = new HashMap();
            this.calls.set($r3);
            $z0 = true;
        }
        FutureTypeAdapter $r2 = (FutureTypeAdapter) $r3.get($r1);
        if ($r2 != null) {
            return $r2;
        }
        try {
            $r2 = r16;
            FutureTypeAdapter r16 = new FutureTypeAdapter();
            $r3.put($r1, $r2);
            for (TypeAdapterFactory create : this.factories) {
                $r5 = create.create(this, $r1);
                if ($r5 != null) {
                    $r2.setDelegate($r5);
                    Map map = this.typeTokenCache;
                    Map $r10 = map;
                    map.put($r1, $r5);
                    return $r5;
                }
            }
            throw new IllegalArgumentException("GSON cannot handle " + $r1);
        } finally {
            $r3.remove($r1);
            if ($z0) {
                this.calls.remove();
            }
        }
    }

    public <T> TypeAdapter<T> getDelegateAdapter(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/TypeAdapterFactory;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeAdapterFactory $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/TypeAdapterFactory;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
        boolean $z0 = false;
        if (!this.factories.contains($r1)) {
            $z0 = true;
        }
        for (TypeAdapterFactory $r6 : this.factories) {
            if ($z0) {
                TypeAdapter $r7 = $r6.create(this, $r2);
                if ($r7 != null) {
                    return $r7;
                }
            } else if ($r6 == $r1) {
                $z0 = true;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + $r2);
    }

    public <T> TypeAdapter<T> getAdapter(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Class<T> $r1) throws  {
        return getAdapter(TypeToken.get((Class) $r1));
    }

    public JsonElement toJsonTree(Object $r1) throws  {
        if ($r1 == null) {
            return JsonNull.INSTANCE;
        }
        return toJsonTree($r1, $r1.getClass());
    }

    public JsonElement toJsonTree(Object $r1, Type $r2) throws  {
        JsonWriter $r3 = new JsonTreeWriter();
        toJson($r1, $r2, $r3);
        return $r3.get();
    }

    public String toJson(Object $r1) throws  {
        if ($r1 == null) {
            return toJson(JsonNull.INSTANCE);
        }
        return toJson($r1, $r1.getClass());
    }

    public String toJson(Object $r1, Type $r2) throws  {
        Appendable $r3 = new StringWriter();
        toJson($r1, $r2, $r3);
        return $r3.toString();
    }

    public void toJson(Object $r1, Appendable $r2) throws JsonIOException {
        if ($r1 != null) {
            toJson($r1, $r1.getClass(), $r2);
        } else {
            toJson(JsonNull.INSTANCE, $r2);
        }
    }

    public void toJson(Object $r1, Type $r2, Appendable $r3) throws JsonIOException {
        try {
            toJson($r1, $r2, newJsonWriter(Streams.writerForAppendable($r3)));
        } catch (Throwable $r4) {
            throw new JsonIOException($r4);
        }
    }

    public void toJson(Object $r1, Type $r2, JsonWriter $r3) throws JsonIOException {
        TypeAdapter $r6 = getAdapter(TypeToken.get($r2));
        boolean $z0 = $r3.isLenient();
        $r3.setLenient(true);
        boolean $z1 = $r3.isHtmlSafe();
        $r3.setHtmlSafe(this.htmlSafe);
        boolean $z2 = $r3.getSerializeNulls();
        $r3.setSerializeNulls(this.serializeNulls);
        try {
            $r6.write($r3, $r1);
            $r3.setLenient($z0);
            $r3.setHtmlSafe($z1);
            $r3.setSerializeNulls($z2);
        } catch (Throwable $r4) {
            throw new JsonIOException($r4);
        } catch (Throwable th) {
            $r3.setLenient($z0);
            $r3.setHtmlSafe($z1);
            $r3.setSerializeNulls($z2);
        }
    }

    public String toJson(JsonElement $r1) throws  {
        Appendable $r2 = new StringWriter();
        toJson($r1, $r2);
        return $r2.toString();
    }

    public void toJson(JsonElement $r1, Appendable $r2) throws JsonIOException {
        try {
            toJson($r1, newJsonWriter(Streams.writerForAppendable($r2)));
        } catch (IOException $r3) {
            throw new RuntimeException($r3);
        }
    }

    public JsonWriter newJsonWriter(Writer $r1) throws IOException {
        if (this.generateNonExecutableJson) {
            $r1.write(JSON_NON_EXECUTABLE_PREFIX);
        }
        JsonWriter $r2 = new JsonWriter($r1);
        if (this.prettyPrinting) {
            $r2.setIndent("  ");
        }
        $r2.setSerializeNulls(this.serializeNulls);
        return $r2;
    }

    public JsonReader newJsonReader(Reader $r1) throws  {
        JsonReader $r2 = new JsonReader($r1);
        $r2.setLenient(this.lenient);
        return $r2;
    }

    public void toJson(JsonElement $r1, JsonWriter $r2) throws JsonIOException {
        boolean $z0 = $r2.isLenient();
        $r2.setLenient(true);
        boolean $z1 = $r2.isHtmlSafe();
        $r2.setHtmlSafe(this.htmlSafe);
        boolean $z2 = $r2.getSerializeNulls();
        $r2.setSerializeNulls(this.serializeNulls);
        try {
            Streams.write($r1, $r2);
            $r2.setLenient($z0);
            $r2.setHtmlSafe($z1);
            $r2.setSerializeNulls($z2);
        } catch (Throwable $r3) {
            throw new JsonIOException($r3);
        } catch (Throwable th) {
            $r2.setLenient($z0);
            $r2.setHtmlSafe($z1);
            $r2.setSerializeNulls($z2);
        }
    }

    public <T> T fromJson(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)TT;"}) String $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r2) throws JsonSyntaxException {
        return Primitives.wrap($r2).cast(fromJson($r1, (Type) $r2));
    }

    public <T> T fromJson(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ")TT;"}) String $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/reflect/Type;", ")TT;"}) Type $r2) throws JsonSyntaxException {
        if ($r1 == null) {
            return null;
        }
        return fromJson(new StringReader($r1), $r2);
    }

    public <T> T fromJson(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/io/Reader;", "Ljava/lang/Class", "<TT;>;)TT;"}) Reader $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/io/Reader;", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r2) throws JsonSyntaxException, JsonIOException {
        JsonReader $r3 = newJsonReader($r1);
        Object $r4 = fromJson($r3, (Type) $r2);
        assertFullConsumption($r4, $r3);
        return Primitives.wrap($r2).cast($r4);
    }

    public <T> T fromJson(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/io/Reader;", "Ljava/lang/reflect/Type;", ")TT;"}) Reader $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/io/Reader;", "Ljava/lang/reflect/Type;", ")TT;"}) Type $r2) throws JsonIOException, JsonSyntaxException {
        JsonReader $r3 = newJsonReader($r1);
        Object $r4 = fromJson($r3, $r2);
        assertFullConsumption($r4, $r3);
        return $r4;
    }

    private static void assertFullConsumption(Object $r0, JsonReader $r1) throws  {
        if ($r0 != null) {
            try {
                if ($r1.peek() != JsonToken.END_DOCUMENT) {
                    throw new JsonIOException("JSON document was not fully consumed.");
                }
            } catch (Throwable $r5) {
                throw new JsonSyntaxException($r5);
            } catch (Throwable $r7) {
                throw new JsonIOException($r7);
            }
        }
    }

    public <T> T fromJson(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/stream/JsonReader;", "Ljava/lang/reflect/Type;", ")TT;"}) JsonReader $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/stream/JsonReader;", "Ljava/lang/reflect/Type;", ")TT;"}) Type $r2) throws JsonIOException, JsonSyntaxException {
        boolean $z0 = true;
        boolean $z1 = $r1.isLenient();
        $r1.setLenient(true);
        try {
            $r1.peek();
            $z0 = false;
            Object $r5 = getAdapter(TypeToken.get($r2)).read($r1);
            $r1.setLenient($z1);
            return $r5;
        } catch (Throwable $r6) {
            if ($z0) {
                $r1.setLenient($z1);
                return null;
            }
            throw new JsonSyntaxException($r6);
        } catch (Throwable $r9) {
            throw new JsonSyntaxException($r9);
        } catch (Throwable $r10) {
            throw new JsonSyntaxException($r10);
        } catch (Throwable th) {
            $r1.setLenient($z1);
        }
    }

    public <T> T fromJson(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/JsonElement;", "Ljava/lang/Class", "<TT;>;)TT;"}) JsonElement $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/JsonElement;", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r2) throws JsonSyntaxException {
        return Primitives.wrap($r2).cast(fromJson($r1, (Type) $r2));
    }

    public <T> T fromJson(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/JsonElement;", "Ljava/lang/reflect/Type;", ")TT;"}) JsonElement $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/JsonElement;", "Ljava/lang/reflect/Type;", ")TT;"}) Type $r2) throws JsonSyntaxException {
        return $r1 == null ? null : fromJson(new JsonTreeReader($r1), $r2);
    }

    public String toString() throws  {
        return "{serializeNulls:" + this.serializeNulls + "factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
    }
}
