package jp.pioneer.ce.aam2.AAM2Kit;

public interface IAAM2MediaInfoReqListener {
    void onAAM2ReceiveTrackInfoRequest(int i, long j);

    void onAAM2ReceiveTrackInfoSettingRequest();
}
