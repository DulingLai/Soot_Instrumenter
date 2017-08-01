package com.facebook.messenger;

import dalvik.annotation.Signature;
import java.util.List;

public class MessengerThreadParams {
    public final String metadata;
    public final Origin origin;
    public final List<String> participants;
    public final String threadToken;

    public enum Origin {
        REPLY_FLOW,
        COMPOSE_FLOW,
        UNKNOWN
    }

    public MessengerThreadParams(@Signature({"(", "Lcom/facebook/messenger/MessengerThreadParams$Origin;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) Origin $r1, @Signature({"(", "Lcom/facebook/messenger/MessengerThreadParams$Origin;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Lcom/facebook/messenger/MessengerThreadParams$Origin;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r3, @Signature({"(", "Lcom/facebook/messenger/MessengerThreadParams$Origin;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r4) throws  {
        this.threadToken = $r2;
        this.metadata = $r3;
        this.participants = $r4;
        this.origin = $r1;
    }
}
