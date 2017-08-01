package com.spotify.android.appremote.internal;

import android.text.TextUtils;

public class Validator {
    boolean isValidTrackUri(String $r1) throws  {
        return matches($r1, "spotify:track:[a-zA-Z0-9]{22}");
    }

    boolean isValidAlbumUri(String $r1) throws  {
        return matches($r1, "spotify:album:[a-zA-Z0-9]{22}");
    }

    boolean isValidArtistUri(String $r1) throws  {
        return matches($r1, "spotify:artist:[a-zA-Z0-9]{22}");
    }

    boolean isValidPlaylistUri(String $r1) throws  {
        return matches($r1, "spotify:user:.+playlist:[a-zA-Z0-9]{22}");
    }

    private boolean matches(String $r1, String $r2) throws  {
        return TextUtils.isEmpty($r1) ? false : $r1.matches($r2);
    }
}
