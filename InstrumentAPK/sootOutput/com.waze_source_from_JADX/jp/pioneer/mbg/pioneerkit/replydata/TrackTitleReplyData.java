package jp.pioneer.mbg.pioneerkit.replydata;

public class TrackTitleReplyData extends TrackInfoReplyDataBase {
    private String f490a;

    public TrackTitleReplyData(long j) {
        super(3, j);
    }

    public String getTrackTitle() {
        return this.f490a;
    }

    public void setTrackTitle(String str) {
        this.f490a = str;
    }
}
