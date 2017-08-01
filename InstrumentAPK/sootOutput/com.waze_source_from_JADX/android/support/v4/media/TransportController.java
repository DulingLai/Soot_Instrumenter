package android.support.v4.media;

public abstract class TransportController {
    public abstract int getBufferPercentage() throws ;

    public abstract long getCurrentPosition() throws ;

    public abstract long getDuration() throws ;

    public abstract int getTransportControlFlags() throws ;

    public abstract boolean isPlaying() throws ;

    public abstract void pausePlaying() throws ;

    public abstract void registerStateListener(TransportStateListener transportStateListener) throws ;

    public abstract void seekTo(long j) throws ;

    public abstract void startPlaying() throws ;

    public abstract void stopPlaying() throws ;

    public abstract void unregisterStateListener(TransportStateListener transportStateListener) throws ;
}
