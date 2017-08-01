package com.facebook.internal;

import android.graphics.Bitmap;

public class ImageResponse {
    private Bitmap bitmap;
    private Exception error;
    private boolean isCachedRedirect;
    private ImageRequest request;

    ImageResponse(ImageRequest $r1, Exception $r2, boolean $z0, Bitmap $r3) throws  {
        this.request = $r1;
        this.error = $r2;
        this.bitmap = $r3;
        this.isCachedRedirect = $z0;
    }

    public ImageRequest getRequest() throws  {
        return this.request;
    }

    public Exception getError() throws  {
        return this.error;
    }

    public Bitmap getBitmap() throws  {
        return this.bitmap;
    }

    public boolean isCachedRedirect() throws  {
        return this.isCachedRedirect;
    }
}
