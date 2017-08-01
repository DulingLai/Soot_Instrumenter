package jp.pioneer.ce.aam2.AAM2Kit_Lib;

import jp.pioneer.ce.aam2.AAM2Kit.IAAM2MediaInfoReqListener;
import jp.pioneer.mbg.pioneerkit.IExtMediaInfoReqListener;

class PioneerKitWrapper$wIMediaInfoReqListener implements IAAM2MediaInfoReqListener, IExtMediaInfoReqListener {
    IExtMediaInfoReqListener m_listener;

    PioneerKitWrapper$wIMediaInfoReqListener(IExtMediaInfoReqListener listener) {
        this.m_listener = listener;
    }

    public void onAAM2ReceiveTrackInfoSettingRequest() {
        if (PioneerKitWrapper.access$000() == 2) {
            this.m_listener.onReceiveTrackInfoSettingRequest();
        }
    }

    public void onAAM2ReceiveTrackInfoRequest(int type, long token) {
        if (PioneerKitWrapper.access$000() == 2) {
            this.m_listener.onReceiveTrackInfoRequest(type, token);
        }
    }

    public void onReceiveTrackInfoSettingRequest() {
        if (PioneerKitWrapper.access$000() == 0) {
            this.m_listener.onReceiveTrackInfoSettingRequest();
        }
    }

    public void onReceiveTrackInfoRequest(int type, long token) {
        if (PioneerKitWrapper.access$000() == 0) {
            this.m_listener.onReceiveTrackInfoRequest(type, token);
        }
    }
}
