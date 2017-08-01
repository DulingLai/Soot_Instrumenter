package com.android.volley;

import android.os.Handler;
import dalvik.annotation.Signature;
import java.util.concurrent.Executor;

public class ExecutorDelivery implements ResponseDelivery {
    private final Executor mResponsePoster;

    private class ResponseDeliveryRunnable implements Runnable {
        private final Request mRequest;
        private final Response mResponse;
        private final Runnable mRunnable;

        public ResponseDeliveryRunnable(Request $r2, Response $r3, Runnable $r4) throws  {
            this.mRequest = $r2;
            this.mResponse = $r3;
            this.mRunnable = $r4;
        }

        public void run() throws  {
            if (this.mRequest.isCanceled()) {
                this.mRequest.finish("canceled-at-delivery");
                return;
            }
            if (this.mResponse.isSuccess()) {
                this.mRequest.deliverResponse(this.mResponse.result);
            } else {
                this.mRequest.deliverError(this.mResponse.error);
            }
            if (this.mResponse.intermediate) {
                this.mRequest.addMarker("intermediate-response");
            } else {
                this.mRequest.finish("done");
            }
            if (this.mRunnable != null) {
                this.mRunnable.run();
            }
        }
    }

    public ExecutorDelivery(final Handler $r1) throws  {
        this.mResponsePoster = new Executor() {
            public void execute(Runnable $r1) throws  {
                $r1.post($r1);
            }
        };
    }

    public ExecutorDelivery(Executor $r1) throws  {
        this.mResponsePoster = $r1;
    }

    public void postResponse(@Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/Response", "<*>;)V"}) Request<?> $r1, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/Response", "<*>;)V"}) Response<?> $r2) throws  {
        postResponse($r1, $r2, null);
    }

    public void postResponse(@Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/Response", "<*>;", "Ljava/lang/Runnable;", ")V"}) Request<?> $r1, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/Response", "<*>;", "Ljava/lang/Runnable;", ")V"}) Response<?> $r2, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/Response", "<*>;", "Ljava/lang/Runnable;", ")V"}) Runnable $r3) throws  {
        $r1.markDelivered();
        $r1.addMarker("post-response");
        this.mResponsePoster.execute(new ResponseDeliveryRunnable($r1, $r2, $r3));
    }

    public void postError(@Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/VolleyError;", ")V"}) Request<?> $r1, @Signature({"(", "Lcom/android/volley/Request", "<*>;", "Lcom/android/volley/VolleyError;", ")V"}) VolleyError $r2) throws  {
        $r1.addMarker("post-error");
        this.mResponsePoster.execute(new ResponseDeliveryRunnable($r1, Response.error($r2), null));
    }
}
