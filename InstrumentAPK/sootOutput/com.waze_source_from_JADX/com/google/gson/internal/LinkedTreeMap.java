package com.google.gson.internal;

import dalvik.annotation.Signature;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public final class LinkedTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = (!LinkedTreeMap.class.desiredAssertionStatus());
    private static final Comparator<Comparable> NATURAL_ORDER = new C10311();
    Comparator<? super K> comparator;
    private EntrySet entrySet;
    final Node<K, V> header;
    private KeySet keySet;
    int modCount;
    Node<K, V> root;
    int size;

    static class C10311 implements Comparator<Comparable> {
        C10311() throws  {
        }

        public int compare(Comparable $r1, Comparable $r2) throws  {
            return $r1.compareTo($r2);
        }
    }

    private abstract class LinkedTreeMapIterator<T> implements Iterator<T> {
        int expectedModCount = LinkedTreeMap.this.modCount;
        Node<K, V> lastReturned = null;
        Node<K, V> next = LinkedTreeMap.this.header.next;

        LinkedTreeMapIterator() throws  {
        }

        public final boolean hasNext() throws  {
            return this.next != LinkedTreeMap.this.header;
        }

        final Node<K, V> nextNode() throws  {
            Node $r1 = this.next;
            if ($r1 == LinkedTreeMap.this.header) {
                throw new NoSuchElementException();
            } else if (LinkedTreeMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            } else {
                this.next = $r1.next;
                this.lastReturned = $r1;
                return $r1;
            }
        }

        public final void remove() throws  {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            LinkedTreeMap.this.removeInternal(this.lastReturned, true);
            this.lastReturned = null;
            this.expectedModCount = LinkedTreeMap.this.modCount;
        }
    }

    class EntrySet extends AbstractSet<Entry<K, V>> {

        class C10321 extends LinkedTreeMapIterator<Entry<K, V>> {
            C10321() throws  {
                super();
            }

            public Entry<K, V> next() throws  {
                return nextNode();
            }
        }

        EntrySet() throws  {
        }

        public int size() throws  {
            return LinkedTreeMap.this.size;
        }

        public Iterator<Entry<K, V>> iterator() throws  {
            return new C10321();
        }

        public boolean contains(Object $r2) throws  {
            return ($r2 instanceof Entry) && LinkedTreeMap.this.findByEntry((Entry) $r2) != null;
        }

        public boolean remove(Object $r2) throws  {
            if (!($r2 instanceof Entry)) {
                return false;
            }
            Node $r1 = LinkedTreeMap.this.findByEntry((Entry) $r2);
            if ($r1 == null) {
                return false;
            }
            LinkedTreeMap.this.removeInternal($r1, true);
            return true;
        }

        public void clear() throws  {
            LinkedTreeMap.this.clear();
        }
    }

    final class KeySet extends AbstractSet<K> {

        class C10331 extends LinkedTreeMapIterator<K> {
            C10331() throws  {
                super();
            }

            public K next() throws  {
                return nextNode().key;
            }
        }

        KeySet() throws  {
        }

        public int size() throws  {
            return LinkedTreeMap.this.size;
        }

        public Iterator<K> iterator() throws  {
            return new C10331();
        }

        public boolean contains(Object $r1) throws  {
            return LinkedTreeMap.this.containsKey($r1);
        }

        public boolean remove(Object $r1) throws  {
            return LinkedTreeMap.this.removeInternalByKey($r1) != null;
        }

        public void clear() throws  {
            LinkedTreeMap.this.clear();
        }
    }

    static final class Node<K, V> implements Entry<K, V> {
        int height;
        final K key;
        Node<K, V> left;
        Node<K, V> next;
        Node<K, V> parent;
        Node<K, V> prev;
        Node<K, V> right;
        V value;

        Node() throws  {
            this.key = null;
            this.prev = this;
            this.next = this;
        }

        Node(@Signature({"(", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;TK;", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r1, @Signature({"(", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;TK;", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;)V"}) K $r2, @Signature({"(", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;TK;", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r3, @Signature({"(", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;TK;", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r4) throws  {
            this.parent = $r1;
            this.key = $r2;
            this.height = 1;
            this.next = $r3;
            this.prev = $r4;
            $r4.next = this;
            $r3.prev = this;
        }

        public K getKey() throws  {
            return this.key;
        }

        public V getValue() throws  {
            return this.value;
        }

        public V setValue(@Signature({"(TV;)TV;"}) V $r1) throws  {
            Object r2 = this.value;
            this.value = $r1;
            return r2;
        }

        public boolean equals(Object $r1) throws  {
            if (!($r1 instanceof Entry)) {
                return false;
            }
            Entry $r2 = (Entry) $r1;
            if (this.key == null) {
                if ($r2.getKey() != null) {
                    return false;
                }
            } else if (!this.key.equals($r2.getKey())) {
                return false;
            }
            if (this.value == null) {
                if ($r2.getValue() != null) {
                    return false;
                }
            } else if (!this.value.equals($r2.getValue())) {
                return false;
            }
            return true;
        }

        public int hashCode() throws  {
            int $i0 = 0;
            int $i1 = this.key == null ? 0 : this.key.hashCode();
            if (this.value != null) {
                $i0 = this.value.hashCode();
            }
            return $i1 ^ $i0;
        }

        public String toString() throws  {
            return this.key + "=" + this.value;
        }

        public Node<K, V> first() throws  {
            Node $r1 = this;
            for (this = this.left; this != null; this = this.left) {
                $r1 = this;
            }
            return $r1;
        }

        public Node<K, V> last() throws  {
            Node $r1 = this;
            for (this = this.right; this != null; this = this.right) {
                $r1 = this;
            }
            return $r1;
        }
    }

    public LinkedTreeMap() throws  {
        this(NATURAL_ORDER);
    }

    public LinkedTreeMap(@Signature({"(", "Ljava/util/Comparator", "<-TK;>;)V"}) Comparator<? super K> $r1) throws  {
        Comparator $r12;
        this.size = 0;
        this.modCount = 0;
        this.header = new Node();
        if ($r1 == null) {
            $r12 = NATURAL_ORDER;
        }
        this.comparator = $r12;
    }

    public int size() throws  {
        return this.size;
    }

    public V get(@Signature({"(", "Ljava/lang/Object;", ")TV;"}) Object $r1) throws  {
        Node $r2 = findByObject($r1);
        return $r2 != null ? $r2.value : null;
    }

    public boolean containsKey(Object $r1) throws  {
        return findByObject($r1) != null;
    }

    public V put(@Signature({"(TK;TV;)TV;"}) K $r1, @Signature({"(TK;TV;)TV;"}) V $r2) throws  {
        if ($r1 == null) {
            throw new NullPointerException("key == null");
        }
        Node $r3 = find($r1, true);
        Object $r12 = $r3.value;
        $r3.value = $r2;
        return $r12;
    }

    public void clear() throws  {
        this.root = null;
        this.size = 0;
        this.modCount++;
        Node $r1 = this.header;
        $r1.prev = $r1;
        $r1.next = $r1;
    }

    public V remove(@Signature({"(", "Ljava/lang/Object;", ")TV;"}) Object $r1) throws  {
        Node $r2 = removeInternalByKey($r1);
        return $r2 != null ? $r2.value : null;
    }

    Node<K, V> find(@Signature({"(TK;Z)", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;"}) K $r1, @Signature({"(TK;Z)", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;"}) boolean $z0) throws  {
        Node $r3;
        Comparator $r2 = this.comparator;
        Node $r4 = this.root;
        int $i0 = 0;
        if ($r4 != null) {
            Comparable $r6 = $r2 == NATURAL_ORDER ? (Comparable) $r1 : null;
            while (true) {
                $i0 = $r6 != null ? $r6.compareTo($r4.key) : $r2.compare($r1, $r4.key);
                if ($i0 == 0) {
                    return $r4;
                }
                if ($i0 < 0) {
                    $r3 = $r4.left;
                } else {
                    $r3 = $r4.right;
                }
                if ($r3 == null) {
                    break;
                }
                $r4 = $r3;
            }
        }
        if (!$z0) {
            return null;
        }
        Node $r12;
        $r3 = this.header;
        if ($r4 != null) {
            $r12 = new Node($r4, $r1, $r3, $r3.prev);
            if ($i0 < 0) {
                $r4.left = $r12;
            } else {
                $r4.right = $r12;
            }
            rebalance($r4, true);
        } else if ($r2 != NATURAL_ORDER || ($r1 instanceof Comparable)) {
            $r12 = new Node($r4, $r1, $r3, $r3.prev);
            this.root = $r12;
        } else {
            throw new ClassCastException($r1.getClass().getName() + " is not Comparable");
        }
        this.size++;
        this.modCount++;
        return $r12;
    }

    Node<K, V> findByObject(@Signature({"(", "Ljava/lang/Object;", ")", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;"}) Object $r1) throws  {
        if ($r1 == null) {
            return null;
        }
        try {
            return find($r1, false);
        } catch (ClassCastException e) {
            return null;
        }
    }

    Node<K, V> findByEntry(@Signature({"(", "Ljava/util/Map$Entry", "<**>;)", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;"}) Entry<?, ?> $r1) throws  {
        Node $r3 = findByObject($r1.getKey());
        boolean $z0 = $r3 != null && equal($r3.value, $r1.getValue());
        return $z0 ? $r3 : null;
    }

    private boolean equal(Object $r1, Object $r2) throws  {
        return $r1 == $r2 || ($r1 != null && $r1.equals($r2));
    }

    void removeInternal(@Signature({"(", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;Z)V"}) Node<K, V> $r1, @Signature({"(", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;Z)V"}) boolean $z0) throws  {
        if ($z0) {
            $r1.prev.next = $r1.next;
            $r1.next.prev = $r1.prev;
        }
        Node $r3 = $r1.left;
        Node $r4 = $r1.right;
        Node $r2 = $r1.parent;
        if ($r3 == null || $r4 == null) {
            if ($r3 != null) {
                replaceInParent($r1, $r3);
                $r1.left = null;
            } else if ($r4 != null) {
                replaceInParent($r1, $r4);
                $r1.right = null;
            } else {
                replaceInParent($r1, null);
            }
            rebalance($r2, false);
            this.size--;
            this.modCount++;
            return;
        }
        $r2 = $r3.height > $r4.height ? $r3.last() : $r4.first();
        removeInternal($r2, false);
        int $i0 = 0;
        $r3 = $r1.left;
        if ($r3 != null) {
            $i0 = $r3.height;
            $r2.left = $r3;
            $r3.parent = $r2;
            $r1.left = null;
        }
        int $i1 = 0;
        $r3 = $r1.right;
        if ($r3 != null) {
            $i1 = $r3.height;
            $r2.right = $r3;
            $r3.parent = $r2;
            $r1.right = null;
        }
        $r2.height = Math.max($i0, $i1) + 1;
        replaceInParent($r1, $r2);
    }

    Node<K, V> removeInternalByKey(@Signature({"(", "Ljava/lang/Object;", ")", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;"}) Object $r1) throws  {
        Node $r2 = findByObject($r1);
        if ($r2 == null) {
            return $r2;
        }
        removeInternal($r2, true);
        return $r2;
    }

    private void replaceInParent(@Signature({"(", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r1, @Signature({"(", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r2) throws  {
        Node $r3 = $r1.parent;
        $r1.parent = null;
        if ($r2 != null) {
            $r2.parent = $r3;
        }
        if ($r3 == null) {
            this.root = $r2;
        } else if ($r3.left == $r1) {
            $r3.left = $r2;
        } else if ($assertionsDisabled || $r3.right == $r1) {
            $r3.right = $r2;
        } else {
            throw new AssertionError();
        }
    }

    private void rebalance(@Signature({"(", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;Z)V"}) Node<K, V> $r1, @Signature({"(", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;Z)V"}) boolean $z0) throws  {
        Node $r12;
        while ($r12 != null) {
            int $i1;
            int $i2;
            Node $r2 = $r12.left;
            Node $r3 = $r12.right;
            if ($r2 != null) {
                $i1 = $r2.height;
            } else {
                $i1 = 0;
            }
            if ($r3 != null) {
                $i2 = $r3.height;
            } else {
                $i2 = 0;
            }
            int $i0 = $i1 - $i2;
            Node $r4;
            if ($i0 == -2) {
                $r2 = $r3.left;
                $r4 = $r3.right;
                if ($r4 != null) {
                    $i0 = $r4.height;
                } else {
                    $i0 = 0;
                }
                if ($r2 != null) {
                    $i1 = $r2.height;
                } else {
                    $i1 = 0;
                }
                $i0 = $i1 - $i0;
                if ($i0 == -1 || ($i0 == 0 && !$z0)) {
                    rotateLeft($r12);
                } else if ($assertionsDisabled || $i0 == 1) {
                    rotateRight($r3);
                    rotateLeft($r12);
                } else {
                    throw new AssertionError();
                }
                if ($z0) {
                    return;
                }
            } else if ($i0 == 2) {
                $r3 = $r2.left;
                $r4 = $r2.right;
                if ($r4 != null) {
                    $i0 = $r4.height;
                } else {
                    $i0 = 0;
                }
                if ($r3 != null) {
                    $i1 = $r3.height;
                } else {
                    $i1 = 0;
                }
                $i0 = $i1 - $i0;
                if ($i0 == 1 || ($i0 == 0 && !$z0)) {
                    rotateRight($r12);
                } else if ($assertionsDisabled || $i0 == -1) {
                    rotateLeft($r2);
                    rotateRight($r12);
                } else {
                    throw new AssertionError();
                }
                if ($z0) {
                    return;
                }
            } else if ($i0 == 0) {
                $r12.height = $i1 + 1;
                if ($z0) {
                    return;
                }
            } else if ($assertionsDisabled || $i0 == -1 || $i0 == 1) {
                $r12.height = Math.max($i1, $i2) + 1;
                if (!$z0) {
                    return;
                }
            } else {
                throw new AssertionError();
            }
            $r12 = $r12.parent;
        }
    }

    private void rotateLeft(@Signature({"(", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r1) throws  {
        int $i1;
        int $i2;
        int $i0 = 0;
        Node $r2 = $r1.left;
        Node $r3 = $r1.right;
        Node $r4 = $r3.left;
        Node $r5 = $r3.right;
        $r1.right = $r4;
        if ($r4 != null) {
            $r4.parent = $r1;
        }
        replaceInParent($r1, $r3);
        $r3.left = $r1;
        $r1.parent = $r3;
        if ($r2 != null) {
            $i1 = $r2.height;
        } else {
            $i1 = 0;
        }
        if ($r4 != null) {
            $i2 = $r4.height;
        } else {
            $i2 = 0;
        }
        $r1.height = Math.max($i1, $i2) + 1;
        $i1 = $r1.height;
        if ($r5 != null) {
            $i0 = $r5.height;
        }
        $r3.height = Math.max($i1, $i0) + 1;
    }

    private void rotateRight(@Signature({"(", "Lcom/google/gson/internal/LinkedTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r1) throws  {
        int $i1;
        int $i2;
        int $i0 = 0;
        Node $r2 = $r1.left;
        Node $r5 = $r1.right;
        Node $r3 = $r2.left;
        Node $r4 = $r2.right;
        $r1.left = $r4;
        if ($r4 != null) {
            $r4.parent = $r1;
        }
        replaceInParent($r1, $r2);
        $r2.right = $r1;
        $r1.parent = $r2;
        if ($r5 != null) {
            $i1 = $r5.height;
        } else {
            $i1 = 0;
        }
        if ($r4 != null) {
            $i2 = $r4.height;
        } else {
            $i2 = 0;
        }
        $r1.height = Math.max($i1, $i2) + 1;
        $i1 = $r1.height;
        if ($r3 != null) {
            $i0 = $r3.height;
        }
        $r2.height = Math.max($i1, $i0) + 1;
    }

    public Set<Entry<K, V>> entrySet() throws  {
        EntrySet $r1 = this.entrySet;
        if ($r1 != null) {
            return $r1;
        }
        $r1 = new EntrySet();
        this.entrySet = $r1;
        return $r1;
    }

    public Set<K> keySet() throws  {
        KeySet $r1 = this.keySet;
        if ($r1 != null) {
            return $r1;
        }
        $r1 = new KeySet();
        this.keySet = $r1;
        return $r1;
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }
}
