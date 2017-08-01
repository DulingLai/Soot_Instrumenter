package jp.pioneer.ce.aam2.AAM2Kit_Lib;

import jp.pioneer.ce.aam2.AAM2Kit.IAAM2RemoteCtrlListener;
import jp.pioneer.mbg.pioneerkit.IExtRemoteCtrlListener;

class PioneerKitWrapper$wIRemoteCtrlListener implements IAAM2RemoteCtrlListener, IExtRemoteCtrlListener {
    IExtRemoteCtrlListener m_listener;

    PioneerKitWrapper$wIRemoteCtrlListener(IExtRemoteCtrlListener listener) {
        this.m_listener = listener;
    }

    public void onAAM2ReceiveRemoteCtrl(int arg0) {
        if (PioneerKitWrapper.access$000() == 2) {
            this.m_listener.onReceiveRemoteCtrl(arg0);
        }
    }

    public void onReceiveRemoteCtrl(int arg0) {
        if (PioneerKitWrapper.access$000() == 0) {
            this.m_listener.onReceiveRemoteCtrl(arg0);
        }
    }
}
