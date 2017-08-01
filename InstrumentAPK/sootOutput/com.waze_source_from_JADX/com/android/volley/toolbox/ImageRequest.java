package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Priority;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import dalvik.annotation.Signature;

public class ImageRequest extends Request<Bitmap> {
    private static final float IMAGE_BACKOFF_MULT = 2.0f;
    private static final int IMAGE_MAX_RETRIES = 2;
    private static final int IMAGE_TIMEOUT_MS = 1000;
    private static final Object sDecodeLock = new Object();
    private final Config mDecodeConfig;
    private final Listener<Bitmap> mListener;
    private final int mMaxHeight;
    private final int mMaxWidth;

    public ImageRequest(@Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Landroid/graphics/Bitmap;", ">;II", "Landroid/graphics/Bitmap$Config;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Landroid/graphics/Bitmap;", ">;II", "Landroid/graphics/Bitmap$Config;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) Listener<Bitmap> $r2, @Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Landroid/graphics/Bitmap;", ">;II", "Landroid/graphics/Bitmap$Config;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) int $i0, @Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Landroid/graphics/Bitmap;", ">;II", "Landroid/graphics/Bitmap$Config;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) int $i1, @Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Landroid/graphics/Bitmap;", ">;II", "Landroid/graphics/Bitmap$Config;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) Config $r3, @Signature({"(", "Ljava/lang/String;", "Lcom/android/volley/Response$Listener", "<", "Landroid/graphics/Bitmap;", ">;II", "Landroid/graphics/Bitmap$Config;", "Lcom/android/volley/Response$ErrorListener;", ")V"}) ErrorListener $r4) throws  {
        super(0, $r1, $r4);
        setRetryPolicy(new DefaultRetryPolicy(1000, 2, 2.0f));
        this.mListener = $r2;
        this.mDecodeConfig = $r3;
        this.mMaxWidth = $i0;
        this.mMaxHeight = $i1;
    }

    public Priority getPriority() throws  {
        return Priority.LOW;
    }

    private static int getResizedDimension(int $i0, int $i1, int $i4, int $i2) throws  {
        if ($i0 == 0 && $i1 == 0) {
            return $i4;
        }
        if ($i0 == 0) {
            return (int) (((double) $i4) * (((double) $i1) / ((double) $i2)));
        } else if ($i1 == 0) {
            return $i0;
        } else {
            double $d0 = ((double) $i2) / ((double) $i4);
            $i4 = $i0;
            if (((double) $i0) * $d0 > ((double) $i1)) {
                $i4 = (int) (((double) $i1) / $d0);
            }
            return $i4;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected com.android.volley.Response<android.graphics.Bitmap> parseNetworkResponse(@dalvik.annotation.Signature({"(", "Lcom/android/volley/NetworkResponse;", ")", "Lcom/android/volley/Response", "<", "Landroid/graphics/Bitmap;", ">;"}) com.android.volley.NetworkResponse r13) throws  {
        /*
        r12 = this;
        r0 = sDecodeLock;
        monitor-enter(r0);
        r1 = r12.doParse(r13);	 Catch:{ OutOfMemoryError -> 0x0009 }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x002e }
        return r1;
    L_0x0009:
        r2 = move-exception;
        r4 = 2;
        r3 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x002e }
        r5 = r13.data;	 Catch:{ Throwable -> 0x002e }
        r6 = r5.length;	 Catch:{ Throwable -> 0x002e }
        r7 = java.lang.Integer.valueOf(r6);	 Catch:{ Throwable -> 0x002e }
        r4 = 0;
        r3[r4] = r7;	 Catch:{ Throwable -> 0x002e }
        r8 = r12.getUrl();	 Catch:{ Throwable -> 0x002e }
        r4 = 1;
        r3[r4] = r8;	 Catch:{ Throwable -> 0x002e }
        r9 = "Caught OOM for %d byte image, url=%s";
        com.android.volley.VolleyLog.m16e(r9, r3);	 Catch:{ Throwable -> 0x002e }
        r10 = new com.android.volley.ParseError;	 Catch:{ Throwable -> 0x002e }
        r10.<init>(r2);	 Catch:{ Throwable -> 0x002e }
        r1 = com.android.volley.Response.error(r10);	 Catch:{ Throwable -> 0x002e }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x002e }
        return r1;
    L_0x002e:
        r11 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x002e }
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.ImageRequest.parseNetworkResponse(com.android.volley.NetworkResponse):com.android.volley.Response<android.graphics.Bitmap>");
    }

    private Response<Bitmap> doParse(@Signature({"(", "Lcom/android/volley/NetworkResponse;", ")", "Lcom/android/volley/Response", "<", "Landroid/graphics/Bitmap;", ">;"}) NetworkResponse $r1) throws  {
        Bitmap $r6;
        byte[] $r2 = $r1.data;
        Options $r3 = new Options();
        if (this.mMaxWidth == 0 && this.mMaxHeight == 0) {
            $r3.inPreferredConfig = this.mDecodeConfig;
            $r6 = BitmapFactory.decodeByteArray($r2, 0, $r2.length, $r3);
        } else {
            $r3.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray($r2, 0, $r2.length, $r3);
            int $i1 = $r3.outWidth;
            int $i0 = $r3.outHeight;
            int $i2 = getResizedDimension(this.mMaxWidth, this.mMaxHeight, $i1, $i0);
            int $i3 = getResizedDimension(this.mMaxHeight, this.mMaxWidth, $i0, $i1);
            $r3.inJustDecodeBounds = false;
            $r3.inSampleSize = findBestSampleSize($i1, $i0, $i2, $i3);
            Bitmap $r5 = BitmapFactory.decodeByteArray($r2, 0, $r2.length, $r3);
            if ($r5 == null || ($r5.getWidth() <= $i2 && $r5.getHeight() <= $i3)) {
                $r6 = $r5;
            } else {
                $r6 = Bitmap.createScaledBitmap($r5, $i2, $i3, true);
                $r5.recycle();
            }
        }
        if ($r6 == null) {
            return Response.error(new ParseError($r1));
        }
        return Response.success($r6, HttpHeaderParser.parseCacheHeaders($r1));
    }

    protected void deliverResponse(Bitmap $r1) throws  {
        this.mListener.onResponse($r1);
    }

    static int findBestSampleSize(int $i0, int $i1, int $i2, int $i3) throws  {
        float $f0 = 1.0f;
        while (((double) (2.0f * $f0)) <= Math.min(((double) $i0) / ((double) $i2), ((double) $i1) / ((double) $i3))) {
            $f0 *= 2.0f;
        }
        return (int) $f0;
    }
}
