package android.support.v4.media;

import android.content.Intent;
import android.media.MediaDescription.Builder;
import android.media.browse.MediaBrowser.MediaItem;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

class MediaBrowserServiceCompatApi21 {

    public interface ServiceImplApi21 {
        void addSubscription(String str, ServiceCallbacks serviceCallbacks) throws ;

        void connect(String str, Bundle bundle, ServiceCallbacks serviceCallbacks) throws ;

        void disconnect(ServiceCallbacks serviceCallbacks) throws ;

        void removeSubscription(String str, ServiceCallbacks serviceCallbacks) throws ;
    }

    static class MediaBrowserServiceAdaptorApi21 {
        ServiceBinderProxyApi21 mBinder;

        static class ServiceBinderProxyApi21 extends Stub {
            final ServiceImplApi21 mServiceImpl;

            ServiceBinderProxyApi21(ServiceImplApi21 $r1) throws  {
                this.mServiceImpl = $r1;
            }

            public void connect(String $r1, Bundle $r2, Object $r3) throws  {
                this.mServiceImpl.connect($r1, $r2, new ServiceCallbacksApi21($r3));
            }

            public void disconnect(Object $r1) throws  {
                this.mServiceImpl.disconnect(new ServiceCallbacksApi21($r1));
            }

            public void addSubscription(String $r1, Object $r2) throws  {
                this.mServiceImpl.addSubscription($r1, new ServiceCallbacksApi21($r2));
            }

            public void removeSubscription(String $r1, Object $r2) throws  {
                this.mServiceImpl.removeSubscription($r1, new ServiceCallbacksApi21($r2));
            }

            public void getMediaItem(String mediaId, ResultReceiver receiver) throws  {
            }
        }

        MediaBrowserServiceAdaptorApi21() throws  {
        }

        public void onCreate(ServiceImplApi21 $r1) throws  {
            this.mBinder = new ServiceBinderProxyApi21($r1);
        }

        public IBinder onBind(Intent $r1) throws  {
            if (MediaBrowserServiceCompat.SERVICE_INTERFACE.equals($r1.getAction())) {
                return this.mBinder;
            }
            return null;
        }
    }

    public interface ServiceCallbacks {
        IBinder asBinder() throws ;

        void onConnect(String str, Object obj, Bundle bundle) throws RemoteException;

        void onConnectFailed() throws RemoteException;

        void onLoadChildren(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/os/Parcel;", ">;)V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/os/Parcel;", ">;)V"}) List<Parcel> list) throws RemoteException;
    }

    public static class ServiceCallbacksApi21 implements ServiceCallbacks {
        private static Object sNullParceledListSliceObj;
        private final IMediaBrowserServiceCallbacksAdapterApi21 mCallbacks;

        static {
            MediaItem $r0 = new MediaItem(new Builder().setMediaId("android.support.v4.media.MediaBrowserCompat.NULL_MEDIA_ITEM").build(), 0);
            ArrayList $r1 = new ArrayList();
            $r1.add($r0);
            sNullParceledListSliceObj = ParceledListSliceAdapterApi21.newInstance($r1);
        }

        ServiceCallbacksApi21(Object $r1) throws  {
            this.mCallbacks = new IMediaBrowserServiceCallbacksAdapterApi21($r1);
        }

        public IBinder asBinder() throws  {
            return this.mCallbacks.asBinder();
        }

        public void onConnect(String $r1, Object $r2, Bundle $r3) throws RemoteException {
            this.mCallbacks.onConnect($r1, $r2, $r3);
        }

        public void onConnectFailed() throws RemoteException {
            this.mCallbacks.onConnectFailed();
        }

        public void onLoadChildren(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/os/Parcel;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/os/Parcel;", ">;)V"}) List<Parcel> $r2) throws RemoteException {
            Object $r5;
            ArrayList $r3 = null;
            if ($r2 != null) {
                $r3 = new ArrayList();
                for (Parcel $r6 : $r2) {
                    $r6.setDataPosition(0);
                    $r3.add(MediaItem.CREATOR.createFromParcel($r6));
                    $r6.recycle();
                }
            }
            if (VERSION.SDK_INT > 23) {
                if ($r3 == null) {
                    $r5 = null;
                } else {
                    $r5 = ParceledListSliceAdapterApi21.newInstance($r3);
                }
            } else if ($r3 == null) {
                $r5 = sNullParceledListSliceObj;
            } else {
                $r5 = ParceledListSliceAdapterApi21.newInstance($r3);
            }
            this.mCallbacks.onLoadChildren($r1, $r5);
        }
    }

    MediaBrowserServiceCompatApi21() throws  {
    }

    public static Object createService() throws  {
        return new MediaBrowserServiceAdaptorApi21();
    }

    public static void onCreate(Object $r1, ServiceImplApi21 $r0) throws  {
        ((MediaBrowserServiceAdaptorApi21) $r1).onCreate($r0);
    }

    public static IBinder onBind(Object $r2, Intent $r0) throws  {
        return ((MediaBrowserServiceAdaptorApi21) $r2).onBind($r0);
    }
}
