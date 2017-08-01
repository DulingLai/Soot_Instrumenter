package com.google.gson.internal;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Excluder implements TypeAdapterFactory, Cloneable {
    public static final Excluder DEFAULT = new Excluder();
    private static final double IGNORE_VERSIONS = -1.0d;
    private List<ExclusionStrategy> deserializationStrategies = Collections.emptyList();
    private int modifiers = 136;
    private boolean requireExpose;
    private List<ExclusionStrategy> serializationStrategies = Collections.emptyList();
    private boolean serializeInnerClasses = true;
    private double version = IGNORE_VERSIONS;

    protected Excluder clone() throws  {
        try {
            return (Excluder) super.clone();
        } catch (CloneNotSupportedException $r1) {
            throw new AssertionError($r1);
        }
    }

    public Excluder withVersion(double $d0) throws  {
        this = clone();
        this.version = $d0;
        return this;
    }

    public com.google.gson.internal.Excluder withModifiers(int... r6) throws  {
        /* JADX: method processing error */
/*
Error: java.lang.IndexOutOfBoundsException: bitIndex < 0: -1
	at java.util.BitSet.get(BitSet.java:623)
	at jadx.core.dex.visitors.CodeShrinker$ArgsInfo.usedArgAssign(CodeShrinker.java:138)
	at jadx.core.dex.visitors.CodeShrinker$ArgsInfo.access$300(CodeShrinker.java:43)
	at jadx.core.dex.visitors.CodeShrinker.canMoveBetweenBlocks(CodeShrinker.java:282)
	at jadx.core.dex.visitors.CodeShrinker.shrinkBlock(CodeShrinker.java:230)
	at jadx.core.dex.visitors.CodeShrinker.shrinkMethod(CodeShrinker.java:38)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkArrayForEach(LoopRegionVisitor.java:196)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkForIndexedLoop(LoopRegionVisitor.java:119)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.processLoopRegion(LoopRegionVisitor.java:65)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.enterRegion(LoopRegionVisitor.java:52)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:56)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:18)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.visit(LoopRegionVisitor.java:46)
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
        r5 = this;
        r5 = r5.clone();
        r0 = 0;
        r5.modifiers = r0;
        r1 = r6.length;
        r2 = 0;
    L_0x0009:
        if (r2 >= r1) goto L_0x0016;
    L_0x000b:
        r3 = r6[r2];
        r4 = r5.modifiers;
        r3 = r4 | r3;
        r5.modifiers = r3;
        r2 = r2 + 1;
        goto L_0x0009;
    L_0x0016:
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.Excluder.withModifiers(int[]):com.google.gson.internal.Excluder");
    }

    public Excluder disableInnerClassSerialization() throws  {
        this = clone();
        this.serializeInnerClasses = false;
        return this;
    }

    public Excluder excludeFieldsWithoutExposeAnnotation() throws  {
        this = clone();
        this.requireExpose = true;
        return this;
    }

    public Excluder withExclusionStrategy(ExclusionStrategy $r1, boolean $z0, boolean $z1) throws  {
        Excluder $r2 = clone();
        if ($z0) {
            $r2.serializationStrategies = new ArrayList(this.serializationStrategies);
            $r2.serializationStrategies.add($r1);
        }
        if (!$z1) {
            return $r2;
        }
        $r2.deserializationStrategies = new ArrayList(this.deserializationStrategies);
        $r2.deserializationStrategies.add($r1);
        return $r2;
    }

    public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
        Class $r3 = $r2.getRawType();
        boolean $z0 = excludeClass($r3, true);
        boolean $z1 = excludeClass($r3, false);
        if (!$z0 && !$z1) {
            return null;
        }
        final boolean z = $z1;
        final boolean z2 = $z0;
        final Gson gson = $r1;
        final TypeToken<T> typeToken = $r2;
        return new TypeAdapter<T>() {
            private TypeAdapter<T> delegate;

            public T read(@Signature({"(", "Lcom/google/gson/stream/JsonReader;", ")TT;"}) JsonReader $r1) throws IOException {
                if (!z) {
                    return delegate().read($r1);
                }
                $r1.skipValue();
                return null;
            }

            public void write(@Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) JsonWriter $r1, @Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) T $r2) throws IOException {
                if (z2) {
                    $r1.nullValue();
                } else {
                    delegate().write($r1, $r2);
                }
            }

            private TypeAdapter<T> delegate() throws  {
                TypeAdapter $r4 = this.delegate;
                if ($r4 != null) {
                    return $r4;
                }
                $r4 = gson.getDelegateAdapter(Excluder.this, typeToken);
                this.delegate = $r4;
                return $r4;
            }
        };
    }

    public boolean excludeField(Field $r1, boolean $z0) throws  {
        if ((this.modifiers & $r1.getModifiers()) != 0) {
            return true;
        }
        if (this.version != IGNORE_VERSIONS) {
            if (!isValidVersion((Since) $r1.getAnnotation(Since.class), (Until) $r1.getAnnotation(Until.class))) {
                return true;
            }
        }
        if ($r1.isSynthetic()) {
            return true;
        }
        if (this.requireExpose) {
            Expose $r6 = (Expose) $r1.getAnnotation(Expose.class);
            if ($r6 == null || ($z0 ? !$r6.serialize() : !$r6.deserialize())) {
                return true;
            }
        }
        if (!this.serializeInnerClasses) {
            if (isInnerClass($r1.getType())) {
                return true;
            }
        }
        if (isAnonymousOrLocal($r1.getType())) {
            return true;
        }
        List $r8;
        if ($z0) {
            $r8 = this.serializationStrategies;
        } else {
            $r8 = this.deserializationStrategies;
        }
        if (!$r8.isEmpty()) {
            FieldAttributes fieldAttributes = new FieldAttributes($r1);
            for (ExclusionStrategy shouldSkipField : $r8) {
                if (shouldSkipField.shouldSkipField(fieldAttributes)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean excludeClass(@Signature({"(", "Ljava/lang/Class", "<*>;Z)Z"}) Class<?> $r1, @Signature({"(", "Ljava/lang/Class", "<*>;Z)Z"}) boolean $z0) throws  {
        if (this.version != IGNORE_VERSIONS) {
            if (!isValidVersion((Since) $r1.getAnnotation(Since.class), (Until) $r1.getAnnotation(Until.class))) {
                return true;
            }
        }
        if (!this.serializeInnerClasses && isInnerClass($r1)) {
            return true;
        }
        if (isAnonymousOrLocal($r1)) {
            return true;
        }
        List $r5;
        if ($z0) {
            $r5 = this.serializationStrategies;
        } else {
            $r5 = this.deserializationStrategies;
        }
        for (ExclusionStrategy shouldSkipClass : $r5) {
            if (shouldSkipClass.shouldSkipClass($r1)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAnonymousOrLocal(@Signature({"(", "Ljava/lang/Class", "<*>;)Z"}) Class<?> $r1) throws  {
        return !Enum.class.isAssignableFrom($r1) && ($r1.isAnonymousClass() || $r1.isLocalClass());
    }

    private boolean isInnerClass(@Signature({"(", "Ljava/lang/Class", "<*>;)Z"}) Class<?> $r1) throws  {
        return $r1.isMemberClass() && !isStatic($r1);
    }

    private boolean isStatic(@Signature({"(", "Ljava/lang/Class", "<*>;)Z"}) Class<?> $r1) throws  {
        return ($r1.getModifiers() & 8) != 0;
    }

    private boolean isValidVersion(Since $r1, Until $r2) throws  {
        return isValidSince($r1) && isValidUntil($r2);
    }

    private boolean isValidSince(Since $r1) throws  {
        return $r1 == null || $r1.value() <= this.version;
    }

    private boolean isValidUntil(Until $r1) throws  {
        return $r1 == null || $r1.value() > this.version;
    }
}
