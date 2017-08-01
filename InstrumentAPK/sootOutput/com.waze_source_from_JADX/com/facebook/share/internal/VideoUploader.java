package com.facebook.share.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.internal.WorkQueue;
import com.facebook.internal.WorkQueue.WorkItem;
import com.facebook.share.Sharer.Result;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.waze.navigate.social.ShareDriveActivity;
import dalvik.annotation.Signature;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoUploader {
    private static final String ERROR_BAD_SERVER_RESPONSE = "Unexpected error in server response";
    private static final String ERROR_UPLOAD = "Video upload failed";
    private static final int MAX_RETRIES_PER_PHASE = 2;
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_END_OFFSET = "end_offset";
    private static final String PARAM_FILE_SIZE = "file_size";
    private static final String PARAM_REF = "ref";
    private static final String PARAM_SESSION_ID = "upload_session_id";
    private static final String PARAM_START_OFFSET = "start_offset";
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_UPLOAD_PHASE = "upload_phase";
    private static final String PARAM_VALUE_UPLOAD_FINISH_PHASE = "finish";
    private static final String PARAM_VALUE_UPLOAD_START_PHASE = "start";
    private static final String PARAM_VALUE_UPLOAD_TRANSFER_PHASE = "transfer";
    private static final String PARAM_VIDEO_FILE_CHUNK = "video_file_chunk";
    private static final String PARAM_VIDEO_ID = "video_id";
    private static final int RETRY_DELAY_BACK_OFF_FACTOR = 3;
    private static final int RETRY_DELAY_UNIT_MS = 5000;
    private static final String TAG = "VideoUploader";
    private static final int UPLOAD_QUEUE_MAX_CONCURRENT = 8;
    private static AccessTokenTracker accessTokenTracker;
    private static Handler handler;
    private static boolean initialized;
    private static Set<UploadContext> pendingUploads = new HashSet();
    private static WorkQueue uploadQueue = new WorkQueue(8);

    static class C06041 extends AccessTokenTracker {
        C06041() throws  {
        }

        protected void onCurrentAccessTokenChanged(AccessToken $r1, AccessToken $r2) throws  {
            if ($r1 != null) {
                if ($r2 == null || !Utility.areObjectsEqual($r2.getUserId(), $r1.getUserId())) {
                    VideoUploader.cancelAllRequests();
                }
            }
        }
    }

    private static abstract class UploadWorkItemBase implements Runnable {
        protected int completedRetries;
        protected UploadContext uploadContext;

        class C06081 implements Runnable {
            C06081() throws  {
            }

            public void run() throws  {
                UploadWorkItemBase.this.enqueueRetry(UploadWorkItemBase.this.completedRetries + 1);
            }
        }

        protected abstract void enqueueRetry(int i) throws ;

        protected abstract Bundle getParameters() throws Exception;

        protected abstract Set<Integer> getTransientErrorCodes() throws ;

        protected abstract void handleError(FacebookException facebookException) throws ;

        protected abstract void handleSuccess(JSONObject jSONObject) throws JSONException;

        protected UploadWorkItemBase(UploadContext $r1, int $i0) throws  {
            this.uploadContext = $r1;
            this.completedRetries = $i0;
        }

        public void run() throws  {
            if (this.uploadContext.isCanceled) {
                endUploadWithFailure(null);
                return;
            }
            try {
                executeGraphRequestSynchronously(getParameters());
            } catch (FacebookException $r2) {
                endUploadWithFailure($r2);
            } catch (Throwable $r1) {
                endUploadWithFailure(new FacebookException(VideoUploader.ERROR_UPLOAD, $r1));
            }
        }

        protected void executeGraphRequestSynchronously(Bundle $r1) throws  {
            GraphResponse $r10 = new GraphRequest(this.uploadContext.accessToken, String.format(Locale.ROOT, "%s/videos", new Object[]{this.uploadContext.graphNode}), $r1, HttpMethod.POST, null).executeAndWait();
            if ($r10 != null) {
                FacebookRequestError $r11 = $r10.getError();
                JSONObject $r12 = $r10.getJSONObject();
                if ($r11 != null) {
                    if (!attemptRetry($r11.getSubErrorCode())) {
                        handleError(new FacebookGraphResponseException($r10, VideoUploader.ERROR_UPLOAD));
                        return;
                    }
                    return;
                } else if ($r12 != null) {
                    try {
                        handleSuccess($r12);
                        return;
                    } catch (Throwable $r3) {
                        endUploadWithFailure(new FacebookException(VideoUploader.ERROR_BAD_SERVER_RESPONSE, $r3));
                        return;
                    }
                } else {
                    handleError(new FacebookException(VideoUploader.ERROR_BAD_SERVER_RESPONSE));
                    return;
                }
            }
            handleError(new FacebookException(VideoUploader.ERROR_BAD_SERVER_RESPONSE));
        }

        private boolean attemptRetry(int $i0) throws  {
            if (this.completedRetries >= 2 || !getTransientErrorCodes().contains(Integer.valueOf($i0))) {
                return false;
            }
            VideoUploader.getHandler().postDelayed(new C06081(), (long) (((int) Math.pow(3.0d, (double) this.completedRetries)) * 5000));
            return true;
        }

        protected void endUploadWithFailure(FacebookException $r1) throws  {
            issueResponseOnMainThread($r1, null);
        }

        protected void issueResponseOnMainThread(final FacebookException $r1, final String $r2) throws  {
            VideoUploader.getHandler().post(new Runnable() {
                public void run() throws  {
                    VideoUploader.issueResponse(UploadWorkItemBase.this.uploadContext, $r1, $r2);
                }
            });
        }
    }

    private static class FinishUploadWorkItem extends UploadWorkItemBase {
        static final Set<Integer> transientErrorCodes = new C06051();

        static class C06051 extends HashSet<Integer> {
            C06051() throws  {
                add(Integer.valueOf(1363011));
            }
        }

        public FinishUploadWorkItem(UploadContext $r1, int $i0) throws  {
            super($r1, $i0);
        }

        public Bundle getParameters() throws  {
            Bundle $r1 = new Bundle();
            if (this.uploadContext.params != null) {
                $r1.putAll(this.uploadContext.params);
            }
            $r1.putString(VideoUploader.PARAM_UPLOAD_PHASE, VideoUploader.PARAM_VALUE_UPLOAD_FINISH_PHASE);
            $r1.putString(VideoUploader.PARAM_SESSION_ID, this.uploadContext.sessionId);
            Utility.putNonEmptyString($r1, "title", this.uploadContext.title);
            Utility.putNonEmptyString($r1, "description", this.uploadContext.description);
            Utility.putNonEmptyString($r1, VideoUploader.PARAM_REF, this.uploadContext.ref);
            return $r1;
        }

        protected void handleSuccess(JSONObject $r1) throws JSONException {
            if ($r1.getBoolean("success")) {
                issueResponseOnMainThread(null, this.uploadContext.videoId);
            } else {
                handleError(new FacebookException(VideoUploader.ERROR_BAD_SERVER_RESPONSE));
            }
        }

        protected void handleError(FacebookException $r1) throws  {
            VideoUploader.logError($r1, "Video '%s' failed to finish uploading", this.uploadContext.videoId);
            endUploadWithFailure($r1);
        }

        protected Set<Integer> getTransientErrorCodes() throws  {
            return transientErrorCodes;
        }

        protected void enqueueRetry(int $i0) throws  {
            VideoUploader.enqueueUploadFinish(this.uploadContext, $i0);
        }
    }

    private static class StartUploadWorkItem extends UploadWorkItemBase {
        static final Set<Integer> transientErrorCodes = new C06061();

        static class C06061 extends HashSet<Integer> {
            C06061() throws  {
                add(Integer.valueOf(ShareDriveActivity.TOOLTIP_TIME_MILLIS));
            }
        }

        public StartUploadWorkItem(UploadContext $r1, int $i0) throws  {
            super($r1, $i0);
        }

        public Bundle getParameters() throws  {
            Bundle $r1 = new Bundle();
            $r1.putString(VideoUploader.PARAM_UPLOAD_PHASE, VideoUploader.PARAM_VALUE_UPLOAD_START_PHASE);
            $r1.putLong(VideoUploader.PARAM_FILE_SIZE, this.uploadContext.videoSize);
            return $r1;
        }

        protected void handleSuccess(JSONObject $r1) throws JSONException {
            this.uploadContext.sessionId = $r1.getString(VideoUploader.PARAM_SESSION_ID);
            this.uploadContext.videoId = $r1.getString(VideoUploader.PARAM_VIDEO_ID);
            VideoUploader.enqueueUploadChunk(this.uploadContext, $r1.getString(VideoUploader.PARAM_START_OFFSET), $r1.getString(VideoUploader.PARAM_END_OFFSET), 0);
        }

        protected void handleError(FacebookException $r1) throws  {
            VideoUploader.logError($r1, "Error starting video upload", new Object[0]);
            endUploadWithFailure($r1);
        }

        protected Set<Integer> getTransientErrorCodes() throws  {
            return transientErrorCodes;
        }

        protected void enqueueRetry(int $i0) throws  {
            VideoUploader.enqueueUploadStart(this.uploadContext, $i0);
        }
    }

    private static class TransferChunkWorkItem extends UploadWorkItemBase {
        static final Set<Integer> transientErrorCodes = new C06071();
        private String chunkEnd;
        private String chunkStart;

        static class C06071 extends HashSet<Integer> {
            C06071() throws  {
                add(Integer.valueOf(1363019));
                add(Integer.valueOf(1363021));
                add(Integer.valueOf(1363030));
                add(Integer.valueOf(1363033));
                add(Integer.valueOf(1363041));
            }
        }

        public TransferChunkWorkItem(UploadContext $r1, String $r2, String $r3, int $i0) throws  {
            super($r1, $i0);
            this.chunkStart = $r2;
            this.chunkEnd = $r3;
        }

        public Bundle getParameters() throws IOException {
            Bundle $r1 = new Bundle();
            $r1.putString(VideoUploader.PARAM_UPLOAD_PHASE, VideoUploader.PARAM_VALUE_UPLOAD_TRANSFER_PHASE);
            $r1.putString(VideoUploader.PARAM_SESSION_ID, this.uploadContext.sessionId);
            $r1.putString(VideoUploader.PARAM_START_OFFSET, this.chunkStart);
            byte[] $r3 = VideoUploader.getChunk(this.uploadContext, this.chunkStart, this.chunkEnd);
            if ($r3 != null) {
                $r1.putByteArray(VideoUploader.PARAM_VIDEO_FILE_CHUNK, $r3);
                return $r1;
            }
            throw new FacebookException("Error reading video");
        }

        protected void handleSuccess(JSONObject $r1) throws JSONException {
            String $r2 = $r1.getString(VideoUploader.PARAM_START_OFFSET);
            String $r3 = $r1.getString(VideoUploader.PARAM_END_OFFSET);
            if (Utility.areObjectsEqual($r2, $r3)) {
                VideoUploader.enqueueUploadFinish(this.uploadContext, 0);
            } else {
                VideoUploader.enqueueUploadChunk(this.uploadContext, $r2, $r3, 0);
            }
        }

        protected void handleError(FacebookException $r1) throws  {
            VideoUploader.logError($r1, "Error uploading video '%s'", this.uploadContext.videoId);
            endUploadWithFailure($r1);
        }

        protected Set<Integer> getTransientErrorCodes() throws  {
            return transientErrorCodes;
        }

        protected void enqueueRetry(int $i0) throws  {
            VideoUploader.enqueueUploadChunk(this.uploadContext, this.chunkStart, this.chunkEnd, $i0);
        }
    }

    private static class UploadContext {
        public final AccessToken accessToken;
        public final FacebookCallback<Result> callback;
        public String chunkStart;
        public final String description;
        public final String graphNode;
        public boolean isCanceled;
        public Bundle params;
        public final String ref;
        public String sessionId;
        public final String title;
        public String videoId;
        public long videoSize;
        public InputStream videoStream;
        public final Uri videoUri;
        public WorkItem workItem;

        private UploadContext(@Signature({"(", "Lcom/facebook/share/model/ShareVideoContent;", "Ljava/lang/String;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) ShareVideoContent $r1, @Signature({"(", "Lcom/facebook/share/model/ShareVideoContent;", "Ljava/lang/String;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) String $r2, @Signature({"(", "Lcom/facebook/share/model/ShareVideoContent;", "Ljava/lang/String;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) FacebookCallback<Result> $r3) throws  {
            this.chunkStart = AppEventsConstants.EVENT_PARAM_VALUE_NO;
            this.accessToken = AccessToken.getCurrentAccessToken();
            this.videoUri = $r1.getVideo().getLocalUrl();
            this.title = $r1.getContentTitle();
            this.description = $r1.getContentDescription();
            this.ref = $r1.getRef();
            this.graphNode = $r2;
            this.callback = $r3;
            this.params = $r1.getVideo().getParameters();
        }

        private void initialize() throws FileNotFoundException {
            try {
                if (Utility.isFileUri(this.videoUri)) {
                    ParcelFileDescriptor $r5 = ParcelFileDescriptor.open(new File(this.videoUri.getPath()), 268435456);
                    this.videoSize = $r5.getStatSize();
                    this.videoStream = new AutoCloseInputStream($r5);
                } else if (Utility.isContentUri(this.videoUri)) {
                    this.videoSize = Utility.getContentSize(this.videoUri);
                    this.videoStream = FacebookSdk.getApplicationContext().getContentResolver().openInputStream(this.videoUri);
                } else {
                    throw new FacebookException("Uri must be a content:// or file:// uri");
                }
            } catch (FileNotFoundException $r1) {
                Utility.closeQuietly(this.videoStream);
                throw $r1;
            }
        }
    }

    public static synchronized void uploadAsync(@Signature({"(", "Lcom/facebook/share/model/ShareVideoContent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) ShareVideoContent $r0, @Signature({"(", "Lcom/facebook/share/model/ShareVideoContent;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) FacebookCallback<Result> $r1) throws FileNotFoundException {
        Class cls = VideoUploader.class;
        synchronized (cls) {
            try {
                uploadAsync($r0, "me", $r1);
            } finally {
                cls = VideoUploader.class;
            }
        }
    }

    public static synchronized void uploadAsync(@Signature({"(", "Lcom/facebook/share/model/ShareVideoContent;", "Ljava/lang/String;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) ShareVideoContent $r0, @Signature({"(", "Lcom/facebook/share/model/ShareVideoContent;", "Ljava/lang/String;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) String $r1, @Signature({"(", "Lcom/facebook/share/model/ShareVideoContent;", "Ljava/lang/String;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) FacebookCallback<Result> $r2) throws FileNotFoundException {
        Class cls = VideoUploader.class;
        synchronized (cls) {
            try {
                if (!initialized) {
                    registerAccessTokenTracker();
                    initialized = true;
                }
                Validate.notNull($r0, "videoContent");
                Validate.notNull($r1, "graphNode");
                ShareVideo $r4 = $r0.getVideo();
                Validate.notNull($r4, "videoContent.video");
                Validate.notNull($r4.getLocalUrl(), "videoContent.video.localUrl");
                UploadContext $r3 = new UploadContext($r0, $r1, $r2);
                $r3.initialize();
                pendingUploads.add($r3);
                enqueueUploadStart($r3, 0);
            } finally {
                cls = VideoUploader.class;
            }
        }
    }

    private static synchronized void cancelAllRequests() throws  {
        Class cls = VideoUploader.class;
        synchronized (cls) {
            try {
                for (UploadContext uploadContext : pendingUploads) {
                    uploadContext.isCanceled = true;
                }
            } finally {
                cls = VideoUploader.class;
            }
        }
    }

    private static synchronized void removePendingUpload(UploadContext $r0) throws  {
        Class cls = VideoUploader.class;
        synchronized (cls) {
            try {
                pendingUploads.remove($r0);
            } finally {
                cls = VideoUploader.class;
            }
        }
    }

    private static synchronized Handler getHandler() throws  {
        Class cls = VideoUploader.class;
        synchronized (cls) {
            try {
                if (handler == null) {
                    handler = new Handler(Looper.getMainLooper());
                }
                Handler $r1 = handler;
                return $r1;
            } finally {
                cls = VideoUploader.class;
            }
        }
    }

    private static void issueResponse(UploadContext $r0, FacebookException $r1, String $r2) throws  {
        removePendingUpload($r0);
        Utility.closeQuietly($r0.videoStream);
        if ($r0.callback == null) {
            return;
        }
        if ($r1 != null) {
            ShareInternalUtility.invokeOnErrorCallback($r0.callback, $r1);
        } else if ($r0.isCanceled) {
            ShareInternalUtility.invokeOnCancelCallback($r0.callback);
        } else {
            ShareInternalUtility.invokeOnSuccessCallback($r0.callback, $r2);
        }
    }

    private static void enqueueUploadStart(UploadContext $r0, int $i0) throws  {
        enqueueRequest($r0, new StartUploadWorkItem($r0, $i0));
    }

    private static void enqueueUploadChunk(UploadContext $r0, String $r1, String $r2, int $i0) throws  {
        enqueueRequest($r0, new TransferChunkWorkItem($r0, $r1, $r2, $i0));
    }

    private static void enqueueUploadFinish(UploadContext $r0, int $i0) throws  {
        enqueueRequest($r0, new FinishUploadWorkItem($r0, $i0));
    }

    private static synchronized void enqueueRequest(UploadContext $r0, Runnable $r1) throws  {
        Class cls = VideoUploader.class;
        synchronized (cls) {
            try {
                $r0.workItem = uploadQueue.addActiveWorkItem($r1);
            } finally {
                cls = VideoUploader.class;
            }
        }
    }

    private static byte[] getChunk(UploadContext $r0, String $r1, String $r2) throws IOException {
        if (Utility.areObjectsEqual($r1, $r0.chunkStart)) {
            int $i3;
            int $i2 = (int) (Long.parseLong($r2) - Long.parseLong($r1));
            ByteArrayOutputStream $r4 = new ByteArrayOutputStream();
            byte[] $r3 = new byte[Math.min(8192, $i2)];
            do {
                InputStream $r7 = $r0.videoStream;
                $i3 = $r7.read($r3);
                if ($i3 != -1) {
                    $r4.write($r3, 0, $i3);
                    $i2 -= $i3;
                    if ($i2 == 0) {
                    }
                }
                $r0.chunkStart = $r2;
                return $r4.toByteArray();
            } while ($i2 >= 0);
            logError(null, "Error reading video chunk. Expected buffer length - '%d'. Actual - '%d'.", Integer.valueOf($i2 + $i3), Integer.valueOf($i3));
            return null;
        }
        logError(null, "Error reading video chunk. Expected chunk '%s'. Requested chunk '%s'.", $r0.chunkStart, $r1);
        return null;
    }

    private static void registerAccessTokenTracker() throws  {
        accessTokenTracker = new C06041();
    }

    private static void logError(Exception $r0, String $r1, Object... $r2) throws  {
        Log.e(TAG, String.format(Locale.ROOT, $r1, $r2), $r0);
    }
}
