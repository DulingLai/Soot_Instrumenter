package com.abaltatech.weblinkserver;

import android.annotation.SuppressLint;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaExtractor;
import android.os.Build.VERSION;
import com.abaltatech.mcp.weblink.sdk.WLNotificationManager;
import com.abaltatech.mcs.logger.MCSLogger;
import com.waze.strings.DisplayStrings;
import java.io.InputStream;
import java.nio.ByteBuffer;

@SuppressLint({"NewApi"})
public abstract class WLAudioManager {
    private static final int ENCODER_ID = 86018;
    private static final String ENCODER_TYPE = "audio/mp4a-latm";
    private static final int[] FREQ_TABLE = new int[]{96000, 88200, 64000, 48000, 44100, 32000, 24000, 22050, 16000, 12000, 11025, WLNotificationManager.DEFAULT_TIMEOUT_MILLISECONDS, 7350};
    private static final String TAG = "WLAudioManager";
    private IAudioNotification m_listener = null;
    private TranscodeAudioThread m_transcoder;

    public interface IAudioNotification {
        void onAudioEnded() throws ;

        void onAudioPaused() throws ;

        void onAudioResumed() throws ;

        void onAudioStarted(String str) throws ;
    }

    class TranscodeAudioThread extends Thread {
        private InputStream m_audioStream = null;
        private String m_audioUrl;
        private volatile boolean m_isStopped = false;
        private volatile int m_pausedCnt = 0;

        private class TranscodeInfo {
            BufferInfo m_bufferInfo;
            int m_chanCfg;
            ByteBuffer[] m_decInputBuffers;
            ByteBuffer[] m_decOutputBuffers;
            MediaCodec m_decoder;
            int m_decoderBufIndex;
            boolean m_decoderFinished;
            boolean m_decoderStarted;
            ByteBuffer[] m_encInputBuffers;
            ByteBuffer[] m_encOutputBuffers;
            MediaCodec m_encoder;
            boolean m_encoderFinished;
            boolean m_encoderStarted;
            boolean m_eofSent;
            MediaExtractor m_extractor;
            boolean m_extractorFinished;
            int m_freqIdx;
            int m_profile;
            ByteBuffer m_readBuffer;
            int m_readBufferLen;

