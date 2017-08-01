package jp.pioneer.ce.aam2.AAM2Kit_Lib;

import jp.pioneer.ce.aam2.AAM2Kit.AAM2LocationInfo;
import jp.pioneer.ce.aam2.AAM2Kit.IAAM2LocationListener;
import jp.pioneer.mbg.pioneerkit.ExtLocation;
import jp.pioneer.mbg.pioneerkit.IExtLocationListener;

class PioneerKitWrapper$wILocationListener implements IAAM2LocationListener, IExtLocationListener {
    IExtLocationListener m_listener;

    PioneerKitWrapper$wILocationListener(IExtLocationListener listener) {
        this.m_listener = listener;
    }

    public void onAAM2ReceiveLocationInfo(AAM2LocationInfo arg0) {
        if (PioneerKitWrapper.access$000() == 2) {
            this.m_listener.onReceiveLocationInfo(PioneerKitWrapper.access$300(arg0));
        }
    }

    public void onReceiveLocationInfo(ExtLocation arg0) {
        if (PioneerKitWrapper.access$000() == 0) {
            this.m_listener.onReceiveLocationInfo(arg0);
        }
    }
}
