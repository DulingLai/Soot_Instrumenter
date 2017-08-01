package com.spotify.protocol;

import com.spotify.protocol.types.Empty;

public class AppProtocol {
    public static final Empty EMPTY = new Empty();

    public static class CallUri {
        public static final String CAPABILITIES = "com.spotify.get_capabilities";
        public static final String GET_CHILDREN_OF_ITEM = "com.spotify.get_children_of_item";
        public static final String GET_IMAGE = "com.spotify.get_image";
        public static final String GET_PLAYER_STATE = "com.spotify.get_player_state";
        public static final String GET_RECOMMENDED_ROOT_ITEMS = "com.spotify.get_recommended_root_items";
        public static final String GET_SAVED = "com.spotify.get_saved";
        public static final String GET_THUMBNAIL_IMAGE = "com.spotify.get_thumbnail_image";
        public static final String PLAY_ITEM = "com.spotify.play_item";
        public static final String PLAY_QUEUE = "com.spotify.queue_spotify_uri";
        public static final String PLAY_URI = "com.spotify.play_spotify_uri";
        public static final String PLAY_URIS = "com.spotify.play_spotify_uris";
        public static final String SET_PLAYBACK_POSITION = "com.spotify.set_playback_position";
        public static final String SET_PLAYBACK_SPEED = "com.spotify.set_playback_speed";
        public static final String SET_REPEAT = "com.spotify.set_repeat";
        public static final String SET_SAVED = "com.spotify.set_saved";
        public static final String SET_SHUFFLE = "com.spotify.set_shuffle";
        public static final String SKIP_NEXT = "com.spotify.skip_next";
        public static final String SKIP_PREVIOUS = "com.spotify.skip_previous";

        private CallUri() throws  {
        }
    }

    public interface ErrorUri {
        public static final String ERROR_AUTHENTICATION_FAILED = "com.spotify.error.client_authentication_failed";
        public static final String ERROR_FEATURE_VERSION_MISMATCH = "com.spotify.error.unsupported_version";
        public static final String ERROR_INVALID_ARGUMENT = "wamp.error.invalid_argument";
        public static final String ERROR_INVALID_URI = "wamp.error.invalid_uri";
        public static final String ERROR_NOT_LOGGED_IN = "com.spotify.error.not_logged_in";
        public static final String ERROR_OFFLINE_MODE_ACTIVE = "com.spotify.error.offline_mode_active";
        public static final String ERROR_USER_NOT_AUTHORIZED = "com.spotify.error.user_not_authorized";
        public static final String WAMP_ERROR = "wamp.error";
        public static final String WAMP_ERROR_NOT_AUTHORIZED = "wamp.error.not_authorized";
        public static final String WAMP_ERROR_SYSTEM_SHUTDOWN = "wamp.error.system_shutdown";
    }

    public static class ItemId {
        public static final String RECENTLY_PLAYED = "com.spotify.recently-played";

        private ItemId() throws  {
        }
    }

    public static class Topic {
        public static final String CAPABILITIES = "com.spotify.capabilities";
        public static final String PLAYER_CONTEXT = "com.spotify.current_context";
        public static final String PLAYER_STATE = "com.spotify.player_state";
        public static final String STATUS = "com.spotify.status";
        public static final String TEMPO_DETECTION_STATE = "com.spotify.running_tempo_detection_state";

        private Topic() throws  {
        }
    }

    private AppProtocol() throws  {
    }
}
