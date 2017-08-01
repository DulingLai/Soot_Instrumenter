package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import dalvik.annotation.Signature;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public final class MediaBrowserCompat {
    public static final String EXTRA_PAGE = "android.media.browse.extra.PAGE";
    public static final String EXTRA_PAGE_SIZE = "android.media.browse.extra.PAGE_SIZE";
    private static final String TAG = "MediaBrowserCompat";
    private final MediaBrowserImpl mImpl;

    private static class CallbackHandler extends Handler {
        private final MediaBrowserServiceCallbackImpl mCallbackImpl;
        private WeakReference<Messenger> mCallbacksMessengerRef;

        CallbackHandler(MediaBrowserServiceCallbackImpl $r1) throws  {
            this.mCallbackImpl = $r1;
        }

        public void handleMessage(Message $r1) throws  {
            WeakReference $r2 = this.mCallbacksMessengerRef;
            this = this;
            if ($r2 != null) {
                Bundle $r3 = $r1.getData();
                $r3.setClassLoader(MediaSessionCompat.class.getClassLoader());
                MediaBrowserServiceCallbackImpl $r8;
                switch ($r1.what) {
                    case 1:
                        $r8 = this.mCallbackImpl;
                        $r8.onServiceConnected((Messenger) this.mCallbacksMessengerRef.get(), $r3.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (Token) $r3.getParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN), $r3.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS));
                        return;
                    case 2:
                        $r8 = this.mCallbackImpl;
                        $r8.onConnectionFailed((Messenger) this.mCallbacksMessengerRef.get());
                        return;
                    case 3:
                        $r8 = this.mCallbackImpl;
                        $r8.onLoadChildren((Messenger) this.mCallbacksMessengerRef.get(), $r3.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), $r3.getParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST), $r3.getBundle(MediaBrowserProtocol.DATA_OPTIONS));
                        return;
                    default:
                        Log.w(MediaBrowserCompat.TAG, "Unhandled message: " + $r1 + "\n  Client version: " + 1 + "\n  Service version: " + $r1.arg1);
                        return;
                }
            }
        }

        void setCallbacksMessenger(Messenger $r1) throws  {
            this.mCallbacksMessengerRef = new WeakReference($r1);
        }
    }

    public static class ConnectionCallback {
        private ConnectionCallbackInternal mConnectionCallbackInternal;
        final Object mConnectionCallbackObj;

        interface ConnectionCallbackInternal {
            void onConnected() throws ;

            void onConnectionFailed() throws ;

            void onConnectionSuspended() throws ;
        }

        private class StubApi21 implements ConnectionCallback {
            private StubApi21() throws  {
            }

            public void onConnected() throws  {
                if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    ConnectionCallback.this.mConnectionCallbackInternal.onConnected();
                }
                ConnectionCallback.this.onConnected();
            }

            public void onConnectionSuspended() throws  {
                if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    ConnectionCallback.this.mConnectionCallbackInternal.onConnectionSuspended();
                }
                ConnectionCallback.this.onConnectionSuspended();
            }

            public void onConnectionFailed() throws  {
                if (ConnectionCallback.this.mConnectionCallbackInternal != null) {
                    ConnectionCallback.this.mConnectionCallbackInternal.onConnectionFailed();
                }
                ConnectionCallback.this.onConnectionFailed();
            }
        }

        public ConnectionCallback() throws  {
            if (VERSION.SDK_INT >= 21) {
                this.mConnectionCallbackObj = MediaBrowserCompatApi21.createConnectionCallback(new StubApi21());
            } else {
                this.mConnectionCallbackObj = null;
            }
        }

        public void onConnected() throws  {
        }

        public void onConnectionSuspended() throws  {
        }

        public void onConnectionFailed() throws  {
        }

        void setInternalConnectionCallback(ConnectionCallbackInternal $r1) throws  {
            this.mConnectionCallbackInternal = $r1;
        }
    }

    public static abstract class ItemCallback {
        final Object mItemCallbackObj;

        private class StubApi23 implements ItemCallback {
            private StubApi23() throws  {
            }

            public void onItemLoaded(Parcel $r1) throws  {
                $r1.setDataPosition(0);
                MediaItem $r4 = (MediaItem) MediaItem.CREATOR.createFromParcel($r1);
                $r1.recycle();
                ItemCallback.this.onItemLoaded($r4);
            }

            public void onError(@NonNull String $r1) throws  {
                ItemCallback.this.onError($r1);
            }
        }

        public ItemCallback() throws  {
            if (VERSION.SDK_INT >= 23) {
                this.mItemCallbackObj = MediaBrowserCompatApi23.createItemCallback(new StubApi23());
            } else {
                this.mItemCallbackObj = null;
            }
        }

        public void onItemLoaded(MediaItem item) throws  {
        }

        public void onError(@NonNull String itemId) throws  {
        }
    }

    private static class ItemReceiver extends ResultReceiver {
        private final ItemCallback mCallback;
        private final String mMediaId;

        ItemReceiver(String $r1, ItemCallback $r2, Handler $r3) throws  {
            super($r3);
            this.mMediaId = $r1;
            this.mCallback = $r2;
        }

        protected void onReceiveResult(int $i0, Bundle $r1) throws  {
            $r1.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            if ($i0 == 0 && $r1 != null && $r1.containsKey(MediaBrowserServiceCompat.KEY_MEDIA_ITEM)) {
                Parcelable $r6 = $r1.getParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM);
                if ($r6 instanceof MediaItem) {
                    this.mCallback.onItemLoaded((MediaItem) $r6);
                    return;
                } else {
                    this.mCallback.onError(this.mMediaId);
                    return;
                }
            }
            this.mCallback.onError(this.mMediaId);
        }
    }

    interface MediaBrowserImpl {
        void connect() throws ;

        void disconnect() throws ;

        @Nullable
        Bundle getExtras() throws ;

        void getItem(@NonNull String str, @NonNull ItemCallback itemCallback) throws ;

        @NonNull
        String getRoot() throws ;

        ComponentName getServiceComponent() throws ;

        @NonNull
        Token getSessionToken() throws ;

        boolean isConnected() throws ;

        void subscribe(@NonNull String str, Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) throws ;

        void unsubscribe(@NonNull String str, Bundle bundle) throws ;
    }

    interface MediaBrowserServiceCallbackImpl {
        void onConnectionFailed(Messenger messenger) throws ;

        void onLoadChildren(Messenger messenger, String str, List list, Bundle bundle) throws ;

        void onServiceConnected(Messenger messenger, String str, Token token, Bundle bundle) throws ;
    }

    static class MediaBrowserImplApi21 implements ConnectionCallbackInternal, MediaBrowserImpl, MediaBrowserServiceCallbackImpl {
        private static final boolean DBG = false;
        protected Object mBrowserObj;
        private Messenger mCallbacksMessenger;
        private final CallbackHandler mHandler = new CallbackHandler(this);
        private ServiceBinderWrapper mServiceBinderWrapper;
        private final ComponentName mServiceComponent;
        private final ArrayMap<String, Subscription> mSubscriptions = new ArrayMap();

        public MediaBrowserImplApi21(Context $r1, ComponentName $r2, ConnectionCallback $r3, Bundle $r4) throws  {
            this.mServiceComponent = $r2;
            $r3.setInternalConnectionCallback(this);
            this.mBrowserObj = MediaBrowserCompatApi21.createBrowser($r1, $r2, $r3.mConnectionCallbackObj, $r4);
        }

        public void connect() throws  {
            MediaBrowserCompatApi21.connect(this.mBrowserObj);
        }

        public void disconnect() throws  {
            if (!(this.mServiceBinderWrapper == null || this.mCallbacksMessenger == null)) {
                try {
                    this.mServiceBinderWrapper.unregisterCallbackMessenger(this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    Log.i(MediaBrowserCompat.TAG, "Remote error unregistering client messenger.");
                }
            }
            MediaBrowserCompatApi21.disconnect(this.mBrowserObj);
        }

        public boolean isConnected() throws  {
            return MediaBrowserCompatApi21.isConnected(this.mBrowserObj);
        }

        public ComponentName getServiceComponent() throws  {
            return MediaBrowserCompatApi21.getServiceComponent(this.mBrowserObj);
        }

        @NonNull
        public String getRoot() throws  {
            return MediaBrowserCompatApi21.getRoot(this.mBrowserObj);
        }

        @Nullable
        public Bundle getExtras() throws  {
            return MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
        }

        @NonNull
        public Token getSessionToken() throws  {
            return Token.fromToken(MediaBrowserCompatApi21.getSessionToken(this.mBrowserObj));
        }

        public void subscribe(@NonNull String $r1, Bundle $r2, @NonNull SubscriptionCallback $r3) throws  {
            SubscriptionCallbackApi21 $r4 = new SubscriptionCallbackApi21($r3, $r2);
            Subscription $r8 = (Subscription) this.mSubscriptions.get($r1);
            if ($r8 == null) {
                $r8 = new Subscription();
                this.mSubscriptions.put($r1, $r8);
            }
            $r8.setCallbackForOptions($r4, $r2);
            if (!MediaBrowserCompatApi21.isConnected(this.mBrowserObj)) {
                return;
            }
            if ($r2 == null || this.mServiceBinderWrapper == null) {
                MediaBrowserCompatApi21.subscribe(this.mBrowserObj, $r1, $r4.mSubscriptionCallbackObj);
                return;
            }
            try {
                this.mServiceBinderWrapper.addSubscription($r1, $r2, this.mCallbacksMessenger);
            } catch (RemoteException e) {
                Log.i(MediaBrowserCompat.TAG, "Remote error subscribing media item: " + $r1);
            }
        }

        public void unsubscribe(@NonNull String $r1, Bundle $r2) throws  {
            if (TextUtils.isEmpty($r1)) {
                throw new IllegalArgumentException("parentId is empty.");
            }
            Subscription $r7 = (Subscription) this.mSubscriptions.get($r1);
            if ($r7 != null && $r7.remove($r2)) {
                if ($r2 == null || this.mServiceBinderWrapper == null) {
                    if (this.mServiceBinderWrapper != null || $r7.isEmpty()) {
                        MediaBrowserCompatApi21.unsubscribe(this.mBrowserObj, $r1);
                    }
                } else if (this.mServiceBinderWrapper == null) {
                    try {
                        this.mServiceBinderWrapper.removeSubscription($r1, $r2, this.mCallbacksMessenger);
                    } catch (RemoteException e) {
                        Log.d(MediaBrowserCompat.TAG, "removeSubscription failed with RemoteException parentId=" + $r1);
                    }
                }
            }
            if ($r7 != null && $r7.isEmpty()) {
                this.mSubscriptions.remove($r1);
            }
        }

        public void getItem(@NonNull String $r1, @NonNull ItemCallback $r2) throws  {
            if (TextUtils.isEmpty($r1)) {
                throw new IllegalArgumentException("mediaId is empty.");
            } else if ($r2 == null) {
                throw new IllegalArgumentException("cb is null.");
            } else if (!MediaBrowserCompatApi21.isConnected(this.mBrowserObj)) {
                Log.i(MediaBrowserCompat.TAG, "Not connected, unable to retrieve the MediaItem.");
                r1 = $r2;
                final String str = $r1;
                this.mHandler.post(new Runnable() {
                    public void run() throws  {
                        r1.onError(str);
                    }
                });
            } else if (this.mServiceBinderWrapper == null) {
                r1 = $r2;
                this.mHandler.post(new Runnable() {
                    public void run() throws  {
                        r1.onItemLoaded(null);
                    }
                });
            } else {
                try {
                    this.mServiceBinderWrapper.getMediaItem($r1, new ItemReceiver($r1, $r2, this.mHandler));
                } catch (RemoteException e) {
                    Log.i(MediaBrowserCompat.TAG, "Remote error getting media item: " + $r1);
                    final ItemCallback itemCallback = $r2;
                    final String str2 = $r1;
                    this.mHandler.post(new Runnable() {
                        public void run() throws  {
                            itemCallback.onError(str2);
                        }
                    });
                }
            }
        }

        public void onConnected() throws  {
            Bundle $r3 = MediaBrowserCompatApi21.getExtras(this.mBrowserObj);
            if ($r3 != null) {
                IBinder $r4 = BundleCompat.getBinder($r3, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER);
                if ($r4 != null) {
                    this.mServiceBinderWrapper = new ServiceBinderWrapper($r4);
                    this.mCallbacksMessenger = new Messenger(this.mHandler);
                    this.mHandler.setCallbacksMessenger(this.mCallbacksMessenger);
                    try {
                        this.mServiceBinderWrapper.registerCallbackMessenger(this.mCallbacksMessenger);
                    } catch (RemoteException e) {
                        Log.i(MediaBrowserCompat.TAG, "Remote error registering client messenger.");
                    }
                    onServiceConnected(this.mCallbacksMessenger, null, null, null);
                }
            }
        }

        public void onConnectionSuspended() throws  {
            this.mServiceBinderWrapper = null;
            this.mCallbacksMessenger = null;
            this.mHandler.setCallbacksMessenger(null);
        }

        public void onConnectionFailed() throws  {
        }

        public void onServiceConnected(Messenger callback, String root, Token session, Bundle extra) throws  {
            for (Entry $r10 : this.mSubscriptions.entrySet()) {
                root = (String) $r10.getKey();
                Subscription $r11 = (Subscription) $r10.getValue();
                List $r12 = $r11.getOptionsList();
                List $r13 = $r11.getCallbacks();
                for (int $i0 = 0; $i0 < $r12.size(); $i0++) {
                    if ($r12.get($i0) == null) {
                        MediaBrowserCompatApi21.subscribe(this.mBrowserObj, root, ((SubscriptionCallbackApi21) $r13.get($i0)).mSubscriptionCallbackObj);
                    } else {
                        ServiceBinderWrapper $r16 = this.mServiceBinderWrapper;
                        try {
                            extra = (Bundle) $r12.get($i0);
                            $r16.addSubscription(root, extra, this.mCallbacksMessenger);
                        } catch (RemoteException e) {
                            Log.d(MediaBrowserCompat.TAG, "addSubscription failed with RemoteException parentId=" + root);
                        }
                    }
                }
            }
        }

        public void onConnectionFailed(Messenger callback) throws  {
        }

        public void onLoadChildren(Messenger $r1, String $r2, List $r3, @NonNull Bundle $r4) throws  {
            if (this.mCallbacksMessenger == $r1) {
                Subscription $r8 = (Subscription) this.mSubscriptions.get($r2);
                if ($r8 != null) {
                    $r8.getCallback($r4).onChildrenLoaded($r2, $r3, $r4);
                }
            }
        }
    }

    static class MediaBrowserImplApi23 extends MediaBrowserImplApi21 {
        public MediaBrowserImplApi23(Context $r1, ComponentName $r2, ConnectionCallback $r3, Bundle $r4) throws  {
            super($r1, $r2, $r3, $r4);
        }

        public void getItem(@NonNull String $r1, @NonNull ItemCallback $r2) throws  {
            MediaBrowserCompatApi23.getItem(this.mBrowserObj, $r1, $r2.mItemCallbackObj);
        }
    }

    static class MediaBrowserImplBase implements MediaBrowserImpl, MediaBrowserServiceCallbackImpl {
        private static final int CONNECT_STATE_CONNECTED = 2;
        private static final int CONNECT_STATE_CONNECTING = 1;
        private static final int CONNECT_STATE_DISCONNECTED = 0;
        private static final int CONNECT_STATE_SUSPENDED = 3;
        private static final boolean DBG = false;
        private final ConnectionCallback mCallback;
        private Messenger mCallbacksMessenger;
        private final Context mContext;
        private Bundle mExtras;
        private final CallbackHandler mHandler = new CallbackHandler(this);
        private Token mMediaSessionToken;
        private final Bundle mRootHints;
        private String mRootId;
        private ServiceBinderWrapper mServiceBinderWrapper;
        private final ComponentName mServiceComponent;
        private MediaServiceConnection mServiceConnection;
        private int mState = 0;
        private final ArrayMap<String, Subscription> mSubscriptions = new ArrayMap();

        private class MediaServiceConnection implements ServiceConnection {
            private MediaServiceConnection() throws  {
            }

            public void onServiceConnected(final ComponentName $r1, final IBinder $r2) throws  {
                postOrRun(new Runnable() {
                    public void run() throws  {
                        this = this;
                        if (MediaServiceConnection.this.isCurrent("onServiceConnected")) {
                            MediaBrowserImplBase.this.mServiceBinderWrapper = new ServiceBinderWrapper($r2);
                            MediaBrowserImplBase.this.mCallbacksMessenger = new Messenger(MediaBrowserImplBase.this.mHandler);
                            MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(MediaBrowserImplBase.this.mCallbacksMessenger);
                            MediaBrowserImplBase.this.mState = 1;
                            this = this;
                            try {
                                ServiceBinderWrapper $r4 = MediaBrowserImplBase.this.mServiceBinderWrapper;
                                MediaServiceConnection $r2 = MediaServiceConnection.this;
                                this = this;
                                Context $r9 = MediaBrowserImplBase.this.mContext;
                                $r2 = MediaServiceConnection.this;
                                this = this;
                                Bundle $r10 = MediaBrowserImplBase.this.mRootHints;
                                $r2 = MediaServiceConnection.this;
                                this = this;
                                $r4.connect($r9, $r10, MediaBrowserImplBase.this.mCallbacksMessenger);
                            } catch (RemoteException e) {
                                C00561 $r22 = this;
                                this = $r22;
                                Log.w(MediaBrowserCompat.TAG, "RemoteException during connect for " + MediaBrowserImplBase.this.mServiceComponent);
                            }
                        }
                    }
                });
            }

            public void onServiceDisconnected(final ComponentName $r1) throws  {
                postOrRun(new Runnable() {
                    public void run() throws  {
                        if (MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
                            MediaBrowserImplBase.this.mServiceBinderWrapper = null;
                            MediaBrowserImplBase.this.mCallbacksMessenger = null;
                            MediaBrowserImplBase.this.mHandler.setCallbacksMessenger(null);
                            MediaBrowserImplBase.this.mState = 3;
                            MediaBrowserImplBase.this.mCallback.onConnectionSuspended();
                        }
                    }
                });
            }

            private void postOrRun(Runnable $r1) throws  {
                if (Thread.currentThread() == MediaBrowserImplBase.this.mHandler.getLooper().getThread()) {
                    $r1.run();
                } else {
                    MediaBrowserImplBase.this.mHandler.post($r1);
                }
            }

            private boolean isCurrent(String $r1) throws  {
                if (MediaBrowserImplBase.this.mServiceConnection == this) {
                    return true;
                }
                if (MediaBrowserImplBase.this.mState != 0) {
                    Log.i(MediaBrowserCompat.TAG, $r1 + " for " + MediaBrowserImplBase.this.mServiceComponent + " with mServiceConnection=" + MediaBrowserImplBase.this.mServiceConnection + " this=" + this);
                }
                return false;
            }
        }

        public MediaBrowserImplBase(Context $r1, ComponentName $r2, ConnectionCallback $r3, Bundle $r4) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("context must not be null");
            } else if ($r2 == null) {
                throw new IllegalArgumentException("service component must not be null");
            } else if ($r3 == null) {
                throw new IllegalArgumentException("connection callback must not be null");
            } else {
                this.mContext = $r1;
                this.mServiceComponent = $r2;
                this.mCallback = $r3;
                this.mRootHints = $r4;
            }
        }

        public void connect() throws  {
            if (this.mState != 0) {
                throw new IllegalStateException("connect() called while not disconnected (state=" + getStateLabel(this.mState) + ")");
            } else if (this.mServiceBinderWrapper != null) {
                throw new RuntimeException("mServiceBinderWrapper should be null. Instead it is " + this.mServiceBinderWrapper);
            } else if (this.mCallbacksMessenger != null) {
                throw new RuntimeException("mCallbacksMessenger should be null. Instead it is " + this.mCallbacksMessenger);
            } else {
                this.mState = 1;
                Intent $r2 = new Intent(MediaBrowserServiceCompat.SERVICE_INTERFACE);
                $r2.setComponent(this.mServiceComponent);
                MediaBrowserImplBase mediaBrowserImplBase = this;
                final MediaServiceConnection $r3 = new MediaServiceConnection();
                this.mServiceConnection = $r3;
                boolean $z0 = false;
                try {
                    $z0 = this.mContext.bindService($r2, this.mServiceConnection, 1);
                } catch (Exception e) {
                    Log.e(MediaBrowserCompat.TAG, "Failed binding to service " + this.mServiceComponent);
                }
                if (!$z0) {
                    this.mHandler.post(new Runnable() {
                        public void run() throws  {
                            if ($r3 == MediaBrowserImplBase.this.mServiceConnection) {
                                MediaBrowserImplBase.this.forceCloseConnection();
                                MediaBrowserImplBase.this.mCallback.onConnectionFailed();
                            }
                        }
                    });
                }
            }
        }

        public void disconnect() throws  {
            if (this.mCallbacksMessenger != null) {
                try {
                    this.mServiceBinderWrapper.disconnect(this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    Log.w(MediaBrowserCompat.TAG, "RemoteException during connect for " + this.mServiceComponent);
                }
            }
            forceCloseConnection();
        }

        private void forceCloseConnection() throws  {
            if (this.mServiceConnection != null) {
                this.mContext.unbindService(this.mServiceConnection);
            }
            this.mState = 0;
            this.mServiceConnection = null;
            this.mServiceBinderWrapper = null;
            this.mCallbacksMessenger = null;
            this.mHandler.setCallbacksMessenger(null);
            this.mRootId = null;
            this.mMediaSessionToken = null;
        }

        public boolean isConnected() throws  {
            return this.mState == 2;
        }

        @NonNull
        public ComponentName getServiceComponent() throws  {
            if (isConnected()) {
                return this.mServiceComponent;
            }
            throw new IllegalStateException("getServiceComponent() called while not connected (state=" + this.mState + ")");
        }

        @NonNull
        public String getRoot() throws  {
            if (isConnected()) {
                return this.mRootId;
            }
            throw new IllegalStateException("getRoot() called while not connected(state=" + getStateLabel(this.mState) + ")");
        }

        @Nullable
        public Bundle getExtras() throws  {
            if (isConnected()) {
                return this.mExtras;
            }
            throw new IllegalStateException("getExtras() called while not connected (state=" + getStateLabel(this.mState) + ")");
        }

        @NonNull
        public Token getSessionToken() throws  {
            if (isConnected()) {
                return this.mMediaSessionToken;
            }
            throw new IllegalStateException("getSessionToken() called while not connected(state=" + this.mState + ")");
        }

        public void subscribe(@NonNull String $r1, Bundle $r2, @NonNull SubscriptionCallback $r3) throws  {
            if (TextUtils.isEmpty($r1)) {
                throw new IllegalArgumentException("parentId is empty.");
            } else if ($r3 == null) {
                throw new IllegalArgumentException("callback is null");
            } else {
                Subscription $r8 = (Subscription) this.mSubscriptions.get($r1);
                if ($r8 == null) {
                    $r8 = new Subscription();
                    this.mSubscriptions.put($r1, $r8);
                }
                $r8.setCallbackForOptions($r3, $r2);
                if (this.mState == 2) {
                    try {
                        this.mServiceBinderWrapper.addSubscription($r1, $r2, this.mCallbacksMessenger);
                    } catch (RemoteException e) {
                        Log.d(MediaBrowserCompat.TAG, "addSubscription failed with RemoteException parentId=" + $r1);
                    }
                }
            }
        }

        public void unsubscribe(@NonNull String $r1, Bundle $r2) throws  {
            if (TextUtils.isEmpty($r1)) {
                throw new IllegalArgumentException("parentId is empty.");
            }
            Subscription $r7 = (Subscription) this.mSubscriptions.get($r1);
            if ($r7 != null && $r7.remove($r2) && this.mState == 2) {
                try {
                    this.mServiceBinderWrapper.removeSubscription($r1, $r2, this.mCallbacksMessenger);
                } catch (RemoteException e) {
                    Log.d(MediaBrowserCompat.TAG, "removeSubscription failed with RemoteException parentId=" + $r1);
                }
            }
            if ($r7 != null && $r7.isEmpty()) {
                this.mSubscriptions.remove($r1);
            }
        }

        public void getItem(@NonNull final String $r1, @NonNull final ItemCallback $r2) throws  {
            if (TextUtils.isEmpty($r1)) {
                throw new IllegalArgumentException("mediaId is empty.");
            } else if ($r2 == null) {
                throw new IllegalArgumentException("cb is null.");
            } else if (this.mState != 2) {
                Log.i(MediaBrowserCompat.TAG, "Not connected, unable to retrieve the MediaItem.");
                this.mHandler.post(new Runnable() {
                    public void run() throws  {
                        $r2.onError($r1);
                    }
                });
            } else {
                try {
                    this.mServiceBinderWrapper.getMediaItem($r1, new ItemReceiver($r1, $r2, this.mHandler));
                } catch (RemoteException e) {
                    Log.i(MediaBrowserCompat.TAG, "Remote error getting media item.");
                    this.mHandler.post(new Runnable() {
                        public void run() throws  {
                            $r2.onError($r1);
                        }
                    });
                }
            }
        }

        public void onServiceConnected(Messenger $r1, String $r2, Token $r3, Bundle $r4) throws  {
            if (!isCurrent($r1, "onConnect")) {
                return;
            }
            if (this.mState != 1) {
                Log.w(MediaBrowserCompat.TAG, "onConnect from service while mState=" + getStateLabel(this.mState) + "... ignoring");
                return;
            }
            this.mRootId = $r2;
            this.mMediaSessionToken = $r3;
            this.mExtras = $r4;
            this.mState = 2;
            this.mCallback.onConnected();
            try {
                for (Entry $r12 : this.mSubscriptions.entrySet()) {
                    $r2 = (String) $r12.getKey();
                    for (Bundle $r42 : ((Subscription) $r12.getValue()).getOptionsList()) {
                        ServiceBinderWrapper $r16 = this.mServiceBinderWrapper;
                        $r16.addSubscription($r2, $r42, this.mCallbacksMessenger);
                    }
                }
            } catch (RemoteException e) {
                Log.d(MediaBrowserCompat.TAG, "addSubscription failed with RemoteException.");
            }
        }

        public void onConnectionFailed(Messenger $r1) throws  {
            Log.e(MediaBrowserCompat.TAG, "onConnectFailed for " + this.mServiceComponent);
            if (!isCurrent($r1, "onConnectFailed")) {
                return;
            }
            if (this.mState != 1) {
                Log.w(MediaBrowserCompat.TAG, "onConnect from service while mState=" + getStateLabel(this.mState) + "... ignoring");
                return;
            }
            forceCloseConnection();
            this.mCallback.onConnectionFailed();
        }

        public void onLoadChildren(Messenger $r1, String $r2, List $r3, Bundle $r4) throws  {
            if (isCurrent($r1, "onLoadChildren")) {
                Subscription $r7 = (Subscription) this.mSubscriptions.get($r2);
                if ($r7 != null) {
                    SubscriptionCallback $r8 = $r7.getCallback($r4);
                    if ($r8 == null) {
                        return;
                    }
                    if ($r4 == null) {
                        $r8.onChildrenLoaded($r2, $r3);
                    } else {
                        $r8.onChildrenLoaded($r2, $r3, $r4);
                    }
                }
            }
        }

        private static String getStateLabel(int $i0) throws  {
            switch ($i0) {
                case 0:
                    return "CONNECT_STATE_DISCONNECTED";
                case 1:
                    return "CONNECT_STATE_CONNECTING";
                case 2:
                    return "CONNECT_STATE_CONNECTED";
                case 3:
                    return "CONNECT_STATE_SUSPENDED";
                default:
                    return "UNKNOWN/" + $i0;
            }
        }

        private boolean isCurrent(Messenger $r1, String $r2) throws  {
            if (this.mCallbacksMessenger == $r1) {
                return true;
            }
            if (this.mState != 0) {
                Log.i(MediaBrowserCompat.TAG, $r2 + " for " + this.mServiceComponent + " with mCallbacksMessenger=" + this.mCallbacksMessenger + " this=" + this);
            }
            return false;
        }

        void dump() throws  {
            Log.d(MediaBrowserCompat.TAG, "MediaBrowserCompat...");
            Log.d(MediaBrowserCompat.TAG, "  mServiceComponent=" + this.mServiceComponent);
            Log.d(MediaBrowserCompat.TAG, "  mCallback=" + this.mCallback);
            Log.d(MediaBrowserCompat.TAG, "  mRootHints=" + this.mRootHints);
            Log.d(MediaBrowserCompat.TAG, "  mState=" + getStateLabel(this.mState));
            Log.d(MediaBrowserCompat.TAG, "  mServiceConnection=" + this.mServiceConnection);
            Log.d(MediaBrowserCompat.TAG, "  mServiceBinderWrapper=" + this.mServiceBinderWrapper);
            Log.d(MediaBrowserCompat.TAG, "  mCallbacksMessenger=" + this.mCallbacksMessenger);
            Log.d(MediaBrowserCompat.TAG, "  mRootId=" + this.mRootId);
            Log.d(MediaBrowserCompat.TAG, "  mMediaSessionToken=" + this.mMediaSessionToken);
        }
    }

    public static class MediaItem implements Parcelable {
        public static final Creator<MediaItem> CREATOR = new C00581();
        public static final int FLAG_BROWSABLE = 1;
        public static final int FLAG_PLAYABLE = 2;
        private final MediaDescriptionCompat mDescription;
        private final int mFlags;

        static class C00581 implements Creator<MediaItem> {
            C00581() throws  {
            }

            public MediaItem createFromParcel(Parcel $r1) throws  {
                return new MediaItem($r1);
            }

            public MediaItem[] newArray(int $i0) throws  {
                return new MediaItem[$i0];
            }
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface Flags {
        }

        public int describeContents() throws  {
            return 0;
        }

        public MediaItem(@NonNull MediaDescriptionCompat $r1, int $i0) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("description cannot be null");
            } else if (TextUtils.isEmpty($r1.getMediaId())) {
                throw new IllegalArgumentException("description must have a non-empty media id");
            } else {
                this.mFlags = $i0;
                this.mDescription = $r1;
            }
        }

        private MediaItem(Parcel $r1) throws  {
            this.mFlags = $r1.readInt();
            this.mDescription = (MediaDescriptionCompat) MediaDescriptionCompat.CREATOR.createFromParcel($r1);
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            $r1.writeInt(this.mFlags);
            this.mDescription.writeToParcel($r1, $i0);
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder("MediaItem{");
            $r1.append("mFlags=").append(this.mFlags);
            $r1.append(", mDescription=").append(this.mDescription);
            $r1.append('}');
            return $r1.toString();
        }

        public int getFlags() throws  {
            return this.mFlags;
        }

        public boolean isBrowsable() throws  {
            return (this.mFlags & 1) != 0;
        }

        public boolean isPlayable() throws  {
            return (this.mFlags & 2) != 0;
        }

        @NonNull
        public MediaDescriptionCompat getDescription() throws  {
            return this.mDescription;
        }

        @NonNull
        public String getMediaId() throws  {
            return this.mDescription.getMediaId();
        }
    }

    private static class ServiceBinderWrapper {
        private Messenger mMessenger;

        public ServiceBinderWrapper(IBinder $r1) throws  {
            this.mMessenger = new Messenger($r1);
        }

        void connect(Context $r1, Bundle $r2, Messenger $r3) throws RemoteException {
            Bundle $r4 = new Bundle();
            $r4.putString(MediaBrowserProtocol.DATA_PACKAGE_NAME, $r1.getPackageName());
            $r4.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, $r2);
            sendRequest(1, $r4, $r3);
        }

        void disconnect(Messenger $r1) throws RemoteException {
            sendRequest(2, null, $r1);
        }

        void addSubscription(String $r1, Bundle $r2, Messenger $r3) throws RemoteException {
            Bundle $r4 = new Bundle();
            $r4.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, $r1);
            $r4.putBundle(MediaBrowserProtocol.DATA_OPTIONS, $r2);
            sendRequest(3, $r4, $r3);
        }

        void removeSubscription(String $r1, Bundle $r2, Messenger $r3) throws RemoteException {
            Bundle $r4 = new Bundle();
            $r4.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, $r1);
            $r4.putBundle(MediaBrowserProtocol.DATA_OPTIONS, $r2);
            sendRequest(4, $r4, $r3);
        }

        void getMediaItem(String $r1, ResultReceiver $r2) throws RemoteException {
            Bundle $r3 = new Bundle();
            $r3.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, $r1);
            $r3.putParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER, $r2);
            sendRequest(5, $r3, null);
        }

        void registerCallbackMessenger(Messenger $r1) throws RemoteException {
            sendRequest(6, null, $r1);
        }

        void unregisterCallbackMessenger(Messenger $r1) throws RemoteException {
            sendRequest(7, null, $r1);
        }

        private void sendRequest(int $i0, Bundle $r1, Messenger $r2) throws RemoteException {
            Message $r3 = Message.obtain();
            $r3.what = $i0;
            $r3.arg1 = 1;
            $r3.setData($r1);
            $r3.replyTo = $r2;
            this.mMessenger.send($r3);
        }
    }

    private static class Subscription {
        private final List<SubscriptionCallback> mCallbacks = new ArrayList();
        private final List<Bundle> mOptionsList = new ArrayList();

        public boolean isEmpty() throws  {
            return this.mCallbacks.isEmpty();
        }

        public List<Bundle> getOptionsList() throws  {
            return this.mOptionsList;
        }

        public List<SubscriptionCallback> getCallbacks() throws  {
            return this.mCallbacks;
        }

        public void setCallbackForOptions(SubscriptionCallback $r1, Bundle $r2) throws  {
            for (int $i0 = 0; $i0 < this.mOptionsList.size(); $i0++) {
                if (MediaBrowserCompatUtils.areSameOptions((Bundle) this.mOptionsList.get($i0), $r2)) {
                    this.mCallbacks.set($i0, $r1);
                    return;
                }
            }
            this.mCallbacks.add($r1);
            this.mOptionsList.add($r2);
        }

        public boolean remove(Bundle $r1) throws  {
            for (int $i0 = 0; $i0 < this.mOptionsList.size(); $i0++) {
                if (MediaBrowserCompatUtils.areSameOptions((Bundle) this.mOptionsList.get($i0), $r1)) {
                    this.mCallbacks.remove($i0);
                    this.mOptionsList.remove($i0);
                    return true;
                }
            }
            return false;
        }

        public SubscriptionCallback getCallback(Bundle $r1) throws  {
            for (int $i0 = 0; $i0 < this.mOptionsList.size(); $i0++) {
                if (MediaBrowserCompatUtils.areSameOptions((Bundle) this.mOptionsList.get($i0), $r1)) {
                    return (SubscriptionCallback) this.mCallbacks.get($i0);
                }
            }
            return null;
        }
    }

    public static abstract class SubscriptionCallback {
        public void onChildrenLoaded(@NonNull @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;)V"}) String parentId, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;)V"}) List<MediaItem> list) throws  {
        }

        public void onChildrenLoaded(@NonNull @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) String parentId, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) List<MediaItem> list, @NonNull @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) Bundle options) throws  {
        }

        public void onError(@NonNull String parentId) throws  {
        }

        public void onError(@NonNull String parentId, @NonNull Bundle options) throws  {
        }
    }

    static class SubscriptionCallbackApi21 extends SubscriptionCallback {
        private Bundle mOptions;
        SubscriptionCallback mSubscriptionCallback;
        private final Object mSubscriptionCallbackObj = MediaBrowserCompatApi21.createSubscriptionCallback(new StubApi21());

        private class StubApi21 implements SubscriptionCallback {
            private StubApi21() throws  {
            }

            public void onChildrenLoaded(@NonNull @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/os/Parcel;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/os/Parcel;", ">;)V"}) List<Parcel> $r2) throws  {
                ArrayList $r3 = null;
                if ($r2 != null) {
                    $r3 = new ArrayList();
                    for (Parcel $r6 : $r2) {
                        $r6.setDataPosition(0);
                        $r3.add(MediaItem.CREATOR.createFromParcel($r6));
                        $r6.recycle();
                    }
                }
                if (SubscriptionCallbackApi21.this.mOptions != null) {
                    SubscriptionCallbackApi21.this.onChildrenLoaded($r1, MediaBrowserCompatUtils.applyOptions($r3, SubscriptionCallbackApi21.this.mOptions), SubscriptionCallbackApi21.this.mOptions);
                } else {
                    SubscriptionCallbackApi21.this.onChildrenLoaded($r1, $r3);
                }
            }

            public void onError(@NonNull String $r1) throws  {
                if (SubscriptionCallbackApi21.this.mOptions != null) {
                    SubscriptionCallbackApi21.this.onError($r1, SubscriptionCallbackApi21.this.mOptions);
                } else {
                    SubscriptionCallbackApi21.this.onError($r1);
                }
            }
        }

        public SubscriptionCallbackApi21(SubscriptionCallback $r1, Bundle $r2) throws  {
            this.mSubscriptionCallback = $r1;
            this.mOptions = $r2;
        }

        public void onChildrenLoaded(@NonNull @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;)V"}) List<MediaItem> $r2) throws  {
            this.mSubscriptionCallback.onChildrenLoaded($r1, $r2);
        }

        public void onChildrenLoaded(@NonNull @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) List<MediaItem> $r2, @NonNull @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) Bundle $r3) throws  {
            this.mSubscriptionCallback.onChildrenLoaded($r1, $r2, $r3);
        }

        public void onError(@NonNull String $r1) throws  {
            this.mSubscriptionCallback.onError($r1);
        }

        public void onError(@NonNull String $r1, @NonNull Bundle $r2) throws  {
            this.mSubscriptionCallback.onError($r1, $r2);
        }
    }

    public MediaBrowserCompat(Context $r1, ComponentName $r2, ConnectionCallback $r3, Bundle $r4) throws  {
        if (VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaBrowserImplApi23($r1, $r2, $r3, $r4);
        } else if (VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaBrowserImplApi21($r1, $r2, $r3, $r4);
        } else {
            this.mImpl = new MediaBrowserImplBase($r1, $r2, $r3, $r4);
        }
    }

    public void connect() throws  {
        this.mImpl.connect();
    }

    public void disconnect() throws  {
        this.mImpl.disconnect();
    }

    public boolean isConnected() throws  {
        return this.mImpl.isConnected();
    }

    @NonNull
    public ComponentName getServiceComponent() throws  {
        return this.mImpl.getServiceComponent();
    }

    @NonNull
    public String getRoot() throws  {
        return this.mImpl.getRoot();
    }

    @Nullable
    public Bundle getExtras() throws  {
        return this.mImpl.getExtras();
    }

    @NonNull
    public Token getSessionToken() throws  {
        return this.mImpl.getSessionToken();
    }

    public void subscribe(@NonNull String $r1, @NonNull SubscriptionCallback $r2) throws  {
        this.mImpl.subscribe($r1, null, $r2);
    }

    public void subscribe(@NonNull String $r1, @NonNull Bundle $r2, @NonNull SubscriptionCallback $r3) throws  {
        if ($r2 == null) {
            throw new IllegalArgumentException("options are null");
        }
        this.mImpl.subscribe($r1, $r2, $r3);
    }

    public void unsubscribe(@NonNull String $r1) throws  {
        this.mImpl.unsubscribe($r1, null);
    }

    public void unsubscribe(@NonNull String $r1, @NonNull Bundle $r2) throws  {
        if ($r2 == null) {
            throw new IllegalArgumentException("options are null");
        }
        this.mImpl.unsubscribe($r1, $r2);
    }

    public void getItem(@NonNull String $r1, @NonNull ItemCallback $r2) throws  {
        this.mImpl.getItem($r1, $r2);
    }
}
