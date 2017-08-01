package com.spotify.android.appremote.api;

import android.content.Context;
import com.spotify.android.appremote.api.Connector.ConnectionListener;
import com.spotify.android.appremote.api.error.SpotifyConnectionTerminatedException;
import com.spotify.android.appremote.internal.AppRemoteDebugImpl;
import com.spotify.android.appremote.internal.RemoteServiceIo.OnConnectionTerminatedListener;
import com.spotify.android.appremote.internal.SdkRemoteClientConnectorFactory;
import com.spotify.android.appremote.internal.SpotifyLocator;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.Debug;
import com.spotify.protocol.client.RemoteClient;
import com.spotify.protocol.client.RemoteClientConnector;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.Item;
import dalvik.annotation.Signature;

public class SpotifyAppRemote {
    public static final Connector CONNECTOR = new LocalConnector(SPOTIFY_LOCATOR, new SdkRemoteClientConnectorFactory());
    private static final SpotifyLocator SPOTIFY_LOCATOR = new SpotifyLocator();
    private static boolean sIsDebug;
    private final ConnectionListener mConnectionListener;
    private final ContentApi mContentApi;
    private final ImagesApi mImagesApi;
    private volatile boolean mIsConnected;
    private final MotionStateApi mMotionStateApi;
    private final OnConnectionTerminatedListener mOnConnectionTerminatedListener = new C10741();
    private final PlayerApi mPlayerApi;
    private final RemoteClient mRemoteClient;
    private final RemoteClientConnector mRemoteClientConnector;
    private final UserApi mUserApi;

    class C10741 implements OnConnectionTerminatedListener {
        C10741() throws  {
        }

        public void onTerminated() throws  {
            SpotifyAppRemote.this.mConnectionListener.onFailure(new SpotifyConnectionTerminatedException());
        }
    }

    static {
        AppRemoteDebugImpl $r0 = new AppRemoteDebugImpl();
        Debug.setLogger($r0);
        Debug.setAssertion($r0);
    }

    SpotifyAppRemote(RemoteClient $r1, PlayerApi $r2, ImagesApi $r3, UserApi $r4, MotionStateApi $r5, ContentApi $r6, RemoteClientConnector $r7, ConnectionListener $r8) throws  {
        this.mRemoteClient = $r1;
        this.mPlayerApi = $r2;
        this.mImagesApi = $r3;
        this.mUserApi = $r4;
        this.mMotionStateApi = $r5;
        this.mContentApi = $r6;
        this.mRemoteClientConnector = $r7;
        this.mConnectionListener = $r8;
    }

    public ImagesApi getImagesApi() throws  {
        return this.mImagesApi;
    }

    public PlayerApi getPlayerApi() throws  {
        return this.mPlayerApi;
    }

    public UserApi getUserApi() throws  {
        return this.mUserApi;
    }

    public MotionStateApi getMotionStateApi() throws  {
        return this.mMotionStateApi;
    }

    public ContentApi getContentApi() throws  {
        return this.mContentApi;
    }

    public boolean isConnected() throws  {
        return this.mIsConnected;
    }

    void setConnected(boolean $z0) throws  {
        this.mIsConnected = $z0;
    }

    OnConnectionTerminatedListener getOnConnectionTerminatedListener() throws  {
        return this.mOnConnectionTerminatedListener;
    }

    void disconnect() throws  {
        this.mIsConnected = false;
        this.mRemoteClient.goodbye();
        this.mRemoteClientConnector.disconnect();
    }

    public static boolean isSpotifyInstalled(Context $r0) throws  {
        return SPOTIFY_LOCATOR.isSpotifyInstalled($r0);
    }

    public static void setDebugMode(boolean $z0) throws  {
        sIsDebug = $z0;
    }

    public static boolean isDebugMode() throws  {
        return sIsDebug;
    }

    public <T extends Item, S extends Item> CallResult<T> call(@Signature({"<T::", "Lcom/spotify/protocol/types/Item;", "S::", "Lcom/spotify/protocol/types/Item;", ">(", "Ljava/lang/String;", "TS;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) String $r1, @Signature({"<T::", "Lcom/spotify/protocol/types/Item;", "S::", "Lcom/spotify/protocol/types/Item;", ">(", "Ljava/lang/String;", "TS;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) S $r2, @Signature({"<T::", "Lcom/spotify/protocol/types/Item;", "S::", "Lcom/spotify/protocol/types/Item;", ">(", "Ljava/lang/String;", "TS;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Class<T> $r3) throws  {
        return this.mRemoteClient.call($r1, $r2, $r3);
    }

    public <T extends Item> Subscription<T> subscribe(@Signature({"<T::", "Lcom/spotify/protocol/types/Item;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/Subscription", "<TT;>;"}) String $r1, @Signature({"<T::", "Lcom/spotify/protocol/types/Item;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/Subscription", "<TT;>;"}) Class<T> $r2) throws  {
        return this.mRemoteClient.subscribe($r1, $r2);
    }
}
