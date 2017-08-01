package com.facebook;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import dalvik.annotation.Signature;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

public class GraphRequestAsyncTask extends AsyncTask<Void, Void, List<GraphResponse>> {
    private static final String TAG = GraphRequestAsyncTask.class.getCanonicalName();
    private static Method executeOnExecutorMethod;
    private final HttpURLConnection connection;
    private Exception exception;
    private final GraphRequestBatch requests;

    static {
        for (Method $r0 : AsyncTask.class.getMethods()) {
            if ("executeOnExecutor".equals($r0.getName())) {
                Class[] $r5 = $r0.getParameterTypes();
                if ($r5.length == 2 && $r5[0] == Executor.class && $r5[1].isArray()) {
                    executeOnExecutorMethod = $r0;
                    return;
                }
            }
        }
    }

    public GraphRequestAsyncTask(GraphRequest... $r1) throws  {
        this(null, new GraphRequestBatch($r1));
    }

    public GraphRequestAsyncTask(@Signature({"(", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;)V"}) Collection<GraphRequest> $r1) throws  {
        this(null, new GraphRequestBatch((Collection) $r1));
    }

    public GraphRequestAsyncTask(GraphRequestBatch $r1) throws  {
        this(null, $r1);
    }

    public GraphRequestAsyncTask(HttpURLConnection $r1, GraphRequest... $r2) throws  {
        this($r1, new GraphRequestBatch($r2));
    }

    public GraphRequestAsyncTask(@Signature({"(", "Ljava/net/HttpURLConnection;", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;)V"}) HttpURLConnection $r1, @Signature({"(", "Ljava/net/HttpURLConnection;", "Ljava/util/Collection", "<", "Lcom/facebook/GraphRequest;", ">;)V"}) Collection<GraphRequest> $r2) throws  {
        this($r1, new GraphRequestBatch((Collection) $r2));
    }

    public GraphRequestAsyncTask(HttpURLConnection $r1, GraphRequestBatch $r2) throws  {
        this.requests = $r2;
        this.connection = $r1;
    }

    protected final Exception getException() throws  {
        return this.exception;
    }

    protected final GraphRequestBatch getRequests() throws  {
        return this.requests;
    }

    public String toString() throws  {
        return "{RequestAsyncTask: " + " connection: " + this.connection + ", requests: " + this.requests + "}";
    }

    protected void onPreExecute() throws  {
        super.onPreExecute();
        if (FacebookSdk.isDebugEnabled()) {
            Log.d(TAG, String.format("execute async task: %s", new Object[]{this}));
        }
        if (this.requests.getCallbackHandler() == null) {
            this.requests.setCallbackHandler(new Handler());
        }
    }

    protected void onPostExecute(@Signature({"(", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;)V"}) List<GraphResponse> $r1) throws  {
        super.onPostExecute($r1);
        if (this.exception != null) {
            Log.d(TAG, String.format("onPostExecute: exception encountered during request: %s", new Object[]{this.exception.getMessage()}));
        }
    }

    protected List<GraphResponse> doInBackground(@Signature({"([", "Ljava/lang/Void;", ")", "Ljava/util/List", "<", "Lcom/facebook/GraphResponse;", ">;"}) Void... params) throws  {
        try {
            if (this.connection == null) {
                return this.requests.executeAndWait();
            }
            return GraphRequest.executeConnectionAndWait(this.connection, this.requests);
        } catch (Exception $r2) {
            this.exception = $r2;
            return null;
        }
    }

    GraphRequestAsyncTask executeOnSettingsExecutor() throws  {
        if (executeOnExecutorMethod != null) {
            Method $r2 = executeOnExecutorMethod;
            Object[] $r3 = new Object[2];
            try {
                $r3[0] = FacebookSdk.getExecutor();
                $r3[1] = null;
                $r2.invoke(this, $r3);
                return this;
            } catch (InvocationTargetException e) {
                return this;
            } catch (IllegalAccessException e2) {
                return this;
            }
        }
        execute(new Void[0]);
        return this;
    }
}
