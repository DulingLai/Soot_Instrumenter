package com.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.facebook.GraphRequest.Callback;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

final class AccessTokenManager {
    static final String ACTION_CURRENT_ACCESS_TOKEN_CHANGED = "com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED";
    static final String EXTRA_NEW_ACCESS_TOKEN = "com.facebook.sdk.EXTRA_NEW_ACCESS_TOKEN";
    static final String EXTRA_OLD_ACCESS_TOKEN = "com.facebook.sdk.EXTRA_OLD_ACCESS_TOKEN";
    private static final String ME_PERMISSIONS_GRAPH_PATH = "me/permissions";
    static final String SHARED_PREFERENCES_NAME = "com.facebook.AccessTokenManager.SharedPreferences";
    static final String TAG = "AccessTokenManager";
    private static final String TOKEN_EXTEND_GRAPH_PATH = "oauth/access_token";
    private static final int TOKEN_EXTEND_RETRY_SECONDS = 3600;
    private static final int TOKEN_EXTEND_THRESHOLD_SECONDS = 86400;
    private static volatile AccessTokenManager instance;
    private final AccessTokenCache accessTokenCache;
    private AccessToken currentAccessToken;
    private Date lastAttemptedTokenExtendDate = new Date(0);
    private final LocalBroadcastManager localBroadcastManager;
    private AtomicBoolean tokenRefreshInProgress = new AtomicBoolean(false);

    class C04751 implements Runnable {
        C04751() throws  {
        }

        public void run() throws  {
            AccessTokenManager.this.refreshCurrentAccessTokenImpl();
        }
    }

    private static class RefreshResult {
        public String accessToken;
        public int expiresAt;

        private RefreshResult() throws  {
        }
    }

    AccessTokenManager(LocalBroadcastManager $r1, AccessTokenCache $r2) throws  {
        Validate.notNull($r1, "localBroadcastManager");
        Validate.notNull($r2, "accessTokenCache");
        this.localBroadcastManager = $r1;
        this.accessTokenCache = $r2;
    }

