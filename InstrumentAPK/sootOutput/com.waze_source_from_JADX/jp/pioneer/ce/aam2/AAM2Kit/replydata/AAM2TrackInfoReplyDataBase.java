package jp.pioneer.ce.aam2.AAM2Kit.replydata;

public class AAM2TrackInfoReplyDataBase extends C3386b {
    private long f317a;

    public AAM2TrackInfoReplyDataBase(int i, long j) {
        super(i);
        setTrackToken(j);
    }

    public long m424c() {
        return this.f317a;
    }

    public void setTrackToken(long j) {
        this.f317a = j;
    }
}
