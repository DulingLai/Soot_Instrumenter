package com.waze.ifs.async;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import dalvik.annotation.Signature;
import java.lang.ref.WeakReference;
import java.util.Random;
import junit.framework.Assert;

public class UpdateHandlers {
    private SparseArray<UpdateHandler> updateHandlers = new SparseArray();

    static abstract class UpdateHandler {
        UpdateHandler next = null;

        abstract Handler getHandler() throws ;

        abstract void setHandler(Handler handler) throws ;

        UpdateHandler() throws  {
        }

        UpdateHandler(Handler $r1) throws  {
            setHandler($r1);
        }

        void sendMessage(int $i0, Bundle $r1) throws  {
            Handler $r2 = getHandler();
            if ($r2 != null) {
                Message $r3 = $r2.obtainMessage($i0);
                $r3.setData($r1);
                $r2.sendMessage($r3);
            }
        }

        public void sendMessage(int $i0, int $i1, int $i2) throws  {
            Handler $r1 = getHandler();
            if ($r1 != null) {
                $r1.sendMessage($r1.obtainMessage($i0, $i1, $i2));
            }
        }

        boolean shouldRemove(Handler $r1) throws  {
            Handler $r2 = getHandler();
            return $r2 == null || $r2 == $r1;
        }
    }

    static class HardUpdateHandler extends UpdateHandler {
        Handler _handler;

        void setHandler(Handler $r1) throws  {
            this._handler = $r1;
        }

        Handler getHandler() throws  {
            return this._handler;
        }

        HardUpdateHandler(Handler $r1) throws  {
            super($r1);
        }
    }

    public static class MicroHandler extends Handler {
        WeakReference<MicroHandlerCallback> _cb = null;

        public interface MicroHandlerCallback {
            void handleMessage(Message message) throws ;
        }

        public MicroHandler(MicroHandlerCallback $r1) throws  {
            setCallback($r1);
        }

        public void setCallback(MicroHandlerCallback $r1) throws  {
            this._cb = new WeakReference($r1);
        }

        public void handleMessage(Message $r1) throws  {
            super.handleMessage($r1);
            if (this._cb != null) {
                MicroHandlerCallback $r4 = (MicroHandlerCallback) this._cb.get();
                if ($r4 != null) {
                    $r4.handleMessage($r1);
                }
            }
        }
    }

    public static class TestThis {
        Handler h1 = new C17001();
        Handler h2 = new C17012();
        Handler hString = new C17023();
        Random rand = new Random();
        String test_messagesString;
        int test_numMessages1 = 0;
        int test_numMessages2 = 0;

        class C17001 extends Handler {
            C17001() throws  {
            }

            public boolean sendMessageAtTime(Message msg, long uptimeMillis) throws  {
                TestThis $r2 = TestThis.this;
                $r2.test_numMessages1++;
                return true;
            }
        }

        class C17012 extends Handler {
            C17012() throws  {
            }

            public boolean sendMessageAtTime(Message msg, long uptimeMillis) throws  {
                TestThis $r2 = TestThis.this;
                $r2.test_numMessages2++;
                return true;
            }
        }

        class C17023 extends Handler {
            C17023() throws  {
            }

            public boolean sendMessageAtTime(Message $r1, long uptimeMillis) throws  {
                TestThis.this.test_messagesString = $r1.getData().getString("string");
                return true;
            }
        }

        public TestThis() throws  {
            testHandlerInsertion();
            testHandlerInsertionAndRemoval();
            testHandlerTwoInsertions();
            testHandlerString();
        }

        private void testHandlerInsertion() throws  {
            int $i0 = this.rand.nextInt();
            UpdateHandlers $r1 = new UpdateHandlers();
            this.test_numMessages1 = 0;
            $r1.setUpdateHandler($i0, this.h1);
            $r1.sendUpdateMessage($i0, null);
            Assert.assertEquals(1, this.test_numMessages1);
        }

        private void testHandlerInsertionAndRemoval() throws  {
            int $i0 = this.rand.nextInt();
            UpdateHandlers $r1 = new UpdateHandlers();
            this.test_numMessages1 = 0;
            $r1.setUpdateHandler($i0, this.h1);
            $r1.unsetUpdateHandler($i0, this.h1);
            $r1.sendUpdateMessage($i0, null);
            Assert.assertEquals(0, this.test_numMessages1);
        }

