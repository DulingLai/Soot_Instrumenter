package com.spotify.android.appremote.internal;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.spotify.android.appremote.api.ImagesApi;
import com.spotify.protocol.AppProtocol.CallUri;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.CallResult.ResultCallback;
import com.spotify.protocol.client.Coding;
import com.spotify.protocol.client.ErrorCallback;
import com.spotify.protocol.client.RemoteClient;
import com.spotify.protocol.client.ResultUtils;
import com.spotify.protocol.types.Identifier;
import com.spotify.protocol.types.Image;
import com.spotify.protocol.types.ImageUri;
import com.spotify.protocol.types.Types.RequestId;
import dalvik.annotation.Signature;

public class ImagesApiImpl implements ImagesApi {
    private final RemoteClient mRemoteClient;

    public ImagesApiImpl(RemoteClient $r1) throws  {
        this.mRemoteClient = (RemoteClient) Coding.checkNotNull($r1);
    }

    public CallResult<Bitmap> getImage(@Signature({"(", "Lcom/spotify/protocol/types/ImageUri;", ")", "Lcom/spotify/protocol/client/CallResult", "<", "Landroid/graphics/Bitmap;", ">;"}) ImageUri $r1) throws  {
        CallResult $r4 = this.mRemoteClient.call(CallUri.GET_IMAGE, new Identifier($r1.raw), Image.class);
        final CallResult $r2 = new CallResult(RequestId.NONE);
        $r4.setResultCallback(new ResultCallback<Image>() {
            public void onResult(Image $r1) throws  {
                try {
                    Options $r3 = new Options();
                    $r3.inPreferredConfig = Config.ARGB_8888;
                    $r2.deliverResult(ResultUtils.createSuccessfulResult(BitmapFactory.decodeByteArray($r1.imageData, 0, $r1.imageData.length, $r3)));
                } catch (Exception $r2) {
                    $r2.deliverError($r2);
                }
            }
        });
        $r4.setErrorCallback(new ErrorCallback() {
            public void onError(Throwable $r1) throws  {
                $r2.deliverError($r1);
            }
        });
        return $r2;
    }
}
