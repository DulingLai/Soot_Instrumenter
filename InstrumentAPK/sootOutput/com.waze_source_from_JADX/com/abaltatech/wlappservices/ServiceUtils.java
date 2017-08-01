package com.abaltatech.wlappservices;

import android.util.Log;
import com.abaltatech.wlappservices.EServiceErrorCode.EServiceErrorCodeWrapper;

public class ServiceUtils {
    private static final int SLEEP_TIME = 20;
    private static final String TAG = ServiceUtils.class.getSimpleName();

    public static class InterruptFlagWrapper {
        private boolean m_flag = false;

        public boolean isInterrupted() throws  {
            return this.m_flag;
        }

        public void interrupt() throws  {
            this.m_flag = true;
        }
    }

    private static class ServiceDiscoveryNotification implements IServiceDiscoveryNotification {
        private boolean m_hasFinished = false;
        private final Object m_lock = new Object();
        private ServiceProxy m_proxy = null;

        public boolean onServiceFound(ServiceProxy $r1, int index) throws  {
            synchronized (this.m_lock) {
                if (this.m_proxy == null) {
                    this.m_proxy = $r1;
                    this.m_hasFinished = true;
                }
            }
            return false;
        }

        public void onServiceDiscoveryCompleted(int foundCount) throws  {
            synchronized (this.m_lock) {
                this.m_hasFinished = true;
            }
        }

        public void onServiceDiscoveryFailed(EServiceDiscoveryErrorCode code) throws  {
            synchronized (this.m_lock) {
                this.m_hasFinished = true;
            }
        }

        public boolean hasFinished() throws  {
            boolean z0;
            synchronized (this.m_lock) {
                z0 = this.m_hasFinished;
            }
            return z0;
        }

        public ServiceProxy getProxy() throws  {
            ServiceProxy r3;
            synchronized (this.m_lock) {
                r3 = this.m_proxy;
            }
            return r3;
        }
    }

    private static class ServiceResponseNotification implements IServiceResponseNotification {
        private int m_errorCode = -1;
        private boolean m_hasFinished = false;
        private Object m_lock = new Object();
        private ServiceResponse m_response = null;

        public void onResponseReceived(ServiceRequest request, ServiceResponse $r2) throws  {
            synchronized (this.m_lock) {
                this.m_hasFinished = true;
                this.m_response = $r2;
            }
        }

        public void onRequestFailed(ServiceRequest request, EServiceErrorCode $r2) throws  {
            synchronized (this.m_lock) {
                this.m_hasFinished = true;
                this.m_errorCode = EServiceErrorCode.toByte($r2);
            }
        }

        public boolean hasFinished() throws  {
            boolean z0;
            synchronized (this.m_lock) {
                z0 = this.m_hasFinished;
            }
            return z0;
        }

        public ServiceResponse getResponse() throws  {
            ServiceResponse r3;
            synchronized (this.m_lock) {
                r3 = this.m_response;
            }
            return r3;
        }

        public int getErrorCode() throws  {
            int i0;
            synchronized (this.m_lock) {
                i0 = this.m_errorCode;
            }
            return i0;
        }
    }

    public static ServiceResponse ExecuteRequestSynchronously(ServiceProxy $r0, ServiceRequest $r1, String $r2, EServiceErrorCodeWrapper $r3, InterruptFlagWrapper $r4, int $i0) throws  {
        if ($i0 <= 0 || $r0 == null) {
            Log.e(TAG, "ExecuteRequestSynchronously invalid args!");
            $r3.setErrorCode(EServiceErrorCode.InvalidArgument);
            return null;
        }
        ServiceResponseNotification $r6 = new ServiceResponseNotification();
        $r0.sendRequest($r2, $r1, $r6);
        while ($i0 > 0) {
            if ($r4 != null) {
                try {
                    if ($r4.isInterrupted()) {
                        break;
                    }
                } catch (InterruptedException $r5) {
                    $r5.printStackTrace();
                }
            }
            if ($r6.hasFinished()) {
                break;
            }
            Thread.sleep(20);
            $i0 -= 20;
        }
        if ($r4 == null || !$r4.isInterrupted()) {
            ServiceResponse $r7 = $r6.getResponse();
            Log.d(TAG, "ExecuteRequestSynchronously finished! code=" + $r6.getErrorCode());
            $r3.setErrorCode($r6.getErrorCode());
            return $r7;
        }
        Log.w(TAG, "ExecuteRequestSynchronously aborted due to interruption!");
        $r3.setErrorCode(EServiceErrorCode.CancelledByCaller);
        return null;
    }

    public static ServiceProxy FindServiceByNameSynchronously(String $r0, InterruptFlagWrapper $r1, int $i0) throws  {
        if ($i0 <= 0) {
            return null;
        }
        ServiceDiscoveryNotification $r3 = new ServiceDiscoveryNotification();
        ServiceManager.getInstance().findServiceByName($r0, $r3);
        while ($i0 > 0) {
            if ($r1 != null) {
                try {
                    if ($r1.isInterrupted()) {
                        break;
                    }
                } catch (InterruptedException $r2) {
                    $r2.printStackTrace();
                }
            }
            if ($r3.hasFinished()) {
                break;
            }
            Thread.sleep(20);
            $i0 -= 20;
        }
        return $r3.getProxy();
    }
}
