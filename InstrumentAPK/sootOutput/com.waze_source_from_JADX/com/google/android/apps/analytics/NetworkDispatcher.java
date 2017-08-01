package com.google.android.apps.analytics;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.google.android.apps.analytics.Dispatcher.Callbacks;
import com.waze.strings.DisplayStrings;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHttpRequest;

/* compiled from: dalvik_source_com.waze.apk */
class NetworkDispatcher implements Dispatcher {
    private static final HttpHost GOOGLE_ANALYTICS_HOST = new HttpHost("www.google-analytics.com", 80);
    private static final int MAX_EVENTS_PER_PIPELINE = 30;
    private static final int MAX_SEQUENTIAL_REQUESTS = 5;
    private static final long MIN_RETRY_INTERVAL = 2;
    private static final String USER_AGENT_TEMPLATE = "%s/%s (Linux; U; Android %s; %s-%s; %s Build/%s)";
    private DispatcherThread dispatcherThread;
    private boolean dryRun;
    private final String userAgent;

    /* compiled from: dalvik_source_com.waze.apk */
    private static class DispatcherThread extends HandlerThread {
        private final Callbacks callbacks;
        private AsyncDispatchTask currentTask;
        private Handler handlerExecuteOnDispatcherThread;
        private int lastStatusCode;
        private int maxEventsPerRequest;
        private final NetworkDispatcher parent;
        private final PipelinedRequester pipelinedRequester;
        private final String referrer;
        private final RequesterCallbacks requesterCallBacks;
        private long retryInterval;
        private final String userAgent;

        /* compiled from: dalvik_source_com.waze.apk */
        private class AsyncDispatchTask implements Runnable {
            private final LinkedList<Event> events = new LinkedList();

            public AsyncDispatchTask(Event[] $r2) throws  {
                Collections.addAll(this.events, $r2);
            }

            private void dispatchSomePendingEvents(boolean $z0) throws IOException, ParseException, HttpException {
                if (GoogleAnalyticsTracker.getInstance().getDebug() && $z0) {
                    Log.v(GoogleAnalyticsTracker.LOG_TAG, "dispatching events in dry run mode");
                }
                int $i0 = 0;
                while ($i0 < this.events.size() && $i0 < DispatcherThread.this.maxEventsPerRequest) {
                    Event $r5 = (Event) this.events.get($i0);
                    String $r6 = "__##GOOGLEPAGEVIEW##__".equals($r5.category) ? NetworkRequestUtil.constructPageviewRequestPath($r5, DispatcherThread.this.referrer) : "__##GOOGLETRANSACTION##__".equals($r5.category) ? NetworkRequestUtil.constructTransactionRequestPath($r5, DispatcherThread.this.referrer) : "__##GOOGLEITEM##__".equals($r5.category) ? NetworkRequestUtil.constructItemRequestPath($r5, DispatcherThread.this.referrer) : NetworkRequestUtil.constructEventRequestPath($r5, DispatcherThread.this.referrer);
                    BasicHttpRequest $r8 = new BasicHttpRequest("GET", $r6);
                    $r8.addHeader("Host", NetworkDispatcher.GOOGLE_ANALYTICS_HOST.getHostName());
                    $r8.addHeader("User-Agent", DispatcherThread.this.userAgent);
                    if (GoogleAnalyticsTracker.getInstance().getDebug()) {
                        Log.i(GoogleAnalyticsTracker.LOG_TAG, $r8.getRequestLine().toString());
                    }
                    if ($z0) {
                        DispatcherThread.this.requesterCallBacks.requestSent();
                    } else {
                        DispatcherThread.this.pipelinedRequester.addRequest($r8);
                    }
                    $i0++;
                }
                if (!$z0) {
                    DispatcherThread.this.pipelinedRequester.sendRequests();
                }
            }

            public Event removeNextEvent() throws  {
                return (Event) this.events.poll();
            }

            public void run() throws  {
                DispatcherThread.this.currentTask = this;
                int $i0 = 0;
                while ($i0 < 5 && this.events.size() > 0) {
                    long $l2 = 0;
                    try {
                        if (DispatcherThread.this.lastStatusCode == DisplayStrings.DS_NO_NETWORK_CONNECTION__UNABLE_TO_REPORT || DispatcherThread.this.lastStatusCode == DisplayStrings.DS_NOTEC) {
                            $l2 = (long) (Math.random() * ((double) DispatcherThread.this.retryInterval));
                            if (DispatcherThread.this.retryInterval < 256) {
                                DispatcherThread.access$630(DispatcherThread.this, 2);
                            }
                        } else {
                            DispatcherThread.this.retryInterval = 2;
                        }
                        Thread.sleep($l2 * 1000);
                        dispatchSomePendingEvents(DispatcherThread.this.parent.isDryRun());
                        $i0++;
                    } catch (Throwable $r4) {
                        Log.w(GoogleAnalyticsTracker.LOG_TAG, "Couldn't sleep.", $r4);
                    } catch (Throwable $r7) {
                        Log.w(GoogleAnalyticsTracker.LOG_TAG, "Problem with socket or streams.", $r7);
                    } catch (HttpException $r8) {
                        Log.w(GoogleAnalyticsTracker.LOG_TAG, "Problem with http streams.", $r8);
                    }
                }
                DispatcherThread.this.pipelinedRequester.finishedCurrentRequests();
                DispatcherThread.this.callbacks.dispatchFinished();
                DispatcherThread.this.currentTask = null;
            }
        }

