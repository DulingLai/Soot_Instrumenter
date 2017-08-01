package com.waze.utils;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.waze.Logger;

class VolleyManager$1 implements ImageListener {
    final /* synthetic */ VolleyManager this$0;
    final /* synthetic */ String val$analyticsEvent;
    final /* synthetic */ VolleyManager$ImageRequestListener val$listener;
    final /* synthetic */ long val$startTime;
    final /* synthetic */ Object val$token;
    final /* synthetic */ String val$url;

    VolleyManager$1(VolleyManager this$0, VolleyManager$ImageRequestListener volleyManager$ImageRequestListener, String str, Object obj, long j, String str2) {
        this.this$0 = this$0;
        this.val$listener = volleyManager$ImageRequestListener;
        this.val$analyticsEvent = str;
        this.val$token = obj;
        this.val$startTime = j;
        this.val$url = str2;
    }

    public void onResponse(ImageContainer response, boolean isImmediate) {
        if (this.val$listener != null && response.getBitmap() != null) {
            VolleyManager.access$000(this.this$0, this.val$analyticsEvent, response.getBitmap());
            this.val$listener.onImageLoadComplete(response.getBitmap(), this.val$token, System.currentTimeMillis() - this.val$startTime);
        } else if (this.val$listener != null) {
            VolleyManager.access$100(this.this$0, this.val$analyticsEvent);
            Logger.e("Volley manager: Failed loading image, returned bitmap is null; image_url: " + this.val$url);
            this.val$listener.onImageLoadFailed(this.val$token, System.currentTimeMillis() - this.val$startTime);
        }
    }

    public void onErrorResponse(VolleyError error) {
        if (this.val$listener != null) {
            VolleyManager.access$100(this.this$0, this.val$analyticsEvent);
            this.val$listener.onImageLoadFailed(this.val$token, System.currentTimeMillis() - this.val$startTime);
        }
        Logger.e("Volley manager: Failed loading image, error: " + error.getMessage() + "; image_url: " + this.val$url);
    }
}
