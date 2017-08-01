package com.spotify.android.appremote.internal;

import android.content.Context;
import android.os.AsyncTask;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.ConnectionParams.AuthMethod;
import com.spotify.protocol.client.AppProtocolCommunicator;
import com.spotify.protocol.client.Coding;
import com.spotify.protocol.client.ConnectionDetails;
import com.spotify.protocol.client.ConnectionDetails.Builder;
import com.spotify.protocol.client.RemoteClientConnector;
import com.spotify.protocol.client.RemoteClientConnector.ConnectionCallback;
import com.spotify.protocol.client.RemoteWampClient;
import com.spotify.protocol.client.Result;
import com.spotify.protocol.client.ResultUtils;
import com.spotify.protocol.client.WampCallsOrchestrator;
import com.spotify.protocol.mappers.JsonMapper;
import com.spotify.protocol.types.WelcomeDetails;
import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SdkRemoteClientConnector implements RemoteClientConnector {
    public static final String APP_ID_AUTH = "appid";
    public static final String EXTRA_REDIRECT_URI = "redirect_uri";
    public static final String EXTRA_SCOPES = "scopes";
    public static final String EXTRA_SHOW_AUTH_VIEW = "show_auth_view";
    public static final String REMOTE_CONTROL_SCOPE = "app-remote-control";
    private ConnectionTask mConnectionTask;
    private final Context mContext;
    private final ConnectionParams mParams;
    private RemoteServiceIo mRemoteService;
    private final String mSpotifyPackagename;

    private final class ConnectionTask extends AsyncTask<Void, Void, Result<WelcomeDetails>> {
        private final ConnectionCallback mConnectionCallback;
        private final ConnectionDetails mConnectionDetails;
        private final JsonMapper mJsonMapper;
        private RemoteWampClient mRemoteWampClient;

        private ConnectionTask(ConnectionParams $r2, ConnectionCallback $r3) throws  {
            AsyncTask asyncTask = this;
            this.mConnectionCallback = $r3;
            this.mJsonMapper = $r2.getJsonMapper();
            Builder $r6 = new Builder($r2.getClientId()).setName(SdkRemoteClientConnector.this.mContext.getPackageName()).setImageSize($r2.getImageSize()).setThumbnailSize($r2.getImageSize()).setRequiredFeatures($r2.getRequiredFeatures());
            if ($r2.getAuthMethod() == AuthMethod.APP_ID) {
                HashMap $r4 = new HashMap();
                $r4.put("redirect_uri", $r2.getRedirectUri());
                $r4.put(SdkRemoteClientConnector.EXTRA_SHOW_AUTH_VIEW, String.valueOf($r2.getShowAuthView()));
                $r4.put(SdkRemoteClientConnector.EXTRA_SCOPES, SdkRemoteClientConnector.REMOTE_CONTROL_SCOPE);
                $r6.setAuthMethods(new String[]{"appid"});
                $r6.setAuthId($r2.getClientId());
                $r6.setExtras($r4);
            }
            this.mConnectionDetails = $r6.build();
        }

        protected void onPreExecute() throws  {
            SdkRemoteClientConnector.this.mRemoteService = new RemoteServiceIo(SdkRemoteClientConnector.this.mSpotifyPackagename, SdkRemoteClientConnector.this.mContext);
            this.mRemoteWampClient = new RemoteWampClient(new AppProtocolCommunicator(this.mConnectionDetails, this.mJsonMapper, SdkRemoteClientConnector.this.mRemoteService), new WampCallsOrchestrator());
        }

        protected Result<WelcomeDetails> doInBackground(@Signature({"([", "Ljava/lang/Void;", ")", "Lcom/spotify/protocol/client/Result", "<", "Lcom/spotify/protocol/types/WelcomeDetails;", ">;"}) Void... args) throws  {
            Result $r6 = SdkRemoteClientConnector.this.mRemoteService.connect().await(20, TimeUnit.SECONDS);
            if ($r6.isSuccessful()) {
                return this.mRemoteWampClient.hello(WelcomeDetails.class).await(1, TimeUnit.HOURS);
            }
            return ResultUtils.createErrorResult($r6.getError());
        }

        protected void onPostExecute(@Signature({"(", "Lcom/spotify/protocol/client/Result", "<", "Lcom/spotify/protocol/types/WelcomeDetails;", ">;)V"}) Result<WelcomeDetails> $r1) throws  {
            if ($r1.isSuccessful()) {
                this.mConnectionCallback.onConnected(this.mRemoteWampClient);
            } else {
                this.mConnectionCallback.onConnectionFailed($r1.getError());
            }
            SdkRemoteClientConnector.this.mConnectionTask = null;
        }
    }

    public RemoteServiceIo getRemoteServiceIO() throws  {
        return this.mRemoteService;
    }

    public static SdkRemoteClientConnector create(Context $r0, ConnectionParams $r1, String $r2) throws  {
        Coding.checkNotNull($r0);
        Coding.checkNotNull($r1);
        Coding.checkNotNull($r2);
        return new SdkRemoteClientConnector($r0, $r1, $r2);
    }

    private SdkRemoteClientConnector(Context $r1, ConnectionParams $r2, String $r3) throws  {
        this.mContext = $r1;
        this.mParams = $r2;
        this.mSpotifyPackagename = $r3;
    }

    public void connect(ConnectionCallback $r1) throws  {
        this.mConnectionTask = new ConnectionTask(this.mParams, $r1);
        this.mConnectionTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public void disconnect() throws  {
        if (this.mRemoteService != null) {
            this.mRemoteService.disconnect();
        }
    }
}