        /* compiled from: dalvik_source_com.waze.apk */
        private class RequesterCallbacks implements Callbacks {
            private RequesterCallbacks() throws  {
            }

            public void pipelineModeChanged(boolean $z0) throws  {
                if ($z0) {
                    DispatcherThread.this.maxEventsPerRequest = 30;
                } else {
                    DispatcherThread.this.maxEventsPerRequest = 1;
                }
            }

            public void requestSent() throws  {
                if (DispatcherThread.this.currentTask != null) {
                    Event $r3 = DispatcherThread.this.currentTask.removeNextEvent();
                    if ($r3 != null) {
                        DispatcherThread.this.callbacks.eventDispatched($r3.eventId);
                    }
                }
            }

            public void serverError(int $i0) throws  {
                DispatcherThread.this.lastStatusCode = $i0;
            }
        }

        private DispatcherThread(Callbacks $r1, PipelinedRequester $r2, String $r3, String $r4, NetworkDispatcher $r5) throws  {
            super("DispatcherThread");
            this.maxEventsPerRequest = 30;
            this.currentTask = null;
            this.callbacks = $r1;
            this.referrer = $r3;
            this.userAgent = $r4;
            this.pipelinedRequester = $r2;
            this.requesterCallBacks = new RequesterCallbacks();
            this.pipelinedRequester.installCallbacks(this.requesterCallBacks);
            this.parent = $r5;
        }

        private DispatcherThread(Callbacks $r1, String $r2, String $r3, NetworkDispatcher $r4) throws  {
            this($r1, new PipelinedRequester(NetworkDispatcher.GOOGLE_ANALYTICS_HOST), $r2, $r3, $r4);
        }

        static /* synthetic */ long access$630(DispatcherThread $r0, long $l0) throws  {
            $l0 = $r0.retryInterval * $l0;
            $r0.retryInterval = $l0;
            return $l0;
        }

        public void dispatchEvents(Event[] $r1) throws  {
            if (this.handlerExecuteOnDispatcherThread != null) {
                this.handlerExecuteOnDispatcherThread.post(new AsyncDispatchTask($r1));
            }
        }

        protected void onLooperPrepared() throws  {
            this.handlerExecuteOnDispatcherThread = new Handler();
        }
    }

    public NetworkDispatcher() throws  {
        this(GoogleAnalyticsTracker.PRODUCT, GoogleAnalyticsTracker.VERSION);
    }

    public NetworkDispatcher(String $r1, String $r2) throws  {
        this.dryRun = false;
        Locale $r4 = Locale.getDefault();
        Object[] $r3 = new Object[7];
        $r3[0] = $r1;
        $r3[1] = $r2;
        $r3[2] = VERSION.RELEASE;
        $r3[3] = $r4.getLanguage() != null ? $r4.getLanguage().toLowerCase() : "en";
        $r3[4] = $r4.getCountry() != null ? $r4.getCountry().toLowerCase() : "";
        $r3[5] = Build.MODEL;
        $r3[6] = Build.ID;
        this.userAgent = String.format(USER_AGENT_TEMPLATE, $r3);
    }

    public void dispatchEvents(Event[] $r1) throws  {
        if (this.dispatcherThread != null) {
            this.dispatcherThread.dispatchEvents($r1);
        }
    }

    String getUserAgent() throws  {
        return this.userAgent;
    }

    public void init(Callbacks $r1, PipelinedRequester $r2, String $r3) throws  {
        stop();
        this.dispatcherThread = new DispatcherThread($r1, $r2, $r3, this.userAgent, this);
        this.dispatcherThread.start();
    }

    public void init(Callbacks $r1, String $r2) throws  {
        stop();
        this.dispatcherThread = new DispatcherThread($r1, $r2, this.userAgent, this);
        this.dispatcherThread.start();
    }

    public boolean isDryRun() throws  {
        return this.dryRun;
    }

    public void setDryRun(boolean $z0) throws  {
        this.dryRun = $z0;
    }

    public void stop() throws  {
        if (this.dispatcherThread != null && this.dispatcherThread.getLooper() != null) {
            this.dispatcherThread.getLooper().quit();
            this.dispatcherThread = null;
        }
    }

    public void waitForThreadLooper() throws  {
        this.dispatcherThread.getLooper();
    }
}
