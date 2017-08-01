package com.spotify.protocol.client;

import com.spotify.protocol.WampClient.Receiver;
import com.spotify.protocol.client.error.RemoteClientException;
import com.spotify.protocol.error.SpotifyAppRemoteException;
import com.spotify.protocol.mappers.JsonMappingException;
import com.spotify.protocol.mappers.JsonObject;
import com.spotify.protocol.types.Types.RequestId;
import com.spotify.protocol.types.Types.SubscriptionId;
import dalvik.annotation.Signature;

public class RemoteWampClient implements RemoteClient {
    private final AppProtocolCommunicator mCommunicator;
    private volatile RequestId mHelloRecordId;
    private final Receiver mReceiver = new C10791();
    private final WampCallsOrchestrator mWampCallsOrchestrator;

    class C10791 implements Receiver {
        C10791() throws  {
        }

        public void onWelcome(int sessionId, JsonObject $r1) throws  {
            CallRecord $r5 = RemoteWampClient.this.mWampCallsOrchestrator.findCallById(RemoteWampClient.this.mHelloRecordId);
            if ($r5 != null) {
                RemoteWampClient.this.mWampCallsOrchestrator.removeCall(RemoteWampClient.this.mHelloRecordId);
                $r5.deliverResultWithPayload($r1);
            }
        }

        public void onAbort(JsonObject $r1, String $r2) throws  {
            CallRecord $r6 = RemoteWampClient.this.mWampCallsOrchestrator.findCallById(RemoteWampClient.this.mHelloRecordId);
            if ($r6 != null) {
                RemoteWampClient.this.mWampCallsOrchestrator.removeCall(RemoteWampClient.this.mHelloRecordId);
                $r6.mCallResult.deliverError(RemoteWampClient.this.getRemoteClientException($r1, $r2));
            }
        }

        public void onGoodbye(JsonObject details, String reasonUri) throws  {
        }

        public void onSubscribed(RequestId $r1, SubscriptionId $r2) throws  {
            RemoteWampClient.this.mWampCallsOrchestrator.onSubscriptionIdReceived($r1, $r2);
            Subscription $r3 = RemoteWampClient.this.mWampCallsOrchestrator.findSubscriptionById($r2).mSubscription;
            if ($r3 != null) {
                $r3.deliverStart();
            }
        }

        public void onSubscribeError(RequestId $r1, JsonObject $r2, String $r3) throws  {
            Subscription $r4 = RemoteWampClient.this.mWampCallsOrchestrator.findSubscriptionByRequestId($r1).mSubscription;
            if ($r4 != null) {
                RemoteWampClient.this.mWampCallsOrchestrator.removeSubscriptionByRequestId($r1);
                $r4.deliverError(RemoteWampClient.this.getRemoteClientException($r2, $r3));
            }
        }

        public void onEvent(SubscriptionId $r1, int publicationId, JsonObject $r2) throws  {
            SubscriptionRecord $r5 = RemoteWampClient.this.mWampCallsOrchestrator.findSubscriptionById($r1);
            if ($r5 != null) {
                $r5.deliverEventWithPayload($r2);
            }
        }

        public void onResult(RequestId $r1, JsonObject details, JsonObject $r3, JsonObject argumentsKw) throws  {
            CallRecord $r7 = RemoteWampClient.this.mWampCallsOrchestrator.findCallById($r1);
            if ($r7 != null) {
                RemoteWampClient.this.mWampCallsOrchestrator.removeCall($r1);
                $r7.deliverResultWithPayload($r3);
            }
        }

        public void onError(RequestId $r1, JsonObject $r2, String $r3) throws  {
            CallRecord $r6 = RemoteWampClient.this.mWampCallsOrchestrator.findCallById($r1);
            if ($r6 != null) {
                RemoteWampClient.this.mWampCallsOrchestrator.removeCall($r1);
                $r6.mCallResult.deliverError(RemoteWampClient.this.getRemoteClientException($r2, $r3));
            }
        }

