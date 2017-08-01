package jp.pioneer.ce.aam2.AAM2Kit.replydata;

public class AAM2TrackArtistNameReplyData extends AAM2TrackInfoReplyDataBase {
    private String f319a;

    public AAM2TrackArtistNameReplyData(long j) {
        super(4, j);
    }

    public String m426a() {
        return this.f319a;
    }

    public void setArtistName(String str) {
        this.f319a = str;
    }
}
