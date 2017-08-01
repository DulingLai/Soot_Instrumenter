package com.facebook.share;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.CollectionMapper;
import com.facebook.internal.CollectionMapper.OnErrorListener;
import com.facebook.internal.CollectionMapper.OnMapValueCompleteListener;
import com.facebook.internal.CollectionMapper.OnMapperCompleteListener;
import com.facebook.internal.CollectionMapper.ValueMapper;
import com.facebook.internal.Mutable;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.Sharer.Result;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.VideoUploader;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import dalvik.annotation.Signature;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareApi {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_GRAPH_NODE = "me";
    private static final String GRAPH_PATH_FORMAT = "%s/%s";
    private static final String PHOTOS_EDGE = "photos";
    private static final String TAG = "ShareApi";
    private String graphNode = DEFAULT_GRAPH_NODE;
    private String message;
    private final ShareContent shareContent;

    class C05703 implements Callback {
        final /* synthetic */ FacebookCallback val$callback;
        final /* synthetic */ ArrayList val$errorResponses;
        final /* synthetic */ Mutable val$requestCount;
        final /* synthetic */ ArrayList val$results;

        C05703(ArrayList $r2, ArrayList $r3, Mutable $r4, FacebookCallback $r5) throws  {
            this.val$results = $r2;
            this.val$errorResponses = $r3;
            this.val$requestCount = $r4;
            this.val$callback = $r5;
        }

        public void onCompleted(GraphResponse $r1) throws  {
            JSONObject $r2 = $r1.getJSONObject();
            if ($r2 != null) {
                this.val$results.add($r2);
            }
            if ($r1.getError() != null) {
                this.val$errorResponses.add($r1);
            }
            this.val$requestCount.value = Integer.valueOf(((Integer) this.val$requestCount.value).intValue() - 1);
            if (((Integer) this.val$requestCount.value).intValue() != 0) {
                return;
            }
            if (!this.val$errorResponses.isEmpty()) {
                ShareInternalUtility.invokeCallbackWithResults(this.val$callback, null, (GraphResponse) this.val$errorResponses.get(0));
            } else if (!this.val$results.isEmpty()) {
                ShareInternalUtility.invokeCallbackWithResults(this.val$callback, ((JSONObject) this.val$results.get(0)).optString("id"), $r1);
            }
        }
    }

    class C05757 implements ValueMapper {
        C05757() throws  {
        }

        public void mapValue(Object $r2, OnMapValueCompleteListener $r1) throws  {
            if ($r2 instanceof ArrayList) {
                ShareApi.this.stageArrayList((ArrayList) $r2, $r1);
            } else if ($r2 instanceof ShareOpenGraphObject) {
                ShareApi.this.stageOpenGraphObject((ShareOpenGraphObject) $r2, $r1);
            } else if ($r2 instanceof SharePhoto) {
                ShareApi.this.stagePhoto((SharePhoto) $r2, $r1);
            } else {
                $r1.onComplete($r2);
            }
        }
    }

    private void sharePhotoContent(@dalvik.annotation.Signature({"(", "Lcom/facebook/share/model/SharePhotoContent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) com.facebook.share.model.SharePhotoContent r33, @dalvik.annotation.Signature({"(", "Lcom/facebook/share/model/SharePhotoContent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) com.facebook.FacebookCallback<com.facebook.share.Sharer.Result> r34) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0060 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r32 = this;
        r6 = new com.facebook.internal.Mutable;
        r8 = 0;
        r7 = java.lang.Integer.valueOf(r8);
        r6.<init>(r7);
        r9 = com.facebook.AccessToken.getCurrentAccessToken();
        r10 = new java.util.ArrayList;
        r10.<init>();
        r11 = new java.util.ArrayList;
        r11.<init>();
        r12 = new java.util.ArrayList;
        r12.<init>();
        r13 = new com.facebook.share.ShareApi$3;
        r0 = r13;
        r1 = r32;
        r2 = r11;
        r3 = r12;
        r4 = r6;
        r5 = r34;
        r0.<init>(r2, r3, r4, r5);
        r0 = r33;	 Catch:{ FileNotFoundException -> 0x008a }
        r14 = r0.getPhotos();	 Catch:{ FileNotFoundException -> 0x008a }
        r15 = r14.iterator();	 Catch:{ FileNotFoundException -> 0x008a }
    L_0x0034:
        r16 = r15.hasNext();	 Catch:{ FileNotFoundException -> 0x008a }
        if (r16 == 0) goto L_0x00b9;	 Catch:{ FileNotFoundException -> 0x008a }
    L_0x003a:
        r17 = r15.next();	 Catch:{ FileNotFoundException -> 0x008a }
        r19 = r17;	 Catch:{ FileNotFoundException -> 0x008a }
        r19 = (com.facebook.share.model.SharePhoto) r19;	 Catch:{ FileNotFoundException -> 0x008a }
        r18 = r19;	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r18;	 Catch:{ FileNotFoundException -> 0x008a }
        r20 = r0.getBitmap();	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r18;	 Catch:{ FileNotFoundException -> 0x008a }
        r21 = r0.getImageUrl();	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r18;	 Catch:{ FileNotFoundException -> 0x008a }
        r22 = r0.getCaption();	 Catch:{ FileNotFoundException -> 0x008a }
        r23 = r22;
        if (r22 != 0) goto L_0x0060;	 Catch:{ FileNotFoundException -> 0x008a }
    L_0x005a:
        r0 = r32;	 Catch:{ FileNotFoundException -> 0x008a }
        r23 = r0.getMessage();	 Catch:{ FileNotFoundException -> 0x008a }
    L_0x0060:
        if (r20 == 0) goto L_0x0093;	 Catch:{ FileNotFoundException -> 0x008a }
    L_0x0062:
        r24 = "photos";	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r32;	 Catch:{ FileNotFoundException -> 0x008a }
        r1 = r24;	 Catch:{ FileNotFoundException -> 0x008a }
        r22 = r0.getGraphPath(r1);	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r18;	 Catch:{ FileNotFoundException -> 0x008a }
        r25 = r0.getParameters();	 Catch:{ FileNotFoundException -> 0x008a }
        goto L_0x0076;	 Catch:{ FileNotFoundException -> 0x008a }
    L_0x0073:
        goto L_0x0034;	 Catch:{ FileNotFoundException -> 0x008a }
    L_0x0076:
        r0 = r9;	 Catch:{ FileNotFoundException -> 0x008a }
        r1 = r22;	 Catch:{ FileNotFoundException -> 0x008a }
        r2 = r20;	 Catch:{ FileNotFoundException -> 0x008a }
        r3 = r23;	 Catch:{ FileNotFoundException -> 0x008a }
        r4 = r25;	 Catch:{ FileNotFoundException -> 0x008a }
        r5 = r13;	 Catch:{ FileNotFoundException -> 0x008a }
        r26 = com.facebook.GraphRequest.newUploadPhotoRequest(r0, r1, r2, r3, r4, r5);	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r26;	 Catch:{ FileNotFoundException -> 0x008a }
        r10.add(r0);	 Catch:{ FileNotFoundException -> 0x008a }
        goto L_0x0034;
    L_0x008a:
        r27 = move-exception;
        r0 = r34;
        r1 = r27;
        com.facebook.share.internal.ShareInternalUtility.invokeCallbackWithException(r0, r1);
        return;
    L_0x0093:
        if (r21 == 0) goto L_0x0034;
    L_0x0095:
        r24 = "photos";	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r32;	 Catch:{ FileNotFoundException -> 0x008a }
        r1 = r24;	 Catch:{ FileNotFoundException -> 0x008a }
        r22 = r0.getGraphPath(r1);	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r18;	 Catch:{ FileNotFoundException -> 0x008a }
        r25 = r0.getParameters();	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r9;	 Catch:{ FileNotFoundException -> 0x008a }
        r1 = r22;	 Catch:{ FileNotFoundException -> 0x008a }
        r2 = r21;	 Catch:{ FileNotFoundException -> 0x008a }
        r3 = r23;	 Catch:{ FileNotFoundException -> 0x008a }
        r4 = r25;	 Catch:{ FileNotFoundException -> 0x008a }
        r5 = r13;	 Catch:{ FileNotFoundException -> 0x008a }
        r26 = com.facebook.GraphRequest.newUploadPhotoRequest(r0, r1, r2, r3, r4, r5);	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r26;	 Catch:{ FileNotFoundException -> 0x008a }
        r10.add(r0);	 Catch:{ FileNotFoundException -> 0x008a }
        goto L_0x0073;	 Catch:{ FileNotFoundException -> 0x008a }
    L_0x00b9:
        r0 = r6.value;
        r17 = r0;
        r28 = r17;	 Catch:{ FileNotFoundException -> 0x008a }
        r28 = (java.lang.Integer) r28;	 Catch:{ FileNotFoundException -> 0x008a }
        r7 = r28;	 Catch:{ FileNotFoundException -> 0x008a }
        r29 = r7.intValue();	 Catch:{ FileNotFoundException -> 0x008a }
        r30 = r10.size();	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r29;	 Catch:{ FileNotFoundException -> 0x008a }
        r1 = r30;	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r0 + r1;	 Catch:{ FileNotFoundException -> 0x008a }
        r29 = r0;	 Catch:{ FileNotFoundException -> 0x008a }
        r7 = java.lang.Integer.valueOf(r0);	 Catch:{ FileNotFoundException -> 0x008a }
        r6.value = r7;	 Catch:{ FileNotFoundException -> 0x008a }
        r15 = r10.iterator();	 Catch:{ FileNotFoundException -> 0x008a }
    L_0x00dc:
        r16 = r15.hasNext();	 Catch:{ FileNotFoundException -> 0x008a }
        if (r16 == 0) goto L_0x00f2;	 Catch:{ FileNotFoundException -> 0x008a }
    L_0x00e2:
        r17 = r15.next();	 Catch:{ FileNotFoundException -> 0x008a }
        r31 = r17;	 Catch:{ FileNotFoundException -> 0x008a }
        r31 = (com.facebook.GraphRequest) r31;	 Catch:{ FileNotFoundException -> 0x008a }
        r26 = r31;	 Catch:{ FileNotFoundException -> 0x008a }
        r0 = r26;	 Catch:{ FileNotFoundException -> 0x008a }
        r0.executeAsync();	 Catch:{ FileNotFoundException -> 0x008a }
        goto L_0x00dc;
    L_0x00f2:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.share.ShareApi.sharePhotoContent(com.facebook.share.model.SharePhotoContent, com.facebook.FacebookCallback):void");
    }

    public static void share(@Signature({"(", "Lcom/facebook/share/model/ShareContent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) ShareContent $r0, @Signature({"(", "Lcom/facebook/share/model/ShareContent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) FacebookCallback<Result> $r1) throws  {
        new ShareApi($r0).share($r1);
    }

    public ShareApi(ShareContent $r1) throws  {
        this.shareContent = $r1;
    }

    public String getMessage() throws  {
        return this.message;
    }

    public void setMessage(String $r1) throws  {
        this.message = $r1;
    }

    public String getGraphNode() throws  {
        return this.graphNode;
    }

    public void setGraphNode(String $r1) throws  {
        this.graphNode = $r1;
    }

    public ShareContent getShareContent() throws  {
        return this.shareContent;
    }

    public boolean canShare() throws  {
        if (getShareContent() == null) {
            return false;
        }
        AccessToken $r2 = AccessToken.getCurrentAccessToken();
        if ($r2 == null) {
            return false;
        }
        Set $r3 = $r2.getPermissions();
        if ($r3 == null || !$r3.contains("publish_actions")) {
            Log.w(TAG, "The publish_actions permissions are missing, the share will fail unless this app was authorized to publish in another installation.");
        }
        return true;
    }

    public void share(@Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) FacebookCallback<Result> $r1) throws  {
        if (canShare()) {
            ShareContent $r3 = getShareContent();
            try {
                ShareContentValidation.validateForApiShare($r3);
                if ($r3 instanceof ShareLinkContent) {
                    shareLinkContent((ShareLinkContent) $r3, $r1);
                    return;
                } else if ($r3 instanceof SharePhotoContent) {
                    sharePhotoContent((SharePhotoContent) $r3, $r1);
                    return;
                } else if ($r3 instanceof ShareVideoContent) {
                    shareVideoContent((ShareVideoContent) $r3, $r1);
                    return;
                } else if ($r3 instanceof ShareOpenGraphContent) {
                    shareOpenGraphContent((ShareOpenGraphContent) $r3, $r1);
                    return;
                } else {
                    return;
                }
            } catch (FacebookException $r2) {
                ShareInternalUtility.invokeCallbackWithException($r1, $r2);
                return;
            }
        }
        ShareInternalUtility.invokeCallbackWithError($r1, "Insufficient permissions for sharing content via Api.");
    }

    private String getGraphPath(String $r1) throws  {
        Locale $r3 = Locale.ROOT;
        Object[] $r4 = new Object[2];
        try {
            $r4[0] = URLEncoder.encode(getGraphNode(), DEFAULT_CHARSET);
            $r4[1] = $r1;
            return String.format($r3, GRAPH_PATH_FORMAT, $r4);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private void addCommonParameters(Bundle $r1, ShareContent $r2) throws  {
        Collection $r3 = $r2.getPeopleIds();
        if (!Utility.isNullOrEmpty($r3)) {
            $r1.putString("tags", TextUtils.join(", ", $r3));
        }
        if (!Utility.isNullOrEmpty($r2.getPlaceId())) {
            $r1.putString("place", $r2.getPlaceId());
        }
        if (!Utility.isNullOrEmpty($r2.getRef())) {
            $r1.putString("ref", $r2.getRef());
        }
    }

    private void shareOpenGraphContent(@Signature({"(", "Lcom/facebook/share/model/ShareOpenGraphContent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) ShareOpenGraphContent $r1, @Signature({"(", "Lcom/facebook/share/model/ShareOpenGraphContent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) final FacebookCallback<Result> $r2) throws  {
        C05681 $r4 = new Callback() {
            public void onCompleted(GraphResponse $r1) throws  {
                String $r3;
                JSONObject $r2 = $r1.getJSONObject();
                if ($r2 == null) {
                    $r3 = null;
                } else {
                    $r3 = $r2.optString("id");
                }
                ShareInternalUtility.invokeCallbackWithResults($r2, $r3, $r1);
            }
        };
        ShareOpenGraphAction $r5 = $r1.getAction();
        Bundle $r6 = $r5.getBundle();
        addCommonParameters($r6, $r1);
        if (!Utility.isNullOrEmpty(getMessage())) {
            $r6.putString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, getMessage());
        }
        final Bundle bundle = $r6;
        final ShareOpenGraphAction shareOpenGraphAction = $r5;
        final C05681 c05681 = $r4;
        final FacebookCallback<Result> facebookCallback = $r2;
        stageOpenGraphAction($r6, new OnMapperCompleteListener() {
            public void onComplete() throws  {
                try {
                    ShareApi.handleImagesOnAction(bundle);
                    new GraphRequest(AccessToken.getCurrentAccessToken(), ShareApi.this.getGraphPath(URLEncoder.encode(shareOpenGraphAction.getActionType(), ShareApi.DEFAULT_CHARSET)), bundle, HttpMethod.POST, c05681).executeAsync();
                } catch (UnsupportedEncodingException $r2) {
                    FacebookCallback $r10 = facebookCallback;
                    ShareInternalUtility.invokeCallbackWithException($r10, $r2);
                }
            }

            public void onError(FacebookException $r1) throws  {
                ShareInternalUtility.invokeCallbackWithException(facebookCallback, $r1);
            }
        });
    }

    private static void handleImagesOnAction(Bundle $r0) throws  {
        String $r3 = $r0.getString("image");
        if ($r3 != null) {
            JSONArray $r2 = new JSONArray($r3);
            for (int $i0 = 0; $i0 < $r2.length(); $i0++) {
                JSONObject $r1 = $r2.optJSONObject($i0);
                if ($r1 != null) {
                    putImageInBundleWithArrayFormat($r0, $i0, $r1);
                } else {
                    try {
                        $r0.putString(String.format(Locale.ROOT, "image[%d][url]", new Object[]{Integer.valueOf($i0)}), $r2.getString($i0));
                    } catch (JSONException e) {
                        try {
                            putImageInBundleWithArrayFormat($r0, 0, new JSONObject($r3));
                            $r0.remove("image");
                            return;
                        } catch (JSONException e2) {
                            return;
                        }
                    }
                }
            }
            $r0.remove("image");
        }
    }

    private static void putImageInBundleWithArrayFormat(Bundle $r0, int $i0, JSONObject $r1) throws JSONException {
        Iterator $r2 = $r1.keys();
        while ($r2.hasNext()) {
            String $r4 = (String) $r2.next();
            $r0.putString(String.format(Locale.ROOT, "image[%d][%s]", new Object[]{Integer.valueOf($i0), $r4}), $r1.get($r4).toString());
        }
    }

    private void shareLinkContent(@Signature({"(", "Lcom/facebook/share/model/ShareLinkContent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) ShareLinkContent $r1, @Signature({"(", "Lcom/facebook/share/model/ShareLinkContent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) FacebookCallback<Result> $r2) throws  {
        final FacebookCallback<Result> facebookCallback = $r2;
        C05714 $r5 = new Callback() {
            public void onCompleted(GraphResponse $r1) throws  {
                String $r3;
                JSONObject $r2 = $r1.getJSONObject();
                if ($r2 == null) {
                    $r3 = null;
                } else {
                    $r3 = $r2.optString("id");
                }
                ShareInternalUtility.invokeCallbackWithResults(facebookCallback, $r3, $r1);
            }
        };
        Bundle $r3 = new Bundle();
        addCommonParameters($r3, $r1);
        $r3.putString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, getMessage());
        $r3.putString("link", Utility.getUriString($r1.getContentUrl()));
        $r3.putString("picture", Utility.getUriString($r1.getImageUrl()));
        $r3.putString("name", $r1.getContentTitle());
        $r3.putString("description", $r1.getContentDescription());
        $r3.putString("ref", $r1.getRef());
        new GraphRequest(AccessToken.getCurrentAccessToken(), getGraphPath("feed"), $r3, HttpMethod.POST, $r5).executeAsync();
    }

    private void shareVideoContent(@Signature({"(", "Lcom/facebook/share/model/ShareVideoContent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) ShareVideoContent $r1, @Signature({"(", "Lcom/facebook/share/model/ShareVideoContent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) FacebookCallback<Result> $r2) throws  {
        try {
            VideoUploader.uploadAsync($r1, getGraphNode(), $r2);
        } catch (FileNotFoundException $r3) {
            ShareInternalUtility.invokeCallbackWithException($r2, $r3);
        }
    }

    private void stageArrayList(final ArrayList $r1, final OnMapValueCompleteListener $r2) throws  {
        final JSONArray $r5 = new JSONArray();
        stageCollectionValues(new CollectionMapper.Collection<Integer>() {
            public Iterator<Integer> keyIterator() throws  {
                final int $i0 = $r1.size();
                final Mutable $r1 = new Mutable(Integer.valueOf(0));
                return new Iterator<Integer>() {
                    public boolean hasNext() throws  {
                        return ((Integer) $r1.value).intValue() < $i0;
                    }

                    public Integer next() throws  {
                        Integer $r3 = (Integer) $r1.value;
                        Mutable $r1 = $r1;
                        $r1.value = Integer.valueOf(((Integer) $r1.value).intValue() + 1);
                        return $r3;
                    }

                    public void remove() throws  {
                    }
                };
            }

            public Object get(Integer $r1) throws  {
                return $r1.get($r1.intValue());
            }

            public void set(Integer $r1, Object $r2, OnErrorListener $r3) throws  {
                try {
                    $r5.put($r1.intValue(), $r2);
                } catch (JSONException $r4) {
                    String $r6 = $r4.getLocalizedMessage();
                    String $r7 = $r6;
                    if ($r6 == null) {
                        $r7 = "Error staging object.";
                    }
                    $r3.onError(new FacebookException($r7));
                }
            }
        }, new OnMapperCompleteListener() {
            public void onComplete() throws  {
                $r2.onComplete($r5);
            }

            public void onError(FacebookException $r1) throws  {
                $r2.onError($r1);
            }
        });
    }

    private <T> void stageCollectionValues(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/facebook/internal/CollectionMapper$Collection", "<TT;>;", "Lcom/facebook/internal/CollectionMapper$OnMapperCompleteListener;", ")V"}) CollectionMapper.Collection<T> $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/facebook/internal/CollectionMapper$Collection", "<TT;>;", "Lcom/facebook/internal/CollectionMapper$OnMapperCompleteListener;", ")V"}) OnMapperCompleteListener $r2) throws  {
        CollectionMapper.iterate($r1, new C05757(), $r2);
    }

    private void stageOpenGraphAction(final Bundle $r1, OnMapperCompleteListener $r2) throws  {
        stageCollectionValues(new CollectionMapper.Collection<String>() {
            public Iterator<String> keyIterator() throws  {
                return $r1.keySet().iterator();
            }

            public Object get(String $r1) throws  {
                return $r1.get($r1);
            }

            public void set(String $r1, Object $r2, OnErrorListener $r3) throws  {
                if (!Utility.putJSONValueInBundle($r1, $r1, $r2)) {
                    $r3.onError(new FacebookException("Unexpected value: " + $r2.toString()));
                }
            }
        }, $r2);
    }

    private void stageOpenGraphObject(final ShareOpenGraphObject $r1, OnMapValueCompleteListener $r2) throws  {
        String $r7 = $r1.getString("type");
        String $r8 = $r7;
        if ($r7 == null) {
            $r8 = $r1.getString("og:type");
        }
        if ($r8 == null) {
            $r2.onError(new FacebookException("Open Graph objects must contain a type value."));
            return;
        }
        final JSONObject $r4 = new JSONObject();
        C05779 $r6 = new CollectionMapper.Collection<String>() {
            public Iterator<String> keyIterator() throws  {
                return $r1.keySet().iterator();
            }

            public Object get(String $r1) throws  {
                return $r1.get($r1);
            }

            public void set(String $r1, Object $r2, OnErrorListener $r3) throws  {
                try {
                    $r4.put($r1, $r2);
                } catch (JSONException $r4) {
                    $r1 = $r4.getLocalizedMessage();
                    String $r6 = $r1;
                    if ($r1 == null) {
                        $r6 = "Error staging object.";
                    }
                    $r3.onError(new FacebookException($r6));
                }
            }
        };
        final OnMapValueCompleteListener onMapValueCompleteListener = $r2;
        final JSONObject jSONObject = $r4;
        final String str = $r8;
        final AnonymousClass10 anonymousClass10 = new Callback() {
            public void onCompleted(GraphResponse $r1) throws  {
                FacebookRequestError $r2 = $r1.getError();
                if ($r2 != null) {
                    String $r3;
                    $r3 = $r2.getErrorMessage();
                    String $r4 = $r3;
                    if ($r3 == null) {
                        $r4 = "Error staging Open Graph object.";
                    }
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException($r1, $r4));
                    return;
                }
                JSONObject $r7 = $r1.getJSONObject();
                if ($r7 == null) {
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException($r1, "Error staging Open Graph object."));
                    return;
                }
                $r3 = $r7.optString("id");
                if ($r3 == null) {
                    onMapValueCompleteListener.onError(new FacebookGraphResponseException($r1, "Error staging Open Graph object."));
                } else {
                    onMapValueCompleteListener.onComplete($r3);
                }
            }
        };
        final OnMapValueCompleteListener onMapValueCompleteListener2 = $r2;
        stageCollectionValues($r6, new OnMapperCompleteListener() {
            public void onComplete() throws  {
                String $r4 = jSONObject.toString();
                Bundle $r1 = new Bundle();
                $r1.putString("object", $r4);
                try {
                    new GraphRequest(AccessToken.getCurrentAccessToken(), ShareApi.this.getGraphPath("objects/" + URLEncoder.encode(str, ShareApi.DEFAULT_CHARSET)), $r1, HttpMethod.POST, anonymousClass10).executeAsync();
                } catch (UnsupportedEncodingException $r2) {
                    $r4 = $r2.getLocalizedMessage();
                    String $r11 = $r4;
                    if ($r4 == null) {
                        $r11 = "Error staging Open Graph object.";
                    }
                    onMapValueCompleteListener2.onError(new FacebookException($r11));
                }
            }

            public void onError(FacebookException $r1) throws  {
                onMapValueCompleteListener2.onError($r1);
            }
        });
    }

    private void stagePhoto(final SharePhoto $r1, final OnMapValueCompleteListener $r2) throws  {
        Bitmap $r5 = $r1.getBitmap();
        Uri $r6 = $r1.getImageUrl();
        if ($r5 == null && $r6 == null) {
            $r2.onError(new FacebookException("Photos must have an imageURL or bitmap."));
            return;
        }
        Callback $r4 = new Callback() {
            public void onCompleted(GraphResponse $r1) throws  {
                String $r5;
                String $r6;
                FacebookRequestError $r4 = $r1.getError();
                if ($r4 != null) {
                    $r5 = $r4.getErrorMessage();
                    $r6 = $r5;
                    if ($r5 == null) {
                        $r6 = "Error staging photo.";
                    }
                    $r2.onError(new FacebookGraphResponseException($r1, $r6));
                    return;
                }
                JSONObject $r3 = $r1.getJSONObject();
                if ($r3 == null) {
                    $r2.onError(new FacebookException("Error staging photo."));
                    return;
                }
                $r5 = $r3.optString("uri");
                if ($r5 == null) {
                    $r2.onError(new FacebookException("Error staging photo."));
                    return;
                }
                $r3 = new JSONObject();
                try {
                    $r3.put("url", $r5);
                    $r3.put(NativeProtocol.IMAGE_USER_GENERATED_KEY, $r1.getUserGenerated());
                    $r2.onComplete($r3);
                } catch (JSONException $r2) {
                    $r5 = $r2.getLocalizedMessage();
                    $r6 = $r5;
                    if ($r5 == null) {
                        $r6 = "Error staging photo.";
                    }
                    $r2.onError(new FacebookException($r6));
                }
            }
        };
        if ($r5 != null) {
            ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), $r5, $r4).executeAsync();
            return;
        }
        try {
            ShareInternalUtility.newUploadStagingResourceWithImageRequest(AccessToken.getCurrentAccessToken(), $r6, $r4).executeAsync();
        } catch (FileNotFoundException $r3) {
            String $r9 = $r3.getLocalizedMessage();
            String $r10 = $r9;
            if ($r9 == null) {
                $r10 = "Error staging photo.";
            }
            $r2.onError(new FacebookException($r10));
        }
    }
}
