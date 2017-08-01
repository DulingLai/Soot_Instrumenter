package android.support.v7.util;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ParallelExecutorCompat;
import android.support.v7.util.ThreadUtil.BackgroundCallback;
import android.support.v7.util.ThreadUtil.MainThreadCallback;
import android.support.v7.util.TileList.Tile;
import android.util.Log;
import dalvik.annotation.Signature;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

class MessageThreadUtil<T> implements ThreadUtil<T> {

    static class MessageQueue {
        private SyncQueueItem mRoot;

        MessageQueue() throws  {
        }

        synchronized SyncQueueItem next() throws  {
            SyncQueueItem $r2;
            if (this.mRoot == null) {
                $r2 = null;
            } else {
                $r2 = this.mRoot;
                this.mRoot = this.mRoot.next;
            }
            return $r2;
        }

        synchronized void sendMessageAtFrontOfQueue(SyncQueueItem $r1) throws  {
            $r1.next = this.mRoot;
            this.mRoot = $r1;
        }

        synchronized void sendMessage(SyncQueueItem $r1) throws  {
            if (this.mRoot == null) {
                this.mRoot = $r1;
            } else {
                SyncQueueItem $r2 = this.mRoot;
                while ($r2.next != null) {
                    $r2 = $r2.next;
                }
                $r2.next = $r1;
            }
        }

        synchronized void removeMessages(int $i0) throws  {
            while (this.mRoot != null && this.mRoot.what == $i0) {
                SyncQueueItem $r1 = this.mRoot;
                this.mRoot = this.mRoot.next;
                $r1.recycle();
            }
            if (this.mRoot != null) {
                $r1 = this.mRoot;
                SyncQueueItem $r2 = $r1.next;
                while ($r2 != null) {
                    SyncQueueItem $r4 = $r2.next;
                    if ($r2.what == $i0) {
                        $r1.next = $r4;
                        $r2.recycle();
                    } else {
                        $r1 = $r2;
                    }
                    $r2 = $r4;
                }
            }
        }
    }

    static class SyncQueueItem {
        private static SyncQueueItem sPool;
        private static final Object sPoolLock = new Object();
        public int arg1;
        public int arg2;
        public int arg3;
        public int arg4;
        public int arg5;
        public Object data;
        private SyncQueueItem next;
        public int what;

        SyncQueueItem() throws  {
        }

        void recycle() throws  {
            this.next = null;
            this.arg5 = 0;
            this.arg4 = 0;
            this.arg3 = 0;
            this.arg2 = 0;
            this.arg1 = 0;
            this.what = 0;
            this.data = null;
            synchronized (sPoolLock) {
                if (sPool != null) {
                    this.next = sPool;
                }
                sPool = this;
            }
        }

        static SyncQueueItem obtainMessage(int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, Object $r0) throws  {
            SyncQueueItem $r2;
            synchronized (sPoolLock) {
                if (sPool == null) {
                    $r2 = new SyncQueueItem();
                } else {
                    $r2 = sPool;
                    sPool = sPool.next;
                    $r2.next = null;
                }
                $r2.what = $i0;
                $r2.arg1 = $i1;
                $r2.arg2 = $i2;
                $r2.arg3 = $i3;
                $r2.arg4 = $i4;
                $r2.arg5 = $i5;
                $r2.data = $r0;
            }
            return $r2;
        }

        static SyncQueueItem obtainMessage(int $i0, int $i1, int $i2) throws  {
            return obtainMessage($i0, $i1, $i2, 0, 0, 0, null);
        }

        static SyncQueueItem obtainMessage(int $i0, int $i1, Object $r0) throws  {
            return obtainMessage($i0, $i1, 0, 0, 0, 0, $r0);
        }
    }

    MessageThreadUtil() throws  {
    }

