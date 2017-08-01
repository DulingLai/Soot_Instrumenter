package jp.pioneer.mbg.pioneerkit.replydata;

public class TrackArtistNameReplyData extends TrackInfoReplyDataBase {
    private String f477a;

    public TrackArtistNameReplyData(long j) {
        super(4, j);
    }

    public String getArtistName() {
        return this.f477a;
    }

    public void setArtistName(String str) {
        this.f477a = str;
    }
}
