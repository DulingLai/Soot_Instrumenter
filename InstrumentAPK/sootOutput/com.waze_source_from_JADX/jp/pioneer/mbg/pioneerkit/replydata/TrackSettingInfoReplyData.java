package jp.pioneer.mbg.pioneerkit.replydata;

public class TrackSettingInfoReplyData extends C3439b {
    private boolean f481a = false;
    private boolean f482b = true;
    private boolean f483c = true;
    private boolean f484d = true;
    private boolean f485e = true;
    private boolean f486f = false;
    private boolean f487g = false;
    private boolean f488h = true;
    private boolean f489i = false;

    public TrackSettingInfoReplyData() {
        super(1);
    }

    public boolean m782b() {
        return this.f481a;
    }

    public boolean m783c() {
        return this.f486f;
    }

    public boolean m784d() {
        return this.f487g;
    }

    public boolean m785e() {
        return this.f489i;
    }

    public boolean hasAlbumTitle() {
        return this.f485e;
    }

    public boolean hasArtistName() {
        return this.f484d;
    }

    public boolean hasElapsedTime() {
        return this.f488h;
    }

    public boolean hasTrackInformation() {
        return this.f482b;
    }

    public boolean hasTrackTitle() {
        return this.f483c;
    }

    public void setAutoNotification(boolean z) {
        this.f481a = z;
    }

    public void setHasAlbumTitle(boolean z) {
        this.f485e = z;
    }

    public void setHasArtistName(boolean z) {
        this.f484d = z;
    }

    public void setHasArtwork(boolean z) {
        this.f487g = z;
    }

    public void setHasElapsedTime(boolean z) {
        this.f488h = z;
    }

    public void setHasGenreName(boolean z) {
        this.f486f = z;
    }

    public void setHasRatingValue(boolean z) {
        this.f489i = z;
    }

    public void setHasTrackInformation(boolean z) {
        this.f482b = z;
    }

    public void setHasTrackTitle(boolean z) {
        this.f483c = z;
    }
}
