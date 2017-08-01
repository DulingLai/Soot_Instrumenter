package com.facebook.share.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.AppCall;
import com.facebook.internal.BundleJSONConverter;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.FileLruCache.Limits;
import com.facebook.internal.Logger;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient.CompletedListener;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.WorkQueue;
import com.facebook.share.internal.LikeContent.Builder;
import com.facebook.share.widget.LikeView.ObjectType;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LikeActionController {
    public static final String ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR = "com.facebook.sdk.LikeActionController.DID_ERROR";
    public static final String ACTION_LIKE_ACTION_CONTROLLER_DID_RESET = "com.facebook.sdk.LikeActionController.DID_RESET";
    public static final String ACTION_LIKE_ACTION_CONTROLLER_UPDATED = "com.facebook.sdk.LikeActionController.UPDATED";
    public static final String ACTION_OBJECT_ID_KEY = "com.facebook.sdk.LikeActionController.OBJECT_ID";
    private static final int ERROR_CODE_OBJECT_ALREADY_LIKED = 3501;
    public static final String ERROR_INVALID_OBJECT_ID = "Invalid Object Id";
    public static final String ERROR_PUBLISH_ERROR = "Unable to publish the like/unlike action";
    private static final String JSON_BOOL_IS_OBJECT_LIKED_KEY = "is_object_liked";
    private static final String JSON_BUNDLE_FACEBOOK_DIALOG_ANALYTICS_BUNDLE = "facebook_dialog_analytics_bundle";
    private static final String JSON_INT_OBJECT_TYPE_KEY = "object_type";
    private static final String JSON_INT_VERSION_KEY = "com.facebook.share.internal.LikeActionController.version";
    private static final String JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY = "like_count_string_without_like";
    private static final String JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY = "like_count_string_with_like";
    private static final String JSON_STRING_OBJECT_ID_KEY = "object_id";
    private static final String JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY = "social_sentence_without_like";
    private static final String JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY = "social_sentence_with_like";
    private static final String JSON_STRING_UNLIKE_TOKEN_KEY = "unlike_token";
    private static final String LIKE_ACTION_CONTROLLER_STORE = "com.facebook.LikeActionController.CONTROLLER_STORE_KEY";
    private static final String LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY = "OBJECT_SUFFIX";
    private static final String LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY = "PENDING_CONTROLLER_KEY";
    private static final int LIKE_ACTION_CONTROLLER_VERSION = 3;
    private static final String LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY = "like_count_string";
    private static final String LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY = "object_is_liked";
    private static final String LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY = "social_sentence";
    private static final String LIKE_DIALOG_RESPONSE_UNLIKE_TOKEN_KEY = "unlike_token";
    private static final int MAX_CACHE_SIZE = 128;
    private static final int MAX_OBJECT_SUFFIX = 1000;
    private static final String TAG = LikeActionController.class.getSimpleName();
    private static AccessTokenTracker accessTokenTracker;
    private static final ConcurrentHashMap<String, LikeActionController> cache = new ConcurrentHashMap();
    private static FileLruCache controllerDiskCache;
    private static WorkQueue diskIOWorkQueue = new WorkQueue(1);
    private static Handler handler;
    private static boolean isInitialized;
    private static WorkQueue mruCacheWorkQueue = new WorkQueue(1);
    private static String objectIdForPendingController;
    private static volatile int objectSuffix;
    private AppEventsLogger appEventsLogger;
    private Bundle facebookDialogAnalyticsBundle;
    private boolean isObjectLiked;
    private boolean isObjectLikedOnServer;
    private boolean isPendingLikeOrUnlike;
    private String likeCountStringWithLike;
    private String likeCountStringWithoutLike;
    private String objectId;
    private boolean objectIsPage;
    private ObjectType objectType;
    private String socialSentenceWithLike;
    private String socialSentenceWithoutLike;
    private String unlikeToken;
    private String verifiedObjectId;

    public interface CreationCallback {
        void onComplete(LikeActionController likeActionController, FacebookException facebookException) throws ;
    }

    static class C05803 implements Callback {
        C05803() throws  {
        }

        public boolean onActivityResult(int $i0, Intent $r1) throws  {
            return LikeActionController.handleOnActivityResult(RequestCodeOffset.Like.toRequestCode(), $i0, $r1);
        }
    }

    static class C05825 extends AccessTokenTracker {
        C05825() throws  {
        }

        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken $r2) throws  {
            Context $r3 = FacebookSdk.getApplicationContext();
            if ($r2 == null) {
                LikeActionController.objectSuffix = (LikeActionController.objectSuffix + 1) % 1000;
                $r3.getSharedPreferences(LikeActionController.LIKE_ACTION_CONTROLLER_STORE, 0).edit().putInt(LikeActionController.LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY, LikeActionController.objectSuffix).apply();
                LikeActionController.cache.clear();
                LikeActionController.controllerDiskCache.clearCache();
            }
            LikeActionController.broadcastAction(null, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
        }
    }

    private interface RequestCompletionCallback {
        void onComplete() throws ;
    }

    class C05889 implements RequestCompletionCallback {
        C05889() throws  {
        }

        public void onComplete() throws  {
            LikeRequestWrapper $r6;
            switch (LikeActionController.this.objectType) {
                case PAGE:
                    Object $r62 = r14;
                    GetPageLikesRequestWrapper r14 = new GetPageLikesRequestWrapper(LikeActionController.this.verifiedObjectId);
                    break;
                default:
                    $r6 = r10;
                    GetOGObjectLikesRequestWrapper r10 = new GetOGObjectLikesRequestWrapper(LikeActionController.this.verifiedObjectId, LikeActionController.this.objectType);
                    break;
            }
            final GetEngagementRequestWrapper $r1 = r11;
            GetEngagementRequestWrapper r11 = new GetEngagementRequestWrapper(LikeActionController.this.verifiedObjectId, LikeActionController.this.objectType);
            GraphRequestBatch $r2 = r12;
            GraphRequestBatch r12 = new GraphRequestBatch();
            $r6.addToBatch($r2);
            $r1.addToBatch($r2);
            $r2.addCallback(new GraphRequestBatch.Callback() {
                public void onBatchCompleted(GraphRequestBatch batch) throws  {
                    if ($r6.getError() == null && $r1.getError() == null) {
                        LikeActionController.this.updateState($r6.isObjectLiked(), $r1.likeCountStringWithLike, $r1.likeCountStringWithoutLike, $r1.socialSentenceStringWithLike, $r1.socialSentenceStringWithoutLike, $r6.getUnlikeToken());
                        return;
                    }
                    Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Unable to refresh like state for id: '%s'", LikeActionController.this.objectId);
                }
            });
            $r2.executeAsync();
        }
    }

    private interface RequestWrapper {
        void addToBatch(GraphRequestBatch graphRequestBatch) throws ;

        FacebookRequestError getError() throws ;
    }

    private abstract class AbstractRequestWrapper implements RequestWrapper {
        protected FacebookRequestError error;
        protected String objectId;
        protected ObjectType objectType;
        private GraphRequest request;

        class C05891 implements GraphRequest.Callback {
            C05891() throws  {
            }

            public void onCompleted(GraphResponse $r1) throws  {
                AbstractRequestWrapper.this.error = $r1.getError();
                if (AbstractRequestWrapper.this.error != null) {
                    AbstractRequestWrapper.this.processError(AbstractRequestWrapper.this.error);
                } else {
                    AbstractRequestWrapper.this.processSuccess($r1);
                }
            }
        }

        protected abstract void processSuccess(GraphResponse graphResponse) throws ;

        protected AbstractRequestWrapper(String $r2, ObjectType $r3) throws  {
            this.objectId = $r2;
            this.objectType = $r3;
        }

        public void addToBatch(GraphRequestBatch $r1) throws  {
            $r1.add(this.request);
        }

        public FacebookRequestError getError() throws  {
            return this.error;
        }

        protected void setRequest(GraphRequest $r1) throws  {
            this.request = $r1;
            $r1.setVersion(ServerProtocol.GRAPH_API_VERSION);
            $r1.setCallback(new C05891());
        }

        protected void processError(FacebookRequestError $r1) throws  {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error running request for object '%s' with type '%s' : %s", this.objectId, this.objectType, $r1);
        }
    }

    private static class CreateLikeActionControllerWorkItem implements Runnable {
        private CreationCallback callback;
        private String objectId;
        private ObjectType objectType;

        CreateLikeActionControllerWorkItem(String $r1, ObjectType $r2, CreationCallback $r3) throws  {
            this.objectId = $r1;
            this.objectType = $r2;
            this.callback = $r3;
        }

        public void run() throws  {
            LikeActionController.createControllerForObjectIdAndType(this.objectId, this.objectType, this.callback);
        }
    }

    private class GetEngagementRequestWrapper extends AbstractRequestWrapper {
        String likeCountStringWithLike = LikeActionController.this.likeCountStringWithLike;
        String likeCountStringWithoutLike = LikeActionController.this.likeCountStringWithoutLike;
        String socialSentenceStringWithLike = LikeActionController.this.socialSentenceWithLike;
        String socialSentenceStringWithoutLike = LikeActionController.this.socialSentenceWithoutLike;

        GetEngagementRequestWrapper(String $r2, ObjectType $r3) throws  {
            super($r2, $r3);
            Bundle $r4 = new Bundle();
            $r4.putString(GraphRequest.FIELDS_PARAM, "engagement.fields(count_string_with_like,count_string_without_like,social_sentence_with_like,social_sentence_without_like)");
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), $r2, $r4, HttpMethod.GET));
        }

        protected void processSuccess(GraphResponse $r1) throws  {
            JSONObject $r2 = Utility.tryGetJSONObjectFromResponse($r1.getJSONObject(), "engagement");
            if ($r2 != null) {
                this.likeCountStringWithLike = $r2.optString("count_string_with_like", this.likeCountStringWithLike);
                this.likeCountStringWithoutLike = $r2.optString("count_string_without_like", this.likeCountStringWithoutLike);
                this.socialSentenceStringWithLike = $r2.optString(LikeActionController.JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, this.socialSentenceStringWithLike);
                this.socialSentenceStringWithoutLike = $r2.optString(LikeActionController.JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, this.socialSentenceStringWithoutLike);
            }
        }

        protected void processError(FacebookRequestError $r1) throws  {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error fetching engagement for object '%s' with type '%s' : %s", this.objectId, this.objectType, $r1);
            LikeActionController.this.logAppEventForError("get_engagement", $r1);
        }
    }

    private class GetOGObjectIdRequestWrapper extends AbstractRequestWrapper {
        String verifiedObjectId;

        GetOGObjectIdRequestWrapper(String $r2, ObjectType $r3) throws  {
            super($r2, $r3);
            Bundle $r4 = new Bundle();
            $r4.putString(GraphRequest.FIELDS_PARAM, "og_object.fields(id)");
            $r4.putString("ids", $r2);
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "", $r4, HttpMethod.GET));
        }

        protected void processError(FacebookRequestError $r1) throws  {
            if ($r1.getErrorMessage().contains("og_object")) {
                this.error = null;
                return;
            }
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error getting the FB id for object '%s' with type '%s' : %s", this.objectId, this.objectType, $r1);
        }

        protected void processSuccess(GraphResponse $r1) throws  {
            JSONObject $r3 = Utility.tryGetJSONObjectFromResponse($r1.getJSONObject(), this.objectId);
            if ($r3 != null) {
                $r3 = $r3.optJSONObject("og_object");
                if ($r3 != null) {
                    this.verifiedObjectId = $r3.optString("id");
                }
            }
        }
    }

    private interface LikeRequestWrapper extends RequestWrapper {
        String getUnlikeToken() throws ;

        boolean isObjectLiked() throws ;
    }

    private class GetOGObjectLikesRequestWrapper extends AbstractRequestWrapper implements LikeRequestWrapper {
        private final String objectId;
        private boolean objectIsLiked = LikeActionController.this.isObjectLiked;
        private final ObjectType objectType;
        private String unlikeToken;

        GetOGObjectLikesRequestWrapper(String $r2, ObjectType $r3) throws  {
            super($r2, $r3);
            this.objectId = $r2;
            this.objectType = $r3;
            Bundle $r4 = new Bundle();
            $r4.putString(GraphRequest.FIELDS_PARAM, "id,application");
            $r4.putString("object", this.objectId);
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "me/og.likes", $r4, HttpMethod.GET));
        }

        protected void processSuccess(GraphResponse $r1) throws  {
            JSONArray $r3 = Utility.tryGetJSONArrayFromResponse($r1.getJSONObject(), "data");
            if ($r3 != null) {
                for (int $i0 = 0; $i0 < $r3.length(); $i0++) {
                    JSONObject $r2 = $r3.optJSONObject($i0);
                    if ($r2 != null) {
                        this.objectIsLiked = true;
                        JSONObject $r4 = $r2.optJSONObject("application");
                        AccessToken $r5 = AccessToken.getCurrentAccessToken();
                        if (!($r4 == null || $r5 == null || !Utility.areObjectsEqual($r5.getApplicationId(), $r4.optString("id")))) {
                            this.unlikeToken = $r2.optString("id");
                        }
                    }
                }
            }
        }

        protected void processError(FacebookRequestError $r1) throws  {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error fetching like status for object '%s' with type '%s' : %s", this.objectId, this.objectType, $r1);
            LikeActionController.this.logAppEventForError("get_og_object_like", $r1);
        }

        public boolean isObjectLiked() throws  {
            return this.objectIsLiked;
        }

        public String getUnlikeToken() throws  {
            return this.unlikeToken;
        }
    }

    private class GetPageIdRequestWrapper extends AbstractRequestWrapper {
        boolean objectIsPage;
        String verifiedObjectId;

        GetPageIdRequestWrapper(String $r2, ObjectType $r3) throws  {
            super($r2, $r3);
            Bundle $r4 = new Bundle();
            $r4.putString(GraphRequest.FIELDS_PARAM, "id");
            $r4.putString("ids", $r2);
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "", $r4, HttpMethod.GET));
        }

        protected void processSuccess(GraphResponse $r1) throws  {
            JSONObject $r3 = Utility.tryGetJSONObjectFromResponse($r1.getJSONObject(), this.objectId);
            if ($r3 != null) {
                this.verifiedObjectId = $r3.optString("id");
                this.objectIsPage = !Utility.isNullOrEmpty(this.verifiedObjectId);
            }
        }

        protected void processError(FacebookRequestError $r1) throws  {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error getting the FB id for object '%s' with type '%s' : %s", this.objectId, this.objectType, $r1);
        }
    }

    private class GetPageLikesRequestWrapper extends AbstractRequestWrapper implements LikeRequestWrapper {
        private boolean objectIsLiked = LikeActionController.this.isObjectLiked;
        private String pageId;

        public String getUnlikeToken() throws  {
            return null;
        }

        GetPageLikesRequestWrapper(String $r2) throws  {
            super($r2, ObjectType.PAGE);
            this.pageId = $r2;
            Bundle $r3 = new Bundle();
            $r3.putString(GraphRequest.FIELDS_PARAM, "id");
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "me/likes/" + $r2, $r3, HttpMethod.GET));
        }

        protected void processSuccess(GraphResponse $r1) throws  {
            JSONArray $r3 = Utility.tryGetJSONArrayFromResponse($r1.getJSONObject(), "data");
            if ($r3 != null && $r3.length() > 0) {
                this.objectIsLiked = true;
            }
        }

        protected void processError(FacebookRequestError $r1) throws  {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error fetching like status for page id '%s': %s", this.pageId, $r1);
            LikeActionController.this.logAppEventForError("get_page_like", $r1);
        }

        public boolean isObjectLiked() throws  {
            return this.objectIsLiked;
        }
    }

    private static class MRUCacheWorkItem implements Runnable {
        private static ArrayList<String> mruCachedItems = new ArrayList();
        private String cacheItem;
        private boolean shouldTrim;

        MRUCacheWorkItem(String $r1, boolean $z0) throws  {
            this.cacheItem = $r1;
            this.shouldTrim = $z0;
        }

        public void run() throws  {
            if (this.cacheItem != null) {
                mruCachedItems.remove(this.cacheItem);
                mruCachedItems.add(0, this.cacheItem);
            }
            if (this.shouldTrim && mruCachedItems.size() >= 128) {
                while (64 < mruCachedItems.size()) {
                    LikeActionController.cache.remove((String) mruCachedItems.remove(mruCachedItems.size() - 1));
                }
            }
        }
    }

    private class PublishLikeRequestWrapper extends AbstractRequestWrapper {
        String unlikeToken;

        PublishLikeRequestWrapper(String $r2, ObjectType $r3) throws  {
            super($r2, $r3);
            Bundle $r4 = new Bundle();
            $r4.putString("object", $r2);
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "me/og.likes", $r4, HttpMethod.POST));
        }

        protected void processSuccess(GraphResponse $r1) throws  {
            this.unlikeToken = Utility.safeGetStringFromResponse($r1.getJSONObject(), "id");
        }

        protected void processError(FacebookRequestError $r1) throws  {
            if ($r1.getErrorCode() == 3501) {
                this.error = null;
                return;
            }
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error liking object '%s' with type '%s' : %s", this.objectId, this.objectType, $r1);
            LikeActionController.this.logAppEventForError("publish_like", $r1);
        }
    }

    private class PublishUnlikeRequestWrapper extends AbstractRequestWrapper {
        private String unlikeToken;

        PublishUnlikeRequestWrapper(String $r2) throws  {
            super(null, null);
            this.unlikeToken = $r2;
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), $r2, null, HttpMethod.DELETE));
        }

        protected void processSuccess(GraphResponse response) throws  {
        }

        protected void processError(FacebookRequestError $r1) throws  {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error unliking object with unlike token '%s' : %s", this.unlikeToken, $r1);
            LikeActionController.this.logAppEventForError("publish_unlike", $r1);
        }
    }

    private static class SerializeToDiskWorkItem implements Runnable {
        private String cacheKey;
        private String controllerJson;

        SerializeToDiskWorkItem(String $r1, String $r2) throws  {
            this.cacheKey = $r1;
            this.controllerJson = $r2;
        }

        public void run() throws  {
            LikeActionController.serializeToDiskSynchronously(this.cacheKey, this.controllerJson);
        }
    }

    private static com.facebook.share.internal.LikeActionController deserializeFromDiskSynchronously(java.lang.String r9) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x001d in list []
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
        r0 = 0;
        r1 = 0;
        r9 = getCacheKeyForObjectId(r9);	 Catch:{ IOException -> 0x0023, Throwable -> 0x0032 }
        r2 = controllerDiskCache;	 Catch:{ IOException -> 0x0023, Throwable -> 0x0032 }
        r3 = r2.get(r9);	 Catch:{ IOException -> 0x0023, Throwable -> 0x0032 }
        r1 = r3;
        if (r3 == 0) goto L_0x001d;	 Catch:{ IOException -> 0x0023, Throwable -> 0x0032 }
    L_0x000f:
        r9 = com.facebook.internal.Utility.readStreamToString(r3);	 Catch:{ IOException -> 0x0023, Throwable -> 0x0032 }
        r4 = com.facebook.internal.Utility.isNullOrEmpty(r9);	 Catch:{ IOException -> 0x0023, Throwable -> 0x0032 }
        if (r4 != 0) goto L_0x001d;	 Catch:{ IOException -> 0x0023, Throwable -> 0x0032 }
    L_0x0019:
        r0 = deserializeFromJson(r9);	 Catch:{ IOException -> 0x0023, Throwable -> 0x0032 }
    L_0x001d:
        if (r3 == 0) goto L_0x0039;
    L_0x001f:
        com.facebook.internal.Utility.closeQuietly(r3);
        return r0;
    L_0x0023:
        r5 = move-exception;
        r9 = TAG;	 Catch:{ IOException -> 0x0023, Throwable -> 0x0032 }
        r6 = "Unable to deserialize controller from disk";	 Catch:{ IOException -> 0x0023, Throwable -> 0x0032 }
        android.util.Log.e(r9, r6, r5);	 Catch:{ IOException -> 0x0023, Throwable -> 0x0032 }
        if (r1 == 0) goto L_0x003a;
    L_0x002d:
        com.facebook.internal.Utility.closeQuietly(r1);
        r7 = 0;
        return r7;
    L_0x0032:
        r8 = move-exception;
        if (r1 == 0) goto L_0x0038;
    L_0x0035:
        com.facebook.internal.Utility.closeQuietly(r1);
    L_0x0038:
        throw r8;
    L_0x0039:
        return r0;
    L_0x003a:
        r7 = 0;
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.share.internal.LikeActionController.deserializeFromDiskSynchronously(java.lang.String):com.facebook.share.internal.LikeActionController");
    }

    private static void serializeToDiskSynchronously(java.lang.String r7, java.lang.String r8) throws  {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = 0;
        r1 = controllerDiskCache;
        r2 = r1.openPutStream(r7);	 Catch:{ IOException -> 0x0015, Throwable -> 0x0023 }
        r0 = r2;	 Catch:{ IOException -> 0x0015, Throwable -> 0x0023 }
        r3 = r8.getBytes();	 Catch:{ IOException -> 0x0015, Throwable -> 0x0023 }
        r2.write(r3);	 Catch:{ IOException -> 0x0015, Throwable -> 0x0023 }
        if (r2 == 0) goto L_0x002a;
    L_0x0011:
        com.facebook.internal.Utility.closeQuietly(r2);
        return;
    L_0x0015:
        r4 = move-exception;
        r7 = TAG;	 Catch:{ IOException -> 0x0015, Throwable -> 0x0023 }
        r5 = "Unable to serialize controller to disk";	 Catch:{ IOException -> 0x0015, Throwable -> 0x0023 }
        android.util.Log.e(r7, r5, r4);	 Catch:{ IOException -> 0x0015, Throwable -> 0x0023 }
        if (r0 == 0) goto L_0x002b;
    L_0x001f:
        com.facebook.internal.Utility.closeQuietly(r0);
        return;
    L_0x0023:
        r6 = move-exception;
        if (r0 == 0) goto L_0x0029;
    L_0x0026:
        com.facebook.internal.Utility.closeQuietly(r0);
    L_0x0029:
        throw r6;
    L_0x002a:
        return;
    L_0x002b:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.share.internal.LikeActionController.serializeToDiskSynchronously(java.lang.String, java.lang.String):void");
    }

    public static boolean handleOnActivityResult(final int $i0, final int $i1, final Intent $r0) throws  {
        if (Utility.isNullOrEmpty(objectIdForPendingController)) {
            objectIdForPendingController = FacebookSdk.getApplicationContext().getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).getString(LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY, null);
        }
        if (Utility.isNullOrEmpty(objectIdForPendingController)) {
            return false;
        }
        getControllerForObjectId(objectIdForPendingController, ObjectType.UNKNOWN, new CreationCallback() {
            public void onComplete(LikeActionController $r1, FacebookException $r2) throws  {
                if ($r2 == null) {
                    $r1.onActivityResult($i0, $i1, $r0);
                } else {
                    Utility.logd(LikeActionController.TAG, (Exception) $r2);
                }
            }
        });
        return true;
    }

    public static void getControllerForObjectId(String $r0, ObjectType $r1, CreationCallback $r2) throws  {
        if (!isInitialized) {
            performFirstInitialize();
        }
        LikeActionController $r4 = getControllerFromInMemoryCache($r0);
        if ($r4 != null) {
            verifyControllerAndInvokeCallback($r4, $r1, $r2);
        } else {
            diskIOWorkQueue.addActiveWorkItem(new CreateLikeActionControllerWorkItem($r0, $r1, $r2));
        }
    }

    private static void verifyControllerAndInvokeCallback(LikeActionController $r2, ObjectType $r0, CreationCallback $r1) throws  {
        ObjectType $r3 = ShareInternalUtility.getMostSpecificObjectType($r0, $r2.objectType);
        FacebookException $r4 = null;
        if ($r3 == null) {
            $r4 = new FacebookException("Object with id:\"%s\" is already marked as type:\"%s\". Cannot change the type to:\"%s\"", $r2.objectId, $r2.objectType.toString(), $r0.toString());
            $r2 = null;
        } else {
            $r2.objectType = $r3;
        }
        invokeCallbackWithController($r1, $r2, $r4);
    }

    private static void createControllerForObjectIdAndType(String $r0, ObjectType $r1, CreationCallback $r2) throws  {
        LikeActionController $r4 = getControllerFromInMemoryCache($r0);
        if ($r4 != null) {
            verifyControllerAndInvokeCallback($r4, $r1, $r2);
            return;
        }
        $r4 = deserializeFromDiskSynchronously($r0);
        LikeActionController $r5 = $r4;
        if ($r4 == null) {
            $r5 = new LikeActionController($r0, $r1);
            serializeToDiskAsync($r5);
        }
        putControllerInMemoryCache($r0, $r5);
        handler.post(new Runnable() {
            public void run() throws  {
                $r5.refreshStatusAsync();
            }
        });
        invokeCallbackWithController($r2, $r5, null);
    }

    private static synchronized void performFirstInitialize() throws  {
        synchronized (LikeActionController.class) {
            try {
                if (!isInitialized) {
                    handler = new Handler(Looper.getMainLooper());
                    objectSuffix = FacebookSdk.getApplicationContext().getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).getInt(LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY, 1);
                    controllerDiskCache = new FileLruCache(TAG, new Limits());
                    registerAccessTokenTracker();
                    CallbackManagerImpl.registerStaticCallback(RequestCodeOffset.Like.toRequestCode(), new C05803());
                    isInitialized = true;
                }
            } catch (Throwable th) {
                Class cls = LikeActionController.class;
            }
        }
    }

    private static void invokeCallbackWithController(final CreationCallback $r0, final LikeActionController $r1, final FacebookException $r2) throws  {
        if ($r0 != null) {
            handler.post(new Runnable() {
                public void run() throws  {
                    $r0.onComplete($r1, $r2);
                }
            });
        }
    }

    private static void registerAccessTokenTracker() throws  {
        accessTokenTracker = new C05825();
    }

    private static void putControllerInMemoryCache(String $r0, LikeActionController $r1) throws  {
        $r0 = getCacheKeyForObjectId($r0);
        mruCacheWorkQueue.addActiveWorkItem(new MRUCacheWorkItem($r0, true));
        cache.put($r0, $r1);
    }

    private static LikeActionController getControllerFromInMemoryCache(String $r0) throws  {
        $r0 = getCacheKeyForObjectId($r0);
        LikeActionController $r4 = (LikeActionController) cache.get($r0);
        if ($r4 == null) {
            return $r4;
        }
        mruCacheWorkQueue.addActiveWorkItem(new MRUCacheWorkItem($r0, false));
        return $r4;
    }

    private static void serializeToDiskAsync(LikeActionController $r0) throws  {
        String $r2 = serializeToJson($r0);
        String $r3 = getCacheKeyForObjectId($r0.objectId);
        if (!Utility.isNullOrEmpty($r2) && !Utility.isNullOrEmpty($r3)) {
            diskIOWorkQueue.addActiveWorkItem(new SerializeToDiskWorkItem($r3, $r2));
        }
    }

    private static LikeActionController deserializeFromJson(String $r0) throws  {
        try {
            JSONObject $r1 = new JSONObject($r0);
            if ($r1.optInt(JSON_INT_VERSION_KEY, -1) != 3) {
                return null;
            }
            LikeActionController $r3 = new LikeActionController($r1.getString("object_id"), ObjectType.fromInt($r1.optInt("object_type", ObjectType.UNKNOWN.getValue())));
            $r3.likeCountStringWithLike = $r1.optString(JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY, null);
            $r3.likeCountStringWithoutLike = $r1.optString(JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY, null);
            $r3.socialSentenceWithLike = $r1.optString(JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, null);
            $r3.socialSentenceWithoutLike = $r1.optString(JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, null);
            $r3.isObjectLiked = $r1.optBoolean(JSON_BOOL_IS_OBJECT_LIKED_KEY);
            $r3.unlikeToken = $r1.optString("unlike_token", null);
            $r1 = $r1.optJSONObject(JSON_BUNDLE_FACEBOOK_DIALOG_ANALYTICS_BUNDLE);
            if ($r1 == null) {
                return $r3;
            }
            $r3.facebookDialogAnalyticsBundle = BundleJSONConverter.convertToBundle($r1);
            return $r3;
        } catch (JSONException $r2) {
            Log.e(TAG, "Unable to deserialize controller from JSON", $r2);
            return null;
        }
    }

    private static String serializeToJson(LikeActionController $r0) throws  {
        JSONObject $r1 = new JSONObject();
        try {
            $r1.put(JSON_INT_VERSION_KEY, 3);
            $r1.put("object_id", $r0.objectId);
            $r1.put("object_type", $r0.objectType.getValue());
            $r1.put(JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY, $r0.likeCountStringWithLike);
            $r1.put(JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY, $r0.likeCountStringWithoutLike);
            $r1.put(JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, $r0.socialSentenceWithLike);
            $r1.put(JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, $r0.socialSentenceWithoutLike);
            $r1.put(JSON_BOOL_IS_OBJECT_LIKED_KEY, $r0.isObjectLiked);
            $r1.put("unlike_token", $r0.unlikeToken);
            if ($r0.facebookDialogAnalyticsBundle != null) {
                JSONObject $r6 = BundleJSONConverter.convertToJSON($r0.facebookDialogAnalyticsBundle);
                if ($r6 != null) {
                    $r1.put(JSON_BUNDLE_FACEBOOK_DIALOG_ANALYTICS_BUNDLE, $r6);
                }
            }
            return $r1.toString();
        } catch (JSONException $r2) {
            Log.e(TAG, "Unable to serialize controller to JSON", $r2);
            return null;
        }
    }

    private static String getCacheKeyForObjectId(String $r0) throws  {
        String $r1 = null;
        AccessToken $r2 = AccessToken.getCurrentAccessToken();
        if ($r2 != null) {
            $r1 = $r2.getToken();
        }
        if ($r1 != null) {
            $r1 = Utility.md5hash($r1);
        }
        return String.format(Locale.ROOT, "%s|%s|com.fb.sdk.like|%d", new Object[]{$r0, Utility.coerceValueIfNullOrEmpty($r1, ""), Integer.valueOf(objectSuffix)});
    }

    private static void broadcastAction(LikeActionController $r0, String $r1) throws  {
        broadcastAction($r0, $r1, null);
    }

    private static void broadcastAction(LikeActionController $r0, String $r1, Bundle $r3) throws  {
        Intent $r2 = new Intent($r1);
        if ($r0 != null) {
            if ($r3 == null) {
                $r3 = new Bundle();
            }
            $r3.putString(ACTION_OBJECT_ID_KEY, $r0.getObjectId());
        }
        if ($r3 != null) {
            $r2.putExtras($r3);
        }
        LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()).sendBroadcast($r2);
    }

    private LikeActionController(String $r1, ObjectType $r2) throws  {
        this.objectId = $r1;
        this.objectType = $r2;
    }

    public String getObjectId() throws  {
        return this.objectId;
    }

    public String getLikeCountString() throws  {
        return this.isObjectLiked ? this.likeCountStringWithLike : this.likeCountStringWithoutLike;
    }

    public String getSocialSentence() throws  {
        return this.isObjectLiked ? this.socialSentenceWithLike : this.socialSentenceWithoutLike;
    }

    public boolean isObjectLiked() throws  {
        return this.isObjectLiked;
    }

    public boolean shouldEnableView() throws  {
        if (LikeDialog.canShowNativeDialog()) {
            return true;
        }
        if (LikeDialog.canShowWebFallback()) {
            return true;
        }
        if (this.objectIsPage || this.objectType == ObjectType.PAGE) {
            return false;
        }
        AccessToken $r3 = AccessToken.getCurrentAccessToken();
        return ($r3 == null || $r3.getPermissions() == null || !$r3.getPermissions().contains("publish_actions")) ? false : true;
    }

    public void toggleLike(Activity $r1, Fragment $r2, Bundle $r3) throws  {
        boolean $z0 = true;
        boolean $z1 = !this.isObjectLiked;
        if (canUseOGPublish()) {
            updateLikeState($z1);
            if (this.isPendingLikeOrUnlike) {
                getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_UNDO_QUICKLY, null, $r3);
                return;
            } else if (!publishLikeOrUnlikeAsync($z1, $r3)) {
                if ($z1) {
                    $z0 = false;
                }
                updateLikeState($z0);
                presentLikeDialog($r1, $r2, $r3);
                return;
            } else {
                return;
            }
        }
        presentLikeDialog($r1, $r2, $r3);
    }

    private AppEventsLogger getAppEventsLogger() throws  {
        if (this.appEventsLogger == null) {
            this.appEventsLogger = AppEventsLogger.newLogger(FacebookSdk.getApplicationContext());
        }
        return this.appEventsLogger;
    }

    private boolean publishLikeOrUnlikeAsync(boolean $z0, Bundle $r1) throws  {
        if (!canUseOGPublish()) {
            return false;
        }
        if ($z0) {
            publishLikeAsync($r1);
            return true;
        } else if (Utility.isNullOrEmpty(this.unlikeToken)) {
            return false;
        } else {
            publishUnlikeAsync($r1);
            return true;
        }
    }

    private void publishDidError(boolean $z0) throws  {
        updateLikeState($z0);
        Bundle $r1 = new Bundle();
        $r1.putString(NativeProtocol.STATUS_ERROR_DESCRIPTION, ERROR_PUBLISH_ERROR);
        broadcastAction(this, ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, $r1);
    }

    private void updateLikeState(boolean $z0) throws  {
        updateState($z0, this.likeCountStringWithLike, this.likeCountStringWithoutLike, this.socialSentenceWithLike, this.socialSentenceWithoutLike, this.unlikeToken);
    }

    private void updateState(boolean $z0, String $r1, String $r2, String $r3, String $r4, String $r5) throws  {
        boolean $z1;
        $r1 = Utility.coerceValueIfNullOrEmpty($r1, null);
        $r2 = Utility.coerceValueIfNullOrEmpty($r2, null);
        $r3 = Utility.coerceValueIfNullOrEmpty($r3, null);
        $r4 = Utility.coerceValueIfNullOrEmpty($r4, null);
        $r5 = Utility.coerceValueIfNullOrEmpty($r5, null);
        if ($z0 == this.isObjectLiked && Utility.areObjectsEqual($r1, this.likeCountStringWithLike) && Utility.areObjectsEqual($r2, this.likeCountStringWithoutLike) && Utility.areObjectsEqual($r3, this.socialSentenceWithLike) && Utility.areObjectsEqual($r4, this.socialSentenceWithoutLike) && Utility.areObjectsEqual($r5, this.unlikeToken)) {
            $z1 = false;
        } else {
            $z1 = true;
        }
        if ($z1) {
            this.isObjectLiked = $z0;
            this.likeCountStringWithLike = $r1;
            this.likeCountStringWithoutLike = $r2;
            this.socialSentenceWithLike = $r3;
            this.socialSentenceWithoutLike = $r4;
            this.unlikeToken = $r5;
            serializeToDiskAsync(this);
            broadcastAction(this, ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
        }
    }

    private void presentLikeDialog(Activity $r1, Fragment $r2, Bundle $r3) throws  {
        String $r4 = null;
        if (LikeDialog.canShowNativeDialog()) {
            $r4 = AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_DIALOG;
        } else if (LikeDialog.canShowWebFallback()) {
            $r4 = AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_FALLBACK;
        } else {
            logAppEventForError("present_dialog", $r3);
            Utility.logd(TAG, "Cannot show the Like Dialog on this device.");
            broadcastAction(null, ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
        }
        if ($r4 != null) {
            if (this.objectType != null) {
                $r4 = this.objectType.toString();
            } else {
                $r4 = ObjectType.UNKNOWN.toString();
            }
            LikeContent $r8 = new Builder().setObjectId(this.objectId).setObjectType($r4).build();
            if ($r2 != null) {
                new LikeDialog($r2).show($r8);
            } else {
                new LikeDialog($r1).show($r8);
            }
            saveState($r3);
            getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_DIALOG, null, $r3);
        }
    }

    private void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        ShareInternalUtility.handleActivityResult($i0, $i1, $r1, getResultProcessor(this.facebookDialogAnalyticsBundle));
        clearState();
    }

    private ResultProcessor getResultProcessor(final Bundle $r1) throws  {
        return new ResultProcessor(null) {
            public void onSuccess(AppCall $r1, Bundle $r2) throws  {
                if ($r2 != null) {
                    if ($r2.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY)) {
                        String $r8;
                        Bundle bundle;
                        boolean $z0 = $r2.getBoolean(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY);
                        String $r4 = LikeActionController.this.likeCountStringWithLike;
                        String $r5 = LikeActionController.this.likeCountStringWithoutLike;
                        if ($r2.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY)) {
                            $r5 = $r2.getString(LikeActionController.LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY);
                            $r4 = $r5;
                        }
                        String $r6 = LikeActionController.this.socialSentenceWithLike;
                        String $r7 = LikeActionController.this.socialSentenceWithoutLike;
                        if ($r2.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY)) {
                            $r7 = $r2.getString(LikeActionController.LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY);
                            $r6 = $r7;
                        }
                        if ($r2.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY)) {
                            $r8 = $r2.getString("unlike_token");
                        } else {
                            $r8 = LikeActionController.this.unlikeToken;
                        }
                        if ($r1 == null) {
                            bundle = new Bundle();
                        } else {
                            $r2 = $r1;
                        }
                        bundle = $r2;
                        bundle.putString(AnalyticsEvents.PARAMETER_CALL_ID, $r1.getCallId().toString());
                        LikeActionController.this.getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DIALOG_DID_SUCCEED, null, $r2);
                        LikeActionController.this.updateState($z0, $r4, $r5, $r6, $r7, $r8);
                    }
                }
            }

            public void onError(AppCall $r1, FacebookException $r2) throws  {
                Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Like Dialog failed with error : %s", $r2);
                Bundle $r6 = $r1 == null ? new Bundle() : $r1;
                $r6.putString(AnalyticsEvents.PARAMETER_CALL_ID, $r1.getCallId().toString());
                LikeActionController.this.logAppEventForError("present_dialog", $r6);
                LikeActionController.broadcastAction(LikeActionController.this, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, NativeProtocol.createBundleForException($r2));
            }

            public void onCancel(AppCall $r1) throws  {
                onError($r1, new FacebookOperationCanceledException());
            }
        };
    }

    private void saveState(Bundle $r1) throws  {
        storeObjectIdForPendingController(this.objectId);
        this.facebookDialogAnalyticsBundle = $r1;
        serializeToDiskAsync(this);
    }

    private void clearState() throws  {
        this.facebookDialogAnalyticsBundle = null;
        storeObjectIdForPendingController(null);
    }

    private static void storeObjectIdForPendingController(String $r0) throws  {
        objectIdForPendingController = $r0;
        FacebookSdk.getApplicationContext().getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).edit().putString(LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY, objectIdForPendingController).apply();
    }

    private boolean canUseOGPublish() throws  {
        AccessToken $r1 = AccessToken.getCurrentAccessToken();
        return (this.objectIsPage || this.verifiedObjectId == null || $r1 == null || $r1.getPermissions() == null || !$r1.getPermissions().contains("publish_actions")) ? false : true;
    }

    private void publishLikeAsync(final Bundle $r1) throws  {
        this.isPendingLikeOrUnlike = true;
        fetchVerifiedObjectId(new RequestCompletionCallback() {
            public void onComplete() throws  {
                if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                    Bundle $r1 = new Bundle();
                    $r1.putString(NativeProtocol.STATUS_ERROR_DESCRIPTION, LikeActionController.ERROR_INVALID_OBJECT_ID);
                    LikeActionController.broadcastAction(LikeActionController.this, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, $r1);
                    return;
                }
                GraphRequestBatch $r3 = new GraphRequestBatch();
                final PublishLikeRequestWrapper $r2 = new PublishLikeRequestWrapper(LikeActionController.this.verifiedObjectId, LikeActionController.this.objectType);
                $r2.addToBatch($r3);
                $r3.addCallback(new GraphRequestBatch.Callback() {
                    public void onBatchCompleted(GraphRequestBatch batch) throws  {
                        LikeActionController.this.isPendingLikeOrUnlike = false;
                        if ($r2.getError() != null) {
                            LikeActionController.this.publishDidError(false);
                            return;
                        }
                        LikeActionController.this.unlikeToken = Utility.coerceValueIfNullOrEmpty($r2.unlikeToken, null);
                        LikeActionController.this.isObjectLikedOnServer = true;
                        LikeActionController.this.getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_LIKE, null, $r1);
                        LikeActionController.this.publishAgainIfNeeded($r1);
                    }
                });
                $r3.executeAsync();
            }
        });
    }

    private void publishUnlikeAsync(final Bundle $r1) throws  {
        this.isPendingLikeOrUnlike = true;
        GraphRequestBatch $r2 = new GraphRequestBatch();
        final PublishUnlikeRequestWrapper $r3 = new PublishUnlikeRequestWrapper(this.unlikeToken);
        $r3.addToBatch($r2);
        $r2.addCallback(new GraphRequestBatch.Callback() {
            public void onBatchCompleted(GraphRequestBatch batch) throws  {
                LikeActionController.this.isPendingLikeOrUnlike = false;
                if ($r3.getError() != null) {
                    LikeActionController.this.publishDidError(true);
                    return;
                }
                LikeActionController.this.unlikeToken = null;
                LikeActionController.this.isObjectLikedOnServer = false;
                LikeActionController.this.getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_UNLIKE, null, $r1);
                LikeActionController.this.publishAgainIfNeeded($r1);
            }
        });
        $r2.executeAsync();
    }

    private void refreshStatusAsync() throws  {
        if (AccessToken.getCurrentAccessToken() == null) {
            refreshStatusViaService();
        } else {
            fetchVerifiedObjectId(new C05889());
        }
    }

    private void refreshStatusViaService() throws  {
        LikeStatusClient $r2 = new LikeStatusClient(FacebookSdk.getApplicationContext(), FacebookSdk.getApplicationId(), this.objectId);
        if ($r2.start()) {
            $r2.setCompletedListener(new CompletedListener() {
                public void completed(Bundle $r1) throws  {
                    if ($r1 != null) {
                        if ($r1.containsKey(ShareConstants.EXTRA_OBJECT_IS_LIKED)) {
                            String $r2;
                            String $r3;
                            String $r4;
                            String $r5;
                            String $r6;
                            boolean $z0 = $r1.getBoolean(ShareConstants.EXTRA_OBJECT_IS_LIKED);
                            if ($r1.containsKey(ShareConstants.EXTRA_LIKE_COUNT_STRING_WITH_LIKE)) {
                                $r2 = $r1.getString(ShareConstants.EXTRA_LIKE_COUNT_STRING_WITH_LIKE);
                            } else {
                                $r2 = LikeActionController.this.likeCountStringWithLike;
                            }
                            if ($r1.containsKey(ShareConstants.EXTRA_LIKE_COUNT_STRING_WITHOUT_LIKE)) {
                                $r3 = $r1.getString(ShareConstants.EXTRA_LIKE_COUNT_STRING_WITHOUT_LIKE);
                            } else {
                                $r3 = LikeActionController.this.likeCountStringWithoutLike;
                            }
                            if ($r1.containsKey(ShareConstants.EXTRA_SOCIAL_SENTENCE_WITH_LIKE)) {
                                $r4 = $r1.getString(ShareConstants.EXTRA_SOCIAL_SENTENCE_WITH_LIKE);
                            } else {
                                $r4 = LikeActionController.this.socialSentenceWithLike;
                            }
                            if ($r1.containsKey(ShareConstants.EXTRA_SOCIAL_SENTENCE_WITHOUT_LIKE)) {
                                $r5 = $r1.getString(ShareConstants.EXTRA_SOCIAL_SENTENCE_WITHOUT_LIKE);
                            } else {
                                $r5 = LikeActionController.this.socialSentenceWithoutLike;
                            }
                            if ($r1.containsKey(ShareConstants.EXTRA_UNLIKE_TOKEN)) {
                                $r6 = $r1.getString(ShareConstants.EXTRA_UNLIKE_TOKEN);
                            } else {
                                $r6 = LikeActionController.this.unlikeToken;
                            }
                            LikeActionController.this.updateState($z0, $r2, $r3, $r4, $r5, $r6);
                        }
                    }
                }
            });
        }
    }

    private void publishAgainIfNeeded(Bundle $r1) throws  {
        if (this.isObjectLiked != this.isObjectLikedOnServer && !publishLikeOrUnlikeAsync(this.isObjectLiked, $r1)) {
            publishDidError(!this.isObjectLiked);
        }
    }

    private void fetchVerifiedObjectId(final RequestCompletionCallback $r1) throws  {
        if (Utility.isNullOrEmpty(this.verifiedObjectId)) {
            final GetOGObjectIdRequestWrapper $r2 = new GetOGObjectIdRequestWrapper(this.objectId, this.objectType);
            final GetPageIdRequestWrapper $r3 = new GetPageIdRequestWrapper(this.objectId, this.objectType);
            GraphRequestBatch $r4 = new GraphRequestBatch();
            $r2.addToBatch($r4);
            $r3.addToBatch($r4);
            $r4.addCallback(new GraphRequestBatch.Callback() {
                public void onBatchCompleted(GraphRequestBatch batch) throws  {
                    LikeActionController.this.verifiedObjectId = $r2.verifiedObjectId;
                    if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                        LikeActionController.this.verifiedObjectId = $r3.verifiedObjectId;
                        LikeActionController.this.objectIsPage = $r3.objectIsPage;
                    }
                    if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                        FacebookRequestError $r9;
                        Logger.log(LoggingBehavior.DEVELOPER_ERRORS, LikeActionController.TAG, "Unable to verify the FB id for '%s'. Verify that it is a valid FB object or page", LikeActionController.this.objectId);
                        LikeActionController $r2 = LikeActionController.this;
                        if ($r3.getError() != null) {
                            $r9 = $r3.getError();
                        } else {
                            $r9 = $r2.getError();
                        }
                        $r2.logAppEventForError("get_verified_id", $r9);
                    }
                    if ($r1 != null) {
                        $r1.onComplete();
                    }
                }
            });
            $r4.executeAsync();
        } else if ($r1 != null) {
            $r1.onComplete();
        }
    }

    private void logAppEventForError(String $r1, Bundle $r2) throws  {
        Bundle $r3 = new Bundle($r2);
        $r3.putString("object_id", this.objectId);
        $r3.putString("object_type", this.objectType.toString());
        $r3.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_CURRENT_ACTION, $r1);
        getAppEventsLogger().logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_ERROR, null, $r3);
    }

    private void logAppEventForError(String $r1, FacebookRequestError $r2) throws  {
        Bundle $r3 = new Bundle();
        if ($r2 != null) {
            JSONObject $r4 = $r2.getRequestResult();
            if ($r4 != null) {
                $r3.putString("error", $r4.toString());
            }
        }
        logAppEventForError($r1, $r3);
    }
}
