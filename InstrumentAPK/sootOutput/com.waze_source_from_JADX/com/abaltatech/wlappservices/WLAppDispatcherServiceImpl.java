package com.abaltatech.wlappservices;

import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;
import com.abaltatech.mcp.weblink.core.DataBuffer;
import com.abaltatech.weblink.service.interfaces.IWLAppDispatcherService.Stub;
import com.abaltatech.weblink.service.interfaces.IWLAppServiceCommandReceiver;
import java.util.HashMap;

public class WLAppDispatcherServiceImpl extends Stub {
    private static final String TAG = "WLAppDispatcherServiceImpl";
    private static WLAppDispatcherServiceImpl ms_instance;
    HashMap<String, IWLAppServiceCommandReceiver> m_receivers = new HashMap();
    private ServiceDispatcher m_serviceDispatcher;

    private class ExecutionTask extends AsyncTask<Integer, Integer, Long> {
        private WLServiceCommand m_command;

        ExecutionTask(WLServiceCommand $r2) throws  {
            this.m_command = $r2;
        }

        protected Long doInBackground(Integer... params) throws  {
            WLAppDispatcherServiceImpl.this.m_serviceDispatcher.processWLServiceCommand(this.m_command);
            return null;
        }
    }

    public static synchronized WLAppDispatcherServiceImpl instance() throws  {
        Class cls = WLAppDispatcherServiceImpl.class;
        synchronized (cls) {
            try {
                if (ms_instance == null) {
                    ms_instance = new WLAppDispatcherServiceImpl();
                }
                WLAppDispatcherServiceImpl $r0 = ms_instance;
                return $r0;
            } finally {
                cls = WLAppDispatcherServiceImpl.class;
            }
        }
    }

    private WLAppDispatcherServiceImpl() throws  {
    }

    public boolean registerCommandReceiver(IWLAppServiceCommandReceiver $r1, String $r2) throws RemoteException {
        if ($r2 == null || $r2.trim().isEmpty()) {
            throw new RemoteException("appID cannot be null or empty");
        } else if ($r1 == null) {
            throw new RemoteException("receiver cannot be null");
        } else {
            $r2 = $r2.trim();
            if ($r2.compareToIgnoreCase(ServiceDispatcher.MasterServiceAppID) == 0) {
                throw new RemoteException("invalid app id");
            }
            synchronized (this.m_receivers) {
                this.m_receivers.put($r2, $r1);
            }
            return true;
        }
    }

    public void sendCommand(String senderAppID, byte[] $r2) throws RemoteException {
        new ExecutionTask(new WLServiceCommand(new DataBuffer($r2, 0, $r2.length))).execute(new Integer[]{Integer.valueOf(0)});
    }

    public void setServiceDispatcher(ServiceDispatcher $r1) throws  {
        this.m_serviceDispatcher = $r1;
    }

    boolean sendRemoteCommand(WLServiceCommand $r1) throws  {
        String $r3 = $r1.getReceiverID();
        synchronized (this.m_receivers) {
            IWLAppServiceCommandReceiver $r7 = (IWLAppServiceCommandReceiver) this.m_receivers.get($r3);
        }
        if ($r7 != null) {
            try {
                $r7.onCommandReceived($r1.getRawCommandData().getData());
                return true;
            } catch (RemoteException $r2) {
                Log.e(TAG, "Handler could not receive command!", $r2);
                return false;
            }
        }
        Log.e(TAG, "No receiver found!");
        return false;
    }
}
