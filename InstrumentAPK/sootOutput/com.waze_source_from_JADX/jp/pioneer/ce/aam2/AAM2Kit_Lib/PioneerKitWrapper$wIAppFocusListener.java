package jp.pioneer.ce.aam2.AAM2Kit_Lib;

import jp.pioneer.ce.aam2.AAM2Kit.IAAM2AppFocusListener;
import jp.pioneer.mbg.pioneerkit.IExtAppFocusListener;

class PioneerKitWrapper$wIAppFocusListener implements IAAM2AppFocusListener, IExtAppFocusListener {
    IExtAppFocusListener m_listener;

    PioneerKitWrapper$wIAppFocusListener(IExtAppFocusListener listener) {
        this.m_listener = listener;
    }

    public void onAAM2StartFocus() {
        if (PioneerKitWrapper.access$000() == 2) {
            this.m_listener.onStartFocus();
        }
    }

    public void onAAM2StopFocus() {
        if (PioneerKitWrapper.access$000() == 2) {
            this.m_listener.onStopFocus();
        }
    }

    public void onStartFocus() {
        if (PioneerKitWrapper.access$000() == 0) {
            this.m_listener.onStartFocus();
        }
    }

    public void onStopFocus() {
        if (PioneerKitWrapper.access$000() == 0) {
            this.m_listener.onStopFocus();
        }
    }
}
