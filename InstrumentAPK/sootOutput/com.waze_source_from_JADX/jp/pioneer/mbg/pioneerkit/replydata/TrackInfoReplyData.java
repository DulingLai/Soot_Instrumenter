package jp.pioneer.mbg.pioneerkit.replydata;

public class TrackInfoReplyData extends TrackInfoReplyDataBase {
    private int f479a;
    private int f480b;

    public TrackInfoReplyData(long j) {
        super(2, j);
    }

    public int getDurationTime() {
        return this.f480b;
    }

    public int getTrackNumber() {
        return this.f479a;
    }

    public void setDurationTime(int i) {
        this.f480b = i;
    }

    public void setTrackNumber(int i) {
        this.f479a = i;
    }
}
