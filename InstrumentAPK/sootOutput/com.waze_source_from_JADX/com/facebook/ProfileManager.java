package com.facebook;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;

final class ProfileManager {
    static final String ACTION_CURRENT_PROFILE_CHANGED = "com.facebook.sdk.ACTION_CURRENT_PROFILE_CHANGED";
    static final String EXTRA_NEW_PROFILE = "com.facebook.sdk.EXTRA_NEW_PROFILE";
    static final String EXTRA_OLD_PROFILE = "com.facebook.sdk.EXTRA_OLD_PROFILE";
    private static volatile ProfileManager instance;
    private Profile currentProfile;
    private final LocalBroadcastManager localBroadcastManager;
    private final ProfileCache profileCache;

    ProfileManager(LocalBroadcastManager $r1, ProfileCache $r2) throws  {
        Validate.notNull($r1, "localBroadcastManager");
        Validate.notNull($r2, "profileCache");
        this.localBroadcastManager = $r1;
        this.profileCache = $r2;
    }

    static ProfileManager getInstance() throws  {
        if (instance == null) {
            synchronized (ProfileManager.class) {
                try {
                    if (instance == null) {
                        instance = new ProfileManager(LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()), new ProfileCache());
                    }
                } catch (Throwable th) {
                    while (true) {
                        Class cls = ProfileManager.class;
                    }
                }
            }
        }
        return instance;
    }

    Profile getCurrentProfile() throws  {
        return this.currentProfile;
    }

    boolean loadCurrentProfile() throws  {
        Profile $r2 = this.profileCache.load();
        if ($r2 == null) {
            return false;
        }
        setCurrentProfile($r2, false);
        return true;
    }

    void setCurrentProfile(Profile $r1) throws  {
        setCurrentProfile($r1, true);
    }

    private void setCurrentProfile(Profile $r1, boolean $z0) throws  {
        Profile $r2 = this.currentProfile;
        this.currentProfile = $r1;
        if ($z0) {
            if ($r1 != null) {
                this.profileCache.save($r1);
            } else {
                this.profileCache.clear();
            }
        }
        if (!Utility.areObjectsEqual($r2, $r1)) {
            sendCurrentProfileChangedBroadcast($r2, $r1);
        }
    }

    private void sendCurrentProfileChangedBroadcast(Profile $r1, Profile $r2) throws  {
        Intent $r3 = new Intent(ACTION_CURRENT_PROFILE_CHANGED);
        $r3.putExtra(EXTRA_OLD_PROFILE, $r1);
        $r3.putExtra(EXTRA_NEW_PROFILE, $r2);
        this.localBroadcastManager.sendBroadcast($r3);
    }
}
