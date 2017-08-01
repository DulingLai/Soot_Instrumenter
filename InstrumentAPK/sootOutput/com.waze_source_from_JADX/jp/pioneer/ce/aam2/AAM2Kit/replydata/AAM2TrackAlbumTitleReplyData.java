package jp.pioneer.ce.aam2.AAM2Kit.replydata;

public class AAM2TrackAlbumTitleReplyData extends AAM2TrackInfoReplyDataBase {
    private String f318a;

    public AAM2TrackAlbumTitleReplyData(long j) {
        super(5, j);
    }

    public String m425a() {
        return this.f318a;
    }

    public void setAlbumTitle(String str) {
        this.f318a = str;
    }
}
