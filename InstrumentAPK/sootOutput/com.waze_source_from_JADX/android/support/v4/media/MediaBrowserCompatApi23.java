package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.media.browse.MediaBrowser.MediaItem;
import android.os.Parcel;
import android.support.annotation.NonNull;
import dalvik.annotation.Signature;

class MediaBrowserCompatApi23 {

    interface ItemCallback {
        void onError(@NonNull String str) throws ;

        void onItemLoaded(Parcel parcel) throws ;
    }

    static class ItemCallbackProxy<T extends ItemCallback> extends android.media.browse.MediaBrowser.ItemCallback {
        protected final T mItemCallback;

        public ItemCallbackProxy(@Signature({"(TT;)V"}) T $r1) throws  {
            this.mItemCallback = $r1;
        }

        public void onItemLoaded(MediaItem $r1) throws  {
            Parcel $r2 = Parcel.obtain();
            $r1.writeToParcel($r2, 0);
            this.mItemCallback.onItemLoaded($r2);
        }

        public void onError(@NonNull String $r1) throws  {
            this.mItemCallback.onError($r1);
        }
    }

    MediaBrowserCompatApi23() throws  {
    }

    public static Object createItemCallback(ItemCallback $r0) throws  {
        return new ItemCallbackProxy($r0);
    }

    public static void getItem(Object $r1, String $r0, Object $r2) throws  {
        ((MediaBrowser) $r1).getItem($r0, (android.media.browse.MediaBrowser.ItemCallback) $r2);
    }
}
