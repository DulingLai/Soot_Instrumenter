package com.abaltatech.weblinkserver;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.view.Surface;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.weblink.core.frameencoding.EncoderUtils;
import com.abaltatech.weblink.core.frameencoding.IFrameEncodedHandler;
import com.abaltatech.weblink.core.frameencoding.IFrameEncoder;
import com.abaltatech.weblinkserver.DeviceH264EncoderSettings.EncoderInfo;
import com.google.android.gms.common.Scopes;
import com.waze.NativeLocation;
import java.nio.ByteBuffer;
import java.util.Map;

@SuppressLint({"NewApi"})
public class FrameEncoderH264 implements IFrameEncoder, IFrameEncoderSurface {
    private static final Paint BMP_PAINT = new Paint();
    private static final int DEF_BIT_RATE = 3500000;
    private static final int DEF_FRAME_RATE = 2;
    private static final int MAX_FAILED_ENCODER_ATTEMPTS = 2;
    private static final String TAG = "FrameEncoder_H264";
    private static final String VIDEO_FORMAT = "video/avc";
    private static final long kTimeOutUs = 500000;
    private Bitmap m_backBuffer;
    private BufferInfo m_bufferInfo = null;
    private String m_encParams;
    private MediaCodec m_encoder = null;
    private EncoderInfo m_encoderInfo = null;
    private ByteBuffer[] m_encoderInputBuffers = null;
    private ByteBuffer[] m_encoderOutputBuffers = null;
    private int m_failedEncoderAttempts = 0;
    private int m_frameRate = 0;
    private IFrameEncodedHandler m_handler = null;
    private Surface m_inputSurface;
    private int m_numInputFrames = 0;
    private int m_srcHeight = 0;
    private int m_srcWidth = 0;
    private boolean m_startedWithSurface;
    private boolean m_suspended;

    private static native int convertColorFormat(ByteBuffer byteBuffer, int i, int i2, int i3, int i4, int i5, int i6, ByteBuffer byteBuffer2, int i7) throws ;

    public int getPriority() throws  {
        return 100;
    }

    public int getType() throws  {
        return 2;
    }

