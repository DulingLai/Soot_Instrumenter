package com.facebook;

import android.content.SharedPreferences;
import android.os.Bundle;
import com.facebook.internal.Validate;
import org.json.JSONException;
import org.json.JSONObject;

class AccessTokenCache {
    static final String CACHED_ACCESS_TOKEN_KEY = "com.facebook.AccessTokenManager.CachedAccessToken";
    private final SharedPreferences sharedPreferences;
    private LegacyTokenHelper tokenCachingStrategy;
    private final SharedPreferencesTokenCachingStrategyFactory tokenCachingStrategyFactory;

    static class SharedPreferencesTokenCachingStrategyFactory {
        SharedPreferencesTokenCachingStrategyFactory() throws  {
        }

        public LegacyTokenHelper create() throws  {
            return new LegacyTokenHelper(FacebookSdk.getApplicationContext());
        }
    }

    AccessTokenCache(SharedPreferences $r1, SharedPreferencesTokenCachingStrategyFactory $r2) throws  {
        this.sharedPreferences = $r1;
        this.tokenCachingStrategyFactory = $r2;
    }

    public AccessTokenCache() throws  {
        this(FacebookSdk.getApplicationContext().getSharedPreferences("com.facebook.AccessTokenManager.SharedPreferences", 0), new SharedPreferencesTokenCachingStrategyFactory());
    }

    public AccessToken load() throws  {
        if (hasCachedAccessToken()) {
            return getCachedAccessToken();
        }
        if (!shouldCheckLegacyToken()) {
            return null;
        }
        AccessToken $r1 = getLegacyAccessToken();
        if ($r1 == null) {
            return $r1;
        }
        save($r1);
        getTokenCachingStrategy().clear();
        return $r1;
    }

    public void save(AccessToken $r1) throws  {
        Validate.notNull($r1, "accessToken");
        try {
            JSONObject $r2 = $r1.toJSONObject();
            this.sharedPreferences.edit().putString(CACHED_ACCESS_TOKEN_KEY, $r2.toString()).apply();
        } catch (JSONException e) {
        }
    }

    public void clear() throws  {
        this.sharedPreferences.edit().remove(CACHED_ACCESS_TOKEN_KEY).apply();
        if (shouldCheckLegacyToken()) {
            getTokenCachingStrategy().clear();
        }
    }

    private boolean hasCachedAccessToken() throws  {
        return this.sharedPreferences.contains(CACHED_ACCESS_TOKEN_KEY);
    }

    private AccessToken getCachedAccessToken() throws  {
        String $r4 = this.sharedPreferences.getString(CACHED_ACCESS_TOKEN_KEY, null);
        if ($r4 == null) {
            return null;
        }
        try {
            return AccessToken.createFromJSONObject(new JSONObject($r4));
        } catch (JSONException e) {
            return null;
        }
    }

    private boolean shouldCheckLegacyToken() throws  {
        return FacebookSdk.isLegacyTokenUpgradeSupported();
    }

    private AccessToken getLegacyAccessToken() throws  {
        Bundle $r2 = getTokenCachingStrategy().load();
        if ($r2 == null) {
            return null;
        }
        if (LegacyTokenHelper.hasTokenInformation($r2)) {
            return AccessToken.createFromLegacyCache($r2);
        }
        return null;
    }

    private LegacyTokenHelper getTokenCachingStrategy() throws  {
        if (this.tokenCachingStrategy == null) {
            synchronized (this) {
                if (this.tokenCachingStrategy == null) {
                    this.tokenCachingStrategy = this.tokenCachingStrategyFactory.create();
                }
            }
        }
        return this.tokenCachingStrategy;
    }
}
