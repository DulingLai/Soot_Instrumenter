package com.google.i18n.phonenumbers;

import dalvik.annotation.Signature;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class RegexCache {
    private LRUCache<String, Pattern> cache;

    private static class LRUCache<K, V> {
        private LinkedHashMap<K, V> map;
        private int size;

        public LRUCache(int $i0) throws  {
            this.size = $i0;
            this.map = new LinkedHashMap<K, V>((($i0 * 4) / 3) + 1, 0.75f, true) {
                protected boolean removeEldestEntry(@Signature({"(", "Ljava/util/Map$Entry", "<TK;TV;>;)Z"}) Entry<K, V> entry) throws  {
                    return size() > LRUCache.this.size;
                }
            };
        }

        public synchronized V get(@Signature({"(TK;)TV;"}) K $r1) throws  {
            return this.map.get($r1);
        }

        public synchronized void put(@Signature({"(TK;TV;)V"}) K $r1, @Signature({"(TK;TV;)V"}) V $r2) throws  {
            this.map.put($r1, $r2);
        }

        public synchronized boolean containsKey(@Signature({"(TK;)Z"}) K $r1) throws  {
            return this.map.containsKey($r1);
        }
    }

    public RegexCache(int $i0) throws  {
        this.cache = new LRUCache($i0);
    }

    public Pattern getPatternForRegex(String $r1) throws  {
        Pattern $r4 = (Pattern) this.cache.get($r1);
        if ($r4 != null) {
            return $r4;
        }
        $r4 = Pattern.compile($r1);
        this.cache.put($r1, $r4);
        return $r4;
    }

    boolean containsRegex(String $r1) throws  {
        return this.cache.containsKey($r1);
    }
}
