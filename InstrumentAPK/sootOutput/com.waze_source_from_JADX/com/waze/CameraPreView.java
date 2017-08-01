package com.waze;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class CameraPreView extends SurfaceView implements Callback {
    private static final int CAMERA_CAPTURE_STATUS_FAILURE = 3;
    private static final int CAMERA_CAPTURE_STATUS_IN_PROCESS = 1;
    private static final int CAMERA_CAPTURE_STATUS_NONE = 0;
    private static final int CAMERA_CAPTURE_STATUS_SUCCESS = 2;
    private static final int CAMERA_PREVIEW_STATUS_ACTIVE = 2;
    private static final int CAMERA_PREVIEW_STATUS_NOT_READY = 0;
    private static final int CAMERA_PREVIEW_STATUS_PREPARED = 1;
    private static final int MainScreenShowDelay = 100;
    private static final boolean THUMBNAIL_FORMAT_BGRA = true;
    private static Bitmap mBitmapOut;
    private static ByteArrayOutputStream mBufOS = new ByteArrayOutputStream();
    private static CaptureParams mCaptureParams = new CaptureParams();
    private static int mCaptureStatus = 0;
    private static boolean mPreserveWholeImage = false;
    private static int mPreviewStatus = 0;
    Camera mCamera;
    SurfaceHolder mHolder = getHolder();

    public static abstract class CallbackNative {
        private boolean mActive = true;

        public abstract void onCapture(int i) throws ;

        private void run(final boolean $z0) throws  {
            if (this.mActive) {
                NativeManager.Post(new Runnable() {
                    public void run() throws  {
                        CallbackNative.this.onCapture($z0 ? (byte) 1 : (byte) 0);
                    }
                });
                this.mActive = false;
            }
        }
    }

    private final class CaptureCallback implements PictureCallback {
        private CaptureCallback() throws  {
        }

        public void onPictureTaken(byte[] $r1, Camera aCamera) throws  {
            boolean $z0 = true;
            Bitmap $r4 = BitmapFactory.decodeByteArray($r1, 0, $r1.length);
            if ($r4 != null) {
                Bitmap $r6 = Bitmap.createScaledBitmap($r4, CameraPreView.mCaptureParams.mImageHeight, CameraPreView.mCaptureParams.mImageWidth, true);
                $r4.recycle();
                Canvas $r3 = new Canvas($r6);
                $r3.rotate(90.0f);
                $r3.save();
                $r4 = Bitmap.createBitmap($r6, 0, 0, $r6.getWidth(), $r6.getHeight(), $r3.getMatrix(), true);
                CameraPreView.this.CompressToBuffer($r4, CompressFormat.JPEG);
                CameraPreView.mBitmapOut = $r4;
                CameraPreView.mCaptureStatus = 2;
            } else {
                CameraPreView.mCaptureStatus = 3;
            }
            if (CameraPreView.mCaptureParams.mCallback == null) {
                NativeManager.Notify(100);
                return;
            }
            CallbackNative $r10 = CameraPreView.mCaptureParams.mCallback;
            if (CameraPreView.mCaptureStatus != 2) {
                $z0 = false;
            }
            $r10.run($z0);
        }
    }

    public static class CaptureParams {
        private CallbackNative mCallback = null;
        private String mImageFile = "temp.jpg";
        private String mImageFolder = "./";
        private int mImageHeight = 256;
        private int mImageQuality = 50;
        private int mImageWidth = 256;

        public void setImageWidth(int $i0) throws  {
            this.mImageWidth = $i0;
        }

        public void setImageHeight(int $i0) throws  {
            this.mImageHeight = $i0;
        }

        public void setImageQuality(int $i0) throws  {
            this.mImageQuality = $i0;
        }

        public void setImageFolder(String $r1) throws  {
            this.mImageFolder = $r1;
        }

        public void setImageFile(String $r1) throws  {
            this.mImageFile = $r1;
        }

        public void setCallback(CallbackNative $r1) throws  {
            this.mCallback = $r1;
        }
    }

    private static final class CompatabilityWrapper {
        private CompatabilityWrapper() throws  {
        }

        public static Size getBestFitSize(int $i2, int $i3, Parameters $r0) throws  {
            List $r1 = $r0.getSupportedPictureSizes();
            int $i4 = 0;
            int $i5 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            if ($i2 < $i3) {
                int i = $i2;
                $i2 = $i3;
                $i3 = i;
            }
            for (i = 0; i < $r1.size(); i++) {
                Size $r3 = (Size) $r1.get(i);
                if ($r3.width >= $i2 && $r3.height >= $i3) {
                    int $i0 = (int) (Math.sqrt(((double) $r3.width) - ((double) $i2)) + Math.sqrt(((double) $r3.height) - ((double) $i3)));
                    if ($i0 < $i5) {
                        $i4 = i;
                        $i5 = $i0;
                    }
                }
            }
            return (Size) $r1.get($i4);
        }
    }

    public void surfaceDestroyed(android.view.SurfaceHolder r11) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x005d in list [B:6:0x001c]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r10 = this;
        r0 = r10.mCamera;	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        if (r0 == 0) goto L_0x0011;	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
    L_0x0004:
        r0 = r10.mCamera;	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        r0.stopPreview();	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        r0 = r10.mCamera;	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        r0.release();	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        r1 = 0;	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        r10.mCamera = r1;	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
    L_0x0011:
        r2 = 0;
        mPreviewStatus = r2;
        r3 = mCaptureParams;
        r4 = r3.mCallback;
        if (r4 != 0) goto L_0x005d;
    L_0x001c:
        r5 = 0;
        com.waze.NativeManager.Notify(r5);
        return;
    L_0x0022:
        r7 = move-exception;
        r8 = "Error in destroying the surface";	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        com.waze.Logger.m39e(r8, r7);	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        r3 = mCaptureParams;	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        r4 = r3.mCallback;	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        if (r4 == 0) goto L_0x003a;	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
    L_0x0030:
        r3 = mCaptureParams;	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        r4 = r3.mCallback;	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        r2 = 0;	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
        r4.run(r2);	 Catch:{ Exception -> 0x0022, Throwable -> 0x004b }
    L_0x003a:
        r2 = 0;
        mPreviewStatus = r2;
        r3 = mCaptureParams;
        r4 = r3.mCallback;
        if (r4 != 0) goto L_0x005e;
    L_0x0045:
        r5 = 0;
        com.waze.NativeManager.Notify(r5);
        return;
    L_0x004b:
        r9 = move-exception;
        r2 = 0;
        mPreviewStatus = r2;
        r3 = mCaptureParams;
        r4 = r3.mCallback;
        if (r4 != 0) goto L_0x005c;
    L_0x0057:
        r5 = 0;
        com.waze.NativeManager.Notify(r5);
    L_0x005c:
        throw r9;
    L_0x005d:
        return;
    L_0x005e:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.CameraPreView.surfaceDestroyed(android.view.SurfaceHolder):void");
    }

    public CameraPreView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        setFocusableInTouchMode(true);
        this.mHolder.addCallback(this);
        this.mHolder.setType(3);
    }

    public void surfaceCreated(SurfaceHolder $r1) throws  {
        try {
            System.gc();
            this.mCamera = Camera.open();
            this.mCamera.setPreviewDisplay($r1);
            mPreviewStatus = 1;
        } catch (Exception $r2) {
            Logger.m39e("Error in creating the surface", $r2);
            mPreviewStatus = 0;
            mCaptureStatus = 0;
            if (mCaptureParams.mCallback == null) {
                NativeManager.Notify(0);
            } else {
                mCaptureParams.mCallback.run(false);
            }
        }
    }

    public void surfaceChanged(SurfaceHolder aHolder, int aFormat, int aWidth, int aHeight) throws  {
        if (mPreviewStatus == 1) {
            try {
                if (mCaptureParams.mImageWidth >= 0 || mCaptureParams.mImageHeight >= 0) {
                    Parameters $r5 = this.mCamera.getParameters();
                    if (Integer.parseInt(VERSION.SDK) >= 5) {
                        Size $r8 = CompatabilityWrapper.getBestFitSize(mCaptureParams.mImageWidth, mCaptureParams.mImageHeight, $r5);
                        $r5.setPictureSize($r8.width, $r8.height);
                    } else if (mCaptureParams.mImageWidth > mCaptureParams.mImageHeight) {
                        $r5.setPictureSize(mCaptureParams.mImageWidth, mCaptureParams.mImageHeight);
                    } else {
                        $r5.setPictureSize(mCaptureParams.mImageHeight, mCaptureParams.mImageWidth);
                    }
                    this.mCamera.setParameters($r5);
                    mCaptureStatus = 0;
                    mPreviewStatus = 2;
                    this.mCamera.startPreview();
                    return;
                }
                Logger.m43w("Requested image dimensions are invalid. Width: " + mCaptureParams.mImageWidth + ". Height: " + mCaptureParams.mImageHeight);
                return;
            } catch (Exception $r2) {
                Logger.m39e("Error in surfaceChanged", $r2);
                $r2.printStackTrace();
                mPreviewStatus = 0;
                mCaptureStatus = 0;
                if (mCaptureParams.mCallback == null) {
                    NativeManager.Notify(0);
                    return;
                } else {
                    mCaptureParams.mCallback.run(false);
                    return;
                }
            }
        }
        Logger.m43w("Camera preivew is not ready!");
    }

    public boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
        boolean $z0 = false;
        switch ($i0) {
            case 4:
                if (mCaptureParams.mCallback == null) {
                    NativeManager.Notify(0);
                } else {
                    mCaptureParams.mCallback.run(false);
                }
                $z0 = true;
                break;
            case 23:
            case 27:
                if (mCaptureStatus == 0) {
                    Capture();
                    $z0 = true;
                    break;
                }
                break;
            default:
                break;
        }
        if ($z0) {
            return $z0;
        }
        return super.onKeyDown($i0, $r1);
    }

    public void Capture() throws  {
        try {
            this.mCamera.takePicture(null, null, new CaptureCallback());
            mCaptureStatus = 1;
        } catch (Exception $r1) {
            Logger.m39e("Error in capturing the picture", $r1);
            mPreviewStatus = 0;
            mCaptureStatus = 0;
            if (mCaptureParams.mCallback == null) {
                NativeManager.Notify(0);
            } else {
                mCaptureParams.mCallback.run(false);
            }
        }
    }

    public static boolean getPreviewActive() throws  {
        return mPreviewStatus == 2;
    }

    public static boolean getCaptureStatus() throws  {
        return mCaptureStatus == 2;
    }

    public static void CaptureConfig(int $i0, int $i1, int $i2, String $r0, String $r1, CallbackNative $r2) throws  {
        mCaptureParams.setImageWidth($i0);
        mCaptureParams.setImageHeight($i1);
        mCaptureParams.setImageQuality($i2);
        mCaptureParams.setImageFolder($r0);
        mCaptureParams.setImageFile($r1);
        mCaptureParams.setCallback($r2);
    }

    private static void SaveImage(Bitmap $r0, String $r1) throws  {
        try {
            $r0.compress(CompressFormat.JPEG, 50, new FileOutputStream($r1));
        } catch (Exception $r2) {
            throw new RuntimeException($r2);
        }
    }

    private void CompressToBuffer(Bitmap $r1, CompressFormat $r2) throws  {
        synchronized (mBufOS) {
            mBufOS.reset();
            $r1.compress($r2, mCaptureParams.mImageQuality, mBufOS);
        }
    }

    public static void SaveToFile() throws  {
        if (mBufOS.size() > 0) {
            try {
                String $r5 = new String(mCaptureParams.mImageFolder);
                File $r2 = new File(($r5 + String.valueOf("/")) + new String(mCaptureParams.mImageFile));
                if ($r2.getParentFile() != null) {
                    $r2.getParentFile().mkdirs();
                }
                FileOutputStream $r1 = new FileOutputStream($r2);
                FileChannel $r10 = $r1.getChannel();
                synchronized (mBufOS) {
                    $r10.write(ByteBuffer.wrap(mBufOS.toByteArray()));
                }
                $r1.close();
            } catch (Exception $r0) {
                Logger.m39e("Error in writing the file to the disk. ", $r0);
                $r0.printStackTrace();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void ReleaseBuf() throws  {
        /*
        r0 = mBufOS;
        monitor-enter(r0);
        r1 = mBufOS;
        r1.close();	 Catch:{ Exception -> 0x000a }
    L_0x0008:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0014 }
        return;
    L_0x000a:
        r2 = move-exception;
        r3 = "Cannot release the buffer. ";
        com.waze.Logger.m39e(r3, r2);	 Catch:{ Throwable -> 0x0014 }
        r2.printStackTrace();	 Catch:{ Throwable -> 0x0014 }
        goto L_0x0008;
    L_0x0014:
        r4 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0014 }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.CameraPreView.ReleaseBuf():void");
    }

    public static int GetBufSize() throws  {
        int $i0;
        synchronized (mBufOS) {
            $i0 = mBufOS.size();
        }
        return $i0;
    }

    public static byte[] GetCaptureBuffer() throws  {
        byte[] $r1;
        synchronized (mBufOS) {
            $r1 = mBufOS.toByteArray();
        }
        return $r1;
    }

    public static int[] GetThumbnail(int $i0, int $i1) throws  {
        Paint $r2 = new Paint();
        if (mBitmapOut == null) {
            return null;
        }
        int $i3;
        int $i4;
        Bitmap $r3;
        if (!mPreserveWholeImage) {
            $i3 = $i0;
            $i4 = (mCaptureParams.mImageHeight * $i0) / mCaptureParams.mImageWidth;
        } else if ($i0 / mCaptureParams.mImageWidth < $i1 / mCaptureParams.mImageHeight) {
            $i3 = $i0;
            $i4 = ($i1 * $i0) / mCaptureParams.mImageWidth;
        } else {
            $i4 = $i1;
            $i3 = ($i1 * $i0) / mCaptureParams.mImageHeight;
        }
        synchronized (mBufOS) {
            $r3 = Bitmap.createScaledBitmap(mBitmapOut, $i3, $i4, true);
        }
        if ($i4 > $i1) {
            int $i2 = ($i4 - $i1) / 2;
            new Canvas($r3).clipRect(0, $i2 - 1, $r3.getWidth() - 1, $r3.getHeight() - $i2);
        }
        Bitmap $r8 = Bitmap.createBitmap($i0, $i1, Config.ARGB_8888);
        float $f0 = (float) (($i0 - $i3) / 2);
        float $f1 = (float) (($i1 - $i4) / 2);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        Canvas canvas = new Canvas($r8);
        canvas.drawARGB(0, 0, 0, 0);
        $r2.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap($r3, $f0, $f1, $r2);
        int[] $r4 = new int[($i1 * $i0)];
        $r8.getPixels($r4, 0, $r8.getWidth(), 0, 0, $r8.getWidth(), $r8.getHeight());
        $r3.recycle();
        $r8.recycle();
        return $r4;
    }
}
