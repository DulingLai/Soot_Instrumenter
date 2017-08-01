package com.abaltatech.weblinkserver;

import android.annotation.SuppressLint;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecList;
import android.os.Build.VERSION;
import com.abaltatech.mcs.logger.MCSLogger;
import com.waze.strings.DisplayStrings;

@SuppressLint({"NewApi"})
class DeviceH264EncoderSettings {
    public static final String BOARD;
    public static final MediaCodecInfo CODEC_INFO;
    public static final int COLOR_FORMAT;
    public static final String HARDWARE;
    public static final String MANUFACTURER;
    private static final int NO_UV_PLANE_OFFSET = 0;
    private static final String TAG = "DeviceH264EncoderSettings";
    private static final int TYPICAL_UV_PLANE_OFFSET = 1024;
    private static final String VIDEO_FORMAT = "video/avc";

    public static class EncoderInfo {
        public boolean m_convertARGBtoABGR = false;
        public int m_height = 0;
        public int m_sliceHeight = 0;
        public int m_stride = 0;
        public int m_uvPlaneOffset = 0;
        public int m_width = 0;
    }

    private static class asus {

        public static class flo {
            public static EncoderInfo getEncoderInfo(int $i0, int $i1) throws  {
                int $i2 = VERSION.SDK_INT;
                if (!($i0 == DisplayStrings.DS_EVENT && $i1 == DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO)) {
                    if ($i0 != DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO) {
                        return null;
                    }
                    if ($i1 != 288) {
                        return null;
                    }
                    if ($i2 != 19) {
                        return null;
                    }
                }
                EncoderInfo $r0 = DeviceH264EncoderSettings.getDefaultEncoderInfo($i0, $i1);
                $r0.m_uvPlaneOffset = 0;
                return $r0;
            }
        }

        private asus() throws  {
        }
    }

    private static class htc {

        public static class m7 {
            public static EncoderInfo getEncoderInfo(int $i0, int $i1) throws  {
                if ($i0 != DisplayStrings.DS_EVENT) {
                    return null;
                }
                if ($i1 != DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO) {
                    return null;
                }
                EncoderInfo $r0 = DeviceH264EncoderSettings.getDefaultEncoderInfo($i0, $i1);
                $r0.m_uvPlaneOffset = 1024;
                return $r0;
            }
        }

        private htc() throws  {
        }
    }

    private static class lge {

        public static class hammerhead {
            public static EncoderInfo getEncoderInfo(int $i0, int $i1) throws  {
                if ($i0 != DisplayStrings.DS_EVENT) {
                    return null;
                }
                if ($i1 != DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO) {
                    return null;
                }
                EncoderInfo $r0 = DeviceH264EncoderSettings.getDefaultEncoderInfo($i0, $i1);
                $r0.m_uvPlaneOffset = 0;
                return $r0;
            }
        }

        public static class mako {
            public static EncoderInfo getEncoderInfo(int $i0, int $i1) throws  {
                if ($i0 != DisplayStrings.DS_EVENT) {
                    return null;
                }
                if ($i1 != DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO) {
                    return null;
                }
                EncoderInfo $r0 = DeviceH264EncoderSettings.getDefaultEncoderInfo($i0, $i1);
                $r0.m_uvPlaneOffset = 0;
                return $r0;
            }
        }

        private lge() throws  {
        }
    }

    private static class samsung {

        public static class msm8960 {
            public static EncoderInfo getEncoderInfo(int $i0, int $i1) throws  {
                if ($i0 != DisplayStrings.DS_EVENT) {
                    return null;
                }
                if ($i1 != DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO) {
                    return null;
                }
                EncoderInfo $r0 = DeviceH264EncoderSettings.getDefaultEncoderInfo($i0, $i1);
                $r0.m_uvPlaneOffset = 1024;
                return $r0;
            }
        }

