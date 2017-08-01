package jp.pioneer.mbg.pioneerkit;

public interface IExtRequiredListener {
    void onCertifiedResult(boolean z);

    void onReceiveDriveStopping(boolean z);

    void onReceiveParkingInfo(boolean z);

    void onReceiveParkingSwitch(boolean z);

    ExtCertifiedInfo onRequireCertification();

    void onStartAdvancedAppMode(ExtDeviceSpecInfo extDeviceSpecInfo);

    void onStopAdvancedAppMode();
}
