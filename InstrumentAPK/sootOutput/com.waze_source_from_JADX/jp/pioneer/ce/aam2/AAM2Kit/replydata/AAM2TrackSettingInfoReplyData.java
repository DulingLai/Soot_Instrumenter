package jp.pioneer.ce.aam2.AAM2Kit.replydata;

public class AAM2TrackSettingInfoReplyData extends C3386b {
    private boolean f323a = false;
    private boolean f324b = true;
    private boolean f325c = true;
    private boolean f326d = true;
    private boolean f327e = true;
    private boolean f328f = false;
    private boolean f329g = false;
    private boolean f330h = true;
    private boolean f331i = false;

    public AAM2TrackSettingInfoReplyData() {
        super(1);
    }

    public boolean m430a() {
        return this.f323a;
    }

    public boolean m431b() {
        return this.f324b;
    }

    public boolean m432c() {
        return this.f325c;
    }

    public boolean m433e() {
        return this.f326d;
    }

    public boolean m434f() {
        return this.f327e;
    }

    public boolean m435g() {
        return this.f328f;
    }

    public boolean m436h() {
        return this.f329g;
    }

    public boolean m437i() {
        return this.f330h;
    }

    public boolean m438j() {
        return this.f331i;
    }

    public void setHasAlbumTitle(boolean z) {
        this.f327e = z;
    }

    public void setHasArtistName(boolean z) {
        this.f326d = z;
    }

    public void setHasElapsedTime(boolean z) {
        this.f330h = z;
    }

    public void setHasRatingValue(boolean z) {
        this.f331i = z;
    }

    public void setHasTrackInformation(boolean z) {
        this.f324b = z;
    }

    public void setHasTrackTitle(boolean z) {
        this.f325c = z;
    }
}
