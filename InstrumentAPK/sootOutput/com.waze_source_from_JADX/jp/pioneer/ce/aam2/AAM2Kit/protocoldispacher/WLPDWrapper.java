package jp.pioneer.ce.aam2.AAM2Kit.protocoldispacher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import com.abaltatech.protocoldispatcher.IPProtocolDispatcher;
import com.abaltatech.protocoldispatcher.IPProtocolDispatcher.Stub;
import com.abaltatech.protocoldispatcher.IPProtocolDispatcherNotification;
import com.abaltatech.weblinkserver.WLServer;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.C3347a;
import jp.pioneer.ce.aam2.AAM2Kit.p004c.C3354a;

public class WLPDWrapper {
    private static final String INTENT_ACTION_BIND_PROTOCOL_DISPATCHER_SERVICE = "abaltatech.intent.action.bindProtocolDispatcherService";
    private boolean mBindService = false;
    private final IPProtocolDispatcherNotification m_notificationCallback = new C33852();
    private IPProtocolDispatcher m_protocolDispatcherService;
    private ServiceConnection m_serviceConnection = new C33841();

    class C33841 implements ServiceConnection {
        C33841() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            C3347a.m289a("The PD service is now connected!");
            try {
                WLPDWrapper.this.m_protocolDispatcherService = Stub.asInterface(iBinder);
                WLPDWrapper.this.m_protocolDispatcherService.registerNotificationListener(WLPDWrapper.this.m_notificationCallback, Process.myPid());
                WLPDWrapper.this.mBindService = true;
            } catch (RemoteException e) {
                C3347a.m289a("An error occured during the call" + e);
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            C3347a.m289a("The connection to the PD service was closed !");
            try {
                WLPDWrapper.this.m_protocolDispatcherService.unregisterNotificationListener(WLPDWrapper.this.m_notificationCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            WLPDWrapper.this.m_protocolDispatcherService = null;
            WLPDWrapper.this.mBindService = false;
            ComponentName componentName2 = new ComponentName("jp.pioneer.mbg.appradio.AppRadioLauncher", "com.abaltatech.protocoldispatcher.ProtocolDispatcherService");
            Intent intent = new Intent(WLPDWrapper.INTENT_ACTION_BIND_PROTOCOL_DISPATCHER_SERVICE);
            intent.setComponent(componentName2);
            Context context = C3354a.m316b().f246e;
            if (context != null) {
                context.bindService(intent, WLPDWrapper.this.m_serviceConnection, 65);
            }
        }
    }

    class C33852 extends IPProtocolDispatcherNotification.Stub {
        C33852() {
        }

        public void onApplicationActivated(int i) {
            int listenPort = WLServer.getInstance().getListenPort();
            C3347a.m289a("onApplicationActivated - Notification from Service, sessionID: " + i + ", port=" + listenPort);
            WLPDWrapper.this.m_protocolDispatcherService.setPortNumber(Process.myPid(), i, listenPort);
        }

        public void onApplicationDeactivated() {
            C3347a.m289a("onApplicationDeactivated - Notification from Service");
        }

        public void onApplicationPaused() {
            C3347a.m289a("onApplicationPaused - Notification from Service");
        }

        public void onApplicationResumed() {
            C3347a.m289a("onApplicationResumed - Notification from Service");
        }

        public void onLoggingChanged(boolean z) {
            WLServer.getInstance().setLoggingEnabled(z);
            C3347a.m289a("onLoggingChanged - Notification from Service");
        }

        public void onVideoChannelClosed() {
            C3347a.m289a("onVideoChannelClosed - Notification from Service");
        }

        public void onVideoChannelReady() {
            C3347a.m289a("onVideoChannelReady - Notification from Service");
        }
    }

    public void connectProtocolDispatcher() {
        boolean z = false;
        ComponentName componentName;
        Intent intent;
        Context context;
        if (this.m_protocolDispatcherService == null) {
            componentName = new ComponentName("jp.pioneer.mbg.appradio.AppRadioLauncher", "com.abaltatech.protocoldispatcher.ProtocolDispatcherService");
            intent = new Intent(INTENT_ACTION_BIND_PROTOCOL_DISPATCHER_SERVICE);
            intent.setComponent(componentName);
            context = C3354a.m316b().f246e;
            if (context != null) {
                z = context.bindService(intent, this.m_serviceConnection, 65);
            }
            C3347a.m289a("isSuccess: " + z);
            C3347a.m289a("The Service will be connected soon (asynchronus call)!");
        } else if (!this.m_protocolDispatcherService.asBinder().pingBinder()) {
            componentName = new ComponentName("jp.pioneer.mbg.appradio.AppRadioLauncher", "com.abaltatech.protocoldispatcher.ProtocolDispatcherService");
            intent = new Intent(INTENT_ACTION_BIND_PROTOCOL_DISPATCHER_SERVICE);
            intent.setComponent(componentName);
            context = C3354a.m316b().f246e;
            if (context != null) {
                z = context.bindService(intent, this.m_serviceConnection, 65);
            }
            C3347a.m289a("DispatcherService.asBinder().pingBinder isSuccess: " + z);
            C3347a.m289a("DispatcherService.asBinder().pingBinder The Service will be connected soon (asynchronus call)!");
        }
    }

    public void disconnectProtocolDispatcher() {
        if (this.m_protocolDispatcherService != null && this.mBindService) {
            try {
                this.m_protocolDispatcherService.unregisterNotificationListener(this.m_notificationCallback);
            } catch (RemoteException e) {
                C3347a.m289a("onAn error occured during the call" + e);
            }
            this.m_protocolDispatcherService = null;
            Context context = C3354a.m316b().f246e;
            if (context != null) {
                context.unbindService(this.m_serviceConnection);
            }
            this.mBindService = false;
        }
    }
}
