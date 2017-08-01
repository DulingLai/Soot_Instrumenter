package com.spotify.protocol.client;

import com.spotify.protocol.mappers.JsonObject;
import com.spotify.protocol.types.Types.RequestId;
import com.spotify.protocol.types.Types.SubscriptionId;
import dalvik.annotation.Signature;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WampCallsOrchestrator {
    private final Map<RequestId, CallRecord<?>> mCallsMap = new ConcurrentHashMap();
    private final AtomicInteger mIdGenerator = new AtomicInteger(0);
    private final Map<SubscriptionId, RequestId> mSubscriptionIdToRequestId = new ConcurrentHashMap();
    private final Map<RequestId, SubscriptionRecord<?>> mSubscriptionsMap = new ConcurrentHashMap();

    static class CallRecord<T> {
        final CallResult<T> mCallResult;
        final RequestId mId;
        private final Class<T> mType;

        CallRecord(@Signature({"(", "Lcom/spotify/protocol/types/Types$RequestId;", "Lcom/spotify/protocol/client/CallResult", "<TT;>;", "Ljava/lang/Class", "<TT;>;)V"}) RequestId $r1, @Signature({"(", "Lcom/spotify/protocol/types/Types$RequestId;", "Lcom/spotify/protocol/client/CallResult", "<TT;>;", "Ljava/lang/Class", "<TT;>;)V"}) CallResult<T> $r2, @Signature({"(", "Lcom/spotify/protocol/types/Types$RequestId;", "Lcom/spotify/protocol/client/CallResult", "<TT;>;", "Ljava/lang/Class", "<TT;>;)V"}) Class<T> $r3) throws  {
            this.mType = $r3;
            this.mId = (RequestId) Coding.checkNotNull($r1);
            this.mCallResult = (CallResult) Coding.checkNotNull($r2);
        }

        public void deliverResultWithPayload(JsonObject $r1) throws  {
            try {
                this.mCallResult.deliverResult(ResultUtils.createSuccessfulResult($r1.getAs(this.mType)));
            } catch (Exception $r2) {
                this.mCallResult.deliverError($r2);
            }
        }

        public int hashCode() throws  {
            return this.mId.hashCode();
        }

        public boolean equals(Object $r2) throws  {
            if ($r2 instanceof CallRecord) {
                return this.mId == ((CallRecord) $r2).mId;
            } else {
                return false;
            }
        }
    }

    static class SubscriptionRecord<T> {
        final Class<T> mEventType;
        final RequestId mId;
        final Subscription<T> mSubscription;
        SubscriptionId mSubscriptionId = SubscriptionId.NONE;

        SubscriptionRecord(@Signature({"(", "Lcom/spotify/protocol/types/Types$RequestId;", "Lcom/spotify/protocol/client/Subscription", "<TT;>;", "Ljava/lang/Class", "<TT;>;)V"}) RequestId $r1, @Signature({"(", "Lcom/spotify/protocol/types/Types$RequestId;", "Lcom/spotify/protocol/client/Subscription", "<TT;>;", "Ljava/lang/Class", "<TT;>;)V"}) Subscription<T> $r2, @Signature({"(", "Lcom/spotify/protocol/types/Types$RequestId;", "Lcom/spotify/protocol/client/Subscription", "<TT;>;", "Ljava/lang/Class", "<TT;>;)V"}) Class<T> $r3) throws  {
            this.mEventType = $r3;
            this.mId = (RequestId) Coding.checkNotNull($r1);
            this.mSubscription = (Subscription) Coding.checkNotNull($r2);
        }

        public void deliverEventWithPayload(JsonObject $r1) throws  {
            try {
                this.mSubscription.deliverResult(ResultUtils.createSuccessfulResult($r1.getAs(this.mEventType)));
            } catch (Exception $r2) {
                this.mSubscription.deliverError($r2);
            }
        }

        public int hashCode() throws  {
            return this.mId.hashCode();
        }

        public boolean equals(Object $r2) throws  {
            if ($r2 instanceof SubscriptionRecord) {
                return this.mId == ((SubscriptionRecord) $r2).mId;
            } else {
                return false;
            }
        }
    }

    RequestId getNextRequestId() throws  {
        return RequestId.from(this.mIdGenerator.getAndIncrement());
    }

    <T> CallRecord<T> newCall(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/WampCallsOrchestrator$CallRecord", "<TT;>;"}) Class<T> $r1) throws  {
        RequestId $r5 = getNextRequestId();
        CallRecord $r2 = new CallRecord($r5, new CallResult($r5), $r1);
        this.mCallsMap.put($r2.mId, $r2);
        return $r2;
    }

    <T> SubscriptionRecord<T> newSubscription(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/spotify/protocol/client/RemoteWampClient;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/WampCallsOrchestrator$SubscriptionRecord", "<TT;>;"}) RemoteWampClient $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/spotify/protocol/client/RemoteWampClient;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/WampCallsOrchestrator$SubscriptionRecord", "<TT;>;"}) Class<T> $r2) throws  {
        RequestId $r6 = getNextRequestId();
        SubscriptionRecord $r4 = new SubscriptionRecord($r6, new Subscription($r6, $r1), $r2);
        this.mSubscriptionsMap.put($r4.mId, $r4);
        return $r4;
    }

    SubscriptionRecord<?> findSubscriptionByRequestId(@Signature({"(", "Lcom/spotify/protocol/types/Types$RequestId;", ")", "Lcom/spotify/protocol/client/WampCallsOrchestrator$SubscriptionRecord", "<*>;"}) RequestId $r1) throws  {
        return (SubscriptionRecord) this.mSubscriptionsMap.get($r1);
    }

    SubscriptionRecord<?> findSubscriptionById(@Signature({"(", "Lcom/spotify/protocol/types/Types$SubscriptionId;", ")", "Lcom/spotify/protocol/client/WampCallsOrchestrator$SubscriptionRecord", "<*>;"}) SubscriptionId $r1) throws  {
        RequestId $r4 = (RequestId) this.mSubscriptionIdToRequestId.get($r1);
        return $r4 != null ? findSubscriptionByRequestId($r4) : null;
    }

    void onSubscriptionIdReceived(RequestId $r1, SubscriptionId $r2) throws  {
        this.mSubscriptionIdToRequestId.put($r2, $r1);
        SubscriptionRecord $r4 = findSubscriptionByRequestId($r1);
        if ($r4 != null) {
            $r4.mSubscriptionId = $r2;
            return;
        }
        String $r6 = String.format("Cannot find a subscription record for [%s]", new Object[]{$r1});
        Debug.m34e($r6, new Object[0]);
        Debug.assertTrue(false, $r6);
    }

    CallRecord<?> findCallById(@Signature({"(", "Lcom/spotify/protocol/types/Types$RequestId;", ")", "Lcom/spotify/protocol/client/WampCallsOrchestrator$CallRecord", "<*>;"}) RequestId $r1) throws  {
        return (CallRecord) this.mCallsMap.get($r1);
    }

    void removeCall(RequestId $r1) throws  {
        this.mCallsMap.remove($r1);
    }

    void removeSubscription(SubscriptionId $r1) throws  {
        RequestId $r4 = (RequestId) this.mSubscriptionIdToRequestId.get($r1);
        this.mSubscriptionIdToRequestId.remove($r1);
        this.mSubscriptionsMap.remove($r4);
    }

    void removeSubscriptionByRequestId(RequestId $r1) throws  {
        this.mSubscriptionsMap.remove($r1);
    }
}
