package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzavo {
    private final Map<Type, zzauq<?>> bXF;

    /* compiled from: dalvik_source_com.waze.apk */
    class C07772 implements zzavt<T> {
        final /* synthetic */ zzavo bYh;

        C07772(zzavo $r1) throws  {
            this.bYh = $r1;
        }

        public T hv() throws  {
            return new LinkedHashMap();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07783 implements zzavt<T> {
        final /* synthetic */ zzavo bYh;

        C07783(zzavo $r1) throws  {
            this.bYh = $r1;
        }

        public T hv() throws  {
            return new zzavs();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07827 implements zzavt<T> {
        final /* synthetic */ zzavo bYh;

        C07827(zzavo $r1) throws  {
            this.bYh = $r1;
        }

        public T hv() throws  {
            return new TreeSet();
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07849 implements zzavt<T> {
        final /* synthetic */ zzavo bYh;

        C07849(zzavo $r1) throws  {
            this.bYh = $r1;
        }

        public T hv() throws  {
            return new LinkedHashSet();
        }
    }

    public zzavo(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/android/gms/internal/zzauq", "<*>;>;)V"}) Map<Type, zzauq<?>> $r1) throws  {
        this.bXF = $r1;
    }

    private <T> zzavt<T> zzc(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<-TT;>;)", "Lcom/google/android/gms/internal/zzavt", "<TT;>;"}) Type $r2, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<-TT;>;)", "Lcom/google/android/gms/internal/zzavt", "<TT;>;"}) Class<? super T> $r1) throws  {
        if (Collection.class.isAssignableFrom($r1)) {
            if (SortedSet.class.isAssignableFrom($r1)) {
                return new C07827(this);
            }
            if (!EnumSet.class.isAssignableFrom($r1)) {
                return Set.class.isAssignableFrom($r1) ? new C07849(this) : Queue.class.isAssignableFrom($r1) ? new zzavt<T>(this) {
                    final /* synthetic */ zzavo bYh;

                    {
                        this.bYh = $r1;
                    }

                    public T hv() throws  {
                        return new LinkedList();
                    }
                } : new zzavt<T>(this) {
                    final /* synthetic */ zzavo bYh;

                    {
                        this.bYh = $r1;
                    }

                    public T hv() throws  {
                        return new ArrayList();
                    }
                };
            } else {
                final Type type = $r2;
                return new zzavt<T>(this) {
                    final /* synthetic */ zzavo bYh;

                    public T hv() throws  {
                        if (type instanceof ParameterizedType) {
                            Type $r1 = ((ParameterizedType) type).getActualTypeArguments()[0];
                            if ($r1 instanceof Class) {
                                return EnumSet.noneOf((Class) $r1);
                            }
                            String $r7 = "Invalid EnumSet type: ";
                            String $r8 = String.valueOf(type.toString());
                            throw new zzauv($r8.length() != 0 ? $r7.concat($r8) : new String("Invalid EnumSet type: "));
                        }
                        $r7 = "Invalid EnumSet type: ";
                        $r8 = String.valueOf(type.toString());
                        throw new zzauv($r8.length() != 0 ? $r7.concat($r8) : new String("Invalid EnumSet type: "));
                    }
                };
            }
        } else if (!Map.class.isAssignableFrom($r1)) {
            return null;
        } else {
            if (SortedMap.class.isAssignableFrom($r1)) {
                return new zzavt<T>(this) {
                    final /* synthetic */ zzavo bYh;

                    {
                        this.bYh = $r1;
                    }

                    public T hv() throws  {
                        return new TreeMap();
                    }
                };
            }
            if ($r2 instanceof ParameterizedType) {
                if (!String.class.isAssignableFrom(zzawk.zzl(((ParameterizedType) $r2).getActualTypeArguments()[0]).hN())) {
                    return new C07772(this);
                }
            }
            return new C07783(this);
        }
    }

    private <T> zzavt<T> zzd(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<-TT;>;)", "Lcom/google/android/gms/internal/zzavt", "<TT;>;"}) final Type $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<-TT;>;)", "Lcom/google/android/gms/internal/zzavt", "<TT;>;"}) final Class<? super T> $r2) throws  {
        return new zzavt<T>(this) {
            final /* synthetic */ zzavo bYh;
            private final zzavw bYi = zzavw.hA();

            public T hv() throws  {
                try {
                    return this.bYi.zze($r2);
                } catch (Exception $r4) {
                    String $r7 = String.valueOf($r1);
                    throw new RuntimeException(new StringBuilder(String.valueOf($r7).length() + 116).append("Unable to invoke no-args constructor for ").append($r7).append(". ").append("Register an InstanceCreator with Gson for this type may fix this problem.").toString(), $r4);
                }
            }
        };
    }

    private <T> zzavt<T> zzk(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<-TT;>;)", "Lcom/google/android/gms/internal/zzavt", "<TT;>;"}) Class<? super T> $r1) throws  {
        try {
            final Constructor $r3 = $r1.getDeclaredConstructor(new Class[0]);
            if (!$r3.isAccessible()) {
                $r3.setAccessible(true);
            }
            return new zzavt<T>(this) {
                final /* synthetic */ zzavo bYh;

                public T hv() throws  {
                    String $r5;
                    try {
                        return $r3.newInstance(null);
                    } catch (InstantiationException $r3) {
                        $r5 = String.valueOf($r3);
                        throw new RuntimeException(new StringBuilder(String.valueOf($r5).length() + 30).append("Failed to invoke ").append($r5).append(" with no args").toString(), $r3);
                    } catch (InvocationTargetException $r8) {
                        $r5 = String.valueOf($r3);
                        throw new RuntimeException(new StringBuilder(String.valueOf($r5).length() + 30).append("Failed to invoke ").append($r5).append(" with no args").toString(), $r8.getTargetException());
                    } catch (IllegalAccessException $r10) {
                        throw new AssertionError($r10);
                    }
                }
            };
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public String toString() throws  {
        return this.bXF.toString();
    }

    public <T> zzavt<T> zzb(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavt", "<TT;>;"}) zzawk<T> $r1) throws  {
        final Type $r2 = $r1.hO();
        Class $r3 = $r1.hN();
        final zzauq $r6 = (zzauq) this.bXF.get($r2);
        if ($r6 != null) {
            return new zzavt<T>(this) {
                final /* synthetic */ zzavo bYh;

                public T hv() throws  {
                    return $r6.zza($r2);
                }
            };
        }
        $r6 = (zzauq) this.bXF.get($r3);
        if ($r6 != null) {
            return new zzavt<T>(this) {
                final /* synthetic */ zzavo bYh;

                public T hv() throws  {
                    return $r6.zza($r2);
                }
            };
        }
        zzavt $r9 = zzk($r3);
        if ($r9 != null) {
            return $r9;
        }
        $r9 = zzc($r2, $r3);
        return $r9 == null ? zzd($r2, $r3) : $r9;
    }
}
