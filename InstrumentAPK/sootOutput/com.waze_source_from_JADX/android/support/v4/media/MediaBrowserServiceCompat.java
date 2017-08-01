package android.support.v4.media;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompat.MediaItem;
import android.support.v4.media.MediaBrowserServiceCompatApi23.ItemCallback;
import android.support.v4.media.session.MediaSessionCompat.Token;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class MediaBrowserServiceCompat extends Service {
    private static final boolean DBG = false;
    public static final String KEY_MEDIA_ITEM = "media_item";
    private static final int RESULT_FLAG_OPTION_NOT_HANDLED = 1;
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    private static final String TAG = "MediaBrowserServiceCompat";
    private final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap();
    private final ServiceHandler mHandler = new ServiceHandler();
    private MediaBrowserServiceImpl mImpl;
    Token mSession;

    public static class Result<T> {
        private Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendResultCalled;

        Result(Object $r1) throws  {
            this.mDebug = $r1;
        }

        public void sendResult(@Signature({"(TT;)V"}) T $r1) throws  {
            if (this.mSendResultCalled) {
                throw new IllegalStateException("sendResult() called twice for: " + this.mDebug);
            }
            this.mSendResultCalled = true;
            onResultSent($r1, this.mFlags);
        }

        public void detach() throws  {
            if (this.mDetachCalled) {
                throw new IllegalStateException("detach() called when detach() had already been called for: " + this.mDebug);
            } else if (this.mSendResultCalled) {
                throw new IllegalStateException("detach() called when sendResult() had already been called for: " + this.mDebug);
            } else {
                this.mDetachCalled = true;
            }
        }

        boolean isDone() throws  {
            return this.mDetachCalled || this.mSendResultCalled;
        }

        void setFlags(int $i0) throws  {
            this.mFlags = $i0;
        }

        void onResultSent(@Signature({"(TT;I)V"}) T t, @Signature({"(TT;I)V"}) int flags) throws  {
        }
    }

    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";
        private final Bundle mExtras;
        private final String mRootId;

        public BrowserRoot(@NonNull String $r1, @Nullable Bundle $r2) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
            }
            this.mRootId = $r1;
            this.mExtras = $r2;
        }

        public String getRootId() throws  {
            return this.mRootId;
        }

        public Bundle getExtras() throws  {
            return this.mExtras;
        }
    }

    private class ConnectionRecord {
        ServiceCallbacks callbacks;
        String pkg;
        BrowserRoot root;
        Bundle rootHints;
        HashMap<String, List<Bundle>> subscriptions;

        private ConnectionRecord() throws  {
            this.subscriptions = new HashMap();
        }
    }

    interface MediaBrowserServiceImpl {
        IBinder onBind(Intent intent) throws ;

        void onCreate() throws ;
    }

    class MediaBrowserServiceImplApi21 implements MediaBrowserServiceImpl {
        private Object mServiceObj;

        MediaBrowserServiceImplApi21() throws  {
        }

        public void onCreate() throws  {
            this.mServiceObj = MediaBrowserServiceCompatApi21.createService();
            MediaBrowserServiceCompatApi21.onCreate(this.mServiceObj, new ServiceImplApi21());
        }

        public IBinder onBind(Intent $r1) throws  {
            return MediaBrowserServiceCompatApi21.onBind(this.mServiceObj, $r1);
        }
    }

    class MediaBrowserServiceImplApi23 implements MediaBrowserServiceImpl {
        private Object mServiceObj;

        MediaBrowserServiceImplApi23() throws  {
        }

        public void onCreate() throws  {
            this.mServiceObj = MediaBrowserServiceCompatApi23.createService();
            MediaBrowserServiceCompatApi23.onCreate(this.mServiceObj, new ServiceImplApi23());
        }

        public IBinder onBind(Intent $r1) throws  {
            return MediaBrowserServiceCompatApi21.onBind(this.mServiceObj, $r1);
        }
    }

    class MediaBrowserServiceImplBase implements MediaBrowserServiceImpl {
        private Messenger mMessenger;

        MediaBrowserServiceImplBase() throws  {
        }

        public void onCreate() throws  {
            this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
        }

        public IBinder onBind(Intent $r1) throws  {
            if (MediaBrowserServiceCompat.SERVICE_INTERFACE.equals($r1.getAction())) {
                return this.mMessenger.getBinder();
            }
            return null;
        }
    }

    private interface ServiceCallbacks {
        IBinder asBinder() throws ;

        void onConnect(String str, Token token, Bundle bundle) throws RemoteException;

        void onConnectFailed() throws RemoteException;

        void onLoadChildren(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) List<MediaItem> list, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) Bundle bundle) throws RemoteException;
    }

    private class ServiceCallbacksApi21 implements ServiceCallbacks {
        final android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCallbacks mCallbacks;
        Messenger mMessenger;

        ServiceCallbacksApi21(android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCallbacks $r2) throws  {
            this.mCallbacks = $r2;
        }

        public IBinder asBinder() throws  {
            return this.mCallbacks.asBinder();
        }

        public void onConnect(String $r1, Token $r2, Bundle $r3) throws RemoteException {
            if ($r3 == null) {
                $r3 = new Bundle();
            }
            this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
            BundleCompat.putBinder($r3, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER, this.mMessenger.getBinder());
            $r3.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 1);
            this.mCallbacks.onConnect($r1, $r2.getToken(), $r3);
        }

        public void onConnectFailed() throws RemoteException {
            this.mCallbacks.onConnectFailed();
        }

        public void onLoadChildren(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) List<MediaItem> $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) Bundle options) throws RemoteException {
            ArrayList $r4 = null;
            if ($r2 != null) {
                $r4 = new ArrayList();
                for (MediaItem $r7 : $r2) {
                    Parcel $r8 = Parcel.obtain();
                    $r7.writeToParcel($r8, 0);
                    $r4.add($r8);
                }
            }
            this.mCallbacks.onLoadChildren($r1, $r4);
        }
    }

    private class ServiceCallbacksCompat implements ServiceCallbacks {
        final Messenger mCallbacks;

        ServiceCallbacksCompat(Messenger $r2) throws  {
            this.mCallbacks = $r2;
        }

        public IBinder asBinder() throws  {
            return this.mCallbacks.getBinder();
        }

        public void onConnect(String $r1, Token $r2, Bundle $r4) throws RemoteException {
            if ($r4 == null) {
                $r4 = new Bundle();
            }
            $r4.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 1);
            Bundle $r3 = new Bundle();
            $r3.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, $r1);
            $r3.putParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN, $r2);
            $r3.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, $r4);
            sendRequest(1, $r3);
        }

        public void onConnectFailed() throws RemoteException {
            sendRequest(2, null);
        }

        public void onLoadChildren(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) List<MediaItem> $r4, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")V"}) Bundle $r2) throws RemoteException {
            Bundle $r3 = new Bundle();
            $r3.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, $r1);
            $r3.putBundle(MediaBrowserProtocol.DATA_OPTIONS, $r2);
            if ($r4 != null) {
                ArrayList $r5;
                if ($r4 instanceof ArrayList) {
                    $r5 = (ArrayList) $r4;
                } else {
                    $r5 = new ArrayList($r4);
                }
                $r3.putParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST, $r5);
            }
            sendRequest(3, $r3);
        }

        private void sendRequest(int $i0, Bundle $r1) throws RemoteException {
            Message $r2 = Message.obtain();
            $r2.what = $i0;
            $r2.arg1 = 1;
            $r2.setData($r1);
            this.mCallbacks.send($r2);
        }
    }

    private final class ServiceHandler extends Handler {
        private final ServiceImpl mServiceImpl;

        private ServiceHandler() throws  {
            this.mServiceImpl = new ServiceImpl();
        }

        public void handleMessage(Message $r1) throws  {
            Bundle $r3 = $r1.getData();
            switch ($r1.what) {
                case 1:
                    this.mServiceImpl.connect($r3.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME), $r3.getInt(MediaBrowserProtocol.DATA_CALLING_UID), $r3.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS), new ServiceCallbacksCompat($r1.replyTo));
                    return;
                case 2:
                    this.mServiceImpl.disconnect(new ServiceCallbacksCompat($r1.replyTo));
                    return;
                case 3:
                    this.mServiceImpl.addSubscription($r3.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), $r3.getBundle(MediaBrowserProtocol.DATA_OPTIONS), new ServiceCallbacksCompat($r1.replyTo));
                    return;
                case 4:
                    this.mServiceImpl.removeSubscription($r3.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), $r3.getBundle(MediaBrowserProtocol.DATA_OPTIONS), new ServiceCallbacksCompat($r1.replyTo));
                    return;
                case 5:
                    this.mServiceImpl.getMediaItem($r3.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (ResultReceiver) $r3.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER));
                    return;
                case 6:
                    this.mServiceImpl.registerCallbacks(new ServiceCallbacksCompat($r1.replyTo));
                    return;
                case 7:
                    this.mServiceImpl.unregisterCallbacks(new ServiceCallbacksCompat($r1.replyTo));
                    return;
                default:
                    Log.w(MediaBrowserServiceCompat.TAG, "Unhandled message: " + $r1 + "\n  Service version: " + 1 + "\n  Client version: " + $r1.arg1);
                    return;
            }
        }

        public boolean sendMessageAtTime(Message $r1, long $l0) throws  {
            Bundle $r2 = $r1.getData();
            $r2.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            $r2.putInt(MediaBrowserProtocol.DATA_CALLING_UID, Binder.getCallingUid());
            return super.sendMessageAtTime($r1, $l0);
        }

        public void postOrRun(Runnable $r1) throws  {
            if (Thread.currentThread() == getLooper().getThread()) {
                $r1.run();
            } else {
                post($r1);
            }
        }

        public ServiceImpl getServiceImpl() throws  {
            return this.mServiceImpl;
        }
    }

    private class ServiceImpl {
        private ServiceImpl() throws  {
        }

        public void connect(String $r1, int $i0, Bundle $r2, ServiceCallbacks $r3) throws  {
            if (MediaBrowserServiceCompat.this.isValidPackage($r1, $i0)) {
                final ServiceCallbacks serviceCallbacks = $r3;
                final String str = $r1;
                final Bundle bundle = $r2;
                final int i = $i0;
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                    public void run() throws  {
                        IBinder $r3 = serviceCallbacks.asBinder();
                        MediaBrowserServiceCompat.this.mConnections.remove($r3);
                        ConnectionRecord $r1 = new ConnectionRecord();
                        $r1.pkg = str;
                        $r1.rootHints = bundle;
                        $r1.callbacks = serviceCallbacks;
                        $r1.root = MediaBrowserServiceCompat.this.onGetRoot(str, i, bundle);
                        ServiceCallbacks $r2;
                        if ($r1.root == null) {
                            Log.i(MediaBrowserServiceCompat.TAG, "No root for client " + str + " from service " + getClass().getName());
                            $r2 = serviceCallbacks;
                            this = this;
                            try {
                                $r2.onConnectFailed();
                                return;
                            } catch (RemoteException e) {
                                Log.w(MediaBrowserServiceCompat.TAG, "Calling onConnectFailed() failed. Ignoring. pkg=" + str);
                                return;
                            }
                        }
                        try {
                            MediaBrowserServiceCompat.this.mConnections.put($r3, $r1);
                            if (MediaBrowserServiceCompat.this.mSession != null) {
                                $r2 = serviceCallbacks;
                                this = this;
                                $r2.onConnect($r1.root.getRootId(), MediaBrowserServiceCompat.this.mSession, $r1.root.getExtras());
                            }
                        } catch (RemoteException e2) {
                            Log.w(MediaBrowserServiceCompat.TAG, "Calling onConnect() failed. Dropping client. pkg=" + str);
                            MediaBrowserServiceCompat.this.mConnections.remove($r3);
                        }
                    }
                });
                return;
            }
            throw new IllegalArgumentException("Package/uid mismatch: uid=" + $i0 + " package=" + $r1);
        }

        public void disconnect(final ServiceCallbacks $r1) throws  {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                public void run() throws  {
                    if (((ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.remove($r1.asBinder())) == null) {
                    }
                }
            });
        }

        public void addSubscription(final String $r1, final Bundle $r2, final ServiceCallbacks $r3) throws  {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                public void run() throws  {
                    ConnectionRecord $r7 = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get($r3.asBinder());
                    if ($r7 == null) {
                        Log.w(MediaBrowserServiceCompat.TAG, "addSubscription for callback that isn't registered id=" + $r1);
                        return;
                    }
                    MediaBrowserServiceCompat.this.addSubscription($r1, $r7, $r2);
                }
            });
        }

        public void removeSubscription(final String $r1, final Bundle $r2, final ServiceCallbacks $r3) throws  {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                public void run() throws  {
                    ConnectionRecord $r7 = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get($r3.asBinder());
                    if ($r7 == null) {
                        Log.w(MediaBrowserServiceCompat.TAG, "removeSubscription for callback that isn't registered id=" + $r1);
                    } else if (!MediaBrowserServiceCompat.this.removeSubscription($r1, $r7, $r2)) {
                        Log.w(MediaBrowserServiceCompat.TAG, "removeSubscription called for " + $r1 + " which is not subscribed");
                    }
                }
            });
        }

        public void getMediaItem(final String $r1, final ResultReceiver $r2) throws  {
            if (!TextUtils.isEmpty($r1) && $r2 != null) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                    public void run() throws  {
                        MediaBrowserServiceCompat.this.performLoadItem($r1, $r2);
                    }
                });
            }
        }

        public void registerCallbacks(final ServiceCallbacks $r1) throws  {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                public void run() throws  {
                    IBinder $r3 = $r1.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove($r3);
                    ConnectionRecord $r1 = new ConnectionRecord();
                    $r1.callbacks = $r1;
                    MediaBrowserServiceCompat.this.mConnections.put($r3, $r1);
                }
            });
        }

        public void unregisterCallbacks(final ServiceCallbacks $r1) throws  {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() {
                public void run() throws  {
                    MediaBrowserServiceCompat.this.mConnections.remove($r1.asBinder());
                }
            });
        }
    }

    private class ServiceImplApi21 implements android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceImplApi21 {
        final ServiceImpl mServiceImpl;

        ServiceImplApi21() throws  {
            this.mServiceImpl = MediaBrowserServiceCompat.this.mHandler.getServiceImpl();
        }

        public void connect(String $r1, Bundle $r2, android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCallbacks $r3) throws  {
            this.mServiceImpl.connect($r1, Binder.getCallingUid(), $r2, new ServiceCallbacksApi21($r3));
        }

        public void disconnect(android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCallbacks $r1) throws  {
            this.mServiceImpl.disconnect(new ServiceCallbacksApi21($r1));
        }

        public void addSubscription(String $r1, android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCallbacks $r2) throws  {
            this.mServiceImpl.addSubscription($r1, null, new ServiceCallbacksApi21($r2));
        }

        public void removeSubscription(String $r1, android.support.v4.media.MediaBrowserServiceCompatApi21.ServiceCallbacks $r2) throws  {
            this.mServiceImpl.removeSubscription($r1, null, new ServiceCallbacksApi21($r2));
        }
    }

    private class ServiceImplApi23 extends ServiceImplApi21 implements android.support.v4.media.MediaBrowserServiceCompatApi23.ServiceImplApi23 {
        private ServiceImplApi23() throws  {
            super();
        }

        public void getMediaItem(String $r1, final ItemCallback $r2) throws  {
            this.mServiceImpl.getMediaItem($r1, new ResultReceiver(MediaBrowserServiceCompat.this.mHandler) {
                protected void onReceiveResult(int $i0, Bundle $r1) throws  {
                    MediaItem $r3 = (MediaItem) $r1.getParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM);
                    Parcel $r4 = null;
                    if ($r3 != null) {
                        Parcel $r5 = Parcel.obtain();
                        $r4 = $r5;
                        $r3.writeToParcel($r5, 0);
                    }
                    $r2.onItemLoaded($i0, $r1, $r4);
                }
            });
        }
    }

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String str, int i, @Nullable Bundle bundle) throws ;

    public abstract void onLoadChildren(@NonNull @Signature({"(", "Ljava/lang/String;", "Landroid/support/v4/media/MediaBrowserServiceCompat$Result", "<", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;>;)V"}) String str, @NonNull @Signature({"(", "Ljava/lang/String;", "Landroid/support/v4/media/MediaBrowserServiceCompat$Result", "<", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;>;)V"}) Result<List<MediaItem>> result) throws ;

    public void onCreate() throws  {
        super.onCreate();
        if (VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaBrowserServiceImplApi23();
        } else if (VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaBrowserServiceImplApi21();
        } else {
            this.mImpl = new MediaBrowserServiceImplBase();
        }
        this.mImpl.onCreate();
    }

    public IBinder onBind(Intent $r1) throws  {
        return this.mImpl.onBind($r1);
    }

    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) throws  {
    }

    public void onLoadChildren(@NonNull @Signature({"(", "Ljava/lang/String;", "Landroid/support/v4/media/MediaBrowserServiceCompat$Result", "<", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;>;", "Landroid/os/Bundle;", ")V"}) String $r1, @NonNull @Signature({"(", "Ljava/lang/String;", "Landroid/support/v4/media/MediaBrowserServiceCompat$Result", "<", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;>;", "Landroid/os/Bundle;", ")V"}) Result<List<MediaItem>> $r2, @NonNull @Signature({"(", "Ljava/lang/String;", "Landroid/support/v4/media/MediaBrowserServiceCompat$Result", "<", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;>;", "Landroid/os/Bundle;", ")V"}) Bundle options) throws  {
        $r2.setFlags(1);
        onLoadChildren($r1, $r2);
    }

    public void onLoadItem(@Signature({"(", "Ljava/lang/String;", "Landroid/support/v4/media/MediaBrowserServiceCompat$Result", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;)V"}) String itemId, @Signature({"(", "Ljava/lang/String;", "Landroid/support/v4/media/MediaBrowserServiceCompat$Result", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;)V"}) Result<MediaItem> $r2) throws  {
        $r2.sendResult(null);
    }

    public void setSessionToken(final Token $r1) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("Session token may not be null.");
        } else if (this.mSession != null) {
            throw new IllegalStateException("The session token has already been set.");
        } else {
            this.mSession = $r1;
            this.mHandler.post(new Runnable() {
                public void run() throws  {
                    for (IBinder $r7 : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                        ConnectionRecord $r8 = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get($r7);
                        try {
                            $r8.callbacks.onConnect($r8.root.getRootId(), $r1, $r8.root.getExtras());
                        } catch (RemoteException e) {
                            Log.w(MediaBrowserServiceCompat.TAG, "Connection for " + $r8.pkg + " is no longer valid.");
                            MediaBrowserServiceCompat.this.mConnections.remove($r7);
                        }
                    }
                }
            });
        }
    }

    @Nullable
    public Token getSessionToken() throws  {
        return this.mSession;
    }

    public void notifyChildrenChanged(@NonNull String $r1) throws  {
        notifyChildrenChangedInternal($r1, null);
    }

    public void notifyChildrenChanged(@NonNull String $r1, @NonNull Bundle $r2) throws  {
        if ($r2 == null) {
            throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
        }
        notifyChildrenChangedInternal($r1, $r2);
    }

    private void notifyChildrenChangedInternal(final String $r1, final Bundle $r2) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        this.mHandler.post(new Runnable() {
            public void run() throws  {
                for (IBinder $r6 : MediaBrowserServiceCompat.this.mConnections.keySet()) {
                    ConnectionRecord $r7 = (ConnectionRecord) MediaBrowserServiceCompat.this.mConnections.get($r6);
                    List<Bundle> $r10 = (List) $r7.subscriptions.get($r1);
                    if ($r10 != null) {
                        for (Bundle $r12 : $r10) {
                            Bundle bundle = $r2;
                            Bundle $r13 = bundle;
                            if (MediaBrowserCompatUtils.hasDuplicatedItems(bundle, $r12)) {
                                MediaBrowserServiceCompat.this.performLoadChildren($r1, $r7, $r12);
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean isValidPackage(String $r1, int $i0) throws  {
        if ($r1 == null) {
            return false;
        }
        for (String $r4 : getPackageManager().getPackagesForUid($i0)) {
            if ($r4.equals($r1)) {
                return true;
            }
        }
        return false;
    }

    private void addSubscription(String $r1, ConnectionRecord $r2, Bundle $r3) throws  {
        List $r6 = (List) $r2.subscriptions.get($r1);
        if ($r6 == null) {
            $r6 = r9;
            ArrayList r9 = new ArrayList();
        }
        for (Bundle areSameOptions : $r6) {
            if (MediaBrowserCompatUtils.areSameOptions($r3, areSameOptions)) {
                return;
            }
        }
        $r6.add($r3);
        $r2.subscriptions.put($r1, $r6);
        performLoadChildren($r1, $r2, $r3);
    }

    private boolean removeSubscription(String $r1, ConnectionRecord $r2, Bundle $r3) throws  {
        boolean $z0 = false;
        List<Bundle> $r6 = (List) $r2.subscriptions.get($r1);
        if ($r6 == null) {
            return false;
        }
        for (Bundle $r8 : $r6) {
            if (MediaBrowserCompatUtils.areSameOptions($r3, $r8)) {
                $z0 = true;
                $r6.remove($r8);
                break;
            }
        }
        if ($r6.size() != 0) {
            return $z0;
        }
        $r2.subscriptions.remove($r1);
        return $z0;
    }

    private void performLoadChildren(String $r1, ConnectionRecord $r2, Bundle $r3) throws  {
        final ConnectionRecord connectionRecord = $r2;
        final String str = $r1;
        final Bundle bundle = $r3;
        C00613 c00613 = new Result<List<MediaItem>>($r1) {
            void onResultSent(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;I)V"}) List<MediaItem> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;I)V"}) int $i0) throws  {
                if (MediaBrowserServiceCompat.this.mConnections.get(connectionRecord.callbacks.asBinder()) == connectionRecord) {
                    List $r12;
                    if (($i0 & 1) != 0) {
                        $r12 = MediaBrowserCompatUtils.applyOptions($r1, bundle);
                    }
                    try {
                        connectionRecord.callbacks.onLoadChildren(str, $r12, bundle);
                    } catch (RemoteException e) {
                        Log.w(MediaBrowserServiceCompat.TAG, "Calling onLoadChildren() failed for id=" + str + " package=" + connectionRecord.pkg);
                    }
                }
            }
        };
        if ($r3 == null) {
            onLoadChildren($r1, c00613);
        } else {
            onLoadChildren($r1, c00613, $r3);
        }
        if (!c00613.isDone()) {
            throw new IllegalStateException("onLoadChildren must call detach() or sendResult() before returning for package=" + $r2.pkg + " id=" + $r1);
        }
    }

    private List<MediaItem> applyOptions(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;"}) List<MediaItem> $r2, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;"}) Bundle $r1) throws  {
        int $i1 = $r1.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int $i2 = $r1.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if ($i1 == -1 && $i2 == -1) {
            return $r2;
        }
        int $i0 = $i2 * ($i1 - 1);
        int $i3 = $i0 + $i2;
        if ($i1 < 1 || $i2 < 1 || $i0 >= $r2.size()) {
            return Collections.emptyList();
        }
        if ($i3 > $r2.size()) {
            $i3 = $r2.size();
        }
        return $r2.subList($i0, $i3);
    }

    private void performLoadItem(String $r1, final ResultReceiver $r2) throws  {
        C00624 $r3 = new Result<MediaItem>($r1) {
            void onResultSent(MediaItem $r1, int flag) throws  {
                Bundle $r2 = new Bundle();
                $r2.putParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM, $r1);
                $r2.send(0, $r2);
            }
        };
        onLoadItem($r1, $r3);
        if (!$r3.isDone()) {
            throw new IllegalStateException("onLoadItem must call detach() or sendResult() before returning for id=" + $r1);
        }
    }
}
