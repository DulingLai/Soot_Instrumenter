package jp.pioneer.ce.aam2.AAM2Kit.protocoldispacher;

import com.abaltatech.weblinkserver.WLServer;
import com.abaltatech.weblinkserver.WLServer.IServerReadyListener;
import com.abaltatech.weblinkserver.WLServerApp;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.C3347a;

public class AAM2PDConnectionUtility implements IServerReadyListener {
    public static final String SERVER_ID = "aam2serverapp://";
    public static final String SERVER_NAME = "AAM2ServerApp";
    public static final int SERVER_PORT = 0;
    public static final String TAG = "AAM2ServerAppTAG";
    private static AAM2PDConnectionUtility mPDConnectionUtility;
    private final WLPDWrapper m_pdWrapper = new WLPDWrapper();

    public AAM2PDConnectionUtility() {
        WLServerApp.setMode(1);
    }

    public static AAM2PDConnectionUtility getInstance() {
        if (mPDConnectionUtility == null) {
            mPDConnectionUtility = new AAM2PDConnectionUtility();
        }
        return mPDConnectionUtility;
    }

    public void connectToPDService() {
        this.m_pdWrapper.connectProtocolDispatcher();
    }

    public void disconnectFromPDService() {
        this.m_pdWrapper.disconnectProtocolDispatcher();
    }

    public void onServerReady() {
        WLServer instance = WLServer.getInstance();
        if (instance != null) {
            instance.setIsBroadcastingEnabled(false);
            instance.setServerReadyListener(null);
            if (instance.start(SERVER_NAME, SERVER_ID, 0, null, null)) {
                this.m_pdWrapper.connectProtocolDispatcher();
            } else {
                C3347a.m289a("server.start fail!!!");
            }
        }
    }

    public void startWLServer() {
        WLServer instance = WLServer.getInstance();
        if (instance == null) {
            return;
        }
        if (instance.isStarted()) {
            this.m_pdWrapper.connectProtocolDispatcher();
        } else {
            instance.setServerReadyListener(this);
        }
    }

    public void stopWLServer() {
        this.m_pdWrapper.disconnectProtocolDispatcher();
        WLServer instance = WLServer.getInstance();
        if (instance != null) {
            instance.setServerReadyListener(null);
            instance.stop();
        }
    }
}
