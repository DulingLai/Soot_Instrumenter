package android.support.v4.media;

import android.media.browse.MediaBrowser.MediaItem;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ResultReceiver;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceImplApi21;
import android.util.Log;

class MediaBrowserServiceCompatApi23 extends MediaBrowserServiceCompatApi21 {
    private static final String TAG = "MediaBrowserServiceCompatApi21";

    public interface ServiceImplApi23 extends ServiceImplApi21 {
        void getMediaItem(String str, ItemCallback itemCallback) throws ;
    }

    public interface ItemCallback {
        void onItemLoaded(int i, Bundle bundle, Parcel parcel) throws ;
    }

    static class MediaBrowserServiceAdaptorApi23 extends MediaBrowserServiceAdaptorApi21 {

        private static class ServiceBinderProxyApi23 extends ServiceBinderProxyApi21 {
            ServiceImplApi23 mServiceImpl;

            ServiceBinderProxyApi23(ServiceImplApi23 $r1) throws  {
                super($r1);
                this.mServiceImpl = $r1;
            }

            public void getMediaItem(String $r1, final ResultReceiver $r2) throws  {
                ReflectiveOperationException $r9;
                try {
                    final String $r6 = (String) MediaBrowserService.class.getDeclaredField("KEY_MEDIA_ITEM").get(null);
                    this.mServiceImpl.getMediaItem($r1, new ItemCallback() {
                        public void onItemLoaded(int $i0, Bundle $r1, Parcel $r2) throws  {
                            if ($r2 != null) {
                                $r2.setDataPosition(0);
                                $r1.putParcelable($r6, (MediaItem) MediaItem.CREATOR.createFromParcel($r2));
                                $r2.recycle();
                            }
                            $r2.send($i0, $r1);
                        }
                    });
                } catch (IllegalAccessException e) {
                    $r9 = e;
                    Log.i(MediaBrowserServiceCompatApi23.TAG, "Failed to get KEY_MEDIA_ITEM via reflection", $r9);
                } catch (NoSuchFieldException e2) {
                    $r9 = e2;
                    Log.i(MediaBrowserServiceCompatApi23.TAG, "Failed to get KEY_MEDIA_ITEM via reflection", $r9);
                }
            }
        }

        MediaBrowserServiceAdaptorApi23() throws  {
        }

        public void onCreate(ServiceImplApi23 $r1) throws  {
            this.mBinder = new ServiceBinderProxyApi23($r1);
        }
    }

    MediaBrowserServiceCompatApi23() throws  {
    }

    public static Object createService() throws  {
        return new MediaBrowserServiceAdaptorApi23();
    }

    public static void onCreate(Object $r1, ServiceImplApi23 $r0) throws  {
        ((MediaBrowserServiceAdaptorApi23) $r1).onCreate($r0);
    }
}
