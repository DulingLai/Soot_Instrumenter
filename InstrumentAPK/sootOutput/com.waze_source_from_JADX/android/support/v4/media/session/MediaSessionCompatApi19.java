package android.support.v4.media.session;

import android.media.Rating;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.MetadataEditor;
import android.os.Bundle;
import com.waze.share.ShareFbQueries;
import dalvik.annotation.Signature;

class MediaSessionCompatApi19 {
    private static final long ACTION_SET_RATING = 128;
    private static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    private static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    private static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";

    interface Callback extends Callback {
        void onSetRating(Object obj) throws ;
    }

    static class OnMetadataUpdateListener<T extends Callback> implements android.media.RemoteControlClient.OnMetadataUpdateListener {
        protected final T mCallback;

        public OnMetadataUpdateListener(@Signature({"(TT;)V"}) T $r1) throws  {
            this.mCallback = $r1;
        }

        public void onMetadataUpdate(int $i0, Object $r1) throws  {
            if ($i0 == ShareFbQueries.FB_QUERY_RESULT_ERROR_GENERAL && ($r1 instanceof Rating)) {
                this.mCallback.onSetRating($r1);
            }
        }
    }

    MediaSessionCompatApi19() throws  {
    }

    public static void setTransportControlFlags(Object $r0, long $l0) throws  {
        ((RemoteControlClient) $r0).setTransportControlFlags(getRccTransportControlFlagsFromActions($l0));
    }

    public static Object createMetadataUpdateListener(Callback $r0) throws  {
        return new OnMetadataUpdateListener($r0);
    }

    public static void setMetadata(Object $r2, Bundle $r0, long $l0) throws  {
        MetadataEditor $r1 = ((RemoteControlClient) $r2).editMetadata(true);
        MediaSessionCompatApi14.buildOldMetadata($r0, $r1);
        addNewMetadata($r0, $r1);
        if ((128 & $l0) != 0) {
            $r1.addEditableKey(ShareFbQueries.FB_QUERY_RESULT_ERROR_GENERAL);
        }
        $r1.apply();
    }

    public static void setOnMetadataUpdateListener(Object $r0, Object $r1) throws  {
        ((RemoteControlClient) $r0).setMetadataUpdateListener((android.media.RemoteControlClient.OnMetadataUpdateListener) $r1);
    }

    static int getRccTransportControlFlagsFromActions(long $l0) throws  {
        int $i2 = MediaSessionCompatApi18.getRccTransportControlFlagsFromActions($l0);
        if ((128 & $l0) != 0) {
            return $i2 | 512;
        }
        return $i2;
    }

    static void addNewMetadata(Bundle $r0, MetadataEditor $r1) throws  {
        if ($r0 != null) {
            if ($r0.containsKey("android.media.metadata.YEAR")) {
                $r1.putLong(8, $r0.getLong("android.media.metadata.YEAR"));
            }
            if ($r0.containsKey("android.media.metadata.RATING")) {
                $r1.putObject(101, $r0.getParcelable("android.media.metadata.RATING"));
            }
            if ($r0.containsKey("android.media.metadata.USER_RATING")) {
                $r1.putObject(ShareFbQueries.FB_QUERY_RESULT_ERROR_GENERAL, $r0.getParcelable("android.media.metadata.USER_RATING"));
            }
        }
    }
}