        public static class smdk4x12 {
            public static EncoderInfo getEncoderInfo(int $i0, int $i1) throws  {
                EncoderInfo $r0 = DeviceH264EncoderSettings.getDefaultEncoderInfo($i0, $i1);
                if ($r0 == null) {
                    return $r0;
                }
                $r0.m_convertARGBtoABGR = true;
                if ($i0 != DisplayStrings.DS_EVENT || $i1 != DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO) {
                    return $r0;
                }
                $r0.m_uvPlaneOffset = 0;
                return $r0;
            }
        }

        private samsung() throws  {
        }
    }

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:34:0x0123 in {6, 12, 23, 25, 28, 30, 36, 38, 43} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4 = 3;
        r3 = new int[r4];
        r4 = 0;
        r5 = 21;
        r3[r4] = r5;
        r4 = 1;
        r5 = 19;
        r3[r4] = r5;
        r4 = 2;
        r5 = 2130706688; // 0x7f000100 float:1.7014638E38 double:1.052708976E-314;
        r3[r4] = r5;
        r6 = android.os.Build.BOARD;
        r7 = java.util.Locale.US;
        r6 = r6.toLowerCase(r7);
        r8 = android.os.Build.MANUFACTURER;
        r7 = java.util.Locale.US;
        r8 = r8.toLowerCase(r7);
        r9 = android.os.Build.HARDWARE;
        r7 = java.util.Locale.US;
        r9 = r9.toLowerCase(r7);
        r10 = 0;
        r11 = -1;
        r12 = android.os.Build.VERSION.SDK_INT;
        r4 = 16;
        if (r12 < r4) goto L_0x00d9;
    L_0x0033:
        r12 = android.media.MediaCodecList.getCodecCount();	 Catch:{ Exception -> 0x0169 }
        r13 = 0;
    L_0x0038:
        if (r13 >= r12) goto L_0x00d9;	 Catch:{ Exception -> 0x0169 }
    L_0x003a:
        r14 = android.media.MediaCodecList.getCodecInfoAt(r13);	 Catch:{ Exception -> 0x0169 }
        r15 = r14.getName();	 Catch:{ Exception -> 0x0169 }
        r10 = 0;
        r11 = -1;	 Catch:{ Exception -> 0x0169 }
        r16 = r14.isEncoder();	 Catch:{ Exception -> 0x0169 }
        if (r16 != 0) goto L_0x004d;	 Catch:{ Exception -> 0x0169 }
    L_0x004a:
        r13 = r13 + 1;
        goto L_0x0038;
    L_0x004d:
        r17 = "OMX.";	 Catch:{ Exception -> 0x0169 }
        r0 = r17;	 Catch:{ Exception -> 0x0169 }
        r16 = r15.startsWith(r0);	 Catch:{ Exception -> 0x0169 }
        if (r16 == 0) goto L_0x004a;	 Catch:{ Exception -> 0x0169 }
    L_0x0057:
        r17 = "OMX.SEC.avc.enc";	 Catch:{ Exception -> 0x0169 }
        r0 = r17;	 Catch:{ Exception -> 0x0169 }
        r16 = r15.equals(r0);	 Catch:{ Exception -> 0x0169 }
        if (r16 != 0) goto L_0x004a;	 Catch:{ Exception -> 0x0169 }
    L_0x0061:
        r18 = r14.getSupportedTypes();	 Catch:{ Exception -> 0x0169 }
        r19 = 0;	 Catch:{ Exception -> 0x0169 }
    L_0x0067:
        r0 = r18;	 Catch:{ Exception -> 0x0169 }
        r0 = r0.length;	 Catch:{ Exception -> 0x0169 }
        r20 = r0;	 Catch:{ Exception -> 0x0169 }
        r0 = r19;	 Catch:{ Exception -> 0x0169 }
        r1 = r20;	 Catch:{ Exception -> 0x0169 }
        if (r0 >= r1) goto L_0x0080;	 Catch:{ Exception -> 0x0169 }
    L_0x0072:
        r15 = r18[r19];	 Catch:{ Exception -> 0x0169 }
        r17 = "video/avc";	 Catch:{ Exception -> 0x0169 }
        r0 = r17;	 Catch:{ Exception -> 0x0169 }
        r16 = r15.equalsIgnoreCase(r0);	 Catch:{ Exception -> 0x0169 }
        if (r16 == 0) goto L_0x0166;
    L_0x007f:
        r10 = r14;
    L_0x0080:
        if (r10 == 0) goto L_0x004a;	 Catch:{ Exception -> 0x0169 }
    L_0x0082:
        r17 = "video/avc";	 Catch:{ Exception -> 0x0169 }
        r0 = r17;	 Catch:{ Exception -> 0x0169 }
        r21 = r10.getCapabilitiesForType(r0);	 Catch:{ Exception -> 0x0169 }
        r22 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0169 }
        r0 = r22;	 Catch:{ Exception -> 0x0169 }
        r0.<init>();	 Catch:{ Exception -> 0x0169 }
        r17 = "Found ";	 Catch:{ Exception -> 0x0169 }
        r0 = r22;	 Catch:{ Exception -> 0x0169 }
        r1 = r17;	 Catch:{ Exception -> 0x0169 }
        r22 = r0.append(r1);	 Catch:{ Exception -> 0x0169 }
        r15 = r10.getName();	 Catch:{ Exception -> 0x0169 }
        r0 = r22;	 Catch:{ Exception -> 0x0169 }
        r22 = r0.append(r15);	 Catch:{ Exception -> 0x0169 }
        r17 = " supporting ";	 Catch:{ Exception -> 0x0169 }
        r0 = r22;	 Catch:{ Exception -> 0x0169 }
        r1 = r17;	 Catch:{ Exception -> 0x0169 }
        r22 = r0.append(r1);	 Catch:{ Exception -> 0x0169 }
        r17 = "video/avc";	 Catch:{ Exception -> 0x0169 }
        r0 = r22;	 Catch:{ Exception -> 0x0169 }
        r1 = r17;	 Catch:{ Exception -> 0x0169 }
        r22 = r0.append(r1);	 Catch:{ Exception -> 0x0169 }
        r0 = r22;	 Catch:{ Exception -> 0x0169 }
        r15 = r0.toString();	 Catch:{ Exception -> 0x0169 }
        r17 = "DeviceH264EncoderSettings";	 Catch:{ Exception -> 0x0169 }
        r0 = r17;	 Catch:{ Exception -> 0x0169 }
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r15);	 Catch:{ Exception -> 0x0169 }
        r0 = r21;	 Catch:{ Exception -> 0x0169 }
        r0 = r0.colorFormats;	 Catch:{ Exception -> 0x0169 }
        r23 = r0;	 Catch:{ Exception -> 0x0169 }
        r19 = getBestColorFormat(r3, r0);	 Catch:{ Exception -> 0x0169 }
        r11 = r19;
        r4 = -1;
        r0 = r19;
        if (r0 == r4) goto L_0x004a;
    L_0x00d9:
        MANUFACTURER = r8;
        BOARD = r6;
        HARDWARE = r9;
        CODEC_INFO = r10;
        COLOR_FORMAT = r11;
        goto L_0x00e7;
    L_0x00e4:
        goto L_0x0067;
    L_0x00e7:
        r10 = CODEC_INFO;
        if (r10 == 0) goto L_0x017a;
    L_0x00eb:
        r22 = new java.lang.StringBuilder;
        r0 = r22;
        r0.<init>();
        r17 = "Selected H264 Encoder settings for manu=";
        r0 = r22;
        r1 = r17;
        r22 = r0.append(r1);
        r6 = MANUFACTURER;
        r0 = r22;
        r22 = r0.append(r6);
        r17 = " board=";
        r0 = r22;
        r1 = r17;
        r22 = r0.append(r1);
        r6 = BOARD;
        r0 = r22;
        r22 = r0.append(r6);
        r17 = " hardware=";
        r0 = r22;
        r1 = r17;
        r22 = r0.append(r1);
        r6 = HARDWARE;
        goto L_0x012a;
        goto L_0x0127;
    L_0x0124:
        goto L_0x00e4;
    L_0x0127:
        goto L_0x00d9;
    L_0x012a:
        r0 = r22;
        r22 = r0.append(r6);
        r17 = " : codec_info=";
        r0 = r22;
        r1 = r17;
        r22 = r0.append(r1);
        r10 = CODEC_INFO;
        r6 = r10.getName();
        r0 = r22;
        r22 = r0.append(r6);
        r17 = " colorFormat=";
        r0 = r22;
        r1 = r17;
        r22 = r0.append(r1);
        r11 = COLOR_FORMAT;
        r0 = r22;
        r22 = r0.append(r11);
        r0 = r22;
        r6 = r0.toString();
        r17 = "DeviceH264EncoderSettings";
        r0 = r17;
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r6);
        return;
    L_0x0166:
        r19 = r19 + 1;
        goto L_0x0124;
    L_0x0169:
        r24 = move-exception;
        r17 = "DeviceH264EncoderSettings";
        r25 = "Exception raised";
        r0 = r17;
        r1 = r25;
        r2 = r24;
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r1, r2);
        r10 = 0;
        r11 = -1;
        goto L_0x0127;
    L_0x017a:
        r17 = "DeviceH264EncoderSettings";
        r25 = "H264 Encoder is not supported";
        r0 = r17;
        r1 = r25;
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r1);
        LogMediaCodecs();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.DeviceH264EncoderSettings.<clinit>():void");
    }

    private static com.abaltatech.weblinkserver.DeviceH264EncoderSettings.EncoderInfo getDefaultEncoderInfo(int r6, int r7) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:19:0x006e in {2, 4, 7, 13, 17, 18, 20, 22, 27, 31, 35} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = new com.abaltatech.weblinkserver.DeviceH264EncoderSettings$EncoderInfo;
        r0.<init>();
        r0.m_width = r6;
        r0.m_height = r7;
        r1 = CODEC_INFO;
        r2 = r1.getName();
        r4 = "OMX.TI.DUCATI1.VIDEO.H264E";
        r3 = r2.equals(r4);
        if (r3 == 0) goto L_0x0026;
    L_0x0017:
        r6 = r0.m_width;
        r6 = r6 & -16;
        r0.m_width = r6;
    L_0x001d:
        r6 = r0.m_width;
        r0.m_stride = r6;
        r6 = r0.m_height;
        r0.m_sliceHeight = r6;
        return r0;
    L_0x0026:
        r1 = CODEC_INFO;
        r2 = r1.getName();
        r4 = "OMX.Nvidia.";
        r3 = r2.startsWith(r4);
        if (r3 == 0) goto L_0x0041;
    L_0x0034:
        r6 = r0.m_width;
        r6 = r6 & -16;
        r0.m_width = r6;
        r6 = r0.m_height;
        r6 = r6 & -16;
        r0.m_height = r6;
        goto L_0x001d;
    L_0x0041:
        r1 = CODEC_INFO;
        r2 = r1.getName();
        r4 = "OMX.qcom.video.encoder.avc";
        r3 = r2.equals(r4);
        if (r3 == 0) goto L_0x001d;
    L_0x004f:
        r6 = r0.m_width;
        r6 = r6 & -16;
        r0.m_width = r6;
        r6 = r0.m_width;
        r5 = 800; // 0x320 float:1.121E-42 double:3.953E-321;
        if (r6 != r5) goto L_0x0072;
    L_0x005b:
        r6 = r0.m_height;
        goto L_0x0061;
    L_0x005e:
        goto L_0x001d;
    L_0x0061:
        r5 = 480; // 0x1e0 float:6.73E-43 double:2.37E-321;
        if (r6 != r5) goto L_0x0072;
    L_0x0065:
        goto L_0x0069;
    L_0x0066:
        goto L_0x001d;
    L_0x0069:
        r5 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0.m_uvPlaneOffset = r5;
        goto L_0x001d;
        goto L_0x0072;
    L_0x006f:
        goto L_0x001d;
    L_0x0072:
        r6 = r0.m_width;
        goto L_0x0078;
    L_0x0075:
        goto L_0x001d;
    L_0x0078:
        r5 = 480; // 0x1e0 float:6.73E-43 double:2.37E-321;
        if (r6 != r5) goto L_0x0087;
    L_0x007c:
        r6 = r0.m_height;
        r5 = 800; // 0x320 float:1.121E-42 double:3.953E-321;
        if (r6 != r5) goto L_0x0087;
    L_0x0082:
        r5 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0.m_uvPlaneOffset = r5;
        goto L_0x001d;
    L_0x0087:
        r6 = r0.m_width;
        r5 = 432; // 0x1b0 float:6.05E-43 double:2.134E-321;
        if (r6 != r5) goto L_0x0093;
    L_0x008d:
        r6 = r0.m_height;
        r5 = 720; // 0x2d0 float:1.009E-42 double:3.557E-321;
        if (r6 == r5) goto L_0x009f;
    L_0x0093:
        r6 = r0.m_width;
        r5 = 720; // 0x2d0 float:1.009E-42 double:3.557E-321;
        if (r6 != r5) goto L_0x00a4;
    L_0x0099:
        r6 = r0.m_height;
        r5 = 432; // 0x1b0 float:6.05E-43 double:2.134E-321;
        if (r6 != r5) goto L_0x00a4;
    L_0x009f:
        r5 = 256; // 0x100 float:3.59E-43 double:1.265E-321;
        r0.m_uvPlaneOffset = r5;
        goto L_0x005e;
    L_0x00a4:
        r6 = r0.m_width;
        r5 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r6 != r5) goto L_0x00b5;
    L_0x00aa:
        r6 = r0.m_height;
        r5 = 240; // 0xf0 float:3.36E-43 double:1.186E-321;
        if (r6 != r5) goto L_0x00b5;
    L_0x00b0:
        r5 = 256; // 0x100 float:3.59E-43 double:1.265E-321;
        r0.m_uvPlaneOffset = r5;
        goto L_0x0066;
    L_0x00b5:
        r6 = r0.m_width;
        r5 = 480; // 0x1e0 float:6.73E-43 double:2.37E-321;
        if (r6 != r5) goto L_0x00c6;
    L_0x00bb:
        r6 = r0.m_height;
        r5 = 288; // 0x120 float:4.04E-43 double:1.423E-321;
        if (r6 != r5) goto L_0x00c6;
    L_0x00c1:
        r5 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0.m_uvPlaneOffset = r5;
        goto L_0x006f;
    L_0x00c6:
        r5 = 0;
        r0.m_uvPlaneOffset = r5;
        goto L_0x0075;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.DeviceH264EncoderSettings.getDefaultEncoderInfo(int, int):com.abaltatech.weblinkserver.DeviceH264EncoderSettings$EncoderInfo");
    }

    DeviceH264EncoderSettings() throws  {
    }

    public static void LogMediaCodecs() throws  {
        int[] $r0 = new int[]{21, 19, 2130706688};
        if (VERSION.SDK_INT >= 16) {
            int $i0 = MediaCodecList.getCodecCount();
            MCSLogger.log(TAG, "Logging all supported media codecs (count=" + $i0 + ")");
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                MediaCodecInfo $r5 = null;
                MediaCodecInfo $r6 = MediaCodecList.getCodecInfoAt($i1);
                String $r4 = $r6.getName();
                MCSLogger.log(TAG, "MediaCodecInfo[" + $i1 + "], name =" + $r4);
                if (!$r6.isEncoder()) {
                    MCSLogger.log(TAG, "MediaCodecInfo[" + $i1 + "] is not encoder, skipping");
                } else if ($r4.startsWith("OMX.")) {
                    try {
                        if ($r4.equals("OMX.SEC.avc.enc")) {
                            MCSLogger.log(TAG, "MediaCodecInfo[" + $i1 + "] is black listed encoder, skipping");
                        } else {
                            int $i2;
                            String[] $r8 = $r6.getSupportedTypes();
                            for ($i2 = 0; $i2 < $r8.length; $i2++) {
                                MCSLogger.log(TAG, "MediaCodecInfo[" + $i1 + "], supportedType[" + $i2 + "] =" + $r8[$i2]);
                                if ($r8[$i2].equalsIgnoreCase(VIDEO_FORMAT)) {
                                    $r5 = $r6;
                                }
                            }
                            if ($r5 != null) {
                                CodecCapabilities $r9 = $r5.getCapabilitiesForType(VIDEO_FORMAT);
                                int[] $r2 = $r9.colorFormats;
                                for ($i2 = 0; $i2 < $r2.length; $i2++) {
                                    MCSLogger.log(TAG, "MediaCodecInfo[" + $i1 + "], colorFormats[" + $i2 + "] =" + $r2[$i2]);
                                }
                                int[] $r22 = $r9.colorFormats;
                                $i2 = getBestColorFormat($r0, $r22);
                                if ($i2 != -1) {
                                    MCSLogger.log(TAG, "MediaCodecInfo[" + $i1 + "], valid matched color format = " + $i2);
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                    } catch (Exception $r1) {
                        MCSLogger.log(TAG, "Exception raised while mediacodec logging", $r1);
                        return;
                    }
                } else {
                    MCSLogger.log(TAG, "MediaCodecInfo[" + $i1 + "] is not OMX.* based, skipping");
                }
            }
            return;
        }
        MCSLogger.log(TAG, "VERSION.SDK_INT < 16 , cannot access MediaCodec");
    }

    public static EncoderInfo getEncoderInfo(int $i0, int $i1) throws  {
        EncoderInfo $r0 = null;
        if (CODEC_INFO == null) {
            return null;
        }
        if (MANUFACTURER.equals("lge") && BOARD.equals("mako")) {
            $r0 = mako.getEncoderInfo($i0, $i1);
        } else if (MANUFACTURER.equals("lge") && BOARD.equals("hammerhead")) {
            $r0 = hammerhead.getEncoderInfo($i0, $i1);
        } else if (MANUFACTURER.equals("asus") && BOARD.equals("flo")) {
            $r0 = flo.getEncoderInfo($i0, $i1);
        } else if (MANUFACTURER.equals("samsung") && BOARD.equals("smdk4x12")) {
            $r0 = smdk4x12.getEncoderInfo($i0, $i1);
        } else if (MANUFACTURER.equals("samsung") && BOARD.equals("msm8960")) {
            $r0 = msm8960.getEncoderInfo($i0, $i1);
        } else if (MANUFACTURER.equals("htc") && BOARD.equals("m7")) {
            $r0 = m7.getEncoderInfo($i0, $i1);
        }
        if ($r0 == null) {
            return getDefaultEncoderInfo($i0, $i1);
        }
        return $r0;
    }

    private static int getBestColorFormat(int[] $r0, int[] $r1) throws  {
        int $i1 = -1;
        int $i2 = 0;
        while ($i1 == -1 && $i2 < $r0.length) {
            int $i0 = $r0[$i2];
            int $i3 = 0;
            while ($i1 == -1 && $i3 < $r1.length) {
                if ($i0 == $r1[$i3]) {
                    $i1 = $i0;
                }
                $i3++;
            }
            $i2++;
        }
        return $i1;
    }
}
