package jp.pioneer.ce.aam2.AAM2Kit;

public interface IAAM2RequiredListener {
    void onAAM2CertifiedResult(boolean z);

    void onAAM2ReceiveDriveStopping(boolean z);

    void onAAM2ReceiveParkingSwitch(boolean z);

    AAM2CertifiedInfo onAAM2RequireCertification();

    void onAAM2StartAdvancedAppMode(AAM2MainUnitSpecInfo aAM2MainUnitSpecInfo);

    void onAAM2StopAdvancedAppMode();
}
