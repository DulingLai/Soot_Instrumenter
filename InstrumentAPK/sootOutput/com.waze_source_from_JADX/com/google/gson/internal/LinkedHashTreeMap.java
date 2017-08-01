package com.google.gson.internal;

import dalvik.annotation.Signature;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public final class LinkedHashTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = (!LinkedHashTreeMap.class.desiredAssertionStatus());
    private static final Comparator<Comparable> NATURAL_ORDER = new C10281();
    Comparator<? super K> comparator;
    private EntrySet entrySet;
    final Node<K, V> header;
    private KeySet keySet;
    int modCount;
    int size;
    Node<K, V>[] table;
    int threshold;

    static class C10281 implements Comparator<Comparable> {
        C10281() throws  {
        }

        public int compare(Comparable $r1, Comparable $r2) throws  {
            return $r1.compareTo($r2);
        }
    }

    static final class AvlBuilder<K, V> {
        private int leavesSkipped;
        private int leavesToSkip;
        private int size;
        private Node<K, V> stack;

        AvlBuilder() throws  {
        }

        void reset(int $i0) throws  {
            this.leavesToSkip = ((Integer.highestOneBit($i0) * 2) - 1) - $i0;
            this.size = 0;
            this.leavesSkipped = 0;
            this.stack = null;
        }

        void add(@Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r1) throws  {
            $r1.right = null;
            $r1.parent = null;
            $r1.left = null;
            $r1.height = 1;
            if (this.leavesToSkip > 0 && (this.size & 1) == 0) {
                this.size++;
                this.leavesToSkip--;
                this.leavesSkipped++;
            }
            $r1.parent = this.stack;
            this.stack = $r1;
            this.size++;
            if (this.leavesToSkip > 0 && (this.size & 1) == 0) {
                this.size++;
                this.leavesToSkip--;
                this.leavesSkipped++;
            }
            for (int $i0 = 4; (this.size & ($i0 - 1)) == $i0 - 1; $i0 *= 2) {
                Node $r2;
                Node $r12;
                if (this.leavesSkipped == 0) {
                    $r2 = this.stack;
                    Node $r3 = $r2.parent;
                    $r12 = $r3.parent;
                    $r3.parent = $r12.parent;
                    this.stack = $r3;
                    $r3.left = $r12;
                    $r3.right = $r2;
                    $r3.height = $r2.height + 1;
                    $r12.parent = $r3;
                    $r2.parent = $r3;
                } else if (this.leavesSkipped == 1) {
                    $r12 = this.stack;
                    $r2 = $r12.parent;
                    this.stack = $r2;
                    $r2.right = $r12;
                    $r2.height = $r12.height + 1;
                    $r12.parent = $r2;
                    this.leavesSkipped = 0;
                } else if (this.leavesSkipped == 2) {
                    this.leavesSkipped = 0;
                }
            }
        }

        Node<K, V> root() throws  {
            Node $r1 = this.stack;
            if ($r1.parent == null) {
                return $r1;
            }
            throw new IllegalStateException();
        }
    }

    static class AvlIterator<K, V> {
        private Node<K, V> stackTop;

        AvlIterator() throws  {
        }

        void reset(@Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r1) throws  {
            Node $r2 = null;
            Node $r12;
            while ($r12 != null) {
                $r12.parent = $r2;
                $r2 = $r12;
                $r12 = $r12.left;
            }
            this.stackTop = $r2;
        }

        public Node<K, V> next() throws  {
            Node $r1 = this.stackTop;
            if ($r1 == null) {
                return null;
            }
            Node $r2 = $r1.parent;
            $r1.parent = null;
            for (Node $r3 = $r1.right; $r3 != null; $r3 = $r3.left) {
                $r3.parent = $r2;
                $r2 = $r3;
            }
            this.stackTop = $r2;
            return $r1;
        }
    }

    private abstract class LinkedTreeMapIterator<T> implements Iterator<T> {
        int expectedModCount = LinkedHashTreeMap.this.modCount;
        Node<K, V> lastReturned = null;
        Node<K, V> next = LinkedHashTreeMap.this.header.next;

        LinkedTreeMapIterator() throws  {
        }

        public final boolean hasNext() throws  {
            return this.next != LinkedHashTreeMap.this.header;
        }

        final Node<K, V> nextNode() throws  {
            Node $r1 = this.next;
            if ($r1 == LinkedHashTreeMap.this.header) {
                throw new NoSuchElementException();
            } else if (LinkedHashTreeMap.this.modCount != this.expectedModCount) {
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
            LinkedHashTreeMap.this.removeInternal(this.lastReturned, true);
            this.lastReturned = null;
            this.expectedModCount = LinkedHashTreeMap.this.modCount;
        }
    }

    final class EntrySet extends AbstractSet<Entry<K, V>> {

        class C10291 extends LinkedTreeMapIterator<Entry<K, V>> {
            C10291() throws  {
                super();
            }

            public Entry<K, V> next() throws  {
                return nextNode();
            }
        }

        EntrySet() throws  {
        }

        public int size() throws  {
            return LinkedHashTreeMap.this.size;
        }

        public Iterator<Entry<K, V>> iterator() throws  {
            return new C10291();
        }

        public boolean contains(Object $r2) throws  {
            return ($r2 instanceof Entry) && LinkedHashTreeMap.this.findByEntry((Entry) $r2) != null;
        }

        public boolean remove(Object $r2) throws  {
            if (!($r2 instanceof Entry)) {
                return false;
            }
            Node $r1 = LinkedHashTreeMap.this.findByEntry((Entry) $r2);
            if ($r1 == null) {
                return false;
            }
            LinkedHashTreeMap.this.removeInternal($r1, true);
            return true;
        }

        public void clear() throws  {
            LinkedHashTreeMap.this.clear();
        }
    }

    final class KeySet extends AbstractSet<K> {

        class C10301 extends LinkedTreeMapIterator<K> {
            C10301() throws  {
                super();
            }

            public K next() throws  {
                return nextNode().key;
            }
        }

        KeySet() throws  {
        }

        public int size() throws  {
            return LinkedHashTreeMap.this.size;
        }

        public Iterator<K> iterator() throws  {
            return new C10301();
        }

        public boolean contains(Object $r1) throws  {
            return LinkedHashTreeMap.this.containsKey($r1);
        }

        public boolean remove(Object $r1) throws  {
            return LinkedHashTreeMap.this.removeInternalByKey($r1) != null;
        }

        public void clear() throws  {
            LinkedHashTreeMap.this.clear();
        }
    }

    static final class Node<K, V> implements Entry<K, V> {
        final int hash;
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
            this.hash = -1;
            this.prev = this;
            this.next = this;
        }

        Node(@Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;TK;I", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r1, @Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;TK;I", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;)V"}) K $r2, @Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;TK;I", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;)V"}) int $i0, @Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;TK;I", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r3, @Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;TK;I", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r4) throws  {
            this.parent = $r1;
            this.key = $r2;
            this.hash = $i0;
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

    public LinkedHashTreeMap() throws  {
        this(NATURAL_ORDER);
    }

    public LinkedHashTreeMap(@Signature({"(", "Ljava/util/Comparator", "<-TK;>;)V"}) Comparator<? super K> $r1) throws  {
        Comparator $r12;
        this.size = 0;
        this.modCount = 0;
        if ($r1 == null) {
            $r12 = NATURAL_ORDER;
        }
        this.comparator = $r12;
        this.header = new Node();
        this.table = new Node[16];
        this.threshold = (this.table.length / 2) + (this.table.length / 4);
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
        Arrays.fill(this.table, null);
        this.size = 0;
        this.modCount++;
        Node $r1 = this.header;
        Node $r4 = $r1.next;
        while ($r4 != $r1) {
            Node $r2 = $r4.next;
            $r4.prev = null;
            $r4.next = null;
            $r4 = $r2;
        }
        $r1.prev = $r1;
        $r1.next = $r1;
    }

    public V remove(@Signature({"(", "Ljava/lang/Object;", ")TV;"}) Object $r1) throws  {
        Node $r2 = removeInternalByKey($r1);
        return $r2 != null ? $r2.value : null;
    }

    Node<K, V> find(@Signature({"(TK;Z)", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;"}) K $r1, @Signature({"(TK;Z)", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;"}) boolean $z0) throws  {
        Node $r2;
        Comparator $r3 = this.comparator;
        Node[] $r4 = this.table;
        int $i1 = secondaryHash($r1.hashCode());
        int $i0 = $i1 & ($r4.length - 1);
        Node $r5 = $r4[$i0];
        int $i2 = 0;
        if ($r5 != null) {
            Comparable $r7 = $r3 == NATURAL_ORDER ? (Comparable) $r1 : null;
            while (true) {
                $i2 = $r7 != null ? $r7.compareTo($r5.key) : $r3.compare($r1, $r5.key);
                if ($i2 == 0) {
                    return $r5;
                }
                if ($i2 < 0) {
                    $r2 = $r5.left;
                } else {
                    $r2 = $r5.right;
                }
                if ($r2 == null) {
                    break;
                }
                $r5 = $r2;
            }
        }
        if (!$z0) {
            return null;
        }
        $r2 = this.header;
        Node node;
        if ($r5 != null) {
            node = new Node($r5, $r1, $i1, $r2, $r2.prev);
            if ($i2 < 0) {
                $r5.left = node;
            } else {
                $r5.right = node;
            }
            rebalance($r5, true);
        } else if ($r3 != NATURAL_ORDER || ($r1 instanceof Comparable)) {
            node = new Node($r5, $r1, $i1, $r2, $r2.prev);
            $r4[$i0] = node;
        } else {
            throw new ClassCastException($r1.getClass().getName() + " is not Comparable");
        }
        $i1 = this.size;
        this.size = $i1 + 1;
        if ($i1 > this.threshold) {
            doubleCapacity();
        }
        this.modCount++;
        return $r13;
    }

    Node<K, V> findByObject(@Signature({"(", "Ljava/lang/Object;", ")", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;"}) Object $r1) throws  {
        if ($r1 == null) {
            return null;
        }
        try {
            return find($r1, false);
        } catch (ClassCastException e) {
            return null;
        }
    }

    Node<K, V> findByEntry(@Signature({"(", "Ljava/util/Map$Entry", "<**>;)", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;"}) Entry<?, ?> $r1) throws  {
        Node $r3 = findByObject($r1.getKey());
        boolean $z0 = $r3 != null && equal($r3.value, $r1.getValue());
        return $z0 ? $r3 : null;
    }

    private boolean equal(Object $r1, Object $r2) throws  {
        return $r1 == $r2 || ($r1 != null && $r1.equals($r2));
    }

    private static int secondaryHash(int $i0) throws  {
        $i0 ^= ($i0 >>> 20) ^ ($i0 >>> 12);
        return (($i0 >>> 7) ^ $i0) ^ ($i0 >>> 4);
    }

    void removeInternal(@Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;Z)V"}) Node<K, V> $r1, @Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;Z)V"}) boolean $z0) throws  {
        if ($z0) {
            $r1.prev.next = $r1.next;
            $r1.next.prev = $r1.prev;
            $r1.prev = null;
            $r1.next = null;
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

    Node<K, V> removeInternalByKey(@Signature({"(", "Ljava/lang/Object;", ")", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;"}) Object $r1) throws  {
        Node $r2 = findByObject($r1);
        if ($r2 == null) {
            return $r2;
        }
        removeInternal($r2, true);
        return $r2;
    }

    private void replaceInParent(@Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r1, @Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r2) throws  {
        Node $r3 = $r1.parent;
        $r1.parent = null;
        if ($r2 != null) {
            $r2.parent = $r3;
        }
        if ($r3 == null) {
            this.table[$r1.hash & (this.table.length - 1)] = $r2;
        } else if ($r3.left == $r1) {
            $r3.left = $r2;
        } else if ($assertionsDisabled || $r3.right == $r1) {
            $r3.right = $r2;
        } else {
            throw new AssertionError();
        }
    }

    private void rebalance(@Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;Z)V"}) Node<K, V> $r1, @Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;Z)V"}) boolean $z0) throws  {
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

    private void rotateLeft(@Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r1) throws  {
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

    private void rotateRight(@Signature({"(", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;)V"}) Node<K, V> $r1) throws  {
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

    private void doubleCapacity() throws  {
        this.table = doubleCapacity(this.table);
        this.threshold = (this.table.length / 2) + (this.table.length / 4);
    }

    static <K, V> Node<K, V>[] doubleCapacity(@Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">([", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;)[", "Lcom/google/gson/internal/LinkedHashTreeMap$Node", "<TK;TV;>;"}) Node<K, V>[] $r0) throws  {
        int $i0 = $r0.length;
        Node[] $r3 = new Node[($i0 * 2)];
        AvlIterator $r1 = new AvlIterator();
        AvlBuilder $r2 = new AvlBuilder();
        AvlBuilder $r4 = new AvlBuilder();
        for (int $i2 = 0; $i2 < $i0; $i2++) {
            Node $r5 = $r0[$i2];
            if ($r5 != null) {
                $r1.reset($r5);
                int $i1 = 0;
                int $i3 = 0;
                while (true) {
                    Node $r6 = $r1.next();
                    if ($r6 == null) {
                        break;
                    } else if (($r6.hash & $i0) == 0) {
                        $i1++;
                    } else {
                        $i3++;
                    }
                }
                $r2.reset($i1);
                $r4.reset($i3);
                $r1.reset($r5);
                while (true) {
                    $r5 = $r1.next();
                    if ($r5 == null) {
                        break;
                    } else if (($r5.hash & $i0) == 0) {
                        $r2.add($r5);
                    } else {
                        $r4.add($r5);
                    }
                }
                if ($i1 > 0) {
                    $r5 = $r2.root();
                } else {
                    $r5 = null;
                }
                $r3[$i2] = $r5;
                $i1 = $i2 + $i0;
                if ($i3 > 0) {
                    $r5 = $r4.root();
                } else {
                    $r5 = null;
                }
                $r3[$i1] = $r5;
            }
        }
        return $r3;
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }
}
