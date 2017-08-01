package jp.pioneer.ce.aam2.AAM2Kit.replydata;

public class AAM2TrackElapsedTimeNotificationData extends AAM2TrackInfoReplyDataBase {
    private int f320a;

    public AAM2TrackElapsedTimeNotificationData(long j) {
        super(6, j);
    }

    public int m427a() {
        return this.f320a;
    }

    public void setElapsedTime(int i) {
        this.f320a = i;
    }
}
