package jp.pioneer.ce.aam2.AAM2Kit.replydata;

public class AAM2TrackInfoReplyData extends AAM2TrackInfoReplyDataBase {
    private int f321a;
    private int f322b;

    public AAM2TrackInfoReplyData(long j) {
        super(2, j);
    }

    public int m428a() {
        return this.f321a;
    }

    public int m429b() {
        return this.f322b;
    }

    public void setDurationTime(int i) {
        this.f322b = i;
    }

    public void setTrackNumber(int i) {
        this.f321a = i;
    }
}
