package jp.pioneer.mbg.pioneerkit.replydata;

public class TrackElapsedTimeNotificationData extends TrackInfoReplyDataBase {
    private int f478a;

    public TrackElapsedTimeNotificationData(long j) {
        super(6, j);
    }

    public int getElapsedTime() {
        return this.f478a;
    }

    public void setElapsedTime(int i) {
        this.f478a = i;
    }
}
