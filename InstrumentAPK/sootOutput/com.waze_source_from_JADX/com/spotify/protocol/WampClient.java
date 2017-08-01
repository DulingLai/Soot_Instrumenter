package com.spotify.protocol;

import com.spotify.protocol.error.SpotifyAppRemoteException;
import com.spotify.protocol.mappers.JsonObject;
import com.spotify.protocol.types.Types.RequestId;
import com.spotify.protocol.types.Types.SubscriptionId;
import dalvik.annotation.Signature;
import java.util.List;

public interface WampClient {
    public static final String ERROR_INVALID_ARGUMENT = "wamp.error.invalid_argument";
    public static final String ERROR_INVALID_URI = "wamp.error.invalid_uri";
    public static final String WAMP_ERROR = "wamp.error";
    public static final String WAMP_ERROR_SYSTEM_SHUTDOWN = "wamp.error.system_shutdown";

    public interface Receiver {
        void onAbort(JsonObject jsonObject, String str) throws ;

        void onError(RequestId requestId, JsonObject jsonObject, String str) throws ;

        void onEvent(SubscriptionId subscriptionId, int i, JsonObject jsonObject) throws ;

        void onGoodbye(JsonObject jsonObject, String str) throws ;

        void onResult(RequestId requestId, JsonObject jsonObject, JsonObject jsonObject2, JsonObject jsonObject3) throws ;

        void onSubscribeError(RequestId requestId, JsonObject jsonObject, String str) throws ;

        void onSubscribed(RequestId requestId, SubscriptionId subscriptionId) throws ;

        void onUnsubscribed(RequestId requestId) throws ;

        void onUnubscribeError(RequestId requestId, JsonObject jsonObject, String str) throws ;

        void onWelcome(int i, JsonObject jsonObject) throws ;
    }

    public interface RequestType {
        public static final int ABORT = 3;
        public static final int CALL = 48;
        public static final int CANCEL = 49;
        public static final int ERROR = 8;
        public static final int EVENT = 36;
        public static final int GOODBYE = 6;
        public static final int HELLO = 1;
        public static final int RESULT = 50;
        public static final int SUBSCRIBE = 32;
        public static final int SUBSCRIBED = 33;
        public static final int UNSUBSCRIBE = 34;
        public static final int UNSUBSCRIBED = 35;
        public static final int WELCOME = 2;
    }

    public interface Router {
        void addReceiver(Receiver receiver) throws ;

        void removeReceiver(Receiver receiver) throws ;

        boolean route(WampMessage wampMessage) throws ;
    }

    public interface Sender {
        void sendCall(int i, Object obj, String str) throws SpotifyAppRemoteException;

        void sendCall(@Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) int i, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) Object obj, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) String str, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) List<Object> list) throws SpotifyAppRemoteException;

        void sendCall(@Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) int i, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) Object obj, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) String str, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) List<Object> list, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) Object obj2) throws SpotifyAppRemoteException;

        void sendCancel(int i, Object obj) throws ;

        void sendGoodbye() throws SpotifyAppRemoteException;

        void sendHello() throws SpotifyAppRemoteException;

        void sendSubscribe(int i, Object obj, String str) throws SpotifyAppRemoteException;

        void sendUnsubscribe(int i, int i2) throws SpotifyAppRemoteException;
    }
}
