package com.google.android.apps.analytics;

import android.util.Log;
import java.io.IOException;
import java.net.Socket;
import org.apache.http.Header;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.params.BasicHttpParams;

/* compiled from: dalvik_source_com.waze.apk */
class PipelinedRequester {
    Callbacks callbacks;
    boolean canPipeline;
    DefaultHttpClientConnection connection;
    HttpHost host;
    int lastStatusCode;
    SocketFactory socketFactory;

    /* compiled from: dalvik_source_com.waze.apk */
    interface Callbacks {
        void pipelineModeChanged(boolean z) throws ;

        void requestSent() throws ;

        void serverError(int i) throws ;
    }

    public PipelinedRequester(HttpHost $r1) throws  {
        this($r1, new PlainSocketFactory());
    }

    public PipelinedRequester(HttpHost $r1, SocketFactory $r2) throws  {
        this.connection = new DefaultHttpClientConnection();
        this.canPipeline = true;
        this.host = $r1;
        this.socketFactory = $r2;
    }

    private void closeConnection() throws  {
        if (this.connection != null && this.connection.isOpen()) {
            try {
                this.connection.close();
            } catch (IOException e) {
            }
        }
    }

    private void maybeOpenConnection() throws IOException {
        if (this.connection == null || !this.connection.isOpen()) {
            BasicHttpParams $r1 = r7;
            BasicHttpParams r7 = new BasicHttpParams();
            Socket $r4 = this.socketFactory.createSocket();
            this.connection.bind(this.socketFactory.connectSocket($r4, this.host.getHostName(), this.host.getPort(), null, 0, $r1), $r1);
        }
    }

    public void addRequest(HttpRequest $r1) throws HttpException, IOException {
        maybeOpenConnection();
        this.connection.sendRequestHeader($r1);
    }

    public void finishedCurrentRequests() throws  {
        closeConnection();
    }

    public void installCallbacks(Callbacks $r1) throws  {
        this.callbacks = $r1;
    }

    public void sendRequests() throws IOException, HttpException {
        this.connection.flush();
        HttpConnectionMetrics $r3 = this.connection.getMetrics();
        while ($r3.getResponseCount() < $r3.getRequestCount()) {
            HttpResponse $r4 = this.connection.receiveResponseHeader();
            if (!$r4.getStatusLine().getProtocolVersion().greaterEquals(HttpVersion.HTTP_1_1)) {
                this.callbacks.pipelineModeChanged(false);
                this.canPipeline = false;
            }
            Header[] $r9 = $r4.getHeaders("Connection");
            if ($r9 != null) {
                for (Header $r10 : $r9) {
                    if ("close".equalsIgnoreCase($r10.getValue())) {
                        this.callbacks.pipelineModeChanged(false);
                        this.canPipeline = false;
                    }
                }
            }
            this.lastStatusCode = $r4.getStatusLine().getStatusCode();
            if (this.lastStatusCode != 200) {
                Callbacks $r8 = this.callbacks;
                int $i3 = this.lastStatusCode;
                $r8.serverError($i3);
                closeConnection();
                return;
            }
            this.connection.receiveResponseEntity($r4);
            $r4.getEntity().consumeContent();
            this.callbacks.requestSent();
            if (GoogleAnalyticsTracker.getInstance().getDebug()) {
                Log.v(GoogleAnalyticsTracker.LOG_TAG, "HTTP Response Code: " + $r4.getStatusLine().getStatusCode());
            }
            if (!this.canPipeline) {
                closeConnection();
                return;
            }
        }
    }
}
