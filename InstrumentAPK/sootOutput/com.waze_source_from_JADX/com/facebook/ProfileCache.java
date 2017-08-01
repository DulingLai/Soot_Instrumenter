package com.facebook;

import android.content.SharedPreferences;
import com.facebook.internal.Validate;
import com.google.android.gms.common.Scopes;
import org.json.JSONException;
import org.json.JSONObject;

final class ProfileCache {
    static final String CACHED_PROFILE_KEY = "com.facebook.ProfileManager.CachedProfile";
    static final String SHARED_PREFERENCES_NAME = "com.facebook.AccessTokenManager.SharedPreferences";
    private final SharedPreferences sharedPreferences = FacebookSdk.getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, 0);

    ProfileCache() throws  {
    }

    Profile load() throws  {
        String $r2 = this.sharedPreferences.getString(CACHED_PROFILE_KEY, null);
        if ($r2 != null) {
            try {
                return new Profile(new JSONObject($r2));
            } catch (JSONException e) {
            }
        }
        return null;
    }

    void save(Profile $r1) throws  {
        Validate.notNull($r1, Scopes.PROFILE);
        JSONObject $r2 = $r1.toJSONObject();
        if ($r2 != null) {
            this.sharedPreferences.edit().putString(CACHED_PROFILE_KEY, $r2.toString()).apply();
        }
    }

    void clear() throws  {
        this.sharedPreferences.edit().remove(CACHED_PROFILE_KEY).apply();
    }
}
