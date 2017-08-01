package com.spotify.android.appremote.api;

import android.content.Context;
import com.spotify.android.appremote.api.Connector.ConnectionListener;
import com.spotify.android.appremote.api.error.AuthenticationFailedException;
import com.spotify.android.appremote.api.error.CouldNotFindSpotifyApp;
import com.spotify.android.appremote.api.error.LoggedOutException;
import com.spotify.android.appremote.api.error.NotLoggedInException;
import com.spotify.android.appremote.api.error.OfflineModeException;
import com.spotify.android.appremote.api.error.UnsupportedFeatureVersionException;
import com.spotify.android.appremote.api.error.UserNotAuthorizedException;
import com.spotify.android.appremote.internal.ContentApiImpl;
import com.spotify.android.appremote.internal.ImagesApiImpl;
import com.spotify.android.appremote.internal.MotionStateApiImpl;
import com.spotify.android.appremote.internal.PlayerApiImpl;
import com.spotify.android.appremote.internal.SdkRemoteClientConnector;
import com.spotify.android.appremote.internal.SdkRemoteClientConnectorFactory;
import com.spotify.android.appremote.internal.SpotifyAppRemoteIsConnectedRule;
import com.spotify.android.appremote.internal.SpotifyLocator;
import com.spotify.android.appremote.internal.StrictRemoteClient;
import com.spotify.android.appremote.internal.UserApiImpl;
import com.spotify.protocol.AppProtocol.ErrorUri;
import com.spotify.protocol.client.Debug;
import com.spotify.protocol.client.ErrorCallback;
import com.spotify.protocol.client.RemoteClient;
import com.spotify.protocol.client.RemoteClientConnector.ConnectionCallback;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.client.Subscription.EventCallback;
import com.spotify.protocol.client.error.RemoteClientException;
import com.spotify.protocol.error.SpotifyAppRemoteException;
import com.spotify.protocol.types.UserStatus;

final class LocalConnector implements Connector {
    private final SdkRemoteClientConnectorFactory mSdkRemoteClientConnectorFactory;
    private final SpotifyLocator mSpotifyLocator;

    public LocalConnector(SpotifyLocator $r1, SdkRemoteClientConnectorFactory $r2) throws  {
        this.mSpotifyLocator = $r1;
        this.mSdkRemoteClientConnectorFactory = $r2;
    }

    public void connect(Context $r1, ConnectionParams $r2, final ConnectionListener $r3) throws  {
        if (this.mSpotifyLocator.isSpotifyInstalled($r1)) {
            final SdkRemoteClientConnector $r8 = this.mSdkRemoteClientConnectorFactory.newConnector($r1, $r2, this.mSpotifyLocator.getSpotifyBestPackageName($r1));
            $r8.connect(new ConnectionCallback() {

                class C10722 implements ErrorCallback {
                    C10722() throws  {
                    }

                    public void onError(Throwable $r1) throws  {
                        $r3.onFailure($r1);
                    }
                }

                public void onConnected(RemoteClient $r1) throws  {
                    StrictRemoteClient $r3 = new StrictRemoteClient($r1);
                    final SpotifyAppRemote spotifyAppRemote = new SpotifyAppRemote($r3, new PlayerApiImpl($r3), new ImagesApiImpl($r3), new UserApiImpl($r3), new MotionStateApiImpl($r3), new ContentApiImpl($r3), $r8, $r3);
                    spotifyAppRemote.setConnected(true);
                    $r3.addRule(new SpotifyAppRemoteIsConnectedRule(spotifyAppRemote));
                    SdkRemoteClientConnector $r7 = $r8;
                    $r7.getRemoteServiceIO().setOnConnectionTerminatedListener(spotifyAppRemote.getOnConnectionTerminatedListener());
                    Subscription $r15 = spotifyAppRemote.getUserApi().subscribeToUserStatus();
                    $r15.setEventCallback(new EventCallback<UserStatus>() {
                        public void onEvent(UserStatus $r1) throws  {
                            Debug.m32d("LoggedIn:%s", Boolean.valueOf($r1.isLoggedIn()));
                            if ($r1.isLoggedIn()) {
                                $r3.onConnected(spotifyAppRemote);
                            } else {
                                $r3.onFailure(new LoggedOutException());
                            }
                        }
                    });
                    $r15.setErrorCallback(new C10722());
                }

                public void onConnectionFailed(Throwable $r1) throws  {
                    Debug.m33d($r1, "Connection failed.", new Object[0]);
                    $r8.disconnect();
                    $r3.onFailure(LocalConnector.this.asAppRemoteException($r1));
                }
            });
            return;
        }
        $r3.onFailure(new CouldNotFindSpotifyApp());
    }

    private Throwable asAppRemoteException(Throwable $r1) throws  {
        String $r3 = $r1 instanceof RemoteClientException ? ((RemoteClientException) $r1).getReasonUri() : null;
        String $r4 = $r1.getMessage();
        if (ErrorUri.ERROR_AUTHENTICATION_FAILED.equals($r3)) {
            return new AuthenticationFailedException($r4, $r1);
        }
        if (ErrorUri.ERROR_FEATURE_VERSION_MISMATCH.equals($r3)) {
            return new UnsupportedFeatureVersionException($r4, $r1);
        }
        if (ErrorUri.ERROR_OFFLINE_MODE_ACTIVE.equals($r3)) {
            return new OfflineModeException($r4, $r1);
        }
        if (ErrorUri.ERROR_USER_NOT_AUTHORIZED.equals($r3)) {
            return new UserNotAuthorizedException($r4, $r1);
        }
        if (ErrorUri.ERROR_NOT_LOGGED_IN.equals($r3)) {
            return new NotLoggedInException($r4, $r1);
        }
        return new SpotifyAppRemoteException($r4, $r1);
    }

    public void disconnect(SpotifyAppRemote $r1) throws  {
        if ($r1 != null) {
            $r1.disconnect();
        }
    }
}