        private void testHandlerTwoInsertions() throws  {
            int $i0 = this.rand.nextInt();
            int $i1 = this.rand.nextInt();
            UpdateHandlers $r1 = new UpdateHandlers();
            this.test_numMessages1 = 0;
            $r1.setUpdateHandler($i0, this.h1);
            $r1.setUpdateHandler($i1, this.h2);
            $r1.sendUpdateMessage($i0, null);
            Assert.assertEquals(1, this.test_numMessages1);
            Assert.assertEquals(0, this.test_numMessages2);
            $r1.sendUpdateMessage($i1, null);
            Assert.assertEquals(1, this.test_numMessages1);
            Assert.assertEquals(1, this.test_numMessages2);
        }

        private void testHandlerString() throws  {
            int $i0 = this.rand.nextInt();
            UpdateHandlers $r2 = new UpdateHandlers();
            this.test_messagesString = null;
            $r2.setUpdateHandler($i0, this.hString);
            Bundle $r1 = new Bundle();
            $r1.putString("string", "test");
            $r2.sendUpdateMessage($i0, $r1);
            Assert.assertEquals("test", this.test_messagesString);
        }
    }

    static class WeakUpdateHandler extends UpdateHandler {
        WeakReference<Handler> _handler;

        void setHandler(Handler $r1) throws  {
            this._handler = new WeakReference($r1);
        }

        Handler getHandler() throws  {
            if (this._handler == null) {
                return null;
            }
            return (Handler) this._handler.get();
        }

        WeakUpdateHandler(Handler $r1) throws  {
            super($r1);
        }

        WeakUpdateHandler(@Signature({"(", "Ljava/lang/ref/WeakReference", "<", "Landroid/os/Handler;", ">;)V"}) WeakReference<Handler> $r1) throws  {
            this._handler = $r1;
        }
    }

    public void setUpdateHandler(int $i0, Handler $r1) throws  {
        UpdateHandler $r5 = (UpdateHandler) this.updateHandlers.get($i0);
        WeakUpdateHandler $r2 = new WeakUpdateHandler($r1);
        $r2.next = $r5;
        this.updateHandlers.put($i0, $r2);
    }

    public void setHardUpdateHandler(int $i0, Handler $r1) throws  {
        UpdateHandler $r5 = (UpdateHandler) this.updateHandlers.get($i0);
        HardUpdateHandler $r2 = new HardUpdateHandler($r1);
        $r2.next = $r5;
        this.updateHandlers.put($i0, $r2);
    }

    public void setUpdateHandler(@Signature({"(I", "Ljava/lang/ref/WeakReference", "<", "Landroid/os/Handler;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/lang/ref/WeakReference", "<", "Landroid/os/Handler;", ">;)V"}) WeakReference<Handler> $r1) throws  {
        UpdateHandler $r5 = (UpdateHandler) this.updateHandlers.get($i0);
        WeakUpdateHandler $r2 = new WeakUpdateHandler((WeakReference) $r1);
        $r2.next = $r5;
        this.updateHandlers.put($i0, $r2);
    }

    public void sendUpdateMessage(int $i0, Bundle $r1) throws  {
        for (UpdateHandler $r4 = (UpdateHandler) this.updateHandlers.get($i0); $r4 != null; $r4 = $r4.next) {
            $r4.sendMessage($i0, $r1);
        }
    }

    public void sendUpdateMessage(int $i0, int $i1, int $i2) throws  {
        for (UpdateHandler $r3 = (UpdateHandler) this.updateHandlers.get($i0); $r3 != null; $r3 = $r3.next) {
            $r3.sendMessage($i0, $i1, $i2);
        }
    }

    public void unsetUpdateHandler(int $i0, Handler $r1) throws  {
        UpdateHandler $r6 = null;
        for (UpdateHandler $r5 = (UpdateHandler) this.updateHandlers.get($i0); $r5 != null; $r5 = $r5.next) {
            if (!$r5.shouldRemove($r1)) {
                $r6 = $r5;
            } else if ($r6 == null) {
                this.updateHandlers.put($i0, $r5.next);
            } else {
                $r6.next = $r5.next;
            }
        }
    }
}
