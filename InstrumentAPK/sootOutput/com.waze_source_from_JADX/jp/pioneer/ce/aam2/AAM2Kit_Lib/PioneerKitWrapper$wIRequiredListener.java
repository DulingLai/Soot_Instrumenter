package jp.pioneer.ce.aam2.AAM2Kit_Lib;

import jp.pioneer.ce.aam2.AAM2Kit.AAM2CertifiedInfo;
import jp.pioneer.ce.aam2.AAM2Kit.AAM2MainUnitSpecInfo;
import jp.pioneer.ce.aam2.AAM2Kit.IAAM2RequiredListener;
import jp.pioneer.mbg.pioneerkit.ExtCertifiedInfo;
import jp.pioneer.mbg.pioneerkit.ExtDeviceSpecInfo;
import jp.pioneer.mbg.pioneerkit.IExtRequiredListener;

class PioneerKitWrapper$wIRequiredListener implements IAAM2RequiredListener, IExtRequiredListener {
    IExtRequiredListener m_listener;

    PioneerKitWrapper$wIRequiredListener(IExtRequiredListener listener) {
        this.m_listener = listener;
    }

    public void onAAM2CertifiedResult(boolean arg0) {
        this.m_listener.onCertifiedResult(arg0);
    }

    public void onAAM2ReceiveDriveStopping(boolean arg0) {
        if (PioneerKitWrapper.access$000() != 0) {
            this.m_listener.onReceiveDriveStopping(arg0);
            this.m_listener.onReceiveParkingInfo(arg0);
        }
    }

    public void onAAM2ReceiveParkingSwitch(boolean arg0) {
        if (PioneerKitWrapper.access$000() != 0) {
            this.m_listener.onReceiveParkingSwitch(arg0);
        }
    }

    public AAM2CertifiedInfo onAAM2RequireCertification() {
        ExtCertifiedInfo info_AAM1 = this.m_listener.onRequireCertification();
        if (info_AAM1 != null) {
            return PioneerKitWrapper.access$100(info_AAM1);
        }
        return null;
    }

    public void onAAM2StartAdvancedAppMode(AAM2MainUnitSpecInfo arg0) {
        if (PioneerKitWrapper.access$000() == -1) {
            PioneerKitWrapper.access$002(2);
            this.m_listener.onStartAdvancedAppMode(PioneerKitWrapper.access$200(arg0));
        }
    }

    public void onAAM2StopAdvancedAppMode() {
        if (PioneerKitWrapper.access$000() == 2) {
            PioneerKitWrapper.access$002(-1);
            this.m_listener.onStopAdvancedAppMode();
        }
    }

    public void onCertifiedResult(boolean arg0) {
        this.m_listener.onCertifiedResult(arg0);
    }

    public void onReceiveDriveStopping(boolean arg0) {
        if (PioneerKitWrapper.access$000() != 2) {
            this.m_listener.onReceiveDriveStopping(arg0);
        }
    }

    public void onReceiveParkingInfo(boolean arg0) {
        if (PioneerKitWrapper.access$000() != 2) {
            this.m_listener.onReceiveParkingInfo(arg0);
        }
    }

    public void onReceiveParkingSwitch(boolean arg0) {
        if (PioneerKitWrapper.access$000() != 2) {
            this.m_listener.onReceiveParkingSwitch(arg0);
        }
    }

    public ExtCertifiedInfo onRequireCertification() {
        return this.m_listener.onRequireCertification();
    }

    public void onStartAdvancedAppMode(ExtDeviceSpecInfo arg0) {
        if (PioneerKitWrapper.access$000() == -1) {
            PioneerKitWrapper.access$002(0);
            this.m_listener.onStartAdvancedAppMode(arg0);
        }
    }

    public void onStopAdvancedAppMode() {
        if (PioneerKitWrapper.access$000() == 0) {
            PioneerKitWrapper.access$002(-1);
            this.m_listener.onStopAdvancedAppMode();
        }
    }
}
