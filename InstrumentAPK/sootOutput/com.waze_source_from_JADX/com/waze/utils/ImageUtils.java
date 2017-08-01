package com.waze.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.internal.view.SupportMenu;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.waze.LayoutManager;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUtils {
    public static final int DIRECTION_DOWN = 3;
    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_UP = 2;
    public static final boolean NO_RECYCLE_INPUT = false;
    public static final boolean RECYCLE_INPUT = true;
    private static final String TAG = "Util";
    public static final int UNCONSTRAINED = -1;
    private static OnClickListener sNullOnClickListener;

    static class C29451 implements OnClickListener {
        C29451() {
        }

        public void onClick(View v) {
        }
    }

    public enum DensityProps {
        LDPI("ldpi", FileUploadSession.SEPARATOR, 0.75f),
        MDPI("mdpi", FileUploadSession.SEPARATOR, 1.0f),
        HDPI("hdpi", "HD", 1.5f),
        XHDPI("xhdpi", "2x", LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN),
        XXHDPI("xxhdpi", "3x", 3.0f),
        XXXHDPI("xxhdpi", "3x", 4.0f);
        
        private final String localDir;
        private final float scale;
        private final String serverDir;

        private DensityProps(String localDir, String serverDir, float scale) {
            this.localDir = localDir;
            this.serverDir = serverDir;
            this.scale = scale;
        }

        public String getLocalDrawableSuffix() {
            return this.localDir;
        }

        public String getServerDir() {
            return this.serverDir;
        }

        public float getScale() {
            return this.scale;
        }

        public static DensityProps getEnumByScale(float scale) {
            if (scale < MDPI.getScale()) {
                return LDPI;
            }
            if (scale < HDPI.getScale()) {
                return MDPI;
            }
            if (scale < XHDPI.getScale()) {
                return HDPI;
            }
            if (scale < XXHDPI.getScale()) {
                return XHDPI;
            }
            if (scale < XXXHDPI.getScale()) {
                return XXHDPI;
            }
            return XXXHDPI;
        }
    }

    private ImageUtils() {
    }

    public static Bitmap rotate(Bitmap b, int degrees) {
        if (degrees == 0 || b == null) {
            return b;
        }
        Matrix m = new Matrix();
        m.setRotate((float) degrees, ((float) b.getWidth()) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, ((float) b.getHeight()) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        try {
            Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
            if (b == b2) {
                return b;
            }
            b.recycle();
            return b2;
        } catch (OutOfMemoryError e) {
            return b;
        }
    }

    public static int computeSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        if (initialSize > 8) {
            return ((initialSize + 7) / 8) * 8;
        }
        int roundedSize = 1;
        while (roundedSize < initialSize) {
            roundedSize <<= 1;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
        int lowerBound;
        int upperBound;
        double w = (double) options.outWidth;
        double h = (double) options.outHeight;
        if (maxNumOfPixels == -1) {
            lowerBound = 1;
        } else {
            lowerBound = (int) Math.ceil(Math.sqrt((w * h) / ((double) maxNumOfPixels)));
        }
        if (minSideLength == -1) {
            upperBound = 128;
        } else {
            upperBound = (int) Math.min(Math.floor(w / ((double) minSideLength)), Math.floor(h / ((double) minSideLength)));
        }
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if (maxNumOfPixels == -1 && minSideLength == -1) {
            return 1;
        }
        if (minSideLength != -1) {
            return upperBound;
        }
        return lowerBound;
    }

    public static Bitmap transform(Matrix scaler, Bitmap source, int targetWidth, int targetHeight, boolean scaleUp, boolean recycle) {
        Bitmap b2;
        int deltaX = source.getWidth() - targetWidth;
        int deltaY = source.getHeight() - targetHeight;
        if (scaleUp || (deltaX >= 0 && deltaY >= 0)) {
            Bitmap b1;
            float bitmapWidthF = (float) source.getWidth();
            float bitmapHeightF = (float) source.getHeight();
            float scale;
            if (bitmapWidthF / bitmapHeightF > ((float) targetWidth) / ((float) targetHeight)) {
                scale = ((float) targetHeight) / bitmapHeightF;
                if (scale < 0.9f || scale > 1.0f) {
                    scaler.setScale(scale, scale);
                } else {
                    scaler = null;
                }
            } else {
                scale = ((float) targetWidth) / bitmapWidthF;
                if (scale < 0.9f || scale > 1.0f) {
                    scaler.setScale(scale, scale);
                } else {
                    scaler = null;
                }
            }
            if (scaler != null) {
                b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), scaler, true);
            } else {
                b1 = source;
            }
            if (recycle && b1 != source) {
                source.recycle();
            }
            b2 = Bitmap.createBitmap(b1, Math.max(0, b1.getWidth() - targetWidth) / 2, Math.max(0, b1.getHeight() - targetHeight) / 2, targetWidth, targetHeight);
            if (b2 != b1 && (recycle || b1 != source)) {
                b1.recycle();
            }
        } else {
            b2 = Bitmap.createBitmap(targetWidth, targetHeight, Config.ARGB_8888);
            Canvas c = new Canvas(b2);
            int deltaXHalf = Math.max(0, deltaX / 2);
            int deltaYHalf = Math.max(0, deltaY / 2);
            int i = deltaXHalf;
            int i2 = deltaYHalf;
            Rect rect = new Rect(i, i2, Math.min(targetWidth, source.getWidth()) + deltaXHalf, Math.min(targetHeight, source.getHeight()) + deltaYHalf);
            int dstX = (targetWidth - rect.width()) / 2;
            int dstY = (targetHeight - rect.height()) / 2;
            c.drawBitmap(source, rect, new Rect(dstX, dstY, targetWidth - dstX, targetHeight - dstY), null);
            if (recycle) {
                source.recycle();
            }
        }
        return b2;
    }

    public static <T> int indexOf(T[] array, T s) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }

    public static void closeSilently(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Throwable th) {
            }
        }
    }

    public static void closeSilently(ParcelFileDescriptor c) {
        if (c != null) {
            try {
                c.close();
            } catch (Throwable th) {
            }
        }
    }

    public static Bitmap makeBitmap(int minSideLength, int maxNumOfPixels, Uri uri, ContentResolver cr, boolean useNative) {
        Bitmap makeBitmap;
        Closeable input = null;
        try {
            input = cr.openInputStream(uri);
            Options options = null;
            if (useNative) {
                options = createNativeAllocOptions();
            }
            makeBitmap = makeBitmap(minSideLength, maxNumOfPixels, uri, cr, input, options);
        } catch (IOException e) {
            makeBitmap = null;
        } finally {
            closeSilently(input);
        }
        return makeBitmap;
    }

    public static Bitmap makeBitmap(int minSideLength, int maxNumOfPixels, InputStream pfd, boolean useNative) {
        Options options = null;
        if (useNative) {
            options = createNativeAllocOptions();
        }
        return makeBitmap(minSideLength, maxNumOfPixels, null, null, pfd, options);
    }

    public static Bitmap makeBitmap(int minSideLength, int maxNumOfPixels, Uri uri, ContentResolver cr, InputStream input, Options options) {
        Bitmap bitmap = null;
        if (input == null) {
            try {
                Closeable input2 = makeInputStream(uri, cr);
            } catch (OutOfMemoryError ex) {
                Log.e(TAG, "Got oom exception " + ex);
            } catch (FileNotFoundException ex2) {
                Log.e(TAG, "Got fnf exception " + ex2);
            } finally {
                closeSilently(input2);
            }
        }
        if (input2 == null) {
            closeSilently(input2);
        } else {
            if (options == null) {
                options = new Options();
            }
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input2, null, options);
            if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
                closeSilently(input2);
            } else {
                options.inSampleSize = computeSampleSize(options, minSideLength, maxNumOfPixels);
                options.inJustDecodeBounds = false;
                options.inDither = false;
                options.inPreferredConfig = Config.ARGB_8888;
                closeSilently(input2);
                input2 = cr.openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(input2, null, options);
                closeSilently(input2);
            }
        }
        return bitmap;
    }

    public static Bitmap makeBitmap(int minSideLength, int maxNumOfPixels, String imageUrl, InputStream input, Options options) {
        Bitmap bitmap = null;
        Closeable input2;
        try {
            URL url = new URL(imageUrl);
            if (input == null) {
                input2 = url.openStream();
            }
            if (input2 != null) {
                if (options == null) {
                    options = new Options();
                }
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(input2, null, options);
                if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
                    closeSilently(input2);
                } else {
                    options.inSampleSize = computeSampleSize(options, minSideLength, maxNumOfPixels);
                    options.inJustDecodeBounds = false;
                    options.inDither = false;
                    options.inPreferredConfig = Config.ARGB_8888;
                    closeSilently(input2);
                    input2 = url.openStream();
                    bitmap = BitmapFactory.decodeStream(input2, null, options);
                    closeSilently(input2);
                }
            }
        } catch (OutOfMemoryError ex) {
            Log.e(TAG, "Got oom exception " + ex);
        } catch (FileNotFoundException ex2) {
            Log.e(TAG, "Got fnf exception " + ex2);
        } catch (MalformedURLException ex3) {
            Log.e(TAG, "Got bad url exception " + ex3);
        } catch (IOException ex4) {
            Log.e(TAG, "Got io exception " + ex4);
        } finally {
            closeSilently(input2);
        }
        return bitmap;
    }

    private static InputStream makeInputStream(Uri uri, ContentResolver cr) {
        try {
            return cr.openInputStream(uri);
        } catch (IOException e) {
            return null;
        }
    }

    public static synchronized OnClickListener getNullOnClickListener() {
        OnClickListener onClickListener;
        synchronized (ImageUtils.class) {
            if (sNullOnClickListener == null) {
                sNullOnClickListener = new C29451();
            }
            onClickListener = sNullOnClickListener;
        }
        return onClickListener;
    }

    public static void Assert(boolean cond) {
        if (!cond) {
            throw new AssertionError();
        }
    }

    public static boolean equals(String a, String b) {
        return a == b || a.equals(b);
    }

    public static Options createNativeAllocOptions() {
        return new Options();
    }

    public static String getImagePath() {
        return DensityProps.getEnumByScale(DisplayUtils.scale()).getServerDir();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void applyColorFilterOnImage(ImageView imageView, int color) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setScale(((float) Color.red(color)) / 255.0f, ((float) Color.green(color)) / 255.0f, ((float) Color.blue(color)) / 255.0f, 1.0f);
        imageView.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
    }

    public static Bitmap RGB565toARGB888(Bitmap img) {
        int[] pixels = new int[(img.getWidth() * img.getHeight())];
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Config.ARGB_8888);
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(SupportMenu.CATEGORY_MASK);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();
        return output;
    }
}
