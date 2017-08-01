package android.support.v4.util;

import dalvik.annotation.Signature;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

abstract class MapCollections<K, V> {
    EntrySet mEntrySet;
    KeySet mKeySet;
    ValuesCollection mValues;

    final class ArrayIterator<T> implements Iterator<T> {
        boolean mCanRemove = false;
        int mIndex;
        final int mOffset;
        int mSize;

        ArrayIterator(int $i0) throws  {
            this.mOffset = $i0;
            this.mSize = MapCollections.this.colGetSize();
        }

        public boolean hasNext() throws  {
            return this.mIndex < this.mSize;
        }

        public T next() throws  {
            Object $r1 = MapCollections.this.colGetEntry(this.mIndex, this.mOffset);
            this.mIndex++;
            this.mCanRemove = true;
            return $r1;
        }

        public void remove() throws  {
            if (this.mCanRemove) {
                this.mIndex--;
                this.mSize--;
                this.mCanRemove = false;
                MapCollections.this.colRemoveAt(this.mIndex);
                return;
            }
            throw new IllegalStateException();
        }
    }

    final class EntrySet implements Set<Entry<K, V>> {
        EntrySet() throws  {
        }

        public boolean add(@Signature({"(", "Ljava/util/Map$Entry", "<TK;TV;>;)Z"}) Entry<K, V> entry) throws  {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(@Signature({"(", "Ljava/util/Collection", "<+", "Ljava/util/Map$Entry", "<TK;TV;>;>;)Z"}) Collection<? extends Entry<K, V>> $r1) throws  {
            int $i0 = MapCollections.this.colGetSize();
            for (Entry $r5 : $r1) {
                MapCollections.this.colPut($r5.getKey(), $r5.getValue());
            }
            return $i0 != MapCollections.this.colGetSize();
        }

        public void clear() throws  {
            MapCollections.this.colClear();
        }

        public boolean contains(Object $r1) throws  {
            if (!($r1 instanceof Entry)) {
                return false;
            }
            Entry $r2 = (Entry) $r1;
            int $i0 = MapCollections.this.colIndexOfKey($r2.getKey());
            return $i0 >= 0 ? ContainerHelpers.equal(MapCollections.this.colGetEntry($i0, 1), $r2.getValue()) : false;
        }

        public boolean containsAll(@Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
            for (Object $r3 : $r1) {
                if (!contains($r3)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isEmpty() throws  {
            return MapCollections.this.colGetSize() == 0;
        }

        public Iterator<Entry<K, V>> iterator() throws  {
            return new MapIterator();
        }

        public boolean remove(Object object) throws  {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(@Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> collection) throws  {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(@Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> collection) throws  {
            throw new UnsupportedOperationException();
        }

        public int size() throws  {
            return MapCollections.this.colGetSize();
        }

        public Object[] toArray() throws  {
            throw new UnsupportedOperationException();
        }

        public <T> T[] toArray(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;)[TT;"}) T[] tArr) throws  {
            throw new UnsupportedOperationException();
        }

        public boolean equals(Object $r1) throws  {
            return MapCollections.equalsSetHelper(this, $r1);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            for (int $i1 = MapCollections.this.colGetSize() - 1; $i1 >= 0; $i1--) {
                int $i2;
                int $i3;
                Object $r2 = MapCollections.this.colGetEntry($i1, 0);
                Object $r3 = MapCollections.this.colGetEntry($i1, 1);
                if ($r2 == null) {
                    $i2 = 0;
                } else {
                    $i2 = $r2.hashCode();
                }
                if ($r3 == null) {
                    $i3 = 0;
                } else {
                    $i3 = $r3.hashCode();
                }
                $i0 += $i3 ^ $i2;
            }
            return $i0;
        }
    }

    final class KeySet implements Set<K> {
        KeySet() throws  {
        }

        public boolean add(@Signature({"(TK;)Z"}) K k) throws  {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(@Signature({"(", "Ljava/util/Collection", "<+TK;>;)Z"}) Collection<? extends K> collection) throws  {
            throw new UnsupportedOperationException();
        }

        public void clear() throws  {
            MapCollections.this.colClear();
        }

        public boolean contains(Object $r1) throws  {
            return MapCollections.this.colIndexOfKey($r1) >= 0;
        }

        public boolean containsAll(@Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
            return MapCollections.containsAllHelper(MapCollections.this.colGetMap(), $r1);
        }

        public boolean isEmpty() throws  {
            return MapCollections.this.colGetSize() == 0;
        }

        public Iterator<K> iterator() throws  {
            return new ArrayIterator(0);
        }

        public boolean remove(Object $r1) throws  {
            int $i0 = MapCollections.this.colIndexOfKey($r1);
            if ($i0 < 0) {
                return false;
            }
            MapCollections.this.colRemoveAt($i0);
            return true;
        }

        public boolean removeAll(@Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
            return MapCollections.removeAllHelper(MapCollections.this.colGetMap(), $r1);
        }

        public boolean retainAll(@Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
            return MapCollections.retainAllHelper(MapCollections.this.colGetMap(), $r1);
        }

        public int size() throws  {
            return MapCollections.this.colGetSize();
        }

        public Object[] toArray() throws  {
            return MapCollections.this.toArrayHelper(0);
        }

        public <T> T[] toArray(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;)[TT;"}) T[] $r1) throws  {
            return MapCollections.this.toArrayHelper($r1, 0);
        }

        public boolean equals(Object $r1) throws  {
            return MapCollections.equalsSetHelper(this, $r1);
        }

        public int hashCode() throws  {
            int $i0 = 0;
            for (int $i1 = MapCollections.this.colGetSize() - 1; $i1 >= 0; $i1--) {
                int $i2;
                Object $r2 = MapCollections.this.colGetEntry($i1, 0);
                if ($r2 == null) {
                    $i2 = 0;
                } else {
                    $i2 = $r2.hashCode();
                }
                $i0 += $i2;
            }
            return $i0;
        }
    }

    final class MapIterator implements Iterator<Entry<K, V>>, Entry<K, V> {
        int mEnd;
        boolean mEntryValid = false;
        int mIndex;

        MapIterator() throws  {
            this.mEnd = MapCollections.this.colGetSize() - 1;
            this.mIndex = -1;
        }

        public boolean hasNext() throws  {
            return this.mIndex < this.mEnd;
        }

        public Entry<K, V> next() throws  {
            this.mIndex++;
            this.mEntryValid = true;
            return this;
        }

        public void remove() throws  {
            if (this.mEntryValid) {
                MapCollections.this.colRemoveAt(this.mIndex);
                this.mIndex--;
                this.mEnd--;
                this.mEntryValid = false;
                return;
            }
            throw new IllegalStateException();
        }

        public K getKey() throws  {
            if (this.mEntryValid) {
                return MapCollections.this.colGetEntry(this.mIndex, 0);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public V getValue() throws  {
            if (this.mEntryValid) {
                return MapCollections.this.colGetEntry(this.mIndex, 1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public V setValue(@Signature({"(TV;)TV;"}) V $r1) throws  {
            if (this.mEntryValid) {
                return MapCollections.this.colSetValue(this.mIndex, $r1);
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final boolean equals(Object $r1) throws  {
            boolean $z0 = true;
            if (!this.mEntryValid) {
                throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            } else if (!($r1 instanceof Entry)) {
                return false;
            } else {
                Entry $r3 = (Entry) $r1;
                if (!(ContainerHelpers.equal($r3.getKey(), MapCollections.this.colGetEntry(this.mIndex, 0)) && ContainerHelpers.equal($r3.getValue(), MapCollections.this.colGetEntry(this.mIndex, 1)))) {
                    $z0 = false;
                }
                return $z0;
            }
        }

        public final int hashCode() throws  {
            int $i0 = 0;
            if (this.mEntryValid) {
                int $i1;
                Object $r3 = MapCollections.this.colGetEntry(this.mIndex, 0);
                Object $r4 = MapCollections.this.colGetEntry(this.mIndex, 1);
                if ($r3 == null) {
                    $i1 = 0;
                } else {
                    $i1 = $r3.hashCode();
                }
                if ($r4 != null) {
                    $i0 = $r4.hashCode();
                }
                return $i0 ^ $i1;
            }
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
        }

        public final String toString() throws  {
            return getKey() + "=" + getValue();
        }
    }

    final class ValuesCollection implements Collection<V> {
        ValuesCollection() throws  {
        }

        public boolean add(@Signature({"(TV;)Z"}) V v) throws  {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(@Signature({"(", "Ljava/util/Collection", "<+TV;>;)Z"}) Collection<? extends V> collection) throws  {
            throw new UnsupportedOperationException();
        }

        public void clear() throws  {
            MapCollections.this.colClear();
        }

        public boolean contains(Object $r1) throws  {
            return MapCollections.this.colIndexOfValue($r1) >= 0;
        }

        public boolean containsAll(@Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
            for (Object $r3 : $r1) {
                if (!contains($r3)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isEmpty() throws  {
            return MapCollections.this.colGetSize() == 0;
        }

        public Iterator<V> iterator() throws  {
            return new ArrayIterator(1);
        }

        public boolean remove(Object $r1) throws  {
            int $i0 = MapCollections.this.colIndexOfValue($r1);
            if ($i0 < 0) {
                return false;
            }
            MapCollections.this.colRemoveAt($i0);
            return true;
        }

        public boolean removeAll(@Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
            int $i0 = MapCollections.this.colGetSize();
            boolean $z0 = false;
            int $i1 = 0;
            while ($i1 < $i0) {
                if ($r1.contains(MapCollections.this.colGetEntry($i1, 1))) {
                    MapCollections.this.colRemoveAt($i1);
                    $i1--;
                    $i0--;
                    $z0 = true;
                }
                $i1++;
            }
            return $z0;
        }

        public boolean retainAll(@Signature({"(", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
            int $i0 = MapCollections.this.colGetSize();
            boolean $z0 = false;
            int $i1 = 0;
            while ($i1 < $i0) {
                if (!$r1.contains(MapCollections.this.colGetEntry($i1, 1))) {
                    MapCollections.this.colRemoveAt($i1);
                    $i1--;
                    $i0--;
                    $z0 = true;
                }
                $i1++;
            }
            return $z0;
        }

        public int size() throws  {
            return MapCollections.this.colGetSize();
        }

        public Object[] toArray() throws  {
            return MapCollections.this.toArrayHelper(1);
        }

        public <T> T[] toArray(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;)[TT;"}) T[] $r1) throws  {
            return MapCollections.this.toArrayHelper($r1, 1);
        }
    }

    protected abstract void colClear() throws ;

    protected abstract Object colGetEntry(int i, int i2) throws ;

    protected abstract Map<K, V> colGetMap() throws ;

    protected abstract int colGetSize() throws ;

    protected abstract int colIndexOfKey(Object obj) throws ;

    protected abstract int colIndexOfValue(Object obj) throws ;

    protected abstract void colPut(@Signature({"(TK;TV;)V"}) K k, @Signature({"(TK;TV;)V"}) V v) throws ;

    protected abstract void colRemoveAt(int i) throws ;

    protected abstract V colSetValue(@Signature({"(ITV;)TV;"}) int i, @Signature({"(ITV;)TV;"}) V v) throws ;

    MapCollections() throws  {
    }

    public static <K, V> boolean containsAllHelper(@Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(", "Ljava/util/Map", "<TK;TV;>;", "Ljava/util/Collection", "<*>;)Z"}) Map<K, V> $r0, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(", "Ljava/util/Map", "<TK;TV;>;", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
        for (Object $r3 : $r1) {
            if (!$r0.containsKey($r3)) {
                return false;
            }
        }
        return true;
    }

    public static <K, V> boolean removeAllHelper(@Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(", "Ljava/util/Map", "<TK;TV;>;", "Ljava/util/Collection", "<*>;)Z"}) Map<K, V> $r0, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(", "Ljava/util/Map", "<TK;TV;>;", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
        int $i0 = $r0.size();
        for (Object $r3 : $r1) {
            $r0.remove($r3);
        }
        return $i0 != $r0.size();
    }

    public static <K, V> boolean retainAllHelper(@Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(", "Ljava/util/Map", "<TK;TV;>;", "Ljava/util/Collection", "<*>;)Z"}) Map<K, V> $r0, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(", "Ljava/util/Map", "<TK;TV;>;", "Ljava/util/Collection", "<*>;)Z"}) Collection<?> $r1) throws  {
        int $i0 = $r0.size();
        Iterator $r3 = $r0.keySet().iterator();
        while ($r3.hasNext()) {
            if (!$r1.contains($r3.next())) {
                $r3.remove();
            }
        }
        return $i0 != $r0.size();
    }

    public Object[] toArrayHelper(int $i0) throws  {
        int $i1 = colGetSize();
        Object[] $r1 = new Object[$i1];
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            $r1[$i2] = colGetEntry($i2, $i0);
        }
        return $r1;
    }

    public <T> T[] toArrayHelper(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;I)[TT;"}) T[] $r1, @Signature({"<T:", "Ljava/lang/Object;", ">([TT;I)[TT;"}) int $i0) throws  {
        int $i1 = colGetSize();
        if ($r1.length < $i1) {
            Object[] $r12 = (Object[]) Array.newInstance($r1.getClass().getComponentType(), $i1);
        }
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            $r12[$i2] = colGetEntry($i2, $i0);
        }
        if ($r12.length <= $i1) {
            return $r12;
        }
        $r12[$i1] = null;
        return $r12;
    }

    public static <T> boolean equalsSetHelper(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/Set", "<TT;>;", "Ljava/lang/Object;", ")Z"}) Set<T> $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/util/Set", "<TT;>;", "Ljava/lang/Object;", ")Z"}) Object $r1) throws  {
        boolean $z0 = true;
        if ($r0 == $r1) {
            return true;
        }
        if (!($r1 instanceof Set)) {
            return false;
        }
        Set $r2 = (Set) $r1;
        try {
            if (!($r0.size() == $r2.size() && $r0.containsAll($r2))) {
                $z0 = false;
            }
            return $z0;
        } catch (NullPointerException e) {
            return false;
        } catch (ClassCastException e2) {
            return false;
        }
    }

    public Set<Entry<K, V>> getEntrySet() throws  {
        if (this.mEntrySet == null) {
            this.mEntrySet = new EntrySet();
        }
        return this.mEntrySet;
    }

    public Set<K> getKeySet() throws  {
        if (this.mKeySet == null) {
            this.mKeySet = new KeySet();
        }
        return this.mKeySet;
    }

    public Collection<V> getValues() throws  {
        if (this.mValues == null) {
            this.mValues = new ValuesCollection();
        }
        return this.mValues;
    }
}
