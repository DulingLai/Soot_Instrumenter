package com.waze.view.drawables;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.waze.carpool.CarpoolDrive;
import com.waze.utils.ImageRepository$ImageRepositoryListener;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager$ImageRequestListener;
import java.util.ArrayList;
import java.util.Iterator;

public class MultiContactsBitmap {
    public static final int LINE_WIDTH = 5;
    private static final int SIDE_LENGTH_PX = 512;
    ArrayList<Bitmap> mBitmaps;
    int mNumBitmapsFailed = 0;
    int mNumBitmapsNeeded;
    int mNumBitmapsReceived = 0;
    final ImageRepository$ImageRepositoryListener mOnDone;
    final Bitmap mPlaceholder;
    ArrayList<ImageContainer> requestsSent = new ArrayList();
    Object vToken;

    class C29951 implements VolleyManager$ImageRequestListener {
        C29951() {
        }

        public void onImageLoadComplete(Bitmap bitmap, Object token, long duration) {
            if (MultiContactsBitmap.this.vToken == token) {
                MultiContactsBitmap.this.mBitmaps.add(bitmap);
                MultiContactsBitmap multiContactsBitmap = MultiContactsBitmap.this;
                multiContactsBitmap.mNumBitmapsReceived++;
                MultiContactsBitmap.this.checkIfDone();
            }
        }

        public void onImageLoadFailed(Object token, long duration) {
            MultiContactsBitmap multiContactsBitmap;
            if (MultiContactsBitmap.this.mPlaceholder != null) {
                MultiContactsBitmap.this.mBitmaps.add(MultiContactsBitmap.this.mPlaceholder);
                multiContactsBitmap = MultiContactsBitmap.this;
                multiContactsBitmap.mNumBitmapsReceived++;
            } else {
                multiContactsBitmap = MultiContactsBitmap.this;
                multiContactsBitmap.mNumBitmapsFailed++;
            }
            MultiContactsBitmap.this.checkIfDone();
        }
    }

    public MultiContactsBitmap(ImageRepository$ImageRepositoryListener onDone) {
        this.mOnDone = onDone;
        this.mPlaceholder = null;
    }

    public MultiContactsBitmap(ImageRepository$ImageRepositoryListener onDone, Resources res, int placeHolderResId) {
        this.mOnDone = onDone;
        this.mPlaceholder = BitmapFactory.decodeResource(res, placeHolderResId);
    }

    public void buildBitmap(ArrayList<String> contactsImageUrls) {
        init(contactsImageUrls.size());
        Iterator it = contactsImageUrls.iterator();
        while (it.hasNext()) {
            String url = (String) it.next();
            if (!(url == null || url.isEmpty())) {
                requestImage(url);
            }
        }
    }

    public void buildBitmap(CarpoolDrive drive) {
        init(drive.getRidesAmount());
        for (int i = 0; i < drive.getRidesAmount(); i++) {
            requestImage(drive.getRider(i).getImage());
        }
    }

    private void requestImage(String url) {
        this.requestsSent.add(VolleyManager.getInstance().loadImageFromUrl(url, new C29951(), this.vToken, PixelMeasure.dp(40), PixelMeasure.dp(40)));
    }

    private void init(int size) {
        this.vToken = new Object();
        this.mNumBitmapsNeeded = size;
        this.mNumBitmapsFailed = 0;
        this.mNumBitmapsReceived = 0;
        this.mBitmaps = new ArrayList(this.mNumBitmapsNeeded);
        Iterator it = this.requestsSent.iterator();
        while (it.hasNext()) {
            ((ImageContainer) it.next()).cancelRequest();
        }
        this.requestsSent.clear();
    }

