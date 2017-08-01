package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.media.browse.MediaBrowser.MediaItem;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

class MediaBrowserCompatApi21 {
    static final String NULL_MEDIA_ITEM_ID = "android.support.v4.media.MediaBrowserCompat.NULL_MEDIA_ITEM";

    interface ConnectionCallback {
        void onConnected() throws ;

        void onConnectionFailed() throws ;

        void onConnectionSuspended() throws ;
    }

    interface SubscriptionCallback {
        void onChildrenLoaded(@NonNull @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/os/Parcel;", ">;)V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/os/Parcel;", ">;)V"}) List<Parcel> list) throws ;

        void onError(@NonNull String str) throws ;
    }

    static class ConnectionCallbackProxy<T extends ConnectionCallback> extends android.media.browse.MediaBrowser.ConnectionCallback {
        protected final T mConnectionCallback;

        public ConnectionCallbackProxy(@Signature({"(TT;)V"}) T $r1) throws  {
            this.mConnectionCallback = $r1;
        }

        public void onConnected() throws  {
            this.mConnectionCallback.onConnected();
        }

        public void onConnectionSuspended() throws  {
            this.mConnectionCallback.onConnectionSuspended();
        }

        public void onConnectionFailed() throws  {
            this.mConnectionCallback.onConnectionFailed();
        }
    }

    static class SubscriptionCallbackProxy<T extends SubscriptionCallback> extends android.media.browse.MediaBrowser.SubscriptionCallback {
        protected final T mSubscriptionCallback;

        public SubscriptionCallbackProxy(@Signature({"(TT;)V"}) T $r1) throws  {
            this.mSubscriptionCallback = $r1;
        }

        public void onChildrenLoaded(@NonNull @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/media/browse/MediaBrowser$MediaItem;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/media/browse/MediaBrowser$MediaItem;", ">;)V"}) List<MediaItem> $r2) throws  {
            ArrayList $r3 = null;
            if ($r2 != null && $r2.size() == 1 && ((MediaItem) $r2.get(0)).getMediaId().equals(MediaBrowserCompatApi21.NULL_MEDIA_ITEM_ID)) {
                List $r22 = null;
            }
            if ($r2 != null) {
                $r3 = new ArrayList();
                for (MediaItem $r5 : $r2) {
                    Parcel $r8 = Parcel.obtain();
                    $r5.writeToParcel($r8, 0);
                    $r3.add($r8);
                }
            }
            this.mSubscriptionCallback.onChildrenLoaded($r1, $r3);
        }

        public void onError(@NonNull String $r1) throws  {
            this.mSubscriptionCallback.onError($r1);
        }
    }

    MediaBrowserCompatApi21() throws  {
    }

    public static Object createConnectionCallback(ConnectionCallback $r0) throws  {
        return new ConnectionCallbackProxy($r0);
    }

    public static Object createBrowser(Context $r0, ComponentName $r1, Object $r4, Bundle $r2) throws  {
        return new MediaBrowser($r0, $r1, (android.media.browse.MediaBrowser.ConnectionCallback) $r4, $r2);
    }

    public static void connect(Object $r0) throws  {
        ((MediaBrowser) $r0).connect();
    }

    public static void disconnect(Object $r0) throws  {
        ((MediaBrowser) $r0).disconnect();
    }

    public static boolean isConnected(Object $r0) throws  {
        return ((MediaBrowser) $r0).isConnected();
    }

    public static ComponentName getServiceComponent(Object $r1) throws  {
        return ((MediaBrowser) $r1).getServiceComponent();
    }

    public static String getRoot(Object $r1) throws  {
        return ((MediaBrowser) $r1).getRoot();
    }

    public static Bundle getExtras(Object $r1) throws  {
        return ((MediaBrowser) $r1).getExtras();
    }

    public static Object getSessionToken(Object $r1) throws  {
        return ((MediaBrowser) $r1).getSessionToken();
    }

    public static Object createSubscriptionCallback(SubscriptionCallback $r0) throws  {
        return new SubscriptionCallbackProxy($r0);
    }

    public static void subscribe(Object $r1, String $r0, Object $r2) throws  {
        ((MediaBrowser) $r1).subscribe($r0, (android.media.browse.MediaBrowser.SubscriptionCallback) $r2);
    }

    public static void unsubscribe(Object $r1, String $r0) throws  {
        ((MediaBrowser) $r1).unsubscribe($r0);
    }
}