            private void transcodePacket() throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:57:0x0243
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
                r7 = r0.m_extractorFinished;
                if (r7 != 0) goto L_0x0051;
            L_0x0006:
                r0 = r30;
                r8 = r0.m_readBufferLen;
                if (r8 != 0) goto L_0x0051;
            L_0x000c:
                r0 = r30;
                r9 = r0.m_extractor;
                r0 = r30;
                r10 = r0.m_readBuffer;
                r11 = 0;
                r8 = r9.readSampleData(r10, r11);
                r0 = r30;
                r0.m_readBufferLen = r8;
                r0 = r30;
                r8 = r0.m_readBufferLen;
                r11 = -1;
                if (r8 != r11) goto L_0x0247;
            L_0x0024:
                r7 = 1;
            L_0x0025:
                r0 = r30;
                r0.m_extractorFinished = r7;
                r0 = r30;
                r8 = r0.m_readBufferLen;
                if (r8 <= 0) goto L_0x0051;
            L_0x002f:
                r0 = r30;
                r10 = r0.m_readBuffer;
                r0 = r30;
                r8 = r0.m_readBufferLen;
                r10.limit(r8);
                r0 = r30;
                r10 = r0.m_readBuffer;
                r11 = 0;
                r10.position(r11);
                r0 = r30;
                r9 = r0.m_extractor;
                r7 = r9.advance();
                if (r7 != 0) goto L_0x024d;
            L_0x004c:
                r7 = 1;
            L_0x004d:
                r0 = r30;
                r0.m_extractorFinished = r7;
            L_0x0051:
                r0 = r30;
                r7 = r0.m_decoderFinished;
                if (r7 != 0) goto L_0x0124;
            L_0x0057:
                r0 = r30;
                r8 = r0.m_readBufferLen;
                if (r8 > 0) goto L_0x0069;
            L_0x005d:
                r0 = r30;
                r7 = r0.m_extractorFinished;
                if (r7 == 0) goto L_0x00c6;
            L_0x0063:
                r0 = r30;
                r7 = r0.m_eofSent;
                if (r7 != 0) goto L_0x00c6;
            L_0x0069:
                r0 = r30;
                r12 = r0.m_decoder;
                r13 = 0;
                r8 = r12.dequeueInputBuffer(r13);
                if (r8 < 0) goto L_0x00c6;
            L_0x0075:
                r0 = r30;
                r15 = r0.m_decInputBuffers;
                r10 = r15[r8];
                r10.clear();
                r0 = r30;
                r0 = r0.m_readBufferLen;
                r16 = r0;
                if (r16 <= 0) goto L_0x0098;
            L_0x0086:
                r0 = r30;
                r0 = r0.m_readBuffer;
                r17 = r0;
                r10.put(r0);
                r0 = r30;
                r0 = r0.m_readBuffer;
                r17 = r0;
                r0.clear();
            L_0x0098:
                r11 = 0;
                r0 = r30;
                r0.m_readBufferLen = r11;
                r0 = r30;
                r12 = r0.m_decoder;
                r16 = r10.position();
                r0 = r30;
                r7 = r0.m_extractorFinished;
                if (r7 == 0) goto L_0x0253;
            L_0x00ab:
                r18 = 4;
            L_0x00ad:
                r11 = 0;
                r13 = 0;
                r0 = r12;
                r1 = r8;
                r2 = r11;
                r3 = r16;
                r4 = r13;
                r6 = r18;
                r0.queueInputBuffer(r1, r2, r3, r4, r6);
                r0 = r30;
                r7 = r0.m_extractorFinished;
                if (r7 == 0) goto L_0x00c6;
            L_0x00c1:
                r11 = 1;
                r0 = r30;
                r0.m_eofSent = r11;
            L_0x00c6:
                r0 = r30;
                r8 = r0.m_decoderBufIndex;
                if (r8 >= 0) goto L_0x0124;
            L_0x00cc:
                r0 = r30;
                r12 = r0.m_decoder;
                r0 = r30;
                r0 = r0.m_bufferInfo;
                r19 = r0;
                r13 = 0;
                r0 = r19;
                r8 = r12.dequeueOutputBuffer(r0, r13);
                r0 = r30;
                r0.m_decoderBufIndex = r8;
                r0 = r30;
                r8 = r0.m_decoderBufIndex;
                if (r8 < 0) goto L_0x025c;
            L_0x00e8:
                r0 = r30;
                r15 = r0.m_decOutputBuffers;
                r0 = r30;
                r8 = r0.m_decoderBufIndex;
                r10 = r15[r8];
                r0 = r30;
                r0 = r0.m_bufferInfo;
                r19 = r0;
                r8 = r0.size;
                r0 = r30;
                r0 = r0.m_bufferInfo;
                r19 = r0;
                r0 = r0.offset;
                r16 = r0;
                r8 = r8 + r0;
                r10.limit(r8);
                r0 = r30;
                r0 = r0.m_bufferInfo;
                r19 = r0;
                r8 = r0.offset;
                r10.position(r8);
                r0 = r30;
                r0 = r0.m_bufferInfo;
                r19 = r0;
                r8 = r0.flags;
                r8 = r8 & 4;
                if (r8 == 0) goto L_0x025a;
            L_0x011f:
                r7 = 1;
            L_0x0120:
                r0 = r30;
                r0.m_decoderFinished = r7;
            L_0x0124:
                r0 = r30;
                r7 = r0.m_encoderStarted;
                if (r7 == 0) goto L_0x0353;
            L_0x012a:
                r0 = r30;
                r8 = r0.m_decoderBufIndex;
                if (r8 < 0) goto L_0x019b;
            L_0x0130:
                r0 = r30;
                r12 = r0.m_encoder;
                r13 = 0;
                r8 = r12.dequeueInputBuffer(r13);
                r11 = -1;
                if (r8 == r11) goto L_0x019b;
            L_0x013d:
                r0 = r30;
                r15 = r0.m_encInputBuffers;
                r10 = r15[r8];
                r10.clear();
                r0 = r30;
                r15 = r0.m_decOutputBuffers;
                r0 = r30;
                r0 = r0.m_decoderBufIndex;
                r16 = r0;
                r17 = r15[r16];
                r0 = r17;
                r10.put(r0);
                r0 = r30;
                r15 = r0.m_decOutputBuffers;
                r0 = r30;
                r0 = r0.m_decoderBufIndex;
                r16 = r0;
                r17 = r15[r16];
                r0 = r17;
                r0.clear();
                r0 = r30;
                r12 = r0.m_decoder;
                r0 = r30;
                r0 = r0.m_decoderBufIndex;
                r16 = r0;
                r11 = 0;
                r0 = r16;
                r12.releaseOutputBuffer(r0, r11);
                r11 = -1;
                r0 = r30;
                r0.m_decoderBufIndex = r11;
                r0 = r30;
                r12 = r0.m_encoder;
                r16 = r10.position();
                r0 = r30;
                r7 = r0.m_decoderFinished;
                if (r7 == 0) goto L_0x0336;
            L_0x018b:
                r18 = 4;
            L_0x018d:
                r11 = 0;
                r13 = 0;
                r0 = r12;
                r1 = r8;
                r2 = r11;
                r3 = r16;
                r4 = r13;
                r6 = r18;
                r0.queueInputBuffer(r1, r2, r3, r4, r6);
            L_0x019b:
                r0 = r30;
                r12 = r0.m_encoder;
                r0 = r30;
                r0 = r0.m_bufferInfo;
                r19 = r0;
                r13 = 0;
                r0 = r19;
                r8 = r12.dequeueOutputBuffer(r0, r13);
                if (r8 < 0) goto L_0x033f;
            L_0x01af:
                r0 = r30;
                r0 = r0.m_bufferInfo;
                r19 = r0;
                r0 = r0.size;
                r16 = r0;
                r16 = r16 + 7;
                r0 = r16;
                r0 = new byte[r0];
                r20 = r0;
                r0 = r30;
                r15 = r0.m_encOutputBuffers;
                r10 = r15[r8];
                r0 = r30;
                r0 = r0.m_bufferInfo;
                r19 = r0;
                r0 = r0.flags;
                r21 = r0;
                r21 = r21 & 4;
                if (r21 == 0) goto L_0x033d;
            L_0x01d5:
                r7 = 1;
            L_0x01d6:
                r0 = r30;
                r0.m_encoderFinished = r7;
                r0 = r30;
                r0 = r0.m_bufferInfo;
                r19 = r0;
                r0 = r0.offset;
                r21 = r0;
                r0 = r30;
                r0 = r0.m_bufferInfo;
                r19 = r0;
                r0 = r0.size;
                r22 = r0;
                r0 = r21;
                r1 = r22;
                r0 = r0 + r1;
                r21 = r0;
                r10.limit(r0);
                r0 = r30;
                r0 = r0.m_bufferInfo;
                r19 = r0;
                r0 = r0.offset;
                r21 = r0;
                r10.position(r0);
                r0 = r30;
                r1 = r20;
                r2 = r16;
                r0.addADTStoPacket(r1, r2);
                r0 = r30;
                r0 = r0.m_bufferInfo;
                r19 = r0;
                r0 = r0.size;
                r16 = r0;
                r11 = 7;
                r0 = r20;
                r1 = r16;
                r10.get(r0, r11, r1);
                r0 = r30;
                r0 = com.abaltatech.weblinkserver.WLAudioManager.TranscodeAudioThread.this;
                r23 = r0;
                r0 = com.abaltatech.weblinkserver.WLAudioManager.this;
                r24 = r0;
                r0 = r20;
                r17 = java.nio.ByteBuffer.wrap(r0);
                r0 = r24;
                r1 = r17;
                r0.onAudioEncoded(r1);
                r10.clear();
                r0 = r30;
                r12 = r0.m_encoder;
                r11 = 0;
                r12.releaseOutputBuffer(r8, r11);
                return;
                goto L_0x0247;
            L_0x0244:
                goto L_0x0025;
            L_0x0247:
                r7 = 0;
                goto L_0x0244;
                goto L_0x024d;
            L_0x024a:
                goto L_0x004d;
            L_0x024d:
                r7 = 0;
                goto L_0x024a;
                goto L_0x0253;
            L_0x0250:
                goto L_0x00ad;
            L_0x0253:
                r18 = 0;
                goto L_0x0250;
                goto L_0x025a;
            L_0x0257:
                goto L_0x0120;
            L_0x025a:
                r7 = 0;
                goto L_0x0257;
            L_0x025c:
                r0 = r30;
                r8 = r0.m_decoderBufIndex;
                r11 = -3;
                if (r8 != r11) goto L_0x0274;
            L_0x0263:
                r0 = r30;
                r12 = r0.m_decoder;
                r15 = r12.getOutputBuffers();
                goto L_0x026f;
            L_0x026c:
                goto L_0x0124;
            L_0x026f:
                r0 = r30;
                r0.m_decOutputBuffers = r15;
                goto L_0x026c;
            L_0x0274:
                r0 = r30;
                r8 = r0.m_decoderBufIndex;
                r11 = -2;
                if (r8 != r11) goto L_0x0124;
            L_0x027b:
                r0 = r30;
                r12 = r0.m_decoder;
                r25 = r12.getOutputFormat();
                r26 = "sample-rate";
                r0 = r25;
                r1 = r26;
                r8 = r0.getInteger(r1);
                r26 = "channel-count";
                r0 = r25;
                r1 = r26;
                r16 = r0.getInteger(r1);
                r26 = "audio/mp4a-latm";
                r0 = r26;
                r1 = r16;
                r25 = android.media.MediaFormat.createAudioFormat(r0, r8, r1);
                r26 = "aac-profile";
                r11 = 2;
                r0 = r25;
                r1 = r26;
                r0.setInteger(r1, r11);
                r26 = "bitrate";
                r11 = 128000; // 0x1f400 float:1.79366E-40 double:6.32404E-319;
                r0 = r25;
                r1 = r26;
                r0.setInteger(r1, r11);
                r26 = "max-input-size";
                r11 = 98304; // 0x18000 float:1.37753E-40 double:4.85686E-319;
                r0 = r25;
                r1 = r26;
                r0.setInteger(r1, r11);
                r0 = r30;
                r12 = r0.m_encoder;
                r27 = 0;
                r28 = 0;
                r11 = 1;
                r0 = r25;
                r1 = r27;
                r2 = r28;
                r12.configure(r0, r1, r2, r11);
                r0 = r30;
                r12 = r0.m_encoder;
                r12.start();
                r11 = 1;
                r0 = r30;
                r0.m_encoderStarted = r11;
                r0 = r30;
                r12 = r0.m_encoder;
                r15 = r12.getInputBuffers();
                r0 = r30;
                r0.m_encInputBuffers = r15;
                r0 = r30;
                r12 = r0.m_encoder;
                r15 = r12.getOutputBuffers();
                r0 = r30;
                r0.m_encOutputBuffers = r15;
                r11 = 2;
                r0 = r30;
                r0.m_profile = r11;
                r0 = r16;
                r1 = r30;
                r1.m_chanCfg = r0;
                r11 = 4;
                r0 = r30;
                r0.m_freqIdx = r11;
                r16 = 0;
            L_0x030b:
                r29 = com.abaltatech.weblinkserver.WLAudioManager.FREQ_TABLE;
                r0 = r29;
                r0 = r0.length;
                r21 = r0;
                r0 = r16;
                r1 = r21;
                if (r0 >= r1) goto L_0x0124;
            L_0x031a:
                r29 = com.abaltatech.weblinkserver.WLAudioManager.FREQ_TABLE;
                r21 = r29[r16];
                r0 = r21;
                if (r0 != r8) goto L_0x032f;
            L_0x0324:
                goto L_0x0328;
            L_0x0325:
                goto L_0x0124;
            L_0x0328:
                r0 = r16;
                r1 = r30;
                r1.m_freqIdx = r0;
                goto L_0x0325;
            L_0x032f:
                r16 = r16 + 1;
                goto L_0x030b;
                goto L_0x0336;
            L_0x0333:
                goto L_0x018d;
            L_0x0336:
                r18 = 0;
                goto L_0x0333;
                goto L_0x033d;
            L_0x033a:
                goto L_0x01d6;
            L_0x033d:
                r7 = 0;
                goto L_0x033a;
            L_0x033f:
                r11 = -3;
                if (r8 != r11) goto L_0x034f;
            L_0x0342:
                r0 = r30;
                r12 = r0.m_encoder;
                r15 = r12.getOutputBuffers();
                r0 = r30;
                r0.m_encOutputBuffers = r15;
                return;
            L_0x034f:
                r11 = -2;
                if (r8 != r11) goto L_0x0354;
            L_0x0352:
                return;
            L_0x0353:
                return;
            L_0x0354:
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.WLAudioManager.TranscodeAudioThread.TranscodeInfo.transcodePacket():void");
            }