    private void checkIfDone() {
        if (this.mNumBitmapsReceived + this.mNumBitmapsFailed >= this.mNumBitmapsNeeded) {
            if (this.mNumBitmapsReceived == 0) {
                this.mOnDone.onImageRetrieved(null);
            } else if (this.mNumBitmapsReceived == 1) {
                this.mOnDone.onImageRetrieved((Bitmap) this.mBitmaps.get(0));
            } else {
                Paint paint = new Paint();
                paint.setColor(-1);
                paint.setStrokeWidth((float) PixelMeasure.dp(5));
                Bitmap b1;
                Bitmap b2;
                Bitmap res;
                Canvas c;
                int w1;
                int w2;
                if (this.mNumBitmapsReceived == 2) {
                    b1 = (Bitmap) this.mBitmaps.get(0);
                    b2 = (Bitmap) this.mBitmaps.get(1);
                    res = Bitmap.createBitmap(512, 512, Config.ARGB_8888);
                    c = new Canvas(res);
                    w1 = b1.getWidth();
                    c.drawBitmap(b1, new Rect(w1 / 4, 0, w1 - (w1 / 4), b1.getHeight()), new Rect(0, 0, 256, 512), paint);
                    w2 = b2.getWidth();
                    c.drawBitmap(b2, new Rect(w2 / 4, 0, w2 - (w2 / 4), b2.getHeight()), new Rect(256, 0, 512, 512), paint);
                    c.drawLine((float) 256, 0.0f, (float) 256, (float) 512, paint);
                    this.mOnDone.onImageRetrieved(res);
                } else if (this.mNumBitmapsReceived == 3) {
                    b1 = (Bitmap) this.mBitmaps.get(0);
                    b2 = (Bitmap) this.mBitmaps.get(1);
                    b3 = (Bitmap) this.mBitmaps.get(2);
                    res = Bitmap.createBitmap(512, 512, Config.ARGB_8888);
                    c = new Canvas(res);
                    w1 = b1.getWidth();
                    c.drawBitmap(b1, new Rect(w1 / 4, 0, w1 - (w1 / 4), b1.getHeight()), new Rect(0, 0, 256, 512), paint);
                    w2 = b2.getWidth();
                    h2 = b2.getHeight();
                    c.drawBitmap(b2, new Rect(w2 / 4, h2 / 4, w2 - (w2 / 4), h2 - (h2 / 4)), new Rect(256, 0, 512, 256), paint);
                    w3 = b3.getWidth();
                    h3 = b3.getHeight();
                    c.drawBitmap(b3, new Rect(w3 / 4, h3 / 4, w3 - (w3 / 4), h3 - (h3 / 4)), new Rect(256, 256, 512, 512), paint);
                    c.drawLine((float) 256, 0.0f, (float) 256, (float) 512, paint);
                    c.drawLine((float) 256, (float) 256, (float) 512, (float) 256, paint);
                    this.mOnDone.onImageRetrieved(res);
                } else if (this.mNumBitmapsReceived >= 4) {
                    b1 = (Bitmap) this.mBitmaps.get(0);
                    b2 = (Bitmap) this.mBitmaps.get(1);
                    b3 = (Bitmap) this.mBitmaps.get(2);
                    Bitmap b4 = (Bitmap) this.mBitmaps.get(3);
                    res = Bitmap.createBitmap(512, 512, Config.ARGB_8888);
                    c = new Canvas(res);
                    w1 = b1.getWidth();
                    int h1 = b1.getHeight();
                    c.drawBitmap(b1, new Rect(w1 / 4, h1 / 4, w1 - (w1 / 4), h1 - (h1 / 4)), new Rect(0, 0, 256, 256), paint);
                    w2 = b2.getWidth();
                    h2 = b2.getHeight();
                    c.drawBitmap(b2, new Rect(w2 / 4, h2 / 4, w2 - (w2 / 4), h2 - (h2 / 4)), new Rect(256, 0, 512, 256), paint);
                    w3 = b3.getWidth();
                    h3 = b3.getHeight();
                    c.drawBitmap(b3, new Rect(w3 / 4, h3 / 4, w3 - (w3 / 4), h3 - (h3 / 4)), new Rect(256, 256, 512, 512), paint);
                    int w4 = b4.getWidth();
                    int h4 = b4.getHeight();
                    c.drawBitmap(b4, new Rect(w4 / 4, h4 / 4, w4 - (w4 / 4), h4 - (h4 / 4)), new Rect(0, 256, 256, 512), paint);
                    c.drawLine((float) 256, 0.0f, (float) 256, (float) 512, paint);
                    c.drawLine(0.0f, (float) 256, (float) 512, (float) 256, paint);
                    this.mOnDone.onImageRetrieved(res);
                }
            }
        }
    }
}
