package android.support.v4.util;

import dalvik.annotation.Signature;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ArrayMap<K, V> extends SimpleArrayMap<K, V> implements Map<K, V> {
    MapCollections<K, V> mCollections;

    class C01101 extends MapCollections<K, V> {
        C01101() throws  {
        }

        protected int colGetSize() throws  {
            return ArrayMap.this.mSize;
        }

        protected Object colGetEntry(int $i0, int $i1) throws  {
            return ArrayMap.this.mArray[($i0 << 1) + $i1];
        }

        protected int colIndexOfKey(Object $r1) throws  {
            return ArrayMap.this.indexOfKey($r1);
        }

        protected int colIndexOfValue(Object $r1) throws  {
            return ArrayMap.this.indexOfValue($r1);
        }

        protected Map<K, V> colGetMap() throws  {
            return ArrayMap.this;
        }

        protected void colPut(@Signature({"(TK;TV;)V"}) K $r1, @Signature({"(TK;TV;)V"}) V $r2) throws  {
            ArrayMap.this.put($r1, $r2);
        }

        protected V colSetValue(@Signature({"(ITV;)TV;"}) int $i0, @Signature({"(ITV;)TV;"}) V $r1) throws  {
            return ArrayMap.this.setValueAt($i0, $r1);
        }

        protected void colRemoveAt(int $i0) throws  {
            ArrayMap.this.removeAt($i0);
        }

        protected void colClear() throws  {
            ArrayMap.this.clear();
        }
    }

    public ArrayMap(int $i0) throws  {
        super($i0);
    }

    public ArrayMap(SimpleArrayMap $r1) throws  {
        super($r1);
    }

    private MapCollections<K, V> getCollection() throws  {
        if (this.mCollections == null) {
            this.mCollections = new C01101();
        }
        return this.mCollections;
    }

    public boolean containsAll(@Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
        return MapCollections.containsAllHelper(this, $r1);
    }

    public void putAll(@Signature({"(", "Ljava/util/Map", "<+TK;+TV;>;)V"}) Map<? extends K, ? extends V> $r1) throws  {
        ensureCapacity(this.mSize + $r1.size());
        for (Entry $r5 : $r1.entrySet()) {
            put($r5.getKey(), $r5.getValue());
        }
    }

    public boolean removeAll(@Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
        return MapCollections.removeAllHelper(this, $r1);
    }

    public boolean retainAll(@Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
        return MapCollections.retainAllHelper(this, $r1);
    }

    public Set<Entry<K, V>> entrySet() throws  {
        return getCollection().getEntrySet();
    }

    public Set<K> keySet() throws  {
        return getCollection().getKeySet();
    }

    public Collection<V> values() throws  {
        return getCollection().getValues();
    }
}
