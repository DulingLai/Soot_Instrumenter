package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument.Page;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.CancellationSignal.OnCancelListener;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintAttributes.Builder;
import android.print.PrintAttributes.MediaSize;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentAdapter.LayoutResultCallback;
import android.print.PrintDocumentAdapter.WriteResultCallback;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import com.waze.LayoutManager;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class PrintHelperKitkat {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    private static final String LOG_TAG = "PrintHelperKitkat";
    private static final int MAX_PRINT_SIZE = 3500;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    int mColorMode = 2;
    final Context mContext;
    Options mDecodeOptions = null;
    private final Object mLock = new Object();
    int mOrientation = 1;
    int mScaleMode = 2;

    public interface OnPrintFinishCallback {
        void onFinish() throws ;
    }

    PrintHelperKitkat(Context $r1) throws  {
        this.mContext = $r1;
    }

    public void setScaleMode(int $i0) throws  {
        this.mScaleMode = $i0;
    }

    public int getScaleMode() throws  {
        return this.mScaleMode;
    }

    public void setColorMode(int $i0) throws  {
        this.mColorMode = $i0;
    }

    public void setOrientation(int $i0) throws  {
        this.mOrientation = $i0;
    }

    public int getOrientation() throws  {
        return this.mOrientation;
    }

    public int getColorMode() throws  {
        return this.mColorMode;
    }

    public void printBitmap(String $r1, Bitmap $r2, OnPrintFinishCallback $r3) throws  {
        if ($r2 != null) {
            int $i0 = this.mScaleMode;
            PrintManager $r6 = (PrintManager) this.mContext.getSystemService("print");
            MediaSize $r7 = MediaSize.UNKNOWN_PORTRAIT;
            if ($r2.getWidth() > $r2.getHeight()) {
                $r7 = MediaSize.UNKNOWN_LANDSCAPE;
            }
            PrintAttributes $r9 = new Builder().setMediaSize($r7).setColorMode(this.mColorMode).build();
            final String str = $r1;
            final Bitmap bitmap = $r2;
            final int i = $i0;
            final OnPrintFinishCallback onPrintFinishCallback = $r3;
            $r6.print($r1, new PrintDocumentAdapter() {
                private PrintAttributes mAttributes;

                public void onLayout(PrintAttributes $r1, PrintAttributes $r2, CancellationSignal cancellationSignal, LayoutResultCallback $r4, Bundle bundle) throws  {
                    boolean $z0 = true;
                    this.mAttributes = $r2;
                    PrintDocumentInfo $r8 = new PrintDocumentInfo.Builder(str).setContentType(1).setPageCount(1).build();
                    if ($r2.equals($r1)) {
                        $z0 = false;
                    }
                    $r4.onLayoutFinished($r8, $z0);
                }

                public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor $r2, CancellationSignal cancellationSignal, WriteResultCallback $r4) throws  {
                    Bitmap $r20;
                    PrintedPdfDocument $r7 = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, this.mAttributes);
                    Bitmap $r11 = PrintHelperKitkat.this.convertBitmapForColorMode(bitmap, this.mAttributes.getColorMode());
                    try {
                        Page $r12 = $r7.startPage(1);
                        $r12.getCanvas().drawBitmap($r11, PrintHelperKitkat.this.getMatrix($r11.getWidth(), $r11.getHeight(), new RectF($r12.getInfo().getContentRect()), i), null);
                        $r7.finishPage($r12);
                        $r7.writeTo(new FileOutputStream($r2.getFileDescriptor()));
                        pageRanges = new PageRange[1];
                        pageRanges[0] = PageRange.ALL_PAGES;
                        $r4.onWriteFinished(pageRanges);
                    } catch (Throwable $r6) {
                        Log.e(PrintHelperKitkat.LOG_TAG, "Error writing printed content", $r6);
                        $r4.onWriteFailed(null);
                    } catch (Throwable th) {
                        if ($r7 != null) {
                            $r7.close();
                        }
                        if ($r2 != null) {
                            try {
                                $r2.close();
                            } catch (IOException e) {
                            }
                        }
                        $r20 = bitmap;
                        if ($r11 != $r20) {
                            $r11.recycle();
                        }
                    }
                    if ($r7 != null) {
                        $r7.close();
                    }
                    if ($r2 != null) {
                        try {
                            $r2.close();
                        } catch (IOException e2) {
                        }
                    }
                    $r20 = bitmap;
                    if ($r11 != $r20) {
                        $r11.recycle();
                    }
                }

                public void onFinish() throws  {
                    if (onPrintFinishCallback != null) {
                        onPrintFinishCallback.onFinish();
                    }
                }
            }, $r9);
        }
    }

    private Matrix getMatrix(int $i0, int $i1, RectF $r1, int $i2) throws  {
        Matrix $r2 = new Matrix();
        float $f1 = $r1.width() / ((float) $i0);
        if ($i2 == 2) {
            $f1 = Math.max($f1, $r1.height() / ((float) $i1));
        } else {
            $f1 = Math.min($f1, $r1.height() / ((float) $i1));
        }
        $r2.postScale($f1, $f1);
        $r2.postTranslate(($r1.width() - (((float) $i0) * $f1)) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN, ($r1.height() - (((float) $i1) * $f1)) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        return $r2;
    }

    public void printBitmap(String $r1, Uri $r2, OnPrintFinishCallback $r3) throws FileNotFoundException {
        final String str = $r1;
        final Uri uri = $r2;
        final OnPrintFinishCallback onPrintFinishCallback = $r3;
        final int i = this.mScaleMode;
        C01042 c01042 = new PrintDocumentAdapter() {
            private PrintAttributes mAttributes;
            Bitmap mBitmap = null;
            AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;

            public void onLayout(PrintAttributes $r1, PrintAttributes $r2, CancellationSignal $r3, LayoutResultCallback $r4, Bundle bundle) throws  {
                boolean $z0 = true;
                this.mAttributes = $r2;
                if ($r3.isCanceled()) {
                    $r4.onLayoutCancelled();
                } else if (this.mBitmap != null) {
                    PrintDocumentInfo $r9 = new PrintDocumentInfo.Builder(str).setContentType(1).setPageCount(1).build();
                    if ($r2.equals($r1)) {
                        $z0 = false;
                    }
                    $r4.onLayoutFinished($r9, $z0);
                } else {
                    final CancellationSignal cancellationSignal = $r3;
                    final PrintAttributes printAttributes = $r2;
                    final PrintAttributes printAttributes2 = $r1;
                    final LayoutResultCallback layoutResultCallback = $r4;
                    this.mLoadBitmap = new AsyncTask<Uri, Boolean, Bitmap>() {

                        class C01021 implements OnCancelListener {
                            C01021() throws  {
                            }

                            public void onCancel() throws  {
                                C01042.this.cancelLoad();
                                C01031.this.cancel(false);
                            }
                        }

                        protected void onPreExecute() throws  {
                            cancellationSignal.setOnCancelListener(new C01021());
                        }

                        protected Bitmap doInBackground(Uri... uris) throws  {
                            try {
                                return PrintHelperKitkat.this.loadConstrainedBitmap(uri, 3500);
                            } catch (FileNotFoundException e) {
                                return null;
                            }
                        }

                        protected void onPostExecute(Bitmap $r1) throws  {
                            boolean $z0 = true;
                            super.onPostExecute($r1);
                            C01042.this.mBitmap = $r1;
                            if ($r1 != null) {
                                PrintDocumentInfo $r5 = new PrintDocumentInfo.Builder(str).setContentType(1).setPageCount(1).build();
                                if (printAttributes.equals(printAttributes2)) {
                                    $z0 = false;
                                }
                                layoutResultCallback.onLayoutFinished($r5, $z0);
                            } else {
                                layoutResultCallback.onLayoutFailed(null);
                            }
                            C01042.this.mLoadBitmap = null;
                        }

                        protected void onCancelled(Bitmap result) throws  {
                            layoutResultCallback.onLayoutCancelled();
                            C01042.this.mLoadBitmap = null;
                        }
                    }.execute(new Uri[0]);
                }
            }

            private void cancelLoad() throws  {
                synchronized (PrintHelperKitkat.this.mLock) {
                    if (PrintHelperKitkat.this.mDecodeOptions != null) {
                        PrintHelperKitkat.this.mDecodeOptions.requestCancelDecode();
                        PrintHelperKitkat.this.mDecodeOptions = null;
                    }
                }
            }

            public void onFinish() throws  {
                super.onFinish();
                cancelLoad();
                if (this.mLoadBitmap != null) {
                    this.mLoadBitmap.cancel(true);
                }
                if (onPrintFinishCallback != null) {
                    onPrintFinishCallback.onFinish();
                }
                if (this.mBitmap != null) {
                    this.mBitmap.recycle();
                    this.mBitmap = null;
                }
            }

            public void onWrite(PageRange[] pageRanges, ParcelFileDescriptor $r2, CancellationSignal cancellationSignal, WriteResultCallback $r4) throws  {
                PrintedPdfDocument $r7 = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, this.mAttributes);
                Bitmap $r11 = PrintHelperKitkat.this.convertBitmapForColorMode(this.mBitmap, this.mAttributes.getColorMode());
                try {
                    Page $r12 = $r7.startPage(1);
                    RectF $r5 = new RectF($r12.getInfo().getContentRect());
                    $r12.getCanvas().drawBitmap($r11, PrintHelperKitkat.this.getMatrix(this.mBitmap.getWidth(), this.mBitmap.getHeight(), $r5, i), null);
                    $r7.finishPage($r12);
                    $r7.writeTo(new FileOutputStream($r2.getFileDescriptor()));
                    pageRanges = new PageRange[1];
                    pageRanges[0] = PageRange.ALL_PAGES;
                    $r4.onWriteFinished(pageRanges);
                } catch (Throwable $r6) {
                    Log.e(PrintHelperKitkat.LOG_TAG, "Error writing printed content", $r6);
                    $r4.onWriteFailed(null);
                } catch (Throwable th) {
                    if ($r7 != null) {
                        $r7.close();
                    }
                    if ($r2 != null) {
                        try {
                            $r2.close();
                        } catch (IOException e) {
                        }
                    }
                    if ($r11 != this.mBitmap) {
                        $r11.recycle();
                    }
                }
                if ($r7 != null) {
                    $r7.close();
                }
                if ($r2 != null) {
                    try {
                        $r2.close();
                    } catch (IOException e2) {
                    }
                }
                if ($r11 != this.mBitmap) {
                    $r11.recycle();
                }
            }
        };
        PrintManager $r8 = (PrintManager) this.mContext.getSystemService("print");
        Builder $r5 = new Builder();
        $r5.setColorMode(this.mColorMode);
        if (this.mOrientation == 1) {
            $r5.setMediaSize(MediaSize.UNKNOWN_LANDSCAPE);
        } else if (this.mOrientation == 2) {
            $r5.setMediaSize(MediaSize.UNKNOWN_PORTRAIT);
        }
        $r8.print($r1, c01042, $r5.build());
    }

    private Bitmap loadConstrainedBitmap(Uri $r1, int $i0) throws FileNotFoundException {
        if ($i0 <= 0 || $r1 == null || this.mContext == null) {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
        Options $r2 = new Options();
        $r2.inJustDecodeBounds = true;
        loadBitmap($r1, $r2);
        int $i2 = $r2.outWidth;
        int $i1 = $r2.outHeight;
        if ($i2 <= 0) {
            return null;
        }
        if ($i1 <= 0) {
            return null;
        }
        int $i4 = Math.max($i2, $i1);
        int $i3 = 1;
        while ($i4 > $i0) {
            $i4 >>>= 1;
            $i3 <<= 1;
        }
        if ($i3 <= 0) {
            return null;
        }
        if (Math.min($i2, $i1) / $i3 <= 0) {
            return null;
        }
        synchronized (this.mLock) {
            this.mDecodeOptions = new Options();
            this.mDecodeOptions.inMutable = true;
            this.mDecodeOptions.inSampleSize = $i3;
            $r2 = this.mDecodeOptions;
        }
        try {
            Bitmap $r6 = loadBitmap($r1, $r2);
            synchronized (this.mLock) {
                this.mDecodeOptions = null;
            }
            return $r6;
        } catch (Throwable th) {
            synchronized (this.mLock) {
                this.mDecodeOptions = null;
            }
        }
    }

    private Bitmap loadBitmap(Uri $r1, Options $r2) throws FileNotFoundException {
        if ($r1 == null || this.mContext == null) {
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }
        InputStream $r5 = null;
        try {
            InputStream $r7 = this.mContext.getContentResolver().openInputStream($r1);
            $r5 = $r7;
            Bitmap $r8 = BitmapFactory.decodeStream($r7, null, $r2);
            if ($r7 == null) {
                return $r8;
            }
            try {
                $r7.close();
                return $r8;
            } catch (IOException $r9) {
                Log.w(LOG_TAG, "close fail ", $r9);
                return $r8;
            }
        } catch (Throwable th) {
            if ($r5 != null) {
                try {
                    $r5.close();
                } catch (IOException $r11) {
                    Log.w(LOG_TAG, "close fail ", $r11);
                }
            }
        }
    }

    private Bitmap convertBitmapForColorMode(Bitmap $r6, int $i0) throws  {
        if ($i0 != 1) {
            return $r6;
        }
        Bitmap $r7 = Bitmap.createBitmap($r6.getWidth(), $r6.getHeight(), Config.ARGB_8888);
        Canvas $r1 = new Canvas($r7);
        Paint $r4 = new Paint();
        ColorMatrix $r2 = new ColorMatrix();
        $r2.setSaturation(0.0f);
        $r4.setColorFilter(new ColorMatrixColorFilter($r2));
        $r1.drawBitmap($r6, 0.0f, 0.0f, $r4);
        $r1.setBitmap(null);
        return $r7;
    }
}
