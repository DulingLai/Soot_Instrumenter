package com.spotify.android.appremote.internal;

import android.content.Context;
import com.waze.audioextension.spotify.SpotifyManager;

public class SpotifyLocator {
    private static final String[] SPOTIFY_PACKAGES = new String[]{SpotifyManager.PACKAGE_ID_DEBUD, "com.spotify.music.canary", "com.spotify.music.partners", SpotifyManager.PACKAGE_ID};

    public String getSpotifyBestPackageName(Context $r1) throws  {
        for (String $r3 : SPOTIFY_PACKAGES) {
            if ($r1.getPackageManager().getLaunchIntentForPackage($r3) != null) {
                return $r3;
            }
        }
        return null;
    }

    public boolean isSpotifyInstalled(Context $r1) throws  {
        return getSpotifyBestPackageName($r1) != null;
    }
}
