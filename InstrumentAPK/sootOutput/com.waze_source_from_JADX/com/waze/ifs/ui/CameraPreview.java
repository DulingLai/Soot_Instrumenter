package com.waze.ifs.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.support.v4.widget.AutoScrollHelper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.settings.SettingsVoiceSearchActivity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class CameraPreview extends SurfaceView implements Callback {
    private static final int CAMERA_AUTO_FOCUS_TIMEOUT = 5000;
    private static final int CAMERA_CAPTURE_STATUS_FAILURE = 3;
    private static final int CAMERA_CAPTURE_STATUS_IN_PROCESS = 1;
    private static final int CAMERA_CAPTURE_STATUS_NONE = 0;
    private static final int CAMERA_CAPTURE_STATUS_SUCCESS = 2;
    private static final int CAMERA_PREVIEW_STATUS_ACTIVE = 2;
    private static final int CAMERA_PREVIEW_STATUS_NOT_READY = 0;
    private static final int CAMERA_PREVIEW_STATUS_PREPARED = 1;
    private static final boolean THUMBNAIL_FORMAT_BGRA = true;
    private static CaptureParams mCaptureParams = new CaptureParams();
    private Bitmap mBitmapOut;
    private ByteArrayOutputStream mBufOS;
    private Camera mCamera;
    private int mCaptureStatus = 0;
    Context mContext;
    private String mFlashModeValue = SettingsVoiceSearchActivity.SEARCH_VOICE_LANG_AUTO;
    private float mHeight = 1.0f;
    private SurfaceHolder mHolder;
    private CameraInfo mInfo;
    private float mLeft;
    private boolean mPreserveWholeImage = false;
    private int mPreviewStatus = 0;
    private float mTop;
    private float mWidth = 1.0f;
    private OrientationEventListener orientationListener;

    class C17092 implements AutoFocusCallback {
        C17092() throws  {
        }

        public void onAutoFocus(boolean $z0, Camera camera) throws  {
            try {
                Logger.v_("capture", "autoFocus done, success=" + $z0);
                CameraPreview.this.mCamera.takePicture(null, null, new CaptureCallback());
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_TEST_PLACES_PHOTO_TRY_CAPTURE, null, null);
            } catch (Exception $r2) {
                Logger.m39e("onAutoFocus", $r2);
                CameraPreview.this.mCaptureStatus = 3;
                CameraPreview.this.callOnCaptureListener(false);
            }
        }
    }

    class C17103 implements Runnable {
        C17103() throws  {
        }

        public void run() throws  {
            if (CameraPreview.this.mCaptureStatus == 1) {
                try {
                    Logger.m43w("capture() - autoFocus timed out");
                    CameraPreview.this.mCamera.cancelAutoFocus();
                    CameraPreview.this.mCamera.takePicture(null, null, new CaptureCallback());
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_TEST_PLACES_PHOTO_TRY_CAPTURE, null, null);
                } catch (Exception $r1) {
                    Logger.m39e("autoFocus timeout", $r1);
                    CameraPreview.this.mCaptureStatus = 3;
                    CameraPreview.this.callOnCaptureListener(false);
                }
            }
        }
    }

    public interface CameraCallbacks {
        void onCapture(boolean z) throws ;

        void onError() throws ;

        void onSize(Size size) throws ;
    }

    private final class CaptureCallback implements PictureCallback {

        private final class ProcessCapture implements Runnable {
            private final byte[] aData;

            class C17111 implements Runnable {
                C17111() throws  {
                }

                public void run() throws  {
                    CameraPreview.this.callOnCaptureListener(CameraPreview.this.mCaptureStatus == 2);
                }
            }

            public void run() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:29:0x014f in {6, 10, 14, 18, 19, 20, 24, 25, 26, 28, 30, 34, 35} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r22 = this;
                r0 = r22;
                r2 = r0.aData;
                r3 = com.waze.ifs.ui.CameraPreview.Exif.getOrientation(r2);
                r4 = r3;
                r0 = r22;
                r2 = r0.aData;
                r0 = r22;
                r5 = r0.aData;
                r6 = r5.length;
                r8 = 0;
                r7 = android.graphics.BitmapFactory.decodeByteArray(r2, r8, r6);
                if (r7 == 0) goto L_0x016b;
            L_0x0019:
                r0 = r22;
                r9 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r10 = com.waze.ifs.ui.CameraPreview.this;
                r11 = r10.mHeight;
                r13 = 0;
                r12 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1));
                if (r12 != 0) goto L_0x0153;
            L_0x0028:
                r14 = com.waze.ifs.ui.CameraPreview.mCaptureParams;
                r6 = r14.mImageHeight;
                r14 = com.waze.ifs.ui.CameraPreview.mCaptureParams;
                r15 = r14.mImageWidth;
                r8 = 1;
                r16 = android.graphics.Bitmap.createScaledBitmap(r7, r6, r15, r8);
                r17 = r16;
                r0 = r16;
                if (r0 == r7) goto L_0x0046;
            L_0x0043:
                r7.recycle();
            L_0x0046:
                r14 = com.waze.ifs.ui.CameraPreview.mCaptureParams;
                r6 = r14.mImageHeight;
                r14 = com.waze.ifs.ui.CameraPreview.mCaptureParams;
                r15 = r14.mImageWidth;
                if (r6 <= r15) goto L_0x0066;
            L_0x0058:
                r0 = r17;
                r6 = r0.getHeight();
                r0 = r17;
                r15 = r0.getWidth();
                if (r6 < r15) goto L_0x0086;
            L_0x0066:
                r14 = com.waze.ifs.ui.CameraPreview.mCaptureParams;
                r6 = r14.mImageHeight;
                r14 = com.waze.ifs.ui.CameraPreview.mCaptureParams;
                r15 = r14.mImageWidth;
                if (r6 >= r15) goto L_0x0156;
            L_0x0078:
                r0 = r17;
                r6 = r0.getHeight();
                r0 = r17;
                r15 = r0.getWidth();
                if (r6 <= r15) goto L_0x0156;
            L_0x0086:
                r8 = 90;
                if (r3 == r8) goto L_0x008e;
            L_0x008a:
                r8 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
                if (r3 != r8) goto L_0x0090;
            L_0x008e:
                r4 = r3 + -90;
            L_0x0090:
                r3 = r4 + 90;
                r0 = r17;
                r17 = com.waze.utils.ImageUtils.rotate(r0, r3);
            L_0x0098:
                r0 = r22;
                r9 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r10 = com.waze.ifs.ui.CameraPreview.this;
                r11 = r10.mHeight;
                r13 = 0;
                r12 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1));
                if (r12 <= 0) goto L_0x015f;
            L_0x00a7:
                r0 = r22;
                r9 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r10 = com.waze.ifs.ui.CameraPreview.this;
                r0 = r17;
                r3 = r0.getWidth();
                r11 = (float) r3;
                r0 = r22;
                r9 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r18 = r0;
                r19 = r0.mLeft;
                r0 = r19;
                r11 = r11 * r0;
                r3 = (int) r11;
                r0 = r17;
                r4 = r0.getHeight();
                r11 = (float) r4;
                r0 = r22;
                r9 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r18 = r0;
                r19 = r0.mTop;
                r0 = r19;
                r11 = r11 * r0;
                r4 = (int) r11;
                r0 = r17;
                r6 = r0.getWidth();
                r11 = (float) r6;
                r0 = r22;
                r9 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r18 = r0;
                r19 = r0.mWidth;
                r0 = r19;
                r11 = r11 * r0;
                r6 = (int) r11;
                goto L_0x00f6;
            L_0x00f3:
                goto L_0x0098;
            L_0x00f6:
                r0 = r17;
                r15 = r0.getHeight();
                r11 = (float) r15;
                r0 = r22;
                r9 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r18 = r0;
                r19 = r0.mHeight;
                r0 = r19;
                r11 = r11 * r0;
                r15 = (int) r11;
                r0 = r17;
                r7 = android.graphics.Bitmap.createBitmap(r0, r3, r4, r6, r15);
                r10.mBitmapOut = r7;
                r0 = r17;
                r0.recycle();
            L_0x011b:
                r0 = r22;
                r9 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r10 = com.waze.ifs.ui.CameraPreview.this;
                r0 = r22;
                r9 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r18 = r0;
                r17 = r0.mBitmapOut;
                r20 = android.graphics.Bitmap.CompressFormat.JPEG;
                r0 = r17;
                r1 = r20;
                r10.compressToBuffer(r0, r1);
                r0 = r22;
                r9 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r10 = com.waze.ifs.ui.CameraPreview.this;
                r8 = 2;
                r10.mCaptureStatus = r8;
            L_0x0140:
                r21 = new com.waze.ifs.ui.CameraPreview$CaptureCallback$ProcessCapture$1;
                r0 = r21;
                r1 = r22;
                r0.<init>();
                r0 = r21;
                com.waze.AppService.Post(r0);
                return;
                goto L_0x0153;
            L_0x0150:
                goto L_0x0046;
            L_0x0153:
                r17 = r7;
                goto L_0x0150;
            L_0x0156:
                if (r3 == 0) goto L_0x0098;
            L_0x0158:
                r0 = r17;
                r17 = com.waze.utils.ImageUtils.rotate(r0, r3);
                goto L_0x00f3;
            L_0x015f:
                r0 = r22;
                r9 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r10 = com.waze.ifs.ui.CameraPreview.this;
                r0 = r17;
                r10.mBitmapOut = r0;
                goto L_0x011b;
            L_0x016b:
                r0 = r22;
                r9 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r10 = com.waze.ifs.ui.CameraPreview.this;
                r8 = 3;
                r10.mCaptureStatus = r8;
                goto L_0x0140;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.waze.ifs.ui.CameraPreview.CaptureCallback.ProcessCapture.run():void");
            }

            private ProcessCapture(byte[] $r2) throws  {
                this.aData = $r2;
            }
        }

        private final class ProcessCaptureFullSize implements Runnable {
            private final byte[] aData;

            class C17121 implements Runnable {
                C17121() throws  {
                }

                public void run() throws  {
                    CameraPreview.this.callOnCaptureListener(CameraPreview.this.mCaptureStatus == 2);
                }
            }

            public void run() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:43:0x0163
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r30 = this;
                r0 = r30;
                r3 = r0.aData;
                r4 = com.waze.ifs.ui.CameraPreview.Exif.getOrientation(r3);
                r5 = r4;
                r6 = new android.graphics.BitmapFactory$Options;
                r6.<init>();
                r7 = 1;
                r6.inJustDecodeBounds = r7;
                r0 = r30;
                r3 = r0.aData;
                r0 = r30;
                r8 = r0.aData;
                r9 = r8.length;
                r7 = 0;
                android.graphics.BitmapFactory.decodeByteArray(r3, r7, r9, r6);
                r10 = com.waze.ifs.ui.CameraPreview.mCaptureParams;
                r9 = r10.mImageHeight;
                r10 = com.waze.ifs.ui.CameraPreview.mCaptureParams;
                r11 = r10.mImageWidth;
                if (r9 <= r11) goto L_0x0167;
            L_0x0030:
                r12 = 1;
            L_0x0031:
                r9 = r6.outHeight;
                r11 = r6.outWidth;
                if (r9 <= r11) goto L_0x016d;
            L_0x0037:
                r13 = 1;
            L_0x0038:
                if (r12 != r13) goto L_0x016f;
            L_0x003a:
                r14 = new android.graphics.Rect;
                r5 = r6.outWidth;
                r15 = (float) r5;
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r17 = r0;
                r18 = r0.mLeft;	 Catch:{ IOException -> 0x025d }
                r0 = r18;
                r15 = r15 * r0;
                r5 = (int) r15;
                r9 = r6.outHeight;
                r15 = (float) r9;
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r17 = r0;	 Catch:{ IOException -> 0x025d }
                r18 = r0.mTop;	 Catch:{ IOException -> 0x025d }
                r0 = r18;
                r15 = r15 * r0;
                r9 = (int) r15;
                r11 = r6.outWidth;
                r15 = (float) r11;
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r17 = r0;	 Catch:{ IOException -> 0x025d }
                r18 = r0.mLeft;	 Catch:{ IOException -> 0x025d }
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r17 = r0;	 Catch:{ IOException -> 0x025d }
                r19 = r0.mWidth;	 Catch:{ IOException -> 0x025d }
                r0 = r18;
                r1 = r19;
                r0 = r0 + r1;
                r18 = r0;
                r15 = r15 * r0;
                r11 = (int) r15;
                r0 = r6.outHeight;
                r20 = r0;
                r15 = (float) r0;
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r17 = r0;	 Catch:{ IOException -> 0x025d }
                r18 = r0.mTop;	 Catch:{ IOException -> 0x025d }
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r17 = r0;	 Catch:{ IOException -> 0x025d }
                r19 = r0.mHeight;	 Catch:{ IOException -> 0x025d }
                r0 = r18;
                r1 = r19;
                r0 = r0 + r1;
                r18 = r0;
                r15 = r15 * r0;
                r0 = (int) r15;	 Catch:{ IOException -> 0x025d }
                r20 = r0;	 Catch:{ IOException -> 0x025d }
                r14.<init>(r5, r9, r11, r0);	 Catch:{ IOException -> 0x025d }
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r17 = r0;
                r0 = r30;
                r3 = r0.aData;
                r0 = r30;
                r8 = r0.aData;
                r5 = r8.length;	 Catch:{ IOException -> 0x025d }
                r7 = 0;	 Catch:{ IOException -> 0x025d }
                r22 = 1;	 Catch:{ IOException -> 0x025d }
                r0 = r22;	 Catch:{ IOException -> 0x025d }
                r21 = android.graphics.BitmapRegionDecoder.newInstance(r3, r7, r5, r0);	 Catch:{ IOException -> 0x025d }
                r24 = 0;	 Catch:{ IOException -> 0x025d }
                r0 = r21;	 Catch:{ IOException -> 0x025d }
                r1 = r24;	 Catch:{ IOException -> 0x025d }
                r23 = r0.decodeRegion(r14, r1);	 Catch:{ IOException -> 0x025d }
                r0 = r17;	 Catch:{ IOException -> 0x025d }
                r1 = r23;	 Catch:{ IOException -> 0x025d }
                r0.mBitmapOut = r1;	 Catch:{ IOException -> 0x025d }
                if (r4 == 0) goto L_0x0111;
            L_0x00ec:
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r17 = r0;
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r25 = r0;	 Catch:{ IOException -> 0x025d }
                r23 = r0.mBitmapOut;	 Catch:{ IOException -> 0x025d }
                r0 = r23;	 Catch:{ IOException -> 0x025d }
                r23 = com.waze.utils.ImageUtils.rotate(r0, r4);	 Catch:{ IOException -> 0x025d }
                r0 = r17;	 Catch:{ IOException -> 0x025d }
                r1 = r23;	 Catch:{ IOException -> 0x025d }
                r0.mBitmapOut = r1;	 Catch:{ IOException -> 0x025d }
            L_0x0111:
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r17 = r0;
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r25 = r0;	 Catch:{ IOException -> 0x025d }
                r23 = r0.mBitmapOut;	 Catch:{ IOException -> 0x025d }
                r26 = android.graphics.Bitmap.CompressFormat.JPEG;	 Catch:{ IOException -> 0x025d }
                r0 = r17;	 Catch:{ IOException -> 0x025d }
                r1 = r23;	 Catch:{ IOException -> 0x025d }
                r2 = r26;	 Catch:{ IOException -> 0x025d }
                r0.compressToBuffer(r1, r2);	 Catch:{ IOException -> 0x025d }
            L_0x0134:
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r17 = r0;
                r23 = r0.mBitmapOut;
                if (r23 == 0) goto L_0x0268;
            L_0x0144:
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r17 = r0;
                r7 = 2;
                r0 = r17;
                r0.mCaptureStatus = r7;
            L_0x0154:
                r27 = new com.waze.ifs.ui.CameraPreview$CaptureCallback$ProcessCaptureFullSize$1;
                r0 = r27;
                r1 = r30;
                r0.<init>();
                r0 = r27;
                com.waze.AppService.Post(r0);
                return;
                goto L_0x0167;
            L_0x0164:
                goto L_0x0031;
            L_0x0167:
                r12 = 0;
                goto L_0x0164;
                goto L_0x016d;
            L_0x016a:
                goto L_0x0038;
            L_0x016d:
                r13 = 0;
                goto L_0x016a;
            L_0x016f:
                r14 = new android.graphics.Rect;
                r9 = r6.outWidth;
                r15 = (float) r9;
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r17 = r0;
                r18 = r0.mTop;	 Catch:{ IOException -> 0x025d }
                r0 = r18;
                r15 = r15 * r0;
                r9 = (int) r15;
                r11 = r6.outHeight;
                r15 = (float) r11;
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r17 = r0;	 Catch:{ IOException -> 0x025d }
                r18 = r0.mLeft;	 Catch:{ IOException -> 0x025d }
                r0 = r18;
                r15 = r15 * r0;
                r11 = (int) r15;
                r0 = r6.outWidth;
                r20 = r0;
                r15 = (float) r0;
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r17 = r0;	 Catch:{ IOException -> 0x025d }
                r18 = r0.mTop;	 Catch:{ IOException -> 0x025d }
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r17 = r0;	 Catch:{ IOException -> 0x025d }
                r19 = r0.mHeight;	 Catch:{ IOException -> 0x025d }
                r0 = r18;
                r1 = r19;
                r0 = r0 + r1;
                r18 = r0;
                r15 = r15 * r0;
                r0 = (int) r15;
                r20 = r0;
                r0 = r6.outHeight;
                r28 = r0;
                r15 = (float) r0;
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r17 = r0;	 Catch:{ IOException -> 0x025d }
                r18 = r0.mLeft;	 Catch:{ IOException -> 0x025d }
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r17 = r0;	 Catch:{ IOException -> 0x025d }
                r19 = r0.mWidth;	 Catch:{ IOException -> 0x025d }
                r0 = r18;
                r1 = r19;
                r0 = r0 + r1;
                r18 = r0;
                r15 = r15 * r0;
                r0 = (int) r15;	 Catch:{ IOException -> 0x025d }
                r28 = r0;	 Catch:{ IOException -> 0x025d }
                r0 = r20;	 Catch:{ IOException -> 0x025d }
                r1 = r28;	 Catch:{ IOException -> 0x025d }
                r14.<init>(r9, r11, r0, r1);	 Catch:{ IOException -> 0x025d }
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r17 = r0;
                r0 = r30;
                r3 = r0.aData;
                r0 = r30;
                r8 = r0.aData;
                r9 = r8.length;	 Catch:{ IOException -> 0x025d }
                r7 = 0;	 Catch:{ IOException -> 0x025d }
                r22 = 1;	 Catch:{ IOException -> 0x025d }
                r0 = r22;	 Catch:{ IOException -> 0x025d }
                r21 = android.graphics.BitmapRegionDecoder.newInstance(r3, r7, r9, r0);	 Catch:{ IOException -> 0x025d }
                r24 = 0;	 Catch:{ IOException -> 0x025d }
                r0 = r21;	 Catch:{ IOException -> 0x025d }
                r1 = r24;	 Catch:{ IOException -> 0x025d }
                r23 = r0.decodeRegion(r14, r1);	 Catch:{ IOException -> 0x025d }
                r0 = r17;	 Catch:{ IOException -> 0x025d }
                r1 = r23;	 Catch:{ IOException -> 0x025d }
                r0.mBitmapOut = r1;	 Catch:{ IOException -> 0x025d }
                r7 = 90;
                if (r4 == r7) goto L_0x022f;
            L_0x022b:
                r7 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
                if (r4 != r7) goto L_0x0231;
            L_0x022f:
                r5 = r4 + -90;
            L_0x0231:
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r17 = r0;
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;	 Catch:{ IOException -> 0x025d }
                r25 = r0;	 Catch:{ IOException -> 0x025d }
                r23 = r0.mBitmapOut;	 Catch:{ IOException -> 0x025d }
                r4 = r5 + 90;	 Catch:{ IOException -> 0x025d }
                r0 = r23;	 Catch:{ IOException -> 0x025d }
                r23 = com.waze.utils.ImageUtils.rotate(r0, r4);	 Catch:{ IOException -> 0x025d }
                goto L_0x0255;	 Catch:{ IOException -> 0x025d }
            L_0x0252:
                goto L_0x0111;	 Catch:{ IOException -> 0x025d }
            L_0x0255:
                r0 = r17;	 Catch:{ IOException -> 0x025d }
                r1 = r23;	 Catch:{ IOException -> 0x025d }
                r0.mBitmapOut = r1;	 Catch:{ IOException -> 0x025d }
                goto L_0x0252;
            L_0x025d:
                r29 = move-exception;
                goto L_0x0262;
            L_0x025f:
                goto L_0x0134;
            L_0x0262:
                r0 = r29;
                r0.printStackTrace();
                goto L_0x025f;
            L_0x0268:
                r0 = r30;
                r0 = com.waze.ifs.ui.CameraPreview.CaptureCallback.this;
                r16 = r0;
                r0 = com.waze.ifs.ui.CameraPreview.this;
                r17 = r0;
                goto L_0x0276;
            L_0x0273:
                goto L_0x0154;
            L_0x0276:
                r7 = 3;
                r0 = r17;
                r0.mCaptureStatus = r7;
                goto L_0x0273;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.waze.ifs.ui.CameraPreview.CaptureCallback.ProcessCaptureFullSize.run():void");
            }

            private ProcessCaptureFullSize(byte[] $r2) throws  {
                this.aData = $r2;
            }
        }

        private CaptureCallback() throws  {
        }

        public void onPictureTaken(byte[] $r1, Camera aCamera) throws  {
            Thread $r6;
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_TEST_PLACES_PHOTO_CAPTURED, null, null);
            if (CameraPreview.mCaptureParams.mMaximizeCapture) {
                $r6 = new Thread(new ProcessCaptureFullSize($r1));
            } else {
                $r6 = new Thread(new ProcessCapture($r1));
            }
            $r6.start();
            CameraPreview.this.mCamera.stopPreview();
        }
    }

    public static class CaptureParams {
        private CameraCallbacks mCallback = null;
        private boolean mCaptureCallbackActive = true;
        private String mImageFile = "temp.jpg";
        private String mImageFolder = "./";
        private int mImageHeight = 256;
        private int mImageQuality = 50;
        private int mImageWidth = 256;
        private int mMaxCaptureSize = 5242880;
        private boolean mMaximizeCapture = false;

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

        public void setCallback(CameraCallbacks $r1) throws  {
            this.mCallback = $r1;
        }

        public void setMaximizeCapture(boolean $z0) throws  {
            this.mMaximizeCapture = $z0;
        }

        public void setMaxCaptureSize(int $i0) throws  {
            this.mMaxCaptureSize = $i0;
        }
    }

    private static final class CompatabilityWrapper {
        private CompatabilityWrapper() throws  {
        }

        public static Size getBestFitSize(int $i2, int $i3, boolean $z0, Parameters $r0, int $i0) throws  {
            List $r1 = $r0.getSupportedPictureSizes();
            int $i4 = -1;
            float $f2 = AutoScrollHelper.NO_MAX;
            if ($i2 < $i3) {
                int i = $i2;
                $i2 = $i3;
                $i3 = i;
            }
            for (i = 0; i < $r1.size(); i++) {
                Size $r3 = (Size) $r1.get(i);
                if ($r3.width >= $i2 && $r3.height >= $i3) {
                    float $f0;
                    if (!$z0) {
                        double $d0 = ((double) $r3.width) - ((double) $i2);
                        double d = $d0;
                        d = Math.sqrt($d0);
                        $d0 = ((double) $r3.height) - ((double) $i3);
                        double d2 = $d0;
                        $d0 = d + Math.sqrt($d0);
                        d = $d0;
                        $f0 = (float) $d0;
                    } else if ($r3.width * $r3.height <= $i0) {
                        if ($i4 >= 0) {
                            Size $r4 = (Size) $r1.get($i4);
                            if ($r3.width < $r4.width && $r3.height < $r4.height) {
                            }
                        }
                        float $f1 = (float) $r3.height;
                        $f0 = Math.abs((((float) $i2) / ((float) $i3)) - (((float) $r3.width) / $f1));
                    }
                    if ($f0 <= $f2) {
                        $i4 = i;
                        $f2 = $f0;
                    }
                }
            }
            return (Size) $r1.get($i4);
        }

        public static Size getBestPreviewSize(int $i2, int $i3, Parameters $r0) throws  {
            List $r1 = $r0.getSupportedPreviewSizes();
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

    public static class Exif {
        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static int getOrientation(byte[] r10) throws  {
            /*
            r0 = 1;
            if (r10 != 0) goto L_0x0005;
        L_0x0003:
            r1 = 0;
            return r1;
        L_0x0005:
            r2 = 0;
            r3 = 0;
        L_0x0007:
            r4 = r2 + 3;
            r5 = r10.length;
            if (r4 >= r5) goto L_0x0035;
        L_0x000c:
            r4 = r2 + 1;
            r6 = r10[r2];
            r1 = 255; // 0xff float:3.57E-43 double:1.26E-321;
            r7 = r6 & r1;
            r1 = 255; // 0xff float:3.57E-43 double:1.26E-321;
            if (r7 != r1) goto L_0x00f3;
        L_0x0018:
            r6 = r10[r4];
            r1 = 255; // 0xff float:3.57E-43 double:1.26E-321;
            r7 = r6 & r1;
            r1 = 255; // 0xff float:3.57E-43 double:1.26E-321;
            if (r7 != r1) goto L_0x0024;
        L_0x0022:
            r2 = r4;
            goto L_0x0007;
        L_0x0024:
            r2 = r4 + 1;
            r1 = 216; // 0xd8 float:3.03E-43 double:1.067E-321;
            if (r7 == r1) goto L_0x0007;
        L_0x002a:
            r1 = 1;
            if (r7 == r1) goto L_0x0007;
        L_0x002d:
            r1 = 217; // 0xd9 float:3.04E-43 double:1.07E-321;
            if (r7 == r1) goto L_0x0035;
        L_0x0031:
            r1 = 218; // 0xda float:3.05E-43 double:1.077E-321;
            if (r7 != r1) goto L_0x0054;
        L_0x0035:
            r1 = 8;
            if (r3 <= r1) goto L_0x00ec;
        L_0x0039:
            r1 = 4;
            r8 = 0;
            r4 = pack(r10, r2, r1, r8);
            r1 = 1229531648; // 0x49492a00 float:823968.0 double:6.074693478E-315;
            if (r4 == r1) goto L_0x0090;
        L_0x0044:
            r1 = 1296891946; // 0x4d4d002a float:2.14958752E8 double:6.40749757E-315;
            if (r4 == r1) goto L_0x0090;
        L_0x0049:
            r9 = "Invalid byte order";
            com.waze.Logger.m38e(r9);
            goto L_0x0052;
        L_0x004f:
            goto L_0x0007;
        L_0x0052:
            r1 = 0;
            return r1;
        L_0x0054:
            r1 = 2;
            r8 = 0;
            r3 = pack(r10, r2, r1, r8);
            r1 = 2;
            if (r3 < r1) goto L_0x0062;
        L_0x005d:
            r4 = r2 + r3;
            r5 = r10.length;
            if (r4 <= r5) goto L_0x0069;
        L_0x0062:
            r9 = "Invalid length";
            com.waze.Logger.m38e(r9);
            r1 = 0;
            return r1;
        L_0x0069:
            r1 = 225; // 0xe1 float:3.15E-43 double:1.11E-321;
            if (r7 != r1) goto L_0x008d;
        L_0x006d:
            r1 = 8;
            if (r3 < r1) goto L_0x008d;
        L_0x0071:
            r4 = r2 + 2;
            r1 = 4;
            r8 = 0;
            r4 = pack(r10, r4, r1, r8);
            r1 = 1165519206; // 0x45786966 float:3974.5874 double:5.758429993E-315;
            if (r4 != r1) goto L_0x008d;
        L_0x007e:
            r4 = r2 + 6;
            r1 = 2;
            r8 = 0;
            r4 = pack(r10, r4, r1, r8);
            if (r4 != 0) goto L_0x008d;
        L_0x0088:
            r2 = r2 + 8;
            r3 = r3 + -8;
            goto L_0x0035;
        L_0x008d:
            r2 = r2 + r3;
            r3 = 0;
            goto L_0x004f;
        L_0x0090:
            r1 = 1229531648; // 0x49492a00 float:823968.0 double:6.074693478E-315;
            if (r4 != r1) goto L_0x00af;
        L_0x0095:
            goto L_0x0099;
        L_0x0096:
            goto L_0x0035;
        L_0x0099:
            r4 = r2 + 4;
            r1 = 4;
            r4 = pack(r10, r4, r1, r0);
            r4 = r4 + 2;
            r1 = 10;
            if (r4 < r1) goto L_0x00a8;
        L_0x00a6:
            if (r4 <= r3) goto L_0x00b1;
        L_0x00a8:
            r9 = "Invalid offset";
            com.waze.Logger.m38e(r9);
            r1 = 0;
            return r1;
        L_0x00af:
            r0 = 0;
            goto L_0x0099;
        L_0x00b1:
            r2 = r2 + r4;
            r3 = r3 - r4;
            r4 = r2 + -2;
            r1 = 2;
            r5 = pack(r10, r4, r1, r0);
        L_0x00ba:
            r4 = r5 + -1;
            if (r5 <= 0) goto L_0x00ec;
        L_0x00be:
            r1 = 12;
            if (r3 < r1) goto L_0x00ec;
        L_0x00c2:
            r1 = 2;
            r5 = pack(r10, r2, r1, r0);
            r1 = 274; // 0x112 float:3.84E-43 double:1.354E-321;
            if (r5 != r1) goto L_0x00e6;
        L_0x00cb:
            r2 = r2 + 8;
            r1 = 2;
            r2 = pack(r10, r2, r1, r0);
            switch(r2) {
                case 1: goto L_0x0003;
                case 2: goto L_0x00d6;
                case 3: goto L_0x00dd;
                case 4: goto L_0x00d6;
                case 5: goto L_0x00d6;
                case 6: goto L_0x00e0;
                case 7: goto L_0x00d6;
                case 8: goto L_0x00e3;
                default: goto L_0x00d5;
            };
        L_0x00d5:
            goto L_0x00d6;
        L_0x00d6:
            r9 = "Unsupported orientation";
            com.waze.Logger.m41i(r9);
            r1 = 0;
            return r1;
        L_0x00dd:
            r1 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
            return r1;
        L_0x00e0:
            r1 = 90;
            return r1;
        L_0x00e3:
            r1 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
            return r1;
        L_0x00e6:
            r2 = r2 + 12;
            r3 = r3 + -12;
            r5 = r4;
            goto L_0x00ba;
        L_0x00ec:
            r9 = "Orientation not found";
            com.waze.Logger.m41i(r9);
            r1 = 0;
            return r1;
        L_0x00f3:
            r2 = r4;
            goto L_0x0096;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.waze.ifs.ui.CameraPreview.Exif.getOrientation(byte[]):int");
        }

        private static int pack(byte[] $r0, int $i0, int $i1, boolean $z0) throws  {
            byte $b2 = (byte) 1;
            if ($z0) {
                $i0 += $i1 - 1;
                $b2 = (byte) -1;
            }
            int $i3 = 0;
            for (int $i4 = $i1; $i4 > 0; $i4--) {
                $i3 = ($i3 << 8) | ($r0[$i0] & (short) 255);
                $i0 += $b2;
            }
            return $i3;
        }
    }

    public enum FlashMode {
        Off,
        On,
        Auto
    }

    public CameraPreview(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        this.mContext = $r1;
        this.orientationListener = createOrientationListener();
        setFocusableInTouchMode(true);
        this.mHolder = getHolder();
        this.mHolder.addCallback(this);
        this.mHolder.setType(3);
    }

    public void surfaceCreated(SurfaceHolder $r1) throws  {
        try {
            System.gc();
            this.mCamera = Camera.open();
            this.mCamera.setPreviewDisplay($r1);
            this.orientationListener.enable();
            this.mBufOS = new ByteArrayOutputStream();
            this.mTop = 0.0f;
            this.mLeft = 0.0f;
            this.mHeight = 1.0f;
            this.mWidth = 1.0f;
            mCaptureParams.mCaptureCallbackActive = true;
            this.mPreviewStatus = 1;
        } catch (Exception $r2) {
            Logger.m39e("Error in creating the surface", $r2);
            this.mPreviewStatus = 0;
            this.mCaptureStatus = 0;
            if (mCaptureParams.mCallback != null) {
                mCaptureParams.mCallback.onError();
            }
        }
    }

    private OrientationEventListener createOrientationListener() throws  {
        return new OrientationEventListener(this.mContext) {
            int lastOrintation = -1;

            public void onOrientationChanged(int $i0) throws  {
                try {
                    if (CameraPreview.this.mCamera != null && CameraPreview.this.mInfo != null && $i0 != -1) {
                        $i0 = (($i0 + 45) / 90) * 90;
                        if (this.lastOrintation != $i0) {
                            this.lastOrintation = $i0;
                            Parameters $r4 = CameraPreview.this.mCamera.getParameters();
                            $i0 = ((WindowManager) CameraPreview.this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
                            short $s2 = (short) 0;
                            short $s3 = (short) 0;
                            if ($i0 == 0) {
                                $s2 = (short) 0;
                                $s3 = (short) 90;
                            } else if ($i0 == 1) {
                                $s2 = (short) 90;
                                $s3 = (short) 0;
                            } else if ($i0 == 2) {
                                $s2 = (short) 180;
                                $s3 = (short) 270;
                            } else if ($i0 == 3) {
                                $s2 = (short) 270;
                                $s3 = (short) 180;
                            }
                            $r4.setRotation($s3);
                            int $i02;
                            if (CameraPreview.this.mInfo.facing == 1) {
                                $i02 = CameraPreview.this.mInfo.orientation;
                                $i02 += $s2;
                                $i0 = (360 - ($i02 % 360)) % 360;
                            } else {
                                $i02 = CameraPreview.this.mInfo.orientation;
                                $i02 -= $s2;
                                $i02 += 360;
                                $i0 = $i02 % 360;
                            }
                            CameraPreview.this.mCamera.setDisplayOrientation($i0);
                            CameraPreview.this.mCamera.setParameters($r4);
                        }
                    }
                } catch (Exception e) {
                }
            }
        };
    }

    public void surfaceDestroyed(SurfaceHolder aHolder) throws  {
        try {
            this.orientationListener.disable();
            if (this.mCamera != null) {
                this.mCamera.stopPreview();
                this.mCamera.release();
                this.mCamera = null;
            }
            if (this.mBufOS != null) {
                releaseBuf();
            }
            this.mPreviewStatus = 0;
        } catch (Exception $r2) {
            Logger.m39e("Error in destroying the surface", $r2);
            if (mCaptureParams.mCallback != null) {
                mCaptureParams.mCallback.onError();
            }
            this.mPreviewStatus = 0;
        } catch (Throwable th) {
            this.mPreviewStatus = 0;
        }
    }

    public void surfaceChanged(SurfaceHolder aHolder, int aFormat, int $i1, int $i2) throws  {
        try {
            if (this.mPreviewStatus != 1) {
                Logger.m43w("Camera preivew is not ready!");
            } else if (mCaptureParams.mImageWidth >= 0 || mCaptureParams.mImageHeight >= 0) {
                Parameters $r5 = this.mCamera.getParameters();
                $r5.setFlashMode(this.mFlashModeValue);
                $r5.setFocusMode(SettingsVoiceSearchActivity.SEARCH_VOICE_LANG_AUTO);
                $r5.setWhiteBalance(SettingsVoiceSearchActivity.SEARCH_VOICE_LANG_AUTO);
                Size $r7 = CompatabilityWrapper.getBestPreviewSize($i1, $i2, $r5);
                $r5.setPreviewSize($r7.width, $r7.height);
                sizeCallback($r7);
                aFormat = mCaptureParams.mImageWidth;
                $r7 = CompatabilityWrapper.getBestFitSize(aFormat, mCaptureParams.mImageHeight, mCaptureParams.mMaximizeCapture, $r5, mCaptureParams.mMaxCaptureSize);
                $r5.setPictureSize($r7.width, $r7.height);
                $r8 = new Object[3];
                int $i0 = $r7.width;
                $r8[0] = Integer.valueOf($i0);
                $i0 = $r7.height;
                $r8[1] = Integer.valueOf($i0);
                $i0 = $r7.width * $r7.height;
                aFormat = $i0;
                $r8[2] = Integer.valueOf($i0);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_TEST_PLACES_PHOTO_SIZE, "W|H|MP", String.format("%d|%d|%d", $r8));
                this.mInfo = new CameraInfo();
                Camera.getCameraInfo(0, this.mInfo);
                aFormat = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
                short $s3 = (short) 0;
                short $s4 = (short) 0;
                if (aFormat == 0) {
                    $s3 = (short) 0;
                    $s4 = (short) 90;
                } else if (aFormat == 1) {
                    $s3 = (short) 90;
                    $s4 = (short) 0;
                } else if (aFormat == 2) {
                    $s3 = (short) 180;
                    $s4 = (short) 270;
                } else if (aFormat == 3) {
                    $s3 = (short) 270;
                    $s4 = (short) 180;
                }
                $r5.setRotation($s4);
                if (this.mInfo.facing == 1) {
                    $i0 = this.mInfo.orientation;
                    $i0 += $s3;
                    aFormat = $i0;
                    aFormat = (360 - ($i0 % 360)) % 360;
                } else {
                    $i0 = this.mInfo.orientation;
                    $i0 -= $s3;
                    aFormat = $i0;
                    $i0 += 360;
                    aFormat = $i0 % 360;
                }
                this.mCamera.setDisplayOrientation(aFormat);
                this.mCamera.setParameters($r5);
                this.mCaptureStatus = 0;
                this.mPreviewStatus = 2;
                this.mCamera.startPreview();
            } else {
                Logger.m43w("Requested image dimensions are invalid. Width: " + mCaptureParams.mImageWidth + ". Height: " + mCaptureParams.mImageHeight);
            }
        } catch (Throwable $r2) {
            Logger.m39e("Error in surfaceChanged", $r2);
            $r2.printStackTrace();
            this.mPreviewStatus = 0;
            this.mCaptureStatus = 0;
            if (mCaptureParams.mCallback != null) {
                mCaptureParams.mCallback.onError();
            }
        }
    }

    public void onOrientationChanged(int orientation) throws  {
    }

    public boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
        boolean $z0 = false;
        switch ($i0) {
            case 23:
            case 27:
                if (this.mCaptureStatus == 0) {
                    capture();
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

    public void capture() throws  {
        try {
            this.mCaptureStatus = 1;
            this.mCamera.cancelAutoFocus();
            this.mCamera.autoFocus(new C17092());
            postDelayed(new C17103(), 5000);
        } catch (Exception $r1) {
            Logger.m39e("Error in capturing the picture", $r1);
            this.mPreviewStatus = 0;
            this.mCaptureStatus = 0;
            callOnCaptureListener(false);
        }
    }

    public void reset() throws  {
        this.mCaptureStatus = 0;
        this.mPreviewStatus = 2;
        mCaptureParams.mCaptureCallbackActive = true;
        if (this.mCamera != null) {
            this.mCamera.startPreview();
        }
    }

    public void focus(float $f0, float $f1) throws  {
        if (this.mCamera != null) {
            this.mCamera.cancelAutoFocus();
            Parameters $r4 = this.mCamera.getParameters();
            if ($r4.getMaxNumFocusAreas() > 0) {
                ArrayList $r2 = new ArrayList(1);
                Rect $r1 = new Rect();
                $r1.left = Math.max(-1000, ((int) (2000.0f * $f0)) - 1050);
                $r1.right = Math.min(1000, ((int) (2000.0f * $f0)) - 950);
                $r1.top = Math.max(-1000, ((int) (2000.0f * $f1)) - 1050);
                $r1.bottom = Math.min(1000, ((int) (2000.0f * $f1)) - 950);
                $r2.add(new Area($r1, 1000));
                $r4.setFocusMode(SettingsVoiceSearchActivity.SEARCH_VOICE_LANG_AUTO);
                $r4.setFocusAreas($r2);
                if ($r4.getMaxNumMeteringAreas() > 0) {
                    $r4.setMeteringAreas($r2);
                }
            }
            try {
                this.mCamera.autoFocus(null);
            } catch (Exception e) {
            }
        }
    }

    public void focus() throws  {
        if (this.mCamera != null) {
            this.mCamera.cancelAutoFocus();
            Parameters $r2 = this.mCamera.getParameters();
            if ($r2.getMaxNumFocusAreas() > 0) {
                $r2.setFocusAreas(null);
            }
            try {
                this.mCamera.autoFocus(null);
            } catch (Exception e) {
            }
        }
    }

    public void setCaptureRect(Float $r1, Float $r2, Float $r3, Float $r4) throws  {
        this.mWidth = $r3.floatValue();
        this.mLeft = $r1.floatValue();
        this.mTop = $r2.floatValue();
        this.mHeight = $r4.floatValue();
    }

    private void callOnCaptureListener(boolean $z0) throws  {
        if (mCaptureParams.mCallback != null && mCaptureParams.mCaptureCallbackActive) {
            mCaptureParams.mCaptureCallbackActive = false;
            mCaptureParams.mCallback.onCapture($z0);
        }
    }

    private void sizeCallback(Size $r1) throws  {
        if (mCaptureParams.mCallback != null) {
            mCaptureParams.mCallback.onSize($r1);
        }
    }

    public boolean getPreviewActive() throws  {
        return this.mPreviewStatus == 2;
    }

    public boolean getCaptureStatus() throws  {
        return this.mCaptureStatus == 2;
    }

    public static void CaptureConfig(int $i0, int $i1, boolean $z0, int $i2, String $r0, String $r1, int $i3, CameraCallbacks $r2) throws  {
        mCaptureParams.setImageWidth($i0);
        mCaptureParams.setImageHeight($i1);
        mCaptureParams.setImageQuality($i2);
        mCaptureParams.setImageFolder($r0);
        mCaptureParams.setImageFile($r1);
        mCaptureParams.setCallback($r2);
        mCaptureParams.setMaximizeCapture($z0);
        mCaptureParams.setMaxCaptureSize($i3);
    }

    private void compressToBuffer(Bitmap $r1, CompressFormat $r2) throws  {
        synchronized (this.mBufOS) {
            this.mBufOS.reset();
            $r1.compress($r2, mCaptureParams.mImageQuality, this.mBufOS);
        }
    }

    public void saveToFile() throws  {
        this = this;
        if (this.mBufOS.size() > 0) {
            try {
                String $r6 = new String(mCaptureParams.mImageFolder);
                File $r3 = new File(($r6 + String.valueOf("/")) + new String(mCaptureParams.mImageFile));
                if ($r3.getParentFile() != null) {
                    $r3.getParentFile().mkdirs();
                }
                FileOutputStream $r2 = new FileOutputStream($r3);
                FileChannel $r11 = $r2.getChannel();
                ByteArrayOutputStream byteArrayOutputStream = this.mBufOS;
                this = this;
                synchronized (byteArrayOutputStream) {
                    $r11.write(ByteBuffer.wrap(this.mBufOS.toByteArray()));
                }
                $r2.close();
            } catch (Exception $r1) {
                Logger.m39e("Error in writing the file to the disk. ", $r1);
                $r1.printStackTrace();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void releaseBuf() throws  {
        /*
        r6 = this;
        r0 = r6.mBufOS;
        monitor-enter(r0);
        r1 = r6.mBufOS;	 Catch:{ Exception -> 0x000d }
        r1.close();	 Catch:{ Exception -> 0x000d }
        r2 = 0;
        r6.mBufOS = r2;	 Catch:{ Exception -> 0x000d }
    L_0x000b:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0017 }
        return;
    L_0x000d:
        r3 = move-exception;
        r4 = "Cannot release the buffer. ";
        com.waze.Logger.m39e(r4, r3);	 Catch:{ Throwable -> 0x0017 }
        r3.printStackTrace();	 Catch:{ Throwable -> 0x0017 }
        goto L_0x000b;
    L_0x0017:
        r5 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0017 }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.ifs.ui.CameraPreview.releaseBuf():void");
    }

    public int getBufSize() throws  {
        int $i0;
        synchronized (this.mBufOS) {
            $i0 = this.mBufOS.size();
        }
        return $i0;
    }

    public byte[] getCaptureBuffer() throws  {
        byte[] $r2;
        synchronized (this.mBufOS) {
            $r2 = this.mBufOS.toByteArray();
        }
        return $r2;
    }

    public int[] getThumbnail(int $i0, int $i1) throws  {
        Paint $r3 = new Paint();
        if (this.mBitmapOut == null) {
            return null;
        }
        int $i3;
        int $i4;
        Bitmap $r4;
        if (!this.mPreserveWholeImage) {
            $i3 = $i0;
            $i4 = (mCaptureParams.mImageHeight * $i0) / mCaptureParams.mImageWidth;
        } else if ($i0 / mCaptureParams.mImageWidth < $i1 / mCaptureParams.mImageHeight) {
            $i3 = $i0;
            $i4 = ($i1 * $i0) / mCaptureParams.mImageWidth;
        } else {
            $i4 = $i1;
            $i3 = ($i1 * $i0) / mCaptureParams.mImageHeight;
        }
        synchronized (this.mBufOS) {
            $r4 = Bitmap.createScaledBitmap(this.mBitmapOut, $i3, $i4, true);
        }
        if ($i4 > $i1) {
            int $i2 = ($i4 - $i1) / 2;
            new Canvas($r4).clipRect(0, $i2 - 1, $r4.getWidth() - 1, $r4.getHeight() - $i2);
        }
        Bitmap $r9 = Bitmap.createBitmap($i0, $i1, Config.ARGB_8888);
        float $f0 = (float) (($i0 - $i3) / 2);
        float $f1 = (float) (($i1 - $i4) / 2);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        Canvas canvas = new Canvas($r9);
        canvas.drawARGB(0, 0, 0, 0);
        $r3.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap($r4, $f0, $f1, $r3);
        int[] $r5 = new int[($i1 * $i0)];
        $r9.getPixels($r5, 0, $r9.getWidth(), 0, 0, $r9.getWidth(), $r9.getHeight());
        $r4.recycle();
        $r9.recycle();
        return $r5;
    }

    public void setFlash(FlashMode $r1) throws  {
        this.mFlashModeValue = null;
        if ($r1 == FlashMode.Off) {
            this.mFlashModeValue = LayoutManager.FRIENDS_CONTROL_MODE_OFF;
        } else if ($r1 == FlashMode.On) {
            this.mFlashModeValue = "on";
        } else if ($r1 == FlashMode.Auto) {
            this.mFlashModeValue = SettingsVoiceSearchActivity.SEARCH_VOICE_LANG_AUTO;
        } else {
            return;
        }
        if (this.mCamera != null) {
            try {
                Parameters $r2 = this.mCamera.getParameters();
                $r2.setFlashMode(this.mFlashModeValue);
                this.mCamera.setParameters($r2);
            } catch (Exception e) {
            }
        }
    }
}
