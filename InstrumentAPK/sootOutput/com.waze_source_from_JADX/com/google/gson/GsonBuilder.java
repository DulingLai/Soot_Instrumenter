package com.google.gson;

import com.google.gson.internal.C$Gson$Preconditions;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import dalvik.annotation.Signature;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GsonBuilder {
    private boolean complexMapKeySerialization = false;
    private String datePattern;
    private int dateStyle = 2;
    private boolean escapeHtmlChars = true;
    private Excluder excluder = Excluder.DEFAULT;
    private final List<TypeAdapterFactory> factories = new ArrayList();
    private FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
    private boolean generateNonExecutableJson = false;
    private final List<TypeAdapterFactory> hierarchyFactories = new ArrayList();
    private final Map<Type, InstanceCreator<?>> instanceCreators = new HashMap();
    private boolean lenient = false;
    private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
    private boolean prettyPrinting = false;
    private boolean serializeNulls = false;
    private boolean serializeSpecialFloatingPointValues = false;
    private int timeStyle = 2;

    public GsonBuilder setVersion(double $d0) throws  {
        this.excluder = this.excluder.withVersion($d0);
        return this;
    }

    public GsonBuilder excludeFieldsWithModifiers(int... $r1) throws  {
        this.excluder = this.excluder.withModifiers($r1);
        return this;
    }

    public GsonBuilder generateNonExecutableJson() throws  {
        this.generateNonExecutableJson = true;
        return this;
    }

    public GsonBuilder excludeFieldsWithoutExposeAnnotation() throws  {
        this.excluder = this.excluder.excludeFieldsWithoutExposeAnnotation();
        return this;
    }

    public GsonBuilder serializeNulls() throws  {
        this.serializeNulls = true;
        return this;
    }

    public GsonBuilder enableComplexMapKeySerialization() throws  {
        this.complexMapKeySerialization = true;
        return this;
    }

    public GsonBuilder disableInnerClassSerialization() throws  {
        this.excluder = this.excluder.disableInnerClassSerialization();
        return this;
    }

    public GsonBuilder setLongSerializationPolicy(LongSerializationPolicy $r1) throws  {
        this.longSerializationPolicy = $r1;
        return this;
    }

    public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy $r1) throws  {
        this.fieldNamingPolicy = $r1;
        return this;
    }

    public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy $r1) throws  {
        this.fieldNamingPolicy = $r1;
        return this;
    }

    public GsonBuilder setExclusionStrategies(ExclusionStrategy... $r1) throws  {
        for (ExclusionStrategy $r2 : $r1) {
            this.excluder = this.excluder.withExclusionStrategy($r2, true, true);
        }
        return this;
    }

    public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy $r1) throws  {
        this.excluder = this.excluder.withExclusionStrategy($r1, true, false);
        return this;
    }

    public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy $r1) throws  {
        this.excluder = this.excluder.withExclusionStrategy($r1, false, true);
        return this;
    }

    public GsonBuilder setPrettyPrinting() throws  {
        this.prettyPrinting = true;
        return this;
    }

    public GsonBuilder setLenient() throws  {
        this.lenient = true;
        return this;
    }

    public GsonBuilder disableHtmlEscaping() throws  {
        this.escapeHtmlChars = false;
        return this;
    }

    public GsonBuilder setDateFormat(String $r1) throws  {
        this.datePattern = $r1;
        return this;
    }

    public GsonBuilder setDateFormat(int $i0) throws  {
        this.dateStyle = $i0;
        this.datePattern = null;
        return this;
    }

    public GsonBuilder setDateFormat(int $i0, int $i1) throws  {
        this.dateStyle = $i0;
        this.timeStyle = $i1;
        this.datePattern = null;
        return this;
    }

    public GsonBuilder registerTypeAdapter(Type $r1, Object $r2) throws  {
        boolean $z0 = ($r2 instanceof JsonSerializer) || ($r2 instanceof JsonDeserializer) || ($r2 instanceof InstanceCreator) || ($r2 instanceof TypeAdapter);
        C$Gson$Preconditions.checkArgument($z0);
        if ($r2 instanceof InstanceCreator) {
            this.instanceCreators.put($r1, (InstanceCreator) $r2);
        }
        if (($r2 instanceof JsonSerializer) || ($r2 instanceof JsonDeserializer)) {
            this.factories.add(TreeTypeAdapter.newFactoryWithMatchRawType(TypeToken.get($r1), $r2));
        }
        if (!($r2 instanceof TypeAdapter)) {
            return this;
        }
        this.factories.add(TypeAdapters.newFactory(TypeToken.get($r1), (TypeAdapter) $r2));
        return this;
    }

    public GsonBuilder registerTypeAdapterFactory(TypeAdapterFactory $r1) throws  {
        this.factories.add($r1);
        return this;
    }

    public GsonBuilder registerTypeHierarchyAdapter(@Signature({"(", "Ljava/lang/Class", "<*>;", "Ljava/lang/Object;", ")", "Lcom/google/gson/GsonBuilder;"}) Class<?> $r1, @Signature({"(", "Ljava/lang/Class", "<*>;", "Ljava/lang/Object;", ")", "Lcom/google/gson/GsonBuilder;"}) Object $r2) throws  {
        boolean $z0 = ($r2 instanceof JsonSerializer) || ($r2 instanceof JsonDeserializer) || ($r2 instanceof TypeAdapter);
        C$Gson$Preconditions.checkArgument($z0);
        if (($r2 instanceof JsonDeserializer) || ($r2 instanceof JsonSerializer)) {
            this.hierarchyFactories.add(0, TreeTypeAdapter.newTypeHierarchyFactory($r1, $r2));
        }
        if (!($r2 instanceof TypeAdapter)) {
            return this;
        }
        this.factories.add(TypeAdapters.newTypeHierarchyFactory($r1, (TypeAdapter) $r2));
        return this;
    }

    public GsonBuilder serializeSpecialFloatingPointValues() throws  {
        this.serializeSpecialFloatingPointValues = true;
        return this;
    }

    public Gson create() throws  {
        ArrayList $r3 = new ArrayList();
        $r3.addAll(this.factories);
        Collections.reverse($r3);
        $r3.addAll(this.hierarchyFactories);
        addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, $r3);
        return new Gson(this.excluder, this.fieldNamingPolicy, this.instanceCreators, this.serializeNulls, this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.prettyPrinting, this.lenient, this.serializeSpecialFloatingPointValues, this.longSerializationPolicy, $r3);
    }

    private void addTypeAdaptersForDate(@Signature({"(", "Ljava/lang/String;", "II", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "II", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) int $i0, @Signature({"(", "Ljava/lang/String;", "II", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) int $i1, @Signature({"(", "Ljava/lang/String;", "II", "Ljava/util/List", "<", "Lcom/google/gson/TypeAdapterFactory;", ">;)V"}) List<TypeAdapterFactory> $r2) throws  {
        DefaultDateTypeAdapter $r5;
        if ($r1 != null && !"".equals($r1.trim())) {
            $r5 = new DefaultDateTypeAdapter($r1);
        } else if ($i0 != 2 && $i1 != 2) {
            $r5 = new DefaultDateTypeAdapter($i0, $i1);
        } else {
            return;
        }
        $r2.add(TreeTypeAdapter.newFactory(TypeToken.get(Date.class), $r5));
        $r2.add(TreeTypeAdapter.newFactory(TypeToken.get(Timestamp.class), $r5));
        $r2.add(TreeTypeAdapter.newFactory(TypeToken.get(java.sql.Date.class), $r5));
    }
}
