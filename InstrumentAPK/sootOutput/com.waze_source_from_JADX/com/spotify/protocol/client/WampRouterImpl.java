package com.spotify.protocol.client;

import com.spotify.protocol.WampClient.Receiver;
import com.spotify.protocol.WampClient.RequestType;
import com.spotify.protocol.WampClient.Router;
import com.spotify.protocol.WampMessage;
import com.spotify.protocol.types.Types.RequestId;
import com.spotify.protocol.types.Types.SubscriptionId;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WampRouterImpl implements RequestType, Router {
    private final Set<Receiver> mReceivers = Collections.newSetFromMap(new ConcurrentHashMap());

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean route(com.spotify.protocol.WampMessage r4) throws  {
        /*
        r3 = this;
        r0 = r4.getAction();	 Catch:{ IOException -> 0x0013 }
        switch(r0) {
            case 2: goto L_0x000a;
            case 3: goto L_0x000f;
            case 6: goto L_0x0019;
            case 8: goto L_0x0029;
            case 33: goto L_0x0025;
            case 35: goto L_0x0008;
            case 36: goto L_0x0021;
            case 50: goto L_0x001d;
            default: goto L_0x0007;
        };	 Catch:{ IOException -> 0x0013 }
    L_0x0007:
        goto L_0x0008;
    L_0x0008:
        r1 = 0;
        return r1;
    L_0x000a:
        r3.routeWelcome(r4);	 Catch:{ IOException -> 0x0013 }
    L_0x000d:
        r1 = 1;
        return r1;
    L_0x000f:
        r3.routeAbort(r4);	 Catch:{ IOException -> 0x0013 }
        goto L_0x000d;
    L_0x0013:
        r2 = move-exception;
        r3.handleIoError(r2);
        r1 = 0;
        return r1;
    L_0x0019:
        r3.routeGoodbye(r4);	 Catch:{ IOException -> 0x0013 }
        goto L_0x000d;
    L_0x001d:
        r3.routeResult(r4);	 Catch:{ IOException -> 0x0013 }
        goto L_0x000d;
    L_0x0021:
        r3.routeEvent(r4);	 Catch:{ IOException -> 0x0013 }
        goto L_0x000d;
    L_0x0025:
        r3.routeSubscribed(r4);	 Catch:{ IOException -> 0x0013 }
        goto L_0x000d;
    L_0x0029:
        r3.routeError(r4);	 Catch:{ IOException -> 0x0013 }
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.spotify.protocol.client.WampRouterImpl.route(com.spotify.protocol.WampMessage):boolean");
    }

    public void addReceiver(Receiver $r1) throws  {
        this.mReceivers.add($r1);
    }

    public void removeReceiver(Receiver $r1) throws  {
        this.mReceivers.remove($r1);
    }

    private void routeError(WampMessage $r1) throws IOException {
        for (Receiver $r5 : this.mReceivers) {
            switch ($r1.getIntAt(1)) {
                case 32:
                    $r5.onSubscribeError(RequestId.from($r1.getIntAt(2)), $r1.getObjectAt(3), $r1.getStringAt(4));
                    break;
                case 34:
                    $r5.onUnubscribeError(RequestId.from($r1.getIntAt(2)), $r1.getObjectAt(3), $r1.getStringAt(4));
                    break;
                case 48:
                    $r5.onError(RequestId.from($r1.getIntAt(2)), $r1.getObjectAt(3), $r1.getStringAt(4));
                    break;
                default:
                    break;
            }
        }
    }

    private void routeAbort(WampMessage $r1) throws IOException {
        for (Receiver onAbort : this.mReceivers) {
            onAbort.onAbort($r1.getObjectAt(1), $r1.getStringAt(2));
        }
    }

    private void routeGoodbye(WampMessage $r1) throws IOException {
        for (Receiver onGoodbye : this.mReceivers) {
            onGoodbye.onGoodbye($r1.getObjectAt(1), $r1.getStringAt(2));
        }
    }

    private void routeResult(WampMessage $r1) throws  {
        for (Receiver onResult : this.mReceivers) {
            onResult.onResult(RequestId.from($r1.getIntAt(1)), $r1.getObjectAt(2), $r1.getObjectAt(3), $r1.getObjectAt(4));
        }
    }

    private void routeWelcome(WampMessage $r1) throws IOException {
        for (Receiver onWelcome : this.mReceivers) {
            onWelcome.onWelcome($r1.getIntAt(1), $r1.getObjectAt(2));
        }
    }

    private void routeEvent(WampMessage $r1) throws  {
        for (Receiver onEvent : this.mReceivers) {
            onEvent.onEvent(SubscriptionId.from($r1.getIntAt(1)), $r1.getIntAt(2), $r1.getObjectAt(5));
        }
    }

    private void routeSubscribed(WampMessage $r1) throws  {
        for (Receiver onSubscribed : this.mReceivers) {
            onSubscribed.onSubscribed(RequestId.from($r1.getIntAt(1)), SubscriptionId.from($r1.getIntAt(2)));
        }
    }

    private void handleIoError(Exception e) throws  {
    }
}
