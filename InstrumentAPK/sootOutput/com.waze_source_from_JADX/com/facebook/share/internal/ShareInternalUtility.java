package com.facebook.share.internal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.ParcelableResourceWithMimeType;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.NativeAppCallAttachmentStore;
import com.facebook.internal.NativeAppCallAttachmentStore.Attachment;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.Mapper;
import com.facebook.share.Sharer.Result;
import com.facebook.share.internal.OpenGraphJSONUtility.PhotoJSONProcessor;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.LikeView.ObjectType;
import dalvik.annotation.Signature;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareInternalUtility {
    public static final String MY_PHOTOS = "me/photos";
    private static final String MY_STAGING_RESOURCES = "me/staging_resources";
    private static final String STAGING_PARAM = "file";

    static class C06015 implements Mapper<Attachment, String> {
        C06015() throws  {
        }

        public String apply(Attachment $r1) throws  {
            return $r1.getAttachmentUrl();
        }
    }

    static class C06037 implements PhotoJSONProcessor {
        C06037() throws  {
        }

        public JSONObject toJSONObject(SharePhoto $r1) throws  {
            Uri $r4 = $r1.getImageUrl();
            JSONObject $r3 = new JSONObject();
            try {
                $r3.put("url", $r4.toString());
                return $r3;
            } catch (Throwable $r2) {
                throw new FacebookException("Unable to attach images", $r2);
            }
        }
    }

    public static org.json.JSONObject removeNamespacesFromOGJsonObject(org.json.JSONObject r25, boolean r26) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0071 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        if (r25 != 0) goto L_0x0004;
    L_0x0002:
        r2 = 0;
        return r2;
    L_0x0004:
        r3 = new org.json.JSONObject;
        r3.<init>();	 Catch:{ JSONException -> 0x0085 }
        r4 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0085 }
        r4.<init>();	 Catch:{ JSONException -> 0x0085 }
        r0 = r25;	 Catch:{ JSONException -> 0x0085 }
        r5 = r0.names();	 Catch:{ JSONException -> 0x0085 }
        r6 = 0;	 Catch:{ JSONException -> 0x0085 }
    L_0x0015:
        r7 = r5.length();	 Catch:{ JSONException -> 0x0085 }
        if (r6 >= r7) goto L_0x00b0;	 Catch:{ JSONException -> 0x0085 }
    L_0x001b:
        r8 = r5.getString(r6);	 Catch:{ JSONException -> 0x0085 }
        r0 = r25;	 Catch:{ JSONException -> 0x0085 }
        r9 = r0.get(r8);	 Catch:{ JSONException -> 0x0085 }
        r10 = r9;
        r11 = r9 instanceof org.json.JSONObject;
        if (r11 == 0) goto L_0x005d;
    L_0x002a:
        r13 = r9;	 Catch:{ JSONException -> 0x0085 }
        r13 = (org.json.JSONObject) r13;	 Catch:{ JSONException -> 0x0085 }
        r12 = r13;	 Catch:{ JSONException -> 0x0085 }
        r14 = 1;	 Catch:{ JSONException -> 0x0085 }
        r10 = removeNamespacesFromOGJsonObject(r12, r14);	 Catch:{ JSONException -> 0x0085 }
    L_0x0033:
        r15 = getFieldNameAndNamespaceFromFullName(r8);	 Catch:{ JSONException -> 0x0085 }
        r9 = r15.first;
        r17 = r9;
        r17 = (java.lang.String) r17;
        r16 = r17;
        r9 = r15.second;
        r19 = r9;
        r19 = (java.lang.String) r19;
        r18 = r19;
        if (r26 == 0) goto L_0x0098;
    L_0x0049:
        if (r16 == 0) goto L_0x0071;	 Catch:{ JSONException -> 0x0085 }
    L_0x004b:
        r20 = "fbsdk";	 Catch:{ JSONException -> 0x0085 }
        r0 = r16;	 Catch:{ JSONException -> 0x0085 }
        r1 = r20;	 Catch:{ JSONException -> 0x0085 }
        r11 = r0.equals(r1);	 Catch:{ JSONException -> 0x0085 }
        if (r11 == 0) goto L_0x0071;	 Catch:{ JSONException -> 0x0085 }
    L_0x0057:
        r3.put(r8, r10);	 Catch:{ JSONException -> 0x0085 }
    L_0x005a:
        r6 = r6 + 1;
        goto L_0x0015;	 Catch:{ JSONException -> 0x0085 }
    L_0x005d:
        r11 = r9 instanceof org.json.JSONArray;
        if (r11 == 0) goto L_0x0033;	 Catch:{ JSONException -> 0x0085 }
    L_0x0061:
        r22 = r9;	 Catch:{ JSONException -> 0x0085 }
        r22 = (org.json.JSONArray) r22;	 Catch:{ JSONException -> 0x0085 }
        r21 = r22;	 Catch:{ JSONException -> 0x0085 }
        r14 = 1;	 Catch:{ JSONException -> 0x0085 }
        r0 = r21;	 Catch:{ JSONException -> 0x0085 }
        r21 = removeNamespacesFromOGJsonArray(r0, r14);	 Catch:{ JSONException -> 0x0085 }
        r10 = r21;
        goto L_0x0033;	 Catch:{ JSONException -> 0x0085 }
    L_0x0071:
        if (r16 == 0) goto L_0x007f;	 Catch:{ JSONException -> 0x0085 }
    L_0x0073:
        r20 = "og";	 Catch:{ JSONException -> 0x0085 }
        r0 = r16;	 Catch:{ JSONException -> 0x0085 }
        r1 = r20;	 Catch:{ JSONException -> 0x0085 }
        r11 = r0.equals(r1);	 Catch:{ JSONException -> 0x0085 }
        if (r11 == 0) goto L_0x0092;	 Catch:{ JSONException -> 0x0085 }
    L_0x007f:
        r0 = r18;	 Catch:{ JSONException -> 0x0085 }
        r3.put(r0, r10);	 Catch:{ JSONException -> 0x0085 }
        goto L_0x005a;
    L_0x0085:
        r23 = move-exception;
        r24 = new com.facebook.FacebookException;
        r20 = "Failed to create json object from share content";
        r0 = r24;
        r1 = r20;
        r0.<init>(r1);
        throw r24;
    L_0x0092:
        r0 = r18;	 Catch:{ JSONException -> 0x0085 }
        r4.put(r0, r10);	 Catch:{ JSONException -> 0x0085 }
        goto L_0x005a;
    L_0x0098:
        if (r16 == 0) goto L_0x00aa;	 Catch:{ JSONException -> 0x0085 }
    L_0x009a:
        r20 = "fb";	 Catch:{ JSONException -> 0x0085 }
        r0 = r16;	 Catch:{ JSONException -> 0x0085 }
        r1 = r20;	 Catch:{ JSONException -> 0x0085 }
        r11 = r0.equals(r1);	 Catch:{ JSONException -> 0x0085 }
        if (r11 == 0) goto L_0x00aa;	 Catch:{ JSONException -> 0x0085 }
    L_0x00a6:
        r3.put(r8, r10);	 Catch:{ JSONException -> 0x0085 }
        goto L_0x005a;
    L_0x00aa:
        r0 = r18;	 Catch:{ JSONException -> 0x0085 }
        r3.put(r0, r10);	 Catch:{ JSONException -> 0x0085 }
        goto L_0x005a;
    L_0x00b0:
        r6 = r4.length();	 Catch:{ JSONException -> 0x0085 }
        if (r6 <= 0) goto L_0x00be;	 Catch:{ JSONException -> 0x0085 }
    L_0x00b6:
        r20 = "data";	 Catch:{ JSONException -> 0x0085 }
        r0 = r20;	 Catch:{ JSONException -> 0x0085 }
        r3.put(r0, r4);	 Catch:{ JSONException -> 0x0085 }
        return r3;
    L_0x00be:
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.share.internal.ShareInternalUtility.removeNamespacesFromOGJsonObject(org.json.JSONObject, boolean):org.json.JSONObject");
    }

    public static void invokeCallbackWithException(@Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Ljava/lang/Exception;", ")V"}) FacebookCallback<Result> $r0, @Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Ljava/lang/Exception;", ")V"}) Exception $r1) throws  {
        if ($r1 instanceof FacebookException) {
            invokeOnErrorCallback((FacebookCallback) $r0, (FacebookException) $r1);
        } else {
            invokeCallbackWithError($r0, "Error preparing share content: " + $r1.getLocalizedMessage());
        }
    }

    public static void invokeCallbackWithError(@Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Ljava/lang/String;", ")V"}) FacebookCallback<Result> $r0, @Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Ljava/lang/String;", ")V"}) String $r1) throws  {
        invokeOnErrorCallback((FacebookCallback) $r0, $r1);
    }

    public static void invokeCallbackWithResults(@Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Ljava/lang/String;", "Lcom/facebook/GraphResponse;", ")V"}) FacebookCallback<Result> $r0, @Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Ljava/lang/String;", "Lcom/facebook/GraphResponse;", ")V"}) String $r1, @Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Ljava/lang/String;", "Lcom/facebook/GraphResponse;", ")V"}) GraphResponse $r2) throws  {
        FacebookRequestError $r3 = $r2.getError();
        if ($r3 != null) {
            $r1 = $r3.getErrorMessage();
            String $r4 = $r1;
            if (Utility.isNullOrEmpty($r1)) {
                $r4 = "Unexpected error sharing.";
            }
            invokeOnErrorCallback($r0, $r2, $r4);
            return;
        }
        invokeOnSuccessCallback($r0, $r1);
    }

    public static String getNativeDialogCompletionGesture(Bundle $r0) throws  {
        if ($r0.containsKey(NativeProtocol.RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY)) {
            return $r0.getString(NativeProtocol.RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY);
        }
        return $r0.getString(NativeProtocol.EXTRA_DIALOG_COMPLETION_GESTURE_KEY);
    }

    public static String getShareDialogPostId(Bundle $r0) throws  {
        if ($r0.containsKey(ShareConstants.RESULT_POST_ID)) {
            return $r0.getString(ShareConstants.RESULT_POST_ID);
        }
        if ($r0.containsKey(ShareConstants.EXTRA_RESULT_POST_ID)) {
            return $r0.getString(ShareConstants.EXTRA_RESULT_POST_ID);
        }
        return $r0.getString(ShareConstants.WEB_DIALOG_RESULT_PARAM_POST_ID);
    }

    public static boolean handleActivityResult(int $i0, int $i1, Intent $r0, ResultProcessor $r1) throws  {
        AppCall $r2 = getAppCallFromActivityResult($i0, $i1, $r0);
        if ($r2 == null) {
            return false;
        }
        NativeAppCallAttachmentStore.cleanupAttachmentsForCall($r2.getCallId());
        if ($r1 == null) {
            return true;
        }
        FacebookException $r5 = NativeProtocol.getExceptionFromErrorData(NativeProtocol.getErrorDataFromResultIntent($r0));
        if ($r5 == null) {
            $r1.onSuccess($r2, NativeProtocol.getSuccessResultsFromIntent($r0));
            return true;
        } else if ($r5 instanceof FacebookOperationCanceledException) {
            $r1.onCancel($r2);
            return true;
        } else {
            $r1.onError($r2, $r5);
            return true;
        }
    }

    public static ResultProcessor getShareResultProcessor(@Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)", "Lcom/facebook/share/internal/ResultProcessor;"}) final FacebookCallback<Result> $r0) throws  {
        return new ResultProcessor($r0) {
            public void onSuccess(AppCall appCall, Bundle $r2) throws  {
                if ($r2 != null) {
                    String $r4 = ShareInternalUtility.getNativeDialogCompletionGesture($r2);
                    if ($r4 == null || "post".equalsIgnoreCase($r4)) {
                        ShareInternalUtility.invokeOnSuccessCallback($r0, ShareInternalUtility.getShareDialogPostId($r2));
                    } else if ("cancel".equalsIgnoreCase($r4)) {
                        ShareInternalUtility.invokeOnCancelCallback($r0);
                    } else {
                        ShareInternalUtility.invokeOnErrorCallback($r0, new FacebookException(NativeProtocol.ERROR_UNKNOWN_ERROR));
                    }
                }
            }

            public void onCancel(AppCall appCall) throws  {
                ShareInternalUtility.invokeOnCancelCallback($r0);
            }

            public void onError(AppCall appCall, FacebookException $r2) throws  {
                ShareInternalUtility.invokeOnErrorCallback($r0, $r2);
            }
        };
    }

    private static AppCall getAppCallFromActivityResult(int $i0, int resultCode, Intent $r0) throws  {
        UUID $r1 = NativeProtocol.getCallIdFromIntent($r0);
        return $r1 == null ? null : AppCall.finishPendingCall($r1, $i0);
    }

    public static void registerStaticShareCallback(final int $i0) throws  {
        CallbackManagerImpl.registerStaticCallback($i0, new Callback() {
            public boolean onActivityResult(int $i0, Intent $r1) throws  {
                return ShareInternalUtility.handleActivityResult($i0, $i0, $r1, ShareInternalUtility.getShareResultProcessor(null));
            }
        });
    }

    public static void registerSharerCallback(@Signature({"(I", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) final int $i0, @Signature({"(I", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) CallbackManager $r1, @Signature({"(I", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) final FacebookCallback<Result> $r0) throws  {
        if ($r1 instanceof CallbackManagerImpl) {
            ((CallbackManagerImpl) $r1).registerCallback($i0, new Callback() {
                public boolean onActivityResult(int $i0, Intent $r1) throws  {
                    return ShareInternalUtility.handleActivityResult($i0, $i0, $r1, ShareInternalUtility.getShareResultProcessor($r0));
                }
            });
            return;
        }
        throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
    }

    public static List<String> getPhotoUrls(@Signature({"(", "Lcom/facebook/share/model/SharePhotoContent;", "Ljava/util/UUID;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) SharePhotoContent $r0, @Signature({"(", "Lcom/facebook/share/model/SharePhotoContent;", "Ljava/util/UUID;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) final UUID $r1) throws  {
        if ($r0 != null) {
            List $r2 = $r0.getPhotos();
            if ($r2 != null) {
                $r2 = Utility.map($r2, new Mapper<SharePhoto, Attachment>() {
                    public Attachment apply(SharePhoto $r1) throws  {
                        return ShareInternalUtility.getAttachment($r1, $r1);
                    }
                });
                List $r5 = Utility.map($r2, new C06015());
                NativeAppCallAttachmentStore.addAttachments($r2);
                return $r5;
            }
        }
        return null;
    }

    public static String getVideoUrl(ShareVideoContent $r0, UUID $r1) throws  {
        if ($r0 == null || $r0.getVideo() == null) {
            return null;
        }
        Attachment $r5 = NativeAppCallAttachmentStore.createAttachment($r1, $r0.getVideo().getLocalUrl());
        ArrayList $r2 = new ArrayList(1);
        $r2.add($r5);
        NativeAppCallAttachmentStore.addAttachments($r2);
        return $r5.getAttachmentUrl();
    }

    public static JSONObject toJSONObjectForCall(UUID $r0, ShareOpenGraphContent $r1) throws JSONException {
        ShareOpenGraphAction $r3 = $r1.getAction();
        final ArrayList $r2 = r12;
        ArrayList r12 = new ArrayList();
        final UUID uuid = $r0;
        JSONObject $r5 = OpenGraphJSONUtility.toJSONObject($r3, new PhotoJSONProcessor() {
            public JSONObject toJSONObject(SharePhoto $r1) throws  {
                Attachment $r4 = ShareInternalUtility.getAttachment(uuid, $r1);
                if ($r4 == null) {
                    return null;
                }
                $r2.add($r4);
                JSONObject $r5 = new JSONObject();
                try {
                    $r5.put("url", $r4.getAttachmentUrl());
                    if (!$r1.getUserGenerated()) {
                        return $r5;
                    }
                    $r5.put(NativeProtocol.IMAGE_USER_GENERATED_KEY, true);
                    return $r5;
                } catch (Throwable $r2) {
                    throw new FacebookException("Unable to attach images", $r2);
                }
            }
        });
        NativeAppCallAttachmentStore.addAttachments($r2);
        if ($r1.getPlaceId() != null && Utility.isNullOrEmpty($r5.optString("place"))) {
            $r5.put("place", $r1.getPlaceId());
        }
        if ($r1.getPeopleIds() == null) {
            return $r5;
        }
        Set $r9;
        JSONArray $r8 = $r5.optJSONArray("tags");
        if ($r8 == null) {
            $r9 = r14;
            HashSet r14 = new HashSet();
        } else {
            $r9 = Utility.jsonArrayToSet($r8);
        }
        for (String add : $r1.getPeopleIds()) {
            $r9.add(add);
        }
        $r5.put("tags", new ArrayList($r9));
        return $r5;
    }

    public static JSONObject toJSONObjectForWeb(ShareOpenGraphContent $r0) throws JSONException {
        return OpenGraphJSONUtility.toJSONObject($r0.getAction(), new C06037());
    }

    public static JSONArray removeNamespacesFromOGJsonArray(JSONArray $r0, boolean $z0) throws JSONException {
        JSONArray $r1 = new JSONArray();
        for (int $i0 = 0; $i0 < $r0.length(); $i0++) {
            Object $r2 = $r0.get($i0);
            Object $r3 = $r2;
            if ($r2 instanceof JSONArray) {
                $r3 = removeNamespacesFromOGJsonArray((JSONArray) $r2, $z0);
            } else if ($r2 instanceof JSONObject) {
                $r3 = removeNamespacesFromOGJsonObject((JSONObject) $r2, $z0);
            }
            $r1.put($r3);
        }
        return $r1;
    }

    public static Pair<String, String> getFieldNameAndNamespaceFromFullName(@Signature({"(", "Ljava/lang/String;", ")", "Landroid/util/Pair", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) String $r0) throws  {
        String $r1 = null;
        int $i1 = $r0.indexOf(58);
        if ($i1 != -1 && $r0.length() > $i1 + 1) {
            $r1 = $r0.substring(0, $i1);
            $r0 = $r0.substring($i1 + 1);
        }
        return new Pair($r1, $r0);
    }

    private static Attachment getAttachment(UUID $r0, SharePhoto $r1) throws  {
        Bitmap $r2 = $r1.getBitmap();
        Uri $r3 = $r1.getImageUrl();
        if ($r2 != null) {
            return NativeAppCallAttachmentStore.createAttachment($r0, $r2);
        }
        return $r3 != null ? NativeAppCallAttachmentStore.createAttachment($r0, $r3) : null;
    }

    static void invokeOnCancelCallback(@Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) FacebookCallback<Result> $r0) throws  {
        logShareResult(AnalyticsEvents.PARAMETER_SHARE_OUTCOME_CANCELLED, null);
        if ($r0 != null) {
            $r0.onCancel();
        }
    }

    static void invokeOnSuccessCallback(@Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Ljava/lang/String;", ")V"}) FacebookCallback<Result> $r0, @Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Ljava/lang/String;", ")V"}) String $r1) throws  {
        logShareResult(AnalyticsEvents.PARAMETER_SHARE_OUTCOME_SUCCEEDED, null);
        if ($r0 != null) {
            $r0.onSuccess(new Result($r1));
        }
    }

    static void invokeOnErrorCallback(@Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Lcom/facebook/GraphResponse;", "Ljava/lang/String;", ")V"}) FacebookCallback<Result> $r0, @Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Lcom/facebook/GraphResponse;", "Ljava/lang/String;", ")V"}) GraphResponse $r1, @Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Lcom/facebook/GraphResponse;", "Ljava/lang/String;", ")V"}) String $r2) throws  {
        logShareResult("error", $r2);
        if ($r0 != null) {
            $r0.onError(new FacebookGraphResponseException($r1, $r2));
        }
    }

    static void invokeOnErrorCallback(@Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Ljava/lang/String;", ")V"}) FacebookCallback<Result> $r0, @Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Ljava/lang/String;", ")V"}) String $r1) throws  {
        logShareResult("error", $r1);
        if ($r0 != null) {
            $r0.onError(new FacebookException($r1));
        }
    }

    static void invokeOnErrorCallback(@Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Lcom/facebook/FacebookException;", ")V"}) FacebookCallback<Result> $r0, @Signature({"(", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;", "Lcom/facebook/FacebookException;", ")V"}) FacebookException $r1) throws  {
        logShareResult("error", $r1.getMessage());
        if ($r0 != null) {
            $r0.onError($r1);
        }
    }

    private static void logShareResult(String $r0, String $r1) throws  {
        AppEventsLogger $r4 = AppEventsLogger.newLogger(FacebookSdk.getApplicationContext());
        Bundle $r2 = new Bundle();
        $r2.putString(AnalyticsEvents.PARAMETER_SHARE_OUTCOME, $r0);
        if ($r1 != null) {
            $r2.putString(AnalyticsEvents.PARAMETER_SHARE_ERROR_MESSAGE, $r1);
        }
        $r4.logSdkEvent(AnalyticsEvents.EVENT_SHARE_RESULT, null, $r2);
    }

    public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken $r0, Bitmap $r1, GraphRequest.Callback $r2) throws  {
        Bundle $r3 = new Bundle(1);
        $r3.putParcelable(STAGING_PARAM, $r1);
        return new GraphRequest($r0, MY_STAGING_RESOURCES, $r3, HttpMethod.POST, $r2);
    }

    public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken $r0, File $r1, GraphRequest.Callback $r2) throws FileNotFoundException {
        ParcelableResourceWithMimeType $r5 = new ParcelableResourceWithMimeType(ParcelFileDescriptor.open($r1, 268435456), "image/png");
        Bundle $r3 = new Bundle(1);
        $r3.putParcelable(STAGING_PARAM, $r5);
        return new GraphRequest($r0, MY_STAGING_RESOURCES, $r3, HttpMethod.POST, $r2);
    }

    public static GraphRequest newUploadStagingResourceWithImageRequest(AccessToken $r0, Uri $r1, GraphRequest.Callback $r2) throws FileNotFoundException {
        if (Utility.isFileUri($r1)) {
            return newUploadStagingResourceWithImageRequest($r0, new File($r1.getPath()), $r2);
        } else if (Utility.isContentUri($r1)) {
            ParcelableResourceWithMimeType $r5 = new ParcelableResourceWithMimeType((Parcelable) $r1, "image/png");
            Bundle $r3 = new Bundle(1);
            $r3.putParcelable(STAGING_PARAM, $r5);
            return new GraphRequest($r0, MY_STAGING_RESOURCES, $r3, HttpMethod.POST, $r2);
        } else {
            throw new FacebookException("The image Uri must be either a file:// or content:// Uri");
        }
    }

    @Nullable
    public static ObjectType getMostSpecificObjectType(ObjectType $r1, ObjectType $r0) throws  {
        if ($r1 == $r0) {
            return $r1;
        }
        if ($r1 == ObjectType.UNKNOWN) {
            return $r0;
        }
        return $r0 != ObjectType.UNKNOWN ? null : $r1;
    }
}
