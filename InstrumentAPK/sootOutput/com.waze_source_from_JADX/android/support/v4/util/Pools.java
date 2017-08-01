package android.support.v4.util;

import dalvik.annotation.Signature;

public final class Pools {

    public interface Pool<T> {
        T acquire() throws ;

        boolean release(@Signature({"(TT;)Z"}) T t) throws ;
    }

    public static class SimplePool<T> implements Pool<T> {
        private final Object[] mPool;
        private int mPoolSize;

        public SimplePool(int $i0) throws  {
            if ($i0 <= 0) {
                throw new IllegalArgumentException("The max pool size must be > 0");
            }
            this.mPool = new Object[$i0];
        }

        public T acquire() throws  {
            if (this.mPoolSize <= 0) {
                return null;
            }
            int $i0 = this.mPoolSize - 1;
            Object $r2 = this.mPool[$i0];
            this.mPool[$i0] = null;
            this.mPoolSize--;
            return $r2;
        }

        public boolean release(@Signature({"(TT;)Z"}) T $r1) throws  {
            if (isInPool($r1)) {
                throw new IllegalStateException("Already in the pool!");
            } else if (this.mPoolSize >= this.mPool.length) {
                return false;
            } else {
                this.mPool[this.mPoolSize] = $r1;
                this.mPoolSize++;
                return true;
            }
        }

        private boolean isInPool(@Signature({"(TT;)Z"}) T $r1) throws  {
            for (int $i0 = 0; $i0 < this.mPoolSize; $i0++) {
                if (this.mPool[$i0] == $r1) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class SynchronizedPool<T> extends SimplePool<T> {
        private final Object mLock = new Object();

        public SynchronizedPool(int $i0) throws  {
            super($i0);
        }

        public T acquire() throws  {
            Object $r2;
            synchronized (this.mLock) {
                $r2 = super.acquire();
            }
            return $r2;
        }

        public boolean release(@Signature({"(TT;)Z"}) T $r1) throws  {
            boolean $z0;
            synchronized (this.mLock) {
                $z0 = super.release($r1);
            }
            return $z0;
        }
    }

    private Pools() throws  {
    }
}
