package com.waze.audioextension.spotify;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.util.Log;
import android.widget.ImageView;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector.ConnectionListener;
import com.spotify.android.appremote.api.ContentApi.ContentType;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.api.error.UserNotAuthorizedException;
import com.spotify.android.appremote.internal.SdkRemoteClientConnector;
import com.spotify.protocol.client.CallResult.ResultCallback;
import com.spotify.protocol.client.ErrorCallback;
import com.spotify.protocol.client.Subscription.EventCallback;
import com.spotify.protocol.types.Empty;
import com.spotify.protocol.types.ImageUri;
import com.spotify.protocol.types.LibraryState;
import com.spotify.protocol.types.ListItem;
import com.spotify.protocol.types.ListItems;
import com.spotify.protocol.types.PlayerContext;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest.Builder;
import com.spotify.sdk.android.authentication.AuthenticationResponse.Type;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.strings.DisplayStrings;
import com.waze.view.popups.SpotifyPopup;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SpotifyManager {
    private static final String CLIENT_ID = "b3b13bd9904a4d6da4116e9c0d17be6d";
    public static final boolean NEW_AUTH_METHOD = false;
    private static String OPEN_SETTINGS_URL = "waze://?a=spotify_settings";
    public static final String PACKAGE_ID = "com.spotify.music";
    public static final String PACKAGE_ID_DEBUD = "com.spotify.music.debug";
    private static final String REDIRECT_URI = "waze://spotifysdk/";
    private static SpotifyManager ourInstance = new SpotifyManager();
    private final int MAX_PLAYLISTS = 11;
    private final String TAG = "SpotifyManager";
    private int currentPlayList = 0;
    private boolean mAfterAuthorization = false;
    private boolean mAuthFlowLocked = true;
    private boolean mAuthorizeOnConnect = false;
    ArrayList<Bitmap> mBitmaps = new ArrayList(11);
    private SpotifyRunningBroadcastReceiver mBroadcastReceiver;
    private ConfigManager mCm;
    private ListItem[] mContentList;
    private Track mCurrenttrack = null;
    private boolean mDisconnected = false;
    public boolean mInitialized = false;
    private boolean mIsConnected = false;
    private boolean mIsPlaying = false;
    private ImageUri mLastImageUri = null;
    private boolean mNeedsAuthorization = false;
    private boolean mOpenAppLocked = true;
    private int mPlayListIndex = -1;
    private String[] mPlayListsTitles = null;
    private PlayerContext mPlayerContext = null;
    private boolean mRegisteredForResume = false;
    private SpotifyAppRemote mSpotifyAppRemote;
    private boolean mTryingToConnect = false;

    class C13431 implements ConnectionListener {
        C13431() throws  {
        }

        public void onConnected(SpotifyAppRemote $r1) throws  {
            SpotifyManager.this.mSpotifyAppRemote = $r1;
            SpotifyManager.this.onConnectedSuccess();
            SpotifyManager.this.mTryingToConnect = false;
        }

        public void onFailure(Throwable $r1) throws  {
            SpotifyManager.this.mTryingToConnect = false;
            if ($r1 instanceof UserNotAuthorizedException) {
                SpotifyManager.logError("Connection failed: " + $r1);
                if (SpotifyManager.this.mNeedsAuthorization) {
                    SpotifyManager.this.onAuthenticationDeclined($r1.getMessage());
                    return;
                }
                SpotifyManager.this.mNeedsAuthorization = true;
                if (AppService.getMainActivity() != null && AppService.getMainActivity().getLayoutMgr() != null) {
                    AppService.getMainActivity().getLayoutMgr().showSpotifyButton();
                    return;
                }
                return;
            }
            SpotifyManager.logError("Connection failed: " + $r1);
            SpotifyManager.this.onDisconnected();
        }
    }

    class C13462 implements Runnable {
        C13462() throws  {
        }

        public void run() throws  {
            SpotifyManager.this.fetchSugegstedContent();
        }
    }

    class C13473 implements ErrorCallback {
        C13473() throws  {
        }

        public void onError(Throwable $r1) throws  {
            SpotifyManager.logError("subscribeToPlayerContext " + $r1.getMessage());
        }
    }

    class C13484 implements EventCallback<PlayerContext> {
        C13484() throws  {
        }

        public void onEvent(PlayerContext $r1) throws  {
            SpotifyManager.logDebug("Player Context = " + $r1);
            SpotifyPopup.getInstance().setPlayListName($r1.title);
            SpotifyManager.this.mPlayerContext = $r1;
            SpotifyManager.this.highlightPlayList();
        }
    }

    class C13547 implements EventCallback<PlayerState> {

        class C13521 implements ErrorCallback {
            C13521() throws  {
            }

            public void onError(Throwable $r1) throws  {
                SpotifyManager.logError("Unsave " + $r1.getMessage());
            }
        }

        class C13532 implements ResultCallback<LibraryState> {
            C13532() throws  {
            }

            public void onResult(LibraryState $r1) throws  {
                SpotifyPopup.getInstance().setSaved($r1.isAdded);
            }
        }

        C13547() throws  {
        }

        public void onEvent(PlayerState $r1) throws  {
            boolean $z0 = true;
            Track $r2 = $r1.track;
            SpotifyManager.this.mCurrenttrack = $r2;
            SpotifyManager.logDebug("subscribeToPlayerState result playerState=" + $r1);
            if ($r2 != null) {
                SpotifyManager.logDebug("subscribeToPlayerState changed " + $r2.name);
                SpotifyPopup.getInstance().setSongName($r2.name);
                SpotifyPopup.getInstance().setAlbumName($r2.artist.name);
                SpotifyPopup.getInstance().setmTrackDuration($r1.track.duration);
                SpotifyPopup.getInstance().setmCurrentPlayingPos($r1.playbackPosition);
                SpotifyPopup.getInstance().updatePlayButton(!$r1.isPaused);
                SpotifyManager $r3 = SpotifyManager.this;
                if ($r1.isPaused) {
                    $z0 = false;
                }
                $r3.mIsPlaying = $z0;
                if (SpotifyManager.this.mIsPlaying) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_PLAYING);
                } else {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_PAUSED);
                }
                SpotifyPopup.getInstance().setCanSkipNext($r1.playbackRestrictions.canSkipNext);
                SpotifyPopup.getInstance().setCanSkipPrevious($r1.playbackRestrictions.canSkipPrev);
                SpotifyPopup.getInstance().showSaveButton(SpotifyManager.this.canSaveTrack($r2));
                SpotifyManager.this.getImageUri($r2.imageUri, 0);
                SpotifyManager.this.mSpotifyAppRemote.getUserApi().getLibraryState($r2.uri).setResultCallback(new C13532()).setErrorCallback(new C13521());
                return;
            }
            SpotifyPopup.getInstance().setAlbumName(null);
        }
    }

    class C13558 implements Runnable {
        C13558() throws  {
        }

        public void run() throws  {
            SpotifyManager.this.onAppBecameActive();
        }
    }

    class C13569 implements Runnable {
        C13569() throws  {
        }

        public void run() throws  {
            if (AppService.getMainActivity() != null && AppService.getMainActivity().isRunning()) {
                SpotifyManager.logDebug("onAppBecameActive getMainActivity().isRunning");
                SpotifyManager.this.connect(false, true);
                SpotifyManager.this.mRegisteredForResume = false;
            } else if (!SpotifyManager.this.mRegisteredForResume) {
                SpotifyManager.logDebug("onAppBecameActive registerOnResumeEvent");
                MainActivity.registerOnResumeEvent(this);
                SpotifyManager.this.mRegisteredForResume = true;
            }
        }
    }

    protected class onNotDriverAuthFlow implements OnClickListener {
        protected onNotDriverAuthFlow() throws  {
        }

        public void onClick(DialogInterface $r1, int $i0) throws  {
            $r1.cancel();
            if ($i0 == 0) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_PASSENGER_AUTH);
                Logger.m43w("SpotifyManager: onNotDriverAuthFlow selected passenger");
                SpotifyManager.this.mAuthFlowLocked = false;
                SpotifyManager.this.authorize();
            }
        }
    }

    protected class onNotDriverOpeningApp implements OnClickListener {
        protected onNotDriverOpeningApp() throws  {
        }

        public void onClick(DialogInterface $r1, int $i0) throws  {
            $r1.cancel();
            if ($i0 == 0) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_PASSENGER_APP);
                Logger.m43w("SpotifyManager: onNotDriverOpeningApp selected passenger");
                SpotifyManager.this.mOpenAppLocked = false;
                SpotifyManager.this.openApp();
            }
        }
    }

    public boolean spotifyAppRunning() throws  {
        return true;
    }

    private SpotifyManager() throws  {
        init();
    }

    public static SpotifyManager getInstance() throws  {
        return ourInstance;
    }

    public void setAuthorizeOnConnect(boolean $z0) throws  {
        logDebug("setAuthorizeOnConnect = " + $z0);
        this.mAuthorizeOnConnect = $z0;
    }

    public boolean SpotifyAvailable() throws  {
        return featureEnabled() && userEnabled() && appInstalled();
    }

    public void init() throws  {
        logDebug("init");
        this.mCm = ConfigManager.getInstance();
        if (featureEnabled() && userEnabled() && appInstalled()) {
            if (spotifyAppRunning()) {
                connect(this.mAuthorizeOnConnect, true);
            } else {
                this.mBroadcastReceiver = new SpotifyRunningBroadcastReceiver();
                AppService.getAppContext().registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.spotify.music.active"));
            }
            this.mInitialized = true;
            return;
        }
        logDebug("init - featureEnabled() =" + featureEnabled() + " userEnabled() = " + userEnabled() + "  appInstalled() = " + appInstalled());
    }

    public boolean shouldShowZeroSpeed() throws  {
        return featureEnabled() && userEnabled() && isConnected() && this.mIsPlaying;
    }

    public void term() throws  {
        logDebug("term");
        if (this.mIsConnected) {
            disconnect();
            this.mSpotifyAppRemote = null;
        }
    }

    public static boolean appInstalled() throws  {
        try {
            return SpotifyAppRemote.isSpotifyInstalled(AppService.getAppContext());
        } catch (Exception $r0) {
            logError("appInstalled" + $r0.getLocalizedMessage());
            return false;
        }
    }

    public boolean featureEnabled() throws  {
        if (VERSION.SDK_INT > 17) {
            return this.mCm.getConfigValueBool(430);
        }
        Logger.m43w("Disabling Spotify is SDK is lower than 18, SDK ver=" + VERSION.SDK_INT);
        return false;
    }

    public boolean userEnabled() throws  {
        return this.mCm.getConfigValueBool(435);
    }

    private boolean ShouldShowDriverDistractionWhenOpenningApp() throws  {
        return this.mCm.getConfigValueBool(431);
    }

    private boolean shouldShowDriverDistractionAuthFlow() throws  {
        return this.mCm.getConfigValueBool(432);
    }

    public void setUserEnable(boolean $z0) throws  {
        String $r2;
        logDebug("setUserEnable = " + $z0);
        this.mCm.setConfigValueBool(435, $z0);
        if ($z0) {
            $r2 = AnalyticsEvents.ANALYTICS_EVENT_ON;
        } else {
            $r2 = AnalyticsEvents.ANALYTICS_EVENT_OFF;
        }
        Analytics.log("SPOTIFY_SETTINGS", AnalyticsEvents.ANALYTICS_EVENT_INFO_CHANGED_TO, $r2);
        if ($z0) {
            connect(true, false);
            return;
        }
        disconnect();
        SpotifyPopup.getInstance().dismiss();
    }

    public boolean isConnected() throws  {
        return this.mIsConnected;
    }

    public boolean shouldShowSpotifyButton() throws  {
        if (!userEnabled()) {
            return false;
        }
        if (!featureEnabled()) {
            return false;
        }
        if (isConnected() || needsAuthorization()) {
            return !this.mDisconnected;
        } else {
            return false;
        }
    }

    public boolean needsAuthorization() throws  {
        return this.mNeedsAuthorization;
    }

    public void authorize() throws  {
        if (!isConnected()) {
            this.mAfterAuthorization = true;
            if (NativeManager.getInstance().isMovingNTV() && this.mAuthFlowLocked && shouldShowDriverDistractionAuthFlow()) {
                showDriverDistractionAuthFlow();
            } else {
                connect(true, false);
            }
        }
    }

    public void authorizeUsingSSO() throws  {
        Builder $r1 = new Builder(CLIENT_ID, Type.TOKEN, REDIRECT_URI);
        $r1.setScopes(new String[]{SdkRemoteClientConnector.REMOTE_CONTROL_SCOPE});
        AuthenticationClient.openLoginActivity(AppService.getMainActivity(), 1337, $r1.build());
    }

    public void onAuthorizationResponseSSO(int $i0, Intent $r1) throws  {
        switch (AuthenticationClient.getResponse($i0, $r1).getType()) {
            case TOKEN:
                this.mNeedsAuthorization = false;
                connect(true, false);
                return;
            case ERROR:
                onAuthenticationDeclined("Declined");
                return;
            default:
                this.mNeedsAuthorization = true;
                return;
        }
    }

    private void connect(boolean $z0, boolean $z1) throws  {
        if (!$z0) {
            this.mNeedsAuthorization = false;
        }
        int $i0 = 0;
        logDebug("connect ");
        if (AppService.getMainActivity() != null) {
            $i0 = (int) AppService.getMainActivity().getResources().getDimension(C1283R.dimen.spotify_image_size);
        }
        if (!$z1 || !MainActivity.IsSDKBound() || AppService.getMainActivity() == null || AppService.getMainActivity().isSpotifyBound()) {
            if (this.mSpotifyAppRemote != null && this.mSpotifyAppRemote.isConnected()) {
                SpotifyAppRemote.CONNECTOR.disconnect(this.mSpotifyAppRemote);
                this.mSpotifyAppRemote = null;
            }
            this.mTryingToConnect = true;
            SpotifyAppRemote.CONNECTOR.connect(AppService.getAppContext(), new ConnectionParams.Builder(CLIENT_ID).setRedirectUri(REDIRECT_URI).setPreferredImageSize($i0).showAuthView($z0).build(), new C13431());
            return;
        }
        logError("Not connecting... SDK is already bounds");
        this.mDisconnected = true;
    }

    public void onAuthenticationDeclined(String $r1) throws  {
        logError("Connection authrization declined: " + $r1);
        this.mNeedsAuthorization = false;
        if (!(AppService.getMainActivity() == null || AppService.getMainActivity().getLayoutMgr() == null)) {
            AppService.getMainActivity().getLayoutMgr().hideSpotifyButton();
        }
        this.mCm.setConfigValueBool(435, false);
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_ACCESS_DECLINED);
        disconnect();
        SpotifyPopup.getInstance().dismiss();
        $r1 = String.format(DisplayStrings.displayString(DisplayStrings.DS_SPOTIFY_TURNED_OFF_TXT), new Object[]{OPEN_SETTINGS_URL});
        MsgBox.getInstance().OpenMessageBoxTimeoutCb(DisplayStrings.displayString(DisplayStrings.DS_SPOTIFY_TURNED_OFF_TITLE), $r1, -1, 0);
    }

    public void onConnectedSuccess() throws  {
        this.mIsConnected = true;
        this.mDisconnected = false;
        logDebug("onConnectedSuccess");
        if (this.mNeedsAuthorization) {
            this.mNeedsAuthorization = false;
            if (!(AppService.getMainActivity() == null || AppService.getMainActivity().getLayoutMgr() == null)) {
                AppService.getMainActivity().getLayoutMgr().openSpotifyPopup();
            }
        }
        this.mNeedsAuthorization = false;
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_CONNECTED);
        SpotifyPopup.getInstance().init();
        SpotifyPopup.getInstance().setSongName("");
        SpotifyPopup.getInstance().setAlbumName("");
        subscribeToPlayerState();
        subscribeToContext();
        AppService.Post(new C13462(), 200);
        if (AppService.getMainActivity() != null && AppService.getMainActivity().getLayoutMgr() != null) {
            AppService.getMainActivity().getLayoutMgr().showSpotifyButton();
        }
    }

    private void highlightPlayList() throws  {
        if (this.mPlayerContext != null && this.mContentList != null && this.mPlayerContext.uri != null && this.mPlayerContext.uri.length() != 0) {
            this.mPlayListIndex = -1;
            int $i1 = 0;
            logDebug("highlightPlayList CurrentContext = " + this.mPlayerContext);
            for (ListItem $r1 : this.mContentList) {
                try {
                    if ($r1.uri.contains(URLEncoder.encode(this.mPlayerContext.uri, "UTF-8"))) {
                        logDebug("highlightPlayList found playlist  = " + $r1);
                        this.mPlayListIndex = $i1;
                    }
                    $i1++;
                } catch (UnsupportedEncodingException $r2) {
                    logDebug("highlightPlayList - Unsupported encoding excaption getting google url string: " + $r2.toString());
                }
            }
            SpotifyPopup.getInstance().updateSelected();
        }
    }

    public int getPlayListIndex() throws  {
        return this.mPlayListIndex;
    }

    private void subscribeToContext() throws  {
        this.mSpotifyAppRemote.getPlayerApi().subscribeToPlayerContext().setEventCallback(new C13484()).setErrorCallback(new C13473());
    }

    private void getImageUri(final ImageUri $r1, final int $i0) throws  {
        if ((this.mLastImageUri == null || !this.mLastImageUri.equals($r1)) && $r1 != null && this.mSpotifyAppRemote != null && this.mSpotifyAppRemote.getImagesApi() != null) {
            logDebug("getTrackImage getImagesApi " + $r1 + "  (retryCount=" + $i0 + ")");
            this.mSpotifyAppRemote.getImagesApi().getImage($r1).setResultCallback(new ResultCallback<Bitmap>() {
                public void onResult(Bitmap $r1) throws  {
                    SpotifyManager.logDebug("getTrackImage success  (retryCount=" + $i0 + ")");
                    SpotifyPopup.getInstance().setImage($r1);
                    SpotifyManager.this.mLastImageUri = $r1;
                }
            }).setErrorCallback(new ErrorCallback() {

                class C13491 implements Runnable {
                    C13491() throws  {
                    }

                    public void run() throws  {
                        SpotifyManager.this.getImageUri($r1, $i0 + 1);
                    }
                }

                public void onError(Throwable $r1) throws  {
                    SpotifyManager.logError("getTrackImage getImage " + $r1.getMessage() + "  (retryCount=" + $i0 + ")");
                    if ($i0 <= 2) {
                        AppService.Post(new C13491(), 500);
                    }
                }
            });
        }
    }

    private boolean isPodcast(Track $r1) throws  {
        if (!$r1.uri.contains("spotify:episode")) {
            return false;
        }
        logDebug("Track " + $r1 + " is podcast");
        return true;
    }

    private boolean canSaveTrack(Track $r1) throws  {
        if ($r1.uri == null) {
            return false;
        }
        return !isPodcast($r1);
    }

    private void subscribeToPlayerState() throws  {
        this.mSpotifyAppRemote.getPlayerApi().subscribeToPlayerState().setEventCallback(new C13547());
    }

    public void onAppBecameActive() throws  {
        logDebug("onAppBecameActive ");
        if (!this.mIsConnected && !this.mNeedsAuthorization) {
            if (this.mBroadcastReceiver == null || AppService.getAppContext() == null) {
                this.mBroadcastReceiver = null;
            } else {
                AppService.getAppContext().unregisterReceiver(this.mBroadcastReceiver);
                this.mBroadcastReceiver = null;
            }
            if (AppService.getMainActivity() == null) {
                logError("onAppBecameActive getMainActivity() == null");
                AppService.Post(new C13558(), 2000);
                return;
            }
            AppService.Post(new C13569());
        }
    }

    public void onDisconnected() throws  {
        logDebug("onDisconnected ");
        this.mDisconnected = true;
        this.mIsConnected = false;
        if (!(AppService.getMainActivity() == null || AppService.getMainActivity().getLayoutMgr() == null)) {
            AppService.getMainActivity().getLayoutMgr().hideSpotifyButton();
        }
        if (this.mBroadcastReceiver == null) {
            this.mBroadcastReceiver = new SpotifyRunningBroadcastReceiver();
        }
        if (AppService.getAppContext() != null) {
            AppService.getAppContext().registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.spotify.music.active"));
        }
        SpotifyPopup.getInstance().dismiss();
    }

    public void disconnect() throws  {
        this.mDisconnected = true;
        logDebug("disconnect ");
        SpotifyAppRemote.CONNECTOR.disconnect(this.mSpotifyAppRemote);
        onDisconnected();
    }

    public void playNext() throws  {
        logDebug("playNext ");
        if (this.mSpotifyAppRemote == null || this.mSpotifyAppRemote.getPlayerApi() == null) {
            logError("play playNext is null");
            return;
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_BUTTON_PRESED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SPOTIFY_NEXT);
        this.mSpotifyAppRemote.getPlayerApi().skipNext().setResultCallback(new ResultCallback<Empty>() {
            public void onResult(Empty data) throws  {
                SpotifyManager.logDebug("Skip next successful");
            }
        }).setErrorCallback(new ErrorCallback() {
            public void onError(Throwable $r1) throws  {
                SpotifyManager.logError("PlayNext " + $r1.getMessage());
            }
        });
    }

    public void playPrevious() throws  {
        logDebug("playPrevious ");
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_BUTTON_PRESED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SPOTIFY_PREVIOUS);
        if (this.mSpotifyAppRemote == null || this.mSpotifyAppRemote.getPlayerApi() == null) {
            logError("playPrevious getPlayerApi is null");
        } else {
            this.mSpotifyAppRemote.getPlayerApi().skipPrevious().setResultCallback(new ResultCallback<Empty>() {
                public void onResult(Empty data) throws  {
                    SpotifyManager.logDebug("Skip previous successful");
                }
            }).setErrorCallback(new ErrorCallback() {
                public void onError(Throwable $r1) throws  {
                    SpotifyManager.logError("PlayPrevious " + $r1.getMessage());
                }
            });
        }
    }

    public void pause() throws  {
        logDebug("pause ");
        if (this.mSpotifyAppRemote == null || this.mSpotifyAppRemote.getPlayerApi() == null) {
            logError("pause getPlayerApi is null");
            return;
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_BUTTON_PRESED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SPOTIFY_PAUSE);
        this.mSpotifyAppRemote.getPlayerApi().pause().setResultCallback(new ResultCallback<Empty>() {
            public void onResult(Empty result) throws  {
                SpotifyManager.logDebug("Pause successful");
            }
        }).setErrorCallback(new ErrorCallback() {
            public void onError(Throwable $r1) throws  {
                SpotifyManager.logError("Pause " + $r1.getMessage());
            }
        });
    }

    public void play() throws  {
        logDebug("play ");
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_BUTTON_PRESED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SPOTIFY_PLAY);
        if (this.mSpotifyAppRemote == null || this.mSpotifyAppRemote.getPlayerApi() == null) {
            logError("play getPlayerApi is null");
        } else {
            this.mSpotifyAppRemote.getPlayerApi().resume().setResultCallback(new ResultCallback<Empty>() {
                public void onResult(Empty result) throws  {
                    SpotifyManager.logDebug("Resume successful");
                }
            }).setErrorCallback(new ErrorCallback() {
                public void onError(Throwable $r1) throws  {
                    SpotifyManager.logError("Play " + $r1.getMessage());
                }
            });
        }
    }

    public void save() throws  {
        logDebug("save ");
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_BUTTON_PRESED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SPOTIFY_ADD_TO_PLAYLIST);
        if (this.mCurrenttrack != null && this.mSpotifyAppRemote != null && this.mSpotifyAppRemote.getUserApi() != null) {
            this.mSpotifyAppRemote.getUserApi().addToLibrary(this.mCurrenttrack.uri).setResultCallback(new ResultCallback<Empty>() {
                public void onResult(Empty data) throws  {
                    SpotifyManager.logDebug("Add to collection successful");
                }
            }).setErrorCallback(new ErrorCallback() {
                public void onError(Throwable $r1) throws  {
                    SpotifyManager.logError("Save " + $r1.getMessage());
                }
            });
        }
    }

    public void unsave() throws  {
        logDebug("unsave ");
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_BUTTON_PRESED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SPOTIFY_REMOVE_FROM_PLAYLIST);
        if (this.mCurrenttrack != null && this.mSpotifyAppRemote != null && this.mSpotifyAppRemote.getUserApi() != null) {
            this.mSpotifyAppRemote.getUserApi().removeFromLibrary(this.mCurrenttrack.uri).setResultCallback(new ResultCallback<Empty>() {
                public void onResult(Empty data) throws  {
                    SpotifyManager.logDebug("Remove from collection successful");
                }
            }).setErrorCallback(new ErrorCallback() {
                public void onError(Throwable $r1) throws  {
                    SpotifyManager.logError("Unsave " + $r1.getMessage());
                }
            });
        }
    }

    public void openApp() throws  {
        logDebug("openApp ");
        if (NativeManager.getInstance().isMovingNTV() && this.mOpenAppLocked && ShouldShowDriverDistractionWhenOpenningApp()) {
            showDriverDistractionOpeningApp();
            return;
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_BUTTON_PRESED, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SPOTIFY_GO_TO_APP);
        if (AppService.getMainActivity() != null && !AppService.getMainActivity().openSpotifyApp()) {
            Intent $r4 = AppService.getMainActivity().getPackageManager().getLaunchIntentForPackage(PACKAGE_ID);
            if ($r4 != null) {
                AppService.getMainActivity().startActivity($r4);
                return;
            }
            $r4 = AppService.getMainActivity().getPackageManager().getLaunchIntentForPackage(PACKAGE_ID_DEBUD);
            if ($r4 != null) {
                AppService.getMainActivity().startActivity($r4);
                return;
            }
            $r4 = AppService.getMainActivity().getPackageManager().getLaunchIntentForPackage("com.spotify.music.canary");
            if ($r4 != null) {
                AppService.getMainActivity().startActivity($r4);
            }
        }
    }

    public void fetchSugegstedContent() throws  {
        logDebug("fetchSugegstedContent ");
        if (this.mSpotifyAppRemote != null && this.mSpotifyAppRemote.getContentApi() != null) {
            this.mSpotifyAppRemote.getContentApi().getRecommendedContentItems(ContentType.NAVIGATION).setResultCallback(new ResultCallback<ListItems>() {

                class C13441 implements ErrorCallback {
                    C13441() throws  {
                    }

                    public void onError(Throwable $r1) throws  {
                        SpotifyManager.logError("fetchSugegstedContent " + $r1.getMessage());
                    }
                }

                class C13452 implements ResultCallback<ListItems> {
                    C13452() throws  {
                    }

                    public void onResult(ListItems $r1) throws  {
                        if (SpotifyManager.this.mContentList == null || SpotifyManager.this.mContentList.length <= 0 || $r1.items.length != 0) {
                            SpotifyManager.this.mContentList = $r1.items;
                            SpotifyManager.logDebug("getChildrenOfItem[0] Recommened Content: " + $r1);
                            SpotifyManager.this.createPlayListArray();
                            SpotifyManager.this.highlightPlayList();
                            return;
                        }
                        SpotifyManager.logDebug("getChildrenOfItem[0] Recommened Content is empty but have already data, ignore: " + $r1);
                    }
                }

                public void onResult(ListItems $r1) throws  {
                    SpotifyManager.logDebug("getRecommendedContentItems: " + $r1);
                    SpotifyManager.this.mSpotifyAppRemote.getContentApi().getChildrenOfItem($r1.items[0], 11, 0).setResultCallback(new C13452()).setErrorCallback(new C13441());
                }
            }).setErrorCallback(new ErrorCallback() {
                public void onError(Throwable $r1) throws  {
                    SpotifyManager.logError("fetchSugegstedContent " + $r1.getMessage());
                }
            });
        }
    }

    private void createPlayListArray() throws  {
        ArrayList $r2 = new ArrayList();
        this.currentPlayList = 0;
        this.mBitmaps.clear();
        downloadPlayListImage(this.currentPlayList);
        for (ListItem $r1 : this.mContentList) {
            logDebug("Added Playlist " + $r1.title);
            $r2.add($r1.title);
            this.mBitmaps.add(null);
        }
        $r2.add("Open Spotify");
        this.mPlayListsTitles = (String[]) $r2.toArray(new String[$r2.size()]);
        SpotifyPopup.getInstance().updatePlayLists(this.mPlayListsTitles);
    }

    public void getPlayListImage(int $i0, ImageView $r1) throws  {
        if ($i0 <= this.mContentList.length) {
            if (this.mBitmaps.size() == 0 || this.mBitmaps.size() <= $i0) {
                $r1.setImageResource(C1283R.drawable.music_player_artwork_placeholder);
            } else if (this.mBitmaps.get($i0) != null) {
                $r1.setImageBitmap((Bitmap) this.mBitmaps.get($i0));
            } else {
                $r1.setImageResource(C1283R.drawable.music_player_artwork_placeholder);
            }
        }
    }

    private Bitmap getRoundedCornerBitmap(Bitmap $r1, int $i0) throws  {
        Bitmap $r7 = Bitmap.createBitmap($r1.getWidth(), $r1.getHeight(), Config.ARGB_8888);
        Canvas $r2 = new Canvas($r7);
        Paint $r3 = new Paint();
        Rect $r4 = new Rect(0, 0, $r1.getWidth(), $r1.getHeight());
        RectF $r5 = new RectF($r4);
        float $f0 = (float) $i0;
        $r3.setAntiAlias(true);
        $r2.drawARGB(0, 0, 0, 0);
        $r3.setColor(-12434878);
        $r2.drawRoundRect($r5, $f0, $f0, $r3);
        $r3.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        $r2.drawBitmap($r1, $r4, $r4, $r3);
        return $r7;
    }

    public void downloadPlayListImage(final int $i0) throws  {
        if ($i0 < this.mContentList.length && this.mSpotifyAppRemote != null && this.mSpotifyAppRemote.getImagesApi() != null) {
            final ListItem $r1 = this.mContentList[$i0];
            if ($r1.imageUri != null) {
                this.mSpotifyAppRemote.getImagesApi().getImage($r1.imageUri).setResultCallback(new ResultCallback<Bitmap>() {
                    public void onResult(Bitmap $r1) throws  {
                        SpotifyManager.logDebug("Downloaded image for Playlist " + $i0 + " " + $r1.title);
                        SpotifyManager.this.mBitmaps.add($i0, $r1);
                        SpotifyManager.this.downloadPlayListImage(SpotifyManager.this.currentPlayList = SpotifyManager.this.currentPlayList + 1);
                        SpotifyPopup.getInstance().refreshPlayLists();
                    }
                }).setErrorCallback(new ErrorCallback() {
                    public void onError(Throwable $r1) throws  {
                        SpotifyManager.logError("downloadPlayListImage " + $r1.getMessage());
                        SpotifyManager.this.downloadPlayListImage(SpotifyManager.this.currentPlayList = SpotifyManager.this.currentPlayList + 1);
                    }
                });
            }
        }
    }

    public String[] getPlayListsTitle() throws  {
        return this.mPlayListsTitles;
    }

    public void playPlayList(int $i0) throws  {
        if ($i0 >= 0 && this.mContentList != null && $i0 <= this.mContentList.length) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_BUTTON_PRESED, "ACTION|INDEX", "SPOTIFY_PLAY_PLAYLIST|" + $i0);
            ListItem $r1 = this.mContentList[$i0];
            if ($r1 == null) {
                return;
            }
            if ($r1.playable) {
                this.mSpotifyAppRemote.getContentApi().playContentItem($r1).setResultCallback(new ResultCallback<Empty>() {
                    public void onResult(Empty data) throws  {
                        SpotifyManager.logDebug("playPlayList - Content item played!");
                    }
                }).setErrorCallback(new ErrorCallback() {
                    public void onError(Throwable $r1) throws  {
                        SpotifyManager.logError("playPlayList " + $r1.getMessage());
                    }
                });
            } else {
                logDebug("playPlayList - Content item is not playable!");
            }
        }
    }

    public void playNextPlayList() throws  {
        logDebug("playNextPlayList - " + this.mPlayListIndex);
        if (this.mPlayListIndex >= 0 && this.mContentList != null && this.mPlayListIndex < this.mContentList.length) {
            int $i0 = this.mPlayListIndex + 1;
            this.mPlayListIndex = $i0;
            playPlayList($i0);
        }
    }

    public void playPreviousPlayList() throws  {
        logDebug("playPreviousPlayList - " + this.mPlayListIndex);
        if (this.mPlayListIndex > 0 && this.mContentList != null) {
            int $i0 = this.mPlayListIndex - 1;
            this.mPlayListIndex = $i0;
            playPlayList($i0);
        }
    }

    public void playUri(String $r1) throws  {
        if (this.mSpotifyAppRemote != null) {
            logDebug("playUri - " + $r1);
            if (isConnected()) {
                if (!(AppService.getMainActivity() == null || AppService.getMainActivity().getLayoutMgr() == null)) {
                    AppService.getMainActivity().getLayoutMgr().openSpotifyPopup();
                }
                this.mSpotifyAppRemote.getPlayerApi().play($r1).setResultCallback(new ResultCallback<Empty>() {
                    public void onResult(Empty result) throws  {
                        SpotifyManager.logDebug("Play successful");
                    }
                }).setErrorCallback(new ErrorCallback() {
                    public void onError(Throwable $r1) throws  {
                        SpotifyManager.logError("playUri " + $r1.getMessage());
                    }
                });
                return;
            }
            logError("playUri - Spotify not connected");
        }
    }

    private static void logError(String $r0) throws  {
        Logger.m38e("SpotifyManager: " + $r0);
        Log.e("SpotifyManager: ", $r0);
    }

    private static void logDebug(String $r0) throws  {
        Logger.m36d("SpotifyManager: " + $r0);
        Log.d("SpotifyManager: ", $r0);
    }

    private void showDriverDistractionOpeningApp() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_DRIVER_DISTRACTION_APP);
        Logger.m43w("SpotifyManager: showDriverDistractionOpeningApp");
        MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(DisplayStrings.displayString(DisplayStrings.DS_SPOTIFY_YOU_ARE_DRIVING), DisplayStrings.displayString(DisplayStrings.DS_SPOTIFY_SWITCHING_DISABLED), true, new onNotDriverOpeningApp(), DisplayStrings.displayString(DisplayStrings.DS_OK), DisplayStrings.displayString(DisplayStrings.DS_PASSENGER), 7);
    }

    private void showDriverDistractionAuthFlow() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPOTIFY_DRIVER_DISTRACTION_AUTH);
        Logger.m43w("SpotifyManager: showDriverDistractionAuthFlow");
        MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(DisplayStrings.displayString(DisplayStrings.DS_SPOTIFY_YOU_ARE_DRIVING), DisplayStrings.displayString(DisplayStrings.DS_SPOTIFY_ACTIVATE_WHILE_DRIVING), true, new onNotDriverAuthFlow(), DisplayStrings.displayString(DisplayStrings.DS_OK), DisplayStrings.displayString(DisplayStrings.DS_PASSENGER), 7);
    }
}