            boolean init(java.lang.String r22) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0044 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
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
                r21 = this;
                r2 = new android.media.MediaExtractor;
                r2.<init>();	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r0.m_extractor = r2;	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r2 = r0.m_extractor;	 Catch:{ Exception -> 0x00ed }
                r0 = r22;	 Catch:{ Exception -> 0x00ed }
                r2.setDataSource(r0);	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r2 = r0.m_extractor;	 Catch:{ Exception -> 0x00ed }
                r3 = r2.getTrackCount();	 Catch:{ Exception -> 0x00ed }
                r4 = 0;
                r22 = 0;
                r5 = 0;
                r6 = 0;
            L_0x001f:
                if (r6 >= r3) goto L_0x0044;	 Catch:{ Exception -> 0x00ed }
            L_0x0021:
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r2 = r0.m_extractor;	 Catch:{ Exception -> 0x00ed }
                r7 = r2.getTrackFormat(r6);	 Catch:{ Exception -> 0x00ed }
                r4 = r7;	 Catch:{ Exception -> 0x00ed }
                r9 = "mime";	 Catch:{ Exception -> 0x00ed }
                r8 = r7.getString(r9);	 Catch:{ Exception -> 0x00ed }
                r22 = r8;
                if (r8 == 0) goto L_0x00e0;	 Catch:{ Exception -> 0x00ed }
            L_0x0034:
                r9 = "audio/";	 Catch:{ Exception -> 0x00ed }
                r10 = r8.indexOf(r9);	 Catch:{ Exception -> 0x00ed }
                if (r10 != 0) goto L_0x00e0;	 Catch:{ Exception -> 0x00ed }
            L_0x003c:
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r2 = r0.m_extractor;	 Catch:{ Exception -> 0x00ed }
                r2.selectTrack(r6);	 Catch:{ Exception -> 0x00ed }
                r5 = 1;
            L_0x0044:
                if (r5 == 0) goto L_0x0100;	 Catch:{ Exception -> 0x00ed }
            L_0x0046:
                r11 = 0;	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r0.m_encoderStarted = r11;	 Catch:{ Exception -> 0x00ed }
                r11 = 0;	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r0.m_decoderStarted = r11;	 Catch:{ Exception -> 0x00ed }
                r11 = 0;	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r0.m_extractorFinished = r11;	 Catch:{ Exception -> 0x00ed }
                r11 = 0;	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r0.m_decoderFinished = r11;	 Catch:{ Exception -> 0x00ed }
                r11 = 0;	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r0.m_encoderFinished = r11;	 Catch:{ Exception -> 0x00ed }
                r11 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;	 Catch:{ Exception -> 0x00ed }
                r12 = java.nio.ByteBuffer.allocate(r11);	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r0.m_readBuffer = r12;	 Catch:{ Exception -> 0x00ed }
                r9 = "audio/mp4a-latm";	 Catch:{ Exception -> 0x00ed }
                r11 = 1;	 Catch:{ Exception -> 0x00ed }
                r13 = 0;	 Catch:{ Exception -> 0x00ed }
                r5 = com.abaltatech.weblinkserver.WLAudioManager.hasCodec(r9, r11, r13);	 Catch:{ Exception -> 0x00ed }
                if (r5 == 0) goto L_0x010a;	 Catch:{ Exception -> 0x00ed }
            L_0x0074:
                goto L_0x0078;	 Catch:{ Exception -> 0x00ed }
            L_0x0075:
                goto L_0x001f;	 Catch:{ Exception -> 0x00ed }
            L_0x0078:
                r11 = 0;	 Catch:{ Exception -> 0x00ed }
                r13 = 1;	 Catch:{ Exception -> 0x00ed }
                r0 = r22;	 Catch:{ Exception -> 0x00ed }
                r5 = com.abaltatech.weblinkserver.WLAudioManager.hasCodec(r0, r11, r13);	 Catch:{ Exception -> 0x00ed }
                if (r5 == 0) goto L_0x010c;	 Catch:{ Exception -> 0x00ed }
            L_0x0082:
                r0 = r22;	 Catch:{ Exception -> 0x00ed }
                r14 = android.media.MediaCodec.createDecoderByType(r0);	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r0.m_decoder = r14;	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r14 = r0.m_decoder;	 Catch:{ Exception -> 0x00ed }
                if (r14 == 0) goto L_0x00e3;	 Catch:{ Exception -> 0x00ed }
            L_0x0092:
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r14 = r0.m_decoder;	 Catch:{ Exception -> 0x00ed }
                r15 = 0;	 Catch:{ Exception -> 0x00ed }
                r16 = 0;	 Catch:{ Exception -> 0x00ed }
                r11 = 0;	 Catch:{ Exception -> 0x00ed }
                r0 = r16;	 Catch:{ Exception -> 0x00ed }
                r14.configure(r4, r15, r0, r11);	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r14 = r0.m_decoder;	 Catch:{ Exception -> 0x00ed }
                r14.start();	 Catch:{ Exception -> 0x00ed }
                r11 = 1;	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r0.m_decoderStarted = r11;	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r14 = r0.m_decoder;	 Catch:{ Exception -> 0x00ed }
                r17 = r14.getInputBuffers();	 Catch:{ Exception -> 0x00ed }
                r0 = r17;	 Catch:{ Exception -> 0x00ed }
                r1 = r21;	 Catch:{ Exception -> 0x00ed }
                r1.m_decInputBuffers = r0;	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r14 = r0.m_decoder;	 Catch:{ Exception -> 0x00ed }
                r17 = r14.getOutputBuffers();	 Catch:{ Exception -> 0x00ed }
                r0 = r17;	 Catch:{ Exception -> 0x00ed }
                r1 = r21;	 Catch:{ Exception -> 0x00ed }
                r1.m_decOutputBuffers = r0;	 Catch:{ Exception -> 0x00ed }
                r9 = "audio/mp4a-latm";	 Catch:{ Exception -> 0x00ed }
                r14 = android.media.MediaCodec.createEncoderByType(r9);	 Catch:{ Exception -> 0x00ed }
                r0 = r21;	 Catch:{ Exception -> 0x00ed }
                r0.m_encoder = r14;	 Catch:{ Exception -> 0x00ed }
                r18 = new android.media.MediaCodec$BufferInfo;	 Catch:{ Exception -> 0x00ed }
                r0 = r18;	 Catch:{ Exception -> 0x00ed }
                r0.<init>();	 Catch:{ Exception -> 0x00ed }
                r0 = r18;	 Catch:{ Exception -> 0x00ed }
                r1 = r21;	 Catch:{ Exception -> 0x00ed }
                r1.m_bufferInfo = r0;	 Catch:{ Exception -> 0x00ed }
                r11 = 1;
                return r11;
            L_0x00e0:
                r6 = r6 + 1;
                goto L_0x0075;
            L_0x00e3:
                r19 = new java.lang.Exception;	 Catch:{ Exception -> 0x00ed }
                r9 = "Failed to create audio encoder: audio/mp4a-latm";	 Catch:{ Exception -> 0x00ed }
                r0 = r19;	 Catch:{ Exception -> 0x00ed }
                r0.<init>(r9);	 Catch:{ Exception -> 0x00ed }
                throw r19;	 Catch:{ Exception -> 0x00ed }
            L_0x00ed:
                r19 = move-exception;
                r9 = "WLAudioManager";
                r20 = "Error detected:";
                r0 = r20;
                r1 = r19;
                com.abaltatech.mcs.logger.MCSLogger.log(r9, r0, r1);
                r0 = r21;
                r0.terminate();
                r11 = 0;
                return r11;
            L_0x0100:
                r19 = new java.lang.Exception;
                r9 = "No audio found!";	 Catch:{ Exception -> 0x00ed }
                r0 = r19;	 Catch:{ Exception -> 0x00ed }
                r0.<init>(r9);	 Catch:{ Exception -> 0x00ed }
                throw r19;	 Catch:{ Exception -> 0x00ed }
            L_0x010a:
                r11 = 1;
                return r11;
            L_0x010c:
                r11 = 1;
                return r11;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.WLAudioManager.TranscodeAudioThread.TranscodeInfo.init(java.lang.String):boolean");
            }

