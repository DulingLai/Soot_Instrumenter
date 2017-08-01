package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;

    static abstract class BoundField {
        final boolean deserialized;
        final String name;
        final boolean serialized;

        abstract void read(JsonReader jsonReader, Object obj) throws IOException, IllegalAccessException;

        abstract void write(JsonWriter jsonWriter, Object obj) throws IOException, IllegalAccessException;

        abstract boolean writeField(Object obj) throws IOException, IllegalAccessException;

        protected BoundField(String $r1, boolean $z0, boolean $z1) throws  {
            this.name = $r1;
            this.serialized = $z0;
            this.deserialized = $z1;
        }
    }

    public static final class Adapter<T> extends TypeAdapter<T> {
        private final Map<String, BoundField> boundFields;
        private final ObjectConstructor<T> constructor;

        public T read(@dalvik.annotation.Signature({"(", "Lcom/google/gson/stream/JsonReader;", ")TT;"}) com.google.gson.stream.JsonReader r17) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x003e in list [B:18:0x0044, B:24:0x0051]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r16 = this;
            r0 = r17;
            r1 = r0.peek();
            r2 = com.google.gson.stream.JsonToken.NULL;
            if (r1 != r2) goto L_0x0011;
        L_0x000a:
            r0 = r17;
            r0.nextNull();
            r3 = 0;
            return r3;
        L_0x0011:
            r0 = r16;
            r4 = r0.constructor;
            r5 = r4.construct();
            r0 = r17;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r0.beginObject();	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
        L_0x001e:
            r0 = r17;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r6 = r0.hasNext();	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            if (r6 == 0) goto L_0x0058;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
        L_0x0026:
            r0 = r17;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r7 = r0.nextName();	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r0 = r16;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r8 = r0.boundFields;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r9 = r8.get(r7);	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r11 = r9;
            r11 = (com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField) r11;
            r10 = r11;
            if (r10 == 0) goto L_0x003e;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
        L_0x003a:
            r6 = r10.deserialized;
            if (r6 != 0) goto L_0x004b;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
        L_0x003e:
            r0 = r17;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r0.skipValue();	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            goto L_0x001e;
        L_0x0044:
            r12 = move-exception;
            r13 = new com.google.gson.JsonSyntaxException;
            r13.<init>(r12);
            throw r13;
        L_0x004b:
            r0 = r17;	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            r10.read(r0, r5);	 Catch:{ IllegalStateException -> 0x0044, IllegalAccessException -> 0x0051 }
            goto L_0x001e;
        L_0x0051:
            r14 = move-exception;
            r15 = new java.lang.AssertionError;
            r15.<init>(r14);
            throw r15;
        L_0x0058:
            r0 = r17;
            r0.endObject();
            return r5;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.Adapter.read(com.google.gson.stream.JsonReader):T");
        }

        Adapter(@Signature({"(", "Lcom/google/gson/internal/ObjectConstructor", "<TT;>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/gson/internal/bind/ReflectiveTypeAdapterFactory$BoundField;", ">;)V"}) ObjectConstructor<T> $r1, @Signature({"(", "Lcom/google/gson/internal/ObjectConstructor", "<TT;>;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/gson/internal/bind/ReflectiveTypeAdapterFactory$BoundField;", ">;)V"}) Map<String, BoundField> $r2) throws  {
            this.constructor = $r1;
            this.boundFields = $r2;
        }

        public void write(@Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) JsonWriter $r1, @Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) T $r2) throws IOException {
            if ($r2 == null) {
                $r1.nullValue();
                return;
            }
            $r1.beginObject();
            try {
                for (BoundField $r8 : this.boundFields.values()) {
                    if ($r8.writeField($r2)) {
                        $r1.name($r8.name);
                        $r8.write($r1, $r2);
                    }
                }
                $r1.endObject();
            } catch (IllegalAccessException $r3) {
                throw new AssertionError($r3);
            }
        }
    }

    public ReflectiveTypeAdapterFactory(ConstructorConstructor $r1, FieldNamingStrategy $r2, Excluder $r3) throws  {
        this.constructorConstructor = $r1;
        this.fieldNamingPolicy = $r2;
        this.excluder = $r3;
    }

    public boolean excludeField(Field $r1, boolean $z0) throws  {
        return excludeField($r1, $z0, this.excluder);
    }

    static boolean excludeField(Field $r0, boolean $z0, Excluder $r1) throws  {
        return ($r1.excludeClass($r0.getType(), $z0) || $r1.excludeField($r0, $z0)) ? false : true;
    }

    private List<String> getFieldNames(@Signature({"(", "Ljava/lang/reflect/Field;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) Field $r1) throws  {
        return getFieldName(this.fieldNamingPolicy, $r1);
    }

    static List<String> getFieldName(@Signature({"(", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/lang/reflect/Field;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) FieldNamingStrategy $r0, @Signature({"(", "Lcom/google/gson/FieldNamingStrategy;", "Ljava/lang/reflect/Field;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) Field $r1) throws  {
        SerializedName $r5 = (SerializedName) $r1.getAnnotation(SerializedName.class);
        LinkedList $r3 = new LinkedList();
        if ($r5 == null) {
            $r3.add($r0.translateName($r1));
            return $r3;
        }
        $r3.add($r5.value());
        for (String $r2 : $r5.alternate()) {
            $r3.add($r2);
        }
        return $r3;
    }

    public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
        Class $r3 = $r2.getRawType();
        return !Object.class.isAssignableFrom($r3) ? null : new Adapter(this.constructorConstructor.get($r2), getBoundFields($r1, $r2, $r3));
    }

    private BoundField createBoundField(@Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Field;", "Ljava/lang/String;", "Lcom/google/gson/reflect/TypeToken", "<*>;ZZ)", "Lcom/google/gson/internal/bind/ReflectiveTypeAdapterFactory$BoundField;"}) Gson $r1, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Field;", "Ljava/lang/String;", "Lcom/google/gson/reflect/TypeToken", "<*>;ZZ)", "Lcom/google/gson/internal/bind/ReflectiveTypeAdapterFactory$BoundField;"}) Field $r2, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Field;", "Ljava/lang/String;", "Lcom/google/gson/reflect/TypeToken", "<*>;ZZ)", "Lcom/google/gson/internal/bind/ReflectiveTypeAdapterFactory$BoundField;"}) String $r3, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Field;", "Ljava/lang/String;", "Lcom/google/gson/reflect/TypeToken", "<*>;ZZ)", "Lcom/google/gson/internal/bind/ReflectiveTypeAdapterFactory$BoundField;"}) TypeToken<?> $r4, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Field;", "Ljava/lang/String;", "Lcom/google/gson/reflect/TypeToken", "<*>;ZZ)", "Lcom/google/gson/internal/bind/ReflectiveTypeAdapterFactory$BoundField;"}) boolean $z0, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Field;", "Ljava/lang/String;", "Lcom/google/gson/reflect/TypeToken", "<*>;ZZ)", "Lcom/google/gson/internal/bind/ReflectiveTypeAdapterFactory$BoundField;"}) boolean $z1) throws  {
        final Gson gson = $r1;
        final Field field = $r2;
        final TypeToken<?> typeToken = $r4;
        final boolean isPrimitive = Primitives.isPrimitive($r4.getRawType());
        return new BoundField($r3, $z0, $z1) {
            final TypeAdapter<?> typeAdapter = ReflectiveTypeAdapterFactory.this.getFieldAdapter(gson, field, typeToken);

            void write(JsonWriter $r1, Object $r2) throws IOException, IllegalAccessException {
                new TypeAdapterRuntimeTypeWrapper(gson, this.typeAdapter, typeToken.getType()).write($r1, field.get($r2));
            }

            void read(JsonReader $r1, Object $r2) throws IOException, IllegalAccessException {
                Object $r3 = this.typeAdapter.read($r1);
                if ($r3 != null || !isPrimitive) {
                    field.set($r2, $r3);
                }
            }

            public boolean writeField(Object $r1) throws IOException, IllegalAccessException {
                if (this.serialized) {
                    return field.get($r1) != $r1;
                } else {
                    return false;
                }
            }
        };
    }

    TypeAdapter<?> getFieldAdapter(@Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Field;", "Lcom/google/gson/reflect/TypeToken", "<*>;)", "Lcom/google/gson/TypeAdapter", "<*>;"}) Gson $r1, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Field;", "Lcom/google/gson/reflect/TypeToken", "<*>;)", "Lcom/google/gson/TypeAdapter", "<*>;"}) Field $r2, @Signature({"(", "Lcom/google/gson/Gson;", "Ljava/lang/reflect/Field;", "Lcom/google/gson/reflect/TypeToken", "<*>;)", "Lcom/google/gson/TypeAdapter", "<*>;"}) TypeToken<?> $r3) throws  {
        JsonAdapter $r5 = (JsonAdapter) $r2.getAnnotation(JsonAdapter.class);
        if ($r5 != null) {
            TypeAdapter $r7 = JsonAdapterAnnotationTypeAdapterFactory.getTypeAdapter(this.constructorConstructor, $r1, $r3, $r5);
            if ($r7 != null) {
                return $r7;
            }
        }
        return $r1.getAdapter((TypeToken) $r3);
    }

    private Map<String, BoundField> getBoundFields(@Signature({"(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/gson/internal/bind/ReflectiveTypeAdapterFactory$BoundField;", ">;"}) Gson $r1, @Signature({"(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/gson/internal/bind/ReflectiveTypeAdapterFactory$BoundField;", ">;"}) TypeToken<?> $r4, @Signature({"(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<*>;", "Ljava/lang/Class", "<*>;)", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/gson/internal/bind/ReflectiveTypeAdapterFactory$BoundField;", ">;"}) Class<?> $r5) throws  {
        LinkedHashMap $r3 = new LinkedHashMap();
        if ($r5.isInterface()) {
            return $r3;
        }
        Type $r6 = $r4.getType();
        Class $r52;
        while ($r52 != Object.class) {
            for (Field $r2 : $r52.getDeclaredFields()) {
                boolean $z0 = excludeField($r2, true);
                boolean z = $z0;
                boolean $z2 = excludeField($r2, false);
                if ($z0 || $z2) {
                    $r2.setAccessible(true);
                    Type $r8 = C$Gson$Types.resolve($r4.getType(), $r52, $r2.getGenericType());
                    List $r10 = getFieldNames($r2);
                    BoundField $r11 = null;
                    for (int $i2 = 0; $i2 < $r10.size(); $i2++) {
                        String $r13 = (String) $r10.get($i2);
                        if ($i2 != 0) {
                            z = false;
                        }
                        Object $r12 = $r3.put($r13, createBoundField($r1, $r2, $r13, TypeToken.get($r8), z, $z2));
                        if ($r11 == null) {
                            $r11 = (BoundField) $r12;
                        }
                    }
                    if ($r11 != null) {
                        throw new IllegalArgumentException($r6 + " declares multiple JSON fields named " + $r11.name);
                    }
                }
            }
            TypeToken $r14 = TypeToken.get(C$Gson$Types.resolve($r4.getType(), $r52, $r52.getGenericSuperclass()));
            TypeToken $r42 = $r14;
            $r52 = $r14.getRawType();
        }
        return $r3;
    }
}
