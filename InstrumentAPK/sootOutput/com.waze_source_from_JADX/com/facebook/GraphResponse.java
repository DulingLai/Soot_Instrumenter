package com.facebook;

import com.facebook.internal.Logger;
import com.facebook.internal.Utility;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class GraphResponse {
    private static final String BODY_KEY = "body";
    private static final String CODE_KEY = "code";
    public static final String NON_JSON_RESPONSE_PROPERTY = "FACEBOOK_NON_JSON_RESULT";
    private static final String RESPONSE_LOG_TAG = "Response";
    public static final String SUCCESS_KEY = "success";
    private final HttpURLConnection connection;
    private final FacebookRequestError error;
    private final JSONObject graphObject;
    private final JSONArray graphObjectArray;
    private final String rawResponse;
    private final GraphRequest request;

    public enum PagingDirection {
        NEXT,
        PREVIOUS
    }

    GraphResponse(GraphRequest $r1, HttpURLConnection $r2, String $r3, JSONObject $r4) throws  {
        this($r1, $r2, $r3, $r4, null, null);
    }

    GraphResponse(GraphRequest $r1, HttpURLConnection $r2, String $r3, JSONArray $r4) throws  {
        this($r1, $r2, $r3, null, $r4, null);
    }

    GraphResponse(GraphRequest $r1, HttpURLConnection $r2, FacebookRequestError $r3) throws  {
        this($r1, $r2, null, null, null, $r3);
    }

    GraphResponse(GraphRequest $r1, HttpURLConnection $r2, String $r3, JSONObject $r4, JSONArray $r5, FacebookRequestError $r6) throws  {
        this.request = $r1;
        this.connection = $r2;
        this.rawResponse = $r3;
        this.graphObject = $r4;
        this.graphObjectArray = $r5;
        this.error = $r6;
    }

    public final FacebookRequestError getError() throws  {
        return this.error;
    }

    public final JSONObject getJSONObject() throws  {
        return this.graphObject;
    }

    public final JSONArray getJSONArray() throws  {
        return this.graphObjectArray;
    }

    public final HttpURLConnection getConnection() throws  {
        return this.connection;
    }

    public GraphRequest getRequest() throws  {
        return this.request;
    }

    public String getRawResponse() throws  {
        return this.rawResponse;
    }

    public GraphRequest getRequestForPagedResults(PagingDirection $r1) throws  {
        String $r3 = null;
        if (this.graphObject != null) {
            JSONObject $r4 = this.graphObject.optJSONObject("paging");
            if ($r4 != null) {
                $r3 = $r1 == PagingDirection.NEXT ? $r4.optString("next") : $r4.optString("previous");
            }
        }
        if (Utility.isNullOrEmpty($r3)) {
            return null;
        }
        if ($r3 != null && $r3.equals(this.request.getUrlForSingleRequest())) {
            return null;
        }
        try {
            return new GraphRequest(this.request.getAccessToken(), new URL($r3));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public String toString() throws  {
        int $i0;
        String $r6;
        Locale $r3 = Locale.US;
        Object[] $r2 = new Object[1];
        if (this.connection != null) {
            try {
                $i0 = this.connection.getResponseCode();
            } catch (IOException e) {
                $r6 = "unknown";
            }
        } else {
            $i0 = 200;
        }
        $r2[0] = Integer.valueOf($i0);
        $r6 = String.format($r3, "%d", $r2);
        return "{Response: " + " responseCode: " + $r6 + ", graphObject: " + this.graphObject + ", error: " + this.error + "}";
    }

    static List<GraphResponse> fromHttpConnection(@Signature({"(", "Ljava/net/HttpURLConnection;", "Lcom/facebook/GraphRequestBatch;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) HttpURLConnection $r0, @Signature({"(", "Ljava/net/HttpURLConnection;", "Lcom/facebook/GraphRequestBatch;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) GraphRequestBatch $r1) throws  {
        List $r4;
        InputStream $r3 = null;
        try {
            if ($r0.getResponseCode() >= 400) {
                $r3 = $r0.getErrorStream();
            } else {
                $r3 = $r0.getInputStream();
            }
            $r4 = createResponsesFromStream($r3, $r0, $r1);
            return $r4;
        } catch (FacebookException $r2) {
            Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", $r2);
            $r4 = constructErrorResponses($r1, $r0, $r2);
            return $r4;
        } catch (Throwable $r7) {
            Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", $r7);
            $r4 = constructErrorResponses($r1, $r0, new FacebookException($r7));
            return $r4;
        } catch (Throwable $r8) {
            Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", $r8);
            $r4 = constructErrorResponses($r1, $r0, new FacebookException($r8));
            return $r4;
        } catch (Throwable $r9) {
            Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response <Error>: %s", $r9);
            $r4 = constructErrorResponses($r1, $r0, new FacebookException($r9));
            return $r4;
        } finally {
            Utility.closeQuietly($r3);
        }
    }

    static List<GraphResponse> createResponsesFromStream(@Signature({"(", "Ljava/io/InputStream;", "Ljava/net/HttpURLConnection;", "Lcom/facebook/GraphRequestBatch;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) InputStream $r0, @Signature({"(", "Ljava/io/InputStream;", "Ljava/net/HttpURLConnection;", "Lcom/facebook/GraphRequestBatch;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) HttpURLConnection $r1, @Signature({"(", "Ljava/io/InputStream;", "Ljava/net/HttpURLConnection;", "Lcom/facebook/GraphRequestBatch;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) GraphRequestBatch $r2) throws FacebookException, JSONException, IOException {
        String $r3 = Utility.readStreamToString($r0);
        Logger.log(LoggingBehavior.INCLUDE_RAW_RESPONSES, RESPONSE_LOG_TAG, "Response (raw)\n  Size: %d\n  Response:\n%s\n", Integer.valueOf($r3.length()), $r3);
        return createResponsesFromString($r3, $r1, $r2);
    }

    static List<GraphResponse> createResponsesFromString(@Signature({"(", "Ljava/lang/String;", "Ljava/net/HttpURLConnection;", "Lcom/facebook/GraphRequestBatch;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Ljava/net/HttpURLConnection;", "Lcom/facebook/GraphRequestBatch;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) HttpURLConnection $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/net/HttpURLConnection;", "Lcom/facebook/GraphRequestBatch;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) GraphRequestBatch $r2) throws FacebookException, JSONException, IOException {
        List $r6 = createResponsesFromObject($r1, $r2, new JSONTokener($r0).nextValue());
        Logger.log(LoggingBehavior.REQUESTS, RESPONSE_LOG_TAG, "Response\n  Id: %s\n  Size: %d\n  Responses:\n%s\n", $r2.getId(), Integer.valueOf($r0.length()), $r6);
        return $r6;
    }

    private static List<GraphResponse> createResponsesFromObject(@Signature({"(", "Ljava/net/HttpURLConnection;", "Ljava/util/List", "<", "Lcom/facebook/GraphRequest;", ">;", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) HttpURLConnection $r0, @Signature({"(", "Ljava/net/HttpURLConnection;", "Ljava/util/List", "<", "Lcom/facebook/GraphRequest;", ">;", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) List<GraphRequest> $r1, @Signature({"(", "Ljava/net/HttpURLConnection;", "Ljava/util/List", "<", "Lcom/facebook/GraphRequest;", ">;", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) Object $r6) throws FacebookException, JSONException {
        GraphRequest $r8;
        int $i0 = $r1.size();
        ArrayList $r5 = new ArrayList($i0);
        Object obj = $r6;
        if ($i0 == 1) {
            $r8 = (GraphRequest) $r1.get(0);
            try {
                JSONObject $r3 = new JSONObject();
                $r3.put(BODY_KEY, $r6);
                $r3.put("code", $r0 != null ? $r0.getResponseCode() : 200);
                JSONArray $r9 = new JSONArray();
                $r9.put($r3);
                $r6 = $r9;
            } catch (Exception $r10) {
                $r5.add(new GraphResponse($r8, $r0, new FacebookRequestError($r0, $r10)));
            } catch (Exception $r13) {
                $r5.add(new GraphResponse($r8, $r0, new FacebookRequestError($r0, $r13)));
            }
        }
        if (($r6 instanceof JSONArray) && ((JSONArray) $r6).length() == $i0) {
            $r9 = (JSONArray) $r6;
            for ($i0 = 0; $i0 < $r9.length(); $i0++) {
                $r8 = (GraphRequest) $r1.get($i0);
                try {
                    $r5.add(createResponseFromObject($r8, $r0, $r9.get($i0), obj));
                } catch (Exception $r14) {
                    $r5.add(new GraphResponse($r8, $r0, new FacebookRequestError($r0, $r14)));
                } catch (Exception $r2) {
                    $r5.add(new GraphResponse($r8, $r0, new FacebookRequestError($r0, $r2)));
                }
            }
            return $r5;
        }
        throw new FacebookException("Unexpected number of results");
    }

    private static GraphResponse createResponseFromObject(GraphRequest $r0, HttpURLConnection $r1, Object $r3, Object $r2) throws JSONException {
        if ($r3 instanceof JSONObject) {
            JSONObject $r4 = (JSONObject) $r3;
            FacebookRequestError $r5 = FacebookRequestError.checkResponseAndCreateError($r4, $r2, $r1);
            if ($r5 != null) {
                if ($r5.getErrorCode() == 190 && Utility.isCurrentAccessToken($r0.getAccessToken())) {
                    AccessToken.setCurrentAccessToken(null);
                }
                return new GraphResponse($r0, $r1, $r5);
            }
            $r3 = Utility.getStringPropertyAsJSON($r4, BODY_KEY, NON_JSON_RESPONSE_PROPERTY);
            if ($r3 instanceof JSONObject) {
                return new GraphResponse($r0, $r1, $r3.toString(), (JSONObject) $r3);
            } else if ($r3 instanceof JSONArray) {
                return new GraphResponse($r0, $r1, $r3.toString(), (JSONArray) $r3);
            } else {
                $r3 = JSONObject.NULL;
            }
        }
        if ($r3 == JSONObject.NULL) {
            return new GraphResponse($r0, $r1, $r3.toString(), null);
        }
        throw new FacebookException("Got unexpected object type in response, class: " + $r3.getClass().getSimpleName());
    }

    static List<GraphResponse> constructErrorResponses(@Signature({"(", "Ljava/util/List", "<", "Lcom/facebook/GraphRequest;", ">;", "Ljava/net/HttpURLConnection;", "Lcom/facebook/FacebookException;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) List<GraphRequest> $r0, @Signature({"(", "Ljava/util/List", "<", "Lcom/facebook/GraphRequest;", ">;", "Ljava/net/HttpURLConnection;", "Lcom/facebook/FacebookException;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) HttpURLConnection $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/facebook/GraphRequest;", ">;", "Ljava/net/HttpURLConnection;", "Lcom/facebook/FacebookException;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) FacebookException $r2) throws  {
        int $i0 = $r0.size();
        ArrayList $r4 = new ArrayList($i0);
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r4.add(new GraphResponse((GraphRequest) $r0.get($i1), $r1, new FacebookRequestError($r1, $r2)));
        }
        return $r4;
    }
}