    static AccessTokenManager getInstance() throws  {
        if (instance == null) {
            synchronized (AccessTokenManager.class) {
                try {
                    if (instance == null) {
                        instance = new AccessTokenManager(LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()), new AccessTokenCache());
                    }
                } catch (Throwable th) {
                    while (true) {
                        Class cls = AccessTokenManager.class;
                    }
                }
            }
        }
        return instance;
    }

    AccessToken getCurrentAccessToken() throws  {
        return this.currentAccessToken;
    }

    boolean loadCurrentAccessToken() throws  {
        AccessToken $r2 = this.accessTokenCache.load();
        if ($r2 == null) {
            return false;
        }
        setCurrentAccessToken($r2, false);
        return true;
    }

    void setCurrentAccessToken(AccessToken $r1) throws  {
        setCurrentAccessToken($r1, true);
    }

    private void setCurrentAccessToken(AccessToken $r1, boolean $z0) throws  {
        AccessToken $r2 = this.currentAccessToken;
        this.currentAccessToken = $r1;
        this.tokenRefreshInProgress.set(false);
        this.lastAttemptedTokenExtendDate = new Date(0);
        if ($z0) {
            if ($r1 != null) {
                this.accessTokenCache.save($r1);
            } else {
                this.accessTokenCache.clear();
                Utility.clearFacebookCookies(FacebookSdk.getApplicationContext());
            }
        }
        if (!Utility.areObjectsEqual($r2, $r1)) {
            sendCurrentAccessTokenChangedBroadcast($r2, $r1);
        }
    }

    private void sendCurrentAccessTokenChangedBroadcast(AccessToken $r1, AccessToken $r2) throws  {
        Intent $r3 = new Intent(ACTION_CURRENT_ACCESS_TOKEN_CHANGED);
        $r3.putExtra(EXTRA_OLD_ACCESS_TOKEN, $r1);
        $r3.putExtra(EXTRA_NEW_ACCESS_TOKEN, $r2);
        this.localBroadcastManager.sendBroadcast($r3);
    }

    void extendAccessTokenIfNeeded() throws  {
        if (shouldExtendAccessToken()) {
            refreshCurrentAccessToken();
        }
    }

    private boolean shouldExtendAccessToken() throws  {
        if (this.currentAccessToken == null) {
            return false;
        }
        Long $r3 = Long.valueOf(new Date().getTime());
        if (!this.currentAccessToken.getSource().canExtendToken()) {
            return false;
        }
        if ($r3.longValue() - this.lastAttemptedTokenExtendDate.getTime() > 3600000) {
            return $r3.longValue() - this.currentAccessToken.getLastRefresh().getTime() > 86400000;
        } else {
            return false;
        }
    }

    private static GraphRequest createGrantedPermissionsRequest(AccessToken $r0, Callback $r1) throws  {
        return new GraphRequest($r0, ME_PERMISSIONS_GRAPH_PATH, new Bundle(), HttpMethod.GET, $r1);
    }

    private static GraphRequest createExtendAccessTokenRequest(AccessToken $r0, Callback $r1) throws  {
        Bundle $r2 = new Bundle();
        $r2.putString("grant_type", "fb_extend_sso_token");
        return new GraphRequest($r0, TOKEN_EXTEND_GRAPH_PATH, $r2, HttpMethod.GET, $r1);
    }

    void refreshCurrentAccessToken() throws  {
        if (Looper.getMainLooper().equals(Looper.myLooper())) {
            refreshCurrentAccessTokenImpl();
        } else {
            new Handler(Looper.getMainLooper()).post(new C04751());
        }
    }

    private void refreshCurrentAccessTokenImpl() throws  {
        AccessToken $r1 = this.currentAccessToken;
        if ($r1 != null && this.tokenRefreshInProgress.compareAndSet(false, true)) {
            Validate.runningOnUiThread();
            this.lastAttemptedTokenExtendDate = new Date();
            final HashSet $r4 = new HashSet();
            final HashSet $r5 = new HashSet();
            final AtomicBoolean $r2 = new AtomicBoolean(false);
            final RefreshResult $r3 = new RefreshResult();
            GraphRequestBatch graphRequestBatch = new GraphRequestBatch(createGrantedPermissionsRequest($r1, new Callback() {
                public void onCompleted(GraphResponse $r1) throws  {
                    JSONObject $r2 = $r1.getJSONObject();
                    if ($r2 != null) {
                        JSONArray $r3 = $r2.optJSONArray("data");
                        if ($r3 != null) {
                            $r2.set(true);
                            for (int $i0 = 0; $i0 < $r3.length(); $i0++) {
                                $r2 = $r3.optJSONObject($i0);
                                if ($r2 != null) {
                                    String $r5 = $r2.optString("permission");
                                    String $r6 = $r2.optString("status");
                                    if (!(Utility.isNullOrEmpty($r5) || Utility.isNullOrEmpty($r6))) {
                                        $r6 = $r6.toLowerCase(Locale.US);
                                        if ($r6.equals("granted")) {
                                            $r4.add($r5);
                                        } else if ($r6.equals("declined")) {
                                            $r5.add($r5);
                                        } else {
                                            Log.w(AccessTokenManager.TAG, "Unexpected status: " + $r6);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }), createExtendAccessTokenRequest($r1, new Callback() {
                public void onCompleted(GraphResponse $r1) throws  {
                    JSONObject $r2 = $r1.getJSONObject();
                    if ($r2 != null) {
                        $r3.accessToken = $r2.optString("access_token");
                        $r3.expiresAt = $r2.optInt("expires_at");
                    }
                }
            }));
            final AccessToken accessToken = $r1;
            final AtomicBoolean atomicBoolean = $r2;
            final RefreshResult refreshResult = $r3;
            final HashSet hashSet = $r4;
            final HashSet hashSet2 = $r5;
            graphRequestBatch.addCallback(new GraphRequestBatch.Callback() {
                public void onBatchCompleted(GraphRequestBatch batch) throws  {
                    if (AccessTokenManager.getInstance().getCurrentAccessToken() != null && AccessTokenManager.getInstance().getCurrentAccessToken().getUserId() == accessToken.getUserId()) {
                        try {
                            if (atomicBoolean.get() || refreshResult.accessToken != null || refreshResult.expiresAt != 0) {
                                String $r4;
                                AccessToken $r8;
                                Set $r10;
                                Set $r11;
                                Date $r13;
                                if (refreshResult.accessToken != null) {
                                    $r4 = refreshResult.accessToken;
                                } else {
                                    $r8 = accessToken;
                                    $r4 = $r8.getToken();
                                }
                                $r8 = accessToken;
                                String $r5 = $r8.getApplicationId();
                                $r8 = accessToken;
                                String $r9 = $r8.getUserId();
                                if (atomicBoolean.get()) {
                                    $r10 = hashSet;
                                } else {
                                    $r8 = accessToken;
                                    $r10 = $r8.getPermissions();
                                }
                                if (atomicBoolean.get()) {
                                    $r11 = hashSet2;
                                } else {
                                    $r8 = accessToken;
                                    $r11 = $r8.getDeclinedPermissions();
                                }
                                $r8 = accessToken;
                                AccessTokenSource $r12 = $r8.getSource();
                                if (refreshResult.expiresAt != 0) {
                                    long $l1 = refreshResult.expiresAt;
                                    int $i0 = $l1;
                                    $l1 = new Date(((long) $l1) * 1000);
                                } else {
                                    $r8 = accessToken;
                                    $r13 = $r8.getExpires();
                                }
                                AccessTokenManager.getInstance().setCurrentAccessToken(new AccessToken($r4, $r5, $r9, $r10, $r11, $r12, $r13, new Date()));
                                AccessTokenManager.this.tokenRefreshInProgress.set(null);
                            }
                        } finally {
                            AccessTokenManager.this.tokenRefreshInProgress.set(false);
                        }
                    }
                }
            });
            graphRequestBatch.executeAsync();
        }
    }
}