            private TranscodeInfo() throws  {
                this.m_decoderBufIndex = -1;
            }

            void terminate() throws  {
                if (this.m_extractor != null) {
                    this.m_extractor.release();
                    this.m_extractor = null;
                }
                if (this.m_encoder != null) {
                    if (this.m_encoderStarted) {
                        this.m_encoderStarted = false;
                        this.m_encoder.stop();
                    }
                    this.m_encoder.release();
                    this.m_encoder = null;
                }
                if (this.m_decoder != null) {
                    if (this.m_decoderStarted) {
                        this.m_decoderStarted = false;
                        this.m_decoder.stop();
                    }
                    this.m_decoder.release();
                    this.m_decoder = null;
                }
                this.m_encoderStarted = false;
                this.m_decoderStarted = false;
                this.m_extractorFinished = false;
                this.m_decoderFinished = false;
                this.m_encoderFinished = false;
                if (this.m_readBuffer != null) {
                    this.m_readBuffer.clear();
                }
            }

            private void addADTStoPacket(byte[] $r1, int $i0) throws  {
                $r1[0] = (byte) -1;
                $r1[1] = (byte) -7;
                $r1[2] = (byte) ((((this.m_profile - 1) << 6) + (this.m_freqIdx << 2)) + (this.m_chanCfg >> 2));
                $r1[3] = (byte) (((this.m_chanCfg & 3) << 6) + ($i0 >> 11));
                $r1[4] = (byte) (($i0 & DisplayStrings.DS_RIDE_REQ_MOCK_TEXT_BOX_LIVE_PS) >> 3);
                $r1[5] = (byte) ((($i0 & 7) << 5) + 31);
                $r1[6] = (byte) -4;
            }
        }

        public void run() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x006d in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r20 = this;
            r1 = new com.abaltatech.weblinkserver.WLAudioManager$TranscodeAudioThread$TranscodeInfo;
            r2 = 0;
            r0 = r20;
            r1.<init>();
            r0 = r20;
            r3 = r0.m_audioUrl;
            r4 = r1.init(r3);
            if (r4 == 0) goto L_0x00a3;
        L_0x0012:
            r0 = r20;
            r5 = com.abaltatech.weblinkserver.WLAudioManager.this;
            r0 = r20;
            r3 = r0.m_audioUrl;
            r6 = 86018; // 0x15002 float:1.20537E-40 double:4.24985E-319;
            r5.notifyAudioStarted(r3, r6);
            r7 = new java.lang.StringBuilder;
            r7.<init>();
            r8 = "Start audio download:";
            r7 = r7.append(r8);
            r0 = r20;
            r3 = r0.m_audioUrl;
            r7 = r7.append(r3);
            r3 = r7.toString();
            r8 = "WLAudioManager";
            com.abaltatech.mcs.logger.MCSLogger.log(r8, r3);
        L_0x003c:
            r0 = r20;	 Catch:{ InterruptedException -> 0x005a, Exception -> 0x0071, Throwable -> 0x008f }
            r4 = r0.isInterrupted();	 Catch:{ InterruptedException -> 0x005a, Exception -> 0x0071, Throwable -> 0x008f }
            if (r4 != 0) goto L_0x0084;
        L_0x0044:
            r0 = r20;
            r4 = r0.m_isStopped;
            if (r4 != 0) goto L_0x0084;
        L_0x004a:
            r4 = r1.m_encoderFinished;
            if (r4 != 0) goto L_0x0084;
        L_0x004e:
            r0 = r20;
            r9 = r0.m_pausedCnt;
            if (r9 <= 0) goto L_0x006d;	 Catch:{ InterruptedException -> 0x005a, Exception -> 0x0071, Throwable -> 0x008f }
        L_0x0054:
            r10 = 10;	 Catch:{ InterruptedException -> 0x005a, Exception -> 0x0071, Throwable -> 0x008f }
            sleep(r10);	 Catch:{ InterruptedException -> 0x005a, Exception -> 0x0071, Throwable -> 0x008f }
            goto L_0x003c;
        L_0x005a:
            r12 = move-exception;
            r1.terminate();
        L_0x005e:
            r0 = r20;
            r5 = com.abaltatech.weblinkserver.WLAudioManager.this;
            r5.notifyAudioEnded();
        L_0x0065:
            r8 = "WLAudioManager";
            r13 = "Audio download finished";
            com.abaltatech.mcs.logger.MCSLogger.log(r8, r13);
            return;
        L_0x006d:
            r1.transcodePacket();	 Catch:{ InterruptedException -> 0x005a, Exception -> 0x0071, Throwable -> 0x008f }
            goto L_0x003c;
        L_0x0071:
            r14 = move-exception;
            r8 = "WLAudioManager";	 Catch:{ InterruptedException -> 0x005a, Exception -> 0x0071, Throwable -> 0x008f }
            r13 = "Error detected:";	 Catch:{ InterruptedException -> 0x005a, Exception -> 0x0071, Throwable -> 0x008f }
            com.abaltatech.mcs.logger.MCSLogger.log(r8, r13, r14);	 Catch:{ InterruptedException -> 0x005a, Exception -> 0x0071, Throwable -> 0x008f }
            r1.terminate();
        L_0x007c:
            r0 = r20;
            r5 = com.abaltatech.weblinkserver.WLAudioManager.this;
            r5.notifyAudioEnded();
            goto L_0x0065;
        L_0x0084:
            r1.terminate();
        L_0x0087:
            r0 = r20;
            r5 = com.abaltatech.weblinkserver.WLAudioManager.this;
            r5.notifyAudioEnded();
            goto L_0x0065;
        L_0x008f:
            r15 = move-exception;
            r1.terminate();	 Catch:{ Exception -> 0x00a1 }
        L_0x0093:
            r0 = r20;
            r5 = com.abaltatech.weblinkserver.WLAudioManager.this;
            r5.notifyAudioEnded();
            throw r15;
        L_0x009b:
            r16 = move-exception;
            goto L_0x0087;
        L_0x009d:
            r17 = move-exception;
            goto L_0x005e;
        L_0x009f:
            r18 = move-exception;
            goto L_0x007c;
        L_0x00a1:
            r19 = move-exception;
            goto L_0x0093;
        L_0x00a3:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblinkserver.WLAudioManager.TranscodeAudioThread.run():void");
        }

        TranscodeAudioThread() throws  {
        }

        public String getUrl() throws  {
            return this.m_audioUrl;
        }

        public void startAudio(String $r1) throws  {
            this.m_audioUrl = $r1;
            start();
        }

        public boolean isPaused() throws  {
            return this.m_pausedCnt > 0;
        }

        public void stopAudio() throws  {
            this.m_isStopped = true;
            this.m_pausedCnt = 0;
            interrupt();
            if (this.m_audioStream != null) {
                try {
                    this.m_audioStream.close();
                } catch (Exception e) {
                }
            }
        }

        public void pauseAudio() throws  {
            this.m_pausedCnt++;
            if (this.m_pausedCnt == 1) {
                WLAudioManager.this.notifyAudioPaused();
            }
        }

        public void resumeAudio(boolean $z0) throws  {
            if (this.m_pausedCnt > 0) {
                if ($z0) {
                    this.m_pausedCnt = 0;
                } else {
                    this.m_pausedCnt--;
                }
                if (this.m_pausedCnt == 0) {
                    WLAudioManager.this.notifyAudioResumed();
                }
            }
        }
    }

    protected abstract void onAudioEncoded(ByteBuffer byteBuffer) throws ;

    public static boolean isSupported() throws  {
        return VERSION.SDK_INT >= 16 && hasCodec(ENCODER_TYPE, true, false);
    }

    public boolean init() throws  {
        return isSupported();
    }

    public void terminate() throws  {
        stopAudio();
    }

    public void setListener(IAudioNotification $r1) throws  {
        this.m_listener = $r1;
    }

    public void startAudio(String $r1) throws  {
        synchronized (this) {
            if ($r1 != null) {
                if (this.m_transcoder == null || !$r1.contentEquals(this.m_transcoder.getUrl())) {
                    stopAudio();
                    this.m_transcoder = new TranscodeAudioThread();
                    this.m_transcoder.startAudio($r1);
                } else if (this.m_transcoder.isPaused()) {
                    this.m_transcoder.resumeAudio(true);
                }
            }
        }
    }

    public void pauseAudio() throws  {
        synchronized (this) {
            if (this.m_transcoder != null) {
                this.m_transcoder.pauseAudio();
            }
        }
    }

    public void resumeAudio() throws  {
        synchronized (this) {
            if (this.m_transcoder != null) {
                this.m_transcoder.resumeAudio(false);
            }
        }
    }

    public void stopAudio() throws  {
        synchronized (this) {
            if (this.m_transcoder != null) {
                this.m_transcoder.stopAudio();
                try {
                    this.m_transcoder.join();
                } catch (Exception e) {
                }
                this.m_transcoder = null;
            }
        }
    }

    protected WLAudioManager() throws  {
    }

    protected void notifyAudioStarted(String $r1, int codecID) throws  {
        IAudioNotification $r2 = this.m_listener;
        MCSLogger.log(TAG, "Audio started: " + $r1);
        if ($r2 != null) {
            $r2.onAudioStarted($r1);
        }
    }

    protected void notifyAudioPaused() throws  {
        IAudioNotification $r1 = this.m_listener;
        MCSLogger.log(TAG, "Audio paused");
        if ($r1 != null) {
            $r1.onAudioPaused();
        }
    }

    protected void notifyAudioResumed() throws  {
        IAudioNotification $r1 = this.m_listener;
        MCSLogger.log(TAG, "Audio resumed");
        if ($r1 != null) {
            $r1.onAudioResumed();
        }
    }

    protected void notifyAudioEnded() throws  {
        IAudioNotification $r1 = this.m_listener;
        MCSLogger.log(TAG, "Audio ended");
        if ($r1 != null) {
            $r1.onAudioEnded();
        }
    }

    public static boolean hasCodec(String $r0, boolean $z0, boolean $z1) throws  {
        int $i1 = MediaCodecList.getCodecCount();
        boolean $z2 = false;
        int $i2 = 0;
        while (!$z2 && $i2 < $i1) {
            MediaCodecInfo $r2 = MediaCodecList.getCodecInfoAt($i2);
            for (String $r1 : $r2.getSupportedTypes()) {
                boolean $z3 = $r0.contentEquals($r1);
                $z2 = $z3;
                if ($z3 && $z0) {
                    $z2 = $r2.isEncoder();
                }
                if ($z2 && $z1) {
                    $z2 = !$r2.isEncoder();
                }
                if ($z2) {
                    break;
                }
            }
            $i2++;
        }
        return $z2;
    }
}