    public android.view.Surface startEncodingWithSurface(int r22, int r23, int r24, int r25, java.lang.String r26, com.abaltatech.weblink.core.frameencoding.IFrameEncodedHandler r27) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0187 in list []
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
        r21 = this;
        r2 = 1280; // 0x500 float:1.794E-42 double:6.324E-321;
        r0 = r24;
        if (r0 > r2) goto L_0x000c;
    L_0x0006:
        r2 = 1280; // 0x500 float:1.794E-42 double:6.324E-321;
        r0 = r25;
        if (r0 <= r2) goto L_0x0010;
    L_0x000c:
        r24 = r22;
        r25 = r23;
    L_0x0010:
        r3 = com.abaltatech.weblinkserver.DeviceH264EncoderSettings.CODEC_INFO;
        if (r3 == 0) goto L_0x0187;
    L_0x0014:
        if (r27 == 0) goto L_0x0187;
    L_0x0016:
        r4 = 0;
        r0 = r24;	 Catch:{ Exception -> 0x018e }
        r1 = r25;	 Catch:{ Exception -> 0x018e }
        r5 = com.abaltatech.weblinkserver.DeviceH264EncoderSettings.getEncoderInfo(r0, r1);	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r0.m_encoderInfo = r5;	 Catch:{ Exception -> 0x018e }
        r6 = android.os.Build.VERSION.SDK_INT;
        r2 = 18;	 Catch:{ Exception -> 0x018e }
        if (r6 < r2) goto L_0x0059;	 Catch:{ Exception -> 0x018e }
    L_0x0029:
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r5 = r0.m_encoderInfo;	 Catch:{ Exception -> 0x018e }
        if (r5 != 0) goto L_0x0038;	 Catch:{ Exception -> 0x018e }
    L_0x002f:
        r5 = new com.abaltatech.weblinkserver.DeviceH264EncoderSettings$EncoderInfo;	 Catch:{ Exception -> 0x018e }
        r5.<init>();	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r0.m_encoderInfo = r5;	 Catch:{ Exception -> 0x018e }
    L_0x0038:
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r5 = r0.m_encoderInfo;	 Catch:{ Exception -> 0x018e }
        r0 = r24;	 Catch:{ Exception -> 0x018e }
        r5.m_width = r0;	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r5 = r0.m_encoderInfo;	 Catch:{ Exception -> 0x018e }
        r0 = r25;	 Catch:{ Exception -> 0x018e }
        r5.m_height = r0;	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r5 = r0.m_encoderInfo;	 Catch:{ Exception -> 0x018e }
        r0 = r24;	 Catch:{ Exception -> 0x018e }
        r5.m_stride = r0;	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r5 = r0.m_encoderInfo;	 Catch:{ Exception -> 0x018e }
        r0 = r25;	 Catch:{ Exception -> 0x018e }
        r5.m_sliceHeight = r0;	 Catch:{ Exception -> 0x018e }
        r4 = 1;	 Catch:{ Exception -> 0x018e }
    L_0x0059:
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r5 = r0.m_encoderInfo;	 Catch:{ Exception -> 0x018e }
        if (r5 == 0) goto L_0x0187;
    L_0x005f:
        if (r4 == 0) goto L_0x0187;	 Catch:{ Exception -> 0x018e }
    L_0x0061:
        r7 = "====>";	 Catch:{ Exception -> 0x018e }
        r8 = "====> STARTING Encoding";	 Catch:{ Exception -> 0x018e }
        com.abaltatech.mcs.logger.MCSLogger.log(r7, r8);	 Catch:{ Exception -> 0x018e }
        r0 = r26;	 Catch:{ Exception -> 0x018e }
        r9 = com.abaltatech.weblink.core.frameencoding.EncoderUtils.parseEncoderParams(r0);	 Catch:{ Exception -> 0x018e }
        r7 = "bitrate";	 Catch:{ Exception -> 0x018e }
        r2 = 3500000; // 0x3567e0 float:4.904545E-39 double:1.72923E-317;	 Catch:{ Exception -> 0x018e }
        r6 = com.abaltatech.weblink.core.frameencoding.EncoderUtils.getParam(r9, r7, r2);	 Catch:{ Exception -> 0x018e }
        r7 = "maxKeyFrameInterval";	 Catch:{ Exception -> 0x018e }
        r2 = 2;	 Catch:{ Exception -> 0x018e }
        r10 = com.abaltatech.weblink.core.frameencoding.EncoderUtils.getParam(r9, r7, r2);	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r0.m_frameRate = r10;	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r10 = r0.m_frameRate;	 Catch:{ Exception -> 0x018e }
        r2 = 2;	 Catch:{ Exception -> 0x018e }
        r10 = java.lang.Math.max(r10, r2);	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r0.m_frameRate = r10;	 Catch:{ Exception -> 0x018e }
        r11 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x018e }
        r11.<init>();	 Catch:{ Exception -> 0x018e }
        r7 = "Create H264 encoder - ";	 Catch:{ Exception -> 0x018e }
        r11 = r11.append(r7);	 Catch:{ Exception -> 0x018e }
        r3 = com.abaltatech.weblinkserver.DeviceH264EncoderSettings.CODEC_INFO;	 Catch:{ Exception -> 0x018e }
        r12 = r3.getName();	 Catch:{ Exception -> 0x018e }
        r11 = r11.append(r12);	 Catch:{ Exception -> 0x018e }
        r12 = r11.toString();	 Catch:{ Exception -> 0x018e }
        r7 = "FrameEncoder_H264";	 Catch:{ Exception -> 0x018e }
        com.abaltatech.mcs.logger.MCSLogger.log(r7, r12);	 Catch:{ Exception -> 0x018e }
        r3 = com.abaltatech.weblinkserver.DeviceH264EncoderSettings.CODEC_INFO;	 Catch:{ Exception -> 0x018e }
        r12 = r3.getName();	 Catch:{ Exception -> 0x018e }
        r13 = android.media.MediaCodec.createByCodecName(r12);	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r0.m_encoder = r13;	 Catch:{ Exception -> 0x018e }
        r7 = "FrameEncoder_H264";	 Catch:{ Exception -> 0x018e }
        r8 = "H264 Encoder created";	 Catch:{ Exception -> 0x018e }
        com.abaltatech.mcs.logger.MCSLogger.log(r7, r8);	 Catch:{ Exception -> 0x018e }
        r7 = "video/avc";	 Catch:{ Exception -> 0x018e }
        r0 = r24;	 Catch:{ Exception -> 0x018e }
        r1 = r25;	 Catch:{ Exception -> 0x018e }
        r14 = android.media.MediaFormat.createVideoFormat(r7, r0, r1);	 Catch:{ Exception -> 0x018e }
        r7 = "bitrate";	 Catch:{ Exception -> 0x018e }
        r14.setInteger(r7, r6);	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r0 = r0.m_frameRate;	 Catch:{ Exception -> 0x018e }
        r24 = r0;	 Catch:{ Exception -> 0x018e }
        r7 = "frame-rate";	 Catch:{ Exception -> 0x018e }
        r0 = r24;	 Catch:{ Exception -> 0x018e }
        r14.setInteger(r7, r0);	 Catch:{ Exception -> 0x018e }
        r7 = "i-frame-interval";	 Catch:{ Exception -> 0x018e }
        r2 = 1;	 Catch:{ Exception -> 0x018e }
        r14.setInteger(r7, r2);	 Catch:{ Exception -> 0x018e }
        r7 = "profile";	 Catch:{ Exception -> 0x018e }
        r2 = 1;	 Catch:{ Exception -> 0x018e }
        r14.setInteger(r7, r2);	 Catch:{ Exception -> 0x018e }
        r7 = "level";	 Catch:{ Exception -> 0x018e }
        r2 = 512; // 0x200 float:7.175E-43 double:2.53E-321;	 Catch:{ Exception -> 0x018e }
        r14.setInteger(r7, r2);	 Catch:{ Exception -> 0x018e }
        r7 = "color-format";	 Catch:{ Exception -> 0x018e }
        r2 = 2130708361; // 0x7f000789 float:1.701803E38 double:1.0527098025E-314;	 Catch:{ Exception -> 0x018e }
        r14.setInteger(r7, r2);	 Catch:{ Exception -> 0x018e }
        r11 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x018e }
        r11.<init>();	 Catch:{ Exception -> 0x018e }
        r7 = "Configuring encoder with input format ";	 Catch:{ Exception -> 0x018e }
        r11 = r11.append(r7);	 Catch:{ Exception -> 0x018e }
        r11 = r11.append(r14);	 Catch:{ Exception -> 0x018e }
        r12 = r11.toString();	 Catch:{ Exception -> 0x018e }
        r7 = "FrameEncoder_H264";	 Catch:{ Exception -> 0x018e }
        com.abaltatech.mcs.logger.MCSLogger.log(r7, r12);	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r13 = r0.m_encoder;	 Catch:{ Exception -> 0x018e }
        r15 = 0;	 Catch:{ Exception -> 0x018e }
        r16 = 0;	 Catch:{ Exception -> 0x018e }
        r2 = 1;	 Catch:{ Exception -> 0x018e }
        r0 = r16;	 Catch:{ Exception -> 0x018e }
        r13.configure(r14, r15, r0, r2);	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r13 = r0.m_encoder;	 Catch:{ Exception -> 0x018e }
        r17 = r13.createInputSurface();	 Catch:{ Exception -> 0x018e }
        r0 = r17;	 Catch:{ Exception -> 0x018e }
        r1 = r21;	 Catch:{ Exception -> 0x018e }
        r1.m_inputSurface = r0;	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r13 = r0.m_encoder;	 Catch:{ Exception -> 0x018e }
        r13.start();	 Catch:{ Exception -> 0x018e }
        r7 = "====>";	 Catch:{ Exception -> 0x018e }
        r8 = "====> STARTED Encoding";	 Catch:{ Exception -> 0x018e }
        com.abaltatech.mcs.logger.MCSLogger.log(r7, r8);	 Catch:{ Exception -> 0x018e }
        r15 = 0;	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r0.m_encoderInputBuffers = r15;	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r13 = r0.m_encoder;	 Catch:{ Exception -> 0x018e }
        r18 = r13.getOutputBuffers();	 Catch:{ Exception -> 0x018e }
        r0 = r18;	 Catch:{ Exception -> 0x018e }
        r1 = r21;	 Catch:{ Exception -> 0x018e }
        r1.m_encoderOutputBuffers = r0;	 Catch:{ Exception -> 0x018e }
        r0 = r27;	 Catch:{ Exception -> 0x018e }
        r1 = r21;	 Catch:{ Exception -> 0x018e }
        r1.m_handler = r0;	 Catch:{ Exception -> 0x018e }
        r0 = r22;	 Catch:{ Exception -> 0x018e }
        r1 = r21;	 Catch:{ Exception -> 0x018e }
        r1.m_srcWidth = r0;	 Catch:{ Exception -> 0x018e }
        r0 = r23;	 Catch:{ Exception -> 0x018e }
        r1 = r21;	 Catch:{ Exception -> 0x018e }
        r1.m_srcHeight = r0;	 Catch:{ Exception -> 0x018e }
        r19 = new android.media.MediaCodec$BufferInfo;	 Catch:{ Exception -> 0x018e }
        r0 = r19;	 Catch:{ Exception -> 0x018e }
        r0.<init>();	 Catch:{ Exception -> 0x018e }
        r0 = r19;	 Catch:{ Exception -> 0x018e }
        r1 = r21;	 Catch:{ Exception -> 0x018e }
        r1.m_bufferInfo = r0;	 Catch:{ Exception -> 0x018e }
        r2 = 0;	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r0.m_numInputFrames = r2;	 Catch:{ Exception -> 0x018e }
        r0 = r26;	 Catch:{ Exception -> 0x018e }
        r1 = r21;	 Catch:{ Exception -> 0x018e }
        r1.m_encParams = r0;	 Catch:{ Exception -> 0x018e }
        r2 = 0;	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r0.m_failedEncoderAttempts = r2;	 Catch:{ Exception -> 0x018e }
        r2 = 1;	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r0.m_startedWithSurface = r2;	 Catch:{ Exception -> 0x018e }
        r2 = 0;	 Catch:{ Exception -> 0x018e }
        r0 = r21;	 Catch:{ Exception -> 0x018e }
        r0.m_suspended = r2;	 Catch:{ Exception -> 0x018e }
    L_0x0187:
        r0 = r21;
        r0 = r0.m_inputSurface;
        r17 = r0;
        return r17;
    L_0x018e:
        r20 = move-exception;
        r7 = "FrameEncoder_H264";
        r8 = "H264 Encoder initialization failed";
        r0 = r20;
        com.abaltatech.mcs.logger.MCSLogger.log(r7, r8, r0);
        r0 = r21;
        r0.stopEncoding();
        goto L_0x0187;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.FrameEncoderH264.startEncodingWithSurface(int, int, int, int, java.lang.String, com.abaltatech.weblink.core.frameencoding.IFrameEncodedHandler):android.view.Surface");
    }

    static {
        BMP_PAINT.setFilterBitmap(true);
    }

    public synchronized boolean startEncoding(int $i0, int $i1, int $i2, int $i3, String $r1, IFrameEncodedHandler $r2) throws  {
        boolean $z0;
        $z0 = false;
        if ($i2 > 1280 || $i3 > 1280) {
            $i2 = $i0;
            $i3 = $i1;
        }
        if (!(DeviceH264EncoderSettings.CODEC_INFO == null || $r2 == null)) {
            try {
                boolean $z1 = WLPlatformSettings.getCurrentPlatformSettings().getSettingValue("enc.h264.useSurface", true, true);
                boolean $z2 = false;
                this.m_encoderInfo = DeviceH264EncoderSettings.getEncoderInfo($i2, $i3);
                if (VERSION.SDK_INT >= 18 && $z1) {
                    if (this.m_encoderInfo == null) {
                        this.m_encoderInfo = new EncoderInfo();
                    }
                    this.m_encoderInfo.m_width = $i2;
                    this.m_encoderInfo.m_height = $i3;
                    this.m_encoderInfo.m_stride = $i2;
                    this.m_encoderInfo.m_sliceHeight = $i3;
                    $z2 = true;
                }
                if (this.m_encoderInfo != null) {
                    MediaCodec $r10;
                    Map $r7 = EncoderUtils.parseEncoderParams($r1);
                    int $i4 = EncoderUtils.getParam($r7, "bitrate", DEF_BIT_RATE);
                    this.m_frameRate = EncoderUtils.getParam($r7, "maxKeyFrameInterval", 2);
                    this.m_frameRate = Math.max(this.m_frameRate, 2);
                    MCSLogger.log(TAG, "Create H264 encoder - " + DeviceH264EncoderSettings.CODEC_INFO.getName());
                    this.m_encoder = MediaCodec.createByCodecName(DeviceH264EncoderSettings.CODEC_INFO.getName());
                    MCSLogger.log(TAG, "H264 Encoder created");
                    MediaFormat $r11 = MediaFormat.createVideoFormat(VIDEO_FORMAT, $i2, $i3);
                    $r11.setInteger("bitrate", $i4);
                    MediaFormat mediaFormat = $r11;
                    mediaFormat.setInteger("frame-rate", this.m_frameRate);
                    $r11.setInteger("i-frame-interval", 1);
                    $r11.setInteger(Scopes.PROFILE, 1);
                    $r11.setInteger("level", 512);
                    if ($z2) {
                        $r11.setInteger("color-format", 2130708361);
                    } else {
                        $r11.setInteger("color-format", DeviceH264EncoderSettings.COLOR_FORMAT);
                        mediaFormat = $r11;
                        mediaFormat.setInteger("stride", this.m_encoderInfo.m_stride);
                        mediaFormat = $r11;
                        mediaFormat.setInteger("slice-height", this.m_encoderInfo.m_sliceHeight);
                    }
                    MCSLogger.log(TAG, "Configuring encoder with input format " + $r11);
                    this.m_encoder.configure($r11, null, null, 1);
                    if ($z2) {
                        $r10 = this.m_encoder;
                        this.m_inputSurface = $r10.createInputSurface();
                        this.m_backBuffer = Bitmap.createBitmap($i0, $i1, Config.ARGB_8888);
                    }
                    $r10 = this.m_encoder;
                    $r10.start();
                    MCSLogger.log("====>", "====> STARTED Encoding");
                    $r10 = this.m_encoder;
                    this.m_encoderInputBuffers = $r10.getInputBuffers();
                    $r10 = this.m_encoder;
                    this.m_encoderOutputBuffers = $r10.getOutputBuffers();
                    this.m_handler = $r2;
                    this.m_srcWidth = $i0;
                    this.m_srcHeight = $i1;
                    this.m_bufferInfo = new BufferInfo();
                    this.m_numInputFrames = 0;
                    this.m_encParams = $r1;
                    this.m_failedEncoderAttempts = 0;
                    this.m_startedWithSurface = false;
                    this.m_suspended = false;
                    $z0 = true;
                }
            } catch (Throwable $r3) {
                MCSLogger.log(TAG, "H264 Encoder initialization failed", $r3);
                stopEncoding();
            }
        }
        return $z0;
    }

    public synchronized void stopEncoding() throws  {
        MediaCodec $r1 = this.m_encoder;
        this.m_startedWithSurface = false;
        this.m_suspended = false;
        if ($r1 != null) {
            this.m_encoder = null;
            this.m_encoderInputBuffers = null;
            this.m_encoderOutputBuffers = null;
            if (!this.m_suspended) {
                $r1.stop();
            }
            $r1.release();
            MCSLogger.log("====>", "====> STOPPED Encoding (stopEncoding)");
            MCSLogger.log(TAG, "H264 Encoder stopped");
            this.m_srcWidth = 0;
            this.m_srcHeight = 0;
            this.m_encoderInfo = null;
            this.m_bufferInfo = null;
            this.m_numInputFrames = 0;
            if (this.m_backBuffer != null) {
                this.m_backBuffer.recycle();
                this.m_backBuffer = null;
            }
            if (this.m_inputSurface != null) {
                this.m_inputSurface.release();
                this.m_inputSurface = null;
            }
        }
    }

    public boolean encodeSurface() throws  {
        if (this.m_suspended) {
            return false;
        }
        if (this.m_inputSurface == null) {
            return false;
        }
        if (this.m_startedWithSurface) {
            return sendPendingFrame();
        }
        return false;
    }

    public synchronized boolean encodeImage(int $i0, ByteBuffer $r1, boolean isKeyFrame) throws  {
        isKeyFrame = false;
        if (!this.m_suspended && $r1.isDirect() && this.m_encoder != null && ($i0 == 4 || $i0 == 5 || $i0 == 6)) {
            if (this.m_inputSurface != null) {
                Canvas $r5 = this.m_inputSurface.lockCanvas(null);
                $r1.clear();
                if (this.m_encoderInfo.m_convertARGBtoABGR) {
                    WLImageUtils.convertARGBtoABGR($r1, this.m_srcWidth, this.m_srcHeight);
                }
                Bitmap bitmap = this.m_backBuffer;
                Bitmap $r7 = bitmap;
                bitmap.copyPixelsFromBuffer($r1);
                $r5.drawBitmap(this.m_backBuffer, new Rect(0, 0, this.m_srcWidth - 1, this.m_srcHeight - 1), new Rect(0, 0, this.m_encoderInfo.m_width - 1, this.m_encoderInfo.m_height - 1), BMP_PAINT);
                $r1.clear();
                this.m_inputSurface.unlockCanvasAndPost($r5);
                isKeyFrame = true;
            } else {
                $i0 = this.m_encoder.dequeueInputBuffer(kTimeOutUs);
                if ($i0 >= 0) {
                    ByteBuffer $r2 = this.m_encoderInputBuffers[$i0];
                    $r2.clear();
                    int $i1 = convertColorFormat($r1, this.m_srcWidth, this.m_srcHeight, this.m_encoderInfo.m_width, this.m_encoderInfo.m_height, this.m_encoderInfo.m_stride, DeviceH264EncoderSettings.COLOR_FORMAT, $r2, this.m_encoderInfo.m_uvPlaneOffset);
                    if ($i1 > 0) {
                        long $l8 = (this.m_numInputFrames * NativeLocation.FIXED_POINT_FACTOR) / this.m_frameRate;
                        Object obj = $l8;
                        long $l82 = (long) $l8;
                        this.m_numInputFrames++;
                        this.m_encoder.queueInputBuffer($i0, 0, $i1, $l82, 0);
                        isKeyFrame = true;
                    }
                }
            }
            sendPendingFrame();
        }
        return isKeyFrame;
    }

    public static boolean isSupported() throws  {
        return (DeviceH264EncoderSettings.CODEC_INFO == null || DeviceH264EncoderSettings.COLOR_FORMAT == -1) ? false : true;
    }

    public boolean suspend() throws  {
        if (this.m_suspended) {
            return false;
        }
        if (this.m_encoder == null) {
            return false;
        }
        this.m_suspended = true;
        this.m_encoder.stop();
        MCSLogger.log("====>", "====> STOPPED Encoding (suspend, no release)");
        this.m_encoderInputBuffers = null;
        this.m_encoderOutputBuffers = null;
        return true;
    }

    public boolean resume() throws  {
        if (!this.m_suspended) {
            return false;
        }
        if (this.m_encoder == null) {
            return false;
        }
        MCSLogger.log("====>", "====> STARTING Encoding");
        this.m_encoder.start();
        MCSLogger.log("====>", "====> STARTED Encoding");
        this.m_encoderOutputBuffers = this.m_encoder.getOutputBuffers();
        if (this.m_startedWithSurface) {
            this.m_encoderInputBuffers = null;
        } else {
            this.m_encoderInputBuffers = this.m_encoder.getInputBuffers();
        }
        this.m_suspended = false;
        return true;
    }

    public boolean isSuspended() throws  {
        return this.m_suspended;
    }

    private boolean sendPendingFrame() throws  {
        int $i0 = this.m_encoder.dequeueOutputBuffer(this.m_bufferInfo, 0);
        IFrameEncodedHandler $r2;
        EncoderInfo $r7;
        int $i1;
        if ($i0 >= 0) {
            ByteBuffer $r3 = this.m_encoderOutputBuffers[$i0];
            $r3.position(this.m_bufferInfo.offset);
            $r3.limit(this.m_bufferInfo.offset + this.m_bufferInfo.size);
            $r2 = this.m_handler;
            $r7 = this.m_encoderInfo;
            $i1 = $r7.m_width;
            $r7 = this.m_encoderInfo;
            $r2.onFrameEncoded($i1, $r7.m_height, 2, $r3);
            this.m_encoder.releaseOutputBuffer($i0, false);
            this.m_failedEncoderAttempts = 0;
            return true;
        } else if ($i0 == -3) {
            this.m_encoderOutputBuffers = this.m_encoder.getOutputBuffers();
            MCSLogger.log(TAG, "encoder output buffers have changed.");
            return false;
        } else if ($i0 == -2) {
            MCSLogger.log(TAG, "encoder output format has changed to " + this.m_encoder.getOutputFormat());
            return false;
        } else if ($i0 != -1) {
            return false;
        } else {
            this.m_failedEncoderAttempts++;
            if (this.m_startedWithSurface) {
                return false;
            }
            if (this.m_failedEncoderAttempts <= 2) {
                return false;
            }
            $i0 = this.m_srcWidth;
            $i1 = this.m_srcHeight;
            $r7 = this.m_encoderInfo;
            int $i2 = $r7.m_width;
            int $i3 = this.m_encoderInfo;
            EncoderInfo $r72 = $i3;
            int $i32 = $i3.m_height;
            String $r1 = this.m_encParams;
            $r2 = this.m_handler;
            MCSLogger.log("====>", "====> RESTART Encoding");
            stopEncoding();
            startEncoding($i0, $i1, $i2, $i32, $r1, $r2);
            this.m_failedEncoderAttempts = 0;
            return false;
        }
    }
}
