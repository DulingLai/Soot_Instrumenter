package com.facebook.internal;

import android.content.Intent;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.gcm.nts.GcmScheduler;
import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.Map;

public final class CallbackManagerImpl implements CallbackManager {
    private static Map<Integer, Callback> staticCallbacks = new HashMap();
    private Map<Integer, Callback> callbacks = new HashMap();

    public interface Callback {
        boolean onActivityResult(int i, Intent intent) throws ;
    }

    public enum RequestCodeOffset {
        Login(0),
        Share(1),
        Message(2),
        Like(3),
        GameRequest(4),
        AppGroupCreate(5),
        AppGroupJoin(6),
        AppInvite(7);
        
        private final int offset;

        private RequestCodeOffset(@Signature({"(I)V"}) int $i1) throws  {
            this.offset = $i1;
        }

        public int toRequestCode() throws  {
            return FacebookSdk.getCallbackRequestCodeOffset() + this.offset;
        }
    }

    public static synchronized void registerStaticCallback(int $i0, Callback $r0) throws  {
        synchronized (CallbackManagerImpl.class) {
            try {
                Validate.notNull($r0, GcmScheduler.INTENT_PARAM_CALLBACK);
                if (!staticCallbacks.containsKey(Integer.valueOf($i0))) {
                    staticCallbacks.put(Integer.valueOf($i0), $r0);
                }
            } catch (Throwable th) {
                Class cls = CallbackManagerImpl.class;
            }
        }
    }

    private static synchronized Callback getStaticCallback(Integer $r0) throws  {
        Class cls = CallbackManagerImpl.class;
        synchronized (cls) {
            try {
                Callback $r3 = (Callback) staticCallbacks.get($r0);
                return $r3;
            } finally {
                cls = CallbackManagerImpl.class;
            }
        }
    }

    private static boolean runStaticCallback(int $i0, int $i1, Intent $r0) throws  {
        Callback $r2 = getStaticCallback(Integer.valueOf($i0));
        if ($r2 != null) {
            return $r2.onActivityResult($i1, $r0);
        }
        return false;
    }

    public void registerCallback(int $i0, Callback $r1) throws  {
        Validate.notNull($r1, GcmScheduler.INTENT_PARAM_CALLBACK);
        this.callbacks.put(Integer.valueOf($i0), $r1);
    }

    public boolean onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        Callback $r5 = (Callback) this.callbacks.get(Integer.valueOf($i0));
        if ($r5 != null) {
            return $r5.onActivityResult($i1, $r1);
        }
        return runStaticCallback($i0, $i1, $r1);
    }
}