        public void onUnsubscribed(RequestId requestId) throws  {
        }

        public void onUnubscribeError(RequestId subscribeRequestId, JsonObject details, String errorUri) throws  {
        }
    }

    private RemoteClientException getRemoteClientException(JsonObject $r1, String $r2) throws  {
        String $r4;
        try {
            $r4 = $r1.toJson();
        } catch (JsonMappingException $r3) {
            $r4 = String.format("Could not parse error details: %s", new Object[]{$r3.getMessage()});
        }
        return new RemoteClientException($r4, $r2);
    }

    public RemoteWampClient(AppProtocolCommunicator $r1, WampCallsOrchestrator $r2) throws  {
        this.mCommunicator = $r1;
        this.mWampCallsOrchestrator = $r2;
        this.mCommunicator.setMessageReceiver(this.mReceiver);
    }

    public <T> CallResult<T> hello(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Class<T> $r1) throws  {
        CallRecord $r4 = this.mWampCallsOrchestrator.newCall($r1);
        this.mHelloRecordId = $r4.mId;
        try {
            this.mCommunicator.sendHello();
        } catch (SpotifyAppRemoteException $r2) {
            this.mWampCallsOrchestrator.removeCall(this.mHelloRecordId);
            $r4.mCallResult.deliverError($r2);
        }
        return $r4.mCallResult;
    }

    public void goodbye() throws  {
        try {
            this.mCommunicator.sendGoodbye();
        } catch (SpotifyAppRemoteException e) {
        }
    }

    public <T> CallResult<T> call(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) String $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Class<T> $r2) throws  {
        CallRecord $r5 = this.mWampCallsOrchestrator.newCall($r2);
        try {
            this.mCommunicator.sendCall($r5.mId.getRaw(), null, $r1);
        } catch (SpotifyAppRemoteException $r3) {
            $r5.mCallResult.deliverError($r3);
        }
        return $r5.mCallResult;
    }

    public <T> CallResult<T> call(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Object;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) String $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Object;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Object $r2, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Object;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Class<T> $r3) throws  {
        CallRecord $r6 = this.mWampCallsOrchestrator.newCall($r3);
        try {
            this.mCommunicator.sendCall($r6.mId.getRaw(), null, $r1, null, $r2);
        } catch (SpotifyAppRemoteException $r4) {
            $r6.mCallResult.deliverError($r4);
        }
        return $r6.mCallResult;
    }

    public <T> Subscription<T> subscribe(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/Subscription", "<TT;>;"}) String $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/Subscription", "<TT;>;"}) Class<T> $r2) throws  {
        SubscriptionRecord $r5 = this.mWampCallsOrchestrator.newSubscription(this, $r2);
        try {
            this.mCommunicator.sendSubscribe($r5.mId.getRaw(), null, $r1);
        } catch (SpotifyAppRemoteException $r3) {
            $r5.mSubscription.deliverError($r3);
        }
        return $r5.mSubscription;
    }

    public <T> void unsubscribe(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/spotify/protocol/client/Subscription", "<TT;>;)V"}) Subscription<T> $r1) throws  {
        SubscriptionRecord $r5 = this.mWampCallsOrchestrator.findSubscriptionByRequestId($r1.getRequestId());
        if ($r5 == null || $r5.mSubscriptionId == SubscriptionId.NONE) {
            Object[] $r9 = new Object[1];
            $r9[0] = $r5;
            Debug.m34e("Cannot unsubscribe using record: %s", $r9);
            return;
        }
        try {
            this.mCommunicator.sendUnsubscribe(this.mWampCallsOrchestrator.getNextRequestId().getRaw(), $r5.mSubscriptionId.getRaw());
            this.mWampCallsOrchestrator.removeSubscription($r5.mSubscriptionId);
        } catch (SpotifyAppRemoteException $r2) {
            Debug.m35e($r2, "Cannot unsubscribe", new Object[0]);
        }
    }
}
