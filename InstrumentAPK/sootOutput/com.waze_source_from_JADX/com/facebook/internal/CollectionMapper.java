package com.facebook.internal;

import com.facebook.FacebookException;
import dalvik.annotation.Signature;
import java.util.Iterator;
import java.util.LinkedList;

public class CollectionMapper {

    public interface OnErrorListener {
        void onError(FacebookException facebookException) throws ;
    }

    public interface OnMapperCompleteListener extends OnErrorListener {
        void onComplete() throws ;
    }

    public interface OnMapValueCompleteListener extends OnErrorListener {
        void onComplete(Object obj) throws ;
    }

    public interface Collection<T> {
        Object get(@Signature({"(TT;)", "Ljava/lang/Object;"}) T t) throws ;

        Iterator<T> keyIterator() throws ;

        void set(@Signature({"(TT;", "Ljava/lang/Object;", "Lcom/facebook/internal/CollectionMapper$OnErrorListener;", ")V"}) T t, @Signature({"(TT;", "Ljava/lang/Object;", "Lcom/facebook/internal/CollectionMapper$OnErrorListener;", ")V"}) Object obj, @Signature({"(TT;", "Ljava/lang/Object;", "Lcom/facebook/internal/CollectionMapper$OnErrorListener;", ")V"}) OnErrorListener onErrorListener) throws ;
    }

    public interface ValueMapper {
        void mapValue(Object obj, OnMapValueCompleteListener onMapValueCompleteListener) throws ;
    }

    public static <T> void iterate(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/facebook/internal/CollectionMapper$Collection", "<TT;>;", "Lcom/facebook/internal/CollectionMapper$ValueMapper;", "Lcom/facebook/internal/CollectionMapper$OnMapperCompleteListener;", ")V"}) Collection<T> $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/facebook/internal/CollectionMapper$Collection", "<TT;>;", "Lcom/facebook/internal/CollectionMapper$ValueMapper;", "Lcom/facebook/internal/CollectionMapper$OnMapperCompleteListener;", ")V"}) ValueMapper $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/facebook/internal/CollectionMapper$Collection", "<TT;>;", "Lcom/facebook/internal/CollectionMapper$ValueMapper;", "Lcom/facebook/internal/CollectionMapper$OnMapperCompleteListener;", ")V"}) OnMapperCompleteListener $r2) throws  {
        final Mutable $r3 = new Mutable(Boolean.valueOf(false));
        final Mutable $r7 = new Mutable(Integer.valueOf(1));
        final OnMapperCompleteListener onMapperCompleteListener = $r2;
        final C05191 $r4 = new OnMapperCompleteListener() {
            public void onComplete() throws  {
                if (!((Boolean) $r3.value).booleanValue()) {
                    Mutable $r1 = $r7;
                    Integer $r5 = Integer.valueOf(((Integer) $r7.value).intValue() - 1);
                    $r1.value = $r5;
                    if ($r5.intValue() == 0) {
                        onMapperCompleteListener.onComplete();
                    }
                }
            }

            public void onError(FacebookException $r1) throws  {
                if (!((Boolean) $r3.value).booleanValue()) {
                    $r3.value = Boolean.valueOf(true);
                    onMapperCompleteListener.onError($r1);
                }
            }
        };
        Iterator $r10 = $r0.keyIterator();
        LinkedList $r5 = new LinkedList();
        while ($r10.hasNext()) {
            $r5.add($r10.next());
        }
        for (final Object $r12 : $r5) {
            Object $r11 = $r0.get($r12);
            final Collection<T> collection = $r0;
            C05202 $r6 = new OnMapValueCompleteListener() {
                public void onComplete(Object $r1) throws  {
                    collection.set($r12, $r1, $r4);
                    $r4.onComplete();
                }

                public void onError(FacebookException $r1) throws  {
                    $r4.onError($r1);
                }
            };
            Integer $r9 = (Integer) $r7.value;
            $r7.value = Integer.valueOf(((Integer) $r7.value).intValue() + 1);
            $r1.mapValue($r11, $r6);
        }
        $r4.onComplete();
    }

    private CollectionMapper() throws  {
    }
}
