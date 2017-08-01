package jp.pioneer.mbg.pioneerkit.replydata;

public class TrackAlbumTitleReplyData extends TrackInfoReplyDataBase {
    private String f476a;

    public TrackAlbumTitleReplyData(long j) {
        super(5, j);
    }

    public String getAlbumTitle() {
        return this.f476a;
    }

    public void setAlbumTitle(String str) {
        this.f476a = str;
    }
}
