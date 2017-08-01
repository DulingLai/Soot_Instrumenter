package com.facebook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.location.Location;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.InternalSettings;
import com.facebook.internal.Logger;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import dalvik.annotation.Signature;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GraphRequest {
    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    private static final String ACCESS_TOKEN_PARAM = "access_token";
    private static final String ATTACHED_FILES_PARAM = "attached_files";
    private static final String ATTACHMENT_FILENAME_PREFIX = "file";
    private static final String BATCH_APP_ID_PARAM = "batch_app_id";
    private static final String BATCH_BODY_PARAM = "body";
    private static final String BATCH_ENTRY_DEPENDS_ON_PARAM = "depends_on";
    private static final String BATCH_ENTRY_NAME_PARAM = "name";
    private static final String BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM = "omit_response_on_success";
    private static final String BATCH_METHOD_PARAM = "method";
    private static final String BATCH_PARAM = "batch";
    private static final String BATCH_RELATIVE_URL_PARAM = "relative_url";
    private static final String CAPTION_PARAM = "caption";
    private static final String CONTENT_ENCODING_HEADER = "Content-Encoding";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String DEBUG_KEY = "__debug__";
    private static final String DEBUG_MESSAGES_KEY = "messages";
    private static final String DEBUG_MESSAGE_KEY = "message";
    private static final String DEBUG_MESSAGE_LINK_KEY = "link";
    private static final String DEBUG_MESSAGE_TYPE_KEY = "type";
    private static final String DEBUG_PARAM = "debug";
    private static final String DEBUG_SEVERITY_INFO = "info";
    private static final String DEBUG_SEVERITY_WARNING = "warning";
    public static final String FIELDS_PARAM = "fields";
    private static final String FORMAT_JSON = "json";
    private static final String FORMAT_PARAM = "format";
    private static final String ISO_8601_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final int MAXIMUM_BATCH_SIZE = 50;
    private static final String ME = "me";
    private static final String MIME_BOUNDARY = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
    private static final String MY_FRIENDS = "me/friends";
    private static final String MY_PHOTOS = "me/photos";
    private static final String PICTURE_PARAM = "picture";
    private static final String SDK_ANDROID = "android";
    private static final String SDK_PARAM = "sdk";
    private static final String SEARCH = "search";
    public static final String TAG = GraphRequest.class.getSimpleName();
    private static final String USER_AGENT_BASE = "FBAndroidSDK";
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String VIDEOS_SUFFIX = "/videos";
    private static String defaultBatchApplicationId;
    private static volatile String userAgent;
    private static Pattern versionPattern = Pattern.compile("^/?v\\d+\\.\\d+/(.*)");
    private AccessToken accessToken;
    private String batchEntryDependsOn;
    private String batchEntryName;
    private boolean batchEntryOmitResultOnSuccess;
    private Callback callback;
    private JSONObject graphObject;
    private String graphPath;
    private HttpMethod httpMethod;
    private String overriddenURL;
    private Bundle parameters;
    private boolean skipClientToken;
    private Object tag;
    private String version;

    public interface Callback {
        void onCompleted(GraphResponse graphResponse) throws ;
    }

    private interface KeyValueSerializer {
        void writeString(String str, String str2) throws IOException;
    }

    private static class Attachment {
        private final GraphRequest request;
        private final Object value;

        public Attachment(GraphRequest $r1, Object $r2) throws  {
            this.request = $r1;
            this.value = $r2;
        }

        public GraphRequest getRequest() throws  {
            return this.request;
        }

        public Object getValue() throws  {
            return this.value;
        }
    }

    public interface GraphJSONArrayCallback {
        void onCompleted(JSONArray jSONArray, GraphResponse graphResponse) throws ;
    }

    public interface GraphJSONObjectCallback {
        void onCompleted(JSONObject jSONObject, GraphResponse graphResponse) throws ;
    }

    public interface OnProgressCallback extends Callback {
        void onProgress(long j, long j2) throws ;
    }

    public static class ParcelableResourceWithMimeType<RESOURCE extends Parcelable> implements Parcelable {
        public static final Creator<ParcelableResourceWithMimeType> CREATOR = new C04911();
        private final String mimeType;
        private final RESOURCE resource;

        static class C04911 implements Creator<ParcelableResourceWithMimeType> {
            C04911() throws  {
            }

            public ParcelableResourceWithMimeType createFromParcel(Parcel $r1) throws  {
                return new ParcelableResourceWithMimeType($r1);
            }

            public ParcelableResourceWithMimeType[] newArray(int $i0) throws  {
                return new ParcelableResourceWithMimeType[$i0];
            }
        }

        public int describeContents() throws  {
            return 1;
        }

        public String getMimeType() throws  {
            return this.mimeType;
        }

        public RESOURCE getResource() throws  {
            return this.resource;
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            $r1.writeString(this.mimeType);
            $r1.writeParcelable(this.resource, $i0);
        }

        public ParcelableResourceWithMimeType(@Signature({"(TRESOURCE;", "Ljava/lang/String;", ")V"}) RESOURCE $r1, @Signature({"(TRESOURCE;", "Ljava/lang/String;", ")V"}) String $r2) throws  {
            this.mimeType = $r2;
            this.resource = $r1;
        }

        private ParcelableResourceWithMimeType(Parcel $r1) throws  {
            this.mimeType = $r1.readString();
            this.resource = $r1.readParcelable(FacebookSdk.getApplicationContext().getClassLoader());
        }
    }

    private static class Serializer implements KeyValueSerializer {
        private boolean firstWrite = true;
        private final Logger logger;
        private final OutputStream outputStream;
        private boolean useUrlEncode = false;

        public Serializer(OutputStream $r1, Logger $r2, boolean $z0) throws  {
            this.outputStream = $r1;
            this.logger = $r2;
            this.useUrlEncode = $z0;
        }

        public void writeObject(String $r1, Object $r3, GraphRequest $r2) throws IOException {
            if (this.outputStream instanceof RequestOutputStream) {
                ((RequestOutputStream) this.outputStream).setCurrentRequest($r2);
            }
            if (GraphRequest.isSupportedParameterType($r3)) {
                writeString($r1, GraphRequest.parameterToString($r3));
            } else if ($r3 instanceof Bitmap) {
                writeBitmap($r1, (Bitmap) $r3);
            } else if ($r3 instanceof byte[]) {
                writeBytes($r1, (byte[]) $r3);
            } else if ($r3 instanceof Uri) {
                writeContentUri($r1, (Uri) $r3, null);
            } else if ($r3 instanceof ParcelFileDescriptor) {
                writeFile($r1, (ParcelFileDescriptor) $r3, null);
            } else if ($r3 instanceof ParcelableResourceWithMimeType) {
                ParcelableResourceWithMimeType $r11 = (ParcelableResourceWithMimeType) $r3;
                Parcelable $r12 = $r11.getResource();
                String $r6 = $r11.getMimeType();
                if ($r12 instanceof ParcelFileDescriptor) {
                    writeFile($r1, (ParcelFileDescriptor) $r12, $r6);
                } else if ($r12 instanceof Uri) {
                    writeContentUri($r1, (Uri) $r12, $r6);
                } else {
                    throw getInvalidTypeError();
                }
            } else {
                throw getInvalidTypeError();
            }
        }

        private RuntimeException getInvalidTypeError() throws  {
            return new IllegalArgumentException("value is not a supported type.");
        }

        public void writeRequestsAsJson(@Signature({"(", "Ljava/lang/String;", "Lorg/json/JSONArray;", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Lorg/json/JSONArray;", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;)V"}) JSONArray $r2, @Signature({"(", "Ljava/lang/String;", "Lorg/json/JSONArray;", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;)V"}) Collection<GraphRequest> $r3) throws IOException, JSONException {
            if (this.outputStream instanceof RequestOutputStream) {
                RequestOutputStream $r6 = (RequestOutputStream) this.outputStream;
                writeContentDisposition($r1, null, null);
                write("[", new Object[0]);
                int $i0 = 0;
                for (GraphRequest $r10 : $r3) {
                    JSONObject $r11 = $r2.getJSONObject($i0);
                    $r6.setCurrentRequest($r10);
                    if ($i0 > 0) {
                        write(",%s", $r11.toString());
                    } else {
                        write("%s", $r11.toString());
                    }
                    $i0++;
                }
                write("]", new Object[0]);
                if (this.logger != null) {
                    this.logger.appendKeyValue("    " + $r1, $r2.toString());
                    return;
                }
                return;
            }
            writeString($r1, $r2.toString());
        }

        public void writeString(String $r1, String $r2) throws IOException {
            writeContentDisposition($r1, null, null);
            writeLine("%s", $r2);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + $r1, $r2);
            }
        }

        public void writeBitmap(String $r1, Bitmap $r2) throws IOException {
            writeContentDisposition($r1, $r1, "image/png");
            $r2.compress(CompressFormat.PNG, 100, this.outputStream);
            writeLine("", new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + $r1, "<Image>");
            }
        }

        public void writeBytes(String $r1, byte[] $r2) throws IOException {
            writeContentDisposition($r1, $r1, "content/unknown");
            this.outputStream.write($r2);
            writeLine("", new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + $r1, String.format(Locale.ROOT, "<Data: %d>", new Object[]{Integer.valueOf($r2.length)}));
            }
        }

        public void writeContentUri(String $r1, Uri $r2, String $r3) throws IOException {
            if ($r3 == null) {
                $r3 = "content/unknown";
            }
            writeContentDisposition($r1, $r1, $r3);
            InputStream $r6 = FacebookSdk.getApplicationContext().getContentResolver().openInputStream($r2);
            int $i0 = 0;
            if (this.outputStream instanceof ProgressNoopOutputStream) {
                ((ProgressNoopOutputStream) this.outputStream).addProgress(Utility.getContentSize($r2));
            } else {
                $i0 = 0 + Utility.copyAndCloseInputStream($r6, this.outputStream);
            }
            writeLine("", new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                this.logger.appendKeyValue("    " + $r1, String.format(Locale.ROOT, "<Data: %d>", new Object[]{Integer.valueOf($i0)}));
            }
        }

        public void writeFile(String $r1, ParcelFileDescriptor $r2, String $r4) throws IOException {
            if ($r4 == null) {
                $r4 = "content/unknown";
            }
            writeContentDisposition($r1, $r1, $r4);
            int $i0 = 0;
            if (this.outputStream instanceof ProgressNoopOutputStream) {
                ((ProgressNoopOutputStream) this.outputStream).addProgress($r2.getStatSize());
            } else {
                $i0 = 0 + Utility.copyAndCloseInputStream(new AutoCloseInputStream($r2), this.outputStream);
            }
            writeLine("", new Object[0]);
            writeRecordBoundary();
            if (this.logger != null) {
                Logger $r8 = this.logger;
                $r1 = "    " + $r1;
                Locale locale = Locale.ROOT;
                $r8.appendKeyValue($r1, String.format(locale, "<Data: %d>", new Object[]{Integer.valueOf($i0)}));
            }
        }

        public void writeRecordBoundary() throws IOException {
            if (this.useUrlEncode) {
                this.outputStream.write("&".getBytes());
                return;
            }
            writeLine("--%s", GraphRequest.MIME_BOUNDARY);
        }

        public void writeContentDisposition(String $r1, String $r2, String $r3) throws IOException {
            if (this.useUrlEncode) {
                this.outputStream.write(String.format("%s=", new Object[]{$r1}).getBytes());
                return;
            }
            write("Content-Disposition: form-data; name=\"%s\"", $r1);
            if ($r2 != null) {
                write("; filename=\"%s\"", $r2);
            }
            writeLine("", new Object[0]);
            if ($r3 != null) {
                writeLine("%s: %s", GraphRequest.CONTENT_TYPE_HEADER, $r3);
            }
            writeLine("", new Object[0]);
        }

        public void write(String $r1, Object... $r2) throws IOException {
            if (this.useUrlEncode) {
                this.outputStream.write(URLEncoder.encode(String.format(Locale.US, $r1, $r2), "UTF-8").getBytes());
                return;
            }
            if (this.firstWrite) {
                this.outputStream.write("--".getBytes());
                this.outputStream.write(GraphRequest.MIME_BOUNDARY.getBytes());
                this.outputStream.write("\r\n".getBytes());
                this.firstWrite = false;
            }
            this.outputStream.write(String.format($r1, $r2).getBytes());
        }

        public void writeLine(String $r1, Object... $r2) throws IOException {
            write($r1, $r2);
            if (!this.useUrlEncode) {
                write("\r\n", new Object[0]);
            }
        }
    }

    private static String getDefaultPhotoPathIfNull(String $r0) throws  {
        return $r0 == null ? "me/photos" : $r0;
    }

    public GraphRequest() throws  {
        this(null, null, null, null, null);
    }

    public GraphRequest(AccessToken $r1, String $r2) throws  {
        this($r1, $r2, null, null, null);
    }

    public GraphRequest(AccessToken $r1, String $r2, Bundle $r3, HttpMethod $r4) throws  {
        this($r1, $r2, $r3, $r4, null);
    }

    public GraphRequest(AccessToken $r1, String $r2, Bundle $r3, HttpMethod $r4, Callback $r5) throws  {
        this($r1, $r2, $r3, $r4, $r5, null);
    }

    public GraphRequest(AccessToken $r1, String $r2, Bundle $r3, HttpMethod $r4, Callback $r5, String $r6) throws  {
        this.batchEntryOmitResultOnSuccess = true;
        this.skipClientToken = false;
        this.accessToken = $r1;
        this.graphPath = $r2;
        this.version = $r6;
        setCallback($r5);
        setHttpMethod($r4);
        if ($r3 != null) {
            this.parameters = new Bundle($r3);
        } else {
            this.parameters = new Bundle();
        }
        if (this.version == null) {
            this.version = ServerProtocol.getAPIVersion();
        }
    }

    GraphRequest(AccessToken $r1, URL $r2) throws  {
        this.batchEntryOmitResultOnSuccess = true;
        this.skipClientToken = false;
        this.accessToken = $r1;
        this.overriddenURL = $r2.toString();
        setHttpMethod(HttpMethod.GET);
        this.parameters = new Bundle();
    }

    public static GraphRequest newDeleteObjectRequest(AccessToken $r0, String $r1, Callback $r2) throws  {
        return new GraphRequest($r0, $r1, null, HttpMethod.DELETE, $r2);
    }

    public static GraphRequest newMeRequest(AccessToken $r0, final GraphJSONObjectCallback $r1) throws  {
        return new GraphRequest($r0, ME, null, null, new Callback() {
            public void onCompleted(GraphResponse $r1) throws  {
                if ($r1 != null) {
                    $r1.onCompleted($r1.getJSONObject(), $r1);
                }
            }
        });
    }

    public static GraphRequest newPostRequest(AccessToken $r0, String $r1, JSONObject $r2, Callback $r3) throws  {
        GraphRequest graphRequest = new GraphRequest($r0, $r1, null, HttpMethod.POST, $r3);
        graphRequest.setGraphObject($r2);
        return graphRequest;
    }

    public static GraphRequest newMyFriendsRequest(AccessToken $r0, final GraphJSONArrayCallback $r1) throws  {
        return new GraphRequest($r0, MY_FRIENDS, null, null, new Callback() {
            public void onCompleted(GraphResponse $r1) throws  {
                if ($r1 != null) {
                    JSONObject $r3 = $r1.getJSONObject();
                    $r1.onCompleted($r3 != null ? $r3.optJSONArray("data") : null, $r1);
                }
            }
        });
    }

    public static GraphRequest newGraphPathRequest(AccessToken $r0, String $r1, Callback $r2) throws  {
        return new GraphRequest($r0, $r1, null, null, $r2);
    }

    public static GraphRequest newPlacesSearchRequest(AccessToken $r0, Location $r1, int $i0, int $i1, String $r2, GraphJSONArrayCallback $r3) throws  {
        if ($r1 == null && Utility.isNullOrEmpty($r2)) {
            throw new FacebookException("Either location or searchText must be specified.");
        }
        Bundle $r4 = new Bundle(5);
        $r4.putString("type", "place");
        $r4.putInt("limit", $i1);
        if ($r1 != null) {
            $r4.putString("center", String.format(Locale.US, "%f,%f", new Object[]{Double.valueOf($r1.getLatitude()), Double.valueOf($r1.getLongitude())}));
            $r4.putInt("distance", $i0);
        }
        if (!Utility.isNullOrEmpty($r2)) {
            $r4.putString("q", $r2);
        }
        final GraphJSONArrayCallback graphJSONArrayCallback = $r3;
        C04873 c04873 = new Callback() {
            public void onCompleted(GraphResponse $r1) throws  {
                if (graphJSONArrayCallback != null) {
                    JSONObject $r3 = $r1.getJSONObject();
                    graphJSONArrayCallback.onCompleted($r3 != null ? $r3.optJSONArray("data") : null, $r1);
                }
            }
        };
        return new GraphRequest($r0, SEARCH, $r4, HttpMethod.GET, c04873);
    }

    public static GraphRequest newUploadPhotoRequest(AccessToken $r0, String $r7, Bitmap $r1, String $r2, Bundle $r3, Callback $r4) throws  {
        $r7 = getDefaultPhotoPathIfNull($r7);
        Bundle $r5 = new Bundle();
        if ($r3 != null) {
            $r5.putAll($r3);
        }
        $r5.putParcelable("picture", $r1);
        if (!($r2 == null || $r2.isEmpty())) {
            $r5.putString("caption", $r2);
        }
        return new GraphRequest($r0, $r7, $r5, HttpMethod.POST, $r4);
    }

    public static GraphRequest newUploadPhotoRequest(AccessToken $r0, String $r7, File $r1, String $r2, Bundle $r3, Callback $r4) throws FileNotFoundException {
        $r7 = getDefaultPhotoPathIfNull($r7);
        ParcelFileDescriptor $r8 = ParcelFileDescriptor.open($r1, 268435456);
        Bundle $r5 = new Bundle();
        if ($r3 != null) {
            $r5.putAll($r3);
        }
        $r5.putParcelable("picture", $r8);
        if (!($r2 == null || $r2.isEmpty())) {
            $r5.putString("caption", $r2);
        }
        return new GraphRequest($r0, $r7, $r5, HttpMethod.POST, $r4);
    }

    public static GraphRequest newUploadPhotoRequest(AccessToken $r0, String $r5, Uri $r1, String $r2, Bundle $r3, Callback $r4) throws FileNotFoundException {
        $r5 = getDefaultPhotoPathIfNull($r5);
        if (Utility.isFileUri($r1)) {
            return newUploadPhotoRequest($r0, $r5, new File($r1.getPath()), $r2, $r3, $r4);
        }
        if (Utility.isContentUri($r1)) {
            Bundle $r10 = new Bundle();
            if ($r3 != null) {
                $r10.putAll($r3);
            }
            $r10.putParcelable("picture", $r1);
            return new GraphRequest($r0, $r5, $r10, HttpMethod.POST, $r4);
        }
        throw new FacebookException("The photo Uri must be either a file:// or content:// Uri");
    }

    public static GraphRequest newCustomAudienceThirdPartyIdRequest(AccessToken $r0, Context $r1, String $r5, Callback $r2) throws  {
        if ($r5 == null && $r0 != null) {
            $r5 = $r0.getApplicationId();
        }
        if ($r5 == null) {
            $r5 = Utility.getMetadataApplicationId($r1);
        }
        if ($r5 == null) {
            throw new FacebookException("Facebook App ID cannot be determined");
        }
        $r5 = $r5 + "/custom_audience_third_party_id";
        AttributionIdentifiers $r8 = AttributionIdentifiers.getAttributionIdentifiers($r1);
        Bundle $r3 = new Bundle();
        if ($r0 == null) {
            String $r9;
            if ($r8.getAttributionId() != null) {
                $r9 = $r8.getAttributionId();
            } else {
                $r9 = $r8.getAndroidAdvertiserId();
            }
            if ($r8.getAttributionId() != null) {
                $r3.putString("udid", $r9);
            }
        }
        if (FacebookSdk.getLimitEventAndDataUsage($r1) || $r8.isTrackingLimited()) {
            $r3.putString("limit_event_usage", AppEventsConstants.EVENT_PARAM_VALUE_YES);
        }
        return new GraphRequest($r0, $r5, $r3, HttpMethod.GET, $r2);
    }

    public static GraphRequest newCustomAudienceThirdPartyIdRequest(AccessToken $r0, Context $r1, Callback $r2) throws  {
        return newCustomAudienceThirdPartyIdRequest($r0, $r1, null, $r2);
    }

    public final JSONObject getGraphObject() throws  {
        return this.graphObject;
    }

    public final void setGraphObject(JSONObject $r1) throws  {
        this.graphObject = $r1;
    }

    public final String getGraphPath() throws  {
        return this.graphPath;
    }

    public final void setGraphPath(String $r1) throws  {
        this.graphPath = $r1;
    }

    public final HttpMethod getHttpMethod() throws  {
        return this.httpMethod;
    }

    public final void setHttpMethod(HttpMethod $r1) throws  {
        if (this.overriddenURL == null || $r1 == HttpMethod.GET) {
            if ($r1 == null) {
                $r1 = HttpMethod.GET;
            }
            this.httpMethod = $r1;
            return;
        }
        throw new FacebookException("Can't change HTTP method on request with overridden URL.");
    }

    public final String getVersion() throws  {
        return this.version;
    }

    public final void setVersion(String $r1) throws  {
        this.version = $r1;
    }

    public final void setSkipClientToken(boolean $z0) throws  {
        this.skipClientToken = $z0;
    }

    public final Bundle getParameters() throws  {
        return this.parameters;
    }

    public final void setParameters(Bundle $r1) throws  {
        this.parameters = $r1;
    }

    public final AccessToken getAccessToken() throws  {
        return this.accessToken;
    }

    public final void setAccessToken(AccessToken $r1) throws  {
        this.accessToken = $r1;
    }

    public final String getBatchEntryName() throws  {
        return this.batchEntryName;
    }

    public final void setBatchEntryName(String $r1) throws  {
        this.batchEntryName = $r1;
    }

    public final String getBatchEntryDependsOn() throws  {
        return this.batchEntryDependsOn;
    }

    public final void setBatchEntryDependsOn(String $r1) throws  {
        this.batchEntryDependsOn = $r1;
    }

    public final boolean getBatchEntryOmitResultOnSuccess() throws  {
        return this.batchEntryOmitResultOnSuccess;
    }

    public final void setBatchEntryOmitResultOnSuccess(boolean $z0) throws  {
        this.batchEntryOmitResultOnSuccess = $z0;
    }

    public static final String getDefaultBatchApplicationId() throws  {
        return defaultBatchApplicationId;
    }

    public static final void setDefaultBatchApplicationId(String $r0) throws  {
        defaultBatchApplicationId = $r0;
    }

    public final Callback getCallback() throws  {
        return this.callback;
    }

    public final void setCallback(final Callback $r1) throws  {
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_INFO) || FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            this.callback = new Callback() {
                public void onCompleted(GraphResponse $r1) throws  {
                    JSONArray $r3;
                    JSONObject $r2 = $r1.getJSONObject();
                    if ($r2 != null) {
                        $r2 = $r2.optJSONObject(GraphRequest.DEBUG_KEY);
                    } else {
                        $r2 = null;
                    }
                    if ($r2 != null) {
                        $r3 = $r2.optJSONArray(GraphRequest.DEBUG_MESSAGES_KEY);
                    } else {
                        $r3 = null;
                    }
                    if ($r3 != null) {
                        for (int $i0 = 0; $i0 < $r3.length(); $i0++) {
                            String $r4;
                            String $r5;
                            String $r6;
                            $r2 = $r3.optJSONObject($i0);
                            if ($r2 != null) {
                                $r4 = $r2.optString("message");
                            } else {
                                $r4 = null;
                            }
                            if ($r2 != null) {
                                $r5 = $r2.optString("type");
                            } else {
                                $r5 = null;
                            }
                            if ($r2 != null) {
                                $r6 = $r2.optString("link");
                            } else {
                                $r6 = null;
                            }
                            if (!($r4 == null || $r5 == null)) {
                                LoggingBehavior $r7 = LoggingBehavior.GRAPH_API_DEBUG_INFO;
                                if ($r5.equals(GraphRequest.DEBUG_SEVERITY_WARNING)) {
                                    $r7 = LoggingBehavior.GRAPH_API_DEBUG_WARNING;
                                }
                                if (!Utility.isNullOrEmpty($r6)) {
                                    $r4 = $r4 + " Link: " + $r6;
                                }
                                Logger.log($r7, GraphRequest.TAG, $r4);
                            }
                        }
                    }
                    if ($r1 != null) {
                        $r1.onCompleted($r1);
                    }
                }
            };
        } else {
            this.callback = $r1;
        }
    }

    public final void setTag(Object $r1) throws  {
        this.tag = $r1;
    }

    public final Object getTag() throws  {
        return this.tag;
    }

    public final GraphResponse executeAndWait() throws  {
        return executeAndWait(this);
    }

    public final GraphRequestAsyncTask executeAsync() throws  {
        return executeBatchAsync(this);
    }

    public static HttpURLConnection toHttpConnection(GraphRequest... $r0) throws  {
        return toHttpConnection(Arrays.asList($r0));
    }

    public static HttpURLConnection toHttpConnection(@Signature({"(", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;)", "Ljava/net/HttpURLConnection;"}) Collection<GraphRequest> $r0) throws  {
        Validate.notEmptyAndContainsNoNulls($r0, "requests");
        return toHttpConnection(new GraphRequestBatch((Collection) $r0));
    }

    public static HttpURLConnection toHttpConnection(GraphRequestBatch $r0) throws  {
        validateFieldsParamForGetRequests($r0);
        try {
            URL $r2;
            if ($r0.size() == 1) {
                $r2 = new URL($r0.get(0).getUrlForSingleRequest());
            } else {
                $r2 = new URL(ServerProtocol.getGraphUrlBase());
            }
            try {
                HttpURLConnection $r4 = createConnection($r2);
                serializeToUrlConnection($r0, $r4);
                return $r4;
            } catch (Throwable $r7) {
                throw new FacebookException("could not construct request body", $r7);
            } catch (Throwable $r8) {
                throw new FacebookException("could not construct request body", $r8);
            }
        } catch (Throwable $r5) {
            throw new FacebookException("could not construct URL for request", $r5);
        }
    }

    public static GraphResponse executeAndWait(GraphRequest $r0) throws  {
        List $r2 = executeBatchAndWait($r0);
        if ($r2 != null && $r2.size() == 1) {
            return (GraphResponse) $r2.get(0);
        }
        throw new FacebookException("invalid state: expected a single response");
    }

    public static List<GraphResponse> executeBatchAndWait(@Signature({"([", "Lcom/facebook/GraphRequest;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) GraphRequest... $r0) throws  {
        Validate.notNull($r0, "requests");
        return executeBatchAndWait(Arrays.asList($r0));
    }

    public static List<GraphResponse> executeBatchAndWait(@Signature({"(", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;)", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) Collection<GraphRequest> $r0) throws  {
        return executeBatchAndWait(new GraphRequestBatch((Collection) $r0));
    }

    public static List<GraphResponse> executeBatchAndWait(@Signature({"(", "Lcom/facebook/GraphRequestBatch;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) GraphRequestBatch $r0) throws  {
        Validate.notEmptyAndContainsNoNulls($r0, "requests");
        try {
            return executeConnectionAndWait(toHttpConnection($r0), $r0);
        } catch (Throwable $r1) {
            List $r4 = GraphResponse.constructErrorResponses($r0.getRequests(), null, new FacebookException($r1));
            runCallbacks($r0, $r4);
            return $r4;
        }
    }

    public static GraphRequestAsyncTask executeBatchAsync(GraphRequest... $r0) throws  {
        Validate.notNull($r0, "requests");
        return executeBatchAsync(Arrays.asList($r0));
    }

    public static GraphRequestAsyncTask executeBatchAsync(@Signature({"(", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;)", "Lcom/facebook/GraphRequestAsyncTask;"}) Collection<GraphRequest> $r0) throws  {
        return executeBatchAsync(new GraphRequestBatch((Collection) $r0));
    }

    public static GraphRequestAsyncTask executeBatchAsync(GraphRequestBatch $r0) throws  {
        Validate.notEmptyAndContainsNoNulls($r0, "requests");
        GraphRequestAsyncTask $r1 = new GraphRequestAsyncTask($r0);
        $r1.executeOnSettingsExecutor();
        return $r1;
    }

    public static List<GraphResponse> executeConnectionAndWait(@Signature({"(", "Ljava/net/HttpURLConnection;", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;)", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) HttpURLConnection $r0, @Signature({"(", "Ljava/net/HttpURLConnection;", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;)", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) Collection<GraphRequest> $r1) throws  {
        return executeConnectionAndWait($r0, new GraphRequestBatch((Collection) $r1));
    }

    public static List<GraphResponse> executeConnectionAndWait(@Signature({"(", "Ljava/net/HttpURLConnection;", "Lcom/facebook/GraphRequestBatch;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) HttpURLConnection $r0, @Signature({"(", "Ljava/net/HttpURLConnection;", "Lcom/facebook/GraphRequestBatch;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) GraphRequestBatch $r1) throws  {
        List $r2 = GraphResponse.fromHttpConnection($r0, $r1);
        Utility.disconnectQuietly($r0);
        if ($r1.size() != $r2.size()) {
            throw new FacebookException(String.format(Locale.US, "Received %d responses while expecting %d", new Object[]{Integer.valueOf($r2.size()), Integer.valueOf($i0)}));
        }
        runCallbacks($r1, $r2);
        AccessTokenManager.getInstance().extendAccessTokenIfNeeded();
        return $r2;
    }

    public static GraphRequestAsyncTask executeConnectionAsync(HttpURLConnection $r0, GraphRequestBatch $r1) throws  {
        return executeConnectionAsync(null, $r0, $r1);
    }

    public static GraphRequestAsyncTask executeConnectionAsync(Handler $r0, HttpURLConnection $r1, GraphRequestBatch $r2) throws  {
        Validate.notNull($r1, "connection");
        GraphRequestAsyncTask $r3 = new GraphRequestAsyncTask($r1, $r2);
        $r2.setCallbackHandler($r0);
        $r3.executeOnSettingsExecutor();
        return $r3;
    }

    public String toString() throws  {
        return "{Request: " + " accessToken: " + (this.accessToken == null ? "null" : this.accessToken) + ", graphPath: " + this.graphPath + ", graphObject: " + this.graphObject + ", httpMethod: " + this.httpMethod + ", parameters: " + this.parameters + "}";
    }

    static void runCallbacks(@Signature({"(", "Lcom/facebook/GraphRequestBatch;", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;)V"}) final GraphRequestBatch $r0, @Signature({"(", "Lcom/facebook/GraphRequestBatch;", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;)V"}) List<GraphResponse> $r1) throws  {
        int $i0 = $r0.size();
        final ArrayList $r2 = new ArrayList();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            GraphRequest $r5 = $r0.get($i1);
            if ($r5.callback != null) {
                $r2.add(new Pair($r5.callback, $r1.get($i1)));
            }
        }
        if ($r2.size() > 0) {
            C04895 $r3 = new Runnable() {
                public void run() throws  {
                    Iterator $r2 = $r2.iterator();
                    while ($r2.hasNext()) {
                        Pair $r4 = (Pair) $r2.next();
                        ((Callback) $r4.first).onCompleted((GraphResponse) $r4.second);
                    }
                    for (com.facebook.GraphRequestBatch.Callback onBatchCompleted : $r0.getCallbacks()) {
                        onBatchCompleted.onBatchCompleted($r0);
                    }
                }
            };
            Handler $r8 = $r0.getCallbackHandler();
            if ($r8 == null) {
                $r3.run();
            } else {
                $r8.post($r3);
            }
        }
    }

    private static HttpURLConnection createConnection(URL $r0) throws IOException {
        HttpURLConnection $r2 = (HttpURLConnection) $r0.openConnection();
        $r2.setRequestProperty(USER_AGENT_HEADER, getUserAgent());
        $r2.setRequestProperty(ACCEPT_LANGUAGE_HEADER, Locale.getDefault().toString());
        $r2.setChunkedStreamingMode(0);
        return $r2;
    }

    private void addCommonParameters() throws  {
        String $r3;
        if (this.accessToken != null) {
            if (!this.parameters.containsKey("access_token")) {
                $r3 = this.accessToken.getToken();
                Logger.registerAccessToken($r3);
                this.parameters.putString("access_token", $r3);
            }
        } else if (!(this.skipClientToken || this.parameters.containsKey("access_token"))) {
            $r3 = FacebookSdk.getApplicationId();
            String $r5 = FacebookSdk.getClientToken();
            if (Utility.isNullOrEmpty($r3) || Utility.isNullOrEmpty($r5)) {
                Log.d(TAG, "Warning: Request without access token missing application ID or client token.");
            } else {
                this.parameters.putString("access_token", $r3 + "|" + $r5);
            }
        }
        this.parameters.putString("sdk", SDK_ANDROID);
        this.parameters.putString(FORMAT_PARAM, FORMAT_JSON);
        if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_INFO)) {
            this.parameters.putString(DEBUG_PARAM, DEBUG_SEVERITY_INFO);
        } else if (FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.GRAPH_API_DEBUG_WARNING)) {
            this.parameters.putString(DEBUG_PARAM, DEBUG_SEVERITY_WARNING);
        }
    }

    private String appendParametersToBaseUrl(String $r1) throws  {
        Builder $r2 = new Builder().encodedPath($r1);
        for (String $r12 : this.parameters.keySet()) {
            Object $r6 = this.parameters.get($r12);
            Object $r7 = $r6;
            if ($r6 == null) {
                $r7 = "";
            }
            if (isSupportedParameterType($r7)) {
                $r2.appendQueryParameter($r12, parameterToString($r7).toString());
            } else if (this.httpMethod == HttpMethod.GET) {
                throw new IllegalArgumentException(String.format(Locale.US, "Unsupported parameter type for GET request: %s", new Object[]{$r7.getClass().getSimpleName()}));
            }
        }
        return $r2.toString();
    }

    final String getUrlForBatchedRequest() throws  {
        if (this.overriddenURL != null) {
            throw new FacebookException("Can't override URL for a batch request");
        }
        String $r1 = getGraphPathWithVersion();
        addCommonParameters();
        return appendParametersToBaseUrl($r1);
    }

    final String getUrlForSingleRequest() throws  {
        if (this.overriddenURL != null) {
            return this.overriddenURL.toString();
        }
        String $r1;
        if (getHttpMethod() == HttpMethod.POST && this.graphPath != null && this.graphPath.endsWith(VIDEOS_SUFFIX)) {
            $r1 = ServerProtocol.getGraphVideoUrlBase();
        } else {
            $r1 = ServerProtocol.getGraphUrlBase();
        }
        $r1 = String.format("%s/%s", new Object[]{$r1, getGraphPathWithVersion()});
        addCommonParameters();
        return appendParametersToBaseUrl($r1);
    }

    private String getGraphPathWithVersion() throws  {
        if (versionPattern.matcher(this.graphPath).matches()) {
            return this.graphPath;
        }
        return String.format("%s/%s", new Object[]{this.version, this.graphPath});
    }

    private void serializeToBatch(@Signature({"(", "Lorg/json/JSONArray;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/GraphRequest$Attachment;", ">;)V"}) JSONArray $r1, @Signature({"(", "Lorg/json/JSONArray;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/GraphRequest$Attachment;", ">;)V"}) Map<String, Attachment> $r2) throws JSONException, IOException {
        JSONObject $r4 = new JSONObject();
        if (this.batchEntryName != null) {
            $r4.put("name", this.batchEntryName);
            $r4.put(BATCH_ENTRY_OMIT_RESPONSE_ON_SUCCESS_PARAM, this.batchEntryOmitResultOnSuccess);
        }
        if (this.batchEntryDependsOn != null) {
            $r4.put(BATCH_ENTRY_DEPENDS_ON_PARAM, this.batchEntryDependsOn);
        }
        String $r5 = getUrlForBatchedRequest();
        $r4.put(BATCH_RELATIVE_URL_PARAM, $r5);
        $r4.put(BATCH_METHOD_PARAM, this.httpMethod);
        if (this.accessToken != null) {
            Logger.registerAccessToken(this.accessToken.getToken());
        }
        ArrayList $r3 = new ArrayList();
        for (String $r8 : this.parameters.keySet()) {
            String $r82;
            Object $r12 = this.parameters.get($r82);
            if (isSupportedAttachmentType($r12)) {
                $r82 = String.format(Locale.ROOT, "%s%d", new Object[]{ATTACHMENT_FILENAME_PREFIX, Integer.valueOf($r2.size())});
                $r3.add($r82);
                $r2.put($r82, new Attachment(this, $r12));
            }
        }
        if (!$r3.isEmpty()) {
            $r4.put(ATTACHED_FILES_PARAM, TextUtils.join(",", $r3));
        }
        if (this.graphObject != null) {
            $r3 = new ArrayList();
            processGraphObject(this.graphObject, $r5, new KeyValueSerializer() {
                public void writeString(String $r1, String $r2) throws IOException {
                    $r3.add(String.format(Locale.US, "%s=%s", new Object[]{$r1, URLEncoder.encode($r2, "UTF-8")}));
                }
            });
            $r4.put(BATCH_BODY_PARAM, TextUtils.join("&", $r3));
        }
        $r1.put($r4);
    }

    private static boolean hasOnProgressCallbacks(GraphRequestBatch $r0) throws  {
        for (com.facebook.GraphRequestBatch.Callback callback : $r0.getCallbacks()) {
            if (callback instanceof com.facebook.GraphRequestBatch.OnProgressCallback) {
                return true;
            }
        }
        Iterator $r2 = $r0.iterator();
        while ($r2.hasNext()) {
            if (((GraphRequest) $r2.next()).getCallback() instanceof OnProgressCallback) {
                return true;
            }
        }
        return false;
    }

    private static void setConnectionContentType(HttpURLConnection $r0, boolean $z0) throws  {
        if ($z0) {
            $r0.setRequestProperty(CONTENT_TYPE_HEADER, "application/x-www-form-urlencoded");
            $r0.setRequestProperty(CONTENT_ENCODING_HEADER, "gzip");
            return;
        }
        $r0.setRequestProperty(CONTENT_TYPE_HEADER, getMimeContentType());
    }

    private static boolean isGzipCompressible(GraphRequestBatch $r0) throws  {
        Iterator $r1 = $r0.iterator();
        while ($r1.hasNext()) {
            GraphRequest $r3 = (GraphRequest) $r1.next();
            for (String $r7 : $r3.parameters.keySet()) {
                if (isSupportedAttachmentType($r3.parameters.get($r7))) {
                    return false;
                }
            }
        }
        return true;
    }

    static final boolean shouldWarnOnMissingFieldsParam(GraphRequest $r0) throws  {
        boolean $z0 = false;
        String $r1 = $r0.getVersion();
        String $r2 = $r1;
        if (Utility.isNullOrEmpty($r1)) {
            return true;
        }
        if ($r1.startsWith("v")) {
            $r2 = $r1.substring(1);
        }
        String[] $r3 = $r2.split("\\.");
        if (($r3.length >= 2 && Integer.parseInt($r3[0]) > 2) || (Integer.parseInt($r3[0]) >= 2 && Integer.parseInt($r3[1]) >= 4)) {
            $z0 = true;
        }
        return $z0;
    }

    static final void validateFieldsParamForGetRequests(GraphRequestBatch $r0) throws  {
        Iterator $r1 = $r0.iterator();
        while ($r1.hasNext()) {
            GraphRequest $r3 = (GraphRequest) $r1.next();
            if (HttpMethod.GET.equals($r3.getHttpMethod()) && shouldWarnOnMissingFieldsParam($r3)) {
                Bundle $r6 = $r3.getParameters();
                if (!$r6.containsKey(FIELDS_PARAM) || Utility.isNullOrEmpty($r6.getString(FIELDS_PARAM))) {
                    Logger.log(LoggingBehavior.DEVELOPER_ERRORS, 5, "Request", "starting with Graph API v2.4, GET requests for /%s should contain an explicit \"fields\" parameter.", $r3.getGraphPath());
                }
            }
        }
    }

    static final void serializeToUrlConnection(GraphRequestBatch $r0, HttpURLConnection $r1) throws IOException, JSONException {
        Throwable $r16;
        Logger $r2 = r17;
        Logger r17 = new Logger(LoggingBehavior.REQUESTS, "Request");
        int $i0 = $r0.size();
        boolean $z0 = isGzipCompressible($r0);
        HttpMethod $r5 = $i0 == 1 ? $r0.get(0).httpMethod : HttpMethod.POST;
        $r1.setRequestMethod($r5.name());
        setConnectionContentType($r1, $z0);
        URL $r7 = $r1.getURL();
        $r2.append("Request:\n");
        $r2.appendKeyValue("Id", $r0.getId());
        $r2.appendKeyValue("URL", $r7);
        $r2.appendKeyValue("Method", $r1.getRequestMethod());
        $r2.appendKeyValue(USER_AGENT_HEADER, $r1.getRequestProperty(USER_AGENT_HEADER));
        $r2.appendKeyValue(CONTENT_TYPE_HEADER, $r1.getRequestProperty(CONTENT_TYPE_HEADER));
        $r1.setConnectTimeout($r0.getTimeout());
        $r1.setReadTimeout($r0.getTimeout());
        if (($r5 == HttpMethod.POST ? 1 : null) == null) {
            $r2.log();
            return;
        }
        $r1.setDoOutput(true);
        OutputStream $r9 = null;
        try {
            OutputStream $r10 = r0;
            OutputStream bufferedOutputStream = new BufferedOutputStream($r1.getOutputStream());
            if ($z0) {
                try {
                    $r10 = new GZIPOutputStream($r10);
                } catch (Throwable th) {
                    $r16 = th;
                    $r9 = $r10;
                    if ($r9 != null) {
                        $r9.close();
                    }
                    throw $r16;
                }
            }
            if (hasOnProgressCallbacks($r0)) {
                OutputStream $r13 = bufferedOutputStream;
                bufferedOutputStream = new ProgressNoopOutputStream($r0.getCallbackHandler());
                processRequest($r0, null, $i0, $r7, $r13, $z0);
                $r9 = bufferedOutputStream;
                bufferedOutputStream = new ProgressOutputStream($r10, $r0, $r13.getProgressMap(), (long) $r13.getMaxProgress());
            } else {
                $r9 = $r10;
            }
            processRequest($r0, $r2, $i0, $r7, $r9, $z0);
            if ($r9 != null) {
                $r9.close();
            }
            $r2.log();
        } catch (Throwable th2) {
            $r16 = th2;
            if ($r9 != null) {
                $r9.close();
            }
            throw $r16;
        }
    }

    private static void processRequest(GraphRequestBatch $r0, Logger $r1, int $i0, URL $r2, OutputStream $r3, boolean $z0) throws IOException, JSONException {
        Serializer $r4 = new Serializer($r3, $r1, $z0);
        HashMap $r6;
        String $r11;
        if ($i0 == 1) {
            GraphRequest $r5 = $r0.get(0);
            $r6 = new HashMap();
            for (String $r112 : $r5.parameters.keySet()) {
                Object $r10 = $r5.parameters.get($r112);
                if (isSupportedAttachmentType($r10)) {
                    $r6.put($r112, new Attachment($r5, $r10));
                }
            }
            if ($r1 != null) {
                $r1.append("  Parameters:\n");
            }
            serializeParameters($r5.parameters, $r4, $r5);
            if ($r1 != null) {
                $r1.append("  Attachments:\n");
            }
            serializeAttachments($r6, $r4);
            if ($r5.graphObject != null) {
                processGraphObject($r5.graphObject, $r2.getPath(), $r4);
                return;
            }
            return;
        }
        $r112 = getBatchAppId($r0);
        if (Utility.isNullOrEmpty($r112)) {
            throw new FacebookException("App ID was not specified at the request or Settings.");
        }
        $r4.writeString(BATCH_APP_ID_PARAM, $r112);
        $r6 = new HashMap();
        serializeRequestsAsJSON($r4, $r0, $r6);
        if ($r1 != null) {
            $r1.append("  Attachments:\n");
        }
        serializeAttachments($r6, $r4);
    }

    private static boolean isMeRequest(String $r0) throws  {
        Matcher $r2 = versionPattern.matcher($r0);
        if ($r2.matches()) {
            $r0 = $r2.group(1);
        }
        if ($r0.startsWith("me/")) {
            return true;
        }
        return $r0.startsWith("/me/");
    }

    private static void processGraphObject(JSONObject $r0, String $r1, KeyValueSerializer $r2) throws IOException {
        boolean $z0 = false;
        if (isMeRequest($r1)) {
            int $i0 = $r1.indexOf(":");
            int $i1 = $r1.indexOf("?");
            if ($i0 <= 3 || ($i1 != -1 && $i0 >= $i1)) {
                $z0 = false;
            } else {
                $z0 = true;
            }
        }
        Iterator $r3 = $r0.keys();
        while ($r3.hasNext()) {
            boolean $z1;
            $r1 = (String) $r3.next();
            Object $r4 = $r0.opt($r1);
            if ($z0 && $r1.equalsIgnoreCase("image")) {
                $z1 = true;
            } else {
                $z1 = false;
            }
            processGraphObjectProperty($r1, $r4, $r2, $z1);
        }
    }

    private static void processGraphObjectProperty(String $r0, Object $r1, KeyValueSerializer $r2, boolean $z0) throws IOException {
        Class $r4 = $r1.getClass();
        if (JSONObject.class.isAssignableFrom($r4)) {
            JSONObject $r6 = (JSONObject) $r1;
            if ($z0) {
                Iterator $r7 = $r6.keys();
                while ($r7.hasNext()) {
                    Object[] $r9 = new Object[]{$r0, (String) $r7.next()};
                    processGraphObjectProperty(String.format("%s[%s]", $r9), $r6.opt((String) $r7.next()), $r2, $z0);
                }
            } else if ($r6.has("id")) {
                processGraphObjectProperty($r0, $r6.optString("id"), $r2, $z0);
            } else if ($r6.has("url")) {
                processGraphObjectProperty($r0, $r6.optString("url"), $r2, $z0);
            } else if ($r6.has(NativeProtocol.OPEN_GRAPH_CREATE_OBJECT_KEY)) {
                processGraphObjectProperty($r0, $r6.toString(), $r2, $z0);
            }
        } else if (JSONArray.class.isAssignableFrom($r4)) {
            JSONArray $r11 = (JSONArray) $r1;
            int $i0 = $r11.length();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                Locale locale = Locale.ROOT;
                processGraphObjectProperty(String.format(locale, "%s[%d]", new Object[]{$r0, Integer.valueOf($i1)}), $r11.opt($i1), $r2, $z0);
            }
        } else if (String.class.isAssignableFrom($r4) || Number.class.isAssignableFrom($r4) || Boolean.class.isAssignableFrom($r4)) {
            $r2.writeString($r0, $r1.toString());
        } else if (Date.class.isAssignableFrom($r4)) {
            $r2.writeString($r0, new SimpleDateFormat(ISO_8601_FORMAT_STRING, Locale.US).format((Date) $r1));
        }
    }

    private static void serializeParameters(Bundle $r0, Serializer $r1, GraphRequest $r2) throws IOException {
        for (String $r6 : $r0.keySet()) {
            Object $r5 = $r0.get($r6);
            if (isSupportedParameterType($r5)) {
                $r1.writeObject($r6, $r5, $r2);
            }
        }
    }

    private static void serializeAttachments(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/GraphRequest$Attachment;", ">;", "Lcom/facebook/GraphRequest$Serializer;", ")V"}) Map<String, Attachment> $r0, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/GraphRequest$Attachment;", ">;", "Lcom/facebook/GraphRequest$Serializer;", ")V"}) Serializer $r1) throws IOException {
        for (String $r5 : $r0.keySet()) {
            Attachment $r6 = (Attachment) $r0.get($r5);
            if (isSupportedAttachmentType($r6.getValue())) {
                $r1.writeObject($r5, $r6.getValue(), $r6.getRequest());
            }
        }
    }

    private static void serializeRequestsAsJSON(@Signature({"(", "Lcom/facebook/GraphRequest$Serializer;", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/GraphRequest$Attachment;", ">;)V"}) Serializer $r0, @Signature({"(", "Lcom/facebook/GraphRequest$Serializer;", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/GraphRequest$Attachment;", ">;)V"}) Collection<GraphRequest> $r1, @Signature({"(", "Lcom/facebook/GraphRequest$Serializer;", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/facebook/GraphRequest$Attachment;", ">;)V"}) Map<String, Attachment> $r2) throws JSONException, IOException {
        JSONArray $r3 = new JSONArray();
        for (GraphRequest serializeToBatch : $r1) {
            serializeToBatch.serializeToBatch($r3, $r2);
        }
        $r0.writeRequestsAsJson(BATCH_PARAM, $r3, $r1);
    }

    private static String getMimeContentType() throws  {
        return String.format("multipart/form-data; boundary=%s", new Object[]{MIME_BOUNDARY});
    }

    private static String getUserAgent() throws  {
        if (userAgent == null) {
            userAgent = String.format("%s.%s", new Object[]{USER_AGENT_BASE, FacebookSdkVersion.BUILD});
            if (!Utility.isNullOrEmpty(InternalSettings.getCustomUserAgent())) {
                userAgent = String.format(Locale.ROOT, "%s/%s", new Object[]{userAgent, $r0});
            }
        }
        return userAgent;
    }

    private static String getBatchAppId(GraphRequestBatch $r0) throws  {
        if (!Utility.isNullOrEmpty($r0.getBatchApplicationId())) {
            return $r0.getBatchApplicationId();
        }
        Iterator $r3 = $r0.iterator();
        while ($r3.hasNext()) {
            AccessToken $r1 = ((GraphRequest) $r3.next()).accessToken;
            if ($r1 != null) {
                String $r2 = $r1.getApplicationId();
                if ($r2 != null) {
                    return $r2;
                }
            }
        }
        if (Utility.isNullOrEmpty(defaultBatchApplicationId)) {
            return FacebookSdk.getApplicationId();
        }
        return defaultBatchApplicationId;
    }

    private static boolean isSupportedAttachmentType(Object $r0) throws  {
        return ($r0 instanceof Bitmap) || ($r0 instanceof byte[]) || ($r0 instanceof Uri) || ($r0 instanceof ParcelFileDescriptor) || ($r0 instanceof ParcelableResourceWithMimeType);
    }

    private static boolean isSupportedParameterType(Object $r0) throws  {
        return ($r0 instanceof String) || ($r0 instanceof Boolean) || ($r0 instanceof Number) || ($r0 instanceof Date);
    }

    private static String parameterToString(Object $r1) throws  {
        if ($r1 instanceof String) {
            return (String) $r1;
        }
        if (($r1 instanceof Boolean) || ($r1 instanceof Number)) {
            return $r1.toString();
        }
        if ($r1 instanceof Date) {
            return new SimpleDateFormat(ISO_8601_FORMAT_STRING, Locale.US).format($r1);
        }
        throw new IllegalArgumentException("Unsupported parameter type.");
    }
}
