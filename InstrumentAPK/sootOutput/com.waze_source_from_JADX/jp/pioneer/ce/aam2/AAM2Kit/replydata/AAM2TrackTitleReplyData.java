package jp.pioneer.ce.aam2.AAM2Kit.replydata;

public class AAM2TrackTitleReplyData extends AAM2TrackInfoReplyDataBase {
    private String f332a;

    public AAM2TrackTitleReplyData(long j) {
        super(3, j);
    }

    public String m439a() {
        return this.f332a;
    }

    public void setTrackTitle(String str) {
        this.f332a = str;
    }
}
