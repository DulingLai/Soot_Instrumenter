package com.google.gson.internal;

import com.google.gson.InstanceCreator;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class ConstructorConstructor {
    private final Map<Type, InstanceCreator<?>> instanceCreators;

    class C10214 implements ObjectConstructor<T> {
        C10214() throws  {
        }

        public T construct() throws  {
            return new TreeSet();
        }
    }

    class C10236 implements ObjectConstructor<T> {
        C10236() throws  {
        }

        public T construct() throws  {
            return new LinkedHashSet();
        }
    }

    class C10247 implements ObjectConstructor<T> {
        C10247() throws  {
        }

        public T construct() throws  {
            return new LinkedList();
        }
    }

    class C10258 implements ObjectConstructor<T> {
        C10258() throws  {
        }

        public T construct() throws  {
            return new ArrayList();
        }
    }

    class C10269 implements ObjectConstructor<T> {
        C10269() throws  {
        }

        public T construct() throws  {
            return new ConcurrentSkipListMap();
        }
    }

    public ConstructorConstructor(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/reflect/Type;", "Lcom/google/gson/InstanceCreator", "<*>;>;)V"}) Map<Type, InstanceCreator<?>> $r1) throws  {
        this.instanceCreators = $r1;
    }

    public <T> ObjectConstructor<T> get(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/internal/ObjectConstructor", "<TT;>;"}) TypeToken<T> $r1) throws  {
        final Type $r2 = $r1.getType();
        Class $r3 = $r1.getRawType();
        final InstanceCreator $r6 = (InstanceCreator) this.instanceCreators.get($r2);
        if ($r6 != null) {
            return new ObjectConstructor<T>() {
                public T construct() throws  {
                    return $r6.createInstance($r2);
                }
            };
        }
        $r6 = (InstanceCreator) this.instanceCreators.get($r3);
        if ($r6 != null) {
            return new ObjectConstructor<T>() {
                public T construct() throws  {
                    return $r6.createInstance($r2);
                }
            };
        }
        ObjectConstructor $r7 = newDefaultConstructor($r3);
        if ($r7 != null) {
            return $r7;
        }
        $r7 = newDefaultImplementationConstructor($r2, $r3);
        if ($r7 != null) {
            return $r7;
        }
        return newUnsafeAllocator($r2, $r3);
    }

    private <T> ObjectConstructor<T> newDefaultConstructor(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<-TT;>;)", "Lcom/google/gson/internal/ObjectConstructor", "<TT;>;"}) Class<? super T> $r1) throws  {
        try {
            final Constructor $r4 = $r1.getDeclaredConstructor(new Class[0]);
            if (!$r4.isAccessible()) {
                $r4.setAccessible(true);
            }
            return new ObjectConstructor<T>() {
                public T construct() throws  {
                    try {
                        return $r4.newInstance(null);
                    } catch (InstantiationException $r3) {
                        throw new RuntimeException("Failed to invoke " + $r4 + " with no args", $r3);
                    } catch (InvocationTargetException $r7) {
                        throw new RuntimeException("Failed to invoke " + $r4 + " with no args", $r7.getTargetException());
                    } catch (IllegalAccessException $r9) {
                        throw new AssertionError($r9);
                    }
                }
            };
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private <T> ObjectConstructor<T> newDefaultImplementationConstructor(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<-TT;>;)", "Lcom/google/gson/internal/ObjectConstructor", "<TT;>;"}) Type $r2, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<-TT;>;)", "Lcom/google/gson/internal/ObjectConstructor", "<TT;>;"}) Class<? super T> $r1) throws  {
        if (Collection.class.isAssignableFrom($r1)) {
            if (SortedSet.class.isAssignableFrom($r1)) {
                return new C10214();
            }
            if (EnumSet.class.isAssignableFrom($r1)) {
                final Type type = $r2;
                return new ObjectConstructor<T>() {
                    public T construct() throws  {
                        if (type instanceof ParameterizedType) {
                            Type $r1 = ((ParameterizedType) type).getActualTypeArguments()[0];
                            if ($r1 instanceof Class) {
                                return EnumSet.noneOf((Class) $r1);
                            }
                            throw new JsonIOException("Invalid EnumSet type: " + type.toString());
                        }
                        throw new JsonIOException("Invalid EnumSet type: " + type.toString());
                    }
                };
            } else if (Set.class.isAssignableFrom($r1)) {
                return new C10236();
            } else {
                if (Queue.class.isAssignableFrom($r1)) {
                    return new C10247();
                }
                return new C10258();
            }
        } else if (!Map.class.isAssignableFrom($r1)) {
            return null;
        } else {
            if (ConcurrentNavigableMap.class.isAssignableFrom($r1)) {
                return new C10269();
            }
            if (ConcurrentMap.class.isAssignableFrom($r1)) {
                return new ObjectConstructor<T>() {
                    public T construct() throws  {
                        return new ConcurrentHashMap();
                    }
                };
            }
            if (SortedMap.class.isAssignableFrom($r1)) {
                return new ObjectConstructor<T>() {
                    public T construct() throws  {
                        return new TreeMap();
                    }
                };
            }
            if ($r2 instanceof ParameterizedType) {
                if (!String.class.isAssignableFrom(TypeToken.get(((ParameterizedType) $r2).getActualTypeArguments()[0]).getRawType())) {
                    return new ObjectConstructor<T>() {
                        public T construct() throws  {
                            return new LinkedHashMap();
                        }
                    };
                }
            }
            return new ObjectConstructor<T>() {
                public T construct() throws  {
                    return new LinkedTreeMap();
                }
            };
        }
    }

    private <T> ObjectConstructor<T> newUnsafeAllocator(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<-TT;>;)", "Lcom/google/gson/internal/ObjectConstructor", "<TT;>;"}) final Type $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/reflect/Type;", "Ljava/lang/Class", "<-TT;>;)", "Lcom/google/gson/internal/ObjectConstructor", "<TT;>;"}) final Class<? super T> $r2) throws  {
        return new ObjectConstructor<T>() {
            private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();

            public T construct() throws  {
                try {
                    return this.unsafeAllocator.newInstance($r2);
                } catch (Exception $r1) {
                    throw new RuntimeException("Unable to invoke no-args constructor for " + $r1 + ". " + "Register an InstanceCreator with Gson for this type may fix this problem.", $r1);
                }
            }
        };
    }

    public String toString() throws  {
        return this.instanceCreators.toString();
    }
}
