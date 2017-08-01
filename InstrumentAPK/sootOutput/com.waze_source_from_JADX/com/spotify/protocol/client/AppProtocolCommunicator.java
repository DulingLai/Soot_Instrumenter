package com.spotify.protocol.client;

import com.spotify.protocol.AppProtocol;
import com.spotify.protocol.WampClient;
import com.spotify.protocol.WampClient.Receiver;
import com.spotify.protocol.WampClient.Router;
import com.spotify.protocol.WampClient.Sender;
import com.spotify.protocol.WampMessage;
import com.spotify.protocol.client.AppProtocolIo.DataInput;
import com.spotify.protocol.error.SpotifyAppRemoteException;
import com.spotify.protocol.mappers.JsonMapper;
import com.spotify.protocol.mappers.JsonMappingException;
import com.spotify.protocol.types.Empty;
import com.spotify.protocol.types.HelloDetails;
import com.spotify.protocol.types.Info;
import com.spotify.protocol.types.Message;
import com.spotify.protocol.types.Roles;
import dalvik.annotation.Signature;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AppProtocolCommunicator implements Sender, WampClient, DataInput {
    private static final int PROTOCOL_VERSION = 1;
    private static final String SPOTIFY_REALM = "spotify";
    private final HelloDetails mHelloDetails;
    private final JsonMapper mJsonMapper;
    private final AppProtocolIo mProtocolIo;
    private final Router mRouter = new WampRouterImpl();

    public AppProtocolCommunicator(ConnectionDetails $r1, JsonMapper $r2, AppProtocolIo $r3) throws  {
        AppProtocolCommunicator appProtocolCommunicator = this;
        this.mJsonMapper = $r2;
        this.mProtocolIo = $r3;
        Roles $r5 = new Roles(null, null, new Empty(), new Empty());
        Info info = new Info(1, $r1.getRequiredFeatures(), $r1.getId(), $r1.getName(), $r1.getModel(), null, $r1.getImageWidth(), $r1.getImageHeight(), $r1.getThumbnailImageHeight(), $r1.getThumbnailImageHeight());
        Map $r13 = $r1.getExtras();
        this.mHelloDetails = new HelloDetails($r5, info, $r1.getAuthMethods(), $r1.getAuthId(), $r13);
        AppProtocolIo appProtocolIo = this.mProtocolIo;
        $r3 = appProtocolIo;
        appProtocolIo.setDataInput(this);
    }

    public void setMessageReceiver(Receiver $r1) throws  {
        this.mRouter.addReceiver($r1);
    }

    public void onData(byte[] $r1, int length) throws  {
        try {
            this.mRouter.route(new WampMessage(this.mJsonMapper.toJsonArray(new String($r1))));
        } catch (JsonMappingException $r2) {
            Debug.m35e($r2, "Message is not parsed.", new Object[0]);
        }
    }

    private void sendWamp(Object[] $r1) throws SpotifyAppRemoteException {
        try {
            byte[] $r6 = this.mJsonMapper.toJson(Arrays.asList($r1)).getBytes();
            this.mProtocolIo.writeData($r6, $r6.length);
        } catch (Throwable $r2) {
            throw new SpotifyAppRemoteException($r2);
        }
    }

    public void sendHello() throws SpotifyAppRemoteException {
        sendWamp(new Object[]{Integer.valueOf(1), SPOTIFY_REALM, this.mHelloDetails});
    }

    public void sendGoodbye() throws SpotifyAppRemoteException {
        Message $r1 = new Message("The client is shutting down");
        sendWamp(new Object[]{Integer.valueOf(6), $r1, "wamp.error.system_shutdown"});
    }

    public void sendSubscribe(int $i0, Object $r1, String $r2) throws SpotifyAppRemoteException {
        sendWamp(new Object[]{Integer.valueOf(32), Integer.valueOf($i0), $r1, $r2});
    }

    public void sendUnsubscribe(int $i0, int $i1) throws SpotifyAppRemoteException {
        sendWamp(new Object[]{Integer.valueOf(34), Integer.valueOf($i0), Integer.valueOf($i1)});
    }

    public void sendCall(int $i0, Object options, String $r2) throws SpotifyAppRemoteException {
        sendWamp(new Object[]{Integer.valueOf(48), Integer.valueOf($i0), AppProtocol.EMPTY, $r2});
    }

    public void sendCall(@Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) Object options, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) String $r2, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;)V"}) List<Object> $r3) throws SpotifyAppRemoteException {
        sendWamp(new Object[]{Integer.valueOf(48), Integer.valueOf($i0), AppProtocol.EMPTY, $r2, $r3});
    }

    public void sendCall(@Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) int $i0, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) Object options, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) String $r2, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) List<Object> $r3, @Signature({"(I", "Ljava/lang/Object;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;", "Ljava/lang/Object;", ")V"}) Object $r4) throws SpotifyAppRemoteException {
        sendWamp(new Object[]{Integer.valueOf(48), Integer.valueOf($i0), AppProtocol.EMPTY, $r2, $r3, $r4});
    }

    public void sendCancel(int requestId, Object options) throws  {
    }
}
