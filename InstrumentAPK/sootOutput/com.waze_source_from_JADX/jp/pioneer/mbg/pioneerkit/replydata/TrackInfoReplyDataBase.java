package jp.pioneer.mbg.pioneerkit.replydata;

public class TrackInfoReplyDataBase extends C3439b {
    private long f475a;

    public TrackInfoReplyDataBase(int i, long j) {
        super(i);
        setTrackToken(j);
    }

    public long getTrackToken() {
        return this.f475a;
    }

    public void setTrackToken(long j) {
        this.f475a = j;
    }
}
