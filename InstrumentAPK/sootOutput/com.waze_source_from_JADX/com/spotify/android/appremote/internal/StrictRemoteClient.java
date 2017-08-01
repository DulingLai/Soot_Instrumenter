package com.spotify.android.appremote.internal;

import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.Coding;
import com.spotify.protocol.client.RemoteClient;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.Types.RequestId;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class StrictRemoteClient implements RemoteClient {
    private final RemoteClient mRemoteClient;
    private final List<Rule> mRules = new ArrayList();

    public interface Rule {
        Throwable getError() throws ;

        boolean isSatisfied() throws ;
    }

    public StrictRemoteClient(RemoteClient $r1) throws  {
        this.mRemoteClient = (RemoteClient) Coding.checkNotNull($r1);
    }

    public void addRule(Rule $r1) throws  {
        this.mRules.add(Coding.checkNotNull($r1));
    }

    Throwable precallCheck() throws  {
        for (Rule $r4 : this.mRules) {
            if (!$r4.isSatisfied()) {
                return $r4.getError();
            }
        }
        return null;
    }

    public <T> CallResult<T> hello(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Class<T> $r1) throws  {
        return this.mRemoteClient.hello($r1);
    }

    public void goodbye() throws  {
        this.mRemoteClient.goodbye();
    }

    public <T> CallResult<T> call(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) String $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Class<T> $r2) throws  {
        Throwable $r3 = precallCheck();
        if ($r3 == null) {
            return this.mRemoteClient.call($r1, $r2);
        }
        CallResult $r4 = new CallResult(RequestId.NONE);
        $r4.deliverError($r3);
        return $r4;
    }

    public <T> CallResult<T> call(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Object;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) String $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Object;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Object $r2, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Object;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/CallResult", "<TT;>;"}) Class<T> $r3) throws  {
        Throwable $r4 = precallCheck();
        if ($r4 == null) {
            return this.mRemoteClient.call($r1, $r2, $r3);
        }
        CallResult $r5 = new CallResult(RequestId.NONE);
        $r5.deliverError($r4);
        return $r5;
    }

    public <T> Subscription<T> subscribe(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/Subscription", "<TT;>;"}) String $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)", "Lcom/spotify/protocol/client/Subscription", "<TT;>;"}) Class<T> $r2) throws  {
        Throwable $r3 = precallCheck();
        if ($r3 == null) {
            return this.mRemoteClient.subscribe($r1, $r2);
        }
        Subscription $r4 = new Subscription(RequestId.NONE, this);
        $r4.deliverError($r3);
        return $r4;
    }

    public <T> void unsubscribe(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/spotify/protocol/client/Subscription", "<TT;>;)V"}) Subscription<T> $r1) throws  {
        Throwable $r3 = precallCheck();
        if ($r3 != null) {
            $r1.deliverError($r3);
        } else {
            this.mRemoteClient.unsubscribe($r1);
        }
    }
}