    public MainThreadCallback<T> getMainThreadProxy(@Signature({"(", "Landroid/support/v7/util/ThreadUtil$MainThreadCallback", "<TT;>;)", "Landroid/support/v7/util/ThreadUtil$MainThreadCallback", "<TT;>;"}) final MainThreadCallback<T> $r1) throws  {
        return new MainThreadCallback<T>() {
            private static final int ADD_TILE = 2;
            private static final int REMOVE_TILE = 3;
            private static final int UPDATE_ITEM_COUNT = 1;
            private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
            private Runnable mMainThreadRunnable = new C01981();
            private final MessageQueue mQueue = new MessageQueue();

            class C01981 implements Runnable {
                C01981() throws  {
                }

                public void run() throws  {
                    SyncQueueItem $r3 = C01991.this.mQueue.next();
                    while ($r3 != null) {
                        switch ($r3.what) {
                            case 1:
                                $r1.updateItemCount($r3.arg1, $r3.arg2);
                                break;
                            case 2:
                                $r1.addTile($r3.arg1, (Tile) $r3.data);
                                break;
                            case 3:
                                $r1.removeTile($r3.arg1, $r3.arg2);
                                break;
                            default:
                                Log.e("ThreadUtil", "Unsupported message, what=" + $r3.what);
                                break;
                        }
                        $r3 = C01991.this.mQueue.next();
                    }
                }
            }

            public void updateItemCount(int $i0, int $i1) throws  {
                sendMessage(SyncQueueItem.obtainMessage(1, $i0, $i1));
            }

            public void addTile(@Signature({"(I", "Landroid/support/v7/util/TileList$Tile", "<TT;>;)V"}) int $i0, @Signature({"(I", "Landroid/support/v7/util/TileList$Tile", "<TT;>;)V"}) Tile<T> $r1) throws  {
                sendMessage(SyncQueueItem.obtainMessage(2, $i0, (Object) $r1));
            }

            public void removeTile(int $i0, int $i1) throws  {
                sendMessage(SyncQueueItem.obtainMessage(3, $i0, $i1));
            }

            private void sendMessage(SyncQueueItem $r1) throws  {
                this.mQueue.sendMessage($r1);
                this.mMainThreadHandler.post(this.mMainThreadRunnable);
            }
        };
    }

    public BackgroundCallback<T> getBackgroundProxy(@Signature({"(", "Landroid/support/v7/util/ThreadUtil$BackgroundCallback", "<TT;>;)", "Landroid/support/v7/util/ThreadUtil$BackgroundCallback", "<TT;>;"}) final BackgroundCallback<T> $r1) throws  {
        return new BackgroundCallback<T>() {
            private static final int LOAD_TILE = 3;
            private static final int RECYCLE_TILE = 4;
            private static final int REFRESH = 1;
            private static final int UPDATE_RANGE = 2;
            private Runnable mBackgroundRunnable = new C02001();
            AtomicBoolean mBackgroundRunning = new AtomicBoolean(false);
            private final Executor mExecutor = ParallelExecutorCompat.getParallelExecutor();
            private final MessageQueue mQueue = new MessageQueue();

            class C02001 implements Runnable {
                C02001() throws  {
                }

                public void run() throws  {
                    while (true) {
                        SyncQueueItem $r3 = C02012.this.mQueue.next();
                        if ($r3 != null) {
                            switch ($r3.what) {
                                case 1:
                                    C02012.this.mQueue.removeMessages(1);
                                    $r1.refresh($r3.arg1);
                                    break;
                                case 2:
                                    C02012.this.mQueue.removeMessages(2);
                                    C02012.this.mQueue.removeMessages(3);
                                    $r1.updateRange($r3.arg1, $r3.arg2, $r3.arg3, $r3.arg4, $r3.arg5);
                                    break;
                                case 3:
                                    BackgroundCallback $r7 = $r1;
                                    int $i0 = $r3.arg1;
                                    int $i1 = $r3.arg2;
                                    $r7.loadTile($i0, $i1);
                                    break;
                                case 4:
                                    $r1.recycleTile((Tile) $r3.data);
                                    break;
                                default:
                                    Log.e("ThreadUtil", "Unsupported message, what=" + $r3.what);
                                    break;
                            }
                        }
                        C02012.this.mBackgroundRunning.set(false);
                        return;
                    }
                }
            }

            public void refresh(int $i0) throws  {
                sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(1, $i0, null));
            }

            public void updateRange(int $i0, int $i1, int $i2, int $i3, int $i4) throws  {
                sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(2, $i0, $i1, $i2, $i3, $i4, null));
            }

            public void loadTile(int $i0, int $i1) throws  {
                sendMessage(SyncQueueItem.obtainMessage(3, $i0, $i1));
            }

            public void recycleTile(@Signature({"(", "Landroid/support/v7/util/TileList$Tile", "<TT;>;)V"}) Tile<T> $r1) throws  {
                sendMessage(SyncQueueItem.obtainMessage(4, 0, (Object) $r1));
            }

            private void sendMessage(SyncQueueItem $r1) throws  {
                this.mQueue.sendMessage($r1);
                maybeExecuteBackgroundRunnable();
            }

            private void sendMessageAtFrontOfQueue(SyncQueueItem $r1) throws  {
                this.mQueue.sendMessageAtFrontOfQueue($r1);
                maybeExecuteBackgroundRunnable();
            }

            private void maybeExecuteBackgroundRunnable() throws  {
                if (this.mBackgroundRunning.compareAndSet(false, true)) {
                    this.mExecutor.execute(this.mBackgroundRunnable);
                }
            }
        };
    }
}
